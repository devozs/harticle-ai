package com.devozs.components.common.message;

import com.devozs.components.common.dto.ServiceStatus;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KubeDeploymentStatusMessage implements TenantAwareMessage, Serializable {
    private String tenantId;
    private String callerFilter;
    private ServiceStatus status;
    private String message;
}
