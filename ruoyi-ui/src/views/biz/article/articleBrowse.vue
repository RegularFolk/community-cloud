<template>
  <div v-loading="loading" class="app-container">

    <!-- 标题 -->
    <div class="flexClass" style="justify-content: space-between">
      <div style="font-size: 40px">{{ this.article.title }}</div>
      <el-button round style="margin-right: 10px" type="danger">关注作者</el-button>
    </div>


    <!-- 灰色副标题 -->
    <div class="flexClass sub-title" style="justify-content: space-between">
      <div class="flexClass">
        <div class="sub-title-item">
          <i class="el-icon-user" style="padding-left: 10px"> {{ this.article.senderName }}</i>
        </div>
        <div v-if="article.publishTime" class="sub-title-item"><i class="el-icon-timer"> 于 {{
            this.article.publishTime
          }} 发布</i></div>
      </div>

      <div class="flexClass" style="margin-right: 40px">
        <div class="sub-title-item"><i class="el-icon-view"> 浏览:{{ this.article.viewCnt }}</i></div>
        <div class="sub-title-item"><i class="el-icon-thumb"> 点赞:{{ this.article.likeCnt }}</i></div>
        <div class="sub-title-item"><i class="el-icon-chat-line-round"> 评论:{{ this.article.commentCnt }}</i>
        </div>
      </div>

    </div>

    <!-- 正文内容 -->
    <div class="ql-container ql-snow">
      <div class="ql-editor" v-html="article.htmlValue"/>
    </div>

    <!-- 正文下方操作按钮 -->
    <div class="flexClass" style="justify-content: flex-end;margin: 10px">
      <el-tooltip class="item" content="点个赞吧~" effect="dark" placement="top">
        <el-button circle icon="el-icon-thumb" type="danger"/>
      </el-tooltip>

      <el-tooltip class="item" content="收藏一下~" effect="dark" placement="top">
        <el-button circle icon="el-icon-star-off" type="warning"/>
      </el-tooltip>

    </div>


    <BlogComment :article-id="article.articleId"/>

  </div>

</template>

<script>
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';

import {getArticle} from "@/api/biz/article";
import BlogComment from "@/components/BlogComment";

export default {
  name: 'articleBrowse',
  components: {BlogComment},
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
    if (this.$route.query.articleId !== this.article.articleId) {
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
  margin-top: 10px;
  padding-top: 10px;
  padding-bottom: 10px;
  margin-bottom: 10px;
  background-color: #EEF1F6FF
}
</style>
