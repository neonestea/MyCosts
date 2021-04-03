function getIndex(list, id) {
    for (var i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}

var accountApi = Vue.resource('/account{/id}');

Vue.component('account-form', {
    props: ['accounts', 'accountAttr', 'currencies'],
    data: function() {
        return {
            name: '',
            amount: '',
            currency: 'RUB',
            id: ''
        }
    },
    watch: {
        accountAttr: function(newVal, oldVal) {
            this.name = newVal.name;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="account name" v-model="name" />' +
        '<input type="text" placeholder="amount" v-model="amount" />' +
        '<select type="text"  v-model="currency" >' +
            '<option v-for="curr in currencies" :key="curr" :value="curr">{{curr}}</option>' +
        '</select>' +
        '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function() {
            console.log(this.currency)
            var account = { name: this.name, amount: this.amount, currency: this.currency };
            if (this.id) {
                accountApi.update({id: this.id}, account).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.accounts, data.id);
                        this.accounts.splice(index, 1, data);
                        this.amount = ''
                        this.name = ''
                        this.currency = ''
                        this.id = ''
                    })
                )
            } else {

                accountApi.save({}, account).then(result =>
                    result.json().then(data => {
                        this.accounts.push(data);
                        this.amount = ''
                        this.name = ''
                        this.currency = ''
                    })
                )
            }

        }
    }
});

Vue.component('account-row', {
    props: ['account', 'editMethod', 'accounts'],
    template: '<div>' +
        '{{ account.name }}   ' +
        '{{ account.currency }}  ' +
        '{{ account.amount }}  ' +
        '<input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="X" @click="del" />' +
        '</div>',
    methods: {
        edit: function() {
            this.editMethod(this.account);
        },
        del: function() {
            accountApi.remove({id: this.account.id}).then(result => {
                if (result.ok) {
                    this.accounts.splice(this.accounts.indexOf(this.account), 1)
                }
            })
        }
    }
});

Vue.component('accounts-list', {
    props: ['accounts', 'currencies'],
    data: function () {
        return {
            account: null
        }
    },
    template:
        '<div>' +
        '<account-form :accounts="accounts" :accountAttr="account" :currencies="currencies" />' +
        '<account-row v-for="account in accounts" :key="account.id" :account="account" ' +
        ':editMethod="editMethod" :accounts="accounts" />' +
        '</div>',
    methods: {
        editMethod: function (account) {
            this.account = account;
        }
    }
});

var app = new Vue({
    el: '#app',
    template:
        '<div>' +
        '<accounts-list :accounts="accounts" :currencies="currencies"/>' +
        '</div>',
    data: {
        accounts: frontendData.accounts,
        currencies: frontendData.currencies,
        profile: frontendData.profile
    },
    created: function() {
        /*accountApi.get().then(result =>
            result.json().then(data =>
                data.forEach(account => this.accounts.push(account))
            )
        )*/
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
        profile: frontendData.profile,
    },
    created: function() {
    },
});