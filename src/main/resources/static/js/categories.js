function getIndex(list, id) {
    for (var i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}


var categoryApi = Vue.resource('/category{/id}');

Vue.component('category-form', {
    props: ['categories', 'categoryAttr'],
    data: function() {
        return {
            name: '',
            id: ''
        }
    },
/*    watch: {
        categoryAttr: function(newVal, oldVal) {
            this.name = newVal.name;
            this.id = newVal.id;
        }
    },*/
    template:
        '<div>' +
        '<input type="text" placeholder="Category name" v-model="name" />' +
        '<input type="button" value="Save" @click="save" :disabled="isDisable(name)"/>' +
        '<p id="error_line"></p>' +
        '</div>',
    methods: {
        isDisable(name) {
            return name.length == 0;
        },
        save: function() {
            var category = { name: this.name };
          /*  if (this.id) {
                categoryApi.update({id: this.id}, category).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.categories, data.id);
                        this.categories.splice(index, 1, data);
                        this.name = ''
                        this.id = ''
                    })
                )
            } else {*/
                categoryApi.save({}, category).then(result =>
                    result.json().then(data => {
                        if (data != null){
                            this.categories.push(data);
                            this.name = ''
                        }
                        else {
                            const errorLine = document.getElementById('error_line');
                            errorLine.innerHTML = "Category already exists!";
                        }
                    })
                )
            }

        }
  /*  }*/
});

Vue.component('category-row', {
    props: ['category', 'editMethod', 'categories'],
    data: function() {
        return {
            name: '',
            id: ''
        }
    },
    template: '<div class="card">' +
        '{{ category.name }}' +
        '<input :id="`edit`+category.id" type="button" value="Edit" @click="askEdit" />' +
        '<input :id="`delete`+category.id" type="button" value="X" @click="del" />' +
        '<input style="display: none;" :id="`name`+category.id" type="text" placeholder="Category name" v-model="name" maxlength="25"/>' +
        '<input :id="`editBtn`+category.id" style="display: none;" type="button" value="Edit" @click="edit" />' +
        '<input :id="`cancelBtn`+category.id" style="display: none;" type="button" value="Cancel" @click="cancel" />' +

        '</div>',
    methods: {
        askEdit: function() {
            const name = document.getElementById('name'+this.category.id);
            const btn = document.getElementById('editBtn'+this.category.id);
            const edit = document.getElementById('edit'+this.category.id);
            const del = document.getElementById('delete'+this.category.id);
            const cancel = document.getElementById('cancelBtn'+this.category.id);
            name.style.display = "block";
            btn.style.display = "block";
            edit.style.display = "none";
            del.style.display = "none";
            cancel.style.display = "block";

        },
        cancel: function(){
            const name = document.getElementById('name'+this.category.id);
            const btn = document.getElementById('editBtn'+this.category.id);
            const edit = document.getElementById('edit'+this.category.id);
            const del = document.getElementById('delete'+this.category.id);
            const cancel = document.getElementById('cancelBtn'+this.category.id);
            name.style.display = "none";
            btn.style.display = "none";
            edit.style.display = "block";
            del.style.display = "block";
            cancel.style.display = "none";
        },
        edit: function() {
            this.editMethod(this.category);
        },
        del: function() {
            categoryApi.remove({id: this.category.id}).then(result => {
                if (result.ok) {
                    this.categories.splice(this.categories.indexOf(this.category), 1)
                }
            })
        }
    }
});

Vue.component('categories-list', {
    props: ['categories'],
    data: function () {
        return {
            category: null
        }
    },
    template:
        '<div>' +
        '<category-form :categories="categories" :categoryAttr="category" />' +
        '<div class="cards">' +

        '<category-row v-for="category in categories" :key="category.id" :category="category" ' +
        ':editMethod="editMethod" :categories="categories" />' +
        '</div>' +
        '</div>',
    methods: {
        editMethod: function (category) {
            this.category = category;
        }
    }
});

var app = new Vue({
    el: '#app',
    template:
        '<div>' +
        '<categories-list :categories="categories" />' +
        '</div>',
    data: {
        categories: frontendData.categories,
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