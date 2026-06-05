package com.devozs.components.harticle.inference.controller;

/** URL constants for the admin/FE-facing inference API (mirrors {@code TrainingURLS}). */
public final class InferenceURLS {
    private InferenceURLS() {}

    public static final String URL = "inference";

    /** Trained models available to test (COMPLETED sessions with an output model). */
    public static final String MODELS = "/models";
    /** Inference test runs (history + create + single-run status). */
    public static final String RUNS = "/runs";
    public static final String STATUS = "/status";

    public static final String ID = "/{id}";

    public static final String URL_PATTERN = "/" + URL + "/**";
}
