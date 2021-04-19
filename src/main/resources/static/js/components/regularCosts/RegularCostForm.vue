<template>
  <div style="paddig: 10px; margin-bottom: 10px;">
    First day:
    <input type="date"
           v-model="date"
           style="background: #FFF;
    padding: 5px;
    border-radius: 5px;"
           :max="max"
           :min="min"/>
    <input type="number"
           step="0.01" placeholder="Amount"
           style="background: #FFF;
    padding: 5px;
    border-radius: 5px;"
           v-model="amount"
           :oninput="checkAmount()"/>
    <select type="text" v-model="account" style="background: #FFF;
    padding: 5px;
    border-radius: 5px;">
      <option value="" disabled selected>Account</option>
      <option v-for="acc in accounts"
              :key="acc.id"
              :value="acc">{{ acc.name }}
      </option>
    </select>
    <select type="text" v-model="category" style="background: #FFF;
    padding: 5px;
    border-radius: 5px;">
      <option value="" disabled selected>Category</option>
      <option v-for="cat in categories"
              :key="cat.id"
              :value="cat">{{ cat.name }}
      </option>
    </select>
    <input id="daysInput" style="background: #FFF;
    padding: 5px;
    border-radius: 5px;"
           type="number" step="1" min="1"
           placeholder="Interval in days" v-model="dayInterval"/>
    <v-btn type="button" value="Save" @click="save" style="height: 22px;"
           :disabled="isDisable(amount, account, category,dayInterval)">Save</v-btn>
  </div>
</template>
<script>
export default {
  props: ['regularCosts', 'accounts', 'categories'],
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
    let currentTime = new Date();
    var days = new Date(currentTime.getFullYear(), month, 0).getDate();
    const maxDate = year + '-' + month + '-'+ days;
    const minDate = year + '-' + month + '-' + day;
    return {
      id: '',
      account: '',
      category: '',
      amount: '',
      date: minDate,
      max: maxDate,
      min: minDate,
      dayInterval: ''

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
    isDisable(amount, account, category) {
        return amount.length == 0 || account.length == 0 || category.length == 0 || this.dayInterval.length == 0;
    },
    save() {

      var regularCost = {lastDate: this.lastDate, /*nextDate: this.nextDate,*/ amount: this.amount, account: this.account, category: this.category};
      this.$resource('/regular_costs{/id}').save({}, regularCost).then(result =>
          result.json().then(data => {
            this.regularCosts.push(data);
            this.amount = ''
            location.reload();
          })
      )
    }

  }
}
</script>
<style>

</style>