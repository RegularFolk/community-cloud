<template>
  <div>
    <el-main>

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
          <el-button v-if="contactor.followed" style="padding: 10px" type="danger">已关注</el-button>
          <el-button v-if="!contactor.followed" style="padding: 10px" type="primary">关注</el-button>
        </div>

        <div ref="messageBox" style="height: 650px;overflow-y: auto">
          <div style="display: flex;justify-content: center;">
            <el-button :disabled="!hasMore" :loading="getMoreBtnLoading" type="text" @click="getMoreMsg">
              查看更多
            </el-button>
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
        id: 2,
        avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
        userName: '若依',
        followed: true
      },
      self: {
        id: 1,
        avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/2b_20240202165949A006.jpg',
        userName: '超级管理员'
      },
      msgList: [
        {
          id: 1,
          senderId: 2,
          receiverId: 1,
          content: '你好！',
          receiverHasRead: 1
        }, {
          id: 2,
          senderId: 1,
          receiverId: 2,
          content: '您好呀！',
          receiverHasRead: 1
        }, {
          id: 3,
          senderId: 2,
          receiverId: 1,
          content: '帅哥约吗？',
          receiverHasRead: 1
        }, {
          id: 4,
          senderId: 1,
          receiverId: 2,
          content: '你是什么罩杯？',
          receiverHasRead: 1
        }, {
          id: 5,
          senderId: 2,
          receiverId: 1,
          content: 'A罩杯哦～',
          receiverHasRead: 0
        }, {
          id: 6,
          senderId: 1,
          receiverId: 2,
          content: '再见！',
          receiverHasRead: 0
        }, {
          id: 7,
          senderId: 1,
          receiverId: 2,
          content: '再见！',
          receiverHasRead: 0
        }, {
          id: 8,
          senderId: 1,
          receiverId: 2,
          content: '再见！',
          receiverHasRead: 0
        }, {
          id: 9,
          senderId: 1,
          receiverId: 2,
          content: '再见！',
          receiverHasRead: 0
        }, {
          id: 10,
          senderId: 1,
          receiverId: 2,
          content: '再见！',
          receiverHasRead: 0
        }, {
          id: 11,
          senderId: 1,
          receiverId: 2,
          content: '再见！',
          receiverHasRead: 0
        }, {
          id: 12,
          senderId: 1,
          receiverId: 2,
          content: '再见！',
          receiverHasRead: 0
        }, {
          id: 13,
          senderId: 1,
          receiverId: 2,
          content: '再见！',
          receiverHasRead: 0
        }, {
          id: 14,
          senderId: 1,
          receiverId: 2,
          content: '再见！',
          receiverHasRead: 0
        }, {
          id: 15,
          senderId: 1,
          receiverId: 2,
          content: '再见！',
          receiverHasRead: 0
        }, {
          id: 16,
          senderId: 1,
          receiverId: 2,
          content: '再见！',
          receiverHasRead: 0
        }, {
          id: 17,
          senderId: 1,
          receiverId: 2,
          content: '再见！',
          receiverHasRead: 0
        },
      ],
      msgInput: '',
      sendBtnLoading: false,
      getMoreBtnLoading: false,
      hasMore: true,
    }
  },
  created() {
    this.initSelf()
  },
  mounted() {
    this.$nextTick(() => {
      this.scrollToBottom();
    });
  },
  methods: {
    // 初始化个人信息
    initSelf() {

    },
    // 滚动到最底层
    scrollToBottom() {
      const messageBox = this.$refs.messageBox;
      messageBox.scrollTop = messageBox.scrollHeight;
    },
    // 发送消息
    sendMsg() {
    },
    // 跳转到用户主页
    routeToProfile() {
      this.$router.push({
        path: '/user/profile',
        query: {
          userId: this.contactor.id
        }
      })
    },
    // 查看更多消息
    getMoreMsg() {

    },
  }
}


</script>
