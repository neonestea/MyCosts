<template>
  <div>
    <div v-if="!profile"><a href="/login" class="navEl">SignIn</a></div>
    <div v-else><a href="/logout" class="navEl">SignOut</a></div>
    <div v-if="profile">{{ profile.name }}</div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      profile: frontendData.profile
    }
  }
}
</script>
<style>

</style>