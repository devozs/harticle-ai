#!/bin/bash
set -euo pipefail

# Set OPENAI_API_KEY in your environment before running (never commit keys).
: "${OPENAI_API_KEY:?Set OPENAI_API_KEY}"

BASE_DATA_FOLDER='harticle/data/sport/reporters'
TITLE_2_CONTENT_FILE_NAME=title_content
SUBTITLE_2_CONTENT_FILE_NAME=subtitle_content

# convert csv to jsonl
echo 'Finetune Title2Content'
openai tools fine_tunes.prepare_data -f "${BASE_DATA_FOLDER}/${TITLE_2_CONTENT_FILE_NAME}.csv"
echo 'Finetune SubTitle2Content'
openai tools fine_tunes.prepare_data -f "${BASE_DATA_FOLDER}/${SUBTITLE_2_CONTENT_FILE_NAME}.csv"

# create custom train
echo 'Convert csv to jsonl'
openai -k "${OPENAI_API_KEY}" api fine_tunes.create -t "${BASE_DATA_FOLDER}/${TITLE_2_CONTENT_FILE_NAME}_prepared.jsonl" -m ada

openai -k "${OPENAI_API_KEY}" api fine_tunes.create -t "${BASE_DATA_FOLDER}/${SUBTITLE_2_CONTENT_FILE_NAME}_prepared.jsonl" -m ada
openai -k "${OPENAI_API_KEY}" api fine_tunes.create -t "${BASE_DATA_FOLDER}/${SUBTITLE_2_CONTENT_FILE_NAME}_prepared.jsonl" -m curie

# resume watch progress (set FINE_TUNE_ID)
# openai -k "${OPENAI_API_KEY}" api fine_tunes.follow -i "${FINE_TUNE_ID}"
