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
        Here you can manage your accounts. To create an account fill in the form and click "Save" button.
        To delete a cost click "Bin" button on the card. You can edit accounts by clicking "Edit" buttons
        on the cards or add money using "Piggy bank" button.
      </v-alert>
    </div>
    <accounts-list :accounts="accounts" :currencies="currencies"/>

  </v-app>
</template>
<script>
import AccountsList from "../components/accounts/AccountsList.vue";

export default {
  components: {
    AccountsList
  },
  data() {
    return {
      accounts: frontendData.accounts,
      currencies: frontendData.currencies,
      profile: frontendData.profile,
      alert: true,
    }
  },
  created() {
    $("#AccountsBtn").css({ "color": "white", "border-bottom": "2px solid white"})
  }
}
</script>
<style>

</style>