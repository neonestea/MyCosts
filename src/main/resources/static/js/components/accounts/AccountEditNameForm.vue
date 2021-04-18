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
           @click="edit"
            :disabled="isDisable(name)"><v-icon>edit</v-icon></button>
    <button :id="`cancelBtn`+account.id"
           type="button"
           value="Cancel"
           @click="cancel"><v-icon>cancel</v-icon></button>
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
    isDisable(name, amount) {
      return this.name.length == 0 || this.name == this.account.name;
    },
    cancel() {
      const form = document.getElementById('name_form' + this.account.id);
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
    },
    edit: function () {
      var account = {name: this.name, amount: this.amount, currency: this.currency};
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
</style>