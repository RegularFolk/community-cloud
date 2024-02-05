<template>
  <div style="background-color: #EEF1F6FF">
    <div class="fixed-box-left">
      <el-menu default-active="1">
        <el-menu-item index="1" @click="getTestBlogList()">
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

    <div class="recommended-post">
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
          <!--          <div class="card-button">
                      <el-badge :value="666" type="warning">
                        <el-button icon="el-icon-edit" type="warning">评论</el-button>
                      </el-badge>
                    </div>-->
          <div class="card-button" @click="testMethod()">
            <el-badge :value="blog.likeCnt" type="danger">
              <el-button icon="el-icon-thumb" type="danger">点赞</el-button>
            </el-badge>
          </div>
        </el-card>
      </el-main>
    </div>


    <el-dialog :before-close="handleClose" :close-on-click-modal="false" :visible.sync="this.showPostDetail"
               title="推文详情">
      <!--   标题下灰色副标题区域   -->
      <div class="post-sub-title">
        <div class="post-sub-title-unit">
          <i class="el-icon-user" style="padding-left: 10px"> {{ this.showBlog.senderName }}</i>
        </div>
        <div class="post-sub-title-unit"><i class="el-icon-timer"> 于 {{ this.showBlog.releaseTime }} 发布</i></div>
        <div class="post-sub-title-unit"><i class="el-icon-view"> 浏览量:{{this.showBlog.viewCnt}}</i></div>
        <div class="post-sub-title-unit"><i class="el-icon-thumb"> 点赞:{{ this.showBlog.likeCnt }}</i></div>
        <div class="post-sub-title-unit"><i class="el-icon-chat-line-round"> 评论数:{{this.showBlog.commentCnt}}</i></div>
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
          <el-button round type="primary" @click="testMethod()">回复</el-button>
        </div>

        <div style="width: 100%">
          <!--   母评论列表     -->
          <div v-for="pComment in this.testComment.content"
               style="display: flex;flex-wrap: wrap;padding-top: 20px;border-bottom: 1px solid #EEF1F6FF">
            <!-- 左部头像框 -->
            <div style="margin-right: 10px">
              <el-avatar :size="35" src="http://121.43.39.16:8082/ruoyi-test/2024/02/02/2b_20240202165949A006.jpg"/>
            </div>

            <!-- 右部框,分为两部分，上部分的回复者、时间信息、点赞按钮等，下部分的回复内容 -->
            <div style="width: 95%">
              <div style="margin-bottom: 10px;display: flex;flex-wrap: wrap">
                <div style="margin-right: 10px">{{ pComment.senderName }}</div>
                <div>{{ pComment.sendTime }}</div>
                <div class="hover-pointer" style="margin-left: 60%"
                     @click="replySubComment(pComment.commentId, '',true)">
                  <i class="el-icon-chat-line-square"/>
                  <span style="margin-left: 5px">回复</span>
                </div>
                <div class="hover-pointer" style="margin-left: 20px" @click="testMethod()">
                  <i class="el-icon-thumb"/>
                  <span style="margin-left: 5px">{{ pComment.likeCnt }}</span>
                </div>
              </div>
              <div style="margin-bottom: 10px">
                {{ pComment.content }}
              </div>
            </div>

            <!--   每个母评论下的子评论列表       -->
            <div v-for="subComment in pComment.subContents"
                 style="width: 95%;margin-left: 5%;display: flex;flex-wrap: wrap">
              <div style="margin-right: 10px">
                <el-avatar :size="35" src="http://121.43.39.16:8082/ruoyi-test/2024/02/02/2b_20240202165949A006.jpg"/>
              </div>

              <div style="margin-bottom: 10px;display: flex;flex-wrap: wrap">
                <div style="margin-bottom: 10px;display: flex;width: 100%">
                  <div style="margin-right: 10px">{{ subComment.senderName }}</div>
                  <div style="margin-right: 10px">回复</div>
                  <div style="margin-right: 10px">{{ subComment.receiverName }}</div>
                  <div style="margin-right: 10px">{{ subComment.sendTime }}</div>

                  <div class="hover-pointer" @click="replySubComment(pComment.commentId, subComment.commentId, false)">
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
            <div v-if="pComment.commentId === showCommentReplyId" style="width: 100%;display: flex;flex-wrap: wrap">
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
                <el-button round type="primary" @click="testMethod()">回复</el-button>
              </div>
            </div>
          </div>


        </div>


      </div>
    </el-dialog>


  </div>
</template>


<script>


import {getTestBlogs} from "@/api/biz/blog";

