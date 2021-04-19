<template>
  <div style="paddig: 10px; margin-bottom: 10px;">
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
    <input type="radio" value="true" v-model="isRegular">
    <label>regular</label>
    <input type="radio" value="false" v-model="isRegular">
    <label>not regular </label>
    <input id="daysInput" style="background: #FFF;
    padding: 5px;
    border-radius: 5px;"
           :disabled="disableInput(isRegular)"
           type="number" step="1" min="1"
           placeholder="Interval in days" v-model="dayInterval"/>
    <v-btn type="button" value="Save" @click="save" style="height: 22px;"
            :disabled="isDisable(amount, account, category,dayInterval)">Save</v-btn>
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
      isRegular: "false",
      dayInterval: ''

    }
  },
  methods: {
    disableInput(isRegular) {
      if (isRegular == "true") {
        return false;
      } else {
        this.dayInterval = '';
        return true;
      }
    },
    checkAmount() {
      if (this.amount.indexOf(".") != '-1') {
        this.amount = this.amount.substring(0, this.amount.indexOf(".") + 3);
      } else {
        this.amount = this.amount + ".00";
      }
    },
    isDisable(amount, account, category) {
      if (this.isRegular == "true") {
        return amount.length == 0 || account.length == 0 || category.length == 0 || this.dayInterval.length == 0;
      } else {
        return amount.length == 0 || account.length == 0 || category.length == 0;
      }
    },
    save() {

      var cost = {date: this.date, amount: this.amount, account: this.account, category: this.category};
      this.$resource('/costs{/id}').save({}, cost).then(result =>
          result.json().then(data => {
            this.costs.push(data);
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