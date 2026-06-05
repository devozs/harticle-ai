package com.devozs.components.harticle.inference.service;

import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.harticle.inference.domain.InferenceStatus;
import com.devozs.components.harticle.inference.dto.EngineInferRequest;
import com.devozs.components.harticle.inference.dto.EngineInferResponse;
import com.devozs.components.harticle.inference.dto.InferenceModelOption;
import com.devozs.components.harticle.inference.dto.InferenceResultReport;
import com.devozs.components.harticle.inference.dto.InferenceRunDto;
import com.devozs.components.harticle.inference.dto.InferenceRunSummary;
import com.devozs.components.harticle.inference.entity.InferenceRun;
import com.devozs.components.harticle.inference.repository.InferenceRunRepository;
import com.devozs.components.harticle.training.domain.TrainingStatus;
import com.devozs.components.harticle.training.entity.ComputeResource;
import com.devozs.components.harticle.training.entity.TrainingSession;
import com.devozs.components.harticle.training.repository.ComputeResourceRepository;
import com.devozs.components.harticle.training.service.ComputeResourceService;
import com.devozs.components.harticle.training.service.TrainingSessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Admin-side lifecycle for inference test runs. A run sources its model from a
 * COMPLETED training session and executes one of two ways:
 * <ul>
 *   <li><b>LOCAL</b>: management calls the co-located engine ({@code /engine/infer})
 *       on the deployment CPU, {@link Async} so the POST returns immediately and
 *       the FE polls status.</li>
 *   <li><b>GPU/HPU</b>: the run is queued PENDING and a matching agent claims it via
 *       the training pull protocol, then POSTs the result back.</li>
 * </ul>
 */
@Service
@Slf4j
public class InferenceService {

    static final String TARGET_LOCAL = "LOCAL";

    private static final int DEFAULT_TEMPERATURE = 50;
    private static final int DEFAULT_MAX_LENGTH = 512;
    private static final int DEFAULT_NUM_RETURN = 3;

    private final InferenceRunRepository runRepository;
    private final TrainingSessionService sessionService;
    private final ComputeResourceService resourceService;
    private final ComputeResourceRepository resourceRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    /** Self-reference (proxied) so {@link #runLocalAsync} actually runs async — a
     * direct this.runLocalAsync() call would bypass the @Async proxy. */
    private final InferenceService self;

    @Value("${api.harticle-engine.url}")
    private String engineUrl;

    public InferenceService(InferenceRunRepository runRepository,
                            TrainingSessionService sessionService,
                            ComputeResourceService resourceService,
                            ComputeResourceRepository resourceRepository,
                            @Qualifier("inferenceRestTemplate") RestTemplate restTemplate,
                            @Lazy InferenceService self) {
        this.runRepository = runRepository;
        this.sessionService = sessionService;
        this.resourceService = resourceService;
        this.resourceRepository = resourceRepository;
        this.restTemplate = restTemplate;
        this.self = self;
    }

    // --- model options -------------------------------------------------------

    public List<InferenceModelOption> models() {
        return sessionService.completedModels().stream()
                .map(s -> InferenceModelOption.builder()
                        .sessionId(s.getId())
                        .name(s.getName())
                        .baseModel(s.getBaseModel())
                        .outputModelRef(s.getOutputModelRef())
                        .availableLocal(sessionService.isModelAvailableLocally(s))
                        .build())
                .toList();
    }

    // --- create + submit -----------------------------------------------------

