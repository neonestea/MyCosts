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
            currency: '',
            id: ''
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Account name" v-model="name" maxlength="25"/>' +
        '<input type="number" step="0.01" placeholder="Amount" v-model="amount" :oninput="checkAmount()"/>' +
        '<select type="text"  v-model="currency" >' +
        '<option value="" disabled selected>Currency</option>' +
            '<option v-for="curr in currencies" :key="curr" :value="curr">{{curr}}</option>' +
        '</select>' +
        '<input type="button" value="Save" @click="save" :disabled="isDisable(name, currency)"/>' +
        '<p id="error_line"></p>' +
        '<p id="recovery_line">You already had this account. Do you want to recover it?</p>' +
        '<input type="button" value="Yes" @click="recover" id="no"/>' +
        '<input type="button" value="No" @click="notRecover" id="yes"/>' +
        '</div>',
    methods: {
        checkAmount(){
            if (this.amount.indexOf(".") != '-1') {
                this.amount=this.amount.substring(0, this.amount.indexOf(".") + 3);
            }
            else {
                this.amount = this.amount + ".00";
            }
        },
        isDisable(name, currency) {
            return name.length == 0 || currency.length == 0;
        },
        recover: function() {
            //RECOVER ACCOUNT
        },
        notRecover: function() {
            const recoveryLine = document.getElementById('recovery_line');
            const yes = document.getElementById('yes');
            const no = document.getElementById('no');
            recoveryLine.style.display = "none";
            yes.style.display = "none";
            no.style.display = "none";
            this.amount = ''
            this.name = ''
            this.currency = ''

        },
        save: function() {
            var account = { name: this.name, amount: this.amount, currency: this.currency };
            accountApi.save({}, account).then(result =>
                result.json().then(data => {
                    if (data != null){
                        this.accounts.push(data);
                        this.amount = ''
                        this.name = ''
                        this.currency = ''
                    }
                    else {
                        const errorLine = document.getElementById('error_line');
                        errorLine.innerHTML = "Account already exists!";
                    }
                    //if (такой аккаунт есть, но он неактивный) {
                    //const recoveryLine = document.getElementById('recovery_line');
                    //const yes = document.getElementById('yes');
                    //const no = document.getElementById('no');
                    //recoveryLine.style.visibility = "visible";
                    //yes.style.visibility = "visible";
                    //no.style.visibility = "visible";
                    //}
                })
            )
        }
    }
});

Vue.component('account-row', {
    props: ['account', 'editMethod', 'accounts'],
    data: function() {
        return {
            name: '',
            amount: '',
            id: ''
        }
    },
    template: '<div class="card">' +
        '<div>{{ account.name }}</div>' +
        '<div>{{ account.currency }}</div>' +
        '<div>{{ account.amount }}</div>' +
        '<input :id="`edit`+account.id" type="button" value="Edit" @click="askEdit" />' +
        '<input :id="`delete`+account.id" type="button" value="X" @click="del" />' +
        '<input style="display: none;" :id="`name`+account.id" type="text" placeholder="Account name" v-model="name" maxlength="25"/>' +
        '<input style="display: none;" :id="`amount`+account.id" type="number" step="0.01" placeholder="Amount" v-model="amount" :oninput="checkAmount()"/>' +
        '<input :id="`editBtn`+account.id" style="display: none;" type="button" value="Edit" @click="edit" />' +
        '<input :id="`cancelBtn`+account.id" style="display: none;" type="button" value="Cancel" @click="cancel" />' +
        '</div>',
    methods: {
        checkAmount(){
            if (this.amount.indexOf(".") != '-1') {
                this.amount=this.amount.substring(0, this.amount.indexOf(".") + 3);
            }
            else {
                this.amount = this.amount + ".00";
            }
        },
        askEdit: function() {
            const name = document.getElementById('name'+this.account.id);
            const amount = document.getElementById('amount'+this.account.id);
            const btn = document.getElementById('editBtn'+this.account.id);
            const edit = document.getElementById('edit'+this.account.id);
            const del = document.getElementById('delete'+this.account.id);
            const cancel = document.getElementById('cancelBtn'+this.account.id);
            name.style.display = "block";
            amount.style.display = "block";
            btn.style.display = "block";
            edit.style.display = "none";
            del.style.display = "none";
            cancel.style.display = "block";

        },
        edit: function() {
            //this.editMethod(this.account);
        },
        cancel: function(){
            const name = document.getElementById('name'+this.account.id);
            const amount = document.getElementById('amount'+this.account.id);
            const btn = document.getElementById('editBtn'+this.account.id);
            const edit = document.getElementById('edit'+this.account.id);
            const del = document.getElementById('delete'+this.account.id);
            const cancel = document.getElementById('cancelBtn'+this.account.id);
            name.style.display = "none";
            amount.style.display = "none";
            btn.style.display = "none";
            edit.style.display = "block";
            del.style.display = "block";
            cancel.style.display = "none";

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
        '<div class="cards">' +
        '<account-row v-for="account in accounts" :key="account.id" :account="account" ' +
        ':editMethod="editMethod" :accounts="accounts" />' +
        '</div>' +
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