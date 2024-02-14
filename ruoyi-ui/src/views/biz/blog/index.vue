<template>
  <div style="background-color: #EEF1F6FF">
    <div class="fixed-box-left">
      <el-menu default-active="1" v-if="!loadingPost">
        <el-menu-item index="1" @click="testInterface()">
          <i class="el-icon-message"/>
          <span slot="title">猜你想看</span>
        </el-menu-item>
        <el-menu-item index="2">
          <i class="el-icon-message"/>
          <span slot="title">最新发布</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-message"/>
          <span slot="title">我的关注</span>
        </el-menu-item>
        <el-menu-item index="4">
          <i class="el-icon-message"/>
          <span slot="title">热门帖子</span>
        </el-menu-item>
        <el-menu-item index="5">
          <i class="el-icon-menu"/>
          <span slot="title">热门提问</span>
        </el-menu-item>
        <el-menu-item index="6">
          <i class="el-icon-setting"/>
          <span slot="title">热门回答</span>
        </el-menu-item>
        <el-menu-item index="7">
          <i class="el-icon-setting"/>
          <span slot="title">校园榜单</span>
        </el-menu-item>
      </el-menu>
    </div>

    <div v-loading="loadingPost" class="recommended-post" style="width: 100%;height: 100%;z-index: 90">
      <el-main>
        <el-card v-for="blog in this.blogs" :key="blog.blogId" shadow="hover" style="margin-bottom: 20px">
          <div slot="header" class="clearfix">
            <div style="width: 60px; float: left">
              <el-avatar :size="50" :src="blog.avatar"/>
            </div>
            <div style="float: left;margin-left: 10px">
              <div>{{ blog.senderName }}</div>
              <div style="margin-top: 10px">{{ blog.releaseTime }}</div>
            </div>
            <el-button style="float: right; padding: 10px" type="primary" @click="testMethod()">关注</el-button>
          </div>
          <div class="card-content">{{ blog.preview }}</div>
          <div class="card-button">
            <el-badge :value="blog.viewCnt" type="primary">
              <el-button icon="el-icon-search" type="primary" @click="showPost(blog.blogId)">查看</el-button>
            </el-badge>
          </div>
          <div class="card-button" @click="testMethod()">
            <el-badge :value="blog.likeCnt" type="danger">
              <el-button icon="el-icon-thumb" type="danger">点赞</el-button>
            </el-badge>
          </div>
        </el-card>
      </el-main>
    </div>


    <el-dialog :before-close="handleClose" :close-on-click-modal="false" :visible.sync="this.showPostDetail"
               v-if="this.showPostDetail"
               title="推文详情">
      <!--   标题下灰色副标题区域   -->
      <div class="post-sub-title">
        <div class="post-sub-title-unit">
          <i class="el-icon-user" style="padding-left: 10px"> {{ this.showBlog.senderName }}</i>
        </div>
        <div class="post-sub-title-unit"><i class="el-icon-timer"> 于 {{ this.showBlog.releaseTime }} 发布</i></div>
        <div class="post-sub-title-unit"><i class="el-icon-view"> 浏览:{{ this.showBlog.viewCnt }}</i></div>
        <div class="post-sub-title-unit"><i class="el-icon-thumb"> 点赞:{{ this.showBlog.likeCnt }}</i></div>
        <div class="post-sub-title-unit"><i class="el-icon-chat-line-round"> 评论:{{ this.showBlog.commentCnt }}</i>
        </div>
      </div>

      <!--   对话框内容部分   -->
      <div class="comment-container">

        <!-- 测试代码，暂时使用preview -->
        <div style="padding-bottom: 30px">
          {{ this.showBlog.preview}}
        </div>

        <!--   评论标题     -->
        <div style="width: 100%;border-top: 1px solid #EEF1F6FF">
          <h1>评论 {{ this.showBlog.commentCnt }}</h1>
        </div>

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

        <div style="width: 30%;padding-left: 20%">
          <el-button round type="primary" @click="sendComment(null)">回复</el-button>
        </div>

        <div style="width: 100%">
          <!--   母评论列表     -->
          <div v-for="pComment in this.blogComments"
               style="display: flex;flex-wrap: wrap;padding-top: 20px;border-bottom: 1px solid #EEF1F6FF">
            <!-- 左部头像框 -->
            <div style="padding-right: 10px;width: 5%">
              <el-avatar :size="35" :src="pComment.senderAvatar"/>
            </div>

            <!-- 右部框,分为两部分，上部分的回复者、时间信息、点赞按钮等，下部分的回复内容 -->
            <div style="width: 95%">
              <div style="margin-bottom: 10px;display: flex;flex-wrap: wrap;justify-content: space-between">
                <div style="display: flex;flex-wrap: wrap">
                  <div style="margin-right: 10px">{{ pComment.senderName }}</div>
                  <div>{{ pComment.sendTime }}</div>
                </div>
                <div style="display: flex;flex-wrap: wrap">
                  <div class="hover-pointer"
                       @click="showSubCommentInput(pComment.id, '',true)">
                    <i class="el-icon-chat-line-square"/>
                    <span style="margin-left: 5px">回复</span>
                  </div>
                  <div class="hover-pointer" style="margin-left: 20px" @click="testMethod()">
                    <i class="el-icon-thumb"/>
                    <span style="margin-left: 5px">{{ pComment.likeCnt }}</span>
                  </div>
                </div>
              </div>
              <div style="margin-bottom: 10px">
                {{ pComment.content }}
              </div>
            </div>

            <!--   每个母评论下的子评论列表       -->
            <div v-for="subComment in pComment.subComments"
                 style="width: 95%;margin-left: 5%;display: flex;flex-wrap: wrap">
              <div style="margin-right: 10px">
                <el-avatar :size="35" :src="subComment.senderAvatar"/>
              </div>

              <div style="margin-bottom: 10px;display: flex;flex-wrap: wrap">
                <div style="margin-bottom: 10px;display: flex;width: 100%">
                  <div style="margin-right: 10px">{{ subComment.senderName }}</div>
                  <div style="margin-right: 10px">回复</div>
                  <div style="margin-right: 10px">{{ subComment.receiverName }}</div>
                  <div style="margin-right: 10px">{{ subComment.sendTime }}</div>

                  <div class="hover-pointer" @click="showSubCommentInput(pComment.id, subComment.id, false)">
                    <i class="el-icon-chat-line-square"/>
                    <span style="margin-left: 5px">回复</span>
                  </div>
                  <div class="hover-pointer" style="margin-left: 20px" @click="testMethod()">
                    <i class="el-icon-thumb"/>
                    <span style="margin-left: 5px">{{ subComment.likeCnt }}</span>
                  </div>

                </div>
                <div style="margin-bottom: 10px">
                  {{ subComment.content }}
                </div>
              </div>
            </div>

            <!-- 回复评论的输入区域 -->
            <div v-if="showCommentReplyId !== '' && pComment.id === showCommentReplyId" style="width: 100%;display: flex;flex-wrap: wrap">
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
              :loading="true"
              style="margin-left: 45%; margin-top: 15px"
              type="primary"
              v-if="commentStatus === '1'"
            >
              加载中
            </el-button>
            <el-button
              v-if="commentStatus === '2'"
              plain
              style="margin-left: 45%; margin-top: 15px"
              type="primary"
              @click="getBlogComment(showBlog.blogId, commentStart, false)"
            >
              加载更多
            </el-button>

            <h3 style="margin-left: 45%" v-if="commentStatus === '3'">
              已经到底啦~~~~~
            </h3>
          </div>

        </div>


      </div>
    </el-dialog>


  </div>
