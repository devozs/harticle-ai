package com.devozs.components.harticle.training.domain;

/**
 * What kind of work a claimed {@code AgentJobDto} represents. The agent polls one
 * claim endpoint and branches on this: {@link #TRAIN} runs a fine-tune;
 * {@link #INFER} loads a trained model and generates against a prompt. Defaults to
 * {@link #TRAIN} for back-compat with agents/jobs that predate inference.
 */
public enum JobKind {
    TRAIN,
    INFER
}
