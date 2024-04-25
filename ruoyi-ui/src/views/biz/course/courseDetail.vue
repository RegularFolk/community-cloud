<template>

  <div class="app-container" style="height: 1350px">

    <div style="display: flex; height: 100%">

      <!-- 左侧章节菜单 -->
      <div
        style="height: 100%; width: 25%; margin-right: 5%; border: 1px solid #D956D7;" v-loading="chapterLoading">

        <h1 v-if="!chapterList || chapterList.length === 0">
          当前课程暂无内容！
        </h1>

        <el-menu default-active="1">

          <el-submenu v-for="(item, index) in chapterList" :key="index" :index="'' + index">
            <template slot="title">
              <div style="display: flex; justify-content: space-between;">
                <el-tooltip :content="item.desc" class="item" effect="dark" placement="right">
                  <div style="font-size: 20px; font-weight: 550"><i style="font-size: 25px" class="el-icon-reading"/>{{ item.title }}</div>
                </el-tooltip>
              </div>
            </template>


            <el-menu-item v-for="(v, vIdx) in item.videoList" :key="vIdx" :index="index + '-' + vIdx"
                          @click="playVod(v)">
              <template slot="title">
                <div style="display: flex; justify-content: space-between;">
                  <el-tooltip :content="v.preview" class="item" effect="dark" placement="right">
                    <div style="font-size: 18px"><i style="font-size: 20px" class="el-icon-video-play"/>{{ v.title }}</div>
                  </el-tooltip>
                </div>
              </template>
            </el-menu-item>

          </el-submenu>


        </el-menu>

      </div>

      <!-- 右侧视频播放区域 -->
      <div v-loading="playLoading" style="width: 70%">

        <div v-if="playingVod.id && (!playingVod.videoId || playingVod.videoId.trim().length === 0)">
          <h1>抱歉，该节内容不包含视频！</h1>
        </div>

        <div id="dplayer" style="height: 90%; width: 100%; margin-bottom: 20px"/>

        <!-- 视频下方信息区域 -->
        <div v-show="dp" style="display: flex; height: 100px">
          <div style="margin-right: 20px">
            <el-popover
              placement="left"
              trigger="hover"
              width="400">
              <div slot="reference" style="margin-right: 20px;cursor: pointer"
                   @click="routeToUserProfile(userBasicInfo.id)">
                <el-avatar :src="userBasicInfo.avatar" shape="square" size="large" style="width: 100px; height: 100px"/>
              </div>

              <UserBasicInfo :hover-show="true" :userInjected="userBasicInfo"/>

            </el-popover>
          </div>

          <div style="width: 75%; height: 100px">
            <div style="font-size: 40px; font-weight: 400; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
              {{ playingVod.title }}
            </div>

            <div style="display: flex; justify-content: space-between; margin-top: 20px; font-size: 20px">
              <div style="margin-right: 30px">
                {{ '由 ' + userBasicInfo.nickName + ' 发布于 ' + playingVod.createTime }}
              </div>

              <div style="margin-right: 30px">
                {{'点赞数：' + playingVod.likeCnt}}
              </div>

              <div style="margin-right: 30px">
                {{'浏览数：' + playingVod.viewCnt}}
              </div>

              <div style="margin-right: 30px">
                {{'收藏数：' + playingVod.collectCnt}}
              </div>

            </div>

          </div>

          <div style="margin-left: 30px">
            <comment-btn-dialog :content-id="'' + playingVod.id" style="height: 100%"/>
          </div>

          <div style="margin-left: 30px; display: flex; align-items: center;">
            <el-button
              v-loading="btnLoading"
              v-if="!playingVod.liked"
              icon="el-icon-thumb"
              type="danger"
              @click="changeLike"
            >
              点赞
            </el-button>

            <el-button
              v-loading="btnLoading"
              v-if="playingVod.liked"
              icon="el-icon-check"
              type="danger"
              @click="changeLike"
            >
              已点赞
            </el-button>
          </div>

          <div style="margin-left: 30px; display: flex; align-items: center;">
            <el-button
              v-loading="btnLoading"
              v-if="!playingVod.collected"
              icon="el-icon-star-off"
              type="primary"
              @click="changeCollect"
            >
              收藏
            </el-button>

            <el-button
              v-loading="btnLoading"
              v-if="playingVod.collected"
              icon="el-icon-check"
              type="primary"
            >
              已收藏
            </el-button>
          </div>

        </div>

      </div>

    </div>

  </div>

</template>

