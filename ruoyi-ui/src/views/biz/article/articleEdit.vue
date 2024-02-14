<template>
  <div style="padding-left: 10px;padding-right: 20px" v-loading="loading">
    <h1 style="width: 100%;">{{this.pageTitle}}</h1>

    <!-- 输入标题部分 -->
    <div style="display: flex">
      <div style="padding-right: 10px">输入标题:</div>
      <el-input
        v-model="article.title"
        :rows="2"
        :show-word-limit="true"
        maxlength="30"
        placeholder="请输入标题"
        resize="none"
        style="width: 30%;"
      />
    </div>

    <!-- 输入部分 -->
    <div style="margin-top: 20px">

      <div class="flexClass" style="justify-content: space-between">
        <h3>内容编辑</h3>
        <h3>当前字数：{{this.curInputCnt}} / {{this.editorLimit}}</h3>
      </div>

      <Editor ref="input" v-model="contentInput" :height="520" :place-holder="editorPlaceHolder"
              @on-text-change="valueChanged" :enable-limit="enableEditorLimit" :limit-count="editorLimit"/>

    </div>

    <!-- 分类选择器 -->
    <div class="flexClass" style="margin-top: 20px">

      <!-- 文章分类 -->
      <div class="flexClass" style="margin-right: 20%">

        <div style="padding-right: 10px;">文章分类:</div>
        <div>
          <el-select v-model="article.articleClassify" filterable placeholder="请选择">
            <el-option
              v-for="item in dict.type.blog_article_classification"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </div>

      </div>

      <!-- 个人分类 -->
      <div class="flexClass">
        <div style="padding-right: 10px;">个人分类:</div>
        <div>
          <el-select v-model="article.personClassify" filterable placeholder="请选择">
            <el-option
              v-for="item in personClassDict"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </div>
      </div>


    </div>

    <!-- 下方按钮 -->
    <div class="flexClass" style="padding-top: 10px;margin-top: 10px;border-top: 1px solid #EEF1F6FF">

      <div class="bottom-button">
        <el-button type="success" @click="publish()">发布</el-button>
      </div>

      <div class="bottom-button">
        <el-button type="warning" @click="draft()">存为草稿</el-button>
      </div>

      <div class="bottom-button">
        <el-button type="info" @click="closePage()">取消</el-button>
      </div>

    </div>

  </div>
</template>

<script>

import {getPersonClassDict, postArticle, getArticle} from "@/api/biz/article";
import router from '@/router';

export default {
  name: 'ArticleEdit',
  dicts:['blog_article_classification'],
  data() {
    return {
      loading: true,
      pageTitle: '',
      personClassDict: [],
      enableEditorLimit: true,
      editorLimit: 5000,
      curInputCnt: 0,
      editorPlaceHolder: '',
      contentInput: '',
      submitType: '', // 提交方式，1：发布，2：保存草稿

      article: {
        id: undefined,
        title: '',
        personClassify: 0,
        articleClassify: '',
      }
    }
  },
  created() {
    this.init()

  },
  methods: {
    init() {
      this.loading = true

      this.initEditor()
      this.initDicts()
      this.initRouter()

      this.loading = false
    },
    initRouter() {
      if (this.$route.query.articleId) {
        let articleId = this.$route.query.articleId;
        console.log('route', articleId)
        this.pageTitle = '修改随笔'

        getArticle(articleId).then(resp => {
          if (resp.code === 200) {
            let articleData = resp.data;
            this.article.id = articleData.articleId
            this.article.title = articleData.title
            this.article.personClassify = articleData.personClassify
            this.article.articleClassify = '' + articleData.articleClassify

            // 将内容插入Quill
            this.$refs.input.Quill.setContents(JSON.parse(articleData.contentFormatting))
          } else {
            this.$message({
              message: resp.msg,
              type: 'error'
            })
          }
        })


      } else {
        this.pageTitle = '新建随笔'

      }
    },
    initDicts() {
      getPersonClassDict().then(resp => {
        this.personClassDict = resp.data

        this.article.personClassify = this.personClassDict.find(u => u.label === '默认分类').value
      })
    },
    initEditor() {
      this.editorPlaceHolder = '请将字数控制在'+ this.editorLimit +'字以内，如果字数过长，请分批发布~'
    },
    valueChanged(delta, oldDelta, source) {
      this.curInputCnt = this.$refs.input.Quill.getLength() - 1
    },
    // 发布文章
    publish() {
      this.submitType = '1'
      this.save()
    },
    // 存为草稿
    draft() {
      this.submitType = '2'
      this.save()
    },
    // 提交文章
    save() {
      // 判断标题输入
      if (this.article.title === null || this.article.title.trim().length === 0) {
        this.$message({
          message: '请输入标题!',
          type: 'error'
        })

        return
      }

      // 判断内容输入
      if (this.$refs.input.Quill.getLength() <= 1) {
        this.$message({
          message: '无法提交空内容!',
          type: 'error',
        })

        return
      }

      // 判断文章分类是否选择
      if (this.article.articleClassify === null
        || this.article.articleClassify.trim().length === 0
        || this.article.personClassify === null
        || this.article.personClassify === 0
      ) {
        this.$message({
          message: '请选择分类!',
          type: 'error'
        })

        return
      }

      let articleDto = {
        articleId: this.article.id,
        title: this.article.title,
        status: this.submitType,
        articleClassify: this.article.articleClassify,
        personClassify: this.article.personClassify,
        content: this.$refs.input.Quill.getText(),
        contentFormatting: JSON.stringify(this.$refs.input.Quill.getContents()),
        contentHtml: this.contentInput
      }

      postArticle(articleDto).then(resp => {
        if (resp.code == 200) {
          this.$message({
            message: '上传成功!',
            type: 'success'
          })

          this.closePage()
        } else {
          this.$message({
            message: '上传失败，请稍后重试!',
            type: 'error'
          })
        }
      })
    },
    closePage() {
      // 关闭当前页面
      this.$tab.closePage();
      router.go(-1)
    }

  }
}


</script>

<style>
.bottom-button {
  margin-right: 15%;
}


/* 设置全局样式为flex */
.flexClass {
  display: flex;
  flex-wrap: wrap;
}

</style>
