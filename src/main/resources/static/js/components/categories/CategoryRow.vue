<template>
  <div >
  <category-edit-form style="display: none; z-index: 9999; position: absolute;"
                      :id="`form`+category.id"
                      :categories="categories"
                      :category="category" />
  <div class="card">
    {{ category.name }}
    <input :style="showButton()"
           class="button"
           :id="`edit`+category.id"
           type="button"
           value="Edit"
           @click="askEdit" />
    <input :style="showButton()"
           class="button"
           :id="`delete`+category.id"
           type="button"
           value="X"
           @click="del" />
    </div>
    </div>
</template>
<script>
import CategoryEditForm from 'components/categories/CategoryEditForm.vue'
export default {
  components: {
    CategoryEditForm
  },
  props: ['category', 'categories'],
  methods: {
    showButton(){
      if(this.category.name == "Other"){
        return "display: none;";
      }
      else {
        return "display: block;"
      }
    },
    askEdit() {
      const form = document.getElementById('form'+this.category.id);
      form.style.display = "block";
      document.querySelectorAll('.button').forEach(elem => {
        elem.disabled = true;
      });
      const add = document.getElementById('addInput');
      add.disabled = true;
    },
    del() {
      this.$resource('/category{/id}').remove({id: this.category.id}).then(result => {
        if (result.ok) {
          this.categories.splice(this.categories.indexOf(this.category), 1)
        }
      })
    }
  }
}
</script>
<style>

</style>