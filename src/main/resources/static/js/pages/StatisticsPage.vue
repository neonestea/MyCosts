
<template>
  <v-app style="background: #F4F5F5;">
    <v-tabs style="max-height: 5px;">
      <v-tab @click="showDonut();" >Month statistics</v-tab>
      <v-tab @click="showBar();">Year statistics</v-tab>
      <v-tab @click="showReport();">Reports</v-tab>
      <v-tab @click="showAverage();">Average</v-tab>
    </v-tabs>
    <apexchart id="monthChart" style="margin: auto; " width="500" type="donut" :options="optionsDonut" :series="seriesDonut"></apexchart>
    <apexchart id="yearChart" style="margin: auto; display: none;" width="700" type="bar" :options="optionsBar" :series="seriesBar"></apexchart>
    <data-app id="report" style="display: none; margin-top: 60px;">
      <v-btn style="margin-bottom: 10px; margin-left: 10px;" title="Hint: your month report will be automatically sent in the end of the month. But, if you want you can press the button and receive it now."><v-icon

      >attach_email</v-icon>Send report</v-btn>
      <v-card>
        <v-card-title>
          Month costs
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
            :items="monthCostsRow"
            :search="search"
            sort-by="category"
            multi-sort
            class="elevation-1"
        >
        </v-data-table>
      </v-card>
    </data-app>
      <apexchart id="averageChart" style="margin: auto; display: none;" width="500" type="donut" :options="optionsAverage" :series="seriesAverage"></apexchart>

    </v-app>

</template>

<script>
export default {
  data () {
    return {
      search: '',
      monthCostsRow: [],
      headers: [
        { text: 'Category', value: 'category' },
        { text: 'Account', value: 'account' },
        { text: 'Currency', value: 'currency' },
        { text: 'Amount', value: 'amount' },
      ],
      tab: null,
      seriesDonut: [],
      optionsDonut: {
        labels: [],
        noData: {
          text: "You don't have costs yet :(",
          align: 'center',
          verticalAlign: 'middle',
          offsetX: 0,
          offsetY: 0,
          style: {
            color: undefined,
            fontSize: '14px',
            fontFamily: undefined
          }
        },
        title: {
          text: "Your costs (USD) by categories rate",
          align: 'center',
          margin: 20,
          offsetX: 0,
          offsetY: 0,
          floating: false,
          style: {
            fontSize:  '14px',
            fontWeight:  'bold',
            fontFamily:  undefined,
            color:  '#263238'
          },
        },
      },
      seriesAverage: [],
      optionsAverage: {
        labels: [],
        noData: {
          text: "You don't have costs yet :(",
          align: 'center',
          verticalAlign: 'middle',
          offsetX: 0,
          offsetY: 0,
          style: {
            color: undefined,
            fontSize: '14px',
            fontFamily: undefined
          }
        },
        title: {
          text: "Your average costs (USD) by categories rate",
          align: 'center',
          margin: 20,
          offsetX: 0,
          offsetY: 0,
          floating: false,
          style: {
            fontSize:  '14px',
            fontWeight:  'bold',
            fontFamily:  undefined,
            color:  '#263238'
          },
        },
      },
      seriesBar: [],
      optionsBar: {

        noData: {
          text: "You don't have costs yet :(",
          align: 'center',
          verticalAlign: 'middle',
          offsetX: 0,
          offsetY: 0,
          style: {
            color: undefined,
            fontSize: '14px',
            fontFamily: undefined
          }
        },
        title: {
          text: "Your costs(USD) by the last year",
          align: 'center',
          margin: 20,
          offsetX: 0,
          offsetY: 0,
          floating: false,
          style: {
            fontSize:  '14px',
            fontWeight:  'bold',
            fontFamily:  undefined,
            color:  '#263238'
          },
        },
        dataLabels: {
          enabled: false,
        },
        chart: {
          type: 'bar',
          height: 350,
          stacked: true,
          toolbar: {
            show: true
          },
          zoom: {
            enabled: true,
            type: 'x',
            resetIcon: {
              offsetX: -10,
              offsetY: 0,
              fillColor: '#fff',
              strokeColor: '#37474F'
            },
            selection: {
              background: '#90CAF9',
              border: '#0D47A1'
            }
          }
        },
        responsive: [{
          breakpoint: 480,
          options: {
            legend: {
              position: 'bottom',
              offsetX: -10,
              offsetY: 0
            }
          }
        }],
        plotOptions: {
          bar: {
            borderRadius: 8,
            horizontal: false,
          },
        },
        xaxis: {
          type: 'datetime',
          labels: {
            format: 'MM yyyy',
          },
          categories: [],
        },
        legend: {
          position: 'right',
          offsetY: 40
        },
        fill: {
          opacity: 1
        }
      },
    }
  },
  created() {
    this.showDonut();
    this.initializeDonut();
    this.initializeAverageDonut();
    this.initializeTable();
  },
  methods: {
    initializeBar(){
      this.$resource('/year-months').get().then(result =>
          result.json().then(data => {
            this.optionsBar = {
              xaxis: {
                categories: data,
              }
            };
          })
      );
      this.$resource('/year-stat').get().then(result =>
          result.json().then(data => {
            for(let i = 0; i < data.length; i++){
              let name = Object.values(data[i])[0];
              let amounts = Object.values(data[i])[1];
              this.seriesBar.push({name: name, data: amounts});
            }
          })
      );
    },
    initializeDonut(){
      this.$resource('/last-month-stat').get().then(result =>
          result.json().then(data => {
            this.optionsDonut = {
              labels: Object.keys(data),
            };
            this.seriesDonut = Object.values(data);
          })
      );
    },
    showDonut(){
      $("#monthChart").show();
      $("#yearChart").hide();
      $("#report").hide();
      $("#averageChart").hide();

    },
    showBar(){
      this.initializeBar();
      $("#yearChart").show();
      $("#monthChart").hide();
      $("#report").hide();
      $("#averageChart").hide();
    },
    showReport(){

      $("#report").show();
      $("#yearChart").hide();
      $("#monthChart").hide();
      $("#averageChart").hide();


    },
    showAverage(){

      $("#averageChart").show();
      $("#yearChart").hide();
      $("#monthChart").hide();
      $("#report").hide();
    },
    initializeAverageDonut(){
      this.$resource('/averages').get().then(result =>
          result.json().then(data => {
            result.json().then(data => {
              this.optionsAverage = {
                labels: Object.keys(data),
              };
              this.seriesAverage = Object.values(data);
            })
          })
      );
    },
    initializeTable(){
      this.$resource('/tables').get().then(result =>
          result.json().then(data => {
            this.monthCostsRow = data.map(function(item) {
              return {
                category: item.category,
                amount: item.amount,
                account: item.account,
                currency: item.currency
              };
            });
          })
      );
    }
  }
}
</script>

<style scoped>

</style>
