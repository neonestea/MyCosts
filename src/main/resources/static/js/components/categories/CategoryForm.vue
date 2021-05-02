<template>
  <div style="margin: 20px 10px; width: 250px;">
    <v-row>
    <v-col
        class="d-flex"
        cols="12"
        sm="6"
    >
      <v-text-field label="Category name"
                    hide-details="auto"
                    placeholder="Category name"
                    type="text"
                    maxlength="25"
                    v-model="name"></v-text-field>
    </v-col>
      <v-col
          class="d-flex"
          cols="12"
          sm="6"
          style="align-items: baseline;"
      >
      <v-btn type="button"
             style="height: 22px; margin-top: 25px;"
             value="Save"
             @click="save"
             :disabled="isDisable(name)">Save</v-btn>
      </v-col>
    </v-row>
<div id="error_line" style="display: none; margin-top: 15px;"><v-alert
    border="top"
    colored-border
    type="error"
    elevation="2"
>
  Category already exists.
</v-alert>
</div>

<!--    <p id="error_line" style="display: none; padding: 15px;">Category already exists!</p>-->
  </div>
</template>
<script>
export default {
  props: ['categories'],
  data() {
    return {
      name: '',
      id: '',
    }
  },
  methods: {
    isDisable(name) {
      return name.length == 0;
    },
    save() {
      var category = {name: this.name};
      this.$resource('/category{/id}').save({}, category).then(result => {
        if (result.status == '201') {
          result.json()
              .then(data => {
                this.categories.push(data);
                this.name = ''
              })
        } else {

          $("#error_line").show('slow');
          setTimeout(function () {
            $("#error_line").hide('slow');
          }, 2000);
          this.name = ''
        }

      })
    },
  }
}
</script>
<style>

</style>