package com.devozs.components.harticle.training.controller;

/** URL constants for the admin/FE-facing training API (mirrors {@code ScraperURLS}). */
public final class TrainingURLS {
    private TrainingURLS() {}

    public static final String URL = "training";

    public static final String RESOURCES = "/resources";
    public static final String SESSIONS = "/sessions";

    // session lifecycle actions (suffix under /sessions/{id})
    public static final String STOP = "/stop";
    public static final String RESUME = "/resume";
    public static final String RERUN = "/rerun";
    public static final String STATUS = "/status";
    public static final String LOGS = "/logs";
    // admin-triggered fetch of a remote model's files to the management host (LOCAL inference)
    public static final String FETCH_LOCAL = "/fetch-local";

    // resource enrollment-code (re)issue
    public static final String ENROLLMENT_CODE = "/enrollment-code";
    // admin-triggered readiness re-verify
    public static final String REVERIFY = "/reverify";

    public static final String ID = "/{id}";

    public static final String URL_PATTERN = "/" + URL + "/**";
}
