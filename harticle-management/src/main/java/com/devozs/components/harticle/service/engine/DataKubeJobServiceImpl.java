package com.devozs.components.harticle.service.engine;

import com.devozs.components.common.config.MessageBrokerConsumerGroupNames;
import com.devozs.components.common.dto.datakubeservice.DataKubeServiceProtos;
import com.devozs.components.common.exception.AsyncTaskIdNotExistException;
import com.devozs.components.common.exception.DataKubeJobDeploymentException;
import com.devozs.components.common.exception.DataKubeJobNotExistException;
import com.devozs.components.common.exception.IllegalAsyncTaskUpdateException;
import com.devozs.components.common.service.TaskManagementService;
import com.devozs.components.common.service.engine.ApplicationEventJobPublisher;
import com.devozs.components.common.service.engine.DataKubeJobService;
import com.devozs.components.common.service.engine.KubeCommonService;
import com.devozs.components.common.utils.DataKubeJobConstants;
import com.devozs.components.common.domain.DataKubeJobEvent;
import com.devozs.components.common.domain.ErrorType;
import com.devozs.components.harticle.domain.Reporter;
import com.devozs.components.harticle.entity.Article;
import com.devozs.components.harticle.exception.ArticleNotExistsException;
import com.devozs.components.common.domain.TaskStatus;
import com.devozs.components.common.entity.DataKubeJob;
import com.devozs.components.common.entity.flow.AsyncTask;
import com.devozs.components.harticle.repository.ArticleRepository;
import com.devozs.components.common.repository.DataKubeJobRepository;
import com.google.protobuf.InvalidProtocolBufferException;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.kafka.common.errors.InvalidRequestException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import static com.devozs.components.common.utils.DataKubeConstants.*;
import static com.devozs.components.common.utils.CommonConstants.REQUEST_PROCESSING_ARTICLE_ID;
import static com.devozs.components.common.utils.CommonConstants.REQUEST_PROCESSING_USER_NAME;

@Service
@Slf4j
public class DataKubeJobServiceImpl implements DataKubeJobService<Article> {

    static final String CONFIG_FILE_NAME_KAFKA_CONFIG = "kafka_config.config";
    static final String DATA_KUBE_JOB_KAFKA_TOPIC = "data_kube_job_service";
    static final String CONTAINER_FACTORY = "strKafkaListenerContainerFactory";

    private final ApplicationContext applicationContext;
    private final KubeCommonService kubeCommonService;

    private final ArticleRepository articleRepository;
    private final DataKubeJobRepository dataKubeJobRepository;

    private final TaskManagementService taskManagementService;

    private static final String HARTICLE_ENGINE_CONTAINER = "harticle-engine";

    private final RestTemplate restTemplate;

    @Value("${api.harticle-engine.url}")
    private String harticleEngineUrl;

    @Value("${harticle.engine.local-mode:false}")
    private boolean localEngineMode;

    @Autowired
    public DataKubeJobServiceImpl(KubeCommonService kubeCommonService,
                                  ApplicationContext applicationEventPublisher,
                                  ArticleRepository articleRepository,
                                  DataKubeJobRepository dataKubeJobRepository,
                                  TaskManagementService taskManagementService,
                                  RestTemplate restTemplate) {
        this.kubeCommonService = kubeCommonService;
        this.applicationContext = applicationEventPublisher;
        this.articleRepository = articleRepository;
        this.dataKubeJobRepository = dataKubeJobRepository;
        this.taskManagementService = taskManagementService;
        this.restTemplate = restTemplate;
    }

