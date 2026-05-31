import os
from time import sleep
from datetime import datetime, timedelta
from tweepy import StreamingClient, StreamRule, Tweet
from logging import getLogger
from logging.config import fileConfig
from config import ApiConfig


config = ApiConfig()
api = config.api
client = config.client
client_id = config.client_id


class TwitterRuleError(Exception):
    pass


class TweetAggregator(StreamingClient):
    def __init__(self, *args, **kwargs):

        self.rules = self.build_default_rules()
        self.timeout = timedelta(minutes=5)
        self.tweet_count = 0

        fileConfig(
            "logging.conf",
            defaults={"logs/twitter.log"},
        )
        self.logger = getLogger("TwitterAggregator")

        self.logger.info("Twitter aggregator initialized")

        super().__init__(*args, **kwargs)

    def build_default_rules(self):

        harticle_rule = "@harticle1"

        return [
            StreamRule(f"({harticle_rule}) lang:he"),
        ]

    def add_default_rules(self):

        existing_rules = self.get_rules()
        response = None

        # No rules exist
        if existing_rules.data is None:  # type: ignore
            self.logger.info("No rules found. Adding new rules")
            response = self.add_rules(self.rules)

        # If rules have changed
        elif set(rule.value for rule in existing_rules.data) != set(rule.value for rule in self.rules):  # type: ignore

            self.logger.info("Rules have changed. Updating existing rules to new rules")

            # Delete old rules
            self.delete_rules([x.id for x in self.get_rules().data])  # type: ignore
            # Add new rules
            response = self.add_rules(self.rules)

        # Rules are same
        else:
            self.logger.info("Same stream rules found")

        if not response is None and response.data is None:  # type: ignore
            self.logger.critical("Unable to update rules")
            self.logger.critical(f'Twitter rule error: {response.errors[0]["details"]}')  # type: ignore
            raise TwitterRuleError(response.errors[0]["details"])  # type: ignore

    def on_tweet(self, tweet: Tweet):
        print("on_tweet")
        tweet_id = tweet.id
        if tweet.in_reply_to_user_id is None:
            if tweet.referenced_tweets is None:
                try:

                    # api.retweet(tweet_id)
                    api.create_favorite(tweet_id)
                    self.logger.info(f" Retweeted & Liked {tweet_id}")
                except:
                    self.logger.error(f" Couldn't perform action on {tweet_id}")

        self.tweet_count += 1

        # Log processed tweet counts every 5 minutes
        if datetime.now() > self.end_time:

            self.logger.info(f"Processed {self.tweet_count} tweets in 5 minutes")

            self.end_time = datetime.now() + self.timeout
            self.tweet_count = 0

    def on_connection_error(self):

        self.logger.error("Cannot connect to stream")

        return super().on_connection_error()

    def on_errors(self, errors):

        self.logger.error("Errors recieved.")

        for k, v in errors.items():
            self.logger.error(f"{k}: {v}")

        return super().on_errors(errors)

    def on_exception(self, exception):

        self.logger.error("An unhandled exception has occured")
        self.logger.exception(exception)

        return super().on_exception(exception)

    def run(self):

        backoff = 1

        self.logger.info("Adding default rules")
        self.add_default_rules()

        self.end_time = datetime.now() + self.timeout

        try:

            while True:

                self.logger.info("Starting filter()")
                self.filter(tweet_fields=["referenced_tweets", "in_reply_to_user_id"])
                self.logger.info(
                    f"Stream disconnected. Backing off for {2 ** backoff} seconds"
                )

                sleep(2**backoff)
                backoff += 1

        except KeyboardInterrupt:
            self.logger.critical("Keyboard Interrupt received. Shutting down..")


if __name__ == "__main__":
    tweet_aggregator = TweetAggregator(
        config.bearer_token, wait_on_rate_limit=True
    )
    tweet_aggregator.run()
