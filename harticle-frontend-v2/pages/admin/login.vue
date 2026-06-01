<script setup lang="ts">
definePageMeta({ layout: false })

const route = useRoute()
const router = useRouter()
const { login } = useAdminAuth()

const passphrase = ref('')
const error = ref('')

function submit() {
  if (login(passphrase.value)) {
    const redirect = (route.query.redirect as string) || '/admin'
    router.push(redirect)
  } else {
    error.value = 'Incorrect passphrase'
  }
}
</script>

<template>
  <div dir="ltr" class="flex min-h-screen items-center justify-center bg-gray-50 text-gray-900">
    <form
      class="w-full max-w-sm rounded-2xl border border-gray-200 bg-white p-6 shadow-md"
      @submit.prevent="submit"
    >
      <h1 class="text-lg font-bold text-cyan-800">Scraper Admin</h1>
      <p class="mt-1 text-sm text-gray-500">Enter the admin passphrase to continue.</p>

      <input
        v-model="passphrase"
        type="password"
        placeholder="Passphrase"
        class="mt-4 block w-full rounded-lg border border-gray-300 bg-gray-50 p-3 text-sm focus:border-cyan-500 focus:ring-cyan-500"
      >
      <p v-if="error" class="mt-2 text-sm text-red-600">{{ error }}</p>

      <button
        type="submit"
        class="mt-4 w-full rounded-lg bg-cyan-700 px-4 py-2 text-sm font-medium text-white hover:bg-cyan-800"
      >
        Enter
      </button>
    </form>
  </div>
</template>
