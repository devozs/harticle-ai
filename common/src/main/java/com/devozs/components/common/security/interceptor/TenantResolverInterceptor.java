package com.devozs.components.common.security.interceptor;

import com.devozs.components.common.dto.datakubeservice.DataKubeServiceProtos;
import com.devozs.components.common.message.TenantAwareMessage;
import com.devozs.components.common.utils.CommonConstants;
import com.devozs.components.common.security.exceptions.TenantIdNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Stream;

@Aspect
@Component
@Slf4j
public class TenantResolverInterceptor {
    private static final String SYSTEM_USER_ID = "System";
    private static final String SYSTEM_USER_NAME = "System";
    private static final String SYSTEM_USER_MAIL = "MAIL";
    private static final String SYSTEM_UNSIGNED_TOKEN = "System";

//    private final IdentityResolverApiService identityResolverApiService;

    @Autowired
    public TenantResolverInterceptor(/*IdentityResolverApiService identityResolverApiService*/) {
//        this.identityResolverApiService = identityResolverApiService;
    }

    @Around("@annotation(com.devozs.components.common.security.interceptor.ProtoTenantResolver)")
    public Object protoTenantResolverMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String tenantId = null;
        Parameter[] parameters =
                ((MethodSignature) joinPoint.getSignature()).getMethod().getParameters();
        Object[] values = joinPoint.getArgs();

        for(int i = 0; i < values.length; i++) {
            Stream<Annotation> annotations = Arrays.stream(parameters[i].getAnnotations());

            if (annotations.anyMatch(annotation ->
                    annotation.annotationType() == Header.class)) {
                DataKubeServiceProtos.DataKubeMessage dataKubeMessageHeader =
                        DataKubeServiceProtos.DataKubeMessage
                                .parseFrom(Base64.getDecoder().decode(values[i].toString()));
                if (dataKubeMessageHeader.getTenantId() != null) {
                    tenantId = dataKubeMessageHeader.getTenantId();
                    MDC.put(CommonConstants.REQUEST_PROCESSING_USER_NAME, dataKubeMessageHeader.getUserName());
                    MDC.put(CommonConstants.TENANT, tenantId);
                    MDC.put(CommonConstants.REQUEST_PROCESSING_ARTICLE_ID, dataKubeMessageHeader.getArticleId());
                    MDC.put(CommonConstants.SOURCE, CommonConstants.INTERNAL);
                    break;
                }
            }
        }

        if (tenantId == null) {
            log.error("Tenant id parameter does not exist or invalid.");
            throw new TenantIdNotExistException();
        }

//        setSecurityContext(tenantId, identityResolverApiService);

        return joinPoint.proceed();
    }

    @Around("@annotation(com.devozs.components.common.security.interceptor.TenantResolver)")
    public Object tenantResolverMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String tenantId = null;
        for (Object parameter : joinPoint.getArgs()) {
            if (parameter instanceof TenantAwareMessage) {
                tenantId = ((TenantAwareMessage) parameter).getTenantId();
            }
        }

        if (tenantId == null) {
            log.error("Tenant id parameter does not exist or invalid.");
            throw new TenantIdNotExistException();
        }
        MDC.put(CommonConstants.TENANT, tenantId);
//        setSecurityContext(tenantId, identityResolverApiService);

        return joinPoint.proceed();
    }

/*    public static void setSecurityContext(String realm, IdentityResolverApiService identityResolverApiService) throws InvalidTenantDataSourceException {

        TenantDatasourceConfigDto tenantDatasourceConfigDto = identityResolverApiService.getTenantDatasourceByRealm(realm);
        String token =  MDC.get(MASTER_TOKEN);
        String userName = SYSTEM_USER_NAME;
        String existsUseName = MDC.get(REQUEST_PROCESSING_USER_NAME);
        if(!StringUtils.isBlank(existsUseName)){
            userName = existsUseName;
        }
        if (tenantDatasourceConfigDto == null) {
            String errorMessage = String.format("Receive from identity resolver invalid tenant configuration. TenantId: %s", realm);
            log.error(errorMessage);
            throw new InvalidTenantDataSourceException(errorMessage);
        }

        User user = new User(userName, "", new ArrayList<>());
        List<GrantedAuthority> authorities = Collections.emptyList();

        AuthenticationDetails authenticationDetails =
                new AuthenticationDetails(realm, SYSTEM_USER_ID, userName, SYSTEM_USER_MAIL, tenantDatasourceConfigDto, SYSTEM_UNSIGNED_TOKEN, token);
        MDC.put(MASTER_TOKEN, "");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        authentication.setDetails(authenticationDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        TenantContextResolver.setCurrentTenant(realm);
    }*/
}
