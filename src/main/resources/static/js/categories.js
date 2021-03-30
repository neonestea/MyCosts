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
            text: '',
            id: ''
        }
    },
    watch: {
        categoryAttr: function(newVal, oldVal) {
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Category name" v-model="text" />' +
        '<input type="button" value="Add" @click="save" />' +
        '</div>',
    methods: {
        save: function() {
            var category = { text: this.text };

            if (this.id) {
                categoryApi.update({id: this.id}, category).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.categories, data.id);
                        this.categories.splice(index, 1, data);
                        this.text = ''
                        this.id = ''
                    })
                )
            } else {
                categoryApi.save({}, category).then(result =>
                    result.json().then(data => {
                        this.categories.push(data);
                        this.text = ''
                    })
                )
            }
        }
    }
});

/*Vue.component('category-row', {
    props: ['category', 'editMethod', 'categories'],
    template: '<div>' +
        '<i>({{ category.id }})</i> {{ category.text }}' +
        '<span style="position: absolute; right: 0">' +
        '<input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="X" @click="del" />' +
        '</span>' +
        '</div>',
    methods: {
        /!*edit: function() {
            this.editMethod(this.category);
        },
        del: function() {
            categoryApi.remove({id: this.category.id}).then(result => {
                if (result.ok) {
                    this.categories.splice(this.categories.indexOf(this.category), 1)
                }
            })
        }*!/
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
        '<div style="position: relative; width: 300px;">' +
        '<category-form :categories="categories" :categoryAttr="category" />' +
        '<category-row v-for="category in categories" :key="category.id" :category="category" ' +
        ':editMethod="editMethod" :categories="categories" />' +
        '</div>',
    methods: {
        editMethod: function (category) {
            this.category = category;
        }
    }
});*/

var app = new Vue({
    el: '#app',
    /*template:
        '<div>' +
        '<categories-list :categories="categories" />' +
        '</div>',
    data: {
        //categories: frontendData.categories,
        profile: frontendData.profile
    },
    created: function() {
    },*/
});