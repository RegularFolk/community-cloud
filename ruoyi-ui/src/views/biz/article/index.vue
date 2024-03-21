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

      <el-card style="margin-bottom: 20px" v-loading="viewRankLoading">
        <div slot="header">
          七天阅读排行
        </div>
        <article-rank :rank-list="viewRank"/>
      </el-card>

      <el-card style="margin-bottom: 20px">
        <div slot="header">
          七天收藏排行
        </div>
        <article-rank :rank-list="collectRank"/>
      </el-card>

      <el-card style="margin-bottom: 20px">
        <div slot="header">
          七天点赞排行
        </div>
        <article-rank :rank-list="likeRank"/>
      </el-card>


    </div>
  </div>


</template>


<script>

import ArticleCard from "@/components/ArticleCard";
import {getArticleCollectRank, getArticleLikeRank, getArticleViewRank} from "@/api/biz/article";
import ArticleRank from "@/views/biz/article/articleRank";

export default {
  name: 'ArticleSquare',
  dicts: [
    'blog_article_classification'
  ],
  components: {ArticleRank, ArticleCard},
  data() {
    return {
      articleClass: null,
      btnLoading: false,
      viewRank: [],
      viewRankLoading: false,
      likeRank: [],
      likeRankLoading: false,
      collectRank: [],
      collectRankLoading: false,
    }
  },
  created() {
    this.initRank()
  },
  methods: {
    // 初始化排行榜
    initRank() {
      // 初始化阅读排行榜
      this.getViewRank()

      // 初始化收藏排行榜
      this.getCollectRank()

      // 初始化点赞排行榜
      this.getLikeRank()
    },
    getLikeRank() {
      this.likeRankLoading = true
      getArticleLikeRank().then(resp =>{
        if (resp.code === 200) {
          this.likeRank = resp.data
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.likeRankLoading = false
      })
    },
    getCollectRank() {
      this.collectRankLoading = true
      getArticleCollectRank().then(resp => {
        if (resp.code === 200) {
          this.collectRank = resp.data
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.collectRankLoading = false
      })
    },
    getViewRank() {
      this.viewRankLoading = true
      getArticleViewRank().then(resp => {
        if (resp.code === 200) {
          this.viewRank = resp.data
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.viewRankLoading = false
      })
    },
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
