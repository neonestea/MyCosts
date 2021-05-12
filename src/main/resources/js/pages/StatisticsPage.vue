
<template>
  <v-app style="background: #F4F5F5;">
    <v-tabs style="max-height: 5px; ">
      <v-tab @click="showDonut();" >Month statistics</v-tab>
      <v-tab @click="showBar();">Year statistics</v-tab>
      <v-tab @click="showReport();">Reports</v-tab>
      <v-tab @click="showTotal();">Total</v-tab>
    </v-tabs>
    <apexchart id="monthChart" style="margin: auto; " width="500" type="donut" :options="optionsDonut" :series="seriesDonut"></apexchart>
    <apexchart id="yearChart" style="margin: auto; display: none;" width="700" type="bar" :options="optionsBar" :series="seriesBar"></apexchart>
    <data-app id="report" style="display: none; margin-top: 60px;">
      <v-btn style="margin-bottom: 10px; margin-left: 10px;" @click="sendReportNow();" @mouseover="showHint();"><v-icon
      >attach_email</v-icon>Send report</v-btn>
      <div id="info_line" style="display: none; margin-top: 15px;">
      <v-alert
          border="top"
          colored-border
          type="info"
          elevation="2"
      >
        We will send you month reports automatically. But if you want, we can send it just now.
      </v-alert>
      </div>
      <div id="info_report_line" style="display: none; margin-top: 15px;">
        <v-alert
            border="top"
            colored-border
            type="info"
            elevation="2"
        >
          We have sent you the report. Check your email.
        </v-alert>
      </div>
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
    <data-app id="reportTotal" style="display: none; margin-top: 60px;">
      <v-card>
        <v-card-title>
          Total costs
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
            :headers="headersTotal"
            :items="totalRow"
            :search="search"
            sort-by="totalAmount"
            multi-sort
            class="elevation-1"
        >
        </v-data-table>
      </v-card>
    </data-app>
    </v-app>

</template>

<script>

export default {
  data () {
    return {
      search: '',
      monthCostsRow: [],
      totalRow: [],
      headers: [
        { text: 'Category', value: 'category' },
        { text: 'Account', value: 'account' },
        { text: 'Currency', value: 'currency' },
        { text: 'Amount', value: 'amount' },
      ],
      headersTotal: [
        { text: 'Category', value: 'category' },
        { text: 'Amount (USD)', value: 'amount' },
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
    $("#StatisticsBtn").css({ "color": "white", "border-bottom": "2px solid white"})
    this.showDonut();
    this.initializeDonut();
    this.initializeTotal();
    this.initializeTable();
  },
  methods: {
    sendReportNow(){
      this.$resource('/report-by-mail').get().then(result =>
          result.json().then(data => {
            $("#info_report_line").show('slow');
            setTimeout(function () {
              $("#info_report_line").hide('slow');
            }, 2000);

          }))
    },
    showHint(){
      $("#info_line").show('slow');
      setTimeout(function () {
        $("#info_line").hide('slow');
      }, 2000);
    },
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
            let old = this.seriesBar.length;
            for(let i = 0; i < data.length; i++){
              let name = Object.values(data[i])[0];
              let amounts = Object.values(data[i])[1];
              this.seriesBar.push({name: name, data: amounts});
            }
            this.seriesBar = this.seriesBar.slice(old);
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
      $("#reportTotal").hide();

    },
    showBar(){
      this.initializeBar();
      $("#yearChart").show();
      $("#monthChart").hide();
      $("#report").hide();
      $("#reportTotal").hide();
    },
    showReport(){

      $("#report").show();
      $("#yearChart").hide();
      $("#monthChart").hide();
      $("#reportTotal").hide();
    },
    showTotal(){

      $("#reportTotal").show();
      $("#yearChart").hide();
      $("#monthChart").hide();
      $("#report").hide();
    },
    initializeTotal(){
      this.$resource('/totals').get().then(result =>
          result.json().then(data => {
            const keys = Object.keys(data);
            const values = Object.values(data);
            for(let i = 0; i < values.length; i++){
              let tot = {
                category: keys[i],
                amount: values[i]
              }
              this.totalRow.push(tot);
            }
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
