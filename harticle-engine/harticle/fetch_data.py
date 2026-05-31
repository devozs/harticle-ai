import requests
import logging
import re
import os
import csv
from os.path import exists
import time

LOGGER = logging.getLogger(__name__)
PAGES = 2000
reporters_base_path = os.getcwd() + '/data/sport/reporters/'
base_folder_exist = os.path.exists(reporters_base_path)
if not base_folder_exist:
    os.makedirs(reporters_base_path)

# one_site_url = "https://www.one.co.il"
site_configuration = [
    # {
    #     "site": "https://www.ynet.co.il",
    #     # "desk_ynet": "/Author/מערכת_אתר_ערוץ_הספורט?Page={}",
    #     "nadav_zanzipar": "/topics/נדב_צנציפר/{}",
    #     "guy_laybe": "/topics/גיא_לייבה/{}",
    # },
    {
        "site": "https://www.sport5.co.il",
        "desk_sport5": "/Author/מערכת_אתר_ערוץ_הספורט?Page={}",
        "moti_psheca": "/Author/מוטי_פשכצקי?Page={}",
        "tomer_levi": "/Author/תומר_לוי?Page={}",
        "igal_goldshtein": "/Author/יגאל_גולדשטיין?Page={}",
        "or_riter": "/Author/אור_ריטר?Page={}",
        "hadar_yacobi": "/Author/הדר_יעקבי?Page={}",
        "yaniv_franco": "/Author/יניב_פרנקו?Page={}",
        "tamir_alyacni": "/Author/תמיר_אלחיאני?Page={}",
        "haim_zlachai": "/Author/חיים_זלקאי?Page={}",
        "oded_kremer": "/Author/עודד_קרמר?Page={}",
        "yoav_modae": "/Author/יואב_מודעי?Page={}",
    },
    {
        "site": "https://sports.walla.co.il",
        "yaniv_tuchman": "/writer/54?page={}",
        "ofir_sahar": "/writer/11?page={}",
        "ron_amikam": "/writer/2089?page={}",
        "shlomo_wies": "/writer/12?page={}",
        "paz_hasdai": "/writer/14?page={}",
        "oren_josipovich": "/writer/17?page={}",
    },
    {
        "site": "https://www.one.co.il",
        "desk_one": "/cat/Articles/MoreArticles.aspx?author=מערכת+ONE&page={}",
        "raz_amir": "/cat/Articles/MoreArticles.aspx?author=רז+אמיר&page={}",
        "doron_ben_dor": "/cat/Articles/MoreArticles.aspx?author=רז+אמיר&page={}",
        "guy_ben_ziv": "/cat/Articles/MoreArticles.aspx?author=גיא+בן+זיו&page={}",
        "asi_maman": "/cat/Articles/MoreArticles.aspx?author=אסי+ממן&page={}",
        "itzik_kalfi": "/cat/Articles/MoreArticles.aspx?author=איציק+כלפי&page={}",
    },
]

header = ['prompt', 'completion', 'site', 'reporter', 'date']

timestr = time.strftime("%Y%m%d-%H%M%S")
common_title_content_file_name = reporters_base_path + timestr +'_title_content.csv'
if exists(common_title_content_file_name):
    os.remove(common_title_content_file_name)
common_title_content_csv_file = open(common_title_content_file_name, 'w')
common_title_content_csv_writer = csv.writer(common_title_content_csv_file)
common_title_content_csv_writer.writerow(header)

common_subtitle_content_file_name = reporters_base_path + timestr + '_subtitle_content.csv'
if exists(common_subtitle_content_file_name):
    os.remove(common_subtitle_content_file_name)
common_subtitle_content_csv_file = open(common_subtitle_content_file_name, 'w')
common_subtitle_content_csv_writer = csv.writer(common_subtitle_content_csv_file)
common_subtitle_content_csv_writer.writerow(header)


def extract_site_name(site_url):
    if "one" in site_url:
        return "one"
    if "walla" in site_url:
        return "walla"
    if "sport5" in site_url:
        return "sport5"
    return ""


