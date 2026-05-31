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
        <div id="wrapper">
            <div class="w1">



                <div id="main" class="main-inner">


                    <div id="twocolumns">
                        <!-- Left -->

                        <div class="main-block">

                            <div class="content-block content-block-article">
                                <div class="content-holder">

                                    <h1 class="article-main">{{ article?.title }}</h1>
                                    <h2 class="article-sub-main">{{ article?.subTitle }}</h2>
                                    <div class="author-img-holder">
                                        <img src="https://www.sport5.co.il/Sip_Storage/FILES/2/Black137X104/1130372.jpg"
                                            alt="מערכת SPORT6" class="author-img">
                                    </div>
                                    <span class="hint"><a href="https://harticle.devozs.com"
                                            id="ContentPlaceHolder1_ancWriter">SPORT6 מערכת</a>&nbsp;&nbsp;{{
                                                    formatDate(article?.updatedAt)
                                            }}


                                        <div class="writer-date">



                                            <!-- talkbacks Articles -->


                                            <div class="talkbacks-cont talkbacks-svg">
                                                <svg x="0px" width="28px"
                                                    class="talkbacksHomePage talkbacksHomePage-red" height="20px"
                                                    viewBox="0 0 96.5 56.4" style=""
                                                    xmlns:xlink="http://www.w3.org/1999/xlink" y="0px"
                                                    xmlns="http://www.w3.org/2000/svg" version="1.1">
                                                    <path d="M70.7,56.4c-1.8,0-3.4-1.2-3.9-3.1c-1.3-5.4-6.9-6-9.2-6H16.1C7.2,47.3,0,40.1,0,31.3V16.1C0,7.2,7.2,0,16.1,0h64.3
	        c8.9,0,16.1,7.2,16.1,16.1v15.2c0,5.1-2.3,9.7-6.3,12.8c-1.8,1.3-4.3,1-5.6-0.7s-1-4.3,0.7-5.6c2-1.5,3.2-3.9,3.2-6.4V16.1
	        c0-4.5-3.6-8.1-8.1-8.1H16.1C11.6,8,8,11.6,8,16.1v15.2c0,4.5,3.6,8.1,8.1,8.1h41.6c6.8,0,14.8,3.2,17,12.1c0.5,2.1-0.8,4.3-2.9,4.8
	        C71.4,56.4,71,56.4,70.7,56.4z" fill="red"></path>

                                                </svg><span data-post-id="420919"
                                                    class=" spot-im-replies-count talkbacksText"
                                                    real-time="true"></span>

                                            </div>


                                        </div>


                                        <!-- ShareBar Start -->
                                        <div class="share_icon">
                                            <div class="share_bar">
                                                <ul>
                                                    <li class="facebook">
                                                        <!-- <a href="#"
                                                            onclick="gaTrackEvent('article', 'share-bar-facebook'); OpenWindow('http://www.facebook.com/sharer.php?u=https%3a%2f%2fwww.one.co.il%2fArticle%2f423669.html', 'facebookshare', 600, 300, 0, 0, 0, 0, 0, 0);"
                                                            title="שתף בפייסבוק" tabindex="10000" data-font-size="16"
                                                            data-font-size-type="px"><img loading="lazy"
                                                                data-src="https://images.one.co.il/images/msites/2015/02/24/1345/fb.png"
                                                                src="https://images.one.co.il/images/msites/2015/02/24/1345/fb.png"
                                                                alt="ONE - מספר 1 בספורט" tabindex="-1"></a> -->

                                                    </li>
                                                    <li class="twitter">
                                                        <a onclick="gaTrackEvent('article', 'share-bar-twitter');"
                                                            :href="'http://twitter.com/intent/tweet?text=' + article?.title + '&amp;url=https://harticle.devozs.com/harticles/sport6/' + route.params.id"
                                                            target="_blank" tabindex="10000" data-font-size="16"
                                                            data-font-size-type="px">
                                                            <img loading="lazy"
                                                                src="https://images.one.co.il/images/msites/2022/03/02/f1122ca799164f8406265daf2332cd5a.png"
                                                                alt="Harticle" tabindex="-1"></a>

                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                        <!-- ShareBar End -->

                                    </span>

                                    <div class="video-holder img_videobackground">
                                        <img :src="article?.image" title="" alt="" width="600px" height="450px">
                                    </div>

                                </div>
                            </div>

                        </div>

                        <!-- <div class="main-container">
                            <div class="article-content" id="content">
                                <div id="find-article-content">
                                    <span><em>באדיבות כאן 11 - המשדר הרשמי של מונדיאל 2022. שידורים חיים, תקצירים,
                                            סטטיסטיקות ועוד מחכים
                                            לכם בעמוד המונדיאל של כאן</em>
                                        <blockquote class="twitter-tweet"
                                            data-twitter-extracted-i1669230983501556396="true">
                                            <p lang="iw" dir="rtl">צפו תקציר המשחק ובשחקנים הספרדים של לואיס אנריקה
                                                מביסים 0-7 את קוסטה
                                                ריקה <a href="https://t.co/H27paUadZH">pic.twitter.com/H27paUadZH</a>
                                            </p>— כאן (@kann)
                                            <a
                                                href="https://twitter.com/kann/status/1595491883802759168?ref_src=twsrc%5Etfw">November
                                                23, 2022</a>
                                        </blockquote>


                                        <p>התמונה בבית 5 מתחילה להתברר, אחרי מחזור ראשון שבו נבחרת ספרד דרסה 0:7 את
                                            קוסטה ריקה, בעוד
                                            גרמניה נחלה הפסד מפתיע, 2:1 ליפן, וניצחון של "לה רוחה" ביום ראשון על על
                                            המנשאפט עשוי להבטיח
                                            לה כמעט בוודאות&nbsp;את המקום בשמינית הגמר, ולקרב אותה מאוד להבטחת המקום
                                            הראשון בבית, לקראת
                                            המחזור האחרון מול יפן.</p>
                                        <p>מנגד, כמו ארגנטינה אתמול, גם גרמניה הסתבכה בגדול ואסור לה למעוד אם היא רוצה
                                            לא להיות מודחת
                                            מונדיאל שני ברצף בשלב הבתים. הקלישאה אומרת שאסור להפסיד משחק ראשון במונדיאל,
                                            וזה כמובן נכון.
                                            ביום ראשון היא תפגוש כאמור את ספרד, ולאחר מכן&nbsp;את קוסטה ריקה, שהראתה למה
                                            היא נחשבת לחלשה
                                            בבית. אך אם וכאשר יפן, שנראתה מצוין תנצח אותה, אז לגרמניה לא תהיה ברירה אלא
                                            לנצח את ספרד,
                                            במה שמסבך משמעותית את הבית הזה.<br><br>הסטטיסטיקה אומרת שמאז שונה הפורמט של
                                            המונדיאל ב-1998
                                            (לשמונה בתים של 4 נבחרות, כאשר שתיים עולות לשמינית), רק 11 אחוז מהנבחרות
                                            שהפסידו במשחק
                                            הפתיחה שלהן, גם עברו שלב. ספרד היא היוצאת מהכלל והיחידה למעשה שנוצחה במשחק
                                            הראשון (1:0
                                            לשוויץ) וגם זכתה במונדיאל ב-2010.</p>
                                        <p>לעומת זאת, במקרה של תוצאת תיקו במחזור הראשון, משתפרים האחוזים ועולים ל-59
                                            באשר לסיכויי העליה
                                            לשלב הבא. הנבחרות שמנצחות במחזור הפתיחה נהנות מסיכוי של 84 אחוזים להעפיל
                                            לנוקאאוט, לתשומת לב
                                            כולם.</p>
                                        <p><strong>מה הם שוברי השוויון בשלב הבתים?</strong><br>אם שתי נבחרות או יותר
                                            סיימו את שלב הבתים
                                            עם אותו מספר נקודות, מה שיכריע הוא: 1. הפרש שערים כללי טוב יותר, 2. מספר
                                            שערים רב יותר
                                            שנכבשו בכל המשחקים.</p>
                                        <p>אם שתי נבחרות או יותר יהיו שוות גם אז, מה שיכריע הוא:<br>1. מספר הנקודות
                                            שהשיגו הנבחרות
                                            במפגשים הפנימיים ביניהן.<br>2. הפרש שערים בין הנבחרות במפגשים
                                            הפנימיים.<br>3. יותר שערים
                                            שהובקעו בין הנבחרות במפגשים הפנימיים.<br>4. מאזן נקי יותר של כרטיסים צהובים
                                            ואדומים. כל צהוב
                                            שווה מינוס נקודה, כל הרחקה בשני כרטיסים שווה מינוס 3 נקודות, הרחקה ישירה
                                            שווה מינוס 4 נקודות
                                            ואילו צהוב + אדום ישיר שווה מינוס 5 נקודות.<br>5. פיפ"א תערוך הגרלה בין
                                            הנבחרות בשובר
                                            השוויון האחרון. במונדיאל הקודם, יפן הקדימה את סנגל ועלתה לשמינית גמר בגלל
                                            מאזן נקי יותר של
                                            צהובים ואדומים.</p>
                                    </span>

                                </div>

                            </div>
                        </div> -->


                    </div>


                </div>

            </div>
        </div>

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
@charset "UTF-8";


body {
    margin: 0;
    color: #000;
    font: 15px/22px Arial, Helvetica, sans-serif;
    background-color: #fff
}

body.color-alt-bluetravel.article-page,
body.color-alt-blue.article-page,
body.color-alt-red.article-page,
body.color-alt-grey.article-page,
body.color-alt-black.article-page,
body.color-alt-green.article-page,
body.color-alt-rio_red.article-page,
body.color-alt-rio_orange.article-page,
body.color-alt-rio_navy.article-page,
body.color-alt-rio_light_green.article-page,
body.color-alt-peach.article-page,
body.color-alt-lightblue.article-page {
    color: #000;
    font: 15px/22px Arial, Helvetica, sans-serif
}

img {
    border-style: none
}

a {
    color: #1b1b1b;
    text-decoration: none
}

a:hover {
    color: #014bad
}

:focus {
    outline: none !important
}

input,
textarea,
select {
    font: 100% Arial, Helvetica, sans-serif;
    vertical-align: middle;
    color: #000
}

figure,
figcaption,
header,
footer,
hgroup,
nav,
menu,
section,
article,
aside {
    display: block
}

input[type="submit"]::-moz-focus-inner,
input[type="button"]::-moz-focus-inner {
    border: 0
}

form,
fieldset {
    margin: 0;
    padding: 0;
    border-style: none;
    overflow: hidden
}

q {
    quotes: none
}

q:before {
    content: ''
}

q:after {
    content: ''
}

.clear {
    clear: both
}

.pos-rel {
    position: relative
}

.o_hidden {
    overflow: hidden
}

.dir_ltr {
    direction: ltr
}

.f_right {
    float: right
}

.f_left {
    float: left
}

.no_margin {
    margin: 0 !important
}

.d_none {
    display: none !important
}

#wrapper {
    min-width: 975px;
    width: 100%;
    position: relative;
    overflow: hidden;
    background-color: white
}

body.with-bg #wrapper {
    background: 0
}

.w1 {
    width: 970px;
    margin: 0 auto;
    direction: rtl
}

@media only screen and (max-width :768px) {
    .w1 {
        width: 100%;
        margin: 0 auto;
        direction: rtl
    }

    #wrapper {
        min-width: 100%;
        width: 100%;
        position: relative;
        overflow: hidden;
        background: url(images/bg-page2.jpg) 500px 0 repeat-x
    }
}

body.with-bg .w1 {
    background: #fff;
    width: 970px;
    padding: 0 10px;
    overflow-x: hidden
}

body.with-bg.special-league .w1 {
    background: 0;
    overflow-x: initial;
    position: relative
}

body.with-bg.special-league .w1:before {
    content: '';
    position: absolute;
    display: block;
    right: calc(50% - 890px);
    width: 1779px;
    height: 440px;
    top: 55px;
    background: url(images/Euro/bg_top_white_glow.png) no-repeat 0 0;
    z-index: -1
}

.main-bar {
    left: 0;
    top: 0;
    position: fixed;
    background: #101720 url(images/sb-red-strip.png) repeat-x 0 -3px;
    padding: 6px 0 3px;
    width: 100%;
    z-index: 1000;
    min-height: 47px
}

.rioEvents {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    z-index: 1000;
    margin-top: 56px
}

.main-bar.un-pinned {
    position: absolute
}

.main-bar>.holder {
    margin: 0 auto;
    width: 946px;
    position: relative
}

.main-bar>.holder:after {
    content: '';
    display: block;
    clear: both
}

.main-bar>.holder .pin {
    position: absolute;
    right: -70px;
    top: 25%;
    background: url(images/pin-bg.png) no-repeat;
    width: 50px;
    height: 27px;
    cursor: pointer;
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: moz-none;
    -ms-user-select: none;
    user-select: none
}

.main-bar>.holder .pin img {
    position: relative;
    float: right;
    margin-top: 1px
}

.main-bar>.holder .pin span {
    font-size: 9px;
    line-height: 28px;
    color: #efefef;
    width: 27px;
    display: inline-block;
    vertical-align: text-bottom;
    text-align: center
}

.time {
    margin: 3px 0 0 10px;
    float: right;
    width: 70px;
    text-align: center;
    color: #FEB207;
    font-size: 20px;
    line-height: 24px;
    background: url(images/bg-time-white.png) no-repeat 5px 26px
}

.time strong {
    font-weight: normal;
    letter-spacing: -0.1em
}

.time .date {
    color: #FEB207;
    font-style: normal;
    font-size: 11px;
    line-height: 13px
}

.main-bar .time {
    color: #fff
}

.main-bar .time .date {
    color: #fff
}

.main-bar .drop-holder {
    float: right;
    margin: 0;
    font-size: 11px;
    line-height: 13px;
    padding: 4px 0 0 4px;
    width: 103px
}

.main-bar .drop-holder>li {
    padding: 3px 0 0
}

.main-bar .drop-holder>li:first-child {
    padding: 0
}

.main-bar .drop-holder>li>a {
    border-top: 1px solid #000;
    border-left: 1px solid #000;
    padding: 1px 6px 2px 22px;
    background: 0;
    border-bottom: 0;
    color: #fff;
    border-right: 0
}

.main-bar .drop-holder>li>a:before {
    background-position: -76px 0;
    width: 8px;
    height: 7px;
    top: 5px;
    left: 5px
}

.main-bar .drop-holder>li:hover>a {
    background: #3e454d;
    color: #bfbfbf
}

.main-bar .drop-holder>li:hover>a:before {
    background-position: -84px 0
}

.main-bar .drop-holder .drop {
    background: #748190;
    width: 103px;
    border: 0;
    box-shadow: 0 1px 2px #c2c2c2;
    -moz-box-shadow: 0 1px 2px #c2c2c2;
    -webkit-box-shadow: 0 1px 2px #c2c2c2
}

.main-bar .drop-holder .drop li {
    border-top: 1px solid #8d959f;
    box-shadow: 0 1px 1px #a2abb5 inset
}

.main-bar .drop-holder .drop li:hover a {
    background: #3e444d;
    color: #dfe2e5;
    box-shadow: 0 1px 1px #a2abb5 inset
}

.main-bar .drop-holder .drop a {
    color: #fff
}

.gallery4 {
    padding: 0 40px;
    float: right;
    font-size: 12px;
    line-height: 15px;
    color: #fff;
    margin-left: 3px;
    position: relative
}

.gallery4 .gmask {
    overflow: hidden;
    width: 680px;
    height: 47px
}

.gallery4 .btn-prev,
.gallery4 .btn-next {
    background: url(images/sprite.png) no-repeat -465px -300px;
    width: 57px;
    height: 46px;
    overflow: hidden;
    text-indent: -9999px;
    position: absolute;
    top: 0;
    z-index: 10;
    right: 0
}

.gallery4 .btn-next {
    right: auto;
    background-position: -526px -300px;
    left: 0
}

.gallery4 .gmask ul {
    padding: 0;
    margin: 0;
    list-style: none;
    overflow: hidden;
    width: 9999px
}

.gallery4 .gmask ul li {
    float: right
}

.gallery4 .gmask ul li .holder {
    width: 217px;
    padding: 0 15px 0 0;
    position: relative;
    height: 46px
}

.gallery4 .gmask ul .holder:before {
    content: '';
    position: absolute;
    top: 0;
    right: 0;
    width: 15px;
    height: 46px;
    background: url(images/item-divider.png) no-repeat
}

.gallery4 .gmask ul li:first-child .holder {
    padding: 0
}

.gallery4 .gmask ul li:first-child .holder:before {
    display: none
}

.gallery4 .heading {
    font-weight: bold;
    padding: 3px 0 4px;
    font-size: 14px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif
}

.gallery4 .score {
    overflow: hidden;
    white-space: nowrap
}

.gallery4 .heading .competition {
    max-width: 132px;
    display: inline-block;
    height: 12px;
    overflow: hidden;
    font-weight: normal
}

.gallery4 .score span {
    display: inline-block;
    font-family: 'blender-regular', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif
}

.gallery4 .score .num {
    width: 20px;
    font-size: 11pt;
    font-weight: bold;
    height: 13px;
    text-align: center;
    line-height: 13px;
    vertical-align: top;
    padding-top: 1px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif
}

.gallery4 .score .num.num-first {
    margin-right: 3px
}

.gallery4 .score .num.num-second {
    margin-left: 3px;
    margin-right: -3px
}

.gallery4 .score.blue .num {
    background-position: -480px -60px
}

.gallery4 .score.grey .num {
    background-position: -480px -74px;
    letter-spacing: 2px
}

.gallery4 .score.grey .num span {
    margin: 0 6px 0 0;
    letter-spacing: 0
}

.gallery4 .score span.team-name {
    max-width: 39%;
    text-overflow: ellipsis;
    overflow: hidden;
    font: 12px/14px 'blender-regular', 'south web', Arial, Helvetica, sans-serif
}

.scoreboard {
    font-size: 12px;
    line-height: 15px;
    color: #FFF;
    padding: 3px 4px 4px 31px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    position: relative;
    margin: 10px 0 0 -3px
}

.scoreboard:hover {
    text-decoration: underline;
    color: #FFF !important
}

#header {
    padding: 75px 2px 18px 0;
    min-height: 111px
}

.hedaer-no-bottom-padding #header {
    padding-bottom: 0;
    left: 15px;
    position: relative
}

.header-holder {
    width: 1155px
}

.header-holder:after,
#header:after {
    content: '';
    display: block;
    clear: both
}

#header .menu {
    float: right;
    width: 825px;
    padding: 26px 0 3px 0
}

#header .menu ul {
    list-style: none;
    margin: 0;
    float: left;
    padding: 0;
    text-transform: uppercase;
    position: relative;
    font-size: 12px;
    line-height: 15px
}

#header .menu ul li:before {
    position: absolute;
    top: 7px;
    right: 0;
    width: 4px;
    height: 3px;
    content: '';
    background: url(images/sprite.png) no-repeat -372px 0
}

#header .menu ul:first-child li:first-child {
    padding-right: 0
}

#header .menu ul:first-child li:first-child div {
    margin-top: -3px;
    height: 18px
}

#header .menu ul:first-child li:first-child:before {
    background: 0
}

#header .menu ul:first-child:before {
    right: auto;
    left: 0
}

#header .menu ul:first-child {
    float: right
}

#header .menu ul li {
    float: right;
    padding: 0 9px 0 14px;
    position: relative
}

#header .menu ul li img {
    display: inline-block;
    vertical-align: middle;
    margin: -5px 6px 0 0
}

#header .menu ul .item {
    font-size: 10px
}

#header .menu ul .fb-like iframe {
    z-index: 11
}

#nav {
    float: right;
    position: relative;
    margin: 0 0 3px
}

#nav .bg {
    position: absolute;
    background: url(images/bg-nav-holder.gif) repeat-x 0 100%;
    height: 25px;
    bottom: 0;
    right: 100%;
    margin: 0 72px 0 0;
    width: 100%
}

#nav .bg:before {
    content: '';
    position: absolute;
    height: 25px;
    width: 90px;
    right: -72px;
    bottom: 0;
    background: url(images/nav-grey.png) no-repeat
}

#nav>ul {
    position: relative;
    padding: 0;
    float: right;
    margin: 0;
    list-style: none;
    font-size: 14px;
    line-height: 18px;
    color: #fff;
    z-index: 10;
    text-align: center;
    font-weight: bold
}

#nav>ul>li {
    float: right;
    height: 30px;
    margin-left: -5px;
    min-width: 90px;
    position: relative
}

#nav ul img {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    z-index: 1
}

#nav ul img.hover,
#nav>ul>li.active>img {
    display: none
}

#nav>ul>li.active>img.hover {
    display: block !important
}

#nav>ul>li:hover>a {
    text-decoration: none
}

#nav ul a {
    display: block;
    color: #fff;
    padding: 10px 17px 4px;
    position: relative;
    z-index: 2;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold'
}

#nav ul .blue {
    min-width: 128px
}

#nav ul .red {
    min-width: 115px
}

#nav ul .orange {
    min-width: 80px
}

#nav ul .bluetravel {
    min-width: 80px
}

#nav ul .peach {
    min-width: 80px
}

#nav ul .green {
    min-width: 113px
}

#nav ul .yellow {
    min-width: 108px
}

.logo {
    float: right;
    background: url(images/logo.png?Q=1) no-repeat;
    background-position: 0 -162px;
    width: 85px;
    height: 80px;
    overflow: hidden;
    text-indent: -9999px;
    margin: -2px 0 0 -11px
}

.logo a {
    display: block;
    height: 100%
}

.red-style .logo {
    background-position: 0 -101px
}

.orange-style .logo {
    background-position: 0 -202px
}

.peach-style .logo {
    background-position: 0 -202px
}

.green-style .logo {
    background-position: 0 -303px
}

.yellow-style .logo {
    background-position: 0 -303px
}

.bluelight-style .logo {
    background-position: 0 -202px
}

.add-nav {
    padding: 0 0 0 9999px;
    margin: 0 16px 4px -9999px;
    float: right;
    position: relative;
    border-bottom: 3px solid #f3f6f8;
    background: #e1e5ea
}

.add-nav>ul {
    padding: 0 15px 0 0;
    margin: 0;
    float: right;
    list-style: none;
    position: relative;
    font-size: 13px;
    line-height: 17px;
    letter-spacing: 0;
    font-weight: bold
}

.add-nav>ul:before {
    background: url(images/sprite.png) no-repeat -434px 0;
    width: 7px;
    height: 23px;
    position: absolute;
    right: -6px;
    bottom: 0;
    content: ''
}

.add-nav:after {
    content: '';
    background: #141c27;
    position: absolute;
    height: 4px;
    bottom: -7px;
    right: 0;
    width: 100%;
    margin: 0 3px 0 0
}

.add-nav>ul>li {
    float: right;
    position: relative;
    padding: 2px 12px 4px 15px
}

.add-nav>ul>li:before {
    background: url(images/sprite.png) no-repeat -443px 0;
    position: absolute;
    top: 3px;
    left: 0;
    content: '';
    width: 7px;
    height: 15px
}

.add-nav>ul>li.active:after {
    content: '';
    position: absolute;
    bottom: -7px;
    right: 0;
    width: 105%;
    height: 4px;
    background: url(images/bg-add-nav.png) no-repeat;
    background-size: 100% 22px;
    z-index: 10
}

.add-nav>ul>li:first-child.active:after {
    right: -15px;
    width: 125%
}

.add-nav>ul>li:first-child {
    padding-right: 0
}

.add-nav a {
    color: #141c27
}

.add-nav ul a:hover {
    color: #014bad
}

.add-nav .more {
    padding: 0 0 0 17px;
    position: relative
}

.add-nav .more:before {
    content: '';
    background: url(images/sprite.png) no-repeat -434px -26px;
    width: 9px;
    height: 5px;
    position: absolute;
    top: 7px;
    left: 0
}

.add-nav ul ul {
    display: none;
    position: absolute;
    top: 100%;
    left: -4px;
    width: 122px;
    margin: 0;
    z-index: 1000;
    list-style: none
}

.add-nav ul ul:before {
    position: absolute;
    top: 3px;
    left: -1px;
    background: url(images/sprite.png) no-repeat -295px -90px;
    content: '';
    height: 4px;
    width: 77px
}

.add-nav ul ul li {
    background: #e1e5ea;
    border-top: 1px solid #fff;
    border-bottom: 1px solid #8a9097;
    padding: 4px 9px 2px
}

.add-nav ul ul li:first-child {
    border: 0;
    background: transparent url(images/trans.gif);
    padding-bottom: 3px
}

.add-nav ul li:hover ul {
    display: block
}

.red-style .add-nav .more:before {
    background-position: -434px -31px
}

.orange-style .add-nav .more:before {
    background-position: -434px -36px
}

.bluetravel-style .add-nav .more:before {
    background-position: -434px -36px
}

.peach-style .add-nav .more:before {
    background-position: -434px -36px
}

.green-style .add-nav .more:before {
    background-position: -434px -41px
}

.yellow-style .add-nav .more:before {
    background-position: -434px -46px
}

.red-style .add-nav:before {
    background-position: -295px -102px
}

.orange-style .add-nav:before {
    background-position: -295px -108px
}

.bluetravel-style .add-nav:before {
    background-position: -295px -108px
}

.peach-style .add-nav:before {
    background-position: -295px -108px
}

.green-style .add-nav:before {
    background-position: -295px -114px
}

.yellow-style .add-nav:before {
    background-position: -295px -120px
}

.banner {
    margin: 0 0 10px
}

.bannerArticle {
    margin: 5px 0 !important
}

.banner.abroad {
    margin: 0 0 10px
}

.banner>img {
    display: block
}

.tab-container {
    border: 1px solid #dcdcdc;
    border-top: 5px solid #014bad;
    margin: 0 0 33px;
    position: relative;
    z-index: 10
}

.tab-container .nav-holder {
    overflow: hidden;
    padding: 0 0 0 19px;
    border-top: 1px solid #fff;
    border-bottom: 1px solid #dcdcdc;
    background: #f3f3f3;
    background: -moz-linear-gradient(top, #f2f2f2 0, #f0f0f0 30%, #e3e3e3 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #f2f2f2), color-stop(30%, #f0f0f0), color-stop(100%, #e3e3e3));
    background: -webkit-linear-gradient(top, #f2f2f2 0, #f0f0f0 30%, #e3e3e3 100%);
    background: -o-linear-gradient(top, #f2f2f2 0, #f0f0f0 30%, #e3e3e3 100%);
    background: -ms-linear-gradient(top, #f2f2f2 0, #f0f0f0 30%, #e3e3e3 100%);
    background: linear-gradient(to bottom, #f2f2f2 0, #f0f0f0 30%, #e3e3e3 100%)
}

.show-all {
    float: left;
    position: relative;
    margin: 0 0 5px;
    color: #014bad;
    font-size: 12px;
    line-height: 15px;
    padding: 0 0 0 12px
}

.show-all:before {
    content: '';
    position: absolute;
    left: 0;
    top: 4px;
    background: url(images/sprite.png) no-repeat;
    width: 6px;
    height: 9px
}

.show-all.day-events {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    line-height: 22px;
    display: block;
    font-size: 15px;
    color: #000;
    margin: 3px 0 0 0;
    font-weight: bold
}

.show-all.day-events:before {
    top: 5px
}

.tab-container .tabset {
    padding: 0;
    text-align: center;
    margin: 0;
    list-style: none;
    border-left: 1px solid #fff;
    font-weight: bold;
    font-size: 14px;
    line-height: 22px;
    float: right
}

.tab-container .tabset li {
    float: right
}

.tab-container .tabset a {
    width: 116px;
    color: #535353;
    padding: 0 0 2px;
    display: block;
    background: url(images/sprite.png) no-repeat 0 -13px
}

.tab-container .tabset a:hover,
.tab-container .tabset a.active {
    background-position: 0 -45px;
    text-decoration: none;
    color: #858585
}

.tab-content {
    border-top: 1px solid #fff;
    padding: 7px 4px 7px 9px;
    background: #fafafa;
    background: -moz-linear-gradient(top, rgba(250, 250, 250, 1) 0, rgba(250, 250, 250, 1) 29%, rgba(238, 238, 238, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(250, 250, 250, 1)), color-stop(29%, rgba(250, 250, 250, 1)), color-stop(100%, rgba(238, 238, 238, 1)));
    background: -webkit-linear-gradient(top, rgba(250, 250, 250, 1) 0, rgba(250, 250, 250, 1) 29%, rgba(238, 238, 238, 1) 100%);
    background: -o-linear-gradient(top, rgba(250, 250, 250, 1) 0, rgba(250, 250, 250, 1) 29%, rgba(238, 238, 238, 1) 100%);
    background: -ms-linear-gradient(top, rgba(250, 250, 250, 1) 0, rgba(250, 250, 250, 1) 29%, rgba(238, 238, 238, 1) 100%);
    background: linear-gradient(to bottom, rgba(250, 250, 250, 1) 0, rgba(250, 250, 250, 1) 29%, rgba(238, 238, 238, 1) 100%)
}

.tab:after {
    content: '';
    display: block;
    clear: both
}

.bar {
    padding: 0 21px 4px 25px;
    margin: 0;
    list-style: none;
    float: right;
    position: relative;
    font-size: 12px;
    line-height: 14px;
    color: #707070
}

.bar:before {
    position: absolute;
    top: 0;
    right: 2px;
    content: '';
    width: 12px;
    background: url(images/sprite.png) no-repeat -124px -13px;
    height: 47px
}

.bar li {
    padding: 0 5px 0 0;
    position: relative
}

.bar li:before {
    background: url(images/sprite.png) no-repeat -7px 0;
    width: 3px;
    height: 1px;
    position: absolute;
    top: 7px;
    right: 0;
    content: ''
}

.bar li.active {
    color: #000
}

.bar .selected {
    position: absolute;
    right: -21px;
    top: 1px;
    background: url(images/sprite.png) no-repeat -144px -13px;
    width: 16px;
    height: 16px;
    content: '';
    visibility: hidden
}

.bar li.active .selected {
    visibility: visible
}

.tab-container .gallery {
    position: relative;
    overflow: hidden;
    border-radius: 3px;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border: 1px solid #cbcbcb;
    background: #ddd;
    padding: 4px 22px 4px 23px
}

.tab-container .gallery .btn-prev,
.tab-container .gallery .btn-next {
    position: absolute;
    overflow: hidden;
    text-indent: -9999px;
    left: 4px;
    top: 4px;
    width: 16px;
    height: 36px;
    background: url(images/sprite.png) no-repeat -168px -13px
}

.tab-container .gallery .btn-next {
    left: auto;
    right: 3px;
    background-position: -187px -13px
}

.tab-container .gallery .btn-next:hover {
    background-position: -187px -61px
}

.tab-container .gallery .btn-prev:hover {
    background-position: -168px -61px
}

.tab-container .gallery .gmask {
    overflow: hidden
}

.tab-container .gallery ul {
    padding: 0;
    margin: 0;
    list-style: none;
    overflow: hidden;
    width: 9999px
}

.tab-container .gallery ul li {
    float: right;
    width: 190px;
    margin-right: 4px;
    background: #fff;
    border: 1px solid #b7b7b7
}

.tab-container .gallery ul li:first-child {
    margin: 0
}

.tab-container .gallery .heading {
    background: #f8f8f8;
    overflow: hidden;
    font-size: 12px;
    line-height: 16px;
    padding: 0 5px 0 9px;
    color: #014bad;
    border-top: 1px solid #fff
}

.tab-container .gallery .heading .direct {
    float: left;
    font-size: 11px;
    line-height: 14px;
    color: #cc1601
}

.tab-container .gallery .holder {
    overflow: hidden;
    font-size: 12px;
    line-height: 16px;
    padding: 0 6px 1px
}

.tab-container .gallery .date {
    font-style: normal;
    float: right;
    color: #898989;
    margin-left: 12px;
    font-size: 11px
}

body.with-bg.special-league .w1 #main {
    position: relative
}

body.with-bg.special-league .w1 #main:before {
    content: '';
    position: absolute;
    display: block;
    right: -47px;
    width: 1038px;
    height: 100%;
    top: 0;
    background: url(images/Euro/bg_dark_glow.png) repeat-y 0 0;
    z-index: -1
}

#main .gallery p {
    margin: 0
}

#main .gallery .holder a {
    display: block;
    color: #fff
}

#main .gallery .score {
    display: table;
    width: 100%;
    font-size: 12px;
    line-height: 12px
}

#main .gallery .score>* {
    text-align: center;
    height: 16px;
    vertical-align: middle;
    display: table-cell;
    padding: 0 0 1px
}

#main .gallery .num {
    color: #fff;
    font-weight: bold;
    width: 32px;
    padding: 0
}

#main .gallery .pink .num {
    background: #E96CA4
}

#main .gallery .green .num {
    background: #61ac11
}

#main .gallery .black .heading {
    color: #313131
}

#main .gallery .black .num {
    background: #313131
}

#main .gallery .blue .heading {
    color: #014bad
}

#main .gallery .blue .num {
    background: #014bad
}

#main .gallery .red .heading {
    color: #cc1601
}

#main .gallery .red .num {
    background: #cc1601
}

#main .galleryWord .num {
    color: #fff;
    font-weight: bold;
    width: 32px;
    padding: 0
}

#main .galleryWord .pink .num {
    background: #E96CA4
}

#main .galleryWord .green .num {
    background: #61ac11
}

#main .galleryWord .black .heading {
    color: #313131
}

#main .galleryWord .black .num {
    background: #313131
}

#main .galleryWord .blue .heading {
    color: #014bad
}

#main .galleryWord .blue .num {
    background: #014bad
}

#main .galleryWord .red .heading {
    color: #cc1601
}

#main .galleryWord .red .num {
    background: #cc1601
}

#twocolumns {
    overflow: hidden;
    position: relative;
    z-index: 1
}

#sidebar {
    float: left;
    width: 325px;
    padding: 0 15px 0 0
}

.events_archive #sidebar {
    padding: 0
}

#sidebar .banner-holder {
    padding: 1px;
    margin: 0 0 18px;
    margin-left: 1px 0
}

#sidebar .banner-holder img {
    display: block
}

#sidebar .gallery {
    margin: 0 0 18px;
    border: 1px solid #d4d4d4;
    overflow: hidden;
    background: #f9f9f9;
    background: -moz-linear-gradient(top, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(249, 249, 249, 1)), color-stop(33%, rgba(248, 248, 248, 1)), color-stop(43%, rgba(244, 244, 244, 1)), color-stop(74%, rgba(240, 240, 240, 1)), color-stop(100%, rgba(233, 233, 233, 1)));
    background: -webkit-linear-gradient(top, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%);
    background: -o-linear-gradient(top, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%);
    background: -ms-linear-gradient(top, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%);
    background: linear-gradient(to bottom, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%)
}

#sidebar .gallery .btn-prev,
#sidebar .gallery .btn-next {
    float: left;
    overflow: hidden;
    text-indent: -9999px;
    width: 15px;
    height: 74px;
    background: url(images/sprite.png) no-repeat -261px 0
}

#sidebar .gallery .btn-next {
    float: right;
    background-position: -279px 0
}

#sidebar .gallery .btn-next:hover {
    background-position: -279px -78px
}

#sidebar .gallery .btn-prev:hover {
    background-position: -261px -78px
}

#sidebar .gallery .gmask {
    overflow: hidden;
    border: solid #fff;
    border-width: 1px 0 0 1px
}

#sidebar .gallery .gmask>ul {
    padding: 0;
    margin: 0;
    list-style: none;
    overflow: hidden;
    width: 9999px
}

#sidebar .meetings-block {
    margin: 0 0 26px;
    border: 1px solid #c3cad1;
    display: inline-block
}

#sidebar .meetings-block table.item-list {
    width: 50%;
    float: right
}

.logo-block .holder {
    border: 1px solid #fff
}

.logo-block .frame {
    border-top: 3px solid #014bad;
    padding: 3px 0 0 4px
}

.logo-block {
    border-bottom: 1px solid #c3cad1;
    background: #fff url(images/bg-logo-block.gif) repeat-x
}

.logo-block .box {
    display: table-cell;
    vertical-align: middle;
    text-align: center;
    width: 151px;
    padding: 13px 0 8px;
    border-right: 1px solid #c3cad1
}

.logo-block .box:first-child {
    border: 0
}

.logo-block .box .img {
    vertical-align: top;
    margin: 0 0 2px
}

.logo-block .box .img img {
    vertical-align: top
}

.logo-block .box a {
    color: #1b1b1b
}

.logo-block .box a:hover {
    color: #014bad
}

#main .about-block .heading,
#main .player-info .heading,
#main .results-holder .heading,
#main .tab-box .heading,
#main .score-list .heading,
#sidebar .heading,
#sidebarHomePage .heading,
#toto-stats .heading,
.twtr-hd {
    text-align: center;
    font-size: 18px;
    line-height: 22px;
    display: block;
    padding: 3px 5px 7px;
    border: 1px solid #fff;
    border-bottom: 1px solid #014bad;
    background: #f5f5f5;
    background: -moz-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    /* background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(237, 237, 237, 1)), color-stop(19%, rgba(248, 248, 248, 1)), color-stop(35%, rgba(253, 253, 253, 1)), color-stop(100%, rgba(255, 255, 255, 1))); */
    background: -webkit-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -o-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -ms-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: linear-gradient(to bottom, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    border-left: none;
    border-right: 0
}

#sidebar .heading {
    padding: 8px 5px 2px
}

table.item-list {
    border: solid #fff;
    border-width: 1px 1px 0;
    border-collapse: collapse;
    width: 100%;
    line-height: 18px
}

table.item-list td {
    height: 37px;
    padding: 7px 12px 5px 15px;
    width: 120px;
    border: solid #fff;
    vertical-align: top;
    border-width: 0 2px 2px 0;
    background: #edf3fb
}

table.item-list td:first-child {
    border-right: 0
}

table.item-list .num {
    float: left;
    font-size: 14px;
    font-weight: bold
}

.premiership-block .heading.heading-alt {
    border: 1px solid #e8e8e8 !important
}

#sidebar .premiership-block .heading {
    text-align: right;
    padding: 4px 18px 1px;
    border-bottom: 3px solid #014bad;
    border-left: none;
    border-right: 0
}

.premiership-block .premiership-block-carousel {
    overflow: hidden;
    border: 1px solid #e8e8e8;
    width: 302px
}

.premiership-block .premiership-block-carousel>div {
    margin: 0;
    padding: 0;
    overflow: hidden
}

.premiership-block .premiership-block-carousel>div>div {
    width: 302px;
    min-height: 230px;
    float: right;
    overflow: hidden
}

.premiership-block a.btn-next,
.premiership-block a.btn-prev {
    position: absolute;
    left: 5px;
    cursor: pointer;
    background: url(images/sprite.png) no-repeat;
    width: 7px;
    height: 9px;
    text-indent: -9999px;
    top: 11px
}

.premiership-block a.btn-prev {
    background-position: -408px 0;
    left: 18px
}

.table {
    width: 100%;
    font-size: 14px;
    border-collapse: collapse;
    border: 1px solid #fff
}

.table td {
    height: 29px;
    text-align: center;
    padding: 2px 0
}

.table th {
    height: 23px;
    border-bottom: 1px solid #000;
    font-weight: normal;
    color: #535353;
    width: 42px;
    padding: 2px 0
}

.table td:first-child,
.table th:first-child {
    width: 42px;
    padding: 0 4px 0 0
}

.table .big {
    width: 127px !important;
    font-size: 14px;
    line-height: 21px;
    text-align: right
}

.table .alt-color {
    background: #edf3fb
}

#main .table-alt th {
    height: auto;
    line-height: 17px !important;
    padding: 4px 0 7px;
    border: 0
}

#main .table-alt td {
    background: #edf3fb;
    border-bottom: 1px solid #fff
}

#main .table-alt .row td {
    border-bottom: 2px solid #fff
}

#sidebar .premiership-block .heading-alt {
    border: 0;
    padding: 1px 14px 6px;
    overflow: hidden
}

.premiership-block .show-all {
    padding-left: 15px
}

.precisely-block {
    border: 1px solid #c3cad1;
    margin-bottom: 20px
}

.statistic-block {
    overflow: hidden;
    border: solid #fff;
    border-width: 4px 1px 0;
    border-bottom: 1px solid #c3cad1
}

.statistic-block .block {
    float: right;
    text-align: center;
    font-size: 12px;
    line-height: 14px;
    width: 96px;
    padding: 17px 0 7px;
    position: relative;
    background: #edf3fb
}

.statistic-block .block:first-child {
    width: 95px
}

.statistic-block .block .img {
    margin: 0 auto 3px
}

.statistic-block .block .img a {
    display: inline-block;
    vertical-align: top;
    margin: 0;
    width: auto
}

.statistic-block .block .img img {
    display: block;
    border: 1px solid #fff
}

.statistic-block .block span {
    position: absolute;
    top: 130px;
    font-weight: bold;
    font-size: 28px;
    display: inline-block;
    line-height: 32px;
    left: 32px;
    cursor: default
}

.statistic-block a {
    width: 41px;
    margin: 0 0 19px;
    display: inline-block;
    vertical-align: top
}

.statistic-block table {
    border-collapse: collapse;
    margin: 0 0 0 4px;
    float: right;
    width: 105px;
    font-size: 16px
}

.statistic-block table td {
    border: solid #fff;
    text-align: center;
    height: 26px;
    background: #edf3fb;
    padding: 0 !important;
    width: 31px;
    border-width: 0 4px 4px 0
}

.statistic-block table td a {
    margin: 0;
    display: block;
    width: auto
}

.statistic-block table img {
    display: block
}

.statistic-block table td:first-child {
    border-left: 0
}

.statistic-block table tr:first-child td {
    border-bottom: 5px solid #fff
}

table.item-list-alt {
    font-size: 12px;
    line-height: 12px
}

.table-holder {
    border-bottom: 1px solid #c3cad1
}

.item-list .img-holder {
    float: right;
    margin: 0 0 0 6px;
    border: 1px solid #fff
}

.item-list .text {
    overflow: hidden;
    padding: 4px 0 0
}

.item-list .text a {
    display: block;
    vertical-align: top;
    margin: 0 0 3px
}

.item-list .img-holder img {
    display: block
}

table.item-list-alt td {
    height: auto;
    border-bottom: 1px solid #fff;
    padding: 10px 13px 12px
}

.item-list .number {
    font-weight: normal;
    font-size: 18px;
    line-height: 18px
}

#sidebar .heading p {
    margin: 0;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold'
}

#sidebar .heading h2 {
    margin: 0;
    font-size: 18px;
    line-height: 22px
}

#sidebar .gallery .gmask>ul>li {
    float: right;
    height: 73px;
    width: 253px;
    padding: 0 10px 0 9px
}

#sidebar .gallery .gmask ul img {
    display: inline-block;
    vertical-align: middle
}

#sidebar .gallery .gmask .holder {
    display: inline-block;
    vertical-align: middle;
    margin: 0 4px 0 8px;
    font-weight: bold;
    text-align: center;
    width: 164px;
    height: 73px
}

#sidebar .gallery strong {
    display: block;
    margin: 0 0 2px
}

#sidebar .gallery .score {
    padding: 0;
    margin: 0 0 3px;
    font-size: 12px;
    line-height: 13px;
    font-weight: normal;
    list-style: none;
    overflow: hidden
}

#sidebar .gallery .score li {
    float: right;
    margin-right: 2px;
    border: 1px solid #bcbcbc;
    width: 51px;
    padding: 2px 0
}

#sidebar .gallery .score li:first-child {
    margin: 0
}

#sidebar .gallery .score span {
    float: left;
    width: 15px;
    font-weight: bold;
    padding: 2px 0;
    color: #cc1601;
    background: #fff;
    margin: -2px 0
}

#sidebar .gallery.gallery-banner {
    height: 73px
}

#sidebar .gallery.gallery-banner .gmask {
    width: 193px;
    border: 0
}

#sidebar .gallery.gallery-banner .gmask>ul>li {
    width: 193px;
    padding: 0
}

#sidebar .gallery.gallery-banner .gmask .holder {
    display: block;
    width: auto;
    margin: 0
}

#sidebar .gallery.gallery-banner strong {
    height: 22px;
    overflow: hidden;
    font-size: 12px
}

#sidebar .gallery.gallery-banner .score li {
    width: 48px;
    line-height: 18px;
    float: none;
    display: inline-block;
    margin: 0 3px
}

#sidebar .gallery.gallery-banner .score span {
    margin: 0;
    padding: 0
}

#sidebar .gallery.gallery-banner a.send {
    display: inline-block
}

#sidebar .gallery.gallery-banner .btn-prev,
#sidebar .gallery.gallery-banner .btn-next,
#sidebar .gallery.gallery-banner .gmask {
    float: left
}

#sidebar .gallery.gallery-banner .btn-next {
    border-right: 1px solid #d4d4d4
}

#sidebar .gallery.gallery-banner>img {
    margin-top: 6px;
    margin: 6px 2px 0 0;
    float: right
}

a.send {
    display: block;
    font-size: 12px;
    line-height: 19px;
    color: #000 !important;
    margin: 0 1px 0 0;
    width: 162px;
    background: url(images/sprite.png) no-repeat 0 -78px
}

a.send:hover {
    color: #014bad !important
}

.main-block {
    overflow: hidden;
    padding: 0
}

.content-block {
    overflow: hidden;
    margin: 0
}

.content-block-article {
    overflow: visible
}

.social-container {
    float: right;
    border-right: 16px solid #014bad;
    min-height: 490px;
    margin: 7px 0 0;
    padding: 0 16px 0 0;
    width: 128px;
    position: relative
}

.social-container-wide {
    min-height: 22px;
    margin: 0;
    padding: 0;
    float: left
}

.social-networks-wide {
    padding: 0;
    margin: 0;
    list-style: none
}

.social-networks-wide li {
    padding: 0 4px 0 0;
    display: inline-block;
    vertical-align: bottom
}

.social-networks {
    padding: 0;
    margin: 0;
    list-style: none
}

.social-networks li {
    padding: 0 0 5px
}

.social-networks a {
    display: block;
    overflow: hidden;
    text-indent: -9999px;
    width: 50px;
    height: 20px;
    background: url(images/sprite.png?q) no-repeat -207px -13px
}

.social-networks-wide a {
    display: block;
    overflow: hidden;
    text-indent: -9999px;
    width: 50px;
    height: 20px;
    background: url(images/sprite.png?q) no-repeat -207px -13px
}

.social-networks li.google div {
    height: 15px !important
}

.social-networks-wide li.google div {
    height: 15px !important
}

.social-networks .google {
    background-position: -207px -38px
}

.social-networks-wide .google {
    background-position: -207px -38px;
    height: 20px !important
}

.social-networks .twitter {
    background-position: -207px -62px
}

.social-networks-wide .twitter {
    background-position: -207px -62px
}

.social-networks .mail {
    background-position: -207px -87px
}

.social-networks-wide .mail {
    background-position: -207px -87px
}

.text-block-tags {
    font-size: 12px;
    line-height: 14px;
    color: #7d7d7d;
    padding: 0;
    margin: 0
}

.social-container .text-block {
    position: absolute;
    right: 17px;
    bottom: -3px;
    font-size: 12px;
    line-height: 14px;
    color: #7d7d7d;
    width: 115px
}

.social-container-wide .text-block {
    right: 17px;
    bottom: -3px;
    font-size: 12px;
    line-height: 14px;
    color: #7d7d7d;
    width: 300px;
    float: right
}

#main .social-container p {
    margin: 0
}

#main .social-container-wide p {
    margin: 0
}

#main .social-container p a {
    display: inline-block;
    color: #7d7d7d
}

#main .social-container-wide p a {
    display: inline-block;
    color: #7d7d7d
}

#main .social-container p a:hover,
#main .social-container a.alt,
#main .social-container span.alt {
    color: #014bad
}

#main .social-container-wide p a:hover,
#main .social-container-wide a.alt,
#main .social-container-wide span.alt {
    color: #014bad
}

.content-holder {
    width: 600px;
    float: right;
    margin-bottom: 10px
}

.content-holder h2 {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    padding-top: 5px
}

#main h2,
#main h1.article-main {
    letter-spacing: -0.010em;
    font-size: 33px;
    line-height: 35px;
    margin: 4px 0 18px
}

#main h1.article-main {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web extrabold', Arial, Helvetica, sans-serif;
    font-weight: bold;
    margin-bottom: 4px
}

#main .news-block h2,
#content h2,
#main h3,
#main p.h3 {
    font-size: 16px;
    line-height: 18px;
    margin: 0 0 12px;
    font-weight: normal
}

#main .content-holder h2.article-sub-main {
    font-size: 16px;
    line-height: 18px;
    margin: 0 0 12px;
    font-weight: normal;
    letter-spacing: 0;
    font-family: 'blender-regular', 'Mukta-Medium', Arial;
    padding: 0
}

#main .hint {
    font-size: 12px;
    line-height: 30px;
    margin: 0;
    display: block;
    position: relative
}

#main div.author-img-holder {
    height: 40px;
    width: 40px;
    border-radius: 50px;
    border: solid 1px blue;
    float: right;
    margin: -10px 0 3px 10px;
    overflow: hidden;
    position: relative
}

#main img.author-img {
    height: 45px;
    width: auto;
    position: absolute;
    margin-left: -32px;
    max-width: 100%;
    max-height: 100%;
    object-fit: cover
}

#main .hint .article-talkbacks {
    float: left;
    margin: 0 10px 0 0;
    padding: 0 20px 0 0;
    background: url(Images/bluecomm.png) no-repeat 100%
}

#main .hint .article-talkbacksHomePage {
    float: left;
    margin: 0 10px 0 0;
    padding: 0 20px 0 0;
    background: url(Images/bluecomm.png) no-repeat 100%
}

#main .hint .article-views {
    float: left;
    padding: 0 20px 0 0;
    background: url(Images/blueeye.png) no-repeat 100%
}

#main .content-holder p {
    margin: 0 0 4px
}

.btn-play>a {
    position: relative;
    display: block;
    z-index: 0
}

.btn-play>a:after {
    content: '';
    position: absolute;
    right: 93%;
    top: 91%;
    height: 100px;
    margin: -50px -50px 0 0;
    z-index: 1000;
    background: url(images/videosmallitem.png) no-repeat 0 0
}

.league-page .btn-play>a:after,
.world-page .btn-play>a:after {
    width: 53px;
    height: 53px;
    background: url(images/VideoMain.png) no-repeat 0 0;
    left: 10px;
    bottom: 7px;
    right: unset;
    top: unset
}

.btn-play.hp.live>a:after {
    top: auto;
    bottom: 8px
}

.btn-play.hp.live>a::before {
    content: '';
    position: absolute;
    right: auto;
    left: 6px;
    bottom: 4px;
    width: 55px;
    height: 29px;
    z-index: 1000;
    background: url(images/play-live.png) no-repeat
}

.btn-play-reverse>a:after {
    content: '';
    position: absolute;
    right: 93%;
    top: 91%;
    width: 101px;
    height: 100px;
    margin: -80px -40px 0 0;
    z-index: 1000;
    background: url(images/sprite.png) no-repeat -101px -117px
}

.btn-play-reverse>a:hover:after {
    cursor: pointer;
    background-position: 2px -117px
}

.btn-play>a:hover:after {
    opacity: .6;
    background-position: 0 0
}

.btn-play.hp>a:after {
    right: 91.8%
}

.btn-play.medium>a:after {
    right: 89%;
    top: 84%
}

.info-container .article .btn-play.medium>a:after {
    right: 90%;
    top: 87%
}

.btn-play.smallLeftSide>a:after {
    width: 59px;
    height: 39px;
    margin: 14px 20px 0 0;
    right: 45.5%;
    top: 45%;
    z-index: 99999
}

.btn-play.smallLeftSide>a:hover:after {
    opacity: .6;
    background-position: 0 0
}

.btn-play.bigLeftSide>a:after {
    width: 59px;
    height: 39px;
    margin: 14px 20px 0 0;
    right: 85.5%;
    z-index: 99999;
    margin: -25px -25px 0 0;
    z-index: 1000;
    background: url(images/VideoMain.png) no-repeat 0 0
}

.btn-play.bigLeftSide>a:hover:after {
    opacity: .6;
    background-position: 0 0
}

.btn-play.small>a:after {
    width: 59px;
    height: 40px;
    margin: 4px 12px 0 0;
    background-position: 0 0;
    right: 52.5%;
    top: 54%
}

.btn-play.small>a:hover:after {
    opacity: .6;
    background-position: 0 0
}

.btn-play.small.video-box>a:after {
    right: 51%
}

.btn-play a>img {
    position: relative;
    z-index: -1
}

.btn-play>a:hover img {
    border: 0
}

.video-holder {
    position: relative;
    margin: 0 0 17px
}

.video-holder .title,
.gallery5 .exclusive {
    position: absolute;
    top: 15px;
    right: 0;
    background: #014bad;
    color: #fff;
    font-size: 14px;
    line-height: 25px;
    padding: 3px 78px 0 1px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web';
    z-index: 10
}

.main-hp .gallery5 .exclusive {
    right: 19px;
    width: auto;
    display: inline-block;
    padding: 2px 19px 1px 1px
}

.main-hp .bomba-article .bomba-exclusive {
    right: 0;
    width: auto;
    display: inline-block;
    padding: 1px 19px 0 1px
}

.video-holder .title.red,
.gallery5 .exclusive.red {
    background: #d10202
}

.video-holder .title.orange,
.gallery5 .exclusive.orange {
    background: #ff5a00
}

.video-holder .title.bluetravel,
.gallery5 .exclusive.orange {
    background: #043959
}

.video-holder .title.lightblue,
.gallery5 .exclusive.lightblue {
    background: #0c9bd9
}

.video-holder .title.peach,
.gallery5 .exclusive.peach {
    background: #B8411F
}

.video-holder .title.green,
.gallery5 .exclusive.green {
    background: #0e9b00
}

.video-holder .title.green,
.gallery5 .exclusive.pink {
    background: #0e9b00
}

.video-holder .title.yellow,
.gallery5 .exclusive.yellow {
    background: #ffa200
}

.video-holder .title:before,
.gallery5 .exclusive:before {
    position: absolute;
    left: -50px;
    top: 0;
    background: url(images/sprite2.png) no-repeat -38px -0px;
    width: 50px;
    height: 28px;
    content: ''
}

.video-holder .title.red:before,
.gallery5 .exclusive.red:before {
    background-image: url(images/sprite2-red.png)
}

.video-holder .title.orange:before,
.gallery5 .exclusive.orange:before {
    background-image: url(images/sprite2-orange.png)
}

.video-holder .title.bluetravel :before,
.gallery5 .exclusive.bluetravel :before {
    background-image: url(images/sprite2-orange.png)
}

.video-holder .title.peach :before,
.gallery5 .exclusive.peach :before {
    background-image: url(images/sprite2-orange.png)
}

.video-holder .title.green:before,
.gallery5 .exclusive.green:before {
    background-image: url(images/sprite2-green.png)
}

.video-holder .title.pink:before,
.gallery5 .exclusive.green:before {
    background-image: url(images/sprite2-green.png)
}

.video-holder .title.yellow:before,
.gallery5 .exclusive.yellow:before {
    background-image: url(images/sprite2-yellow.png)
}

.video-holder img {
    display: block
}

.video-holder .desc {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 100%;
    color: #fff;
    font-size: 12px;
    line-height: 15px;
    background: url(images/bg-desc.png)
}

#main .desc a,
.video-holder .desc span {
    display: block;
    padding: 3px 14px 5px
}

.payed-btn-play .video-anc:after {
    content: '';
    position: absolute;
    right: 93%;
    top: 85%;
    width: 101px;
    height: 100px;
    margin: -50px -50px 0 0;
    z-index: 1000;
    background: url(images/videosmallitem.png) no-repeat 0 0
}

.payed-btn-play .video-anc:hover:after {
    cursor: pointer;
    background-position: 2px -117px
}

#main .desc a {
    color: #fff
}

.social-holder {
    padding: 0;
    margin: 0 0 0 1px;
    list-style: none;
    float: left;
    min-height: 22px
}

.social-holder li {
    float: left;
    margin-right: 7px
}

.social-holder li a {
    float: left;
    margin-right: 2px
}

.social-holder label {
    float: left;
    background: url(images/sprite.png) no-repeat -332px 1px;
    width: 27px;
    height: 18px;
    font-size: 11px;
    line-height: 18px;
    letter-spacing: .055em;
    padding: 1px 4px 0 7px
}

.social-holder img {
    display: block
}

.social-holder .fb-like iframe {
    left: 0
}

.main-container:after {
    content: '';
    display: block;
    clear: both
}

.aside {
    float: right;
    padding: 7px 0 0 15px;
    width: 154px
}

.aside-holder {
    float: right
}

.aside-holder {
    padding-top: 15px
}

.aside-alt {
    padding-top: 0
}

.banner-list {
    padding: 7px 0 14px;
    margin: 0;
    list-style: none;
    text-align: right
}

.radio-container {
    padding: 0 0 7px;
    margin: 0;
    list-style: none;
    text-align: right
}

.banner-list.center {
    padding: 0;
    overflow: hidden
}

.banner-list.center li {
    padding: 0 0 15px
}

.banner-list li {
    padding: 0 0 10px;
    display: inline-block;
    vertical-align: bottom
}

.banner-list img,
.banner-list object {
    display: block
}

.tab-block {
    margin-bottom: 18px
}

.tab-block .tabset {
    padding: 0;
    margin: 0;
    list-style: none;
    border: solid #dcdcdc;
    border-width: 1px 1px 0;
    text-align: center;
    font-size: 12px;
    overflow: hidden;
    line-height: 26px
}

.tab-block .tabset li {
    float: right;
    width: 71px
}

.tab-block .tabset a {
    color: #535353;
    display: block;
    background: url(images/sprite.png) no-repeat 0 -217px
}

.tab-block .tabset a:hover,
.tab-block .tabset a.active {
    color: #8d8d8d;
    background-position: -71px -217px;
    text-decoration: none
}

.tab-block .tab-content {
    position: relative;
    padding: 0 0 18px;
    background: 0
}

.tab-block .tab-content:after {
    content: '';
    position: absolute;
    bottom: 0;
    right: 0;
    width: 144px;
    height: 29px;
    background: url(images/sprite.png) no-repeat -142px -217px
}

.tab-block .tab-content>div {
    position: relative;
    z-index: 10
}

#main .article-block .heading,
#main .aside .heading,
#content .heading,
#main .tab-block .heading {
    border: 1px solid #c3cad1;
    margin: 0 0 15px;
    border-bottom: 3px solid #014bad
}

#main .article-block .heading h2,
#main .aside .heading h2,
#main #content .heading h2,
#main .tab-block .heading h2 {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    line-height: 22px;
    border: 1px solid #fff;
    display: block;
    font-size: 15px;
    color: #000;
    margin: 0;
    font-weight: bold;
    padding: 5px 8px 2px 5px;
    background: #f5f5f5;
    background: -moz-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(237, 237, 237, 1)), color-stop(19%, rgba(248, 248, 248, 1)), color-stop(35%, rgba(253, 253, 253, 1)), color-stop(100%, rgba(255, 255, 255, 1)));
    background: -webkit-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -o-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -ms-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: linear-gradient(to bottom, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%)
}

#main #content.tlkb-days .heading {
    padding-top: 0
}

#main #content.tlkb-days .heading h2 {
    background: 0;
    border: 0
}

#main #sidebar #content.tlkb-days .post p {
    height: inherit
}

.writer {
    font-style: normal;
    font-size: 11px;
    margin-left: 3px
}

.date {
    color: #626262;
    font-style: normal;
    font-size: 10px;
    display: block
}

#main .descHomepageDoublePass {
    margin: 0 10px;
    height: 50px;
    overflow: hidden
}

#main .descHomepageDoublePass a {
    color: black !important;
    font: 14px/22px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    line-height: 10px;
    height: auto;
    margin: 12px 0;
    overflow: hidden;
    text-decoration: none
}

.datedoublepass {
    color: #737373;
    font: 10px/12px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    display: block;
    bottom: -16px;
    display: block;
    position: relative;
    padding-right: 10px
}

.article-list-talkbacks {
    margin: 0 5px;
    padding: 1px 20px 0 0;
    background: url(Images/graycomm.png) no-repeat 100%
}

.article-holder .date .talkbacks {
    margin: 0 5px;
    padding: 1px 20px 0 0;
    background: url(Images/graycomm.png) no-repeat 100%
}

.article-list-talkbacksHomePage {
    margin: 0 5px;
    padding: 1px 20px 0 0;
    background: url(Images/graycomm.png) no-repeat 100%
}

.article-holder .date .talkbacksHomePage {
    margin: 0 5px;
    padding: 1px 20px 0 0;
    background: url(Images/graycomm.png) no-repeat 100%
}

.writer-date .date {
    display: inline-block;
    margin: 0 0 0 5px
}

.writer-date .talkbacks {
    color: #626262;
    font-style: normal;
    font-size: 10px;
    display: inline-block;
    padding: 0 20px 0 0;
    background: url(Images/graycomm.png) no-repeat 100%
}

.writer-date .talkbacksHomePage {
    color: white;
    font-style: normal;
    font-size: 11px;
    display: inline-block;
    margin-top: 0;
    left: 8px;
    position: absolute;
    text-align: center
}

.talkbacksText {
    color: white;
    font-style: normal;
    font-size: 10px;
    display: inline-block;
    margin-top: -2px;
    left: 0;
    position: relative;
    text-align: center;
    float: left;
    width: 52px
}

.writer-date .talkbacksHomePage-blue {
    fill: #0042B0
}

.writer-date .talkbacksHomePage-red {
    fill: #d10202
}

.writer-date .talkbacksHomePage-orange {
    fill: #ff5a00
}

.writer-date .talkbacksHomePage-bluetravel {
    fill: #043959
}

.writer-date .talkbacksHomePage-lightblue {
    fill: #0c9bd9
}

.writer-date .talkbacksHomePage-peach {
    fill: #B8411F
}

.writer-date .talkbacksHomePage-grey {
    fill: #59616c
}

.writer-date .talkbacksHomePage-black {
    fill: #141c27
}

.writer-date .talkbacksHomePage-green {
    fill: #0e9b00
}

.writer-date .talkbacksHomePage-gold {
    fill: #691e32
}

.writer-date .talkbacksHomePage-rio_red {
    fill: #ed1b24
}

.writer-date .talkbacksHomePage-rio_light_green {
    fill: #63b32e
}

.writer-date .talkbacksHomePage-rio_orange {
    fill: #ee7202
}

.writer-date .talkbacksHomePage-rio_navy {
    fill: #004b88
}

.writer-date .talkbacksHomePage-rio_green {
    fill: #008b3b
}

.writer-date .talkbacksHomePage-rio_purple {
    fill: #672879
}

.writer-date .talkbacksHomePage-rio_light_blue {
    fill: #01abe4
}

.writer-date .talkbacksHomePage-rio_blue {
    fill: #2564b9
}

.writer-date .writer {
    display: table;
    height: 15px
}

.comment-box .date {
    display: inline
}

.comment-box .comment-opener {
    cursor: pointer
}

.comment-box .comment-content {
    display: none
}

.comment-box .comment-opener-active .comment-content {
    display: block
}

.article-content {
    text-align: justify;
    width: 600px;
    display: inline-block;
    margin-top: 10px;
    line-height: 25px
}

.article-content>span {
    display: block;
    margin-bottom: 23px
}

.article-content p a {
    color: #014bad
}

.article-content div.video-div {
    margin: 0 0 16px;
    position: relative
}

.article-content div.video-div.aod iframe {
    margin-bottom: -6px
}

.content-block.content-block-article .video-holder .video-div {
    min-height: 339px
}

.content-block.content-block-article .video-holder div.video-div>a.video-anc,
.article-content div.video-div>a.video-anc {
    position: relative;
    display: block;
    z-index: 0
}

.content-block.content-block-article .video-holder div.video-div>a.video-anc>img.video-image,
.article-content div.video-div>a.video-anc>img.video-image {
    position: relative;
    display: block;
    z-index: -1
}

.content-block.content-block-article .video-holder div.video-div>a.video-anc>img.video-image {
    height: 339px;
    object-fit: cover
}

.article-content div.video-div:not(.aod)>a.video-anc>img.video-image {
    width: 601px;
    height: 339px;
    object-fit: cover
}

.content-block.content-block-article .video-holder div.video-div>a.video-anc:after,
.article-content div.video-div:not(.aod)>a.video-anc:after {
    content: '';
    position: absolute;
    width: 69px;
    height: 69px;
    z-index: 10;
    left: 265px;
    background: url(images/play_button_large.png) no-repeat;
    top: 135px;
    margin-right: auto;
    margin-left: auto
}

.article-content div.video-div>a.video-anc:after {
    top: 17px;
    left: 12px
}

.content-block.content-block-article .video-holder div.video-div>a.video-anc:hover:after,
.article-content div.video-div>a.video-anc:hover:after {
    cursor: pointer;
    opacity: .6;
    background-position: 0 0
}

.article-content div.video-div div.video-desc {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 100%;
    color: #fff;
    font-size: 14px;
    line-height: 16px;
    padding: 2px 10px 0;
    margin: 0 1px;
    background: url(images/bg-desc.png)
}

.content-block.content-block-article .video-holder div.video-div div.video-desc {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 100%;
    color: #fff;
    font-size: 14px;
    line-height: 16px;
    padding: 2px 10px 4px;
    background: url(images/bg-desc.png)
}

.article-content div.video-div div.video-desc span {
    display: block;
    padding: 1px 0 3px
}

.content-block.content-block-article .video-holder div.video-div div.video-desc spann {
    display: block;
    padding: 3px 14px 5px
}

.article-content>span ul li {
    line-height: 18px
}

.mainarticles-img {
    width: 100%;
    height: 100%
}

.article-content div.article-img {
    position: relative;
    width: 304px;
    height: 227px;
    margin-right: 10px;
    float: left;
    margin-top: 6px
}

.article-content div.article-img div.image-desc {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 100%;
    color: #fff;
    font-size: 12px;
    line-height: 15px;
    background: url(images/bg-desc.png)
}

.article-content div.article-img div.image-desc span {
    display: block;
    padding: 3px 14px 5px
}

.info-block {
    float: left;
    background: #edf3fb;
    margin: 8px 10px 0 0;
    border-top: 12px solid #014bad;
    padding: 7px 18px;
    width: 108px;
    font-size: 14px;
    line-height: 15px
}

#main .post-container h2,
#main .info-block h3 {
    font-weight: bold;
    line-height: 16px;
    margin: 0;
    font: 16px/18px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif
}

#main .info-block p a,
#main .info-block h3 a {
    color: #000
}

#main .info-block p a:hover,
#main .info-block h3 a:hover {
    color: #014bad;
    text-decoration: none
}

#content {
    overflow: hidden;
    padding: 0
}

#content .bullet {
    float: right;
    margin: 8px -3px 0 6px;
    background: #014bad
}

#content p {
    margin: 0 0 20px
}

#content p a {
    color: #014bad
}

#content p a:hover {
    text-decoration: underline
}

#content .info-block p {
    margin: 0 0 10px
}

#content .article-list .img-holder {
    margin: 0 0 11px;
    position: relative;
    float: right;
    margin-left: 24px
}

#content>a,
#content span:not(.writer)>a {
    color: #014bad
}

#content>ul,
#content span>ul {
    padding: 9px 4px 17px 0;
    margin: 0;
    list-style: none
}

#content>ul li,
#content span>ul li {
    position: relative;
    padding: 0 21px 0 0;
    font-weight: bold
}

#content span>ul li a {
    font-weight: bold;
    text-decoration: underline
}

#content>ul li:before,
#content span>ul li:before {
    position: absolute;
    top: 9px;
    right: 0;
    content: '';
    width: 5px;
    height: 5px;
    background: url(images/sprite.png) no-repeat -11px 0
}

#content h2 {
    font-weight: bold;
    margin: 0 0 3px
}

#content .article-block .heading {
    margin-bottom: 13px
}

#content .article-list .league-title a {
    color: #969696;
    text-decoration: none
}

#content .article-list .league-title {
    display: block;
    font: 16px/18px 'blender-regular', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    line-height: 20px;
    padding: 0
}

a.print {
    display: inline-block;
    margin: -5px -2px 18px 0;
    vertical-align: top;
    background: url(images/sprite.png) no-repeat -202px -116px;
    overflow: hidden;
    text-indent: -9999px;
    width: 28px;
    height: 27px;
    float: left
}

.article-block {
    margin: 0 -2px 0 0
}

.article-list {
    border-bottom: 0 solid #e8e8e8;
    padding: 0;
    list-style: none;
    overflow: hidden;
    position: relative;
    line-height: 14px
}

.article-list:after {
    content: '';
    background: url(images/sprite.png) no-repeat 0 -299px;
    width: 465px;
    height: 25px;
    position: absolute;
    bottom: 0;
    right: 0
}

.blogs .article-list {
    padding: 0;
    margin-bottom: 15px
}

#main .article-list li {
    float: right;
    width: 25%;
    padding: 0 12px 0 7px;
    border-right: 1px solid #e8e8e8
}

#main .article-list li:first-child {
    padding-right: 0;
    border: 0;
    width: 144px;
    margin: 0 0 0 7px
}

#main .article-list li:first-child div.small,
#main .article-list li:first-child div.small a {
    width: 144px
}

#main .article-list img {
    display: block
}

#main .article-list h2 {
    font: 16px/18px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    margin: 0 0 2px 0;
    line-height: 16px;
    font-weight: bold;
    height: 50px;
    overflow: hidden
}

#main .article-list p.abstract {
    height: 64px;
    overflow: hidden;
    line-height: 16px;
    font-weight: normal;
    font-size: 14px
}

#main .article-list .writer-date {
    font-weight: normal;
    margin: 0
}

#main .containerHomePage .article-list .writer-date {
    width: 100%
}

#main .article-list3 .writer-date {
    margin-top: 10px;
    overflow: hidden
}

#main .article-list3 .writer-date .writer {
    height: 14px;
    display: block;
    overflow: hidden
}

#main .article-list .writer-date .writer a {
    color: #222;
    font-weight: normal
}

#main .article-list p.h3 {
    font-size: 15px
}

#main .blogs .article-list p.h3 {
    height: 54px;
    overflow: hidden
}

#content .article-list img {
    display: block;
    width: 210px;
    height: 156px
}

#content .article-list .dynamicBanner img {
    display: block;
    width: 125px;
    height: 62px
}

#content .share_icon img.icon {
    width: 11px;
    height: 11px;
    margin: 0;
    margin-left: 8px
}

#content .article-list p.abstract a {
    color: #222;
    font-family: blender-regular;
    display: block;
    line-height: 18px
}

#content .share_icon {
    position: relative;
    display: inline-block;
    margin-top: 5px;
    cursor: pointer;
    float: left
}

#content .writer-date {
    font: 10px/22px Arial, 'Mukta-ExtraBold', 'south web', Helvetica, sans-serif;
    color: #000;
    margin-top: 14px
}

#content .writer-date .writer {
    display: inline-block;
    bottom: 0;
    position: relative;
    font-size: 14px !important;
    font-family: 'blender-regular', 'Mukta-Medium', Arial, Helvetica, sans-serif;
    color: #222
}

#content .writer-date .date {
    display: inline-block;
    bottom: 0;
    position: relative;
    font-family: Arial, 'Mukta-ExtraBold', 'south web', Helvetica, sans-serif;
    font-size: 12px !important;
    color: #222
}

#content .divider {
    border-top: 1px solid #ebebeb;
    width: 648px;
    margin: 1px 0 10px 0;
    display: inline-block
}

#sidebarHomePage .dividerLeftSideBig {
    border-top: 1px solid #ebebeb;
    width: 304px;
    margin: 1px 0 12px 0;
    display: inline-block
}

#sidebarHomePage .dividerLeftSide {
    border-top: 1px solid #ebebeb;
    width: 304px;
    margin: 1px 0 10px 0;
    display: inline-block
}

#sidebarHomePage .dividerLeftSidePass {
    border-top: 1px solid #ebebeb;
    width: 304px;
    margin: 1px 0 4px 0;
    display: inline-block
}

#content .article-list h2 {
    font: 22px/23px 'blender-bold', Mukta-ExtraBold, 'south web bold', Arial, sans-serif;
    height: auto;
    overflow: hidden;
    line-height: 22px;
    width: 100%
}

#content .article-list p.abstract {
    height: 57px;
    overflow: hidden;
    line-height: 17px;
    font: 16px/22px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    width: 100%;
    color: #222;
    padding: 4px 0
}

#content .article-list h2 a {
    color: #222;
    text-decoration: none
}

#content .article-list li {
    width: 625px;
    padding: 12px 0 0 0;
    border-right: 0 solid #e8e8e8;
    text-align: justify
}

#content .article-list li.red a:hover {
    color: #d10202
}

#content .article-list li.orange a:hover {
    color: #ff5a00
}

#content .article-list li.bluetravel a:hover {
    color: #043959
}

#content .article-list li.lightblue a:hover {
    color: #0c9bd9
}

#content .article-list li.peach a:hover {
    color: #B8411F
}

#content .article-list li.green a:hover {
    color: #0e9b00
}

#content .article-list li.pink a:hover {
    color: #E96CA4
}

#content .article-list p {
    margin: 0 0 4px
}

#content .article-list p a {
    color: #000
}

#content .comment-block .heading {
    margin: 0;
    border: 0;
    border-bottom: 3px solid #014bad
}

#content .comment-box a.reply:hover {
    text-decoration: underline
}

#content .comment-box .h3 {
    font-size: 12px;
    margin: 0 0 0 7px;
    line-height: 14px;
    color: #1b1b1b;
    float: right;
    font-weight: bold
}

#content .comment-box .h3 a {
    color: #000;
    text-decoration: none
}

#content .comment-box .h3 a:hover {
    text-decoration: underline
}

#content .comment-box a {
    text-decoration: underline
}

#content .comment-box a:hover {
    text-decoration: none
}

#content .comment-box p {
    margin: 0 0 2px
}

#content .comment-box .box {
    padding: 0 11px 0 0;
    position: relative
}

#content .comment-box .box:after {
    content: '';
    background: url(images/sprite.png) no-repeat -11px 0;
    width: 5px;
    position: absolute;
    height: 5px;
    top: 4px;
    right: 1px
}

#content .tlkb_item_lev2 {
    padding-right: 20px
}

#content .tlkb_cont {
    min-height: 270px
}

#content .galleryWord {
    background: #1e518c none;
    overflow: hidden;
    position: relative;
    width: 448px;
    margin: 0 0 18px;
    padding: 13px 8px 0
}

#main .galleryWord2 .title,
#content .galleryWord .title {
    padding: 0 32px 2px;
    margin-top: 3px;
    color: #fff;
    display: block;
    font-size: 16px;
    line-height: 18px;
    font-weight: normal;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light'
}

#content .galleryWord .gmask {
    width: 448px;
    overflow: hidden;
    color: #fff;
    font-size: 14px;
    line-height: 16px;
    text-align: center
}

#content .galleryWord .gmask ul {
    width: 448px;
    height: 346px;
    position: relative;
    padding: 0;
    margin: 0;
    list-style: none;
    overflow: hidden
}

#content .galleryWord .gmask ul li {
    position: absolute;
    top: 0;
    z-index: 0;
    right: 0;
    width: 464px;
    height: 346px
}

#content .galleryWord .gmask .img-holder {
    position: relative
}

#content .galleryWord .gmask img {
    display: block;
    float: right
}

#content .galleryWord .thumbnails-holder {
    overflow: hidden;
    width: 448px
}

#content .galleryWord .thumbnails {
    overflow: hidden;
    position: relative;
    z-index: 1;
    width: 448px;
    padding: 8px 0
}

#content .galleryWord .thumbnails ul {
    padding: 0;
    margin: 0;
    width: 9999px;
    list-style: none;
    overflow: hidden
}

#content .galleryWord .thumbnails ul li {
    float: right;
    padding: 0 0 0 10px
}

#content .galleryWord .thumbnails ul li a {
    color: white
}

#content .galleryWord .thumbnails img {
    display: block
}

#content .galleryWord .btn-next,
#content .galleryWord .btn-prev {
    position: absolute;
    bottom: 47px;
    overflow: hidden;
    text-indent: -9999px;
    z-index: 10;
    right: 7px;
    width: 11px;
    height: 21px;
    background: #1e518c url(images/sprite.png) no-repeat -393px 0
}

#content .galleryWord .btn-next {
    right: auto;
    left: 8px;
    background-position: -379px 0
}

.article-list .img-holder {
    margin: 0 0 11px;
    position: relative
}

.article-list .img-holder img {
    display: block;
    width: 144px;
    height: 107px
}

.article-list .league-title {
    display: block;
    font-size: 12px;
    margin: 0 0 10px;
    line-height: 15px
}

.article-list .league-title a {
    color: #707070
}

.article-list .league-title a:hover {
    color: #014bad
}

.comment-block {
    position: relative;
    border: 1px solid #e8e8e8;
    margin: 0 -2px 18px -1px
}

.comment-block .control-block,
.comment-list .control-block {
    border: solid #fff;
    position: relative;
    padding: 4px 16px 5px 14px;
    font-size: 14px;
    line-height: 18px;
    border-width: 0 1px 1px;
    background: #fff;
    background: -moz-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(253, 253, 253, 1) 62%, rgba(248, 248, 248, 1) 79%, rgba(237, 237, 237, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(255, 255, 255, 1)), color-stop(62%, rgba(253, 253, 253, 1)), color-stop(79%, rgba(248, 248, 248, 1)), color-stop(100%, rgba(237, 237, 237, 1)));
    background: -webkit-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(253, 253, 253, 1) 62%, rgba(248, 248, 248, 1) 79%, rgba(237, 237, 237, 1) 100%);
    background: -o-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(253, 253, 253, 1) 62%, rgba(248, 248, 248, 1) 79%, rgba(237, 237, 237, 1) 100%);
    background: -ms-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(253, 253, 253, 1) 62%, rgba(248, 248, 248, 1) 79%, rgba(237, 237, 237, 1) 100%);
    background: linear-gradient(to bottom, rgba(255, 255, 255, 1) 0, rgba(253, 253, 253, 1) 62%, rgba(248, 248, 248, 1) 79%, rgba(237, 237, 237, 1) 100%)
}

.comment-list .tlkb_load_more a {
    text-align: center;
    display: block;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web'
}

.comment-list .tlkb_load_more a span {
    background: url(images/sprite.png) no-repeat 0 0;
    width: 7px;
    height: 8px;
    display: inline-block;
    margin-right: 5px
}

.comment-block .control-block:after {
    content: '';
    display: block;
    clear: both
}

.add-comment {
    float: left;
    border: 1px solid #e5e5e5;
    border-radius: 4px;
    -moz-border-radius: 4px;
    font-weight: bold;
    font-size: 12px;
    line-height: 15px;
    -webkit-border-radius: 4px;
    background: #fff;
    overflow: hidden;
    padding: 1px 26px 3px 8px;
    position: relative;
    background: -moz-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 6%, rgba(248, 248, 248, 1) 35%, rgba(236, 236, 236, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(255, 255, 255, 1)), color-stop(6%, rgba(249, 249, 249, 1)), color-stop(35%, rgba(248, 248, 248, 1)), color-stop(100%, rgba(236, 236, 236, 1)));
    background: -webkit-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 6%, rgba(248, 248, 248, 1) 35%, rgba(236, 236, 236, 1) 100%);
    background: -o-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 6%, rgba(248, 248, 248, 1) 35%, rgba(236, 236, 236, 1) 100%);
    background: -ms-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 6%, rgba(248, 248, 248, 1) 35%, rgba(236, 236, 236, 1) 100%);
    background: linear-gradient(to bottom, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 6%, rgba(248, 248, 248, 1) 35%, rgba(236, 236, 236, 1) 100%)
}

.add-comment:after {
    content: '';
    position: absolute;
    right: 7px;
    top: 3px;
    background: url(images/sprite.png) no-repeat -21px 0;
    width: 13px;
    height: 13px
}

.comment-block .control-block>.result {
    display: block;
    padding: 1px 0 0
}

.comment-block .result strong {
    font-size: 12px
}

.form-holder {
    z-index: 10;
    position: absolute;
    top: 100%;
    border: 1px solid #e8e8e8;
    background: #efefef;
    right: 0;
    width: 460px;
    padding: 1px;
    box-shadow: 0 0 1px #fff;
    -moz-box-shadow: 0 0 1px #fff;
    -webkit-box-shadow: 0 0 1px #fff
}

.form-holder:after {
    content: '';
    background: url(images/sprite.png) no-repeat 0 -325px;
    width: 463px;
    height: 17px;
    position: absolute;
    bottom: -17px;
    right: -1px
}

.form-holder img {
    display: block
}

.comment-list {
    padding: 0 16px 0 0;
    position: relative
}

.comment-box {
    padding: 0 0 12px;
    color: #707070;
    font-size: 12px;
    line-height: 14px
}

.comment-box .num {
    float: right;
    font-size: 12px;
    line-height: 15px;
    color: #626262;
    font-weight: bold;
    margin: 0 0 0 9px
}

.comment-box .holder {
    overflow: hidden
}

.comment-box .title {
    overflow: hidden;
    line-height: 15px
}

.comment-box .reply {
    color: #014bad;
    text-decoration: underline
}

.banner-alt {
    margin: 0
}

#footer {
    background: url(images/bg-footer.gif) repeat-x;
    overflow: hidden;
    padding: 31px 0 66px;
    margin: 0 -3px 0 -2px;
    color: #313131;
    font-size: 13px;
    line-height: 15px;
    display: table;
    width: 949px
}

body.with-bg #footer {
    padding-bottom: 0;
    margin-bottom: 66px
}

#footer .matrix-logo {
    float: left;
    margin-top: 20px
}

#footer .col {
    float: right;
    width: 158px
}

#footer .bullet {
    display: inline-block;
    vertical-align: top;
    margin: 0 12px 6px 0
}

#footer .holder {
    position: relative;
    padding: 0 14px 0 0
}

#footer .holder:after {
    content: '';
    display: block;
    clear: both
}

#footer .holder:before {
    height: 100%;
    background: #cad0d6;
    position: absolute;
    width: 1px;
    top: 0;
    right: -7px;
    content: ''
}

#footer .holder .frame {
    margin: -4px 0 -5px;
    float: right
}

#footer h2,
#footer strong.h2 {
    font-size: 14px;
    line-height: 18px;
    margin: 0 0 4px;
    font-weight: bold;
    display: block
}

#footer ul {
    padding: 0;
    margin: 0;
    list-style: none
}

#footer .col:first-child .holder {
    border: 0
}

#footer ul li {
    padding: 0 0 5px
}

#footer ul a {
    color: #535353;
    font-family: 'blender-regular', 'Mukta-Medium', Arial, Helvetica, sans-serif
}

#footer ul a:hover {
    color: #014bad
}

ul.drop-holder {
    padding: 0;
    float: right;
    width: 108px;
    margin: 0 -7px 0 8px;
    list-style: none;
    font-size: 12px;
    line-height: 15px
}

.drop-holder>li {
    padding: 2px 0 0;
    position: relative
}

.drop-holder li:first-child {
    padding: 0
}

.drop-holder>li>a {
    display: block;
    border-radius: 5px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    background: #e9e9e9;
    border: 1px solid #d9d9d9;
    color: #000;
    padding: 2px 5px 3px 22px;
    overflow: hidden;
    position: relative
}

.drop-holder>li>a:before {
    content: '';
    position: absolute;
    top: 2px;
    left: 3px;
    background: url(images/sprite.png) no-repeat -186px -100px;
    width: 16px;
    height: 16px
}

.drop-holder li:hover a {
    color: #000;
    text-decoration: none
}

.drop-holder li:hover a:before {
    background-position: -170px -100px
}

.drop-holder ul.drop {
    position: absolute;
    top: 100%;
    padding: 0;
    z-index: 10;
    display: none;
    margin: 0;
    overflow: hidden;
    list-style: none;
    width: 106px;
    border: 1px solid #c1bfbf;
    border-radius: 0 0 5px 5px;
    -moz-border-radius: 0 0 5px 5px;
    -webkit-border-bottom-right-radius: 5px;
    -webkit-border-bottom-left-radius: 5px;
    right: 0;
    box-shadow: 0 1px 1px #c1bfbf;
    -moz-box-shadow: 0 1px 1px #c1bfbf;
    -webkit-box-shadow: 0 1px 1px #c1bfbf;
    background: #f3f3f3
}

.drop-holder ul.drop li {
    border-top: 1px solid #c1bfbf;
    box-shadow: 0 1px 1px #fff inset;
    -moz-box-shadow: 0 1px 1px #fff inset;
    -webkit-box-shadow: 0 1px 1px #fff inset
}

.drop-holder ul.drop li:first-child {
    border: 0
}

.drop-holder ul.drop a {
    display: block;
    overflow: hidden;
    padding: 3px 5px 4px
}

.drop-holder ul.drop li:hover a {
    background: #fff
}

.drop-holder li:hover ul {
    display: block
}

.tab-container .gallery .green .heading {
    color: #61ac11
}

.tab-container .gallery .pink .heading {
    color: #E96CA4
}

#sidebar .heading2 {
    border: 1px solid #e8e8e8;
    text-align: right;
    margin: 0 0 16px;
    font-weight: normal;
    border-bottom: 3px solid #014bad;
    padding: 0
}

#sidebar .heading2 strong {
    padding: 7px 17px 0;
    display: block;
    border: 1px solid #fff;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    font-weight: normal
}

.video-alt .desc {
    background: 0;
    text-align: center;
    font-size: 16px;
    line-height: 20px
}

.video-alt {
    margin: 0 0 10px
}

.video-alt .desc a {
    padding: 8px 14px 10px
}

.video-list {
    overflow: hidden;
    margin: 0 0 -1px
}

.video-box {
    float: right;
    width: 144px;
    margin: 0 16px 0 0
}

.video-box:first-child,
.video-box-even {
    margin-right: 0 !important
}

.video-box .desc {
    font-size: 14px;
    line-height: 16px
}

.video-box .desc span {
    padding: 6px 14px 8px
}

.video-boxLeftSide {
    float: right;
    width: 140px;
    margin: 0 16px 0 0
}

.video-boxLeftSide:first-child,
.video-boxLeftSide-even {
    margin-right: 0 !important
}

.video-boxLeftSide .descHomepageLeftSide {
    font-size: 14px;
    line-height: 16px
}

.video-boxLeftSide .descHomepageLeftSide span {
    padding: 6px 14px 8px
}

.video-list .desc {
    height: 34px;
    overflow: hidden
}

.video-container.center {
    background: #141c27;
    width: 100%;
    position: relative;
    direction: ltr;
    padding: 5px 8px 5px
}

.video-container.center .video-list {
    height: 145px;
    width: 95%;
    margin: 0 0 0 17px
}

.video-container.center .video-list.slick-slider {
    margin-bottom: 0
}

#main .video-container.center .video-list .video-box {
    margin-right: 0 !important;
    width: 140px;
    padding: 0 0 0 5px;
    direction: rtl
}

.video-container.center .video-list .video-box-even {
    margin-right: 0 !important
}

.video-container.center .video-list .video-box:first-child {
    margin-right: 0 !important
}

.video-container.center .gallery-button.prev {
    background: url(images/video-arrow-left.png) no-repeat;
    position: absolute;
    top: 35%;
    left: 17px;
    cursor: pointer;
    height: 17px;
    width: 10px
}

.video-container.center .gallery-button.next {
    background: url(images/video-arrow-right.png) no-repeat;
    position: absolute;
    top: 35%;
    right: 2px;
    cursor: pointer;
    height: 17px;
    width: 10px
}

#sidebar .banner-list {
    padding: 0
}

#sidebar .banner-list-alt {
    overflow: hidden;
    margin: 0 0 0 -16px
}

#sidebar .banner-list-alt li {
    float: right;
    padding: 0 0 18px 16px
}

#sidebar .tab-box .gallery {
    margin: 0;
    font-size: 12px;
    border: solid #d4d4d4;
    border-width: 1px 1px 0
}

#sidebar .tab-box .gallery .btn-next,
#sidebar .tab-box .gallery .btn-prev {
    width: 17px;
    background-position: -297px 0;
    height: 29px
}

#sidebar .tab-box .gallery .btn-next {
    background-position: -314px 0;
    margin: 0 -1px 0 0;
    width: 16px
}

#sidebar .tab-box .gallery .btn-prev:hover {
    background-position: -298px -29px
}

#sidebar .tab-box .gallery .btn-next:hover {
    background-position: -314px -29px
}

#sidebar .tab-box .gallery .gmask {
    border: 0
}

#sidebar .tab-box .gallery ul {
    text-align: center
}

#sidebar .tab-box .gallery ul>li {
    height: 28px;
    padding: 0;
    width: 90px
}

#sidebar .tab-box .gallery ul>li a {
    padding: 1px 5px 3px;
    display: block;
    border: solid #fff;
    border-width: 0 1px 0 0
}

.tab-box .gallery ul a.active,
.tab-box .gallery ul li:hover a {
    text-decoration: none;
    color: #8e8e8e;
    background: url(images/sprite.png) no-repeat -237px -249px
}

#sidebar .tab-content {
    background: 0;
    padding: 0;
    border: 0
}

#sidebar .tab-box .gallery ul a {
    line-height: 25px
}

#sidebar .tab-content .premiership-block {
    position: relative
}

.placeholder {
    margin: 0 0 20px
}

.placeholder img {
    display: block
}

.post-list {
    padding-top: 8px
}

#sidebar .post-list.pos-rel {
    position: relative
}

#sidebar .post-list.pos-rel a.link-more {
    width: 14px;
    height: 12px;
    position: absolute;
    top: 17px;
    left: 5px
}

#sidebar .post-list .heading {
    border: 1px solid #e8e8e8;
    border-bottom: 3px solid #cb302f !important;
    margin: 0 0 14px;
    padding: 0
}

#sidebar .post-list .heading strong {
    padding: 7px 14px 3px;
    border: 1px solid #fff;
    display: block;
    text-align: right
}

#sidebar .post-list .heading strong a {
    background: url(images/text-heading.png) no-repeat;
    width: 56px;
    border: 1px solid #fff;
    overflow: hidden;
    display: block;
    text-indent: -9999px;
    height: 20px
}

#sidebar .post-list .heading .text-heading {
    background: url(images/text-heading2.png) no-repeat 0 2px;
    width: 61px
}

#sidebar .post-list .heading .text-heading3 {
    background: url(images/text-heading3.png) no-repeat 0 2px;
    width: 59px
}

#sidebar .post-list .heading .text-heading4 {
    background: url(images/text-heading4.png) no-repeat 0 0
}

#sidebar .post-list .heading.black {
    border-bottom: 3px solid #000 !important
}

#sidebar .post-holder {
    padding: 0 16px 3px;
    position: relative
}

#sidebar .post-holder:after {
    content: '';
    background: url(images/sprite.png) no-repeat 0 -342px;
    width: 304px;
    height: 18px;
    position: absolute;
    bottom: 0;
    right: 0
}

#sidebar .post {
    padding: 0 0 14px;
    overflow: hidden;
    line-height: 16px;
    font-size: 14px
}

#sidebar .post.tlkb_item_lev2 {
    padding-right: 20px
}

#sidebar .post>.img-holder {
    float: right;
    position: relative;
    margin: 2px 0 0 14px
}

#sidebar .post>.img-holder img {
    display: block
}

#sidebar .post .text-holder {
    overflow: hidden
}

#sidebar .post p {
    margin: 0;
    height: 32px;
    overflow: hidden
}

.banner-holder {
    position: relative;
    color: #fff;
    overflow: hidden;
    font-size: 16px;
    line-height: 18px;
    margin: 0 0 18px
}

.banner-right-fixed {
    right: 40px;
    top: 0;
    position: fixed;
    z-index: 999;
    margin-top: 61px
}

.banner-left-fixed {
    left: 40px;
    top: 0;
    position: fixed;
    z-index: 999;
    margin-top: 61px
}

.banner-holder-toto {
    text-align: center;
    margin-top: 2px
}

.banner-holder.visual-box {
    margin: 8px
}

.banner-holder .desc {
    position: absolute;
    right: 0;
    height: 346px;
    top: 0;
    z-index: 10;
    background: #014bad;
    width: 203px;
    padding: 10px 9px 0 10px;
    overflow: hidden
}

.mainarticle-world.banner-holder .desc {
    padding-top: 3px
}

.mainarticle-world.banner-holder .desc .main-article-writer {
    float: right;
    margin: 0 0 0 20px
}

.mainarticle-world.banner-holder .desc .talkbacksHomePage {
    display: table;
    padding: 0 20px 0 0;
    background: url(Images/whitecomm.png) no-repeat 100%
}

.mainarticle-league.banner-holder .desc {
    background: white;
    padding: 0 5px 0 5px;
    border-right: 12px solid #014bad;
    width: 203px;
    color: black
}

.mainarticle-league.banner-holder .desc .main-article-writer {
    float: right;
    margin: 0 0 0 20px
}

.article-page .main-block .hint .writer-date {
    display: inline-block;
    float: left;
    margin: -2px 32px 0 0
}

.talkbacks-svg svg {
    left: 10px;
    position: absolute
}

.talkbacks-svg {
    font: 11px/22px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    float: left;
    position: relative;
    display: inline-block;
    height: 24px;
    width: 41px;
    margin: 0
}

.content-block-article .talkbacks-svg {
    margin: 8px 0 0 0
}

.talkbacks-svg span.talkbacksText {
    font-size: 11px;
    float: left;
    margin: -2px 0 0 0;
    width: 52px
}

.containerHomePage .talkbacks-svg span.talkbacksText {
    margin: -2px 0 0 0
}

.article-page .talkbacks-svg span.talkbacksText {
    width: 48px
}

.talkbacks-svg .talkbacksText .messages-count a {
    margin: 0 5px 0 0;
    display: block;
    font-size: 12px;
    font-family: blender-bold
}

.desc .talkbacks-container {
    height: 25px
}

.desc .talkbacks-svg .messages-count {
    width: 27px;
    position: absolute
}

.world-page .desc .talkbacks-svg .messages-count {
    top: 3px;
    left: 5px
}

.league-page .desc .talkbacks-svg .messages-count {
    top: 1px;
    left: 1px
}

.desc .talkbacks-svg .talkbacksText {
    width: 33px;
    height: 18px
}

#main .mainarticle-league.banner-holder h1 {
    color: #707070
}

#main .mainarticle-league.banner-holder h2 a,
#main .mainarticle-league.banner-holder h3 a,
#main .mainarticle-league.banner-holder p.h3 a {
    color: #000
}

.mainarticle-league.banner-holder .date {
    color: #707070
}

#main .mainarticle-league.banner-holder h3 {
    width: 165px
}

.banner-holder .img-holder {
    position: relative;
    float: left
}

.banner-holder img {
    display: block
}

.banner-holder .img-holder a.article-mag,
.gallery2 .img-holder a.article-mag,
.video-holder a.article-mag {
    background: url(images/sprite2.png) no-repeat 0 0;
    position: absolute;
    bottom: 2px;
    right: 67px;
    width: 32px;
    height: 32px
}

.video-holder a.article-mag {
    bottom: 25px;
    right: 5px
}

.gallery2 .img-holder a.article-mag {
    right: 0;
    bottom: -40px
}

.banner-holder .img-holder a.article-mag:after,
.gallery2 .img-holder a.article-mag:after,
.video-holder a.article-mag:after {
    background: 0
}

#main .content-holder .banner-holder {
    letter-spacing: -0.045em
}

#main .banner-holder h1,
#main .visual-container h1 {
    color: white;
    font-size: 12px;
    margin-bottom: 15px;
    margin-top: 5px
}

#main .banner-holder h1 {
    margin-right: 5px
}

#main .visual-container h1 {
    margin: 0 0 2px 0;
    line-height: 15px
}

#main .banner-holder h2 {
    display: block;
    margin: 0 -1px 11px 0;
    font-size: 30px;
    line-height: 30px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    font-weight: normal
}

.banner-holder .date,
.banner-holder .title a {
    color: #fff;
    padding: 0 !important
}

.banner-holder span {
    font-size: 12px;
    line-height: 12px;
    display: block
}

.banner-holder .writer-talkback {
    display: table;
    position: relative
}

.main-hp .banner-holder .writer-talkback {
    display: inline-block;
    width: 182px;
    height: 25px
}

.main-hp .banner-holder .writer-talkback span:first-child {
    display: inline-block;
    margin: 0 0 0 15px;
    border: solid 0;
    width: 180px;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden
}

.main-hp .writer-date {
    width: 180px;
    height: 25px
}

.main-hp .writer-date .share_icon {
    width: 50px;
    height: 100%
}

.main-hp .writer-date .share_icon .share_bar {
    width: 50px;
    height: 100%;
    display: inline-block
}

.main-hp .writer-date .share_icon .share_bar ul {
    width: 100%;
    height: 25px;
    color: #fff
}

.banner-holder .writer-talkback span:first-child {
    display: inline-block;
    margin: 0 0 0 15px
}

.banner-holder .writer-talkback .talkbacks {
    display: inline-block;
    padding: 0 20px 0 0;
    margin-left: 15px;
    background: url(Images/whitecomm.png) no-repeat 100%
}

.banner-holder .writer-talkback .talkbacksHomePage {
    display: inline-block;
    padding: 0 20px 0 0;
    margin-left: 15px;
    background: url(Images/whitecomm.png) no-repeat 100%
}

.banner-holder.cm8 span {
    line-height: 0
}

#main .banner-holder p.h3,
#main .banner-holder h3 {
    margin: 0 0 8px;
    font-size: 16px;
    line-height: 18px;
    text-align: justify
}

#main .banner-holder a {
    padding: 0 !important
}

#main .banner-holder a:hover {
    color: #fff
}

#main .content-holder .article-list {
    padding: 0 0 9px;
    margin-bottom: 13px
}

#main .article-list-alt:after {
    display: none
}

#main .content-holder .article-list p {
    height: 56px;
    overflow: hidden
}

#main .content-holder .article-list:after {
    display: none
}

#main .content-holder .article-list h2 {
    font-weight: bold;
    line-height: 16px;
    font-size: 15px;
    margin: 0 0 8px;
    height: 32px;
    overflow: hidden
}

.post-container {
    overflow: hidden
}

.people-block .box-holder {
    margin: 0 0 18px;
    padding: 12px 8px 5px;
    background: #141c27 url(images/bg-video.jpg) no-repeat -304px 0
}

#main .people-block .article .heading,
#main .people-block .box-holder .heading {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    color: #fff;
    font-size: 14px;
    line-height: 18px;
    display: block;
    margin: 0;
    border: 0;
    padding: 3px 22px 1px;
    font-weight: normal
}

.people-blockHomepage .talkbacks {
    color: white;
    font-style: normal;
    font-size: 10px;
    display: inline-block;
    padding: 0 10px 2px 10px;
    left: 1px;
    position: absolute;
    width: 35px;
    height: 23px;
    padding: 1px 0 0 0;
    vertical-align: middle;
    text-align: center
}

.writer-dateTalent {
    color: white;
    font-style: normal;
    font-size: 11px;
    display: inline-block;
    margin-top: 0;
    left: 5px;
    position: absolute;
    text-align: center
}

.people-block {
    width: 144px;
    margin-bottom: 18px;
    font-family: 'blender-regular', 'Mukta-ExtraBold', 'south web bold'
}

.people-block .box {
    font-weight: bold;
    line-height: 16px;
    font-size: 15px;
    margin: 0 0 11px
}

.people-block .box .talkbacks {
    display: block;
    color: #fff;
    font-weight: normal;
    font-size: 12px;
    padding: 0 20px 0 0;
    background: url(Images/whitecomm.png) no-repeat 100%
}

.people-block .figure {
    position: relative;
    display: block;
    width: 128px;
    height: 90px
}

.people-block .figure span {
    position: absolute;
    right: 0;
    bottom: 0;
    font-size: 12px;
    color: #fff;
    font-weight: bold;
    background: #014bad;
    padding: 0 6px;
    line-height: 15px;
    width: 84px
}

.people-block .figure span:after {
    background: url(images/bg-title.gif) no-repeat;
    width: 5px;
    height: 15px;
    position: absolute;
    left: -5px;
    bottom: 0;
    content: ''
}

.people-block .box img {
    display: block
}

.people-block .post-holder {
    background: #fff
}

.people-block .place-holder {
    padding: 0 1px 0 0
}

.people-block .place-holder a {
    width: 45%;
    display: inline-block
}

#main .blockquote .place-holder a {
    display: inline-block;
    padding: 0 10px 0;
    position: relative;
    line-height: 12px
}

.people-block .place-holder img,
#main .blockquote .place-holder img {
    display: inline-block;
    margin-left: 3px;
    position: absolute;
    min-width: 11px
}

.people-block .place-holder span,
#main .blockquote .place-holder span {
    color: #e8e8e8;
    font-size: 11px;
    margin-right: 15px
}

.people-block .place-holder span:hover,
#main .blockquote .place-holder span:hover {
    color: #aaa
}

.people-block .holder {
    padding: 5px 5px 7px
}

#main .people-block p {
    margin: 0 0 8px
}

#main .people-block p a {
    color: #fff
}

#main .people-block p a:hover {
    color: #aaa
}

#main .people-block .box p {
    overflow: hidden
}

.people-block .article {
    font-size: 14px;
    line-height: 14px;
    padding: 13px 8px 10px;
    color: #fff;
    background: #151b24 url(images/bg-video.jpg) no-repeat -304px 0
}

.people-block .article .img-holder {
    position: relative;
    overflow: hidden
}

.people-block .article img {
    display: block
}

.people-block .article .holder {
    background: #014bad;
    min-height: 107px
}

.people-block .article strong {
    display: block;
    margin: 0 1px 6px 0;
    font-size: 12px;
    line-height: 15px
}

#main .people-block .article p a,
.people-block .article strong a,
#main .people-block .article h3 a {
    color: #fff
}

#main .people-block .article p.h3 {
    font-size: 16px;
    line-height: 18px;
    margin: 0 0 12px;
    font-weight: normal
}

#main .people-block .date {
    color: #e8e8e8;
    padding: 0 2px 3px
}

#main .people-block .article .date {
    padding: 0 5px 9px
}

#main .people-block .article p {
    margin: 0 0 6px;
    overflow: hidden
}

.post-container .post-holder {
    background: #fff;
    padding: 0 2px 18px 0;
    overflow: hidden
}

.post-container .post {
    overflow: hidden;
    line-height: 14px;
    font-size: 14px;
    display: block;
    padding: 8px 0 7px 16px;
    border-top: 1px solid #e8e8e8
}

.post-container .post-holder .post:last-child {
    padding-bottom: 0
}

.post-container .post-alt {
    border-top: 1px solid transparent;
    padding-bottom: 8px;
    margin: 0 0 -1px;
    position: relative;
    z-index: 2;
    background: #fff
}

.post-container .post-alt .holder {
    background: #f4f4f4
}

.post-container .post .holder {
    overflow: hidden;
    padding: 0 0 0 16px;
    margin: 0 0 0 -16px;
    line-height: 20px
}

.post-container .post:first-child {
    border: 0;
    padding-top: 0
}

.post-container .post .img-holder {
    float: right;
    margin: 0 0 0 16px;
    position: relative
}

.post-container .post img {
    display: block
}

.post-container .post .text-holder {
    overflow: hidden;
    padding: 2px 0 0
}

.post-container .post .title {
    display: block;
    font-size: 12px;
    margin: 0 0 3px;
    line-height: 15px
}

.post-container .post .title a {
    color: #707070
}

.post-container .post-alt .title {
    color: #707070
}

.post-container .post .title a:hover {
    color: #014bad
}

#main .post-container .post p {
    margin: 3px 0 4px 0;
    font: 14px 'blender-regular', Arial, Helvetica, sans-serif;
    line-height: 16px
}

.post-container .post .title a {
    font-family: 'blender-regular', Arial, Helvetica, sans-serif !important;
    color: #969696 !important
}

.world-page .post-container h2 a,
.league-page .post-container h2 a {
    font-family: 'blender-bold', Arial, Helvetica, sans-serif !important
}

.world-page .league-title a,
.league-page .league-title a {
    font-family: 'blender-regular', Arial, Helvetica, sans-serif !important;
    color: #969696;
    font-weight: 400
}

.world-page #content {
    width: 100%
}

.aside-alt {
    padding: 0 0 0 13px
}

.aside-alt .heading {
    margin: 0 0 13px
}

.news-block {
    margin: 0 0 18px
}

#main .news-block h2 {
    font-weight: bold;
    margin: 0 5px 10px 0
}

.news-block ul {
    padding: 0;
    position: relative;
    margin: 0;
    list-style: none;
    font-size: 14px;
    line-height: 16px
}

.news-block ul li {
    position: relative;
    z-index: 10;
    padding: 15px 0 13px;
    line-height: 20px
}

.news-block ul li:before {
    content: '';
    position: absolute;
    top: 0;
    right: 52px;
    background: url(images/sprite.png) no-repeat -41px 0;
    width: 23px;
    height: 5px
}

.news-block ul li:first-child {
    padding-top: 0
}

.news-block ul li:first-child:before {
    display: none
}

.items-container {
    width: 448px;
    padding: 13px 8px 0;
    overflow: hidden;
    background: #141c27 url(images/bg-video.jpg) no-repeat -448px 0;
    margin: 0 0 19px
}

.items-container .title {
    overflow: hidden;
    display: block;
    padding: 0 34px 5px;
    font-size: 14px;
    line-height: 18px;
    color: #fff;
    font-weight: bold;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold'
}

.items-container>.holder {
    overflow: hidden;
    margin: 0 0 -4px -14px
}

.items-container>.holder .item:first-child {
    margin-right: 0
}

.items-container .item {
    float: right;
    margin: 0 8px 8px 0;
    padding: 0 0 24px;
    position: relative
}

.items-container .item .img-holder {
    position: relative
}

.items-container .item img {
    display: block
}

#main .items-container .item .desc a {
    padding: 0;
    max-height: 16px;
    overflow: hidden;
    display: inline
}

#main .items-container .item .desc {
    background: #ffa200;
    font-size: 14px;
    position: absolute;
    bottom: 0;
    right: 0;
    line-height: 16px;
    text-align: center;
    width: 134px;
    color: #fff;
    padding: 4px 5px 4px
}

#main .items-container .item.big {
    width: 464px;
    float: none;
    margin: 0 0 8px;
    padding-bottom: 0
}

#main .items-container .item.big .desc {
    width: 438px;
    padding-bottom: 5px
}

#main .items-container .item.big .desc a {
    max-height: 32px;
    overflow: hidden
}

.galleryWord .pos-rel {
    height: 346px
}

.galleryWord2-flat .desc {
    bottom: 6px;
    padding: 2px 10px 4px;
    margin: 0 8px;
    width: 428px;
    font-size: 14px;
    line-height: 16px
}

#main .galleryWord2,
#content .galleryWord {
    background: #000 none;
    overflow: hidden;
    position: relative;
    width: 465px;
    margin: 0 0 18px;
    padding: 13px 8px 0;
    background: #141c27 url(images/bg-video.jpg) no-repeat -912px 0;
    overflow: hidden;
    position: relative;
    width: 464px;
    margin: 1px 1px 7px;
    padding: 13px 8px 0
}

#content .gallery {
    position: relative
}

#main .gallery2 {
    background: #141c27 url(images/bg-video.jpg) no-repeat -912px 0;
    overflow: hidden;
    position: relative;
    width: 100%;
    margin: 0 0 18px
}

#main .gallery2 .title,
#content .gallery .title {
    padding: 0 34px 5px;
    color: #fff;
    display: block;
    font-size: 14px;
    line-height: 18px;
    font-weight: bold;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold'
}

#content .gallery .gmask {
    width: 600px;
    overflow: hidden;
    color: #fff;
    font-size: 14px;
    line-height: 16px;
    text-align: center
}

#content .gallery .gmask ul {
    width: 600px;
    height: 400px;
    position: relative;
    padding: 0;
    margin: 0;
    list-style: none;
    overflow: hidden
}

#content .gallery .gmask ul li {
    position: absolute;
    top: 0;
    z-index: 0;
    right: 0;
    width: 600px;
    height: 400px
}

#content .gallery .gmask .img-holder {
    position: relative
}

#content .gallery .gmask img {
    display: block;
    float: right
}

#content .gallery img {
    width: 600px
}

#content .gmask .holder {
    padding: 4px 5px 5px;
    position: absolute;
    bottom: 0;
    background: #014bad;
    right: 0;
    width: 450px
}

#content .gallery .thumbnails-holder {
    overflow: hidden;
    width: 600px
}

#content .gallery .thumbnails {
    overflow: hidden;
    position: relative;
    z-index: 1;
    width: 600px;
    padding: 8px 0
}

#content .gallery .thumbnails ul {
    padding: 0;
    margin: 0;
    width: 9999px;
    list-style: none;
    overflow: hidden
}

#content .gallery .thumbnails ul li {
    float: right;
    padding: 0 0 0 4px
}

#content .gallery .thumbnails img {
    display: block
}

#content .gallery .btn-next,
#content .gallery .btn-prev {
    position: absolute;
    bottom: 47px;
    overflow: hidden;
    text-indent: -9999px;
    z-index: 10;
    right: 7px;
    width: 13px;
    height: 26px;
    background: #141c27 url(images/sprite.png) no-repeat -393px 0
}

#content .gallery .btn-next {
    right: auto;
    left: 8px;
    background-position: -379px 0
}

#content .gallery3 {
    overflow: hidden;
    margin: 0 0 16px
}

#content .gallery3 .gmask {
    position: relative;
    overflow: hidden;
    border: 0
}

.gallery3 .gmask ul {
    padding: 0;
    margin: 0;
    list-style: none;
    overflow: hidden
}

.gallery3 .gmask ul li {
    float: right;
    width: 600px !important;
    position: static !important
}

.gallery3 .gmask ul img {
    display: block
}

.gallery3 .pagination {
    position: absolute;
    width: 600px;
    bottom: 0;
    right: 0;
    text-align: center;
    font-size: 12px;
    line-height: 15px;
    padding: 3px 0 4px;
    background: url(images/bg-desc.png)
}

.gallery3 .pagination ul {
    padding: 0;
    margin: 0;
    list-style: none;
    direction: ltr
}

.gallery3 .pagination ul li {
    display: inline-block;
    vertical-align: top;
    margin-left: -3px
}

.gallery3 .pagination ul li:first-child {
    margin: 0
}

.gallery3 .desc {
    position: absolute;
    right: 0;
    bottom: 22px;
    width: 100%;
    color: #fff;
    font-size: 14px;
    line-height: 15px;
    padding: 4px 0;
    background: url(images/bg-desc.png)
}

.gallery3 .desc span {
    display: block
}

.gallery .pos-rel {
    height: 400px
}

#content .gallery3 .pagination ul a {
    color: #fff;
    padding: 1px 0 0;
    display: block;
    width: 17px
}

#content .gallery3 .pagination ul a:hover,
#content .gallery3 .pagination ul li.active a {
    background: #014bad
}

.news-list {
    padding: 0;
    line-height: 16px;
    font-size: 14px;
    margin: 0;
    list-style: none
}

.news-list li {
    position: relative;
    padding: 8px 0 11px 10px
}

.news-list li:before {
    position: absolute;
    top: 0;
    right: 0;
    content: '';
    width: 5px;
    height: 5px;
    background: url(images/sprite.png) no-repeat -11px 0
}

#world-center {
    width: 465px;
    float: left
}

#world-center .m-class {
    margin-bottom: 16px
}

#world-center .m-class:last-child {
    margin-bottom: 0
}

#world-right {
    width: 112px
}

.heading a.fb-icon {
    background: url(images/facebook-icon.png) no-repeat;
    width: 22px;
    height: 22px;
    display: inline-block;
    float: left
}

.popup-container {
    position: absolute;
    left: -1px;
    top: -20px;
    z-index: 10
}

.popup-box {
    overflow: hidden
}

.popup-frame {
    overflow: hidden
}

.no-talkbacks .popup-container {
    position: relative;
    left: 18px;
    overflow: visible !important
}

.no-talkbacks .popup-box,
.no-talkbacks .popup-holder,
.no-talkbacks .popup-frame {
    overflow: visible
}

.tlkb_form_wrapper {
    background: #e8e8e8
}

.comment-form {
    overflow: hidden;
    width: 458px;
    direction: rtl;
    background-color: #efefef;
    padding: 1px 1px 1px 5px
}

.left-tlkb.comment-form,
.tlkb_form_wrapper.left-tlkb .comment-form {
    width: 299px
}

.tlkb_form {
    background-color: #efefef;
    border: 1px solid white;
    padding: 1px
}

.comment-form .comment-area {
    overflow: hidden;
    float: right;
    background: url(Images/tb-inputs.png) right 0 no-repeat;
    margin: 8px 11px 0 0
}

.tlkb_form_wrapper.left-tlkb .comment-form .comment-area {
    background: 0
}

.comment-form .comment-area input {
    float: left;
    padding: 0 8px;
    border: 0;
    background: 0;
    height: 29px;
    width: 183px;
    color: #a0a0a0;
    font-size: 12px
}

.tlkb_form_wrapper.left-tlkb .comment-form .comment-area input {
    width: 183px;
    background: white;
    border: 1px solid #a0a0a0;
    width: 160px
}

.comment-form .comment-area-wide {
    background-position: right -38px;
    margin: 8px 11px 8px 0
}

.comment-form .comment-area-wide input {
    width: 417px
}

.comment-form .area {
    overflow: hidden;
    margin: 0 11px 0 0;
    width: 433px
}

.comment-form .comments-box {
    overflow: hidden;
    width: 433px
}

.comment-form .comments-frame {
    background: url(Images/tb-inputs.png) right -75px no-repeat
}

.tlkb_form_wrapper.left-tlkb .comment-form .comments-frame {
    background: 0
}

.comment-form .comments-box textarea {
    background: 0;
    border: 0;
    overflow: auto;
    color: #a0a0a0;
    padding: 0 8px;
    width: 417px !important;
    min-height: 69px;
    line-height: 20px;
    resize: none;
    font-size: 12px
}

.tlkb_form_wrapper.left-tlkb .comments-box textarea {
    width: 263px !important;
    background: #fff;
    border: 1px solid #a0a0a0
}

.comment-form .btn-section {
    overflow: hidden;
    padding: 0 0 6px
}

.btn-tb {
    text-align: center;
    border: 1px solid #e5e5e5;
    border-radius: 4px;
    -moz-border-radius: 4px;
    font-weight: bold;
    font-size: 12px;
    line-height: 15px;
    -webkit-border-radius: 4px;
    background: #fff;
    overflow: hidden;
    padding: 3px 10px;
    position: relative;
    background: -moz-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 6%, rgba(248, 248, 248, 1) 35%, rgba(236, 236, 236, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(255, 255, 255, 1)), color-stop(6%, rgba(249, 249, 249, 1)), color-stop(35%, rgba(248, 248, 248, 1)), color-stop(100%, rgba(236, 236, 236, 1)));
    background: -webkit-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 6%, rgba(248, 248, 248, 1) 35%, rgba(236, 236, 236, 1) 100%);
    background: -o-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 6%, rgba(248, 248, 248, 1) 35%, rgba(236, 236, 236, 1) 100%);
    background: -ms-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 6%, rgba(248, 248, 248, 1) 35%, rgba(236, 236, 236, 1) 100%);
    background: linear-gradient(to bottom, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 6%, rgba(248, 248, 248, 1) 35%, rgba(236, 236, 236, 1) 100%)
}

.comment-form .btn-contact {
    width: 66px;
    color: #014bad;
    cursor: pointer;
    margin-top: 10px
}

.comment-form .btn-contact:hover {
    text-decoration: underline
}

.comment-form .tlkb_form_button_cancel {
    float: right;
    margin-right: 10px
}

.comment-form .tlkb_form_button_send {
    float: left;
    margin-left: 15px
}

.comment-form .facebook-holder {
    float: left;
    margin-left: 15px;
    margin-top: 9px;
    cursor: pointer
}

.tlkb_form_wrapper.left-tlkb .comment-form .facebook-holder {
    margin-top: 13px;
    margin-left: 8px
}

.tb-message {
    display: block;
    float: none;
    margin: 0 auto;
    min-height: 196px
}

.tb-message .tlkb_form_msg_success {
    text-align: center;
    margin: 45px 0 10px
}

.tb-message .tlkb_form_msg_success .a {
    font-weight: bold;
    font-size: 22px
}

.tb-message .tlkb_form_msg_success .b {
    font-weight: bold;
    font-size: 18px;
    margin-bottom: 5px
}

.tb-message .tlkb_form_msg_success .c {
    font-size: 14px
}

.tb-message .tlkb_form_button_cancel {
    float: none;
    display: block;
    margin: 0 auto 10px
}

.social-networks .popup-holder {
    position: relative
}

.social-networks-wide .popup-holder {
    position: relative
}

.social-networks .popup-holder .popup {
    position: absolute;
    right: 68px;
    display: none;
    top: -77px;
    border: 1px solid #c3cad1;
    z-index: 100;
    box-shadow: 3px 3px 2px #b6b8b8;
    width: 260px;
    background: #efefef;
    height: 482px
}

.social-networks-wide .popup-holder .popup {
    position: absolute;
    right: 68px;
    display: none;
    top: -77px;
    border: 1px solid #c3cad1;
    z-index: 100;
    box-shadow: 3px 3px 2px #b6b8b8;
    width: 260px;
    background: #efefef;
    height: 50px
}

.social-networks .popup-holder .popup:before {
    position: absolute;
    content: '';
    border: 9px solid #efefef;
    -moz-transform: rotate(-45deg);
    -webkit-transform: rotate(-45deg);
    transform: rotate(-45deg);
    right: -6px;
    box-shadow: 1px 5px 2px #b6b8b8;
    top: 78px
}

.social-networks-wide .popup-holder .popup:before {
    position: absolute;
    content: '';
    border: 9px solid #efefef;
    -moz-transform: rotate(-45deg);
    -webkit-transform: rotate(-45deg);
    transform: rotate(-45deg);
    right: -6px;
    box-shadow: 1px 5px 2px #b6b8b8;
    top: 78px
}

.message-form {
    background: #efefef;
    position: relative;
    z-index: 2;
    font-size: 12px;
    line-height: 15px;
    width: 260px;
    overflow: hidden;
    margin: 0
}

.message-form .holder {
    padding: 14px 25px 5px;
    min-height: 420px
}

.message-form label {
    display: block;
    margin: 0 0 7px
}

.message-form textarea,
.message-form input[type="text"] {
    width: 210px;
    border: 1px solid #dcdcdc;
    box-shadow: 1px 1px #949494 inset;
    background: #fff;
    margin: 0 0 11px;
    padding: 6px 5px
}

.message-form textarea {
    width: 210px !important;
    height: 69px !important;
    overflow: auto;
    margin: 0 0 7px
}

.message-form .row {
    overflow: hidden;
    margin: 0 -2px 18px
}

.message-form .row img {
    float: right;
    margin: 0 0 0 11px
}

.message-form .row input[type="text"] {
    float: right;
    margin: 7px 0 0;
    width: 77px
}

.message-form .btn-holder {
    border: solid #fff;
    overflow: hidden;
    border-width: 1px 0;
    padding: 8px 20px 11px 20px
}

.message-form .cancel,
.message-form input[type="submit"] {
    float: left;
    width: 55px;
    border: 1px solid #c3cad1;
    font-size: 12px;
    line-height: 15px;
    color: #014bad;
    font-weight: bold;
    cursor: pointer;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background: url(images/bg-submit.gif) repeat-x;
    -webkit-border-radius: 5px;
    margin: 1px 0 0;
    padding: 0;
    height: 21px
}

.message-form input[type="submit"]:hover {
    cursor: pointer
}

.message-form .cancel {
    float: right;
    height: 19px;
    text-indent: 0;
    text-align: center;
    line-height: 18px;
    width: 53px
}

.message-form .warning {
    color: #f00;
    font-size: 12px;
    margin-left: 10px
}

.video-container {
    background: #141c27 url(images/bg-video.jpg) no-repeat;
    width: 304px;
    margin: 0 0 18px;
    padding: 13px 8px 10px
}

.video-container a.vod {
    width: 26px;
    height: 22px;
    display: inline-block;
    position: absolute;
    left: 0
}

.video-container .desc {
    position: static;
    width: auto;
    padding: 10px 16px 0 14px
}

#main .video-container .desc a {
    display: inline;
    padding: 0
}

#main .video-container .desc a:hover {
    color: #aaa
}

.schedule-block .title,
.play-block .title,
.video-container .title,
#main .video-container .title h2 {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web';
    font-size: 14px;
    line-height: 18px;
    color: #fff;
    display: block;
    padding: 3px 34px 2px;
    font-weight: normal
}

#main .video-container .title h2 {
    letter-spacing: 0;
    margin: 0;
    padding: 0
}

#main .video-container .video-list .desc {
    padding: 4px 14px 0
}

#main .video-container .video-box {
    width: 140px;
    padding: 0;
    margin-right: 8px;
    overflow: hidden
}

.logos-holder {
    width: 944px;
    overflow: hidden;
    border: 1px solid #c3cad1;
    margin: 0 0 15px;
    background: url(images/bg-logos.gif) repeat-x
}

.logos {
    margin: 0;
    width: 946px;
    padding: 0;
    list-style: none;
    display: table
}

.logos li {
    height: 62px;
    display: table-cell;
    vertical-align: middle;
    text-align: center;
    background: url(images/separator.gif) repeat-y
}

.logos li a {
    display: block
}

.logos li:hover {
    background-color: #ededed
}

.logos li.separator {
    width: 56px;
    height: 61px;
    padding: 0 0 0 2px
}

.logos img {
    vertical-align: top
}

.logos-list {
    overflow: hidden;
    border: solid #c3cad0;
    border-width: 1px 0 1px 1px;
    background: #f8f8f8;
    margin: 0 0 23px
}

.logos-list ul {
    padding: 0;
    margin: 0 0 -1px -5px;
    overflow: hidden;
    list-style: none
}

.logos-list li {
    float: right;
    padding: 0 31px 0 0
}

.logos-list li:first-child {
    padding: 0
}

.logos-list table {
    border-collapse: collapse;
    width: 100%
}

.logos-list table td {
    vertical-align: middle;
    width: 42px;
    height: 39px;
    text-align: center;
    border-left: 1px solid #c3cad0;
    border-right: 1px solid #c3cad2;
    border-top: 1px solid #fff;
    border-bottom: 1px solid #c3cad0
}

.logos-list table td img {
    vertical-align: top
}

.post-box {
    margin: -15px 0 18px;
    border: solid #c3cad1;
    border-width: 0 1px 1px;
    background: #fff;
    padding: 1px
}

.post-box>.holder {
    background: #f0f1f3;
    padding: 15px 12px 0 16px;
    margin: 0 0 -3px
}

.post-box .post {
    border: 0;
    padding: 0 0 21px
}

.post-box .post .img-holder {
    display: block;
    width: 112px;
    margin: 0 0 6px
}

#main .post-box .post h3 {
    font-weight: normal;
    margin: 0 0 5px
}

#main .post-box .date {
    padding: 0 3px
}

.link-holder {
    letter-spacing: 0;
    border-top: 2px solid #fff;
    font-size: 12px;
    line-height: 16px;
    font-weight: bold;
    padding: 16px 10px 10px 5px;
    background: #f5f5f5;
    background: -moz-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(237, 237, 237, 1)), color-stop(19%, rgba(248, 248, 248, 1)), color-stop(35%, rgba(253, 253, 253, 1)), color-stop(100%, rgba(255, 255, 255, 1)));
    background: -webkit-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -o-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -ms-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: linear-gradient(to bottom, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%)
}

.link-holder a {
    color: #014bad
}

.link-holder a span {
    background: url(images/sprite.png) no-repeat 0 0;
    width: 8px;
    height: 8px;
    display: inline-block;
    margin-right: 5px
}

.news-alt {
    padding-bottom: 21px !important
}

.goal-list {
    padding: 0 0 1px;
    margin: 0 0 0 -18px;
    list-style: none
}

.goal-list:after {
    content: '';
    display: block;
    clear: both
}

.goal-list li {
    float: right;
    position: relative;
    margin: 0 0 25px 20px;
    border: 1px solid #c3cad1;
    width: 140px;
    height: 265px
}

.modal-cont {
    display: none
}

.modal-cont .popup,
.goal-list .popup,
.cycle-games-popup .popup {
    position: absolute;
    display: none;
    width: 357px;
    background: #fff;
    z-index: 1000;
    top: -50px;
    right: -450px;
    padding: 15px 12px 8px;
    border-radius: 2px;
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    box-shadow: 0 0 16px #000
}

.modal-cont .popup {
    top: auto;
    right: 80px;
    bottom: 100px;
    display: block
}

.article-page .modal-cont .popup {
    right: 250px;
    bottom: 0
}

.cycle-games-popup .popup {
    right: 20px;
    width: 400px;
    top: auto;
    bottom: 40px
}

.modal-cont .popup .title,
.goal-list .popup .title,
.cycle-games-popup .popup .title {
    background: #014bad;
    border-radius: 3px;
    font-size: 18px;
    margin: 0 0 11px -3px;
    line-height: 22px;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    color: #fff;
    position: relative;
    padding: 3px 19px 5px 25px;
    text-align: right
}

.modal-cont .popup .close,
.goal-list .popup .close,
.cycle-games-popup .popup .close {
    position: absolute;
    top: 8px;
    left: 8px;
    background: url(images/sprite.png) no-repeat -418px 0;
    overflow: hidden;
    text-indent: -9999px;
    width: 12px;
    height: 12px
}

.modal-cont .popup .holder,
.goal-list .popup .holder,
.cycle-games-popup .popup .holder {
    direction: ltr;
    height: 404px;
    float: left;
    width: 334px;
    overflow: auto
}

.modal-cont .popup .holder {
    overflow-x: hidden
}

.cycle-games-popup .popup .holder {
    width: 379px;
    overflow: hidden
}

.cycle-games-popup .popup .all-games-pop {
    overflow-y: auto;
    height: 363px;
    width: 378px
}

.goal-list .popup .frame,
.modal-cont .popup .frame {
    direction: rtl
}

.goal-list .popup .frame,
.cycle-games-popup .popup .frame {
    width: 301px;
    padding: 0 0 0 3px;
    float: left
}

.modal-cont .popup .frame {
    width: 100%;
    padding: 0 0 0 3px;
    float: left
}

.cycle-games-popup .popup .frame {
    width: 375px
}

.modal-cont .popup th,
.goal-list .popup table th,
.cycle-games-popup .popup table th {
    border-top: 3px solid #014bad;
    width: auto
}

.modal-cont .popup th:first-child {
    width: 100px
}

.cycle-games-popup .popup table th {
    font-size: 14px
}

.goal-list .popup table td,
.cycle-games-popup .popup table td {
    width: auto;
    text-align: center
}

.goal-list .popup table td:first-child,
.cycle-games-popup .popup table td:first-child {
    width: 20px;
    padding: 1px 0 0 2px
}

.goal-list .popup table .medium,
.modal-cont .popup table .medium {
    width: 98px;
    text-align: right;
    font-size: 14px
}

.modal-cont .popup table .big,
.goal-list .popup table .big,
.cycle-games-popup .popup table .big {
    width: 111px;
    font-size: 14px;
    text-align: right
}

.cycle-games-popup .popup .results-holder {
    border: 0;
    margin-right: 5px;
    direction: rtl
}

#main .map-block .heading,
#main .about-block .heading,
#main .player-info .heading,
#main .item-table .heading,
#main .results-holder .heading,
#main .tab-box .heading,
#main .score-list .heading,
#sidebar .goal-list .heading,
#toto-stats .goal-list .heading {
    text-align: right;
    overflow: hidden;
    padding: 4px 17px 1px;
    border-bottom-width: 3px
}

#sidebar .goal-list .heading,
#toto-stats .goal-list .heading {
    border-right: 0;
    border-left: none
}

#main .map-block .heading h2,
#main .about-block .heading h2,
#main .player-info .heading h2,
#main .item-table .heading h2,
#toto-stats .goal-list .heading h2,
#main .results-holder .heading p,
#main .tab-box .heading p,
#main .score-list .heading p,
#sidebar .goal-list .heading p,
#toto-stats .goal-list .heading p {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold'
}

#main .map-block .heading h2,
#main .about-block .heading h2,
#main .player-info .heading h2,
#main .item-table .heading h2,
#main .results-holder .heading h2,
#main .tab-box .heading h2,
#main .score-list .heading h2,
#sidebar .goal-list .heading h2,
#toto-stats .goal-list .heading h2 {
    font-size: 16px;
    line-height: 20px;
    font-weight: bold;
    margin: 0
}

#main .score-list .heading h2,
#sidebar .goal-list .heading h2,
#toto-stats .goal-list .heading h2 {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold'
}

#main .score-list .heading strong,
#sidebar .heading strong,
#sidebarHomePage .heading strong,
#main .player-info .heading strong,
.main-block .tab-box .tab-content strong,
#main .map-block .heading strong,
#main .about-block .heading strong,
#toto-stats .goal-list .heading strong {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    font-size: 16px;
    background-color: #f3f3f3
}

#main .item-table table,
.results-holder .result-table,
.tab-box .result-table,
#main .score-list table,
.goal-list table,
.modal-cont table {
    border-collapse: collapse;
    width: 100%;
    font-size: 14px;
    line-height: 18px;
    color: #000
}

.results-holder .result-table th,
.tab-box .result-table th,
#main .score-list table th,
.goal-list table th,
.modal-cont table th {
    color: #535353;
    height: 31px;
    text-align: center;
    font-weight: normal;
    border-bottom: 1px solid #000
}

.modal-cont table th {
    text-align: right;
    padding-right: 5px
}

#main .score-list.manual-tbl table th,
#main .score-list.manual-tbl table th:first-child,
#main .score-list.manual-tbl table td {
    text-align: right
}

#main .score-list.manual-tbl table td:first-child {
    padding-right: 16px
}

.goal-list table th {
    width: 50px;
    line-height: 15px;
    padding: 0 2px
}

.results-holder .result-table th:first-child,
.tab-box .result-table th:first-child,
#main .score-list table th:first-child,
.goal-list table th:first-child {
    width: 85px;
    padding: 0 16px 0 0;
    text-align: right
}

#main .score-list-alt table th:first-child {
    text-align: center;
    width: auto
}

#main .score-list-alt table th.th-rank {
    width: 10%;
    text-align: right
}

.results-holder .result-table td,
.tab-box .result-table td,
#main .score-list table td,
.goal-list table td,
.modal-cont table td {
    width: 76px;
    padding: 0 0 1px 0;
    height: 31px;
    background: #edf3fb;
    text-align: right
}

.goal-list table td a {
    max-height: 32px;
    overflow: hidden;
    vertical-align: middle;
    display: block
}

.modal-cont table td {
    padding-right: 5px
}

.goal-list table td {
    line-height: 15px;
    padding: 0 2px
}

.results-holder .result-table tr.alt td,
.tab-box .result-table tr.alt td,
#main .score-list table tr.alt td,
.goal-list table tr.alt td,
.modal-cont table tr.alt td,
.item-table table tr.alt td {
    background: #fff
}

#main .score-list table td:last-child {
    min-width: 56px
}

#main .score-list .heading-alt {
    border: 0
}

.goal-list table td.score,
.modal-cont table td.score {
    width: 30px;
    padding: 1px 0 0;
    font-size: 12px;
    text-align: center
}

.goal-list table td:first-child {
    width: 23px;
    padding: 1px 0 0 1px;
    text-align: center
}

#sidebar .goal-list .heading-alt,
#toto-stats .goal-list .heading-alt {
    border-bottom: 0;
    padding: 6px 13px 2px;
    position: absolute;
    bottom: 0;
    left: 0;
    width: 116px
}

.video-list-yellow {
    background-position: -1376px 0
}

.content {
    overflow: hidden;
    padding: 0 2px 0 0
}

.score-list {
    border: 1px solid #c3cad1;
    padding: 0;
    margin: 0 0 18px 0;
    background: #fff
}

.score-list .result-table {
    margin: 0
}

#main .score-list table {
    font-size: 14px;
    line-height: 15px
}

#main .score-list table th {
    height: 29px
}

#main .score-list table td {
    width: 44px;
    text-align: center;
    border-bottom: 1px solid transparent;
    padding-top: 1px
}

#main .score-list table td:first-child {
    width: 43px;
    padding-right: 4px
}

#main .score-list table td.big {
    width: 149px;
    text-align: right;
    font-size: 14px;
    padding: 0
}

#main .score-list table tr.green td {
    border-bottom-color: #61ac11
}

#main .score-list table tr.pink td {
    border-bottom-color: #E96CA4
}

#main .score-list table tr.red td {
    border-bottom-color: #cc1601
}

#main .score-list .menu {
    overflow: hidden;
    padding: 5px 18px 4px 19px;
    border-bottom: 1px solid #fff;
    background: #fff;
    background: -moz-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(253, 253, 253, 1) 63%, rgba(246, 246, 246, 1) 83%, rgba(247, 247, 247, 1) 87%, rgba(241, 241, 241, 1) 90%, rgba(240, 240, 240, 1) 97%, rgba(237, 237, 237, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(255, 255, 255, 1)), color-stop(63%, rgba(253, 253, 253, 1)), color-stop(83%, rgba(246, 246, 246, 1)), color-stop(87%, rgba(247, 247, 247, 1)), color-stop(90%, rgba(241, 241, 241, 1)), color-stop(97%, rgba(240, 240, 240, 1)), color-stop(100%, rgba(237, 237, 237, 1)));
    background: -webkit-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(253, 253, 253, 1) 63%, rgba(246, 246, 246, 1) 83%, rgba(247, 247, 247, 1) 87%, rgba(241, 241, 241, 1) 90%, rgba(240, 240, 240, 1) 97%, rgba(237, 237, 237, 1) 100%);
    background: -o-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(253, 253, 253, 1) 63%, rgba(246, 246, 246, 1) 83%, rgba(247, 247, 247, 1) 87%, rgba(241, 241, 241, 1) 90%, rgba(240, 240, 240, 1) 97%, rgba(237, 237, 237, 1) 100%);
    background: -ms-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(253, 253, 253, 1) 63%, rgba(246, 246, 246, 1) 83%, rgba(247, 247, 247, 1) 87%, rgba(241, 241, 241, 1) 90%, rgba(240, 240, 240, 1) 97%, rgba(237, 237, 237, 1) 100%);
    background: linear-gradient(to bottom, rgba(255, 255, 255, 1) 0, rgba(253, 253, 253, 1) 63%, rgba(246, 246, 246, 1) 83%, rgba(247, 247, 247, 1) 87%, rgba(241, 241, 241, 1) 90%, rgba(240, 240, 240, 1) 97%, rgba(237, 237, 237, 1) 100%)
}

#main .score-list .menu a.next {
    padding: 0 0 0 17px;
    margin: 4px 0 0
}

#main .score-list .menu a.prev {
    float: right;
    margin: 4px 0 0;
    padding: 0 15px 0 0
}

#main .score-list .menu a.prev:before {
    background-position: -408px 0;
    width: 7px;
    left: auto;
    right: 0
}

.score-list .menu .title {
    display: block;
    text-align: center
}

.result-table {
    margin: 0 0 1px
}

.result-table th {
    font-size: 14px;
    line-height: 18px;
    border-top: 1px solid #e8e8e8
}

#main .popup table.result-table td {
    width: 80px !important
}

#main .result-table td {
    width: 41px !important;
    text-align: center !important;
    height: 31px !important;
    padding: 0 !important
}

#main .result-table th:first-child,
#main .result-table td:first-child {
    width: 213px !important;
    text-align: right !important;
    padding: 0 16px 0 0 !important
}

.tab-box .tab-content .title-block,
.score-list .title-block {
    text-align: right;
    border: solid #e8e8e8;
    border-width: 1px 0 0;
    padding: 1px 0
}

.tab-box .tab-content .title-block strong,
.score-list .title-block strong {
    display: block;
    padding: 5px 13px 4px;
    background: #ececec;
    background: -moz-linear-gradient(top, rgba(236, 236, 236, 1) 0, rgba(244, 244, 244, 1) 10%, rgba(254, 254, 254, 1) 43%, rgba(253, 253, 253, 1) 63%, rgba(246, 246, 246, 1) 83%, rgba(247, 247, 247, 1) 87%, rgba(241, 241, 241, 1) 90%, rgba(240, 240, 240, 1) 97%, rgba(237, 237, 237, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(236, 236, 236, 1)), color-stop(10%, rgba(244, 244, 244, 1)), color-stop(43%, rgba(254, 254, 254, 1)), color-stop(63%, rgba(253, 253, 253, 1)), color-stop(83%, rgba(246, 246, 246, 1)), color-stop(87%, rgba(247, 247, 247, 1)), color-stop(90%, rgba(241, 241, 241, 1)), color-stop(97%, rgba(240, 240, 240, 1)), color-stop(100%, rgba(237, 237, 237, 1)));
    background: -webkit-linear-gradient(top, rgba(236, 236, 236, 1) 0, rgba(244, 244, 244, 1) 10%, rgba(254, 254, 254, 1) 43%, rgba(253, 253, 253, 1) 63%, rgba(246, 246, 246, 1) 83%, rgba(247, 247, 247, 1) 87%, rgba(241, 241, 241, 1) 90%, rgba(240, 240, 240, 1) 97%, rgba(237, 237, 237, 1) 100%);
    background: -o-linear-gradient(top, rgba(236, 236, 236, 1) 0, rgba(244, 244, 244, 1) 10%, rgba(254, 254, 254, 1) 43%, rgba(253, 253, 253, 1) 63%, rgba(246, 246, 246, 1) 83%, rgba(247, 247, 247, 1) 87%, rgba(241, 241, 241, 1) 90%, rgba(240, 240, 240, 1) 97%, rgba(237, 237, 237, 1) 100%);
    background: -ms-linear-gradient(top, rgba(236, 236, 236, 1) 0, rgba(244, 244, 244, 1) 10%, rgba(254, 254, 254, 1) 43%, rgba(253, 253, 253, 1) 63%, rgba(246, 246, 246, 1) 83%, rgba(247, 247, 247, 1) 87%, rgba(241, 241, 241, 1) 90%, rgba(240, 240, 240, 1) 97%, rgba(237, 237, 237, 1) 100%);
    background: linear-gradient(to bottom, rgba(236, 236, 236, 1) 0, rgba(244, 244, 244, 1) 10%, rgba(254, 254, 254, 1) 43%, rgba(253, 253, 253, 1) 63%, rgba(246, 246, 246, 1) 83%, rgba(247, 247, 247, 1) 87%, rgba(241, 241, 241, 1) 90%, rgba(240, 240, 240, 1) 97%, rgba(237, 237, 237, 1) 100%)
}

#main .tab-box .tab-content .title-block-stages th,
#main .content .tab-content .title-block-stages th {
    padding: 0 !important
}

#main .tab-box .tab-content .title-block-stages strong,
#main .content .tab-content .title-block-stages strong {
    font-family: arial;
    color: black;
    font-size: 15px
}

.results-holder .result-table .link,
.tab-box .result-table .link,
.score-list .link {
    color: #014bad;
    font-size: 14px;
    line-height: 18px
}

.results-holder .result-table .link:hover,
.tab-box .result-table .link:hover,
.score-list .link:hover {
    text-decoration: underline
}

.result-form {
    overflow: hidden;
    margin: 0;
    padding: 12px 20px 10px 5px;
    background: #f5f5f5;
    background: -moz-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 10%, rgba(253, 253, 253, 1) 19%, rgba(255, 255, 255, 1) 29%, rgba(254, 254, 254, 1) 78%, rgba(248, 248, 248, 1) 90%, rgba(237, 237, 237, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(237, 237, 237, 1)), color-stop(10%, rgba(248, 248, 248, 1)), color-stop(19%, rgba(253, 253, 253, 1)), color-stop(29%, rgba(255, 255, 255, 1)), color-stop(78%, rgba(254, 254, 254, 1)), color-stop(90%, rgba(248, 248, 248, 1)), color-stop(100%, rgba(237, 237, 237, 1)));
    background: -webkit-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 10%, rgba(253, 253, 253, 1) 19%, rgba(255, 255, 255, 1) 29%, rgba(254, 254, 254, 1) 78%, rgba(248, 248, 248, 1) 90%, rgba(237, 237, 237, 1) 100%);
    background: -o-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 10%, rgba(253, 253, 253, 1) 19%, rgba(255, 255, 255, 1) 29%, rgba(254, 254, 254, 1) 78%, rgba(248, 248, 248, 1) 90%, rgba(237, 237, 237, 1) 100%);
    background: -ms-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 10%, rgba(253, 253, 253, 1) 19%, rgba(255, 255, 255, 1) 29%, rgba(254, 254, 254, 1) 78%, rgba(248, 248, 248, 1) 90%, rgba(237, 237, 237, 1) 100%);
    background: linear-gradient(to bottom, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 10%, rgba(253, 253, 253, 1) 19%, rgba(255, 255, 255, 1) 29%, rgba(254, 254, 254, 1) 78%, rgba(248, 248, 248, 1) 90%, rgba(237, 237, 237, 1) 100%)
}

#main .result-form h3 {
    float: right;
    font-size: 14px;
    line-height: 16px;
    color: #626262;
    text-align: center;
    margin: 2px 0 0 30px;
    max-width: 90px
}

.result-form .select-holder {
    float: right;
    padding: 8px 14px 4px 10px;
    border-right: 1px solid #e0e0e0
}

.result-form select {
    float: right;
    width: 110px;
    margin: 0 11px 0 0;
    font-size: 12px;
    color: #626262
}

.result-form select:first-child {
    margin: 0
}

.result-form input[type="submit"],
.result-form input[type="button"] {
    float: right;
    width: 52px;
    height: 22px;
    font-size: 12px;
    line-height: 15px;
    color: #014bad;
    font-weight: bold;
    border: 1px solid #e5e5e5;
    border-radius: 4px;
    -moz-border-radius: 4px;
    -webkit-border-radius: 4px;
    cursor: pointer;
    margin: 7px 0 0 13px;
    padding: 0;
    float: left;
    background: #fff;
    background: -moz-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 5%, rgba(245, 245, 245, 1) 53%, rgba(234, 234, 234, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(255, 255, 255, 1)), color-stop(5%, rgba(249, 249, 249, 1)), color-stop(53%, rgba(245, 245, 245, 1)), color-stop(100%, rgba(234, 234, 234, 1)));
    background: -webkit-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 5%, rgba(245, 245, 245, 1) 53%, rgba(234, 234, 234, 1) 100%);
    background: -o-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 5%, rgba(245, 245, 245, 1) 53%, rgba(234, 234, 234, 1) 100%);
    background: -ms-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 5%, rgba(245, 245, 245, 1) 53%, rgba(234, 234, 234, 1) 100%);
    background: linear-gradient(to bottom, rgba(255, 255, 255, 1) 0, rgba(249, 249, 249, 1) 5%, rgba(245, 245, 245, 1) 53%, rgba(234, 234, 234, 1) 100%)
}

.result-form input[type="submit"]:hover,
.result-form input[type="button"]:hover {
    cursor: pointer
}

.main-box {
    margin: 0 0 25px;
    overflow: hidden;
    border-right: 16px solid #014bad;
    padding: 0 16px 0 0
}

.main-box>img {
    float: left;
    margin: 0 5px 0 0
}

.main-box .text-block {
    overflow: hidden;
    font-size: 12px;
    line-height: 16px;
    color: #1b1b1b;
    padding: 45px 0 0
}

#main .main-box h2 {
    font-size: 33px;
    line-height: 28px;
    margin: 0 0 22px
}

#main .main-box h3 {
    margin: 0 0 17px 25px
}

.tab-box {
    overflow: hidden;
    margin: 0 0 18px
}

.tab-box .tabset {
    padding: 0;
    margin: 0;
    list-style: none;
    font-size: 12px;
    line-height: 25px;
    overflow: hidden;
    border: 1px solid #dcdcdc;
    border-width: 1px 0 0 1px
}

.player-modal .tab-box .tabs-all {
    margin-bottom: 20px;
    max-width: 601px;
    border: 0;
    border-bottom: 1px solid #dcdcdc;
    padding: 1px 1px 0 0
}

#statisticsTop5 .tabset {
    border-width: 0
}

.tab-box .tabset li {
    float: right;
    border: solid #dcdcdc;
    border-width: 0 1px 1px 0;
    height: 29px;
    overflow: hidden
}

.player-modal .tab-box .tabset li {
    border-width: 1px
}

.main-block .tab-box .tabset a,
.player-modal .tab-box .tabset a {
    display: block;
    border: 1px solid #fff;
    border-width: 1px 1px 0;
    background: #f4f4f4 url(images/bg-tab.gif) repeat-x;
    width: 113px;
    text-align: center;
    color: #000;
    padding: 0 0 2px
}

.player-modal .tab-box .tabset a {
    width: 96px
}

.player-modal .stat-data strong {
    display: block;
    background: url(Images/sprite.png) 0 360px no-repeat;
    cursor: pointer;
    padding-right: 12px;
    line-height: 23px;
    height: 25px;
    outline: 0;
    font-size: 12px
}

.player-modal .stat-data strong:hover,
.player-modal .stat-data strong.ui-state-hover,
.player-modal .stat-data strong.ui-state-active,
.player-modal .stat-data strong.ui-state-focus {
    color: #014bad;
    border: 0
}

.player-modal .stat-data table {
    width: 99.9%
}

.player-modal .stat-data table tr th {
    text-align: right;
    background: #fff;
    border-bottom: 1px solid #000;
    color: #535353;
    font-size: 14px;
    line-height: 15px;
    padding: 5px 5px 1px 0
}

.player-modal .stat-data table tr td {
    padding: 5px 5px 5px 0;
    background: #fff
}

.player-modal .stat-data table tr.alt td {
    background: #edf3fb
}

.player-modal .stat-data .events {
    background: url(/images/player-events.png) no-repeat 0 0;
    width: 20px;
    height: 20px;
    display: inline-block;
    float: right;
    margin-top: 2px;
    margin-left: 5px
}

.player-modal .stat-data .events.score {
    background-position: 0 0
}

.player-modal .stat-data .events.assist {
    background-position: -22px 0
}

.player-modal .stat-data .events.subout {
    background-position: -43px 0
}

.player-modal .stat-data .events.subin {
    background-position: -67px 0
}

.player-modal .stat-data .events.red {
    background-position: -90px 0
}

.player-modal .stat-data .events.yellow {
    background-position: -110px 0
}

.player-modal .stat-data .events.yellows {
    background-position: -131px 0
}

.player-modal .stat-data .events.opend {
    background-position: -153px 0
}

.player-modal .stat-data .events.vote {
    background-position: -198px 0
}

.player-modal .stat-data .ui-accordion-content>div {
    max-height: 360px
}

.player-modal .stat-seps-data table tr th {
    text-align: right
}

.main-block .tab-box .tabset a.active,
.main-block .tab-box .tabset a:hover,
.player-modal .tab-box .tabs-all a.active,
.player-modal .tab-box .tabs-all a:hover {
    background: #d4d4d4 url(images/bg-tab-hover.gif) no-repeat 50% 0
}

.main-block .tab-box .tab-content {
    background: 0;
    padding: 0;
    border: 1px solid #dcdcdc;
    border-width: 0 1px 1px
}

.results-holder {
    margin: 0 0 18px;
    border: 1px solid #dcdcdc
}

.main-block .tab-box-alt .tabset a,
.tab-box-alt .tabset a {
    width: 55px
}

.tab-box-alt .tabset a.active,
.tab-box-alt .tabset a:hover {
    background: #dadada url(images/bg-tab-alt.gif) no-repeat 50% 0
}

.tab-box-alt table .green td {
    border-bottom: 1px solid #61ac11
}

.tab-box-alt .score-list {
    margin: -1px 0 0 0
}

#main .article-block-alt .article-list {
    border: 0
}

#main .article-block-alt .article-list:after {
    display: none
}

.gallery2 ul {
    padding: 0;
    margin: 0;
    list-style: none;
    overflow: hidden;
    text-align: center;
    font-size: 14px;
    line-height: 16px
}

.gallery2 ul li {
    float: right;
    margin-right: 3px;
    width: 143px
}

.gallery2 ul li:last-child {
    margin-right: 3px;
    width: 140px
}

.gallery2 ul a.btn-play {
    display: inline-block;
    vertical-align: top;
    margin: 0 0 1px
}

.gallery2 ul a.btn-play img {
    display: block
}

.gallery2 ul a {
    color: #fff
}

.gallery2 ul a:hover {
    color: #014bad
}

.gallery2 .gmask ul li {
    margin: 0
}

#content .gallery .gmask ul li.active {
    z-index: 1
}

.schedule-block,
.play-block {
    width: 314px;
    font-size: 14px;
    line-height: 12px;
    position: relative;
    margin: 10px 0 0
}

.play-block {
    display: inline-block
}

.play-block .heading3 {
    overflow: hidden;
    padding: 0 34px 8px 17px
}

.play-block .heading3 .title {
    padding: 0
}

.play-block .heading3 a {
    color: #fff;
    margin: 0 0 -5px -21px;
    float: left;
    width: 80px;
    padding: 3px 0 0 21px;
    text-align: left;
    height: 20px;
    background: url(images/sprite.png) no-repeat -434px -121px
}

.play-block .heading3 a:hover {
    text-decoration: underline
}

.play-block .play-by-play {
    overflow: hidden;
    margin-left: 1px
}

.play-table {
    border-collapse: collapse;
    width: 287px;
    border: 1px solid #828a96;
    float: right
}

.play-table td {
    height: 33px;
    padding: 2px 10px 2px 2px;
    border: 1px solid #828a96;
    background: #fff;
    background: -moz-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(254, 255, 255, 1) 44%, rgba(237, 238, 240, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(255, 255, 255, 1)), color-stop(44%, rgba(254, 255, 255, 1)), color-stop(100%, rgba(237, 238, 240, 1)));
    background: -webkit-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(254, 255, 255, 1) 44%, rgba(237, 238, 240, 1) 100%);
    background: -o-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(254, 255, 255, 1) 44%, rgba(237, 238, 240, 1) 100%);
    background: -ms-linear-gradient(top, rgba(255, 255, 255, 1) 0, rgba(254, 255, 255, 1) 44%, rgba(237, 238, 240, 1) 100%);
    background: linear-gradient(to bottom, rgba(255, 255, 255, 1) 0, rgba(254, 255, 255, 1) 44%, rgba(237, 238, 240, 1) 100%)
}

.play-table .score td {
    text-align: center;
    height: 42px;
    padding: 0 10px
}

.play-table .result {
    font-weight: bold;
    width: 70px
}

.play-table .result .holder {
    line-height: 21px
}

.play-table .result .holder strong {
    font-size: 20px
}

.play-table a {
    display: inline-block;
    vertical-align: middle
}

.play-table a.team-name {
    width: 39px;
    font-weight: bold;
    text-align: right
}

.play-table a.link {
    font-weight: bold;
    text-align: center;
    display: block
}

.play-table a img {
    display: block
}

.play-table .num {
    font-size: 14px;
    line-height: 18px;
    color: #014bad;
    display: inline-block;
    vertical-align: middle;
    margin-left: 5px
}

.play-table .text {
    display: inline-block;
    vertical-align: middle;
    width: 240px;
    height: 26px;
    overflow: hidden
}

.play-table .img-holder {
    margin: 0 2px 0 0
}

.play-table .img-holder2 {
    margin: 0 0 0 2px
}

.play-table table.remarks td {
    padding: 0;
    border: 0;
    margin: 0;
    height: 21px
}

.play-block .pagination ul {
    list-style-type: disc;
    margin: 0;
    padding: 0;
    text-align: center;
    width: 100%
}

.play-block .pagination ul li {
    display: inline-block;
    text-indent: -9999px;
    background: url(images/sprite.png) no-repeat -11px 0;
    width: 5px;
    height: 5px;
    margin: 0 2px;
    cursor: pointer
}

.section .link-more,
.schedule-block .link-more {
    position: absolute;
    top: 17px;
    left: 5px;
    overflow: hidden;
    text-indent: -9999px;
    background: url(images/sprite.png) no-repeat -434px -125px;
    width: 14px;
    height: 12px
}

.section .link-more {
    top: 16px;
    left: 6px
}

.schedule-block .scrollable-area {
    float: right;
    height: auto;
    margin: 0 16px 0 0;
    width: 286px !important;
    direction: rtl;
    text-align: right
}

.schedule-block table {
    border-collapse: collapse;
    width: 100%;
    font-size: 14px;
    line-height: 18px
}

.schedule-block table td {
    font: 14px/16px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    float: right;
    color: #000;
    margin: 10px 17px 5px 0
}

.schedule-block table img {
    float: right;
    margin: 0 1px 0 0
}

.schedule-block table img.scnd-channel {
    margin-right: -50px
}

.schedule-block table span.channel-num {
    float: left;
    font-size: 11px;
    font-weight: bold;
    position: absolute;
    left: 5px
}

.heding {
    border-bottom: 1px solid #ebebeb
}

.schedule-block table .date {
    font: 14px/16px 'blender-bold', 'Mukta-ExtraBold', 'south web bold', Arial, Helvetica, sans-serif;
    float: right;
    line-height: 20px;
    color: #000;
    margin: 0 0 0 15px;
    height: 100%;
    margin-top: -3px;
    height: auto
}

.schedule-block table .oneCol {
    margin: 10px 16px 5px 0
}

.schedule-block table .secondCol {
    width: 212px;
    margin: 9px 0 5px 0;
    font: 14px/16px 'blender-bold', 'Mukta-ExtraBold', 'south web normal', Arial, Helvetica, sans-serif
}

.jcf-hidden {
    display: block !important;
    position: absolute !important;
    left: -9999px !important
}

.scrollable-area-wrapper {
    position: relative;
    overflow: hidden;
    width: 100%
}

.schedule-block .scrollable-area-wrapper {
    width: 314px !important
}

.scrollable-area-wrapper .hscrollbar,
.scrollable-area-wrapper .vscrollbar {
    top: 0;
    right: 300px;
    width: 12px
}

.scrollable-area-wrapper .hscroll-left,
.scrollable-area-wrapper .vscroll-up,
.scrollable-area-wrapper .hscroll-right,
.scrollable-area-wrapper .vscroll-down {
    background: #EEE;
    width: 14px;
    height: 14px;
    float: left;
    background: url(images/sprite.png) no-repeat -552px -55px
}

.scrollable-area-wrapper .vscroll-down {
    background-position: -538px -56px;
    height: 17px
}

.scrollable-area-wrapper .hscroll-line,
.scrollable-area-wrapper .vscroll-line {
    background: #EEE;
    width: 14px;
    float: left
}

.scrollable-area-wrapper .hscroll-slider,
.scrollable-area-wrapper .vscroll-slider {
    width: 13px;
    height: 20px;
    background: #DADADA
}

#sidebar .post-list .heading2 {
    color: #fff;
    background: url(images/bg-video.jpg) no-repeat;
    padding: 16px 48px 3px;
    margin: 0 0 21px;
    border: 0
}

#sidebar .post-list .heading2 strong {
    border: 0;
    padding: 0;
    font: 14px 'blender-bold', 'Mukta-ExtraBold', 'south web bold', Arial, Helvetica, sans-serif
}

.banner-holder ul {
    list-style: none;
    margin: 0;
    padding: 0
}

.gallery5 {
    margin: 0 0 15px;
    position: relative
}

.gallery5 .gmask {
    position: relative;
    z-index: 1
}

.gallery5 .gmask ul {
    position: relative;
    width: 625px;
    height: 346px
}

.gallery5 .gmask ul li {
    position: absolute;
    top: 0;
    left: 0;
    width: 625px;
    height: 346px;
    z-index: 2
}

.gallery5 .gmask ul li.active {
    z-index: 10
}

.gallery5 .pagination {
    position: absolute;
    top: -20px;
    right: 0;
    z-index: 100;
    font-size: 10px;
    line-height: 13px;
    text-align: center;
    font-weight: bold
}

.gallery5 .pagination ul {
    list-style: none;
    margin: 0;
    padding: 0
}

.gallery5 .pagination ul:after {
    content: "";
    display: block;
    clear: both
}

.gallery5 .pagination ul li {
    float: right;
    margin: 0 0 0 -13px;
    background: url(images/bg-pagination.png) no-repeat;
    width: 37px
}

.gallery5 .pagination ul li:first-child {
    background: url(images/bg-pagination-first.png) no-repeat
}

#main .gallery5 .pagination ul a {
    color: #fff;
    display: block;
    position: relative;
    padding: 10px 0 1px !important
}

.gallery5 .pagination ul li.active a,
.gallery5 .pagination ul a:hover {
    background: url(images/bg-pagination-hover.png) no-repeat 10px 0
}

.gallery5 .pagination ul li.active a.red,
.gallery5 .pagination ul a.red:hover {
    background: url(images/bg-red-pagination-hover.png) no-repeat 10px 0
}

.gallery5 .pagination ul li.active a.orange,
.gallery5 .pagination ul a.orange:hover {
    background: url(images/bg-orange-pagination-hover.png) no-repeat 10px 0
}

.gallery5 .pagination ul li.active a.bluetravel,
.gallery5 .pagination ul a.bluetravel :hover {
    background: url(images/bg-orange-pagination-hover.png) no-repeat 10px 0
}

.gallery5 .pagination ul li.active a.peach,
.gallery5 .pagination ul a.peach :hover {
    background: url(images/bg-orange-pagination-hover.png) no-repeat 10px 0
}

.gallery5 .pagination ul li.active a.green,
.gallery5 .pagination ul a.green:hover {
    background: url(images/bg-green-pagination-hover.png) no-repeat 10px 0
}

.gallery5 .pagination ul li.active a {
    cursor: default
}

.gallery5 .desc {
    background: #014bad;
    font-size: 12px;
    line-height: 15px;
    height: 346px;
    padding: 40px 13px 0 10px;
    width: 203px
}

.gallery5 .desc.red {
    background: #d10202
}

.gallery5 .desc.orange {
    background: #ff5a00
}

.gallery5 .desc.bluetravel {
    background: #043959
}

.gallery5 .desc.lightblue {
    background: #0c9bd9
}

.gallery5 .desc.peach {
    background: #B8411F
}

.gallery5 .desc.green {
    background: #0e9b00
}

#main .gallery5 h2 {
    margin: 0 -1px 8px 0
}

#main .gallery5 h3 {
    font: 16px/18px 'blender-bold', 'Mukta-ExtraBold', 'south web bold', Arial, Helvetica, sans-serif;
    margin: 0 -2px 10px 0
}

#main .gallery5 .date {
    display: inline
}

#main .gallery5 p {
    margin: 0
}

#main .gallery5 .gallery5_inner ul {
    width: 464px
}

#main .gallery5 .gallery5_inner .pagination2 {
    position: absolute;
    bottom: 6px;
    left: 8px
}

#main .gallery5 .gallery5_inner .pagination2 ul {
    width: auto;
    height: auto;
    position: static
}

#main .gallery5 .gallery5_inner .pagination2 ul li {
    bottom: 27px;
    background: url(images/bg-gallery2.png);
    width: auto;
    height: 17px;
    position: relative;
    float: left;
    z-index: 10;
    padding: 4px 5px 4px 0
}

#main .gallery5 .gallery5_inner .pagination2 ul li:first-child {
    padding-left: 5px
}

#main .gallery5 .gallery5_inner .pagination2 ul li:last-child {
    padding-left: 0
}

#main .gallery5 .gallery5_inner .pagination2 ul li a {
    color: white;
    font-size: 12px;
    background: #014bad;
    width: 19px;
    height: 17px;
    text-align: center
}

#main .gallery5 .gallery5_inner .pagination2.red ul li a {
    background: #d10202
}

#main .gallery5 .gallery5_inner .pagination2.orange ul li a {
    background: #ff5a00
}

#main .gallery5 .gallery5_inner .pagination2.bluetravel ul li a {
    background: #043959
}

#main .gallery5 .gallery5_inner .pagination2.lightblue ul li a {
    background: #0c9bd9
}

#main .gallery5 .gallery5_inner .pagination2.peach ul li a {
    background: #B8411F
}

#main .gallery5 .gallery5_inner .pagination2.green ul li a {
    background: #0e9b00
}

#main .gallery5 .gallery5_inner .pagination2 ul li a:hover {
    text-decoration: underline
}

#main .gallery5 .gallery5_inner .pagination2 ul li.active a {
    background: #fff;
    color: #014bad
}

#main .gallery5 .gallery5_inner .pagination2 ul li.active a:hover {
    text-decoration: none;
    cursor: default
}

#main .gallery5 .gallery5_inner .pagination2.red ul li.active a {
    color: #d10202
}

#main .gallery5 .gallery5_inner .pagination2.orange ul li.active a {
    color: #ff5a00
}

#main .gallery5 .gallery5_inner .pagination2.bluetravel ul li.active a {
    color: #043959
}

#main .gallery5 .gallery5_inner .pagination2.lightblue ul li.active a {
    color: #0c9bd9
}

#main .gallery5 .gallery5_inner .pagination2.peach ul li.active a {
    color: #B8411F
}

#main .gallery5 .gallery5_inner .pagination2.green ul li.active a {
    color: #0e9b00
}

#main .gallery5 .gallery5_inner ul li a {
    float: left
}

.aside .banner-list {
    list-style: none;
    margin: 0;
    padding: 0
}

.aside-holder .banner-list {
    list-style: none;
    margin: 0;
    padding: 0
}

.aside-holder.banner-list li {
    padding: 0 0 18px;
    display: inline-block;
    vertical-align: bottom
}

.aside,
.aside-holder .banner-list img {
    display: block
}

#main .article-list-alt2:after {
    display: none
}

#main .article-list-alt2 {
    width: 100%
}

#main .article-list-alt2 li:before {
    display: none
}

.section {
    overflow: hidden;
    width: 100%;
    position: relative
}

#main .section .heading {
    border: 0;
    background: #111720 url(images/bg-block.jpg) no-repeat;
    padding: 17px 45px 2px;
    color: #fff;
    margin: 0 0 11px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    font-weight: normal
}

#main .section .heading a {
    color: #fff
}

.section>.holder {
    overflow: hidden;
    position: relative
}

.section>.holder:before {
    width: 144px;
    height: 100%;
    background: #014bad;
    content: '';
    position: absolute;
    right: 0;
    top: 8px;
    z-index: -1
}

.section .figure {
    color: #fff;
    float: right;
    font-weight: bold;
    font-size: 12px;
    width: 144px;
    line-height: 15px;
    margin: 8px 0 0 15px
}

.section .figure img {
    display: block;
    margin: 0 0 11px
}

.section .figure .desc {
    padding: 0 10px
}

.section .figure .date {
    color: #fff;
    margin: 0 0 10px
}

.section .figure .date .talkbacks {
    margin: 0 5px 0 0;
    padding: 1px 20px 0 0;
    background: url(Images/whitecomm.png) no-repeat 100%
}

.section .figure .date .talkbacksHomePage {
    margin: 0 5px 0 0;
    padding: 1px 20px 0 0;
    background: url(Images/whitecomm.png) no-repeat 100%
}

#main .section .figure a {
    color: #fff;
    padding: 0
}

#main .section .figure p {
    margin: 0 0 3px
}

#main .section .figure h2 {
    margin: 0;
    font-size: 15px;
    line-height: 16px;
    max-height: 80px;
    overflow: hidden;
    padding-bottom: 20px;
    padding-top: 10px
}

.section .figure.video .desc>p>a:after {
    background: url(images/white-vi.png) 0 0 no-repeat;
    content: '';
    display: inline-block;
    width: 16px;
    height: 10px;
    margin-right: 8px
}

.section .figure.video.aod .desc>p>a:after {
    background: url(images/audios.png) -106px 0 no-repeat;
    width: 18px;
    height: 12px
}

.article-holder-Homepage {
    margin: 0;
    padding: 0 0 0 7px;
    font-size: 12px;
    line-height: 15px;
    color: #535353;
    overflow: hidden
}

.article-holder {
    margin: 0;
    padding: 0 0 0 7px;
    font-size: 12px;
    line-height: 15px;
    color: #535353;
    overflow: hidden
}

.article-holder li {
    list-style: none;
    padding: 4px 5px
}

.article-holder li.alt {
    background: #e9edf2
}

.article-holder .item-holder {
    overflow: hidden
}

.article-holder .item-holder span {
    float: right;
    margin: 0 0 0 10px
}

.article-holder .item-holder .date {
    display: inline
}

.section .article-holder .item-holder a {
    color: #535353;
    font-weight: bold
}

.section .article-holder .item-holder a:hover {
    color: #014bad
}

#main .article-holder h2 {
    font-size: 14px;
    line-height: 14px;
    max-height: 28px;
    overflow: hidden
}

#main .article-holder .item-holder.video:after {
    background: url(images/black-vi.png) 0 0 no-repeat;
    content: '';
    display: inline-block;
    width: 16px;
    height: 10px
}

#main .article-holder .item-holder.video.aod:after {
    background: url(images/audios.png) -1px 0 no-repeat;
    width: 18px;
    height: 12px
}

#main .section-blue .Background {
    background-color: #0044B0
}

#main .section-blue .Background span {
    display: block;
    width: 280px;
    text-align: right;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap
}

#main .section-blue .article-holder .item-holder.video:after {
    background-image: url(images/blue-vi.png)
}

#main .section-blue .article-holder .item-holder.video.aod:after {
    background: url(images/audios.png) -1px 0 no-repeat
}

#main .section-red .article-holder .item-holder.video:after {
    background-image: url(images/red-vi.png)
}

#main .section-red .article-holder .item-holder.video.aod:after {
    background: url(images/audios.png) -22px 0 no-repeat
}

#main .section-green .article-holder .item-holder.video:after {
    background-image: url(images/green-vi.png)
}

#main .section-greenLight .article-holder .item-holder.video:after {
    background-image: url(images/green-vi.png)
}

#main .section-grey .article-holder .item-holder.video:after {
    background-image: url(images/grey-vi.png)
}

#main .section-green .article-holder .item-holder.video.aod:after {
    background: url(images/audios.png) -64px 0 no-repeat
}

#main .section-greenLight .article-holder .item-holder.video.aod:after {
    background: url(images/audios.png) -64px 0 no-repeat
}

#main .section-yellow .article-holder .item-holder.video:after {
    background-image: url(images/yellow-vi.png)
}

#main .section-yellow .article-holder .item-holder.video.aod:after {
    background: url(images/audios.png) -85px 0 no-repeat
}

#main .section-orange .article-holder .item-holder.video:after {
    background-image: url(images/orange-vi.png)
}

#main .section-bluetravel .article-holder .item-holder.video:after {
    background-image: url(images/orange-vi.png)
}

#main .section-peach .article-holder .item-holder.video:after {
    background-image: url(images/orange-vi.png)
}

#main .section-orange .article-holder .item-holder.video.aod:after {
    background: url(images/audios.png) -43px 0 no-repeat
}

#main .section-bluetravel .article-holder .item-holder.video.aod:after {
    background: url(images/audios.png) -43px 0 no-repeat
}

#main .section-peach .article-holder .item-holder.video.aod:after {
    background: url(images/audios.png) -43px 0 no-repeat
}

#main .section-black .heading {
    background-position: -466px 0
}

#main .section-black .link-more {
    background-position: -434px -144px
}

.section-black>.holder:before {
    background: #141c27
}

#main .section-black .article-holder .item-holder a:hover {
    color: #141c27
}

#main .section-red .heading {
    background-position: -466px 0
}

#main .section-red .link-more {
    background-position: -434px -144px
}

.section-red>.holder:before {
    background: #d10202
}

#main .section-red .article-holder .item-holder a:hover {
    color: #d10202
}

#main .section-orange .heading {
    background-position: -932px 0
}

#main .section-orange .link-more {
    background-position: -434px -156px
}

.section-orange>.holder:before {
    background: #ff5a00
}

#main .section-orange .article-holder .item-holder a:hover {
    color: #ff5a00
}

#main .section-bluetravel .heading {
    background-position: -932px 0
}

#main .section-peach .heading {
    background-position: -932px 0
}

#main .section-bluetravel .link-more {
    background-position: -434px -156px
}

#main .section-peach .link-more {
    background-position: -434px -156px
}

.section-bluetravel>.holder:before {
    background: #043959
}

.section-bluetravel>.holder:before {
    background: #0c9bd9
}

.section-peach>.holder:before {
    background: #B8411F
}

#main .section-bluetravel .article-holder .item-holder a:hover {
    color: #043959
}

#main .section-lightblue .article-holder .item-holder a:hover {
    color: #0c9bd9
}

#main .section-peach .article-holder .item-holder a:hover {
    color: #B8411F
}

#main .section-green .heading {
    background-position: -1398px 0
}

#main .section-greenLight .heading {
    background-position: -1398px 0
}

#main .section-green .link-more {
    background-position: -434px -168px
}

#main .section-greenLight .link-more {
    background-position: -434px -168px
}

.section-green>.holder:before {
    background: #0e9b00
}

.section-greenLight>.holder:before {
    background: #60993A
}

#main .section-green .article-holder .item-holder a:hover {
    color: #0e9b00
}

#main .section-greenLight .article-holder .item-holder a:hover {
    color: #60993A
}

.items-container .figure {
    float: right;
    margin: 0 0 0 8px;
    position: relative
}

.items-container .box img,
.items-container .figure img {
    display: block
}

.items-container .box .desc,
.items-container .figure .desc {
    position: absolute;
    bottom: 0;
    font-size: 14px;
    line-height: 16px;
    left: 0;
    width: 100%;
    background: #ffa200;
    color: #000;
    padding: 0 3px
}

#main .items-container .box .desc span,
#main .items-container .figure .desc span,
#main .items-container .box .desc a,
#main .items-container .figure .desc a {
    display: block;
    padding: 2px 12px 4px;
    color: #000
}

.items-container .box {
    margin: 0 0 13px 14px;
    float: right;
    position: relative;
    overflow: hidden
}

.items-container .box:first-child {
    margin: 0
}

.people-block-grey .box-holder {
    background: #141c27 url(images/bg-block2.jpg) no-repeat
}

.people-block-grey .figure span {
    background: #636a76
}

.people-block-grey .figure span:after {
    background: url(images/bg-titl2.png) no-repeat
}

.main-section {
    margin: -5px 0 18px -30px
}

.main-section:after {
    display: block;
    clear: both;
    content: ''
}

.main-section .box {
    float: right;
    width: 591px;
    color: #dcdcdc;
    font-size: 16px;
    line-height: 20px;
    padding: 16px;
    margin: 0 0 19px 16px;
    background: #020306
}

.main-section .box.box-hidden {
    height: 0;
    padding: 0;
    overflow: hidden;
    margin: 0
}

.main-section .box a {
    color: #dcdcdc
}

.main-section .box .img-holder {
    display: inline-block;
    vertical-align: middle;
    position: relative;
    margin: 0 0 0 27px
}

.main-section .box .desc {
    display: inline-block;
    width: 150px;
    vertical-align: middle
}

#main .main-section .box h2 {
    font-size: 24px;
    line-height: 29px;
    margin: 0 0 12px 0
}

#main .main-section .box h2 a {
    color: #ffa200
}

#main .main-section .box a {
    padding: 0
}

.main-section .box img {
    display: block
}

#main .main-section .box p {
    margin: 0
}

.main-section .img-btn {
    position: absolute;
    bottom: 1px;
    right: 1px;
    overflow: hidden;
    text-indent: -9999px;
    background: url(images/sprite.png) no-repeat -538px -83px;
    width: 32px;
    height: 32px
}

.main-section .btn-video {
    background-position: -605px -83px;
    width: 35px;
    height: 35px;
    right: 4px;
    bottom: 6px
}

.main-section .btn-gallery {
    background-position: -566px -83px;
    width: 35px;
    height: 28px;
    right: 5px
}

.main-section .box-medium {
    width: 272px;
    font-size: 14px;
    line-height: 17px
}

#main .main-section .box-big {
    height: 314px;
    overflow: hidden
}

#main .main-section .box-big .desc {
    max-height: 314px;
    overflow: hidden
}

#main .main-section .box-medium {
    height: 277px;
    overflow: hidden
}

#main .main-section .box-medium h2 {
    height: 18px;
    overflow: hidden
}

#main .main-section .box-medium p {
    height: 34px;
    overflow: hidden
}

#main .main-section .box-small {
    height: 117px;
    overflow: hidden;
    padding: 14px 21px 13px 13px
}

#main .main-section .box-small .desc {
    height: 120px;
    overflow: hidden
}

#main .main-section .box-smallest {
    height: 43px;
    overflow: hidden;
    padding: 14px 21px 13px 13px
}

#main .main-section .box-smallest .desc {
    height: 49px;
    overflow: hidden
}

#main .main-section .box-smallestk .desc p {
    display: none
}

.main-section .box-medium .img-holder {
    margin: 0 0 16px;
    display: block
}

.main-section .box-medium .desc {
    display: block;
    width: auto;
    padding: 0 5px 5px
}

#main .main-section .box-medium h2 {
    font-size: 16px;
    line-height: 18px;
    margin: 0 0 9px
}

.main-section .banner {
    border: 2px solid #000;
    border-radius: 2px;
    float: right;
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    padding: 0;
    margin: 0 0 21px
}

.main-section .box-orange {
    background: #ffa200
}

.main-section .box-bluetravel {
    background: #043959
}

.main-section .box-lightblue {
    background: #043959
}

.main-section .box-peach {
    background: #B8411F
}

#main .main-section .box-small .img-holder.alignleft,
#main .main-section .box-smallest .img-holder.alignleft {
    margin: 0 10px 0 0
}

#main .main-section .box-small,
#main .main-section .box-smallest {
    font-size: 14px;
    width: 270px;
    line-height: 18px;
    padding: 13px 21px 16px 13px
}

#main .main-section .box-small .desc,
#main .main-section .box-smallest .desc {
    width: 102px
}

#main .main-section .box-small .img-holder,
#main .main-section .box-smallest .img-holder {
    margin: 0 -6px 0 10px
}

#main .main-section .box-small h2,
#main .main-section .box-smallest h2 {
    font-size: 16px;
    line-height: 18px
}

#main .main-section .box-orange h2,
#main .main-section .box-orange h2 a,
#main .main-section .box-orange p,
#main .main-section .box-orange a {
    color: #000
}

.main-section .box-3 {
    margin: -241px 0 0 15px
}

.main-section .box-4 {
    margin: -241px 0 0 15px
}

.main-section .box-5 {
    margin: -76px 0 0 15px
}

.main-section .box-6 {
    margin: -4px 0 19px 15px
}

.main-section .box-8 {
    margin: -4px 0 17px 15px
}

.main-section .banner-7 {
    margin: -168px 0 0 15px
}

.main-section .box-9 {
    margin: -258px -1px 0 15px
}

.main-section .box-10 {
    margin: -91px -1px 0 15px
}

.main-section .banner-12 {
    margin: -1px 2px 15px 0
}

#main .main-section .box-smallest.box-13 {
    margin: -202px -2px 0 15px;
    height: 49px
}

.main-section .box-14 {
    margin: -109px -2px 0 17px
}

.main-section .banner-2,
.main-section .banner-7,
.main-section .banner-12 {
    width: 300px;
    height: 250px
}

.main-section .banner- body>.popup-holder,
.main-section .box .popup-holder {
    position: relative;
    overflow: hidden;
    height: 0
}

#fancybox-loading {
    position: fixed;
    top: 50%;
    left: 50%;
    width: 40px;
    height: 40px;
    margin-top: -20px;
    margin-left: -20px;
    cursor: pointer;
    overflow: hidden;
    z-index: 1104;
    display: none
}

#fancybox-loading div {
    position: absolute;
    top: 0;
    left: 0;
    width: 40px;
    height: 480px
}

#fancybox-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 1100;
    display: none !important
}

#fancybox-tmp {
    padding: 0;
    margin: 0;
    border: 0;
    overflow: auto;
    display: none
}

#fancybox-wrap {
    position: absolute;
    top: 0;
    left: 0;
    padding: 20px;
    z-index: 1101;
    outline: 0;
    display: none
}

#fancybox-outer {
    position: relative;
    width: 100%;
    height: 100%;
    background: #fff
}

#fancybox-content {
    width: 0;
    height: 0;
    padding: 0;
    outline: 0;
    position: relative;
    overflow: hidden;
    z-index: 1102;
    box-shadow: 3px 3px 2px #000;
    border: 0 solid #fff
}

#fancybox-hide-sel-frame {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: transparent;
    z-index: 1101
}

#fancybox-close {
    position: absolute;
    top: 14px;
    left: 18px;
    width: 18px;
    height: 18px;
    background: url(images/sprite.png) no-repeat -570px -55px;
    cursor: pointer;
    z-index: 1103;
    display: none
}

#fancybox-error {
    color: #444;
    font: normal 12px/20px Arial;
    padding: 14px;
    margin: 0
}

#fancybox-img {
    width: 100%;
    height: 100%;
    padding: 0;
    margin: 0;
    border: 0;
    outline: 0;
    line-height: 0;
    vertical-align: top
}

#fancybox-frame {
    width: 100%;
    height: 100%;
    border: 0;
    display: block
}

#fancybox-left,
#fancybox-right {
    position: absolute;
    bottom: 0;
    height: 100%;
    width: 35%;
    cursor: pointer;
    outline: 0;
    background: transparent url(images/trans.gif);
    z-index: 1102;
    display: none
}

#fancybox-left {
    left: 0
}

#fancybox-right {
    right: 0
}

#fancybox-left-ico,
#fancybox-right-ico {
    position: absolute;
    top: 50%;
    left: -9999px;
    width: 30px;
    height: 30px;
    margin-top: -15px;
    cursor: pointer;
    z-index: 1102;
    display: block
}

#fancybox-left-ico {
    background-position: -40px -30px
}

#fancybox-right-ico {
    background-position: -40px -60px
}

#fancybox-left:hover,
#fancybox-right:hover {
    visibility: visible
}

#fancybox-left:hover span {
    left: 20px
}

#fancybox-right:hover span {
    left: auto;
    right: 20px
}

.fancybox-bg {
    display: none
}

.fancybox-title-inside {
    padding-bottom: 10px;
    text-align: center;
    color: #333;
    background: #fff;
    position: relative
}

.fancybox-title-outside {
    padding-top: 10px;
    color: #fff
}

.fancybox-title-over {
    position: absolute;
    bottom: 0;
    left: 0;
    color: #FFF;
    text-align: left
}

#fancybox-title-over {
    padding: 10px;
    background-image: url(images/fancybox/fancy_title_over.png);
    display: block
}

.fancybox-title-float {
    position: absolute;
    left: 0;
    bottom: -20px;
    height: 32px
}

#fancybox-title-float-wrap {
    border: 0;
    border-collapse: collapse;
    width: auto
}

#fancybox-title-float-wrap td {
    border: 0;
    white-space: nowrap
}

#fancybox-title-float-left {
    padding: 0 0 0 15px
}

#fancybox-title-float-main {
    color: #FFF;
    line-height: 29px;
    font-weight: bold;
    padding: 0 0 3px 0
}

#fancybox-title-float-right {
    padding: 0 0 0 15px
}

.lightbox {
    width: 733px;
    background: #141c27;
    border: 1px solid #223043;
    padding: 7px;
    box-shadow: 4px 4px 4px #000
}

.lightbox-video {
    width: 643px
}

.lightbox>.holder {
    overflow: hidden;
    position: relative;
    margin: 0 0 2px 1px;
    padding: 2px 8px 2px 60px;
    background: #111720 url(images/bg-lightbox.png) repeat-x
}

.lightbox>.holder:before {
    content: '';
    background: url(images/sprite.png) no-repeat -652px -52px;
    width: 60px;
    height: 31px;
    position: absolute;
    left: 0;
    top: 0
}

.lightbox .facebook {
    float: left;
    margin: 4px 0 0
}

.lightbox .logo {
    background: url(images/logo-small.png) no-repeat;
    background-position: initial !important;
    width: 41px;
    height: 27px
}

.lightbox .img-holder {
    position: relative;
    margin: 0 1px 0 2px;
    padding: 0 0 2px
}

.lightbox .img-holder img {
    display: block
}

.lightbox .gallery .desc,
.lightbox .img-holder .desc {
    position: absolute;
    bottom: 0;
    line-height: 14px;
    left: 0;
    width: 100%;
    background: #ffb400;
    text-align: center;
    color: #000
}

.lightbox .gallery .desc span,
.lightbox .img-holder .desc span {
    display: block;
    padding: 4px 10px;
    line-height: 15px
}

.lightbox-gallery {
    padding: 7px 8px 2px 9px
}

.lightbox .gallery {
    position: relative
}

.lightbox .gallery .gmask {
    overflow: hidden
}

.lightbox .gallery .gmask ul {
    list-style: none;
    margin: 0;
    padding: 0;
    float: left
}

.lightbox .gallery .gmask ul li {
    float: left;
    position: relative
}

.lightbox .gallery .gmask ul img {
    display: block
}

.lightbox .gallery .desc {
    bottom: 84px
}

.lightbox .gallery .panel {
    background: #111720 url(images/bg-lightbox.png) repeat-x;
    margin: 0 2px 0 -1px;
    font-size: 14px;
    overflow: hidden
}

.lightbox .gallery .btn-prev,
.lightbox .gallery .btn-next {
    float: right;
    padding: 4px 72px 5px 0;
    position: relative;
    color: #7c8695;
    text-decoration: none
}

.lightbox .gallery .btn-prev:hover,
.lightbox .gallery .btn-next:hover {
    text-decoration: underline
}

.lightbox .gallery .btn-next {
    float: left;
    padding: 4px 0 5px 55px
}

.lightbox .gallery .btn-prev:before,
.lightbox .gallery .btn-next:before {
    position: absolute;
    right: 0;
    background: url(images/sprite.png) no-repeat -583px -118px;
    top: 0;
    width: 44px;
    height: 31px;
    content: ''
}

.lightbox .gallery .btn-next:before {
    right: auto;
    left: 0;
    background-position: -538px -118px
}

.lightbox .gallery6 {
    margin: 0 0 5px;
    position: relative
}

.lightbox .gallery6 .gmask {
    overflow: hidden;
    position: relative
}

.lightbox .gallery6>.gmask>ul {
    position: relative;
    width: 730px;
    height: 528px
}

.lightbox .gallery6>.gmask>ul>li {
    position: absolute;
    width: 730px;
    height: 528px
}

.lightbox .gallery6 .btn-prev,
.lightbox .gallery6 .btn-next {
    position: absolute;
    right: 15px;
    bottom: 35px;
    width: 11px;
    z-index: 100;
    border: none !important;
    padding: 0;
    height: 11px;
    overflow: hidden;
    text-indent: -9999px;
    background: url(images/sprite.png) no-repeat -605px -52px
}

.lightbox .gallery6 .btn-prev:before,
.lightbox .gallery6 .btn-next:before {
    display: none
}

.lightbox .gallery6 .btn-next {
    right: auto;
    left: 11px;
    background-position: -617px -52px
}

.lightbox .gallery6 .gmask li.active {
    z-index: 10
}

.lightbox .gallery .pagination {
    position: absolute;
    bottom: 0;
    background: url(images/bg-gallery2.png);
    right: 0;
    z-index: 100;
    width: 657px;
    padding: 8px 37px 7px 40px
}

.lightbox .gallery .pagination .gmask {
    overflow: hidden
}

.lightbox .gallery .pagination ul {
    list-style: none;
    margin: 0;
    float: right;
    overflow: hidden
}

.lightbox .gallery .pagination ul li {
    float: right;
    margin: 0 9px 0 0
}

.lightbox .gallery .pagination ul li:first-child {
    margin: 0
}

.lightbox .gallery .pagination a {
    display: block;
    border: 2px solid #000
}

.lightbox .text {
    position: absolute;
    right: 0;
    top: 0;
    width: 183px;
    height: 100%;
    font-size: 14px;
    line-height: 17px;
    padding: 0 25px;
    background: url(images/bg-gallery2.png);
    color: #fff;
    overflow: hidden;
    direction: rtl
}

.lightbox .text>.holder {
    padding: 24px 0 0
}

.lightbox .text p {
    margin: 0
}

.lightbox .text h2 {
    font: 30px/28px 'blender-bold', 'Mukta-ExtraBold', 'south web bold', Arial, Helvetica, sans-serif;
    color: #ffa200;
    margin: 0 0 15px
}

.blog-page #fancybox-close {
    left: auto;
    width: 24px;
    height: 24px;
    top: 8px;
    right: 8px;
    background-position: -640px -118px
}

.article-lightbox {
    border: 0;
    color: #000;
    background: #fff;
    padding: 8px 8px 6px 10px;
    width: 624px
}

.article-lightbox>.holder {
    background: #eee;
    margin: 0;
    padding: 0 0 0 46px
}

.article-lightbox>.holder:before {
    display: none
}

.article-lightbox .img-holder {
    float: right;
    position: relative;
    margin: 0 0 0 25px;
    padding: 0
}

.article-lightbox .img-holder img {
    display: block
}

.article-lightbox .text {
    overflow: hidden;
    padding: 20px 25px 0 25px;
    right: auto;
    left: 15px;
    background: 0;
    direction: rtl;
    width: 265px;
    height: 225px;
    color: #000
}

.article-lightbox .date {
    color: #000;
    margin: 0 4px 4px
}

.article-lightbox .text h2 {
    font-size: 16px;
    line-height: 20px;
    margin: 0 0 9px;
    color: #000;
    font-family: Arial;
    font-weight: bold
}

.article-lightbox .text h3,
.article-lightbox .text p {
    font-size: 14px;
    font-weight: normal;
    line-height: 18px;
    margin: 0 0 5px;
    color: #000
}

.article-lightbox .text p {
    width: 290px
}

#sidebar .people-block {
    overflow: hidden;
    width: auto;
    background: 0;
    margin: 0 0 0 -30px
}

#sidebar .people-block .box {
    float: right;
    width: 128px;
    padding: 9px 8px 12px;
    background: #141c27;
    margin: 0 0 16px 19px
}

.item-table {
    margin: 0 0 18px;
    padding: 0 1px 1px;
    border: 1px solid #e8e8e8
}

#main .item-table .heading-alt {
    border-width: 0
}

.item-table table {
    border: 0;
    border-bottom: 1px solid #fff
}

.item-table table th {
    border-bottom: 1px solid #000;
    height: 28px;
    font-size: 14px;
    line-height: 18px;
    text-align: right;
    padding: 0;
    color: #535353;
    font-weight: normal
}

.item-table table th:first-child {
    padding-right: 10px
}

.item-table table th.big {
    padding: 0 11px 0 0
}

.item-table table td {
    height: 31px;
    background: #edf3fb
}

.item-table table td:first-child {
    padding: 0 10px 0 6px
}

.item-table table td.big {
    width: 98px
}

.item-table table td.small {
    width: 65px
}

.visual-container {
    overflow: hidden;
    margin: 0 0 15px;
    background: #ff5a00;
    color: #fff
}

.visual-container>.img-holder {
    float: left;
    position: relative;
    overflow: hidden
}

.article-lightbox .img-holder:before,
.visual-container>.img-holder:before {
    width: 45px;
    content: '';
    position: absolute;
    bottom: 28px;
    right: -33px;
    height: 45px;
    background: #ff5a00;
    -moz-transform: rotate(45deg);
    -o-transform: rotate(45deg);
    -webkit-transform: rotate(45deg);
    transform: rotate(45deg)
}

.article-lightbox .img-holder:before,
.visual-container.even>.img-holder:before {
    right: auto;
    left: -33px
}

.visual-container img {
    display: block
}

.visual-container .desc {
    position: relative;
    z-index: 2;
    padding: 0 24px 0 16px;
    overflow: hidden
}

.visual-container .autor {
    margin: 0 1px 19px 0
}

.visual-container .autor:after {
    content: "";
    display: block;
    clear: both
}

#main .visual-container:first-child .autor {
    margin-bottom: 27px
}

.visual-container .autor .img-holder {
    background: #fff;
    float: right;
    margin: 0 0 0 9px;
    padding: 24px 2px 2px;
    box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.58)
}

.visual-container .autor .holder {
    overflow: hidden;
    padding-top: 23px
}

.visual-container:first-child .autor .holder {
    padding-top: 7px
}

#main .visual-container .autor p {
    margin: 0 0 1px
}

#main .visual-container .autor p a {
    padding: 0
}

#main .visual-container .desc .autor .img-holder a {
    padding: 0
}

.visual-container .date {
    color: #fff;
    line-height: 15px
}

.visual-container .date .talkbacks {
    margin: 0 8px;
    padding: 0 20px 0 0;
    background: url(Images/whitecomm.png) no-repeat 100%
}

.visual-container .date .talkbacksHomePage {
    margin: 0 8px;
    padding: 0 20px 0 0;
    background: url(Images/whitecomm.png) no-repeat 100%
}

#main .visual-container:first-child h2 {
    font-size: 24px;
    line-height: 28px
}

#main .visual-container:first-child>.img-holder:before {
    display: none
}

#main .visual-container h2 {
    font-size: 20px;
    line-height: 24px;
    margin: 0 0 12px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold'
}

#main .visual-container h3 a,
#main .visual-container p.h3 a,
#main .visual-container h2 a {
    padding: 0
}

.visual-container.even .img-holder {
    float: right
}

.visual-container.blue>.img-holder:before,
.visual-container.blue {
    background: #014bad
}

.visual-container.red>.img-holder:before,
.visual-container.red {
    background: #cc1601
}

.visual-container.pink>.img-holder:before,
.visual-container.pink {
    background: #E96CA4
}

.visual-container.green>.img-holder:before,
.visual-container.green {
    background: #0e9b00
}

.visual-container.grey>.img-holder:before,
.visual-container.grey {
    background: #434343
}

.article-lightbox .img-holder:before {
    background: #eee
}

.article-list-holder {
    overflow: hidden
}

.article-list-holder>ul {
    border: 0
}

.article-list-holder>ul:after {
    display: none
}

.visual-container .blockquote {
    font-size: 18px;
    line-height: 24px;
    padding: 5px 5px 25px
}

#main .visual-container .blockquote p {
    margin: 0 0 34px
}

.visual-container blockquote {
    font-size: 14px;
    line-height: 18px;
    margin: 0;
    padding: 0 23px 0 26px
}

.visual-container blockquote q {
    font-size: 22px;
    line-height: 24px;
    display: block;
    margin: 0 0 6px
}

.visual-container blockquote cite {
    display: block;
    text-align: left;
    font-style: normal
}

#main .visual-container blockquote a {
    padding: 0
}

.visual-container .like {
    position: absolute;
    right: 185px;
    bottom: 5px
}

.visual-container .like img {
    display: block
}

#main .main-block2 .heading {
    overflow: hidden;
    margin: 20px 2px 10px;
    padding: 12px 34px 4px;
    position: relative;
    padding-left: 0
}

#main .main-block2 .heading .leftRectLeftSide {
    width: 88% !important
}

#main .main-block2 .heading strong,
#main .main-block2 .heading h1 {
    position: absolute;
    color: #0044B0 !important;
    top: 19px;
    right: 33px;
    font-size: 16px !important
}

#main .main-block2 .items2 {
    margin: -4px 0 0 0
}

#main .main-block2 .items2 .heading {
    padding-top: 0;
    border: unset;
    background-color: #F6FBFF;
    border: unset
}

#main .main-block2 .items2 .heading h1 {
    top: -17px;
    right: 30px;
    font-family: 'blender-bold', "south web bold", Arial, Helvetica, sans-serif;
    font-size: 16px;
    font-weight: bold
}

#main .article-archive .main-block2 .items2 .heading {
    background-color: #000;
    height: 32px !important;
    margin: 0 0 8px 0;
    width: 630px
}

#main .article-archive .main-block2 .items2 .heading h1 {
    color: #fff !important;
    font-size: 18px !important;
    font: 18px 'blender-regular';
    margin: 27px -14px 0 0
}

#main .article-archive .main-block2 .heading {
    background-color: #000;
    height: 30px;
    width: 627px
}

#main .article-archive .main-block2 .heading strong {
    color: #fff !important;
    margin: -12px -17px 0 0;
    font-size: 18px !important;
    font-family: 'blender-regular'
}

#main .main-block2 .items2 .heading .leftRectLeftSide {
    width: 64% !important
}

#main .main-block>.heading {
    color: #fff;
    overflow: hidden;
    background: #111720 url(images/bg-block.jpg) no-repeat -2328px 0;
    margin: 0 0 12px;
    padding: 12px 32px 3px
}

#main .main-block2>.heading h2,
#main .main-block>.heading h2,
#main .main-block2>.heading strong,
#main .main-block>.heading strong {
    font: 14px/18px 'blender-bold', 'Mukta-ExtraBold', 'south web bold', Arial, Helvetica, sans-serif;
    margin: 0
}

.tags-container .main-block2 .items .headerLeft.header-text-left-side {
    width: 629px;
    height: 30px;
    margin: -20px 0 7px 0
}

.tags-container .main-block2 .items .headerLeft.header-text-left-side h1 {
    color: white;
    font-size: 18px;
    padding: 5px 10px
}

.tags-container .main-block2 .headerLeft.header-text-left-side {
    width: 629px;
    height: 30px;
    margin: 0 0 7px 0;
    background-color: #000
}

.tags-container .main-block2 .headerLeft.header-text-left-side div {
    color: white;
    font-size: 18px;
    padding: 7px 10px
}

.players-block {
    margin: 0 0 18px;
    border: 1px solid #c3cad1;
    position: relative;
    z-index: 100;
    background: #f2f2f2 url(images/bg-logos.gif) no-repeat;
    padding: 7px;
    width: 937px
}

.players-block:after {
    content: "";
    display: block;
    clear: both
}

.players-block .item {
    float: right;
    width: 112px;
    text-align: center
}

.players-block .logo-holder {
    border: 0 solid #fff;
    border-bottom: 0 solid #c3cad1;
    padding: 1px 19px 2px 25px;
    vertical-align: middle;
    border-width: 0;
    font-size: 14px;
    line-height: 0;
    height: 123px
}

.players-block .logo-holder img {
    display: block;
    margin-top: 5px
}

.players-block .logo-holder h1,
#main .players-block .logo-holder h2,
.players-block .logo-holder img {
    display: inline-block;
    vertical-align: middle;
    margin: 0;
    font-size: 18px;
    line-height: 17px;
    font-weight: normal;
    text-align: center
}

.players-block .logo-holder .f_right {
    width: 87px
}

.players-block .drop-block {
    position: relative;
    font-size: 12px;
    line-height: 17px;
    cursor: pointer;
    border: 1px solid rgba(158, 158, 158, 0.56)
}

.players-block .drop-block>span {
    margin-right: -10px
}

.players-block .drop-block>a {
    position: relative;
    display: block;
    padding: 2px 20px
}

.players-block .drop-block:after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 21px;
    height: 17px;
    background: url(images/sprite.png) no-repeat -716px -2px
}

.players-block .drop-block ul {
    position: absolute;
    display: none;
    list-style: none;
    background: #fafafa;
    margin: 0;
    padding: 0;
    top: 100%;
    right: -1px;
    border: 1px solid #dcdcdc;
    border-width: 1px 0 0 1px;
    width: 179px;
    z-index: 1
}

.players-block .drop-block:hover ul {
    display: block
}

.players-block .drop-block ul li {
    display: table;
    width: 100%
}

.players-block .drop-block div {
    display: table-cell;
    border: 1px solid #dcdcdc;
    border-width: 0 1px 1px 0;
    text-align: center;
    vertical-align: middle;
    width: 33%;
    height: 55px
}

.players-block .drop-block div:hover {
    background: #ededed
}

.players-block .drop-block img {
    vertical-align: top;
    width: 45px
}

.players-block .gallery {
    overflow: hidden;
    font-size: 12px;
    border-right: 0 solid #c3cad1;
    line-height: 12px
}

.players-block .gallery .gmask {
    overflow: hidden
}

.players-block .gallery .gmask>div {
    width: 9999px
}

.players-block .gallery .gmask ul {
    list-style: none;
    margin: 0;
    padding: 0;
    display: table
}

.players-block .gallery ul li {
    display: table-cell;
    width: 61px;
    height: 87px;
    padding: 2.9px;
    text-align: center;
    border-left: 0 solid #fff;
    border-right: 0 solid #c3cad1;
    vertical-align: middle
}

.players-block .gallery ul li>div {
    position: relative
}

.players-block .gallery ul li span.top-score {
    color: #0e8140;
    background: white url('Images/sprite2.png') no-repeat -98px 0;
    padding: 2px;
    padding-left: 16px;
    font-size: 11px;
    line-height: 9px;
    position: absolute;
    left: 8px;
    top: 40px
}

.players-block .gallery .img-holder {
    display: inline-block;
    vertical-align: top;
    margin: 0 0 5px
}

.players-block .gallery img {
    display: block
}

.players-block .gallery a {
    color: #525050
}

.players-block .gallery ul li:hover {
    background: #ededed
}

.players-block .gallery ul li:hover a {
    color: #014bad
}

.players-block .gallery ul li a:last-child {
    height: 27px;
    display: inline-block;
    overflow: hidden
}

.players-block .gallery ul li a:first-child {
    margin-top: 5px
}

.players-block .gallery .btn-prev {
    float: right;
    width: 14px;
    height: 96px;
    overflow: hidden;
    text-indent: -9999px;
    background: url(images/ticker-btn-right.png) no-repeat 6px 73px
}

.players-block .gallery .btn-next {
    float: left;
    width: 14px;
    height: 96px;
    overflow: hidden;
    text-indent: -9999px;
    background: url(images/ticker-btn-left.png) no-repeat 0 74px
}

.players-block .img-holder {
    border: 0 solid #707072
}

.info-container {
    overflow: hidden;
    padding: 0 0 11px
}

.info-container .article {
    float: right;
    width: 304px;
    margin: 0 0 0 15px;
    line-height: 18px;
    color: #1b1b1b;
    font-family: 'blender-regular'
}

#main .info-container .article h2 {
    font-size: 18px;
    line-height: 18px;
    margin: 0 -1px 3px 0;
    font-family: 'blender-bold'
}

#main .info-container .article p {
    margin: 0
}

#main .article-list2 {
    border: 0;
    margin: 0;
    padding: 0
}

#main .article-list2:after {
    display: none
}

#main .article-list2 li {
    float: none;
    padding: 10px 0 9px;
    width: auto;
    border: 1px solid #e8e8e8;
    border-width: 1px 0 0;
    overflow: hidden;
    font-family: 'blender-regular'
}

#main .article-list2 li:first-child {
    padding-top: 0;
    border-top: 0;
    width: auto
}

#main .article-list2 li p {
    margin: 0 0 7px 0;
    height: 48px;
    overflow: hidden;
    line-height: 16px
}

#main .article-list2 li h2 {
    height: 32px
}

#main .article-list2 .img-holder {
    float: right;
    width: 144px;
    margin: 0 0 0 16px
}

.info-container .article .img-holder {
    margin: 0 0 15px
}

.info-container .article .img-holder img {
    display: block
}

.player-info {
    background: #fff;
    border: 1px solid #c3cad1;
    margin: 0 1px 18px 0
}

.player-info .heading a {
    color: #014bad
}

.player-info>.holder {
    padding: 19px 20px 12px 14px;
    border-top: 1px solid #fff;
    overflow: hidden;
    background: #fafafa;
    background: -moz-linear-gradient(top, rgba(250, 250, 250, 1) 0, rgba(250, 250, 250, 1) 33%, rgba(237, 237, 237, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(250, 250, 250, 1)), color-stop(33%, rgba(250, 250, 250, 1)), color-stop(100%, rgba(237, 237, 237, 1)));
    background: -webkit-linear-gradient(top, rgba(250, 250, 250, 1) 0, rgba(250, 250, 250, 1) 33%, rgba(237, 237, 237, 1) 100%);
    background: -o-linear-gradient(top, rgba(250, 250, 250, 1) 0, rgba(250, 250, 250, 1) 33%, rgba(237, 237, 237, 1) 100%);
    background: -ms-linear-gradient(top, rgba(250, 250, 250, 1) 0, rgba(250, 250, 250, 1) 33%, rgba(237, 237, 237, 1) 100%);
    background: linear-gradient(to bottom, rgba(250, 250, 250, 1) 0, rgba(250, 250, 250, 1) 33%, rgba(237, 237, 237, 1) 100%);
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#fafafa', endColorstr='#ededed', GradientType=0)
}

.player-info .img-holder {
    float: right;
    margin: 0 0 0 22px;
    border: 1px solid #000
}

.player-info .img-holder img {
    display: block
}

.player-info .info {
    overflow: hidden;
    font-size: 10px;
    line-height: 13px
}

.player-info .info dl {
    overflow: hidden;
    padding: 0 4px 4px 30px;
    margin: 0;
    font-size: 14px;
    line-height: 14px
}

.player-info .info dt {
    float: right;
    padding: 0 11px 0 5px;
    position: relative;
    color: #7d7d7d
}

.player-info .info dt:before {
    content: '';
    position: absolute;
    right: 1px;
    top: 5px;
    width: 5px;
    height: 5px;
    background: url(images/sprite.png) no-repeat -732px -19px
}

.player-info .info dl dd {
    display: block;
    margin: 0 0 5px
}

.player-info .info ul {
    list-style: none;
    margin: 0;
    padding: 24px 0 0;
    font-size: 18px;
    line-height: 18px;
    overflow: hidden;
    text-align: center
}

.player-info .info ul li {
    float: right;
    border-right: 1px solid #ececec;
    position: relative;
    width: 40px;
    padding: 3px 0 8px;
    background: #fff
}

.player-info .info ul.imported li {
    width: 47px
}

.player-info .info ul li:first-child {
    border: 0
}

.player-info .info ul li:before {
    position: absolute;
    top: -24px;
    height: 24px;
    right: 0;
    width: 34px;
    content: '';
    background: url(images/sprite.png) no-repeat -697px -262px
}

.player-info .info ul.imported li:before {
    right: 4px
}

.player-info .info ul .replaced:before {
    background-position: -654px -262px
}

.player-info .info ul .replaced2:before {
    background-position: -611px -262px
}

.player-info .info ul .goals:before {
    background-position: -569px -262px
}

.player-info .info ul .cooking:before {
    background-position: -527px -262px
}

.player-info .info ul .yellow:before {
    background-position: -483px -262px
}

.player-info .info ul .red:before {
    background-position: -443px -262px
}

.player-info .info ul .desc {
    display: block;
    padding: 0 0 2px;
    font-size: 10px;
    margin: 0 0 9px;
    line-height: 13px;
    border-bottom: 1px solid #ececec
}

.player-info .info .rate {
    position: absolute;
    left: 23px;
    top: 52px;
    background: url(images/sprite.png) no-repeat -697px -168px;
    width: 35px;
    text-align: center;
    font-size: 11px;
    line-height: 34px;
    font-weight: bold;
    color: #fff;
    height: 34px
}

.about-block {
    border: 1px solid #c3cad1;
    line-height: 18px;
    padding: 0 0 13px;
    margin-bottom: 18px
}

.about-block img {
    display: block;
    margin: 5px 0 13px
}

.about-block .text {
    padding: 5px 12px 0;
    font-family: 'blender-regular'
}

#main .about-block .text h3 {
    font-size: 18px;
    line-height: 18px;
    color: #014bad;
    margin: 8px 0 8px;
    font-weight: normal;
    font-family: 'blender-regular'
}

#main .about-block p {
    margin: 0 0 16px
}

.about-block dl {
    overflow: hidden;
    margin: 0;
    font-size: 14px;
    line-height: 18px
}

.about-block dl dt {
    float: right;
    margin: 0 0 0 15px;
    font-weight: bold
}

.about-block dl dd {
    display: block;
    margin: 0
}

.table2 td,
.table2 th {
    text-align: right;
    width: auto
}

.table2 td:first-child,
.table2 th:first-child {
    padding: 0 17px 0;
    width: 89px
}

.table2 .last {
    text-align: center;
    padding: 0 0 0 10px;
    width: 27px
}

.premiership-block2 {
    margin: 0 0 18px;
    border: 1px solid #e8e8e8
}

.map-block {
    font-size: 14px;
    line-height: 16px;
    border: 1px solid #c3cad1;
    padding: 0 1px 1px;
    position: relative;
    margin-bottom: 18px
}

.map-block .text {
    padding: 0 15px 5px 15px;
    line-height: 17px;
    font-family: 'blender-regular'
}

.map-block img,
.map-block iframe {
    display: block;
    margin: 4px 0 21px;
    border-bottom: 1px solid #c3cad1
}

.map-block iframe,
.map-block small {
    display: none
}

.map-block a.gmap img {
    position: absolute;
    margin: 0;
    top: 40px;
    left: 8px
}

#main .map-block p {
    margin: 0 0 16px
}

.main-holder:after {
    content: "";
    display: block;
    clear: both
}

.main-block2 {
    padding: 0 0 0 12px;
    width: 641px
}

.events_archive .main-block2 {
    width: 624px;
    float: right
}

.main-block2:after {
    content: "";
    display: block;
    clear: both
}

.main-block2 .heading2 {
    padding: 4px 24px 2px;
    margin: 0 0 10px;
    overflow: hidden;
    border: 1px solid #000;
    background: #111821 url(images/bg-heading2.gif) repeat-x
}

#main .main-block2 .heading2 h1 {
    font: normal 18px/22px 'blender-bold', 'Mukta-ExtraBold', 'south web bold', Arial, Helvetica, sans-serif;
    color: #fff;
    margin: 0
}

.program-block {
    overflow: hidden;
    background: #181F28;
    padding: 11px 7px 8px 8px;
    border: 1px solid #000;
    margin: 0 0 18px
}

.program-block .gallery8 .gmask ul a {
    padding: 4px 4px 6px
}

.program-block .gallery8 .gmask ul a.live img {
    margin-top: 3px
}

.program-block.scoreboard-block {
    background: 0;
    border: 0;
    padding-top: 0
}

.program-block .title {
    overflow: hidden;
    margin: 0 14px 16px 21px;
    border-radius: 5px;
    color: #fff;
    background: #181F28;
    padding: 9px 10px 0 9px;
    text-align: center
}

.program-block .title .btn-prev,
.program-block .title .btn-next {
    overflow: hidden;
    text-indent: -9999px;
    width: 10px;
    height: 11px;
    display: inline-block;
    background: url(images/sprite.png) no-repeat -677px -118px
}

.program-block .title .btn-prev.disable,
.program-block .title .btn-next.disable {
    display: none
}

.program-block .title .btn-next {
    background-position: -665px -118px
}

#main .program-block .title span.day {
    font: 18px/18px 'blender-bold', 'Mukta-ExtraBold', 'south web bold', Arial, Helvetica, sans-serif;
    color: #fff;
    margin: 0;
    display: inline-block;
    text-align: center;
    min-width: 170px
}

.gallery8 {
    overflow: hidden;
    margin: 0 -7px 0 -8px
}

.gallery8 .btn-prev,
.gallery8 .btn-next {
    overflow: hidden;
    text-indent: -9999px;
    float: right;
    width: 15px;
    height: 28px;
    background: url(images/sprite.png) no-repeat -705px -118px
}

.gallery8 .btn-next {
    float: left;
    width: 14px;
    background-position: -689px -118px
}

.gallery8 .gmask {
    overflow: hidden
}

.gallery8 .gmask ul {
    list-style: none;
    margin: 0;
    width: 9999px;
    padding: 0;
    text-align: center;
    overflow: hidden;
    font: 12px/20px 'blender-bold', 'Mukta-ExtraBold', 'south web bold', Arial, Helvetica, sans-serif;
    color: #fff;
    padding-right: 7px
}

.gallery8 .gmask ul li {
    float: right;
    background: url(images/separator2.gif) repeat-y
}

.gallery8 .gmask ul img {
    vertical-align: top
}

.gallery8 .gmask ul li a img:last-child {
    display: none
}

.gallery8 .gmask ul a {
    color: #fff;
    padding: 4px 5px 6px;
    min-width: 77px;
    display: inline-block;
    vertical-align: top;
    height: 37px;
    position: relative;
    border-top: 1px solid #464d58
}

.gallery8 .gmask ul a>* {
    position: relative;
    z-index: 2
}

.gallery8 .gmask ul span {
    display: inline-block;
    vertical-align: top;
    padding: 2px 0 0;
    line-height: 26px
}

.gallery8 .gmask ul a:hover,
.gallery8 .gmask ul li.active a {
    color: #000;
    background: #fff;
    border-top: 1px solid #464d58
}

.gallery8 .gmask ul a:hover img:last-child,
.gallery8 .gmask ul li.active a img:last-child {
    display: inline-block
}

.gallery8 .gmask ul a:hover img:first-child,
.gallery8 .gmask ul li.active a img:first-child {
    display: none
}

.gallery8 .gmask ul a:hover:before,
.gallery8 .gmask ul li.active a:before,
.gallery8 .gmask ul a:hover:after,
.gallery8 .gmask ul li.active a:after {
    position: absolute;
    top: -1px;
    right: 0;
    width: 23px;
    height: 28px;
    content: '';
    background: url(images/bg-tab-hover2.jpg) no-repeat 100% 0
}

.gallery8 .gmask ul a:hover:before,
.gallery8 .gmask ul li.active a:before {
    background-position: 0 0;
    left: 0;
    right: auto
}

.program-block .tab-content {
    overflow: hidden;
    background: #fff;
    padding: 2px 4px;
    min-height: 629px;
    min-width: 615px
}

.program-block table {
    border-collapse: collapse;
    width: 100%;
    margin: 0;
    color: #000;
    direction: rtl
}

.program-block table th {
    padding: 10px 18px;
    font: 14px/16px 'blender-bold', 'Mukta-ExtraBold', 'south web bold', Arial, Helvetica, sans-serif;
    text-align: right;
    border-bottom: 3px solid #014bad;
    background: #f5f5f5;
    background: -moz-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(237, 237, 237, 1)), color-stop(19%, rgba(248, 248, 248, 1)), color-stop(35%, rgba(253, 253, 253, 1)), color-stop(100%, rgba(255, 255, 255, 1)));
    background: -webkit-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -o-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -ms-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: linear-gradient(to bottom, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%)
}

.program-block table th span.channel_numbers {
    float: left;
    font: 12px/19px Arial, Helvetica, sans-serif;
    font-weight: bold
}

.program-block table td {
    background: #E5E5E5;
    height: 32px;
    vertical-align: middle
}

.program-block table .alt td {
    background: #fff
}

.program-block table td:first-child {
    width: 30px;
    text-align: center
}

.program-block table td.date {
    width: 63px;
    font-size: 16px;
    display: table-cell;
    line-height: 16px;
    color: #000
}

.program-block table .now td,
.program-block table .now td.date {
    background: #014bad;
    color: #fff
}

.program-block table td.last {
    width: 55px
}

.program-block .no-programs div {
    text-align: center;
    font-weight: bold;
    display: block;
    margin-top: 80px
}

#tiptip_holder {
    display: none;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 99999
}

#tiptip_holder.tip_top {
    padding-bottom: 5px
}

#tiptip_holder.tip_bottom {
    padding-top: 5px
}

#tiptip_holder.tip_right {
    padding-left: 5px
}

#tiptip_holder.tip_left {
    padding-right: 5px
}

#tiptip_content {
    font-size: 11px;
    color: #fff;
    text-shadow: 0 0 2px #000;
    padding: 4px 8px;
    border: 1px solid rgba(255, 255, 255, 0.25);
    /* background-image: linear-gradient(bottom, #000 0, transparent 100%); */
    background-image: -o-linear-gradient(bottom, #000 0, transparent 100%);
    background-image: -moz-linear-gradient(bottom, #000 0, transparent 100%);
    background-image: -webkit-linear-gradient(bottom, #000 0, transparent 100%);
    background-image: -ms-linear-gradient(bottom, #000 0, transparent 100%);
    background-color: Black;
    border-radius: 3px;
    box-shadow: 0 0 3px #555;
    text-align: right;
    direction: rtl;
    line-height: 16px
}

#tiptip_arrow,
#tiptip_arrow_inner {
    position: absolute;
    border-color: transparent;
    border-style: solid;
    border-width: 6px;
    height: 0;
    width: 0;
    display: none
}

#tiptip_holder.tip_top #tiptip_arrow {
    border-top-color: #fff;
    border-top-color: rgba(255, 255, 255, 0.35)
}

#tiptip_holder.tip_bottom #tiptip_arrow {
    border-bottom-color: #fff;
    border-bottom-color: rgba(255, 255, 255, 0.35)
}

#tiptip_holder.tip_right #tiptip_arrow {
    border-right-color: #fff;
    border-right-color: rgba(255, 255, 255, 0.35)
}

#tiptip_holder.tip_left #tiptip_arrow {
    border-left-color: #fff;
    border-left-color: rgba(255, 255, 255, 0.35)
}

#tiptip_holder.tip_top #tiptip_arrow_inner {
    margin-top: -7px;
    margin-left: -6px;
    border-top-color: #191919;
    border-top-color: rgba(25, 25, 25, 0.92)
}

#tiptip_holder.tip_bottom #tiptip_arrow_inner {
    margin-top: -5px;
    margin-left: -6px;
    border-bottom-color: #191919;
    border-bottom-color: rgba(25, 25, 25, 0.92)
}

#tiptip_holder.tip_right #tiptip_arrow_inner {
    margin-top: -6px;
    margin-left: -5px;
    border-right-color: #191919;
    border-right-color: rgba(25, 25, 25, 0.92)
}

#tiptip_holder.tip_left #tiptip_arrow_inner {
    margin-top: -6px;
    margin-left: -7px;
    border-left-color: #191919;
    border-left-color: rgba(25, 25, 25, 0.92)
}

.pic-page {
    text-align: center;
    margin-bottom: 18px
}

.pic-page>div {
    position: relative;
    display: inline-block
}

.pic-page .social-networks {
    position: absolute;
    right: 12px;
    top: 12px
}

.pic-page figure {
    padding: 8px 8px 4px 8px;
    background: #141c27;
    margin: 0
}

.pic-page figure img {
    max-width: 928px
}

.pic-page figure figcaption {
    text-align: center;
    color: white;
    font-size: 15px;
    line-height: 16px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web';
    padding-top: 2px
}

.person-block {
    overflow: hidden;
    position: relative;
    background: #fafafa;
    padding: 16px 14px 15px;
    margin: 0 0 18px;
    border: 1px solid #c3cad1;
    background: -moz-linear-gradient(top, #fafafa 0, #fafafa 33%, #ededed 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #fafafa), color-stop(33%, #fafafa), color-stop(100%, #ededed));
    background: -webkit-linear-gradient(top, #fafafa 0, #fafafa 33%, #ededed 100%);
    background: -o-linear-gradient(top, #fafafa 0, #fafafa 33%, #ededed 100%);
    background: -ms-linear-gradient(top, #fafafa 0, #fafafa 33%, #ededed 100%);
    background: linear-gradient(to bottom, #fafafa 0, #fafafa 33%, #ededed 100%)
}

.person-block .rate {
    position: absolute;
    left: 15px;
    top: 11px;
    background: url(images/sprite.png) no-repeat -697px -168px;
    width: 35px;
    text-align: center;
    font-size: 11px;
    line-height: 34px;
    font-weight: bold;
    color: #fff;
    height: 38px
}

.person-block .info-holder {
    overflow: hidden;
    line-height: 18px;
    margin: 0 0 14px
}

.person-block .info-holder p:empty {
    display: none
}

.person-block .info-holder .img-holder {
    float: right;
    margin: 0 0 0 46px;
    text-align: center
}

.person-block .info-holder .img-holder>img {
    display: block;
    margin: 0 0 7px;
    border: 1px solid #fff
}

.person-block .like {
    vertical-align: top
}

.person-block .info-holder .holder {
    overflow: hidden;
    padding: 4px 0 0;
    font-size: 12px
}

#main .person-block .info-holder h1 {
    font-size: 22px;
    line-height: 24px;
    margin: 0 0 20px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold'
}

.person-block .info-holder .holder ul {
    list-style: none;
    font-size: 12px;
    line-height: 14px;
    color: #1b1b1b;
    margin: 0;
    padding: 0 0 15px;
    overflow: hidden
}

.person-block .info-holder .holder ul li {
    float: right;
    position: relative;
    padding: 0 14px 0 30px
}

.person-block .info-holder .holder ul li:before {
    content: '';
    position: absolute;
    top: 3px;
    right: 0;
    background: url(images/sprite.png) no-repeat -95px 2px;
    width: 5px;
    height: 7px
}

.person-block ul.social-holder {
    margin: 0 auto;
    width: 100%
}

.person-block ul.social-holder li {
    float: none;
    display: inline;
    text-align: center
}

.person-block ul.social-holder a {
    float: none;
    position: relative;
    display: inline-block
}

.person-block ul.social-holder a img {
    display: inline;
    position: absolute;
    margin-top: 2px
}

.person-block ul.social-holder li:last-child img {
    margin-top: 6px
}

.person-block ul.social-holder label {
    background: 0;
    color: #637aab;
    float: none;
    cursor: pointer;
    margin-right: 13px
}

.person-block p {
    margin: 0 0 10px
}

.person-block .info-data {
    overflow: hidden;
    font-size: 12px;
    line-height: 14px;
    padding: 0 1px 0 2px
}

.person-block .info-data .img-holder {
    float: right;
    margin: 31px 0 0;
    border-right: 1px solid #e0e0e0;
    background: #fff;
    width: 162px;
    height: 156px;
    background: white;
    text-align: center;
    position: relative
}

.person-block .info-data .img-holder img {
    display: block;
    margin: 0 auto;
    padding-top: 18px
}

.person-block .info-data .img-holder span {
    position: absolute;
    bottom: -7px;
    font-size: 42px;
    display: inline-block;
    width: 44px;
    line-height: 44px;
    right: 1px;
    cursor: default;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web'
}

.person-block .table-holder:after {
    content: "";
    display: block;
    clear: both
}

.person-block .info-table {
    border-collapse: collapse;
    padding: 0;
    margin: 0;
    font-size: 12px;
    text-align: center;
    background: #fff
}

.person-block .info-table:first-child {
    margin: 0 0 9px
}

.person-block .info-table:first-child td {
    padding: 2px 2px 4px;
    position: relative;
    border-top: 0
}

.person-block .info-table td {
    width: 51px;
    padding: 5px 2px;
    vertical-align: bottom;
    border: 1px solid #e2e2e2
}

.person-block .imported .info-table td {
    width: 55px
}

.person-block .info-table td:first-child {
    width: 35px
}

.person-block .imported .info-table td:first-child {
    width: 60px
}

.person-block .info-table td.align-middle {
    vertical-align: middle;
    padding-top: 25px
}

.person-block .info-table tr.big td {
    font-size: 30px;
    line-height: 32px;
    width: 51px;
    padding: 8px 2px
}

.title-image {
    list-style: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    text-align: left
}

.title-image li {
    margin: 0 0 0 6px;
    display: inline-block;
    vertical-align: top
}

.imported .title-image li {
    margin: 0 0 0 12px
}

.title-image li img {
    display: block
}

.time-line {
    overflow: hidden;
    display: none
}

.time-line .title {
    float: right;
    border-left: 1px solid #c3c3c3;
    width: 149px;
    position: relative;
    padding: 59px 14px 17px 0;
    text-align: center
}

.time-line .title:before {
    position: absolute;
    top: 0;
    left: 0;
    width: 1px;
    height: 100%;
    content: '';
    background: #fff
}

.time-line .title:after {
    position: absolute;
    top: 30px;
    left: 67px;
    content: '';
    background: url(images/sprite.png) no-repeat -707px -207px;
    width: 18px;
    height: 24px
}

.time-line .title span {
    font-size: 18px;
    line-height: 20px;
    margin: 0;
    font-weight: normal
}

.person-block a.more-stat {
    font-size: 12px;
    float: left;
    margin-top: 15px
}

.person-block a.more-stat:hover {
    color: #fff
}

.slider-holder {
    width: 393px;
    float: right;
    margin: 15px 35px 0 0
}

.slider-holder .slider {
    border-radius: 15px;
    -moz-border-radius: 15px;
    padding: 4px 3px;
    position: relative;
    margin: 0 0 12px;
    -webkit-border-radius: 15px;
    background: #d0d0d0;
    background: -moz-linear-gradient(top, #d0d0d0 0, #d9d9d9 10%, #dcdcdc 16%, #dcdcdc 97%, #dadada 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #d0d0d0), color-stop(10%, #d9d9d9), color-stop(16%, #dcdcdc), color-stop(97%, #dcdcdc), color-stop(100%, #dadada));
    background: -webkit-linear-gradient(top, #d0d0d0 0, #d9d9d9 10%, #dcdcdc 16%, #dcdcdc 97%, #dadada 100%);
    background: -o-linear-gradient(top, #d0d0d0 0, #d9d9d9 10%, #dcdcdc 16%, #dcdcdc 97%, #dadada 100%);
    background: -ms-linear-gradient(top, #d0d0d0 0, #d9d9d9 10%, #dcdcdc 16%, #dcdcdc 97%, #dadada 100%);
    background: linear-gradient(to bottom, #d0d0d0 0, #d9d9d9 10%, #dcdcdc 16%, #dcdcdc 97%, #dadada 100%)
}

.slider-holder .slider:after {
    content: '';
    position: absolute;
    bottom: -11px;
    left: 20px;
    width: 360px;
    height: 5px;
    background: url(images/bg-time-line.gif)
}

.slider-holder .slider .holder {
    border-radius: 12px;
    -moz-border-radius: 12px;
    -webkit-border-radius: 12px;
    font-size: 11px;
    line-height: 26px;
    text-align: center;
    height: 26px;
    color: #fff
}

.slider-holder .slider-range {
    float: left;
    overflow: hidden;
    direction: ltr;
    height: 26px
}

.slider-holder .slider-range-left {
    border-bottom-left-radius: 12px;
    border-top-left-radius: 12px;
    -moz-border-radius-topleft: 12px;
    -moz-border-radius-bottomleft: 12px;
    -webkit-border-top-left-radius: 12px;
    -webkit-border-bottom-left-radius: 12px
}

.slider-holder .slider-range-right {
    border-bottom-right-radius: 12px;
    border-top-right-radius: 12px;
    -moz-border-radius-topright: 12px;
    -moz-border-radius-bottomright: 12px;
    -webkit-border-top-right-radius: 12px;
    -webkit-border-bottom-right-radius: 12px
}

.slider-holder .slider-range span {
    direction: rtl;
    cursor: default
}

.slider-holder .date-list {
    list-style: none;
    margin: 0;
    padding: 0 10px 13px;
    font-size: 10px;
    line-height: 12px;
    color: #1b1b1b;
    direction: ltr
}

.slider-holder .date-list li {
    display: inline;
    margin: 0 30px 0 0;
    position: relative
}

.slider-holder .date-list li:last-child {
    margin: 0
}

.slider-holder .date-list li img {
    position: absolute;
    bottom: 56px;
    left: 5px
}

.slider-holder .notes {
    list-style: none;
    margin: 0 0 0 -2px;
    padding: 0;
    text-align: left;
    font-size: 11px;
    color: #1b1b1b;
    line-height: 13px
}

.slider-holder .notes li {
    display: inline;
    position: relative;
    padding: 0 11px 0 10px
}

.slider-holder .notes li img {
    padding-left: 5px;
    vertical-align: middle
}

.slider-holder .slider-handle {
    position: absolute;
    top: -13px;
    left: 31.8%;
    overflow: hidden;
    text-indent: -9999px;
    background: url(images/sprite.png) no-repeat -687px -207px;
    width: 10px;
    height: 15px
}

.slider-holder .slider-handle2 {
    left: 58.2%;
    background-position: -697px -207px
}

.article-list3 {
    width: 631px;
    background-position: 0 -360px;
    padding: 0 0 24px
}

.article-list3:after {
    width: 631px;
    background-position: 0 -360px
}

.player-block {
    margin: 0 0 18px;
    border: 1px solid #c3cad1;
    font-size: 14px;
    line-height: 18px;
    background: #f9f9f9;
    background: -moz-linear-gradient(top, #f9f9f9 0, #f8f8f8 34%, #f0f0f0 73%, #e8e8e8 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #f9f9f9), color-stop(34%, #f8f8f8), color-stop(73%, #f0f0f0), color-stop(100%, #e8e8e8));
    background: -webkit-linear-gradient(top, #f9f9f9 0, #f8f8f8 34%, #f0f0f0 73%, #e8e8e8 100%);
    background: -o-linear-gradient(top, #f9f9f9 0, #f8f8f8 34%, #f0f0f0 73%, #e8e8e8 100%);
    background: -ms-linear-gradient(top, #f9f9f9 0, #f8f8f8 34%, #f0f0f0 73%, #e8e8e8 100%);
    background: linear-gradient(to bottom, #f9f9f9 0, #f8f8f8 34%, #f0f0f0 73%, #e8e8e8 100%);
    padding: 0 0 21px
}

#sidebar .player-block .heading2 {
    border-right: 0;
    border-left: none
}

.player-block .holder {
    padding: 0 18px 0 5px;
    overflow: hidden
}

.player-block p {
    margin: 0 0 18px
}

.player-block .img-list {
    float: right;
    color: #313131;
    list-style: none;
    margin: 0;
    padding: 1px 0 0 8px
}

.player-block .img-list li {
    float: right;
    margin: 0 5px 0 0;
    border: 1px solid #1b1b1b
}

.player-block .img-list li:first-child {
    margin: 0
}

.player-block .img-list img {
    display: block
}

.player-block .control {
    float: right;
    margin: 0;
    font-size: 12px;
    line-height: 14px;
    width: 99px
}

.player-block .control select {
    width: 99px;
    margin: 0 0 9px
}

a.more-stat,
.player-block .control a.compare {
    color: #fff;
    text-align: center;
    display: block;
    padding: 3px 5px 4px;
    font-weight: bold;
    border-radius: 4px;
    -moz-border-radius: 4px;
    margin: -1px 0 0;
    -webkit-border-radius: 4px;
    background: #4188eb;
    background: -moz-linear-gradient(top, #4188eb 0, #3a81e7 20%, #1a68d7 70%, #0c5cd0 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #4188eb), color-stop(20%, #3a81e7), color-stop(70%, #1a68d7), color-stop(100%, #0c5cd0));
    background: -webkit-linear-gradient(top, #4188eb 0, #3a81e7 20%, #1a68d7 70%, #0c5cd0 100%);
    background: -o-linear-gradient(top, #4188eb 0, #3a81e7 20%, #1a68d7 70%, #0c5cd0 100%);
    background: -ms-linear-gradient(top, #4188eb 0, #3a81e7 20%, #1a68d7 70%, #0c5cd0 100%);
    background: linear-gradient(to bottom, #4188eb 0, #3a81e7 20%, #1a68d7 70%, #0c5cd0 100%)
}

.info-modal,
.player-modal {
    padding: 9px 11px;
    border: 1px solid #fff;
    direction: rtl;
    font-size: 12px;
    line-height: 14px;
    border-radius: 2px;
    min-height: 454px;
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    width: 607px;
    background: #f9f9f9;
    background: -moz-linear-gradient(top, #f9f9f9 0, #f8f8f8 40%, #f0f0f0 92%, #ededed 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #f9f9f9), color-stop(40%, #f8f8f8), color-stop(92%, #f0f0f0), color-stop(100%, #ededed));
    background: -webkit-linear-gradient(top, #f9f9f9 0, #f8f8f8 40%, #f0f0f0 92%, #ededed 100%);
    background: -o-linear-gradient(top, #f9f9f9 0, #f8f8f8 40%, #f0f0f0 92%, #ededed 100%);
    background: -ms-linear-gradient(top, #f9f9f9 0, #f8f8f8 40%, #f0f0f0 92%, #ededed 100%);
    background: linear-gradient(to bottom, #f9f9f9 0, #f8f8f8 40%, #f0f0f0 92%, #ededed 100%)
}

.player-modal {
    width: 500px
}

.info-modal .title,
.player-modal .title {
    border-radius: 5px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    background: #0252c1;
    margin: 0 0 11px;
    color: #fff;
    padding: 3px 16px 4px 8px;
    overflow: hidden
}

.info-modal .close,
.player-modal .close {
    float: left;
    overflow: hidden;
    text-indent: -9999px;
    width: 12px;
    height: 12px;
    background: url(images/sprite.png) no-repeat -102px 0;
    margin: 5px 0 0;
    opacity: .6
}

.info-modal .title h2,
.player-modal .title h2 {
    font-size: 16px;
    line-height: 20px;
    margin: 0
}

.info-modal .holder {
    overflow: hidden;
    padding: 0 66px 0 57px;
    background: #e1e1e1;
    margin: 0 2px 0 0;
    background: -moz-linear-gradient(top, #e1e1e1 0, #f1f1f1 2%, #f7f7f7 3%, #f9f9f9 4%, #f8f8f8 33%, #f0f0f0 91%, #ededed 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #e1e1e1), color-stop(2%, #f1f1f1), color-stop(3%, #f7f7f7), color-stop(4%, #f9f9f9), color-stop(33%, #f8f8f8), color-stop(91%, #f0f0f0), color-stop(100%, #ededed));
    background: -webkit-linear-gradient(top, #e1e1e1 0, #f1f1f1 2%, #f7f7f7 3%, #f9f9f9 4%, #f8f8f8 33%, #f0f0f0 91%, #ededed 100%);
    background: -o-linear-gradient(top, #e1e1e1 0, #f1f1f1 2%, #f7f7f7 3%, #f9f9f9 4%, #f8f8f8 33%, #f0f0f0 91%, #ededed 100%);
    background: -ms-linear-gradient(top, #e1e1e1 0, #f1f1f1 2%, #f7f7f7 3%, #f9f9f9 4%, #f8f8f8 33%, #f0f0f0 91%, #ededed 100%);
    background: linear-gradient(to bottom, #e1e1e1 0, #f1f1f1 2%, #f7f7f7 3%, #f9f9f9 4%, #f8f8f8 33%, #f0f0f0 91%, #ededed 100%);
    min-height: 405px
}

.info-modal .item {
    float: right;
    padding: 17px 0 0;
    width: 111px
}

.info-modal .item-alt {
    float: left;
    text-align: left
}

.info-modal .img-holder {
    border: 1px solid #161616;
    margin: 0 0 6px;
    width: 104px
}

.info-modal img {
    display: block
}

.info-modal h3 {
    font-size: 18px;
    line-height: 22px;
    margin: 0 0 7px;
    font-weight: normal;
    color: #000
}

.info-modal ul {
    list-style: none;
    margin: 0;
    padding: 0 2px 22px;
    color: #1b1b1b
}

.info-modal ul li {
    position: relative;
    padding: 0 12px 6px 0
}

.info-modal ul li:before {
    position: absolute;
    top: 6px;
    right: 0;
    width: 5px;
    height: 5px;
    content: '';
    background: url(images/sprite.png) no-repeat -95px 0
}

.info-modal .img-item {
    margin: 0 5px 0 -2px;
    display: inline-block;
    vertical-align: top
}

.info-modal .shirt-holder {
    position: relative;
    text-align: center
}

.info-modal .shirt-holder span {
    position: absolute;
    bottom: -7px;
    font-size: 42px;
    display: inline-block;
    width: 56px;
    line-height: 44px;
    left: 85px;
    cursor: default;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web'
}

.info-modal .item-alt ul li {
    padding: 0 0 6px 12px
}

.info-modal .item-alt ul li:before {
    right: auto;
    left: 0
}

.info-modal .item-alt .shirt-holder span {
    left: -35px
}

.info-modal table.score {
    border-collapse: collapse;
    padding: 0;
    font-size: 30px;
    direction: ltr;
    line-height: 32px;
    vertical-align: middle;
    text-align: center;
    margin: 0 56px 0 40px;
    width: 161px;
    float: right
}

.info-modal table.score td {
    border-top: 1px solid #dfdfdf;
    border-left: 1px solid #dfdfdf;
    padding: 0
}

.info-modal table.score td.img {
    width: 39px
}

.info-modal table.score img {
    display: inline-block;
    vertical-align: top
}

.info-modal table.score td:first-child {
    border-top: 1px solid #fff;
    border-left: 1px solid transparent
}

.info-modal table.score tr:first-child td {
    border-top: 1px solid #dfdfdf
}

.select-area {
    background: url(images/sprite.png) no-repeat -598px -185px;
    position: relative;
    overflow: hidden;
    cursor: default;
    height: 22px;
    font-size: 12px;
    line-height: 14px;
    color: #313131;
    margin: 0 0 9px;
    display: block
}

.select-area .center {
    white-space: nowrap;
    padding: 4px 17px;
    display: block
}

.select-area .select-opener {
    background: url(images/sprite.png) no-repeat -515px -185px;
    position: absolute;
    height: 22px;
    width: 16px;
    right: 0;
    top: 0
}

.select-options {
    position: absolute;
    overflow: hidden;
    background: #fff;
    z-index: 2000
}

.select-options .drop-holder {
    border: 1px solid #dbdfe8;
    overflow: hidden;
    height: 1%
}

.select-options ul {
    list-style: none;
    padding: 0;
    margin: 0
}

.select-options ul li {
    width: 100%;
    float: left
}

.select-options ul a {
    text-decoration: none;
    padding: 1px 5px;
    display: block;
    cursor: default;
    color: #000;
    height: 1%;
    font-size: 12px;
    text-align: right
}

.select-options .item-selected a {
    text-decoration: none;
    background: #dbdfe8;
    color: #000
}

.select-options .optgroup {
    clear: both
}

.select-options .optgroup strong {
    display: block;
    padding: 5px
}

.select-options .optgroup ul a {
    padding-left: 30px
}

.player-page #fancybox-close {
    display: none !important
}

.items {
    overflow: hidden;
    border-bottom: 1px solid #e8e8e8;
    position: relative;
    z-index: 2;
    margin: 0 0 19px;
    padding: 0 0 13px
}

.recommendations-block .score:after,
.items:after {
    width: 100%;
    position: absolute;
    z-index: -1;
    left: 0;
    height: 46px;
    content: '';
    bottom: 5px;
    background: #fff;
    background: -moz-linear-gradient(top, #fff 0, #fefefe 78%, #f8f8f8 93%, #f3f3f3 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #fff), color-stop(78%, #fefefe), color-stop(93%, #f8f8f8), color-stop(100%, #f3f3f3));
    background: -webkit-linear-gradient(top, #fff 0, #fefefe 78%, #f8f8f8 93%, #f3f3f3 100%);
    background: -o-linear-gradient(top, #fff 0, #fefefe 78%, #f8f8f8 93%, #f3f3f3 100%);
    background: -ms-linear-gradient(top, #fff 0, #fefefe 78%, #f8f8f8 93%, #f3f3f3 100%);
    background: linear-gradient(to bottom, #fff 0, #fefefe 78%, #f8f8f8 93%, #f3f3f3 100%)
}

.items:before {
    position: absolute;
    content: '';
    width: 100%;
    height: 3px;
    bottom: 1px;
    left: 0;
    background: #131a25
}

#main .items>.heading {
    border-top: 1px solid #c5d0db;
    border-bottom: 3px solid #141c27;
    margin: 0 0 26px;
    background: #ededed;
    background: -moz-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(237, 237, 237, 1)), color-stop(19%, rgba(248, 248, 248, 1)), color-stop(35%, rgba(253, 253, 253, 1)), color-stop(100%, rgba(255, 255, 255, 1)));
    background: -webkit-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -o-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -ms-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: linear-gradient(to bottom, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%)
}

#main .items>.heading h1 {
    font-size: 17px;
    line-height: 20px;
    padding: 12px 25px 8px;
    border-top: 1px solid #fff;
    margin: 0;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    font-weight: normal
}

#main .article-list3 {
    border: 0;
    margin-bottom: 8px;
    padding: 0
}

#main .article-list3:after {
    display: none
}

#main .article-list3>li:first-child,
#main .article-list3>li {
    width: 304px;
    border: 1px solid #e8e8e8;
    margin: 0 0 13px 11px;
    padding: 0 0 9px;
    border-width: 0 0 1px
}

#main .article-list3 .img-holder {
    float: right;
    margin: 0 0 10px 16px
}

#main .events_archive .article-list3 .img-holder {
    width: 138px;
    height: 107px
}

#main .article-list2 .holder,
#main .article-list3 .holder {
    overflow: hidden
}

#main .article-list3 h2 {
    margin-top: 2px;
    height: 47px
}

#main .events_archive .article-list3 h2 {
    margin-top: 2px;
    height: 32px
}

#main .article-list3 h3,
#main .article-list3 p.abstract {
    margin: 0 0 8px;
    line-height: 15px;
    height: 45px
}

.paging {
    list-style: none;
    margin: 0;
    padding: 0;
    text-align: center;
    font-size: 12px;
    line-height: 15px;
    direction: ltr
}

.paging li {
    display: inline-block;
    vertical-align: middle;
    text-align: left;
    margin: 0 6px 0 7px
}

.paging .next,
.paging .prev {
    display: block;
    overflow: hidden;
    width: 6px;
    height: 7px;
    text-indent: -9999px;
    background: url(images/sprite.png) no-repeat -410px -40px
}

.paging .next {
    background-position: -417px -40px
}

.paging a {
    color: #014bad
}

.paging a:hover,
.paging li.active a {
    color: #141c27
}

.twtr-widget-profile {
    border: 1px solid #e8e8e8;
    margin-bottom: 18px
}

.twtr-doc {
    border: 1px solid #fff;
    border-top: 0;
    border-radius: 0 !important
}

.twtr-hd {
    padding: 7px 0 0 !important;
    border-bottom-width: 3px
}

.twtr-hd a {
    float: left
}

.twtr-hd a img {
    margin-top: 1px
}

.twtr-ft {
    border-top: 1px dotted #ddd;
    line-height: 22px;
    padding: 3px 5px 7px;
    background: #f5f5f5;
    background: -moz-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(237, 237, 237, 1)), color-stop(19%, rgba(248, 248, 248, 1)), color-stop(35%, rgba(253, 253, 253, 1)), color-stop(100%, rgba(255, 255, 255, 1)));
    background: -webkit-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -o-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -ms-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: linear-gradient(to bottom, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%)
}

.twtr-ft div {
    padding: 0 !important
}

.twtr-ft div a {
    color: #014bad !important
}

.twtr-ft div>a:first-child {
    display: none
}

.twtr-ft span {
    padding-left: 20px
}

.twtr-ft span,
.twtr-ft span a {
    float: left !important
}

.twtr-timeline {
    height: auto !important
}

#twtr-widget-1 h3 {
    text-align: left
}

#twtr-widget-1 h4 {
    text-align: left;
    font-weight: normal
}

#twtr-widget-1 h4:after {
    content: '';
    background: url(images/twitter.png) no-repeat;
    width: 22px;
    height: 18px;
    position: absolute;
    right: 7px;
    top: 12px
}

.twtr-ft div span a:before {
    content: '';
    position: absolute;
    left: 10px;
    top: 10px;
    background: url(images/sprite.png) no-repeat;
    width: 6px;
    height: 9px
}

.homepage .facebook-widget .fb_iframe_widget {
    width: 142px !important
}

.homepage .fb_iframe_widget span {
    width: 304px !important
}

.homepage .facebook-widget .fb_iframe_widget iframe {
    visibility: visible !important;
    width: 304px !important
}

.facebook-widget {
    border: 1px solid #e8e8e8;
    margin-top: 7px;
    width: 304px
}

.facebook-widget>div {
    border-top: 0
}

.facebook-widget .fb_iframe_widget iframe {
    visibility: visible !important
}

#sidebar .facebook-widget .heading2 {
    border: 0;
    border-bottom: 3px solid #014bad;
    position: relative;
    color: #0242AE
}

#sidebar .facebook-widget .heading2 strong:after {
    content: '';
    position: absolute;
    left: 5px;
    top: 5px;
    background: url(images/facebook-icon.png) no-repeat;
    width: 22px;
    height: 22px
}

.facebook_image {
    vertical-align: middle;
    position: absolute;
    margin-top: 7px;
    left: 15px;
    z-index: 1
}

.twiter_image {
    vertical-align: middle;
    position: absolute;
    margin-top: 11px;
    left: 22px;
    z-index: 1
}

#sidebar .facebook-widget .fb-recommendations {
    overflow: hidden;
    margin-top: 3px
}

#sidebar .facebook-widget .fb-recommendations span {
    left: 3px;
    top: -1px;
    width: 297px !important;
    height: 147px !important
}

#sidebar .facebook-widget .fb-recommendations span iframe {
    left: -5px;
    top: -1px
}

.recomm-holder {
    direction: rtl;
    width: 378px;
    height: 303px;
    display: inline-block;
    text-align: center;
    padding: 22px 43px 23px 43px;
    background: #141c27;
    color: white;
    position: relative
}

.recomm-holder .btn-next,
.recomm-holder .btn-prev {
    display: inline-block;
    float: right;
    position: absolute;
    top: 50%;
    left: 8px;
    background: url('images/sprite.png') -352px -184px no-repeat;
    width: 15px;
    height: 18px;
    width: 20px;
    height: 20px
}

.recomm-holder .btn-prev {
    right: 8px;
    left: auto;
    background-position: -372px -184px
}

.recomm-holder .gmask {
    float: right;
    border-left: 1px solid #717478;
    border-right: 1px solid #717478;
    width: 380px;
    overflow: hidden
}

.recomm-holder .gmask .slider .slides {
    float: right;
    padding: 0 38px 0 14px;
    width: 326px
}

.recomm-holder .gmask .slider .clear {
    height: 20px
}

.recomm-holder .gmask .holder {
    float: right;
    margin: 0 0 0 18px;
    height: 142px;
    overflow: hidden
}

.recomm-holder .gmask .holder.alt {
    margin: 0 18px 0 0
}

#main .recomm-holder .gmask .holder .text-holder h2 {
    font-size: 15px;
    font-weight: normal;
    margin: 5px 0 0 0;
    width: 144px;
    height: 31px;
    overflow: hidden;
    line-height: 15px;
    font-family: arial;
    padding: 0
}

#main .recomm-holder .gmask .holder .text-holder h2 a {
    color: #fff
}

#main .recomm-holder .gmask .holder .text-holder h2 a:hover {
    color: #aaa
}

.score-container .table-block,
.recommendations-block {
    overflow: hidden;
    background: #7d7d7f;
    padding: 16px 14px 15px;
    margin: 0 0 11px
}

.score-form .title,
.score-container .title,
.recommendations-block .title,
.stat-tables>.title {
    overflow: hidden;
    padding: 0 18px 0 0;
    margin: 0 0 16px;
    background: #ed1c24
}

#main .recommendations-block .title h1,
#main .stat-tables>.title div.h1 {
    font: 16px/18px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    color: #fff;
    margin: 12px 0 0;
    display: block
}

#main .stat-tables>.title div.h1 h2 {
    font: 16px/18px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    color: #fff;
    margin: 12px 0 0;
    display: inline
}

.score-form .title img,
.score-container .title img,
#main .recommendations-block .title img,
.stat-tables>.title img {
    float: left
}

#main .recommendations-block .holder {
    padding: 0 2px 0 0
}

#main .recommendations-block .holder:after {
    content: '';
    display: block;
    clear: both
}

#main .recommendations-block .col {
    float: right;
    background: #eee;
    width: 216px;
    border-bottom: 1px solid #fff;
    margin: 0 16px 0 0
}

#main .recommendations-block .col:first-child {
    margin: 0
}

#main .recommendations-block .photo-holder {
    display: block;
    background: #ed1c24;
    color: #fff;
    font: 18px/20px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    overflow: hidden
}

#main .recommendations-block .photo-holder img {
    display: inline-block;
    vertical-align: middle
}

#main .recommendations-block .photo-holder .text {
    display: inline-block;
    vertical-align: middle;
    width: 75px;
    padding: 0 24px 0 0
}

#main .recommendations-block .col-alt .photo-holder {
    color: #000;
    background: #f9cf03
}

.recommendations-block .score {
    position: relative;
    overflow: hidden;
    font-size: 14px;
    padding: 13px 0 14px;
    background: #fff;
    text-align: center;
    line-height: 18px;
    z-index: 2;
    font-weight: bold
}

.recommendations-block .score:after {
    bottom: 0;
    width: 100%;
    height: 18px;
    left: 0;
    position: absolute;
    content: '';
    background: #fefefe;
    background: -moz-linear-gradient(top, #fefefe 0, #f5f5f5 41%, #dcdcdc 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #fefefe), color-stop(41%, #f5f5f5), color-stop(100%, #dcdcdc));
    background: -webkit-linear-gradient(top, #fefefe 0, #f5f5f5 41%, #dcdcdc 100%);
    background: -o-linear-gradient(top, #fefefe 0, #f5f5f5 41%, #dcdcdc 100%);
    background: -ms-linear-gradient(top, #fefefe 0, #f5f5f5 41%, #dcdcdc 100%);
    background: linear-gradient(to bottom, #fefefe 0, #f5f5f5 41%, #dcdcdc 100%)
}

#main .recommendations-block p {
    margin: 0 0 8px
}

.recommendations-block .score ul {
    margin: 0;
    padding: 0;
    list-style: none
}

.recommendations-block .score ul li {
    display: inline-block;
    vertical-align: top;
    margin: 0 0 0 1px;
    width: 38px;
    padding: 5px 5px 4px;
    border-radius: 5px;
    border: 1px solid #bfbfbf;
    border-bottom: 1px solid #5a5a5a;
    -webkit-box-shadow: 0 0 2px #d6d6d6;
    box-shadow: 0 0 2px #d6d6d6;
    background: #efefef;
    background: -moz-linear-gradient(top, #efefef 0, #ededed 28%, #e0e0e0 76%, #d6d6d6 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #efefef), color-stop(28%, #ededed), color-stop(76%, #e0e0e0), color-stop(100%, #d6d6d6));
    background: -webkit-linear-gradient(top, #efefef 0, #ededed 28%, #e0e0e0 76%, #d6d6d6 100%);
    background: -o-linear-gradient(top, #efefef 0, #ededed 28%, #e0e0e0 76%, #d6d6d6 100%);
    background: -ms-linear-gradient(top, #efefef 0, #ededed 28%, #e0e0e0 76%, #d6d6d6 100%);
    background: linear-gradient(to bottom, #efefef 0, #ededed 28%, #e0e0e0 76%, #d6d6d6 100%)
}

.recommendations-block .score ul li.red {
    color: #e30613
}

.recommendations-block .col>.text {
    padding: 13px 18px 0;
    line-height: 17px;
    overflow: hidden;
    height: 108px;
    margin-bottom: 30px
}

.recommendations-block .send {
    background: #bfbfbf;
    border-radius: 5px;
    overflow: hidden;
    display: block;
    margin: -17px auto 7px;
    width: 108px;
    text-align: center;
    color: #252426;
    font-size: 15px;
    line-height: 17px;
    font-weight: bold;
    padding: 2px 2px 2px 3px
}

.recommendations-block .send:hover {
    color: #252426 !important
}

.recommendations-block .send span {
    display: block;
    padding: 4px 5px 5px;
    border: 1px solid #b5a765;
    font-weight: bold;
    border-radius: 5px;
    -webkit-box-shadow: 0 1px 1px #fff inset;
    box-shadow: 0 1px 1px #fff inset;
    background: #ffd100;
    background: -moz-linear-gradient(top, #ffd100 0, #ffc900 58%, #f3bf00 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #ffd100), color-stop(58%, #ffc900), color-stop(100%, #f3bf00));
    background: -webkit-linear-gradient(top, #ffd100 0, #ffc900 58%, #f3bf00 100%);
    background: -o-linear-gradient(top, #ffd100 0, #ffc900 58%, #f3bf00 100%);
    background: -ms-linear-gradient(top, #ffd100 0, #ffc900 58%, #f3bf00 100%);
    background: linear-gradient(to bottom, #ffd100 0, #ffc900 58%, #f3bf00 100%)
}

.recommendations-block .col-alt .send {
    color: #fff !important
}

.recommendations-block .col-alt .send span {
    border: 1px solid #a86c67;
    background: #f21;
    -webkit-box-shadow: 0 1px 1px #f19d96 inset;
    box-shadow: 0 1px 1px #f19d96 inset;
    background: -moz-linear-gradient(top, #f21 0, #ff1e0e 38%, #e11406 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #f21), color-stop(38%, #ff1e0e), color-stop(100%, #e11406));
    background: -webkit-linear-gradient(top, #f21 0, #ff1e0e 38%, #e11406 100%);
    background: -o-linear-gradient(top, #f21 0, #ff1e0e 38%, #e11406 100%);
    background: -ms-linear-gradient(top, #f21 0, #ff1e0e 38%, #e11406 100%);
    background: linear-gradient(to bottom, #f21 0, #ff1e0e 38%, #e11406 100%)
}

.recommendations-block .table-block {
    position: relative;
    z-index: 2;
    background: #dbdbdb;
    display: table;
    padding: 0 0 11px;
    width: 100%;
    height: 96px
}

.recommendations-block .table-block:before {
    content: '';
    position: absolute;
    left: 0;
    top: 0;
    height: 17px;
    z-index: -1;
    width: 100%;
    background: #bebebe;
    background: -moz-linear-gradient(top, #bebebe 0, #d4d4d4 63%, #dbdbdb 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #bebebe), color-stop(63%, #d4d4d4), color-stop(100%, #dbdbdb));
    background: -webkit-linear-gradient(top, #bebebe 0, #d4d4d4 63%, #dbdbdb 100%);
    background: -o-linear-gradient(top, #bebebe 0, #d4d4d4 63%, #dbdbdb 100%);
    background: -ms-linear-gradient(top, #bebebe 0, #d4d4d4 63%, #dbdbdb 100%);
    background: linear-gradient(to bottom, #bebebe 0, #d4d4d4 63%, #dbdbdb 100%)
}

.recommendations-block .table-block table {
    border-collapse: collapse;
    padding: 0;
    border: 0;
    width: 183px;
    font-size: 14px;
    line-height: 16px;
    color: #252426;
    font-weight: bold;
    margin: 0 auto
}

.recommendations-block .table-block table td {
    background: #fff;
    border: 2px solid #dcdcdc;
    padding: 2px 9px 4px 0;
    width: 50%
}

.recommendations-block .table-block table td:first-child {
    font-weight: normal;
    padding: 0 13px 0 0;
    border-right: 0
}

.testimonials {
    margin: 0;
    padding: 24px 28px 21px;
    font-size: 14px;
    line-height: 16px;
    list-style: none
}

.testimonials li {
    padding: 0 0 15px
}

.testimonials li strong {
    font-weight: bold;
    margin: 0 0 0 4px
}

#main .testimonials p {
    margin: 0
}

.testimonials li a {
    color: #e02517;
    position: relative
}

.testimonials .tooltip {
    z-index: 1100;
    width: 259px;
    background: #fff;
    position: absolute;
    color: #252426;
    display: none;
    bottom: 40px;
    left: -8px;
    border: 1px solid #aaa;
    padding: 22px 28px 18px;
    -webkit-box-shadow: 0 5px 5px rgba(0, 0, 0, 0.65);
    box-shadow: 0 5px 5px rgba(0, 0, 0, 0.65)
}

.testimonials li a:hover .tooltip {
    display: block
}

.testimonials .tooltip:after {
    position: absolute;
    left: 42px;
    bottom: -38px;
    content: '';
    background: url(images/bullet7.png) no-repeat 0 100%;
    width: 28px;
    height: 38px
}

.testimonials .tooltip .title {
    display: block;
    background: 0;
    padding: 0;
    margin: 0 0 13px;
    font: 16px/18px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif
}

.testimonials .tooltip span {
    margin: 0 0 7px;
    display: block
}

.testimonials .tooltip .alert {
    display: block;
    color: #cc1601
}

.score-container {
    overflow: hidden;
    padding: 0 0 18px
}

.score-container .table-block {
    float: right;
    padding-left: 16px;
    width: 681px;
    margin: 0 0 0 16px
}

.score-container .title {
    margin: 0
}

.score-container .block {
    overflow: hidden;
    background: #414042;
    border-bottom: 1px solid #7d7d7f;
    padding: 8px 22px 5px;
    color: #fff;
    font: 14px/18px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif
}

.score-container .block strong {
    float: right;
    margin: 0 0 0 10px;
    font-weight: normal
}

.score-container .scrollable-area-wrapper .vscrollbar {
    background: #EBEBEB;
    width: 26px
}

.score-container table {
    background: #5f5f60;
    border-collapse: collapse;
    margin: 0;
    width: 100%;
    padding: 0
}

.score-container table td {
    color: #fff;
    font-size: 12px;
    line-height: 15px;
    height: 22px;
    width: 53px;
    padding: 8px 1px 5px;
    border: 1px solid #5f5f60;
    border-bottom: 1px solid #7d7d7f
}

.score-container table td img {
    vertical-align: top;
    display: inline-block;
    margin: 4px 0 0
}

.score-container table td:first-child {
    width: 33px;
    padding: 0 0 0 11px;
    text-align: center
}

.score-container table td.second {
    width: 118px
}

.score-container table td.third {
    width: 79px
}

.score-container table td.fourth {
    width: 138px
}

.score-container table td.fifth {
    width: 59px
}

.score-container table td.sixth {
    width: 138px;
    padding-left: 10px
}

.score-container table td div {
    border: 1px solid #30323a;
    text-align: left;
    border-width: 0 1px 1px 0;
    border-left: 1px solid #fff;
    color: #000;
    width: 100%;
    height: 100%;
    display: table;
    background: #fff;
    background: -moz-linear-gradient(top, #fff 0, #efeff0 36%, #dbdbdc 68%, #cccccd 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #fff), color-stop(36%, #efeff0), color-stop(68%, #dbdbdc), color-stop(100%, #cccccd));
    background: -webkit-linear-gradient(top, #fff 0, #efeff0 36%, #dbdbdc 68%, #cccccd 100%);
    background: -o-linear-gradient(top, #fff 0, #efeff0 36%, #dbdbdc 68%, #cccccd 100%);
    background: -ms-linear-gradient(top, #fff 0, #efeff0 36%, #dbdbdc 68%, #cccccd 100%);
    background: linear-gradient(to bottom, #fff 0, #efeff0 36%, #dbdbdc 68%, #cccccd 100%)
}

.score-container table td div span {
    display: table-cell;
    padding: 4px 8px 3px 0;
    text-align: right;
    vertical-align: middle
}

.score-container table td div strong {
    color: #e02517;
    display: table-cell;
    vertical-align: middle;
    padding: 4px 0 3px 12px
}

.score-container table td.fifth span {
    font-weight: bold
}

.score-container table td div .score-container .banner-list {
    margin: 0;
    overflow: hidden;
    padding: 1px 0 0;
    list-style: none;
    text-align: center
}

.score-container .banner-list li {
    padding: 0 0 9px
}

.score-container .banner-list li a {
    display: inline-block;
    vertical-align: top
}

.banner-list img {
    vertical-align: top
}

.score-container .scroll-block {
    height: 586px;
    background: #5f5f60
}

.score-container .scroll-block .scrollable-area {
    overflow: auto;
    height: 586px;
    width: 681px;
    float: left
}

.score-container .scroll-block .vscrollbar {
    width: 23px
}

.score-container .scrollable-area-wrapper .vscroll-up,
.score-container .scrollable-area-wrapper .vscroll-down {
    height: 37px;
    width: 26px;
    background: url(images/arrow.gif) no-repeat -3px 0
}

.score-container .scrollable-area-wrapper .vscroll-down {
    background-position: -1px 100%
}

.score-container .scrollable-area-wrapper .vscroll-line {
    background: 0;
    margin-left: 5px
}

.score-container .scrollable-area-wrapper .vscroll-slider {
    width: 13px;
    background: #dcdcdc
}

.popup {
    background: #7d7d7f;
    padding: 15px 12px
}

.text-form {
    overflow: hidden;
    color: #000;
    font-size: 14px;
    line-height: 18px;
    background: #fff200 url(images/bg-text-form.jpg) no-repeat 0 100%;
    margin: 0;
    width: 473px;
    direction: rtl;
    padding: 33px 40px 41px;
    height: 230px
}

.text-form .error {
    font-size: 12px;
    color: #e02517;
    margin-right: 5px;
    width: 73px;
    line-height: 23px;
    display: inline-block
}

.text-form h2 {
    font: 24px/28px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    margin: 0 0 17px
}

.toto-page #fancybox-close {
    position: absolute;
    right: 19px;
    left: auto;
    top: 20px;
    overflow: hidden;
    text-indent: -9999px;
    background: url(images/ico-close.gif) no-repeat;
    width: 18px;
    height: 18px
}

.toto-page .popup iframe {
    display: block
}

.text-form .row:after {
    display: block;
    clear: both;
    content: ''
}

.text-form .row {
    padding: 0 3px 16px
}

.text-form label {
    float: right;
    width: 45px;
    padding: 2px 0 0 11px
}

.text-form input[type="text"] {
    float: right;
    width: 216px;
    border: 1px solid #cfcfcf;
    border-width: 0 1px;
    border-top: 1px solid #707070;
    margin: 0;
    padding: 3px 5px 4px
}

.text-form textarea {
    float: right;
    width: 216px !important;
    height: 50px !important;
    border: 1px solid #cfcfcf;
    resize: none;
    border-width: 0 1px;
    border-top: 1px solid #707070;
    margin: 0;
    overflow: auto;
    padding: 3px 5px 4px
}

.text-form input[type="submit"] {
    float: right;
    background: url(images/bg-submit2.png) no-repeat;
    width: 108px;
    padding: 0;
    margin: 3px 62px 0 0;
    cursor: pointer;
    border: 0;
    height: 28px;
    font-size: 15px;
    line-height: 19px;
    color: #fff;
    font-weight: bold
}

.text-form input[type="submit"]:hover {
    cursor: pointer
}

#main .stat-tables>.title div.h1 {
    margin-top: 7px
}

.stat-tables>.title select {
    font-size: 13px;
    margin-right: 10px;
    vertical-align: baseline
}

.stat-tables>.title a.show {
    display: inline-block;
    height: 16px;
    border-radius: 4px;
    color: #e02517;
    margin-right: 10px;
    background: #fff;
    /* background: linear-gradient(bottom, #ebe8eb 4%, #fcfcfc 52%); */
    background: -o-linear-gradient(bottom, #ebe8eb 4%, #fcfcfc 52%);
    background: -moz-linear-gradient(bottom, #ebe8eb 4%, #fcfcfc 52%);
    background: -webkit-linear-gradient(bottom, #ebe8eb 4%, #fcfcfc 52%);
    background: -ms-linear-gradient(bottom, #ebe8eb 4%, #fcfcfc 52%);
    font-size: 13px;
    padding: 2px 15px;
    font-family: Arial;
    font-weight: bold;
    vertical-align: text-bottom;
    line-height: 15px
}

.stat-tables #toto-stats {
    padding: 0
}

.stat-tables #toto-stats>div.right-block {
    width: 465px;
    float: right;
    padding-left: 20px
}

.stat-tables #toto-stats>div.left-block {
    float: right;
    width: 459px
}

.stat-tables .goal-list li {
    margin-left: 13px
}

.stat-tables .tab-box .result-table td,
#main .stat-tables .score-list table td,
.stat-tables .goal-list table td {
    background: #FFF2F2
}

.racer-page #main .banner-holder .desc {
    padding-top: 25px;
    background-color: #41ad49
}

.racer-page #main .banner-holder .desc>span,
.racer-page #main .banner-holder .desc>em {
    display: none
}

.english-title {
    font-family: Verdana !important;
    font-weight: bold !important;
    font-size: 13px !important
}

#img-loader {
    text-align: center;
    margin: 20px auto;
    display: block
}

#img-loader img {
    width: 50px
}

#simplemodal-overlay {
    display: none !important
}

#simplemodal-container {
    position: absolute !important
}

.ranking-table-comments td {
    height: 20px !important;
    text-align: right !important;
    font-size: 12px;
    background-color: #edf3fb !important
}

.first-comment-row {
    border-top-style: double;
    border-top-width: 1px;
    border-color: black
}

.gallery2-flat .desc {
    bottom: 6px;
    padding: 2px 10px 4px;
    margin: 0;
    width: 600px;
    font-size: 14px;
    line-height: 16px;
    bottom: 0
}

#main .aside .facebook-widget .heading {
    border: 0;
    border-bottom: 3px solid #014bad
}

#main .aside .facebook-widget .heading strong {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    font-size: 14px
}

#main .bomba-article {
    padding: 1px 0 0 0;
    margin: 0 0 12px 0;
    width: 648px
}

.bomba-img-holder {
    position: relative
}

.bomba-article .bomba-image {
    width: 100%;
    height: auto
}

.super-bomba .bomba-article .bomba-image {
    width: 977px;
    height: 723px
}

.super-bomba .bomba-article .img-responsive {
    width: 970px;
    height: 723px;
    max-width: 970px
}

.bomba-article .bomba-titles-container {
    position: absolute;
    bottom: 24px;
    width: 100%
}

.super-bomba .bomba-article .bomba-titles-container {
    width: 970px
}

.super-bomba .bomba-article .bomba-title .middle {
    padding: 3px 0 0 10px;
    margin: 0 0 -6px 0;
    max-width: 100%;
    width: auto;
    height: 74px;
    font-size: 32px;
    line-height: 32px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    text-overflow: ellipsis;
    vertical-align: middle;
    display: table-cell
}

.super-bomba .bomba-article .bomba-subtitle span {
    width: 100%;
    font-size: 16px;
    font-family: 'blender-regular', 'Mukta-ExtraBold', 'south web light';
    line-height: 18px
}

.super-bomba .bomba-article .bomba-author {
    width: 970px !important;
    margin: 0 0 3px 0
}

.super-bomba .bomba-article .btn-play1 .bomba-title:after {
    content: '';
    position: absolute;
    width: 60px;
    height: 60px;
    top: 10px;
    right: 12px;
    background: url(images/play-main-article.png) no-repeat
}

.bomba-article .bomba-title {
    position: relative;
    overflow: hidden;
    right: 0;
    padding: 8px 10px 0 0;
    width: 100%;
    color: #fff;
    font-size: 33px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    line-height: 33px;
    background-color: rgba(34, 34, 34, 0.75);
    height: 74px
}

.bomba-article .bomba-title .middle {
    vertical-align: middle;
    display: table-cell;
    height: 74px;
    padding: 0 0 0 5px;
    width: 99%;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    text-overflow: ellipsis
}

.bomba-article .bomba-subtitle {
    position: relative;
    overflow: hidden;
    right: 0;
    padding: 6px 10px 2px 5px;
    color: #fff;
    font-size: 16px;
    font-family: 'blender-regular', 'Mukta-ExtraBold', 'south web bold';
    line-height: 18px;
    width: 100%;
    background-color: rgba(34, 34, 34, 0.75)
}

.bomba-article .bomba-author {
    position: absolute;
    overflow: hidden;
    right: 0;
    bottom: 0;
    padding: 3px 10px 2px 10px;
    height: 24px;
    color: #fff;
    font-size: 14px;
    line-height: 19px;
    background-color: rgba(34, 34, 34, 0.75);
    width: 100%
}

.bomba-article .bomba-author a {
    font-size: 14px;
    color: #fff;
    text-decoration: none;
    margin: 0 0 0 20px;
    display: inline-block
}

.bomba-article .bomba-author .date {
    display: inline-table;
    font-size: 14px;
    color: #fff
}

.bomba-article .bomba-exclusive {
    position: absolute;
    top: 30px;
    right: 0;
    background: #1732b4;
    color: #fff;
    font-size: 14px;
    line-height: 25px;
    height: 25px;
    padding: 1px 32px 0 1px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web';
    z-index: 10
}

.bomba-article.red .bomba-exclusive {
    background: #d10202
}

.bomba-article.orange .bomba-exclusive {
    background: #ff5a00
}

.bomba-article.bluetravel .bomba-exclusive {
    background: #043959
}

.bomba-article.lightblue .bomba-exclusive {
    background: #0c9bd9
}

.bomba-article.peach .bomba-exclusive {
    background: #B8411F
}

.bomba-article.green .bomba-exclusive {
    background: #0e9b00
}

.bomba-article.yellow .bomba-exclusive {
    background: #ffa200
}

.bomba-article .bomba-exclusive:before {
    position: absolute;
    left: -50px;
    top: 0;
    background: url(images/sprite2.png) no-repeat -38px -0px;
    width: 50px;
    height: 25px;
    content: ''
}

.bomba-article.red .bomba-exclusive:before {
    background-image: url(images/sprite2-red.png)
}

.bomba-article.orange .bomba-exclusive:before {
    background-image: url(images/sprite2-orange.png)
}

.bomba-article.bluetravel .bomba-exclusive:before {
    background-image: url(images/sprite2-orange.png)
}

.bomba-article.peach .bomba-exclusive:before {
    background-image: url(images/sprite2-orange.png)
}

.bomba-article.green .bomba-exclusive:before {
    background-image: url(images/sprite2-green.png)
}

.bomba-article.yellow .bomba-exclusive:before {
    background-image: url(images/sprite2-yellow.png)
}

.bomba-article .btn-play.live a::before {
    content: '';
    position: absolute;
    left: auto;
    right: 19px;
    top: 69px;
    width: 55px;
    height: 29px;
    z-index: 10;
    background: url(images/play-live.png) no-repeat
}

.league-page .bomba-article .btn-play1 a:after {
    content: '';
    position: absolute;
    left: auto;
    right: auto;
    top: 360px;
    width: 60px;
    height: 60px;
    margin: 0 5px 0 0;
    z-index: 10;
    background: url(images/play-main-article.png) no-repeat
}

.world-page .bomba-article .btn-play1 .bomba-title:after {
    content: '';
    position: absolute;
    width: 60px;
    height: 60px;
    top: 10px;
    right: 12px;
    background: url(images/play-main-article.png) no-repeat
}

.bomba-article .btn-play1.live1 a::before {
    content: '';
    position: absolute;
    left: auto;
    right: 19px;
    top: 60px;
    width: 55px;
    height: 29px;
    z-index: 10;
    background: url(images/play-live.png) no-repeat
}

.bomba-article .btn-play a:after {
    content: '';
    position: absolute;
    left: auto;
    right: 42px;
    top: 44px;
    width: 101px;
    height: 100px;
    margin: -50px -50px 0 0;
    z-index: 10;
    background: url(images/sprite.png) no-repeat -101px -117px
}

.bomba-article .btn-play1 a:hover:after {
    cursor: pointer
}

.bomba-article .btn-play a:hover:after {
    opacity: .6;
    cursor: pointer
}

.bomba-article-home .btn-play1 .bomba-title:after {
    content: '';
    position: absolute;
    width: 60px;
    height: 60px;
    top: 10px;
    right: 12px;
    background: url(images/play-main-article.png) no-repeat
}

.bomba-article-home .btn-play a:after {
    content: '';
    position: absolute;
    left: auto;
    right: auto;
    top: 349px;
    width: 39px;
    height: 38px;
    margin: 0 20px 0 0;
    z-index: 10;
    background: url(images/VideoMain.png) no-repeat
}

#sidebarHomePage .btn-play.small>a:after {
    width: 59px;
    height: 38px;
    margin: 0;
    background-position: 0 0;
    right: 69.5%;
    top: 66%
}

#sidebarHomePage .btn-play.small>a:hover:after {
    opacity: .6;
    background-position: 0 0;
    cursor: pointer
}

#sidebarHomePage .btn-play.small.video-box>a:after {
    right: 70%
}

#sidebarHomePage .btn-play>a:after {
    content: '';
    position: absolute;
    right: 93%;
    top: 91%;
    height: 100px;
    margin: -50px -50px 0 0;
    z-index: 1000;
    background: url(images/videosmallitem.png) no-repeat 0 0
}

#sidebarHomePage .btn-play>a:hover:after {
    cursor: pointer;
    background-position: 2px -117px
}

#sidebarHomePage .btn-play.smallLeftSide>a:hover:after {
    opacity: .6;
    background-position: 0 0
}

#sidebarHomePage .btn-play.bigLeftSide>a:hover:after {
    opacity: .6;
    background-position: 0 0
}

#sidebarHomePage .btn-play.bigLeftSide>a:after {
    width: 59px;
    height: 39px;
    margin: 14px 20px 0 0;
    right: 83.5%;
    z-index: 99999;
    margin: -28px -28px 0 0;
    z-index: 1000;
    background: url(images/VideoMain.png) no-repeat 0 0
}

#sidebarHomePage .btn-play.smallLeftSide>a:after {
    width: 59px;
    height: 39px;
    margin: 10px 20px 0 0;
    right: 48.5%;
    top: 45%;
    z-index: 99999
}

#content .btn-play.small>a:after {
    width: 59px;
    height: 38px;
    margin: 0;
    background-position: 0 0;
    right: 69.5%;
    top: 66%
}

#content .btn-play.small>a:hover:after {
    opacity: .6;
    background-position: 0 0;
    cursor: pointer
}

#content .btn-play.small.video-box>a:after {
    right: 66.5%
}

#content .btn-play>a:after {
    content: '';
    position: absolute;
    right: 93%;
    top: 91%;
    height: 100px;
    margin: -50px -50px 0 0;
    z-index: 1000;
    background: url(images/videosmallitem.png) no-repeat 0 0
}

#content .btn-play>a:hover:after {
    opacity: .6;
    background-position: 0 0
}

.instagram-page .heading_cont {
    background: url(images/right_bottom_shadow.png) right bottom no-repeat;
    margin: 0 -12px -18px 0;
    padding: 0 12px 18px 0
}

.instagram-page .heading {
    min-height: 88px;
    background: #141c27;
    color: #fff;
    border: 1px solid #ebebeb;
    padding: 13px 20px 15px 20px;
    direction: ltr
}

.instagram-page .heading img {
    float: right;
    width: 88px;
    height: 88px;
    margin: 0 0 0 30px
}

.instagram-page .heading a.banner {
    float: left;
    width: 88px;
    height: 88px;
    margin: 0 30px 0 0
}

.instagram-page .heading-titles {
    margin: 18px 0 14px;
    float: right;
    width: 660px;
    direction: rtl
}

.instagram-page .heading .title {
    display: block;
    font-size: 31px !important;
    font-weight: bold;
    margin: 0 0 10px 0 !important;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold' !important
}

.instagram-page .heading .subtitle {
    display: block;
    font-size: 24px !important;
    font-weight: bold;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold' !important;
    letter-spacing: normal !important;
    line-height: 23px !important;
    margin: 0 0 0 0 !important
}

.instagram-list {
    list-style: none;
    padding: 24px 0 0 0
}

.instagram-list li {
    width: 221px;
    height: 304px;
    margin: 0 -12px 18px 20px;
    float: right;
    background: url("images/right_bottom_shadow.png") right bottom no-repeat;
    padding: 0 12px 18px 0
}

.instagram-list li .insta_item {
    width: 219px;
    height: 304px;
    direction: ltr;
    color: #fff;
    outline: 0;
    background: #141c27;
    border: 1px solid #ebebeb
}

.instagram-list li.last {
    margin-left: 0
}

.instagram-list li .insta-user,
.instagram-list li .insta-desc {
    height: 50px
}

.instagram-list li .insta-desc .insta-likes {
    overflow: hidden;
    position: relative;
    text-align: center;
    font-weight: bold
}

.instagram-list li .insta-desc .insta-desc-txt-cont {
    height: 28px;
    display: block;
    padding: 0 10px;
    font: bold 12px arial;
    overflow: hidden;
    line-height: 28px;
    text-align: center;
    direction: rtl
}

.instagram-list li .insta-user .insta-user-img-cont {
    position: relative;
    top: -21px;
    width: 44px;
    height: 44px;
    margin: auto
}

.instagram-list li .insta-desc .insta-likes span {
    background: url(images/lev.png) left 2px no-repeat;
    padding: 0 0 0 24px;
    height: 19px;
    display: inline-block;
    font: bold 18px arial
}

.instagram-list li .insta-user .insta-user-img-cont img {
    width: 39px;
    height: 40px;
    border-radius: 21px;
    border: 2px solid #fff
}

.instagram-list li .insta-user .insta-user-txt-cont {
    position: relative;
    top: -20px;
    text-align: center;
    height: 20px;
    overflow: hidden;
    font: bold 14px arial;
    line-height: 20px
}

.instagram-list li .insta-picture {
    overflow: hidden
}

.instagram-list li .insta-picture {
    height: 193px;
    width: 193px;
    overflow: hidden;
    margin: 0 13px
}

.instagram-list li .insta-picture img {
    height: 193px;
    width: 193px
}

.instagram-list li .insta-picture .video-icon {
    position: absolute;
    bottom: 5px;
    right: 5px;
    height: 29px;
    width: 39px;
    background: url(images/video_i.png) left top
}

.instagram-list li .insta-picture.video {
    cursor: pointer;
    position: relative
}

.instagram-list li .insta-picture .video-loader {
    display: none;
    position: relative;
    bottom: 109px;
    width: 24px;
    height: 24px;
    background: url('images/instagram-loader.gif') no-repeat;
    margin: auto;
    z-index: 1
}

.instagram-list li .insta-picture #vidPlayer {
    position: relative;
    z-index: 0
}

.instagram_page {
    position: relative;
    height: 30px;
    clear: both;
    margin: 17px 0 70px
}

.instagram_page .paging .next,
.instagram_page .paging .prev {
    background: url("images/paging.png") left top no-repeat;
    display: block;
    height: 25px;
    overflow: hidden;
    text-indent: -9999px;
    width: 25px;
    top: -5px;
    position: absolute
}

.instagram_page .paging .next {
    right: 0
}

.instagram_page .paging .prev {
    background: url("images/paging.png") left bottom no-repeat;
    left: 0
}

.instagram_page .paging li {
    font: bold 14px arial;
    color: #ffa200;
    margin: 0 8px
}

.instagram_page .paging li a {
    font: bold 14px arial;
    color: #111
}

@media screen and (-webkit-min-device-pixel-ratio:0) {
    #tiptip_content {
        padding: 4px 8px 5px 8px;
        background-color: rgba(45, 45, 45, 0.88)
    }

    #tiptip_holder.tip_bottom #tiptip_arrow_inner {
        border-bottom-color: rgba(45, 45, 45, 0.88)
    }

    #tiptip_holder.tip_top #tiptip_arrow_inner {
        border-top-color: rgba(20, 20, 20, 0.92)
    }
}

.color-alt-red .logo {
    background-position: 0 -80px
}

.color-alt-red .add-nav .more:before {
    background-position: -434px -31px
}

.color-alt-red .info-block,
.color-alt-red #footer,
.color-alt-red .logo-block .frame,
.color-alt-red .tab-container,
.color-alt-red .goal-list .popup table th,
.color-alt-red .modal-cont .popup th,
.color-alt-red .cycle-games-popup .popup table th {
    border-top-color: #d10202
}

.color-alt-red .social-container,
.color-alt-red .mainarticle-league.banner-holder .desc {
    border-color: #d10202
}

.color-alt-red .heading {
    border-bottom-color: #d10202 !important
}

.color-alt-red #content .gmask .holder,
.color-alt-red .people-block,
.color-alt-red .banner-holder.mainarticle-world .desc,
.color-alt-red #content .gallery3 .pagination ul a:hover,
.color-alt-red #content .gallery3 .pagination ul li.active a,
.color-alt-red .goal-list .popup .title,
.color-alt-red .modal-cont .popup .title,
.color-alt-red .info-modal .title,
.color-alt-red .player-modal .title,
.color-alt-red .cycle-games-popup .popup .title,
.color-alt-red .people-block .figure span,
.color-alt-red .people-block .article .holder,
.color-alt-red .video-holder .title,
.color-alt-red #main .section-black .Background {
    background: #d10202
}

.color-alt-red .post-container .post .title a:hover,
.color-alt-red .logo-block .box a:hover,
.color-alt-red .show-all,
.color-alt-red #main .social-container p a:hover,
.color-alt-red #main .social-container a.alt,
.color-alt-red #content>a,
.color-alt-red #main .gallery .holder a:hover,
.color-alt-red #main .galleryWord .holder a:hover,
#content .section-red.color-alt-red a:hover,
.color-alt-red .article-content p a,
.color-alt-red .comment-box .reply,
.color-alt-red #main .info-block p a:hover,
.color-alt-red #main .info-block h3 a:hover,
.color-alt-red #main .social-container span.alt,
.color-alt-red #main .article-list h2 a:hover,
.color-alt-red #main .article-list a:hover,
.color-alt-red .article-list .league-title a:hover,
.color-alt-red #footer ul a:hover,
#main .section-red .article-holder h2 a:hover,
#main .section-red .article-holder-homepage h2 a:hover,
.color-alt-red .link-holder a,
.color-alt-red .players-block .gallery ul li:hover a,
.color-alt-red .results-holder .result-table .link,
.color-alt-red .player-info .heading a,
.color-alt-red #main .about-block .text h3,
.color-alt-red .tab-box .result-table .link,
.color-alt-red .score-list .link,
.color-alt-red .result-form input[type="button"],
.color-alt-red .player-modal .stat-data strong:hover,
.color-alt-red #content span>ul li,
.color-alt-red #content span>ul li a,
.color-alt-red .ranking-table-comment-title,
#main .section-red .headerTextLeftSide,
#main .section-red .article-holder .date .writer a:hover,
#content .section-red .article-list p a:hover,
#content .section-red .article-list .writer-date .writer a:hover,
#content .section-red .article-list .league-title a:hover,
#content .section-red .article-list .league-title h2 a:hover {
    color: #d10202 !important
}

#main .section-red .Background {
    background-color: #d10202
}

#main .section-red .rightRect {
    border-bottom: 24px solid #d10202
}

#main .section-red .leftRect {
    color: #d10202;
    border-top: 24px solid #d10202
}

#main .section-red .leftRectLeftSide {
    color: #d10202;
    border-top: 24px solid #d10202
}

.color-alt-red #content .galleryWord .thumbnails ul li a :hover,
#main .section-red .text-red {
    color: #d10202
}

.color-alt-red #content .bullet {
    background-color: #d10202 !important
}

.color-alt-red .news-block .news-list ul li:before,
.color-alt-red .news-list li:before,
.color-alt-red .show-all:before,
.color-alt-red #content>ul li:before,
.color-alt-red a.print,
.color-alt-red .add-comment:after,
.color-alt-red #content .comment-box .box:after,
.color-alt-red #content>ul li:before,
.color-alt-red #content span>ul li:before,
.color-alt-red .link-holder a span,
.color-alt-red .premiership-block a.btn-next,
.color-alt-red .premiership-block a.btn-prev,
.color-alt-red .comment-list .tlkb_load_more a span,
.color-alt-red #content .gallery .btn-next,
.color-alt-red #content .gallery .btn-prev,
.color-alt-red #content .galleryWord .btn-next,
.color-alt-red #content .galleryWord .btn-prev,
.color-alt-red .cycle-games-popup .popup .close,
.color-alt-red .modal-cont .popup .close,
.color-alt-red .goal-list .popup .close,
.color-alt-red .recomm-holder .btn-next,
.color-alt-red .recomm-holder .btn-prev,
.color-alt-red .scoreboard:before,
.color-alt-red .info-modal .close,
.color-alt-red .player-modal .close {
    background-image: url('/images/sprite-red.png')
}

.color-alt-red .add-nav>ul>li.active:after {
    background-position: 0 -6px
}

.color-alt-red .people-block .figure span:after {
    background: url(images/bg-title-red.gif) no-repeat
}

.color-alt-red .video-holder .title:before {
    background-image: url(images/sprite2-red.png)
}

.color-alt-red #main .hint .article-views {
    background: url(Images/redeye.png) no-repeat 100%
}

.color-alt-red #main .hint .article-talkbacks {
    background: url(Images/redcomm.png) no-repeat 100%
}

.color-alt-red #main .hint .article-talkbacksHomePage {
    background: url(Images/redcomm.png) no-repeat 100%
}

.color-alt-red #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #d10202
}

.color-alt-red .talkbacks-cont svg {
    fill: #d10202
}

.color-alt-gold .info-block,
.color-alt-gold #footer,
.color-alt-gold .logo-block .frame,
.color-alt-gold .tab-container,
.color-alt-gold .goal-list .popup table th,
.color-alt-gold .modal-cont .popup th,
.color-alt-gold .cycle-games-popup .popup table th {
    border-top-color: #691e32
}

.color-alt-gold .social-container,
.color-alt-gold .mainarticle-league.banner-holder .desc {
    border-color: #691e32
}

.color-alt-gold .heading {
    border-bottom-color: #691e32 !important
}

.color-alt-gold #content .gmask .holder,
.color-alt-gold .people-block,
.color-alt-gold .banner-holder.mainarticle-world .desc,
.color-alt-gold #content .gallery3 .pagination ul a:hover,
.color-alt-gold #content .gallery3 .pagination ul li.active a,
.color-alt-gold .goal-list .popup .title,
.color-alt-gold .modal-cont .popup .title,
.color-alt-gold .info-modal .title,
.color-alt-gold .player-modal .title,
.color-alt-gold .cycle-games-popup .popup .title,
.color-alt-gold .people-block .figure span,
.color-alt-gold .people-block .article .holder,
.color-alt-gold .video-holder .title,
.color-alt-gold #main .section-black .Background {
    background: #691e32
}

.color-alt-gold .post-container .post .title a:hover,
.color-alt-gold .logo-block .box a:hover,
.color-alt-gold .show-all,
.color-alt-gold #main .social-container p a:hover,
.color-alt-gold #main .social-container a.alt,
.color-alt-gold #content>a,
.color-alt-gold #main .gallery .holder a:hover,
.color-alt-gold #main .galleryWord .holder a:hover,
#content .section-gold.color-alt-gold a:hover,
.color-alt-gold .article-content p a,
.color-alt-gold .comment-box .reply,
.color-alt-gold #main .info-block p a:hover,
.color-alt-gold #main .info-block h3 a:hover,
.color-alt-gold #main .social-container span.alt,
.color-alt-gold #main .article-list h2 a:hover,
.color-alt-gold #main .article-list a:hover,
.color-alt-gold .article-list .league-title a:hover,
.color-alt-gold #footer ul a:hover,
#main .section-gold .article-holder h2 a:hover,
#main .section-gold .article-holder-homepage h2 a:hover,
.color-alt-gold .link-holder a,
.color-alt-gold .players-block .gallery ul li:hover a,
.color-alt-gold .results-holder .result-table .link,
.color-alt-gold .player-info .heading a,
.color-alt-gold #main .about-block .text h3,
.color-alt-gold .tab-box .result-table .link,
.color-alt-gold .score-list .link,
.color-alt-gold .result-form input[type="button"],
.color-alt-gold .player-modal .stat-data strong:hover,
.color-alt-gold #content span>ul li,
.color-alt-gold #content span>ul li a,
.color-alt-gold .ranking-table-comment-title,
#main .section-gold .headerTextLeftSide,
#main .section-gold .article-holder .date .writer a:hover,
#content .section-gold .article-list p a:hover,
#content .section-gold .article-list .writer-date .writer a:hover,
#content .section-gold .article-list .league-title a:hover,
#content .section-gold .article-list .league-title h2 a:hover {
    color: #691e32 !important
}

#main .section-gold .Background {
    background-color: #691e32
}

#main .section-gold .rightRect {
    border-bottom: 24px solid #691e32
}

#main .section-gold .leftRect {
    color: #691e32;
    border-top: 24px solid #691e32
}

#main .section-gold .leftRectLeftSide {
    color: #d10202;
    border-top: 24px solid #691e32
}

.color-alt-gold #content .galleryWord .thumbnails ul li a :hover,
#main .section-gold .text-gold {
    color: #691e32
}

.color-alt-gold #content .bullet {
    background-color: #691e32 !important
}

.color-alt-gold #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #691e32
}

.color-alt-gold .talkbacks-cont svg {
    fill: #691e32
}

.color-alt-black .logo {
    background-position: 0 -101px
}

.color-alt-black .add-nav .more:before {
    background-position: -434px -31px
}

.color-alt-black .info-block,
.color-alt-black #footer,
.color-alt-black .logo-block .frame,
.color-alt-black .tab-container,
.color-alt-black .goal-list .popup table th,
.color-alt-black .modal-cont .popup th,
.color-alt-black .cycle-games-popup .popup table th {
    border-top-color: #141c27
}

.color-alt-black .social-container,
.color-alt-black .mainarticle-league.banner-holder .desc {
    border-color: #141c27
}

.color-alt-black .heading {
    border-bottom-color: #141c27 !important
}

.color-alt-black #content .gmask .holder,
.color-alt-black .people-block,
.color-alt-black .banner-holder.mainarticle-world .desc,
.color-alt-black #content .gallery3 .pagination ul a:hover,
.color-alt-black #content .gallery3 .pagination ul li.active a,
.color-alt-black .goal-list .popup .title,
.color-alt-black .modal-cont .popup .title,
.color-alt-black .info-modal .title,
.color-alt-black .player-modal .title,
.color-alt-black .cycle-games-popup .popup .title,
.color-alt-black .people-block .figure span,
.color-alt-black .people-block .article .holder,
.color-alt-black .video-holder .title {
    background: #141c27
}

.color-alt-black .post-container .post .title a:hover,
.color-alt-black .logo-block .box a:hover,
.color-alt-black .show-all,
.color-alt-black #main .social-container p a:hover,
.color-alt-black #main .social-container a.alt,
.color-alt-black #content>a,
.color-alt-black #main .gallery .holder a:hover,
.color-alt-black #main .galleryWord .holder a:hover,
#content .section-black.color-alt-black a:hover,
.color-alt-black .article-content p a,
.color-alt-black .comment-box .reply,
.color-alt-black #main .info-block p a:hover,
.color-alt-black #main .info-block h3 a:hover,
.color-alt-black #main .social-container span.alt,
.color-alt-black #main .article-list h2 a:hover,
.color-alt-black #main .article-list a:hover,
.color-alt-black .article-list .league-title a:hover,
.color-alt-black #footer ul a:hover,
#main .sectblack-red .article-holder h2 a:hover,
#main .sectblack-red .article-holder-homepage h2 a:hover,
.color-alt-black .link-holder a,
.color-alt-black .players-block .gallery ul li:hover a,
.color-alt-black .results-holder .result-table .link,
.color-alt-black .player-info .heading a,
.color-alt-black #main .about-block .text h3,
.color-alt-black .tab-box .result-table .link,
.color-alt-black .score-list .link,
.color-alt-black .result-form input[type="button"],
.color-alt-black .player-modal .stat-data strong:hover,
.color-alt-black #content span>ul li,
.color-alt-black #content span>ul li a,
.color-alt-black .ranking-table-comment-title,
#main .section-black .headerTextLeftSide,
#main .section-black .article-holder .date .writer a:hover,
#content .section-black .article-list p a:hover,
#content .section-black .article-list .writer-date .writer a:hover,
#content .section-black .article-list .league-title a:hover,
#content .section-black .article-list .league-title h2 a:hover {
    color: #141c27 !important
}

#main .section-black .Background {
    background-color: #141c27
}

#main .section-black .rightRect {
    color: #141c27;
    height: 24px;
    border-bottom: 24px solid #141c27
}

#main .section-black .leftRect {
    color: #141c27;
    border-top: 24px solid #141c27 !important
}

#main .section-black .leftRectLeftSide {
    color: #141c27;
    border-top: 24px solid #141c27 !important
}

.color-alt-black #content .galleryWord .thumbnails ul li a :hover,
#main .section-black .text-black {
    color: #141c27;
    text-decoration: none
}

.color-alt-black #content .bullet {
    background-color: #141c27 !important
}

.color-alt-black .news-block .news-list ul li:before,
.color-alt-black .news-list li:before,
.color-alt-black .show-all:before,
.color-alt-black #content>ul li:before,
.color-alt-black a.print,
.color-alt-black .add-comment:after,
.color-alt-black #content .comment-box .box:after,
.color-alt-black #content>ul li:before,
.color-alt-black #content span>ul li:before,
.color-alt-black .link-holder a span,
.color-alt-black .premiership-block a.btn-next,
.color-alt-black .premiership-block a.btn-prev,
.color-alt-black .comment-list .tlkb_load_more a span,
.color-alt-black #content .gallery .btn-next,
.color-alt-black #content .gallery .btn-prev,
.color-alt-black .cycle-games-popup .popup .close,
.color-alt-black .modal-cont .popup .close,
.color-alt-black .goal-list .popup .close,
.color-alt-black .recomm-holder .btn-next,
.color-alt-black .recomm-holder .btn-prev,
.color-alt-black .scoreboard:before,
.color-alt-black .info-modal .close,
.color-alt-black .player-modal .close {
    background-image: url('images/sprite-black.png')
}

.color-alt-black .add-nav>ul>li.active:after {
    background-position: 0 -6px
}

.color-alt-black #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #141c27
}

.color-alt-black .talkbacks-cont svg {
    fill: #141c27
}

.color-alt-orange .logo {
    background-position: 0 -240px
}

.color-alt-orange .add-nav .more:before {
    background-position: -434px -36px
}

.color-alt-orange .info-block,
.color-alt-orange #footer,
.color-alt-orange .logo-block .frame,
.color-alt-orange .tab-container,
.color-alt-orange .goal-list .popup table th,
.color-alt-orange .modal-cont .popup th,
.color-alt-orange .cycle-games-popup .popup table th {
    border-top-color: #ff5a00
}

.color-alt-orange .social-container,
.color-alt-orange .mainarticle-league.banner-holder .desc {
    border-color: #ff5a00
}

.color-alt-orange .heading {
    border-bottom-color: #ff5a00 !important
}

.color-alt-orange #content .gmask .holder,
.color-alt-orange .people-block,
.color-alt-orange .banner-holder.mainarticle-world .desc,
.color-alt-orange #content .gallery3 .pagination ul a:hover,
.color-alt-orange #content .gallery3 .pagination ul li.active a,
.color-alt-orange .goal-list .popup .title,
.color-alt-orange .modal-cont .popup .title,
.color-alt-orange .info-modal .title,
.color-alt-orange .player-modal .title,
.color-alt-orange .cycle-games-popup .popup .title,
.color-alt-orange .people-block .figure span,
.color-alt-orange .people-block .article .holder,
.color-alt-orange .video-holder .title,
.color-alt-orange #main .section-black .Background {
    background: #ff5a00
}

.color-alt-orange .post-container .post .title a:hover,
.color-alt-orange .logo-block .box a:hover,
.color-alt-orange .show-all,
.color-alt-orange #main .social-container p a:hover,
.color-alt-orange #main .social-container a.alt,
.color-alt-orange #content>a,
#content .section-orange.color-alt-orange a:hover,
.color-alt-orange #main .gallery .holder a:hover,
.color-alt-orange #main .galleryWord .holder a:hover,
.color-alt-orange .article-content p a,
.color-alt-orange .comment-box .reply,
.color-alt-orange #main .info-block p a:hover,
.color-alt-orange #main .info-block h3 a:hover,
.color-alt-orange #main .social-container span.alt,
.color-alt-orange #main .article-list h2 a:hover,
.color-alt-orange #main .article-list a:hover,
.color-alt-orange .article-list .league-title a:hover,
.color-alt-orange #footer ul a:hover,
#main .section-orange .article-holder h2 a:hover,
#main .section-orange .article-holder-homepage h2 a:hover,
.color-alt-orange .link-holder a,
.color-alt-orange .players-block .gallery ul li:hover a,
.color-alt-orange .results-holder .result-table .link,
.color-alt-orange .player-info .heading a,
.color-alt-orange #main .about-block .text h3,
.color-alt-orange .tab-box .result-table .link,
.color-alt-orange .score-list .link,
.color-alt-orange .result-form input[type="button"],
.color-alt-orange .player-modal .stat-data strong:hover,
.color-alt-orange #content span>ul li,
.color-alt-orange #content span>ul li a,
.color-alt-orange .ranking-table-comment-title,
.color-alt-orange #content .galleryWord .thumbnails ul li a :hover,
#main .section-orange .headerTextLeftSide,
#main .section-orange .article-holder .date .writer a:hover,
#content .section-orange .article-list p a:hover,
#content .section-orange .article-list .writer-date .writer a:hover,
#content .section-orange .article-list .league-title a:hover,
#content .section-orange .article-list .league-title h2 a:hover {
    color: #ff5a00 !important
}

#main .section-orange .Background {
    background-color: #ff5a00
}

#main .section-orange .rightRect {
    border-bottom: 24px solid #ff5a00
}

#main .section-orange .leftRect {
    color: #ff5a00;
    border-top: 24px solid #ff5a00
}

#main .section-orange .leftRectLeftSide {
    color: #ff5a00;
    border-top: 24px solid #ff5a00
}

#main .section-orange .text-orange {
    color: #ff5a00
}

.color-alt-orange #content .bullet {
    background-color: #ff5a00 !important
}

.color-alt-orange .news-block .news-list ul li:before,
.color-alt-orange .news-list li:before,
.color-alt-orange .show-all:before,
.color-alt-orange #content>ul li:before,
.color-alt-orange a.print,
.color-alt-orange .add-comment:after,
.color-alt-orange #content .comment-box .box:after,
.color-alt-orange #content>ul li:before,
.color-alt-orange #content span>ul li:before,
.color-alt-orange .link-holder a span,
.color-alt-orange .premiership-block a.btn-next,
.color-alt-orange .premiership-block a.btn-prev,
.color-alt-orange .comment-list .tlkb_load_more a span,
.color-alt-orange #content .gallery .btn-next,
.color-alt-orange #content .gallery .btn-prev,
.color-alt-orange .cycle-games-popup .popup .close,
.color-alt-orange .modal-cont .popup .close,
.color-alt-orange .goal-list .popup .close,
.color-alt-orange .recomm-holder .btn-next,
.color-alt-orange .recomm-holder .btn-prev,
.color-alt-orange .scoreboard:before,
.color-alt-orange .info-modal .close,
.color-alt-orange .player-modal .close {
    background-image: url('images/sprite-orange.png')
}

.color-alt-orange .add-nav>ul>li.active:after {
    background-position: 0 -12px
}

.color-alt-orange #content .galleryWord,
.color-alt-orange .people-block .box-holder,
.color-alt-orange .people-block .article,
.color-alt-orange .items-container,
.color-alt-orange #main .gallery2,
.color-alt-orange #main .galleryWord2,
.color-alt-orange .video-container,
.color-alt-orange .schedule-block,
.color-alt-orange .play-block,
.color-alt-orange #sidebar .post-list .heading2,
.color-alt-orange .article-content div.video-div>a.video-anc {
    background-image: url('images/bg-video-orange.jpg')
}

.color-alt-orange .people-block .figure span:after {
    background: url(images/bg-title-orange.gif) no-repeat
}

.color-alt-orange .video-holder .title:before {
    background-image: url(images/sprite2-orange.png)
}

.color-alt-orange #main .hint .article-views {
    background: url(Images/orangeeye.png) no-repeat 100%
}

.color-alt-orange #main .hint .article-talkbacks {
    background: url(Images/orangecomm.png) no-repeat 100%
}

.color-alt-orange #main .hint .article-talkbacksHomePage {
    background: url(Images/orangecomm.png) no-repeat 100%
}

.color-alt-orange #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #ff5a00
}

.color-alt-orange .talkbacks-cont svg {
    fill: #ff5a00
}

.color-alt-bluetravel .logo {
    background-position: 0 -560px
}

.color-alt-bluetravel .add-nav .more:before {
    background-position: -434px -36px
}

.color-alt-bluetravel .info-block,
.color-alt-bluetravel #footer,
.color-alt-bluetravel .logo-block .frame,
.color-alt-bluetravel .tab-container,
.color-alt-bluetravel .goal-list .popup table th,
.color-alt-bluetravel .modal-cont .popup th,
.color-alt-bluetravel .cycle-games-popup .popup table th {
    border-top-color: #043959
}

.color-alt-bluetravel .social-container,
.color-alt-bluetravel .mainarticle-league.banner-holder .desc {
    border-color: #043959
}

.color-alt-bluetravel .heading {
    border-bottom-color: #043959 !important
}

.color-alt-bluetravel #content .gmask .holder,
.color-alt-bluetravel .people-block,
.color-alt-bluetravel .banner-holder.mainarticle-world .desc,
.color-alt-bluetravel #content .gallery3 .pagination ul a:hover,
.color-alt-bluetravel #content .gallery3 .pagination ul li.active a,
.color-alt-bluetravel .goal-list .popup .title,
.color-alt-bluetravel .modal-cont .popup .title,
.color-alt-bluetravel .info-modal .title,
.color-alt-bluetravel .player-modal .title,
.color-alt-bluetravel .cycle-games-popup .popup .title,
.color-alt-bluetravel .people-block .figure span,
.color-alt-bluetravel .people-block .article .holder,
.color-alt-bluetravel .video-holder .title {
    background: #043959
}

.color-alt-bluetravel .post-container .post .title a:hover,
.color-alt-bluetravel .logo-block .box a:hover,
.color-alt-bluetravel .show-all,
.color-alt-bluetravel #main .social-container p a:hover,
.color-alt-bluetravel #main .social-container a.alt,
.color-alt-bluetravel #content>a,
#content .section-bluetravel.color-alt-orange a:hover,
.color-alt-bluetravel #main .gallery .holder a:hover,
.color-alt-bluetravel #main .galleryWord .holder a:hover,
.color-alt-bluetravel .article-content p a,
.color-alt-bluetravel .comment-box .reply,
.color-alt-bluetravel #main .info-block p a:hover,
.color-alt-bluetravel #main .info-block h3 a:hover,
.color-alt-bluetravel #main .social-container span.alt,
.color-alt-bluetravel #main .article-list h2 a:hover,
.color-alt-bluetravel #main .article-list a:hover,
.color-alt-bluetravel .article-list .league-title a:hover,
.color-alt-bluetravel #footer ul a:hover,
#main .section-bluetravel .article-holder h2 a:hover,
#main .section-bluetravel .article-holder-homepage h2 a:hover,
.color-alt-bluetravel .link-holder a,
.color-alt-bluetravel .players-block .gallery ul li:hover a,
.color-alt-bluetravel .results-holder .result-table .link,
.color-alt-bluetravel .player-info .heading a,
.color-alt-bluetravel #main .about-block .text h3,
.color-alt-bluetravel .tab-box .result-table .link,
.color-alt-bluetravel .score-list .link,
.color-alt-bluetravel .result-form input[type="button"],
.color-alt-bluetravel .player-modal .stat-data strong:hover,
.color-alt-bluetravel #content span>ul li,
.color-alt-bluetravel #content span>ul li a,
.color-alt-bluetravel .ranking-table-comment-title,
.color-alt-bluetravel #content .galleryWord .thumbnails ul li a :hover,
#main .section-bluetravel .headerTextLeftSide,
#main .section-bluetravel .article-holder .date .writer a:hover,
#content .section-bluetravel .article-list p a:hover,
#content .section-bluetravel .article-list .writer-date .writer a:hover,
#content .section-bluetravel .article-list .league-title a:hover,
#content .section-bluetravel .article-list .league-title h2 a:hover {
    color: #043959 !important
}

#main .section-bluetravel .Background {
    background-color: #043959
}

#main .section-bluetravel .rightRect {
    border-bottom: 24px solid #043959
}

#main .section-bluetravel .leftRect {
    color: #043959;
    border-top: 24px solid #043959
}

#main .section-bluetravel .leftRectLeftSide {
    color: #043959;
    border-top: 24px solid #043959
}

#main .section-bluetravel .text-orange {
    color: #043959
}

.color-alt-bluetravel #content .bullet {
    background-color: #043959 !important
}

.color-alt-bluetravel .show-all:before,
.color-alt-bluetravel #content>ul li:before,
.color-alt-bluetravel a.print,
.color-alt-bluetravel .add-comment:after,
.color-alt-bluetravel #content .comment-box .box:after,
.color-alt-bluetravel #content>ul li:before,
.color-alt-bluetravel #content span>ul li:before,
.color-alt-bluetravel .link-holder a span,
.color-alt-bluetravel .premiership-block a.btn-next,
.color-alt-bluetravel .premiership-block a.btn-prev,
.color-alt-bluetravel .comment-list .tlkb_load_more a span,
.color-alt-bluetravel #content .gallery .btn-next,
.color-alt-bluetravel #content .gallery .btn-prev,
.color-alt-bluetravel .cycle-games-popup .popup .close,
.color-alt-bluetravel .modal-cont .popup .close,
.color-alt-bluetravel .goal-list .popup .close,
.color-alt-bluetravel .recomm-holder .btn-next,
.color-alt-bluetravel .recomm-holder .btn-prev,
.color-alt-bluetravel .scoreboard:before,
.color-alt-bluetravel .info-modal .close,
.color-alt-bluetravel .player-modal .close {
    background-image: url('images/sprite-blue.png')
}

.color-alt-bluetravel .add-nav>ul>li.active:after {
    background-position: 0 -15px
}

.color-alt-bluetravel #content .galleryWord,
.color-alt-bluetravel .people-block .box-holder,
.color-alt-bluetravel .people-block .article,
.color-alt-bluetravel .items-container,
.color-alt-bluetravel #main .gallery2,
.color-alt-bluetravel #main .galleryWord2,
.color-alt-bluetravel .video-container,
.color-alt-bluetravel .schedule-block,
.color-alt-bluetravel .play-block,
.color-alt-bluetravel #sidebar .post-list .heading2,
.color-alt-bluetravel .article-content div.video-div>a.video-anc {
    background-image: url('images/bg-video-blue.jpg')
}

.color-alt-bluetravel .people-block .figure span:after {
    background: url(images/bg-title-blueTravel.gif) no-repeat
}

.color-alt-bluetravel .video-holder .title:before {
    background-image: url(images/sprite2-blue.png)
}

.color-alt-bluetravel #main .hint .article-views {
    background: url(Images/orangeeye.png) no-repeat 100%
}

.color-alt-orange #main .hint .article-talkbacks {
    background: url(Images/orangecomm.png) no-repeat 100%
}

.color-alt-bluetravel #main .hint .article-talkbacksHomePage {
    background: url(Images/orangecomm.png) no-repeat 100%
}

.color-alt-bluetravel #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #043959
}

.color-alt-bluetravel .talkbacks-cont svg {
    fill: #043959
}

.color-alt-peach .logo {
    background-position: 0 -801px
}

.color-alt-peach .add-nav .more:before {
    background-position: -434px -36px
}

.color-alt-peach .info-block,
.color-alt-peach #footer,
.color-alt-peach .logo-block .frame,
.color-alt-peach .tab-container,
.color-alt-peach .goal-list .popup table th,
.color-alt-peach .modal-cont .popup th,
.color-alt-peach .cycle-games-popup .popup table th {
    border-top-color: #B8411F
}

.color-alt-peach .social-container,
.color-alt-peach .mainarticle-league.banner-holder .desc {
    border-color: #B8411F
}

.color-alt-peach .heading {
    border-bottom-color: #B8411F !important
}

.color-alt-peach #content .gmask .holder,
.color-alt-peach .people-block,
.color-alt-peach .banner-holder.mainarticle-world .desc,
.color-alt-peach #content .gallery3 .pagination ul a:hover,
.color-alt-peach #content .gallery3 .pagination ul li.active a,
.color-alt-peach .goal-list .popup .title,
.color-alt-peach .modal-cont .popup .title,
.color-alt-peach .info-modal .title,
.color-alt-peach .player-modal .title,
.color-alt-peach .cycle-games-popup .popup .title,
.color-alt-peach .people-block .figure span,
.color-alt-peach .people-block .article .holder,
.color-alt-peach .video-holder .title {
    background: #B8411F
}

.color-alt-peach .post-container .post .title a:hover,
.color-alt-peach .logo-block .box a:hover,
.color-alt-peach .show-all,
.color-alt-peach #main .social-container p a:hover,
.color-alt-peach #main .social-container a.alt,
.color-alt-peach #content>a,
#content .section-peach.color-alt-orange a:hover,
.color-alt-peach #main .gallery .holder a:hover,
.color-alt-peach #main .galleryWord .holder a:hover,
.color-alt-peach .article-content p a,
.color-alt-peach .comment-box .reply,
.color-alt-peach #main .info-block p a:hover,
.color-alt-peach #main .info-block h3 a:hover,
.color-alt-peach #main .social-container span.alt,
.color-alt-peach #main .article-list h2 a:hover,
.color-alt-peach #main .article-list a:hover,
.color-alt-peach .article-list .league-title a:hover,
.color-alt-peach #footer ul a:hover,
#main .section-peach .article-holder h2 a:hover,
#main .section-peach .article-holder-homepage h2 a:hover,
.color-alt-peach .link-holder a,
.color-alt-peach .players-block .gallery ul li:hover a,
.color-alt-peach .results-holder .result-table .link,
.color-alt-peach .player-info .heading a,
.color-alt-peach #main .about-block .text h3,
.color-alt-peach .tab-box .result-table .link,
.color-alt-peach .score-list .link,
.color-alt-peach .result-form input[type="button"],
.color-alt-peach .player-modal .stat-data strong:hover,
.color-alt-peach #content span>ul li,
.color-alt-peach #content span>ul li a,
.color-alt-peach .ranking-table-comment-title,
.color-alt-peach #content .galleryWord .thumbnails ul li a :hover,
#main .section-peach .headerTextLeftSide,
#main .section-peach .article-holder .date .writer a:hover,
#content .section-peach .article-list p a:hover,
#content .section-peach .article-list .writer-date .writer a:hover,
#content .section-peach .article-list .league-title a:hover,
#content .section-peach .article-list .league-title h2 a:hover {
    color: #B8411F !important
}

#main .section-peach .Background {
    background-color: #B8411F
}

#main .section-peach .rightRect {
    border-bottom: 24px solid #B8411F
}

#main .section-peach .leftRect {
    color: #B8411F;
    border-top: 24px solid #B8411F
}

#main .section-peach .leftRectLeftSide {
    color: #B8411F;
    border-top: 24px solid #B8411F
}

#main .section-peach .text-orange {
    color: #B8411F
}

.color-alt-peach #content .bullet {
    background-color: #B8411F !important
}

.color-alt-peach .show-all:before,
.color-alt-peach #content>ul li:before,
.color-alt-peach a.print,
.color-alt-peach .add-comment:after,
.color-alt-peach #content .comment-box .box:after,
.color-alt-peach #content>ul li:before,
.color-alt-peach #content span>ul li:before,
.color-alt-peach .link-holder a span,
.color-alt-peach .premiership-block a.btn-next,
.color-alt-peach .premiership-block a.btn-prev,
.color-alt-peach .comment-list .tlkb_load_more a span,
.color-alt-peach #content .gallery .btn-next,
.color-alt-peach #content .gallery .btn-prev,
.color-alt-peach .cycle-games-popup .popup .close,
.color-alt-peach .modal-cont .popup .close,
.color-alt-peach .goal-list .popup .close,
.color-alt-peach .recomm-holder .btn-next,
.color-alt-peach .recomm-holder .btn-prev,
.color-alt-peach .scoreboard:before,
.color-alt-peach .info-modal .close,
.color-alt-peach .player-modal .close {
    background-image: url('images/sprite-blue.png')
}

.color-alt-peach .add-nav>ul>li.active:after {
    background-position: 0 -19px
}

.color-alt-peach #content .galleryWord,
.color-alt-peach .people-block .box-holder,
.color-alt-peach .people-block .article,
.color-alt-peach .items-container,
.color-alt-peach #main .gallery2,
.color-alt-peach #main .galleryWord2,
.color-alt-peach .video-container,
.color-alt-peach .schedule-block,
.color-alt-peach .play-block,
.color-alt-peach #sidebar .post-list .heading2,
.color-alt-peach .article-content div.video-div>a.video-anc {
    background-image: url('images/bg-video-blue.jpg')
}

.color-alt-peach .people-block .figure span:after {
    background: url(images/bg-title-blueTravel.gif) no-repeat
}

.color-alt-peach .video-holder .title:before {
    background-image: url(images/sprite2-blue.png)
}

.color-alt-peach #main .hint .article-views {
    background: url(Images/orangeeye.png) no-repeat 100%
}

.color-alt-orange #main .hint .article-talkbacks {
    background: url(Images/orangecomm.png) no-repeat 100%
}

.color-alt-peach #main .hint .article-talkbacksHomePage {
    background: url(Images/orangecomm.png) no-repeat 100%
}

.color-alt-peach #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #B8411F
}

.color-alt-peach .talkbacks-cont svg {
    fill: #B8411F
}

.color-alt-lightblue .logo {
    background-position: 0 -909px
}

.color-alt-lightblue .add-nav .more:before {
    background-position: -434px -61px
}

.color-alt-lightblue .info-block,
.color-alt-lightblue #footer,
.color-alt-lightblue .logo-block .frame,
.color-alt-lightblue .tab-container,
.color-alt-lightblue .goal-list .popup table th,
.color-alt-lightblue .modal-cont .popup th,
.color-alt-lightblue .cycle-games-popup .popup table th {
    border-top-color: #0c9bd9
}

.color-alt-lightblue .social-container,
.color-alt-lightblue .mainarticle-league.banner-holder .desc {
    border-color: #0c9bd9
}

.color-alt-lightblue .heading {
    border-bottom-color: #0c9bd9 !important
}

.color-alt-lightblue #content .gmask .holder,
.color-alt-lightblue .people-block,
.color-alt-lightblue .banner-holder.mainarticle-world .desc,
.color-alt-lightblue #content .gallery3 .pagination ul a:hover,
.color-alt-lightblue #content .gallery3 .pagination ul li.active a,
.color-alt-lightblue .goal-list .popup .title,
.color-alt-lightblue .modal-cont .popup .title,
.color-alt-lightblue .info-modal .title,
.color-alt-lightblue .player-modal .title,
.color-alt-lightblue .cycle-games-popup .popup .title,
.color-alt-lightblue .people-block .figure span,
.color-alt-lightblue .people-block .article .holder,
.color-alt-lightblue .video-holder .title {
    background: #0c9bd9
}

.color-alt-lightblue .post-container .post .title a:hover,
.color-alt-lightblue .logo-block .box a:hover,
.color-alt-lightblue .show-all,
.color-alt-lightblue #main .social-container p a:hover,
.color-alt-lightblue #main .social-container a.alt,
.color-alt-lightblue #content>a,
#content .section-lightblue.color-alt-orange a:hover,
.color-alt-lightblue #main .gallery .holder a:hover,
.color-alt-lightblue #main .galleryWord .holder a:hover,
.color-alt-lightblue .article-content p a,
.color-alt-lightblue .comment-box .reply,
.color-alt-lightblue #main .info-block p a:hover,
.color-alt-lightblue #main .info-block h3 a:hover,
.color-alt-lightblue #main .social-container span.alt,
.color-alt-lightblue #main .article-list h2 a:hover,
.color-alt-lightblue #main .article-list a:hover,
.color-alt-lightblue .article-list .league-title a:hover,
.color-alt-lightblue #footer ul a:hover,
#main .section-lightblue .article-holder h2 a:hover,
#main .section-lightblue .article-holder-homepage h2 a:hover,
.color-alt-lightblue .link-holder a,
.color-alt-lightblue .players-block .gallery ul li:hover a,
.color-alt-lightblue .results-holder .result-table .link,
.color-alt-lightblue .player-info .heading a,
.color-alt-lightblue #main .about-block .text h3,
.color-alt-lightblue .tab-box .result-table .link,
.color-alt-lightblue .score-list .link,
.color-alt-lightblue .result-form input[type="button"],
.color-alt-lightblue .player-modal .stat-data strong:hover,
.color-alt-lightblue #content span>ul li,
.color-alt-lightblue #content span>ul li a,
.color-alt-lightblue .ranking-table-comment-title,
.color-alt-lightblue #content .galleryWord .thumbnails ul li a :hover,
#main .section-lightblue .headerTextLeftSide,
#main .section-lightblue .article-holder .date .writer a:hover,
#content .section-lightblue .article-list p a:hover,
#content .section-lightblue .article-list .writer-date .writer a:hover,
#content .section-lightblue .article-list .league-title a:hover,
#content .section-lightblue .article-list .league-title h2 a:hover {
    color: #0c9bd9 !important
}

#main .section-lightblue .Background {
    background-color: #0c9bd9
}

#main .section-lightblue .rightRect {
    border-bottom: 24px solid #0c9bd9
}

#main .section-lightblue .leftRect {
    color: #0c9bd9;
    border-top: 24px solid #0c9bd9
}

#main .section-lightblue .leftRectLeftSide {
    color: #0c9bd9;
    border-top: 24px solid #0c9bd9
}

#main .section-lightblue .text-orange {
    color: #0c9bd9
}

.color-alt-lightblue #content .bullet {
    background-color: #0c9bd9 !important
}

.color-alt-lightblue .show-all:before,
.color-alt-lightblue #content>ul li:before,
.color-alt-lightblue a.print,
.color-alt-lightblue .add-comment:after,
.color-alt-lightblue #content .comment-box .box:after,
.color-alt-lightblue #content>ul li:before,
.color-alt-lightblue #content span>ul li:before,
.color-alt-lightblue .link-holder a span,
.color-alt-lightblue .premiership-block a.btn-next,
.color-alt-lightblue .premiership-block a.btn-prev,
.color-alt-lightblue .comment-list .tlkb_load_more a span,
.color-alt-lightblue #content .gallery .btn-next,
.color-alt-lightblue #content .gallery .btn-prev,
.color-alt-lightblue .cycle-games-popup .popup .close,
.color-alt-lightblue .modal-cont .popup .close,
.color-alt-lightblue .goal-list .popup .close,
.color-alt-lightblue .recomm-holder .btn-next,
.color-alt-lightblue .recomm-holder .btn-prev,
.color-alt-lightblue .scoreboard:before,
.color-alt-lightblue .info-modal .close,
.color-alt-lightblue .player-modal .close {
    background-image: url('images/sprite-lightblue.png')
}

.color-alt-lightblue .add-nav>ul>li.active:after {
    background-position: 0 -19px;
    background-image: url(images/bg-add-nav-lightblue.png)
}

.color-alt-lightblue #content .galleryWord,
.color-alt-lightblue .people-block .box-holder,
.color-alt-lightblue .people-block .article,
.color-alt-lightblue .items-container,
.color-alt-lightblue #main .gallery2,
.color-alt-lightblue #main .galleryWord2,
.color-alt-lightblue .video-container,
.color-alt-lightblue .schedule-block,
.color-alt-lightblue .play-block,
.color-alt-lightblue #sidebar .post-list .heading2,
.color-alt-lightblue .article-content div.video-div>a.video-anc {
    background-image: url('images/bg-video-blue.jpg')
}

.color-alt-lightblue .people-block .figure span:after {
    background: url(images/bg-title-blue.gif) no-repeat
}

.color-alt-lightblue .video-holder .title:before {
    background-image: url(images/sprite-lightblue.png.png)
}

.color-alt-lightblue #main .hint .article-views {
    background: url(Images/blueeye.png) no-repeat 100%
}

.color-alt-orange #main .hint .article-talkbacks {
    background: url(Images/bluecomm.png) no-repeat 100%
}

.color-alt-lightblue #main .hint .article-talkbacksHomePage {
    background: url(Images/bluecomm.png) no-repeat 100%
}

#main .section-greenLight .leftRectLeftSide {
    color: #0c9bd9;
    border-top: 24px solid #0c9bd9
}

.color-alt-lightblue #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #0c9bd9
}

.color-alt-lightblue .talkbacks-cont svg {
    fill: #0c9bd9
}

.color-alt-green .logo {
    background-position: 0 -800px
}

.color-alt-green .add-nav .more:before {
    background-position: -434px -41px
}

.color-alt-green .info-block,
.color-alt-green #footer,
.color-alt-green .logo-block .frame,
.color-alt-green .tab-container,
.color-alt-green .goal-list .popup table th,
.color-alt-green .modal-cont .popup th,
.color-alt-green .cycle-games-popup .popup table th {
    border-top-color: #0e9b00
}

.color-alt-green .social-container,
.color-alt-green .mainarticle-league.banner-holder .desc {
    border-color: #0e9b00
}

.color-alt-green .heading {
    border-bottom-color: #0e9b00 !important
}

.color-alt-green #content .gmask .holder,
.color-alt-green .people-block,
.color-alt-green .banner-holder.mainarticle-world .desc,
.color-alt-green #content .gallery3 .pagination ul a:hover,
.color-alt-green #content .gallery3 .pagination ul li.active a,
.color-alt-green .goal-list .popup .title,
.color-alt-green .modal-cont .popup .title,
.color-alt-green .info-modal .title,
.color-alt-green .player-modal .title,
.color-alt-green .cycle-games-popup .popup .title,
.color-alt-green .people-block .figure span,
.color-alt-green .people-block .article .holder,
.color-alt-green .video-holder .title,
.color-alt-green #main .section-black .Background {
    background: #0e9b00
}

.color-alt-green .post-container .post .title a:hover,
.color-alt-green .logo-block .box a:hover,
.color-alt-green .show-all,
.color-alt-green #main .social-container p a:hover,
.color-alt-green #main .social-container a.alt,
.color-alt-green #content>a,
#content .section-green.color-alt-green a:hover,
.color-alt-green #main .gallery .holder a:hover,
.color-alt-green #main .galleryWord .holder a:hover,
.color-alt-green .article-content p a,
.color-alt-green .comment-box .reply,
.color-alt-green #main .info-block p a:hover,
.color-alt-green #main .info-block h3 a:hover,
.color-alt-green #main .social-container span.alt,
.color-alt-green #main .article-list h2 a:hover,
.color-alt-green #main .article-list a:hover,
.color-alt-green .article-list .league-title a:hover,
.color-alt-green #footer ul a:hover,
#main .section-green .article-holder h2 a:hover,
#main .section-green .article-holder-homepage h2 a:hover,
.color-alt-green .link-holder a,
.color-alt-green .players-block .gallery ul li:hover a,
.color-alt-green .results-holder .result-table .link,
.color-alt-green .player-info .heading a,
.color-alt-green #main .about-block .text h3,
.color-alt-green .tab-box .result-table .link,
.color-alt-green .score-list .link,
.color-alt-green .result-form input[type="button"],
.color-alt-green .player-modal .stat-data strong:hover,
.color-alt-green #content span>ul li,
.color-alt-green #content span>ul li a,
.color-alt-green .ranking-table-comment-title,
#main .section-green .headerTextLeftSide,
#main .section-green .article-holder .date .writer a:hover,
#content .section-green .article-list p a:hover,
#content .section-green .article-list .writer-date .writer a:hover,
#content .section-green .article-list .league-title a:hover,
#content .section-green .article-list .league-title h2 a:hover {
    color: #0e9b00 !important
}

#main .section-green .Background {
    background-color: #0e9b00
}

#main .section-green .rightRect {
    border-bottom: 24px solid #0e9b00;
    height: 24px
}

#main .section-green .rightRectLeftSide {
    border-bottom: 24px solid #0e9b00
}

#main .section-green .leftRect {
    color: #0e9b00;
    border-top: 24px solid #0e9b00
}

#main .section-green .leftRectLeftSide {
    color: #0e9b00;
    border-top: 24px solid #0e9b00
}

#main .section-green .text-green {
    color: #0e9b00
}

.color-alt-green #content .bullet {
    background-color: #0e9b00 !important
}

.color-alt-green .news-block .news-list ul li:before,
.color-alt-green .news-list li:before,
.color-alt-green .show-all:before,
.color-alt-green #content>ul li:before,
.color-alt-green a.print,
.color-alt-green .add-comment:after,
.color-alt-green #content .comment-box .box:after,
.color-alt-green #content>ul li:before,
.color-alt-green #content span>ul li:before,
.color-alt-green .link-holder a span,
.color-alt-green .premiership-block a.btn-next,
.color-alt-green .premiership-block a.btn-prev,
.color-alt-green .comment-list .tlkb_load_more a span,
.color-alt-green #content .gallery .btn-next,
.color-alt-green #content .gallery .btn-prev,
.color-alt-green .cycle-games-popup .popup .close,
.color-alt-green .modal-cont .popup .close,
.color-alt-green .goal-list .popup .close,
.color-alt-green .recomm-holder .btn-next,
.color-alt-green .recomm-holder .btn-prev,
.color-alt-green .scoreboard:before,
.color-alt-green .info-modal .close,
.color-alt-green .player-modal .close {
    background-image: url('images/sprite-green.png')
}

.color-alt-green .add-nav>ul>li.active:after {
    background-position: 0 -18px
}

.color-alt-green #content .galleryWord,
.color-alt-green .people-block .box-holder,
.color-alt-green .people-block .article,
.color-alt-green .items-container,
.color-alt-green #main .gallery2,
.color-alt-green #main .galleryWord2,
.color-alt-green .video-container,
.color-alt-green .schedule-block,
.color-alt-green .play-block,
.color-alt-green #sidebar .post-list .heading2,
.color-alt-green .article-content div.video-div>a.video-anc {
    background-image: url('images/bg-video-green.jpg')
}

.color-alt-green .people-block .figure span:after {
    background: url(images/bg-title-green.gif) no-repeat
}

.color-alt-green .video-holder .title:before {
    background-image: url(images/sprite2-green.png)
}

.color-alt-green #main .hint .article-views {
    background: url(Images/greeneye.png) no-repeat 100%
}

.color-alt-green #main .hint .article-talkbacks {
    background: url(Images/greencomm.png) no-repeat 100%
}

.color-alt-green #main .hint .article-talkbacksHomePage {
    background: url(Images/greencomm.png) no-repeat 100%
}

.color-alt-green #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #0e9b00
}

.color-alt-green .talkbacks-cont svg {
    fill: #0e9b00
}

.color-alt-greenLight .logo {
    background-position: 0 -303px
}

.color-alt-greenLight .add-nav .more:before {
    background-position: -434px -41px
}

.color-alt-greenLight .info-block,
.color-alt-greenLight #footer,
.color-alt-greenLight .logo-block .frame,
.color-alt-greenLight .tab-container,
.color-alt-greenLight .goal-list .popup table th,
.color-alt-greenLight .modal-cont .popup th,
.color-alt-greenLight .cycle-games-popup .popup table th {
    border-top-color: #60993A
}

.color-alt-greenLight .social-container,
.color-alt-greenLight .mainarticle-league.banner-holder .desc {
    border-color: #60993A
}

.color-alt-greenLight .heading {
    border-bottom-color: #60993A !important
}

.color-alt-greenLight #content .gmask .holder,
.color-alt-greenLight .people-block,
.color-alt-greenLight .banner-holder.mainarticle-world .desc,
.color-alt-greenLight #content .gallery3 .pagination ul a:hover,
.color-alt-greenLight #content .gallery3 .pagination ul li.active a,
.color-alt-greenLight .goal-list .popup .title,
.color-alt-greenLight .modal-cont .popup .title,
.color-alt-greenLight .info-modal .title,
.color-alt-greenLight .player-modal .title,
.color-alt-greenLight .cycle-games-popup .popup .title,
.color-alt-greenLight .people-block .figure span,
.color-alt-greenLight .people-block .article .holder,
.color-alt-greenLight .video-holder .title {
    background: #60993A
}

.color-alt-greenLight .post-container .post .title a:hover,
.color-alt-greenLight .logo-block .box a:hover,
.color-alt-greenLight .show-all,
.color-alt-greenLight #main .social-container p a:hover,
.color-alt-greenLight #main .social-container a.alt,
.color-alt-greenLight #content>a,
#content .section-greenLight.color-alt-greenLight a:hover,
.color-alt-greenLight #main .gallery .holder a:hover,
.color-alt-greenLight #main .galleryWord .holder a:hover,
.color-alt-greenLight .article-content p a,
.color-alt-greenLight .comment-box .reply,
.color-alt-greenLight #main .info-block p a:hover,
.color-alt-greenLight #main .info-block h3 a:hover,
.color-alt-greenLight #main .social-container span.alt,
.color-alt-greenLight #main .article-list h2 a:hover,
.color-alt-greenLight #main .article-list a:hover,
.color-alt-greenLight .article-list .league-title a:hover,
.color-alt-greenLight #footer ul a:hover,
#main .section-greenLight .article-holder h2 a:hover,
#main .section-greenLight .article-holder-homepage h2 a:hover,
.color-alt-greenLight .link-holder a,
.color-alt-greenLight .players-block .gallery ul li:hover a,
.color-alt-greenLight .results-holder .result-table .link,
.color-alt-greenLight .player-info .heading a,
.color-alt-greenLight #main .about-block .text h3,
.color-alt-greenLight .tab-box .result-table .link,
.color-alt-greenLight .score-list .link,
.color-alt-greenLight .result-form input[type="button"],
.color-alt-greenLight .player-modal .stat-data strong:hover,
.color-alt-greenLight #content span>ul li,
.color-alt-greenLight #content span>ul li a,
.color-alt-greenLight .ranking-table-comment-title,
#main .section-greenLight .headerTextLeftSide,
#main .section-greenLight .article-holder .date .writer a:hover,
#content .section-greenLight .article-list p a:hover,
#content .section-greenLight .article-list .writer-date .writer a:hover,
#content .section-greenLight .article-list .league-title a:hover,
#content .section-greenLight .article-list .league-title h2 a:hover {
    color: #60993A !important
}

#main .section-greenLight .Background {
    background-color: #60993A
}

#main .section-greenLight .rightRect {
    border-bottom: 24px solid #60993A;
    height: 24px
}

#main .section-greenLight .rightRectLeftSide {
    border-bottom: 24px solid #60993A
}

#main .section-greenLight .leftRect {
    color: #60993A;
    border-top: 24px solid #60993A
}

#main .section-greenLight .leftRectLeftSide {
    color: #60993A;
    border-top: 24px solid #60993A
}

#main .section-greenLight .text-greenLight {
    color: #60993A
}

.color-alt-greenLight #content .bullet {
    background-color: #60993A !important
}

.color-alt-greenLight .news-block .news-list ul li:before,
.color-alt-greenLight .news-list li:before,
.color-alt-greenLight .show-all:before,
.color-alt-greenLight #content>ul li:before,
.color-alt-greenLight a.print,
.color-alt-greenLight .add-comment:after,
.color-alt-greenLight #content .comment-box .box:after,
.color-alt-greenLight #content>ul li:before,
.color-alt-greenLight #content span>ul li:before,
.color-alt-greenLight .link-holder a span,
.color-alt-greenLight .premiership-block a.btn-next,
.color-alt-greenLight .premiership-block a.btn-prev,
.color-alt-greenLight .comment-list .tlkb_load_more a span,
.color-alt-greenLight #content .gallery .btn-next,
.color-alt-greenLight #content .gallery .btn-prev,
.color-alt-greenLight .cycle-games-popup .popup .close,
.color-alt-greenLight .modal-cont .popup .close,
.color-alt-greenLight .goal-list .popup .close,
.color-alt-greenLight .recomm-holder .btn-next,
.color-alt-greenLight .recomm-holder .btn-prev,
.color-alt-greenLight .scoreboard:before,
.color-alt-greenLight .info-modal .close,
.color-alt-greenLight .player-modal .close {
    background-image: url('images/sprite-green.png')
}

.color-alt-greenLight .add-nav>ul>li.active:after {
    background-position: 0 -18px
}

.color-alt-greenLight #content .galleryWord,
.color-alt-greenLight .people-block .box-holder,
.color-alt-greenLight .people-block .article,
.color-alt-greenLight .items-container,
.color-alt-greenLight #main .gallery2,
.color-alt-greenLight #main .galleryWord2,
.color-alt-greenLight .video-container,
.color-alt-greenLight .schedule-block,
.color-alt-greenLight .play-block,
.color-alt-greenLight #sidebar .post-list .heading2,
.color-alt-greenLight .article-content div.video-div>a.video-anc {
    background-image: url('images/bg-video-green.jpg')
}

.color-alt-greenLight .people-block .figure span:after {
    background: url(images/bg-title-green.gif) no-repeat
}

.color-alt-greenLight .video-holder .title:before {
    background-image: url(images/sprite2-green.png)
}

.color-alt-greenLight #main .hint .article-views {
    background: url(Images/greeneye.png) no-repeat 100%
}

.color-alt-greenLight #main .hint .article-talkbacks {
    background: url(Images/greencomm.png) no-repeat 100%
}

.color-alt-greenLight #main .hint .article-talkbacksHomePage {
    background: url(Images/greencomm.png) no-repeat 100%
}

.color-alt-greenLight #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #60993A
}

.color-alt-greenLight .talkbacks-cont svg {
    fill: #60993A
}

.color-alt-grey .logo {
    background-position: 0 -1040px
}

.color-alt-grey .add-nav .more:before {
    background-position: -434px -41px
}

.color-alt-grey .info-block,
.color-alt-grey #footer,
.color-alt-grey .logo-block .frame,
.color-alt-grey .tab-container,
.color-alt-grey .goal-list .popup table th,
.color-alt-grey .modal-cont .popup th,
.color-alt-grey .cycle-games-popup .popup table th {
    border-top-color: #59616c
}

.color-alt-grey .social-container,
.color-alt-grey .mainarticle-league.banner-holder .desc {
    border-color: #59616c
}

.color-alt-grey .heading {
    border-bottom-color: #59616c !important
}

.color-alt-grey #content .gmask .holder,
.color-alt-grey .people-block,
.color-alt-grey .banner-holder.mainarticle-world .desc,
.color-alt-grey #content .gallery3 .pagination ul a:hover,
.color-alt-grey #content .gallery3 .pagination ul li.active a,
.color-alt-grey .goal-list .popup .title,
.color-alt-grey .modal-cont .popup .title,
.color-alt-grey .info-modal .title,
.color-alt-grey .player-modal .title,
.color-alt-grey .cycle-games-popup .popup .title,
.color-alt-grey .people-block .figure span,
.color-alt-grey .people-block .article .holder,
.color-alt-grey .video-holder .title,
.color-alt-grey #main .section-black .Background {
    background: #59616c
}

.color-alt-grey .post-container .post .title a:hover,
.color-alt-grey .logo-block .box a:hover,
.color-alt-grey .show-all,
.color-alt-grey #main .social-container p a:hover,
.color-alt-grey #main .social-container a.alt,
.color-alt-grey #content>a,
#content .section-grey.color-alt-grey a:hover,
.color-alt-grey #main .gallery .holder a:hover,
.color-alt-grey #main .galleryWord .holder a:hover,
.color-alt-grey .article-content p a,
.color-alt-grey .comment-box .reply,
.color-alt-grey #main .info-block p a:hover,
.color-alt-grey #main .info-block h3 a:hover,
.color-alt-grey #main .social-container span.alt,
.color-alt-grey #main .article-list h2 a:hover,
.color-alt-grey #main .article-list a:hover,
.color-alt-grey .article-list .league-title a:hover,
.color-alt-grey #footer ul a:hover,
#main .section-grey .article-holder h2 a:hover,
#main .section-grey .article-holder-homepage h2 a:hover,
.color-alt-grey #content .galleryWord .thumbnails ul li a :hover,
.color-alt-grey .link-holder a,
.color-alt-grey .players-block .gallery ul li:hover a,
.color-alt-grey .results-holder .result-table .link,
.color-alt-grey .player-info .heading a,
.color-alt-grey #main .about-block .text h3,
.color-alt-grey .tab-box .result-table .link,
.color-alt-grey .score-list .link,
.color-alt-grey .result-form input[type="button"],
.color-alt-grey .player-modal .stat-data strong:hover,
.color-alt-grey #content span>ul li,
.color-alt-grey #content span>ul li a,
.color-alt-grey .ranking-table-comment-title,
#main .section-grey .headerTextLeftSide,
#main .section-grey .article-holder .date .writer a:hover,
#content .section-grey .article-list p a:hover,
#content .section-grey .article-list .writer-date .writer a:hover,
#content .section-grey .article-list .league-title a:hover,
#content .section-grey .article-list .league-title h2 a:hover {
    color: #59616c !important
}

#main .section-grey .Background {
    background-color: #59616c
}

#main .section-grey .rightRect {
    height: 24px;
    border-bottom: 24px solid #59616c
}

#main .section-grey .rightRectLeftSide {
    border-bottom: 24px solid #59616c
}

#main .section-grey .leftRect {
    color: #59616c;
    border-top: 24px solid #59616c
}

#main .section-grey .leftRectLeftSide {
    color: #59616c;
    border-top: 24px solid #59616c
}

#main .section-grey .text-grey {
    color: #59616c
}

.color-alt-grey #content .bullet {
    background-color: #59616c !important
}

.color-alt-grey .news-block .news-list ul li:before,
.color-alt-grey .news-list li:before,
.color-alt-grey .show-all:before,
.color-alt-grey #content>ul li:before,
.color-alt-grey a.print,
.color-alt-grey .add-comment:after,
.color-alt-grey #content .comment-box .box:after,
.color-alt-grey #content>ul li:before,
.color-alt-grey #content span>ul li:before,
.color-alt-grey .link-holder a span,
.color-alt-grey .premiership-block a.btn-next,
.color-alt-grey .premiership-block a.btn-prev,
.color-alt-grey .comment-list .tlkb_load_more a span,
.color-alt-grey #content .gallery .btn-next,
.color-alt-grey #content .gallery .btn-prev,
.color-alt-grey .cycle-games-popup .popup .close,
.color-alt-grey .modal-cont .popup .close,
.color-alt-grey .goal-list .popup .close,
.color-alt-grey .recomm-holder .btn-next,
.color-alt-grey .recomm-holder .btn-prev,
.color-alt-grey .scoreboard:before,
.color-alt-grey .info-modal .close,
.color-alt-grey .player-modal .close {
    background-image: url('images/sprite-grey.png')
}

.color-alt-grey .add-nav>ul>li.active:after {
    background-position: 0 -18px
}

.color-alt-grey .people-block .box-holder,
.color-alt-grey .people-block .article,
.color-alt-grey .items-container,
.color-alt-grey #main .gallery2,
.color-alt-grey #main .galleryWord2,
.color-alt-grey .video-container,
.color-alt-grey .schedule-block,
.color-alt-grey .play-block,
.color-alt-grey #sidebar .post-list .heading2,
.color-alt-grey .article-content div.video-div>a.video-anc {
    background-image: url('images/bg-video-grey.jpg')
}

.color-alt-grey .people-block .figure span:after {
    background: url(images/bg-title-grey.gif) no-repeat
}

.color-alt-grey .video-holder .title:before {
    background-image: url(images/sprite2-grey.png)
}

.color-alt-grey #main .hint .article-views {
    background: url(Images/greyeye.png) no-repeat 100%
}

.color-alt-grey #main .hint .article-talkbacks {
    background: url(Images/greycomm.png) no-repeat 100%
}

.color-alt-grey #main .hint .article-talkbacksHomePage {
    background: url(Images/greycomm.png) no-repeat 100%
}

.color-alt-grey .add-nav>ul>li.active:after {
    background: url(images/bg-add-nav-grey.png) no-repeat 0 -18px
}

.color-alt-grey .add-nav .more:before {
    background: url(images/sprite-grey.png) no-repeat -434px -26px
}

.section-grey .link-more,
.schedule-block .link-more {
    background: url(images/sprite-grey.png) no-repeat -434px -168px !important
}

.section-grey>.holder:before {
    background: #59616c
}

#main .section-grey .article-holder .item-holder.video.aod:after {
    background: url(images/audios.png) -127px 0 no-repeat
}

.color-alt-grey #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #59616c
}

.color-alt-grey .talkbacks-cont svg {
    fill: #59616c
}

.color-alt-pink .logo {
    background-position: 0 -303px
}

.color-alt-pink .add-nav .more:before {
    background-position: -434px -41px
}

.color-alt-pink .info-block,
.color-alt-pink #footer,
.color-alt-pink .logo-block .frame,
.color-alt-pink .tab-container,
.color-alt-pink .goal-list .popup table th,
.color-alt-pink .modal-cont .popup th,
.color-alt-pink .cycle-games-popup .popup table th {
    border-top-color: #E1386F
}

.color-alt-pink .social-container,
.color-alt-pink .mainarticle-league.banner-holder .desc {
    border-color: #E1386F
}

.color-alt-pink .heading {
    border-bottom-color: #E1386F !important
}

.color-alt-pink #content .gmask .holder,
.color-alt-pink .people-block,
.color-alt-pink .banner-holder.mainarticle-world .desc,
.color-alt-pink #content .gallery3 .pagination ul a:hover,
.color-alt-pink #content .gallery3 .pagination ul li.active a,
.color-alt-pink .goal-list .popup .title,
.color-alt-pink .modal-cont .popup .title,
.color-alt-pink .info-modal .title,
.color-alt-pink .player-modal .title,
.color-alt-pink .cycle-games-popup .popup .title,
.color-alt-pink .people-block .figure span,
.color-alt-pink .people-block .article .holder,
.color-alt-pink .video-holder .title,
.color-alt-pink #main .section-black .Background {
    background: #E1386F
}

.color-alt-pink .post-container .post .title a:hover,
.color-alt-pink .logo-block .box a:hover,
.color-alt-pink .show-all,
.color-alt-pink #main .social-container p a:hover,
.color-alt-pink #main .social-container a.alt,
.color-alt-pink #content>a,
#content .section-pink.color-alt-pink a:hover,
.color-alt-pink #main .gallery .holder a:hover,
.color-alt-pink #main .galleryWord .holder a:hover,
.color-alt-pink .article-content p a,
.color-alt-pink .comment-box .reply,
.color-alt-pink #main .info-block p a:hover,
.color-alt-pink #main .info-block h3 a:hover,
.color-alt-pink #main .social-container span.alt,
.color-alt-pink #main .article-list h2 a:hover,
.color-alt-pink #main .article-list a:hover,
.color-alt-pink .article-list .league-title a:hover,
.color-alt-pink #footer ul a:hover,
#main .section-pink .article-holder h2 a:hover,
#main .section-pink .article-holder-homepage h2 a:hover,
.color-alt-pink #content .galleryWord .thumbnails ul li a :hover,
.color-alt-pink .link-holder a,
.color-alt-pink .players-block .gallery ul li:hover a,
.color-alt-pink .results-holder .result-table .link,
.color-alt-pink .player-info .heading a,
.color-alt-pink #main .about-block .text h3,
.color-alt-pink .tab-box .result-table .link,
.color-alt-pink .score-list .link,
.color-alt-pink .result-form input[type="button"],
.color-alt-pink .player-modal .stat-data strong:hover,
.color-alt-pink #content span>ul li,
.color-alt-pink #content span>ul li a,
.color-alt-pink .ranking-table-comment-title,
#main .section-pink .headerTextLeftSide,
#main .section-pink .article-holder .date .writer a:hover,
#content .section-pink .article-list p a:hover,
#content .section-pink .article-list .writer-date .writer a:hover,
#content .section-pink .article-list .league-title a:hover,
#content .section-pink .article-list .league-title h2 a:hover {
    color: #E1386F !important
}

#main .section-pink .Background {
    background-color: #E1386F
}

#main .section-pink .rightRect {
    border-bottom: 24px solid #E1386F;
    height: 24px
}

#main .section-pink .rightRectLeftSide {
    border-bottom: 24px solid #E1386F
}

#main .section-pink .leftRect {
    color: #E1386F;
    border-top: 24px solid #E1386F
}

#main .section-pink .leftRectLeftSide {
    color: #E1386F;
    border-top: 24px solid #E1386F
}

#main .section-pink .text-pink {
    color: #E1386F
}

.color-alt-green #content .bullet {
    background-color: #E1386F !important
}

.color-alt-pink .news-block .news-list ul li:before,
.color-alt-pink .news-list li:before,
.color-alt-pink .show-all:before,
.color-alt-pink #content>ul li:before,
.color-alt-pink a.print,
.color-alt-pink .add-comment:after,
.color-alt-pink #content .comment-box .box:after,
.color-alt-pink #content>ul li:before,
.color-alt-pink #content span>ul li:before,
.color-alt-pink .link-holder a span,
.color-alt-pink .premiership-block a.btn-next,
.color-alt-pink .premiership-block a.btn-prev,
.color-alt-pink .comment-list .tlkb_load_more a span,
.color-alt-pink #content .gallery .btn-next,
.color-alt-pink #content .gallery .btn-prev,
.color-alt-pink .cycle-games-popup .popup .close,
.color-alt-pink .modal-cont .popup .close,
.color-alt-pink .goal-list .popup .close,
.color-alt-pink .recomm-holder .btn-next,
.color-alt-pink .recomm-holder .btn-prev,
.color-alt-pink .scoreboard:before,
.color-alt-pink .info-modal .close,
.color-alt-pink .player-modal .close {
    background-image: url('images/sprite-green.png')
}

.color-alt-pink .add-nav>ul>li.active:after {
    background-position: 0 -18px
}

.color-alt-pink .people-block .box-holder,
.color-alt-pink .people-block .article,
.color-alt-pink .items-container,
.color-alt-pink #main .gallery2,
.color-alt-pink #main .galleryWord2,
.color-alt-pink .video-container,
.color-alt-pink .schedule-block,
.color-alt-pink .play-block,
.color-alt-pink #sidebar .post-list .heading2,
.color-alt-pink .article-content div.video-div>a.video-anc {
    background-image: url('images/bg-video-green.jpg')
}

.color-alt-pink .people-block .figure span:after {
    background: url(images/bg-title-green.gif) no-repeat
}

.color-alt-pink .video-holder .title:before {
    background-image: url(images/sprite2-green.png)
}

.color-alt-pink #main .hint .article-views {
    background: url(Images/greeneye.png) no-repeat 100%
}

.color-alt-pink #main .hint .article-talkbacks {
    background: url(Images/greencomm.png) no-repeat 100%
}

.color-alt-pink #main .hint .article-talkbacksHomePage {
    background: url(Images/greencomm.png) no-repeat 100%
}

.color-alt-pink #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #E1386F
}

.color-alt-pink .talkbacks-cont svg {
    fill: #E1386F
}

.color-alt-dark-green .logo {
    background-position: 0 -303px
}

.color-alt-dark-green .add-nav .more:before {
    background-position: -434px -41px
}

.color-alt-dark-green .info-block,
.color-alt-dark-green #footer,
.color-alt-dark-green .logo-block .frame,
.color-alt-dark-green .tab-container,
.color-alt-dark-green .goal-list .popup table th,
.color-alt-dark-green .modal-cont .popup th,
.color-alt-dark-green .cycle-games-popup .popup table th {
    border-top-color: #0b7314
}

.color-alt-dark-green .social-container,
.color-alt-dark-green .mainarticle-league.banner-holder .desc {
    border-color: #0b7314
}

.color-alt-dark-green .heading {
    border-bottom-color: #0b7314 !important
}

.color-alt-dark-green #content .gmask .holder,
.color-alt-dark-green .people-block,
.color-alt-dark-green .banner-holder.mainarticle-world .desc,
.color-alt-dark-green #content .gallery3 .pagination ul a:hover,
.color-alt-dark-green #content .gallery3 .pagination ul li.active a,
.color-alt-dark-green .goal-list .popup .title,
.color-alt-dark-green .modal-cont .popup .title,
.color-alt-dark-green .info-modal .title,
.color-alt-dark-green .player-modal .title,
.color-alt-dark-green .cycle-games-popup .popup .title,
.color-alt-dark-green .people-block .figure span,
.color-alt-dark-green .people-block .article .holder,
.color-alt-dark-green .video-holder .title,
.color-alt-dark-green #main .section-black .Background {
    background: #0b7314
}

.color-alt-dark-green .post-container .post .title a:hover,
.color-alt-dark-green .logo-block .box a:hover,
.color-alt-dark-green .show-all,
.color-alt-dark-green #main .social-container p a:hover,
.color-alt-dark-green #main .social-container a.alt,
.color-alt-dark-green #content>a,
#content .section-dark-green.color-alt-dark-green a:hover,
.color-alt-dark-green #main .gallery .holder a:hover,
.color-alt-dark-green #main .galleryWord .holder a:hover,
.color-alt-dark-green .article-content p a,
.color-alt-dark-green .comment-box .reply,
.color-alt-dark-green #main .info-block p a:hover,
.color-alt-dark-green #main .info-block h3 a:hover,
.color-alt-dark-green #main .social-container span.alt,
.color-alt-dark-green #main .article-list h2 a:hover,
.color-alt-dark-green #main .article-list a:hover,
.color-alt-dark-green .article-list .league-title a:hover,
.color-alt-dark-green #footer ul a:hover,
#main .section-green .article-holder h2 a:hover,
#main .section-green .article-holder-homepage h2 a:hover,
.color-alt-dark-green .link-holder a,
.color-alt-dark-green .players-block .gallery ul li:hover a,
.color-alt-dark-green .results-holder .result-table .link,
.color-alt-dark-green .player-info .heading a,
.color-alt-dark-green #main .about-block .text h3,
.color-alt-dark-green .tab-box .result-table .link,
.color-alt-dark-green .score-list .link,
.color-alt-dark-green .result-form input[type="button"],
.color-alt-dark-green .player-modal .stat-data strong:hover,
.color-alt-dark-green #content span>ul li,
.color-alt-dark-green #content span>ul li a,
.color-alt-dark-green .ranking-table-comment-title,
#main .section-dark-green .headerTextLeftSide,
#main .section-dark-green .article-holder .date .writer a:hover,
#content .section-dark-green .article-list p a:hover,
#content .section-dark-green .article-list .writer-date .writer a:hover,
#content .section-dark-green .article-list .league-title a:hover,
#content .section-dark-green .article-list .league-title h2 a:hover {
    color: #0b7314 !important
}

#main .section-dark-green .Background {
    background-color: #0b7314
}

#main .section-dark-green .rightRect {
    border-bottom: 24px solid #0b7314;
    height: 24px
}

#main .section-dark-green .rightRectLeftSide {
    border-bottom: 24px solid #0b7314
}

#main .section-dark-green .leftRect {
    color: #0b7314;
    border-top: 24px solid #0b7314
}

#main .section-dark-green .leftRectLeftSide {
    color: #0b7314;
    border-top: 24px solid #0b7314
}

#main .section-dark-green .text-dark-green {
    color: #0b7314
}

.color-alt-dark-green #content .bullet {
    background-color: #0b7314 !important
}

.color-alt-dark-green .news-block .news-list ul li:before,
.color-alt-dark-green .news-list li:before,
.color-alt-dark-green .show-all:before,
.color-alt-dark-green #content>ul li:before,
.color-alt-dark-green a.print,
.color-alt-dark-green .add-comment:after,
.color-alt-dark-green #content .comment-box .box:after,
.color-alt-dark-green #content>ul li:before,
.color-alt-dark-green #content span>ul li:before,
.color-alt-dark-green .link-holder a span,
.color-alt-dark-green .premiership-block a.btn-next,
.color-alt-dark-green .premiership-block a.btn-prev,
.color-alt-dark-green .comment-list .tlkb_load_more a span,
.color-alt-dark-green #content .gallery .btn-next,
.color-alt-dark-green #content .gallery .btn-prev,
.color-alt-dark-green .cycle-games-popup .popup .close,
.color-alt-dark-green .modal-cont .popup .close,
.color-alt-dark-green .goal-list .popup .close,
.color-alt-dark-green .recomm-holder .btn-next,
.color-alt-dark-green .recomm-holder .btn-prev,
.color-alt-dark-green .scoreboard:before,
.color-alt-dark-green .info-modal .close,
.color-alt-dark-green .player-modal .close {
    background-image: url('images/sprite-dark-green.png')
}

.color-alt-dark-green .add-nav>ul>li.active:after {
    background-position: 0 -18px
}

.color-alt-dark-green .people-block .box-holder,
.color-alt-dark-green .people-block .article,
.color-alt-dark-green .items-container,
.color-alt-dark-green #main .gallery2,
.color-alt-dark-green #main .galleryWord2,
.color-alt-dark-green .video-container,
.color-alt-dark-green .schedule-block,
.color-alt-dark-green .play-block,
.color-alt-dark-green #sidebar .post-list .heading2,
.color-alt-dark-green .article-content div.video-div>a.video-anc {
    background-image: url('images/bg-video-dark-green.jpg')
}

.color-alt-dark-green .people-block .figure span:after {
    background: url(images/bg-title-dark-green.gif) no-repeat
}

.color-alt-dark-green .video-holder .title:before {
    background-image: url(images/sprite2-dark-green.png)
}

.color-alt-dark-green #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #0b7314
}

.color-alt-dark-green .talkbacks-cont svg {
    fill: #0b7314
}

.color-alt-yellow .logo {
    background-position: 0 -404px
}

.color-alt-yellow .add-nav .more:before {
    background-position: -434px -41px
}

.color-alt-yellow .info-block,
.color-alt-yellow #footer,
.color-alt-yellow .logo-block .frame,
.color-alt-yellow .tab-container,
.color-alt-yellow .goal-list .popup table th,
.color-alt-yellow .modal-cont .popup th,
.color-alt-yellow .cycle-games-popup .popup table th {
    border-top-color: #FFA200
}

.color-alt-yellow .social-container,
.color-alt-yellow .mainarticle-league.banner-holder .desc {
    border-color: #FFA200
}

.color-alt-yellow .heading {
    border-bottom-color: #FFA200 !important
}

.color-alt-yellow #content .gmask .holder,
.color-alt-yellow .people-block,
.color-alt-yellow .banner-holder.mainarticle-world .desc,
.color-alt-yellow #content .gallery3 .pagination ul a:hover,
.color-alt-yellow #content .gallery3 .pagination ul li.active a,
.color-alt-yellow .goal-list .popup .title,
.color-alt-yellow .modal-cont .popup .title,
.color-alt-yellow .info-modal .title,
.color-alt-yellow .player-modal .title,
.color-alt-yellow .cycle-games-popup .popup .title,
.color-alt-yellow .people-block .figure span,
.color-alt-yellow .people-block .article .holder,
.color-alt-yellow .video-holder .title {
    background: #FFA200
}

.color-alt-yellow .post-container .post .title a:hover,
.color-alt-yellow .logo-block .box a:hover,
.color-alt-yellow .show-all,
.color-alt-yellow #main .social-container p a:hover,
.color-alt-yellow #main .social-container a.alt,
.color-alt-yellow #content>a,
#content .section-yellow.color-alt-yellow a:hover,
.color-alt-yellow #main .gallery .holder a:hover,
.color-alt-yellow #main .galleryWord .holder a:hover,
.color-alt-yellow .article-content p a,
.color-alt-yellow .comment-box .reply,
.color-alt-yellow #main .info-block p a:hover,
.color-alt-yellow #main .info-block h3 a:hover,
.color-alt-yellow #main .social-container span.alt,
.color-alt-yellow #main .article-list h2 a:hover,
.color-alt-yellow #main .article-list a:hover,
.color-alt-yellow .article-list .league-title a:hover,
.color-alt-yellow #footer ul a:hover,
#main .section-yellow .article-holder h2 a:hover,
#main .section-yellow .article-holder-homepage h2 a:hover,
.color-alt-yellow #content .galleryWord .thumbnails ul li a :hover,
.color-alt-yellow .link-holder a,
.color-alt-yellow .players-block .gallery ul li:hover a,
.color-alt-yellow .results-holder .result-table .link,
.color-alt-yellow .player-info .heading a,
.color-alt-yellow #main .about-block .text h3,
.color-alt-yellow .tab-box .result-table .link,
.color-alt-yellow .score-list .link,
.color-alt-yellow .result-form input[type="button"],
.color-alt-yellow .player-modal .stat-data strong:hover,
.color-alt-yellow #content span>ul li,
.color-alt-yellow #content span>ul li a,
.color-alt-yellow .ranking-table-comment-title,
#main .section-yellow .headerTextLeftSide,
#main .section-yellow .article-holder .date .writer a:hover,
#content .section-yellow .article-list p a:hover,
#content .section-yellow .article-list .writer-date .writer a:hover,
#content .section-yellow .article-list .league-title a:hover,
#content .section-yellow .article-list .league-title h2 a:hover {
    color: #FFA200 !important
}

#main .section-yellow-green .Background,
.section-yellow #main .section-black .Background {
    background-color: #0b7314
}

#main .section-yellow .rightRect {
    border-bottom: 24px solid #0b7314;
    height: 24px
}

#main .section-yellow .rightRectLeftSide {
    border-bottom: 24px solid #0b7314
}

#main .section-yellow .leftRect {
    color: #FFA200;
    border-top: 24px solid #FFA200
}

#main .section-yellow .leftRectLeftSide {
    color: #FFA200;
    border-top: 24px solid #0b7314
}

#main .section-yellow .text-yellow {
    color: #FFA200
}

.color-alt-yellow #content .bullet {
    background-color: #FFA200 !important
}

.color-alt-yellow .news-block .news-list ul li:before,
.color-alt-yellow .news-list li:before,
.color-alt-yellow .show-all:before,
.color-alt-yellow #content>ul li:before,
.color-alt-yellow a.print,
.color-alt-yellow .add-comment:after,
.color-alt-yellow #content .comment-box .box:after,
.color-alt-yellow #content>ul li:before,
.color-alt-yellow #content span>ul li:before,
.color-alt-yellow .link-holder a span,
.color-alt-yellow .premiership-block a.btn-next,
.color-alt-yellow .premiership-block a.btn-prev,
.color-alt-yellow .comment-list .tlkb_load_more a span,
.color-alt-yellow #content .gallery .btn-next,
.color-alt-yellow #content .gallery .btn-prev,
.color-alt-yellow .cycle-games-popup .popup .close,
.color-alt-yellow .modal-cont .popup .close,
.color-alt-yellow .goal-list .popup .close,
.color-alt-yellow .recomm-holder .btn-next,
.color-alt-yellow .recomm-holder .btn-prev,
.color-alt-yellow .scoreboard:before,
.color-alt-yellow .info-modal .close,
.color-alt-yellow .player-modal .close {
    background-image: url('images/sprite-yellow.png')
}

.color-alt-yellow .add-nav>ul>li.active:after {
    background-position: 0 -18px
}

.color-alt-yellow .people-block .box-holder,
.color-alt-yellow .people-block .article,
.color-alt-yellow .items-container,
.color-alt-yellow #main .gallery2,
.color-alt-yellow #main .galleryWord2,
.color-alt-yellow .video-container,
.color-alt-yellow .schedule-block,
.color-alt-yellow .play-block,
.color-alt-yellow #sidebar .post-list .heading2,
.color-alt-yellow .article-content div.video-div>a.video-anc {
    background-image: url('images/bg-video-yellow.jpg')
}

.color-alt-yellow .people-block .figure span:after {
    background: url(images/bg-title-yellow.gif) no-repeat
}

.color-alt-yellow .video-holder .title:before {
    background-image: url(images/sprite2-yellow.png)
}

.color-alt-yellow #main .hint .article-views {
    background: url(Images/yelloweye.png) no-repeat 100%
}

.color-alt-yellow #main .hint .article-talkbacks {
    background: url(Images/yellowcomm.png) no-repeat 100%
}

.color-alt-yellow #main .hint .article-talkbacksHomePage {
    background: url(Images/yellowcomm.png) no-repeat 100%
}

.color-alt-yellow #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #FFA200
}

.color-alt-yellow .talkbacks-cont svg {
    fill: #FFA200
}

.color-alt-blue #content span>ul li,
.color-alt-blue #content span>ul li a,
.color-alt-blue .ranking-table-comment-title {
    color: #014bad
}

.color-alt-blue .logo {
    background-position: 0 0
}

.color-alt-blue #content .galleryWord .thumbnails ul li a :hover,
#main .section-blue .article-holder h2 a:hover,
#main .section-blue .article-holder-homepage h2 a:hover,
.color-alt-blue #content .galleryWord .thumbnails ul li a :hover,
.color-alt-blue article-list h2 a:hover,
.color-alt-blue .article-list a:hover,
.color-alt-blue .article-list h2 a:hover,
.color-alt-blue #main .article-list a:hover,
.color-alt-blue .article-list .league-title a:hover,
#main .section-blue .article-holder .date .writer a:hover,
#content .section-blue .articleBannerSpace p a:hover,
#content .section-blue .article-list .writer-date .writer a:hover,
#content .section-blue .articleBannerSpace a:hover,
#content .section-blue .articleBannerSpace a:hover {
    color: #014bad !important
}

.color-alt-blue #main .section-black .Background,
.color-alt-blue #content .bullet {
    background-color: #014bad !important
}

.color-alt-blue .news-block .news-list ul li:before,
.color-alt-blue .news-list li:before,
.color-alt-blue .show-all:before,
.color-alt-blue #content>ul li:before,
.color-alt-blue a.print,
.color-alt-blue .add-comment:after,
.color-alt-blue #content .comment-box .box:after,
.color-alt-blue #content>ul li:before,
.color-alt-blue #content span>ul li:before,
.color-alt-blue .link-holder a span,
.color-alt-blue .premiership-block a.btn-next,
.color-alt-blue .premiership-block a.btn-prev,
.color-alt-blue .comment-list .tlkb_load_more a span,
.color-alt-blue #content .gallery .btn-next,
.color-alt-blue #content .gallery .btn-prev,
.color-alt-blue .cycle-games-popup .popup .close,
.color-alt-blue .modal-cont .popup .close,
.color-alt-blue .goal-list .popup .close,
.color-alt-blue .recomm-holder .btn-next,
.color-alt-blue .recomm-holder .btn-prev,
.color-alt-blue .scoreboard:before,
.color-alt-blue .info-modal .close,
.color-alt-blue .player-modal .close {
    background-image: url('images/sprite-blue.png')
}

.color-alt-blue .add-nav>ul>li.active:after {
    background-position: 0 -18px
}

.color-alt-blue .people-block .box-holder,
.color-alt-blue .people-block .article,
.color-alt-blue .items-container,
.color-alt-blue #main .gallery2,
.color-alt-blue #main .galleryWord2,
.color-alt-blue .video-container,
.color-alt-blue .schedule-block,
.color-alt-blue .play-block,
.color-alt-blue #sidebar .post-list .heading2,
.color-alt-blue .article-content div.video-div>a.video-anc {
    background-image: url('images/bg-video-blue.jpg')
}

.color-alt-blue .people-block .figure span:after {
    background: url(images/bg-title-#014bad.gif) no-repeat
}

.color-alt-blue .video-holder .title:before {
    background-image: url(images/sprite2-#014bad.png)
}

.color-alt-blue #main .hint .article-views {
    background: url(Images/#014badeye.png) no-repeat 100%
}

.color-alt-blue #main .hint .article-talkbacksHomePage {
    background: url(Images/#014badcomm.png) no-repeat 100%
}

.color-alt-blue #main .banner-holder.mainarticle-world .talkbacks-cont .messages-count a {
    color: #014bad
}

.color-alt-blue .talkbacks-cont svg {
    fill: #014bad
}

.mainarticle-league .desc span>a {
    color: #000 !important
}

.red_mark {
    position: absolute;
    width: 27px;
    height: 27px;
    top: 10px;
    left: 18px;
    z-index: 10;
    background: url('Images/red_mark.png') no-repeat
}

.article-list .red_mark {
    width: 20px;
    height: 20px;
    background-size: 20px 20px;
    top: 8px;
    left: 15px
}

.bomba-img-holder.btn-play .red_mark {
    top: 30px;
    left: 80px;
    z-index: 999
}

.dialog-confirm {
    font-size: 16px
}

.dialog-confirm p {
    margin: 0;
    line-height: 17px;
    font-size: 14px
}

.dlg-person {
    position: fixed !important;
    top: 250px !important;
    direction: rtl
}

.dlg-person .ui-dialog-titlebar-close {
    top: 73% !important
}

.dlg-person .ui-icon-closethick {
    left: 45% !important
}

.dlg-person .ui-dialog-title {
    float: none !important
}

.counter_flach_cont {
    position: relative;
    width: 970px;
    height: 60px;
    margin: 15px 0 15px;
    background: url(../../Images/mondial-counterSite.png) no-repeat;
    color: #FFF
}

.counter_flach_cont .counter_banner {
    position: absolute;
    top: 2px;
    right: 50px;
    z-index: 2
}

.counter_flach_cont .counter_time {
    position: absolute;
    width: 40px;
    height: 40px;
    top: 0;
    font-size: 32px;
    font-weight: bold;
    text-align: center
}

.counter_flach_cont .counter_time.seconds {
    left: 436px
}

.counter_flach_cont .counter_time.minutes {
    left: 368px
}

.counter_flach_cont .counter_time.hours {
    left: 298px
}

.counter_flach_cont .counter_time.days {
    left: 226px
}

.counter_flach_cont .mondial_area_link {
    position: absolute;
    width: 100%;
    height: 56px;
    top: 2px;
    left: 2px
}

.counter_flach_cont .counter_date {
    position: absolute;
    width: 80px;
    height: 20px;
    top: 36px;
    right: 63px;
    color: #1c4a75;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web';
    font-size: 18px;
    font-weight: normal;
    text-align: center;
    display: none
}

.mondial_iframe {
    margin: 0 -6px 10px
}

.title_blue.daka {
    font-size: 18px;
    line-height: 22px;
    display: block;
    margin: 0 2px 0 2px;
    padding: 0 5px 2px;
    border: 0;
    background: #f5f5f5;
    background: -moz-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(237, 237, 237, 1)), color-stop(19%, rgba(248, 248, 248, 1)), color-stop(35%, rgba(253, 253, 253, 1)), color-stop(100%, rgba(255, 255, 255, 1)));
    background: -webkit-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -o-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: -ms-linear-gradient(top, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    background: linear-gradient(to bottom, rgba(237, 237, 237, 1) 0, rgba(248, 248, 248, 1) 19%, rgba(253, 253, 253, 1) 35%, rgba(255, 255, 255, 1) 100%);
    border-left: none;
    border-right: 0
}

.title_blue {
    background: url(images/title_bg.gif) bottom center repeat-x;
    color: #2d7335;
    margin: -23px 3px 10px 2px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web';
    padding: 0 10px 4px;
    text-align: right;
    font-weight: normal;
    clear: both
}

.title_blue.title_blue2 {
    margin-top: 0
}

.title_blue span {
    font-size: 16px;
    color: #2d7335;
    font-weight: normal;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web'
}

.title_blue.daka span {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    display: block;
    font-size: 20px;
    color: #0044B0;
    margin: 3px 0 0 0;
    line-height: 29px;
    right: 32px
}

.dakaImg {
    height: 42px;
    width: 42px
}

.title_blue h1 {
    font-size: 16px;
    color: #2d7335;
    font-weight: normal;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web';
    margin: 28px 0 0 0
}

.title_blue span.date_cur {
    font-size: 16px;
    color: #073c62;
    font-weight: normal;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web'
}

.prev_link {
    background: url(images/arrow_gray.gif) left 6px no-repeat;
    font-size: 14px;
    color: #898989;
    padding-left: 10px;
    text-align: left;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web';
    float: left;
    display: inline-block;
    text-decoration: none;
    font-weight: normal
}

#fb-like-days-events {
    float: right;
    margin-right: 20px;
    margin-top: -6px
}

.table_cont_daka {
    margin: 0 3px 38px 2px
}

.table_cont_daka table {
    border-collapse: collapse;
    width: 100%;
    direction: rtl
}

.table_cont_daka table td {
    text-align: right;
    vertical-align: top;
    border-top: 1px solid #ebebeb;
    margin-bottom: 10px;
    padding: 10px 0
}

.table_cont_daka table td.tblRtColumn {
    padding-left: 30px;
    padding-right: 30px
}

.table_cont_daka table td.tblRtColumn .first {
    text-align: center;
    font: 13pt 'blender-bold', 'Mukta-ExtraBold', 'south web ultralight', Arial, Helvetica, sans-serif;
    padding-bottom: 14px
}

.table_cont_daka table td p {
    font: normal 14px arial;
    color: #000;
    line-height: 17px;
    padding: 0;
    margin: 0
}

.table_cont_daka table td.first {
    text-align: center;
    vertical-align: top;
    padding: 43px 0 22px;
    width: 66px;
    background: #c6e5f6;
    border-bottom: 2px solid #fff;
    font: normal 15px arial;
    color: #073c62;
    text-align: center;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light'
}

.table_cont_daka table td.flag {
    text-align: center;
    vertical-align: top;
    padding: 46px 0 22px;
    width: 69px;
    background: #e4f3fb;
    border-bottom: 2px solid #fff;
    text-align: center
}

.table_cont_daka table td.flag img {
    max-width: 30px
}

.table_cont_daka table .img_cont {
    width: 448px;
    height: 333px;
    position: relative;
    margin: 8px 19px 17px 0
}

.table_cont_daka table .img_cont img {
    width: 100%
}

.table_cont_daka table .img_cont .desc {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 100%;
    color: #fff;
    font-size: 12px;
    line-height: 15px;
    background: url(images/bg-desc.png)
}

.table_cont_daka table .img_cont .desc span {
    display: block;
    padding: 4px 14px 4px
}

.table_cont_daka table tr.last td {
    border-bottom: 1px solid #c9e6f7 !important
}

.table_cont_daka table iframe {
    margin: 0 auto;
    margin-top: -2px;
    display: block;
    max-width: 464px
}

.video-iframe {
    max-width: 100% !important
}

.article-list .img-holder .no_image {
    display: block;
    background: url(images/logo.png?Q=1) 30px 4px no-repeat;
    width: 125px;
    height: 84px;
    overflow: hidden;
    text-indent: -9999px;
    margin: 0 auto
}

.article-list .img-holder .event-arch-link img {
    height: 107px;
    width: 144px
}

#content .share_icon {
    position: absolute;
    display: inline-block;
    cursor: pointer;
    left: 48px
}

#content .share_bar ul {
    list-style: none;
    float: left
}

#content .share_bar ul li {
    padding: 5px 3px;
    width: auto;
    display: inline-block;
    cursor: pointer
}

#content .share_bar ul li img {
    height: 14px;
    width: auto
}

#main .share_bar ul {
    padding: 0
}

#main .share_bar ul li {
    list-style: none;
    display: inline-block;
    float: none;
    width: auto;
    padding: 0 2px;
    border: 0;
    cursor: pointer
}

.banner-holder .share_icon {
    display: inline-block;
    float: left
}

.section .figure .share_icon {
    margin-right: 2px;
    display: inline-block
}

.section .figure .share_icon img.icon.white {
    width: 11px;
    height: 11px;
    display: block !important
}

.section .figure .share_icon img.icon.gray {
    width: 11px;
    height: 11px;
    display: none !important
}

.section .figure .share_bar ul {
    margin: 0
}

.section .figure .share_bar ul img {
    margin: 0;
    display: inline
}

.section .figure span.writer {
    display: inline-block
}

.mainarticle-world.banner-holder .desc .main-article-writer,
.mainarticle-world.banner-holder .desc span {
    margin: -3px 0;
    display: inline-block;
    height: 20px
}

.mainarticle-world.banner-holder .desc .talkbacks,
.mainarticle-league.banner-holder .desc .talkbacks {
    display: inline-block;
    margin-left: 10px
}

.mainarticle-world.banner-holder .desc .talkbacksHomePage,
.mainarticle-league.banner-holder .desc .talkbacksHomePage {
    display: inline-block;
    margin-left: 10px
}

.mainarticle-league.banner-holder .desc .main-article-writer,
.mainarticle-league.banner-holder .desc span {
    margin: 0 0 0 10px;
    display: inline-block;
    float: none
}

.mainarticle-league.banner-holder .desc .share_icon {
    display: inline-block;
    float: left
}

.mainarticle-league.banner-holder .desc .share_icon li {
    display: inline-block
}

.mainarticle-league.banner-holder .desc .share_icon img.icon {
    width: 14px;
    height: 14px
}

.article-list .share_icon,
.content-block-article .share_icon,
.post-container .share_icon {
    display: inline-block;
    margin-top: 5px;
    float: left
}

.share_icon li:after {
    content: "";
    width: 14px;
    height: 14px;
    display: inline-block
}

/* .share_icon li.mail:after {
    background-image: url('../../../assets/share_email.png')
} */

/* .share_icon li.facebook:after {
    background-image: url('../../../assets/share_fb.png')
} */

/* .share_icon li.twitter:after {
    background-image: url('../../../assets/share_twitter.png')
} */

.content-block-article .share_icon li:after {
    width: 21px;
    height: 20px
}

/* .content-block-article .share_icon li.mail:after {
    background-image: url('../../../assets/share_email.png')
} */

/* .content-block-article .share_icon li.facebook:after {
    background-image: url('../../../assets/share_fb.png')
    width: 22px
} */

/* .content-block-article .share_icon li.twitter:after {
    background-image: url('../../../assets/share_twitter.png')
} */

/* .banner-holder .writer-talkback .share_icon li.mail:after,
.mainarticle-world.banner-holder .desc .share_icon li.mail:after {
    background-image: url('../../Images/share_email_w.png')
} */

/* .banner-holder .writer-talkback .share_icon li.facebook:after,
.mainarticle-world.banner-holder .desc .share_icon li.facebook:after {
    background-image: url('../../Images/share_fb_w.png')
} */

/* .banner-holder .writer-talkback .share_icon li.twitter:after,
.mainarticle-world.banner-holder .desc .share_icon li.twitter:after {
    background-image: url('../../Images/share_twitter_w.png')
} */

.banner-holder .writer-talkback .talkbacks-cont,
.mainarticle-world.banner-holder .desc .talkbacks-cont {
    width: 52px;
    margin-top: 3px
}

.banner-holder .writer-talkback .talkbacks-cont svg,
.mainarticle-world.banner-holder .desc .talkbacks-cont svg {
    fill: white
}

.date.team {
    display: inline-block
}

.schedule-block .title img.live-icon {
    float: right;
    margin-left: 10px
}

.pic-page.gallery .gallery-holder {
    position: relative;
    direction: ltr
}

.pic-page.gallery .gallery-images {
    width: 928px
}

.pic-page.gallery .gallery-images .fig-holder {
    width: 928px
}

.pic-page.gallery figure {
    display: inline-block;
    direction: rtl
}

.pic-page.gallery figure img {
    max-width: 912px
}

.pic-page.gallery .social-networks {
    z-index: 1
}

.pic-page.gallery .gallery-holder .gallery-button {
    position: absolute;
    top: 0;
    width: 64px;
    height: 100%;
    cursor: pointer
}

.pic-page.gallery .gallery-holder .gallery-button.prev {
    left: 10px;
    background: url('images/gallery-arrow-left.png') center center no-repeat
}

.pic-page.gallery .gallery-holder .gallery-button.next {
    right: 10px;
    background: url('images/gallery-arrow-right.png') center center no-repeat
}

#content>ul.social-networks {
    display: inline-block;
    margin: 0 0 18px;
    padding: 0
}

#content>ul.social-networks li {
    display: inline-block;
    list-style: none;
    padding: 0 10px 0 0
}

#content>ul.social-networks li:first-child {
    padding: 0
}

#content>ul.social-networks li:before {
    display: none
}

#content>ul.social-networks li.google iframe {
    margin-bottom: -4px !important
}

#content>.social-networks-wide li.google iframe {
    margin-top: 3px !important;
    margin-bottom: -4px !important
}

#content>ul.social-networks li.popup-holder .popup {
    left: -120px;
    bottom: 40px;
    top: auto;
    right: auto
}

#content>ul.social-networks li.popup-holder .popup:before {
    bottom: -9px;
    left: 134px;
    top: auto;
    right: auto;
    -moz-transform: rotate(-45deg);
    -webkit-transform: rotate(-45deg);
    transform: rotate(-45deg);
    box-shadow: -1px 5px 2px #b6b8b8
}

.sidebar_div .tlkb-days iframe {
    width: 305px !important
}

.video-holder .skin-header {
    display: none
}

.video-holder .skin-footer {
    display: block;
    height: 40px !important;
    margin-top: -7px;
    background: url(images/SamsungBannerNeo.png) no-repeat;
    background-size: 100%
}

.day-events .skin-footer {
    display: block;
    height: 35px !important;
    background: url(images/SamsungBannerNeo.png) no-repeat;
    background-size: cover;
    width: 466px;
    margin: 0 auto;
    margin-top: -1px
}

.twitter-cont a:hover {
    text-decoration: underline
}

.twitter-cont .twitter-title {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    color: #00ACEC;
    font-size: 16px;
    padding-right: -120px;
    text-decoration: none !important;
    width: 250px;
    position: absolute;
    right: 20px;
    margin-top: -32px;
    height: 32px;
    background: #F3F3F3;
    padding-right: 20px;
    padding-top: 7px
}

.twitter-cont .twitter-sport {
    direction: ltr;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web';
    color: #00ACEC;
    font-size: 15px
}

#div-popunder {
    display: none
}

.win-data .stage-cube {
    position: absolute;
    left: 0;
    top: 0;
    width: 145px;
    height: 72px;
    font-size: 10px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light'
}

.win-data .stage-cube .top-title,
.win-data .stage-cube .bottom-title {
    height: 10px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light';
    line-height: 10px;
    color: #000
}

.win-data .stage-cube .top-title .top_title {
    float: right;
    color: #000
}

.win-data .stage-cube .top-title img {
    float: left;
    margin-right: 8px;
    position: relative;
    bottom: 2px
}

.win-data .stage-cube .top-title .ltr-date {
    float: left
}

.win-data .stage-cube table {
    width: 144px;
    height: 50px;
    font-size: 15px;
    text-align: center;
    color: #929292;
    border-collapse: collapse;
    border-spacing: 2px;
    line-height: 17px
}

.win-data .stage-cube table tr td {
    padding: 0
}

.win-data .stage-cube table td.team-name {
    width: 64%
}

.win-data .stage-cube table td.team-img,
.win-data .stage-cube table td.team-score {
    width: 16%
}

.win-data .stage-cube table td.team-img {
    height: 27px
}

.win-data .stage-cube table td.team-img img {
    padding-top: 3px;
    width: 18px;
    height: 18px
}

.win-data .stage-cube .bottom-title .stadium {
    float: left;
    margin-left: 4px
}

.win-data .stage-cube .bottom-title .buttom_title {
    float: right
}

.win-data .stage-cube.to-left {
    direction: ltr
}

.win-data .stage-cube.to-left .bottom-title .stadium {
    float: right;
    margin: 0
}

.win-data .stage-cube.to-left .bottom-title .buttom_title {
    float: left;
    margin-left: 4px
}

.win-data .stage-cube.to-left .top-title .top_title {
    float: left
}

.win-data .stage-cube.to-left .top-title img {
    float: right;
    margin-left: 8px;
    margin-right: 0
}

.win-data .stage-cube.to-left .top-title .ltr-date {
    float: right
}

.win-data .stage-cube .div-final {
    position: absolute;
    top: -70px;
    color: #5B5B5B
}

.win-data .stage-cube .div-final .final-inner {
    font-size: 20px;
    font-weight: bold
}

.win-data .stage-cube .div-final .final-date {
    font-size: 15px
}

.win-data .stage-cube .div-final .final-stad {
    font-size: 12px;
    color: #000
}

/*! jQuery UI - v1.10.3 - 2013-05-03
* http://jqueryui.com
* Includes: jquery.ui.core.css, jquery.ui.accordion.css, jquery.ui.autocomplete.css, jquery.ui.button.css, jquery.ui.datepicker.css, jquery.ui.dialog.css, jquery.ui.menu.css, jquery.ui.progressbar.css, jquery.ui.resizable.css, jquery.ui.selectable.css, jquery.ui.slider.css, jquery.ui.spinner.css, jquery.ui.tabs.css, jquery.ui.tooltip.css
* To view and modify this theme, visit http://jqueryui.com/themeroller/?ffDefault=Lucida%20Grande%2CLucida%20Sans%2CArial%2Csans-serif&fwDefault=bold&fsDefault=1.1em&cornerRadius=6px&bgColorHeader=deedf7&bgTextureHeader=highlight_soft&bgImgOpacityHeader=100&borderColorHeader=aed0ea&fcHeader=222222&iconColorHeader=72a7cf&bgColorContent=f2f5f7&bgTextureContent=highlight_hard&bgImgOpacityContent=100&borderColorContent=dddddd&fcContent=362b36&iconColorContent=72a7cf&bgColorDefault=d7ebf9&bgTextureDefault=glass&bgImgOpacityDefault=80&borderColorDefault=aed0ea&fcDefault=2779aa&iconColorDefault=3d80b3&bgColorHover=e4f1fb&bgTextureHover=glass&bgImgOpacityHover=100&borderColorHover=74b2e2&fcHover=0070a3&iconColorHover=2694e8&bgColorActive=3baae3&bgTextureActive=glass&bgImgOpacityActive=50&borderColorActive=2694e8&fcActive=ffffff&iconColorActive=ffffff&bgColorHighlight=ffef8f&bgTextureHighlight=highlight_soft&bgImgOpacityHighlight=25&borderColorHighlight=f9dd34&fcHighlight=363636&iconColorHighlight=2e83ff&bgColorError=cd0a0a&bgTextureError=flat&bgImgOpacityError=15&borderColorError=cd0a0a&fcError=ffffff&iconColorError=ffffff&bgColorOverlay=eeeeee&bgTextureOverlay=diagonals_thick&bgImgOpacityOverlay=90&opacityOverlay=80&bgColorShadow=000000&bgTextureShadow=highlight_hard&bgImgOpacityShadow=70&opacityShadow=30&thicknessShadow=7px&offsetTopShadow=-7px&offsetLeftShadow=-7px&cornerRadiusShadow=8px
* Copyright 2013 jQuery Foundation and other contributors Licensed MIT */
.ui-helper-hidden {
    display: none
}

.ui-helper-hidden-accessible {
    border: 0;
    clip: rect(0 0 0 0);
    height: 1px;
    margin: -1px;
    overflow: hidden;
    padding: 0;
    position: absolute;
    width: 1px
}

.ui-helper-reset {
    margin: 0;
    padding: 0;
    border: 0;
    outline: 0;
    line-height: 1.3;
    text-decoration: none;
    font-size: 100%;
    list-style: none
}

.ui-helper-clearfix:before,
.ui-helper-clearfix:after {
    content: "";
    display: table;
    border-collapse: collapse
}

.ui-helper-clearfix:after {
    clear: both
}

.ui-helper-clearfix {
    min-height: 0
}

.ui-helper-zfix {
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    position: absolute;
    opacity: 0;
    filter: Alpha(Opacity=0)
}

.ui-front {
    z-index: 100
}

.ui-state-disabled {
    cursor: default !important
}

.ui-icon {
    display: block;
    text-indent: -99999px;
    overflow: hidden;
    background-repeat: no-repeat
}

.ui-widget-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%
}

.ui-accordion .ui-accordion-header {
    display: block;
    cursor: pointer;
    position: relative;
    margin-top: 2px;
    padding: .5em .5em .5em .7em;
    min-height: 0
}

.ui-accordion .ui-accordion-icons {
    padding-left: 2.2em
}

.ui-accordion .ui-accordion-noicons {
    padding-left: .7em
}

.ui-accordion .ui-accordion-icons .ui-accordion-icons {
    padding-left: 2.2em
}

.ui-accordion .ui-accordion-header .ui-accordion-header-icon {
    position: absolute;
    left: .5em;
    top: 50%;
    margin-top: -8px
}

.ui-accordion .ui-accordion-content {
    padding: 1em 2.2em;
    border-top: 0;
    overflow: auto
}

.ui-autocomplete {
    position: absolute;
    top: 0;
    left: 0;
    cursor: default
}

.ui-button {
    display: inline-block;
    position: relative;
    padding: 0;
    line-height: normal;
    margin-right: .1em;
    cursor: pointer;
    vertical-align: middle;
    text-align: center;
    overflow: visible
}

.ui-button,
.ui-button:link,
.ui-button:visited,
.ui-button:hover,
.ui-button:active {
    text-decoration: none
}

.ui-button-icon-only {
    width: 2.2em
}

button.ui-button-icon-only {
    width: 2.4em
}

.ui-button-icons-only {
    width: 3.4em
}

button.ui-button-icons-only {
    width: 3.7em
}

.ui-button .ui-button-text {
    display: block;
    line-height: normal
}

.ui-button-text-only .ui-button-text {
    padding: .4em 1em
}

.ui-button-icon-only .ui-button-text,
.ui-button-icons-only .ui-button-text {
    padding: .4em;
    text-indent: -9999999px
}

.ui-button-text-icon-primary .ui-button-text,
.ui-button-text-icons .ui-button-text {
    padding: .4em 1em .4em 2.1em
}

.ui-button-text-icon-secondary .ui-button-text,
.ui-button-text-icons .ui-button-text {
    padding: .4em 2.1em .4em 1em
}

.ui-button-text-icons .ui-button-text {
    padding-left: 2.1em;
    padding-right: 2.1em
}

input.ui-button {
    padding: .4em 1em
}

.ui-button-icon-only .ui-icon,
.ui-button-text-icon-primary .ui-icon,
.ui-button-text-icon-secondary .ui-icon,
.ui-button-text-icons .ui-icon,
.ui-button-icons-only .ui-icon {
    position: absolute;
    top: 50%;
    margin-top: -8px
}

.ui-button-icon-only .ui-icon {
    left: 50%;
    margin-left: -8px
}

.ui-button-text-icon-primary .ui-button-icon-primary,
.ui-button-text-icons .ui-button-icon-primary,
.ui-button-icons-only .ui-button-icon-primary {
    left: .5em
}

.ui-button-text-icon-secondary .ui-button-icon-secondary,
.ui-button-text-icons .ui-button-icon-secondary,
.ui-button-icons-only .ui-button-icon-secondary {
    right: .5em
}

.ui-buttonset {
    margin-right: 7px
}

.ui-buttonset .ui-button {
    margin-left: 0;
    margin-right: -.3em
}

input.ui-button::-moz-focus-inner,
button.ui-button::-moz-focus-inner {
    border: 0;
    padding: 0
}

.ui-datepicker {
    width: 17em;
    padding: .2em .2em 0;
    display: none
}

.ui-datepicker .ui-datepicker-header {
    position: relative;
    padding: .2em 0
}

.ui-datepicker .ui-datepicker-prev,
.ui-datepicker .ui-datepicker-next {
    position: absolute;
    top: 2px;
    width: 1.8em;
    height: 1.8em
}

.ui-datepicker .ui-datepicker-prev-hover,
.ui-datepicker .ui-datepicker-next-hover {
    top: 1px
}

.ui-datepicker .ui-datepicker-prev {
    left: 2px
}

.ui-datepicker .ui-datepicker-next {
    right: 2px
}

.ui-datepicker .ui-datepicker-prev-hover {
    left: 1px
}

.ui-datepicker .ui-datepicker-next-hover {
    right: 1px
}

.ui-datepicker .ui-datepicker-prev span,
.ui-datepicker .ui-datepicker-next span {
    display: block;
    position: absolute;
    left: 50%;
    margin-left: -8px;
    top: 50%;
    margin-top: -8px
}

.ui-datepicker .ui-datepicker-title {
    margin: 0 2.3em;
    line-height: 1.8em;
    text-align: center
}

.ui-datepicker .ui-datepicker-title select {
    font-size: 1em;
    margin: 1px 0
}

.ui-datepicker select.ui-datepicker-month-year {
    width: 100%
}

.ui-datepicker select.ui-datepicker-month,
.ui-datepicker select.ui-datepicker-year {
    width: 49%
}

.ui-datepicker table {
    width: 100%;
    font-size: .9em;
    border-collapse: collapse;
    margin: 0 0 .4em
}

.ui-datepicker th {
    padding: .7em .3em;
    text-align: center;
    font-weight: 700;
    border: 0
}

.ui-datepicker td {
    border: 0;
    padding: 1px
}

.ui-datepicker td span,
.ui-datepicker td a {
    display: block;
    padding: .2em;
    text-align: right;
    text-decoration: none
}

.ui-datepicker .ui-datepicker-buttonpane {
    background-image: none;
    margin: .7em 0 0;
    padding: 0 .2em;
    border-left: 0;
    border-right: 0;
    border-bottom: 0
}

.ui-datepicker .ui-datepicker-buttonpane button {
    float: right;
    margin: .5em .2em .4em;
    cursor: pointer;
    padding: .2em .6em .3em;
    width: auto;
    overflow: visible
}

.ui-datepicker .ui-datepicker-buttonpane button.ui-datepicker-current {
    float: left
}

.ui-datepicker.ui-datepicker-multi {
    width: auto
}

.ui-datepicker-multi .ui-datepicker-group {
    float: left
}

.ui-datepicker-multi .ui-datepicker-group table {
    width: 95%;
    margin: 0 auto .4em
}

.ui-datepicker-multi-2 .ui-datepicker-group {
    width: 50%
}

.ui-datepicker-multi-3 .ui-datepicker-group {
    width: 33.3%
}

.ui-datepicker-multi-4 .ui-datepicker-group {
    width: 25%
}

.ui-datepicker-multi .ui-datepicker-group-last .ui-datepicker-header,
.ui-datepicker-multi .ui-datepicker-group-middle .ui-datepicker-header {
    border-left-width: 0
}

.ui-datepicker-multi .ui-datepicker-buttonpane {
    clear: left
}

.ui-datepicker-row-break {
    clear: both;
    width: 100%;
    font-size: 0
}

.ui-datepicker-rtl {
    direction: rtl
}

.ui-datepicker-rtl .ui-datepicker-prev {
    right: 2px;
    left: auto
}

.ui-datepicker-rtl .ui-datepicker-next {
    left: 2px;
    right: auto
}

.ui-datepicker-rtl .ui-datepicker-prev:hover {
    right: 1px;
    left: auto
}

.ui-datepicker-rtl .ui-datepicker-next:hover {
    left: 1px;
    right: auto
}

.ui-datepicker-rtl .ui-datepicker-buttonpane {
    clear: right
}

.ui-datepicker-rtl .ui-datepicker-buttonpane button {
    float: left
}

.ui-datepicker-rtl .ui-datepicker-buttonpane button.ui-datepicker-current,
.ui-datepicker-rtl .ui-datepicker-group {
    float: right
}

.ui-datepicker-rtl .ui-datepicker-group-last .ui-datepicker-header,
.ui-datepicker-rtl .ui-datepicker-group-middle .ui-datepicker-header {
    border-right-width: 0;
    border-left-width: 1px
}

.ui-dialog {
    position: absolute;
    top: 0;
    left: 0;
    padding: .2em;
    outline: 0
}

.ui-dialog .ui-dialog-titlebar {
    padding: .4em 1em;
    position: relative;
    direction: rtl
}

.ui-dialog .ui-dialog-title {
    line-height: 16px;
    float: left;
    margin: 3px 0 0 0;
    white-space: nowrap;
    width: 90%;
    overflow: hidden;
    text-overflow: ellipsis;
    font-size: 14px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold'
}

.ui-dialog .ui-dialog-titlebar-close {
    position: absolute;
    right: .3em;
    top: 50%;
    width: 21px;
    margin: -10px 0 0 0;
    padding: 1px;
    height: 20px
}

.ui-dialog .ui-dialog-content {
    position: relative;
    border: 0;
    padding: .5em 1em;
    background: 0;
    overflow: auto
}

.ui-dialog .ui-dialog-buttonpane {
    text-align: left;
    border-width: 1px 0 0;
    background-image: none;
    margin-top: .5em;
    padding: .3em 1em .5em .4em
}

.ui-dialog .ui-dialog-buttonpane .ui-dialog-buttonset {
    float: left
}

.ui-dialog .ui-dialog-buttonpane button {
    margin: .5em .4em .5em 0;
    cursor: pointer
}

.ui-dialog .ui-resizable-se {
    width: 12px;
    height: 12px;
    right: -5px;
    bottom: -5px;
    background-position: 16px 16px
}

.ui-draggable .ui-dialog-titlebar {
    cursor: move
}

.ui-menu {
    list-style: none;
    padding: 2px;
    margin: 0;
    display: block;
    outline: 0
}

.ui-menu .ui-menu {
    margin-top: -3px;
    position: absolute
}

.ui-menu .ui-menu-item {
    margin: 0;
    padding: 0;
    width: 100%;
    list-style-image: url(data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7)
}

.ui-menu .ui-menu-divider {
    margin: 5px -2px 5px -2px;
    height: 0;
    font-size: 0;
    line-height: 0;
    border-width: 1px 0 0
}

.ui-menu .ui-menu-item a {
    text-decoration: none;
    display: block;
    padding: 2px .4em;
    line-height: 1.5;
    min-height: 0;
    font-weight: 400
}

.ui-menu .ui-menu-item a.ui-state-focus,
.ui-menu .ui-menu-item a.ui-state-active {
    font-weight: 400;
    margin: -1px
}

.ui-menu .ui-state-disabled {
    font-weight: 400;
    margin: .4em 0 .2em;
    line-height: 1.5
}

.ui-menu .ui-state-disabled a {
    cursor: default
}

.ui-menu-icons {
    position: relative
}

.ui-menu-icons .ui-menu-item a {
    position: relative;
    padding-left: 2em
}

.ui-menu .ui-icon {
    position: absolute;
    top: .2em;
    left: .2em
}

.ui-menu .ui-menu-icon {
    position: static;
    float: right
}

.ui-progressbar {
    height: 2em;
    text-align: left;
    overflow: hidden
}

.ui-progressbar .ui-progressbar-value {
    margin: -1px;
    height: 100%
}

.ui-progressbar .ui-progressbar-overlay {
    background: url(images/jquery.ui/animated-overlay.gif);
    height: 100%;
    filter: alpha(opacity=25);
    opacity: .25
}

.ui-progressbar-indeterminate .ui-progressbar-value {
    background-image: none
}

.ui-resizable {
    position: relative
}

.ui-resizable-handle {
    position: absolute;
    font-size: .1px;
    display: block
}

.ui-resizable-disabled .ui-resizable-handle,
.ui-resizable-autohide .ui-resizable-handle {
    display: none
}

.ui-resizable-n {
    cursor: n-resize;
    height: 7px;
    width: 100%;
    top: -5px;
    left: 0
}

.ui-resizable-s {
    cursor: s-resize;
    height: 7px;
    width: 100%;
    bottom: -5px;
    left: 0
}

.ui-resizable-e {
    cursor: e-resize;
    width: 7px;
    right: -5px;
    top: 0;
    height: 100%
}

.ui-resizable-w {
    cursor: w-resize;
    width: 7px;
    left: -5px;
    top: 0;
    height: 100%
}

.ui-resizable-se {
    cursor: se-resize;
    width: 12px;
    height: 12px;
    right: 1px;
    bottom: 1px
}

.ui-resizable-sw {
    cursor: sw-resize;
    width: 9px;
    height: 9px;
    left: -5px;
    bottom: -5px
}

.ui-resizable-nw {
    cursor: nw-resize;
    width: 9px;
    height: 9px;
    left: -5px;
    top: -5px
}

.ui-resizable-ne {
    cursor: ne-resize;
    width: 9px;
    height: 9px;
    right: -5px;
    top: -5px
}

.ui-selectable-helper {
    position: absolute;
    z-index: 100;
    border: 1px dotted #000
}

.ui-slider {
    position: relative;
    text-align: left
}

.ui-slider .ui-slider-handle {
    position: absolute;
    z-index: 2;
    width: 1.2em;
    height: 1.2em;
    cursor: default
}

.ui-slider .ui-slider-range {
    position: absolute;
    z-index: 1;
    font-size: .7em;
    display: block;
    border: 0;
    background-position: 0 0
}

.ui-slider.ui-state-disabled .ui-slider-handle,
.ui-slider.ui-state-disabled .ui-slider-range {
    filter: inherit
}

.ui-slider-horizontal {
    height: .8em
}

.ui-slider-horizontal .ui-slider-handle {
    top: -.3em;
    margin-left: -.6em
}

.ui-slider-horizontal .ui-slider-range {
    top: 0;
    height: 100%
}

.ui-slider-horizontal .ui-slider-range-min {
    left: 0
}

.ui-slider-horizontal .ui-slider-range-max {
    right: 0
}

.ui-slider-vertical {
    width: .8em;
    height: 100px
}

.ui-slider-vertical .ui-slider-handle {
    left: -.3em;
    margin-left: 0;
    margin-bottom: -.6em
}

.ui-slider-vertical .ui-slider-range {
    left: 0;
    width: 100%
}

.ui-slider-vertical .ui-slider-range-min {
    bottom: 0
}

.ui-slider-vertical .ui-slider-range-max {
    top: 0
}

.ui-spinner {
    position: relative;
    display: inline-block;
    overflow: hidden;
    padding: 0;
    vertical-align: middle
}

.ui-spinner-input {
    border: 0;
    background: 0;
    color: inherit;
    padding: 0;
    margin: .2em 0;
    vertical-align: middle;
    margin-left: .4em;
    margin-right: 22px
}

.ui-spinner-button {
    width: 16px;
    height: 50%;
    font-size: .5em;
    padding: 0;
    margin: 0;
    text-align: center;
    position: absolute;
    cursor: default;
    display: block;
    overflow: hidden;
    right: 0
}

.ui-spinner a.ui-spinner-button {
    border-top: 0;
    border-bottom: 0;
    border-right: 0
}

.ui-spinner .ui-icon {
    position: absolute;
    margin-top: -8px;
    top: 50%;
    left: 0
}

.ui-spinner-up {
    top: 0
}

.ui-spinner-down {
    bottom: 0
}

.ui-spinner .ui-icon-triangle-1-s {
    background-position: -65px -16px
}

.ui-tabs {
    position: relative;
    padding: .2em
}

.ui-tabs .ui-tabs-nav {
    margin: 0;
    padding: .2em .2em 0
}

.ui-tabs .ui-tabs-nav li {
    list-style: none;
    float: left;
    position: relative;
    top: 0;
    margin: 1px .2em 0 0;
    border-bottom-width: 0;
    padding: 0;
    white-space: nowrap
}

.ui-tabs .ui-tabs-nav li a {
    float: left;
    padding: .5em 1em;
    text-decoration: none
}

.ui-tabs .ui-tabs-nav li.ui-tabs-active {
    margin-bottom: -1px;
    padding-bottom: 1px
}

.ui-tabs .ui-tabs-nav li.ui-tabs-active a,
.ui-tabs .ui-tabs-nav li.ui-state-disabled a,
.ui-tabs .ui-tabs-nav li.ui-tabs-loading a {
    cursor: text
}

.ui-tabs .ui-tabs-nav li a,
.ui-tabs-collapsible .ui-tabs-nav li.ui-tabs-active a {
    cursor: pointer
}

.ui-tabs .ui-tabs-panel {
    display: block;
    border-width: 0;
    padding: 1em 1.4em;
    background: 0
}

.ui-tooltip {
    padding: 8px;
    position: absolute;
    z-index: 9999;
    max-width: 300px;
    -webkit-box-shadow: 0 0 5px #aaa;
    box-shadow: 0 0 5px #aaa
}

body .ui-tooltip {
    border-width: 2px
}

.ui-widget {
    font-family: Arial, sans-serif;
    font-size: 14px
}

.ui-widget .ui-widget {
    font-size: 1em
}

.ui-widget input,
.ui-widget select,
.ui-widget textarea,
.ui-widget button {
    font-family: Lucida Grande, Lucida Sans, Arial, sans-serif;
    font-size: 1em
}

.ui-widget-content {
    text-align: right;
    border: 1px solid #ddd;
    background: #f2f5f7 url(images/jquery.ui/ui-bg_highlight-hard_100_f2f5f7_1x100.png) 50% top repeat-x;
    color: #362b36
}

.ui-widget-content a {
    color: #362b36
}

.ui-widget-header {
    border: 1px solid #aed0ea;
    background: #deedf7 url(images/jquery.ui/ui-bg_highlight-soft_100_deedf7_1x100.png) 50% 50% repeat-x;
    color: #222;
    font-weight: bold
}

.ui-widget-header a {
    color: #222
}

.ui-state-default,
.ui-widget-content .ui-state-default,
.ui-widget-header .ui-state-default {
    border: 1px solid #aed0ea;
    background: #d7ebf9 url(images/jquery.ui/ui-bg_glass_80_d7ebf9_1x400.png) 50% 50% repeat-x;
    font-weight: bold;
    color: #2779aa
}

.ui-state-default a,
.ui-state-default a:link,
.ui-state-default a:visited {
    color: #2779aa;
    text-decoration: none
}

.ui-state-hover,
.ui-widget-content .ui-state-hover,
.ui-widget-header .ui-state-hover,
.ui-state-focus,
.ui-widget-content .ui-state-focus,
.ui-widget-header .ui-state-focus {
    border: 1px solid #74b2e2;
    background: #e4f1fb url(images/jquery.ui/ui-bg_glass_100_e4f1fb_1x400.png) 50% 50% repeat-x;
    font-weight: bold;
    color: #0070a3
}

.ui-state-hover a,
.ui-state-hover a:hover,
.ui-state-hover a:link,
.ui-state-hover a:visited {
    color: #0070a3;
    text-decoration: none
}

.ui-state-active,
.ui-widget-content .ui-state-active,
.ui-widget-header .ui-state-active {
    border: 1px solid #2694e8;
    background: #3baae3 url(images/jquery.ui/ui-bg_glass_50_3baae3_1x400.png) 50% 50% repeat-x;
    font-weight: bold;
    color: #fff
}

.ui-state-active a,
.ui-state-active a:link,
.ui-state-active a:visited {
    color: #fff;
    text-decoration: none
}

.ui-state-highlight,
.ui-widget-content .ui-state-highlight,
.ui-widget-header .ui-state-highlight {
    border: 1px solid #f9dd34;
    background: #ffef8f url(images/jquery.ui/ui-bg_highlight-soft_25_ffef8f_1x100.png) 50% top repeat-x;
    color: #363636
}

.ui-state-highlight a,
.ui-widget-content .ui-state-highlight a,
.ui-widget-header .ui-state-highlight a {
    color: #363636
}

.ui-state-error,
.ui-widget-content .ui-state-error,
.ui-widget-header .ui-state-error {
    border: 1px solid #cd0a0a;
    background: #cd0a0a url(images/jquery.ui/ui-bg_flat_15_cd0a0a_40x100.png) 50% 50% repeat-x;
    color: #fff
}

.ui-state-error a,
.ui-widget-content .ui-state-error a,
.ui-widget-header .ui-state-error a {
    color: #fff
}

.ui-state-error-text,
.ui-widget-content .ui-state-error-text,
.ui-widget-header .ui-state-error-text {
    color: #fff
}

.ui-priority-primary,
.ui-widget-content .ui-priority-primary,
.ui-widget-header .ui-priority-primary {
    font-weight: bold
}

.ui-priority-secondary,
.ui-widget-content .ui-priority-secondary,
.ui-widget-header .ui-priority-secondary {
    opacity: .7;
    filter: Alpha(Opacity=70);
    font-weight: normal
}

.ui-state-disabled,
.ui-widget-content .ui-state-disabled,
.ui-widget-header .ui-state-disabled {
    opacity: .35;
    filter: Alpha(Opacity=35);
    background-image: none
}

.ui-state-disabled .ui-icon {
    filter: Alpha(Opacity=35)
}

.ui-icon {
    width: 16px;
    height: 16px
}

.ui-icon,
.ui-widget-content .ui-icon {
    background-image: url(images/jquery.ui/ui-icons_72a7cf_256x240.png)
}

.ui-widget-header .ui-icon {
    background-image: url(images/jquery.ui/ui-icons_72a7cf_256x240.png)
}

.ui-state-default .ui-icon {
    background-image: url(images/jquery.ui/ui-icons_3d80b3_256x240.png)
}

.ui-state-hover .ui-icon,
.ui-state-focus .ui-icon {
    background-image: url(images/jquery.ui/ui-icons_2694e8_256x240.png)
}

.ui-state-active .ui-icon {
    background-image: url(images/jquery.ui/ui-icons_ffffff_256x240.png)
}

.ui-state-highlight .ui-icon {
    background-image: url(images/jquery.ui/ui-icons_2e83ff_256x240.png)
}

.ui-state-error .ui-icon,
.ui-state-error-text .ui-icon {
    background-image: url(images/jquery.ui/ui-icons_ffffff_256x240.png)
}

.ui-icon-blank {
    background-position: 16px 16px
}

.ui-icon-carat-1-n {
    background-position: 0 0
}

.ui-icon-carat-1-ne {
    background-position: -16px 0
}

.ui-icon-carat-1-e {
    background-position: -32px 0
}

.ui-icon-carat-1-se {
    background-position: -48px 0
}

.ui-icon-carat-1-s {
    background-position: -64px 0
}

.ui-icon-carat-1-sw {
    background-position: -80px 0
}

.ui-icon-carat-1-w {
    background-position: -96px 0
}

.ui-icon-carat-1-nw {
    background-position: -112px 0
}

.ui-icon-carat-2-n-s {
    background-position: -128px 0
}

.ui-icon-carat-2-e-w {
    background-position: -144px 0
}

.ui-icon-triangle-1-n {
    background-position: 0 -16px
}

.ui-icon-triangle-1-ne {
    background-position: -16px -16px
}

.ui-icon-triangle-1-e {
    background-position: -32px -16px
}

.ui-icon-triangle-1-se {
    background-position: -48px -16px
}

.ui-icon-triangle-1-s {
    background-position: -64px -16px
}

.ui-icon-triangle-1-sw {
    background-position: -80px -16px
}

.ui-icon-triangle-1-w {
    background-position: -96px -16px
}

.ui-icon-triangle-1-nw {
    background-position: -112px -16px
}

.ui-icon-triangle-2-n-s {
    background-position: -128px -16px
}

.ui-icon-triangle-2-e-w {
    background-position: -144px -16px
}

.ui-icon-arrow-1-n {
    background-position: 0 -32px
}

.ui-icon-arrow-1-ne {
    background-position: -16px -32px
}

.ui-icon-arrow-1-e {
    background-position: -32px -32px
}

.ui-icon-arrow-1-se {
    background-position: -48px -32px
}

.ui-icon-arrow-1-s {
    background-position: -64px -32px
}

.ui-icon-arrow-1-sw {
    background-position: -80px -32px
}

.ui-icon-arrow-1-w {
    background-position: -96px -32px
}

.ui-icon-arrow-1-nw {
    background-position: -112px -32px
}

.ui-icon-arrow-2-n-s {
    background-position: -128px -32px
}

.ui-icon-arrow-2-ne-sw {
    background-position: -144px -32px
}

.ui-icon-arrow-2-e-w {
    background-position: -160px -32px
}

.ui-icon-arrow-2-se-nw {
    background-position: -176px -32px
}

.ui-icon-arrowstop-1-n {
    background-position: -192px -32px
}

.ui-icon-arrowstop-1-e {
    background-position: -208px -32px
}

.ui-icon-arrowstop-1-s {
    background-position: -224px -32px
}

.ui-icon-arrowstop-1-w {
    background-position: -240px -32px
}

.ui-icon-arrowthick-1-n {
    background-position: 0 -48px
}

.ui-icon-arrowthick-1-ne {
    background-position: -16px -48px
}

.ui-icon-arrowthick-1-e {
    background-position: -32px -48px
}

.ui-icon-arrowthick-1-se {
    background-position: -48px -48px
}

.ui-icon-arrowthick-1-s {
    background-position: -64px -48px
}

.ui-icon-arrowthick-1-sw {
    background-position: -80px -48px
}

.ui-icon-arrowthick-1-w {
    background-position: -96px -48px
}

.ui-icon-arrowthick-1-nw {
    background-position: -112px -48px
}

.ui-icon-arrowthick-2-n-s {
    background-position: -128px -48px
}

.ui-icon-arrowthick-2-ne-sw {
    background-position: -144px -48px
}

.ui-icon-arrowthick-2-e-w {
    background-position: -160px -48px
}

.ui-icon-arrowthick-2-se-nw {
    background-position: -176px -48px
}

.ui-icon-arrowthickstop-1-n {
    background-position: -192px -48px
}

.ui-icon-arrowthickstop-1-e {
    background-position: -208px -48px
}

.ui-icon-arrowthickstop-1-s {
    background-position: -224px -48px
}

.ui-icon-arrowthickstop-1-w {
    background-position: -240px -48px
}

.ui-icon-arrowreturnthick-1-w {
    background-position: 0 -64px
}

.ui-icon-arrowreturnthick-1-n {
    background-position: -16px -64px
}

.ui-icon-arrowreturnthick-1-e {
    background-position: -32px -64px
}

.ui-icon-arrowreturnthick-1-s {
    background-position: -48px -64px
}

.ui-icon-arrowreturn-1-w {
    background-position: -64px -64px
}

.ui-icon-arrowreturn-1-n {
    background-position: -80px -64px
}

.ui-icon-arrowreturn-1-e {
    background-position: -96px -64px
}

.ui-icon-arrowreturn-1-s {
    background-position: -112px -64px
}

.ui-icon-arrowrefresh-1-w {
    background-position: -128px -64px
}

.ui-icon-arrowrefresh-1-n {
    background-position: -144px -64px
}

.ui-icon-arrowrefresh-1-e {
    background-position: -160px -64px
}

.ui-icon-arrowrefresh-1-s {
    background-position: -176px -64px
}

.ui-icon-arrow-4 {
    background-position: 0 -80px
}

.ui-icon-arrow-4-diag {
    background-position: -16px -80px
}

.ui-icon-extlink {
    background-position: -32px -80px
}

.ui-icon-newwin {
    background-position: -48px -80px
}

.ui-icon-refresh {
    background-position: -64px -80px
}

.ui-icon-shuffle {
    background-position: -80px -80px
}

.ui-icon-transfer-e-w {
    background-position: -96px -80px
}

.ui-icon-transferthick-e-w {
    background-position: -112px -80px
}

.ui-icon-folder-collapsed {
    background-position: 0 -96px
}

.ui-icon-folder-open {
    background-position: -16px -96px
}

.ui-icon-document {
    background-position: -32px -96px
}

.ui-icon-document-b {
    background-position: -48px -96px
}

.ui-icon-note {
    background-position: -64px -96px
}

.ui-icon-mail-closed {
    background-position: -80px -96px
}

.ui-icon-mail-open {
    background-position: -96px -96px
}

.ui-icon-suitcase {
    background-position: -112px -96px
}

.ui-icon-comment {
    background-position: -128px -96px
}

.ui-icon-person {
    background-position: -144px -96px
}

.ui-icon-print {
    background-position: -160px -96px
}

.ui-icon-trash {
    background-position: -176px -96px
}

.ui-icon-locked {
    background-position: -192px -96px
}

.ui-icon-unlocked {
    background-position: -208px -96px
}

.ui-icon-bookmark {
    background-position: -224px -96px
}

.ui-icon-tag {
    background-position: -240px -96px
}

.ui-icon-home {
    background-position: 0 -112px
}

.ui-icon-flag {
    background-position: -16px -112px
}

.ui-icon-calendar {
    background-position: -32px -112px
}

.ui-icon-cart {
    background-position: -48px -112px
}

.ui-icon-pencil {
    background-position: -64px -112px
}

.ui-icon-clock {
    background-position: -80px -112px
}

.ui-icon-disk {
    background-position: -96px -112px
}

.ui-icon-calculator {
    background-position: -112px -112px
}

.ui-icon-zoomin {
    background-position: -128px -112px
}

.ui-icon-zoomout {
    background-position: -144px -112px
}

.ui-icon-search {
    background-position: -160px -112px
}

.ui-icon-wrench {
    background-position: -176px -112px
}

.ui-icon-gear {
    background-position: -192px -112px
}

.ui-icon-heart {
    background-position: -208px -112px
}

.ui-icon-star {
    background-position: -224px -112px
}

.ui-icon-link {
    background-position: -240px -112px
}

.ui-icon-cancel {
    background-position: 0 -128px
}

.ui-icon-plus {
    background-position: -16px -128px
}

.ui-icon-plusthick {
    background-position: -32px -128px
}

.ui-icon-minus {
    background-position: -48px -128px
}

.ui-icon-minusthick {
    background-position: -64px -128px
}

.ui-icon-close {
    background-position: -80px -128px
}

.ui-icon-closethick {
    background-position: -96px -128px
}

.ui-icon-key {
    background-position: -112px -128px
}

.ui-icon-lightbulb {
    background-position: -128px -128px
}

.ui-icon-scissors {
    background-position: -144px -128px
}

.ui-icon-clipboard {
    background-position: -160px -128px
}

.ui-icon-copy {
    background-position: -176px -128px
}

.ui-icon-contact {
    background-position: -192px -128px
}

.ui-icon-image {
    background-position: -208px -128px
}

.ui-icon-video {
    background-position: -224px -128px
}

.ui-icon-script {
    background-position: -240px -128px
}

.ui-icon-alert {
    background-position: 0 -144px
}

.ui-icon-info {
    background-position: -16px -144px
}

.ui-icon-notice {
    background-position: -32px -144px
}

.ui-icon-help {
    background-position: -48px -144px
}

.ui-icon-check {
    background-position: -64px -144px
}

.ui-icon-bullet {
    background-position: -80px -144px
}

.ui-icon-radio-on {
    background-position: -96px -144px
}

.ui-icon-radio-off {
    background-position: -112px -144px
}

.ui-icon-pin-w {
    background-position: -128px -144px
}

.ui-icon-pin-s {
    background-position: -144px -144px
}

.ui-icon-play {
    background-position: 0 -160px
}

.ui-icon-pause {
    background-position: -16px -160px
}

.ui-icon-seek-next {
    background-position: -32px -160px
}

.ui-icon-seek-prev {
    background-position: -48px -160px
}

.ui-icon-seek-end {
    background-position: -64px -160px
}

.ui-icon-seek-start {
    background-position: -80px -160px
}

.ui-icon-seek-first {
    background-position: -80px -160px
}

.ui-icon-stop {
    background-position: -96px -160px
}

.ui-icon-eject {
    background-position: -112px -160px
}

.ui-icon-volume-off {
    background-position: -128px -160px
}

.ui-icon-volume-on {
    background-position: -144px -160px
}

.ui-icon-power {
    background-position: 0 -176px
}

.ui-icon-signal-diag {
    background-position: -16px -176px
}

.ui-icon-signal {
    background-position: -32px -176px
}

.ui-icon-battery-0 {
    background-position: -48px -176px
}

.ui-icon-battery-1 {
    background-position: -64px -176px
}

.ui-icon-battery-2 {
    background-position: -80px -176px
}

.ui-icon-battery-3 {
    background-position: -96px -176px
}

.ui-icon-circle-plus {
    background-position: 0 -192px
}

.ui-icon-circle-minus {
    background-position: -16px -192px
}

.ui-icon-circle-close {
    background-position: -32px -192px
}

.ui-icon-circle-triangle-e {
    background-position: -48px -192px
}

.ui-icon-circle-triangle-s {
    background-position: -64px -192px
}

.ui-icon-circle-triangle-w {
    background-position: -80px -192px
}

.ui-icon-circle-triangle-n {
    background-position: -96px -192px
}

.ui-icon-circle-arrow-e {
    background-position: -112px -192px
}

.ui-icon-circle-arrow-s {
    background-position: -128px -192px
}

.ui-icon-circle-arrow-w {
    background-position: -144px -192px
}

.ui-icon-circle-arrow-n {
    background-position: -160px -192px
}

.ui-icon-circle-zoomin {
    background-position: -176px -192px
}

.ui-icon-circle-zoomout {
    background-position: -192px -192px
}

.ui-icon-circle-check {
    background-position: -208px -192px
}

.ui-icon-circlesmall-plus {
    background-position: 0 -208px
}

.ui-icon-circlesmall-minus {
    background-position: -16px -208px
}

.ui-icon-circlesmall-close {
    background-position: -32px -208px
}

.ui-icon-squaresmall-plus {
    background-position: -48px -208px
}

.ui-icon-squaresmall-minus {
    background-position: -64px -208px
}

.ui-icon-squaresmall-close {
    background-position: -80px -208px
}

.ui-icon-grip-dotted-vertical {
    background-position: 0 -224px
}

.ui-icon-grip-dotted-horizontal {
    background-position: -16px -224px
}

.ui-icon-grip-solid-vertical {
    background-position: -32px -224px
}

.ui-icon-grip-solid-horizontal {
    background-position: -48px -224px
}

.ui-icon-gripsmall-diagonal-se {
    background-position: -64px -224px
}

.ui-icon-grip-diagonal-se {
    background-position: -80px -224px
}

.ui-corner-all,
.ui-corner-top,
.ui-corner-left,
.ui-corner-tl {
    border-top-left-radius: 6px
}

.ui-corner-all,
.ui-corner-top,
.ui-corner-right,
.ui-corner-tr {
    border-top-right-radius: 6px
}

.ui-corner-all,
.ui-corner-bottom,
.ui-corner-left,
.ui-corner-bl {
    border-bottom-left-radius: 6px
}

.ui-corner-all,
.ui-corner-bottom,
.ui-corner-right,
.ui-corner-br {
    border-bottom-right-radius: 6px
}

.ui-widget-overlay {
    background: #eee url(images/jquery.ui/ui-bg_diagonals-thick_90_eeeeee_40x40.png) 50% 50% repeat;
    opacity: .8;
    filter: Alpha(Opacity=80)
}

.ui-widget-shadow {
    margin: -7px 0 0 -7px;
    padding: 7px;
    background: #000 url(images/jquery.ui/ui-bg_highlight-hard_70_000000_1x100.png) 50% top repeat-x;
    opacity: .3;
    filter: Alpha(Opacity=30);
    border-radius: 8px
}

.statistics-small-header {
    position: relative;
    width: 633px;
    height: 41px;
    margin-bottom: 18px;
    background: url("images/groupstage_header.png") no-repeat
}

.statistics-small-header a {
    position: absolute;
    left: 21px;
    top: 12px;
    width: 148px;
    height: 18px;
    margin-bottom: 18px;
    background: url("images/print_schedule.png") no-repeat
}

.group_stats-small {
    width: 190px;
    float: right;
    margin: 0 14px 17px 0
}

.group_stats-small .score-list-small {
    margin-bottom: 0
}

.group_stats-small .score-list .heading {
    background: url("images/group_header.png") no-repeat left top !important;
    background-size: 100% 100% !important;
    border: none !important;
    color: #fff;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    height: 23px;
    line-height: 33px
}

#main .group_stats-small .score-list table.table {
    border: 0
}

#main .group_stats-small .score-list table.table th {
    font: normal 11px arial;
    background: #00589C;
    color: #fff;
    height: 15px;
    border: 0
}

#main .group_stats-small .score-list table.table th.big {
    text-align: right
}

#main .group_stats-small .score-list table th:first-child {
    width: 10px;
    padding: 0;
    text-align: right
}

#main .group_stats-small .score-list table td {
    background: 0;
    border-bottom: 1px solid #c3cad1;
    vertical-align: bottom;
    height: 25px;
    padding-bottom: 3px
}

#main .group_stats-small .score-list table td.big {
    padding-bottom: 0
}

#main .group_stats-small .score-list table tr:last-child td {
    border-bottom: 0
}

#main .group_stats-small .score-list table td:first-child {
    width: 10px;
    padding: 0 4px 3px
}

#main .group_stats-small .score-list table td:last-child {
    min-width: 20px
}

.group_stats-small .table .big img {
    vertical-align: middle;
    width: 15px;
    height: 15px
}

#main .group_stats-small .table_side_cont {
    border: 1px solid #C3CAD1;
    border-top: 0
}

#main .group_stats-small .table_side_cont table {
    border: 0
}

#main .group_stats-small .table_side_cont th {
    background: #00589C;
    color: #fff;
    vertical-align: top;
    height: 22px !important;
    text-align: center !important;
    padding: 0;
    border-bottom: 0
}

#main .group_stats-small .table_side_cont .table tr td {
    height: 22px;
    text-align: center;
    padding: 0 4px;
    background: #fff url(images/row_reg.gif) left 1px repeat-x;
    font-size: 12px;
    color: #000;
    line-height: normal;
    vertical-align: middle
}

#main .group_stats-small .table_side_cont .table tr.empty_line td {
    height: 23px;
    text-align: right;
    padding: 0 4px;
    background: #fff;
    font-size: 11px;
    color: #707070;
    line-height: normal;
    vertical-align: middle;
    border-bottom: 1px solid #ececec
}

#main .group_stats-small .table_side_cont .table tr td.country {
    text-align: right;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light'
}

#main .group_stats-small .table_side_cont .table tr td.country2 {
    text-align: left
}

#main .group_stats-small .table_side_cont .table tr td.flag {
    width: 20px
}

#main .group_stats-small .table_side_cont .table td:first-child,
.table_side_cont .table th:first-child {
    padding: 0 4px;
    text-align: center
}

#main .group_stats-small .table_side_cont .table .flag img {
    width: 15px;
    height: 15px
}

.ticker-games-bar {
    width: 944px;
    height: 111px;
    margin: 0 0 23px;
    position: relative
}

.ticker-games-bar .bar-header {
    width: 880px;
    height: 23px;
    margin: 0 31px 5px
}

.ticker-games-bar .bar-header span.bar-title {
    float: right;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    font-size: 20px
}

.ticker-games-bar .bar-header a.vod-link {
    float: left;
    height: 23px;
    width: 145px;
    font-size: 14px;
    color: #fff;
    text-decoration: none;
    background: url('images/scores-bar-btn.png') no-repeat
}

.ticker-games-bar .bar-header a.vod-link:hover {
    color: #fff;
    text-decoration: none
}

.ticker-games-bar .bar-header a.vod-link span {
    padding-right: 10px
}

.ticker-games-bar .games-tbl {
    width: 882px;
    margin: 0 31px;
    overflow: hidden
}

.ticker-games-bar .games-tbl .game-cont {
    display: table-cell;
    width: 147px;
    height: 83px;
    background: #F4F4F4;
    border-left: 2px solid #fff;
    direction: rtl
}

.ticker-games-bar .games-tbl .game-cont .game-title {
    background: #0089CF;
    border-top: 2px solid #0089CF;
    border-bottom: 2px solid #fff;
    height: 14px
}

.ticker-games-bar .games-tbl .game-cont .game-title span.hour {
    display: none
}

.ticker-games-bar .games-tbl .game-cont .game-title span {
    color: #fff;
    padding: 0 5px;
    font-size: 14px;
    line-height: 15px;
    float: right;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light'
}

.ticker-games-bar .games-tbl .game-cont .team {
    height: 22px;
    margin-top: 5px;
    padding: 0 10px 0 7px;
    font-size: 14px
}

.ticker-games-bar .games-tbl .game-cont .team a.name {
    float: right;
    width: 103px;
    height: 22px;
    overflow: hidden
}

.ticker-games-bar .games-tbl .game-cont .team span.score {
    float: left;
    font-weight: bold;
    width: 24px
}

.ticker-games-bar .games-tbl .game-cont.future .game-title {
    background: #13AEE3;
    border-top: 2px solid #13AEE3
}

.ticker-games-bar .games-tbl .game-cont.future .game-title span.hour {
    float: left;
    display: block;
    direction: ltr
}

.ticker-games-bar .games-tbl .game-cont.future .team span.score {
    display: none
}

.ticker-games-bar .ticker-button {
    width: 9px;
    height: 10px;
    position: absolute;
    display: block;
    line-height: 0;
    font-size: 0;
    cursor: pointer;
    background: transparent;
    color: transparent;
    bottom: 37px;
    margin-top: -5px;
    padding: 0;
    border: 0;
    outline: 0
}

.ticker-games-bar .ticker-button.next {
    background: url('images/ticker-btn-left.png') no-repeat;
    left: 10px
}

.ticker-games-bar .ticker-button.prev {
    background: url('images/ticker-btn-right.png') no-repeat;
    right: 10px
}

.player_popup_div .popup_close_btn:hover {
    cursor: pointer
}

.player_popup_div {
    display: none;
    position: fixed;
    padding: 15px 15px 15px 5px;
    top: 50%;
    left: 50%;
    background: #fff;
    z-index: 1000;
    box-shadow: 0 0 16px #000
}

.player_popup_div .popup_close_btn {
    float: left;
    position: relative;
    top: -12px;
    left: -2px;
    background: url('images/close_popup.png') no-repeat 0 0;
    background-size: 10px 10px;
    height: 10px;
    width: 10px
}

.player_popup_div .popup-loader,
.player_popup_div .imgPopup {
    display: block
}

.video-div.day-events {
    text-align: center;
    position: relative;
    margin-top: 8px
}

.video-div.day-events .video-desc {
    display: none
}

.video-div.day-events>a.video-anc {
    position: relative;
    display: inline-block
}

.video-div.day-events>a.video-anc:after {
    content: '';
    position: absolute;
    left: 10px;
    top: 10px;
    width: 49px;
    height: 49px;
    z-index: 1000;
    background: url('images/sprite.png') no-repeat -126px -143px
}

.video-div.day-events>a.video-anc:hover:after {
    cursor: pointer;
    background-position: -23px -143px
}

.twitter-cont {
    width: 304px;
    margin-top: 10px
}

.spot-im-ticker {
    z-index: 2147483646 !important
}

.ticker-games-euro .ticker-games-bar {
    width: 944px;
    height: 111px;
    margin: 0 0 23px;
    position: relative
}

.ticker-games-euro .ticker-games-bar .bar-header {
    width: 880px;
    height: 23px;
    margin: 0 31px 5px
}

.ticker-games-euro .ticker-games-bar .bar-header span.bar-title {
    float: right;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    font-size: 20px
}

.ticker-games-euro .ticker-games-bar .bar-header a.vod-link {
    float: left;
    height: 23px;
    width: 145px;
    font-size: 14px;
    color: #fff;
    text-decoration: none;
    background: url('images/scores-bar-btn.png') no-repeat
}

.ticker-games-euro .ticker-games-bar .bar-header a.vod-link:hover {
    color: #fff;
    text-decoration: none
}

.ticker-games-euro .ticker-games-bar .bar-header a.vod-link span {
    padding-right: 10px
}

.ticker-games-euro .ticker-games-bar .games-tbl {
    width: 882px;
    margin: 0 31px;
    overflow: hidden
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .penalty {
    display: block;
    position: absolute;
    margin-right: 17px;
    font-size: 12px
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont {
    display: table-cell;
    width: 147px;
    height: 83px;
    background: #F4F4F4;
    border-left: 2px solid #fff;
    direction: rtl
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .game-title {
    background: #0089CF;
    border-top: 2px solid #0089CF;
    border-bottom: 2px solid #fff;
    height: 14px
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .game-title span.hour {
    display: none
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .game-title span {
    color: #fff;
    padding: 0 5px;
    font-size: 14px;
    line-height: 15px;
    float: right;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light'
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .team {
    height: 22px;
    margin-top: 5px;
    padding: 0 10px 0 7px;
    font-size: 14px
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .team a.name {
    float: right;
    width: 103px;
    height: 22px;
    overflow: hidden
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .team span.score {
    float: left;
    font-weight: bold;
    width: 24px
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont.future .game-title {
    background: #13AEE3;
    border-top: 2px solid #13AEE3
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont.future .game-title span.hour {
    float: left;
    display: block;
    direction: ltr
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont.future .team span.score {
    display: none
}

.ticker-games-euro .ticker-games-bar .ticker-button {
    width: 9px;
    height: 10px;
    position: absolute;
    display: block;
    line-height: 0;
    font-size: 0;
    cursor: pointer;
    background: transparent;
    color: transparent;
    bottom: 37px;
    margin-top: -5px;
    padding: 0;
    border: 0;
    outline: 0
}

.ticker-games-euro .ticker-games-bar .ticker-button.next {
    background: url('images/ticker-btn-left.png') no-repeat;
    left: 10px
}

.ticker-games-euro .ticker-games-bar .ticker-button.prev {
    background: url('images/ticker-btn-right.png') no-repeat;
    right: 10px
}

.ticker-games-euro .euro-tables-cont .games-cont table {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light'
}

.ticker-games-euro .euro-tables-cont .games-cont table td.date-time {
    position: relative;
    text-align: right;
    padding: 0
}

.ticker-games-euro .euro-tables-cont .games-cont table td.date-time span.game-date:after {
    content: '';
    position: absolute;
    height: 5px;
    width: 55px;
    bottom: 0;
    right: 0;
    background: #02ADF2
}

.ticker-games-euro .euro-tables-cont .games-cont table td.group-name {
    vertical-align: top;
    text-align: right;
    color: #02ADF2;
    font-size: 22px;
    line-height: 22px;
    width: 180px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light';
    padding: 10px 0
}

.ticker-games-euro .euro-tables-cont .games-cont table td.team img {
    width: 20px;
    height: 20px
}

.ticker-games-euro .ticker-games-bar {
    width: 944px;
    height: 180px;
    background: url(images/Euro/matches_bg_180h.png) no-repeat
}

.ticker-games-euro .ticker-games-bar .games-tbl {
    width: 760px;
    margin: 0 150px 0 0
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont {
    height: 170px;
    padding: 5px 5px 5px 10px;
    background: 0;
    border: 0
}

.ticker-games-euro .ticker-games-bar .games-tbl .to-arena {
    width: 112px;
    overflow-x: hidden;
    display: block;
    margin-top: 6px;
    margin-right: -6px
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .game-title {
    position: relative;
    background: 0;
    border: 0;
    color: #000;
    height: 25px;
    margin-bottom: 2px
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .game-title span {
    color: #000;
    padding: 0;
    float: none
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .game-title span:after {
    content: '';
    position: absolute;
    height: 5px;
    width: 50px;
    bottom: 0;
    right: 0;
    background: #02ADF2
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .group {
    height: 15px
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .group span {
    color: #02ADF2;
    font-size: 14px;
    line-height: 15px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light';
    vertical-align: top
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont:nth-child(6n) .score-cont {
    border-left: none
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .score-cont {
    border-left: 1px solid #B2B2B2;
    height: 95px
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .team {
    padding: 0;
    margin: 0;
    width: 200px
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .team a.name {
    width: auto;
    float: none
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .team a.name span {
    font-size: 18px;
    line-height: 20px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light'
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .team a.name img {
    display: inline-block;
    width: 18px;
    height: 18px
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .score-cont .score-holder {
    position: relative
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .score-cont .score {
    display: block;
    width: 42px;
    font-size: 22px;
    line-height: 25px
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .score-cont .score:first-child {
    border-bottom: 1px solid #B2B2B2
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .score-cont .status {
    display: block;
    position: absolute;
    top: 13px;
    left: 10px;
    color: #B2B2B2
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .score-cont .hour {
    display: block;
    font-size: 28px;
    line-height: 29px
}

.ticker-games-euro .ticker-games-bar .games-tbl .game-cont .score-cont .spnStadium {
    color: #095E9E;
    font-size: 14px
}

.ticker-games-euro .ticker-games-bar .ticker-button {
    width: 13px;
    height: 20px;
    bottom: 82px
}

.ticker-games-euro .ticker-games-bar .ticker-button.next {
    background: url(images/Euro/arrow_games_L.png) no-repeat;
    left: 10px
}

.ticker-games-euro .ticker-games-bar .ticker-button.prev {
    background: url(images/Euro/arrow_games_R.png) no-repeat;
    right: 130px
}

#main .section-grey .heading {
    background: #111720 url(images/bg-block-car.jpg) no-repeat;
    background-position: 0 0
}

.headerLeft {
    font: normal 1.1vw 'blender-regular', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    margin: 0;
    width: 304px;
    position: relative;
    height: 30px;
    background-color: #0142ad
}

.headerLeft .leftSideTitle {
    width: 100%;
    height: 100%
}

.headerLeft .leftSideTitle span {
    font-size: 18px;
    font-family: 'blender-regular';
    color: #fff;
    padding: 8px 10px;
    display: block
}

.headerLeft .arrowlinktitleLeftSide {
    z-index: 9
}

.header {
    font: normal 1.1vw 'blender-regular', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    margin: 2px 0;
    width: 648px;
    height: 30px
}

.headerWorld {
    font: normal 1.1vw 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    margin: 0 0 12px 0;
    width: 464px
}

.leftRect {
    color: white;
    border-top: 24px solid #0142ad;
    border-right: 7px solid transparent;
    line-height: 1;
    width: 450px;
    float: left;
    line-height: 0
}

.leftRectplay {
    color: white;
    border-top: 24px solid #0142ad;
    border-right: 7px solid transparent;
    line-height: 1;
    width: 450px;
    float: left;
    line-height: 0
}

.leftRectWorld {
    color: white;
    border-top: 24px solid #0142ad;
    border-right: 7px solid transparent;
    line-height: 1;
    width: 360px;
    float: left;
    line-height: 0
}

.leftRectLeftSide {
    color: white;
    border-top: 24px solid #0142ad;
    border-right: 7px solid transparent;
    line-height: 1;
    width: 150px;
    float: left;
    line-height: 0
}

.leftRectLeftSide .textLeftSide {
    color: #fff;
    position: absolute;
    font-size: 18px;
    left: 58px;
    margin-top: -9px
}

.textLeftSide.add-dots {
    display: inline-block;
    width: 177px;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: right
}

.headerTextLeftRedSide.add-dots {
    display: inline-block;
    width: 250px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    text-align: right
}

.rightRect {
    color: white;
    padding-left: -1.5%;
    float: right;
    right: 0;
    border-bottom: 24px solid #0142ad;
    border-left: 7px solid transparent;
    width: 24px;
    position: absolute;
    margin-top: -23px;
    height: 24px;
    line-height: 0
}

.rightRectLeftSide {
    color: white;
    padding-left: -1.5%;
    float: right;
    right: 0;
    border-bottom: 24px solid #0142ad;
    border-left: 7px solid transparent;
    width: 24px;
    position: absolute;
    margin-top: -24px;
    height: 24px;
    line-height: 0
}

.headerText {
    float: right;
    text-align: center;
    color: #fff;
    font-size: 18px;
    margin: 0 10px;
    position: relative;
    height: 24px;
    top: 7px
}

.headerTextLeftSide {
    float: left;
    text-align: center;
    color: #0044B0;
    font-size: 17px;
    margin-top: -10px;
    position: relative;
    height: 24px;
    white-space: nowrap
}

.headerTextLeftRedSide {
    float: right;
    text-align: center;
    color: #fff;
    font-size: 18px;
    height: auto;
    margin: 0 10px;
    position: relative;
    white-space: nowrap;
    top: 7px
}

.TextLeftSide_BlackText {
    color: #FFF;
    font-size: 18px;
    line-height: 11px
}

.header-text-left-side .TextLeftSide_BlackText {
    display: inline-block;
    width: 268px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    text-align: right;
    line-height: 17px
}

.header_title {
    padding-right: 31px;
    overflow: hidden
}

.header_title span {
    display: inline-block;
    position: relative;
    color: #0142ad;
    font-size: 16px;
    line-height: 28px;
    font-family: "south web", Arial, Helvetica, sans-serif
}

.header_title span:before,
.header_title span:after {
    content: "";
    position: absolute;
    height: 24px;
    top: 0;
    min-width: 572px;
    background-color: #0142ad;
    transform: skewX(-20deg)
}

.header_title span:before {
    right: 100%;
    margin-right: 10px
}

.header_title span:after {
    left: 100%;
    margin-left: 10px
}

.externalbanner-Homepage.center {
    width: 100%;
    position: relative;
    direction: ltr;
    background-color: #f3f3f3;
    height: 62px;
    padding-right: -10px
}

.externalbanner-Homepage.center .list {
    height: 43px;
    width: 96%;
    padding-right: 10px
}

#main .externalbanner-Homepage.center .list .video-box {
    margin-right: 2px !important;
    width: 200px;
    direction: rtl
}

.externalbanner-Homepage.center .list .video-box-even {
    margin-right: 0 !important
}

.externalbanner-Homepage.center .list .video-box:first-child {
    margin-right: 0 !important
}

.externalbanner-Homepage.center .gallery-button.prev {
    background: url(images/arrow_games_L.png) no-repeat;
    position: absolute;
    top: 35%;
    left: 4px;
    cursor: pointer;
    height: 20px;
    width: 13px;
    z-index: 10
}

.externalbanner-Homepage.center .gallery-button.next {
    background: url(images/arrow_games_R.png) no-repeat;
    position: absolute;
    top: 35%;
    right: 5px;
    cursor: pointer;
    height: 20px;
    width: 13px;
    z-index: 10
}

.carouselbanner-Homepage.center {
    width: 100%;
    position: relative;
    direction: ltr;
    padding-right: 0;
    text-align: right;
    background-color: #f3f3f3
}

.carouselbanner-Homepage.center .video-list {
    width: 100%;
    padding-right: 0
}

#main .carouselbanner-Homepage.center .video-list .video-box {
    margin-right: 2px !important;
    width: 200px;
    direction: rtl
}

.carouselbanner-Homepage.center .video-list .video-box-even {
    margin-right: 0 !important
}

.carouselbanner-Homepage.center .video-list .video-box:first-child {
    margin-right: 0 !important
}

.carouselbanner-Homepage.center .gallery-button.prev {
    position: absolute;
    top: 36%;
    left: 5px;
    cursor: pointer;
    height: 17px;
    width: 10px;
    z-index: 10;
    font-size: 21px
}

.carouselbanner-Homepage.center .gallery-button.next {
    position: absolute;
    top: 36%;
    right: 14px;
    cursor: pointer;
    height: 17px;
    width: 10px;
    z-index: 10;
    font-size: 21px
}

.video-containerHomepage.center {
    width: 100%;
    position: relative;
    direction: rtl;
    height: auto;
    margin-bottom: 15px
}

.video-containerHomepage.center .video-list {
    height: auto;
    width: 100%
}

#main .descHomepage {
    margin: 12px 4px;
    text-align: right;
    height: 29px
}

#main .descHomepage a {
    color: black !important;
    font: 16px/22px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    line-height: 18px;
    height: auto;
    margin: 12px 0;
    overflow: hidden;
    text-decoration: none
}

#main .descHomepageLeftSide {
    margin: 5px 5px
}

#main .descHomepageLeftSide a {
    color: black !important;
    font: 14px/22px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    line-height: 10px;
    height: auto;
    margin: 12px 0;
    overflow: hidden;
    text-decoration: none
}

.video-containerHomepage.center .video-list.slick-slider {
    margin-bottom: 0;
    margin-right: -2px
}

#main .video-containerHomepage.center .video-list .video-box {
    margin-right: 2px !important;
    width: 200px;
    direction: rtl
}

.video-containerHomepage.center .video-list .video-box-even {
    margin-right: 0 !important
}

.video-containerHomepage.center .video-list .video-box:first-child {
    margin-right: 0 !important
}

.video-containerHomepage.center .gallery-button.prev {
    background: url(images/video-arrow-left.png) no-repeat;
    position: absolute;
    top: 36%;
    left: 17px;
    cursor: pointer;
    height: 17px;
    width: 10px;
    z-index: 10
}

.video-containerHomepage.center .gallery-button.next {
    background: url(images/video-arrow-right.png) no-repeat;
    position: absolute;
    top: 36%;
    right: 10px;
    cursor: pointer;
    height: 17px;
    width: 10px;
    z-index: 10
}

.people-blockHomepage.center {
    width: 100%;
    position: relative;
    direction: rtl
}

.people-blockHomepage.center .video-list {
    height: auto;
    width: 100%;
    padding-bottom: 12px;
    text-align: right
}

.carouselbanner-blockHomepage.center {
    width: 100%;
    position: relative;
    direction: rtl;
    text-align: right;
    height: 285px
}

.carouselbanner-blockHomepage.center .video-list {
    height: auto;
    width: 100%;
    padding-bottom: 12px;
    text-align: right
}

#main .descBlockHomepage {
    margin: 4px 4px 0 0;
    height: auto;
    max-width: 190px
}

#main .descBlockHomepage a {
    color: black;
    font: 16px/22px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    line-height: 18px;
    height: auto;
    margin: 4px 0;
    overflow: hidden;
    text-decoration: none
}

.people-blockHomepage.center .video-list.slick-slider {
    margin: 0
}

#main .people-blockHomepage.center .video-list .video-box {
    direction: rtl
}

.people-blockHomepage.center .video-list .video-box-even {
    margin-right: 16px !important
}

.people-blockHomepage.center .video-list .video-box:first-child {
    margin-right: 0 !important
}

.people-blockHomepage.center .gallery-button.prev {
    background: url(images/video-arrow-left.png) no-repeat;
    position: absolute;
    top: 34%;
    left: 20px;
    cursor: pointer;
    height: 17px;
    width: 10px;
    z-index: 10
}

.people-blockHomepage.center .gallery-button.next {
    background: url(images/video-arrow-right.png) no-repeat;
    position: absolute;
    top: 34%;
    right: 10px;
    cursor: pointer;
    height: 17px;
    width: 10px;
    z-index: 10
}

.gallery-blockHomepage.center {
    width: 100%;
    position: relative;
    direction: rtl;
    height: 155px
}

.gallery-blockHomepage.center .video-list {
    height: auto;
    width: 100%;
    padding-bottom: 12px;
    text-align: right
}

.gallery-blockHomepage.center .video-list.slick-slider {
    margin-bottom: 0;
    margin-right: -2px
}

#main .gallery-blockHomepage.center .video-list .video-box {
    margin-right: 2px !important;
    width: 200px;
    direction: rtl
}

.gallery-blockHomepage.center .video-list .video-box-even {
    margin-right: 0 !important
}

.gallery-blockHomepage.center .video-list .video-box:first-child {
    margin-right: 0 !important
}

.gallery-blockHomepage.center .gallery-button.prev {
    background: url(images/video-arrow-left.png) no-repeat;
    position: absolute;
    top: 45%;
    left: 20px;
    cursor: pointer;
    height: 17px;
    width: 10px;
    z-index: 10
}

.gallery-blockHomepage.center .gallery-button.next {
    background: url(images/video-arrow-right.png) no-repeat;
    position: absolute;
    top: 45%;
    right: 10px;
    cursor: pointer;
    height: 17px;
    width: 10px;
    z-index: 10
}

.gallery-blockHomepage.center .gallery-buttonNew.prev {
    background: url(images/video-arrow-left.png) no-repeat;
    position: absolute;
    top: 30%;
    left: 12px;
    cursor: pointer;
    height: 17px;
    width: 10px;
    z-index: 10
}

.gallery-blockHomepage.center .gallery-buttonNew.next {
    background: url(images/video-arrow-right.png) no-repeat;
    position: absolute;
    top: 30%;
    right: 8px;
    cursor: pointer;
    height: 17px;
    width: 10px;
    z-index: 10
}

.arrowlinktitle {
    position: absolute;
    left: 10px;
    margin-top: 5px;
    height: 20px;
    width: 20px;
    background: url(images/arrow_l_white.png) no-repeat;
    background-position: 50%;
    background-size: 45%;
    cursor: pointer
}

.arrowlinktitleLeftSide {
    position: absolute;
    left: 10px;
    margin-top: 5px;
    height: 20px;
    width: 20px;
    background: url(images/arrow_l_white.png) no-repeat;
    background-position: 50%;
    background-size: 45%;
    cursor: pointer
}

#sidebarHomePage {
    float: left;
    padding: 0 10px 0 0;
    width: 314px
}

.events_archive #sidebarHomePage {
    padding: 0
}

#sidebarHomePage .banner-holder {
    margin: 0 3px 15px;
    text-align: right
}

#sidebarHomePage .banner-holder img {
    display: block
}

#sidebarHomePage .gallery {
    margin: 0 0 10px;
    border: 1px solid #d4d4d4;
    overflow: hidden;
    background: #f9f9f9;
    background: -moz-linear-gradient(top, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(249, 249, 249, 1)), color-stop(33%, rgba(248, 248, 248, 1)), color-stop(43%, rgba(244, 244, 244, 1)), color-stop(74%, rgba(240, 240, 240, 1)), color-stop(100%, rgba(233, 233, 233, 1)));
    background: -webkit-linear-gradient(top, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%);
    background: -o-linear-gradient(top, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%);
    background: -ms-linear-gradient(top, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%);
    background: linear-gradient(to bottom, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%)
}

#sidebarHomePage .gallery .btn-prev,
#sidebarHomePage .gallery .btn-next {
    float: left;
    overflow: hidden;
    text-indent: -9999px;
    width: 15px;
    height: 74px;
    background: url(images/sprite.png) no-repeat -261px 0
}

#sidebarHomePage .gallery .btn-next {
    float: right;
    background-position: -279px 0
}

#sidebarHomePage .gallery .btn-next:hover {
    background-position: -279px -78px
}

#sidebarHomePage .gallery .btn-prev:hover {
    background-position: -261px -78px
}

#sidebarHomePage .gallery .gmask {
    overflow: hidden;
    border: solid #fff;
    border-width: 1px 0 0 1px
}

#sidebarHomePage .gallery .gmask>ul {
    padding: 0;
    margin: 0;
    list-style: none;
    overflow: hidden;
    width: 9999px
}

#sidebarHomePage .meetings-block {
    margin: 0 0 26px;
    border: 1px solid #c3cad1;
    display: inline-block
}

#sidebarHomePage .meetings-block table.item-list {
    width: 50%;
    float: right
}

.events_archive #sidebarHomePage {
    padding: 0
}

#sidebarHomePage .banner-holder {
    margin: 0 0 18px;
    text-align: right
}

#sidebarHomePage .banner-holder img {
    display: block
}

#sidebarHomePage .gallery {
    border: 1px solid #d4d4d4;
    overflow: hidden;
    background: #f9f9f9;
    background: -moz-linear-gradient(top, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%);
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(249, 249, 249, 1)), color-stop(33%, rgba(248, 248, 248, 1)), color-stop(43%, rgba(244, 244, 244, 1)), color-stop(74%, rgba(240, 240, 240, 1)), color-stop(100%, rgba(233, 233, 233, 1)));
    background: -webkit-linear-gradient(top, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%);
    background: -o-linear-gradient(top, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%);
    background: -ms-linear-gradient(top, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%);
    background: linear-gradient(to bottom, rgba(249, 249, 249, 1) 0, rgba(248, 248, 248, 1) 33%, rgba(244, 244, 244, 1) 43%, rgba(240, 240, 240, 1) 74%, rgba(233, 233, 233, 1) 100%)
}

#sidebarHomePage .gallery .btn-prev,
#sidebarHomePage .gallery .btn-next {
    float: left;
    overflow: hidden;
    text-indent: -9999px;
    width: 15px;
    height: 74px;
    background: url(images/sprite.png) no-repeat -261px 0
}

#sidebarHomePage .gallery .btn-next {
    float: right;
    background-position: -279px 0
}

#sidebarHomePage .gallery .btn-next:hover {
    background-position: -279px -78px
}

#sidebarHomePage .gallery .btn-prev:hover {
    background-position: -261px -78px
}

#sidebarHomePage .gallery .gmask {
    overflow: hidden;
    border: solid #fff;
    border-width: 1px 0 0 1px
}

#sidebarHomePage .gallery .gmask>ul {
    padding: 0;
    margin: 0;
    list-style: none;
    overflow: hidden;
    width: 9999px
}

#sidebarHomePage .meetings-block {
    margin: 0 0 26px;
    border: 1px solid #c3cad1;
    display: inline-block
}

#sidebarHomePage .meetings-block table.item-list {
    width: 50%;
    float: right
}

#sidebarHomePage .heading {
    padding: 8px 5px 2px
}

#sidebarHomePage .premiership-block .heading {
    text-align: right;
    padding: 4px 18px 1px;
    border-bottom: 3px solid #014bad;
    border-left: none;
    border-right: 0
}

#sidebarHomePage .premiership-block .heading-alt {
    border: 0;
    padding: 1px 14px 6px;
    overflow: hidden
}

#sidebarHomePage .heading p {
    margin: 0;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold'
}

#sidebarHomePage .heading h2 {
    margin: 0;
    font-size: 18px;
    line-height: 22px
}

#sidebarHomePage .gallery .gmask>ul>li {
    float: right;
    height: 73px;
    width: 253px;
    padding: 0 10px 0 9px
}

#sidebarHomePage .gallery .gmask ul img {
    display: inline-block;
    vertical-align: middle
}

#sidebarHomePage .gallery .gmask .holder {
    display: inline-block;
    vertical-align: middle;
    margin: 0 4px 0 8px;
    font-weight: bold;
    text-align: center;
    width: 164px;
    height: 73px
}

#sidebarHomePage .gallery strong {
    display: block;
    margin: 0 0 2px
}

#sidebarHomePage .gallery .score {
    padding: 0;
    margin: 0 0 3px;
    font-size: 12px;
    line-height: 13px;
    font-weight: normal;
    list-style: none;
    overflow: hidden
}

#sidebarHomePage .gallery .score li {
    float: right;
    margin-right: 2px;
    border: 1px solid #bcbcbc;
    width: 51px;
    padding: 2px 0
}

#sidebarHomePage .gallery .score li:first-child {
    margin: 0
}

#sidebarHomePage .gallery .score span {
    float: left;
    width: 15px;
    font-weight: bold;
    padding: 2px 0;
    color: #cc1601;
    background: #fff;
    margin: -2px 0
}

#sidebarHomePage .gallery.gallery-banner {
    height: 73px
}

#sidebarHomePage .gallery.gallery-banner .gmask {
    width: 193px;
    border: 0
}

#sidebarHomePage .gallery.gallery-banner .gmask>ul>li {
    width: 193px;
    padding: 0
}

#sidebarHomePage .gallery.gallery-banner .gmask .holder {
    display: block;
    width: auto;
    margin: 0
}

#sidebarHomePage .gallery.gallery-banner strong {
    height: 22px;
    overflow: hidden;
    font-size: 12px
}

#sidebarHomePage .gallery.gallery-banner .score li {
    width: 48px;
    line-height: 12px;
    float: none;
    display: inline-block;
    margin: 0 3px
}

#sidebarHomePage .gallery.gallery-banner .score span {
    margin: 0;
    padding: 0
}

#sidebarHomePage .gallery.gallery-banner a.send {
    display: inline-block
}

#sidebarHomePage .gallery.gallery-banner .btn-prev,
#sidebarHomePage .gallery.gallery-banner .btn-next,
#sidebarHomePage .gallery.gallery-banner .gmask {
    float: left
}

#sidebarHomePage .gallery.gallery-banner .btn-next {
    border-right: 1px solid #d4d4d4
}

#sidebarHomePage .gallery.gallery-banner>img {
    margin-top: 6px;
    margin: 6px 2px 0 0;
    float: right
}

#sidebarHomePage .gallery.gallery-banner .score li {
    width: 48px;
    line-height: 12px;
    float: none;
    display: inline-block;
    margin: 0 3px
}

#sidebarHomePage .heading3 strong {
    padding: 3px 17px 0;
    display: block;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    font-weight: normal
}

#sidebarHomePage .banner-list {
    padding: 0;
    text-align: right
}

#sidebarHomePage .post-holder {
    position: relative
}

#sidebarHomePage .post .text-holder {
    overflow: hidden;
    height: 75px
}

#sidebarHomePage .post-list p a {
    color: #000;
    font: 14px 'blender-bold', 'Mukta-ExtraBold', 'south web', Arial, Helvetica, sans-serif;
    text-decoration: none
}

#sidebarHomePage .facebook-widget .heading2 {
    border: 0;
    border-bottom: 3px solid #014bad;
    position: relative;
    color: #0242AE
}

#sidebarHomePage .facebook-widget .heading2 strong:after {
    content: '';
    position: absolute;
    left: 0;
    top: 0;
    width: 54px;
    border-top: 27px solid #0242AE;
    border-right: 7px solid transparent
}

#sidebarHomePage .heading2 {
    border: 1px solid #e8e8e8;
    text-align: right;
    margin: 0 0 16px;
    font-weight: normal;
    border-bottom: 3px solid #014bad;
    padding: 0
}

#sidebarHomePage .heading2 strong {
    padding: 3px 17px 0;
    display: block;
    border: 1px solid #fff;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    font-weight: normal;
    width: 300px
}

#sidebarHomePage .heading3 {
    text-align: right;
    font-weight: normal;
    padding: 0;
    width: 54px;
    border-top: 32px solid #00ACEC;
    border-right: 7px solid transparent;
    float: left;
    background: #F3F3F3
}

#sidebarHomePage .heading3 strong {
    padding: 3px 17px 0;
    display: block;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web bold';
    font-weight: normal
}

@media only screen and (max-width:1400px) {
    .banner-right-fixed {
        display: none
    }
}

@media only screen and (max-width:1320px) {
    .banner-left-fixed {
        display: none
    }

    .Plazma_944x200 {
        display: none
    }
}

.banner-holder-strip {
    margin-bottom: 10px
}

.containerHomePage {
    width: 982px;
    padding-right: 15px;
    padding-left: 15px;
    margin-right: -15px
}

.center {
    text-align: center
}

.articleBannerSpace {
    padding-right: 10px !important;
    padding-top: 8px;
    text-align: right
}

.video-containerLeftVideo {
    width: 304px;
    margin-top: 7px
}

#main .carouserShare.center {
    width: 107.8%;
    position: relative;
    direction: ltr;
    height: 280px;
    margin-bottom: 10px
}

#main .carouserShare.center .video-list {
    height: auto;
    width: 100%
}

#main .carouserShare.center .video-list.slick-slider {
    margin-bottom: 0;
    margin-right: 0
}

#main .carouserShare.center .video-list .video-box {
    margin-right: -2px !important;
    width: 100%;
    direction: rtl
}

#main .carouserShare.center .video-list .video-box-even {
    margin-right: 0 !important
}

#main .carouserShare.center .video-list .video-box:first-child {
    margin-right: 0 !important
}

.carouserShare.center .gallery-button.prev {
    background: url(images/arrow_games_L.png) no-repeat;
    position: absolute;
    top: 50%;
    left: 50px;
    cursor: pointer;
    height: 20px;
    width: 13px;
    z-index: 10
}

.carouserShare.center .gallery-button.next {
    background: url(images/arrow_games_R.png) no-repeat;
    position: absolute;
    top: 50%;
    right: 10px;
    cursor: pointer;
    height: 20px;
    width: 13px;
    z-index: 10
}

#main .carouserShare.center .video-list span.desc {
    position: absolute;
    bottom: 0;
    width: 250px;
    max-height: 28px;
    height: 28px;
    overflow: hidden;
    color: #fff;
    font-size: 11px;
    padding: 2px 5px 2px 2px;
    white-space: normal;
    text-align: right;
    background: url(https://m.sport5.co.il/images/black_bg.png) repeat
}

#main .carouserShareVerticle.center {
    width: 305px;
    position: relative;
    direction: rtl;
    height: 280px;
    margin-bottom: 10px;
    margin-left: 10px;
    padding-right: 4px
}

#main .carouserShareVerticle.center .video-list {
    height: auto;
    width: 100%
}

#main .carouserShareVerticle.center .video-list.slick-slider {
    margin-bottom: 0;
    margin-right: 0
}

#main .carouserShareVerticle.center .video-list .video-box {
    margin-right: 0 !important;
    width: 100%;
    direction: rtl
}

#main .carouserShareVerticle.center .video-list .video-box-even {
    margin-right: 0 !important
}

#main .carouserShareVerticle.center .video-list .video-box:first-child {
    margin-right: 0 !important
}

.carouserShareVerticle.center .gallery-button.prev {
    background: url(images/arrow_games_L.png) no-repeat;
    position: absolute;
    top: 50%;
    left: 10px;
    cursor: pointer;
    height: 20px;
    width: 13px;
    z-index: 10
}

.carouserShareVerticle.center .gallery-button.next {
    background: url(images/arrow_games_R.png) no-repeat;
    position: absolute;
    top: 50%;
    right: 10px;
    cursor: pointer;
    height: 20px;
    width: 13px;
    z-index: 10
}

#main .carouserShareVerticle.center .video-list span.desc {
    position: absolute;
    bottom: 0;
    width: 250px;
    max-height: 28px;
    height: 28px;
    overflow: hidden;
    color: #fff;
    font-size: 11px;
    padding: 2px 5px 2px 2px;
    white-space: normal;
    text-align: right;
    background: url(https://m.sport5.co.il/images/black_bg.png) repeat
}

#galleryHome .desc {
    position: absolute;
    right: 0;
    bottom: 0;
    color: #fff;
    font-size: 14px;
    line-height: 15px;
    padding: 5px 0;
    background: url(images/bg-desc.png);
    padding: 2px 10px 4px;
    opacity: .8;
    width: 648px;
    height: 50px
}

#galleryHome.desc span {
    display: block
}

#galleryHome .descWorld {
    position: absolute;
    bottom: 0;
    color: #fff;
    font-size: 14px;
    line-height: 15px;
    padding: 5px 0;
    background: url(images/bg-desc.png);
    padding: 2px 10px 4px;
    opacity: .8;
    width: 464px;
    height: 50px
}

#galleryHome.descWorld span {
    display: block
}

.videoAnimation,
.four_three,
.reg_pic {
    position: relative;
    width: 100%;
    resize: both
}

.videoAnimation,
.four_three,
.reg_pic {
    margin-bottom: 30px;
    margin-top: 20px;
    position: relative;
    width: 100%
}

.close-video {
    font-size: 16px;
    margin: 30px -2px;
    color: white;
    z-index: 206;
    position: absolute;
    cursor: pointer;
    background: url(images/Close_button_35x35.png) no-repeat 0 0;
    background-size: 20px 20px;
    height: 24px;
    left: 2px;
    width: 24px;
    top: -26px
}

.videoAnimation:after {
    content: "";
    display: block
}

.four_three .img_videobackground,
.videoAnimation .img_videobackground {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    background-size: cover;
    background-position: top center
}

.video-scroll {
    width: 297px;
    height: 339px;
    bottom: 25px;
    left: 300px;
    position: fixed;
    z-index: 2;
    animation: video_to_left .3s ease-out;
    animation-fill-mode: forwards
}

@keyframes video_to_left {
    100% {
        width: 297px;
        height: 172px;
        bottom: 25px
    }
}

.spaceLeftSize {
    margin-top: 10px
}

.teamReadOnly {
    pointer-events: none;
    opacity: .6
}

.main-block2 .main-header,
.main-block2 .news-header {
    margin-bottom: 5px;
    display: -webkit-inline-box;
    width: 100%
}

.main-block2 .main-header .right-rect {
    border-bottom: 24px solid #181F28;
    border-left: 15px solid transparent;
    height: 0;
    width: 29px
}

.main-block2 .main-header .left-rect {
    border-top: 24px solid #181F28;
    border-right: 15px solid transparent;
    height: 0;
    width: 82%
}

.main-block2 .main-header span {
    font-family: "south web", Arial, Helvetica, sans-serif;
    font-size: 16px
}

.main-block2 .news-header span {
    font-family: "south web", Arial, Helvetica, sans-serif;
    font-size: 16px;
    color: #0044b0
}

.main-block2 .news-header .right-rect {
    border-bottom: 24px solid #0044b0;
    border-left: 15px solid transparent;
    height: 0;
    width: 29px
}

.main-block2 .news-header .left-rect {
    border-top: 24px solid #0044b0;
    border-right: 15px solid transparent;
    height: 0;
    width: 88%
}

.author-details {
    display: block;
    margin-bottom: 15px;
    padding-bottom: 15px
}

.author-title.header_title {
    color: #fff;
    background-color: #000;
    height: 29px;
    font-size: 18px;
    padding: 1px 10px 0 0;
    margin: 0 0 8px 0;
    font-family: 'blender-regular', 'Mukta-Regular', 'south web light'
}

.author-details .author-text {
    display: inline-block;
    width: 451px;
    vertical-align: top
}

.author-details h1 {
    font-size: 28px;
    color: black;
    vertical-align: top;
    font-family: 'blender-regular', 'Mukta-Regular', 'south web light'
}

.author-details {
    position: relative
}

.author-details .author-pic-place {
    display: inline-block;
    width: 164px;
    min-height: 144px
}

.author-details .author-img {
    display: inline-block;
    width: 144px;
    height: 144px;
    margin: 15px;
    border-radius: 50%;
    background-size: cover;
    background-position: 50% 50%;
    background-repeat: no-repeat
}

.author-details .author-pic-thumb {
    width: 144px;
    height: 144px;
    position: relative;
    overflow: hidden;
    -webkit-border-radius: 50%;
    -moz-border-radius: 50%;
    -ms-border-radius: 50%;
    -o-border-radius: 50%;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: 20px;
    right: 10px
}

.author-pic-thumb-radius {
    position: absolute;
    top: 0;
    left: 0;
    margin: auto;
    width: 144px;
    height: 144px;
    border: solid 1px #0142ad;
    border-radius: 72px
}

.author-details .author-pic {
    height: 100%;
    width: auto
}

.author-details .author-more-text {
    font-size: 16px
}

.author-details .author-social {
    margin-top: 20px;
    font-size: 16px
}

.author-details .author-social a {
    width: 24px;
    height: 24px;
    margin-right: 2px;
    vertical-align: middle;
    display: inline-block;
    background-repeat: no-repeat
}

.author-details .author-social .author-email {
    background-image: url('../../Images/Social/social-icon-email.png')
}

.author-details .author-social .author-wiki {
    background-image: url('../../Images/Social/social-icon-wiki.png')
}

.author-details .author-social .author-fb {
    background-image: url('../../Images/Social/social-icon-fb.png')
}

.author-details .author-social .author-tw {
    background-image: url('../../Images/Social/social-icon-tw.png')
}

.author-details .author-social .author-linkedin {
    background-image: url('../../Images/Social/social-icon-linkedin.png')
}

.author-details .author-social .author-ig {
    background-image: url('../../Images/Social/social-icon-ig.png')
}

.p404 a.p404-link {
    line-height: 28px;
    font-family: "south web", Arial, Helvetica, sans-serif
}

.p404 h1 {
    font-family: "south web", Arial, Helvetica, sans-serif;
    color: #034bad;
    text-align: center;
    font-size: 30px
}

.p404 .p404-pic {
    text-align: center
}

.p404-recommendation .header_title span:before {
    min-width: 844px
}

.p404-recommendation .btn-play.small>a:after {
    right: 68.5%;
    top: 62%
}

.p404-recommendation .article-list .img-holder img {
    width: 229px;
    height: auto;
    min-height: 170px
}

.p404-recommendation #main .article-list li {
    width: 25%
}

#main .p404-recommendation .article-list li {
    width: 239px;
    border-right: 0
}

#main .p404-recommendation .article-list li:first-child {
    width: 226px
}

.p404-recommendation .article-list-holder {
    margin-top: 10px
}

.p404-recommendation .article-list .writer-date,
.p404-recommendation .article-list .talkbacks-cont svg {
    display: none
}

.LeftBannerSize {
    width: 304px
}

.radio-comp .LiveChannel {
    background: #fff;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light', Arial, Helvetica, sans-serif;
    font-size: 10pt;
    border: 0;
    box-shadow: none;
    right: 2px;
    border-radius: 2px;
    border: white 3px solid;
    margin: 6px 8px;
    font-weight: bold
}

.LiveChannel {
    width: 32px;
    height: 20px;
    font-size: 12px;
    line-height: 17px;
    color: #FFF;
    border: 1px solid #49515a;
    border-radius: 5px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    box-shadow: 0 1px 3px #0a0d11;
    position: absolute;
    background: url(images/scores-btn.png) no-repeat;
    background-size: 100%
}

.talkbacks-svg .spot-im-replies-count {
    color: #000
}

.talkbacks-svg .talkbacksText-blue a,
.color-alt-blue .talkbacks-cont a {
    color: #0042B0 !important
}

.talkbacks-svg .talkbacksText-red a,
.color-alt-red .talkbacks-cont a {
    color: #d10202 !important
}

.talkbacks-svg .talkbacksText-orange a,
.color-alt-orange .talkbacks-cont a {
    color: #ff5a00 !important
}

.talkbacks-svg .talkbacksText-bluetravel a,
.color-alt-bluetravel .talkbacks-cont a {
    color: #043959 !important
}

.talkbacks-svg .talkbacksText-lightblue a,
.color-alt-lightblue .talkbacks-cont a {
    color: #0c9bd9 !important
}

.talkbacks-svg .talkbacksText-peach a,
.color-alt-peach .talkbacks-cont a {
    color: #B8411F !important
}

.talkbacks-svg .talkbacksText-grey a,
.color-alt-grey .talkbacks-cont a {
    color: #59616c !important
}

.talkbacks-svg .talkbacksText-black a,
.color-alt-black .talkbacks-cont a {
    color: #141c27 !important
}

.talkbacks-svg .talkbacksText-green a,
.color-alt-green .talkbacks-cont a {
    color: #0e9b00 !important
}

.talkbacks-svg .talkbacksText-gold a,
.color-alt-gold .talkbacks-cont a {
    color: #94896a !important
}

.talkbacks-svg .talkbacksText-white a,
.color-alt-white .talkbacks-cont a {
    color: #fff !important
}

.talkbacks-svg .talkbacksText-rio_red a {
    color: #ed1b24 !important
}

.logo-line {
    border-bottom-color: black;
    border-bottom-width: 5px;
    border-bottom-style: solid;
    width: 865px
}

.editor-tv-wrapper.editor-tv-active {
    margin-top: 2% !important
}

#main .events_archive .main-block2 .items>.heading {
    position: relative;
    background-color: #222 !important;
    background-image: unset;
    border: unset;
    height: 30px
}

#main .events_archive .main-block2 .items>.heading h1 {
    position: absolute;
    top: -8px;
    right: -15px;
    color: #fff !important;
    font-size: 18px !important;
    font-weight: normal;
    font-family: blender-regular
}

#sidebarHomePage .facebook-widget .heading2 {
    border: unset;
    position: relative;
    color: #fff;
    background-color: #0242AE;
    background-image: unset
}

#main .events_archive #sidebarHomePage .heading2 span {
    display: block;
    width: 100%;
    height: 29px;
    padding: 4px 6px;
    border: 0;
    background-color: #0242AE;
    background: url("/images/faceboolicon.png") no-repeat 10px center;
    background-size: 8px
}

#main .events_archive #sidebarHomePage .heading2 span:after {
    padding: unset;
    content: unset
}

.events_archive .article-list .talkbacks-cont {
    margin: 0
}

.fb-logo-red {
    float: right;
    height: 128px;
    width: 86px
}

.fb-share-red {
    float: right;
    background-color: #DA171D;
    height: 128px;
    width: 218px
}

.fb-share-text {
    color: #fff;
    font-family: 'blender-regular';
    font-size: 23px;
    line-height: 24px;
    padding: 12px 10px
}

.fb-share-icons img {
    margin: 0 2px 0 0;
    width: 48px;
    height: 50px
}

.header-holder li a,
.header-holder div {
    font-family: 'blender-regular' !important
}

.main-article h1,
.main-article .h3 a,
.main-article span a,
.main-article em.date,
.article-list .abstract a,
.article-list .writer a,
.article-list em.date,
.post-holder em.date,
.post-holder .writer a,
.post-container .content,
.goal-list li {
    font-family: 'blender-regular'
}

.news-block .news-list li {
    font-family: 'blender-regular'
}

.players-block li a,
.main-holder .premiership-block {
    font-family: 'blender-regular' !important
}

.person-block h1,
.person-block li,
.person-block td {
    font-family: 'blender-regular'
}

.player-block .holder,
.drop-holder {
    font-family: 'blender-regular' !important
}

a.more-stat {
    font-family: 'blender-regular'
}

.title-image {
    text-align: unset;
    padding: 0 49px 0 0
}

.title-image li {
    margin: 0 0 0 2px
}

.program-block.scoreboard-block {
    font-family: 'blender-regular' !important
}

.text-block-tags,
.find-article-content,
.gallery .desc,
.content-block a,
.score-list td,
#spotim-specific span,
.Button__contentWrapper {
    font-family: 'blender-regular' !important
}

.writer-date a,
.writer-date em,
.abstract a,
.date {
    font-family: 'blender-regular' !important
}

.world-page .slick-list .descBlockHomepage a {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
    overflow: hidden;
    text-overflow: ellipsis
}

.league-page #allTalents,
.world-page #allTalents {
    cursor: default
}

.containerHomePage .section {
    margin: 0 0 2px 0
}

.league-page .slick-list .btn-play a>img {
    width: 212px !important;
    height: auto
}

.world-page .slick-list .btn-play a>img {
    width: 210px !important;
    height: auto
}

.calculatorSubTitle {
    display: block;
    position: relative;
    font: 15px/23px blender-regular, Mukta-ExtraBold, "south web ultralight", Arial, Helvetica, sans-serif;
    line-height: 25px;
    padding: 10px
}

a {
    color: #000;
    text-decoration: none
}

.world-page .world-title,
.league-page .world-title,
.section.sub-container .header {
    height: 30px;
    width: 100%
}

.world-page #main .people-blockHomepage.center .video-list .video-box,
.league-page #main .people-blockHomepage.center .video-list .video-box {
    direction: rtl;
    display: block;
    width: 210px;
    height: auto
}

.world-page #main .people-blockHomepage.center .btn-play a>img,
.league-page #main .people-blockHomepage.center .btn-play a>img {
    position: relative;
    z-index: -1;
    width: 210px;
    height: auto
}

.world-page .post-container .post-alt .holder,
.league-page .post-container .post-alt .holder {
    background: #fff
}

.world-page .post-container .post-alt,
.league-page .post-container .post-alt {
    padding-bottom: 0
}

.world-page .post-container .post,
.league-page .post-container .post {
    padding: 0 0 0 16px
}

.world-page .world-title,
.league-page .world-title {
    height: 38px !important;
    padding: 5px 10px 0 0;
    display: inline-block
}

.world-page .text-holder .date,
.league-page .text-holder .date {
    width: 100%;
    padding: 11px 0 0 0
}

.world-page .post-container .title,
.league-page .post-container .title {
    padding: 0 0 10px 0;
    margin: -2px 0 0 0;
    font-family: 'blender-regular', Arial, Helvetica, sans-serif !important;
    color: #000;
    font-size: 14px
}

.world-page .world-title h1,
.league-page .world-title h1,
.section.sub-container .sub-title {
    margin: 7px 15px;
    font-size: 18px;
    font-family: 'blender-regular';
    color: #fff;
    display: inline-block
}

.word-main-article.banner-holder .img-holder {
    position: relative;
    float: right
}

.word-main-article.banner-holder .desc {
    position: relative;
    float: left;
    width: 428px;
    margin: 10px 0 0 30px;
    background-color: #fff !important
}

.league-page .word-main-article.banner-holder .talkbacks-container,
.league-page #main .banner-holder p.h3,
.league-page #main .banner-holder h3,
.league-page #main .banner-holder h2,
.world-page .word-main-article.banner-holder .talkbacks-container,
.world-page #main .banner-holder p.h3,
.world-page #main .banner-holder h3,
.world-page #main .banner-holder h2 {
    width: 407px
}

.league-page #main .banner-holder h2,
.world-page #main .banner-holder h2 {
    margin: 10px -1px 11px 0;
    line-height: 33px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 4;
    overflow: hidden;
    text-overflow: ellipsis
}

.league-page #main .banner-holder p.h3,
.world-page #main .banner-holder p.h3 {
    line-height: 22px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 6;
    overflow: hidden;
    text-overflow: ellipsis
}

.mainarticle-league.banner-holder .desc {
    border: unset
}

.word-main-article.banner-holder .desc h2 a {
    color: #000 !important;
    font-size: 33px !important;
    text-align: right
}

.word-main-article.banner-holder .desc p.h3 a {
    color: #000 !important;
    font-size: 18px !important;
    text-align: right
}

.word-main-article.banner-holder .talkbacks-container {
    position: absolute;
    bottom: 20px;
    width: 100%
}

.league-page #sidebarHomePage .heading strong {
    font-size: 18px;
    font-family: 'blender-regular';
    background-color: transparent;
    color: #fff;
    font-weight: 300
}

.league-page.color-alt-blue .tab-box .tabset {
    background: #071C81
}

.league-page.color-alt-blue .tab-box-list,
.league-page.color-alt-blue .tab-box .tabset a:hover,
.league-page.color-alt-blue .tab-box .tabset li a.active,
.color-alt-blue .world-title,
.league-page.color-alt-blue #sidebarHomePage .heading {
    background: #014bad !important;
    height: 30px;
    border: 0;
    padding: 4px 7px 0 7px
}

.league-page.color-alt-blue .tab-box .tabset li a.active {
    height: 38px
}

.color-alt-blue .word-main-article .talkbacks-svg svg,
.color-alt-blue .post-container .world-arrow {
    fill: #1430ba !important;
    color: #1430ba
}

.color-alt-blue .word-main-article .talkbacks-svg .messages-count a,
.league-page.color-alt-blue .result-form a.open,
.league-page.color-alt-blue .result-form a.openYearList {
    color: #1430ba !important
}

.league-page.color-alt-orange .tab-box .tabset {
    background: #c24704
}

.league-page.color-alt-orange .tab-box-list,
.league-page.color-alt-orange .tab-box .tabset li a:hover,
.league-page.color-alt-orange .tab-box .tabset li a.active,
.color-alt-orange .world-title,
.league-page.color-alt-orange #sidebarHomePage .heading {
    background: #ff5a00 !important
}

.color-alt-orange .word-main-article .talkbacks-svg svg,
.color-alt-orange .post-container .world-arrow {
    fill: #ff5a00 !important;
    color: #ff5a00
}

.color-alt-orange .word-main-article .talkbacks-svg .messages-count a,
.league-page.color-alt-orange .result-form a.open,
.league-page.color-alt-orange .result-form a.openYearList {
    color: #ff5a00 !important
}

.league-page.color-alt-green .tab-box .tabset {
    background: #0b7a00
}

.league-page.color-alt-green .tab-box-list,
.league-page.color-alt-green .tab-box .tabset li a:hover,
.league-page.color-alt-green .tab-box .tabset li a.active,
.color-alt-green .world-title,
.league-page.color-alt-green #sidebarHomePage .heading {
    background: #0e9b00 !important
}

.color-alt-green .word-main-article .talkbacks-svg svg,
.color-alt-green .post-container .world-arrow {
    fill: #0e9b00 !important;
    color: #0e9b00
}

.color-alt-green .word-main-article .talkbacks-svg .messages-count a,
.league-page.color-alt-green .result-form a.open,
.league-page.color-alt-green .result-form a.openYearList {
    color: #0e9b00 !important
}

.league-page.color-alt-red .tab-box .tabset {
    background: #940101
}

.league-page.color-alt-red .tab-box-list,
.league-page.color-alt-red .tab-box .tabset li a:hover,
.league-page.color-alt-red .tab-box .tabset li a.active,
.color-alt-red .world-title,
.league-page.color-alt-red #sidebarHomePage .heading {
    background: #d10202 !important
}

.color-alt-red .word-main-article .talkbacks-svg svg,
.color-alt-red .post-container .world-arrow {
    fill: #d10202 !important;
    color: #d10202
}

.color-alt-red .word-main-article .talkbacks-svg .messages-count a,
.league-page.color-alt-red .result-form a.open,
.league-page.color-alt-red .result-form a.openYearList {
    color: #d10202 !important
}

.league-page #sidebarHomePage .heading {
    background-color: #d10202
}

.league-page.color-alt-bluetravel .tab-box .tabset {
    background: #01253b
}

.league-page.color-alt-bluetravel .tab-box-list,
.league-page.color-alt-bluetravel .tab-box .tabset li a:hover,
.league-page.color-alt-bluetravel .tab-box .tabset li a.active,
.color-alt-bluetravel .world-title,
.league-page.color-alt-bluetravel #sidebarHomePage .heading {
    background: #014bad !important
}

.color-alt-bluetravel .word-main-article .talkbacks-svg svg,
.color-alt-bluetravel .post-container .world-arrow {
    fill: #043959 !important;
    color: #043959
}

.color-alt-bluetravel .word-main-article .talkbacks-svg .messages-count a,
.league-page.color-alt-bluetravel .result-form a.open,
.league-page.color-alt-bluetravel .result-form a.openYearList {
    color: #043959 !important
}

.league-page.color-alt-greenLight .tab-box .tabset {
    background: #446e29
}

.league-page.color-alt-greenLight .tab-box-list,
.league-page.color-alt-greenLight .tab-box .tabset li a:hover,
.league-page.color-alt-greenLight .tab-box .tabset li a.active,
.color-alt-greenLight .world-title,
.league-page.color-alt-greenLight #sidebarHomePage .heading {
    background: #60993A !important
}

.color-alt-greenLight .word-main-article .talkbacks-svg svg,
.color-alt-greenLight .post-container .world-arrow {
    fill: #60993A !important;
    color: #60993A
}

.color-alt-greenLight .word-main-article .talkbacks-svg .messages-count a,
.league-page.color-alt-greenLight .result-form a.open,
.league-page.color-alt-greenLight .result-form a.openYearList {
    color: #60993A !important
}

.league-page.color-alt-grey .tab-box .tabset {
    background: #3f454d
}

.league-page.color-alt-grey .tab-box-list,
.league-page.color-alt-grey .tab-box .tabset li a:hover,
.league-page.color-alt-grey .tab-box .tabset li a.active,
.color-alt-grey .world-title,
.league-page.color-alt-grey #sidebarHomePage .heading {
    background: #59616c !important
}

.color-alt-grey .word-main-article .talkbacks-svg svg,
.color-alt-grey .post-container .world-arrow {
    fill: #59616c !important;
    color: #59616c
}

.color-alt-grey .word-main-article .talkbacks-svg .messages-count a,
.league-page.color-alt-grey .result-form a.open,
.league-page.color-alt-grey .result-form a.openYearList {
    color: #59616c !important
}

.league-page.color-alt-gold .tab-box .tabset {
    background: #691e32
}

.league-page.color-alt-gold .tab-box-list,
.league-page.color-alt-gold .tab-box .tabset li a:hover,
.league-page.color-alt-gold .tab-box .tabset li a.active,
.color-alt-gold .world-title,
.league-page.color-alt-gold #sidebarHomePage .heading {
    background: #691e32 !important
}

.color-alt-gold .word-main-article .talkbacks-svg svg,
.color-alt-gold .post-container .world-arrow {
    fill: #691e32 !important;
    color: #691e32
}

.color-alt-gold .word-main-article .talkbacks-svg .messages-count a,
.league-page.color-alt-gold .result-form a.open,
.league-page.color-alt-gold .result-form a.openYearList {
    color: #691e32 !important
}

.word-main-article.banner-holder .talkbacks-container a,
.word-main-article.banner-holder .talkbacks-container em.date {
    color: #000 !important;
    font-size: 12px !important;
    display: inline;
    margin-top: 1px
}

#main .word-main-article.banner-holder .mainarticle-img {
    width: 475px;
    height: 346px
}

.word-main-article.banner-holder {
    margin: 5px 0 0 0
}

.world-page .world-line,
.league-page .world-line {
    height: 10px;
    width: 100%;
    border-top: 1px solid #e8e8e8
}

.world-page .post-container a.show-all,
.league-page .post-container a.show-all {
    font-size: 18px;
    margin: 6px 0 0 0;
    font-family: 'blender-regular'
}

.world-page .post-container a.show-all:before,
.league-page .post-container a.show-all:before {
    background-image: unset
}

.world-page .post-container .world-arrow,
.league-page .post-container .world-arrow {
    float: left;
    margin: 0 10px
}

.league-page .show-all {
    float: left;
    position: relative;
    margin: 0 0 5px;
    color: #014bad;
    font-size: 14px;
    line-height: 15px;
    padding: 0 0 0 12px;
    font-family: 'blender-bold' !important
}

.league-page .result-form {
    background-image: unset;
    display: inline-block;
    height: 35px;
    width: 100%;
    padding: 0 15px 0 0
}

.league-page .result-form span.open {
    font-family: 'blender-bold';
    font-size: 16px;
    color: #000;
    padding: 0;
    margin-top: 0 !important
}

.league-page .result-form select {
    font-family: 'blender-regular';
    font-size: 18px;
    color: #000;
    width: 93px;
    height: 25px;
    margin-top: 0 !important
}

.league-page #main .result-form h3 {
    font-family: 'blender-regular';
    float: right;
    font-size: 15px;
    line-height: 34px;
    color: #000;
    text-align: right;
    margin: 2px 0 0 0;
    max-width: 300px;
    width: 300px;
    font-weight: bold
}

select[name$="teamSelect"] {
    width: 125px !important;
    font-size: 14px !important
}

select[id$="cycleSelect"] {
    width: 120px !important;
    font-size: 14px !important
}

.league-page .result-form .select-holder {
    float: right;
    padding: 8px 14px 4px 10px;
    border-right: 0 solid #e0e0e0
}

.league-page .result-form a.open {
    font-family: 'blender-bold' !important;
    font-size: 16px;
    margin: 9px 10px;
    display: inline-block
}

.league-page .result-form a.openYearList {
    font-family: 'blender-bold' !important;
    font-size: 16px;
    margin: 2px 10px;
    display: inline-block
}

.league-page .result-table,
.league-page .score-list table {
    border: solid #bfbfbf 1px;
    border-bottom: solid #bfbfbf 2px
}

.league-page .score-list table tr {
    border: unset
}

.league-page .score-list .title-block {
    font-family: 'blender-bold';
    color: #000;
    font-size: 18px;
    border-left: solid #bfbfbf 1px;
    border-right: solid #bfbfbf 1px
}

.league-page .score-list table tr th {
    font-family: 'blender-bold';
    color: #000;
    font-size: 16px;
    border: solid 0 !important
}

.league-page table.result-table tr th {
    background-image: unset !important;
    border: solid 0 !important
}

.league-page table.result-table tr th strong {
    background-image: unset !important;
    font-family: 'blender-bold' !important
}

.league-page .score-list table tr td,
.goal-list table td {
    background-color: #F9F9F9 !important;
    height: 39px;
    font-family: 'blender-regular';
    color: #000;
    font-size: 14px
}

.league-page .goal-list li {
    margin: 0 0 40px 20px
}

.league-page .goal-list table td {
    font-size: 12px
}

.league-page .tab-box .result-table td {
    background: #F9F9F9
}

.league-page .goal-list table th,
.modal-cont table th {
    font-weight: bold
}

.league-page .goal-list table th:first-child {
    width: 85px;
    padding: 0 8px 0 0;
    text-align: right
}

.league-page .score-list table tr.alt td,
.league-page .goal-list table tr.alt td {
    background-color: #FFF !important;
    height: 39px;
    font-family: 'blender-regular'
}

.league-page .results-holder .result-table th,
.league-page .tab-box .result-table th,
.league-page #main .score-list table th,
.league-page .goal-list table th,
.league-page .modal-cont table th {
    color: #000
}

.goal-list table td.score,
.modal-cont table td.score {
    font-size: 14px
}

.league-page .score-list {
    border: solid 0
}

.league-page #main .score-list .menu {
    padding: 0;
    margin: 15px 0 0 0
}

.league-page .score-list .menu,
.league-page .score-list .title-block strong {
    background-image: unset !important;
    padding: 5px 16px 4px
}

.league-page .score-list .menu a {
    font-family: 'blender-bold' !important;
    font-size: 16px;
    display: inline-block
}

.league-page .score-list .menu a.prev {
    margin: 0 -16px !important
}

.league-page .score-list .menu a.next {
    margin: 0 -7px !important
}

.league-page .score-list .menu a svg {
    margin: 0 5px
}

.league-page .score-list .menu strong.title {
    font-family: 'blender-bold';
    font-size: 16px;
    color: #000
}

.league-page .tab-box .heading {
    border: unset !important
}

.league-page .tab-box .tabset {
    border: solid 0;
    height: 30px;
    width: 100%
}

.league-page .tab-box .tab-box-list {
    display: inline-block;
    background-color: inherit;
    width: 100%
}

.league-page .tab-box.ranking-tables .tab-box-list .tabset,
.league-page .tab-box.stage-games .tab-box-list .tabset {
    display: inline !important
}

.league-page .tab-box .tabset li {
    border: solid 0;
    height: 100% !important;
    background: inherit;
    padding: 0;
    border: solid #fff;
    border-width: 0 0 1px 1px
}

.league-page .tab-box .tabset li a {
    color: #fff;
    opacity: .6;
    font-size: 18px;
    height: 100%;
    width: unset;
    display: inline-block;
    background: inherit;
    white-space: nowrap;
    border: solid 0;
    padding: 4px 7px 0 7px
}

.league-page .tab-box .tabset li a:hover,
.league-page .tab-box .tabset li a.active {
    opacity: 1.0 !important
}

.league-page .logos-holder li img {
    height: 81px;
    width: auto
}

.league-page .logos-holder {
    border: 1px solid #c3cad1
}

.people-blockHomepage.center .gallery-button.prev {
    top: 32%
}

.people-blockHomepage.center .gallery-button.next {
    top: 32%
}

.world-page #main .article-list img {
    display: block;
    display: block;
    width: 210px;
    height: 156px
}

.world-page #main .article-list-alt {
    width: 100%
}

.league-page #main .article-list img {
    display: block;
    display: block;
    width: 210px;
    height: 156px
}

.league-page #main .article-list-alt {
    width: 100%
}

.league-page .score-list table td:first-child {
    width: 32px
}

.league-page #sidebarHomePage .heading {
    border: 0 solid #fff
}

.league-page .main-block .tab-box .tab-content {
    border-width: 0 0 1px
}

.league-page .people-blockHomepage.center .video-list {
    padding-bottom: 0
}

.league-page .result-form input[type="submit"],
.league-page .result-form input[type="button"] {
    float: right;
    font-size: 17px;
    font-family: 'blender-bold';
    color: #014bad;
    border: 0 solid #e5e5e5;
    cursor: pointer;
    padding: 0;
    background: 0;
    margin: 7px 1px
}

.league-page .result-form .openYearList {
    margin: 0 1px !important
}

.teamSlick-Logos.center {
    width: 100%;
    position: relative;
    direction: ltr;
    background-color: #fff;
    height: 81px
}

.teamSlick-Logos.center .list {
    height: 81px;
    width: 98%;
    text-align: center;
    margin: 0 15px
}

.teamSlick-Logos.center .list .video-box {
    margin-right: 30px !important
}

.teamSlick-Logos.center .gallery-button.prev {
    background: url(images/arrow_games_L.png) no-repeat;
    position: absolute;
    top: 35%;
    left: 0;
    cursor: pointer;
    height: 20px;
    width: 13px;
    z-index: 10
}

.teamSlick-Logos.center .gallery-button.next {
    background: url(images/arrow_games_R.png) no-repeat;
    position: absolute;
    top: 35%;
    right: 0;
    cursor: pointer;
    height: 20px;
    width: 13px;
    z-index: 10
}

.teamGroupSlick-Logos.center {
    width: 100%;
    position: relative;
    direction: ltr;
    background-color: #fff;
    height: 81px
}

.teamGroupSlick-Logos.center .list {
    height: 81px;
    width: 98%;
    text-align: center;
    margin: 0 15px
}

.teamGroupSlick-Logos.center .list .video-box {
    margin-right: 30px !important
}

.teamGroupSlick-Logos.center .gallery-button.prev {
    background: url(images/arrow_games_L.png) no-repeat;
    position: absolute;
    top: 35%;
    left: 0;
    cursor: pointer;
    height: 20px;
    width: 13px;
    z-index: 10
}

.teamGroupSlick-Logos.center .gallery-button.next {
    background: url(images/arrow_games_R.png) no-repeat;
    position: absolute;
    top: 35%;
    right: 0;
    cursor: pointer;
    height: 20px;
    width: 13px;
    z-index: 10
}

#header.flat strong.logo {
    position: absolute;
    z-index: 999;
    margin: -4px 0 0 -11px
}

.article-page #header.flat strong.logo {
    margin: 2px 0 0 -11px
}

.league-page #header.flat strong.logo {
    margin: -2px 0 0 -11px
}

.world-page #header.flat strong.logo {
    margin: -2px 0 0 -11px
}

#header.flat h1.logo {
    position: absolute;
    z-index: 999;
    margin: -3px 0 0 -11px
}

#header.flat {
    bottom: 17px;
    position: relative;
    height: 176px
}

.main-bar.flat {
    background: #e5005c
}

.flat .holder {
    width: 1094px
}

.flat .holder .pin {
    right: 0;
    background: #95022E;
    color: #e5005c;
    border-radius: 25px;
    width: 57px;
    border: solid 2px #fff
}

.flat .holder .pin span {
    color: white;
    font-weight: bold;
    float: left;
    width: 26px;
    display: block;
    margin: -1px 0 0 2px;
    font-size: 9pt
}

.flat .holder .pin img {
    margin: -1px 0 -1px 0
}

.flat .holder .time {
    background: 0;
    right: 70px;
    position: absolute
}

.flat .holder .time strong {
    letter-spacing: unset;
    font-family: 'blender-regular', 'Mukta-ExtraBold', 'south web light', Arial, Helvetica, sans-serif;
    font-size: 20px
}

.flat .holder .time .date {
    background: 0;
    font-family: 'blender-regular', 'Mukta-ExtraBold', 'south web light', Arial, Helvetica, sans-serif;
    font-size: 14px
}

.flat .holder .gallery4 {
    background: unset;
    right: 135px
}

.flat .holder .gallery4 .btn-prev {
    background: url(images/arrow_r_white.png) no-repeat;
    background-position: 50%;
    background-size: 13%;
    cursor: pointer
}

.flat .holder .gallery4 .btn-next {
    background: url(images/arrow_l_white.png) no-repeat;
    background-position: 50%;
    background-size: 13%;
    cursor: pointer
}

.flat .holder .gallery4 .gmask {
    width: 840px
}

.flat .holder .gallery4 .gmask ul li {
    width: 280px
}

.flat .holder .gallery4 .gmask ul li .holder {
    width: 280px
}

.flat .holder .gallery4 .gmask ul li .holder:before {
    background: 0
}

.flat .holder .gallery4 .gmask ul li .holder .score .num {
    background: 0;
    font-size: 11pt;
    width: 22%
}

.flat .holder .scoreboard {
    color: white !IMPORTANT;
    background: #910029;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light', Arial, Helvetica, sans-serif;
    font-size: 12pt;
    border: 0;
    box-shadow: none;
    padding: 4px 8px 4px 7px;
    right: 130px;
    border-radius: 25px;
    border: white 3px solid;
    top: 13px
}

.flat .holder .scoreboard:before {
    background: 0
}

.flat .holder.scoreboard:hover {
    color: #e5005c
}

#header.flat {
    padding-top: 75px
}

#header.flat .header-holder {
    width: 100vw;
    height: 85px
}

.flat .header-holder #nav {
    width: 100vw;
    float: unset;
    bottom: 3px;
    margin: 0 60px 0 0;
    z-index: 0
}

.hedaer-no-bottom-padding .flat .header-holder #nav {
    margin: 0 60px 0 0
}

.flat .header-holder #nav .bg:before {
    background: 0
}

.flat #nav ul a {
    color: black;
    padding: 15px 0 0 0;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light', Arial, Helvetica, sans-serif;
    font-size: 15px;
    height: 43px
}

.flat #nav>ul {
    width: 100vw;
    height: 43px;
    float: unset;
    padding-right: 45px;
    background: #fff
}

.flat #nav>ul>li {
    min-width: unset;
    margin: 0
}

#header.flat .header-holder .sub-nav {
    width: 100vw;
    float: unset;
    z-index: 999;
    position: absolute
}

.flat .add-nav {
    border-bottom: 0;
    background: white;
    display: none;
    padding: 0;
    margin: 0;
    top: 96px
}

.flat .add-nav.shown {
    display: block;
    z-index: 9999;
    position: fixed
}

.flat .add-nav:after {
    background: 0
}

.flat .add-nav>ul {
    background-color: white;
    width: 150px
}

.flat .add-nav>ul:before {
    background: 0
}

.flat .add-nav>ul>li {
    float: unset;
    padding: 17px 7px 17px 5px
}

.flat .add-nav>ul>li:before {
    background: 0
}

.flat .add-nav>ul>li>a {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web ultralight', Arial, Helvetica, sans-serif;
    font-size: 11pt
}

.flat .add-nav>ul>li.active:after {
    background: 0
}

#header.flat .menu {
    width: 100vw;
    position: absolute;
    top: 90px;
    right: 120px
}

#header.flat.general-header .menu {
    right: 0
}

#header.flat .menu ul {
    width: 100%;
    line-height: 38px
}

#header.flat .menu ul.no-level-2 {
    left: unset
}

#header.flat .menu ul li {
    margin-left: 0;
    padding: 0
}

#header.flat .menu ul li:before {
    background: 0
}

#header.flat .menu ul.no-level-2 li {
    margin-left: 25px
}

.flat .level-2-links a {
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web light', Arial, Helvetica, sans-serif;
    color: black;
    font-size: 13px
}

#header.flat level-2-links ul {
    list-style: none
}

#header.flat .header-holder .menu .level-2-links {
    width: 100%;
    margin-bottom: 10px;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web ultralight', Arial, Helvetica, sans-serif;
    font-weight: bold;
    left: unset;
    height: 29px
}

#facebook-btn {
    bottom: 0
}

#header.flat .header-holder .menu .level-2-links li {
    margin-left: 15px
}

#header.flat .menu ul:nth-child(3) li:first-child {
    bottom: 11px
}

#header.flat .header-holder .menu .level-2-links.no-level-2 {
    display: none
}

.small-header-spacing {
    height: 15px;
    display: block
}

.big-header-spacing {
    height: 55px;
    display: block
}

#level-3-links {
    left: 105px;
    margin: 10px 0;
    float: right !important;
    background-color: #f6f6f6;
    width: 970px !important
}

#header.flat.world-header .menu {
    top: 135px
}

.spacing-div {
    height: 35px;
    display: block
}

.add-nav-liga {
    top: 5px;
    right: 65px;
    position: relative;
    font-family: 'blender-bold', 'Mukta-ExtraBold', 'south web ultralight', Arial, Helvetica, sans-serif;
    font-weight: bold;
    z-index: 999
}

.add-nav-liga>ul {
    list-style: none;
    top: 3px;
    width: 100vw
}

.add-nav-liga ul li {
    display: inline-block;
    margin-left: 23px;
    width: auto;
    line-height: 12px
}

.flat#header .menu ul li::before {
    background: 0
}

.add-nav-liga .more-ligas {
    display: none;
    position: absolute;
    top: 25px;
    width: 150px
}

.more-ligas-ul {
    background-color: white;
    padding: unset;
    width: 130px;
    margin-top: 20px;
    float: right
}

.more-ligas-ul li {
    background-color: white;
    margin: 16px 10px
}

#more-ligas-here {
    display: inline-flex
}

#more-ligas-anc {
    pointer-events: none;
    cursor: default
}

.slick-slider {
    position: relative;
    display: block;
    box-sizing: border-box;
    -moz-box-sizing: border-box;
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    -ms-touch-action: pan-y;
    touch-action: pan-y;
    -webkit-tap-highlight-color: transparent
}

.slick-list {
    position: relative;
    overflow: hidden;
    display: block;
    margin: 0;
    padding: 0
}

.slick-list:focus {
    outline: 0
}

.slick-list.dragging {
    cursor: pointer;
    cursor: hand
}

.slick-slider .slick-track {
    -webkit-transform: translate3d(0, 0, 0);
    -moz-transform: translate3d(0, 0, 0);
    -ms-transform: translate3d(0, 0, 0);
    -o-transform: translate3d(0, 0, 0);
    transform: translate3d(0, 0, 0)
}

.slick-track {
    position: relative;
    left: 0;
    top: 0;
    display: block
}

.slick-track:before,
.slick-track:after {
    content: "";
    display: table
}

.slick-track:after {
    clear: both
}

.slick-loading .slick-track {
    visibility: hidden
}

.slick-slide {
    float: right;
    height: 100%;
    min-height: 1px;
    display: none
}

[dir="rtl"] .slick-slide {
    float: right
}

.slick-slide img {
    display: block
}

.slick-slide.slick-loading img {
    display: none
}

.slick-slide.dragging img {
    pointer-events: none
}

.slick-initialized .slick-slide {
    display: block
}

.slick-loading .slick-slide {
    visibility: hidden
}

.slick-vertical .slick-slide {
    display: block;
    height: auto;
    border: 1px solid transparent
}

.slick-prev,
.slick-next {
    position: absolute;
    display: block;
    height: 20px;
    width: 20px;
    line-height: 0;
    font-size: 0;
    cursor: pointer;
    background: transparent;
    color: transparent;
    top: 50%;
    margin-top: -10px;
    padding: 0;
    border: 0;
    outline: 0
}

.slick-prev:hover,
.slick-prev:focus,
.slick-next:hover,
.slick-next:focus {
    outline: 0;
    background: transparent;
    color: transparent
}

.slick-prev:hover:before,
.slick-prev:focus:before,
.slick-next:hover:before,
.slick-next:focus:before {
    opacity: 1
}

.slick-prev.slick-disabled:before,
.slick-next.slick-disabled:before {
    opacity: .25
}

.slick-prev:before,
.slick-next:before {
    font-family: "slick";
    font-size: 20px;
    line-height: 1;
    color: white;
    opacity: .75;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale
}

.slick-prev {
    left: -25px
}

[dir="rtl"] .slick-prev {
    left: auto;
    right: -25px
}

.slick-prev:before {
    content: "←"
}

[dir="rtl"] .slick-prev:before {
    content: "→"
}

.slick-next {
    right: -25px
}

[dir="rtl"] .slick-next {
    left: -25px;
    right: auto
}

.slick-next:before {
    content: "→"
}

[dir="rtl"] .slick-next:before {
    content: "←"
}

.slick-dots {
    position: absolute;
    bottom: -45px;
    list-style: none;
    display: block;
    text-align: center;
    padding: 0;
    width: 100%
}

.slick-dots li {
    position: relative;
    display: inline-block;
    height: 20px;
    width: 20px;
    margin: 0 5px;
    padding: 0;
    cursor: pointer
}

.slick-dots li button {
    border: 0;
    background: transparent;
    display: block;
    height: 20px;
    width: 20px;
    outline: 0;
    line-height: 0;
    font-size: 0;
    color: transparent;
    padding: 5px;
    cursor: pointer
}

.slick-dots li button:hover,
.slick-dots li button:focus {
    outline: 0
}

.slick-dots li button:hover:before,
.slick-dots li button:focus:before {
    opacity: 1
}

.slick-dots li button:before {
    position: absolute;
    top: 0;
    left: 0;
    content: "•";
    width: 20px;
    height: 20px;
    font-family: "slick";
    font-size: 6px;
    line-height: 20px;
    text-align: center;
    color: black;
    opacity: .25;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale
}

.slick-dots li.slick-active button:before {
    color: black;
    opacity: .75
}

/*!
 * Bootstrap v3.3.7 (http://getbootstrap.com)
 * Copyright 2011-2016 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 */
/*! normalize.css v3.0.3 | MIT License | github.com/necolas/normalize.css */
html {
    font-family: sans-serif;
    -webkit-text-size-adjust: 100%;
    -ms-text-size-adjust: 100%
}

body {
    margin: 0
}

article,
aside,
details,
figcaption,
figure,
footer,
header,
hgroup,
main,
menu,
nav,
section,
summary {
    display: block
}

audio,
canvas,
progress,
video {
    display: inline-block;
    vertical-align: baseline
}

audio:not([controls]) {
    display: none;
    height: 0
}

[hidden],
template {
    display: none
}

a {
    background-color: transparent
}

a:active,
a:hover {
    outline: 0
}

abbr[title] {
    border-bottom: 1px dotted
}

b,
strong {
    font-weight: 700
}

dfn {
    font-style: italic
}

h1 {
    margin: .67em 0;
    font-size: 2em
}

mark {
    color: #000;
    background: #ff0
}

small {
    font-size: 80%
}

sub,
sup {
    position: relative;
    font-size: 75%;
    line-height: 0;
    vertical-align: baseline
}

sup {
    top: -.5em
}

sub {
    bottom: -.25em
}

img {
    border: 0
}

svg:not(:root) {
    overflow: hidden
}

figure {
    margin: 1em 40px
}

hr {
    height: 0;
    -webkit-box-sizing: content-box;
    -moz-box-sizing: content-box;
    box-sizing: content-box
}

pre {
    overflow: auto
}

code,
kbd,
pre,
samp {
    font-family: monospace, monospace;
    font-size: 1em
}

button,
input,
optgroup,
select,
textarea {
    margin: 0;
    font: inherit;
    color: inherit
}

button {
    overflow: visible
}

button,
select {
    text-transform: none
}

button,
html input[type=button],
input[type=reset],
input[type=submit] {
    -webkit-appearance: button;
    cursor: pointer
}

button[disabled],
html input[disabled] {
    cursor: default
}

button::-moz-focus-inner,
input::-moz-focus-inner {
    padding: 0;
    border: 0
}

input {
    line-height: normal
}

input[type=checkbox],
input[type=radio] {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    padding: 0
}

input[type=number]::-webkit-inner-spin-button,
input[type=number]::-webkit-outer-spin-button {
    height: auto
}

input[type=search] {
    -webkit-box-sizing: content-box;
    -moz-box-sizing: content-box;
    box-sizing: content-box;
    -webkit-appearance: textfield
}

input[type=search]::-webkit-search-cancel-button,
input[type=search]::-webkit-search-decoration {
    -webkit-appearance: none
}

fieldset {
    padding: .35em .625em .75em;
    margin: 0 2px;
    border: 1px solid silver
}

legend {
    padding: 0;
    border: 0
}

textarea {
    overflow: auto
}

optgroup {
    font-weight: 700
}

table {
    border-spacing: 0;
    border-collapse: collapse
}

td,
th {
    padding: 0
}

/*! Source: https://github.com/h5bp/html5-boilerplate/blob/master/src/css/main.css */
@media print {

    *,
    :after,
    :before {
        color: #000 !important;
        text-shadow: none !important;
        background: 0 0 !important;
        -webkit-box-shadow: none !important;
        box-shadow: none !important
    }

    a,
    a:visited {
        text-decoration: underline
    }

    a[href]:after {
        content: " (" attr(href) ")"
    }

    abbr[title]:after {
        content: " (" attr(title) ")"
    }

    a[href^="javascript:"]:after,
    a[href^="#"]:after {
        content: ""
    }

    blockquote,
    pre {
        border: 1px solid #999;
        page-break-inside: avoid
    }

    thead {
        display: table-header-group
    }

    img,
    tr {
        page-break-inside: avoid
    }

    img {
        max-width: 100% !important
    }

    h2,
    h3,
    p {
        orphans: 3;
        widows: 3
    }

    h2,
    h3 {
        page-break-after: avoid
    }

    .navbar {
        display: none
    }

    .btn>.caret,
    .dropup>.btn>.caret {
        border-top-color: #000 !important
    }

    .label {
        border: 1px solid #000
    }

    .table {
        border-collapse: collapse !important
    }

    .table td,
    .table th {
        background-color: #fff !important
    }

    .table-bordered td,
    .table-bordered th {
        border: 1px solid #ddd !important
    }
}


.glyphicon {
    position: relative;
    top: 1px;
    display: inline-block;
    font-family: 'Glyphicons Halflings';
    font-style: normal;
    font-weight: 400;
    line-height: 1;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale
}

.glyphicon-asterisk:before {
    content: "\002a"
}

.glyphicon-plus:before {
    content: "\002b"
}

.glyphicon-eur:before,
.glyphicon-euro:before {
    content: "\20ac"
}

.glyphicon-minus:before {
    content: "\2212"
}

.glyphicon-cloud:before {
    content: "\2601"
}

.glyphicon-envelope:before {
    content: "\2709"
}

.glyphicon-pencil:before {
    content: "\270f"
}

.glyphicon-glass:before {
    content: "\e001"
}

.glyphicon-music:before {
    content: "\e002"
}

.glyphicon-search:before {
    content: "\e003"
}

.glyphicon-heart:before {
    content: "\e005"
}

.glyphicon-star:before {
    content: "\e006"
}

.glyphicon-star-empty:before {
    content: "\e007"
}

.glyphicon-user:before {
    content: "\e008"
}

.glyphicon-film:before {
    content: "\e009"
}

.glyphicon-th-large:before {
    content: "\e010"
}

.glyphicon-th:before {
    content: "\e011"
}

.glyphicon-th-list:before {
    content: "\e012"
}

.glyphicon-ok:before {
    content: "\e013"
}

.glyphicon-remove:before {
    content: "\e014"
}

.glyphicon-zoom-in:before {
    content: "\e015"
}

.glyphicon-zoom-out:before {
    content: "\e016"
}

.glyphicon-off:before {
    content: "\e017"
}

.glyphicon-signal:before {
    content: "\e018"
}

.glyphicon-cog:before {
    content: "\e019"
}

.glyphicon-trash:before {
    content: "\e020"
}

.glyphicon-home:before {
    content: "\e021"
}

.glyphicon-file:before {
    content: "\e022"
}

.glyphicon-time:before {
    content: "\e023"
}

.glyphicon-road:before {
    content: "\e024"
}

.glyphicon-download-alt:before {
    content: "\e025"
}

.glyphicon-download:before {
    content: "\e026"
}

.glyphicon-upload:before {
    content: "\e027"
}

.glyphicon-inbox:before {
    content: "\e028"
}

.glyphicon-play-circle:before {
    content: "\e029"
}

.glyphicon-repeat:before {
    content: "\e030"
}

.glyphicon-refresh:before {
    content: "\e031"
}

.glyphicon-list-alt:before {
    content: "\e032"
}

.glyphicon-lock:before {
    content: "\e033"
}

.glyphicon-flag:before {
    content: "\e034"
}

.glyphicon-headphones:before {
    content: "\e035"
}

.glyphicon-volume-off:before {
    content: "\e036"
}

.glyphicon-volume-down:before {
    content: "\e037"
}

.glyphicon-volume-up:before {
    content: "\e038"
}

.glyphicon-qrcode:before {
    content: "\e039"
}

.glyphicon-barcode:before {
    content: "\e040"
}

.glyphicon-tag:before {
    content: "\e041"
}

.glyphicon-tags:before {
    content: "\e042"
}

.glyphicon-book:before {
    content: "\e043"
}

.glyphicon-bookmark:before {
    content: "\e044"
}

.glyphicon-print:before {
    content: "\e045"
}

.glyphicon-camera:before {
    content: "\e046"
}

.glyphicon-font:before {
    content: "\e047"
}

.glyphicon-bold:before {
    content: "\e048"
}

.glyphicon-italic:before {
    content: "\e049"
}

.glyphicon-text-height:before {
    content: "\e050"
}

.glyphicon-text-width:before {
    content: "\e051"
}

.glyphicon-align-left:before {
    content: "\e052"
}

.glyphicon-align-center:before {
    content: "\e053"
}

.glyphicon-align-right:before {
    content: "\e054"
}

.glyphicon-align-justify:before {
    content: "\e055"
}

.glyphicon-list:before {
    content: "\e056"
}

.glyphicon-indent-left:before {
    content: "\e057"
}

.glyphicon-indent-right:before {
    content: "\e058"
}

.glyphicon-facetime-video:before {
    content: "\e059"
}

.glyphicon-picture:before {
    content: "\e060"
}

.glyphicon-map-marker:before {
    content: "\e062"
}

.glyphicon-adjust:before {
    content: "\e063"
}

.glyphicon-tint:before {
    content: "\e064"
}

.glyphicon-edit:before {
    content: "\e065"
}

.glyphicon-share:before {
    content: "\e066"
}

.glyphicon-check:before {
    content: "\e067"
}

.glyphicon-move:before {
    content: "\e068"
}

.glyphicon-step-backward:before {
    content: "\e069"
}

.glyphicon-fast-backward:before {
    content: "\e070"
}

.glyphicon-backward:before {
    content: "\e071"
}

.glyphicon-play:before {
    content: "\e072"
}

.glyphicon-pause:before {
    content: "\e073"
}

.glyphicon-stop:before {
    content: "\e074"
}

.glyphicon-forward:before {
    content: "\e075"
}

.glyphicon-fast-forward:before {
    content: "\e076"
}

.glyphicon-step-forward:before {
    content: "\e077"
}

.glyphicon-eject:before {
    content: "\e078"
}

.glyphicon-chevron-left:before {
    content: "\e079"
}

.glyphicon-chevron-right:before {
    content: "\e080"
}

.glyphicon-plus-sign:before {
    content: "\e081"
}

.glyphicon-minus-sign:before {
    content: "\e082"
}

.glyphicon-remove-sign:before {
    content: "\e083"
}

.glyphicon-ok-sign:before {
    content: "\e084"
}

.glyphicon-question-sign:before {
    content: "\e085"
}

.glyphicon-info-sign:before {
    content: "\e086"
}

.glyphicon-screenshot:before {
    content: "\e087"
}

.glyphicon-remove-circle:before {
    content: "\e088"
}

.glyphicon-ok-circle:before {
    content: "\e089"
}

.glyphicon-ban-circle:before {
    content: "\e090"
}

.glyphicon-arrow-left:before {
    content: "\e091"
}

.glyphicon-arrow-right:before {
    content: "\e092"
}

.glyphicon-arrow-up:before {
    content: "\e093"
}

.glyphicon-arrow-down:before {
    content: "\e094"
}

.glyphicon-share-alt:before {
    content: "\e095"
}

.glyphicon-resize-full:before {
    content: "\e096"
}

.glyphicon-resize-small:before {
    content: "\e097"
}

.glyphicon-exclamation-sign:before {
    content: "\e101"
}

.glyphicon-gift:before {
    content: "\e102"
}

.glyphicon-leaf:before {
    content: "\e103"
}

.glyphicon-fire:before {
    content: "\e104"
}

.glyphicon-eye-open:before {
    content: "\e105"
}

.glyphicon-eye-close:before {
    content: "\e106"
}

.glyphicon-warning-sign:before {
    content: "\e107"
}

.glyphicon-plane:before {
    content: "\e108"
}

.glyphicon-calendar:before {
    content: "\e109"
}

.glyphicon-random:before {
    content: "\e110"
}

.glyphicon-comment:before {
    content: "\e111"
}

.glyphicon-magnet:before {
    content: "\e112"
}

.glyphicon-chevron-up:before {
    content: "\e113"
}

.glyphicon-chevron-down:before {
    content: "\e114"
}

.glyphicon-retweet:before {
    content: "\e115"
}

.glyphicon-shopping-cart:before {
    content: "\e116"
}

.glyphicon-folder-close:before {
    content: "\e117"
}

.glyphicon-folder-open:before {
    content: "\e118"
}

.glyphicon-resize-vertical:before {
    content: "\e119"
}

.glyphicon-resize-horizontal:before {
    content: "\e120"
}

.glyphicon-hdd:before {
    content: "\e121"
}

.glyphicon-bullhorn:before {
    content: "\e122"
}

.glyphicon-bell:before {
    content: "\e123"
}

.glyphicon-certificate:before {
    content: "\e124"
}

.glyphicon-thumbs-up:before {
    content: "\e125"
}

.glyphicon-thumbs-down:before {
    content: "\e126"
}

.glyphicon-hand-right:before {
    content: "\e127"
}

.glyphicon-hand-left:before {
    content: "\e128"
}

.glyphicon-hand-up:before {
    content: "\e129"
}

.glyphicon-hand-down:before {
    content: "\e130"
}

.glyphicon-circle-arrow-right:before {
    content: "\e131"
}

.glyphicon-circle-arrow-left:before {
    content: "\e132"
}

.glyphicon-circle-arrow-up:before {
    content: "\e133"
}

.glyphicon-circle-arrow-down:before {
    content: "\e134"
}

.glyphicon-globe:before {
    content: "\e135"
}

.glyphicon-wrench:before {
    content: "\e136"
}

.glyphicon-tasks:before {
    content: "\e137"
}

.glyphicon-filter:before {
    content: "\e138"
}

.glyphicon-briefcase:before {
    content: "\e139"
}

.glyphicon-fullscreen:before {
    content: "\e140"
}

.glyphicon-dashboard:before {
    content: "\e141"
}

.glyphicon-paperclip:before {
    content: "\e142"
}

.glyphicon-heart-empty:before {
    content: "\e143"
}

.glyphicon-link:before {
    content: "\e144"
}

.glyphicon-phone:before {
    content: "\e145"
}

.glyphicon-pushpin:before {
    content: "\e146"
}

.glyphicon-usd:before {
    content: "\e148"
}

.glyphicon-gbp:before {
    content: "\e149"
}

.glyphicon-sort:before {
    content: "\e150"
}

.glyphicon-sort-by-alphabet:before {
    content: "\e151"
}

.glyphicon-sort-by-alphabet-alt:before {
    content: "\e152"
}

.glyphicon-sort-by-order:before {
    content: "\e153"
}

.glyphicon-sort-by-order-alt:before {
    content: "\e154"
}

.glyphicon-sort-by-attributes:before {
    content: "\e155"
}

.glyphicon-sort-by-attributes-alt:before {
    content: "\e156"
}

.glyphicon-unchecked:before {
    content: "\e157"
}

.glyphicon-expand:before {
    content: "\e158"
}

.glyphicon-collapse-down:before {
    content: "\e159"
}

.glyphicon-collapse-up:before {
    content: "\e160"
}

.glyphicon-log-in:before {
    content: "\e161"
}

.glyphicon-flash:before {
    content: "\e162"
}

.glyphicon-log-out:before {
    content: "\e163"
}

.glyphicon-new-window:before {
    content: "\e164"
}

.glyphicon-record:before {
    content: "\e165"
}

.glyphicon-save:before {
    content: "\e166"
}

.glyphicon-open:before {
    content: "\e167"
}

.glyphicon-saved:before {
    content: "\e168"
}

.glyphicon-import:before {
    content: "\e169"
}

.glyphicon-export:before {
    content: "\e170"
}

.glyphicon-send:before {
    content: "\e171"
}

.glyphicon-floppy-disk:before {
    content: "\e172"
}

.glyphicon-floppy-saved:before {
    content: "\e173"
}

.glyphicon-floppy-remove:before {
    content: "\e174"
}

.glyphicon-floppy-save:before {
    content: "\e175"
}

.glyphicon-floppy-open:before {
    content: "\e176"
}

.glyphicon-credit-card:before {
    content: "\e177"
}

.glyphicon-transfer:before {
    content: "\e178"
}

.glyphicon-cutlery:before {
    content: "\e179"
}

.glyphicon-header:before {
    content: "\e180"
}

.glyphicon-compressed:before {
    content: "\e181"
}

.glyphicon-earphone:before {
    content: "\e182"
}

.glyphicon-phone-alt:before {
    content: "\e183"
}

.glyphicon-tower:before {
    content: "\e184"
}

.glyphicon-stats:before {
    content: "\e185"
}

.glyphicon-sd-video:before {
    content: "\e186"
}

.glyphicon-hd-video:before {
    content: "\e187"
}

.glyphicon-subtitles:before {
    content: "\e188"
}

.glyphicon-sound-stereo:before {
    content: "\e189"
}

.glyphicon-sound-dolby:before {
    content: "\e190"
}

.glyphicon-sound-5-1:before {
    content: "\e191"
}

.glyphicon-sound-6-1:before {
    content: "\e192"
}

.glyphicon-sound-7-1:before {
    content: "\e193"
}

.glyphicon-copyright-mark:before {
    content: "\e194"
}

.glyphicon-registration-mark:before {
    content: "\e195"
}

.glyphicon-cloud-download:before {
    content: "\e197"
}

.glyphicon-cloud-upload:before {
    content: "\e198"
}

.glyphicon-tree-conifer:before {
    content: "\e199"
}

.glyphicon-tree-deciduous:before {
    content: "\e200"
}

.glyphicon-cd:before {
    content: "\e201"
}

.glyphicon-save-file:before {
    content: "\e202"
}

.glyphicon-open-file:before {
    content: "\e203"
}

.glyphicon-level-up:before {
    content: "\e204"
}

.glyphicon-copy:before {
    content: "\e205"
}

.glyphicon-paste:before {
    content: "\e206"
}

.glyphicon-alert:before {
    content: "\e209"
}

.glyphicon-equalizer:before {
    content: "\e210"
}

.glyphicon-king:before {
    content: "\e211"
}

.glyphicon-queen:before {
    content: "\e212"
}

.glyphicon-pawn:before {
    content: "\e213"
}

.glyphicon-bishop:before {
    content: "\e214"
}

.glyphicon-knight:before {
    content: "\e215"
}

.glyphicon-baby-formula:before {
    content: "\e216"
}

.glyphicon-tent:before {
    content: "\26fa"
}

.glyphicon-blackboard:before {
    content: "\e218"
}

.glyphicon-bed:before {
    content: "\e219"
}

.glyphicon-apple:before {
    content: "\f8ff"
}

.glyphicon-erase:before {
    content: "\e221"
}

.glyphicon-hourglass:before {
    content: "\231b"
}

.glyphicon-lamp:before {
    content: "\e223"
}

.glyphicon-duplicate:before {
    content: "\e224"
}

.glyphicon-piggy-bank:before {
    content: "\e225"
}

.glyphicon-scissors:before {
    content: "\e226"
}

.glyphicon-bitcoin:before {
    content: "\e227"
}

.glyphicon-btc:before {
    content: "\e227"
}

.glyphicon-xbt:before {
    content: "\e227"
}

.glyphicon-yen:before {
    content: "\00a5"
}

.glyphicon-jpy:before {
    content: "\00a5"
}

.glyphicon-ruble:before {
    content: "\20bd"
}

.glyphicon-rub:before {
    content: "\20bd"
}

.glyphicon-scale:before {
    content: "\e230"
}

.glyphicon-ice-lolly:before {
    content: "\e231"
}

.glyphicon-ice-lolly-tasted:before {
    content: "\e232"
}

.glyphicon-education:before {
    content: "\e233"
}

.glyphicon-option-horizontal:before {
    content: "\e234"
}

.glyphicon-option-vertical:before {
    content: "\e235"
}

.glyphicon-menu-hamburger:before {
    content: "\e236"
}

.glyphicon-modal-window:before {
    content: "\e237"
}

.glyphicon-oil:before {
    content: "\e238"
}

.glyphicon-grain:before {
    content: "\e239"
}

.glyphicon-sunglasses:before {
    content: "\e240"
}

.glyphicon-text-size:before {
    content: "\e241"
}

.glyphicon-text-color:before {
    content: "\e242"
}

.glyphicon-text-background:before {
    content: "\e243"
}

.glyphicon-object-align-top:before {
    content: "\e244"
}

.glyphicon-object-align-bottom:before {
    content: "\e245"
}

.glyphicon-object-align-horizontal:before {
    content: "\e246"
}

.glyphicon-object-align-left:before {
    content: "\e247"
}

.glyphicon-object-align-vertical:before {
    content: "\e248"
}

.glyphicon-object-align-right:before {
    content: "\e249"
}

.glyphicon-triangle-right:before {
    content: "\e250"
}

.glyphicon-triangle-left:before {
    content: "\e251"
}

.glyphicon-triangle-bottom:before {
    content: "\e252"
}

.glyphicon-triangle-top:before {
    content: "\e253"
}

.glyphicon-console:before {
    content: "\e254"
}

.glyphicon-superscript:before {
    content: "\e255"
}

.glyphicon-subscript:before {
    content: "\e256"
}

.glyphicon-menu-left:before {
    content: "\e257"
}

.glyphicon-menu-right:before {
    content: "\e258"
}

.glyphicon-menu-down:before {
    content: "\e259"
}

.glyphicon-menu-up:before {
    content: "\e260"
}

* {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box
}

:after,
:before {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box
}

html {
    font-size: 10px;
    -webkit-tap-highlight-color: rgba(0, 0, 0, 0)
}

body {
    font-family: Arial, Helvetica, Arial, sans-serif;
    font-size: 14px;
    line-height: 1.42857143;
    color: #333;
    background-color: #fff
}

button,
input,
select,
textarea {
    font-family: inherit;
    font-size: inherit;
    line-height: inherit
}

a {
    text-decoration: none !important
}

a:focus,
a:hover {
    color: #23527c;
    text-decoration: underline
}

a:focus {
    outline: 5px auto -webkit-focus-ring-color;
    outline-offset: -2px
}

figure {
    margin: 0
}

img {
    vertical-align: middle
}

.carousel-inner>.item>a>img,
.carousel-inner>.item>img,
.img-responsive,
.thumbnail a>img,
.thumbnail>img {
    display: block;
    max-width: 100%;
    height: auto
}

.img-rounded {
    border-radius: 6px
}

.img-thumbnail {
    display: inline-block;
    max-width: 100%;
    height: auto;
    padding: 4px;
    line-height: 1.42857143;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 4px;
    -webkit-transition: all .2s ease-in-out;
    -o-transition: all .2s ease-in-out;
    transition: all .2s ease-in-out
}

.img-circle {
    border-radius: 50%
}

hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee
}

.sr-only {
    position: absolute;
    width: 1px;
    height: 1px;
    padding: 0;
    margin: -1px;
    overflow: hidden;
    clip: rect(0, 0, 0, 0);
    border: 0
}

.sr-only-focusable:active,
.sr-only-focusable:focus {
    position: static;
    width: auto;
    height: auto;
    margin: 0;
    overflow: visible;
    clip: auto
}

[role=button] {
    cursor: pointer
}

.h1,
.h2,
.h3,
.h4,
.h5,
.h6,
h1,
h2,
h3,
h4,
h5,
h6 {
    font-family: inherit;
    font-weight: 500;
    line-height: 1.1;
    color: inherit
}

.h1 .small,
.h1 small,
.h2 .small,
.h2 small,
.h3 .small,
.h3 small,
.h4 .small,
.h4 small,
.h5 .small,
.h5 small,
.h6 .small,
.h6 small,
h1 .small,
h1 small,
h2 .small,
h2 small,
h3 .small,
h3 small,
h4 .small,
h4 small,
h5 .small,
h5 small,
h6 .small,
h6 small {
    font-weight: 400;
    line-height: 1;
    color: #777
}

.h1,
.h2,
.h3,
h1,
h2,
h3 {
    margin-top: 20px;
    margin-bottom: 10px
}

.h1 .small,
.h1 small,
.h2 .small,
.h2 small,
.h3 .small,
.h3 small,
h1 .small,
h1 small,
h2 .small,
h2 small,
h3 .small,
h3 small {
    font-size: 65%
}

.h4,
.h5,
.h6,
h4,
h5,
h6 {
    margin-top: 10px;
    margin-bottom: 10px
}

.h4 .small,
.h4 small,
.h5 .small,
.h5 small,
.h6 .small,
.h6 small,
h4 .small,
h4 small,
h5 .small,
h5 small,
h6 .small,
h6 small {
    font-size: 75%
}

.h1,
h1 {
    font-size: 36px
}

.h2,
h2 {
    font-size: 30px
}

.h3,
h3 {
    font-size: 24px
}

.h4,
h4 {
    font-size: 18px
}

.h5,
h5 {
    font-size: 14px
}

.h6,
h6 {
    font-size: 12px
}

p {
    margin: 0 0 10px
}

.lead {
    margin-bottom: 20px;
    font-size: 16px;
    font-weight: 300;
    line-height: 1.4
}

@media(min-width:768px) {
    .lead {
        font-size: 21px
    }
}

.small,
small {
    font-size: 85%
}

.mark,
mark {
    padding: .2em;
    background-color: #fcf8e3
}

.text-left {
    text-align: left
}

.text-right {
    text-align: right
}

.text-center {
    text-align: center
}

.text-justify {
    text-align: justify
}

.text-nowrap {
    white-space: nowrap
}

.text-lowercase {
    text-transform: lowercase
}

.text-uppercase {
    text-transform: uppercase
}

.text-capitalize {
    text-transform: capitalize
}

.text-muted {
    color: #777
}

.text-primary {
    color: #337ab7
}

a.text-primary:focus,
a.text-primary:hover {
    color: #286090
}

.text-success {
    color: #3c763d
}

a.text-success:focus,
a.text-success:hover {
    color: #2b542c
}

.text-info {
    color: #31708f
}

a.text-info:focus,
a.text-info:hover {
    color: #245269
}

.text-warning {
    color: #8a6d3b
}

a.text-warning:focus,
a.text-warning:hover {
    color: #66512c
}

.text-danger {
    color: #a94442
}

a.text-danger:focus,
a.text-danger:hover {
    color: #843534
}

.bg-primary {
    color: #fff;
    background-color: #337ab7
}

a.bg-primary:focus,
a.bg-primary:hover {
    background-color: #286090
}

.bg-success {
    background-color: #dff0d8
}

a.bg-success:focus,
a.bg-success:hover {
    background-color: #c1e2b3
}

.bg-info {
    background-color: #d9edf7
}

a.bg-info:focus,
a.bg-info:hover {
    background-color: #afd9ee
}

.bg-warning {
    background-color: #fcf8e3
}

a.bg-warning:focus,
a.bg-warning:hover {
    background-color: #f7ecb5
}

.bg-danger {
    background-color: #f2dede
}

a.bg-danger:focus,
a.bg-danger:hover {
    background-color: #e4b9b9
}

.page-header {
    padding-bottom: 9px;
    margin: 40px 0 20px;
    border-bottom: 1px solid #eee
}

ol,
ul {
    margin-top: 0;
    margin-bottom: 10px
}

ol ol,
ol ul,
ul ol,
ul ul {
    margin-bottom: 0
}

.list-unstyled {
    padding-left: 0;
    list-style: none
}

.list-inline {
    padding-left: 0;
    margin-left: -5px;
    list-style: none
}

.list-inline>li {
    display: inline-block;
    padding-right: 5px;
    padding-left: 5px
}

dl {
    margin-top: 0;
    margin-bottom: 20px
}

dd,
dt {
    line-height: 1.42857143
}

dt {
    font-weight: 700
}

dd {
    margin-left: 0
}

@media(min-width:768px) {
    .dl-horizontal dt {
        float: left;
        width: 160px;
        overflow: hidden;
        clear: left;
        text-align: right;
        text-overflow: ellipsis;
        white-space: nowrap
    }

    .dl-horizontal dd {
        margin-left: 180px
    }
}

abbr[data-original-title],
abbr[title] {
    cursor: help;
    border-bottom: 1px dotted #777
}

.initialism {
    font-size: 90%;
    text-transform: uppercase
}

blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee
}

blockquote ol:last-child,
blockquote p:last-child,
blockquote ul:last-child {
    margin-bottom: 0
}

blockquote .small,
blockquote footer,
blockquote small {
    display: block;
    font-size: 80%;
    line-height: 1.42857143;
    color: #777
}

blockquote .small:before,
blockquote footer:before,
blockquote small:before {
    content: '\2014 \00A0'
}

.blockquote-reverse,
blockquote.pull-right {
    padding-right: 15px;
    padding-left: 0;
    text-align: right;
    border-right: 5px solid #eee;
    border-left: 0
}

.blockquote-reverse .small:before,
.blockquote-reverse footer:before,
.blockquote-reverse small:before,
blockquote.pull-right .small:before,
blockquote.pull-right footer:before,
blockquote.pull-right small:before {
    content: ''
}

.blockquote-reverse .small:after,
.blockquote-reverse footer:after,
.blockquote-reverse small:after,
blockquote.pull-right .small:after,
blockquote.pull-right footer:after,
blockquote.pull-right small:after {
    content: '\00A0 \2014'
}

address {
    margin-bottom: 20px;
    font-style: normal;
    line-height: 1.42857143
}

code,
kbd,
pre,
samp {
    font-family: Menlo, Monaco, Consolas, "Courier New", monospace
}

code {
    padding: 2px 4px;
    font-size: 90%;
    color: #c7254e;
    background-color: #f9f2f4;
    border-radius: 4px
}

kbd {
    padding: 2px 4px;
    font-size: 90%;
    color: #fff;
    background-color: #333;
    border-radius: 3px;
    -webkit-box-shadow: inset 0 -1px 0 rgba(0, 0, 0, .25);
    box-shadow: inset 0 -1px 0 rgba(0, 0, 0, .25)
}

kbd kbd {
    padding: 0;
    font-size: 100%;
    font-weight: 700;
    -webkit-box-shadow: none;
    box-shadow: none
}

pre {
    display: block;
    padding: 9.5px;
    margin: 0 0 10px;
    font-size: 13px;
    line-height: 1.42857143;
    color: #333;
    word-break: break-all;
    word-wrap: break-word;
    background-color: #f5f5f5;
    border: 1px solid #ccc;
    border-radius: 4px
}

pre code {
    padding: 0;
    font-size: inherit;
    color: inherit;
    white-space: pre-wrap;
    background-color: transparent;
    border-radius: 0
}

.pre-scrollable {
    max-height: 340px;
    overflow-y: scroll
}

.container {
    padding-right: 15px;
    padding-left: 15px;
    margin-right: auto;
    margin-left: auto
}

@media(min-width:768px) {
    .container {
        width: 750px
    }
}

@media(min-width:992px) {
    .container {
        width: 970px
    }
}

@media(min-width:1200px) {
    .container {
        width: 1170px
    }
}

.container-fluid {
    padding-right: 15px;
    padding-left: 15px;
    margin-right: auto;
    margin-left: auto
}

.row {
    margin-right: 0;
    margin-left: 0
}

.col-lg-1,
.col-lg-10,
.col-lg-11,
.col-lg-12,
.col-lg-2,
.col-lg-3,
.col-lg-4,
.col-lg-5,
.col-lg-6,
.col-lg-7,
.col-lg-8,
.col-lg-9,
.col-md-1,
.col-md-10,
.col-md-11,
.col-md-12,
.col-md-2,
.col-md-3,
.col-md-4,
.col-md-5,
.col-md-6,
.col-md-7,
.col-md-8,
.col-md-9,
.col-sm-1,
.col-sm-10,
.col-sm-11,
.col-sm-12,
.col-sm-2,
.col-sm-3,
.col-sm-4,
.col-sm-5,
.col-sm-6,
.col-sm-7,
.col-sm-8,
.col-sm-9,
.col-xs-1,
.col-xs-10,
.col-xs-11,
.col-xs-12,
.col-xs-2,
.col-xs-3,
.col-xs-4,
.col-xs-5,
.col-xs-6,
.col-xs-7,
.col-xs-8,
.col-xs-9 {
    position: relative;
    min-height: 1px;
    padding-right: 0;
    padding-left: 0
}

.col-xs-1,
.col-xs-10,
.col-xs-11,
.col-xs-12,
.col-xs-2,
.col-xs-3,
.col-xs-4,
.col-xs-5,
.col-xs-6,
.col-xs-7,
.col-xs-8,
.col-xs-9 {
    float: left
}

.col-xs-12 {
    width: 100%
}

.col-xs-11 {
    width: 91.66666667%
}

.col-xs-10 {
    width: 83.33333333%
}

.col-xs-9 {
    width: 75%
}

.col-xs-8 {
    width: 66.66666667%
}

.col-xs-7 {
    width: 58.33333333%
}

.col-xs-6 {
    width: 50%
}

.col-xs-5 {
    width: 41.66666667%
}

.col-xs-4 {
    width: 33.33333333%
}

.col-xs-3 {
    width: 25%
}

.col-xs-2 {
    width: 16.66666667%
}

.col-xs-1 {
    width: 8.33333333%
}

.col-xs-pull-12 {
    right: 100%
}

.col-xs-pull-11 {
    right: 91.66666667%
}

.col-xs-pull-10 {
    right: 83.33333333%
}

.col-xs-pull-9 {
    right: 75%
}

.col-xs-pull-8 {
    right: 66.66666667%
}

.col-xs-pull-7 {
    right: 58.33333333%
}

.col-xs-pull-6 {
    right: 50%
}

.col-xs-pull-5 {
    right: 41.66666667%
}

.col-xs-pull-4 {
    right: 33.33333333%
}

.col-xs-pull-3 {
    right: 25%
}

.col-xs-pull-2 {
    right: 16.66666667%
}

.col-xs-pull-1 {
    right: 8.33333333%
}

.col-xs-pull-0 {
    right: auto
}

.col-xs-push-12 {
    left: 100%
}

.col-xs-push-11 {
    left: 91.66666667%
}

.col-xs-push-10 {
    left: 83.33333333%
}

.col-xs-push-9 {
    left: 75%
}

.col-xs-push-8 {
    left: 66.66666667%
}

.col-xs-push-7 {
    left: 58.33333333%
}

.col-xs-push-6 {
    left: 50%
}

.col-xs-push-5 {
    left: 41.66666667%
}

.col-xs-push-4 {
    left: 33.33333333%
}

.col-xs-push-3 {
    left: 25%
}

.col-xs-push-2 {
    left: 16.66666667%
}

.col-xs-push-1 {
    left: 8.33333333%
}

.col-xs-push-0 {
    left: auto
}

.col-xs-offset-12 {
    margin-left: 100%
}

.col-xs-offset-11 {
    margin-left: 91.66666667%
}

.col-xs-offset-10 {
    margin-left: 83.33333333%
}

.col-xs-offset-9 {
    margin-left: 75%
}

.col-xs-offset-8 {
    margin-left: 66.66666667%
}

.col-xs-offset-7 {
    margin-left: 58.33333333%
}

.col-xs-offset-6 {
    margin-left: 50%
}

.col-xs-offset-5 {
    margin-left: 41.66666667%
}

.col-xs-offset-4 {
    margin-left: 33.33333333%
}

.col-xs-offset-3 {
    margin-left: 25%
}

.col-xs-offset-2 {
    margin-left: 16.66666667%
}

.col-xs-offset-1 {
    margin-left: 8.33333333%
}

.col-xs-offset-0 {
    margin-left: 0
}

@media(min-width:768px) {

    .col-sm-1,
    .col-sm-10,
    .col-sm-11,
    .col-sm-12,
    .col-sm-2,
    .col-sm-3,
    .col-sm-4,
    .col-sm-5,
    .col-sm-6,
    .col-sm-7,
    .col-sm-8,
    .col-sm-9 {
        float: left
    }

    .col-sm-12 {
        width: 100%
    }

    .col-sm-11 {
        width: 91.66666667%
    }

    .col-sm-10 {
        width: 83.33333333%
    }

    .col-sm-9 {
        width: 75%
    }

    .col-sm-8 {
        width: 66.66666667%
    }

    .col-sm-7 {
        width: 58.33333333%
    }

    .col-sm-6 {
        width: 50%
    }

    .col-sm-5 {
        width: 41.66666667%
    }

    .col-sm-4 {
        width: 33.33333333%
    }

    .col-sm-3 {
        width: 25%
    }

    .col-sm-2 {
        width: 16.66666667%
    }

    .col-sm-1 {
        width: 8.33333333%
    }

    .col-sm-pull-12 {
        right: 100%
    }

    .col-sm-pull-11 {
        right: 91.66666667%
    }

    .col-sm-pull-10 {
        right: 83.33333333%
    }

    .col-sm-pull-9 {
        right: 75%
    }

    .col-sm-pull-8 {
        right: 66.66666667%
    }

    .col-sm-pull-7 {
        right: 58.33333333%
    }

    .col-sm-pull-6 {
        right: 50%
    }

    .col-sm-pull-5 {
        right: 41.66666667%
    }

    .col-sm-pull-4 {
        right: 33.33333333%
    }

    .col-sm-pull-3 {
        right: 25%
    }

    .col-sm-pull-2 {
        right: 16.66666667%
    }

    .col-sm-pull-1 {
        right: 8.33333333%
    }

    .col-sm-pull-0 {
        right: auto
    }

    .col-sm-push-12 {
        left: 100%
    }

    .col-sm-push-11 {
        left: 91.66666667%
    }

    .col-sm-push-10 {
        left: 83.33333333%
    }

    .col-sm-push-9 {
        left: 75%
    }

    .col-sm-push-8 {
        left: 66.66666667%
    }

    .col-sm-push-7 {
        left: 58.33333333%
    }

    .col-sm-push-6 {
        left: 50%
    }

    .col-sm-push-5 {
        left: 41.66666667%
    }

    .col-sm-push-4 {
        left: 33.33333333%
    }

    .col-sm-push-3 {
        left: 25%
    }

    .col-sm-push-2 {
        left: 16.66666667%
    }

    .col-sm-push-1 {
        left: 8.33333333%
    }

    .col-sm-push-0 {
        left: auto
    }

    .col-sm-offset-12 {
        margin-left: 100%
    }

    .col-sm-offset-11 {
        margin-left: 91.66666667%
    }

    .col-sm-offset-10 {
        margin-left: 83.33333333%
    }

    .col-sm-offset-9 {
        margin-left: 75%
    }

    .col-sm-offset-8 {
        margin-left: 66.66666667%
    }

    .col-sm-offset-7 {
        margin-left: 58.33333333%
    }

    .col-sm-offset-6 {
        margin-left: 50%
    }

    .col-sm-offset-5 {
        margin-left: 41.66666667%
    }

    .col-sm-offset-4 {
        margin-left: 33.33333333%
    }

    .col-sm-offset-3 {
        margin-left: 25%
    }

    .col-sm-offset-2 {
        margin-left: 16.66666667%
    }

    .col-sm-offset-1 {
        margin-left: 8.33333333%
    }

    .col-sm-offset-0 {
        margin-left: 0
    }
}

@media(min-width:992px) {

    .col-md-1,
    .col-md-10,
    .col-md-11,
    .col-md-12,
    .col-md-2,
    .col-md-3,
    .col-md-4,
    .col-md-5,
    .col-md-6,
    .col-md-7,
    .col-md-8,
    .col-md-9 {
        float: left
    }

    .col-md-12 {
        width: 100%
    }

    .col-md-11 {
        width: 91.66666667%
    }

    .col-md-10 {
        width: 83.33333333%
    }

    .col-md-9 {
        width: 75%
    }

    .col-md-8 {
        width: 66.66666667%
    }

    .col-md-7 {
        width: 58.33333333%
    }

    .col-md-6 {
        width: 50%
    }

    .col-md-5 {
        width: 41.66666667%
    }

    .col-md-4 {
        width: 33.33333333%
    }

    .col-md-3 {
        width: 25%
    }

    .col-md-2 {
        width: 16.66666667%
    }

    .col-md-1 {
        width: 8.33333333%
    }

    .col-md-pull-12 {
        right: 100%
    }

    .col-md-pull-11 {
        right: 91.66666667%
    }

    .col-md-pull-10 {
        right: 83.33333333%
    }

    .col-md-pull-9 {
        right: 75%
    }

    .col-md-pull-8 {
        right: 66.66666667%
    }

    .col-md-pull-7 {
        right: 58.33333333%
    }

    .col-md-pull-6 {
        right: 50%
    }

    .col-md-pull-5 {
        right: 41.66666667%
    }

    .col-md-pull-4 {
        right: 33.33333333%
    }

    .col-md-pull-3 {
        right: 25%
    }

    .col-md-pull-2 {
        right: 16.66666667%
    }

    .col-md-pull-1 {
        right: 8.33333333%
    }

    .col-md-pull-0 {
        right: auto
    }

    .col-md-push-12 {
        left: 100%
    }

    .col-md-push-11 {
        left: 91.66666667%
    }

    .col-md-push-10 {
        left: 83.33333333%
    }

    .col-md-push-9 {
        left: 75%
    }

    .col-md-push-8 {
        left: 66.66666667%
    }

    .col-md-push-7 {
        left: 58.33333333%
    }

    .col-md-push-6 {
        left: 50%
    }

    .col-md-push-5 {
        left: 41.66666667%
    }

    .col-md-push-4 {
        left: 33.33333333%
    }

    .col-md-push-3 {
        left: 25%
    }

    .col-md-push-2 {
        left: 16.66666667%
    }

    .col-md-push-1 {
        left: 8.33333333%
    }

    .col-md-push-0 {
        left: auto
    }

    .col-md-offset-12 {
        margin-left: 100%
    }

    .col-md-offset-11 {
        margin-left: 91.66666667%
    }

    .col-md-offset-10 {
        margin-left: 83.33333333%
    }

    .col-md-offset-9 {
        margin-left: 75%
    }

    .col-md-offset-8 {
        margin-left: 66.66666667%
    }

    .col-md-offset-7 {
        margin-left: 58.33333333%
    }

    .col-md-offset-6 {
        margin-left: 50%
    }

    .col-md-offset-5 {
        margin-left: 41.66666667%
    }

    .col-md-offset-4 {
        margin-left: 33.33333333%
    }

    .col-md-offset-3 {
        margin-left: 25%
    }

    .col-md-offset-2 {
        margin-left: 16.66666667%
    }

    .col-md-offset-1 {
        margin-left: 8.33333333%
    }

    .col-md-offset-0 {
        margin-left: 0
    }
}

@media(min-width:1200px) {

    .col-lg-1,
    .col-lg-10,
    .col-lg-11,
    .col-lg-12,
    .col-lg-2,
    .col-lg-3,
    .col-lg-4,
    .col-lg-5,
    .col-lg-6,
    .col-lg-7,
    .col-lg-8,
    .col-lg-9 {
        float: left
    }

    .col-lg-12 {
        width: 100%
    }

    .col-lg-11 {
        width: 91.66666667%
    }

    .col-lg-10 {
        width: 83.33333333%
    }

    .col-lg-9 {
        width: 75%
    }

    .col-lg-8 {
        width: 66.66666667%
    }

    .col-lg-7 {
        width: 58.33333333%
    }

    .col-lg-6 {
        width: 50%
    }

    .col-lg-5 {
        width: 41.66666667%
    }

    .col-lg-4 {
        width: 33.33333333%
    }

    .col-lg-3 {
        width: 25%
    }

    .col-lg-2 {
        width: 16.66666667%
    }

    .col-lg-1 {
        width: 8.33333333%
    }

    .col-lg-pull-12 {
        right: 100%
    }

    .col-lg-pull-11 {
        right: 91.66666667%
    }

    .col-lg-pull-10 {
        right: 83.33333333%
    }

    .col-lg-pull-9 {
        right: 75%
    }

    .col-lg-pull-8 {
        right: 66.66666667%
    }

    .col-lg-pull-7 {
        right: 58.33333333%
    }

    .col-lg-pull-6 {
        right: 50%
    }

    .col-lg-pull-5 {
        right: 41.66666667%
    }

    .col-lg-pull-4 {
        right: 33.33333333%
    }

    .col-lg-pull-3 {
        right: 25%
    }

    .col-lg-pull-2 {
        right: 16.66666667%
    }

    .col-lg-pull-1 {
        right: 8.33333333%
    }

    .col-lg-pull-0 {
        right: auto
    }

    .col-lg-push-12 {
        left: 100%
    }

    .col-lg-push-11 {
        left: 91.66666667%
    }

    .col-lg-push-10 {
        left: 83.33333333%
    }

    .col-lg-push-9 {
        left: 75%
    }

    .col-lg-push-8 {
        left: 66.66666667%
    }

    .col-lg-push-7 {
        left: 58.33333333%
    }

    .col-lg-push-6 {
        left: 50%
    }

    .col-lg-push-5 {
        left: 41.66666667%
    }

    .col-lg-push-4 {
        left: 33.33333333%
    }

    .col-lg-push-3 {
        left: 25%
    }

    .col-lg-push-2 {
        left: 16.66666667%
    }

    .col-lg-push-1 {
        left: 8.33333333%
    }

    .col-lg-push-0 {
        left: auto
    }

    .col-lg-offset-12 {
        margin-left: 100%
    }

    .col-lg-offset-11 {
        margin-left: 91.66666667%
    }

    .col-lg-offset-10 {
        margin-left: 83.33333333%
    }

    .col-lg-offset-9 {
        margin-left: 75%
    }

    .col-lg-offset-8 {
        margin-left: 66.66666667%
    }

    .col-lg-offset-7 {
        margin-left: 58.33333333%
    }

    .col-lg-offset-6 {
        margin-left: 50%
    }

    .col-lg-offset-5 {
        margin-left: 41.66666667%
    }

    .col-lg-offset-4 {
        margin-left: 33.33333333%
    }

    .col-lg-offset-3 {
        margin-left: 25%
    }

    .col-lg-offset-2 {
        margin-left: 16.66666667%
    }

    .col-lg-offset-1 {
        margin-left: 8.33333333%
    }

    .col-lg-offset-0 {
        margin-left: 0
    }
}

table {
    background-color: transparent
}

caption {
    padding-top: 8px;
    padding-bottom: 8px;
    color: #777;
    text-align: left
}

th {
    text-align: left
}

.table {
    width: 100%;
    max-width: 100%;
    margin-bottom: 20px
}

.table>tbody>tr>td,
.table>tbody>tr>th,
.table>tfoot>tr>td,
.table>tfoot>tr>th,
.table>thead>tr>td,
.table>thead>tr>th {
    padding: 8px;
    line-height: 1.42857143;
    vertical-align: top;
    border-top: 1px solid #ddd
}

.table>thead>tr>th {
    vertical-align: bottom;
    border-bottom: 2px solid #ddd
}

.table>caption+thead>tr:first-child>td,
.table>caption+thead>tr:first-child>th,
.table>colgroup+thead>tr:first-child>td,
.table>colgroup+thead>tr:first-child>th,
.table>thead:first-child>tr:first-child>td,
.table>thead:first-child>tr:first-child>th {
    border-top: 0
}

.table>tbody+tbody {
    border-top: 2px solid #ddd
}

.table .table {
    background-color: #fff
}

.table-condensed>tbody>tr>td,
.table-condensed>tbody>tr>th,
.table-condensed>tfoot>tr>td,
.table-condensed>tfoot>tr>th,
.table-condensed>thead>tr>td,
.table-condensed>thead>tr>th {
    padding: 5px
}

.table-bordered {
    border: 1px solid #ddd
}

.table-bordered>tbody>tr>td,
.table-bordered>tbody>tr>th,
.table-bordered>tfoot>tr>td,
.table-bordered>tfoot>tr>th,
.table-bordered>thead>tr>td,
.table-bordered>thead>tr>th {
    border: 1px solid #ddd
}

.table-bordered>thead>tr>td,
.table-bordered>thead>tr>th {
    border-bottom-width: 2px
}

.table-striped>tbody>tr:nth-of-type(odd) {
    background-color: #f9f9f9
}

.table-hover>tbody>tr:hover {
    background-color: #f5f5f5
}

table col[class*=col-] {
    position: static;
    display: table-column;
    float: none
}

table td[class*=col-],
table th[class*=col-] {
    position: static;
    display: table-cell;
    float: none
}

.table>tbody>tr.active>td,
.table>tbody>tr.active>th,
.table>tbody>tr>td.active,
.table>tbody>tr>th.active,
.table>tfoot>tr.active>td,
.table>tfoot>tr.active>th,
.table>tfoot>tr>td.active,
.table>tfoot>tr>th.active,
.table>thead>tr.active>td,
.table>thead>tr.active>th,
.table>thead>tr>td.active,
.table>thead>tr>th.active {
    background-color: #f5f5f5
}

.table-hover>tbody>tr.active:hover>td,
.table-hover>tbody>tr.active:hover>th,
.table-hover>tbody>tr:hover>.active,
.table-hover>tbody>tr>td.active:hover,
.table-hover>tbody>tr>th.active:hover {
    background-color: #e8e8e8
}

.table>tbody>tr.success>td,
.table>tbody>tr.success>th,
.table>tbody>tr>td.success,
.table>tbody>tr>th.success,
.table>tfoot>tr.success>td,
.table>tfoot>tr.success>th,
.table>tfoot>tr>td.success,
.table>tfoot>tr>th.success,
.table>thead>tr.success>td,
.table>thead>tr.success>th,
.table>thead>tr>td.success,
.table>thead>tr>th.success {
    background-color: #dff0d8
}

.table-hover>tbody>tr.success:hover>td,
.table-hover>tbody>tr.success:hover>th,
.table-hover>tbody>tr:hover>.success,
.table-hover>tbody>tr>td.success:hover,
.table-hover>tbody>tr>th.success:hover {
    background-color: #d0e9c6
}

.table>tbody>tr.info>td,
.table>tbody>tr.info>th,
.table>tbody>tr>td.info,
.table>tbody>tr>th.info,
.table>tfoot>tr.info>td,
.table>tfoot>tr.info>th,
.table>tfoot>tr>td.info,
.table>tfoot>tr>th.info,
.table>thead>tr.info>td,
.table>thead>tr.info>th,
.table>thead>tr>td.info,
.table>thead>tr>th.info {
    background-color: #d9edf7
}

.table-hover>tbody>tr.info:hover>td,
.table-hover>tbody>tr.info:hover>th,
.table-hover>tbody>tr:hover>.info,
.table-hover>tbody>tr>td.info:hover,
.table-hover>tbody>tr>th.info:hover {
    background-color: #c4e3f3
}

.table>tbody>tr.warning>td,
.table>tbody>tr.warning>th,
.table>tbody>tr>td.warning,
.table>tbody>tr>th.warning,
.table>tfoot>tr.warning>td,
.table>tfoot>tr.warning>th,
.table>tfoot>tr>td.warning,
.table>tfoot>tr>th.warning,
.table>thead>tr.warning>td,
.table>thead>tr.warning>th,
.table>thead>tr>td.warning,
.table>thead>tr>th.warning {
    background-color: #fcf8e3
}

.table-hover>tbody>tr.warning:hover>td,
.table-hover>tbody>tr.warning:hover>th,
.table-hover>tbody>tr:hover>.warning,
.table-hover>tbody>tr>td.warning:hover,
.table-hover>tbody>tr>th.warning:hover {
    background-color: #faf2cc
}

.table>tbody>tr.danger>td,
.table>tbody>tr.danger>th,
.table>tbody>tr>td.danger,
.table>tbody>tr>th.danger,
.table>tfoot>tr.danger>td,
.table>tfoot>tr.danger>th,
.table>tfoot>tr>td.danger,
.table>tfoot>tr>th.danger,
.table>thead>tr.danger>td,
.table>thead>tr.danger>th,
.table>thead>tr>td.danger,
.table>thead>tr>th.danger {
    background-color: #f2dede
}

.table-hover>tbody>tr.danger:hover>td,
.table-hover>tbody>tr.danger:hover>th,
.table-hover>tbody>tr:hover>.danger,
.table-hover>tbody>tr>td.danger:hover,
.table-hover>tbody>tr>th.danger:hover {
    background-color: #ebcccc
}

.table-responsive {
    min-height: .01%;
    overflow-x: auto
}

@media screen and (max-width:767px) {
    .table-responsive {
        width: 100%;
        margin-bottom: 15px;
        overflow-y: hidden;
        -ms-overflow-style: -ms-autohiding-scrollbar;
        border: 1px solid #ddd
    }

    .table-responsive>.table {
        margin-bottom: 0
    }

    .table-responsive>.table>tbody>tr>td,
    .table-responsive>.table>tbody>tr>th,
    .table-responsive>.table>tfoot>tr>td,
    .table-responsive>.table>tfoot>tr>th,
    .table-responsive>.table>thead>tr>td,
    .table-responsive>.table>thead>tr>th {
        white-space: nowrap
    }

    .table-responsive>.table-bordered {
        border: 0
    }

    .table-responsive>.table-bordered>tbody>tr>td:first-child,
    .table-responsive>.table-bordered>tbody>tr>th:first-child,
    .table-responsive>.table-bordered>tfoot>tr>td:first-child,
    .table-responsive>.table-bordered>tfoot>tr>th:first-child,
    .table-responsive>.table-bordered>thead>tr>td:first-child,
    .table-responsive>.table-bordered>thead>tr>th:first-child {
        border-left: 0
    }

    .table-responsive>.table-bordered>tbody>tr>td:last-child,
    .table-responsive>.table-bordered>tbody>tr>th:last-child,
    .table-responsive>.table-bordered>tfoot>tr>td:last-child,
    .table-responsive>.table-bordered>tfoot>tr>th:last-child,
    .table-responsive>.table-bordered>thead>tr>td:last-child,
    .table-responsive>.table-bordered>thead>tr>th:last-child {
        border-right: 0
    }

    .table-responsive>.table-bordered>tbody>tr:last-child>td,
    .table-responsive>.table-bordered>tbody>tr:last-child>th,
    .table-responsive>.table-bordered>tfoot>tr:last-child>td,
    .table-responsive>.table-bordered>tfoot>tr:last-child>th {
        border-bottom: 0
    }
}

fieldset {
    min-width: 0;
    padding: 0;
    margin: 0;
    border: 0
}

legend {
    display: block;
    width: 100%;
    padding: 0;
    margin-bottom: 20px;
    font-size: 21px;
    line-height: inherit;
    color: #333;
    border: 0;
    border-bottom: 1px solid #e5e5e5
}

label {
    display: inline-block;
    max-width: 100%;
    margin-bottom: 5px;
    font-weight: 700
}

input[type=search] {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box
}

input[type=checkbox],
input[type=radio] {
    margin: 4px 0 0;
    margin-top: 1px\9;
    line-height: normal
}

input[type=file] {
    display: block
}

input[type=range] {
    display: block;
    width: 100%
}

select[multiple],
select[size] {
    height: auto
}

input[type=file]:focus,
input[type=checkbox]:focus,
input[type=radio]:focus {
    outline: 5px auto -webkit-focus-ring-color;
    outline-offset: -2px
}

output {
    display: block;
    padding-top: 7px;
    font-size: 14px;
    line-height: 1.42857143;
    color: #555
}

.form-control {
    display: block;
    width: 100%;
    height: 34px;
    padding: 6px 12px;
    font-size: 14px;
    line-height: 1.42857143;
    color: #555;
    background-color: #fff;
    background-image: none;
    border: 1px solid #ccc;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
    -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
    -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
    transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s
}

.form-control:focus {
    border-color: #66afe9;
    outline: 0;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px rgba(102, 175, 233, .6)
}

.form-control::-moz-placeholder {
    color: #999;
    opacity: 1
}

.form-control:-ms-input-placeholder {
    color: #999
}

.form-control::-webkit-input-placeholder {
    color: #999
}

.form-control::-ms-expand {
    background-color: transparent;
    border: 0
}

.form-control[disabled],
.form-control[readonly],
fieldset[disabled] .form-control {
    background-color: #eee;
    opacity: 1
}

.form-control[disabled],
fieldset[disabled] .form-control {
    cursor: not-allowed
}

textarea.form-control {
    height: auto
}

input[type=search] {
    -webkit-appearance: none
}

@media screen and (-webkit-min-device-pixel-ratio:0) {

    input[type=date].form-control,
    input[type=time].form-control,
    input[type=datetime-local].form-control,
    input[type=month].form-control {
        line-height: 34px
    }

    .input-group-sm input[type=date],
    .input-group-sm input[type=time],
    .input-group-sm input[type=datetime-local],
    .input-group-sm input[type=month],
    input[type=date].input-sm,
    input[type=time].input-sm,
    input[type=datetime-local].input-sm,
    input[type=month].input-sm {
        line-height: 30px
    }

    .input-group-lg input[type=date],
    .input-group-lg input[type=time],
    .input-group-lg input[type=datetime-local],
    .input-group-lg input[type=month],
    input[type=date].input-lg,
    input[type=time].input-lg,
    input[type=datetime-local].input-lg,
    input[type=month].input-lg {
        line-height: 46px
    }
}

.form-group {
    margin-bottom: 15px
}

.checkbox,
.radio {
    position: relative;
    display: block;
    margin-top: 10px;
    margin-bottom: 10px
}

.checkbox label,
.radio label {
    min-height: 20px;
    padding-left: 20px;
    margin-bottom: 0;
    font-weight: 400;
    cursor: pointer
}

.checkbox input[type=checkbox],
.checkbox-inline input[type=checkbox],
.radio input[type=radio],
.radio-inline input[type=radio] {
    position: absolute;
    margin-top: 4px\9;
    margin-left: -20px
}

.checkbox+.checkbox,
.radio+.radio {
    margin-top: -5px
}

.checkbox-inline,
.radio-inline {
    position: relative;
    display: inline-block;
    padding-left: 20px;
    margin-bottom: 0;
    font-weight: 400;
    vertical-align: middle;
    cursor: pointer
}

.checkbox-inline+.checkbox-inline,
.radio-inline+.radio-inline {
    margin-top: 0;
    margin-left: 10px
}

fieldset[disabled] input[type=checkbox],
fieldset[disabled] input[type=radio],
input[type=checkbox].disabled,
input[type=checkbox][disabled],
input[type=radio].disabled,
input[type=radio][disabled] {
    cursor: not-allowed
}

.checkbox-inline.disabled,
.radio-inline.disabled,
fieldset[disabled] .checkbox-inline,
fieldset[disabled] .radio-inline {
    cursor: not-allowed
}

.checkbox.disabled label,
.radio.disabled label,
fieldset[disabled] .checkbox label,
fieldset[disabled] .radio label {
    cursor: not-allowed
}

.form-control-static {
    min-height: 34px;
    padding-top: 7px;
    padding-bottom: 7px;
    margin-bottom: 0
}

.form-control-static.input-lg,
.form-control-static.input-sm {
    padding-right: 0;
    padding-left: 0
}

.input-sm {
    height: 30px;
    padding: 5px 10px;
    font-size: 12px;
    line-height: 1.5;
    border-radius: 3px
}

select.input-sm {
    height: 30px;
    line-height: 30px
}

select[multiple].input-sm,
textarea.input-sm {
    height: auto
}

.form-group-sm .form-control {
    height: 30px;
    padding: 5px 10px;
    font-size: 12px;
    line-height: 1.5;
    border-radius: 3px
}

.form-group-sm select.form-control {
    height: 30px;
    line-height: 30px
}

.form-group-sm select[multiple].form-control,
.form-group-sm textarea.form-control {
    height: auto
}

.form-group-sm .form-control-static {
    height: 30px;
    min-height: 32px;
    padding: 6px 10px;
    font-size: 12px;
    line-height: 1.5
}

.input-lg {
    height: 46px;
    padding: 10px 16px;
    font-size: 18px;
    line-height: 1.3333333;
    border-radius: 6px
}

select.input-lg {
    height: 46px;
    line-height: 46px
}

select[multiple].input-lg,
textarea.input-lg {
    height: auto
}

.form-group-lg .form-control {
    height: 46px;
    padding: 10px 16px;
    font-size: 18px;
    line-height: 1.3333333;
    border-radius: 6px
}

.form-group-lg select.form-control {
    height: 46px;
    line-height: 46px
}

.form-group-lg select[multiple].form-control,
.form-group-lg textarea.form-control {
    height: auto
}

.form-group-lg .form-control-static {
    height: 46px;
    min-height: 38px;
    padding: 11px 16px;
    font-size: 18px;
    line-height: 1.3333333
}

.has-feedback {
    position: relative
}

.has-feedback .form-control {
    padding-right: 42.5px
}

.form-control-feedback {
    position: absolute;
    top: 0;
    right: 0;
    z-index: 2;
    display: block;
    width: 34px;
    height: 34px;
    line-height: 34px;
    text-align: center;
    pointer-events: none
}

.form-group-lg .form-control+.form-control-feedback,
.input-group-lg+.form-control-feedback,
.input-lg+.form-control-feedback {
    width: 46px;
    height: 46px;
    line-height: 46px
}

.form-group-sm .form-control+.form-control-feedback,
.input-group-sm+.form-control-feedback,
.input-sm+.form-control-feedback {
    width: 30px;
    height: 30px;
    line-height: 30px
}

.has-success .checkbox,
.has-success .checkbox-inline,
.has-success .control-label,
.has-success .help-block,
.has-success .radio,
.has-success .radio-inline,
.has-success.checkbox label,
.has-success.checkbox-inline label,
.has-success.radio label,
.has-success.radio-inline label {
    color: #3c763d
}

.has-success .form-control {
    border-color: #3c763d;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075)
}

.has-success .form-control:focus {
    border-color: #2b542c;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #67b168;
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #67b168
}

.has-success .input-group-addon {
    color: #3c763d;
    background-color: #dff0d8;
    border-color: #3c763d
}

.has-success .form-control-feedback {
    color: #3c763d
}

.has-warning .checkbox,
.has-warning .checkbox-inline,
.has-warning .control-label,
.has-warning .help-block,
.has-warning .radio,
.has-warning .radio-inline,
.has-warning.checkbox label,
.has-warning.checkbox-inline label,
.has-warning.radio label,
.has-warning.radio-inline label {
    color: #8a6d3b
}

.has-warning .form-control {
    border-color: #8a6d3b;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075)
}

.has-warning .form-control:focus {
    border-color: #66512c;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #c0a16b;
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #c0a16b
}

.has-warning .input-group-addon {
    color: #8a6d3b;
    background-color: #fcf8e3;
    border-color: #8a6d3b
}

.has-warning .form-control-feedback {
    color: #8a6d3b
}

.has-error .checkbox,
.has-error .checkbox-inline,
.has-error .control-label,
.has-error .help-block,
.has-error .radio,
.has-error .radio-inline,
.has-error.checkbox label,
.has-error.checkbox-inline label,
.has-error.radio label,
.has-error.radio-inline label {
    color: #a94442
}

.has-error .form-control {
    border-color: #a94442;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075)
}

.has-error .form-control:focus {
    border-color: #843534;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #ce8483;
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 6px #ce8483
}

.has-error .input-group-addon {
    color: #a94442;
    background-color: #f2dede;
    border-color: #a94442
}

.has-error .form-control-feedback {
    color: #a94442
}

.has-feedback label~.form-control-feedback {
    top: 25px
}

.has-feedback label.sr-only~.form-control-feedback {
    top: 0
}

.help-block {
    display: block;
    margin-top: 5px;
    margin-bottom: 10px;
    color: #737373
}

@media(min-width:768px) {
    .form-inline .form-group {
        display: inline-block;
        margin-bottom: 0;
        vertical-align: middle
    }

    .form-inline .form-control {
        display: inline-block;
        width: auto;
        vertical-align: middle
    }

    .form-inline .form-control-static {
        display: inline-block
    }

    .form-inline .input-group {
        display: inline-table;
        vertical-align: middle
    }

    .form-inline .input-group .form-control,
    .form-inline .input-group .input-group-addon,
    .form-inline .input-group .input-group-btn {
        width: auto
    }

    .form-inline .input-group>.form-control {
        width: 100%
    }

    .form-inline .control-label {
        margin-bottom: 0;
        vertical-align: middle
    }

    .form-inline .checkbox,
    .form-inline .radio {
        display: inline-block;
        margin-top: 0;
        margin-bottom: 0;
        vertical-align: middle
    }

    .form-inline .checkbox label,
    .form-inline .radio label {
        padding-left: 0
    }

    .form-inline .checkbox input[type=checkbox],
    .form-inline .radio input[type=radio] {
        position: relative;
        margin-left: 0
    }

    .form-inline .has-feedback .form-control-feedback {
        top: 0
    }
}

.form-horizontal .checkbox,
.form-horizontal .checkbox-inline,
.form-horizontal .radio,
.form-horizontal .radio-inline {
    padding-top: 7px;
    margin-top: 0;
    margin-bottom: 0
}

.form-horizontal .checkbox,
.form-horizontal .radio {
    min-height: 27px
}

.form-horizontal .form-group {
    margin-right: -15px;
    margin-left: -15px
}

@media(min-width:768px) {
    .form-horizontal .control-label {
        padding-top: 7px;
        margin-bottom: 0;
        text-align: right
    }
}

.form-horizontal .has-feedback .form-control-feedback {
    right: 15px
}

@media(min-width:768px) {
    .form-horizontal .form-group-lg .control-label {
        padding-top: 11px;
        font-size: 18px
    }
}

@media(min-width:768px) {
    .form-horizontal .form-group-sm .control-label {
        padding-top: 6px;
        font-size: 12px
    }
}

.btn {
    display: inline-block;
    padding: 6px 12px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px
}

.btn.active.focus,
.btn.active:focus,
.btn.focus,
.btn:active.focus,
.btn:active:focus,
.btn:focus {
    outline: 5px auto -webkit-focus-ring-color;
    outline-offset: -2px
}

.btn.focus,
.btn:focus,
.btn:hover {
    color: #333;
    text-decoration: none
}

.btn.active,
.btn:active {
    background-image: none;
    outline: 0;
    -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125);
    box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125)
}

.btn.disabled,
.btn[disabled],
fieldset[disabled] .btn {
    cursor: not-allowed;
    filter: alpha(opacity=65);
    -webkit-box-shadow: none;
    box-shadow: none;
    opacity: .65
}

a.btn.disabled,
fieldset[disabled] a.btn {
    pointer-events: none
}

.btn-default {
    color: #333;
    background-color: #fff;
    border-color: #ccc
}

.btn-default.focus,
.btn-default:focus {
    color: #333;
    background-color: #e6e6e6;
    border-color: #8c8c8c
}

.btn-default:hover {
    color: #333;
    background-color: #e6e6e6;
    border-color: #adadad
}

.btn-default.active,
.btn-default:active,
.open>.dropdown-toggle.btn-default {
    color: #333;
    background-color: #e6e6e6;
    border-color: #adadad
}

.btn-default.active.focus,
.btn-default.active:focus,
.btn-default.active:hover,
.btn-default:active.focus,
.btn-default:active:focus,
.btn-default:active:hover,
.open>.dropdown-toggle.btn-default.focus,
.open>.dropdown-toggle.btn-default:focus,
.open>.dropdown-toggle.btn-default:hover {
    color: #333;
    background-color: #d4d4d4;
    border-color: #8c8c8c
}

.btn-default.active,
.btn-default:active,
.open>.dropdown-toggle.btn-default {
    background-image: none
}

.btn-default.disabled.focus,
.btn-default.disabled:focus,
.btn-default.disabled:hover,
.btn-default[disabled].focus,
.btn-default[disabled]:focus,
.btn-default[disabled]:hover,
fieldset[disabled] .btn-default.focus,
fieldset[disabled] .btn-default:focus,
fieldset[disabled] .btn-default:hover {
    background-color: #fff;
    border-color: #ccc
}

.btn-default .badge {
    color: #fff;
    background-color: #333
}

.btn-primary {
    color: #fff;
    background-color: #337ab7;
    border-color: #2e6da4
}

.btn-primary.focus,
.btn-primary:focus {
    color: #fff;
    background-color: #286090;
    border-color: #122b40
}

.btn-primary:hover {
    color: #fff;
    background-color: #286090;
    border-color: #204d74
}

.btn-primary.active,
.btn-primary:active,
.open>.dropdown-toggle.btn-primary {
    color: #fff;
    background-color: #286090;
    border-color: #204d74
}

.btn-primary.active.focus,
.btn-primary.active:focus,
.btn-primary.active:hover,
.btn-primary:active.focus,
.btn-primary:active:focus,
.btn-primary:active:hover,
.open>.dropdown-toggle.btn-primary.focus,
.open>.dropdown-toggle.btn-primary:focus,
.open>.dropdown-toggle.btn-primary:hover {
    color: #fff;
    background-color: #204d74;
    border-color: #122b40
}

.btn-primary.active,
.btn-primary:active,
.open>.dropdown-toggle.btn-primary {
    background-image: none
}

.btn-primary.disabled.focus,
.btn-primary.disabled:focus,
.btn-primary.disabled:hover,
.btn-primary[disabled].focus,
.btn-primary[disabled]:focus,
.btn-primary[disabled]:hover,
fieldset[disabled] .btn-primary.focus,
fieldset[disabled] .btn-primary:focus,
fieldset[disabled] .btn-primary:hover {
    background-color: #337ab7;
    border-color: #2e6da4
}

.btn-primary .badge {
    color: #337ab7;
    background-color: #fff
}

.btn-success {
    color: #fff;
    background-color: #5cb85c;
    border-color: #4cae4c
}

.btn-success.focus,
.btn-success:focus {
    color: #fff;
    background-color: #449d44;
    border-color: #255625
}

.btn-success:hover {
    color: #fff;
    background-color: #449d44;
    border-color: #398439
}

.btn-success.active,
.btn-success:active,
.open>.dropdown-toggle.btn-success {
    color: #fff;
    background-color: #449d44;
    border-color: #398439
}

.btn-success.active.focus,
.btn-success.active:focus,
.btn-success.active:hover,
.btn-success:active.focus,
.btn-success:active:focus,
.btn-success:active:hover,
.open>.dropdown-toggle.btn-success.focus,
.open>.dropdown-toggle.btn-success:focus,
.open>.dropdown-toggle.btn-success:hover {
    color: #fff;
    background-color: #398439;
    border-color: #255625
}

.btn-success.active,
.btn-success:active,
.open>.dropdown-toggle.btn-success {
    background-image: none
}

.btn-success.disabled.focus,
.btn-success.disabled:focus,
.btn-success.disabled:hover,
.btn-success[disabled].focus,
.btn-success[disabled]:focus,
.btn-success[disabled]:hover,
fieldset[disabled] .btn-success.focus,
fieldset[disabled] .btn-success:focus,
fieldset[disabled] .btn-success:hover {
    background-color: #5cb85c;
    border-color: #4cae4c
}

.btn-success .badge {
    color: #5cb85c;
    background-color: #fff
}

.btn-info {
    color: #fff;
    background-color: #5bc0de;
    border-color: #46b8da
}

.btn-info.focus,
.btn-info:focus {
    color: #fff;
    background-color: #31b0d5;
    border-color: #1b6d85
}

.btn-info:hover {
    color: #fff;
    background-color: #31b0d5;
    border-color: #269abc
}

.btn-info.active,
.btn-info:active,
.open>.dropdown-toggle.btn-info {
    color: #fff;
    background-color: #31b0d5;
    border-color: #269abc
}

.btn-info.active.focus,
.btn-info.active:focus,
.btn-info.active:hover,
.btn-info:active.focus,
.btn-info:active:focus,
.btn-info:active:hover,
.open>.dropdown-toggle.btn-info.focus,
.open>.dropdown-toggle.btn-info:focus,
.open>.dropdown-toggle.btn-info:hover {
    color: #fff;
    background-color: #269abc;
    border-color: #1b6d85
}

.btn-info.active,
.btn-info:active,
.open>.dropdown-toggle.btn-info {
    background-image: none
}

.btn-info.disabled.focus,
.btn-info.disabled:focus,
.btn-info.disabled:hover,
.btn-info[disabled].focus,
.btn-info[disabled]:focus,
.btn-info[disabled]:hover,
fieldset[disabled] .btn-info.focus,
fieldset[disabled] .btn-info:focus,
fieldset[disabled] .btn-info:hover {
    background-color: #5bc0de;
    border-color: #46b8da
}

.btn-info .badge {
    color: #5bc0de;
    background-color: #fff
}

.btn-warning {
    color: #fff;
    background-color: #f0ad4e;
    border-color: #eea236
}

.btn-warning.focus,
.btn-warning:focus {
    color: #fff;
    background-color: #ec971f;
    border-color: #985f0d
}

.btn-warning:hover {
    color: #fff;
    background-color: #ec971f;
    border-color: #d58512
}

.btn-warning.active,
.btn-warning:active,
.open>.dropdown-toggle.btn-warning {
    color: #fff;
    background-color: #ec971f;
    border-color: #d58512
}

.btn-warning.active.focus,
.btn-warning.active:focus,
.btn-warning.active:hover,
.btn-warning:active.focus,
.btn-warning:active:focus,
.btn-warning:active:hover,
.open>.dropdown-toggle.btn-warning.focus,
.open>.dropdown-toggle.btn-warning:focus,
.open>.dropdown-toggle.btn-warning:hover {
    color: #fff;
    background-color: #d58512;
    border-color: #985f0d
}

.btn-warning.active,
.btn-warning:active,
.open>.dropdown-toggle.btn-warning {
    background-image: none
}

.btn-warning.disabled.focus,
.btn-warning.disabled:focus,
.btn-warning.disabled:hover,
.btn-warning[disabled].focus,
.btn-warning[disabled]:focus,
.btn-warning[disabled]:hover,
fieldset[disabled] .btn-warning.focus,
fieldset[disabled] .btn-warning:focus,
fieldset[disabled] .btn-warning:hover {
    background-color: #f0ad4e;
    border-color: #eea236
}

.btn-warning .badge {
    color: #f0ad4e;
    background-color: #fff
}

.btn-danger {
    color: #fff;
    background-color: #d9534f;
    border-color: #d43f3a
}

.btn-danger.focus,
.btn-danger:focus {
    color: #fff;
    background-color: #c9302c;
    border-color: #761c19
}

.btn-danger:hover {
    color: #fff;
    background-color: #c9302c;
    border-color: #ac2925
}

.btn-danger.active,
.btn-danger:active,
.open>.dropdown-toggle.btn-danger {
    color: #fff;
    background-color: #c9302c;
    border-color: #ac2925
}

.btn-danger.active.focus,
.btn-danger.active:focus,
.btn-danger.active:hover,
.btn-danger:active.focus,
.btn-danger:active:focus,
.btn-danger:active:hover,
.open>.dropdown-toggle.btn-danger.focus,
.open>.dropdown-toggle.btn-danger:focus,
.open>.dropdown-toggle.btn-danger:hover {
    color: #fff;
    background-color: #ac2925;
    border-color: #761c19
}

.btn-danger.active,
.btn-danger:active,
.open>.dropdown-toggle.btn-danger {
    background-image: none
}

.btn-danger.disabled.focus,
.btn-danger.disabled:focus,
.btn-danger.disabled:hover,
.btn-danger[disabled].focus,
.btn-danger[disabled]:focus,
.btn-danger[disabled]:hover,
fieldset[disabled] .btn-danger.focus,
fieldset[disabled] .btn-danger:focus,
fieldset[disabled] .btn-danger:hover {
    background-color: #d9534f;
    border-color: #d43f3a
}

.btn-danger .badge {
    color: #d9534f;
    background-color: #fff
}

.btn-link {
    font-weight: 400;
    color: #337ab7;
    border-radius: 0
}

.btn-link,
.btn-link.active,
.btn-link:active,
.btn-link[disabled],
fieldset[disabled] .btn-link {
    background-color: transparent;
    -webkit-box-shadow: none;
    box-shadow: none
}

.btn-link,
.btn-link:active,
.btn-link:focus,
.btn-link:hover {
    border-color: transparent
}

.btn-link:focus,
.btn-link:hover {
    color: #23527c;
    text-decoration: underline;
    background-color: transparent
}

.btn-link[disabled]:focus,
.btn-link[disabled]:hover,
fieldset[disabled] .btn-link:focus,
fieldset[disabled] .btn-link:hover {
    color: #777;
    text-decoration: none
}

.btn-group-lg>.btn,
.btn-lg {
    padding: 10px 16px;
    font-size: 18px;
    line-height: 1.3333333;
    border-radius: 6px
}

.btn-group-sm>.btn,
.btn-sm {
    padding: 5px 10px;
    font-size: 12px;
    line-height: 1.5;
    border-radius: 3px
}

.btn-group-xs>.btn,
.btn-xs {
    padding: 1px 5px;
    font-size: 12px;
    line-height: 1.5;
    border-radius: 3px
}

.btn-block {
    display: block;
    width: 100%
}

.btn-block+.btn-block {
    margin-top: 5px
}

input[type=button].btn-block,
input[type=reset].btn-block,
input[type=submit].btn-block {
    width: 100%
}

.fade {
    opacity: 0;
    -webkit-transition: opacity .15s linear;
    -o-transition: opacity .15s linear;
    transition: opacity .15s linear
}

.fade.in {
    opacity: 1
}

.collapse {
    display: none
}

.collapse.in {
    display: block
}

tr.collapse.in {
    display: table-row
}

tbody.collapse.in {
    display: table-row-group
}

.collapsing {
    position: relative;
    height: 0;
    overflow: hidden;
    -webkit-transition-timing-function: ease;
    -o-transition-timing-function: ease;
    transition-timing-function: ease;
    -webkit-transition-duration: .35s;
    -o-transition-duration: .35s;
    transition-duration: .35s;
    -webkit-transition-property: height, visibility;
    -o-transition-property: height, visibility;
    transition-property: height, visibility
}

.caret {
    display: inline-block;
    width: 0;
    height: 0;
    margin-left: 2px;
    vertical-align: middle;
    border-top: 4px dashed;
    border-top: 4px solid\9;
    border-right: 4px solid transparent;
    border-left: 4px solid transparent
}

.dropdown,
.dropup {
    position: relative
}

.dropdown-toggle:focus {
    outline: 0
}

.dropdown-menu {
    position: absolute;
    top: 100%;
    left: 0;
    z-index: 1000;
    display: none;
    float: left;
    min-width: 160px;
    padding: 5px 0;
    margin: 2px 0 0;
    font-size: 14px;
    text-align: left;
    list-style: none;
    background-color: #fff;
    -webkit-background-clip: padding-box;
    background-clip: padding-box;
    border: 1px solid #ccc;
    border: 1px solid rgba(0, 0, 0, .15);
    border-radius: 4px;
    -webkit-box-shadow: 0 6px 12px rgba(0, 0, 0, .175);
    box-shadow: 0 6px 12px rgba(0, 0, 0, .175)
}

.dropdown-menu.pull-right {
    right: 0;
    left: auto
}

.dropdown-menu .divider {
    height: 1px;
    margin: 9px 0;
    overflow: hidden;
    background-color: #e5e5e5
}

.dropdown-menu>li>a {
    display: block;
    padding: 3px 20px;
    clear: both;
    font-weight: 400;
    line-height: 1.42857143;
    color: #333;
    white-space: nowrap
}

.dropdown-menu>li>a:focus,
.dropdown-menu>li>a:hover {
    color: #262626;
    text-decoration: none;
    background-color: #f5f5f5
}

.dropdown-menu>.active>a,
.dropdown-menu>.active>a:focus,
.dropdown-menu>.active>a:hover {
    color: #fff;
    text-decoration: none;
    background-color: #337ab7;
    outline: 0
}

.dropdown-menu>.disabled>a,
.dropdown-menu>.disabled>a:focus,
.dropdown-menu>.disabled>a:hover {
    color: #777
}

.dropdown-menu>.disabled>a:focus,
.dropdown-menu>.disabled>a:hover {
    text-decoration: none;
    cursor: not-allowed;
    background-color: transparent;
    background-image: none;
    filter: progid:DXImageTransform.Microsoft.gradient(enabled=false)
}

.open>.dropdown-menu {
    display: block
}

.open>a {
    outline: 0
}

.dropdown-menu-right {
    right: 0;
    left: auto
}

.dropdown-menu-left {
    right: auto;
    left: 0
}

.dropdown-header {
    display: block;
    padding: 3px 20px;
    font-size: 12px;
    line-height: 1.42857143;
    color: #777;
    white-space: nowrap
}

.dropdown-backdrop {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 990
}

.pull-right>.dropdown-menu {
    right: 0;
    left: auto
}

.dropup .caret,
.navbar-fixed-bottom .dropdown .caret {
    content: "";
    border-top: 0;
    border-bottom: 4px dashed;
    border-bottom: 4px solid\9
}

.dropup .dropdown-menu,
.navbar-fixed-bottom .dropdown .dropdown-menu {
    top: auto;
    bottom: 100%;
    margin-bottom: 2px
}

@media(min-width:768px) {
    .navbar-right .dropdown-menu {
        right: 0;
        left: auto
    }

    .navbar-right .dropdown-menu-left {
        right: auto;
        left: 0
    }
}

.btn-group,
.btn-group-vertical {
    position: relative;
    display: inline-block;
    vertical-align: middle
}

.btn-group-vertical>.btn,
.btn-group>.btn {
    position: relative;
    float: left
}

.btn-group-vertical>.btn.active,
.btn-group-vertical>.btn:active,
.btn-group-vertical>.btn:focus,
.btn-group-vertical>.btn:hover,
.btn-group>.btn.active,
.btn-group>.btn:active,
.btn-group>.btn:focus,
.btn-group>.btn:hover {
    z-index: 2
}

.btn-group .btn+.btn,
.btn-group .btn+.btn-group,
.btn-group .btn-group+.btn,
.btn-group .btn-group+.btn-group {
    margin-left: -1px
}

.btn-toolbar {
    margin-left: -5px
}

.btn-toolbar .btn,
.btn-toolbar .btn-group,
.btn-toolbar .input-group {
    float: left
}

.btn-toolbar>.btn,
.btn-toolbar>.btn-group,
.btn-toolbar>.input-group {
    margin-left: 5px
}

.btn-group>.btn:not(:first-child):not(:last-child):not(.dropdown-toggle) {
    border-radius: 0
}

.btn-group>.btn:first-child {
    margin-left: 0
}

.btn-group>.btn:first-child:not(:last-child):not(.dropdown-toggle) {
    border-top-right-radius: 0;
    border-bottom-right-radius: 0
}

.btn-group>.btn:last-child:not(:first-child),
.btn-group>.dropdown-toggle:not(:first-child) {
    border-top-left-radius: 0;
    border-bottom-left-radius: 0
}

.btn-group>.btn-group {
    float: left
}

.btn-group>.btn-group:not(:first-child):not(:last-child)>.btn {
    border-radius: 0
}

.btn-group>.btn-group:first-child:not(:last-child)>.btn:last-child,
.btn-group>.btn-group:first-child:not(:last-child)>.dropdown-toggle {
    border-top-right-radius: 0;
    border-bottom-right-radius: 0
}

.btn-group>.btn-group:last-child:not(:first-child)>.btn:first-child {
    border-top-left-radius: 0;
    border-bottom-left-radius: 0
}

.btn-group .dropdown-toggle:active,
.btn-group.open .dropdown-toggle {
    outline: 0
}

.btn-group>.btn+.dropdown-toggle {
    padding-right: 8px;
    padding-left: 8px
}

.btn-group>.btn-lg+.dropdown-toggle {
    padding-right: 12px;
    padding-left: 12px
}

.btn-group.open .dropdown-toggle {
    -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125);
    box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125)
}

.btn-group.open .dropdown-toggle.btn-link {
    -webkit-box-shadow: none;
    box-shadow: none
}

.btn .caret {
    margin-left: 0
}

.btn-lg .caret {
    border-width: 5px 5px 0;
    border-bottom-width: 0
}

.dropup .btn-lg .caret {
    border-width: 0 5px 5px
}

.btn-group-vertical>.btn,
.btn-group-vertical>.btn-group,
.btn-group-vertical>.btn-group>.btn {
    display: block;
    float: none;
    width: 100%;
    max-width: 100%
}

.btn-group-vertical>.btn-group>.btn {
    float: none
}

.btn-group-vertical>.btn+.btn,
.btn-group-vertical>.btn+.btn-group,
.btn-group-vertical>.btn-group+.btn,
.btn-group-vertical>.btn-group+.btn-group {
    margin-top: -1px;
    margin-left: 0
}

.btn-group-vertical>.btn:not(:first-child):not(:last-child) {
    border-radius: 0
}

.btn-group-vertical>.btn:first-child:not(:last-child) {
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0
}

.btn-group-vertical>.btn:last-child:not(:first-child) {
    border-top-left-radius: 0;
    border-top-right-radius: 0;
    border-bottom-right-radius: 4px;
    border-bottom-left-radius: 4px
}

.btn-group-vertical>.btn-group:not(:first-child):not(:last-child)>.btn {
    border-radius: 0
}

.btn-group-vertical>.btn-group:first-child:not(:last-child)>.btn:last-child,
.btn-group-vertical>.btn-group:first-child:not(:last-child)>.dropdown-toggle {
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0
}

.btn-group-vertical>.btn-group:last-child:not(:first-child)>.btn:first-child {
    border-top-left-radius: 0;
    border-top-right-radius: 0
}

.btn-group-justified {
    display: table;
    width: 100%;
    table-layout: fixed;
    border-collapse: separate
}

.btn-group-justified>.btn,
.btn-group-justified>.btn-group {
    display: table-cell;
    float: none;
    width: 1%
}

.btn-group-justified>.btn-group .btn {
    width: 100%
}

.btn-group-justified>.btn-group .dropdown-menu {
    left: auto
}

[data-toggle=buttons]>.btn input[type=checkbox],
[data-toggle=buttons]>.btn input[type=radio],
[data-toggle=buttons]>.btn-group>.btn input[type=checkbox],
[data-toggle=buttons]>.btn-group>.btn input[type=radio] {
    position: absolute;
    clip: rect(0, 0, 0, 0);
    pointer-events: none
}

.input-group {
    position: relative;
    display: table;
    border-collapse: separate
}

.input-group[class*=col-] {
    float: none;
    padding-right: 0;
    padding-left: 0
}

.input-group .form-control {
    position: relative;
    z-index: 2;
    float: left;
    width: 100%;
    margin-bottom: 0
}

.input-group .form-control:focus {
    z-index: 3
}

.input-group-lg>.form-control,
.input-group-lg>.input-group-addon,
.input-group-lg>.input-group-btn>.btn {
    height: 46px;
    padding: 10px 16px;
    font-size: 18px;
    line-height: 1.3333333;
    border-radius: 6px
}

select.input-group-lg>.form-control,
select.input-group-lg>.input-group-addon,
select.input-group-lg>.input-group-btn>.btn {
    height: 46px;
    line-height: 46px
}

select[multiple].input-group-lg>.form-control,
select[multiple].input-group-lg>.input-group-addon,
select[multiple].input-group-lg>.input-group-btn>.btn,
textarea.input-group-lg>.form-control,
textarea.input-group-lg>.input-group-addon,
textarea.input-group-lg>.input-group-btn>.btn {
    height: auto
}

.input-group-sm>.form-control,
.input-group-sm>.input-group-addon,
.input-group-sm>.input-group-btn>.btn {
    height: 30px;
    padding: 5px 10px;
    font-size: 12px;
    line-height: 1.5;
    border-radius: 3px
}

select.input-group-sm>.form-control,
select.input-group-sm>.input-group-addon,
select.input-group-sm>.input-group-btn>.btn {
    height: 30px;
    line-height: 30px
}

select[multiple].input-group-sm>.form-control,
select[multiple].input-group-sm>.input-group-addon,
select[multiple].input-group-sm>.input-group-btn>.btn,
textarea.input-group-sm>.form-control,
textarea.input-group-sm>.input-group-addon,
textarea.input-group-sm>.input-group-btn>.btn {
    height: auto
}

.input-group .form-control,
.input-group-addon,
.input-group-btn {
    display: table-cell
}

.input-group .form-control:not(:first-child):not(:last-child),
.input-group-addon:not(:first-child):not(:last-child),
.input-group-btn:not(:first-child):not(:last-child) {
    border-radius: 0
}

.input-group-addon,
.input-group-btn {
    width: 1%;
    white-space: nowrap;
    vertical-align: middle
}

.input-group-addon {
    padding: 6px 12px;
    font-size: 14px;
    font-weight: 400;
    line-height: 1;
    color: #555;
    text-align: center;
    background-color: #eee;
    border: 1px solid #ccc;
    border-radius: 4px
}

.input-group-addon.input-sm {
    padding: 5px 10px;
    font-size: 12px;
    border-radius: 3px
}

.input-group-addon.input-lg {
    padding: 10px 16px;
    font-size: 18px;
    border-radius: 6px
}

.input-group-addon input[type=checkbox],
.input-group-addon input[type=radio] {
    margin-top: 0
}

.input-group .form-control:first-child,
.input-group-addon:first-child,
.input-group-btn:first-child>.btn,
.input-group-btn:first-child>.btn-group>.btn,
.input-group-btn:first-child>.dropdown-toggle,
.input-group-btn:last-child>.btn-group:not(:last-child)>.btn,
.input-group-btn:last-child>.btn:not(:last-child):not(.dropdown-toggle) {
    border-top-right-radius: 0;
    border-bottom-right-radius: 0
}

.input-group-addon:first-child {
    border-right: 0
}

.input-group .form-control:last-child,
.input-group-addon:last-child,
.input-group-btn:first-child>.btn-group:not(:first-child)>.btn,
.input-group-btn:first-child>.btn:not(:first-child),
.input-group-btn:last-child>.btn,
.input-group-btn:last-child>.btn-group>.btn,
.input-group-btn:last-child>.dropdown-toggle {
    border-top-left-radius: 0;
    border-bottom-left-radius: 0
}

.input-group-addon:last-child {
    border-left: 0
}

.input-group-btn {
    position: relative;
    font-size: 0;
    white-space: nowrap
}

.input-group-btn>.btn {
    position: relative
}

.input-group-btn>.btn+.btn {
    margin-left: -1px
}

.input-group-btn>.btn:active,
.input-group-btn>.btn:focus,
.input-group-btn>.btn:hover {
    z-index: 2
}

.input-group-btn:first-child>.btn,
.input-group-btn:first-child>.btn-group {
    margin-right: -1px
}

.input-group-btn:last-child>.btn,
.input-group-btn:last-child>.btn-group {
    z-index: 2;
    margin-left: -1px
}

.nav {
    padding-left: 0;
    margin-bottom: 0;
    list-style: none
}

.nav>li {
    position: relative;
    display: block
}

.nav>li>a {
    position: relative;
    display: block;
    padding: 10px 15px
}

.nav>li>a:focus,
.nav>li>a:hover {
    text-decoration: none;
    background-color: #eee
}

.nav>li.disabled>a {
    color: #777
}

.nav>li.disabled>a:focus,
.nav>li.disabled>a:hover {
    color: #777;
    text-decoration: none;
    cursor: not-allowed;
    background-color: transparent
}

.nav .open>a,
.nav .open>a:focus,
.nav .open>a:hover {
    background-color: #eee;
    border-color: #337ab7
}

.nav .nav-divider {
    height: 1px;
    margin: 9px 0;
    overflow: hidden;
    background-color: #e5e5e5
}

.nav>li>a>img {
    max-width: none
}

.nav-tabs {
    border-bottom: 1px solid #ddd
}

.nav-tabs>li {
    float: left;
    margin-bottom: -1px
}

.nav-tabs>li>a {
    margin-right: 2px;
    line-height: 1.42857143;
    border: 1px solid transparent;
    border-radius: 4px 4px 0 0
}

.nav-tabs>li>a:hover {
    border-color: #eee #eee #ddd
}

.nav-tabs>li.active>a,
.nav-tabs>li.active>a:focus,
.nav-tabs>li.active>a:hover {
    color: #555;
    cursor: default;
    background-color: #fff;
    border: 1px solid #ddd;
    border-bottom-color: transparent
}

.nav-tabs.nav-justified {
    width: 100%;
    border-bottom: 0
}

.nav-tabs.nav-justified>li {
    float: none
}

.nav-tabs.nav-justified>li>a {
    margin-bottom: 5px;
    text-align: center
}

.nav-tabs.nav-justified>.dropdown .dropdown-menu {
    top: auto;
    left: auto
}

@media(min-width:768px) {
    .nav-tabs.nav-justified>li {
        display: table-cell;
        width: 1%
    }

    .nav-tabs.nav-justified>li>a {
        margin-bottom: 0
    }
}

.nav-tabs.nav-justified>li>a {
    margin-right: 0;
    border-radius: 4px
}

.nav-tabs.nav-justified>.active>a,
.nav-tabs.nav-justified>.active>a:focus,
.nav-tabs.nav-justified>.active>a:hover {
    border: 1px solid #ddd
}

@media(min-width:768px) {
    .nav-tabs.nav-justified>li>a {
        border-bottom: 1px solid #ddd;
        border-radius: 4px 4px 0 0
    }

    .nav-tabs.nav-justified>.active>a,
    .nav-tabs.nav-justified>.active>a:focus,
    .nav-tabs.nav-justified>.active>a:hover {
        border-bottom-color: #fff
    }
}

.nav-pills>li {
    float: left
}

.nav-pills>li>a {
    border-radius: 4px
}

.nav-pills>li+li {
    margin-left: 2px
}

.nav-pills>li.active>a,
.nav-pills>li.active>a:focus,
.nav-pills>li.active>a:hover {
    color: #fff;
    background-color: #337ab7
}

.nav-stacked>li {
    float: none
}

.nav-stacked>li+li {
    margin-top: 2px;
    margin-left: 0
}

.nav-justified {
    width: 100%
}

.nav-justified>li {
    float: none
}

.nav-justified>li>a {
    margin-bottom: 5px;
    text-align: center
}

.nav-justified>.dropdown .dropdown-menu {
    top: auto;
    left: auto
}

@media(min-width:768px) {
    .nav-justified>li {
        display: table-cell;
        width: 1%
    }

    .nav-justified>li>a {
        margin-bottom: 0
    }
}

.nav-tabs-justified {
    border-bottom: 0
}

.nav-tabs-justified>li>a {
    margin-right: 0;
    border-radius: 4px
}

.nav-tabs-justified>.active>a,
.nav-tabs-justified>.active>a:focus,
.nav-tabs-justified>.active>a:hover {
    border: 1px solid #ddd
}

@media(min-width:768px) {
    .nav-tabs-justified>li>a {
        border-bottom: 1px solid #ddd;
        border-radius: 4px 4px 0 0
    }

    .nav-tabs-justified>.active>a,
    .nav-tabs-justified>.active>a:focus,
    .nav-tabs-justified>.active>a:hover {
        border-bottom-color: #fff
    }
}

.tab-content>.tab-pane {
    display: none
}

.tab-content>.active {
    display: block
}

.nav-tabs .dropdown-menu {
    margin-top: -1px;
    border-top-left-radius: 0;
    border-top-right-radius: 0
}

.navbar {
    position: relative;
    min-height: 50px;
    margin-bottom: 20px;
    border: 1px solid transparent
}

@media(min-width:768px) {
    .navbar {
        border-radius: 4px
    }
}

@media(min-width:768px) {
    .navbar-header {
        float: left
    }
}

.navbar-collapse {
    padding-right: 15px;
    padding-left: 15px;
    overflow-x: visible;
    -webkit-overflow-scrolling: touch;
    border-top: 1px solid transparent;
    -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, .1);
    box-shadow: inset 0 1px 0 rgba(255, 255, 255, .1)
}

.navbar-collapse.in {
    overflow-y: auto
}

@media(min-width:768px) {
    .navbar-collapse {
        width: auto;
        border-top: 0;
        -webkit-box-shadow: none;
        box-shadow: none
    }

    .navbar-collapse.collapse {
        display: block !important;
        height: auto !important;
        padding-bottom: 0;
        overflow: visible !important
    }

    .navbar-collapse.in {
        overflow-y: visible
    }

    .navbar-fixed-bottom .navbar-collapse,
    .navbar-fixed-top .navbar-collapse,
    .navbar-static-top .navbar-collapse {
        padding-right: 0;
        padding-left: 0
    }
}

.navbar-fixed-bottom .navbar-collapse,
.navbar-fixed-top .navbar-collapse {
    max-height: 340px
}

@media(max-device-width:480px) and (orientation:landscape) {

    .navbar-fixed-bottom .navbar-collapse,
    .navbar-fixed-top .navbar-collapse {
        max-height: 200px
    }
}

.container-fluid>.navbar-collapse,
.container-fluid>.navbar-header,
.container>.navbar-collapse,
.container>.navbar-header {
    margin-right: -15px;
    margin-left: -15px
}

@media(min-width:768px) {

    .container-fluid>.navbar-collapse,
    .container-fluid>.navbar-header,
    .container>.navbar-collapse,
    .container>.navbar-header {
        margin-right: 0;
        margin-left: 0
    }
}

.navbar-static-top {
    z-index: 1000;
    border-width: 0 0 1px
}

@media(min-width:768px) {
    .navbar-static-top {
        border-radius: 0
    }
}

.navbar-fixed-bottom,
.navbar-fixed-top {
    position: fixed;
    right: 0;
    left: 0;
    z-index: 1030
}

@media(min-width:768px) {

    .navbar-fixed-bottom,
    .navbar-fixed-top {
        border-radius: 0
    }
}

.navbar-fixed-top {
    top: 0;
    border-width: 0 0 1px
}

.navbar-fixed-bottom {
    bottom: 0;
    margin-bottom: 0;
    border-width: 1px 0 0
}

.navbar-brand {
    float: left;
    height: 50px;
    padding: 15px 15px;
    font-size: 18px;
    line-height: 20px
}

.navbar-brand:focus,
.navbar-brand:hover {
    text-decoration: none
}

.navbar-brand>img {
    display: block
}

@media(min-width:768px) {

    .navbar>.container .navbar-brand,
    .navbar>.container-fluid .navbar-brand {
        margin-left: -15px
    }
}

.navbar-toggle {
    position: relative;
    float: right;
    padding: 9px 10px;
    margin-top: 8px;
    margin-right: 15px;
    margin-bottom: 8px;
    background-color: transparent;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px
}

.navbar-toggle:focus {
    outline: 0
}

.navbar-toggle .icon-bar {
    display: block;
    width: 22px;
    height: 2px;
    border-radius: 1px
}

.navbar-toggle .icon-bar+.icon-bar {
    margin-top: 4px
}

@media(min-width:768px) {
    .navbar-toggle {
        display: none
    }
}

.navbar-nav {
    margin: 7.5px -15px
}

.navbar-nav>li>a {
    padding-top: 10px;
    padding-bottom: 10px;
    line-height: 20px
}

@media(max-width:767px) {
    .navbar-nav .open .dropdown-menu {
        position: static;
        float: none;
        width: auto;
        margin-top: 0;
        background-color: transparent;
        border: 0;
        -webkit-box-shadow: none;
        box-shadow: none
    }

    .navbar-nav .open .dropdown-menu .dropdown-header,
    .navbar-nav .open .dropdown-menu>li>a {
        padding: 5px 15px 5px 25px
    }

    .navbar-nav .open .dropdown-menu>li>a {
        line-height: 20px
    }

    .navbar-nav .open .dropdown-menu>li>a:focus,
    .navbar-nav .open .dropdown-menu>li>a:hover {
        background-image: none
    }
}

@media(min-width:768px) {
    .navbar-nav {
        float: left;
        margin: 0
    }

    .navbar-nav>li {
        float: left
    }

    .navbar-nav>li>a {
        padding-top: 15px;
        padding-bottom: 15px
    }
}

.navbar-form {
    padding: 10px 15px;
    margin-top: 8px;
    margin-right: -15px;
    margin-bottom: 8px;
    margin-left: -15px;
    border-top: 1px solid transparent;
    border-bottom: 1px solid transparent;
    -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, .1), 0 1px 0 rgba(255, 255, 255, .1);
    box-shadow: inset 0 1px 0 rgba(255, 255, 255, .1), 0 1px 0 rgba(255, 255, 255, .1)
}

@media(min-width:768px) {
    .navbar-form .form-group {
        display: inline-block;
        margin-bottom: 0;
        vertical-align: middle
    }

    .navbar-form .form-control {
        display: inline-block;
        width: auto;
        vertical-align: middle
    }

    .navbar-form .form-control-static {
        display: inline-block
    }

    .navbar-form .input-group {
        display: inline-table;
        vertical-align: middle
    }

    .navbar-form .input-group .form-control,
    .navbar-form .input-group .input-group-addon,
    .navbar-form .input-group .input-group-btn {
        width: auto
    }

    .navbar-form .input-group>.form-control {
        width: 100%
    }

    .navbar-form .control-label {
        margin-bottom: 0;
        vertical-align: middle
    }

    .navbar-form .checkbox,
    .navbar-form .radio {
        display: inline-block;
        margin-top: 0;
        margin-bottom: 0;
        vertical-align: middle
    }

    .navbar-form .checkbox label,
    .navbar-form .radio label {
        padding-left: 0
    }

    .navbar-form .checkbox input[type=checkbox],
    .navbar-form .radio input[type=radio] {
        position: relative;
        margin-left: 0
    }

    .navbar-form .has-feedback .form-control-feedback {
        top: 0
    }
}

@media(max-width:767px) {
    .navbar-form .form-group {
        margin-bottom: 5px
    }

    .navbar-form .form-group:last-child {
        margin-bottom: 0
    }
}

@media(min-width:768px) {
    .navbar-form {
        width: auto;
        padding-top: 0;
        padding-bottom: 0;
        margin-right: 0;
        margin-left: 0;
        border: 0;
        -webkit-box-shadow: none;
        box-shadow: none
    }
}

.navbar-nav>li>.dropdown-menu {
    margin-top: 0;
    border-top-left-radius: 0;
    border-top-right-radius: 0
}

.navbar-fixed-bottom .navbar-nav>li>.dropdown-menu {
    margin-bottom: 0;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0
}

.navbar-btn {
    margin-top: 8px;
    margin-bottom: 8px
}

.navbar-btn.btn-sm {
    margin-top: 10px;
    margin-bottom: 10px
}

.navbar-btn.btn-xs {
    margin-top: 14px;
    margin-bottom: 14px
}

.navbar-text {
    margin-top: 15px;
    margin-bottom: 15px
}

@media(min-width:768px) {
    .navbar-text {
        float: left;
        margin-right: 15px;
        margin-left: 15px
    }
}

@media(min-width:768px) {
    .navbar-left {
        float: left !important
    }

    .navbar-right {
        float: right !important;
        margin-right: -15px
    }

    .navbar-right~.navbar-right {
        margin-right: 0
    }
}

.navbar-default {
    background-color: #f8f8f8;
    border-color: #e7e7e7
}

.navbar-default .navbar-brand {
    color: #777
}

.navbar-default .navbar-brand:focus,
.navbar-default .navbar-brand:hover {
    color: #5e5e5e;
    background-color: transparent
}

.navbar-default .navbar-text {
    color: #777
}

.navbar-default .navbar-nav>li>a {
    color: #777
}

.navbar-default .navbar-nav>li>a:focus,
.navbar-default .navbar-nav>li>a:hover {
    color: #333;
    background-color: transparent
}

.navbar-default .navbar-nav>.active>a,
.navbar-default .navbar-nav>.active>a:focus,
.navbar-default .navbar-nav>.active>a:hover {
    color: #555;
    background-color: #e7e7e7
}

.navbar-default .navbar-nav>.disabled>a,
.navbar-default .navbar-nav>.disabled>a:focus,
.navbar-default .navbar-nav>.disabled>a:hover {
    color: #ccc;
    background-color: transparent
}

.navbar-default .navbar-toggle {
    border-color: #ddd
}

.navbar-default .navbar-toggle:focus,
.navbar-default .navbar-toggle:hover {
    background-color: #ddd
}

.navbar-default .navbar-toggle .icon-bar {
    background-color: #888
}

.navbar-default .navbar-collapse,
.navbar-default .navbar-form {
    border-color: #e7e7e7
}

.navbar-default .navbar-nav>.open>a,
.navbar-default .navbar-nav>.open>a:focus,
.navbar-default .navbar-nav>.open>a:hover {
    color: #555;
    background-color: #e7e7e7
}

@media(max-width:767px) {
    .navbar-default .navbar-nav .open .dropdown-menu>li>a {
        color: #777
    }

    .navbar-default .navbar-nav .open .dropdown-menu>li>a:focus,
    .navbar-default .navbar-nav .open .dropdown-menu>li>a:hover {
        color: #333;
        background-color: transparent
    }

    .navbar-default .navbar-nav .open .dropdown-menu>.active>a,
    .navbar-default .navbar-nav .open .dropdown-menu>.active>a:focus,
    .navbar-default .navbar-nav .open .dropdown-menu>.active>a:hover {
        color: #555;
        background-color: #e7e7e7
    }

    .navbar-default .navbar-nav .open .dropdown-menu>.disabled>a,
    .navbar-default .navbar-nav .open .dropdown-menu>.disabled>a:focus,
    .navbar-default .navbar-nav .open .dropdown-menu>.disabled>a:hover {
        color: #ccc;
        background-color: transparent
    }
}

.navbar-default .navbar-link {
    color: #777
}

.navbar-default .navbar-link:hover {
    color: #333
}

.navbar-default .btn-link {
    color: #777
}

.navbar-default .btn-link:focus,
.navbar-default .btn-link:hover {
    color: #333
}

.navbar-default .btn-link[disabled]:focus,
.navbar-default .btn-link[disabled]:hover,
fieldset[disabled] .navbar-default .btn-link:focus,
fieldset[disabled] .navbar-default .btn-link:hover {
    color: #ccc
}

.navbar-inverse {
    background-color: #222;
    border-color: #080808
}

.navbar-inverse .navbar-brand {
    color: #9d9d9d
}

.navbar-inverse .navbar-brand:focus,
.navbar-inverse .navbar-brand:hover {
    color: #fff;
    background-color: transparent
}

.navbar-inverse .navbar-text {
    color: #9d9d9d
}

.navbar-inverse .navbar-nav>li>a {
    color: #9d9d9d
}

.navbar-inverse .navbar-nav>li>a:focus,
.navbar-inverse .navbar-nav>li>a:hover {
    color: #fff;
    background-color: transparent
}

.navbar-inverse .navbar-nav>.active>a,
.navbar-inverse .navbar-nav>.active>a:focus,
.navbar-inverse .navbar-nav>.active>a:hover {
    color: #fff;
    background-color: #080808
}

.navbar-inverse .navbar-nav>.disabled>a,
.navbar-inverse .navbar-nav>.disabled>a:focus,
.navbar-inverse .navbar-nav>.disabled>a:hover {
    color: #444;
    background-color: transparent
}

.navbar-inverse .navbar-toggle {
    border-color: #333
}

.navbar-inverse .navbar-toggle:focus,
.navbar-inverse .navbar-toggle:hover {
    background-color: #333
}

.navbar-inverse .navbar-toggle .icon-bar {
    background-color: #fff
}

.navbar-inverse .navbar-collapse,
.navbar-inverse .navbar-form {
    border-color: #101010
}

.navbar-inverse .navbar-nav>.open>a,
.navbar-inverse .navbar-nav>.open>a:focus,
.navbar-inverse .navbar-nav>.open>a:hover {
    color: #fff;
    background-color: #080808
}

@media(max-width:767px) {
    .navbar-inverse .navbar-nav .open .dropdown-menu>.dropdown-header {
        border-color: #080808
    }

    .navbar-inverse .navbar-nav .open .dropdown-menu .divider {
        background-color: #080808
    }

    .navbar-inverse .navbar-nav .open .dropdown-menu>li>a {
        color: #9d9d9d
    }

    .navbar-inverse .navbar-nav .open .dropdown-menu>li>a:focus,
    .navbar-inverse .navbar-nav .open .dropdown-menu>li>a:hover {
        color: #fff;
        background-color: transparent
    }

    .navbar-inverse .navbar-nav .open .dropdown-menu>.active>a,
    .navbar-inverse .navbar-nav .open .dropdown-menu>.active>a:focus,
    .navbar-inverse .navbar-nav .open .dropdown-menu>.active>a:hover {
        color: #fff;
        background-color: #080808
    }

    .navbar-inverse .navbar-nav .open .dropdown-menu>.disabled>a,
    .navbar-inverse .navbar-nav .open .dropdown-menu>.disabled>a:focus,
    .navbar-inverse .navbar-nav .open .dropdown-menu>.disabled>a:hover {
        color: #444;
        background-color: transparent
    }
}

.navbar-inverse .navbar-link {
    color: #9d9d9d
}

.navbar-inverse .navbar-link:hover {
    color: #fff
}

.navbar-inverse .btn-link {
    color: #9d9d9d
}

.navbar-inverse .btn-link:focus,
.navbar-inverse .btn-link:hover {
    color: #fff
}

.navbar-inverse .btn-link[disabled]:focus,
.navbar-inverse .btn-link[disabled]:hover,
fieldset[disabled] .navbar-inverse .btn-link:focus,
fieldset[disabled] .navbar-inverse .btn-link:hover {
    color: #444
}

.breadcrumb {
    padding: 8px 15px;
    margin-bottom: 20px;
    list-style: none;
    background-color: #f5f5f5;
    border-radius: 4px
}

.breadcrumb>li {
    display: inline-block
}

.breadcrumb>li+li:before {
    padding: 0 5px;
    color: #ccc;
    content: "/\00a0"
}

.breadcrumb>.active {
    color: #777
}

.pagination {
    display: inline-block;
    padding-left: 0;
    border-radius: 4px
}

.pagination>li {
    display: inline
}

.pagination>li>a,
.pagination>li>span {
    position: relative;
    float: left;
    padding: 6px 12px;
    margin-left: -1px;
    line-height: 1.42857143;
    color: #337ab7;
    text-decoration: none;
    background-color: #fff;
    border: 1px solid #ddd
}

.pagination>li:first-child>a,
.pagination>li:first-child>span {
    margin-left: 0;
    border-top-left-radius: 4px;
    border-bottom-left-radius: 4px
}

.pagination>li:last-child>a,
.pagination>li:last-child>span {
    border-top-right-radius: 4px;
    border-bottom-right-radius: 4px
}

.pagination>li>a:focus,
.pagination>li>a:hover,
.pagination>li>span:focus,
.pagination>li>span:hover {
    z-index: 2;
    color: #23527c;
    background-color: #eee;
    border-color: #ddd
}

.pagination>.active>a,
.pagination>.active>a:focus,
.pagination>.active>a:hover,
.pagination>.active>span,
.pagination>.active>span:focus,
.pagination>.active>span:hover {
    z-index: 3;
    color: #fff;
    cursor: default;
    background-color: #337ab7;
    border-color: #337ab7
}

.pagination>.disabled>a,
.pagination>.disabled>a:focus,
.pagination>.disabled>a:hover,
.pagination>.disabled>span,
.pagination>.disabled>span:focus,
.pagination>.disabled>span:hover {
    color: #777;
    cursor: not-allowed;
    background-color: #fff;
    border-color: #ddd
}

.pagination-lg>li>a,
.pagination-lg>li>span {
    padding: 10px 16px;
    font-size: 18px;
    line-height: 1.3333333
}

.pagination-lg>li:first-child>a,
.pagination-lg>li:first-child>span {
    border-top-left-radius: 6px;
    border-bottom-left-radius: 6px
}

.pagination-lg>li:last-child>a,
.pagination-lg>li:last-child>span {
    border-top-right-radius: 6px;
    border-bottom-right-radius: 6px
}

.pagination-sm>li>a,
.pagination-sm>li>span {
    padding: 5px 10px;
    font-size: 12px;
    line-height: 1.5
}

.pagination-sm>li:first-child>a,
.pagination-sm>li:first-child>span {
    border-top-left-radius: 3px;
    border-bottom-left-radius: 3px
}

.pagination-sm>li:last-child>a,
.pagination-sm>li:last-child>span {
    border-top-right-radius: 3px;
    border-bottom-right-radius: 3px
}

.pager {
    padding-left: 0;
    margin: 20px 0;
    text-align: center;
    list-style: none
}

.pager li {
    display: inline
}

.pager li>a,
.pager li>span {
    display: inline-block;
    padding: 5px 14px;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 15px
}

.pager li>a:focus,
.pager li>a:hover {
    text-decoration: none;
    background-color: #eee
}

.pager .next>a,
.pager .next>span {
    float: right
}

.pager .previous>a,
.pager .previous>span {
    float: left
}

.pager .disabled>a,
.pager .disabled>a:focus,
.pager .disabled>a:hover,
.pager .disabled>span {
    color: #777;
    cursor: not-allowed;
    background-color: #fff
}

.label {
    display: inline;
    padding: .2em .6em .3em;
    font-size: 75%;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em
}

a.label:focus,
a.label:hover {
    color: #fff;
    text-decoration: none;
    cursor: pointer
}

.label:empty {
    display: none
}

.btn .label {
    position: relative;
    top: -1px
}

.label-default {
    background-color: #777
}

.label-default[href]:focus,
.label-default[href]:hover {
    background-color: #5e5e5e
}

.label-primary {
    background-color: #337ab7
}

.label-primary[href]:focus,
.label-primary[href]:hover {
    background-color: #286090
}

.label-success {
    background-color: #5cb85c
}

.label-success[href]:focus,
.label-success[href]:hover {
    background-color: #449d44
}

.label-info {
    background-color: #5bc0de
}

.label-info[href]:focus,
.label-info[href]:hover {
    background-color: #31b0d5
}

.label-warning {
    background-color: #f0ad4e
}

.label-warning[href]:focus,
.label-warning[href]:hover {
    background-color: #ec971f
}

.label-danger {
    background-color: #d9534f
}

.label-danger[href]:focus,
.label-danger[href]:hover {
    background-color: #c9302c
}

.badge {
    display: inline-block;
    min-width: 10px;
    padding: 3px 7px;
    font-size: 12px;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #777;
    border-radius: 10px
}

.badge:empty {
    display: none
}

.btn .badge {
    position: relative;
    top: -1px
}

.btn-group-xs>.btn .badge,
.btn-xs .badge {
    top: 0;
    padding: 1px 5px
}

a.badge:focus,
a.badge:hover {
    color: #fff;
    text-decoration: none;
    cursor: pointer
}

.list-group-item.active>.badge,
.nav-pills>.active>a>.badge {
    color: #337ab7;
    background-color: #fff
}

.list-group-item>.badge {
    float: right
}

.list-group-item>.badge+.badge {
    margin-right: 5px
}

.nav-pills>li>a>.badge {
    margin-left: 3px
}

.jumbotron {
    padding-top: 30px;
    padding-bottom: 30px;
    margin-bottom: 30px;
    color: inherit;
    background-color: #eee
}

.jumbotron .h1,
.jumbotron h1 {
    color: inherit
}

.jumbotron p {
    margin-bottom: 15px;
    font-size: 21px;
    font-weight: 200
}

.jumbotron>hr {
    border-top-color: #d5d5d5
}

.container .jumbotron,
.container-fluid .jumbotron {
    padding-right: 15px;
    padding-left: 15px;
    border-radius: 6px
}

.jumbotron .container {
    max-width: 100%
}

@media screen and (min-width:768px) {
    .jumbotron {
        padding-top: 48px;
        padding-bottom: 48px
    }

    .container .jumbotron,
    .container-fluid .jumbotron {
        padding-right: 60px;
        padding-left: 60px
    }

    .jumbotron .h1,
    .jumbotron h1 {
        font-size: 63px
    }
}

.thumbnail {
    display: block;
    padding: 4px;
    margin-bottom: 20px;
    line-height: 1.42857143;
    background-color: #fff;
    border: 1px solid #ddd;
    border-radius: 4px;
    -webkit-transition: border .2s ease-in-out;
    -o-transition: border .2s ease-in-out;
    transition: border .2s ease-in-out
}

.thumbnail a>img,
.thumbnail>img {
    margin-right: auto;
    margin-left: auto
}

a.thumbnail.active,
a.thumbnail:focus,
a.thumbnail:hover {
    border-color: #337ab7
}

.thumbnail .caption {
    padding: 9px;
    color: #333
}

.alert {
    padding: 15px;
    margin-bottom: 20px;
    border: 1px solid transparent;
    border-radius: 4px
}

.alert h4 {
    margin-top: 0;
    color: inherit
}

.alert .alert-link {
    font-weight: 700
}

.alert>p,
.alert>ul {
    margin-bottom: 0
}

.alert>p+p {
    margin-top: 5px
}

.alert-dismissable,
.alert-dismissible {
    padding-right: 35px
}

.alert-dismissable .close,
.alert-dismissible .close {
    position: relative;
    top: -2px;
    right: -21px;
    color: inherit
}

.alert-success {
    color: #3c763d;
    background-color: #dff0d8;
    border-color: #d6e9c6
}

.alert-success hr {
    border-top-color: #c9e2b3
}

.alert-success .alert-link {
    color: #2b542c
}

.alert-info {
    color: #31708f;
    background-color: #d9edf7;
    border-color: #bce8f1
}

.alert-info hr {
    border-top-color: #a6e1ec
}

.alert-info .alert-link {
    color: #245269
}

.alert-warning {
    color: #8a6d3b;
    background-color: #fcf8e3;
    border-color: #faebcc
}

.alert-warning hr {
    border-top-color: #f7e1b5
}

.alert-warning .alert-link {
    color: #66512c
}

.alert-danger {
    color: #a94442;
    background-color: #f2dede;
    border-color: #ebccd1
}

.alert-danger hr {
    border-top-color: #e4b9c0
}

.alert-danger .alert-link {
    color: #843534
}

@-webkit-keyframes progress-bar-stripes {
    from {
        background-position: 40px 0
    }

    to {
        background-position: 0 0
    }
}

@-o-keyframes progress-bar-stripes {
    from {
        background-position: 40px 0
    }

    to {
        background-position: 0 0
    }
}

@keyframes progress-bar-stripes {
    from {
        background-position: 40px 0
    }

    to {
        background-position: 0 0
    }
}

.progress {
    height: 20px;
    margin-bottom: 20px;
    overflow: hidden;
    background-color: #f5f5f5;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 2px rgba(0, 0, 0, .1);
    box-shadow: inset 0 1px 2px rgba(0, 0, 0, .1)
}

.progress-bar {
    float: left;
    width: 0;
    height: 100%;
    font-size: 12px;
    line-height: 20px;
    color: #fff;
    text-align: center;
    background-color: #337ab7;
    -webkit-box-shadow: inset 0 -1px 0 rgba(0, 0, 0, .15);
    box-shadow: inset 0 -1px 0 rgba(0, 0, 0, .15);
    -webkit-transition: width .6s ease;
    -o-transition: width .6s ease;
    transition: width .6s ease
}

.progress-bar-striped,
.progress-striped .progress-bar {
    background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent);
    background-image: -o-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent);
    background-image: linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent);
    -webkit-background-size: 40px 40px;
    background-size: 40px 40px
}

.progress-bar.active,
.progress.active .progress-bar {
    -webkit-animation: progress-bar-stripes 2s linear infinite;
    -o-animation: progress-bar-stripes 2s linear infinite;
    animation: progress-bar-stripes 2s linear infinite
}

.progress-bar-success {
    background-color: #5cb85c
}

.progress-striped .progress-bar-success {
    background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent);
    background-image: -o-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent);
    background-image: linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
}

.progress-bar-info {
    background-color: #5bc0de
}

.progress-striped .progress-bar-info {
    background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent);
    background-image: -o-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent);
    background-image: linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
}

.progress-bar-warning {
    background-color: #f0ad4e
}

.progress-striped .progress-bar-warning {
    background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent);
    background-image: -o-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent);
    background-image: linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
}

.progress-bar-danger {
    background-color: #d9534f
}

.progress-striped .progress-bar-danger {
    background-image: -webkit-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent);
    background-image: -o-linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent);
    background-image: linear-gradient(45deg, rgba(255, 255, 255, .15) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, .15) 50%, rgba(255, 255, 255, .15) 75%, transparent 75%, transparent)
}

.media {
    margin-top: 15px
}

.media:first-child {
    margin-top: 0
}

.media,
.media-body {
    overflow: hidden;
    zoom: 1
}

.media-body {
    width: 10000px
}

.media-object {
    display: block
}

.media-object.img-thumbnail {
    max-width: none
}

.media-right,
.media>.pull-right {
    padding-left: 10px
}

.media-left,
.media>.pull-left {
    padding-right: 10px
}

.media-body,
.media-left,
.media-right {
    display: table-cell;
    vertical-align: top
}

.media-middle {
    vertical-align: middle
}

.media-bottom {
    vertical-align: bottom
}

.media-heading {
    margin-top: 0;
    margin-bottom: 5px
}

.media-list {
    padding-left: 0;
    list-style: none
}

.list-group {
    padding-left: 0;
    margin-bottom: 20px
}

.list-group-item {
    position: relative;
    display: block;
    padding: 10px 15px;
    margin-bottom: -1px;
    background-color: #fff;
    border: 1px solid #ddd
}

.list-group-item:first-child {
    border-top-left-radius: 4px;
    border-top-right-radius: 4px
}

.list-group-item:last-child {
    margin-bottom: 0;
    border-bottom-right-radius: 4px;
    border-bottom-left-radius: 4px
}

a.list-group-item,
button.list-group-item {
    color: #555
}

a.list-group-item .list-group-item-heading,
button.list-group-item .list-group-item-heading {
    color: #333
}

a.list-group-item:focus,
a.list-group-item:hover,
button.list-group-item:focus,
button.list-group-item:hover {
    color: #555;
    text-decoration: none;
    background-color: #f5f5f5
}

button.list-group-item {
    width: 100%;
    text-align: left
}

.list-group-item.disabled,
.list-group-item.disabled:focus,
.list-group-item.disabled:hover {
    color: #777;
    cursor: not-allowed;
    background-color: #eee
}

.list-group-item.disabled .list-group-item-heading,
.list-group-item.disabled:focus .list-group-item-heading,
.list-group-item.disabled:hover .list-group-item-heading {
    color: inherit
}

.list-group-item.disabled .list-group-item-text,
.list-group-item.disabled:focus .list-group-item-text,
.list-group-item.disabled:hover .list-group-item-text {
    color: #777
}

.list-group-item.active,
.list-group-item.active:focus,
.list-group-item.active:hover {
    z-index: 2;
    color: #fff;
    background-color: #337ab7;
    border-color: #337ab7
}

.list-group-item.active .list-group-item-heading,
.list-group-item.active .list-group-item-heading>.small,
.list-group-item.active .list-group-item-heading>small,
.list-group-item.active:focus .list-group-item-heading,
.list-group-item.active:focus .list-group-item-heading>.small,
.list-group-item.active:focus .list-group-item-heading>small,
.list-group-item.active:hover .list-group-item-heading,
.list-group-item.active:hover .list-group-item-heading>.small,
.list-group-item.active:hover .list-group-item-heading>small {
    color: inherit
}

.list-group-item.active .list-group-item-text,
.list-group-item.active:focus .list-group-item-text,
.list-group-item.active:hover .list-group-item-text {
    color: #c7ddef
}

.list-group-item-success {
    color: #3c763d;
    background-color: #dff0d8
}

a.list-group-item-success,
button.list-group-item-success {
    color: #3c763d
}

a.list-group-item-success .list-group-item-heading,
button.list-group-item-success .list-group-item-heading {
    color: inherit
}

a.list-group-item-success:focus,
a.list-group-item-success:hover,
button.list-group-item-success:focus,
button.list-group-item-success:hover {
    color: #3c763d;
    background-color: #d0e9c6
}

a.list-group-item-success.active,
a.list-group-item-success.active:focus,
a.list-group-item-success.active:hover,
button.list-group-item-success.active,
button.list-group-item-success.active:focus,
button.list-group-item-success.active:hover {
    color: #fff;
    background-color: #3c763d;
    border-color: #3c763d
}

.list-group-item-info {
    color: #31708f;
    background-color: #d9edf7
}

a.list-group-item-info,
button.list-group-item-info {
    color: #31708f
}

a.list-group-item-info .list-group-item-heading,
button.list-group-item-info .list-group-item-heading {
    color: inherit
}

a.list-group-item-info:focus,
a.list-group-item-info:hover,
button.list-group-item-info:focus,
button.list-group-item-info:hover {
    color: #31708f;
    background-color: #c4e3f3
}

a.list-group-item-info.active,
a.list-group-item-info.active:focus,
a.list-group-item-info.active:hover,
button.list-group-item-info.active,
button.list-group-item-info.active:focus,
button.list-group-item-info.active:hover {
    color: #fff;
    background-color: #31708f;
    border-color: #31708f
}

.list-group-item-warning {
    color: #8a6d3b;
    background-color: #fcf8e3
}

a.list-group-item-warning,
button.list-group-item-warning {
    color: #8a6d3b
}

a.list-group-item-warning .list-group-item-heading,
button.list-group-item-warning .list-group-item-heading {
    color: inherit
}

a.list-group-item-warning:focus,
a.list-group-item-warning:hover,
button.list-group-item-warning:focus,
button.list-group-item-warning:hover {
    color: #8a6d3b;
    background-color: #faf2cc
}

a.list-group-item-warning.active,
a.list-group-item-warning.active:focus,
a.list-group-item-warning.active:hover,
button.list-group-item-warning.active,
button.list-group-item-warning.active:focus,
button.list-group-item-warning.active:hover {
    color: #fff;
    background-color: #8a6d3b;
    border-color: #8a6d3b
}

.list-group-item-danger {
    color: #a94442;
    background-color: #f2dede
}

a.list-group-item-danger,
button.list-group-item-danger {
    color: #a94442
}

a.list-group-item-danger .list-group-item-heading,
button.list-group-item-danger .list-group-item-heading {
    color: inherit
}

a.list-group-item-danger:focus,
a.list-group-item-danger:hover,
button.list-group-item-danger:focus,
button.list-group-item-danger:hover {
    color: #a94442;
    background-color: #ebcccc
}

a.list-group-item-danger.active,
a.list-group-item-danger.active:focus,
a.list-group-item-danger.active:hover,
button.list-group-item-danger.active,
button.list-group-item-danger.active:focus,
button.list-group-item-danger.active:hover {
    color: #fff;
    background-color: #a94442;
    border-color: #a94442
}

.list-group-item-heading {
    margin-top: 0;
    margin-bottom: 5px
}

.list-group-item-text {
    margin-bottom: 0;
    line-height: 1.3
}

.panel {
    margin-bottom: 20px;
    background-color: #fff;
    border: 1px solid transparent;
    border-radius: 4px;
    -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
    box-shadow: 0 1px 1px rgba(0, 0, 0, .05)
}

.panel-body {
    padding: 15px
}

.panel-heading {
    padding: 10px 15px;
    border-bottom: 1px solid transparent;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px
}

.panel-heading>.dropdown .dropdown-toggle {
    color: inherit
}

.panel-title {
    margin-top: 0;
    margin-bottom: 0;
    font-size: 16px;
    color: inherit
}

.panel-title>.small,
.panel-title>.small>a,
.panel-title>a,
.panel-title>small,
.panel-title>small>a {
    color: inherit
}

.panel-footer {
    padding: 10px 15px;
    background-color: #f5f5f5;
    border-top: 1px solid #ddd;
    border-bottom-right-radius: 3px;
    border-bottom-left-radius: 3px
}

.panel>.list-group,
.panel>.panel-collapse>.list-group {
    margin-bottom: 0
}

.panel>.list-group .list-group-item,
.panel>.panel-collapse>.list-group .list-group-item {
    border-width: 1px 0;
    border-radius: 0
}

.panel>.list-group:first-child .list-group-item:first-child,
.panel>.panel-collapse>.list-group:first-child .list-group-item:first-child {
    border-top: 0;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px
}

.panel>.list-group:last-child .list-group-item:last-child,
.panel>.panel-collapse>.list-group:last-child .list-group-item:last-child {
    border-bottom: 0;
    border-bottom-right-radius: 3px;
    border-bottom-left-radius: 3px
}

.panel>.panel-heading+.panel-collapse>.list-group .list-group-item:first-child {
    border-top-left-radius: 0;
    border-top-right-radius: 0
}

.panel-heading+.list-group .list-group-item:first-child {
    border-top-width: 0
}

.list-group+.panel-footer {
    border-top-width: 0
}

.panel>.panel-collapse>.table,
.panel>.table,
.panel>.table-responsive>.table {
    margin-bottom: 0
}

.panel>.panel-collapse>.table caption,
.panel>.table caption,
.panel>.table-responsive>.table caption {
    padding-right: 15px;
    padding-left: 15px
}

.panel>.table-responsive:first-child>.table:first-child,
.panel>.table:first-child {
    border-top-left-radius: 3px;
    border-top-right-radius: 3px
}

.panel>.table-responsive:first-child>.table:first-child>tbody:first-child>tr:first-child,
.panel>.table-responsive:first-child>.table:first-child>thead:first-child>tr:first-child,
.panel>.table:first-child>tbody:first-child>tr:first-child,
.panel>.table:first-child>thead:first-child>tr:first-child {
    border-top-left-radius: 3px;
    border-top-right-radius: 3px
}

.panel>.table-responsive:first-child>.table:first-child>tbody:first-child>tr:first-child td:first-child,
.panel>.table-responsive:first-child>.table:first-child>tbody:first-child>tr:first-child th:first-child,
.panel>.table-responsive:first-child>.table:first-child>thead:first-child>tr:first-child td:first-child,
.panel>.table-responsive:first-child>.table:first-child>thead:first-child>tr:first-child th:first-child,
.panel>.table:first-child>tbody:first-child>tr:first-child td:first-child,
.panel>.table:first-child>tbody:first-child>tr:first-child th:first-child,
.panel>.table:first-child>thead:first-child>tr:first-child td:first-child,
.panel>.table:first-child>thead:first-child>tr:first-child th:first-child {
    border-top-left-radius: 3px
}

.panel>.table-responsive:first-child>.table:first-child>tbody:first-child>tr:first-child td:last-child,
.panel>.table-responsive:first-child>.table:first-child>tbody:first-child>tr:first-child th:last-child,
.panel>.table-responsive:first-child>.table:first-child>thead:first-child>tr:first-child td:last-child,
.panel>.table-responsive:first-child>.table:first-child>thead:first-child>tr:first-child th:last-child,
.panel>.table:first-child>tbody:first-child>tr:first-child td:last-child,
.panel>.table:first-child>tbody:first-child>tr:first-child th:last-child,
.panel>.table:first-child>thead:first-child>tr:first-child td:last-child,
.panel>.table:first-child>thead:first-child>tr:first-child th:last-child {
    border-top-right-radius: 3px
}

.panel>.table-responsive:last-child>.table:last-child,
.panel>.table:last-child {
    border-bottom-right-radius: 3px;
    border-bottom-left-radius: 3px
}

.panel>.table-responsive:last-child>.table:last-child>tbody:last-child>tr:last-child,
.panel>.table-responsive:last-child>.table:last-child>tfoot:last-child>tr:last-child,
.panel>.table:last-child>tbody:last-child>tr:last-child,
.panel>.table:last-child>tfoot:last-child>tr:last-child {
    border-bottom-right-radius: 3px;
    border-bottom-left-radius: 3px
}

.panel>.table-responsive:last-child>.table:last-child>tbody:last-child>tr:last-child td:first-child,
.panel>.table-responsive:last-child>.table:last-child>tbody:last-child>tr:last-child th:first-child,
.panel>.table-responsive:last-child>.table:last-child>tfoot:last-child>tr:last-child td:first-child,
.panel>.table-responsive:last-child>.table:last-child>tfoot:last-child>tr:last-child th:first-child,
.panel>.table:last-child>tbody:last-child>tr:last-child td:first-child,
.panel>.table:last-child>tbody:last-child>tr:last-child th:first-child,
.panel>.table:last-child>tfoot:last-child>tr:last-child td:first-child,
.panel>.table:last-child>tfoot:last-child>tr:last-child th:first-child {
    border-bottom-left-radius: 3px
}

.panel>.table-responsive:last-child>.table:last-child>tbody:last-child>tr:last-child td:last-child,
.panel>.table-responsive:last-child>.table:last-child>tbody:last-child>tr:last-child th:last-child,
.panel>.table-responsive:last-child>.table:last-child>tfoot:last-child>tr:last-child td:last-child,
.panel>.table-responsive:last-child>.table:last-child>tfoot:last-child>tr:last-child th:last-child,
.panel>.table:last-child>tbody:last-child>tr:last-child td:last-child,
.panel>.table:last-child>tbody:last-child>tr:last-child th:last-child,
.panel>.table:last-child>tfoot:last-child>tr:last-child td:last-child,
.panel>.table:last-child>tfoot:last-child>tr:last-child th:last-child {
    border-bottom-right-radius: 3px
}

.panel>.panel-body+.table,
.panel>.panel-body+.table-responsive,
.panel>.table+.panel-body,
.panel>.table-responsive+.panel-body {
    border-top: 1px solid #ddd
}

.panel>.table>tbody:first-child>tr:first-child td,
.panel>.table>tbody:first-child>tr:first-child th {
    border-top: 0
}

.panel>.table-bordered,
.panel>.table-responsive>.table-bordered {
    border: 0
}

.panel>.table-bordered>tbody>tr>td:first-child,
.panel>.table-bordered>tbody>tr>th:first-child,
.panel>.table-bordered>tfoot>tr>td:first-child,
.panel>.table-bordered>tfoot>tr>th:first-child,
.panel>.table-bordered>thead>tr>td:first-child,
.panel>.table-bordered>thead>tr>th:first-child,
.panel>.table-responsive>.table-bordered>tbody>tr>td:first-child,
.panel>.table-responsive>.table-bordered>tbody>tr>th:first-child,
.panel>.table-responsive>.table-bordered>tfoot>tr>td:first-child,
.panel>.table-responsive>.table-bordered>tfoot>tr>th:first-child,
.panel>.table-responsive>.table-bordered>thead>tr>td:first-child,
.panel>.table-responsive>.table-bordered>thead>tr>th:first-child {
    border-left: 0
}

.panel>.table-bordered>tbody>tr>td:last-child,
.panel>.table-bordered>tbody>tr>th:last-child,
.panel>.table-bordered>tfoot>tr>td:last-child,
.panel>.table-bordered>tfoot>tr>th:last-child,
.panel>.table-bordered>thead>tr>td:last-child,
.panel>.table-bordered>thead>tr>th:last-child,
.panel>.table-responsive>.table-bordered>tbody>tr>td:last-child,
.panel>.table-responsive>.table-bordered>tbody>tr>th:last-child,
.panel>.table-responsive>.table-bordered>tfoot>tr>td:last-child,
.panel>.table-responsive>.table-bordered>tfoot>tr>th:last-child,
.panel>.table-responsive>.table-bordered>thead>tr>td:last-child,
.panel>.table-responsive>.table-bordered>thead>tr>th:last-child {
    border-right: 0
}

.panel>.table-bordered>tbody>tr:first-child>td,
.panel>.table-bordered>tbody>tr:first-child>th,
.panel>.table-bordered>thead>tr:first-child>td,
.panel>.table-bordered>thead>tr:first-child>th,
.panel>.table-responsive>.table-bordered>tbody>tr:first-child>td,
.panel>.table-responsive>.table-bordered>tbody>tr:first-child>th,
.panel>.table-responsive>.table-bordered>thead>tr:first-child>td,
.panel>.table-responsive>.table-bordered>thead>tr:first-child>th {
    border-bottom: 0
}

.panel>.table-bordered>tbody>tr:last-child>td,
.panel>.table-bordered>tbody>tr:last-child>th,
.panel>.table-bordered>tfoot>tr:last-child>td,
.panel>.table-bordered>tfoot>tr:last-child>th,
.panel>.table-responsive>.table-bordered>tbody>tr:last-child>td,
.panel>.table-responsive>.table-bordered>tbody>tr:last-child>th,
.panel>.table-responsive>.table-bordered>tfoot>tr:last-child>td,
.panel>.table-responsive>.table-bordered>tfoot>tr:last-child>th {
    border-bottom: 0
}

.panel>.table-responsive {
    margin-bottom: 0;
    border: 0
}

.panel-group {
    margin-bottom: 20px
}

.panel-group .panel {
    margin-bottom: 0;
    border-radius: 4px
}

.panel-group .panel+.panel {
    margin-top: 5px
}

.panel-group .panel-heading {
    border-bottom: 0
}

.panel-group .panel-heading+.panel-collapse>.list-group,
.panel-group .panel-heading+.panel-collapse>.panel-body {
    border-top: 1px solid #ddd
}

.panel-group .panel-footer {
    border-top: 0
}

.panel-group .panel-footer+.panel-collapse .panel-body {
    border-bottom: 1px solid #ddd
}

.panel-default {
    border-color: #ddd
}

.panel-default>.panel-heading {
    color: #333;
    background-color: #f5f5f5;
    border-color: #ddd
}

.panel-default>.panel-heading+.panel-collapse>.panel-body {
    border-top-color: #ddd
}

.panel-default>.panel-heading .badge {
    color: #f5f5f5;
    background-color: #333
}

.panel-default>.panel-footer+.panel-collapse>.panel-body {
    border-bottom-color: #ddd
}

.panel-primary {
    border-color: #337ab7
}

.panel-primary>.panel-heading {
    color: #fff;
    background-color: #337ab7;
    border-color: #337ab7
}

.panel-primary>.panel-heading+.panel-collapse>.panel-body {
    border-top-color: #337ab7
}

.panel-primary>.panel-heading .badge {
    color: #337ab7;
    background-color: #fff
}

.panel-primary>.panel-footer+.panel-collapse>.panel-body {
    border-bottom-color: #337ab7
}

.panel-success {
    border-color: #d6e9c6
}

.panel-success>.panel-heading {
    color: #3c763d;
    background-color: #dff0d8;
    border-color: #d6e9c6
}

.panel-success>.panel-heading+.panel-collapse>.panel-body {
    border-top-color: #d6e9c6
}

.panel-success>.panel-heading .badge {
    color: #dff0d8;
    background-color: #3c763d
}

.panel-success>.panel-footer+.panel-collapse>.panel-body {
    border-bottom-color: #d6e9c6
}

.panel-info {
    border-color: #bce8f1
}

.panel-info>.panel-heading {
    color: #31708f;
    background-color: #d9edf7;
    border-color: #bce8f1
}

.panel-info>.panel-heading+.panel-collapse>.panel-body {
    border-top-color: #bce8f1
}

.panel-info>.panel-heading .badge {
    color: #d9edf7;
    background-color: #31708f
}

.panel-info>.panel-footer+.panel-collapse>.panel-body {
    border-bottom-color: #bce8f1
}

.panel-warning {
    border-color: #faebcc
}

.panel-warning>.panel-heading {
    color: #8a6d3b;
    background-color: #fcf8e3;
    border-color: #faebcc
}

.panel-warning>.panel-heading+.panel-collapse>.panel-body {
    border-top-color: #faebcc
}

.panel-warning>.panel-heading .badge {
    color: #fcf8e3;
    background-color: #8a6d3b
}

.panel-warning>.panel-footer+.panel-collapse>.panel-body {
    border-bottom-color: #faebcc
}

.panel-danger {
    border-color: #ebccd1
}

.panel-danger>.panel-heading {
    color: #a94442;
    background-color: #f2dede;
    border-color: #ebccd1
}

.panel-danger>.panel-heading+.panel-collapse>.panel-body {
    border-top-color: #ebccd1
}

.panel-danger>.panel-heading .badge {
    color: #f2dede;
    background-color: #a94442
}

.panel-danger>.panel-footer+.panel-collapse>.panel-body {
    border-bottom-color: #ebccd1
}

.embed-responsive {
    position: relative;
    display: block;
    height: 0;
    padding: 0;
    overflow: hidden
}

.embed-responsive .embed-responsive-item,
.embed-responsive embed,
.embed-responsive iframe,
.embed-responsive object,
.embed-responsive video {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
    border: 0
}

.embed-responsive-16by9 {
    padding-bottom: 56.25%
}

.embed-responsive-4by3 {
    padding-bottom: 75%
}

.well {
    min-height: 20px;
    padding: 19px;
    margin-bottom: 20px;
    background-color: #f5f5f5;
    border: 1px solid #e3e3e3;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .05)
}

.well blockquote {
    border-color: #ddd;
    border-color: rgba(0, 0, 0, .15)
}

.well-lg {
    padding: 24px;
    border-radius: 6px
}

.well-sm {
    padding: 9px;
    border-radius: 3px
}

.close {
    float: right;
    font-size: 21px;
    font-weight: 700;
    line-height: 1;
    color: #000;
    text-shadow: 0 1px 0 #fff;
    filter: alpha(opacity=20);
    opacity: .2
}

.close:focus,
.close:hover {
    color: #000;
    text-decoration: none;
    cursor: pointer;
    filter: alpha(opacity=50);
    opacity: .5
}

button.close {
    -webkit-appearance: none;
    padding: 0;
    cursor: pointer;
    background: 0;
    border: 0
}

.modal-open {
    overflow: hidden
}

.modal {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 1050;
    display: none;
    overflow: hidden;
    -webkit-overflow-scrolling: touch;
    outline: 0
}

.modal.fade .modal-dialog {
    -webkit-transition: -webkit-transform .3s ease-out;
    -o-transition: -o-transform .3s ease-out;
    transition: transform .3s ease-out;
    -webkit-transform: translate(0, -25%);
    -ms-transform: translate(0, -25%);
    -o-transform: translate(0, -25%);
    transform: translate(0, -25%)
}

.modal.in .modal-dialog {
    -webkit-transform: translate(0, 0);
    -ms-transform: translate(0, 0);
    -o-transform: translate(0, 0);
    transform: translate(0, 0)
}

.modal-open .modal {
    overflow-x: hidden;
    overflow-y: auto
}

.modal-dialog {
    position: relative;
    width: auto;
    margin: 10px
}

.modal-content {
    position: relative;
    background-color: #fff;
    -webkit-background-clip: padding-box;
    background-clip: padding-box;
    border: 1px solid #999;
    border: 1px solid rgba(0, 0, 0, .2);
    border-radius: 6px;
    outline: 0;
    -webkit-box-shadow: 0 3px 9px rgba(0, 0, 0, .5);
    box-shadow: 0 3px 9px rgba(0, 0, 0, .5)
}

.modal-backdrop {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 1040;
    background-color: #000
}

.modal-backdrop.fade {
    filter: alpha(opacity=0);
    opacity: 0
}

.modal-backdrop.in {
    filter: alpha(opacity=50);
    opacity: .5
}

.modal-header {
    padding: 15px;
    border-bottom: 1px solid #e5e5e5
}

.modal-header .close {
    margin-top: -2px
}

.modal-title {
    margin: 0;
    line-height: 1.42857143
}

.modal-body {
    position: relative;
    padding: 15px
}

.modal-footer {
    padding: 15px;
    text-align: right;
    border-top: 1px solid #e5e5e5
}

.modal-footer .btn+.btn {
    margin-bottom: 0;
    margin-left: 5px
}

.modal-footer .btn-group .btn+.btn {
    margin-left: -1px
}

.modal-footer .btn-block+.btn-block {
    margin-left: 0
}

.modal-scrollbar-measure {
    position: absolute;
    top: -9999px;
    width: 50px;
    height: 50px;
    overflow: scroll
}

@media(min-width:768px) {
    .modal-dialog {
        width: 600px;
        margin: 30px auto
    }

    .modal-content {
        -webkit-box-shadow: 0 5px 15px rgba(0, 0, 0, .5);
        box-shadow: 0 5px 15px rgba(0, 0, 0, .5)
    }

    .modal-sm {
        width: 300px
    }
}

@media(min-width:992px) {
    .modal-lg {
        width: 900px
    }
}

.tooltip {
    position: absolute;
    z-index: 1070;
    display: block;
    font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
    font-size: 12px;
    font-style: normal;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: left;
    text-align: start;
    text-decoration: none;
    text-shadow: none;
    text-transform: none;
    letter-spacing: normal;
    word-break: normal;
    word-spacing: normal;
    word-wrap: normal;
    white-space: normal;
    filter: alpha(opacity=0);
    opacity: 0;
    line-break: auto
}

.tooltip.in {
    filter: alpha(opacity=90);
    opacity: .9
}

.tooltip.top {
    padding: 5px 0;
    margin-top: -3px
}

.tooltip.right {
    padding: 0 5px;
    margin-left: 3px
}

.tooltip.bottom {
    padding: 5px 0;
    margin-top: 3px
}

.tooltip.left {
    padding: 0 5px;
    margin-left: -3px
}

.tooltip-inner {
    max-width: 200px;
    padding: 3px 8px;
    color: #fff;
    text-align: center;
    background-color: #000;
    border-radius: 4px
}

.tooltip-arrow {
    position: absolute;
    width: 0;
    height: 0;
    border-color: transparent;
    border-style: solid
}

.tooltip.top .tooltip-arrow {
    bottom: 0;
    left: 50%;
    margin-left: -5px;
    border-width: 5px 5px 0;
    border-top-color: #000
}

.tooltip.top-left .tooltip-arrow {
    right: 5px;
    bottom: 0;
    margin-bottom: -5px;
    border-width: 5px 5px 0;
    border-top-color: #000
}

.tooltip.top-right .tooltip-arrow {
    bottom: 0;
    left: 5px;
    margin-bottom: -5px;
    border-width: 5px 5px 0;
    border-top-color: #000
}

.tooltip.right .tooltip-arrow {
    top: 50%;
    left: 0;
    margin-top: -5px;
    border-width: 5px 5px 5px 0;
    border-right-color: #000
}

.tooltip.left .tooltip-arrow {
    top: 50%;
    right: 0;
    margin-top: -5px;
    border-width: 5px 0 5px 5px;
    border-left-color: #000
}

.tooltip.bottom .tooltip-arrow {
    top: 0;
    left: 50%;
    margin-left: -5px;
    border-width: 0 5px 5px;
    border-bottom-color: #000
}

.tooltip.bottom-left .tooltip-arrow {
    top: 0;
    right: 5px;
    margin-top: -5px;
    border-width: 0 5px 5px;
    border-bottom-color: #000
}

.tooltip.bottom-right .tooltip-arrow {
    top: 0;
    left: 5px;
    margin-top: -5px;
    border-width: 0 5px 5px;
    border-bottom-color: #000
}

.popover {
    position: absolute;
    top: 0;
    left: 0;
    z-index: 1060;
    display: none;
    max-width: 276px;
    padding: 1px;
    font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
    font-size: 14px;
    font-style: normal;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: left;
    text-align: start;
    text-decoration: none;
    text-shadow: none;
    text-transform: none;
    letter-spacing: normal;
    word-break: normal;
    word-spacing: normal;
    word-wrap: normal;
    white-space: normal;
    background-color: #fff;
    -webkit-background-clip: padding-box;
    background-clip: padding-box;
    border: 1px solid #ccc;
    border: 1px solid rgba(0, 0, 0, .2);
    border-radius: 6px;
    -webkit-box-shadow: 0 5px 10px rgba(0, 0, 0, .2);
    box-shadow: 0 5px 10px rgba(0, 0, 0, .2);
    line-break: auto
}

.popover.top {
    margin-top: -10px
}

.popover.right {
    margin-left: 10px
}

.popover.bottom {
    margin-top: 10px
}

.popover.left {
    margin-left: -10px
}

.popover-title {
    padding: 8px 14px;
    margin: 0;
    font-size: 14px;
    background-color: #f7f7f7;
    border-bottom: 1px solid #ebebeb;
    border-radius: 5px 5px 0 0
}

.popover-content {
    padding: 9px 14px
}

.popover>.arrow,
.popover>.arrow:after {
    position: absolute;
    display: block;
    width: 0;
    height: 0;
    border-color: transparent;
    border-style: solid
}

.popover>.arrow {
    border-width: 11px
}

.popover>.arrow:after {
    content: "";
    border-width: 10px
}

.popover.top>.arrow {
    bottom: -11px;
    left: 50%;
    margin-left: -11px;
    border-top-color: #999;
    border-top-color: rgba(0, 0, 0, .25);
    border-bottom-width: 0
}

.popover.top>.arrow:after {
    bottom: 1px;
    margin-left: -10px;
    content: " ";
    border-top-color: #fff;
    border-bottom-width: 0
}

.popover.right>.arrow {
    top: 50%;
    left: -11px;
    margin-top: -11px;
    border-right-color: #999;
    border-right-color: rgba(0, 0, 0, .25);
    border-left-width: 0
}

.popover.right>.arrow:after {
    bottom: -10px;
    left: 1px;
    content: " ";
    border-right-color: #fff;
    border-left-width: 0
}

.popover.bottom>.arrow {
    top: -11px;
    left: 50%;
    margin-left: -11px;
    border-top-width: 0;
    border-bottom-color: #999;
    border-bottom-color: rgba(0, 0, 0, .25)
}

.popover.bottom>.arrow:after {
    top: 1px;
    margin-left: -10px;
    content: " ";
    border-top-width: 0;
    border-bottom-color: #fff
}

.popover.left>.arrow {
    top: 50%;
    right: -11px;
    margin-top: -11px;
    border-right-width: 0;
    border-left-color: #999;
    border-left-color: rgba(0, 0, 0, .25)
}

.popover.left>.arrow:after {
    right: 1px;
    bottom: -10px;
    content: " ";
    border-right-width: 0;
    border-left-color: #fff
}

.carousel {
    position: relative
}

.carousel-inner {
    position: relative;
    width: 100%;
    overflow: hidden
}

.carousel-inner>.item {
    position: relative;
    display: none;
    -webkit-transition: .6s ease-in-out left;
    -o-transition: .6s ease-in-out left;
    transition: .6s ease-in-out left
}

.carousel-inner>.item>a>img,
.carousel-inner>.item>img {
    line-height: 1
}

@media all and (transform-3d),
(-webkit-transform-3d) {
    .carousel-inner>.item {
        -webkit-transition: -webkit-transform .6s ease-in-out;
        -o-transition: -o-transform .6s ease-in-out;
        transition: transform .6s ease-in-out;
        -webkit-backface-visibility: hidden;
        backface-visibility: hidden;
        -webkit-perspective: 1000px;
        perspective: 1000px
    }

    .carousel-inner>.item.active.right,
    .carousel-inner>.item.next {
        left: 0;
        -webkit-transform: translate3d(100%, 0, 0);
        transform: translate3d(100%, 0, 0)
    }

    .carousel-inner>.item.active.left,
    .carousel-inner>.item.prev {
        left: 0;
        -webkit-transform: translate3d(-100%, 0, 0);
        transform: translate3d(-100%, 0, 0)
    }

    .carousel-inner>.item.active,
    .carousel-inner>.item.next.left,
    .carousel-inner>.item.prev.right {
        left: 0;
        -webkit-transform: translate3d(0, 0, 0);
        transform: translate3d(0, 0, 0)
    }
}

.carousel-inner>.active,
.carousel-inner>.next,
.carousel-inner>.prev {
    display: block
}

.carousel-inner>.active {
    left: 0
}

.carousel-inner>.next,
.carousel-inner>.prev {
    position: absolute;
    top: 0;
    width: 100%
}

.carousel-inner>.next {
    left: 100%
}

.carousel-inner>.prev {
    left: -100%
}

.carousel-inner>.next.left,
.carousel-inner>.prev.right {
    left: 0
}

.carousel-inner>.active.left {
    left: -100%
}

.carousel-inner>.active.right {
    left: 100%
}

.carousel-control {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    width: 15%;
    font-size: 20px;
    color: #fff;
    text-align: center;
    text-shadow: 0 1px 2px rgba(0, 0, 0, .6);
    background-color: rgba(0, 0, 0, 0);
    filter: alpha(opacity=50);
    opacity: .5
}

.carousel-control.left {
    background-image: -webkit-linear-gradient(left, rgba(0, 0, 0, .5) 0, rgba(0, 0, 0, .0001) 100%);
    background-image: -o-linear-gradient(left, rgba(0, 0, 0, .5) 0, rgba(0, 0, 0, .0001) 100%);
    background-image: -webkit-gradient(linear, left top, right top, from(rgba(0, 0, 0, .5)), to(rgba(0, 0, 0, .0001)));
    background-image: linear-gradient(to right, rgba(0, 0, 0, .5) 0, rgba(0, 0, 0, .0001) 100%);
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#80000000', endColorstr='#00000000', GradientType=1);
    background-repeat: repeat-x
}

.carousel-control.right {
    right: 0;
    left: auto;
    background-image: -webkit-linear-gradient(left, rgba(0, 0, 0, .0001) 0, rgba(0, 0, 0, .5) 100%);
    background-image: -o-linear-gradient(left, rgba(0, 0, 0, .0001) 0, rgba(0, 0, 0, .5) 100%);
    background-image: -webkit-gradient(linear, left top, right top, from(rgba(0, 0, 0, .0001)), to(rgba(0, 0, 0, .5)));
    background-image: linear-gradient(to right, rgba(0, 0, 0, .0001) 0, rgba(0, 0, 0, .5) 100%);
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#00000000', endColorstr='#80000000', GradientType=1);
    background-repeat: repeat-x
}

.carousel-control:focus,
.carousel-control:hover {
    color: #fff;
    text-decoration: none;
    filter: alpha(opacity=90);
    outline: 0;
    opacity: .9
}

.carousel-control .glyphicon-chevron-left,
.carousel-control .glyphicon-chevron-right,
.carousel-control .icon-next,
.carousel-control .icon-prev {
    position: absolute;
    top: 50%;
    z-index: 5;
    display: inline-block;
    margin-top: -10px
}

.carousel-control .glyphicon-chevron-left,
.carousel-control .icon-prev {
    left: 50%;
    margin-left: -10px
}

.carousel-control .glyphicon-chevron-right,
.carousel-control .icon-next {
    right: 50%;
    margin-right: -10px
}

.carousel-control .icon-next,
.carousel-control .icon-prev {
    width: 20px;
    height: 20px;
    font-family: serif;
    line-height: 1
}

.carousel-control .icon-prev:before {
    content: '\2039'
}

.carousel-control .icon-next:before {
    content: '\203a'
}

.carousel-indicators {
    position: absolute;
    bottom: 10px;
    left: 50%;
    z-index: 15;
    width: 60%;
    padding-left: 0;
    margin-left: -30%;
    text-align: center;
    list-style: none
}

.carousel-indicators li {
    display: inline-block;
    width: 10px;
    height: 10px;
    margin: 1px;
    text-indent: -999px;
    cursor: pointer;
    /* background-color: #000\9; */
    /* background-color: rgba(0, 0, 0, 0); */
    /* border: 1px solid #fff; */
    /* border-radius: 10px */
}

.carousel-indicators .active {
    width: 12px;
    height: 12px;
    margin: 0;
    background-color: #fff
}

.carousel-caption {
    position: absolute;
    right: 15%;
    bottom: 20px;
    left: 15%;
    z-index: 10;
    padding-top: 20px;
    padding-bottom: 20px;
    color: #fff;
    text-align: center;
    text-shadow: 0 1px 2px rgba(0, 0, 0, .6)
}

.carousel-caption .btn {
    text-shadow: none
}

@media screen and (min-width:768px) {

    .carousel-control .glyphicon-chevron-left,
    .carousel-control .glyphicon-chevron-right,
    .carousel-control .icon-next,
    .carousel-control .icon-prev {
        width: 30px;
        height: 30px;
        margin-top: -10px;
        font-size: 30px
    }

    .carousel-control .glyphicon-chevron-left,
    .carousel-control .icon-prev {
        margin-left: -10px
    }

    .carousel-control .glyphicon-chevron-right,
    .carousel-control .icon-next {
        margin-right: -10px
    }

    .carousel-caption {
        right: 20%;
        left: 20%;
        padding-bottom: 30px
    }

    .carousel-indicators {
        bottom: 20px
    }
}

.btn-group-vertical>.btn-group:after,
.btn-group-vertical>.btn-group:before,
.btn-toolbar:after,
.btn-toolbar:before,
.clearfix:after,
.clearfix:before,
.container-fluid:after,
.container-fluid:before,
.container:after,
.container:before,
.dl-horizontal dd:after,
.dl-horizontal dd:before,
.form-horizontal .form-group:after,
.form-horizontal .form-group:before,
.modal-footer:after,
.modal-footer:before,
.modal-header:after,
.modal-header:before,
.nav:after,
.nav:before,
.navbar-collapse:after,
.navbar-collapse:before,
.navbar-header:after,
.navbar-header:before,
.navbar:after,
.navbar:before,
.pager:after,
.pager:before,
.panel-body:after,
.panel-body:before,
.row:after,
.row:before {
    display: table;
    content: " "
}

.btn-group-vertical>.btn-group:after,
.btn-toolbar:after,
.clearfix:after,
.container-fluid:after,
.container:after,
.dl-horizontal dd:after,
.form-horizontal .form-group:after,
.modal-footer:after,
.modal-header:after,
.nav:after,
.navbar-collapse:after,
.navbar-header:after,
.navbar:after,
.pager:after,
.panel-body:after,
.row:after {
    clear: both
}

.center-block {
    display: block;
    margin-right: auto;
    margin-left: auto
}

.pull-right {
    float: right !important
}

.pull-left {
    float: left !important
}

.hide {
    display: none !important
}

.show {
    display: block !important
}

.invisible {
    visibility: hidden
}

.text-hide {
    font: 0/0 a;
    color: transparent;
    text-shadow: none;
    background-color: transparent;
    border: 0
}

.hidden {
    display: none !important
}

.affix {
    position: fixed
}

@-ms-viewport {
    width: device-width
}

.visible-lg,
.visible-md,
.visible-sm,
.visible-xs {
    display: none !important
}

.visible-lg-block,
.visible-lg-inline,
.visible-lg-inline-block,
.visible-md-block,
.visible-md-inline,
.visible-md-inline-block,
.visible-sm-block,
.visible-sm-inline,
.visible-sm-inline-block,
.visible-xs-block,
.visible-xs-inline,
.visible-xs-inline-block {
    display: none !important
}

@media(max-width:767px) {
    .visible-xs {
        display: block !important
    }

    table.visible-xs {
        display: table !important
    }

    tr.visible-xs {
        display: table-row !important
    }

    td.visible-xs,
    th.visible-xs {
        display: table-cell !important
    }
}

@media(max-width:767px) {
    .visible-xs-block {
        display: block !important
    }
}

@media(max-width:767px) {
    .visible-xs-inline {
        display: inline !important
    }
}

@media(max-width:767px) {
    .visible-xs-inline-block {
        display: inline-block !important
    }
}

@media(min-width:768px) and (max-width:991px) {
    .visible-sm {
        display: block !important
    }

    table.visible-sm {
        display: table !important
    }

    tr.visible-sm {
        display: table-row !important
    }

    td.visible-sm,
    th.visible-sm {
        display: table-cell !important
    }
}

@media(min-width:768px) and (max-width:991px) {
    .visible-sm-block {
        display: block !important
    }
}

@media(min-width:768px) and (max-width:991px) {
    .visible-sm-inline {
        display: inline !important
    }
}

@media(min-width:768px) and (max-width:991px) {
    .visible-sm-inline-block {
        display: inline-block !important
    }
}

@media(min-width:992px) and (max-width:1199px) {
    .visible-md {
        display: block !important
    }

    table.visible-md {
        display: table !important
    }

    tr.visible-md {
        display: table-row !important
    }

    td.visible-md,
    th.visible-md {
        display: table-cell !important
    }
}

@media(min-width:992px) and (max-width:1199px) {
    .visible-md-block {
        display: block !important
    }
}

@media(min-width:992px) and (max-width:1199px) {
    .visible-md-inline {
        display: inline !important
    }
}

@media(min-width:992px) and (max-width:1199px) {
    .visible-md-inline-block {
        display: inline-block !important
    }
}

@media(min-width:1200px) {
    .visible-lg {
        display: block !important
    }

    table.visible-lg {
        display: table !important
    }

    tr.visible-lg {
        display: table-row !important
    }

    td.visible-lg,
    th.visible-lg {
        display: table-cell !important
    }
}

@media(min-width:1200px) {
    .visible-lg-block {
        display: block !important
    }
}

@media(min-width:1200px) {
    .visible-lg-inline {
        display: inline !important
    }
}

@media(min-width:1200px) {
    .visible-lg-inline-block {
        display: inline-block !important
    }
}

@media(max-width:767px) {
    .hidden-xs {
        display: none !important
    }
}

@media(min-width:768px) and (max-width:991px) {
    .hidden-sm {
        display: none !important
    }
}

@media(min-width:992px) and (max-width:1199px) {
    .hidden-md {
        display: none !important
    }
}

@media(min-width:1200px) {
    .hidden-lg {
        display: none !important
    }
}

.visible-print {
    display: none !important
}

@media print {
    .visible-print {
        display: block !important
    }

    table.visible-print {
        display: table !important
    }

    tr.visible-print {
        display: table-row !important
    }

    td.visible-print,
    th.visible-print {
        display: table-cell !important
    }
}

.visible-print-block {
    display: none !important
}

@media print {
    .visible-print-block {
        display: block !important
    }
}

.visible-print-inline {
    display: none !important
}

@media print {
    .visible-print-inline {
        display: inline !important
    }
}

.visible-print-inline-block {
    display: none !important
}

@media print {
    .visible-print-inline-block {
        display: inline-block !important
    }
}

@media print {
    .hidden-print {
        display: none !important
    }
}

div.breadcrumbs {
    font: 15px blender-regular, south web light, Arial, Helvetica, sans-serif;
    direction: rtl;
    text-align: right;
    width: 100%;
    margin: 15px 0 10px 0
}

div.breadcrumbs li {
    display: inline-block;
    max-width: 400px;
    text-overflow: ellipsis;
    white-space: nowrap;
    color: #000
}

div.breadcrumbs li a {
    color: #014bad
}

div.breadcrumbs li a:not([href]) {
    color: #7d7d7d
}

div.breadcrumbs span::before {
    color: #7d7d7d;
    content: ' \00BB'
}

div.breadcrumbs ul {
    padding: 5px
}
</style>