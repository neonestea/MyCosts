<template>
  <v-app style="background: #F4F5F5;">
    <v-tabs style="max-height: 50px; ">
      <v-tab @click="showDescription();">About</v-tab>
      <v-tab @click="showSteppers();" >Hints</v-tab>
      <v-tab v-if="profile" @click="showAgreement();">User agreement</v-tab>
    </v-tabs>
    <div v-if="profile && !agreement">
      <v-alert
          v-model="alert"
          border="left"
          close-text="Close Alert"
          color="red lighten-2"
          dark
          dismissible

      >
        To get access to the application please accept User Agreement.
      </v-alert>
    </div>
    <div id="about" style="margin-top: 50px;">
    <v-expansion-panels
        multiple
    >
      <v-expansion-panel>
        <v-expansion-panel-header>Managing your finances</v-expansion-panel-header>
        <v-expansion-panel-content>
          Our app helps you manage your outcomes easily.
        </v-expansion-panel-content>
      </v-expansion-panel>
      <v-expansion-panel>
        <v-expansion-panel-header>Categories</v-expansion-panel-header>
        <v-expansion-panel-content>
          You can use default categories or create your own ones.
        </v-expansion-panel-content>
      </v-expansion-panel>

      <v-expansion-panel>
        <v-expansion-panel-header>Accounts</v-expansion-panel-header>
        <v-expansion-panel-content>
          You are able to manage different accounts with different currencies.
        </v-expansion-panel-content>
      </v-expansion-panel>
      <v-expansion-panel>
        <v-expansion-panel-header>Costs</v-expansion-panel-header>
        <v-expansion-panel-content>
          Besides normal costs you can create regular costs to avoid adding them every time.
        </v-expansion-panel-content>
      </v-expansion-panel>
      <v-expansion-panel>
        <v-expansion-panel-header>Statistics</v-expansion-panel-header>
        <v-expansion-panel-content>
          You can check your month, year and average statistics and ret monthly reports.
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </div>
    <v-stepper
        v-model="e6"
        vertical
        id="stepper"
        style="display: none;"
    >
      <v-stepper-step
          :complete="e6 > 1"
          step="1"
      >
        Register in our app or log in if you already have an account.
      </v-stepper-step>

      <v-stepper-content step="1">
        <small>Click the logIn button in the top right corner of the app.</small>
        <v-img
            max-width="550"
            src="/images/step0.jpg"
        ></v-img>
        <v-btn
            color="primary"
            @click="e6 = 2"
        >
          Next
        </v-btn>
      </v-stepper-content>

      <v-stepper-step
          :complete="e6 > 2"
          step="2"
      >
        Manage your accounts in "Accounts" tab.

      </v-stepper-step>
      <v-stepper-content step="2">
        <small>Add, edit or remove your accounts.</small>
        <v-img
            max-height="150"
            max-width="250"
            src="/images/step1.jpg"
        ></v-img>
        <v-btn
            color="primary"
            @click="e6 = 3"
        >
          Next
        </v-btn>
        <v-btn text
               @click="e6 = 2">
          Previous
        </v-btn>
      </v-stepper-content>
      <v-stepper-step
          :complete="e6 > 3"
          step="3"
      >
        Manage your categories in "Categories" tab.
      </v-stepper-step>
      <v-stepper-content step="3">
        <small>You can use our default categories or add yours.</small>
        <v-img
            max-height="150"
            max-width="250"
            src="/images/step2.jpg"
        ></v-img>
        <v-btn
            color="primary"
            @click="e6 = 4"
        >
          Next
        </v-btn>
        <v-btn text
               @click="e6 = 3">
          Previous
        </v-btn>
      </v-stepper-content>
      <v-stepper-step step="4">
        Start adding your costs and regular costs.
      </v-stepper-step>
      <v-stepper-content step="4">
        <small>Check "Costs" and "Regular costs" tab to manage your costs.</small>
        <v-img
            max-height="150"
            max-width="450"
            src="/images/step3-1.jpg"
        ></v-img>
        <v-btn
            color="primary"
            @click="e6 = 5"
        >
          Next
        </v-btn>
        <v-btn text
               @click="e6 = 4">
          Previous
        </v-btn>
      </v-stepper-content>
      <v-stepper-step step="5">
        See your statistics in "Statistics" tab.
      </v-stepper-step>
      <v-stepper-content step="5">
        <small>We will also send you monthly reports by email.</small>
        <v-img
            max-height="150"
            max-width="250"
            src="/images/step3-2.jpg"
        ></v-img>
        <v-btn text
               @click="e6 = 1">
          Return
        </v-btn>
      </v-stepper-content>
    </v-stepper>
    <v-card class="text-center" style="width: 70%; margin: 10px auto; display: none;" id="agreement">
      <v-card-title>MyCosts user agreement</v-card-title>
      <v-card-text>Here are the points of user agreement.</v-card-text>
      <v-btn
          id="agreeBtn"
          class="ma-2 text-center"
          @click="agree"
          v-if="!agreement"
      >
        Accept Terms
      </v-btn>
    </v-card>
  </v-app>
</template>

<script>


export default {
  data () {
    return {
      e6: 1,
      profile: frontendData.profile,
      agreement: frontendData.profile.accepted,
    }
  },
  methods: {
    agree(){
      this.$resource('/accept').update().then(result => {
            $("#agreeBtn").hide()
            location.reload()
          }
      );
    },
    showSteppers(){
      $("#stepper").show();
      $("#about").hide();
      $("#agreement").hide();
    },
    showDescription(){
      $("#stepper").hide();
      $("#agreement").hide();
      $("#about").show();
    },
    showAgreement(){
      $("#agreement").show();
      $("#stepper").hide();
      $("#about").hide();
    }
  }
}
</script>

<style scoped>

</style>