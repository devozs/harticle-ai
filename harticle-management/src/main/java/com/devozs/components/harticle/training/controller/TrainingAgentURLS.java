package com.devozs.components.harticle.training.controller;

/**
 * URL constants for the agent-facing polling protocol. All connections are
 * OUTBOUND from the agent; management never dials the box.
 */
public final class TrainingAgentURLS {
    private TrainingAgentURLS() {}

    public static final String URL = "training/agent";

    /**
     * Per-agent bearer token header. Deliberately NOT {@code Authorization}: the
     * OAuth2 resource-server filter on the classpath intercepts {@code
     * Authorization: Bearer} and 403s our opaque (non-JWT) agent token before the
     * controller runs. A custom header bypasses that filter entirely.
     */
    public static final String TOKEN_HEADER = "X-Agent-Token";

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

    // inference-run result callback (under /inference/{id}/result)
    public static final String INFERENCE = "/inference";
    public static final String RESULT = "/result";

    // model push (fetch-to-local): agent streams each file, then finalizes.
    // under /sessions/{id}/model-file and /sessions/{id}/model-upload-complete
    public static final String MODEL_FILE = "/model-file";
    public static final String MODEL_UPLOAD_COMPLETE = "/model-upload-complete";
    /** Header carrying each model file's path relative to the model dir root. */
    public static final String REL_PATH_HEADER = "X-Rel-Path";

    public static final String ID = "/{id}";

    public static final String URL_PATTERN = "/" + URL + "/**";
}
