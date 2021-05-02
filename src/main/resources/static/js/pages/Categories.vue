<template>
  <v-app style="background: #F4F5F5;">

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
      profile: frontendData.profile
    }
  },
  created() {
    $("#CategoriesBtn").css({ "color": "white", "border-bottom": "2px solid white"})
  }
}
</script>
<style>

</style>