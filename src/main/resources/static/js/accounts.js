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
        '<input id="addInput" type="text" placeholder="Account name" v-model="name" maxlength="25" onkeypress="return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)"/>' +
        '<input id="addInput" type="number" step="0.01" placeholder="Amount" v-model="amount" :oninput="checkAmount()"/>' +
        '<select type="text"  v-model="currency" >' +
        '<option value="" disabled selected>Currency</option>' +
            '<option v-for="curr in currencies" :key="curr" :value="curr">{{curr}}</option>' +
        '</select>' +
        '<input type="button" value="Save" @click="save" :disabled="isDisable(name, currency)"/>' +
        '<p id="error_line" style="display: none;">Account already exists!</p>' +
        '<p id="rec_line" style="display: none;">You have deleted this account. Try to recover it from the line above.</p>' +
        '<div style="display: none;" id="recover_block">' +
        '<p >You already had this account. Do you want to recover it?</p>' +
        '<input type="button" value="Yes" @click="recover" id="yes"/>' +
        '<input type="button" value="No" @click="notRecover" id="no"/>' +
        '</div>' +
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
            accountApi.update({id: this.id}, null)
                .then(result => {
                        result.json()
                            .then(data => {
                            this.accounts.push(data);
                                this.id = '';
                                const recoveryLine = document.getElementById('recover_block');
                                recoveryLine.style.display = "none";
                                document.querySelectorAll('.button').forEach(elem => {
                                    elem.disabled = false;
                                });
                                const add = document.getElementById('addInput');
                                add.disabled = false;
                                this.amount = ''
                                this.name = ''
                                this.currency = ''
                        })

                })
        },
        notRecover: function() {
            this.id = '';
            const recoveryLine = document.getElementById('recover_block');
            recoveryLine.style.display = "none";
            document.querySelectorAll('.button').forEach(elem => {
                elem.disabled = false;
            });
            const add = document.getElementById('addInput');
            add.disabled = false;
            this.amount = ''
            this.name = ''
            this.currency = ''

        },
        save: function() {
            var account = { name: this.name, amount: this.amount, currency: this.currency };
            accountApi.save({}, account).then(result => {
                if(result.status == '200') {
                    result.json()
                        .then(data => {
                                this.accounts.push(data);
                                this.amount = ''
                                this.name = ''
                                this.currency = ''
                        })
                    }
                else if(result.status == "201") {
                    result.json()
                        .then(data => {
                            this.id = data.id;
                            document.querySelectorAll('.button').forEach(elem => {
                                elem.disabled = true;
                            });
                            const add = document.getElementById('addInput');
                            add.disabled = true;
                            const recLine = document.getElementById('recover_block');
                            recLine.style.display = "block";
                        })
                }
                else {

                    $("#error_line").show('slow');
                    setTimeout(function() { $("#error_line").hide('slow'); }, 2000);
                    this.amount = ''
                    this.name = ''
                    this.currency = ''
                }

            })
        }
    }
});
Vue.component('account-edit-form', {
    props: ['account', 'accounts'],
    data: function() {
        return {
            name: this.account.name,
            amount: this.account.amount.toString(),
            currency: this.account.currency,
            id: this.account.id
        }
    },
    template:
        '<div class="editForm" :id="`form`+amount.id">' +
        'New account name:' +
        '<input :id="`name`+account.id" type="text" placeholder="Account name" v-model="name" maxlength="25" onkeypress="return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)"/>' +
        'New amount:' +
        '<input :id="`amount`+account.id" type="number" step="0.01" placeholder="Amount" v-model="amount" :oninput="checkAmount()"/>' +
        '<input :id="`editBtn`+account.id"  type="button" value="Edit" @click="edit" :disabled="isDisable(name)"/>' +
        '<input :id="`cancelBtn`+account.id" type="button" value="Cancel" @click="cancel" />' +
        '</div>',


    methods: {
        checkAmount() {
            if (this.amount.indexOf(".") != '-1') {
                this.amount = this.amount.substring(0, this.amount.indexOf(".") + 3);
            } else {
                this.amount = this.amount + ".00";
            }
        },
        isDisable(name, amount) {
            return this.name.length == 0 || this.amount.length == 0 || (this.name == this.account.name && this.amount == this.account.amount);
        },
        cancel: function () {
            const form = document.getElementById('form' + this.account.id);
            const editBtn = document.getElementById('editBtn' + this.account.id);
            const cancelBtn = document.getElementById('cancelBtn' + this.account.id);
            form.style.display = "none";
            cancelBtn.disabled = false;
            editBtn.disabled = false;
            document.querySelectorAll('.button').forEach(elem => {
                elem.disabled = false;
            });
            const add = document.getElementById('addInput');
            add.disabled = false;
        },
        edit: function () {
            var account = {name: this.name, amount: this.amount, currency: this.currency};
            accountApi.update({id: this.account.id}, account)
                .then(result => {
                    if(result.status == '200') {
                    result.json()
                        .then(data => {
                            this.name = this.account.name;
                            this.accounts.splice(this.accounts.indexOf(this.account), 1);
                            const form = document.getElementById('form' + this.account.id);
                            form.style.display = "none";
                            this.accounts.push(data);
                            document.querySelectorAll('.button').forEach(elem => {
                                elem.disabled = false;
                            });
                            const add = document.getElementById('addInput');
                            add.disabled = false;
                        })
                    }

                    else if(result.status == "201") {
                        const form = document.getElementById('form' + this.account.id);
                        form.style.display = "none";
                        $("#rec_line").show('slow');
                        setTimeout(function() { $("#rec_line").hide('slow'); }, 2000);
                        //this.amount = this.account.amount;
                        this.name = this.account.name;
                        document.querySelectorAll('.button').forEach(elem => {
                            elem.disabled = false;
                        });
                        const add = document.getElementById('addInput');
                        add.disabled = false;
                }
                else {
                        const form = document.getElementById('form' + this.account.id);
                        form.style.display = "none";
                    $("#error_line").show('slow');
                    setTimeout(function() { $("#error_line").hide('slow'); }, 2000);
                    //this.amount = this.account.amount;
                    this.name = this.account.name;
                        document.querySelectorAll('.button').forEach(elem => {
                            elem.disabled = false;
                        });
                        const add = document.getElementById('addInput');
                        add.disabled = false;
                }
            })
        },
    }
});

Vue.component('account-row', {
    props: ['account', 'editMethod', 'accounts'],
    template:
        '<div >' +
        '<account-edit-form style="display: none; z-index: 9999; position: absolute;" :id="`form`+account.id" :accounts="accounts" :account="account" />' +

        '<div class="card">' +

        '<div>{{ account.name }}</div>' +
        '<div>{{ account.currency }}</div>' +
        '<div>{{ account.amount }}</div>' +
        '<input class="button" :id="`edit`+account.id" type="button" value="Edit" @click="askEdit" />' +
        '<input class="button" :id="`delete`+account.id" type="button" value="X" @click="del" />' +
        '</div>' +
        '</div>',
    methods: {
        askEdit: function() {
            const form = document.getElementById('form'+this.account.id);
            form.style.display = "block";
            document.querySelectorAll('.button').forEach(elem => {
                elem.disabled = true;
            });
            const add = document.getElementById('addInput');
            add.disabled = true;
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