<template>

    <Head>
        <Title>{{ article.title }}</Title>
        <Meta name="description" :content="article.subTitle">
        </Meta>
        <Meta name="twitter:card" content="summary_large_image">
        </Meta>
        <Meta name="twitter:site" content="@OziShemesh">
        </Meta>
        <Meta name="twitter:title" :content="article.title">
        </Meta>
        <Meta name="twitter:description" :content="article.subTitle">
        </Meta>
        <Meta name="twitter:image" :content="article.image + '?' + new Date().getTime()">
        </Meta>
        <Meta name="twitter:image:alt" content="harticle.devozs.com">
        </Meta>
        <Meta name="og:title" :content="article.title">
        </Meta>
        <Meta name="og:description" :content="article.subTitle">
        </Meta>
        <Meta name="og:image" :content="article.image + '?' + new Date().getTime()">
        </Meta>
        <Meta name="og:url" :content="'https://harticle.devozs.com/harticles/' + route.params.id">
        </Meta>
        <Meta name="og:site_name" content="Harticle">
        </Meta>
        <Meta name="og:type" content="summary_large_image">
        </Meta>
    </Head>

    <section class="container mx-auto lg:mt-3 transform duration-500 p-5 mt-1">


        <article :dir="dir" itemscope="" itemtype="http://schema.org/Article" tabindex="-1">
            <header tabindex="-1">
                <h1 itemprop="headline" class="article-main-title" tabindex="-1" data-font-size="31"
                    data-font-size-type="px">{{ article?.title }}</h1>
                <h2 itemprop="headline" class="article-sub-title" tabindex="-1" data-font-size="22"
                    data-font-size-type="px">{{ article?.subTitle }}</h2>
            </header>
            <div class="h10" id="temp-splitter" tabindex="-1" data-font-size="10" data-font-size-type="px"
                data-line-height="10px"></div>
            <div class="article-body-container" tabindex="-1" data-font-size="10" data-font-size-type="px">
                <div class="article-credit" tabindex="-1" data-font-size="16" data-font-size-type="px">
                    <div class="pc-jump-2-comments-container" style="float: left;" tabindex="-1" data-font-size="16"
                        data-font-size-type="px">
                    </div>
                    <div class="article-social-buttons" tabindex="-1" data-font-size="16" data-font-size-type="px">
                        <div class="share-buttons"
                            id="share-panel-_ctl0__ctl0_ContentHolder_Body_ContentHolder_Body_ucShareButtons"
                            tabindex="-1" data-font-size="16" data-font-size-type="px">



                            <a onclick="gaTrackEvent('article', 'share-bar-twitter');"
                                :href="'http://twitter.com/intent/tweet?text=' + article?.title + '&amp;url=https://harticle.devozs.com/harticles/two/' + route.params.id"
                                target="_blank" tabindex="10000" data-font-size="16" data-font-size-type="px">
                                <img loading="lazy"
                                    src="https://images.one.co.il/images/msites/2022/03/02/f1122ca799164f8406265daf2332cd5a.png"
                                    alt="Harticle" tabindex="-1"></a>


                            <!-- <a href="#"
                                onclick="gaTrackEvent('article', 'share-bar-facebook'); OpenWindow('http://www.facebook.com/sharer.php?u=https%3a%2f%2fwww.one.co.il%2fArticle%2f423669.html', 'facebookshare', 600, 300, 0, 0, 0, 0, 0, 0);"
                                title="שתף בפייסבוק" tabindex="10000" data-font-size="16" data-font-size-type="px"><img
                                    loading="lazy"
                                    data-src="https://images.one.co.il/images/msites/2015/02/24/1345/fb.png"
                                    src="https://images.one.co.il/images/msites/2015/02/24/1345/fb.png"
                                    alt="ONE - מספר 1 בספורט" tabindex="-1"></a> -->
                        </div>
                    </div>
                    <a rel="author" class="name" tabindex="10000" data-font-size="16" data-font-size-type="px">
                        <img loading="lazy" id="_ctl0__ctl0_ContentHolder_Body_ContentHolder_Body_imgAuthorPhoto"
                            data-src="https://images.one.co.il/images/msites/2020/04/26/c34a6b1cadf92230d8ec4563bb56144c.png"
                            src="https://images.one.co.il/images/msites/2020/04/26/c34a6b1cadf92230d8ec4563bb56144c.png"
                            alt="TWO - מספר 2 בספורט" tabindex="-1">
                        מערכת TWO</a>
                    | <time datetime="2022-11-22" :title="formatDate(article?.updatedAt)"
                        itemprop="dateCreated datePublished" tabindex="-1">{{
                                formatDate(article?.updatedAt)
                        }}</time>
                </div>
                <figure class="article-image article-main-image article-image-1636572" data-type="image"
                    data-id="1636572" tabindex="-1">
                    <img loading="lazy" :src="article?.image + '?width=700'" alt="ליאו מסי (רויטרס)" tabindex="-1"><br
                        tabindex="-1">
                    ליאו מסי (רויטרס)
                </figure>
                <!-- <section itemprop="articleBody" tabindex="-1">
                    <p tabindex="-1" data-font-size="20" data-font-size-type="px" data-line-height="29px">ההפסד
                        הסנסציוני של
                        ארגנטינה לערב הסעודית מצטרף ללא מעט סנסציות שכבר קיבלנו בעבר בשלב הבתים של גביע העולם. החל
                        מהפסדים
                        של אלופות עולם מכהנות, כולל האלביסלסטה בעצמם ב-1990. זה הזמן להיזכר בכל ההפתעות.</p>
                    <h3 class="blue" tabindex="-1" data-font-size="21" data-font-size-type="px">2002, צרפת –&nbsp;סנגל
                        1:0
                    </h3>
                    <div class="OUTBRAIN" data-src="//www.one.co.il/Article/423669.html" data-widget-id="AR_2"
                        data-ob-template="One" tabindex="-1" data-font-size="10" data-font-size-type="px"></div>

                    <p tabindex="-1" data-font-size="20" data-font-size-type="px" data-line-height="29px">זינדין זידאן
                        אמנם
                        היה פצוע, אבל על הנייר הטריקולור היו עדיפים אפילו על הנבחרת שהניפה ב-1998 מבחינת איכות הסגל,
                        כשהם גם
                        הגיעו כאלופי אירופה המכהנים. הנבחרת האפריקאית הופיעה לראשונה במונדיאל והפתיעה את כולם, כשפאפא
                        בובה
                        דיופ הכריע (30).</p>
                    <h3 class="blue" tabindex="-1" data-font-size="21" data-font-size-type="px">1990, ארגנטינה
                        –&nbsp;קמרון
                        1:0</h3>
                    <p tabindex="-1" data-font-size="20" data-font-size-type="px" data-line-height="29px">העולם ציפה
                        שדייגו
                        מראדונה וחבריו יספקו הצגה במשחק הפתיחה במילאנו, כאשר הנבחרת האפריקאית הייתה בהופעתה השנייה בלבד
                        בגביע העולם ונהנתה משער של פרנסוה עומאם-ביאיק (67). האלביסלסטה הגיעו כאלופה המכהנת ואכזבו
                        בפתיחה, אך
                        האלביסלסטה הצליחו להתאושש והמשיכו עד לגמר.</p>
                    <figure class="article-image landscape align-center" data-id="1593220" data-type="image"
                        tabindex="-1">
                        <img loading="lazy" class="article-image-inner" alt="דייגו מראדונה (רויטרס)"
                            src="https://photo.one.co.il/Image/GG/5,1/1593220.webp?width=700" tabindex="-1">
                        <figurecaption tabindex="-1">דייגו מראדונה (רויטרס)</figurecaption>
                    </figure>
                    <h3 class="blue" tabindex="-1" data-font-size="21" data-font-size-type="px">2014, איטליה
                        –&nbsp;קוסטה
                        ריקה 1:0</h3>
                    <p tabindex="-1" data-font-size="20" data-font-size-type="px" data-line-height="29px">אף אחד לא ציפה
                        מהקוסטה ריקנים לעשות משהו בבית, אבל אחרי שניצחו את אורוגוואי, הם הצליחו להראות מול האזורי שזה לא
                        הגיע במקרה. איטליה עפה בבתים בסופו של דבר לאחר אחד מההפסדים המבינים ביותר שלה. הגיבור היה השוער
                        קיילוס נבאס, אך בריאן רואיס הכריע בדקה ה-44.</p>
                    <h3 class="blue" tabindex="-1" data-font-size="21" data-font-size-type="px">1998, ברזיל
                        –&nbsp;נורבגיה
                        2:1</h3>
                    <p tabindex="-1" data-font-size="20" data-font-size-type="px" data-line-height="29px">הסלסאו ניצחו
                        בצמד
                        המשחקים הראשונים שלהם בבית, אבל הסקנדינבים הצליחו להמם ולעשות סנסציה במשחקם האחרון בטורניר עד
                        היום.
                        בבטו כבש ראשון, אבל טורה אנדרה פלו (83) ושטיל רקדאל בפנדל בדקה ה-89 השלימו מהפך.&nbsp;ברזיל
                        הצליחה
                        להעפיל עד לגמר באותו הטורניר, אז נכנעה למארחת צרפת.</p>
                    <div class="article-vote" data-type="vote" data-vote-id="9190"
                        data-vote-image="https://images.one.co.il/images/d/dmain/361_185/gg1636576.jpg"
                        data-vote-question="האם ההפסד של ארגנטינה לערב הסעודית הוא הסנסציה הכי גדולה בתולדות שלב הבתים של המונדיאל?"
                        tabindex="-1" data-font-size="10" data-font-size-type="px">
                        <div class="vote">
                            <div class="vote-image-container">
                                <img class="vote-image" alt="Alternate Text"
                                    src="https://photo.one.co.il/Image/GG/7,1/1636576.webp">
                                <span class="vote-image-credit">רויטרס</span>
                            </div>
                            <div class="question">האם ההפסד של ארגנטינה לערב הסעודית הוא הסנסציה הכי גדולה בתולדות שלב
                                הבתים
                                של המונדיאל?</div>
                            <div class="answers">

                                <button type="button" class="answer">
                                    <div class="percentage"></div>
                                    <div class="text-container">
                                        <div class="bar-container">
                                            <div class="bar"></div>
                                        </div>
                                        <span class="text">כן</span>
                                    </div>

                                </button><button type="button" class="answer">
                                    <div class="percentage"></div>
                                    <div class="text-container">
                                        <div class="bar-container">
                                            <div class="bar"></div>
                                        </div>
                                        <span class="text">לא, הפסד צרפת ב-2002</span>
                                    </div>

                                </button><button type="button" class="answer">
                                    <div class="percentage"></div>
                                    <div class="text-container">
                                        <div class="bar-container">
                                            <div class="bar"></div>
                                        </div>
                                        <span class="text">לא, הפסד איטליה ב-2014</span>
                                    </div>

                                </button><button type="button" class="answer">
                                    <div class="percentage"></div>
                                    <div class="text-container">
                                        <div class="bar-container">
                                            <div class="bar"></div>
                                        </div>
                                        <span class="text">לא, הפסד ברזיל ב-1998</span>
                                    </div>

                                </button><button type="button" class="answer">
                                    <div class="percentage"></div>
                                    <div class="text-container">
                                        <div class="bar-container">
                                            <div class="bar"></div>
                                        </div>
                                        <span class="text">לא, הפסד ארגנטינה ב-1990</span>
                                    </div>

                                </button>
                            </div>

                        </div>
                    </div>

                </section> -->
            </div>
        </article>
    </section>