    @Override
    public UUID runDataKubeJob(AsyncTask asyncTask, Article article, File basicDeploymentYamlFile) throws DataKubeJobDeploymentException {
        DataKubeJob dataKubeJob = createDataKubeJob(asyncTask, article, "");

        log.debug(String.format("create kubernetesClient + async task: %s", asyncTask));
        try (final KubernetesClient kubernetesClient = kubeCommonService.createKubernetesClient()) {
            Secret infrastructureSecret = updateInfrastructureSecret(kubernetesClient, dataKubeJob.getId());
            Job job = deployJob(kubernetesClient, infrastructureSecret, basicDeploymentYamlFile, dataKubeJob, asyncTask.getId(), article);
            dataKubeJob.setKubernetesJobId(job.getMetadata().getUid());
            dataKubeJobRepository.save(dataKubeJob);
        } catch (Exception e) {
            log.error(String.format("failed to deploy data kube job: %s, async task id %s", dataKubeJob.getId(), dataKubeJob.getAsyncTaskId()));
            dataKubeJobRepository.deleteById(dataKubeJob.getId());
            throw new DataKubeJobDeploymentException(e);
        }
        return dataKubeJob.getId();
    }

    @Override
    public UUID runDataKubeRest(AsyncTask asyncTask, Article article) throws DataKubeJobDeploymentException {
        DataKubeJob dataKubeJob = createDataKubeJob(asyncTask, article, "");
        log.debug("calling engine REST, async task: {}", asyncTask.getId());
        try {
            String kubeJobId = "local-dev";
            String kubernetesJobId = "local-dev";
            if (!localEngineMode) {
                try (final KubernetesClient kubernetesClient = kubeCommonService.createKubernetesClient()) {
                    Pod harticlePod = getHarticlePod(kubernetesClient, kubeCommonService.getKubernetesNamespace());
                    kubeJobId = harticlePod.getMetadata().getName();
                    kubernetesJobId = harticlePod.getMetadata().getUid();
                }
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            Map<String, Object> map = buildEngineGeneratePayload(dataKubeJob, article, kubeJobId);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    harticleEngineUrl + "/engine/generate", entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                log.debug("engine REST OK: {}", response.getBody());
            } else {
                log.warn("engine REST unexpected status {}: {}", response.getStatusCode(), response.getBody());
            }

            dataKubeJob.setKubernetesJobId(kubernetesJobId);
            dataKubeJobRepository.save(dataKubeJob);
        } catch (Exception e) {
            log.error("failed to call engine REST: job {}, async task {}", dataKubeJob.getId(), dataKubeJob.getAsyncTaskId(), e);
            dataKubeJobRepository.deleteById(dataKubeJob.getId());
            throw new DataKubeJobDeploymentException(e);
        }

        return dataKubeJob.getId();
    }

    private Map<String, Object> buildEngineGeneratePayload(DataKubeJob dataKubeJob, Article article, String kubeJobId) {
        Map<String, Object> map = new HashMap<>();
        map.put("tenant_id", "devozs");
        map.put("security_identifier", dataKubeJob.getSecurityIdentifier().toString());
        map.put("data_kube_job_id", dataKubeJob.getId().toString());
        map.put("kube_job_id", kubeJobId);
        map.put("user_name", "");
        map.put("kafka_password", localEngineMode ? "" : kubeCommonService.getKafkaPassword());
        map.put("user_email", "");
        map.put("article_id", article.getId().toString());
        map.put("env_name", "");
        map.put("article_keywords", article.getKeywords());
        map.put("reporter_id", String.valueOf(article.getReporter()));
        map.put("reporter_name", article.getReporter().getValue());
        map.put("temperature", article.getTemperature());
        return map;
    }

    @Override
    public DataKubeJob getDataKubeJob(UUID kubeJobId) throws DataKubeJobNotExistException {
        return dataKubeJobRepository.findById(kubeJobId).orElseThrow(() -> new DataKubeJobNotExistException(kubeJobId));
    }

    @Override
    public List<DataKubeJob> getDataKubeJobByTaskIds(List<UUID> asyncTaskIds) {
        return dataKubeJobRepository.findByAsyncTaskIdIn(asyncTaskIds);
    }

