<template>

  <div>
    <div v-for="(item, index) in rankList" :key="item.blogId" class="rank-item">
      <el-button type="text" @click="routeToArticle(item.blogId)">{{index + 1}}：{{item.title}}</el-button>
    </div>

    <div v-if="rankList.length === 0">
      <h3>暂无内容</h3>
    </div>

  </div>


</template>

<script>

export default {
  name: 'ArticleRank',
  props: {
    rankList: {
      type: Array,
      default: []
    }
  },
  data() {
    return {}
  },
  created() {
  },
  methods: {
    // 路由到文章详情
    routeToArticle(blogId) {
      this.$router.push({
        path: '/article/browse',
        query: {
          articleId: blogId
        }
      })
    }
  }
}


</script>

<style>

.rank-item {
  border-bottom: 2px solid #EEF1F6FF;
}



</style>
