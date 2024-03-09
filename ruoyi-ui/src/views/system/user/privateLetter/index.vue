<template>

  <div class="app-container" style="display: flex">

    <contact-list ref="contactList" style="width: 30%" @selectContact="selectContact"/>

    <div v-if="contactId === 0" style="display: flex;justify-content: center;align-items: center;margin-left: 20%;">
      <h1> 请选择联系人 </h1>
    </div>

    <DialogBox v-if="contactId !== 0" :key="contactId" ref="dialogBox" :contact-id="contactId" style="width: 60%;"
               @msgSent="msgSent"/>


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
      contactId: 0
    }
  },
  created() {
  },
  methods: {
    // 发送了消息
    msgSent(msg) {
      this.$refs.contactList.contactList.find(c => c.contactId == this.contactId).latestMsgPreview = msg.substr(0, 15) + '......'
    },
    // 选中了一个联系人
    selectContact(contactId) {
      this.contactId = contactId
    }
  }
}


</script>
