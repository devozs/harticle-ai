import requests

create_response = requests.post("https://devozs.com/api/article/מי יהיה שחקן העונה/DORON_BEN_DOR/50", timeout=20).json()
print(create_response)
article_id = create_response.id
if article_id is not None:
    status_response = requests.get(f'https://devozs.com/api/article/{article_id}')
    print(status_response)





