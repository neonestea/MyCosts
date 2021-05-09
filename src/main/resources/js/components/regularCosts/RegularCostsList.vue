<template>
  <v-app style="background: #F4F5F5;">
  <div style="background: #F4F5F5;">
    <regular-cost-form :regularCosts="regularCosts"
               :accounts="accounts"
               :categories="categories" />
    <v-divider style="margin: 20px 0;"></v-divider>
    <v-card>
      <v-card-title>
        Regular costs
        <v-spacer></v-spacer>
        <v-text-field
            v-model="search"
            append-icon="search"
            label="Search"
            single-line
            hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
          :headers="headers"
          :items="regularCostsRow"
          :loading="loadTable"
          loading-text="Loading... Please wait"
          :search="search"
          sort-by="date"
          class="elevation-1"
      >
          <template v-slot:item.actions="{ item }">

            <v-icon
                small
                @click="deleteItem(item)"
            >delete</v-icon>
          </template>
      </v-data-table>
    </v-card>
  </div>
  </v-app>
</template>
<script>
import RegularCostForm from 'components/regularCosts/RegularCostForm.vue'
export default {
  components: {
    RegularCostForm
  },
  data: function () {
    return {
      loadTable: true,
      search: '',
      dialog: false,
      menu2: false,
      date: '',
      editedIndex: -1,
      editedItem: {
        name: '',
        nextDate: '',
        amount: '',
        account: '',
        category: '',
        period: '',
        everyMonth: ''
      },
      defaultItem: {
        name: '',
        nextDate: '',
        amount: '',
        account: '',
        category: '',
        period: '',
        everyMonth: ''
      },
      regularCosts: frontendData.regularCosts,
      accounts: frontendData.accounts,
      categories: frontendData.categories,
      regularCostsRow: [],
      headers: [
        { text: 'Name', value: 'name'},
        { text: 'Last Pay Date', value: 'lastDate' },
        { text: 'Next Pay Date', value: 'nextPayDate' },
        { text: 'Period', value: 'period' },
        { text: 'Amount', value: 'amount' },
        { text: 'Account', value: 'account' },
        { text: 'Category', value: 'category' },
        { text: 'Actions', value: 'actions', sortable: false },
      ],
    }
  },
  created () {
    this.initialize();
    this.loadTable= false;
  },
  methods: {
    initialize(){
      let per;
      this.regularCostsRow = this.regularCosts.map(function(item) {
        if(item.everyMonth == false){
          per = item.period + " day(s)";
        }
        else {
          per = 'monthly';
        }
        return {
          name: item.name,
          lastDate: item.lastDate,
          nextPayDate: item.nextDate,
          period: per,
          amount: item.amount,
          id: item.id,
          category: item.category.name,
          account: item.account.name
        };
      });
    },
    deleteItem (item) {
      this.$resource('/regular_costs{/id}').remove({id: item.id}).then(result => {
        if (result.ok) {
          location.reload();
        }
      })
    },
    edit(item){
      console.log("KEK");
    }

  }
}
</script>
<style>

</style>