<template>
  <div class="cost">
  <div class="costEl">{{ cost.date }}</div>
  <div class="costEl">{{ cost.amount }} {{cost.account.currency}}</div>
  <div class="costEl">{{ cost.account.name }}</div>
  <div class="costEl">{{ cost.category.name }}</div>
  <input type="button" value="X" @click="del" />
  </div>
</template>
<script>
export default {
  props: ['cost', 'costs'],
  methods: {
    del() {
      this.$resource('/costs{/id}').remove({id: this.cost.id}).then(result => {
        if (result.ok) {
          this.costs.splice(this.costs.indexOf(this.cost), 1)
        }
      })
    }
  }
}
</script>
<style>

</style>