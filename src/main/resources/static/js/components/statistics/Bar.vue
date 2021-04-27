<template>
  <apexchart style="margin: auto;" width="700" type="bar" :options="options" :series="series"></apexchart>
</template>

<script>
export default {
  data() {
    return {
      series: [{
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
      options: {
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
            enabled: true
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
  }
}
</script>

<style scoped>

</style>

