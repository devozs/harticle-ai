package com.devozs.components.common.service.engine;

import com.devozs.components.common.entity.BaseEntity;
import com.devozs.components.common.entity.DataKubeJob;
import com.devozs.components.common.entity.flow.AsyncTask;
import com.devozs.components.common.exception.DataKubeJobDeploymentException;
import com.devozs.components.common.exception.DataKubeJobNotExistException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

public interface DataKubeJobService <E extends BaseEntity>{

    UUID runDataKubeJob(AsyncTask asyncTask, E article, File basicDeploymentYamlFile) throws DataKubeJobDeploymentException;

    UUID runDataKubeRest(AsyncTask asyncTask, E e) throws DataKubeJobDeploymentException;

    DataKubeJob getDataKubeJob(UUID kubeJobId) throws DataKubeJobNotExistException;
    List<DataKubeJob> getDataKubeJobByTaskIds(List<UUID> asyncTaskIds);
    void deleteDataKubeJob(UUID kubeJobId) throws DataKubeJobNotExistException, IOException, InvalidKeyException, NoSuchAlgorithmException;
    void deleteDataKubeJobsByAsyncTasksIds(List<UUID> asyncTasksIds) throws  IOException, InvalidKeyException;

//    void consumeInfra(DataKubeJobStatusUpdateMessage dataKubeJobStatusUpdateMessage);

    void consumeRunner(String dataKubeMessage, String type);
}
