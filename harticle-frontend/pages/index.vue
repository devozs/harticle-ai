<template>
    <!-- <div>
      <select v-model="locale">
        <option value="he">he</option>
        <option value="en">en </option>
      </select>
      <p :dir="dir">{{ $t('greeting') }}</p>
  </div> -->
    <div>
        <section class="container mx-auto lg:mt-3 transform duration-500 p-5 mt-1">
            <div
                class="w-12 h-12 rounded-xl bg-gradient-to-br flex items-center justify-center from-cyan-400 to-cyan-900 mb-1">
                <svg width="32" height="32" fill="none" viewBox="0 0 32 32">
                    <g clip-path="url(#build-anything_svg__clip0)">
                        <g filter="url(#build-anything_svg__filter0_d)">
                            <path fill="#FDBA74" fill-rule="evenodd"
                                d="M25.414 6.586a2 2 0 010 2.828l-20 20a2 2 0 11-2.828-2.828l20-20a2 2 0 012.828 0z"
                                clip-rule="evenodd">
                            </path>
                        </g>
                        <g filter="url(#build-anything_svg__filter1_dd)">
                            <path fill="#FFEDD5"
                                d="M26.85 3.844c.822-.339 1.644.483 1.305 1.305l-1.841 4.47 3.135 3.68c.577.678.048 1.715-.839 1.646l-4.817-.375-2.53 4.116c-.465.758-1.614.576-1.823-.288l-1.139-4.7-4.7-1.139c-.864-.209-1.046-1.358-.288-1.824l4.116-2.529-.375-4.817c-.069-.887.968-1.416 1.646-.84l3.68 3.136 4.47-1.841z">
                            </path>
                        </g>
                        <g filter="url(#build-anything_svg__filter2_dd)">
                            <path fill="#FED7AA"
                                d="M6.535 3.18a.5.5 0 01.93 0l.95 2.406 2.406.949a.5.5 0 010 .93l-2.406.95-.95 2.406a.5.5 0 01-.93 0l-.949-2.407-2.406-.949a.5.5 0 010-.93l2.406-.95.95-2.406z">
                            </path>
                        </g>
                        <g filter="url(#build-anything_svg__filter3_dd)">
                            <path fill="#FED7AA"
                                d="M20.535 23.18a.5.5 0 01.93 0l.667 1.689 1.69.666a.5.5 0 010 .93l-1.69.666-.666 1.69a.5.5 0 01-.93 0l-.667-1.69-1.69-.666a.5.5 0 010-.93l1.69-.666.666-1.69z">
                            </path>
                        </g>
                    </g>
                    <defs>
                        <filter id="build-anything_svg__filter0_d" width="28" height="28" x="0" y="5"
                            color-interpolation-filters="sRGB" filterUnits="userSpaceOnUse">
                            <feFlood flood-opacity="0" result="BackgroundImageFix"></feFlood>
                            <feColorMatrix in="SourceAlpha" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0">
                            </feColorMatrix>
                            <feOffset dy="1"></feOffset>
                            <feGaussianBlur stdDeviation="1"></feGaussianBlur>
                            <feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.05 0"></feColorMatrix>
                            <feBlend in2="BackgroundImageFix" result="effect1_dropShadow"></feBlend>
                            <feBlend in="SourceGraphic" in2="effect1_dropShadow" result="shape"></feBlend>
                        </filter>
                        <filter id="build-anything_svg__filter1_dd" width="22.855" height="22.855" x="9.836" y="0.309"
                            color-interpolation-filters="sRGB" filterUnits="userSpaceOnUse">
                            <feFlood flood-opacity="0" result="BackgroundImageFix"></feFlood>
                            <feColorMatrix in="SourceAlpha" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0">
                            </feColorMatrix>
                            <feOffset dy="1"></feOffset>
                            <feGaussianBlur stdDeviation="1"></feGaussianBlur>
                            <feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.06 0"></feColorMatrix>
                            <feBlend in2="BackgroundImageFix" result="effect1_dropShadow"></feBlend>
                            <feColorMatrix in="SourceAlpha" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0">
                            </feColorMatrix>
                            <feOffset dy="1"></feOffset>
                            <feGaussianBlur stdDeviation="1.5"></feGaussianBlur>
                            <feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.1 0"></feColorMatrix>
                            <feBlend in2="effect1_dropShadow" result="effect2_dropShadow"></feBlend>
                            <feBlend in="SourceGraphic" in2="effect2_dropShadow" result="shape"></feBlend>
                        </filter>
                        <filter id="build-anything_svg__filter2_dd" width="14.274" height="14.274" x="-0.137" y="0.863"
                            color-interpolation-filters="sRGB" filterUnits="userSpaceOnUse">
                            <feFlood flood-opacity="0" result="BackgroundImageFix"></feFlood>
                            <feColorMatrix in="SourceAlpha" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0">
                            </feColorMatrix>
                            <feOffset dy="1"></feOffset>
                            <feGaussianBlur stdDeviation="1"></feGaussianBlur>
                            <feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.06 0"></feColorMatrix>
                            <feBlend in2="BackgroundImageFix" result="effect1_dropShadow"></feBlend>
                            <feColorMatrix in="SourceAlpha" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0">
                            </feColorMatrix>
                            <feOffset dy="1"></feOffset>
                            <feGaussianBlur stdDeviation="1.5"></feGaussianBlur>
                            <feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.1 0"></feColorMatrix>
                            <feBlend in2="effect1_dropShadow" result="effect2_dropShadow"></feBlend>
                            <feBlend in="SourceGraphic" in2="effect2_dropShadow" result="shape"></feBlend>
                        </filter>
                        <filter id="build-anything_svg__filter3_dd" width="12.274" height="12.274" x="14.863" y="20.863"
                            color-interpolation-filters="sRGB" filterUnits="userSpaceOnUse">
                            <feFlood flood-opacity="0" result="BackgroundImageFix"></feFlood>
                            <feColorMatrix in="SourceAlpha" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0">
                            </feColorMatrix>
                            <feOffset dy="1"></feOffset>
                            <feGaussianBlur stdDeviation="1"></feGaussianBlur>
                            <feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.06 0"></feColorMatrix>
                            <feBlend in2="BackgroundImageFix" result="effect1_dropShadow"></feBlend>
                            <feColorMatrix in="SourceAlpha" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0">
                            </feColorMatrix>
                            <feOffset dy="1"></feOffset>
                            <feGaussianBlur stdDeviation="1.5"></feGaussianBlur>
                            <feColorMatrix values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.1 0"></feColorMatrix>
                            <feBlend in2="effect1_dropShadow" result="effect2_dropShadow"></feBlend>
                            <feBlend in="SourceGraphic" in2="effect2_dropShadow" result="shape"></feBlend>
                        </filter>
                        <clipPath id="build-anything_svg__clip0">
                            <path fill="#fff" d="M0 0h32v32H0z"></path>
                        </clipPath>
                    </defs>
                </svg>
            </div>
            <a class="sm:text-lg sm:leading-snug font-semibold tracking-wide uppercase text-cyan-600 mb-3"
                href="https://devozs.com" target="_blank" data-size="large" data-show-count="true"
                aria-label="Star devozs on GitHub">
                {{ $t('github') }}
            </a>
            <h1
                class="text-4xl sm:text-5xl lg:text-6xl leading-none font-extrabold text-gray-900 tracking-tight mt-2 mb-2 md:mt-4 md:mb-4">
                {{ $t('heading') }}
            </h1>
            <p :dir="dir"
                class="max-w-5xl text-lg sm:text-2xl sm:leading-10 space-y-6 mb-2 text-gray-700 font-extrabold">
                <!-- <ShareNetwork network="twitter" url="https://harticle.devozs.com"
                    :title="(selected && selected.title) || 'Harticle'" twitter-user="ozishemesh"
                    hashtags="harticle,AI,IFPL,DevOzs">
                    Share on Twitter
                </ShareNetwork> -->
                <a onclick="gaTrackEvent('article', 'share-bar-twitter');"
                    :href="'http://twitter.com/intent/tweet?text=' + 'Harticle' + '&amp;url=https://harticle.devozs.com'"
                    target="_blank" tabindex="10000" data-font-size="16" data-font-size-type="px">
                    <img loading="lazy"
                        src="https://images.one.co.il/images/msites/2022/03/02/f1122ca799164f8406265daf2332cd5a.png"
                        alt="Harticle" tabindex="-1"></a>


            </p>
            <p :dir="dir">{{ $t("disclaimer") }}</p>

        </section>

        <section :dir="dir" class="container mx-auto p-5 md:p-3 transform duration-500">
            <div v-if="isCreating" class="flex flex-col p-3 bg-white shadow-md hover:shodow-lg rounded-2xl">
                <div class="shadow" role="alert">
                    <div class="flex">
                        <div class=" w-20 text-center p-2">
                            <div class="flex justify-center h-full items-center">
                                <svg xmlns="http://www.w3.org/2000/svg"
                                    class="w-16 h-16 rounded-2xl p-3 border border-cyan-100 text-cyan-600 bg-cyan-50"
                                    fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                        d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                                </svg>
                            </div>

                        </div>
                        <div class="bg-white border-r-4 border-cyan-500 w-full p-4">
                            <div>
                                <p class="text-gray-600 font-bold">{{ $t('creating_article_title') }}</p>
                                <p class="text-gray-600 text-sm">{{ $t('creating_article_subtitle') }}</p>
                            </div>
                        </div>
                    </div>
                </div>




            </div>
            <div v-else class="relative">
                <div :dir="dir" class="flex absolute inset-y-0 right-2 items-center pl-3 pointer-events-none">
                    <svg aria-hidden="true" class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="none"
                        stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                    </svg>

                </div>
                <input :dir="dir" type="search" id="default-search" v-model="keywords" :disabled="isCreating"
                    class="block p-4 pr-10 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-cyan-500 focus:border-cyan-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-cyan-500 dark:focus:border-cyan-500"
                    :placeholder="$t('search_placeholder')" required dire>
              <button :dir="dir" type="submit" @click="createArticle" disabled="true"
                      class="text-white absolute left-2.5 bottom-2.5 bg-cyan-700 hover:bg-cyan-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-cyan-600 dark:hover:bg-cyan-700 dark:focus:ring-cyan-800 disabled:opacity-40">{{
                            $t('submit')
                    }}</button>
            </div>


            
        </section>

        <section :dir="dir" class="container mx-auto p-5 md:p-3 transform duration-900">
            <div class="w-1/2">
  <label for="step" class="font-bold text-gray-600">{{$t('slider_title')}}</label>
  <input type="range" min="1" step="0" max="100" v-model="temperature" class="w-full h-2 bg-cyan-100 appearance-none " />
