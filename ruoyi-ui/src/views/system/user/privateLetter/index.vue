<template>

  <div class="app-container" style="display: flex">

    <contact-list style="width: 30%"/>


    <DialogBox style="width: 60%;"/>


  </div>

</template>


<script>

import DialogBox from "@/components/DialogBox";
import ContactList from "@/views/system/user/privateLetter/contactList";
export default {
  name: 'PrivateLetter',
  components: {ContactList, DialogBox},
  data() {
    return {

    }
  },
  created() {
  },
  methods: {

  }
}


</script>
