<template>
  <div class="editForm"
       :id="`form`+category.id">
  New category name:
  <input :id="`name`+category.id"
         type="text"
         placeholder="Category name"
         v-model="name"
         maxlength="25"
         onkeypress="return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)"/>
  <input :id="`editBtn`+category.id"
          type="button"
          value="Edit"
          @click="edit"
          :disabled="isDisable(name)"/>
  <input :id="`cancelBtn`+category.id"
         type="button"
         value="Cancel"
         @click="cancel" />
  </div>
</template>
<script>
export default {
  props: ['category', 'categories'],
  data: function() {
    return {
      name: this.category.name,
      id: this.category.id
    }
  },
  methods: {
    isDisable(name, amount) {
      return this.name.length == 0 || this.name == this.category.name;
    },
    cancel() {
      const form = document.getElementById('form' + this.category.id);
      const editBtn = document.getElementById('editBtn' + this.category.id);
      const cancelBtn = document.getElementById('cancelBtn' + this.category.id);
      form.style.display = "none";
      cancelBtn.disabled = false;
      editBtn.disabled = false;
      document.querySelectorAll('.button').forEach(elem => {
        elem.disabled = false;
      });
      const add = document.getElementById('addInput');
      add.disabled = false;
    },
    edit() {
      var category = {name: this.name};
      this.$resource('/category{/id}').update({name: this.category.name}, category)
          .then(result => {
            if(result.status == '201') {
              result.json()
                  .then(data => {
                    console.log(data)
                    console.log(data.id + " :id")
                    this.name = this.category.name;
                    this.categories.splice(this.categories.indexOf(this.category), 1);
                    const form = document.getElementById('form' + this.category.id);
                    form.style.display = "none";
                    this.categories.push(data);
                    document.querySelectorAll('.button').forEach(elem => {
                      elem.disabled = false;
                    });
                    const add = document.getElementById('addInput');
                    add.disabled = false;
                  })
            }
            else {
              const form = document.getElementById('form' + this.category.id);
              form.style.display = "none";
              $("#error_line").show('slow');
              setTimeout(function() { $("#error_line").hide('slow'); }, 2000);
              this.name = this.category.name;
              document.querySelectorAll('.button').forEach(elem => {
                elem.disabled = false;
              });
              const add = document.getElementById('addInput');
              add.disabled = false;
            }
          })
    },
  }
}
</script>
<style>

</style>