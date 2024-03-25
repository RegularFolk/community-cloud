<template>
  <div>

    <div v-for="qtn in questionList" v-loading="queryLoading">
      <el-card shadow="never">

        <div style="display: flex">

          <!-- 左侧区域 -->
          <div style="width: 7%;margin-right: 3%">
            <div class="qtn-card-side-left">{{ qtn.likeCnt + '  点赞' }}</div>
            <div class="qtn-card-side-left">{{ qtn.viewCnt + '  浏览' }}</div>
            <div class="qtn-card-side-left">{{ qtn.ansCnt + '  回答' }}</div>
            <div class="qtn-card-side-left">{{ qtn.collectCnt + '  关注' }}</div>
          </div>

          <!-- 右侧区域 -->
          <div style="width: 90%;">
            <div style="margin-bottom: 15px">
              <div class="btn-wrap-text" type="text">
                {{ qtn.title }}
              </div>
            </div>

            <div style="display: flex;justify-content: right">

              <el-popover
                placement="left"
                trigger="hover"
                width="400">
                <div slot="reference" style="cursor: pointer" @click="routeToProfile(qtn.author.id)">
                  <el-avatar :src="qtn.author.avatar" shape="square" style="margin-right: 15px"/>
                </div>

                <UserBasicInfo :hover-show="true" :userInjected="qtn.author"/>

              </el-popover>

              <div style="cursor:pointer;margin-right: 15px;align-self: center;color: #1B75D0"
                   @click="routeToProfile(qtn.author.id)">
                {{ qtn.author.nickName }}
              </div>
              <div style="align-self: center;color:#636B74;">{{ ' 发布于 ' + qtn.publishTime }}</div>
            </div>
          </div>


        </div>

      </el-card>

    </div>

    <!-- 加载更多评论按钮,三种状态:加载更多，加载中，已经到底 -->
    <div style="display: flex;justify-content: center">
      <el-button
        v-if="loadStatus === 1"
        :loading="true"
        type="primary"
      >
        加载中
      </el-button>
      <el-button
        v-if="loadStatus === 2"
        plain
        type="primary"
        @click="handleQuery"
      >
        加载更多
      </el-button>

      <h3 v-if="loadStatus === 3">
        已经到底啦~~~~~
      </h3>
    </div>

  </div>

</template>

<script>

import {qtnList} from "@/api/biz/qtn";
import UserBasicInfo from "@/views/system/user/profile/userBasicInfo";

export default {
  name: 'QtnTabList',
  components: {UserBasicInfo},
  props: {
    orderType: {
      type: Number,
      default: 4
    }
  },
  data() {
    return {
      tabChosen: 4,
      queryParam: {
        pageNum: 1,
        pageSize: 8,
        blogOrder: 4
      },
      queryLoading: false,
      questionList: [],
      loadStatus: 2,
    }
  },
  created() {
    this.queryParam.blogOrder = this.orderType
    this.handleQuery()
  },
  activated() {
    this.refresh()
  },
  methods: {
    refresh() {
      this.queryParam.pageNum = 1
      this.questionList = []
      this.handleQuery()
    },
    routeToProfile(userId) {
      this.$router.push({
        path: '/user/profile',
        query: {
          userId: userId
        }
      })
    },
    handleQuery() {
      this.queryLoading = true
      this.loadStatus = 1
      qtnList(this.queryParam).then(resp => {
        if (resp.code === 200) {
          this.questionList = this.questionList.concat(resp.data)

          if (resp.data.length === this.queryParam.pageSize) {
            this.queryParam.pageNum++
            this.loadStatus = 2
          } else {
            this.loadStatus = 3
          }

        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.queryLoading = false
      })
    }
  }
}


</script>


<style>
.qtn-card-side-left {
  display: flex;
  justify-content: right;
  margin-top: 10px;
  font-size: 13px;
  color: #636B74;
}

/* #1890FF  27px */
.btn-wrap-text {
  font-size: 27px;
  color: #1890FF;
  cursor: pointer;
}

.btn-wrap-text:hover {
  text-decoration: underline;
}

</style>