</template>


<script>


import {getComment, getTestBlogs, postComment} from "@/api/biz/blog";
import {getUserProfile} from "@/api/system/user"

export default {
  name: "Blog",
  data() {
    return {
      blogs: [
        {
          authorFollowed: '',
          avatar: '',
          blogId: '',
          commentCnt: '',
          likeCnt: '',
          preview: '',
          releaseTime: '',
          senderName: '',
          viewCnt: ''
        }
      ],
      commentStatus: '1', // 1：加载中，2：加载更多，3：已经到底
      commentStart: 0,
      blogComments: [
        // {
        //   content: '',
        //   id: '',
        //   likeCnt: '',
        //   parentId: '',
        //   receiverName: '',
        //   sendTime: '',
        //   senderAvatar: '',
        //   senderName: '',
        //   senderId: '',
        //   receiverId: '',
        //   subComments: []
        // }
      ],
      loadingPost: true,

      showPostDetail: false,
      showBlog: {},
      commentInput: '',
      showCommentReplyId: '',
      showSubCommentReplyId: '',
      commentReplyHolder: '评论的回复:',
      commentReplyInput: ''
    }
  },
  created() {
    this.getTestBlogList()
  },
  methods: {

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
        blogId: this.showBlog.blogId,
      }

      // 如果不传入 pCommentId, 说明是一级评论
      if (pCommentId != null) {
        putCommentDto.content = this.commentReplyInput
        putCommentDto.parentId = pCommentId

        let receiverId;
        if (this.showSubCommentReplyId === '') {
          // 回复一级评论，从父评论中寻找 receiverId
          receiverId = this.blogComments.find(u => u.id === this.showCommentReplyId).senderId
        } else {
          let pComment = this.blogComments.find(u => u.id === this.showCommentReplyId);
          receiverId = pComment.subComments.find(u => u.id === this.showSubCommentReplyId).senderId
        }

        putCommentDto.receiverId = receiverId;
      } else {
        putCommentDto.content = this.commentInput
        this.commentInput = ''
      }

      postComment(putCommentDto)

      // 发送评论后刷新当前评论
      this.getBlogComment(this.showBlog.blogId, this.commentStart, true)

      // 回复成功提醒
      this.$message({
        message: '回复成功!',
        type: 'success'
      })
    },

    // 展示子评论输入框
    showSubCommentInput(pCommentId, subCommentId, isParent) {
      this.showSubCommentReplyId = ''
      this.commentReplyInput = ''
      this.showCommentReplyId = pCommentId
      let commentName = ''
      let pComment = this.blogComments.find(u => u.id === pCommentId);
      if (isParent) {
        commentName = pComment.senderName
      } else {
        commentName = pComment.subComments.find(u => u.id === subCommentId).senderName
        this.showSubCommentReplyId = subCommentId
      }
      this.commentReplyHolder = '回复 @' + commentName + ':'
    },
    testMethod(msg) {
      this.$message({
        message: '获得响应！\n' + msg,
        type: 'success'
      })
    },
    showPost(blogId) {
      this.showBlog = this.blogs.find(u => u.blogId === blogId)
      this.showPostDetail = true
      this.getBlogComment(blogId, this.commentStart, false)
    },

    handleClose() {
      this.showPostDetail = false
      this.commentInput = ''
      this.commentReplyInput = ''
      this.showCommentReplyId = ''
      this.showSubCommentReplyId = ''

      // 初始化评论状态
      this.blogComments = []
      this.commentStart = 0
      this.commentStatus = '1'
    },

    getTestBlogList() {
      this.loadingPost = true
      getTestBlogs().then(resp => {
        this.blogs = resp.data
        this.loadingPost = false
      })

    },

    getBlogComment(blogId, start, refreshFlag) {
      this.showCommentReplyId = ''
      this.showSubCommentReplyId = ''
      this.commentStatus = '1'
      getComment(blogId, start, refreshFlag).then(resp => {
        if (this.commentStart === 0 || refreshFlag) {
          this.blogComments = resp.data.comments
        } else {
          this.blogComments = this.blogComments.concat(resp.data.comments)
        }
        this.commentStart += 3
        if (resp.data.hasMore) {
          this.commentStatus = '2'
        } else {
          this.commentStatus = '3'
        }
      })
    },

    testInterface() {
      getUserProfile().then(resp => {
        console.log('resp:', resp)
      })
    }

  }
};


