package com.devozs.components.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DataKubeJobEvent extends AbstractCallerApplicationEvent {
    private UUID dataKubeJobId;

    public DataKubeJobEvent(Object source, String caller, UUID dataKubeJobId) {
        super(source, caller);
        this.dataKubeJobId = dataKubeJobId;
    }
}
