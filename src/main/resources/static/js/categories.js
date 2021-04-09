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
        '<input id="addInput" type="text" placeholder="Category name" v-model="name" />' +
        '<input type="button" value="Save" @click="save" :disabled="isDisable(name)"/>' +
        '<p id="error_line" style="display: none;"></p>' +
        '</div>',
    methods: {

        isDisable(name) {
            return name.length == 0;
        },
        save: function() {
            var category = { name: this.name };
          /*  if (this.id) {

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
Vue.component('category-edit-form', {
    props: ['category', 'categories'],
    data: function() {
        return {
            name: this.category.name,
            id: ''
        }
    },
    template:

        '<div class="editForm" :id="`form`+category.id">' +
        'New category name:' +
        '<input :id="`name`+category.id" type="text" placeholder="Category name" v-model="name" maxlength="25" />' +
        '<input :id="`editBtn`+category.id" type="button" value="Edit" @click="edit" :disabled="isDisable(name)"/>' +
        '<input :id="`cancelBtn`+category.id" type="button" value="Cancel" @click="cancel" />' +
        '</div>',

    methods: {
        isDisable(name) {
            return name.length == 0;
        },
        cancel: function(){
            const form = document.getElementById('form'+this.category.id);
            const editBtn = document.getElementById('editBtn'+this.category.id);
            const cancelBtn = document.getElementById('cancelBtn'+this.category.id);
            form.style.display = "none";
            cancelBtn.disabled = false;
            editBtn.disabled = false;
            document.querySelectorAll('.button').forEach(elem => {
                elem.disabled = false;
            });
            const add = document.getElementById('addInput');
            add.disabled = false;
        },
        edit: function() {
            this.editMethod(this.category);
        },
    }
});


Vue.component('category-row', {
    props: ['category', 'editMethod', 'categories'],
    template:
        '<div >' +
        '<category-edit-form style="display: none; z-index: 9999; position: absolute;" :id="`form`+category.id" :categories="categories" :category="category" />' +

        '<div class="card">' +
        '{{ category.name }}' +
        '<input class="button" :id="`edit`+category.id" type="button" value="Edit" @click="askEdit" />' +
        '<input class="button" :id="`delete`+category.id" type="button" value="X" @click="del" />' +
        '</div>' +
    '</div>',
    methods: {
        askEdit: function() {
            const form = document.getElementById('form'+this.category.id);
            form.style.display = "block";
            document.querySelectorAll('.button').forEach(elem => {
                elem.disabled = true;
            });
            const add = document.getElementById('addInput');
            add.disabled = true;
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