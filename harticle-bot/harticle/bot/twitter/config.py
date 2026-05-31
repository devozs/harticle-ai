import tweepy
import logging
import os

logger = logging.getLogger()


class ApiConfig:
    def __init__(self):
        self.consumer_key = os.getenv("CONSUMER_KEY")
        self.consumer_secret = os.getenv("CONSUMER_SECRET")
        self.access_token = os.getenv("ACCESS_TOKEN")
        self.access_token_secret = os.getenv("ACCESS_TOKEN_SECRET")
        self.bearer_token = os.getenv("BEARER_TOKEN")
        auth = tweepy.OAuth1UserHandler(
            self.consumer_key, self.consumer_secret, self.access_token, self.access_token_secret)
        self.api = tweepy.API(auth, wait_on_rate_limit=True)
        self.client = tweepy.Client(
            self.bearer_token, self.consumer_key, self.consumer_secret, self.access_token, self.access_token_secret)
        try:
            self.api.verify_credentials()
            logger.info("API created")
            self.client_id = self.client.get_me().data.id
            logger.info("Client ID:" + str(self.client_id))
        except Exception as e:
            logger.error("Error creating API", exc_info=True)
            raise e
