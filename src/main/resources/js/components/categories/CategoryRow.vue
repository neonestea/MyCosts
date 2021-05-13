<template>
  <div>
  <category-edit-form style="display: none; z-index: 9999; position: absolute;"
                      :id="`form`+category.id"
                      :categories="categories"
                      :category="category" />

  <v-card style="margin: 20px; padding: 10px; display: flex; flex-direction: column; justify-content: center;">
    <v-card-title style="justify-content: center;">{{ category.name }}</v-card-title>
    <div style="display: flex; justify-content: space-evenly;">
      <v-btn :style="showButton()"
           class="button"
           :id="`edit`+category.id"
           type="button"
           value="Edit"
             style="margin: 5px;"
           @click="askEdit">

        <v-icon>edit</v-icon>
        Edit
      </v-btn>
      <v-btn :style="showButton()"
           class="button"
           :id="`delete`+category.id"
           type="button"
           value="X"
             style="margin: 5px;"
           @click="del" >
        <v-icon>delete</v-icon>
        Delete
      </v-btn>
    </div>
  </v-card>
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
button:disabled {
  cursor: default;
  opacity: 0.3;
}

button {
  opacity: 0.8;
}
</style>