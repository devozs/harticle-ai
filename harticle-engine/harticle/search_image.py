import os, urllib.request, json # json for pretty output
from serpapi import GoogleSearch


def get_google_image(query):
    params = {
        "api_key": '6633462f84075824cd6c99a837fe0619ffc6565e31e73ad8b6000f94013e8015',
        "engine": "google",
        "q": query,
        "tbm": "isch"
    }

    search = GoogleSearch(params)
    results = search.get_dict()

    # print(json.dumps(results['suggested_searches'], indent=2, ensure_ascii=False))
    # print(json.dumps(results['images_results'], indent=2, ensure_ascii=False))
    image_list = results['images_results']
    image = ""
    if image_list is not None and len(image_list) > 0:
        image = image_list[0]["original"]
    print("selected image:", image)
    return image


    # -----------------------
    # Downloading images

    # for index, image in enumerate(results['images_results']):
    #
    #     print(f'Downloading {index} image...')
    #
    #     opener=urllib.request.build_opener()
    #     opener.addheaders=[('User-Agent','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36 Edge/18.19582')]
    #     urllib.request.install_opener(opener)
    #
    #     urllib.request.urlretrieve(image['original'], f'SerpApi_Images/original_size_img_{index}.jpg')


if __name__ == '__main__':
    get_google_image("דולב חזיזה הבקיע שער")

