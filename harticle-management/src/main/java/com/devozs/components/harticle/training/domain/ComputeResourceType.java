package com.devozs.components.harticle.training.domain;

/**
 * The accelerator family a registered compute box exposes. Drives which Python
 * training backend the agent selects: {@link #CUDA} → transformers Trainer on an
 * NVIDIA GPU; {@link #HPU} → optimum-habana GaudiTrainer on an Intel Gaudi VM.
 */
public enum ComputeResourceType {
    CUDA,
    HPU
}
