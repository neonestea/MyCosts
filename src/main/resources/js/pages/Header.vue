<template>
  <v-card class="overflow-hidden" style="height: 220px; background: #F4F5F5;">
    <v-app-bar
        absolute
        color="#6A76AB"
        dark
        shrink-on-scroll
        prominent
        src="https://www.roberthalf.com/sites/default/files/2018-07/Compare-Salaries-FA-07-12-2018.jpg"
        fade-img-on-scroll
        scroll-target="#scrolling-techniques-3"
        style=" background: #F4F5F5;"

    >
      <template v-slot:img="{ props }">
        <v-img
            v-bind="props"
            gradient="to top right, rgba(100,115,201,.7), rgba(25,32,72,.7)"
        ></v-img>
      </template>


      <v-app-bar-title>MyCosts</v-app-bar-title>

      <v-spacer></v-spacer>

      <v-btn icon v-if="!profile"href="/login">
        <v-icon>login</v-icon>
      </v-btn>
      <div v-if="profile" style="cursor:default; font-size: 20px; padding-top: 10px;">{{profile.name}}</div>

      <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
              </v-btn>

      <template v-slot:extension>
        <v-row align-with-title>
          <v-btn
                      color="white"
                      text
                      tile
                      class="my-2"
                      href="/"
                      id="MainBtn"
                  >
                    Main
                  </v-btn>
                  <v-btn
                      color="white"
                      text
                      tile
                      class="my-2"
                      href="/home"
                      id="CostsBtn"
                  >
                    Costs
                  </v-btn>
                  <v-btn
                      color="white"
                      text
                      tile
                      class="my-2"
                      href="/stat"
                      id="StatisticsBtn"
                  >
                    Statistics
                  </v-btn>
                  <v-btn
                      color="white"
                      text
                      tile
                      class="my-2"
                      href="/regular"
                      id="RegularCostsBtn"
                  >
                    Regular Costs
                  </v-btn>

                  <v-btn
                      color="white"
                      text
                      tile
                      class="my-2"
                      href="/accounts"
                      id="AccountsBtn"
                  >
                    Accounts
                  </v-btn>
                  <v-btn
                      color="white"
                      text
                      tile
                      class="my-2"
                      href="/categories"
                      id="CategoriesBtn"
                  >
                    Categories
                  </v-btn>
        </v-row>
      </template>
    </v-app-bar>
    <v-sheet
        id="scrolling-techniques-3"
        class="overflow-y-auto"
        max-height="600"
        style="background: #F4F5F5;"
    >

    </v-sheet>
  </v-card>
</template>
<script>
export default {
  data() {
    return {

      profile: frontendData.profile
    }
  },
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