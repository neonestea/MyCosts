<template>
  <div style="padding: 10px; margin-bottom: 10px;">
    <v-menu
        v-model="menu"
        :close-on-content-click="false"
        :nudge-width="200"
        offset-x
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            color="indigo"
            dark
            v-bind="attrs"
            v-on="on"
        >
          Add new
        </v-btn>
      </template>
      <v-card style="padding: 15px; background: rgba(165, 168, 185); width: 300px;">
        <v-card-title>Add new regular cost</v-card-title>
        <v-text-field label="Cost name"
                      hide-details="auto"
                      placeholder="Cost name"
                      type="text"
                      maxlength="25"
                      v-model="name"></v-text-field>
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
                v-model="firstDay"
                label="First day"
                readonly
                v-bind="attrs"
                v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
              v-model="firstDay"
              :min="min"
              @input="menu2 = false"
          ></v-date-picker>
        </v-menu>
        <v-text-field label="Amount"
                      :id="`amount`+account.id"
                      hide-details="auto"
                      placeholder="Amount"
                      step="0.01"
                      min="0"
                      type="number"
                      :rules="rules"
                      v-model="amount">
        </v-text-field>
        <v-select
            :items="accounts"
            label="Account"
            v-model="account"
            name="account"
            item-text="name"
        ></v-select>
        <v-select
            v-model="category"
            :items="categories"
            label="Category"
            name="category"
            item-text="name"
        ></v-select>
        <v-checkbox
            style="padding-right: 5px;"
            v-model="everyMonthPicked"
            label="Every month"
        ></v-checkbox>
        <v-text-field label="Interval"

                      id="daysInput"
                      hide-details="auto"
                      placeholder="1"
                      step="1"
                      min="1"
                      type="number"
                      :disabled="isDisableInterval()"
                      v-model="dayInterval">
        </v-text-field>
        <v-btn type="button" value="Save" @click="save" style="height: 22px; margin-top: 20px;"
               outlined
               rounded
               text
               :disabled="isDisable()">Save</v-btn>
      </v-card>
    </v-menu>

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
    const minDate = year + '-' + month + '-' + day;
    return {
      id: '',
      name: '',
      account: '',
      menu: false,
      category: '',
      amount: '',
      firstDay: minDate,
      min: minDate,
      dayInterval: '',
      everyMonthPicked: false,
      menu2: false,
      rules: [
        value => (((value.match(/^\d+(\.\d\d)?$/) || value.match(/^\d+(\.\d)?$/))&& value.indexOf(".") != '-1') || value.indexOf(".") == '-1' ) || 'Invalid input.',
      ],
    }
  },
  methods: {
    isDisable() {
      if(this.everyMonthPicked == false){
        return this.name.length == 0 || this.amount.length == 0 || this.account.length == 0 || this.category.length == 0 || this.dayInterval.length == 0;
      }
      else {
        return this.name.length == 0 || this.amount.length == 0 || this.account.length == 0 || this.category.length == 0;
      }
    },
    isDisableInterval(){
      return this.everyMonthPicked;
    },
    save() {
      var realAcc;
      for (let i = 0; i < this.accounts.length; i++) {
        if (this.accounts[i].name === this.account) {
          realAcc = this.accounts[i];
        }
      }

      var realCat;
      for (let i = 0; i < this.categories.length; i++) {
        if (this.categories[i].name === this.category) {
          realCat = this.categories[i];
        }
      }

      var regularCost = {name: this.name, nextDate: this.firstDay, amount: this.amount, account: realAcc, category: realCat,
      everyMonth: this.everyMonthPicked, period: this.dayInterval};
      console.log(regularCost)
      this.$resource('/regular_costs{/id}').save({}, regularCost).then(result =>
          result.json().then(data => {
            this.regularCosts.push(data);
            this.amount = ''
            this.account = ''
            this.category = ''
            location.reload();
          })
      )
    }

  }
}
</script>
<style>

</style>