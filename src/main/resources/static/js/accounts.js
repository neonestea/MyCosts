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
    props: ['accounts', 'accountAttr'],
    data: function() {
        return {
            name: '',
            id: '',
            amount: '',
            currency: ''
        }
    },
    watch: {
        accountAttr: function(newVal, oldVal) {
            this.name = newVal.name;
            this.amount = newVal.amount;
            this.currency = newVal.currency;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Account name" v-model="name" />' +
        '<select type="text" placeholder="Currency" v-model="currency">' +
        '<option>USD</option>\n' +
        '<option>RUB</option>' +
        '</select>' +
        '<input type="text" placeholder="Amount" v-model="amount" />' +
        '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function() {
            var amount = { name: this.name, amount: this.amount, currency: this.currency};
            //console.log(category);
            if (this.id) {
                amountApi.update({id: this.id}, amount).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.amounts, data.id);
                        this.amounts.splice(index, 1, data);
                        this.name = ''
                        this.id = ''
                        this.amount = ''
                        this.currency = ''
                    })
                )
            } else {
                amountApi.save({}, amount).then(result =>
                    result.json().then(data => {
                        this.amounts.push(data);
                        this.name = ''
                        this.amount = ''
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
        '{{ account.name }}' +
        '{{ account.currency }}' +
        '{{ account.amount }}' +
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
    props: ['accounts'],
    data: function () {
        return {
            account: null
        }
    },
    template:
        '<div>' +
        '<account-form :accounts="accounts" :accountAttr="account" />' +
        '<account-row v-for="account in accounts" :key="account.id" :account="account" ' +
        ':editMethod="editMethod" :accounts="accounts" />' +
        '</div>',
    methods: {
        editMethod: function (account) {
            this.account = account;
        }
    }
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

var app = new Vue({
    el: '#app',
    template:
        '<div>' +

        '<accounts-list :accounts="accounts" />' +
        '</div>',
    data: {
        accounts: frontendData.accounts,
        profile: frontendData.profile
    },
    created: function() {
        /*categoryApi.get().then(result =>
            result.json().then(data =>
                data.forEach(category => this.categories.push(category))
            )
        )*/
    },
});