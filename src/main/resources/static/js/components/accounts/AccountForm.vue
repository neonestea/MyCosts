<template>
  <div style="margin: 20px 10px;">
    <v-row style="padding: 15px;">
      <v-text-field id="addInput"
                    label="Account name"
                    hide-details="auto"
                    placeholder="Account name"
                    type="text"
                    maxlength="25"
                    v-model="name"></v-text-field>
      <v-text-field label="Amount"
                    id="addInput"
                    :rules="rules"
                    hide-details="auto"
                    placeholder="0.00"
                    step="0.01"
                    min="0"
                    type="number"
                    v-model="amount">

      </v-text-field>
      <v-select
          v-model="currency"
          :items="currencies"
          label="Currency"
          name="currency"
          item-text="name"
      ></v-select>
      <v-btn type="button"
             style="height: 22px; margin-top: 20px;"
             value="Save"
             @click="save"
             :disabled="isDisable(name, currency)">Save</v-btn>
    </v-row>
    <p id="error_line"
       style="display: none; padding: 15px;">Account already exists!</p>
    <p id="rec_line"
       style="display: none; padding: 15px;">You have deleted this account. Try to recover it from the line above.</p>
    <div style="display: none; padding: 10px;"
         id="recover_block">
      <p>You already had this account. Do you want to recover it?</p>
      <button type="button"
             value="Yes"
             @click="recover"
              id="yes"><v-icon>thumb_up_alt</v-icon></button>
      <button type="button"
             value="No"
             @click="notRecover"
              id="no"><v-icon>thumb_down_alt</v-icon></button>
    </div>
  </div>

</template>
<script>
export default {
  props: ['accounts', 'currencies'],
  data: function () {
    return {
      name: '',
      amount: '',
      currency: '',
      id: '',
      currenciesValues:[],
      rules: [
        value => (((value.match(/^\d+(\.\d\d)?$/) || value.match(/^\d+(\.\d)?$/))&& value.indexOf(".") != '-1') || value.indexOf(".") == '-1' ) || 'Invalid input.',
      ],
    }
  },
  created () {
    this.initialize()
  },

  methods: {
    initialize(){
      this.currenciesValues = this.currencies.map(function(item) {
        return {
          currency: item.name
        };
      });
    },
    isDisable(name, currency) {
      let res = ((this.amount.match(/^\d+(\.\d\d)?$/) || this.amount.match(/^\d+(\.\d)?$/)) && this.amount.indexOf(".") != '-1') || this.amount.indexOf(".") == '-1';

      return name.length == 0 || currency.length == 0 || !res || this.amount.length == 0;
    },
    recover() {
      this.$resource('/account{/id}').update({id: this.id}, null)
          .then(result => {
            result.json()
                .then(data => {
                  this.accounts.push(data);
                  this.id = '';
                  const recoveryLine = document.getElementById('recover_block');
                  recoveryLine.style.display = "none";
                  document.querySelectorAll('.button').forEach(elem => {
                    elem.disabled = false;
                  });
                  const add = document.getElementById('addInput');
                  add.disabled = false;
                  this.amount = ''
                  this.name = ''
                  this.currency = ''
                })

          })
    },
    notRecover() {
      this.id = '';
      const recoveryLine = document.getElementById('recover_block');
      recoveryLine.style.display = "none";
      document.querySelectorAll('.button').forEach(elem => {
        elem.disabled = false;
      });
      const add = document.getElementById('addInput');
      add.disabled = false;
      this.amount = ''
      this.name = ''
      this.currency = ''

    },
    save() {

      var account = {name: this.name, amount: this.amount, currency: this.currency};
      this.$resource('/account{/id}').save({}, account).then(result => {
        if (result.status == '200') {
          result.json()
              .then(data => {
                this.accounts.push(data);
                this.amount = ''
                this.name = ''
                this.currency = ''
              })
        } else if (result.status == "201") {
          result.json()
              .then(data => {
                this.id = data.id;
                document.querySelectorAll('.button').forEach(elem => {
                  elem.disabled = true;
                });
                const add = document.getElementById('addInput');
                add.disabled = true;
                const recLine = document.getElementById('recover_block');
                recLine.style.display = "block";
              })
        } else {

          $("#error_line").show('slow');
          setTimeout(function () {
            $("#error_line").hide('slow');
          }, 2000);
          this.amount = ''
          this.name = ''
          this.currency = ''
        }

      })
    }
  }
}
</script>
<style>

</style>