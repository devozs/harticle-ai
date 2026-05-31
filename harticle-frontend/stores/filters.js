import { defineStore } from 'pinia'

export const useFiltersStore = defineStore({
  id: 'filter-store',
  state: () => {
    return {
      filters: ['youtube', 'twitch'],
    }
  },
  actions: {},
  getters: {
    filtersList: state => state.filters,
  },
})
