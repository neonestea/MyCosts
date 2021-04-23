<template>
    <apexchart width="380" type="donut" :options="options" :series="series"></apexchart>
</template>

<script>
export default {
  data: function() {
    return {
      options: {
        labels: ['Apple', 'Mango', 'Orange']
      },
      series: [44, 55, 41]

    }
  }
}
</script>

<style scoped>

</style>
