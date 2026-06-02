package com.devozs.components.harticle.training.entity;

import com.devozs.components.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

/**
 * One append-only log line streamed by an agent during a training session. The
 * monitor view tails these (via {@code seq} for incremental fetch) so the admin
 * sees what the box is doing in near real time.
 */
@Entity
@Table(name = "training_log")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class TrainingLog extends BaseEntity {

    @Column(name = "session_id", nullable = false)
    private UUID sessionId;

    @Column(length = 16)
    private String level;

    @Column(columnDefinition = "text")
    private String message;

    @Column(name = "logged_at")
    private Date loggedAt;

    /** Monotonic per-session sequence so the FE can fetch only lines after the last seen. */
    @Column(name = "seq", nullable = false)
    private long seq;
}
