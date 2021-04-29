
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
    this.initializeBar();

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
