<template>
  <div>
  <costs-list :costs="costs"
               :accounts="accounts"
               :categories="categories"/>
  </div>
</template>
<script>
import CostsList from "../components/costs/CostsList.vue";

export default {
  components: {
    CostsList
  },
  data() {
    return {
      costs: frontendData.costs,
      profile: frontendData.profile,
      accounts: frontendData.accounts,
      categories: frontendData.categories
    }
  },
}
</script>
<style>

</style>