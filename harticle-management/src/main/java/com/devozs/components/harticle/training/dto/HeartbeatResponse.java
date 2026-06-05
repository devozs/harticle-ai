package com.devozs.components.harticle.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/** Heartbeat ack; tells the agent which session (if any) it is currently assigned. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeartbeatResponse {
    private boolean ack;
    private UUID assignedSessionId;
    /** Admin asked for a fresh readiness preflight; the agent should re-run it. */
    private boolean reverifyRequested;

    /**
     * Admin asked this box to push a completed session's model up to management for
     * LOCAL inference. Non-null = upload this session's model dir; the source
     * {@code file://} path is in {@link #modelUploadSourceUri}.
     */
    private UUID modelUploadSessionId;
    /** Where the model lives on the agent box ({@code file://…}); only set with the id above. */
    private String modelUploadSourceUri;
}