    @Override
    public void deleteDataKubeJob(UUID kubeJobId) throws DataKubeJobNotExistException, IOException, InvalidKeyException, NoSuchAlgorithmException {
        cleanJobFromKubernetes(dataKubeJobRepository.findById(kubeJobId).orElseThrow(() -> new DataKubeJobNotExistException(kubeJobId)));
        dataKubeJobRepository.deleteById(kubeJobId);
    }

    @Override
    public void deleteDataKubeJobsByAsyncTasksIds(List<UUID> articlesIds) throws IOException, InvalidKeyException {
        final List<DataKubeJob> dataKubeJobs = dataKubeJobRepository.findByAsyncTaskIdIn(articlesIds);
        for (DataKubeJob job : dataKubeJobs) {
            cleanJobFromKubernetes(job);
        }
        dataKubeJobRepository.deleteAll(dataKubeJobs);

    }

    @KafkaListener(topics = DATA_KUBE_JOB_KAFKA_TOPIC,
            groupId = MessageBrokerConsumerGroupNames.MANAGEMENT_CONSUMER_GROUP,
            containerFactory = CONTAINER_FACTORY,
            concurrency = "1")
//    @ProtoTenantResolver
    @Override
    public void consumeRunner(@Payload String dataKubeMessage,
                              @Header String type) {

        try {
            DataKubeServiceProtos.DataKubeMessage dataKubeMessageHeader =
                    DataKubeServiceProtos.DataKubeMessage.parseFrom(Base64.getDecoder().decode(type));

            log.info(String.format("Kafka message type received: %s", dataKubeMessageHeader.getObjectType()));

            switch (dataKubeMessageHeader.getObjectType()) {
                case ADD_METADATA_MESSAGE:
                    doAddMetadataMessage(dataKubeMessageHeader, dataKubeMessage);
                    break;
                case SUBTASK_COMPLETED:
                    doSubtaskCompletedMessage(dataKubeMessageHeader, dataKubeMessage);
                    break;
                case FINISH_JOB:
                    doFinishJobMessage(dataKubeMessageHeader, dataKubeMessage);
                    break;
                case UNRECOGNIZED:
                default:
                    String message = "dataKubeHeaders is not recognized";
                    log.error(message);
                    throw new InvalidRequestException(message);

            }
        } catch (DataKubeJobNotExistException | InvalidRequestException | AsyncTaskIdNotExistException |
                 InvalidProtocolBufferException | ArticleNotExistsException e) {
            log.error("Failed to handle DataKubeJobStatusUpdateMessage message.", e);
        }
    }


    protected DataKubeJob createDataKubeJob(AsyncTask asyncTask, Article article, String callerFilter) {
        DataKubeJob dataKubeJob = new DataKubeJob();
        dataKubeJob.setAsyncTaskId(asyncTask.getId());
        dataKubeJob.setCallerFilter(callerFilter);
        dataKubeJob.setSecurityIdentifier(UUID.randomUUID());
        dataKubeJob.setMetadata(new HashMap<>());
        return dataKubeJobRepository.save(dataKubeJob);
    }

    private Job deployJob(KubernetesClient kubernetesClient,
                          Secret infrastructureSecret,
                          File basicDeploymentYamlFile,
                          DataKubeJob dataKubeJob,
                          UUID asyncTaskId,
                          Article article) {


        JobBuilder jobBuilder = new JobBuilder(kubernetesClient.batch().v1().jobs()
                .load(basicDeploymentYamlFile).get())
                .editMetadata().withName(getJobNameByJob(dataKubeJob.getId())).endMetadata();

        String imageName = jobBuilder.editSpec()
                .editTemplate().editSpec()
                .editFirstContainer()
                .getImage();

        dataKubeJob.setImageVersion(imageName);

        jobBuilder = jobBuilder.editSpec()
                .editTemplate().editSpec()
                .editFirstContainer()
                .withImage(imageName)
                .addNewVolumeMount()
                .withName("config-volume")
                .withMountPath("/etc/config")
                .endVolumeMount()
                .addAllToEnv(buildEnvVariables(dataKubeJob, article.getId(), article.getKeywords(), article.getReporter(), article.getTemperature()))
                .endContainer()
                .endSpec()
                .endTemplate()
                .endSpec();

//        //map configMap to volume
        jobBuilder = jobBuilder.editSpec()
                .editTemplate().editSpec()
                .addNewVolume().withName("config-volume")
                .withSecret(new SecretVolumeSourceBuilder().withSecretName(infrastructureSecret.getMetadata().getName()).build())
                .endVolume()
                .endSpec()
                .endTemplate()
                .endSpec();

        return kubernetesClient.batch().v1().jobs()
                .inNamespace(kubeCommonService.getKubernetesNamespace())
                .create(jobBuilder.build());

    }

