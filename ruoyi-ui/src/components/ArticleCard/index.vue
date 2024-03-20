<template>

  <div v-loading="cardLoading" style="margin: 20px;width: 1500px">
    <!-- 随笔列表 -->
    <el-card v-for="article in articleList" :key="article.id" shadow="hover"
             style="margin: 20px;width: 1500px">
      <div slot="header">
        <el-link type="primary" @click="routeToArticle(article.articleId)">
          {{ article.title }}
        </el-link>
      </div>

      <div style="display: flex">
        <el-popover
          placement="right"
          trigger="hover"
          width="400">
          <div slot="reference"
               style="cursor: pointer;display: flex;justify-content: center;margin-right: 15px"
               @click="routeToProfile(article.author.id)">
            <el-avatar :src="article.author.avatar"/>
          </div>

          <UserBasicInfo :hover-show="true" :userInjected="article.author"/>
        </el-popover>

        <div>
          {{ article.preview }}......
        </div>

      </div>

      <div style="display: flex;margin-top: 20px;justify-content: space-between">
        <div style="display: flex">
          <div class="card-bottom">{{ article.author.nickName }}</div>
          <div class="card-bottom">{{ article.publishTime }}</div>

          <div class="card-bottom">
            <i class="el-icon-chat-line-square">{{ article.commentCnt }}</i>
          </div>

          <div class="card-bottom">
            <i class="el-icon-thumb">{{ article.likeCnt }}</i>
          </div>

          <div class="card-bottom">
            <i class="el-icon-star-on">{{ article.collectCnt }}</i>
          </div>

          <div class="card-bottom">
            <i class="el-icon-view">{{ article.viewCnt }}</i>
          </div>
        </div>

        <template>
          <dict-tag :options="dict.type.blog_article_classification" :value="article.articleClassification"/>
        </template>
      </div>

    </el-card>


  </div>

</template>


<script>

import {squareList} from "@/api/biz/article";
import UserBasicInfo from "@/views/system/user/profile/userBasicInfo";

export default {
  name: 'ArticleCard',
  components: {UserBasicInfo},
  dicts: [
    'blog_article_classification'
  ],
  props: {
    articleClassification: {
      type: String,
      default: 0
    }
  },
  data() {
    return {
      userId: 0,
      pageSize: 8,
      cardLoading: false,
      articleList: []
    }
  },
  created() {
    this.userId = this.$store.state.user.id
    this.getSquareList()
  },
  methods: {
    // 跳转到随笔主页
    routeToArticle(articleId) {
      this.$router.push({
        path: '/article/browse',
        query: {
          articleId: articleId
        }
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
    },
    // 查询随笔列表
    getSquareList() {
      let dto = {
        pageSize: this.pageSize,
        articleClassification: this.articleClassification
      }
      this.cardLoading = true
      squareList(dto).then(resp => {
        if (resp.code === 200) {
          this.articleList = resp.data
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.cardLoading = false
        this.$emit('listReloadFinish')
      })
    }
  },
  watch: {
    articleClassification: {
      handler(nVal) {
        this.getSquareList()
      }
    }
  }
}


</script>

<style>
.card-bottom {
  margin-right: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}


</style>
