<template>
  <v-app>
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
&lt;!&ndash;                <v-row>
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
&lt;!&ndash;                    <v-menu
                        v-model="menu2"
                        :close-on-content-click="false"
                        :nudge-right="40"
                        transition="scale-transition"
                        offset-y
                        min-width="auto"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                            v-model="editedItem.nextDate"
                            label="Next pay date"
                            readonly
                            v-bind="attrs"
                            v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                          v-model="nextDate"
                          @input="menu2 = false"
                      ></v-date-picker>
                    </v-menu>&ndash;&gt;
                  </v-col>
                  <v-col
                      cols="12"
                      sm="6"
                      md="4"
                  >
                    <v-select
                        :items="accounts"
                        label="Account"
                        v-model="editedItem.account"
                        name="account"
                        item-text="name"
                    ></v-select>
                  </v-col>
                  <v-col
                      cols="12"
                      sm="6"
                      md="4"
                  >
                    <v-select
                        :items="categories"
                        label="Category"
                        v-model="editedItem.category"
                        name="category"
                        item-text="name"
                    ></v-select>
                  </v-col>
                  <v-col
                      cols="12"
                      sm="6"
                      md="4"
                  >
                    <v-checkbox
                        style="padding-right: 5px;"
                        v-model="editedItem.everyMonth"
                        label="Every month"
                    ></v-checkbox>
                  </v-col>
                  <v-col
                      cols="12"
                      sm="6"
                      md="4"
                  >
                    <v-text-field label="Day interval"
                                  id="daysInput"
                                  hide-details="auto"
                                  placeholder="1"
                                  step="1"
                                  min="1"
                                  type="number"
                                  v-model="editedItem.period">
                    </v-text-field>
                  </v-col>
                </v-row>&ndash;&gt;
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
                  @click="edit"
              >
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>-->
          <template v-slot:item.actions="{ item }">
<!--            <v-icon
                small
                class="mr-2"
                @click="editItem(item)"
            >edit</v-icon>-->
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
    this.initialize()
  },
  /*watch: {
    dialog (val) {
      val || this.close()
    },
  },*/
  methods: {
    /*isDisableInterval(){
      return this.everyMonthPicked;
    },*/
    /*editItem (item) {
      this.editedIndex = this.regularCostsRow.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },
    close () {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },*/
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
    edit(item){
      console.log("KEK");
    }

  }
}
</script>
<style>

</style>