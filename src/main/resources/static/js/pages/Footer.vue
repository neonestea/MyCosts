<template>
  <v-footer padless style="background: rgba(205, 214, 224, 0.4);">
    <v-btn class="mx-4" style="margin: auto;">
      <v-icon size="24px" style="margin: 10px;">
        email
      </v-icon>
    </v-btn>
    <v-divider></v-divider>
    <v-col
        class="text-center"
        cols="12"
    >
      {{ new Date().getFullYear() }} — <strong>&copy;NoKek Limited</strong>
    </v-col>
  </v-footer>
</template>
<script>

</script>
<style>

</style>