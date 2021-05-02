<template>
  <v-app style="background: #F4F5F5;">
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
      profile: frontendData.profile
    }
  },
  created() {
    $("#AccountsBtn").css({ "color": "white", "border-bottom": "2px solid white"})
  }
}
</script>
<style>

</style>