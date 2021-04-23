<template>
  <v-expansion-panels focusable>
    <v-expansion-panel>
      <v-expansion-panel-header>Categories</v-expansion-panel-header>
      <v-expansion-panel-content>
        Enable users to work with default and customizable categories.
      </v-expansion-panel-content>
    </v-expansion-panel>
    <v-expansion-panel>
      <v-expansion-panel-header>Accounts</v-expansion-panel-header>
      <v-expansion-panel-content>
        You can manage your accounts.
      </v-expansion-panel-content>
    </v-expansion-panel>
    <v-expansion-panel>
      <v-expansion-panel-header>Monthly report</v-expansion-panel-header>
      <v-expansion-panel-content>
        We will send you a monthly report.
      </v-expansion-panel-content>
    </v-expansion-panel>
    <v-expansion-panel>
      <v-expansion-panel-header>Regular costs</v-expansion-panel-header>
      <v-expansion-panel-content>
        You can add regular costs not to add costs each time.
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script>
export default {
  name: "MainPage.vue"
}
</script>

<style scoped>

</style>