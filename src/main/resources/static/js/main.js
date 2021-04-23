import Vue from 'vue'
import VueResource from 'vue-resource'
import Header from 'pages/Header.vue'
import Footer from 'pages/Footer.vue'
import Accounts from 'pages/Accounts.vue'
import Categories from 'pages/Categories.vue'
import Costs from 'pages/Costs.vue'
import RegularCosts from 'pages/RegularCosts.vue'
import Statistics from 'pages/Statistics.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import MainPage from 'pages/MainPage.vue'
import VueApexCharts from 'vue-apexcharts'
Vue.use(VueApexCharts)

Vue.component('apexchart', VueApexCharts)

Vue.use(Vuetify)
Vue.use(VueResource)

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
    el: '#accountsApp',
    vuetify : new Vuetify(),
    render: a => a(Accounts)
})

new Vue ({
    el: '#statApp',
    vuetify : new Vuetify(),
    render: a => a(Statistics)
})

new Vue ({
    el: '#mainPage',
    vuetify : new Vuetify(),
    render: a => a(MainPage)
})

new Vue ({
    el: '#categoriesApp',
    vuetify : new Vuetify(),
    render: a => a(Categories)
})

new Vue ({
    el: '#costsApp',
    vuetify : new Vuetify(),
    render: a => a(Costs)
})

new Vue ({
    el: '#regularCostsApp',
    vuetify : new Vuetify(),
    render: a => a(RegularCosts)
})
