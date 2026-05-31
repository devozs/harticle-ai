#!/bin/bash
IN_DIR="../common/src/main/proto"
OUT_DIR="./harticle/generated_protos/"

mkdir -p ${OUT_DIR}

protoc --proto_path=${IN_DIR} \
  --python_out="${OUT_DIR}" \
  "${IN_DIR}"/*.proto

if command -v protoc-gen-mypy >/dev/null 2>&1; then
  protoc --proto_path=${IN_DIR} \
    --mypy_out="${OUT_DIR}" \
    "${IN_DIR}"/*.proto
fi