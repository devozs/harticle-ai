from transformers import AutoTokenizer, TrainingArguments, Trainer, AutoConfig, DataCollatorForLanguageModeling, GPTNeoForCausalLM
from transformers.trainer_callback import TrainerCallback
# from harticle.huggingface_test.common.utils import clean_model, free_gpu_cache
# from harticle.huggingface_test.common import constants
from datasets import load_dataset
import numpy as np
import evaluate
import torch
import gc
from numba import cuda
import pandas as pd
from huggingface_test.common import constants
from huggingface_test.common.utils import clean_model, free_gpu_cache
from torch.utils.data import Dataset, random_split

####################################
# PYTHONUNBUFFERED=1;PYTORCH_CUDA_ALLOC_CONF=max_split_size_mb:128;CUDA_LAUNCH_BLOCKING=1
####################################

BASE_REPO = "Norod78"
BASE_MODEL_NAME = "hebrew-gpt_neo-small"
FULL_MODEL_NAME = "{}/{}".format(BASE_REPO, BASE_MODEL_NAME)
CUSTOM_REPO = "devozs"
CUSTOM_MODEL_NAME = "{}/{}-soccer-news".format(CUSTOM_REPO, BASE_MODEL_NAME)
context_length = 40  # better to had context_length as near as the requested generated article

clean_model("devozs")
gc.collect()
torch.cuda.empty_cache()


class ArticleDataset(Dataset):
    def __init__(self, txt_list, tokenizer, max_length):
        self.input_ids = []
        self.attn_masks = []
        self.labels = []
        self.input_ids, self.attn_masks = tokenizer(list('<|startoftext|>' + txt_list + '<|endoftext|>'),
                                                    truncation=True, max_length=max_length, padding="max_length").values()
        self.input_ids = torch.tensor(self.input_ids)
        self.attn_masks = torch.tensor(self.attn_masks)

    def __len__(self):
        return len(self.input_ids)

    def __getitem__(self, idx):
        return self.input_ids[idx], self.attn_masks[idx]


max_length = 50  # better to had context_length as near as the requested generated article
tokenizer = AutoTokenizer.from_pretrained(FULL_MODEL_NAME, bos_token='<|startoftext|>', eos_token='<|endoftext|>',
                                          pad_token='<|pad|>')

model = GPTNeoForCausalLM.from_pretrained(FULL_MODEL_NAME, pad_token_id=tokenizer.pad_token_id)
model.resize_token_embeddings(len(tokenizer))

descriptions = pd.read_csv('../../data/sport/reporters/20221130-220644_subtitle_content.csv', engine='python')['completion']
dataset = ArticleDataset(descriptions[~descriptions.isna()], tokenizer, max_length=max_length)
train_size = int(0.9 * len(dataset))
train_dataset, val_dataset = random_split(dataset, [train_size, len(dataset) - train_size])
torch.cuda.empty_cache()
model = model.to(torch.device('cuda'))

training_args = TrainingArguments(output_dir='./results',
                                  overwrite_output_dir=True,
                                  num_train_epochs=100,
                                  logging_steps=100,
                                  save_steps=124,
                                  per_device_train_batch_size=4,
                                  per_device_eval_batch_size=4,
                                  save_total_limit=1,
                                  warmup_steps=10,
                                  weight_decay=0.05,
                                  logging_dir='./logs',
                                  do_eval=True)


trainer = Trainer(model=model,
                  args=training_args,
                  train_dataset=train_dataset,
                  eval_dataset=val_dataset,
                  data_collator=lambda data: {'input_ids': torch.stack([f[0] for f in data]),
                                                              'attention_mask': torch.stack([f[1] for f in data]),
                                                              'labels': torch.stack([f[0] for f in data])})
trainer.train()

kwargs = {
    "tags": ["hebrew", "text-generation", "devozs"],
    "finetuned_from": FULL_MODEL_NAME,
    "dataset": constants.NAME,
}
# trainer.push_to_hub(CUSTOM_MODEL_NAME)
tokenizer.push_to_hub(CUSTOM_MODEL_NAME)
model.push_to_hub(CUSTOM_MODEL_NAME, kwargs)

# generated = tokenizer("<|startoftext|>", return_tensors="pt")['input_ids'].cuda()
# sample_outputs = model.generate(generated, do_sample=True, top_k=50, max_length=300, top_p=0.95, temperature=1., num_return_sequences=20)
# for i, sample_output in enumerate(sample_outputs):
#     print(f'{i}: {tokenizer.decode(sample_output, skip_special_tokens=True)}')




