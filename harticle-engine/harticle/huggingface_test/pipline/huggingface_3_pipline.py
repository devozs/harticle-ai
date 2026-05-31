import torch
from datasets import load_dataset
from transformers import AutoTokenizer
from transformers import DataCollatorForLanguageModeling
from harticle.huggingface_test.common import constants

device = "cuda:0" if torch.cuda.is_available() else "cpu"
print(device)
context_length = 40
# tokenizer_name = constants.NAME + "_tokenizer"
tokenizer_name = constants.NAME
tokenizer = AutoTokenizer.from_pretrained(tokenizer_name)
soccer_ds = load_dataset(constants.NAME)


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

out = data_collator([tokenized_dataset["train"][i] for i in range(5)])
for key in out:
    print(f"{key} shape: {out[key].shape}")


tf_train_dataset = tokenized_dataset["train"].to_tf_dataset(
    columns=["input_ids", "attention_mask", "labels"],
    collate_fn=data_collator,
    shuffle=True,
    batch_size=32
)

tf_eval_dataset = tokenized_dataset["validation"].to_tf_dataset(
    columns=["input_ids", "attention_mask", "labels"],
    collate_fn=data_collator,
    shuffle=True,
    batch_size=32
)

print(len(tf_train_dataset))
