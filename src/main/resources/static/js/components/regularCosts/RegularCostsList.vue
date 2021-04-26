<template>
  <v-app>
  <div>
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
          :search="search"
          sort-by="date"
          class="elevation-1"
      >
<!--        <v-dialog
            v-model="dialog"
            max-width="500px"
        >
          <v-card>
            <v-card-title>
              <span class="headline">Edit</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col
                      cols="12"
                      sm="6"
                      md="4"
                  >
                    <v-text-field
                        v-model="editedItem.name"
                        label="Cost name"
                    ></v-text-field>
                  </v-col>
                  <v-col
                      cols="12"
                      sm="6"
                      md="4"
                  >
                    <v-text-field
                        v-model="editedItem.nextPayDate"
                        label="Next pay date"
                    ></v-text-field>
                  </v-col>
                  <v-col
                      cols="12"
                      sm="6"
                      md="4"
                  >
                    <v-text-field
                        v-model="editedItem.amount"
                        label="Amount"
                    ></v-text-field>
                  </v-col>
                  <v-col
                      cols="12"
                      sm="6"
                      md="4"
                  >
                    <v-text-field
                        v-model="editedItem.carbs"
                        label="Carbs (g)"
                    ></v-text-field>
                  </v-col>
                  <v-col
                      cols="12"
                      sm="6"
                      md="4"
                  >
                    <v-text-field
                        v-model="editedItem.protein"
                        label="Protein (g)"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                  color="blue darken-1"
                  text
                  @click="close"
              >
                Cancel
              </v-btn>
              <v-btn
                  color="blue darken-1"
                  text
                  @click="save"
              >
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>-->
          <template v-slot:item.actions="{ item }">
            <v-icon
                small
                class="mr-2"
                @click="editItem(item)"
            >edit</v-icon>
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
      search: '',
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
    this.initialize()
  },
  methods: {
    initialize(){
      let per;
      this.regularCostsRow = this.regularCosts.map(function(item) {
        if(item.everyMonth == false){
          per = item.period;
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

  }
}
</script>
<style>

</style>