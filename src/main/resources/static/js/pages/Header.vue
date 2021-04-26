<template>

  <div>
    <v-toolbar
        dark
        prominent
        src="https://www.roberthalf.com/sites/default/files/2018-07/Compare-Salaries-FA-07-12-2018.jpg"
    >
            <v-toolbar-title class="titleText">MyCosts</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon v-if="!profile"href="/login">
        <v-icon>login</v-icon>
      </v-btn>

      <span v-if="profile" style="padding-top: 15px; cursor:default;">{{profile.name}}</span>
      <v-btn v-if="profile" icon href="/logout">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-toolbar>
    <div style="background: rgba(205, 214, 224, 0.4); margin-bottom: 20px;">
      <v-btn
          color="white"
          text
          rounded
          class="my-2"
          href="/"
      >
        Main
      </v-btn>
      <v-btn
          color="white"
          text
          rounded
          class="my-2"
          href="/home"
      >
        Costs
      </v-btn>
      <v-btn
          color="white"
          text
          rounded
          class="my-2"
          href="/stat"
      >
        Month statistics
      </v-btn>
      <v-btn
          color="white"
          text
          rounded
          class="my-2"
          href="/regular"
      >
        Regular Costs
      </v-btn>

      <v-btn
          color="white"
          text
          rounded
          class="my-2"
          href="/accounts"
      >
        Accounts
      </v-btn>
      <v-btn
          color="white"
          text
          rounded
          class="my-2"
          href="/categories"
      >
        Categories
      </v-btn>

    </div>
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
.titleText {
  margin: 1em 0 0.5em 0;
  color: #FFFFFF;
  font-weight: normal;
  font-size: 36px;
  line-height: 42px;
  text-transform: uppercase;
  text-shadow: 0 2px #3A4C62, 0 3px #777;
  cursor: default;
}
</style>