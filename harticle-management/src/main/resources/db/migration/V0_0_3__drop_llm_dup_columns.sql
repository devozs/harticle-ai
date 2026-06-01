-- Drop the legacy LLM prompt/completion columns. They were pure duplication:
-- prompt = title + subtitle, completion = an exact copy of content. Consumers
-- now derive the fine-tune framing from title/sub_title/content directly, which
-- roughly halves per-article storage.

alter table scraped_article drop column if exists prompt;
alter table scraped_article drop column if exists completion;