def extract_article_list(current_site, reporter_page):
    report_links = []
    if "one" in current_site:
        links = re.compile('(\/Article[^"]+)').findall(reporter_page)
        for link in links:
            if "MoreArticles" not in link:
                report_links.append(link)

    if "walla" in current_site:  # todo: use only regex instead of find / replace
        links = re.compile('(?<=<\/h2><ul>)(.*)(?=<\/ul>)').findall(reporter_page)
        if type(links) is list and len(links) > 0:
            links = re.compile('(https[^"]+)').findall(links[0])
        for link in links:
            if "item" in link and "sports" in link:
                report_links.append(link.replace('https://sports.walla.co.il', ''))
    if "sport5" in current_site:
        links = re.compile('(?<=<h2><a href=")(.*)(?=">)').findall(reporter_page)
        for link in links:
            if "articles" in link:
                report_links.append(link.replace('https://www.sport5.co.il', '').replace('" target="_self', '').replace('" target="_blank', ''))
    return report_links


def extract_article_title(current_site, page_content):
    if "one" in current_site:
        title = re.compile('(?<=article-main-title">)(.*)(?=<\/h1)').findall(page_content)
    if "walla" in current_site:
        title = re.compile('(?<=<h1 class="title   article_speakable">)(.*)(?=<\/h1)').findall(page_content)
    if "sport5" in current_site:
        title = re.compile('(?s)(?<=<h1 class="article-main">)(.+?)(?=<\/h1>)').findall(page_content)
    if isinstance(title, list):
        if len(title) > 0:
            title = title[0]
        else:
            return None
    title = clear_characters(title, True)
    return title


def extract_article_subtitle(current_site, page_content):
    if "one" in current_site:
        subtitle = re.compile('(?<=article-sub-title">)(.*)(?=<\/h2)').findall(article_page_content)
    if "walla" in current_site:
        subtitle = re.compile('(?<=<p class="subtitle   article_speakable">)(.*)(?=<\/p)').findall(page_content)
    if "sport5" in current_site:
        subtitle = re.compile('(?s)(?<=<h2 class="article-sub-main">)(.+?)(?=<\/h2>)').findall(page_content)
    if isinstance(subtitle, list):
        if len(subtitle) > 0:
            subtitle = subtitle[0]
    subtitle = clear_characters(subtitle, True)
    return subtitle


def extract_article_paragraph(current_site, page_content):
    if "one" in current_site:
        paragraph_list = re.compile('([^<p>"]+)<\/p>').findall(page_content)
    if "walla" in current_site:
        paragraph_list = re.compile('([^<p>"]+)<\/p>').findall(page_content)
    if "sport5" in current_site:
        paragraph_list = re.compile('([^<P>"]+)<\/P>').findall(page_content)
    content = ''
    for p in paragraph_list:
        if len(str(p)) > 1:
            content += p
            # content += p + '\n'
    content = clear_characters(content, False)
    return content


def extract_date(current_site, page_content):
    date = ""
    if "one" in current_site:
        date = re.compile('(?<=datePublished">)(.*)(?= \d)').findall(article_page_content)
    if "walla" in current_site:
        date = re.compile('(?<=<div class="date">)(.*)(?=<\/div><time)').findall(page_content)
    if "sport5" in current_site:
        date = re.compile('(?<=&nbsp;&nbsp;)(.*)(?= -)').findall(page_content)
    if date is not None and type(date) is list and len(date) > 0:
        date = date[0].split(" ")[0].replace('"', '').split(" ")[0]
    return date


def extract_reporter(current_site, page_content):
    name = ""
    if "one" in current_site:
        name = re.compile('(?<= \/>\n)(.*)(?=<\/a>)').findall(article_page_content)
    if "walla" in current_site:
        name = re.compile('(?<="itemAuthor":)(.*)(?=,"itemAu)').findall(page_content)
    if "sport5" in current_site:
        name = re.compile('(?<=ContentPlaceHolder1_ancWriter">)(.*)(?=<\/a>)').findall(page_content)
    if name is not None and type(name) is list and len(name) > 0:
        name = clear_characters(name[0].replace('"', ''), True)
    return name


def clear_characters(text, remove_new_line):
    text = text.replace('&ldquo;', '"').replace('&rdquo;', '"').replace('&ndash;', '-').replace('&quot;', '"').replace(
        ';', '').replace('</p>', '').replace('<STRONG>', '').replace('</STRONG>', '').replace('&nbsp', ' ').replace(
        '&#x27', '').replace('&#39', "'").replace('&rsquo', "'")
    if remove_new_line:
        text = text.replace('\n', '').replace('\r', '').strip()
    return text


