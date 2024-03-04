<template>
  <div class="app-container">

    <el-header style="border-bottom: 1px solid #EEF1F6FF">
      <h1> 全部粉丝 </h1>
    </el-header>

    <el-main>
      <UserList/>
    </el-main>

    <el-footer>
      <Pagination :limit="pageSize" :page="pageNum" :total="total" @pagination="handlePage"/>
    </el-footer>

  </div>
</template>

<script>

import UserList from "@/components/UserList";

export default {
  name: 'FollowManage',
  components: {UserList},
  data() {
    return {
      total: 100,
      pageNum: 1,
      pageSize: 10
    }
  },
  created() {
  },
  methods: {
    // 分页查询方法
    handlePage() {

    }
  }
}


</script>