</div>

        </section>
        









        <section class="container mx-auto p-3 md:py-5 px-0 md:p-10 md:px-0">
            <section
                class="p-5 md:p-0 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-2 xl:grid-cols-3 2xl:grid-cols-4 gap-10">

                <div v-bind:key="harticleIndex" v-for="(harticle, harticleIndex) in completed">

                    <article :id="harticleIndex" v-if="harticleIndex % 3 == 0" :dir="dir"
                        class="min-h-116 max-w-3xl w-full text-gray-100 xl:col-span-2 bg-center bg-cover transform duration-500 hover:-translate-y-1 cursor-pointer flex flex-col justify-between"
                        v-bind:style="{ 'background-image': 'url(' + harticle.image + ')' }">

                        <div class="flex justify-between items-center ml-5 pr-5 mt-23">
                            <div class="w-auto shadow flex flex-col-reverse p-2 text-center font-bold text-white">
                                SPORT6</div>
                            <div
                                class="bg-red-600 text-white bg-opacity-95 shadow px-2 py-1 flex items-center font-bold text-xs rounded">
                                פרסום ראשון</div>
                        </div>
                        <NuxtLink :to="'/harticles/sport6/' + harticle.id" @click="selectArticle(harticle)">
                            <div class="bg-black bg-opacity-65 shadow-md pr-1 pl-1 flex flex-col">
                                <div>

                                    <h3 class="sm:text-sm md:text-lg text-xl font-bold pb-2 pt-2 line-clamp-2">{{
                                            harticle.title
                                    }}</h3>

                                    <p class="sm:text-sm text-sm line-clamp-2">{{ harticle.subTitle }}</p>
                                </div>
                                <div class="flex justify-between items-center pt-1 mt-2 pb-1">
                                    <span class="text-xs">
                                        {{ formatDate(harticle.updatedAt) }}</span>
                                    <span>
                                        <a :href="'http://twitter.com/intent/tweet?text=' + harticle.title + '&amp;url=https://harticle.devozs.com/harticles/sport6/' + harticle.id"
                                            target="_blank" tabindex="10000" data-font-size="16"
                                            data-font-size-type="px">
                                            <img loading="lazy"
                                                src="https://images.one.co.il/images/msites/2022/03/02/f1122ca799164f8406265daf2332cd5a.png"
                                                alt="Harticle" tabindex="-1"></a>
                                    </span>
                                    <span class="text-xs">SPORT6 מערכת</span>

                                </div>
                            </div>
                        </NuxtLink>
                    </article>


                    <article :id="harticleIndex" v-else-if="harticleIndex % 2 == 0" :dir="dir"
                        class="min-h-116 max-w-3xl w-full text-gray-100 xl:col-span-2 bg-center bg-cover transform duration-500 hover:-translate-y-1 cursor-pointer flex flex-col justify-between bg-indigo-800">

                        <h3 class="sm:text-sm md:text-lg text-xl font-bold mr-2 pt-3 pb-3">{{ harticle.title }}</h3>
                        <div class="mb-0">
                            <img class="object-center object-cover w-full p-2" :src="harticle.image" alt="photo">
                            <div class="flex justify-between items-center ml-4 pr-8">
                                <span
                                    class="absolute top-25 left-5 bg-red-600 text-white bg-opacity-95 shadow px-2 py-1 flex items-center font-bold text-xs rounded">חשיפה
                                    ראשונה</span>
                                <span
                                    class="absolute top-25 right-5 text-white bg-opacity-95 shadow px-2 py-1 flex items-center font-bold text-xs rounded">TWO</span>

                            </div>
                        </div>
                        <NuxtLink :to="'/harticles/two/' + harticle.id" @click="selectArticle(harticle)">

                            <div class="shadow-md pr-1 pl-1 flex flex-col">
                                <div>
                                    <p class="sm:text-sm text-sm line-clamp-2">{{ harticle.subTitle }}</p>
                                </div>
                                <div class="flex justify-between items-center pt-1 mt-2 pb-1">
                                    <span class="text-xs">{{ formatDate(harticle.updatedAt)
                                    }}</span>
                                    <span>
                                        <a :href="'http://twitter.com/intent/tweet?text=' + harticle.title + '&amp;url=https://harticle.devozs.com/harticles/two/' + harticle.id"
                                            target="_blank" tabindex="10000" data-font-size="16"
                                            data-font-size-type="px">
                                            <img loading="lazy"
                                                src="https://images.one.co.il/images/msites/2022/03/02/f1122ca799164f8406265daf2332cd5a.png"
                                                alt="Harticle" tabindex="-1"></a>
                                    </span>
                                    <span class="text-xs">TWO מערכת</span>
                                </div>
                            </div>
                        </NuxtLink>
                    </article>


                    <article :id="harticleIndex" v-else :dir="dir"
                        class="min-h-116 max-w-3xl w-full text-gray-100 xl:col-span-2 bg-center bg-cover bg-no-repeat transform duration-500 hover:-translate-y-1 cursor-pointer flex flex-col justify-between "
                        v-bind:style="{ 'background-image': 'url(' + harticle.image + ')' }">
                        <div class="flex justify-between items-center ml-5 pr-5 mt-23">
                            <div class="w-auto shadow flex flex-col-reverse p-2 text-center font-bold text-white">
                                YALLA</div>
                            <div
                                class="bg-red-600 text-white bg-opacity-95 shadow px-2 py-1 flex items-center font-bold text-xs rounded">
                                פרסום בלעדי</div>
                        </div>
                        <NuxtLink :to="'/harticles/' + harticle.id" @click="selectArticle(harticle)">

                            <div class="bg-black shadow-md pr-1 pl-1 flex flex-col bg-opacity-10">
                                <div>
                                    <h3 class="sm:text-sm md:text-lg text-xl font-bold pb-2 pt-2">{{ harticle.title }}
                                    </h3>
                                    <p class="sm:text-sm text-sm line-clamp-2">{{ harticle.subTitle }}</p>
                                </div>
                                <div class="flex justify-between items-center pt-1 mt-2 pb-1">
                                    <span class="text-xs">
                                        {{ formatDate(harticle.updatedAt) }}
                                    </span>
                                    <span>
                                        <a :href="'http://twitter.com/intent/tweet?text=' + harticle.title + '&amp;url=https://harticle.devozs.com/harticles/yalla/' + harticle.id"
                                            target="_blank" tabindex="10000" data-font-size="16"
                                            data-font-size-type="px">
                                            <img loading="lazy"
                                                src="https://images.one.co.il/images/msites/2022/03/02/f1122ca799164f8406265daf2332cd5a.png"
                                                alt="Harticle" tabindex="-1"></a>
                                    </span>
                                    <span class="text-xs">מערכת יאללה! ספורט</span>
                                </div>
                            </div>
                        </NuxtLink>
                    </article>
                </div>
            </section>
        </section>

    </div>
</template>


<script setup>

import { storeToRefs } from 'pinia'
import dayjs from 'dayjs'
import Cookies from 'js-cookie'
import VueSocialSharing from 'vue-social-sharing'

const { locale } = useI18n()
const formatDate = (value) => dayjs(value).format('YYYY-MM-DD HH:mm:ss')

const { fetchAll, create, wait, select, getById } = useHarticleStore()
const { keywords, reporter, isValidKeywords, articles, completed, loading, isCreating, creationSuccessed, selected, temperature } = storeToRefs(useHarticleStore())

const dir = computed(() => (locale.value === 'en' ? 'ltr' : 'rtl'))

onMounted(() => {
    fetchAll()
    wait()
})

onUnmounted(() => {
    // destroyInterval()
})

const createArticle = () => {
    create()
}

const selectArticle = (article) => {
    console.log("click")
    select(article)
}

</script>