</script>

<style>
.hover-pointer:hover {
  cursor: pointer;
}

.comment-container {
  padding-left: 30px;
  padding-right: 30px;
  padding-bottom: 30px;
  display: flex;
  flex-wrap: wrap;
}

.post-sub-title-unit {
  margin-right: 30px;
  float: left
}

.post-sub-title {
  padding-top: 10px;
  padding-bottom: 30px;
  margin-bottom: 30px;
  background-color: #EEF1F6FF
}

.fixed-box-left {
  background-color: rgb(238, 241, 246);
  padding-top: 30px;
  padding-left: 30px;
  padding-right: 0;
  width: 215px;
  position: fixed;
  z-index: 100;
}

.fixed-box-right {
  background-color: rgb(238, 241, 246);
  position: fixed;
  margin-left: 1650px;
  margin-top: 30px
}

.recommended-post {
  background-color: #EEF1F6FF;
  padding-left: 230px;
  padding-right: 330px;
  float: left
}

.card-button {
  float: left;
  width: 50%;
  text-align: center;
  margin-top: 30px;
  margin-bottom: 20px;
}

.card-content {
  float: left;
  width: 100%;
  border-bottom: 1px solid rgb(238, 241, 246);
  padding-left: 40px;
  padding-right: 40px;
  padding-bottom: 20px;
}
</style>
