
<template>
  <v-app style="background: #F4F5F5;">
<v-tabs style="max-height: 5px;">
    <v-tab @click="showDonut();" >Month statistics</v-tab>
    <v-tab @click="showBar();">Year statistics</v-tab>
    <v-tab @click="showReport();">Reports</v-tab>
  </v-tabs>
  <apexchart id="monthChart" style="margin: auto; " width="500" type="donut" :options="optionsDonut" :series="seriesDonut"></apexchart>
  <apexchart id="yearChart" style="margin: auto; display: none;" width="700" type="bar" :options="optionsBar" :series="seriesBar"></apexchart>
    <div id="report" style="margin: auto; display: none;">
      <p>Здесь будет таблица и кнопка для отправки отчёта :)</p>
      <p>Но пока их нет :(</p>
    </div>
  </v-app>

</template>

<script>
export default {
  data () {
    return {
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
      seriesBar: [{
        name: 'Food',
        data: [44000, 55000, 41000, 67000, 22000, 43000,44000, 55000, 41000, 67000, 22000, 43000]
      }, {
        name: 'Services',
        data: [13000, 23000, 20000, 8000, 13000, 27000, 13000, 23000, 20000, 8000, 13000, 27000,]
      }, {
        name: 'Other',
        data: [11000, 17000, 15000, 15000, 21000, 14000,11000, 17000, 15000, 15000, 21000, 14000, ]
      }, {
        name: 'Entertainment',
        data: [21000, 7000, 25000, 13000, 22000, 8000,21000, 7000, 25000, 13000, 22000, 8000,]
      }],
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
          text: "Your costs by the last year",
          align: 'center',
          margin: 10,
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
          categories: ['05/01/2020 GMT', '06/01/2020 GMT', '07/01/2020 GMT', '08/01/2020 GMT',
            '09/01/2020 GMT', '10/01/2020 GMT', '11/01/2020 GMT', '12/01/2020 GMT',
            '01/01/2021 GMT', '02/01/2021 GMT','03/01/2021 GMT','04/01/2021 GMT'
          ],
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
    this.initializeBar();

  },
  methods: {
    initializeBar(){
      this.$resource('/year-months').get().then(result =>
          result.json().then(data => {
            console.log("DATES");
              console.log(data);
          })
      );
      this.$resource('/year-stat').get().then(result =>
          result.json().then(data => {
            console.log("CATEGORIES AND AMOUNTS");
            console.log(data);
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

    },
    showBar(){
      $("#yearChart").show();
      $("#monthChart").hide();
      $("#report").hide();
    },
    showReport(){
      $("#report").show();
      $("#yearChart").hide();
      $("#monthChart").hide();
    },
  }
}
</script>

<style scoped>

</style>
