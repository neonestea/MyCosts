<template>
  <apexchart width="500" type="bar" :options="options" :series="series"></apexchart>
</template>

<script>
export default {
  data() {
    return {
      options: {
        xaxis: {
          categories: ["May 2020","June 2020","July 2020","August 2020","September 2020","October 2020","November 2020", "December 2020", "January 2021", "February 2021", "March 2021", "April 2021"],

        },
      },
      series: [{
        name: 'Total amount (USD)',
        data: [30000, 40000, 45000, 20000, 30000, 40000, 45000, 20000, 30000, 40000, 45000, 20000]
      }]
    }
  }
}
</script>

<style scoped>

</style>