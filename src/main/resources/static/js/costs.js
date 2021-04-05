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
        const today = new Date();
        const year = today.getFullYear();
        let month = today.getMonth()+1;
        if (month < 10){
            month = "0" + month;
        }
        let day = today.getDate();
        if (day < 10){
            day = "0" + day;
        }
        let minMonth = today.getMonth();
        if (minMonth < 10){
            minMonth = "0" + minMonth;
        }
        const minDate = year + '-'+ minMonth +'-01';
        const maxDate = year + '-'+ month +'-'+day;
        return {
            id: '',
            account: '',
            category: '',
            amount: '',
            date: '',
            max: maxDate,
            min: minDate

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
        '<input type="date" v-model="date" :max="max" :min="min" />' +
        '<input type="text" placeholder="Amount" v-model="amount" />' +
        '<select type="text" v-model="account" >' +
        '<option value="" disabled selected>Account</option>' +
        '<option v-for="acc in accounts" :key="acc.id" :value="acc">{{acc.name}}</option>' +
        '</select>' +
        '<select type="text"  v-model="category" >' +
        '<option value="" disabled selected>Category</option>' +
        '<option v-for="cat in categories" :key="cat.id":value="cat">{{cat.name}}</option>' +
        '</select>' +
        '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function() {
            var cost = { date: this.date, amount: this.amount, account: this.account, category: this.category };
            //console.log(cost);
            if (this.id) {
                costApi.update({id: this.id}, cost).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.costs, data.id);
                        this.costs.splice(index, 1, data);
                        this.amount = ''
                        this.id = ''
                    })
                )
            } else {
                costApi.save({}, cost).then(result =>
                    result.json().then(data => {
                        this.costs.push(data);
                        this.amount = ''

                    })
                )
            }

        }
    }
});

Vue.component('cost-row', {
    props: ['cost', 'editMethod', 'costs'],
    template: '<div>' +
        '{{ cost.date }}  ' +
        '{{ cost.amount }}  ' +
        '{{ cost.account.name }}   ' +
        '{{ cost.category.name }}' +
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
        '<costs-list :costs="costs" :accounts="accounts" :categories="categories"/>' +
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