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
        Here you can manage your costs. To create a cost fill in the form and click "Save" button.
        To delete a cost click "Bin" button. You can also search costs by typing in the "Search" line
        or sort costs by clicking columns' names.
      </v-alert>
    </div>
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
      categories: frontendData.categories,
      alert: true,
    }
  },
  created() {
    $("#CostsBtn").css({ "color": "white", "border-bottom": "2px solid white"})
  }
}
</script>
<style>

</style>