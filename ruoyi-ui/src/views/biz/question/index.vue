<template>

  <div class="app-container" style="display: flex;justify-content: space-between">

    <div style="width: 1500px">
      <qtn-list/>
    </div>

    <div>
      <el-button type="danger" @click="drawerVisible = true">我要提问</el-button>

    </div>

    <div style="width: 500px">
      <el-card v-loading="rankLoading" style="margin-bottom: 20px">
        <div slot="header">
          热门问题
        </div>
        <article-rank :rank-list="qtnRank"/>
      </el-card>
    </div>

    <el-drawer
      :visible.sync="drawerVisible"
      direction="rtl"
      size="50%"
      title="请编辑您的问题">

      <div style="margin: 15px">
        <!-- 输入标题部分 -->
        <div style="display: flex">
          <div style="padding-right: 10px">请用一句话简明扼要地概括您的问题：</div>
          <el-input
            v-model="questionInput.title"
            :rows="2"
            :show-word-limit="true"
            maxlength="100"
            placeholder="请输入标题"
            resize="none"
            style="width: 700px"
          />
        </div>

        <!-- 输入部分 -->
        <div style="margin-top: 70px">

          <div style="display:flex;justify-content: space-between">
            <h3>内容编辑</h3>
            <h3>当前字数：{{ this.curInputCnt }} / {{ this.editorLimit }}</h3>
          </div>

          <Editor ref="input" v-model="questionInput.content" :enable-limit="enableEditorLimit" :height="520"
                  :limit-count="editorLimit" :place-holder="editorPlaceHolder" @on-text-change="valueChanged"/>

        </div>

        <div style="display: flex;justify-content: center;margin-top: 70px">
          <el-tooltip class="item" content="标题和内容不可为空" effect="dark" placement="top" :disabled="!submitDisabled">
            <el-button :disabled="submitDisabled" type="success" v-loading="submitBtnLoading" @click="submitQtn">提交</el-button>
          </el-tooltip>
        </div>

      </div>

    </el-drawer>

  </div>

</template>

<script>

import ArticleRank from "@/views/biz/article/articleRank";
import QtnList from "@/views/biz/question/qtnList";
import {postQtn} from "@/api/biz/qtn";

export default {
  name: 'Question',
  components: {QtnList, ArticleRank},
  data() {
    return {
      rankLoading: false,
      qtnRank: [],
      drawerVisible: false,
      curInputCnt: 0,
      editorLimit: 5000,
      enableEditorLimit: true,
      editorPlaceHolder: '请输入您的问题',
      submitBtnLoading: false,
      questionInput: {
        title: '',
        content: '',
      }
    }
  },
  created() {
  },
  computed: {
    submitDisabled() {
      return !this.questionInput.title
        || this.questionInput.title.trim().length === 0
        || !this.questionInput.content
        || this.questionInput.content.trim().length === 0
    }
  },
  methods: {
    valueChanged(delta, oldDelta, source) {
      this.curInputCnt = this.$refs.input.Quill.getLength() - 1
    },
    // 提交问题
    submitQtn() {
      this.submitBtnLoading = true
      let dto = {
        title: this.questionInput.title,
        status: 1,
        content: this.$refs.input.Quill.getText(),
        contentFormatting: JSON.stringify(this.$refs.input.Quill.getContents()),
        contentHtml: this.questionInput.content
      }

      postQtn(dto).then(resp => {
        if (resp.code === 200) {
          this.$message({
            message: '提交成功！',
            type: 'success'
          })
          this.drawerVisible = false
          this.questionInput = {
            title: '',
            content: ''
          }
          this.curInputCnt = 0
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.submitBtnLoading = false
      })
    }
  }
}


</script>
