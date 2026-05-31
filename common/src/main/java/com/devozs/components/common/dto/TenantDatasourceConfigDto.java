package com.devozs.components.common.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TenantDatasourceConfigDto {
    private Long id;
    private String connectionString;
    private String username;
    private String password;
    private String domain;
    private String driver;
    private String realmName;
    private boolean isSSL;
    private boolean disableKeycloakCertificateCheck;
}