package com.devozs.components.common.security;

import com.devozs.components.common.dto.RoleDto;
import com.devozs.components.common.dto.RoleEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public final class SecurityGroupsNaming {
    public static final String BUSINESS_UNITS_PREFIX = "businessUnit";
    public static final String PROJECTS_PREFIX = "project";
    public static final String DATA_SOURCE_PREFIX = "dataSource";

    public static final String ADMIN = RoleEnum.ADMIN.name();
    public static final String MEMBER = RoleEnum.MEMBER.name();
    public static final String DELIMITER = "_";
    public static final String FORMAT_FORM = "%s" + DELIMITER + "%s";
    public static final String ID_FORMAT_FORM = "%s" + DELIMITER + "%d" + DELIMITER + "%s";
    public static final String BUSINESS_ADMIN = String.format(FORMAT_FORM, BUSINESS_UNITS_PREFIX, ADMIN);
    public static final String BUSINESS_MEMBER = String.format(FORMAT_FORM, BUSINESS_UNITS_PREFIX, MEMBER);
    public static final String PROJECT_ADMIN = String.format(FORMAT_FORM, PROJECTS_PREFIX, ADMIN);
    public static final String PROJECT_MEMBER = String.format(FORMAT_FORM, PROJECTS_PREFIX, MEMBER);
    public static final String DATA_SOURCE_ADMIN = String.format(FORMAT_FORM, DATA_SOURCE_PREFIX, ADMIN);

    public static final String REALM_ADMIN = "realmAdmin";
    public static final String NAME = "name";
    public static final String SUB = "sub";
    public static final String ISS = "iss";
    public static final String MASTER = "master";
    public static final String TRIAL = "trial";
    public static final String PREFERRED_USER_NAME = "preferred_username";
    public static final String EMAIL = "email";



    static final Map<String, BiFunction<String, String, RoleDto>> attributeToBuilder = new HashMap<>();

    static {
        attributeToBuilder.put(BUSINESS_ADMIN, (s, p) -> SecurityGroupsNaming.buildRoleDto(s, RoleEnum.ADMIN, p));
        attributeToBuilder.put(BUSINESS_MEMBER, (s, p) -> SecurityGroupsNaming.buildRoleDto(s, RoleEnum.MEMBER, p));
        attributeToBuilder.put(PROJECT_ADMIN, (s, p) -> SecurityGroupsNaming.buildRoleDto(s, RoleEnum.ADMIN, p));
        attributeToBuilder.put(PROJECT_MEMBER, (s, p) -> SecurityGroupsNaming.buildRoleDto(s, RoleEnum.MEMBER, p));
    }

    private SecurityGroupsNaming() {
    }

    public static Map<String, BiFunction<String, String, RoleDto>> getAttributeToBuilderMap() {
        return attributeToBuilder;
    }

    public static RoleDto buildRoleDto(String id, RoleEnum role, String path) {

        String[] groupNames = path.split("/");

        RoleDto.RoleDtoBuilder roleBuilder = RoleDto.builder().id(Long.parseLong(id)).role(role);

        if (groupNames.length >= 4) {
            roleBuilder = roleBuilder.parentId(Optional.of(Long.parseLong(groupNames[groupNames.length - 3].split(DELIMITER)[1])));
        } else {
            roleBuilder.parentId(null);
        }

        return roleBuilder.build();
    }

    public static String getBusinessUnitWithIdAdminFormatForm(Long id) {
        return String.format(ID_FORMAT_FORM, BUSINESS_UNITS_PREFIX, id, ADMIN);
    }

    public static String getDataSourceWithIdAdminFormatForm(Long id) {
        return String.format(ID_FORMAT_FORM, DATA_SOURCE_PREFIX, id, ADMIN);
    }

    public static String getProjectWithIdAdminFormatForm(Long id) {
        return String.format(ID_FORMAT_FORM, PROJECTS_PREFIX, id, ADMIN);
    }

    public static String getProjectWithIdMemberFormatForm(Long id) {
        return String.format(ID_FORMAT_FORM, PROJECTS_PREFIX, id, MEMBER);
    }
}
