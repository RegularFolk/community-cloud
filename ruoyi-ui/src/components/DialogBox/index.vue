<template>
  <div>

    <el-main v-if="contactId !== 0">

      <el-card shadow="always">
        <div slot="header" style="display: flex;justify-content: space-between">
          <div style="display: flex;">
            <div style="width: 60px; cursor: pointer" @click="routeToProfile">
              <el-avatar :size="50" :src="contactor.avatar"/>
            </div>
            <div style="margin-left: 10px">
              <div>{{ contactor.userName }}</div>
              <div style="margin-top: 10px">用户的简介</div>
            </div>
          </div>
          <el-button v-if="contactor.followed" style="padding: 10px" type="danger" @click="unfollow">已关注</el-button>
          <el-button v-if="!contactor.followed" style="padding: 10px" type="success" @click="follow">关注</el-button>
        </div>

        <div ref="messageBox" class="msgBox" style="height: 650px;overflow-y: auto">
          <div style="display: flex;justify-content: center;">
            <el-tooltip class="item" content="到顶啦" effect="dark" placement="top" :disabled="hasMore">
              <el-button :disabled="!hasMore" :loading="getMoreBtnLoading" type="text" @click="getMoreMsg">
                查看更多
              </el-button>
            </el-tooltip>
          </div>

          <div v-for="msg in msgList" :key="msg.id" style="margin: 10px">

            <!-- 如果消息是我发送的 -->
            <div v-if="msg.senderId === self.id" style="display: flex;justify-content: flex-end">
              <el-card shadow="always" style="margin-right: 10px">
                {{ msg.content }}
              </el-card>

              <el-avatar :size="40" :src="self.avatar"/>
            </div>

            <!-- 如果消息是对方发的 -->
            <div v-if="msg.senderId === contactor.id" style="display: flex;">
              <div style="cursor: pointer" @click="routeToProfile">
                <el-avatar :size="40" :src="contactor.avatar"/>
              </div>

              <el-card shadow="always" style="margin-left: 10px">
                {{ msg.content }}
              </el-card>

            </div>

          </div>
        </div>

      </el-card>

      <!-- 输入框 -->
      <el-card shadow="always" style="margin-top: 20px">
        <div slot="header">
          <el-input
            v-model="msgInput"
            :maxlength="500"
            :rows="10"
            :show-word-limit="true"
            placeholder="请输入内容，长度不超过500字"
            resize="vertical"
            type="textarea"/>
        </div>

        <div style="display: flex;justify-content: flex-end">
          <el-tooltip :disabled="msgInput.trim().length > 0" class="item" content="不可发送空内容" effect="dark"
                      placement="top">
            <el-button :disabled="msgInput.trim().length === 0" :loading="sendBtnLoading" type="success"
                       @click="sendMsg">
              发送
            </el-button>
          </el-tooltip>
        </div>

      </el-card>

    </el-main>

  </div>

</template>


<script>
import {changeUserFollow, getUserProfileById} from "@/api/system/user";
import {msgList, pullMsg, sendMSg} from "@/api/biz/message";

