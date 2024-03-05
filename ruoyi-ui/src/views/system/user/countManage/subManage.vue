<template>
  <div class="app-container" v-loading="followLoading">

    <el-header style="border-bottom: 1px solid #EEF1F6FF">
      <h1> 全部关注 </h1>
    </el-header>

    <el-main>

      <h1 v-if="total === 0">你还没有关注任何人哦</h1>

      <UserList ref="userList" @refreshList="handlePage"/>
    </el-main>

    <el-footer>
      <Pagination v-if="total !== 0" :total="total" :page="pageNum" :limit="pageSize" @pagination="handlePage"/>
    </el-footer>

  </div>

</template>

<script>

import UserList from "@/components/UserList";
import {followList, subList} from "@/api/system/user";

export default {
  name: 'SubManage',
  components: {UserList},
  data() {
    return {
      pageNum: 1,
      pageSize: 10,
      total: 100,
      followLoading: false,
      userList: []
    }
  },
  created() {
    this.handlePage()
  },
  methods: {
    // 分页方法
    handlePage() {
      this.followLoading = true
      let dto = {
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }
      subList(dto).then(resp => {
        if (resp.code === 200) {
          this.total = resp.total
          this.userList = resp.data

          this.$refs.userList.userList = this.userList

        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.followLoading = false
      })
    }
  }
}



</script>

