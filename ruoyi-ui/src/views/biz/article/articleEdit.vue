<template>
  <div style="padding-left: 10px;padding-right: 20px">
    <h1 style="width: 100%;">编辑随笔</h1>

    <!-- 输入标题部分 -->
    <div style="display: flex">
      <div style="padding-right: 10px">输入标题:</div>
      <el-input
        v-model="titleInput"
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
          <el-select v-model="articleClassify" filterable placeholder="请选择">
            <el-option
              v-for="item in testCategory"
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
          <el-select v-model="personClassify" filterable placeholder="请选择">
            <el-option
              v-for="item in testCategory"
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
        <el-button type="info">取消</el-button>
      </div>

    </div>

  </div>
</template>

<script>

import {postArticle} from "@/api/biz/blog";

export default {
  name: 'articleCreate',
  data() {
    return {
      enableEditorLimit: true,
      editorLimit: 5000,
      curInputCnt: 0,
      editorPlaceHolder: '',
      titleInput: '',
      contentInput: '',
      articleClassify: '',
      personClassify: '',
      submitType: '', // 提交方式，1：发布，2：保存草稿

      testCategory: [
        {
          value: '1',
          label: '技术分享'
        }, {
          value: '2',
          label: '新闻资讯'
        }, {
          value: '3',
          label: '趣闻吐槽'
        }, {
          value: '4',
          label: '心得总结'
        }, {
          value: '5',
          label: '碎碎念'
        },

      ]

    }
  },
  created() {
    this.initCurUser()
    this.initEditor()
  },
  methods: {
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
      if (this.titleInput === null || this.titleInput.trim().length === 0) {
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
      if (this.articleClassify === null
        || this.articleClassify.trim().length === 0
        || this.personClassify === null
        || this.personClassify.trim().length === 0
      ) {
        this.$message({
          message: '请选择分类!',
          type: 'error'
        })

        return
      }

      let articleDto = {
        title: this.titleInput,
        status: this.submitType,
        articleClassify: this.articleClassify,
        personClassify: this.personClassify,
        content: this.$refs.input.Quill.getText(),
        contentFormatting: JSON.stringify(this.$refs.input.Quill.getContents())
      }

      postArticle(articleDto).then(resp => {
        if (resp.code == 200) {
          this.$message({
            message: '上传成功!',
            type: 'success'
          })
        } else {
          this.$message({
            message: '上传失败，请稍后重试!',
            type: 'error'
          })
        }
      })


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
