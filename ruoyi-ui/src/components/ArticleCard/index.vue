<template>

  <div>
    <!-- 随笔列表 -->
    <el-card v-for="article in articleList" :key="article.id" :loading="cardLoading" shadow="hover"
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
          <div slot="reference" style="cursor: pointer" @click="routeToProfile(article.author.id)">
            <el-avatar :src="article.author.avatar"/>
          </div>

          <UserBasicInfo :hover-show="true" :userInjected="article.author"/>
        </el-popover>

        <div>
          {{ article.preview }}
        </div>

      </div>

      <div style="display: flex">
        <div>{{ article.author.nickName }}</div>
        <div>{{ article.publishTime }}</div>

        <div>
          <i class="el-icon-chat-line-square">{{ article.commentCnt }}</i>
        </div>

        <div>
          <i class="el-icon-thumb">{{ article.likeCnt }}</i>
        </div>

        <div>
          <i class="el-icon-star-on">{{ article.collectCnt }}</i>
        </div>

        <div>
          <i class="el-icon-view">{{ article.viewCnt }}</i>
        </div>
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
  props: {
    articleClassification: {
      type: Number,
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
      })
    }
  },
  watch: {}
}


</script>
