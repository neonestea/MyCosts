<template>
  <div class="editForm"
       :id="`name_form`+amount.id">
    <v-card-title>New account name:</v-card-title>
    <input :id="`name`+account.id"

           type="text"
           placeholder="Account name"
           v-model="name"
           maxlength="25"
           style="padding: 5px;
    border: dotted 1px;"
           onkeypress="return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)"/>
    <button :id="`editBtn`+account.id"

            type="button"
            value="Edit"
            @click="editName"
            :disabled="isDisableName()"><v-icon>edit</v-icon></button>
    <v-card-title>New amount:</v-card-title>
    <input :id="`amount`+account.id"
           class="inp"
           style="padding: 5px;
    border: dotted 1px;"
           type="number"
           step="0.01"
           placeholder="Amount"
           v-model="amount"
           :oninput="checkAmount()"/>
    <button :id="`editBtn`+account.id"
           type="button"
           value="Edit"
           @click="editAmount"
            :disabled="isDisableAmount()"><v-icon>edit</v-icon></button>
    <div style="display: flex; justify-content: center; margin: 10px;">
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
      amount: this.account.amount.toString(),
      currency: this.account.currency,
      id: this.account.id
    }
  },
  methods: {
    checkAmount() {
      if(parseInt(this.amount) > 0){
        let res = this.amount.match(/^\d+(\.\d\d)?$/);
        const form = document.getElementById('name_form' + this.account.id);
        if(form != null) {
          const el = document.getElementById('amount' + this.account.id);
          if (this.amount.length != 0) {
            if (res && (this.amount.indexOf(".") != '-1')) {
              el.style.background = "#E2ECDE";
            } else {
              el.style.background = "#F9D4D1";
            }
          }
        }
      }
      else{
        let new_amount = this.amount.slice(1);
        let res = new_amount.match(/^\d+(\.\d\d)?$/);

        const form = document.getElementById('name_form' + this.account.id);
        if(form != null) {
          const el = document.getElementById('amount' + this.account.id);
          if (this.amount.length != 0) {
            if (res && (this.amount.indexOf(".") != '-1')) {
              el.style.background = "#E2ECDE";
            } else {
              el.style.background = "#F9D4D1";
            }
          }
        }
      }
    },
    isDisableName() {

      return this.name.length == 0 || this.name == this.account.name;
    },
    isDisableAmount(){
      let res = false;
      if(parseInt(this.amount) > 0){
        res = this.amount.match(/^\d+(\.\d\d)?$/);}
      else {
        let new_amount = this.amount.slice(1);
        res = new_amount.match(/^\d+(\.\d\d)?$/);
      }
      return this.amount.length == 0 || this.amount == this.account.amount || !res || (this.amount.indexOf(".") == '-1');
    },
    cancel() {
      const form = document.getElementById('name_form' + this.account.id);
      const editBtn = document.getElementById('editBtn' + this.account.id);
      const cancelBtn = document.getElementById('cancelBtn' + this.account.id);
      form.style.display = "none";
      cancelBtn.disabled = false;
      editBtn.disabled = false;
      this.amount = this.account.amount;
      document.querySelectorAll('.button').forEach(elem => {
        elem.disabled = false;
      });
      const add = document.getElementById('addInput');
      add.disabled = false;
    },
    editName: function () {
      var account = {name: this.name, amount: this.account.amount, currency: this.currency};
      this.$resource('/account{/id}').update({id: this.account.id}, account)
          .then(result => {
            if (result.status == '200') {
              result.json()
                  .then(data => {
                    this.name = this.account.name;
                    this.accounts.splice(this.accounts.indexOf(this.account), 1);
                    const form = document.getElementById('name_form' + this.account.id);
                    form.style.display = "none";
                    this.accounts.push(data);
                    document.querySelectorAll('.button').forEach(elem => {
                      elem.disabled = false;
                    });
                    const add = document.getElementById('addInput');
                    add.disabled = false;
                  })
            } else if (result.status == "201") {
              const form = document.getElementById('name_form' + this.account.id);
              form.style.display = "none";
              $("#rec_line").show('slow');
              setTimeout(function () {
                $("#rec_line").hide('slow');
              }, 2000);
              this.name = this.account.name;
              document.querySelectorAll('.button').forEach(elem => {
                elem.disabled = false;
              });
              const add = document.getElementById('addInput');
              add.disabled = false;
            } else {
              const form = document.getElementById('name_form' + this.account.id);
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
            }
          })
    },
    editAmount: function () {
      var account = {name: this.account.name, amount: this.amount, currency: this.currency};
      this.$resource('/account{/id}').update({id: this.account.id}, account)
          .then(result => {
            if (result.status == '200') {
              result.json()
                  .then(data => {
                    this.name = this.account.name;
                    this.accounts.splice(this.accounts.indexOf(this.account), 1);
                    const form = document.getElementById('name_form' + this.account.id);
                    form.style.display = "none";
                    this.accounts.push(data);
                    document.querySelectorAll('.button').forEach(elem => {
                      elem.disabled = false;
                    });
                    const add = document.getElementById('addInput');
                    add.disabled = false;
                  })
            } else if (result.status == "201") {
              const form = document.getElementById('name_form' + this.account.id);
              form.style.display = "none";
              $("#rec_line").show('slow');
              setTimeout(function () {
                $("#rec_line").hide('slow');
              }, 2000);
              this.name = this.account.name;
              document.querySelectorAll('.button').forEach(elem => {
                elem.disabled = false;
              });
              const add = document.getElementById('addInput');
              add.disabled = false;
            } else {
              const form = document.getElementById('name_form' + this.account.id);
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