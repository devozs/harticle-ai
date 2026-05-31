import tweepy
import logging
from config import ApiConfig
import requests

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger()
botname = "@harticle1"

config = ApiConfig()
api = config.api
client = config.client
client_id = config.client_id


class FavRetweetListener(tweepy.StreamingClient):
    def on_connect(self):
        logger.info(" Bot Started")

    def on_tweet(self, tweet: tweepy.Tweet):
        tweet_id = tweet.id
        if tweet.in_reply_to_user_id is None:
            if tweet.referenced_tweets is None:
                try:

                    # api.retweet(tweet_id)
                    api.create_favorite(tweet_id)
                    logger.info(f" Retweeted & Liked {tweet_id}")
                except:
                    logger.error(f" Couldn't perform action on {tweet_id}")

    def on_request_error(self, status_code):
        logger.critical(f" Encountered error: {status_code}")


def main():
    fav_stream = FavRetweetListener(bearer_token=config.bearer_token, wait_on_rate_limit=True)
    # fav_stream.delete_rules(client)
    fav_stream.add_rules(tweepy.StreamRule(value='@harticle1'))
    fav_stream.filter(tweet_fields=["referenced_tweets", "in_reply_to_user_id"])


main()
