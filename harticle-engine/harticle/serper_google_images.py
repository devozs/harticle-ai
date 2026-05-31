import http.client
import json
import random


def image_search(query):
    conn = http.client.HTTPSConnection("google.serper.dev")
    payload = json.dumps({
        "q": query,
        "gl": "il",
        "hl": "iw",
        "autocorrect": True
    })
    headers = {
        'X-API-KEY': 'dc6b11ad6e4769078d913d92962c42f9e9e4817b',
        'Content-Type': 'application/json'
    }
    conn.request("POST", "/images", payload, headers)
    res = conn.getresponse()
    data = res.read()
    print(data.decode("utf-8"))
    return json.loads(data)


def get_google_image(query):
    images = image_search(query)['images']
    ret = list(filter(lambda x: "http:" not in x['imageUrl'] or "facebook" not in x['imageUrl'], images))
    if len(ret) > 0:
        selected = random.choice(ret)
        print(selected)
        return selected['imageUrl']


if __name__ == '__main__':
    get_google_image("דולב חזיזה הבקיע שער")
