from harticle.generated_protos import infrastructuresettings_pb2


class SafePrinter(object):
    @staticmethod
    def get_kafka_config(kafka_config: infrastructuresettings_pb2.KafkaConfigurationDto) -> str:
        return f'url: {kafka_config.url} topic: {kafka_config.topic}'
