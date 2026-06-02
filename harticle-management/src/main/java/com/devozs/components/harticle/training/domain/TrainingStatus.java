package com.devozs.components.harticle.training.domain;

/**
 * Lifecycle of a training session, persisted so it survives the agent network
 * boundary and is visible across boxes / restarts.
 *
 * <pre>
 *   PENDING ──claim──▶ ASSIGNED ──agent starts──▶ RUNNING ──complete──▶ COMPLETED
 *      ▲                                             │  │
 *      │                                     error   │  └─error─▶ FAILED
 *      │                                             ▼
 *   (resume)                              STOP_REQUESTED ──agent acks──▶ STOPPED
 *      │                                                                    │
 *      └──────────────── RESUMING ◀──── admin "resume" ──────────────────────┘
 * </pre>
 *
 * STOP is cooperative: the admin flips the session to STOP_REQUESTED and the
 * agent learns of it in its next progress response (mirrors the scraper's
 * {@code ScrapeProgressTracker.requestCancel()}, but DB-persisted). RESUME
 * re-queues a STOPPED/FAILED session that has a checkpoint to resume from.
 */
public enum TrainingStatus {
    PENDING,
    ASSIGNED,
    RUNNING,
    STOP_REQUESTED,
    STOPPED,
    RESUMING,
    COMPLETED,
    FAILED
}