    private String getJobNameByJob(UUID dataKubeJobId) {
        return DataKubeJobConstants.DATA_KUBE_JOB_NAME_PREFIX + '-' + dataKubeJobId;
    }

    private List<EnvVar> buildEnvVariables(DataKubeJob dataKubeJob, UUID articleId, String keywords, Reporter reporter, int temperature) {

        return Arrays.asList(
                new EnvVarBuilder().withName(ENV_VARIABLE_SECURITY_IDENTIFIER).withValue(dataKubeJob.getSecurityIdentifier().toString()).build(),
                new EnvVarBuilder().withName(ENV_VARIABLE_KAFKA_PASSWORD).withValue(kubeCommonService.getKafkaPassword()).build(),
                new EnvVarBuilder().withName(ENV_VARIABLE_DATA_KUBE_JOB_ID).withValue(dataKubeJob.getId().toString()).build(),
                new EnvVarBuilder().withName(ENV_VARIABLE_ARTICLE_ID).withValue(articleId.toString()).build(),
                new EnvVarBuilder().withName(ENV_VARIABLE_ARTICLE_KEYWORDS).withValue(keywords).build(),
                new EnvVarBuilder().withName(ENV_VARIABLE_REPORTER_ID).withValue(String.valueOf(reporter)).build(),
                new EnvVarBuilder().withName(ENV_VARIABLE_REPORTER_NAME).withValue(reporter.getValue()).build(),
                new EnvVarBuilder().withName(ENV_VARIABLE_TEMPERATURE).withValue(String.valueOf(temperature)).build());
    }

    private void cleanJobFromKubernetes(DataKubeJob dataKubeJob) {
        try (final KubernetesClient kubernetesClient = kubeCommonService.createKubernetesClient()) {
            cleanJobFromKubernetes(dataKubeJob, kubernetesClient);
            cleanSecretsByJob(kubernetesClient, dataKubeJob.getId());
        } catch (Exception e) {
            log.error(String.format("failed to clean job from k8s data kube job: %s, async task idL %s", dataKubeJob.getId(), dataKubeJob.getAsyncTaskId()));
            throw e;
        }
    }

    private void cleanJobFromKubernetes(DataKubeJob dataKubeJob, KubernetesClient kubernetesClient) {

        Optional<Job> jobSearchResult = kubernetesClient.batch().v1().jobs()
                .inNamespace(kubeCommonService.getKubernetesNamespace())
                .withLabels(Map.of(DATA_KUBE_JOB_LABEL_TAG_KEY, DATA_KUBE_JOB_LABEL_TAG_VALUE))
                .list().getItems().stream().filter(job -> job.getMetadata().getName()
                        .equals(getJobNameByJob(dataKubeJob.getId()))).findAny();

        if (jobSearchResult.isEmpty()) {
            return;
        }

        kubernetesClient.batch().v1().jobs()
                .inNamespace(kubeCommonService.getKubernetesNamespace())
                .delete(jobSearchResult.get());
    }

