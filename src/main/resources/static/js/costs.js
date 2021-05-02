import Vue from 'vue'
import VueResource from 'vue-resource'
import Header from 'pages/Header.vue'
import Footer from 'pages/Footer.vue'
import Costs from 'pages/Costs.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import * as Sentry from "@sentry/vue";
import { Integrations } from "@sentry/tracing";

Sentry.init({
    Vue,
    dsn: "https://b15b7775280f493c961a41b059d41751@o605290.ingest.sentry.io/5744792",
    integrations: [new Integrations.BrowserTracing()],

    // Set tracesSampleRate to 1.0 to capture 100%
    // of transactions for performance monitoring.
    // We recommend adjusting this value in production
    tracesSampleRate: 1.0,
});

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
    el: '#costsApp',
    vuetify : new Vuetify(),
    render: a => a(Costs)
})
