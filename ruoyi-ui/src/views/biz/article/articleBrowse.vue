<template>
  <div v-loading="loading" class="app-container">

    <div style="font-size: 40px">{{this.article.title}}</div>

    <div class="flexClass sub-title" style="justify-content: space-between">
      <div class="flexClass">
        <div class="sub-title-item">
          <i class="el-icon-user" style="padding-left: 10px"> {{ this.article.senderName }}</i>
        </div>
        <div class="sub-title-item" v-if="article.publishTime"><i class="el-icon-timer"> 于 {{ this.article.publishTime }} 发布</i></div>
      </div>

      <div class="flexClass" style="margin-right: 40px">
        <div class="sub-title-item"><i class="el-icon-view"> 浏览:{{ this.article.viewCnt }}</i></div>
        <div class="sub-title-item"><i class="el-icon-thumb"> 点赞:{{ this.article.likeCnt }}</i></div>
        <div class="sub-title-item"><i class="el-icon-chat-line-round"> 评论:{{ this.article.commentCnt }}</i>
        </div>
      </div>

    </div>

    <div class="ql-container ql-snow">
      <div class="ql-editor" v-html="article.htmlValue"/>
    </div>

  </div>

</template>

<script>
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';

import {getArticle} from "@/api/biz/article";

export default {
  name: 'articleBrowse',
  data() {
    return {
      loading: true,
      article: {
        articleId: undefined,
        htmlValue: undefined,
        title: undefined,
        senderName: undefined,
        publishTime: undefined,
        articleClassify: undefined,
        viewCnt: undefined,
        likeCnt: undefined,
        commentCnt: undefined
      }
    }
  },
  activated() {
    // 处理路由跳转不刷新的问题
    if (this.$route.query.articleId !== this.articleId) {
      this.loading = true
      this.init()

      this.loading = false
    }
  },
  created() {
    this.loading = true
    this.init()

    this.loading = false
  },
  methods: {
    init() {
      this.initArticle()
    },
    // 初始化随笔
    initArticle() {
      this.article.articleId = this.$route.query.articleId

      getArticle(this.article.articleId).then(resp => {
        if (resp.code === 200) {
          this.article.htmlValue = resp.data.contentHtml
          this.article.title = resp.data.title
          this.article.senderName = resp.data.senderName
          this.article.publishTime = resp.data.publishTime
          this.article.articleClassify = resp.data.articleClassify
          this.article.viewCnt = resp.data.viewCnt
          this.article.likeCnt = resp.data.likeCnt
          this.article.commentCnt = resp.data.commentCnt


        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })
    }
  }

}


</script>

<style>
.sub-title-item {
  margin-right: 15px;
}

/* 设置全局样式为flex */
.flexClass {
  display: flex;
  flex-wrap: wrap;
}

.sub-title {
  margin-top: 5px;
  padding-top: 10px;
  padding-bottom: 10px;
  margin-bottom: 10px;
  background-color: #EEF1F6FF
}
</style>
