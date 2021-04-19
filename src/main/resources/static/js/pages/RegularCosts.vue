<template>
  <div>
    <regular-costs-list :regularCosts="regularCosts"
                :accounts="accounts"
                :categories="categories"/>
  </div>
</template>
<script>
import RegularCostsList from "../components/regularCosts/RegularCostsList.vue";

export default {
  components: {
    RegularCostsList
  },
  data() {
    return {
      regularCosts: frontendData.regularCosts,
      profile: frontendData.profile,
      accounts: frontendData.accounts,
      categories: frontendData.categories
    }
  },
}
</script>
<style>

</style>