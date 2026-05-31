package com.devozs.components.common.service.engine;

import com.devozs.components.common.service.TaskManagementService;
import com.devozs.components.common.utils.CommonConstants;
import com.devozs.components.common.domain.DataKubeJobEvent;
import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.common.domain.TaskStatus;
import com.devozs.components.common.entity.DataKubeJob;
import com.devozs.components.common.entity.flow.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

@Slf4j
public class ApplicationEventJobPublisher implements Runnable {

    private final DataKubeJobEvent dataKubeJobEvent;
    private final String realm;
    private final String userName;
    private final String articleId;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
//    @Autowired
//    private IdentityResolverApiService identityResolverApiService;
    @Autowired
    private DataKubeJobService dataKubeJobService;
    @Autowired
    private TaskManagementService taskManagementService;

    public ApplicationEventJobPublisher(DataKubeJobEvent dataKubeJobEvent, String realm, String userNme, String articleId) {
        this.dataKubeJobEvent = dataKubeJobEvent;
        this.realm = realm;
        this.userName = userNme;
        this.articleId = articleId;
    }

    @Override
    public void run() {
        //try {
            MDC.put(CommonConstants.REQUEST_PROCESSING_USER_NAME, userName);
            MDC.put(CommonConstants.TENANT, realm);
            MDC.put(CommonConstants.REQUEST_PROCESSING_ARTICLE_ID, articleId);
            MDC.put(CommonConstants.SOURCE, CommonConstants.INTERNAL);

/*
disable until keycloak + identity service and multi tenant support
TenantResolverInterceptor.setSecurityContext(realm, identityResolverApiService);
* */


    /*    } catch (InvalidTenantDataSourceException e) {
            failAsyncTask();
            log.error("could not set security settings for published event in new thread when a second ago in main thread " +
                    "it was possible this should not have happened", e);
            return;
        }*/

        try {
            applicationEventPublisher.publishEvent(this.dataKubeJobEvent);
        } catch (Exception e) {
            failAsyncTask();
            log.error("error occurred on kube event publication there is no auto retry policy", e);
        }
    }

    private void failAsyncTask() {
        try {
            DataKubeJob dataKubeJob = dataKubeJobService.getDataKubeJob(this.dataKubeJobEvent.getDataKubeJobId());
            Optional<AsyncTask> task = taskManagementService.findById(dataKubeJob.getAsyncTaskId());
            if (task.isPresent() && (!task.get().getTaskStatus().equals(TaskStatus.COMPLETED) && !task.get().getTaskStatus().equals(TaskStatus.ERROR))) {
                taskManagementService.failTask(task.get(), "could not finish job on management side", ErrorType.INTERNAL);
            }

        } catch (Exception ex) {
            log.error("error occurred when trying to fail task after unsuccessful publication event", ex);
        }
    }
}
