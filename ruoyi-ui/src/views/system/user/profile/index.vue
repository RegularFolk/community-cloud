<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card class="box-card" v-loading="infoCardLoading">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <el-tabs v-model="activeTab">

            <el-tab-pane label="基本信息" name="basicInfo">
              <keep-alive>
                <UserBasicInfo ref="userBasicInfo"/>
              </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="修改资料" name="userinfo" v-if="user.userId == loginUser.userId">
              <keep-alive>
                <userInfo :user="user"/>
              </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="修改密码" name="resetPwd" v-if="user.userId == loginUser.userId">
              <keep-alive>
                <resetPwd/>
              </keep-alive>
            </el-tab-pane>

          </el-tabs>

          <div v-if="user.userId != loginUser.userId" style="display: flex;justify-content: center">
            <el-tooltip v-if="!followed" class="item" content="点击以关注" effect="dark" placement="top">
              <el-button type="success" @click="subUser">关注</el-button>
            </el-tooltip>
            <el-tooltip v-if="followed" class="item" content="点击取消关注" effect="dark" placement="top">
              <el-button type="danger" @click="unSubUser">已关注</el-button>
            </el-tooltip>
          </div>
        </el-card>
      </el-col>

      <el-col :span="18" :xs="24">
        <el-card v-loading="blogCardLoading">
          <div slot="header" class="clearfix">
            <span>最新动态</span>
          </div>
          <keep-alive>
            <BlogReleased ref="blogReleased" @getLatestBlog="latestBlog"/>
          </keep-alive>
        </el-card>
      </el-col>

    </el-row>

    <el-row :gutter="20" style="margin-top: 30px">
      <el-col :xs="24">
        <el-card v-loading="articleCardLoading">
          <div slot="header" class="clearfix">
            <span>随笔</span>
          </div>
          <keep-alive>
            <ArticleReleased ref="articleReleased"/>
          </keep-alive>
        </el-card>
      </el-col>

    </el-row>

  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import userInfo from "./userInfo";
import resetPwd from "./resetPwd";

import {changeUserFollow, getUserProfile, getUserProfileById} from "@/api/system/user";
import BlogReleased from "@/views/system/user/profile/blogReleased";
import {getBlogList} from "@/api/biz/blog";
import {getPersonArticle} from "@/api/biz/article"
import ArticleReleased from "@/views/system/user/profile/articleReleased";
import UserBasicInfo from "@/views/system/user/profile/userBasicInfo";

export default {
  name: "Profile",
  components: {UserBasicInfo, ArticleReleased, BlogReleased, userAvatar, userInfo, resetPwd},
  data() {
    return {
      // 当前用户
      user: {},
      // 查看的用户
      loginUser: {},
      activeTab: "basicInfo",
      infoCardLoading: false,
      blogCardLoading: false,
      articleCardLoading: false,
      followed: false
    };
  },
  activated() {
    let routeUserId = this.$route.query.userId;
    let userId = this.user.userId;
    let loginUserId = this.loginUser.userId;
    if ((routeUserId && userId != routeUserId)
      || (!routeUserId && userId != loginUserId)) {
      console.log('this.$route.query.userId', this.$route.query.userId)
      console.log('this.user.userId', userId)
      this.initUser()
    }
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
    console.log('created!!!!!')
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
      }).finally(() => {
        this.articleCardLoading = false
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
    // 关注用户
    subUser() {
      let dto = {
        followId: this.user.userId,
        operateType: 1
      }
      this.submitSub(dto)
    },
    // 取消关注
    unSubUser() {
      this.$confirm('确定要取消关注吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let dto = {
          followId: this.user.userId,
          operateType: 2
        }
        this.submitSub(dto)
      })

    },
    // 提交关注请求
    submitSub(dto) {
      changeUserFollow(dto).then(resp => {
        if (resp.code === 200) {
          this.$message({
            message: '操作成功！',
            type: 'success'
          })
          this.followed = !this.followed
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })
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
      }).finally(() => {
        this.blogCardLoading = false
      })
    },
    getUser() {
      this.infoCardLoading = true
      this.blogCardLoading = true
      this.articleCardLoading = true
      getUserProfile().then(response => {

        this.loginUser = response.data;
        this.$refs.userBasicInfo.loginUser = this.loginUser

        this.initUser()
      })
    },
    initUser() {
      this.infoCardLoading = true
      this.blogCardLoading = true
      this.articleCardLoading = true
      // 如果路由没有携带userId或者携带的用户Id与登录相等，不需要额外请求
      if (!this.$route.query.userId || this.$route.query.userId === '0' || this.$route.query.userId === this.loginUser.userId) {
        this.user = this.loginUser
        this.$refs.userBasicInfo.user = this.user

        this.infoCardLoading = false

        this.initRestCards()
      } else {
        getUserProfileById({id: this.$route.query.userId}).then(resp => {
          if (resp.code === 200) {
            this.user = resp.data
            this.$refs.userBasicInfo.user = this.user
            this.infoCardLoading = false

            this.followed = resp.followed

            this.$refs.userBasicInfo.$refs.userAvatar.options.img = this.user.avatar

            this.initRestCards()
          } else {
            this.$message({
              message: resp.msg,
              type: 'error'
            })
          }
        })
      }
    },
    initRestCards() {
      /* 最新想法和随笔的初始化放在getUser方法内部，原因是getUser这个请求是异步的，拿出来可能还是来不及初始化 */
      this.$refs.blogReleased.user = this.user
      this.initLatestBlog()

      this.$refs.articleReleased.userId = this.user.userId
      this.initArticleReleased()
    },
  },
  watch: {
    // 处理参数不一致路由不跳转的问题
    '$route'(to, from) {
      if (to.query.userId !== from.query.userId) {
        this.initUser()
      }
    }
  }
};
</script>