    private void cleanSecretsByJob(KubernetesClient kubernetesClient, UUID dataKubeJobId) throws KubernetesClientException {
//        final String infra_secret = getSecretNameByJobAndPrefix(DataKubeJobConstants.DATA_KUBE_JOB_INFRASTRUCTURE_SECRET_LABEL_VALUE, dataKubeJobId);
//        final String data_source_secret = getSecretNameByJobAndPrefix(DataKubeJobConstants.DATA_KUBE_JOB_DATASOURCE_SECRET_LABEL_VALUE, dataKubeJobId);
//        kubeCommonService.deleteSecret(kubernetesClient, infra_secret);
//        kubeCommonService.deleteSecret(kubernetesClient, data_source_secret);
    }

    private void doAddMetadataMessage(DataKubeServiceProtos.DataKubeMessage dataKubeMessageHeader, String message)
            throws DataKubeJobNotExistException, InvalidProtocolBufferException, AsyncTaskIdNotExistException, ArticleNotExistsException {
        UUID dataKubeJobId = UUID.fromString(dataKubeMessageHeader.getJobId());
        DataKubeJob dataKubeJob = dataKubeJobRepository.findById(dataKubeJobId)
                .orElseThrow(() -> new DataKubeJobNotExistException(dataKubeJobId));

        DataKubeServiceProtos.AddMetadataMessage metadataMessage =
                DataKubeServiceProtos.AddMetadataMessage.parseFrom(Base64.getDecoder().decode(message));

        UUID articleId = UUID.fromString(dataKubeMessageHeader.getArticleId());
        log.info(String.format("Kafka FinishJob received for article: %s", articleId));

        if (metadataMessage.getMapFieldMap().containsKey("progress")) {
            try {
                String progressValue = metadataMessage.getMapFieldMap().get("progress");
                if (NumberUtils.isParsable(progressValue)) {
                    int progress = Double.valueOf(progressValue).intValue();
                    taskManagementService.updateAsyncTaskProgress(dataKubeJob.getAsyncTaskId(), progress);
                    log.info(String.format("update progress value is %s", progress));
                } else {
                    log.info(String.format("couldn't update progress, given value %s is not number", progressValue));
                }
            } catch (NumberFormatException | AsyncTaskIdNotExistException e) {
                log.error("couldn't update progress", e);
            }
        }

        if (metadataMessage.getMapFieldMap().containsKey("title")) {
            String contentValue = metadataMessage.getMapFieldMap().get("title");
            log.info(String.format("update title value: %s", contentValue));
            updateArticleTitle(articleId, contentValue);
        }

        if (metadataMessage.getMapFieldMap().containsKey("sub_title")) {
            String contentValue = metadataMessage.getMapFieldMap().get("sub_title");
            log.info(String.format("update sub_title value: %s", contentValue));
            updateArticleSubTitle(articleId, contentValue);
        }

        if (metadataMessage.getMapFieldMap().containsKey("content")) {
            String contentValue = metadataMessage.getMapFieldMap().get("content");
            log.info(String.format("update content value is: %s", contentValue));
            updateArticleContent(articleId, contentValue);
        }

        if (metadataMessage.getMapFieldMap().containsKey("votes")) {
            String contentValue = metadataMessage.getMapFieldMap().get("votes");
            log.info(String.format("update votes value: %s", contentValue));
            updateArticleVotes(articleId, contentValue);
        }

        if (metadataMessage.getMapFieldMap().containsKey("image")) {
            String contentValue = metadataMessage.getMapFieldMap().get("image");
            log.info(String.format("update image value: %s", contentValue));
            updateArticleImage(articleId, contentValue);
        }

        Map<String, String> metadata = dataKubeJob.getMetadata();
        metadataMessage.getMapFieldMap().forEach((k, v) -> {
            if (!k.equals("content") && k.equals("image")){
                metadata.put(k, v);
            }
            log.info("key: %s, value: %d%n", k, v);
        });
        dataKubeJobRepository.save(dataKubeJob);
    }

