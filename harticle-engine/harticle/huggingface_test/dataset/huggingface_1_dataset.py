from datasets import load_dataset, load_from_disk
from harticle.huggingface_test.common import constants

soccer_ds = load_dataset("csv", data_files="../../data/sport/reporters/subtitle_content.csv")
print(soccer_ds)
print(soccer_ds['train'][:2])

soccer_ds = soccer_ds['train'].shuffle(seed=42)  #.select(range(1000))
soccer_ds = soccer_ds.rename_column(
    original_column_name='prompt',
    new_column_name='article_title'
)
soccer_ds = soccer_ds.rename_column(
    original_column_name='completion',
    new_column_name='article_body'
)
print(soccer_ds)


def compute_article_length(example):
    body_len = 0
    if example['article_body'] is not None:
        body_len = len(example['article_body'].split());
    return {"article_body_length": body_len}


soccer_ds = soccer_ds.map(compute_article_length)
print(soccer_ds)
print(soccer_ds[0])

print(soccer_ds.sort("article_body_length")[:3])
soccer_ds = soccer_ds.filter(lambda x: x["article_body_length"] > 5)
print(soccer_ds.num_rows)

soccer_ds = soccer_ds.filter(lambda x: len(x["article_body"]) > 0)
print(soccer_ds.num_rows)


def remove_repeated_title(example):
    return {"article_title": example["article_title"].replace('&#39', "'").replace('&rsquo', "'")}


def remove_repeated_body(example):
    return {"article_body": example["article_body"].replace('\n', '').replace('&#39', "'").replace('&rsquo', "'")}


def combine_title_and_body(example):
    return {"article_body": example["article_title"] + '\n' + example["article_body"]}


soccer_ds = soccer_ds.map(remove_repeated_title)
soccer_ds = soccer_ds.map(remove_repeated_body)
soccer_ds = soccer_ds.map(combine_title_and_body)
print(soccer_ds[0])

soccer_ds = soccer_ds.train_test_split(train_size=0.9, seed=42)
soccer_ds["validation"] = soccer_ds.pop("test")
print(soccer_ds)

soccer_ds.push_to_hub(constants.NAME)
loaded_ds = load_dataset(constants.NAME)
print(loaded_ds)

