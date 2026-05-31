package com.devozs.components.common.entity;

import com.devozs.components.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "data_kube_job")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
//@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@AllArgsConstructor
public class DataKubeJob extends BaseEntity {
    private String kubernetesJobId;

    @ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
    @MapKeyColumn(name="KubeJobMetadataKey_KubeJobMetadataValue")
    private Map<String, String> metadata;

//    private UUID articleId;
    private UUID asyncTaskId;

    private String imageVersion;

    @Column(columnDefinition = "uuid")
    private UUID securityIdentifier;

    @PrePersist
    void assignSecurityIdentifier() {
        if (securityIdentifier == null) {
            securityIdentifier = UUID.randomUUID();
        }
    }

    private String callerFilter;
}
