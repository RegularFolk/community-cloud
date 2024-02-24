<template>
  <div style="background-color: #EEF1F6FF">

    <!-- 左侧导航栏 -->
    <div class="fixed-box-left">
      <div style="background-color: white">
        <el-menu v-if="!loadingPost" default-active="1">
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
    </div>

    <!-- 右侧按钮 -->
    <div style="position: fixed;top: 100px;right: 60px">

      <div style="margin: 10px">
        <div style="margin: 10px">
          <el-button icon="el-icon-edit" type="primary" @click="showDrawer">发布想法</el-button>
        </div>

        <div style="margin: 10px">
          <el-button icon="el-icon-edit" type="warning" @click="showDrawer">想法管理</el-button>
        </div>

      </div>

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
          <div v-if="!blog.liked" class="card-button" @click="blogLike(blog.blogId)">
            <el-badge :value="blog.likeCnt" type="danger">
              <el-button :loading="likeButtonLoading" icon="el-icon-thumb" type="danger">点赞</el-button>
            </el-badge>
          </div>

          <div v-if="blog.liked" class="card-button" @click="blogLikeCancel(blog.blogId)">
            <el-badge :value="blog.likeCnt" type="success">
              <el-button :loading="likeButtonLoading" icon="el-icon-thumb" type="success">已点赞</el-button>
            </el-badge>
          </div>

        </el-card>
      </el-main>
    </div>

    <!-- 想法详情弹窗 -->
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
          {{ this.showBlog.preview }}
        </div>

        <BlogComment :article-id="showBlog.blogId"/>

      </div>
    </el-dialog>

    <!-- 编辑想法抽屉 -->
    <el-drawer
      :visible.sync="drawerVisible"
      direction="btt"
      size="50%"
      title="此时此刻，向小伙伴分享您的心情吧~~"
    >
      <div class="app-container">
        <el-input
          v-model="newBlogInput"
          :autosize="{ minRows: 3}"
          maxlength="500"
          placeholder="字数请控制在500字以内"
          show-word-limit
          style="margin-bottom: 10px"
          type="textarea"
        >
        </el-input>

        <el-upload
          ref="picUpload"
          :action="uploadUrl"
          :auto-upload="false"
          :before-upload="handleBeforeUpload"
          :file-list="picList"
          :headers="headers"
          :limit="fileUploadLimit"
          :on-change="fileAppended"
          :on-error="picUploadFailed"
          :on-remove="handleRemove"
          :on-success="picUploadSuccess"
          list-type="picture"
          style="width: 20%"
        >
          <el-button slot="trigger" :disabled="uploadDisabled" size="medium" style="margin-right: 20px" type="primary">
            上传图片
          </el-button>
          <el-button size="medium" type="success" @click="submitBlog">发表想法</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过{{ this.picSizeLimit }}MB，最多上传9张图片</div>
        </el-upload>

      </div>
    </el-drawer>


  </div>
</template>


<script>

import {getToken} from "@/utils/auth";
import {getTestBlogs, like, submitBlog, testMq} from "@/api/biz/blog";
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
          viewCnt: '',
          liked: false
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
      likeButtonLoading: false,
      drawerVisible: false,
      newBlogInput: '',
      uploadUrl: process.env.VUE_APP_BASE_API + "/file/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken()
      },
      fileUploadLimit: 9,
      uploadDisabled: false,
      picList: [],
      picSizeLimit: 5, // 图片上传大小限制，单位MB


      showPostDetail: false,
      showBlog: {},
    }
  },
  created() {
    this.getTestBlogList()
  },
  methods: {
    // 提交点赞请求
    postLike(dto) {
      this.likeButtonLoading = true
      like(dto).then(resp => {
        if (resp.code === 200) {
          // this.article.liked = !this.article.liked
          this.blogs.forEach(u => {
            if (u.blogId === dto.blogId) {
              u.liked = !u.liked
              if (dto.operateType === 1) {
                u.likeCnt += 1
              } else if (dto.operateType === 2) {
                u.likeCnt -= 1
              }
            }
          });
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
    // 点赞
    blogLike(blogId) {
      let likeDto = {
        blogId: blogId,
        operateType: 1
      }
      this.postLike(likeDto)
    },
    // 取消点赞
    blogLikeCancel(blogId) {
      this.$confirm('要取消点赞吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let likeDto = {
          blogId: blogId,
          operateType: 2
        }
        this.postLike(likeDto)
      })
    },
    // 图片上传前校验
    handleBeforeUpload(file) {
      const type = ["image/jpeg", "image/jpg", "image/png"];
      const isJPG = type.includes(file.type);
      // 检验文件格式
      if (!isJPG) {
        this.$message.error(`图片格式错误!`);
        return false;
      }
      // 校检文件大小
      if (this.picSizeLimit) {
        const isLt = file.size / 1024 / 1024 < this.picSizeLimit;
        if (!isLt) {
          this.$message.error(`上传文件大小不能超过 ${this.picSizeLimit} MB!`);
          return false;
        }
      }
      return true;
    },
    // 提交发布想法的请求
    doSubmitBlog(dto) {
      submitBlog(dto).then(resp => {
        if (resp.code === 200) {
          this.$message({
            message: '发布成功！',
            type: 'success'
          })
          this.newBlogInput = ''
          this.picList = []
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })

      this.drawerVisible = false
    },
    // 图片上传成功的回调
    picUploadSuccess(response, file, fileList) {
      this.picList = fileList

      let urlList = this.picList.map(u => u.response.data.url)
      let urlStr = urlList.join(',')

      let dto = {
        content: this.newBlogInput,
        picUrls: urlStr
      }

      this.doSubmitBlog(dto)

    },
    // 图片上传失败的回调
    picUploadFailed(err, file, fileList) {
      this.$message({
        message: '图片上传失败！请稍后再试或者联系管理员',
        type: 'error'
      })
      console.log('图片上传失败！err,file,fileList', err, file, fileList)
    },
    // 发表想法，首先上传图片，图片上传完毕后整理URL，连带着文字内容提交后端
    submitBlog() {
      let hasPic = this.picList.length !== 0;
      if (!hasPic && this.newBlogInput.trim().length === 0) {
        this.$message({
          message: '无法提交空内容！',
          type: 'warning'
        })
        return
      }

      if (hasPic) {
        // 调用图片上传，后续流程在回调中实现
        this.$refs.picUpload.submit()
      } else {
        let dto = {
          content: this.newBlogInput,
          picUrls: ''
        }

        this.doSubmitBlog(dto)
      }
    },
    // 文件数目变化时的钩子
    fileAppended(file, fileList) {
      this.picList = fileList
      console.log('picList', this.picList)
      this.uploadDisabled = fileList.length === this.fileUploadLimit
    },
    handleRemove(file, fileList) {
      this.fileAppended(file, fileList)
    },
    // 后端接口测试方法
    testInterface() {
      testMq()

      this.$message({
        message: '测试接口已发送请求！',
        type: 'success'
      })
    },
    // 打开编辑想法抽屉
    showDrawer() {
      this.drawerVisible = true
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

/* 容器内元素水平垂直居中 */
.all-center {
  display: flex;
  justify-content: center;
  align-items: center
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
