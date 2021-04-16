<template>
  <div>
    <categories-list :categories="categories" />
  </div>
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
}
</script>
<style>

</style>