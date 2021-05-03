<template>
  <v-footer style="margin-top: 20px; display:flex; flex-direction: column; color: white; font-family: Roboto,sans-serif; background: #5C6BC0">
    <v-btn class="mx-4 white" style="margin: 5px auto;" icon href="mailto:mycostssender@gmail.com">
      <v-icon size="24px" style="margin: 15px; color: white;">
        email
      </v-icon>
    </v-btn>

    <div style="width: 100%; border-top: 0.4px solid white;"></div>
    <div
        style="margin: 5px auto;"
    >
      {{ new Date().getFullYear() }} — <strong>&copy;NoKek Limited</strong>
    </div>
  </v-footer>
</template>
<script>

</script>
<style>

</style>