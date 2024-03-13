<template>

  <div class="app-container" style="display: flex;justify-content: space-between">

    <div>
      <ArticleCard/>
    </div>

    <!-- 右侧热榜 -->
    <div style="width: 500px;margin-right: 50px">
      <el-card style="margin-bottom: 20px">
        <div slot="header">
          七天阅读排行
        </div>
      </el-card>

      <el-card style="margin-bottom: 20px">
        <div slot="header">
          七天评论排行
        </div>
      </el-card>

      <el-card style="margin-bottom: 20px">
        <div slot="header">
          七天点赞排行
        </div>
      </el-card>


    </div>
  </div>


</template>


<script>

import ArticleCard from "@/components/ArticleCard";

export default {
  name: 'Article',
  components: {ArticleCard},
  data() {
    return {}
  },
  created() {
  },
  methods: {}
}


</script>
