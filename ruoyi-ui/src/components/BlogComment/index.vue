<template>
  <!-- 评论区模块，父组件传入文章id即可生成评论区-->
  <div class="app-container" style="width: 100%">


    <!--   评论标题     -->
    <div style="display: flex; border-top: 1px solid #EEF1F6FF">
      <h1 style="margin-right: 20px;">评论 {{ this.commentCnt }}</h1>

      <el-radio-group v-model="commentsOrder" class="all-center"
                      style="padding-left: 20px;border-left: 2px solid #EEF1F6FF" @input="changeOrder">
        <el-radio-button v-for="item in dict.type.blog_comment_order" :key="item.value" :label="item.value"
                         :value="item.value">
          {{ item.label }}
        </el-radio-button>
      </el-radio-group>

    </div>

    <div style="display: flex;justify-content: space-between">
      <!--   评论输入区域   -->
      <div style="width: 70%">
        <el-input
          v-model="commentInput"
          :autosize="true"
          :rows="2"
          :show-word-limit="true"
          maxlength="500"
          placeholder="请友善发言哦~~~~"
          resize="none"
          type="textarea"
        />
      </div>

      <el-button round type="primary" @click="sendComment(null)">回复</el-button>

    </div>

    <div style="width: 100%">
      <!--   母评论列表     -->
      <div v-for="pComment in this.comments"
           style="padding-top: 20px;border-bottom: 1px solid #EEF1F6FF">

        <div style="display: flex">
          <!-- 左部头像框 -->
          <div style="padding-right: 10px">
            <el-avatar :size="35" :src="pComment.senderAvatar"/>
          </div>

          <!-- 右部框,分为两部分，上部分的回复者、时间信息、点赞按钮等，下部分的回复内容 -->
          <div style="width: 95%">
            <div style="margin-bottom: 10px;display: flex;flex-wrap: wrap;justify-content: space-between">
              <div style="display: flex;flex-wrap: wrap">
                <div style="margin-right: 10px">{{ pComment.senderName }}</div>
                <div style="margin-right: 10px;padding-top: 4px"><i class="el-icon-time"/>{{ pComment.sendTime }}</div>
              </div>
              <div style="display: flex;flex-wrap: wrap">

                <div class="hover-pointer"
                     @click="showSubCommentInput(pComment.id, '',true)">
                  <i class="el-icon-chat-line-square"/>
                  <span style="margin-left: 5px">回复</span>
                </div>

                <el-tooltip v-if="!pComment.liked" class="item" content="点赞" effect="dark" placement="top">
                  <div class="hover-pointer" style="margin-left: 20px"
                       @click="likeComment(true, pComment.id, pComment)">
                    <i class="el-icon-thumb"/>
                    <span style="margin-left: 5px">{{ pComment.likeCnt }}</span>
                  </div>
                </el-tooltip>

                <el-tooltip v-if="pComment.liked" class="item" content="已点赞" effect="dark" placement="top">
                  <div class="hover-pointer" style="margin-left: 20px"
                       @click="likeComment(false, pComment.id, pComment)">
                    <i class="el-icon-check"/>
                    <span style="margin-left: 5px">{{ pComment.likeCnt }}</span>
                  </div>
                </el-tooltip>

              </div>
            </div>
            <div style="margin-bottom: 10px">
              {{ pComment.content }}
            </div>
          </div>
        </div>

        <!--   每个母评论下的子评论列表       -->
        <div v-for="subComment in pComment.subComments">
          <div style="width: 95%;margin-left: 5%;display: flex">

            <div style="margin-right: 10px">
              <el-avatar :size="35" :src="subComment.senderAvatar"/>
            </div>

            <div style="margin-bottom: 10px;display: flex;flex-wrap: wrap">
              <div style="margin-bottom: 10px;display: flex;width: 100%;justify-content: space-between">
                <div class="flexClass">
                  <div style="margin-right: 10px">{{ subComment.senderName }}</div>
                  <div style="margin-right: 10px">回复</div>
                  <div style="margin-right: 10px">{{ subComment.receiverName }}</div>
                  <div style="margin-right: 10px;padding-top: 4px"><i class="el-icon-time"/>{{ subComment.sendTime }}
                  </div>
                </div>

                <div class="flexClass">
                  <div class="hover-pointer" @click="showSubCommentInput(pComment.id, subComment.id, false)">
                    <i class="el-icon-chat-line-square"/>
                    <span style="margin-left: 5px">回复</span>
                  </div>

                  <el-tooltip v-if="!subComment.liked" class="item" content="点赞" effect="dark" placement="top">
                    <div class="hover-pointer" style="margin-left: 20px"
                         @click="likeComment(true, subComment.id, subComment)">
                      <i class="el-icon-thumb"/>
                      <span style="margin-left: 5px">{{ subComment.likeCnt }}</span>
                    </div>
                  </el-tooltip>

                  <el-tooltip v-if="subComment.liked" class="item" content="已点赞" effect="dark" placement="top">
                    <div class="hover-pointer" style="margin-left: 20px"
                         @click="likeComment(false, subComment.id, subComment)">
                      <i class="el-icon-check"/>
                      <span style="margin-left: 5px">{{ subComment.likeCnt }}</span>
                    </div>
                  </el-tooltip>


                </div>

              </div>
              <div style="margin-bottom: 10px">
                {{ subComment.content }}
              </div>
            </div>
          </div>
        </div>

        <!-- 回复评论的输入区域 -->
        <div v-if="showCommentReplyId !== '' && pComment.id === showCommentReplyId"
             style="width: 100%;display: flex;flex-wrap: wrap">
          <div style="width: 70%">
            <el-input
              v-model="commentReplyInput"
              :autosize="true"
              :placeholder="commentReplyHolder"
              :rows="2"
              :show-word-limit="true"
              maxlength="500"
              resize="none"
              type="textarea"
            />
          </div>

          <div style="width: 30%;padding-left: 20%">
            <el-button round type="primary" @click="sendComment(pComment.id)">回复</el-button>
          </div>
        </div>

      </div>


      <!-- 加载更多评论按钮,三种状态:加载更多，加载中，已经到底 -->
      <div>
        <el-button
          v-if="commentStatus === '1'"
          :loading="true"
          style="margin-left: 45%; margin-top: 15px"
          type="primary"
        >
          加载中
        </el-button>
        <el-button
          v-if="commentStatus === '2'"
          plain
          style="margin-left: 45%; margin-top: 15px"
          type="primary"
          @click="getComments(false)"
        >
          加载更多
        </el-button>

        <h3 v-if="commentStatus === '3'" style="margin-left: 45%">
          已经到底啦~~~~~
        </h3>
      </div>

    </div>

  </div>
