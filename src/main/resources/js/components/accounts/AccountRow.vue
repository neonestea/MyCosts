<template>
  <div>
    <account-edit-name-form style="display: none; z-index: 9999; position: absolute;"
                            :id="`name_form`+account.id"
                            :accounts="accounts"
                            :account="account"/>
    <account-edit-amount-form style="display: none; z-index: 9999; position: absolute;"
                              :id="`amount_form`+account.id"
                              :accounts="accounts"
                              :account="account"/>
    <v-card style="margin: 20px; padding: 10px; display: flex; flex-direction: column;">
      <v-card-title style="justify-content: center;">{{ account.name }}</v-card-title>
      <v-card-subtitle>{{ account.currency }}</v-card-subtitle>
      <v-card-subtitle>{{ account.amount }}</v-card-subtitle>
      <v-btn class="button"
             :id="`editName`+account.id"
             type="button"
             style="margin: 5px;"
             value="Edit"
           @click="askEditName"><v-icon>edit</v-icon>Edit</v-btn>
      <v-btn class="button"
             :id="`editAmount`+account.id"
             type="button"
             value="Check"
             style="margin: 5px;"
           @click="askEditAmount"><v-icon>savings</v-icon>Add money</v-btn>
      <v-btn class="button"
             :id="`delete`+account.id"
             type="button" value="X"
             style="margin: 5px;"
           @click="del"><v-icon>delete</v-icon>Delete</v-btn>
    </v-card>
  </div>
</template>
<script>
import AccountEditAmountForm from 'components/accounts/AccountEditAmountForm.vue'
import AccountEditNameForm from 'components/accounts/AccountEditNameForm.vue'


export default {
  components: {
    AccountEditAmountForm,
    AccountEditNameForm
  },
  props: ['account', 'accounts'],
  methods: {
    askEditName() {
      const form = document.getElementById('name_form' + this.account.id);
      form.style.display = "block";
      document.querySelectorAll('.button').forEach(elem => {
        elem.disabled = true;
      });
      const add = document.getElementById('addInput');
      add.disabled = true;
    },
    askEditAmount() {
      const form = document.getElementById('amount_form' + this.account.id);
      form.style.display = "block";
      document.querySelectorAll('.button').forEach(elem => {
        elem.disabled = true;
      });
      const add = document.getElementById('addInput');
      add.disabled = true;
    },
    del() {
      this.$resource('/account{/id}').remove({id: this.account.id}).then(result => {
        if (result.ok) {
          this.accounts.splice(this.accounts.indexOf(this.account), 1)
        }
      })
    }
  }
}
</script>
<style>
button:disabled {
  cursor: default;
  opacity: 0.3;
}

button {
  opacity: 0.8;
}
</style>