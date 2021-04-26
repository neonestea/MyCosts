<template>
    <apexchart style="margin: auto;" width="480" type="donut" :options="options" :series="series"></apexchart>
</template>

<script>
export default {
  data: function() {
    return {
      options: {
        labels: ['Food', 'Services', 'Other', 'Entertainment'],
      },
      series: [44, 55, 100, 21]

    }
  },
  created () {
    this.initialize()
  },
  methods: {
    initialize(){
      this.$resource('/last-month-stat').get().then(result =>
          result.json().then(data => {
            console.log(data);
          })
      )
    }
  }
}
</script>

<style scoped>

</style>
