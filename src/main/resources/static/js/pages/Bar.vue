<template>
  <apexchart width="500" type="bar" :options="options" :series="series"></apexchart>

</template>

<script>
export default {
  data() {
    return {
      options: {
        xaxis: {
          categories: ["Last month", "This month", "Average"]
        }
      },
      series: [{
        //name: 'series-1',
        data: [30, 40, 45]
      }]
    }
  }
}
</script>

<style scoped>

</style>