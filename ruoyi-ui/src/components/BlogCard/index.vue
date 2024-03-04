<template>
  <div v-show="blogs.length > 0" v-loading="loadingPost" class="recommended-post">
    <el-main>
      <el-card v-for="blog in this.blogs" :key="blog.blogId" shadow="hover" style="margin-bottom: 20px">
        <div slot="header" class="clearfix">
          <div style="width: 60px; float: left;cursor: pointer" @click="routeToProfile(blog.authorId)">
            <el-avatar :size="50" :src="blog.avatar"/>
          </div>
          <div style="float: left;margin-left: 10px">
            <div>{{ blog.senderName }}</div>
            <div style="margin-top: 10px">{{ blog.releaseTime }}</div>
          </div>
          <el-button v-if="queryParam.queryMode === 1" style="float: right; padding: 10px" type="primary"
                     @click="testMethod()">关注
          </el-button>
          <el-button v-if="queryParam.queryMode === 2" style="float: right; padding: 10px" type="danger"
                     @click="removeBlog(blog.blogId)">删除
          </el-button>
        </div>
        <div class="card-content">
          <div style="margin-bottom: 10px;padding: 10px;border-bottom: 1px solid #EEF1F6FF">
            {{ blog.preview }}
          </div>

          <BlogPicWall :enable-preview="false" :pic-url-list="blog.picUrlList"/>
        </div>

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

    <!-- 想法详情弹窗 -->
    <el-dialog v-if="this.showPostDetail" :before-close="handleClose" :close-on-click-modal="false"
               :visible.sync="this.showPostDetail"
               append-to-body title="推文详情">
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
          <div style="margin-bottom: 10px;padding: 10px;border-bottom: 1px solid #EEF1F6FF">
            {{ this.showBlog.preview }}
          </div>

          <BlogPicWall :enable-preview="true" :pic-url-list="showBlog.picUrlList"/>


        </div>

        <BlogComment :article-id="showBlog.blogId" @dialogClose="dialogClose"/>

      </div>
    </el-dialog>

  </div>
</template>


<script>

import BlogPicWall from "@/components/BlogPicWall";
import {deleteBlog, getBlogDetail, getBlogList, like} from "@/api/biz/blog";
import BlogComment from "@/components/BlogComment";

export default {
  name: 'BlogCard',
  components: {BlogPicWall, BlogComment},
  props: {
    queryParam: {
      type: Object,
      default: function () {
        return {
          queryMode: 1,
          pageNum: 1,
          pageSize: 8
        }
      }
    }
  },
  data() {
    return {
      blogs: [
        // {
        //   authorFollowed: '',
        //   avatar: '',
        //   blogId: '',
        //   commentCnt: '',
        //   likeCnt: '',
        //   preview: '',
        //   releaseTime: '',
        //   senderName: '',
        //   viewCnt: '',
        //   liked: false,
        //   picUrlList: []
        // }
      ],
      likeButtonLoading: false,
      showBlog: {},
      showPostDetail: false,
      loadingPost: false,
    }
  },
  created() {
  },
  methods: {
    // 关闭详情弹窗
    dialogClose() {
      this.showPostDetail = false
    },
    routeToProfile(authorId) {
      this.$router.push({
        path: '/user/profile',
        query: {
          userId: authorId
        }
      })

    },
    testMethod(msg) {
      this.$message({
        message: '获得响应！\n' + msg,
        type: 'success'
      })
    },
    // 重新加载随笔
    reloadBlog() {
      this.loadingPost = true
      let dto = {
        pageSize: this.queryParam.pageSize * this.queryParam.pageNum,
        pageNum: 1,
        queryMode: this.queryParam.queryMode
      }

      getBlogList(dto).then(resp => {
        if (resp.code !== 200) {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        } else {
          this.blogs = resp.data
        }
      }).finally(() => {
        this.loadingPost = false
      })

    },
    // 删除个人随笔
    removeBlog(blogId) {
      this.$confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let dto = {
          id: blogId
        }
        deleteBlog(dto).then(resp => {
          if (resp.code === 200) {
            this.reloadBlog()
          } else {
            this.$message({
              message: resp.msg,
              type: 'error'
            })
          }
        })
      })
    },
    // 查看随笔详情
    showPost(blogId) {
      getBlogDetail(blogId).then(resp => {
        if (resp.code === 200) {
          let blog = this.blogs.find(u => u.blogId === blogId)
          blog.viewCnt += 1

          this.showBlog.blogId = blog.blogId
          this.showBlog.authorFollowed = blog.authorFollowed
          this.showBlog.avatar = blog.avatar
          this.showBlog.releaseTime = blog.releaseTime
          this.showBlog.senderName = blog.senderName
          this.showBlog.liked = blog.liked

          this.showBlog.viewCnt = resp.data.viewCnt
          this.showBlog.likeCnt = resp.data.likeCnt
          this.showBlog.commentCnt = resp.data.commentCnt
          this.showBlog.preview = resp.data.content
          this.showBlog.picUrlList = resp.data.picUrlList
          this.showPostDetail = true
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })
    },
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
    // 关闭随笔详情框
    handleClose() {
      this.showPostDetail = false
    },
  },


}


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
