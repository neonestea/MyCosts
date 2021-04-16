import Vue from 'vue'
import VueResource from 'vue-resource'
import Index from 'pages/Index.vue'
import Accounts from 'pages/Accounts.vue'
import Categories from 'pages/Categories.vue'
import Costs from 'pages/Costs.vue'

Vue.use(VueResource)

new Vue ({
    el: '#log',
    render: a => a(Index)
})

new Vue ({
    el: '#accountsApp',
    render: a => a(Accounts)
})


new Vue ({
    el: '#categoriesApp',
    render: a => a(Categories)
})

new Vue ({
    el: '#costsApp',
    render: a => a(Costs)
})
