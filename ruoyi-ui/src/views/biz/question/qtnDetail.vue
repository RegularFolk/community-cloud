<template>
  <div class="app-container" style="display: flex;margin-bottom: 100px;overflow: visible">

    <!-- 左侧展示区 -->
    <div style="width: 1300px; margin-right:100px;">

      <!-- 标题信息栏 -->
      <div style="margin: 10px">
        <div style="font-size: 27px">
          {{ qtn.title }}
        </div>

        <div style="display: flex; margin-top: 10px">
          <div style="margin-right: 30px">{{ '提问于  ' + qtn.publishTime }}</div>
          <div>{{ '浏览  ' + qtn.viewCnt + ' 次' }}</div>
        </div>

      </div>

      <div style="display: flex">

        <!-- 点赞按钮、点赞数、收藏按钮、是否采纳标志 -->
        <div style="margin-right: 10px">
          <div v-loading="likeButtonLoading" style="display: flex; justify-content: center; cursor: pointer; color: rgb(59, 64, 69)"
               title="点赞这个问题" @click="submitLike(qtn.articleId, true)">
            <i class="el-icon-caret-top" style="font-size: 50px"/>
          </div>

          <div style="font-size: 19px; color: #0C0D0E; display: flex; justify-content: center; font-weight: 700">
            {{ qtn.likeCnt }}
          </div>

          <div v-if="!qtn.collected" v-loading="collectLoading"
               style="display: flex; justify-content: center; margin-top: 10px; cursor: pointer" title="收藏这个问题"
               @click="collectQtn">
            <i class="el-icon-collection-tag" style="font-size: 30px"/>
          </div>

          <div v-if="qtn.collected" style="display: flex; justify-content: center; cursor: pointer; color: green"
               title="已收藏！">

            <i class="el-icon-check" style="font-size: 30px"/>

          </div>

        </div>

        <!-- 使用class方式展示，会出现双层scroll的问题，所以使用编辑器展示 -->
        <editor ref="editor" v-loading="qtnLoading" :for-present="true" :read-only="true"/>

      </div>
      <!-- 问题评论区 -->
      <div
        style="display: flex;justify-content: right;margin-top: 50px;border-bottom: 1px solid #5a5e66;padding-bottom: 10px">
        <comment-btn-dialog :content-id="qId"/>

      </div>

      <!-- 回答区 -->
      <div v-loading="ansLoading" style="margin-top: 10px">
        <!-- 回答标题 -->
        <div style="font-size: 26px; color: #636B74; margin-bottom: 20px">
          {{ 'Answers ：' + ansTotal }}
        </div>

        <!-- 回答列表 -->
        <el-card v-for="ans in ansList" :key="ans.articleId">
          <div style="display: flex">

            <!-- 点赞按钮、点赞数、收藏按钮、是否采纳标志 -->
            <div style="margin-right: 10px">
              <div v-loading="likeButtonLoading" style="display: flex; justify-content: center; cursor: pointer"
                   title="点赞这个回答" @click="submitLike(ans.articleId, false)">
                <i class="el-icon-caret-top" style="font-size: 50px"/>
              </div>

              <div style="font-size: 19px; color: #0C0D0E; display: flex; justify-content: center; font-weight: 700; color: rgb(59, 64, 69)">
                {{ ans.likeCnt }}
              </div>

              <div style="margin-top: 15px">

                <el-tag v-if="ans.accepted" effect="dark" label="已采纳！" type="success">
                  <i class="el-icon-check">已采纳！</i>
                </el-tag>

              </div>

              <div v-loading="acptLoading" style="margin-top: 15px; cursor: pointer">

                <el-tag v-if="!qtnHasAccepted && !ans.accepted && isSelfQtn" effect="dark" label="采纳问题" type="danger"
                        @click="acptAns(ans.articleId)">
                  <i class="el-icon-s-check">采纳问题</i>
                </el-tag>

              </div>


            </div>

            <div style="width: 1200px">

              <div>
                <editor :ref="'ansEditor' + ans.articleId" :for-present="true" :read-only="true"/>
              </div>

              <div style="display: flex;justify-content: right; margin-top: 20px">

                <div style="margin-right: 20px; display: flex; align-items: center">

                  {{ '回答于 ' + ans.publishTime }}

                </div>

                <div style="margin-right: 20px; display: flex; align-items: center">
                  <comment-btn-dialog :content-id="ans.articleId.toString()"/>
                </div>

                <div style="margin-right: 20px; display: flex; align-items: center">
                  <el-popover
                    placement="right"
                    trigger="hover"
                    width="400">
                    <div slot="reference" style="margin-right: 20px;cursor: pointer"
                         @click="routeToProfile(ans.userBasicInfo.id)">
                      <el-avatar :size="60" :src="ans.userBasicInfo.avatar" shape="square"/>
                    </div>

                    <UserBasicInfo :hover-show="true" :userInjected="ans.userBasicInfo"/>

                  </el-popover>

                </div>

              </div>

            </div>

          </div>
        </el-card>

        <!-- 加载更多评论按钮,三种状态:加载更多，加载中，已经到底 -->
        <div style="display: flex;justify-content: center;margin-top: 20px">
          <el-button
            v-if="loadStatus === 1"
            :loading="true"
            type="primary"
          >
            加载中
          </el-button>
          <el-button
            v-if="loadStatus === 2"
            plain
            type="primary"
            @click="handleAnsQuery"
          >
            加载更多
          </el-button>

          <h3 v-if="loadStatus === 3">
            已经到底啦~~~~~
          </h3>
        </div>

      </div>


    </div>

    <!-- 右侧编辑回答区 -->
    <div v-loading="qtnLoading || ansLoading" style="width: 900px">
      <!-- 输入区标题 -->
      <div style="font-size: 26px;color: #636B74;margin-bottom: 10px">
        请输入您的回答吧！
      </div>

      <Editor
        ref="ansInput"
        v-model="ansInput.content"
        :enable-limit="true"
        :height="820"
        :limit-count="editorLimit"
        :place-holder="editorPlaceHolder"
      />

      <div style="display: flex;justify-content: center;margin-top: 10px">
        <el-tooltip effect="dark" content="您已经回答过该问题！" :disabled="!qtnHasAnswered" placement="left">
          <el-button
            :disabled="qtnHasAnswered"
            v-loading="submitAnsLoading || qtnLoading || ansLoading"
            round
            type="primary"
            @click="submitAns"
          >
            提交回答
          </el-button>
        </el-tooltip>
      </div>

    </div>

  </div>

