<template>
  <div class="editForm"
       :id="`form`+category.id">
    <v-text-field :id="`name`+category.id"
        label="New name"
                  hide-details="auto"
                  placeholder="Category name"
                  type="text"
                  maxlength="25"
                  v-model="name"></v-text-field>
    <div style="display: flex; justify-content: space-evenly; padding: 10px;">
  <button :id="`editBtn`+category.id"
          type="button"
          value="Edit"
          @click="edit"
         :disabled="isDisable(name)"><v-icon>edit</v-icon></button>
  <button :id="`cancelBtn`+category.id"
         type="button"
         value="Cancel"
         @click="cancel" ><v-icon>cancel</v-icon></button>
  </div>
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
      console.log(this.category.id + " id " + this.name )
      this.$resource('/category/' + this.category.id + '?newCategoryName=' + this.name).update()
          .then(result => {
            if(result.status == '202') {
              result.json()
                  .then(data => {
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
.editForm {
  display: flex;
  justify-content: center;
  padding: 25px;
  align-items: center;
  z-index: 9999;
  flex-direction: column;
  border-radius: 3px;
  background: #FFFFFF;
  -webkit-box-shadow: -20px 21px 8px 0px rgba(66, 73, 78, 0.2);
  -moz-box-shadow: -20px 21px 8px 0px rgba(66, 73, 78, 0.2);
  box-shadow: -20px 21px 8px 0px rgba(66, 73, 78, 0.2);
}
</style>