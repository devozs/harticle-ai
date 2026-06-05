package com.devozs.components.harticle.inference.controller;

import com.devozs.components.harticle.inference.dto.InferenceModelOption;
import com.devozs.components.harticle.inference.dto.InferenceRunDto;
import com.devozs.components.harticle.inference.dto.InferenceRunSummary;
import com.devozs.components.harticle.inference.entity.InferenceRun;
import com.devozs.components.harticle.inference.service.InferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Admin/FE surface for inference testing: list the trained models that can be
 * tested, create + submit a run, poll its status, and read run history. The
 * agent-facing result callback for GPU/HPU runs lives in the training agent
 * controller (the agent protocol is consolidated there).
 */
@RestController
@RequestMapping(InferenceURLS.URL)
@Slf4j
public class InferenceController {

    private final InferenceService inferenceService;

    public InferenceController(InferenceService inferenceService) {
        this.inferenceService = inferenceService;
    }

    /** Trained models available to test (COMPLETED sessions with an output model). */
    @GetMapping(InferenceURLS.MODELS)
    @ResponseBody
    public List<InferenceModelOption> models() {
        return inferenceService.models();
    }

    @GetMapping(InferenceURLS.RUNS)
    @ResponseBody
    public List<InferenceRunSummary> runs() {
        return inferenceService.summaries();
    }

    /** Create + submit a run (LOCAL executes async; GPU/HPU is queued for an agent). */
    @PostMapping(InferenceURLS.RUNS)
    @ResponseBody
    public InferenceRunSummary createRun(@RequestBody InferenceRunDto dto) {
        InferenceRun run = inferenceService.create(dto);
        return inferenceService.toSummary(run);
    }

    /** Live snapshot for the run view (FE polls this until terminal). */
    @GetMapping(InferenceURLS.RUNS + InferenceURLS.ID + InferenceURLS.STATUS)
    @ResponseBody
    public InferenceRunSummary runStatus(@PathVariable UUID id) {
        return inferenceService.snapshot(id);
    }

    @DeleteMapping(InferenceURLS.RUNS + InferenceURLS.ID)
    public ResponseEntity<Void> deleteRun(@PathVariable UUID id) {
        inferenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