</template>

<script>

import {commentLike, getComment, postComment} from "@/api/biz/blog";

export default {
  name: 'BlogComment',
  dicts: ['blog_comment_order'],
  props: {
    /* 文章Id */
    articleId: {
      type: [Number, String],
      default: undefined
    }
  },
  data() {
    return {
      comments: [
        {
          content: '',
          id: '',
          likeCnt: '',
          parentId: '',
          receiverName: '',
          sendTime: '',
          senderAvatar: '',
          senderName: '',
          senderId: '',
          receiverId: '',
          subComments: [],
          liked: false
        }
      ],

      commentsOrder: '1', // 评论排序方式，1：按时间升序（默认），2：按时间倒序，3：最多点赞

      commentCnt: 0, // 总评论数
      commentStatus: '1', // 1：加载中，2：加载更多，3：已经到底
      commentStart: 0,
      commentInput: '',
      showCommentReplyId: '',
      showSubCommentReplyId: '',
      commentReplyHolder: '评论的回复:',
      commentReplyInput: ''
    }
  },
  created() {
    if (this.articleId) {
      this.getComments(false)
    }
  },
  methods: {
    //评论点赞/取消点赞，flag：是否已点赞
    likeComment(flag, commentId, comment) {
      let operate = flag ? 1 : 2;
      let dto = {
        commentId: commentId,
        operateType: operate
      }
      this.submitLike(dto, comment)
    },
    // 提交点赞请求
    submitLike(dto, comment) {
      commentLike(dto).then(resp => {
        if (resp.code === 200) {
          if (dto.operateType === 1) {
            comment.likeCnt += 1
          } else if (dto.operateType === 2) {
            comment.likeCnt -= 1
          }
          comment.liked = !comment.liked
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })
    },
    // 改变评论的排序方式
    changeOrder(label) {
      this.getComments(true)
    },
    // 展示子评论输入框
    showSubCommentInput(pCommentId, subCommentId, isParent) {
      this.showSubCommentReplyId = ''
      this.commentReplyInput = ''
      this.showCommentReplyId = pCommentId
      let commentName = ''
      let pComment = this.comments.find(u => u.id === pCommentId);
      if (isParent) {
        commentName = pComment.senderName
      } else {
        commentName = pComment.subComments.find(u => u.id === subCommentId).senderName
        this.showSubCommentReplyId = subCommentId
      }
      this.commentReplyHolder = '回复 @' + commentName + ':'
    },
    // 获取评论
    getComments(refreshFlag) {
      this.showCommentReplyId = ''
      this.showSubCommentReplyId = ''
      this.commentStatus = '1'

      let dto = {
        blogId: this.articleId,
        start: this.commentStart,
        refreshFlag: refreshFlag,
        order: this.commentsOrder
      }

      getComment(dto).then(resp => {
        if (this.commentStart === 0 || refreshFlag) {
          this.comments = resp.data.comments
        } else {
          this.comments = this.comments.concat(resp.data.comments)
        }

        this.commentCnt = resp.data.commentTotal

        this.$emit('commentCntUpdated', this.commentCnt)

        if (!refreshFlag) {
          this.commentStart += 3
        }
        if (resp.data.hasMore) {
          this.commentStatus = '2'
        } else {
          this.commentStatus = '3'
        }
      }).catch(ex => {
        this.commentStatus = '2'
        this.$message({
          message: ex.message,
          type: 'error'
        })
      })
    },

    // 发送评论
    sendComment(pCommentId) {
      let subCommentEmpty = this.commentReplyInput === '' || this.commentReplyInput.trim().length === 0;
      let commentEmpty = this.commentInput === '' || this.commentInput.trim().length === 0;
      if ((pCommentId == null && commentEmpty) || (pCommentId != null && subCommentEmpty)) {
        this.$message({
          message: '无法回复空内容!',
          type: 'warning'
        })
        return
      }

      let putCommentDto = {
        blogId: this.articleId,
      }

      // 如果不传入 pCommentId, 说明是一级评论
      if (pCommentId != null) {
        putCommentDto.content = this.commentReplyInput
        putCommentDto.parentId = pCommentId

        let receiverId;
        if (this.showSubCommentReplyId === '') {
          // 回复一级评论，从父评论中寻找 receiverId
          receiverId = this.comments.find(u => u.id === this.showCommentReplyId).senderId
        } else {
          let pComment = this.comments.find(u => u.id === this.showCommentReplyId);
          receiverId = pComment.subComments.find(u => u.id === this.showSubCommentReplyId).senderId
        }

        putCommentDto.receiverId = receiverId;
      } else {
        putCommentDto.content = this.commentInput
        this.commentInput = ''
      }

      postComment(putCommentDto).then(resp => {
        if (resp.code === 200) {
          // 发送评论后刷新当前评论
          this.getComments(true)

          // 回复成功提醒
          this.$message({
            message: '回复成功!',
            type: 'success'
          })
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })


    },

  },
  watch: {
    articleId: {
      handler(newVal) {
        this.commentsOrder = '1'
        this.commentCnt = 0
        this.commentStatus = '1'
        this.commentStart = 0
        this.commentInput = ''
        this.showCommentReplyId = ''
        this.showSubCommentReplyId = ''
        this.commentReplyHolder = '评论的回复:'
        this.commentReplyInput = ''

        this.getComments(false)
      }
    }
  }
}


</script>

<style>
/* 容器内元素水平垂直居中 */
.all-center {
  display: flex;
  justify-content: center;
  align-items: center
}

/* 设置全局样式为flex */
.flexClass {
  display: flex;
  flex-wrap: wrap;
}

.hover-pointer:hover {
  cursor: pointer;
}
</style>
