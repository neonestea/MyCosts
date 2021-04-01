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