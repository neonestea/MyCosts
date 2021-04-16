<template>
  <div>
  <cost-form :costs="costs"
              :accounts="accounts"
              :categories="categories" />
  <div class="cards">
    <cost-row v-for="cost in costs"
              :key="cost.id"
              :cost="cost"
              :costs="costs" />
    </div>
  </div>
</template>
<script>
import CostRow from 'components/costs/CostRow.vue'
import CostForm from 'components/costs/CostForm.vue'
export default {
  components: {
    CostRow,
    CostForm
  },
  props: ['costs', 'accounts', 'categories'],
  data: function () {
    return {
      cost: null,
      fields: ['date', 'amount', 'account', 'category'],
    }
  },
  methods: {
    editMethod(cost) {
      this.cost = cost;
    },
  }
}
</script>
<style>

</style>