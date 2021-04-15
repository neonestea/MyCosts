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
            date: maxDate,
            max: maxDate,
            min: minDate,
            isRegular: "false",
            dayInterval: ''

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
        '<input type="number" step="0.01" placeholder="Amount" v-model="amount" :oninput="checkAmount()" />' +
        '<select type="text" v-model="account" >' +
        '<option value="" disabled selected>Account</option>' +
        '<option v-for="acc in accounts" :key="acc.id" :value="acc">{{acc.name}}</option>' +
        '</select>' +
        '<select type="text"  v-model="category" >' +
        '<option value="" disabled selected>Category</option>' +
        '<option v-for="cat in categories" :key="cat.id":value="cat">{{cat.name}}</option>' +
        '</select>' +
        '<input type="radio" value="true" v-model="isRegular" >' +
        '<label>regular</label>' +
        '<input type="radio" value="false" v-model="isRegular" >' +
        '<label>not regular </label>' +
        '<input id="daysInput" :disabled="disableInput(isRegular)" type="number" step="1" min="1" placeholder="Interval in days" v-model="dayInterval"/>' +
        '<input type="button" value="Save" @click="save" :disabled="isDisable(amount, account, category,dayInterval)"/>' +
        '</div>',
    methods: {
        disableInput(isRegular){
            if(isRegular == "true"){
                return false;
            }
            else {
                this.dayInterval = '';
                return true;
            }
        },
        checkAmount(){
            if (this.amount.indexOf(".") != '-1') {
                this.amount=this.amount.substring(0, this.amount.indexOf(".") + 3);
            }
            else {
                this.amount = this.amount + ".00";
            }
        },
        isDisable(amount, account, category) {
            if (this.isRegular == "true"){
                return amount.length == 0 || account.length == 0 || category.length == 0 || this.dayInterval.length == 0;
            }
            else {
                return amount.length == 0 || account.length == 0 || category.length == 0;
            }
        },
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
                        this.currency = ''
                        this.account = ''
                        this.dayInterval = '';
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
    template:

        '<div class="cost">' +
        '<div class="costEl">{{ cost.date }}</div>' +
        '<div class="costEl">{{ cost.amount }} {{cost.account.currency}}</div>' +
        '<div class="costEl">{{ cost.account.name }}</div>' +
        '<div class="costEl">{{ cost.category.name }}</div>' +
        '<input type="button" value="X" @click="del" />' +
         '</div>',
    methods: {
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
            cost: null,
            fields: ['date', 'amount', 'account', 'category'],
        }
    },
    template:
        '<div>' +
        '<cost-form :costs="costs" :costAttr="cost" :accounts="accounts" :categories="categories" />' +

        '<div class="cards">' +
        '<cost-row v-for="cost in costs" :key="cost.id" :cost="cost" ' +
        ':editMethod="editMethod" :costs="costs" />' +
        '</div>' +
        '</div>',
    methods: {
        editMethod: function (cost) {
            this.cost = cost;
        },
        sortBy: function(sortKey) {
            this.reverse = (this.sortKey == sortKey) ? ! this.reverse : false;
            this.sortKey = sortKey;
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