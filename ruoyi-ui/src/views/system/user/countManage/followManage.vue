<template>
  <div class="app-container" v-loading="subLoading">

    <el-header style="border-bottom: 1px solid #EEF1F6FF">
      <h1> 全部粉丝 </h1>
    </el-header>

    <el-main>

      <h1 v-if="total === 0">你还没有粉丝哦</h1>

      <UserList ref="userList" @refreshList="handlePage"/>
    </el-main>

    <el-footer>
      <Pagination v-if="total !== 0" :limit="pageSize" :page="pageNum" :total="total" @pagination="handlePage"/>
    </el-footer>

  </div>
</template>

<script>

import UserList from "@/components/UserList";
import {followList} from "@/api/system/user";

export default {
  name: 'FollowManage',
  components: {UserList},
  data() {
    return {
      total: 100,
      pageNum: 1,
      pageSize: 10,
      subLoading: false,
      userList: []
    }
  },
  created() {
    this.handlePage()
  },
  methods: {
    // 分页查询方法
    handlePage() {
      this.subLoading = true
      let dto = {
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }
      followList(dto).then(resp => {
        if (resp.code === 200) {
          this.userList = resp.data
          this.total = resp.total

          this.$refs.userList.userList = this.userList


        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.subLoading = false
      })
    }
  }
}


</script>

