package com.devozs.components.harticle.training.entity;

import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.common.entity.BaseEntity;
import com.devozs.components.harticle.training.domain.ComputeResourceType;
import com.devozs.components.harticle.training.domain.TrainingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

/**
 * One fine-tune run: an admin-selected HuggingFace base model trained on the
 * project's scraped articles, producing a custom model for later inference.
 *
 * <p>This entity owns the full lifecycle ({@link TrainingStatus}) and the live
 * progress fields (epoch/step/loss/percent) that the Python agent reports back
 * over the polling protocol. It is deliberately NOT built on the Kafka-coupled
 * {@code AsyncTask} (which lacks epoch/step/loss, resource assignment, checkpoint
 * URI, and STOP/RESUME states); it only reuses the {@link ErrorType} enum.
 *
 * <p>{@code assignedResourceId} is a loose UUID ref (not a JPA relation), matching
 * the scraper module's style of holding {@code reporterId}/{@code siteId} as UUIDs.
 */
@Entity
@Table(name = "training_session")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class TrainingSession extends BaseEntity {

    @Column(nullable = false)
    private String name;

    /** HuggingFace repo id of the base model, e.g. {@code Norod78/hebrew-gpt_neo-small}. */
    @Column(name = "base_model", nullable = false)
    private String baseModel;

    /** What data to train on: {@code {fields:[...], reporterIds:[...], format:"jsonl"}}. */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dataset_spec", columnDefinition = "jsonb")
    private String datasetSpec;

    /** Storage URI of the exported JSONL once materialized (null until export runs). */
    @Column(name = "dataset_uri", length = 1024)
    private String datasetUri;

    /** Training knobs: {@code {epochs, batchSize, learningRate, warmupSteps, weightDecay, saveSteps, contextLength}}. */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String hyperparams;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private TrainingStatus status;

    /** Which accelerator family this session needs; only a matching resource may claim it. */
    @Enumerated(EnumType.STRING)
    @Column(name = "required_type", nullable = false, length = 16)
    private ComputeResourceType requiredType;

    /** Run on the no-ML stub backend (local dev without a real accelerator). */
    @Column(name = "stub_mode", nullable = false)
    private boolean stubMode;

    @Column(name = "progress_percent", nullable = false)
    private int progressPercent;

    @Column(name = "current_epoch")
    private Double currentEpoch;

    @Column(name = "total_epochs")
    private Double totalEpochs;

    @Column(name = "current_step")
    private Long currentStep;

    @Column(name = "total_steps")
    private Long totalSteps;

    @Column(name = "last_loss")
    private Double lastLoss;

    @Column(name = "error_message", columnDefinition = "text")
    private String errorMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "error_type", length = 32)
    private ErrorType errorType;

    /** The compute box currently assigned to this session (loose ref, see class doc). */
    @Column(name = "assigned_resource_id")
    private UUID assignedResourceId;

    /** Storage URI of the latest checkpoint dir; non-null enables RESUME. */
    @Column(name = "checkpoint_uri", length = 1024)
    private String checkpointUri;

    /** Also push the final model to the HuggingFace Hub (in addition to object storage). */
    @Column(name = "push_to_hub", nullable = false)
    private boolean pushToHub;

    /** Final artifact reference: storage URI (always) and/or HF Hub repo id (if pushed). */
    @Column(name = "output_model_ref", length = 1024)
    private String outputModelRef;

    /** Last time the assigned agent was heard from during this run; drives stall detection. */
    @Column(name = "last_agent_seen_at")
    private Date lastAgentSeenAt;

    /**
     * The session this one was re-run from (null for an original run). A re-run is
     * a fresh session that copies the parent's exact config + dataset; the parent
     * row is never mutated, so the attempt chain is preserved and walkable.
     */
    @Column(name = "parent_session_id")
    private UUID parentSessionId;

    /** 1 for an original run; the parent's attempt + 1 for each re-run. */
    @Column(name = "attempt_number", nullable = false)
    @lombok.Builder.Default
    private int attemptNumber = 1;
}
