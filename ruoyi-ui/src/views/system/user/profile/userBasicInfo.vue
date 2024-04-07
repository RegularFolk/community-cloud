<template>
<!-- 用户基本信息，需要父组件注入user和$refs.userAvatar.options.img -->
  <div>
    <div class="text-center" v-if="!this.hoverShow">
      <userAvatar ref="userAvatar" :editable="user.id == loginUser.id"/>
    </div>
    <ul class="list-group list-group-striped">
      <li class="list-group-item">
        <svg-icon icon-class="user"/>
        用户名称
        <div class="pull-right">{{ user.nickName }}</div>
      </li>
      <li class="list-group-item">
        <i class="el-icon-user"/>
        性别
        <div class="pull-right">{{ user.sex === '0' ? '男' : user.sex === '1' ? '女' : '未知' }}</div>
      </li>
      <li class="list-group-item">
        <i class="el-icon-user"/>
        粉丝数
        <div class="pull-right">{{ user.followerCnt }}</div>
      </li>
      <li class="list-group-item">
        <i class="el-icon-user"/>
        点赞数
        <div class="pull-right">{{ user.likeCnt }}</div>
      </li>
      <li class="list-group-item">
        <i class="el-icon-user"/>
        关注数
        <div class="pull-right">{{ user.subCnt }}</div>
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

    <div v-if="user.id && user.id != $store.state.user.id" style="display: flex;justify-content: center">
      <el-tooltip v-if="!user.followed" class="item" content="点击以关注" effect="dark" placement="top">
        <el-button type="success" @click="subUser">关注</el-button>
      </el-tooltip>
      <el-tooltip v-if="user.followed" class="item" content="点击取消关注" effect="dark" placement="top">
        <el-button type="danger" @click="unSubUser">已关注</el-button>
      </el-tooltip>

      <el-button v-if="user.id != loginUser.id" type="success" @click="openDialogBox">私信</el-button>
    </div>

    <el-dialog
        :destroy-on-close="true"
        :visible.sync="showDialogBox"
        title="发送私信"
        append-to-body
    >
      <DialogBox v-if="showDialogBox" ref="dialogBox" :contact-id="user.id" @dialogClose="closeDialog"
                 @userFollowChanged="userFollowChanged"/>
    </el-dialog>
  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import {changeUserFollow, getBasicInfo} from "@/api/system/user";
import DialogBox from "@/components/DialogBox";

export default {
  name: 'UserBasicInfo',
  components: {userAvatar, DialogBox},
  props: {
    userInjected: {
      type: Object,
      default: function () {
        return undefined
      }
    },
    hoverShow: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      user: {
        followed: false
      },
      loginUser: {},
      followed: false,
      showDialogBox: false
    }
  },
  created() {
    if (this.userInjected) {
      this.user = this.userInjected
    } else {
      this.initLoginUser()
    }
  },
  methods: {
    // 初始化loginUser
    initLoginUser() {
      getBasicInfo({id: this.$store.state.user.id}).then(resp => {

        this.loginUser = resp.data;
      })
    },
    // 关注用户
    subUser() {
      let dto = {
        followId: this.user.id,
        operateType: 1
      }
      this.submitSub(dto)
    },
    // 取消关注
    unSubUser() {
      this.$confirm('确定要取消关注吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let dto = {
          followId: this.user.id,
          operateType: 2
        }
        this.submitSub(dto)
      })

    },
    // 提交关注请求
    submitSub(dto) {
      changeUserFollow(dto).then(resp => {
        if (resp.code === 200) {
          this.$message({
            message: '操作成功！',
            type: 'success'
          })
          this.user.followed = !this.user.followed
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })
    },
    // 打开私信对话框
    openDialogBox() {
      console.log('this.user.id', this.user.id)
      this.showDialogBox = true
    },
    // 关闭私信对话框
    closeDialog() {
      this.showDialogBox = false
    },
    userFollowChanged(followed) {
      this.user.followed = followed
    },
  },
  watch: {
    userInjected: {
      handler(nVal) {
        this.user = this.userInjected
      }
    }
  }
}


</script>
