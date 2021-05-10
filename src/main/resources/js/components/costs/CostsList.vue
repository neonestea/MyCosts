<template>
  <v-app>
<div style="background: #F4F5F5;">
  <cost-form :costs="costs"
              :accounts="accounts"
              :categories="categories" />
  <v-divider style="margin: 20px 0;"></v-divider>
  <data-app>
  <v-card>
  <v-card-title>
    MyCosts
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
      :items="costsRow"
      :search="search"
      sort-by="date"
      multi-sort
      class="elevation-1"
      :loading="loadTable"
      loading-text="Loading... Please wait"
  >
    <template v-slot:item.actions="{ item }">
      <v-icon
          small
          @click="deleteItem(item)"
      >delete</v-icon>
    </template>
  </v-data-table>
  </v-card>
  </data-app>
  </div>
  </v-app>
</template>
<script>
import CostForm from 'components/costs/CostForm.vue'
export default {
  components: {
    CostForm
  },
  data: function () {
    return {
      loadTable: true,
      search: '',
      costs: frontendData.costs,
      accounts: frontendData.accounts,
      categories: frontendData.categories,
      costsRow: [],
      headers: [
        { text: 'Date', value: 'date' },
        { text: 'Amount', value: 'amount' },
        { text: 'Currency', value: 'currency' },
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
      this.costsRow = this.costs.map(function(item) {
        return {
            date: item.date,
            amount: item.amount,
            currency: item.account.currency,
            id: item.id,
            category: item.category.name,
            account: item.account.name
          };
      });
    },
    deleteItem (item) {
      this.$resource('/costs{/id}').remove({id: item.id}).then(result => {
        if (result.ok) {
          location.reload();
        }
      })
    },

  }
}
</script>
<style>

</style>