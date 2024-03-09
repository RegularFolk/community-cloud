<template>

  <div>
    <div style="display: flex;justify-content: center;border-bottom: 3px solid #EEF1F6FF">
      <h2>联系人</h2>
    </div>
    <el-main :loading="contactLoading" style="height: 1050px">

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

      <el-tooltip :disabled="hasMore" class="item" content="没有更多了哦" effect="dark" placement="bottom">
        <div style="display: flex;justify-content: center;">
          <el-button :disabled="!hasMore" :loading="getMoreBtnLoading" type="text" @click="getContact">
            查看更多
          </el-button>
        </div>
      </el-tooltip>

    </el-main>

  </div>

</template>


<script>

import {getContactList} from "@/api/biz/message";

export default {
  name: 'ContactList',
  data() {
    return {
      contactList: [],
      hasMore: true,
      getMoreBtnLoading: false,
      userId: 0,
      pageNum: 1,
      pageSize: 2,
      contactLoading: false,
      total: 0,
      contactSelected: '0'
    }
  },
  created() {
    this.userId = this.$store.state.user.id
    this.getContact()
  },
  methods: {
    // 选中一个联系人
    selectContact(index, indexPath,) {
      console.log('index', index)
      console.log('indexPath', indexPath)

      this.contactList.find(c => c.contactId == index).unReadCnt = 0

      if (index !== this.contactSelected) {
        this.contactSelected = index
        this.$emit('selectContact', parseInt(this.contactSelected))
      }
    },
    // 查询更多联系人
    getContact() {
      let dto = {
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }

      this.contactLoading = true
      getContactList(dto).then(resp => {
        if (resp.code === 200) {
          if (resp.data.length < this.pageSize) {
            this.hasMore = false
          }

          this.contactList = this.contactList.concat(resp.data)
          this.total = resp.total

          this.pageNum++

        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })
    }
  }
}


</script>
