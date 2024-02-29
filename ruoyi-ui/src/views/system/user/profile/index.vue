<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <el-tabs v-model="activeTab">

            <el-tab-pane label="基本信息" name="basicInfo">
              <div>
                <div class="text-center">
                  <userAvatar/>
                </div>
                <ul class="list-group list-group-striped">
                  <li class="list-group-item">
                    <svg-icon icon-class="user"/>
                    用户名称
                    <div class="pull-right">{{ user.userName }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="phone"/>
                    手机号码
                    <div class="pull-right">{{ user.phonenumber }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="email"/>
                    用户邮箱
                    <div class="pull-right">{{ user.email }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="tree"/>
                    所属部门
                    <div v-if="user.dept" class="pull-right">{{ user.dept.deptName }} / {{ postGroup }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="peoples"/>
                    所属角色
                    <div class="pull-right">{{ roleGroup }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="date"/>
                    创建日期
                    <div class="pull-right">{{ user.createTime }}</div>
                  </li>
                </ul>
              </div>
            </el-tab-pane>

            <el-tab-pane label="修改资料" name="userinfo">
              <userInfo :user="user"/>
            </el-tab-pane>

            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd/>
            </el-tab-pane>

          </el-tabs>
        </el-card>
      </el-col>

      <el-col :span="18" :xs="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>最新动态</span>
          </div>

          <BlogReleased ref="blogReleased" @getLatestBlog="latestBlog"/>

        </el-card>
      </el-col>

    </el-row>

    <el-row :gutter="20" style="margin-top: 30px">
      <el-col :xs="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>随笔</span>
          </div>
          <ArticleReleased ref="articleReleased"/>
        </el-card>
      </el-col>

    </el-row>

  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import userInfo from "./userInfo";
import resetPwd from "./resetPwd";

import {getUserProfile} from "@/api/system/user";
import BlogReleased from "@/views/system/user/profile/blogReleased";
import {getBlogList} from "@/api/biz/blog";
import {getPersonArticle} from "@/api/biz/article"
import ArticleReleased from "@/views/system/user/profile/articleReleased";

export default {
  name: "Profile",
  components: {ArticleReleased, BlogReleased, userAvatar, userInfo, resetPwd},
  data() {
    return {
      user: {},
      roleGroup: {},
      postGroup: {},
      activeTab: "basicInfo",
    };
  },
  created() {
    /*
    * user 在父组件 created 时初始化，但是子组件又依赖 user 进行初始化
    * 使用 props 注入会来不及，父组件还没初始化好子组件就拿去了
    *
    * 所以，对于子组件中需要父组件值的部分，在父组件初始化好后手动 $refs 设置到子组件中
    * 如果子组件在初始化时就需要父组件的值，那么使这部分由父组件代为初始化再传进子组件
    *
    * （父子组件的通信：父组件往子组件中传：props或者$refs；子组件往父组件中传：$emit）
    * */

    this.getUser();
  },
  methods: {
    // 初始化随笔
    initArticleReleased() {
      let dto = {
        userId: this.user.userId,
        pageNum: this.$refs.articleReleased.pageNum,
        pageSize: this.$refs.articleReleased.pageSize
      }
      getPersonArticle(dto).then(resp => {
        if (resp.code === 200) {
          this.$refs.articleReleased.articleList = resp.data.list
          this.$refs.articleReleased.total = resp.data.total
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })
    },
    // 初始化最新想法
    initLatestBlog() {
      let queryParam = {
        pageNum: 1,
        pageSize: 5,
        queryMode: 3,
        userId: this.user.userId
      }

      this.latestBlog(queryParam)
    },
    latestBlog(queryParam) {
      queryParam.userId = this.user.userId
      getBlogList(queryParam).then(resp => {
        if (resp.code === 200) {
          this.$refs.blogReleased.$refs.blogCard.blogs = resp.data
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })
    },
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;

        /* 最新想法和随笔的初始化放在getUser方法内部，原因是getUser这个请求是异步的，拿出来可能还是来不及初始化 */
        this.$refs.blogReleased.user = this.user
        this.initLatestBlog()

        this.$refs.articleReleased.userId = this.user.userId
        this.initArticleReleased()

        this.roleGroup = response.roleGroup;
        this.postGroup = response.postGroup;
      });
    },
  }
};
</script>
