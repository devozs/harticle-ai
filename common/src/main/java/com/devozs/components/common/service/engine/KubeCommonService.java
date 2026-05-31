package com.devozs.components.common.service.engine;

import com.devozs.components.common.dto.infrastructure.settings.InfrastructureSettingsProtos;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.*;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Base64;
import java.util.Map;

@Service
@Slf4j
public class KubeCommonService {

    static final String KAFKA_USER = "kafka-user";

    @Autowired
    public KubeCommonService() {
    }

    public String getKubernetesNamespace() {
        return "devozs";
    }

//    public KafkaConfiguration getKafkaConfiguration() {
//        return infrastructureSettingsService.getKafkaConfiguration();
//    }

    public KubernetesClient createKubernetesClient() {
        Config config = new ConfigBuilder()
                .withRequestRetryBackoffLimit(2)
                .build();
        return new DefaultKubernetesClient(config);
    }



    protected ConfigMap createConfigMap(KubernetesClient client, Map<String, String> dataMap, String configMapName) {
        return client.configMaps().inNamespace(getKubernetesNamespace())
                .createOrReplace(new ConfigMapBuilder().withData(dataMap)
                        .withMetadata(getObjectMeta(configMapName)).build());
    }


    protected ConfigMap createConfigMapFromFile(KubernetesClient client, File baseApplicationYaml, String configMapName) {
        return client.configMaps().inNamespace(getKubernetesNamespace())
                .createOrReplace(new ConfigMapBuilder(client.configMaps().load(baseApplicationYaml).get())
                        .withMetadata(getObjectMeta(configMapName)).build());
    }

    public Secret createSecret(KubernetesClient client, Map<String, String> dataMap, String secretName) {
        return client.secrets()
                .inNamespace(getKubernetesNamespace())
                .createOrReplace(new SecretBuilder().withStringData(dataMap).withType("Opaque")
                        .withMetadata(getObjectMeta(secretName)).build());
    }

    private ObjectMeta getObjectMeta(String secretName) {
        return new ObjectMetaBuilder()
                .withName(secretName)
                .build();
    }

    protected Secret getSecret(KubernetesClient client, String secretName) throws KubernetesClientException {
        return client.secrets()
                .inNamespace(getKubernetesNamespace())
                .withName(secretName).get();
    }

    protected void deleteSecret(KubernetesClient client, String secretName) throws KubernetesClientException {
        client.secrets().inNamespace(getKubernetesNamespace()).withName(secretName).delete();
    }

    public String getKafkaPassword() {
        return getBase64Secret(KAFKA_USER, "password");
    }


    protected String getBase64Secret(String secretKey, String secretValue) {
        try (final KubernetesClient kubernetesControlPlaneClient = createKubernetesClient()) {
            Secret secret = getSecret(kubernetesControlPlaneClient, secretKey);
            return new String(Base64.getDecoder().decode(secret.getData().get(secretValue)));
        } catch (Exception e) {
            log.error(String.format("Failed to get secret of name %s", secretKey));
            throw e;
        }
    }

    public InfrastructureSettingsProtos.KafkaConfigurationDto buildKafkaConfigurationDTO(String topic) {

        InfrastructureSettingsProtos.KafkaConfigurationDto.Builder builder = InfrastructureSettingsProtos.KafkaConfigurationDto.newBuilder()
                .setUrl("devozs-cluster-kafka-bootstrap:9092")
                .setTopic(topic);

        builder.setUsername(KAFKA_USER);

        builder.setPassword(getKafkaPassword());

        return builder.build();
    }

}