    private void doFinishJobMessage(DataKubeServiceProtos.DataKubeMessage dataKubeMessageHeader, String message)
            throws InvalidProtocolBufferException, DataKubeJobNotExistException, AsyncTaskIdNotExistException, ArticleNotExistsException {
        UUID dataKubeJobId = UUID.fromString(dataKubeMessageHeader.getJobId());
        DataKubeJob dataKubeJob = dataKubeJobRepository.findById(dataKubeJobId)
                .orElseThrow(() -> new DataKubeJobNotExistException(dataKubeJobId));

        DataKubeServiceProtos.FinishJob finishJob =
                DataKubeServiceProtos.FinishJob.parseFrom(Base64.getDecoder().decode(message));

        UUID articleId = UUID.fromString(dataKubeMessageHeader.getArticleId());
        if (!DataKubeServiceProtos.FinishJob.Status.SUCCESS.equals(finishJob.getStatus())) {
            updateAsyncTasks(dataKubeJob.getAsyncTaskId(), TaskStatus.ERROR, finishJob.getException());
            updateArticleStatus(articleId, true, true);
        }else {
            updateArticleStatus(articleId, true, false);
        }

/*
        try {
            saveModelImageVersionByDataKubeJob(dataKubeJob);
        } catch (Exception e) {
            log.error("error while saving image version to model", e);
        }
*/
        publishEvent(dataKubeJob);

        /*
        UUID articleId = UUID.fromString(dataKubeMessageHeader.getArticleId());
        log.info(String.format("Kafka FinishJob received for article: %s and status: %s", articleId, finishJob.getStatus()));


        boolean isCompleted = true;
        boolean isFaulted = false;
        if (!DataKubeServiceProtos.FinishJob.Status.SUCCESS.equals(finishJob.getStatus())) {
            isFaulted = true;
//            updateAsyncTasks(dataKubeJob.getArticleId(), TaskStatus.ERROR, finishJob.getException());
        }
        updateArticleStatus(articleId, isCompleted, isFaulted);
        try {
//            saveModelImageVersionByDataKubeJob(dataKubeJob);
        } catch (Exception e) {
            log.error("error while saving image version to model", e);
        }
//        publishEvent(dataKubeJob);
*/
    }

    private Secret updateInfrastructureSecret(KubernetesClient client, UUID dataKubeJobId) {
        Map<String, String> dataMap = Map.of(
                CONFIG_FILE_NAME_KAFKA_CONFIG,
                Base64.getEncoder().encodeToString(kubeCommonService.buildKafkaConfigurationDTO(DATA_KUBE_JOB_KAFKA_TOPIC).toByteArray()));

        String secretName = getSecretNameByJobAndPrefix(DataKubeJobConstants.DATA_KUBE_JOB_INFRASTRUCTURE_SECRET_LABEL_VALUE, dataKubeJobId);
        return kubeCommonService.createSecret(client, dataMap, secretName);

    }

    private String getSecretNameByJobAndPrefix(String prefix, UUID dataKubeJobId) {
//        return prefix + '-' + tenantContextResolver.resolveCurrentTenantIdentifier() + '-' + dataKubeJobId;
        return prefix + '-' + '-' + dataKubeJobId;
    }

