<template>
<apexchart width="500" type="bar" :options="optionsBar" :series="seriesBar"></apexchart>
  <apexchart width="380" type="donut" :options="optionsDonut" :series="seriesDonut"></apexchart>
</template>

<script>
import VueApexCharts from 'vue-apexcharts'
export default {
  data: function() {
    return {
      optionsDonut: {},
      seriesDonut: [44, 55, 41, 17, 15],
      optionsBar: {
        xaxis: {
          categories: ["Last month", "This month", "Average"]
        }
      },
      seriesBar: [{
        //name: 'series-1',
        data: [30, 40, 45]
      }]
    }
  }
}
</script>

<style scoped>

</style>