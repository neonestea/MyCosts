<template>
  <div >
    <category-form
        :categories="categories"
        :categoryAttr="category"/>
    <v-divider></v-divider>
    <div style="display: flex;
    flex-wrap: wrap;
    justify-content:center;
    align-items:center;">
      <category-row v-for="category in categories"
                    :key="category.id"
                    :category="category"
                    :categories="categories"/>
    </div>
  </div>
</template>
<script>
import CategoryForm from 'components/categories/CategoryForm.vue'
import CategoryRow from 'components/categories/CategoryRow.vue'
export default {
  components: {
    CategoryForm,
    CategoryRow
  },
  props: ['categories'],
  data: function () {
    return {
      category: null
    }
  },
  methods: {
    editMethod: function (category) {
      this.category = category;
    }
  }
}
</script>
<style>

</style>