export default {
  name: 'DialogBox',
  props: {
    /* 父组件注入对话对象的id */
    contactId: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      contactor: {
        id: 0,
        avatar: '',
        userName: '',
        followed: false
      },
      self: {
        id: 0,
        avatar: '',
        userName: ''
      },
      msgList: [
        // {
        //   id: 1,
        //   senderId: 2,
        //   receiverId: 1,
        //   content: '你好！',
        //   receiverHasRead: 1
        // }, {
        //   id: 2,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '您好呀！',
        //   receiverHasRead: 1
        // }, {
        //   id: 3,
        //   senderId: 2,
        //   receiverId: 1,
        //   content: '帅哥约吗？',
        //   receiverHasRead: 1
        // }, {
        //   id: 4,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '你是什么罩杯？',
        //   receiverHasRead: 1
        // }, {
        //   id: 5,
        //   senderId: 2,
        //   receiverId: 1,
        //   content: 'A罩杯哦～',
        //   receiverHasRead: 0
        // }, {
        //   id: 6,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '再见！',
        //   receiverHasRead: 0
        // }, {
        //   id: 7,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '再见！',
        //   receiverHasRead: 0
        // }, {
        //   id: 8,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '再见！',
        //   receiverHasRead: 0
        // }, {
        //   id: 9,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '再见！',
        //   receiverHasRead: 0
        // }, {
        //   id: 10,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '再见！',
        //   receiverHasRead: 0
        // }, {
        //   id: 11,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '再见！',
        //   receiverHasRead: 0
        // }, {
        //   id: 12,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '再见！',
        //   receiverHasRead: 0
        // }, {
        //   id: 13,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '再见！',
        //   receiverHasRead: 0
        // }, {
        //   id: 14,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '再见！',
        //   receiverHasRead: 0
        // }, {
        //   id: 15,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '再见！',
        //   receiverHasRead: 0
        // }, {
        //   id: 16,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '再见！',
        //   receiverHasRead: 0
        // }, {
        //   id: 17,
        //   senderId: 1,
        //   receiverId: 2,
        //   content: '再见！',
        //   receiverHasRead: 0
        // },
      ],
      msgInput: '',
      sendBtnLoading: false,
      getMoreBtnLoading: false,
      hasMore: true,
      queryDto: {
        pageSize: 10,
        startMsgId: 0
      },
      defaultDown: false,
      msgPulling: false
    }
  },
  created() {
    this.initSelf()
    this.initContact()
    this.getMoreMsg()
  },
  deactivated() {
    this.msgPulling = true
  },
  activated() {
    this.msgPulling = false
  },
  mounted() {
    console.log('mounted');
    this.scrollToBottom()
    this.time();
    // 通过$once来监听定时器，在beforeDestroy钩子可以被清除
    this.$once("hook:beforeDestroy", () => {
      // 在页面销毁时，销毁定时器
      clearInterval(this.timer);
    });
  },
  methods: {
    // 定时任务
    time() {
      this.timer = setInterval(() => {
        // 这里调用调用需要执行的方法，1为自定义的参数，由于特殊的需求它将用来区分，定时器调用和手工调用，然后执行不同的业务逻辑
        console.log('定时任务触发！！');
        if (!this.defaultDown) {
          this.scrollToBottom()
          this.defaultDown = true
        }

        this.pollingMsg()

      }, 1500); // 每两秒执行1次
    },
    // 初始化查询条件
    initQueryDto() {
      this.hasMore = true
      this.queryDto = {
        pageSize: 10,
        startMsgId: 0
      }
      this.msgList = []
    },
    // 初始化联系人信息
    initContact() {
      this.initQueryDto()
      let dto = {
        id: this.contactId
      }
      getUserProfileById(dto).then(resp => {
        if (resp.code === 200) {
          this.contactor.id = this.contactId
          this.contactor.avatar = resp.data.avatar
          this.contactor.userName = resp.data.nickName
          this.contactor.followed = resp.followed
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })
    },
    // 初始化个人信息
    initSelf() {
      let user = this.$store.state.user
      this.self.id = user.id
      this.self.avatar = user.avatar
      this.self.userName = user.name
    },
    // 关注用户
    follow() {
      let dto = {
        followId: this.contactor.id,
        operateType: 1
      }
      this.submitFollow(dto)
    },
    // 取消关注
    unfollow() {
      this.$confirm('确定要取消关注吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let dto = {
          followId: this.contactor.id,
          operateType: 2
        }
        this.submitFollow(dto)
      })
    },
    // 提交关注请求
    submitFollow(dto) {
      changeUserFollow(dto).then(resp => {
        if (resp.code === 200) {
          this.$message({
            message: '操作成功！',
            type: 'success'
          })
          this.contactor.followed = !this.contactor.followed
          this.$emit('userFollowChanged', this.contactor.followed)
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })
    },
    // 轮询拉取消息
    pollingMsg() {
      if (this.msgPulling) {
        return
      }
      this.msgPulling = true
      let dto = {
        id: this.contactId
      }

      pullMsg(dto).then(resp => {
        if (resp.code === 200) {
          if (resp.data.length > 0) {
            this.msgList = resp.data.concat(this.msgList)
            this.scrollToBottom()
          }
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.msgPulling = false
      })
    },
    // 滚动到最底层
    scrollToBottom() {
      let container = document.querySelector('.msgBox')
      container.scrollTop = container.scrollHeight
    },
    // 发送消息
    sendMsg() {
      if (!this.msgInput || this.msgInput === '' || this.msgInput.trim().length === 0) {
        this.$message({
          message: '不可以发送空消息！',
          type: 'warning'
        })
        return
      }

      this.sendBtnLoading = true

      let dto = {
        receiverId: this.contactId,
        content: this.msgInput
      }
      sendMSg(dto).then(resp => {
        if (resp.code === 200) {
          let message = {
            id: resp.data,
            content: this.msgInput,
            senderId: this.self.id,
            receiverId: this.contactId,
          }
          this.msgList.push(message)

          this.msgInput = ''

          this.$message({
            message: '发送成功！',
            type: 'success'
          })

          this.$emit('msgSent', message.content)

        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.scrollToBottom();
        this.sendBtnLoading = false
      })
    },
    // 跳转到用户主页
    routeToProfile() {
      this.$emit('dialogClose')
      this.$router.push({
        path: '/user/profile',
        query: {
          userId: this.contactor.id
        }
      })
    },
    // 查看更多消息
    getMoreMsg() {
      this.getMoreBtnLoading = true
      let dto = {
        contactId: this.contactId,
        pageSize: this.queryDto.pageSize,
        startMsgId: this.queryDto.startMsgId
      }

      msgList(dto).then(resp => {
        if (resp.code === 200) {
          this.hasMore = resp.data.length >= this.queryDto.pageSize
          this.msgList = resp.data.concat(this.msgList)
          this.queryDto.startMsgId = resp.data[0].id

        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.getMoreBtnLoading = false
      })
    },
  },
  watch: {
    contactId: {
      handler(nVal) {
        this.msgPulling = false
        this.defaultDown = false
        this.initContact()
        this.getMoreMsg()
      }
    }
  }
}


</script>
