import Vue from 'vue'
import VueResource from 'vue-resource'
import Header from 'pages/Header.vue'
import Footer from 'pages/Footer.vue'
import Stat from 'pages/StatisticsPage.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import VueApexCharts from 'vue-apexcharts'
import * as Sentry from "@sentry/vue";
import { Integrations } from "@sentry/tracing";

Sentry.init({
    Vue,
    dsn: "https://f2637d9fbb68418a9fd5a43b88b5438f@o605330.ingest.sentry.io/5744798",
    integrations: [new Integrations.BrowserTracing()],

    // Set tracesSampleRate to 1.0 to capture 100%
    // of transactions for performance monitoring.
    // We recommend adjusting this value in production
    tracesSampleRate: 1.0,
});


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

/*
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
*/



new Vue ({
    el: '#statApp',
    vuetify : new Vuetify(),
    render: a => a(Stat)
})