</template>

<script setup>
import { storeToRefs } from 'pinia'
import VueSocialSharing from 'vue-social-sharing'
import dayjs from 'dayjs'

const { locale } = useI18n()
const dir = computed(() => (locale.value === 'en' ? 'ltr' : 'rtl'))
const route = useRoute()
const config = useRuntimeConfig()
const formatDate = (value) => dayjs(value).format('DD/MM/YYYY HH:mm:ss')
const { getById, fetchOne, select } = useHarticleStore()
const { selected, articles } = storeToRefs(useHarticleStore())

console.log("missing selected")
const { data } = useFetch(`${config.public.apiBase}/article/${route.params.id}`)
const article = data
    // fetchOne(route.params.id)
// }

</script>

<style type="text/css" scoped>
h1.article-main-title {
    direction: rtl;
    text-align: right;
    color: #BF0000;
    margin: -0.7rem 0 0 0;
    font-size: 3.1rem;
    font-weight: bold;
}

h2.article-sub-title {
    direction: rtl;
    text-align: right;
    color: #7F7F7F;
    margin: 0;
    font-size: 2.2rem;
    font-weight: bold;
}

div.article-credit {
    width: 100%;
    font-size: 1.6rem;
    direction: rtl;
    text-align: right;
    color: #7f7f7f;
    line-height: 3.6rem;
}

