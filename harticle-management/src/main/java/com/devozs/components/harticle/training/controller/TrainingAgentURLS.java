package com.devozs.components.harticle.training.controller;

/**
 * URL constants for the agent-facing polling protocol. All connections are
 * OUTBOUND from the agent; management never dials the box.
 */
public final class TrainingAgentURLS {
    private TrainingAgentURLS() {}

    public static final String URL = "training/agent";

    public static final String ENROLL = "/enroll";
    public static final String HEARTBEAT = "/heartbeat";
    public static final String CLAIM = "/claim";
    public static final String PREFLIGHT = "/preflight";

    // session-scoped reports (under /sessions/{id})
    public static final String SESSIONS = "/sessions";
    public static final String PROGRESS = "/progress";
    public static final String LOG = "/log";
    public static final String CHECKPOINT = "/checkpoint";
    public static final String COMPLETE = "/complete";
    public static final String STOPPED = "/stopped";
    public static final String ERROR = "/error";
    public static final String DATASET = "/dataset";

    public static final String ID = "/{id}";

    public static final String URL_PATTERN = "/" + URL + "/**";
}
