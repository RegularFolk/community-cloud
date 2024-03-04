<template>

  <div>
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
              <el-button v-if="user.followed" round type="info">取消关注</el-button>
              <el-button v-if="!user.followed" round type="danger">关注</el-button>
              <el-button round type="primary">私信</el-button>
            </div>
            <el-button v-if="user.followed" slot="reference" type="success">
              <i class="el-icon-check">已关注</i>
            </el-button>
            <el-button v-if="!user.followed" slot="reference" type="danger">
              <i class="el-icon-s-opportunity">关注</i>
            </el-button>
          </el-popover>
        </div>
      </div>

    </div>

  </div>

</template>

<script>

export default {
  name: 'UserList',
  data() {
    return {
      userList: [
        {
          userId: 1,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/2b_20240202165949A006.jpg',
          userName: 'xxx',
          followed: true,
          followerCnt: 10,
          subCnt: 19,
          like_cnt: 20
        }, {
          userId: 2,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          userName: 'xxx',
          followed: false,
          followerCnt: 10,
          subCnt: 19,
          like_cnt: 20
        }
      ]
    }
  },
  created() {
  },
  methods: {
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