export default {
  name: "blog",
  data() {
    return {
      blogs: [],
      testBlogs: [
        {
          blogId:'',
          likeCnt: 0,
          preview: '',
          releaseTime: '',
          senderName: '',
          viewCnt: 0,
          avatar: '',
          id: 1,
          username: 'name1',
          postTime: '2024年2月2日20:41:37',
          content: 'Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。'
        }, {
          id: 2,
          username: 'name2',
          postTime: '2024年2月2日20:41:37',
          content: 'hhhhhhhhhhh'
        }, {
          id: 3,
          username: 'name3',
          postTime: '2024年2月2日20:41:37',
          content: 'hhhhhhhhhhh'
        }, {
          id: 4,
          username: 'name4',
          postTime: '2024年2月2日20:41:37',
          content: 'hhhhhhhhhhh'
        }, {
          id: 5,
          username: 'name5',
          postTime: '2024年2月2日20:41:37',
          content: 'hhhhhhhhhhh'
        }, {
          id: 6,
          username: 'name6',
          postTime: '2024年2月2日20:41:37',
          content: 'hhhhhhhhhhh'
        }, {
          id: 7,
          username: 'name7',
          postTime: '2024年2月2日20:41:37',
          content: 'hhhhhhhhhhh'
        }
      ],
      testComment: {
        cnt: 7,
        content: [
          {
            commentId: 1,
            parentId: -1,
            senderId: '1',
            senderName: '用户一',
            receiverId: '-1',
            receiverName: '',
            sendTime: '2024年2月3日22:33:08',
            likeCnt: 999,
            content: '真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼真牛逼',
            subContents: [
              {
                commentId: 2,
                parentId: 1,
                senderId: '2',
                senderName: '用户二',
                receiverId: '1',
                receiverName: '用户一',
                sendTime: '2024年2月3日22:33:08',
                likeCnt: 999,
                content: '真牛逼'

              }, {
                commentId: 3,
                parentId: 1,
                senderId: '3',
                senderName: '用户三',
                receiverId: '1',
                receiverName: '用户一',
                sendTime: '2024年2月3日22:33:08',
                likeCnt: 999,
                content: '真牛逼'
              }
            ]
          }, {
            commentId: 4,
            parentId: -1,
            senderId: '4',
            senderName: '用户四',
            receiverId: '-1',
            receiverName: '',
            sendTime: '2024年2月3日22:33:08',
            likeCnt: 999,
            content: '真牛逼',
            subContents: [
              {
                commentId: 5,
                parentId: 4,
                senderId: '5',
                senderName: '用户五',
                receiverId: '4',
                receiverName: '用户四',
                sendTime: '2024年2月3日22:33:08',
                likeCnt: 999,
                content: '真牛逼'

              }, {
                commentId: 6,
                parentId: 4,
                senderId: '6',
                senderName: '用户六',
                receiverId: '4',
                receiverName: '用户四',
                sendTime: '2024年2月3日22:33:08',
                likeCnt: 999,
                content: '真牛逼'

              }
            ]
          }, {
            commentId: 7,
            parentId: -1,
            senderId: '7',
            senderName: '用户七',
            receiverId: '-1',
            receiverName: '',
            sendTime: '2024年2月3日22:33:08',
            likeCnt: 999,
            content: '真牛逼',
            subContents: []
          },
        ]
      },
      showPostDetail: false,
      showBlog: {},
      commentInput: '',
      showCommentReplyId: '',
      commentReplyHolder: '评论的回复:',
      commentReplyInput: ''
    }
  },
  created() {
    this.getTestBlogList()
  },
  methods: {
    replySubComment(pCommentId, subCommentId, isParent) {
      this.showCommentReplyId = pCommentId
      let commentName = ''
      let pComment = this.testComment.content.find(u => u.commentId === pCommentId);
      if (isParent) {
        commentName = pComment.senderName
      } else {
        commentName = pComment.subContents.find(u => u.commentId === subCommentId).senderName
      }
      this.commentReplyHolder = '回复 @' + commentName + ':'
    },
    testMethod() {
      this.$message({
        message: '获得响应！',
        type: 'success'
      })
    },
    showPost(blogId) {
      this.showBlog = this.blogs.find(u => u.blogId === blogId)
      this.showPostDetail = true
    },
    handleClose() {
      this.showPostDetail = false
      this.commentInput = ''
      this.showCommentReplyId = ''
    },

    getTestBlogList() {
      getTestBlogs().then(resp => {
        console.log("resp:", resp)
        this.blogs = resp.data
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
  position: fixed
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