<script>

import {courseDetail, getVodUrl, videoCollect} from "@/api/biz/vod";
import UserBasicInfo from "@/views/system/user/profile/userBasicInfo";
import CommentBtnDialog from "@/components/CommentBtnDialog";
import DPlayer from "dplayer";
import {like} from "@/api/biz/blog";

export default {
  name: 'CourseDetail',
  components: {UserBasicInfo, CommentBtnDialog},
  data() {
    return {
      btnLoading: false,
      playLoading: false,
      chapterLoading: false,
      dp: undefined,
      course: {
        courseId: 0,
        title: '',
        coverPic: '',
        createTime: '',
        viewCnt: 0,
        collectCnt: 0,
        commentCnt: 0,
        desc: '',
      },
      chapterList: [],
      userBasicInfo: {
        id: 0,
        avatar: '',
        nickName: '',
        phonenumber: '',
        sex: 0,
        email: '',
        followerCnt: 0,
        subCnt: 0,
        likeCnt: 0,
        createTime: '',
        followed: false
      },
      playingVod: {
        id: '',
        title: '',
        preview: '',
        createTime: '',
        likeCnt: '',
        viewCnt: '',
        collectCnt: '',
        videoId: '',
        liked: false,
        collected: false,
        url: ''
      }
    }
  },
  activated() {
    // 处理页面跳转不刷新的问题
    if (this.course.courseId
      && this.course.courseId != this.$route.query.courseId) {
      this.initCourseDetail()
    }
  },
  created() {
    this.initCourseDetail()
  },
  methods: {
    // 初始化
    initCourseDetail() {
      this.chapterLoading = true
      let courseId = this.$route.query.courseId
      courseDetail(courseId).then(resp => {
        if (resp.code === 200) {
          this.course = resp.data.course
          this.chapterList = resp.data.chapterList
          this.userBasicInfo = resp.data.course.userBasicInfo
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.chapterLoading = false
      })
    },
    // 改变收藏状态
    changeCollect() {
      if (!this.playingVod.collected) {
        this.btnLoading = true
        videoCollect({id: this.playingVod.id}).then(resp => {
          if (resp.code === 200) {
            this.playingVod.collected = true
            this.playingVod.collectCnt++
          } else {
            this.$message({
              message: resp.msg,
              type: 'error'
            })
          }
        }).finally(() => {
          this.btnLoading = false
        })
      }
    },
    // 改变点赞状态
    changeLike() {
      if (this.playingVod.liked) {
        this.$confirm('确定要取消点赞吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.btnLoading = true
          like({blogId: this.playingVod.id, operateType: 2}).then(resp => {
            if (resp.code === 200) {
              this.playingVod.liked = false
              this.playingVod.likeCnt--
            } else {
              this.$message({
                message: resp.msg,
                type: 'error'
              })
            }
          }).finally(() => {
            this.btnLoading = false
          })
        });
      } else {
        this.btnLoading = true
        like({blogId: this.playingVod.id, operateType: 1}).then(resp => {
          if (resp.code === 200) {
            this.playingVod.liked = true
            this.playingVod.likeCnt++
          } else {
            this.$message({
              message: resp.msg,
              type: 'error'
            })
          }
        }).finally(() => {
          this.btnLoading = false
        })
      }
    },
    // 跳转到用户主页
    routeToUserProfile(id) {
      this.$router.push({
        path: '/user/profile',
        query: {
          userId: id
        }
      })
    },
    playVod(v) {
      if (this.playingVod.id == v.id) {
        return
      }
      this.playLoading = true
      this.playingVod = v
    },
    refreshVod() {
      if (this.dp) {
        this.dp.destroy()
      }
      if (this.playingVod.videoId && this.playingVod.videoId.trim().length > 0) {
        getVodUrl(this.playingVod.videoId).then(resp => {
          if (resp.code === 200) {
            this.playingVod.url = resp.msg
            this.dp = new DPlayer({
              container: document.getElementById('dplayer'),
              video: {
                url: this.playingVod.url,
              },
            });
          } else {
            this.$message({
              message: resp.msg,
              type: 'error'
            })
          }
        }).finally(() => {
          this.playLoading = false
        })

      } else if (this.dp) {
        this.dp.destroy()
        this.playLoading = false
      } else {
        this.playLoading = false
      }
    }
  },
  watch: {
    playingVod: {
      deep: true,
      immediate: true,
      handler(nVal) {
        this.refreshVod()
      }
    }
  }
}

</script>