    public Article updateArticleStatus(UUID id, boolean completed, boolean faulted) throws ArticleNotExistsException {
        final Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotExistsException(id));
        article.setCompleted(completed);
        article.setFaulted(faulted);
        return articleRepository.save(article);
    }

    public Article updateArticleTitle(UUID id, String title) throws ArticleNotExistsException {
        final Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotExistsException(id));
        article.setTitle(title);
        return articleRepository.save(article);
    }

    public Article updateArticleSubTitle(UUID id, String subTitle) throws ArticleNotExistsException {
        final Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotExistsException(id));
        article.setSubTitle(subTitle);
        return articleRepository.save(article);
    }

    public Article updateArticleContent(UUID id, String content) throws ArticleNotExistsException {
        final Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotExistsException(id));
        article.setContent(content);
        return articleRepository.save(article);
    }

    public Article updateArticleImage(UUID id, String image) throws ArticleNotExistsException {
        final Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotExistsException(id));
        article.setImage(image);
        return articleRepository.save(article);
    }

    public Article updateArticleVotes(UUID id, String vote) throws ArticleNotExistsException {
        final Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotExistsException(id));
        int votes = article.getVotes();
        if(vote != null){
            votes = vote.equals("increase")? votes+1 : votes-1;
            if(votes < 0)
                votes = 0;
        }
        article.setVotes(votes);
        return articleRepository.save(article);
    }

    protected void updateAsyncTasks(UUID asyncTaskId, TaskStatus taskStatus, String errorMessage) throws AsyncTaskIdNotExistException {

        if (TaskStatus.ERROR.equals(taskStatus)) {
                taskManagementService.failTask(asyncTaskId, errorMessage, ErrorType.EVENT_TRACER);
        }

        try {
            taskManagementService.updateAsyncTaskCompleted(asyncTaskId);
        } catch (IllegalAsyncTaskUpdateException e) {
            log.error("a request to complete an already completed task was reported ignoring", e);
        }

    }

    private void doSubtaskCompletedMessage(DataKubeServiceProtos.DataKubeMessage dataKubeMessageHeader, String message)
            throws DataKubeJobNotExistException, InvalidProtocolBufferException, AsyncTaskIdNotExistException {
        UUID dataKubeJobId = UUID.fromString(dataKubeMessageHeader.getJobId());
        DataKubeJob dataKubeJob = dataKubeJobRepository.findById(dataKubeJobId)
                .orElseThrow(() -> new DataKubeJobNotExistException(dataKubeJobId));
        dataKubeJob.setCallerFilter(dataKubeJob.getCallerFilter() + SUBTASK_SUFFIX);

        DataKubeServiceProtos.FinishJob finishSubtaskJob =
                DataKubeServiceProtos.FinishJob.parseFrom(Base64.getDecoder().decode(message));

        if (!DataKubeServiceProtos.FinishJob.Status.SUCCESS.equals(finishSubtaskJob.getStatus())) {
            updateAsyncTasks(dataKubeJob.getAsyncTaskId(), TaskStatus.ERROR, finishSubtaskJob.getException());
        }

        publishEvent(dataKubeJob);

    }


    private void publishEvent(DataKubeJob dataKubeJob) {
        String realmName = "devozs";
//        if(SecurityContextService.getAuthDetails() != null){
//            realmName = SecurityContextService.getAuthDetails().getRealmName();
//        }
        ApplicationEventJobPublisher applicationEventJobPublisher =
                new ApplicationEventJobPublisher(
                        new DataKubeJobEvent(this, dataKubeJob.getCallerFilter(), dataKubeJob.getId()),
                        realmName,
                        MDC.get(REQUEST_PROCESSING_USER_NAME), MDC.get(REQUEST_PROCESSING_ARTICLE_ID));
        applicationContext.getAutowireCapableBeanFactory().autowireBean(applicationEventJobPublisher);
        Thread thread = new Thread(applicationEventJobPublisher);
        thread.start();
    }

    private static Pod getHarticlePod(KubernetesClient kubernetesClient, String nameSpace) {
        if (null == kubernetesClient)
            return null;

        Optional<Pod> optionalPod = kubernetesClient.pods().inNamespace(nameSpace).list().getItems()
                .stream().filter(pod -> pod.getSpec().getContainers()
                        .stream().anyMatch(container -> container.getName().equals(HARTICLE_ENGINE_CONTAINER)))
                .findFirst();
        if (optionalPod.isPresent()) {
            Optional<Container> optionalContainer = optionalPod.get().getSpec().getContainers().stream()
                    .filter(container -> container.getName().equals(HARTICLE_ENGINE_CONTAINER)).findFirst();

            if (optionalContainer.isPresent()) {
                log.debug("harticle pod name: %s".formatted(optionalPod.get().getMetadata().getName()));
                log.debug("harticle pod id: %s".formatted(optionalPod.get().getMetadata().getUid()));
                return optionalPod.get();
            }
        }
        return null;
    }

}