package com.devozs.components.common.dto;

import lombok.*;

import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private RoleEnum role;
    private Optional<Long> parentId;
}