</template>


<script>

import {ansList, postQtn, qtnAnsAcpt, qtnCollect, qtnDetail} from "@/api/biz/qtn";
import BlogComment from "@/components/BlogComment";
import CommentBtnDialog from "@/components/CommentBtnDialog";
import UserBasicInfo from "@/views/system/user/profile/userBasicInfo";
import {like} from "@/api/biz/blog";

export default {
  name: 'QtnDetail',
  components: {UserBasicInfo, CommentBtnDialog, BlogComment},
  data() {
    return {
      isSelfQtn: false,
      qtnHasAnswered: false,
      qtnHasAccepted: false,
      qtnLoading: false,
      ansLoading: false,
      acptLoading: false,
      collectLoading: false,
      likeButtonLoading: false,
      submitAnsLoading: false,
      ansInput: {
        content: ''
      },
      editorLimit: 5000,
      editorPlaceHolder: '请输入您的回答',
      qId: '',
      qtn: {},
      ansList: [],
      ansQuery: {
        qtnId: '',
        pageSize: 8,
        pageNum: 1
      },
      ansHasMore: true,
      ansTotal: 0,
      loadStatus: 1
    }
  },
  created() {
    this.qId = this.$route.query.qId
    this.ansQuery.qtnId = this.qId
    console.log('this.$route.query.qId', this.$route.query.qId)
    this.init()
  },
  activated() {
    if (this.$route.query.qId != this.qId) {
      this.qId = this.$route.query.qId
      this.init()
    }
  },
  methods: {
    // 初始化
    init() {
      this.qtnLoading = true
      this.ansLoading = true

      this.initQtn()
      this.initAns()
    },
    initQtn() {
      qtnDetail({id: this.qId}).then(resp => {
        if (resp.code === 200) {
          this.qtn = resp.data

          this.isSelfQtn = (this.qtn.authorId == this.$store.state.user.id)
          this.qtnHasAnswered = resp.hasAnswered
          this.qtnHasAccepted = resp.hasAccepted

          this.$refs.ansInput.Quill.enable(!this.qtnHasAnswered)

          this.$refs.editor.Quill.setContents(JSON.parse(this.qtn.contentFormatting))
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.qtnLoading = false
      })
    },
    // 收藏问题
    collectQtn() {
      let dto = {
        id: this.qtn.articleId
      }
      this.collectLoading = true
      qtnCollect(dto).then(resp => {
        if (resp.code == 200) {
          this.$message({
            message: '收藏成功！',
            type: 'success'
          })
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.collectLoading = false
      })
    },
    acptAns(ansId) {
      let dto = {
        qtnId: this.qtn.articleId,
        ansId: ansId
      }
      qtnAnsAcpt(dto).then(resp => {
        this.acptLoading = true
        if (resp.code === 200) {
          this.ansList.find(item => item.articleId == ansId).accepted = true
          this.$message("采纳成功！")
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.acptLoading = false
      })
    },
    submitLike(id, qtnFlag) {
      let dto = {
        blogId: id,
        operateType: 1
      }
      this.likeButtonLoading = true
      like(dto).then(resp => {
        if (resp.code === 200) {
          if (qtnFlag) {
            this.qtn.likeCnt++
          } else {
            this.ansList.find(item => item.articleId == id).likeCnt++
          }
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.likeButtonLoading = false
      })
    },
    initAns() {
      this.handleAnsQuery()
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
    // 提交回答
    submitAns() {
      let content = this.$refs.ansInput.Quill.getText()
      console.log('content', content)
      if (!content || content.trim().length <= 0) {
        this.$message({
          message: '不可提交空内容！',
          type: 'warning'
        })
        return
      }
      this.submitAnsLoading = true
      let dto = {
        qtnPId: this.qId,
        content: content,
        contentFormatting: JSON.stringify(this.$refs.ansInput.Quill.getContents()),
        contentHtml: this.ansInput.content
      }

      postQtn(dto).then(resp => {
        if (resp.code === 200) {
          this.ansInput = {
            content: ''
          }
          this.$message({
            message: '发布成功，感谢您的回答！',
            type: 'success'
          })
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.submitAnsLoading = false
      })
    },
    handleAnsQuery() {
      if (!this.ansHasMore) {
        return
      }
      this.ansLoading = true
      this.loadStatus = 1
      ansList(this.ansQuery).then(resp => {
        if (resp.code === 200) {
          this.ansTotal = resp.data.total
          this.ansList = this.ansList.concat(resp.data.ansList)


          this.$nextTick(() => {
            // 设置回答内容
            this.ansList.forEach(item => {
              this.$refs['ansEditor' + item.articleId][0].Quill.setContents(JSON.parse(item.contentFormatting))
            })
          })

          if (this.ansList.length < this.ansTotal) {
            this.ansQuery.pageNum++
            this.loadStatus = 2
          } else {
            this.hasMore = false
            this.loadStatus = 3
          }

        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.ansLoading = false
      })
    }
  }

}


</script>

<style>


</style>
