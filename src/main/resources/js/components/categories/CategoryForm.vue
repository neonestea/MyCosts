<template>
  <div style="margin: 20px 10px;  ">
    <v-menu
        v-model="menu"
        :close-on-content-click="false"
        :nudge-width="200"
        offset-x
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            color="indigo"
            dark
            v-bind="attrs"
            v-on="on"
        >
          Add new
        </v-btn>
      </template>
    <v-card style="padding: 15px; background: rgba(165, 168, 185); width: 300px;">
      <v-card-title>Add new category</v-card-title>
      <v-text-field
          label="Category name"
                    hide-details="auto"
                    placeholder="Category name"
                    type="text"
                    maxlength="25"
                    v-model="name"
          required
      ></v-text-field>

      <v-btn type="button"
             style="height: 22px; margin-top: 25px;"
             outlined
             rounded
             text
             value="Save"
             @click="save"
             :disabled="isDisable(name)">Save</v-btn>
    </v-card>
    </v-menu>
<div id="error_line" style="display: none; margin-top: 15px;"><v-alert
    border="top"
    colored-border
    type="error"
    elevation="2"
>
  Category already exists.
</v-alert>
</div>
    <div id="error_line2" style="display: none; margin-top: 15px;"><v-alert
        border="top"
        colored-border
        type="error"
        elevation="2"
    >
      Too many categories. You can add not more than 20 categories.
    </v-alert>
    </div>
  </div>
</template>
<script>
export default {
  props: ['categories'],
  data() {
    return {
      name: '',
      menu: false,
      id: '',
    }
  },
  methods: {
    isDisable(name) {
      return name.length == 0;
    },
    save() {
      const catCount = this.categories.length;
      if (catCount >= 20){
        $("#error_line2").show('slow');
        setTimeout(function () {
          $("#error_line2").hide('slow');
        }, 2000);
        this.name = ''
      }
      var category = {name: this.name};
      this.$resource('/category{/id}').save({}, category).then(result => {
        if (result.status == '201') {
          result.json()
              .then(data => {
                this.categories.push(data);
                this.name = '';
                this.menu = false;
              })
        } else {

          $("#error_line").show('slow');
          setTimeout(function () {
            $("#error_line").hide('slow');
          }, 2000);
          this.name = ''
          this.menu = false;
        }

      })
    },
  }
}
</script>
<style>

</style>