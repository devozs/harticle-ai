package com.devozs.components.harticle.training.controller;

import com.devozs.components.harticle.training.dto.ComputeResourceDto;
import com.devozs.components.harticle.training.dto.EnrollmentCodeResponse;
import com.devozs.components.harticle.training.dto.TrainingLogDto;
import com.devozs.components.harticle.training.dto.TrainingSessionDto;
import com.devozs.components.harticle.training.dto.TrainingSessionSummary;
import com.devozs.components.harticle.training.entity.ComputeResource;
import com.devozs.components.harticle.training.entity.TrainingSession;
import com.devozs.components.harticle.training.service.ComputeResourceService;
import com.devozs.components.harticle.training.service.TrainingSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Admin/FE surface for GPU training: register compute resources, issue enrollment
 * codes, create/stop/resume training sessions, and poll live status + logs. The
 * agent-facing protocol lives separately in {@link TrainingAgentController}.
 */
@RestController
@RequestMapping(TrainingURLS.URL)
@Slf4j
public class TrainingController {

    private final ComputeResourceService resourceService;
    private final TrainingSessionService sessionService;

    public TrainingController(ComputeResourceService resourceService,
                              TrainingSessionService sessionService) {
        this.resourceService = resourceService;
        this.sessionService = sessionService;
    }

    // --- compute resources ---------------------------------------------------

    @GetMapping(TrainingURLS.RESOURCES)
    @ResponseBody
    public List<ComputeResource> getResources() {
        return resourceService.getAll();
    }

    @PostMapping(TrainingURLS.RESOURCES)
    @ResponseBody
    public ComputeResource createResource(@RequestBody ComputeResourceDto dto) {
        return resourceService.create(dto);
    }

    @PutMapping(TrainingURLS.RESOURCES + TrainingURLS.ID)
    @ResponseBody
    public ComputeResource updateResource(@PathVariable UUID id, @RequestBody ComputeResourceDto dto) {
        return resourceService.update(id, dto);
    }

    @DeleteMapping(TrainingURLS.RESOURCES + TrainingURLS.ID)
    public ResponseEntity<Void> deleteResource(@PathVariable UUID id) {
        resourceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** (Re)issue a one-time enrollment code for an agent to redeem. Shown once. */
    @PostMapping(TrainingURLS.RESOURCES + TrainingURLS.ID + TrainingURLS.ENROLLMENT_CODE)
    @ResponseBody
    public EnrollmentCodeResponse issueEnrollmentCode(@PathVariable UUID id) {
        return resourceService.issueEnrollmentCode(id);
    }

    /** Ask the box to re-run its readiness preflight (rejected while BUSY). */
    @PostMapping(TrainingURLS.RESOURCES + TrainingURLS.ID + TrainingURLS.REVERIFY)
    @ResponseBody
    public ComputeResource reverify(@PathVariable UUID id) {
        return resourceService.requestReverify(id);
    }

    // --- training sessions ---------------------------------------------------

    @GetMapping(TrainingURLS.SESSIONS)
    @ResponseBody
    public List<TrainingSessionSummary> getSessions() {
        return sessionService.summaries();
    }

    @PostMapping(TrainingURLS.SESSIONS)
    @ResponseBody
    public TrainingSession createSession(@RequestBody TrainingSessionDto dto) {
        return sessionService.create(dto);
    }

    @DeleteMapping(TrainingURLS.SESSIONS + TrainingURLS.ID)
    public ResponseEntity<Void> deleteSession(@PathVariable UUID id) {
        sessionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** Live snapshot for the monitor view (the DB-backed analogue of scrape status). */
    @GetMapping(TrainingURLS.SESSIONS + TrainingURLS.ID + TrainingURLS.STATUS)
    @ResponseBody
    public TrainingSessionSummary sessionStatus(@PathVariable UUID id) {
        return sessionService.snapshot(id);
    }

    /** Incremental log tail; pass {@code afterSeq} to fetch only newer lines. */
    @GetMapping(TrainingURLS.SESSIONS + TrainingURLS.ID + TrainingURLS.LOGS)
    @ResponseBody
    public List<TrainingLogDto> sessionLogs(@PathVariable UUID id,
                                            @RequestParam(required = false, defaultValue = "-1") long afterSeq) {
        return sessionService.logs(id, afterSeq);
    }

    /** Cooperative stop: the agent checkpoints and halts at the next step boundary. */
    @PostMapping(TrainingURLS.SESSIONS + TrainingURLS.ID + TrainingURLS.STOP)
    @ResponseBody
    public TrainingSessionSummary stopSession(@PathVariable UUID id) {
        return sessionService.toSummary(sessionService.stop(id));
    }

    /** Resume a STOPPED/FAILED session from its last checkpoint. */
    @PostMapping(TrainingURLS.SESSIONS + TrainingURLS.ID + TrainingURLS.RESUME)
    @ResponseBody
    public TrainingSessionSummary resumeSession(@PathVariable UUID id) {
        return sessionService.toSummary(sessionService.resume(id));
    }

    /**
     * Re-run a FAILED/STOPPED session as a fresh attempt with the same config +
     * dataset. Returns the NEW session's summary (the original is left intact).
     */
    @PostMapping(TrainingURLS.SESSIONS + TrainingURLS.ID + TrainingURLS.RERUN)
    @ResponseBody
    public TrainingSessionSummary rerunSession(@PathVariable UUID id) {
        return sessionService.toSummary(sessionService.rerun(id));
    }

    /**
     * Fetch a remotely-trained model's files down to the management host so it can
     * be tested on LOCAL CPU. Flags the owning agent to push (it's outbound-only);
     * the FE polls status to watch the fetch progress.
     */
    @PostMapping(TrainingURLS.SESSIONS + TrainingURLS.ID + TrainingURLS.FETCH_LOCAL)
    @ResponseBody
    public TrainingSessionSummary fetchModelLocal(
            @PathVariable UUID id,
            @org.springframework.web.bind.annotation.RequestParam(value = "fromScratch", defaultValue = "false") boolean fromScratch) {
        return sessionService.toSummary(sessionService.requestModelFetch(id, fromScratch));
    }
}