div.article-credit img {
    width: 3.6rem;
    height: 3.6rem;
    border-radius: 1.8rem;
    margin-left: 1rem;
    float: right;
}

div.article-credit a.name {
    display: inline-block;
    color: #7f7f7f;
    direction: rtl;
}

h1.article-main-title,
h2.article-sub-title {
    margin-bottom: 1rem;
}

div.article-credit,
div.one-aloud-player {
    margin-bottom: 3rem;
}

h3 {
    margin: 0;
    font-size: 2.1rem;
}

figure.article-image {
    text-align: center;
    font-size: 1.6rem;
    color: #666;
    margin: 0;
    padding: 0;
}

figure.article-image img {
    width: 100%;
    max-width: 70rem !important;
}

figure.portrait img {
    display: block;
    margin: auto;
    width: auto !important;
}

figure.article-video {
    text-align: center;
    font-size: 1.6rem;
    color: #666;
    margin: 0;
    padding: 0;
    width: 100%;
}

.article-body-container p {
    text-align: justify;
    font-size: 2rem;
    margin: 0;
    line-height: 2.9rem;
}

.article-body-container p a {
    font-weight: bold;
}

.twitter-tweet-rendered {
    margin-left: auto;
    margin-right: auto;
}

.one-podcast-embed {
    width: 100%;
    margin: 2rem 0;
}

