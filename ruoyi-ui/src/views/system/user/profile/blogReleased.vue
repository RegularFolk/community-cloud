<template>
  <div>
    <el-row>
      <BlogCard ref="blogCard" :query-param="queryParam"/>
    </el-row>

    <el-row>
      <div class="all-center" style="margin: 10px">
        <el-tooltip class="item" content="当前用户没有发布想法" effect="dark" placement="bottom" :disabled="!btnDisabled">
          <el-button :disabled="btnDisabled" round type="success" @click="showAllBlog">查看全部动态</el-button>
        </el-tooltip>
      </div>
    </el-row>

    <el-dialog
      :visible.sync="allCardDialog"
      center
      title="全部想法"
      width="80%">

      <BlogCard ref="allBlogCard" :query-param="queryParam" style="margin-bottom: 15px"/>

      <span slot="footer" class="dialog-footer">
        <el-tooltip :disabled="allBlogHasMore" class="item" content="已经到底了哦~~" effect="dark" placement="top">
          <el-button :disabled="!allBlogHasMore" type="warning" @click="allCardShowMore" :loading="loadingPost">查看更多</el-button>
        </el-tooltip>
        <el-button type="primary" @click="allCardDialog = false">关 闭</el-button>
      </span>
    </el-dialog>

  </div>
</template>


<script>

import BlogCard from "@/components/BlogCard";
import {getBlogList} from "@/api/biz/blog";

export default {
  name: 'BlogReleased',
  components: {BlogCard},
  props: {},
  data() {
    return {
      user: {},
      blogs: [],
      queryParam: {
        pageNum: 1,
        pageSize: 5,
        queryMode: 3,
        userId: 0
      },
      loadingPost: false,
      allCardDialog: false,
      allBlogHasMore: true,
      btnDisabled: false
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {
    // 点击 查看全部随笔 按钮
    showAllBlog() {
      this.allCardDialog = true

      this.loadingPost = true

      this.queryParam.queryMode = 4
      this.queryParam.pageNum = 1

      this.submitGetBlog()


    },
    // 查看全部随笔弹窗 查看更多按钮
    allCardShowMore() {
      this.loadingPost = true

      this.submitGetBlog()

    },
    // 提交查看更多随笔请求
    submitGetBlog() {
      this.queryParam.userId = this.user.userId

      getBlogList(this.queryParam).then(resp => {
        if (resp.code === 200) {

          if (this.queryParam.pageNum === 1) {
            this.blogs = resp.data
          } else {
            this.blogs = this.blogs.concat(resp.data)
          }

          this.allBlogHasMore = resp.data.length === this.queryParam.pageSize

          if (this.allBlogHasMore) {
            this.queryParam.pageNum += 1
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

    }
  },
  watch: {
    loadingPost: {
      handler(nVal) {
        // this.$refs.allBlogCard.loadingPost = this.loadingPost
      }
    },
    blogs: {
      handler(nVal) {
        this.$refs.allBlogCard.blogs = this.blogs
      }
    },
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


</style>
