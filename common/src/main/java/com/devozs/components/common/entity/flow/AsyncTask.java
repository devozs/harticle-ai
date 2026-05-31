package com.devozs.components.common.entity.flow;

import com.devozs.components.common.dto.datakubeservice.DataKubeServiceProtos;
import com.devozs.components.common.entity.BaseEntity;
import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.common.domain.TaskStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "asynctask")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
//@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@AllArgsConstructor

public class AsyncTask extends BaseEntity {
    private DataKubeServiceProtos.DataKubeStep stepType;
    private TaskStatus taskStatus;
    private String errorMessage;
    private ErrorType errorType;
    @Builder.Default
    @Column(columnDefinition = "boolean not null default true")
    private boolean isValid = true;
    private int progress;
}
