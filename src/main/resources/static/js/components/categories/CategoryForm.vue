<template>
  <div style="margin: 20px 10px;">

    <input id="addInput"
           type="text"
           style="background: #FFF;
    padding: 5px;
    border-radius: 5px;"
           placeholder="Category name"
           v-model="name"
           onkeypress="return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)"/>
    <v-btn type="button"
           style="height: 22px;"
           value="Save"
           @click="save"
           :disabled="isDisable(name)">Save</v-btn>
    <p id="error_line" style="display: none; padding: 15px;">Category already exists!</p>
  </div>
</template>
<script>
export default {
  props: ['categories'],
  data() {
    return {
      name: '',
      id: ''
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
                console.log(data)
                console.log(data.id + " id")
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