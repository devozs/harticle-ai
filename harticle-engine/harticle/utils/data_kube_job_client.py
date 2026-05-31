from kafka import KafkaProducer
import base64
from enum import Enum
from harticle.generated_protos import infrastructuresettings_pb2, datakubeservice_pb2
from harticle.utils import exceptions


class DataKubeJobClient(object):
    KAFKA_ENV_KEY_SERVERS_URL = 'KAFKA_URLS'
    KAFKA_ENV_KEY_TOPIC_NAME = 'DATA_KUBE_EVENTS_TOPIC_NAME'
    KAFKA_ENV_KEY_USERNAME = 'KAFKA_USERNAME'
    KAFKA_ENV_KEY_PASSWORD = 'KAFKA_PASSWORD'

    DEFAULT_CONFIG = {
        KAFKA_ENV_KEY_SERVERS_URL: 'devozs-cluster-kafka-bootstrap:9092',
        KAFKA_ENV_KEY_TOPIC_NAME: 'data_kube_job_service',
        # KAFKA_ENV_KEY_USERNAME: 'kafka-user',
        # KAFKA_ENV_KEY_PASSWORD: 'missing'
    }

    class Status(Enum):
        SUCCESS = 0
        FAILED = 1

        @classmethod
        def has_value(cls, value):
            return value in cls._value2member_map_

    class ExceptionReason(Enum):
        NONE = 0
        GENERAL = 1
        COMMUNICATION_ERROR = 2
        INVALID_USER_PASSWORD = 3
        INSUFFICIENT_PRIVILEGE_ERROR = 4

        @classmethod
        def has_value(cls, value):
            return value in cls._value2member_map_

    def __init__(self, kafka_config: infrastructuresettings_pb2.KafkaConfigurationDto,
                 kube_job_id=None, tenant_id=None, security_identifier=None, user_name=None, article_id=None):

        self._init_kafka_params(kafka_config)

        if kube_job_id is not None:
            self._kube_job_id = kube_job_id
        else:
            raise exceptions.InvalidConfiguration('kube job id was not found')

        if tenant_id is not None:
            self._tenant_id = tenant_id
        else:
            raise exceptions.InvalidConfiguration('tenant id was not found')

        if security_identifier is not None:
            self._security_identifier = security_identifier
        else:
            raise exceptions.InvalidConfiguration('security identifier was not found')

        if user_name is not None:
            self._user_name = user_name
        else:
            raise exceptions.InvalidConfiguration('user name was not found')

        if article_id is not None:
            self._article_Id = article_id
        else:
            raise exceptions.InvalidConfiguration('article id was not found')

        self._init_producer()
        self.send_metadata({'progress': '1'})

    def _init_kafka_params(self, kafka_config: infrastructuresettings_pb2.KafkaConfigurationDto):
        if kafka_config.url is not None:
            self._bootstrap_servers = kafka_config.url
        else:
            self._bootstrap_servers = self.DEFAULT_CONFIG[self.KAFKA_ENV_KEY_SERVERS_URL]

        if kafka_config.topic is not None:
            self._topic_name = kafka_config.topic
        else:
            self._topic_name = self.DEFAULT_CONFIG[self.KAFKA_ENV_KEY_TOPIC_NAME]

        if kafka_config.username is not None:
            self._username = kafka_config.username
        else:
            self._username = None

        if kafka_config.password is not None:
            self._password = kafka_config.password
        else:
            self._password = None

    def _init_producer(self):
        print(self._bootstrap_servers)
        if self._password is not None and bool(self._password):
            self._producer = KafkaProducer(bootstrap_servers=[self._bootstrap_servers], security_protocol="SASL_PLAINTEXT", sasl_mechanism="SCRAM-SHA-512", sasl_plain_username=self._username, sasl_plain_password=self._password )
        else:
            self._producer = KafkaProducer(bootstrap_servers=[self._bootstrap_servers])

    def __del__(self):
        self._producer.flush()
        self._producer.close()
    
    @staticmethod
    def __serialize(protobuf_object):
        return base64.b64encode(protobuf_object.SerializeToString())
    
    @staticmethod
    def deserialize(buffer, protobuf_object):
        protobuf_object.ParseFromString(base64.b64decode(buffer))
        return protobuf_object

    def __send_message(self, message, header):
        data_kube_message = datakubeservice_pb2.DataKubeMessage()
        data_kube_message.objectType = datakubeservice_pb2._DATAKUBEMESSAGE_OBJECTTYPE.values_by_name[header].number
        data_kube_message.jobId = self._kube_job_id
        data_kube_message.tenantId = self._tenant_id
        data_kube_message.securityIdentifier = self._security_identifier
        data_kube_message.userName = self._user_name
        data_kube_message.articleId = self._article_Id

        self._producer.send(topic=self._topic_name, value=DataKubeJobClient.__serialize(message),
                            headers=[('type', DataKubeJobClient.__serialize(data_kube_message))])

    def send_metadata(self, key_value_dict):
        add_metadata_message = datakubeservice_pb2.AddMetadataMessage()
        for i, (k, v) in enumerate(key_value_dict.items()):
            add_metadata_message.map_field[k] = v

        self.__send_message(add_metadata_message, 'ADD_METADATA_MESSAGE')

    def send_finish_job_message(self, status, message_header, exception_reason=ExceptionReason.NONE, exception_message=''):
        if not self.Status.has_value(status.value):
            raise exceptions.InvalidInput('status value is unknown')

        if exception_reason is not None and not self.ExceptionReason.has_value(exception_reason.value):
            raise exceptions.InvalidInput('exception reason value is unknown')

        finish_job = datakubeservice_pb2.FinishJob()
        finish_job.status = status.value
        finish_job.exceptionReason = exception_reason.value
        finish_job.exception = exception_message

        self.__send_message(finish_job, message_header)

    def send_job_status(self, status, exception_reason=ExceptionReason.NONE, exception_message=''):
        self.send_finish_job_message(status, 'FINISH_JOB', exception_reason, exception_message)

    def send_subtask_completed(self, status, exception_reason=ExceptionReason.NONE, exception_message=''):
        self.send_finish_job_message(status, 'SUBTASK_COMPLETED', exception_reason, exception_message)

