<template>
  <v-app style="background: #F4F5F5;">
    <apexchart style="margin: auto;" width="480" type="donut" :options="options" :series="series"></apexchart>
<!--  <v-btn
      color="blue-grey"
      class="ma-2 white&#45;&#45;text"

  >
    Upload
    <v-icon
        right
        dark
    >
      attach_email
    </v-icon>
  </v-btn>-->
  </v-app>
</template>

<script>
export default {
  data: function() {
    return {
      series: [],
      options: {
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
      },
    }
  },
  created () {
    this.initialize()
  },
  methods: {
    initialize(){
      this.$resource('/last-month-stat').get().then(result =>
          result.json().then(data => {
            this.options = {
              labels: Object.keys(data),
            };
            this.series = Object.values(data);
          })
      )
    }
  }
}
</script>

<style scoped>

</style>
