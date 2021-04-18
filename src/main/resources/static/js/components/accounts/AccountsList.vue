<template>
  <div>
    <account-form :accounts="accounts"
                  :currencies="currencies"/>
    <v-divider></v-divider>
    <div style="display: flex;
    flex-wrap: wrap;
    justify-content:center;
    align-items:center;">
      <account-row v-for="account in accounts"
                   :key="account.id"
                   :account="account"
                   :accounts="accounts"/>
    </div>
  </div>
</template>
<script>
import AccountRow from 'components/accounts/AccountRow.vue'
import AccountForm from 'components/accounts/AccountForm.vue'

export default {
  components: {
    AccountRow,
    AccountForm
  },
  props: ['accounts', 'currencies'],
  data() {
    return {
      account: null
    }
  },
  methods: {
    editMethod(account) {
      this.account = account;
    }
  }
}
</script>
<style>

</style>