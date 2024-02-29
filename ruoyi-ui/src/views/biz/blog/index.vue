<template>
  <div style="background-color: #EEF1F6FF">

    <!-- 左侧导航栏 -->
    <div v-show="!loadingPost" class="fixed-box-left">
      <div style="background-color: white">
        <el-menu ref="leftMenu" default-active="1">
          <el-menu-item index="1" @click="getDefaultPageList()">
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

    <!-- 右上角按钮 -->
    <div style="position: fixed;top: 100px;right: 60px">

      <div style="margin: 10px">
        <div style="margin: 10px">
          <el-button icon="el-icon-edit" type="primary" @click="showDrawer">发布想法</el-button>
        </div>

        <div style="margin: 10px">
          <el-button :disabled="queryParam.queryMode === 2" icon="el-icon-s-opportunity" type="warning"
                     @click="blogManage">我的想法
          </el-button>
        </div>

      </div>

    </div>

    <!-- 右下角按钮 -->
    <div style="position: fixed;bottom: 100px;right: 60px">
      <el-button v-show="queryParam.queryMode !== 2" :loading="loadingPost" icon="el-icon-refresh" type="danger"
                 @click="getDefaultPageList">换一批
      </el-button>

      <el-tooltip class="item" content="已经到底了哦~~" effect="dark" placement="top" :disabled="personBlogHasMore">
        <el-button v-show="queryParam.queryMode === 2" :disabled="!personBlogHasMore" :loading="loadingPost" icon="el-icon-plus"
                   type="success" @click="blogManageMore">查看更多
        </el-button>
      </el-tooltip>

    </div>

    <BlogCard ref="blogCard"/>

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
          <el-button slot="trigger" :disabled="uploadDisabled" :loading="postBlogLoading" size="medium"
                     style="margin-right: 20px"
                     type="primary">
            上传图片
          </el-button>
          <el-button :loading="postBlogLoading" size="medium" type="success" @click="submitBlog">发表想法</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过{{ this.picSizeLimit }}MB，最多上传9张图片</div>
        </el-upload>

      </div>
    </el-drawer>


  </div>
</template>


<script>

import {getToken} from "@/utils/auth";
import {getBlogList, submitBlog} from "@/api/biz/blog";
import BlogComment from "@/components/BlogComment";
import BlogPicWall from "@/components/BlogPicWall";
import BlogCard from "@/components/BlogCard";

export default {
  name: "Blog",
  components: {BlogCard, BlogPicWall, BlogComment},
  data() {
    return {
      queryParam: {
        pageSize: 8,
        pageNum: 1,
        queryMode: 1 // 查询模式：1：正常模式（暂定），2：我的想法
      },
      personBlogHasMore: true,
      postBlogLoading: false,

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
          liked: false,
          picUrlList: []
        }
      ],
      loadingPost: true,
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

    }
  },
  created() {
    this.getDefaultPageList()
  },
  mounted() {
    this.initBlogCard()
  },
  methods: {
    // 初始化博客卡片
    initBlogCard() {
      this.$refs.blogCard.blogs = this.blogs
      this.$refs.blogCard.queryParam.queryMode = this.queryParam.queryMode
      this.$refs.blogCard.loadingPost = true
    },
    // 个人博客管理查看更多
    blogManageMore() {
      if (!this.personBlogHasMore) {
        this.$message({
          message: '已经到底了哦~',
          type: 'warning'
        })
        return
      }
      this.loadingPost = true
      if (this.queryParam.queryMode !== 2) {
        this.queryParam.pageNum = 1
      }
      this.queryParam.queryMode = 2

      getBlogList(this.queryParam).then(resp => {
        if (resp.code === 200) {
          if (this.queryParam.pageNum === 1) {
            this.blogs = resp.data
          } else {
            this.blogs = this.blogs.concat(resp.data)
          }

          if (resp.data.length === this.queryParam.pageSize) {
            this.queryParam.pageNum++
          } else {
            this.personBlogHasMore = false
          }

        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.loadingPost = false
      })
    },
    // 我的想法管理
    blogManage() {
      // 取消左侧标签选中
      this.$refs.leftMenu.activeIndex = null

      if (this.queryParam.queryMode === 2) {
        return
      }

      this.blogManageMore()

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
      }).finally(() => {
        this.postBlogLoading = false
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
      this.postBlogLoading = false
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

      this.postBlogLoading = true

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
      this.uploadDisabled = fileList.length === this.fileUploadLimit
    },
    handleRemove(file, fileList) {
      this.fileAppended(file, fileList)
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
    // 左侧导航 猜你想看列表查询
    getDefaultPageList() {
      this.personBlogHasMore = true
      this.loadingPost = true
      if (this.queryParam.queryMode !== 1) {
        this.queryParam.pageNum = 1
      }

      this.queryParam.queryMode = 1
      this.queryParam.pageSize = 8

      getBlogList(this.queryParam).then(resp => {
        if (resp.code === 200) {

          this.blogs = resp.data

        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.loadingPost = false
      })

    }


  },
  watch: {
    blogs: {
      handler(nVal) {
        this.$refs.blogCard.blogs = this.blogs

      }
    },
    queryParam: {
      handler(nVal) {
        this.$refs.blogCard.queryParam = this.queryParam
      },
      // deep 监听，可以监听到对象内部值的变化
      deep: true
    },
    loadingPost: {
      handler(nVal) {
        this.$refs.blogCard.loadingPost = this.loadingPost
      }
    }
  }
};


</script>

<style>
/* 设置全局样式为flex */
.flexClass {
  display: flex;
  flex-wrap: wrap;
}

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
