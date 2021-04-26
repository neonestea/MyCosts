<template>
    <apexchart width="380" type="donut" :options="options" :series="series"></apexchart>
</template>

<script>
export default {
  data: function() {
    return {
      options: {
        labels: ['Food', 'Services', 'Other', 'Entertainment']
      },
      series: [44, 55, 100, 21]

    }
  }
}
</script>

<style scoped>

</style>
