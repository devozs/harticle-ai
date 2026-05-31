import os
import logging
from typing import Callable

from harticle.generated_protos import infrastructuresettings_pb2
from harticle.utils import constants
from harticle.utils.data_kube_job_client import DataKubeJobClient
from harticle.utils.logger import config_logger
from harticle.utils.debug_utils import check_debug
from harticle.utils.proto_printer import SafePrinter

LOGGER = logging.getLogger(__name__)


class EnvVariables(object):
    def __init__(self, tenant_id, security_identifier, data_kube_job_id, kube_job_id, user_name, kafka_password,
                 user_email, article_id, env_name, article_keywords,
                 reporter_id, reporter_name, temperature):
        self.tenant_id = tenant_id
        self.security_identifier = security_identifier
        self.data_kube_job_id = data_kube_job_id
        self.kube_job_id = kube_job_id
        self.user_name = user_name
        self.kafka_password = kafka_password
        self.user_email = user_email
        self.article_id = article_id
        self.article_keywords = article_keywords
        self.env_name = env_name
        self.reporter_id = reporter_id
        self.reporter_name = reporter_name
        self.temperature = temperature

    @classmethod
    def read_env_variables(cls):
        tenant_id = readEnvVar(constants.ENV_VARIABLE_TENANT_ID)
        security_identifier = readEnvVar(constants.ENV_SECURITY_IDENTIFIER)
        data_kube_job_id = readEnvVar(constants.ENV_DATA_KUBE_JOB_ID)
        kube_job_id = readEnvVar(constants.ENV_KUBE_JOB_ID)
        user_name = readEnvVar(constants.ENV_VARIABLE_USER_NAME)
        kafka_password = readEnvVar(constants.ENV_VARIABLE_KAFKA_PASSWORD)
        user_email = readEnvVar(constants.ENV_VARIABLE_USER_EMAIL)
        env_name = readEnvVar(constants.ENV_VARIABLE_ENV_NAME)
        article_id = readEnvVar(constants.ENV_VARIABLE_ARTICLE_ID)
        article_keywords = readEnvVar(constants.ENV_VARIABLE_ARTICLE_KEYWORDS)
        reporter_id = readEnvVar(constants.ENV_VARIABLE_REPORTER_ID)
        reporter_name = readEnvVar(constants.ENV_VARIABLE_REPORTER_NAME)
        temperature = readEnvVar(constants.ENV_VARIABLE_TEMPERATURE)
        return cls(tenant_id,
                   security_identifier,
                   data_kube_job_id,
                   kube_job_id,
                   user_name,
                   kafka_password,
                   user_email,
                   article_id,
                   env_name,
                   article_keywords,
                   reporter_id,
                   reporter_name,
                   temperature)


def readEnvVar(var_name):
    var_value = os.getenv(var_name)
    if var_value is None:
        var_value = "missing"
    return var_value


def read_all_and_invoke(method: Callable, env_vars: EnvVariables = None):
    config_logger()
    check_debug()
    if env_vars is None:
        LOGGER.info('Empty input vars, reading from OS vars')
        env_vars = EnvVariables.read_env_variables()
    else:
        LOGGER.info('Non empty input vars, using...')
    LOGGER.info('ENV_NAME = %s', env_vars.env_name)
    LOGGER.info('ENV_VARIABLE_TENANT_ID = %s', env_vars.tenant_id)
    LOGGER.info('ENV_SECURITY_IDENTIFIER = %s', env_vars.security_identifier)
    LOGGER.info('ENV_DATA_KUBE_JOB_ID = %s', env_vars.data_kube_job_id)
    LOGGER.info('ENV_KUBE_JOB_ID = %s', env_vars.kube_job_id)
    LOGGER.info('ENV_USER_NAME = %s', env_vars.user_name)
    LOGGER.info('ENV_KAFKA_PASSWORD = %s', env_vars.kafka_password)
    LOGGER.info('ENV_USER_EMAIL = %s', env_vars.user_email)
    LOGGER.info('ENV_ARTICLE_ID = %s', env_vars.article_id)
    LOGGER.info('ENV_ARTICLE_KEYWORDS = %s', env_vars.article_keywords)
    LOGGER.info('ENV_REPORTER_ID = %s', env_vars.reporter_id)
    LOGGER.info('ENV_REPORTER_NAME = %s', env_vars.reporter_name)
    LOGGER.info('ENV_TEMPERATURE = %s', env_vars.temperature)

    infrastructuresettings = infrastructuresettings_pb2.KafkaConfigurationDto();
    infrastructuresettings.topic = os.environ.get("KAFKA_TOPIC", "data_kube_job_service")
    infrastructuresettings.url = os.environ.get(
        "KAFKA_BOOTSTRAP_SERVERS",
        "devozs-cluster-kafka-bootstrap.devozs.svc.cluster.local:9092",
    )
    infrastructuresettings.username = os.environ.get("KAFKA_USERNAME", "kafka-user")
    infrastructuresettings.password = env_vars.kafka_password

    LOGGER.info('kafka_config: %s', SafePrinter.get_kafka_config(infrastructuresettings))
    data_kube_client = DataKubeJobClient(kafka_config=infrastructuresettings,
                                         kube_job_id=env_vars.data_kube_job_id,
                                         tenant_id=env_vars.tenant_id,
                                         security_identifier=env_vars.security_identifier,
                                         user_name=env_vars.user_name,
                                         article_id=env_vars.article_id)
    try:
        method(env_vars, data_kube_client)
    except Exception as ex:
        data_kube_client.send_job_status(status=data_kube_client.Status.FAILED,
                                         exception_reason=DataKubeJobClient.ExceptionReason.GENERAL,
                                         exception_message=str(ex))
        LOGGER.exception(ex)
        raise ex