#
#
# soccer_ds = load_dataset(constants.NAME)
# tokenizer.pad_token = tokenizer.eos_token
#
#
# def tokenize_function(examples):
#     return tokenizer(examples["article_body"], padding="max_length", truncation=True)
#
#
# tokenized_datasets = soccer_ds.map(tokenize_function, batched=True)
# # model = AutoModelForSequenceClassification.from_pretrained("bert-base-cased", num_labels=5)
# # model = AutoModelForCausalLM.from_pretrained("Norod78/hebrew-gpt_neo-xl", pad_token_id=tokenizer.eos_token_id)
# config = AutoConfig.from_pretrained(
#     BASE_MODEL_NAME,
#     vocab_size=len(tokenizer),
#     n_ctx=context_length,
#     bos_token_id=tokenizer.bos_token_id,
#     eos_token_id=tokenizer.eos_token_id,
# )
#
# # config = AutoConfig.from_pretrained(BASE_MODEL_NAME, vocab_size=len(tokenizer))
# model = GPTNeoForCausalLM.from_pretrained(BASE_MODEL_NAME, config=config, ignore_mismatched_sizes=True).cuda()
# # model = AutoModelForCausalLM.from_pretrained(BASE_MODEL_NAME, num_labels=5)
#
# # device = torch.device("cuda") if torch.cuda.is_available() else torch.device("cpu")
# # model.to(device)
#
# # torch.cuda.tra
# training_args = TrainingArguments(output_dir="test_trainer")
# metric = evaluate.load("accuracy")
#
#
# def compute_metrics(eval_pred):
#     logits, labels = eval_pred
#     predictions = np.argmax(logits, axis=-1)
#     return metric.compute(predictions=predictions, references=labels)
#
#
# tokenized_dataset = soccer_ds.map(tokenize_function, batched=True)
#
# # training_args = TrainingArguments(output_dir="test_trainer", evaluation_strategy="epoch")
# epochs = 1
# lr = 0.00006
# batch_size = 2
#
# data_collator = DataCollatorForLanguageModeling(tokenizer, mlm=False)
# training_args = TrainingArguments(
#     output_dir="test_trainer",
#     # learning_rate=lr,
#     num_train_epochs=epochs,
#     per_device_train_batch_size=batch_size,
#     per_device_eval_batch_size=batch_size,
#     save_total_limit=3,
#     evaluation_strategy="steps",
#     save_strategy="steps",
#     save_steps=20,
#     eval_steps=20,
#     logging_steps=1,
#     eval_accumulation_steps=5,
#     load_best_model_at_end=True,
#     # push_to_hub=True,
#     hub_model_id=BASE_MODEL_NAME + "-soccer-news",
#     hub_strategy="end",
#     no_cuda=False,
# )
#
#
# train_dataset = tokenized_dataset["train"].shuffle(seed=42)
# eval_dataset = tokenized_dataset["validation"].shuffle(seed=42)
# # callback = TrainerCallback(model=model, tokenizer=tokenizer)
# trainer = Trainer(
#     model=model,
#     args=training_args,
#     train_dataset=train_dataset,
#     eval_dataset=eval_dataset,
#     compute_metrics=compute_metrics,
#     data_collator=data_collator
# )
# gc.collect()
# torch.cuda.empty_cache()
#
# training_args = TrainingArguments(output_dir='./results', overwrite_output_dir=True, num_train_epochs=1,
#                                   logging_steps=100, save_steps=124, per_device_train_batch_size=4,
#                                   per_device_eval_batch_size=16, save_total_limit=1, warmup_steps=10, weight_decay=0.05,
#                                   logging_dir='./logs')
#
#
# trainer = Trainer(model=model,  args=training_args, train_dataset=train_dataset,
#         eval_dataset=eval_dataset, data_collator=lambda data: {'input_ids': torch.stack([f[0] for f in data]),
#                                                               'attention_mask': torch.stack([f[1] for f in data]),
#                                                               'labels': torch.stack([f[0] for f in data])})
#
#
# trainer.train()
# kwargs = {
#     "tags": ["hebrew", "text-generation"],
#     "finetuned_from": BASE_MODEL_NAME,
#     "dataset": constants.NAME,
# }
# model.push_to_hub(BASE_MODEL_NAME + "-soccer-news", kwargs)
