<template>
  <div class="editForm"
       :id="`amount_form`+amount.id">
    <v-text-field label="Add amount"
                  :id="`add_amount`+account.id"
                  :rules="rules"
                  hide-details="auto"
                  placeholder="0.00"
                  step="0.01"
                  min="0"
                  type="number"
                  v-model="amount"></v-text-field>
    <div style="display: flex; justify-content: space-evenly; padding: 10px;">
    <button :id="`editBtn`+account.id"
           type="button"
           value="Edit"
           @click="addAmount"
            :disabled="isDisable()"><v-icon>edit</v-icon></button>
      <button :id="`cancelBtn`+account.id"
              type="button"
              value="Cancel"
              @click="cancel"><v-icon>cancel</v-icon></button>
    </div>
  </div>
</template>
<script>
export default {
  props: ['account', 'accounts'],
  data: function () {
    return {
      name: this.account.name,
      amount: '',
      currency: this.account.currency,
      id: this.account.id,
      rules: [
        value => (((value.match(/^\d+(\.\d\d)?$/) || value.match(/^\d+(\.\d)?$/))&& value.indexOf(".") != '-1') || value.indexOf(".") == '-1' ) || 'Invalid input.',
      ],
    }
  },
  methods: {
    isDisable() {
      let res = ((this.amount.match(/^\d+(\.\d\d)?$/) || this.amount.match(/^\d+(\.\d)?$/)) && this.amount.indexOf(".") != '-1') || this.amount.indexOf(".") == '-1';

          return this.amount.length == 0 || !res;
    },
    cancel() {
      const form = document.getElementById('amount_form' + this.account.id);
      const editBtn = document.getElementById('editBtn' + this.account.id);
      const cancelBtn = document.getElementById('cancelBtn' + this.account.id);
      form.style.display = "none";
      cancelBtn.disabled = false;
      editBtn.disabled = false;
      document.querySelectorAll('.button').forEach(elem => {
        elem.disabled = false;
      });
      const add = document.getElementById('addInput');
      add.disabled = false;
      this.amount = ''
    },
    addAmount() {
      let newAmount = parseFloat(this.account.amount) + parseFloat(this.amount);
      var account = {name: this.name, amount: newAmount, currency: this.currency};
      this.$resource('/account{/id}').update({id: this.account.id}, account)
          .then(result => {
            if (result.status == '200') {
              result.json()
                  .then(data => {
                    this.name = this.account.name;
                    this.accounts.splice(this.accounts.indexOf(this.account), 1);
                    const form = document.getElementById('amount_form' + this.account.id);
                    form.style.display = "none";
                    this.accounts.push(data);
                    document.querySelectorAll('.button').forEach(elem => {
                      elem.disabled = false;
                    });
                    const add = document.getElementById('addInput');
                    add.disabled = false;
                    this.amount = ''
                  })
            } else if (result.status == "201") {
              const form = document.getElementById('amount_form' + this.account.id);
              form.style.display = "none";
              $("#rec_line").show('slow');
              setTimeout(function () {
                $("#rec_line").hide('slow');
              }, 2000);
              //this.amount = this.account.amount;
              this.name = this.account.name;
              document.querySelectorAll('.button').forEach(elem => {
                elem.disabled = false;
              });
              const add = document.getElementById('addInput');
              add.disabled = false;
              this.amount = ''
            } else {
              const form = document.getElementById('amount_form' + this.account.id);
              form.style.display = "none";
              $("#error_line").show('slow');
              setTimeout(function () {
                $("#error_line").hide('slow');
              }, 2000);
              //this.amount = this.account.amount;
              this.name = this.account.name;
              document.querySelectorAll('.button').forEach(elem => {
                elem.disabled = false;
              });
              const add = document.getElementById('addInput');
              add.disabled = false;
              this.amount = ''
            }
          })
    },
  }
}
</script>
<style>
.editForm {
  display: flex;
  justify-content: center;
  padding: 25px;
  align-items: center;
  z-index: 9999;
  flex-direction: column;
  border-radius: 3px;
  background: #FFFFFF;
  -webkit-box-shadow: -20px 21px 8px 0px rgba(66, 73, 78, 0.2);
  -moz-box-shadow: -20px 21px 8px 0px rgba(66, 73, 78, 0.2);
  box-shadow: -20px 21px 8px 0px rgba(66, 73, 78, 0.2);
}

.inp:focus {
  border: none;
}
</style>