.one-podcast-embed a {
    display: block;
    text-align: center;
    background: #f5f5f5;
    margin-bottom: 5px;
}

.pc-jump-2-comments-container a.jump-2-comments {
    display: inline-block;
    background: #33499c;
    color: white;
    text-align: center;
    padding: 0 0.5rem;
    margin: 0.5rem 0 0.5rem 0;
    float: left;
    direction: rtl;
    font-size: 1.4rem;
    font-weight: bold;
    line-height: 2.2rem;
}

.pc-jump-2-comments-container a.jump-2-comments {
    float: none;
    clear: both;
}

.pc-jump-2-comments-container a.jump-2-comments img {
    width: 1.7rem;
}

.pc-jump-2-comments-container a.jump-2-comments span.number-of-comments-placeholder {
    font-size: 1.5rem;
    font-weight: bold;
}

.add-comments-bubble-icon:before {
    width: 1.9rem;
    height: 1.65rem;
    display: inline-block;
    content: '';
    background: url('https://images.one.co.il/images/msites/2022/02/03/balonit.png');
    background-size: contain;
    background-size: contain;
    background-repeat: no-repeat;
    background-position-x: center;
    background-position-y: bottom;
    margin: 0.4rem 0 0 0.5rem;
    float: right;
}

.pc-jump-2-comments-container a.jump-2-comments span.number-of-comments-placeholder span {
    font-size: 1.2rem;
}

div.article-social-buttons {
    width: 15.5rem;
    float: left;
    margin: 0.55rem 1rem;
}

div.article-social-buttons div.share-buttons a img {
    width: 2.5rem !important;
    height: 2.5rem !important;
}

.jump-2-comments-from-artcile-bottom {
    background: #f3f8f9;
    width: calc(100% - 4rem);
    padding: 0 2rem;
    padding: 0.5rem 2rem;
    display: flex;
    justify-content: space-between;
    font-weight: bold;
    font-size: 1.8rem;
    margin: 2rem 0 0 0;
}

.jump-2-comments-from-artcile-bottom span.comments-message-left,
.jump-2-comments-from-artcile-bottom span.comments-message-right {
    color: black;
    display: inline-block;
    padding: 1.7rem 0;
}

.jump-2-comments-from-artcile-bottom span.comments-message-left {
    margin-right: 5rem !important;
}

.jump-2-comments-from-artcile-bottom span a {
    text-decoration: underline;
    color: black;
}

.jump-2-comments-from-artcile-bottom a.jump-to-comments-button {
    display: inline-block;
    background: #33499c;
    color: white;
    text-align: center;
    padding: 0.5rem 1rem;
    height: 2.1rem;
    margin: 1.2rem 0;
}

.article-body-container p,
.article-body-container h3,
.article-body-container figure {
    margin: 1rem 0;
    display: inline-block;
    width: 100%;
}

:focus,
*:focus {
    outline: 0 !important;
    outline: none !important;
}

a.one-accessibility:focus,
input.one-accessibility:focus,
button.one-accessibility:focus,
textarea.one-accessibility:focus {
    outline-width: 3px !important;
    outline-color: #ffd800 !important;
    outline-style: solid !important;
    background-color: #ff0 !important;
    color: #600 !important;
}

.one-main-container {
    width: 124rem !important;
}

.top-article-container {
    float: right;
}

.one-hp-left-container-column-articles {
    float: right;
    width: calc(100% - 36.2rem);
}
</style>