package com.devozs.components.harticle.training.entity;

import com.devozs.components.common.entity.BaseEntity;
import com.devozs.components.harticle.training.domain.ComputeResourceReadiness;
import com.devozs.components.harticle.training.domain.ComputeResourceStatus;
import com.devozs.components.harticle.training.domain.ComputeResourceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
 * A GPU/HPU machine an admin has registered to run training jobs. The box is NOT
 * a k8s cluster node: it runs a lightweight Python agent that connects OUTBOUND
 * (register → heartbeat → claim → report), so it works behind NAT / a corporate
 * proxy. Management never dials the box.
 *
 * <p>Enrollment is admin-gated: the admin creates the row (status OFFLINE, not
 * yet enrolled) and gets a one-time enrollment code; the agent redeems it to
 * receive a per-agent bearer token. We store only hashes of both secrets.
 */
@Entity
@Table(name = "compute_resource")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ComputeResource extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ComputeResourceType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ComputeResourceStatus status;

    /** SHA-256 of the one-time enrollment code; cleared once redeemed. */
    @Column(name = "enrollment_code_hash")
    private String enrollmentCodeHash;

    /** SHA-256 of the per-agent bearer token; set at enrollment, never the plaintext. */
    @Column(name = "agent_token_hash", unique = true)
    private String agentTokenHash;

    @Column(nullable = false)
    private boolean enrolled;

    @Column(name = "last_heartbeat")
    private Date lastHeartbeat;

    /** Free-form agent-reported specs (gpu/hpu count, VRAM, driver + agent versions, hostname). */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String capabilities;

    /** The session this box is currently executing, if any. Loose UUID ref (scraper style). */
    @Column(name = "current_session_id")
    private UUID currentSessionId;

    @Column(nullable = false)
    private boolean enabled;

    /**
     * Readiness gate: only a {@code READY} box may claim training jobs. Set by the
     * agent's preflight (accelerator identification + a tiny real LLM workload).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "readiness", nullable = false, length = 16)
    @Builder.Default
    private ComputeResourceReadiness readiness = ComputeResourceReadiness.UNVERIFIED;

    /** Human-readable preflight outcome (device summary on success, error on failure). */
    @Column(name = "readiness_detail", columnDefinition = "text")
    private String readinessDetail;

    @Column(name = "readiness_checked_at")
    private Date readinessCheckedAt;

    /**
     * Admin requested a fresh preflight; the agent observes this in its heartbeat
     * ack and re-runs the check. Cleared once the agent starts verifying.
     */
    @Column(name = "reverify_requested", nullable = false)
    private boolean reverifyRequested;
}
