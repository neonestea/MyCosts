<template>
  <div style="margin: 20px 10px;">
    <input id="addInput"
           style="background: #FFF;
    padding: 5px;
    border-radius: 5px;"
           type="text"
           placeholder="Account name"
           v-model="name"
           maxlength="25"
           onkeypress="return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)"/>
    <input id="addInput"
           style="background: #FFF;
    padding: 5px;
    border-radius: 5px;"
           type="number"
           step="0.01"
           placeholder="Amount"
           v-model="amount"
           :oninput="checkAmount()"/>
    <select type="text"
            style="background: #FFF;
    padding: 5px;
    border-radius: 5px;"
            v-model="currency">
      <option value="" disabled selected>Currency</option>
      <option v-for="curr in currencies"
              :key="curr"
              :value="curr">{{ curr }}</option>
    </select>
    <v-btn type="button"
           style="height: 22px;"
           value="Save"
           @click="save"
           :disabled="isDisable(name, currency)">Save</v-btn>
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
      id: ''
    }
  },
  methods: {
    checkAmount() {
      if (this.amount.indexOf(".") != '-1') {
        this.amount = this.amount.substring(0, this.amount.indexOf(".") + 3);
      } else {
        this.amount = this.amount + ".00";
      }
    },
    isDisable(name, currency) {
      return name.length == 0 || currency.length == 0;
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