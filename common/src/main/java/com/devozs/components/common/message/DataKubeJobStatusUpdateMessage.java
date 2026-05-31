package com.devozs.components.common.message;

import com.devozs.components.common.dto.KubeJobStatusDto;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataKubeJobStatusUpdateMessage implements TenantAwareMessage, Serializable {
    private String kubeJobId;
    private String dataJobId;
    private String tenantId;
    private UUID securityIdentifier;
    private KubeJobStatusDto status;
    private String message;
}
