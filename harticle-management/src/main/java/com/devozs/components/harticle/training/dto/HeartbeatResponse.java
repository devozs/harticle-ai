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
}
