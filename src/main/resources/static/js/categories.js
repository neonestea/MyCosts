import Vue from 'vue'
import VueResource from 'vue-resource'
import Header from 'pages/Header.vue'
import Footer from 'pages/Footer.vue'
import Categories from 'pages/Categories.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
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
    el: '#categoriesApp',
    vuetify : new Vuetify(),
    render: a => a(Categories)
})



