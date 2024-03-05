<template>
  <div>
    <div class="text-center">
      <userAvatar ref="userAvatar" :editable="user.userId == loginUser.userId"/>
    </div>
    <ul class="list-group list-group-striped">
      <li class="list-group-item">
        <svg-icon icon-class="user"/>
        用户名称
        <div class="pull-right">{{ user.userName }}</div>
      </li>
      <li class="list-group-item">
        <svg-icon icon-class="phone"/>
        手机号码
        <div class="pull-right">{{ user.phonenumber }}</div>
      </li>
      <li class="list-group-item">
        <svg-icon icon-class="email"/>
        用户邮箱
        <div class="pull-right">{{ user.email }}</div>
      </li>
      <li class="list-group-item">
        <svg-icon icon-class="date"/>
        创建日期
        <div class="pull-right">{{ user.createTime }}</div>
      </li>
    </ul>
  </div>
</template>

<script>
import userAvatar from "./userAvatar";

export default {
  name: 'UserBasicInfo',
  components: {userAvatar},
  data() {
    return {
      user: {},
      loginUser: {}
    }
  },
  methods: {}
}


</script>
