<template>

  <div>
    <div style="display: flex;justify-content: center;border-bottom: 3px solid #EEF1F6FF">
      <h2>联系人</h2>
    </div>
    <el-main style="height: 1050px">

      <el-menu @select="selectContact">

        <el-menu-item v-for="contact in contactList" :key="contact.contactId" :index="contact.contactId + ''"
                      style="height:100%;border: 1px solid #EEF1F6FF">

          <div slot="title" style="display: flex">
            <div style="margin-right: 20px;display: flex;align-items: center">
              <el-avatar :size="50" :src="contact.avatar"/>
            </div>

            <div>
              <div>{{ contact.contactName }}</div>
              <div>{{ contact.latestMsgPreview }}</div>
            </div>


            <div v-if="contact.unReadCnt > 0"
                 style="display: flex;justify-content: center;align-items: center;margin-left: 55%">
              <el-tooltip class="item" :content="contact.unReadCnt + ' 个未读消息'" effect="dark" placement="top">
                <el-tag
                  effect="dark"
                  size="small"
                  type="danger">
                  {{ contact.unReadCnt }}
                </el-tag>
              </el-tooltip>
            </div>
          </div>
        </el-menu-item>

      </el-menu>

      <div style="display: flex;justify-content: center;">
        <el-button :disabled="!hasMore" :loading="getMoreBtnLoading" type="text" @click="getMoreContact">
          查看更多
        </el-button>
      </div>

    </el-main>

  </div>

</template>


<script>

export default {
  name: 'ContactList',
  data() {
    return {
      contactList: [
        {
          contactId: 2,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户一',
          unReadCnt: 10,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 3,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户二',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 4,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户三',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 5,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户四',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 6,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户五',
          unReadCnt: 99,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 7,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户六',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 7,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户六',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 7,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户六',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 7,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户六',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 7,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户六',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 7,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户六',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 7,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户六',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 7,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户六',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 7,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户六',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 7,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户六',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 7,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户六',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        }, {
          contactId: 7,
          avatar: 'http://121.43.39.16:8082/ruoyi-test/2024/02/02/ASCII%E8%A1%A8_20240202161511A004.png',
          contactName: '用户六',
          unReadCnt: 0,
          latestMsgPreview: '哦喔喔我我哦我我我我我......'
        },
      ],
      hasMore: true,
      getMoreBtnLoading: false,
    }
  },
  created() {
  },
  methods: {
    // 选中一个联系人
    selectContact(index, indexPath,) {
      console.log('index', index)
      console.log('indexPath', indexPath)
    },
    // 查询更多联系人
    getMoreContact() {

    }
  }
}


</script>
