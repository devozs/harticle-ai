FROM python:3.8-slim-buster

WORKDIR /app

COPY . .

RUN apt update -y
RUN apt install -y protobuf-compiler
RUN protoc --version
RUN pip install -r requirements.txt
RUN mkdir -p ./harticle/generated_protos
RUN protoc --proto_path=./proto --python_out=./harticle/generated_protos --mypy_out=./harticle/generated_protos ./proto/*.proto



ENTRYPOINT ["python", "harticle/create_article.py"]
#CMD ["sanity"]
