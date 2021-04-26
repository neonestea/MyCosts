import Vue from 'vue'
import VueResource from 'vue-resource'
import Header from 'pages/Header.vue'
import Footer from 'pages/Footer.vue'
import Bar from 'pages/Bar.vue'
import Donut from 'pages/Donut.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import VueApexCharts from 'vue-apexcharts'


Vue.component('apexchart', VueApexCharts)

Vue.use(Vuetify)
Vue.use(VueResource)
Vue.use(VueApexCharts)

new Vue ({
    el: '#head',
    vuetify : new Vuetify(),
    render: a => a(Header)
})

new Vue ({
    el: '#foot',
    vuetify : new Vuetify(),
    render: a => a(Footer)
})

new Vue ({
    el: '#donutApp',
    vuetify : new Vuetify(),
    render: a => a(Donut)
})
new Vue ({
    el: '#barApp',
    vuetify : new Vuetify(),
    render: a => a(Bar)
})