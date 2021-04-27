<template>
<v-tabs v-model="tab">
    <v-tab @click="showDonut();">Month statistics</v-tab>
    <v-tab @click="showBar();">Year statistics</v-tab>
    <v-tab @click="showReport();">Reports</v-tab>
  </v-tabs>
<donut id="monthChart" style="display: none;"></donut>
  <bar id="yearChart" style="display: none;"></bar>

</template>

<script>
import Bar from 'components/statistics/Bar.vue'
import Donut from 'components/statistics/Donut.vue'
export default {
  components: {
    Donut,
    Bar
  },
  data () {
    return {
      tab: null,
    }
  },
  created() {
    this.showDonut();
  },
  methods: {
    showDonut(){
      /*const element = document.getElementById('monthChart');
      element.style.display = 'block';*/
      console.log("DONUT");
    },
    showBar(){
      /*const element = document.getElementById('yearChart');
      element.style.display = 'block';*/
      console.log("BAR");
    },
    showReport(){
      console.log("REPORT");
    },
  }
}
</script>

<style scoped>

</style>