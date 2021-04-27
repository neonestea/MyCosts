<template>
  <div >
    <accounts-list :accounts="accounts" :currencies="currencies"/>
  </div>
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
  }
}
</script>
<style>

</style>