    public InferenceRun create(InferenceRunDto dto) {
        if (dto.getSourceSessionId() == null) {
            throw new IllegalArgumentException("sourceSessionId is required");
        }
        if (dto.getPrompt() == null || dto.getPrompt().isBlank()) {
            throw new IllegalArgumentException("prompt is required");
        }
        TrainingSession source = sessionService.get(dto.getSourceSessionId());
        if (source.getStatus() != TrainingStatus.COMPLETED
                || source.getOutputModelRef() == null || source.getOutputModelRef().isBlank()) {
            throw new IllegalStateException("source session has no trained model to test (must be COMPLETED with an output model)");
        }

        boolean local = TARGET_LOCAL.equalsIgnoreCase(dto.getTarget());
        // LOCAL can only load a model whose files are on THIS host. A file:// model
        // trained on a remote box isn't reachable until it's been fetched to local;
        // reject up front with a clear message instead of failing mid-generation.
        if (local && !sessionService.isModelAvailableLocally(source)) {
            throw new IllegalStateException(
                    "this model is not available locally — fetch it to local storage first, or run it on the GPU/HPU resource that trained it");
        }
        InferenceRun.InferenceRunBuilder<?, ?> builder = InferenceRun.builder()
                .sourceSessionId(source.getId())
                .modelRef(source.getOutputModelRef())
                .baseModel(source.getBaseModel())
                .prompt(dto.getPrompt())
                .params(buildParams(dto))
                .status(InferenceStatus.PENDING)
                .local(local);

        if (!local) {
            ComputeResource resource = resourceRepository.findById(UUID.fromString(dto.getTarget()))
                    .orElseThrow(() -> new NoSuchElementException("compute resource not found: " + dto.getTarget()));
            builder.requiredType(resource.getType());
        }

        InferenceRun run = runRepository.save(builder.build());

        if (local) {
            log.info("inference run {} created (LOCAL) — dispatching to engine {}", run.getId(), engineUrl);
            self.runLocalAsync(run.getId());
        } else {
            log.info("inference run {} created (GPU/HPU, type={}) — awaiting agent claim",
                    run.getId(), run.getRequiredType());
        }
        return run;
    }

    /**
     * LOCAL execution: call the co-located engine on the deployment CPU and write
     * the result back. Runs async so the create request returns immediately and the
     * FE polls status, keeping both targets uniform from the UI's perspective.
     */
    @Async
    public void runLocalAsync(UUID runId) {
        log.info("local inference {} starting on engine {}", runId, engineUrl);
        InferenceRun run = get(runId);
        long start = System.currentTimeMillis();
        run.setStatus(InferenceStatus.RUNNING);
        run.setStartedAt(new Date(start));
        // Capture the merged (managed) copy: save() returns a new instance with the
        // bumped @Version; the passed entity keeps the old one. finish() reloads
        // anyway, but keep this correct for the in-flight reference.
        run = runRepository.save(run);
        try {
            EngineInferRequest req = EngineInferRequest.builder()
                    .modelRef(run.getModelRef())
                    .baseModel(run.getBaseModel())
                    .storageKind("local")
                    .modelKeyPrefix("models/" + run.getSourceSessionId())
                    .prompt(run.getPrompt())
                    .temperature(readParam(run, "temperature", DEFAULT_TEMPERATURE))
                    .maxLength(readParam(run, "maxLength", DEFAULT_MAX_LENGTH))
                    .numReturnSequences(readParam(run, "numReturnSequences", DEFAULT_NUM_RETURN))
                    .build();
            EngineInferResponse resp = restTemplate.postForObject(
                    engineUrl + "/engine/infer", req, EngineInferResponse.class);
            if (resp == null) {
                throw new IllegalStateException("engine returned an empty response");
            }
            if (resp.getError() != null) {
                throw new IllegalStateException(resp.getError());
            }
            log.info("local inference {} done on {} ({} sample(s), {}ms)",
                    runId, resp.getDevice(),
                    resp.getOutputs() == null ? 0 : resp.getOutputs().size(),
                    System.currentTimeMillis() - start);
            finish(run, resp.getOutputs(), null, null);
        } catch (Exception e) {
            log.error("local inference failed for run {}", runId, e);
            finish(run, null, ErrorType.INTERNAL, e.getMessage());
        }
    }

    // --- agent callback (GPU/HPU path) ---------------------------------------

    public void recordResult(InferenceRun run, InferenceResultReport report) {
        boolean failed = report.getOutputs() == null || report.getErrorType() != null || report.getMessage() != null;
        if (failed) {
            finish(run, null,
                    report.getErrorType() == null ? ErrorType.INTERNAL : report.getErrorType(),
                    report.getMessage());
        } else {
            finish(run, report.getOutputs(), null, null);
        }
        freeResource(run);
    }

    private void finish(InferenceRun staleRun, List<String> outputs, ErrorType errorType, String message) {
        // Reload fresh by id: the entity passed in was detached before a long
        // generation (LOCAL @Async / GPU callback), so its @Version is stale and
        // saving it directly throws OptimisticLockingFailure. Reloading also drops
        // a late result for a run that was deleted mid-flight (findById empty).
        InferenceRun run = runRepository.findById(staleRun.getId()).orElse(null);
        if (run == null) {
            log.info("inference run {} was deleted before its result arrived; dropping", staleRun.getId());
            return;
        }
        Date now = new Date();
        if (errorType != null || message != null) {
            run.setStatus(InferenceStatus.FAILED);
            run.setErrorType(errorType == null ? ErrorType.INTERNAL : errorType);
            run.setErrorMessage(message);
        } else {
            run.setStatus(InferenceStatus.COMPLETED);
            run.setOutputs(writeOutputs(outputs));
        }
        run.setFinishedAt(now);
        if (run.getStartedAt() != null) {
            run.setDurationMs(now.getTime() - run.getStartedAt().getTime());
        }
        runRepository.save(run);
    }

