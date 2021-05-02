<template>
  <v-app style="background: #F4F5F5;">
    <div>
      <v-alert
          v-model="alert"
          border="left"
          close-text="Close Alert"
          color="blue-grey accent-4"
          dark
          dismissible
      >
        Here you can manage your categories. Some default categories have already been created.
        To create a category fill in the form and click "Save" button.
        To delete a category click "Bin" button. To edit a category use "Edit" button.
      </v-alert>
    </div>
    <categories-list :categories="categories" />

  </v-app>
</template>
<script>
import CategoriesList from "../components/categories/CategoriesList.vue";


export default {
  components: {
    CategoriesList
  },
  data() {
    return {
      categories: frontendData.categories,
      profile: frontendData.profile,
      alert: true,
    }
  },
  created() {
    $("#CategoriesBtn").css({ "color": "white", "border-bottom": "2px solid white"})
  }
}
</script>
<style>

</style>