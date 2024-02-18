<template>
  <div style="background-color: #EEF1F6FF">
    <div class="fixed-box-left">
      <el-menu default-active="1" v-if="!loadingPost">
        <el-menu-item index="1" @click="testInterface">
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

        <BlogComment :article-id="showBlog.blogId"/>

      </div>
    </el-dialog>


  </div>
</template>


<script>


import {getTestBlogs, testMq} from "@/api/biz/blog";
import BlogComment from "@/components/BlogComment";

export default {
  name: "Blog",
  components: {BlogComment},
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
    }
  },
  created() {
    this.getTestBlogList()
  },
  methods: {
    // 后端接口测试方法
    testInterface() {
      testMq()

      this.$message({
        message: '测试接口已发送请求！',
        type: 'success'
      })
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
    },

    handleClose() {
      this.showPostDetail = false
    },

    getTestBlogList() {
      this.loadingPost = true
      getTestBlogs().then(resp => {
        this.blogs = resp.data
        this.loadingPost = false
      })

    },



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
