<template>

  <div class="app-container">

    <div style="display: flex">

      <!-- 左侧面包屑、卡片列表、分页 -->
      <div style="width: 70%; margin-right: 100px;">
        <el-tabs v-model="queryParam.listType" :before-leave="changeTab">
          <el-tab-pane :name="'2'" label="猜你喜欢"/>
          <el-tab-pane :name="'5'" label="最新发布"/>
          <el-tab-pane :name="'4'" label="热门课程"/>
          <el-tab-pane :name="'6'" label="我的课程"/>
        </el-tabs>

        <!-- 课程卡片列表 -->
        <div v-loading="courseLoading" style="display: flex; flex-wrap: wrap; margin-top: 20px">

          <el-popover
            :key="course.courseId"
            placement="top"
            trigger="hover"
            v-for="course in courseList"
            width="200"
            :disabled="queryParam.listType !== '6'"
            style="width: 30%; margin-right: 3%; height: 280px; margin-bottom: 3%">
            <el-card
              slot="reference"
              shadow="hover"
            >
              <div style="display: flex; width: 100%">

                <!-- 课程封面 -->
                <image-preview :height="250" :src="course.coverPic" style="width: 30%; margin-right: 10px;"/>

                <!-- 右侧课程信息 -->
                <div style="width: 70%;">

                  <!-- 标题 -->
                  <div :title="course.title" class="course-title" @click="routeToCourse(course.courseId)">
                    {{ course.title }}
                  </div>

                  <div style="display: flex; margin-bottom: 5px">
                    <div class="course-cnt-info">
                      {{ course.viewCnt + '人已学习' }}
                    </div>
                    <div class="course-cnt-info">
                      {{ course.collectCnt + '人 收藏' }}
                    </div>
                    <div class="course-cnt-info">
                      {{ course.likeCnt + '人 点赞' }}
                    </div>

                  </div>

                  <!-- 字数上限150 -->
                  <div :title="course.title" class="course-desc">
                    {{ course.desc }}
                  </div>

                  <!-- 底部作者信息 -->
                  <div style="display: flex; justify-content: right; margin-top: 10px">

                    <div style="display: flex; align-items: center; margin-right: 15px; font-size: 14px">
                      {{ course.createTime }}
                    </div>

                    <div style="display: flex; align-items: center; margin-right: 15px; font-size: 14px">
                      {{ course.userBasicInfo.nickName }}
                    </div>

                    <el-popover
                      placement="right"
                      trigger="hover"
                      width="400">
                      <el-avatar slot="reference" :src="course.userBasicInfo.avatar" shape="circle"/>
                      <UserBasicInfo :hover-show="true" :user-injected="course.userBasicInfo"/>
                    </el-popover>
                  </div>

                </div>

              </div>

            </el-card>

            <div style="display: flex; justify-content: space-between">
              <span><el-button type="primary" @click="editCourse(course.courseId)">编辑</el-button></span>
              <span><el-button type="danger" @click="delCourse(course.courseId)">删除</el-button></span>
            </div>

          </el-popover>

        </div>

        <!-- 分页组件 -->
        <pagination
          :limit="queryParam.pageSize"
          :page="queryParam.pageNum"
          :total="total"
          @pagination="handleQueryPagination"
          :page-sizes="[9]"
        />


      </div>

      <!-- 右侧发布课程按钮、热榜 -->
      <div style="width: 25%; height: 1000px">

        <!-- 搜索框 -->
        <div style="margin-top: 10%; display: flex">
          <div style="width: 70%">
          <el-input
            placeholder="请输入课程标题"
            prefix-icon="el-icon-search"
            v-model="queryParam.title"/>
          </div>
          <div style="margin-left: 20px">
            <el-button type="warning" @click="titleSearch">搜索</el-button>
          </div>
        </div>

        <el-card style="margin-bottom: 20px; margin-top: 5%">
          <div slot="header" class="hot-rank-name">
            7日热门榜
          </div>
          <div class="hot-rank">
            <div v-for="(item, index) in hotList" :key="item.courseId" class="rank-item">
              <el-button class="hot-rank-title" type="text" @click="routeToCourse(item.courseId)">
                <span class="hot-rank-num">
                  {{ index + 1 }}
                </span>
                <span>{{ item.title }}</span>
              </el-button>
            </div>

            <div v-if="hotList.length === 0">
              <h3>暂无内容</h3>
            </div>
          </div>

        </el-card>

        <!-- 发布课程按钮 -->
        <div style="margin-top: 15%; display: flex; justify-content: center;">
          <el-button round type="success" @click="routeToCourseEdit">发布我的课程</el-button>
        </div>

      </div>

    </div>

  </div>

