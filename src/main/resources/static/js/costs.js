function getIndex(list, id) {
    for (var i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}

var costApi = Vue.resource('/costs{/id}');

Vue.component('cost-form', {
    props: ['costs', 'costAttr', 'accounts', 'categories'],
    data: function() {
        return {
            id: '',
            account: '',
            category: '',
            amount: '',
            date: ''
        }
    },
    watch: {
        costAttr: function(newVal, oldVal) {
            this.id = newVal.id;
            this.account = newVal.account;
            this.category = newVal.category;
            this.amount = newVal.amount;
            this.date = newVal.date;
        }
    },
    template:
        '<div>' +
        '<input type="date" v-model="date"' +
        '<input type="text" placeholder="amount" v-model="amount" />' +
        '<select type="text" v-model="account" >' +
        '<option disabled="true">Account</option>' +
        '<option v-for="acc in accounts" :key="acc.id" :value="acc">{{acc.name}}</option>' +
        '</select>' +
        '<select type="text"  v-model="category" >' +
        '<option disabled="true">Category</option>' +
        '<option v-for="cat in categories" :key="cat.id":value="cat">{{cat.name}}</option>' +
        '</select>' +
        '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function() {
            var cost = { date: this.date, amount: this.amount, account: this.account, category: this.category };
            console.log(category);
            if (this.id) {
                costApi.update({id: this.id}, cost).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.costs, data.id);
                        this.costs.splice(index, 1, data);
                        this.name = ''
                        this.id = ''
                    })
                )
            } else {
                costApi.save({}, cost).then(result =>
                    result.json().then(data => {
                        //console.log("KEK");
                        this.costs.push(data);
                        this.name = ''
                    })
                )
            }

        }
    }
});

Vue.component('cost-row', {
    props: ['cost', 'editMethod', 'costs'],
    template: '<div>' +
        '{{ cost.name }}' +
        '<input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="X" @click="del" />' +
        '</div>',
    methods: {
        edit: function() {
            this.editMethod(this.cost);
        },
        del: function() {
            costApi.remove({id: this.cost.id}).then(result => {
                if (result.ok) {
                    this.costs.splice(this.costs.indexOf(this.cost), 1)
                }
            })
        }
    }
});

Vue.component('costs-list', {
    props: ['costs', 'accounts', 'categories'],
    data: function () {
        return {
            cost: null
        }
    },
    template:
        '<div>' +
        '<cost-form :costs="costs" :costAttr="cost" :accounts="accounts" :categories="categories" />' +
        '<cost-row v-for="cost in costs" :key="cost.id" :cost="cost" ' +
        ':editMethod="editMethod" :costs="costs" />' +
        '</div>',
    methods: {
        editMethod: function (cost) {
            this.cost = cost;
        }
    }
});

var app = new Vue({
    el: '#app',
    template:
        '<div>' +
        '<costs-list :costs="costs" :accounts="accounts" categories="categories"/>' +
        '</div>',
    data: {
        costs: frontendData.costs,
        profile: frontendData.profile,
        accounts: frontendData.accounts,
        categories: frontendData.categories
    },
    created: function() {
    },
});

var app2 = new Vue({
    el: '#log',
    template:
        '<div>' +
        '<div v-if="!profile"><a href="/login" class="navEl">SignIn</a></div>' +
        '<div v-else><a href="/logout" class="navEl">SignOut</a></div>' +
        '<div v-if="profile">{{profile.name}}</div>' +
        '</div>',
    data: {
        profile: frontendData.profile
    },
    created: function() {
    },
});