from datasets import load_dataset
from transformers import AutoTokenizer
from harticle.huggingface_test.common import constants

# tokenizer_name = constants.NAME + "_tokenizer"
tokenizer_name = constants.NAME
soccer_ds = load_dataset(constants.NAME)
context_length = 40
pretrained_tokenizer = AutoTokenizer.from_pretrained("Norod78/hebrew-gpt_neo-small")

outputs = pretrained_tokenizer(
    soccer_ds["train"][:2]["article_body"],
    truncation=True,
    max_length=context_length,
    return_overflowing_tokens=False,
    return_length=True
)
print(f"Input Ids length: {len(outputs['input_ids'])}")
print(f"Input chunk length: {len(outputs['length'])}")
print("vocab_size: ", len(pretrained_tokenizer))

txt = "משחק הכדורגל יתקיים בשבת הקרובה בין הקבוצות המובילות של הכדורגל הישראלי"
# txt = "the israeli soccer teams will play between them on saturday"
tokens = pretrained_tokenizer(txt)['input_ids']
print(tokens)

converted = pretrained_tokenizer.convert_ids_to_tokens(tokens)
print(converted)


def get_training_corpus():
    batch_size = 1000
    return (
        soccer_ds['train'][i: i + batch_size]['article_body']
        for i in range(0, len(soccer_ds["train"]), batch_size)
    )


for articles in get_training_corpus():
    print(len(articles))

training_corpus = get_training_corpus()
vocab_size = 52000
tokenizer = pretrained_tokenizer.train_new_from_iterator(training_corpus, vocab_size)
print(tokenizer.eos_token_id)
print(tokenizer.vocab_size)
tokens = tokenizer(txt)['input_ids']
print(len(tokenizer.tokenize(txt)))
print(len(pretrained_tokenizer.tokenize(txt)))

# tokenizer_file_name = "israeli_soccer_news_tokenizer"
# tokenizer.save_pretrained("./" + model_name)
tokenizer.push_to_hub(tokenizer_name)

loaded_tokenizer = AutoTokenizer.from_pretrained(tokenizer_name)
tokens = tokenizer(txt)['input_ids']
print("trained tokenizer: ", tokens)
tokens = loaded_tokenizer(txt)['input_ids']
print("loaded tokenizer: ", tokens)

