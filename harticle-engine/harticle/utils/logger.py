import os
import logging
import sys
import requests
import io
import json

LOG_FILE_NAME = "log.txt"
DATE_FORMAT = "%d-%m-%Y %H:%M:%S"

LOG_FORMAT = json.dumps({
    "date": "%(asctime)s.%(msecs)03d",
    "className": "%(name)s",
    "thread": "%(threadName)s",
    "level": "%(levelname)s",
    "message": "%(message)s"
})


def config_logger():
    log_level = os.environ.get('LOGLEVEL', 'INFO').upper()
    print("LOGLEVEL: ", log_level)

    root = logging.getLogger()
    root.setLevel(log_level)
    logging.basicConfig(filename=LOG_FILE_NAME)

    handler = logging.StreamHandler(sys.stdout)
    handler.setLevel(log_level)
    formatter = logging.Formatter(fmt=LOG_FORMAT, datefmt=DATE_FORMAT)
    handler.setFormatter(formatter)
    root.addHandler(handler)