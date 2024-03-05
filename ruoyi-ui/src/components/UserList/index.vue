<template>

  <div v-loading="userListLoading">
    <div v-for="user in userList" :key="user.userId"
         style="display: flex;padding: 10px;margin: 10px;border-bottom: 1px solid #EEF1F6FF">
      <div style="margin-right: 20px;cursor: pointer" @click="routeToProfile(user.userId)">
        <el-avatar :size="60" :src="user.avatar"/>
      </div>

      <div style="display: flex;justify-content: space-between;width: 90%">
        <div>
          {{ user.userName }}
        </div>

        <div>
          <el-popover
            placement="left"
            trigger="hover"
          >
            <div style="display: flex">
              <el-button v-if="user.followed" round type="info" @click="unSubUser(user.userId)">取消关注</el-button>
              <el-button v-if="!user.followed" round type="danger" @click="subUser(user.userId)">关注</el-button>
              <el-button round type="primary">私信</el-button>
            </div>
            <el-button v-if="user.followed" slot="reference" type="success">
              <i class="el-icon-check">已关注</i>
            </el-button>
            <el-button v-if="!user.followed" slot="reference" type="danger">
              <i class="el-icon-s-opportunity">未关注</i>
            </el-button>
          </el-popover>
        </div>
      </div>

    </div>

  </div>

</template>

<script>

import {changeUserFollow} from "@/api/system/user";

export default {
  name: 'UserList',
  data() {
    return {
      userListLoading: false,
      userList: []
    }
  },
  created() {
  },
  methods: {
    // 关注用户
    subUser(userId) {
      let dto = {
        followId: userId,
        operateType: 1
      }

      this.submitChangeFollow(dto)
    },
    // 取消关注用户
    unSubUser(userId) {
      this.$confirm('确定要取消关注吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let dto = {
          followId: userId,
          operateType: 2
        }

        this.submitChangeFollow(dto)


      })
    },
    // 提交改变关注状态请求
    submitChangeFollow(dto) {
      this.userListLoading = true
      changeUserFollow(dto).then(resp => {
        if (resp.code === 200) {
          this.$message({
            message: '操作成功！',
            type: 'success'
          })

          this.$emit('refreshList')

        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.userListLoading = false
      })
    },
    // 跳转到用户主页
    routeToProfile(userId) {
      this.$router.push({
        path: '/user/profile',
        query: {
          userId: userId
        }
      })
    }
  }
}


</script>
