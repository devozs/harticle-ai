package com.devozs.components.harticle.inference.entity;

import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.common.entity.BaseEntity;
import com.devozs.components.harticle.inference.domain.InferenceStatus;
import com.devozs.components.harticle.training.domain.ComputeResourceType;
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
 * One admin inference test: run a prompt against a trained model and keep the
 * result as history. The model comes from a COMPLETED {@code TrainingSession}
 * (its {@code outputModelRef} is copied here at create time, so the run survives
 * deletion of the source session).
 *
 * <p>Two execution targets, distinguished by {@link #local}:
 * <ul>
 *   <li><b>LOCAL</b> ({@code local=true}, {@code requiredType=null}): runs on the
 *       deployment's own CPU by calling the co-located engine over HTTP. Never
 *       claimed by an agent.</li>
 *   <li><b>GPU/HPU</b> ({@code local=false}, {@code requiredType} set): queued and
 *       claimed by a matching agent through the same pull protocol as training.</li>
 * </ul>
 */
@Entity
@Table(name = "inference_run")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class InferenceRun extends BaseEntity {

    /** The COMPLETED training session whose model is being tested (loose UUID ref). */
    @Column(name = "source_session_id")
    private UUID sourceSessionId;

    /** Copied from the source session's {@code outputModelRef}: storage URI and/or HF repo id. */
    @Column(name = "model_ref", nullable = false, length = 1024)
    private String modelRef;

    /** The base model of the source session, for display. */
    @Column(name = "base_model", length = 1024)
    private String baseModel;

    @Column(columnDefinition = "text", nullable = false)
    private String prompt;

    /** Generation knobs: {@code {temperature, maxLength, numReturnSequences}}. */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String params;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 24)
    private InferenceStatus status;

    /** Null when {@link #local}; otherwise the accelerator family a claiming agent must be. */
    @Enumerated(EnumType.STRING)
    @Column(name = "required_type", length = 16)
    private ComputeResourceType requiredType;

    /** True ⇒ run on the deployment CPU via the engine; false ⇒ run on a GPU/HPU agent. */
    @Column(nullable = false)
    private boolean local;

    /** The agent assigned to a non-local run (loose ref). */
    @Column(name = "assigned_resource_id")
    private UUID assignedResourceId;

    /** Generated samples as a JSON array of strings. */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String outputs;

    @Column(name = "error_message", columnDefinition = "text")
    private String errorMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "error_type", length = 32)
    private ErrorType errorType;

    @Column(name = "started_at")
    private Date startedAt;

    @Column(name = "finished_at")
    private Date finishedAt;

    @Column(name = "duration_ms")
    private Long durationMs;
}
