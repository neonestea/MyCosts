<template>
  <v-app style="background: #F4F5F5;">
    <regular-costs-list :regularCosts="regularCosts"
                :accounts="accounts"
                :categories="categories"/>

  </v-app>
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
  created() {
    $("#RegularCostsBtn").css({ "color": "white", "border-bottom": "2px solid white"})
  }
}
</script>
<style>

</style>