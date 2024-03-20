<template>

  <div class="app-container" style="display: flex;justify-content: space-between">

    <div>
      <keep-alive>
        <ArticleCard ref="articleCard" :articleClassification="articleClass" @listReloadFinish="listReloadFinish"/>
      </keep-alive>
    </div>

    <!-- 右侧热榜 -->
    <div style="width: 500px;margin-right: 50px;margin-top: 20px">

      <div style="display: flex;justify-content: space-between">
        <el-select v-model="articleClass" v-loading="btnLoading" clearable placeholder="选择分类"
                   style="margin-bottom: 20px" @change="articleClassChange">
          <el-option
            v-for="dict in dict.type.blog_article_classification"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"/>
        </el-select>

        <div>
          <el-button v-loading="btnLoading" icon="el-icon-refresh" type="danger" @click="reload">换一批</el-button>
        </div>
      </div>

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
  name: 'ArticleSquare',
  dicts: [
    'blog_article_classification'
  ],
  components: {ArticleCard},
  data() {
    return {
      articleClass: null,
      btnLoading: false
    }
  },
  created() {
  },
  methods: {
    // 选中文章分类
    articleClassChange(val) {
      this.btnLoading = true
      this.articleClass = val
    },
    // 随笔列表重新加载完成
    listReloadFinish() {
      console.log('列表子组件加载完成！')
      this.btnLoading = false
    },
    // 换一批
    reload() {
      this.btnLoading = true
      this.$refs.articleCard.getSquareList()
    }
  }
}


</script>