article_url_history = []
for site in site_configuration:
    for reporter in site:
        if reporter == 'site':
            continue
        one_site_url = site['site']
        site_name = extract_site_name(one_site_url)
        print("processing reporter " + reporter)
        reporter_folder = reporters_base_path + reporter
        reporter_folder_exist = os.path.exists(reporter_folder)
        if not reporter_folder_exist:
            os.makedirs(reporter_folder)

        title_content_file_name = reporter_folder + "/" + timestr + '_title_content.csv'
        if exists(title_content_file_name):
            os.remove(title_content_file_name)
        title_content_csv_file = open(title_content_file_name, 'w')
        title_content_csv_writer = csv.writer(title_content_csv_file)
        title_content_csv_writer.writerow(header)

        subtitle_content_file_name = reporter_folder + "/" + timestr + '_subtitle_content.csv'
        if exists(subtitle_content_file_name):
            os.remove(subtitle_content_file_name)
        subtitle_content_csv_file = open(subtitle_content_file_name, 'w')
        subtitle_content_csv_writer = csv.writer(subtitle_content_csv_file)
        subtitle_content_csv_writer.writerow(header)

        i = 0
        for page_no in range(1, PAGES):
            skipped_urls_per_page = 0
            print("processing page no. " + str(page_no) + "/" + str(PAGES))
            reporter_page_url = one_site_url + str(site[reporter]).format(page_no)
            print(reporter_page_url)
            try:
                reporter_page_content = requests.get(reporter_page_url, timeout=20).text
            except Exception:
                print("Timeout has been raised, skipping (" + str(i) + ") " + reporter_page_url)
                continue
            # print(reporter_page_content)
            article_urls = extract_article_list(one_site_url, reporter_page_content)
            for url in article_urls:
                print("processing article no. " + str(i) + "/" + str(len(article_urls)))
                article_url = one_site_url + url
                if article_url in article_url_history:
                    print("skipping (" + str(i) + ") " + article_url + ", title already exists")
                    skipped_urls_per_page = skipped_urls_per_page+1
                    continue
                article_url_history.append(article_url)
                try:
                    article_page_content = ""
                    try:
                        article_page_content = requests.get(article_url, timeout=20).text
                        file_name = reporter_folder + '/' + str(i) + '.txt'
                        article_title = extract_article_title(one_site_url, article_page_content)
                    except Exception:
                        print("Timeout has been raised, skipping (" + str(i) + ") " + article_url)
                        continue
                    if article_title is None:
                        print("skipping (" + str(i) + ") " + article_url + " due to timeout")
                        continue
                    print(article_title)
                    # article_title = '\n\n###\n\n' + article_title  # openai prompt suggestion
                    article_subtitle = extract_article_subtitle(one_site_url, article_page_content)
                    # article_subtitle = '\n\n###\n\n' + article_title + '\n' + article_subtitle  # openai prompt suggestion
                    article_subtitle = article_title + '.\n' + article_subtitle + '.'
                    article_content = extract_article_paragraph(one_site_url, article_page_content)
                    # article_content = " " + article_content + '\n'  # openai completion suggestion
                    reporter_name = extract_reporter(one_site_url, article_page_content)
                    article_date = extract_date(one_site_url, article_page_content)
                    title_content_data = [article_title, article_content, site_name, reporter_name, article_date]
                    title_content_csv_writer.writerow(title_content_data)
                    common_title_content_csv_writer.writerow(title_content_data)

                    subtitle_content_data = [article_subtitle, article_content, site_name, reporter_name, article_date]
                    subtitle_content_csv_writer.writerow(subtitle_content_data)
                    common_subtitle_content_csv_writer.writerow(subtitle_content_data)

                    with open(file_name, 'w') as f:
                        f.write(article_content)
                    f.close()
                except Exception as e:
                    print("failed to fetch (" + str(i) + ") " + article_url)
                    print(e)
                i = i + 1
            if len(article_urls) == 0 or skipped_urls_per_page == len(article_urls):
                print("skipping reporter " + reporter_page_url + " since there are no more articles")
                break
        title_content_csv_file.close()
        # subprocess.run(["openai", "tools", "fine_tunes.prepare_data", "-f", title_content_file_name])
        # cmd = "openai tools fine_tunes.prepare_data -f " + title_content_file_name
        # p = subprocess.Popen(cmd, stdout=subprocess.PIPE, shell=True)
        # print(p.communicate())
        subtitle_content_csv_file.close()

common_title_content_csv_file.close()
common_subtitle_content_csv_file.close()
