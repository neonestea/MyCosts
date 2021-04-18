<template>

  <div>

    <v-toolbar
        dark
        prominent
        src="https://www.roberthalf.com/sites/default/files/2018-07/Compare-Salaries-FA-07-12-2018.jpg"
    >
      <v-card id="menu"
              class="mx-auto"
              tile
              light
              style="z-index: 9999; position: fixed; margin-left: 40px; visibility: hidden;"
      >
        <v-list shaped>
          <v-subheader>MENU</v-subheader>
          <v-list-item-group>
            <v-list-item href="/">
              <v-list-item-content>
                <v-list-item-title>Main</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item href="/home">
              <v-list-item-content>
                <v-list-item-title>Costs</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item href="/accounts">
              <v-list-item-content>
                <v-list-item-title >Accounts</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item href="/categories">
              <v-list-item-content>
                <v-list-item-title>Categories</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-card>
      <v-btn icon @click="switchMenu()">
        <v-icon>menu</v-icon>
      </v-btn>

      <v-toolbar-title>MyCosts</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon v-if="!profile"href="/login">
        <v-icon>login</v-icon>
      </v-btn>

      <span v-if="profile" style="padding-top: 15px;">{{profile.name}}</span>
      <v-btn v-if="profile" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-toolbar>
  </div>

</template>
<script>
export default {
  data() {
    return {

      profile: frontendData.profile
    }
  },
  methods: {
    switchMenu(){
      const menu = document.getElementById('menu');
      if (menu.style.visibility == 'hidden'){
        menu.style.visibility = 'visible';
      }
      else {
        menu.style.visibility = 'hidden';
      }
    }
  }

}
</script>
<style>

</style>