    private void freeResource(InferenceRun run) {
        if (run.getAssignedResourceId() == null) {
            return;
        }
        resourceRepository.findById(run.getAssignedResourceId()).ifPresent(resource -> {
            // Only reclaim the box if it's still pinned to THIS run, so we don't
            // free a resource that has since moved on to another job.
            if (run.getId().equals(resource.getCurrentSessionId())) {
                resourceService.markIdle(resource);
            }
        });
    }

    // --- read-back -----------------------------------------------------------

    public InferenceRun get(UUID id) {
        return runRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("inference run not found: " + id));
    }

    public List<InferenceRunSummary> summaries() {
        return runRepository.findAllByOrderByCreatedAtDesc().stream().map(this::toSummary).toList();
    }

    public InferenceRunSummary snapshot(UUID id) {
        return toSummary(get(id));
    }

    /**
     * Delete an inference run, freeing any compute it holds. If the run is in-flight
     * on a GPU/HPU agent, freeing the resource is the operational kill (we can't
     * reach a remote outbound-only agent; its late result is dropped by
     * {@link #recordResult} once the row is gone). A LOCAL run's @Async engine call
     * can't be interrupted mid-generation, but the row removal makes its result a
     * no-op too.
     *
     * <p>No artifacts are deleted here: an inference run owns nothing in storage —
     * the model it tested belongs to the source training session and must survive.
     */
    public void delete(UUID id) {
        runRepository.findById(id).ifPresent(this::freeResource);
        runRepository.deleteById(id);
    }

    public InferenceRunSummary toSummary(InferenceRun r) {
        String resourceName = null;
        if (r.getAssignedResourceId() != null) {
            resourceName = resourceRepository.findById(r.getAssignedResourceId())
                    .map(ComputeResource::getName).orElse(null);
        }
        return InferenceRunSummary.builder()
                .id(r.getId())
                .sourceSessionId(r.getSourceSessionId())
                .modelRef(r.getModelRef())
                .baseModel(r.getBaseModel())
                .prompt(r.getPrompt())
                .status(r.getStatus())
                .local(r.isLocal())
                .requiredType(r.getRequiredType())
                .assignedResourceId(r.getAssignedResourceId())
                .assignedResourceName(resourceName)
                .outputs(readOutputs(r.getOutputs()))
                .errorMessage(r.getErrorMessage())
                .errorType(r.getErrorType())
                .createdAtEpochMs(r.getCreatedAt() == null ? null : r.getCreatedAt().getTime())
                .durationMs(r.getDurationMs())
                .build();
    }

    // --- JSON helpers --------------------------------------------------------

    private String buildParams(InferenceRunDto dto) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("temperature", dto.getTemperature() == null ? DEFAULT_TEMPERATURE : dto.getTemperature());
        node.put("maxLength", dto.getMaxLength() == null ? DEFAULT_MAX_LENGTH : dto.getMaxLength());
        node.put("numReturnSequences", dto.getNumReturnSequences() == null ? DEFAULT_NUM_RETURN : dto.getNumReturnSequences());
        return node.toString();
    }

    int readParam(InferenceRun run, String field, int fallback) {
        try {
            var node = objectMapper.readTree(run.getParams());
            var v = node.get(field);
            return v == null ? fallback : v.asInt(fallback);
        } catch (Exception e) {
            return fallback;
        }
    }

    private String writeOutputs(List<String> outputs) {
        try {
            return objectMapper.writeValueAsString(outputs == null ? List.of() : outputs);
        } catch (Exception e) {
            log.warn("could not serialize inference outputs", e);
            return "[]";
        }
    }

    private List<String> readOutputs(String json) {
        if (json == null || json.isBlank()) {
            return null;
        }
        try {
            var node = objectMapper.readTree(json);
            if (!node.isArray()) {
                return null;
            }
            List<String> out = new ArrayList<>();
            node.forEach(n -> out.add(n.asText()));
            return out;
        } catch (Exception e) {
            log.warn("could not parse inference outputs: {}", json, e);
            return null;
        }
    }
}
