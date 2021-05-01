<template>
  <v-footer padless style="margin-top: 20px; display:flex; flex-direction: column; background: rgba(166, 174, 177, 0.4);">
    <v-btn class="mx-4" style="margin: 5px auto;" icon href="mailto:mycostssender@gmail.com">
      <v-icon size="24px" style="margin: 15px;">
        email
      </v-icon>
    </v-btn>
    <div style="width: 100%; border-top: 1.3px solid rgba(0,0,0,.12); margin: 10px;"></div>
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