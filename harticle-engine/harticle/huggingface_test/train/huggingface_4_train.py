from datasets import load_dataset
from transformers import AutoTokenizer, TFGPT2LMHeadModel, AutoConfig, create_optimizer, DataCollatorForLanguageModeling
from transformers.keras_callbacks import PushToHubCallback
import tensorflow as tf
import torch
from harticle.huggingface_test.common import constants
import os
from os.path import exists


if exists("./devozs"):
    for root, dirs, files in os.walk("./devozs", topdown=False):
        for name in files:
            os.remove(os.path.join(root, name))
        for name in dirs:
            os.rmdir(os.path.join(root, name))
    os.rmdir("./devozs")
device = "cuda:0" if torch.cuda.is_available() else "cpu"
print(device)
context_length = 40
# tokenizer_name = constants.NAME + "_tokenizer"
tokenizer_name = constants.NAME
# tokenizer = AutoTokenizer.from_pretrained(tokenizer_name)
tokenizer = AutoTokenizer.from_pretrained("Norod78/hebrew-gpt_neo-xl")
soccer_ds = load_dataset(constants.NAME)

config = AutoConfig.from_pretrained(
    "gpt2",
    vocab_size=len(tokenizer),
    n_ctx=context_length,
    bos_token_id=tokenizer.bos_token_id,
    eos_token_id=tokenizer.eos_token_id,
)

model = TFGPT2LMHeadModel(config)
model(model.dummy_inputs)
model.summary()


def tokenize(element):
    outputs = tokenizer(
        element["article_body"],
        truncation=True,
        max_length=context_length,
        return_overflowing_tokens=True,
        return_length=True
    )
    input_batch = []
    for length, input_ids in zip(outputs["length"], outputs["input_ids"]):
        if length == context_length:
            input_batch.append(input_ids)
    return {"input_ids": input_batch}


tokenized_dataset = soccer_ds.map(tokenize,
                                  batched=True,
                                  remove_columns=soccer_ds["train"].column_names)
print(tokenized_dataset)

tokenizer.pad_token = tokenizer.eos_token
data_collator = DataCollatorForLanguageModeling(tokenizer, mlm=False, return_tensors="tf")

tf_train_dataset = tokenized_dataset["train"].to_tf_dataset(
    columns=["input_ids", "attention_mask", "labels"],
    collate_fn=data_collator,
    shuffle=True,
    batch_size=32,
)

tf_eval_dataset = tokenized_dataset["validation"].to_tf_dataset(
    columns=["input_ids", "attention_mask", "labels"],
    collate_fn=data_collator,
    shuffle=True,
    batch_size=32,
)

num_train_steps = len(tf_train_dataset)
optimizer, schedule = create_optimizer(
    init_lr=5e-5,
    num_warmup_steps=1_000,
    num_train_steps=num_train_steps,
    weight_decay_rate=0.01,
)

model.compile(optimizer=optimizer)
tf.keras.mixed_precision.set_global_policy("mixed_float16")

callback = PushToHubCallback(output_dir=constants.NAME, tokenizer=tokenizer)
model.fit(tf_train_dataset, validation_data=tf_eval_dataset, epochs=5, callbacks=[callback])
model.push_to_hub(constants.NAME)

