package com.devozs.components.harticle.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/** One-time enrollment code, shown to the admin once (only its hash is stored). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentCodeResponse {
    private UUID resourceId;
    private String enrollmentCode;
}
