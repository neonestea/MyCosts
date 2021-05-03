<template>
  <v-app style="background: #F4F5F5;">
    <div>
      <v-alert
          v-model="alert"
          border="left"
          close-text="Close Alert"
          color="blue-grey accent-4"
          dark
          dismissible
      >
        Here you can manage your regular costs. They are needed if you have scheduled costs and do not want to create them every time.
        To create a regular cost fill in the form and click "Save" button.
        To delete a regular cost click "Bin" button. You can also search costs by typing in the "Search" line
        or sort costs by clicking columns' names.
      </v-alert>
    </div>
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
      categories: frontendData.categories,
      alert: true,
    }
  },
  created() {
    $("#RegularCostsBtn").css({ "color": "white", "border-bottom": "2px solid white"})
  }
}
</script>
<style>

</style>