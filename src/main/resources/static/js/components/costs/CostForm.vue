<template>
  <div style="paddig: 10px; margin-bottom: 10px; margin-top:30px; margin-left: 10px; margin-right:10px;">
    <v-row>
      <v-menu
          v-model="menu2"
          :close-on-content-click="false"
          :nudge-right="40"
          transition="scale-transition"
          offset-y
          min-width="auto"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
              v-model="date"
              :max="max"
              :min="min"
              prepend-icon="event"
              readonly
              v-bind="attrs"
              v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
            v-model="date"
            @input="menu2 = false"
        ></v-date-picker>
      </v-menu>

    <v-text-field label="Amount"
                  :id="`amount`+account.id"
                  :rules="rules"
                  hide-details="auto"
                  placeholder="Amount"
                  step="0.01"
                  min="0"
                  type="number"
                  v-model="amount">

    </v-text-field>

        <v-select
            :items="accounts"
            label="Account"
            v-model="account"
        ></v-select>


        <v-select
            v-model="category"
            :items="categories"
            label="Category"
        ></v-select>

      <v-btn type="button" value="Save" @click="save" style="height: 22px; margin-top: 20px;"
                     :disabled="isDisable(amount, account, category)">Save</v-btn>
    </v-row>
<!--    <select type="text" v-model="account" style="background: #FFF;
    padding: 5px;
    border-radius: 5px;">
      <option value="" disabled selected>Account</option>
      <option v-for="acc in accounts"
              :key="acc.id"
              :value="acc">{{ acc.name }}
      </option>
    </select>-->
<!--    <select type="text" v-model="category" style="background: #FFF;
    padding: 5px;
    border-radius: 5px;">
      <option value="" disabled selected>Category</option>
      <option v-for="cat in categories"
              :key="cat.id"
              :value="cat">{{ cat.name }}
      </option>
    </select>-->

  </div>
</template>
<script>

export default {
  props: ['costs', 'accounts', 'categories'],
  data: function () {
    const today = new Date();
    const year = today.getFullYear();
    let month = today.getMonth() + 1;
    if (month < 10) {
      month = "0" + month;
    }
    let day = today.getDate();
    if (day < 10) {
      day = "0" + day;
    }
    let minMonth = today.getMonth();
    if (minMonth < 10) {
      minMonth = "0" + minMonth;
    }
    const minDate = year + '-' + minMonth + '-01';
    const maxDate = year + '-' + month + '-' + day;

    return {
      id: '',
      account: '',
      category: '',
      amount: '',
      date: maxDate,
      max: maxDate,
      min: minDate,
      menu2: false,
      rules: [
        value => !!value || 'Required.',
        value => (value && value.match(/^\d+(\.\d\d)?$/) && value.indexOf(".") != '-1') || 'Invalid input.',
      ],
      accItems: [],
    }
  },
  created () {
    this.initialize()
  },
  methods: {

    initialize(){
      for (let i = 0; i < props.accounts.length; i++){
        console.log(this.accounts.get(i).name)
      }
    },
    isDisable(amount, account, category) {
        let res = this.amount.match(/^\d+(\.\d\d)?$/);
        return amount.length == 0 || account.length == 0 || category.length == 0 || !res || (this.amount.indexOf(".") == '-1');
    },
    save() {
      var cost = {date: this.date, amount: this.amount, account: this.account, category: this.category};
      this.$resource('/costs{/id}').save({}, cost).then(result =>
          result.json().then(data => {
            this.costs.push(data);
            this.amount = ''
            const el = document.getElementById('amount' + this.account.id);
            el.style.background = "white";
            location.reload();
          })
      )
    }
  }
}
</script>
<style>

</style>