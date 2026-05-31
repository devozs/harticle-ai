package com.devozs.components.common.security.tenant;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class TenantContextResolver implements CurrentTenantIdentifierResolver {
    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

    private static final String DEFAULT_TENANT_ID = "devozs";

    public static void setCurrentTenant(String tenant) {
        log.debug("Setting tenant to " + tenant);
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }

    public static String getDefaultTenantId() {
        return DEFAULT_TENANT_ID;
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContextResolver.getCurrentTenant();
        if (tenantId != null) {
            return tenantId;
        }
        return DEFAULT_TENANT_ID;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