</template>

<script>

import UserBasicInfo from "@/views/system/user/profile/userBasicInfo";
import ArticleRank from "@/views/biz/article/articleRank";
import {delCourse, list} from "@/api/biz/vod";

export default {
  name: 'Course',
  components: {ArticleRank, UserBasicInfo},
  data() {
    return {
      courseLoading: false,
      queryParam: {
        pageSize: 9,
        pageNum: 1,
        listType: '2',
        title: ''
      },
      total: 100,

      courseList: [],
      hotList: []

    }
  },
  created() {

    // this.testCourseListData()
    this.handleQuery()
    this.initTestHotRank()

  },
  methods: {
    changeTab(nName, oldName) {
    },
    // 进入编辑课程页面
    editCourse(courseId) {
      this.$router.push({
        path: '/courses/edit',
        query: {
          courseId: courseId
        }
      })
    },
    // 跳转到课程编辑页面
    routeToCourseEdit() {
      this.$router.push({
        path: '/courses/create',
        query: {}
      })
    },
    // 跳转到课程详情
    routeToCourse(courseId) {
      this.$router.push({
        path: '/courses/play',
        query: {
          courseId: courseId
        }
      })
    },
    // 删除课程
    delCourse(courseId) {
      this.$confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delCourse({id: courseId}).then(resp => {
          if (resp.code === 200) {
            this.$message({
              message: '删除成功！',
              type: 'success'
            })
            this.handleQuery()
          } else {
            this.$message({
              message: resp.msg,
              type: 'error'
            })
          }
        })
      })
    },
    // 标题搜索
    titleSearch() {
      this.queryParam.pageNum = 1
      this.handleQuery()
    },
    handleQueryPagination(args) {
      this.queryParam.pageNum = args.page
      this.queryParam.pageSize = args.limit
      this.handleQuery()
    },
    // 查询
    handleQuery() {
      this.courseLoading = true
      list(this.queryParam).then(resp => {
        if (resp.code === 200) {
          this.courseList = resp.data
          this.total = resp.total
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.courseLoading = false
      })
    },
    // 测试数据
    testCourseListData() {
    },
    initTestHotRank() {
      this.hotList = [
        {
          courseId: '1',
          title: '热门课程1',
        },
        {
          courseId: '12',
          title: '热门课程12',
        },
        {
          courseId: '13',
          title: '热门课程13',
        },
        {
          courseId: '14',
          title: '热门课程14',
        },
        {
          courseId: '15',
          title: '热门课程15',
        },
        {
          courseId: '16',
          title: '热门课程16',
        },
        {
          courseId: '17',
          title: '热门课程17',
        },
        {
          courseId: '18',
          title: '热门课程18',
        },
        {
          courseId: '19',
          title: '热门课程19',
        },
        {
          courseId: '10',
          title: '热门课程10',
        },

      ]
    }
  },
  watch: {
    'queryParam.listType': {
      handler(nVal) {
        this.queryParam.pageNum = 1
        this.handleQuery()
      }
    }
  }
}


</script>

<style>
.course-cnt-info {
  padding-left: 8px;
  padding-right: 8px;
  border-right: #b2b2b2 solid 1px;
  color: #b2b2b2;
  font-size: 14px;
  font-weight: 400;
}

.course-title {
  color: #4C4C4C;
  font-size: 18px;
  font-weight: 500;
  margin-bottom: 5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  cursor: pointer;
}

.course-desc {
  background-color: #f2f3f6;
  color: #888888;
  font-size: 14px;
  font-weight: 400;
  overflow: hidden;
  text-overflow: ellipsis;
  height: 60%;
  width: 100%;
}

.hot-rank-name {
  font-size: 25px;
  font-weight: 500;
  color: #bd5c5e;
}

.hot-rank-title {
  font-size: 20px;
  padding-bottom: 20px;
  padding-top: 20px;
  display: flex;
  align-items: center
}

.hot-rank-num {
  height: 34px;
  font-size: 23px;
  font-weight: 700;
  color: #b2b2b2;
  padding-right: 20px;
}

.hot-rank .rank-item:first-child .hot-rank-num {
  color: #ff7155;
}

.hot-rank .rank-item:nth-of-type(2) .hot-rank-num {
  color: #ffc51d;
}

.hot-rank .rank-item:nth-of-type(3) .hot-rank-num {
  color: #a78b3d;
}


</style>
