<template>
  <div>

    <div v-for="article in articleList" :key="article.articleId" class="blog-row">
      <div style="display: flex;justify-content: space-between">
        <div>
          <el-button class="blog-content" type="text" @click="routeToDetail(article.articleId)">
            {{ article.title }}
          </el-button>
        </div>

        <div style="display: flex">

          <el-tooltip class="item" content="发布时间" effect="dark" placement="top">
            <div class="article-info">
              <i class="el-icon-time">
                {{ article.publishTime }}
              </i>
            </div>
          </el-tooltip>

          <el-tooltip class="item" content="浏览量" effect="dark" placement="top">
            <div class="article-info">
              <i class="el-icon-view">
                {{ article.viewCnt }}
              </i>
            </div>
          </el-tooltip>

          <el-tooltip class="item" content="点赞数" effect="dark" placement="top">
            <div class="article-info">
              <i class="el-icon-thumb">
                {{ article.likeCnt }}
              </i>
            </div>
          </el-tooltip>

          <el-tooltip class="item" content="评论数" effect="dark" placement="top">
            <div class="article-info">
              <i class="el-icon-chat-dot-square">
                {{ article.commentCnt }}
              </i>
            </div>
          </el-tooltip>

          <el-tooltip class="item" content="收藏数" effect="dark" placement="top">
            <div class="article-info">
              <i class="el-icon-star-on">
                {{ article.collectCnt }}
              </i>
            </div>
          </el-tooltip>

        </div>


      </div>


    </div>

    <pagination v-if="total > 0" :limit.sync="pageSize" :page.sync="pageNum" :total="total"
                @pagination="handleQuery"/>
  </div>
</template>


<script>

import {getPersonArticle} from "@/api/biz/article"

export default {
  name: 'ArticleReleased',
  data() {
    return {
      articleList: [],
      pageNum: 1,
      pageSize: 10,
      userId: 0,
      total: 0,
    }
  },
  created() {
  },
  methods: {
    // 分页查询方法
    handleQuery() {
      let dto = {
        userId: this.userId,
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }
      getPersonArticle(dto).then(resp => {
        if (resp.code === 200) {
          this.total = resp.data.total
          this.articleList = resp.data.list

        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })
    },
    // 路由跳转到随笔详情页
    routeToDetail(articleId) {
      this.$router.push({
        path: '/article/browse',
        query: {
          articleId: articleId
        }
      })
    }
  }
}


</script>

<style>
/* 博客列表项样式 */
.blog-row {
  margin: 10px;
}


/* 博客列表内部样式 */
.blog-content {
  font-size: medium;
}

/* 随笔右侧样式 */
.article-info {
  margin-left: 30px;
}

</style>
