<template>
  <v-app style="background: #F4F5F5;">
  <costs-list :costs="costs"
               :accounts="accounts"
               :categories="categories"/>

  </v-app>
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
  created() {
    $("#CostsBtn").css({ "color": "white", "border-bottom": "2px solid white"})
  }
}
</script>
<style>

</style>