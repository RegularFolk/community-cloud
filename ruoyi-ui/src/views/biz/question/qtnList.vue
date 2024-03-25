<template>
  <div>
    <el-tabs v-model="tabChosen" v-loading="false" :before-leave="leaveTab" type="border-card" @tab-click="tabChange">

      <el-tab-pane :lazy="true" :name="'4'">
        <span slot="label"><i class="el-icon-star-off"></i>猜你感兴趣</span>
        <qtn-tab-list :key="4" ref="tabList4" :order-type="4"/>
      </el-tab-pane>

      <el-tab-pane :lazy="true" :name="'2'" label="最新发布">
        <qtn-tab-list :key="2" ref="tabList2" :order-type="2"/>
      </el-tab-pane>

      <el-tab-pane :lazy="true" :name="'6'" label="热门问题">
        <qtn-tab-list :key="6" ref="tabList6" :order-type="6"/>
      </el-tab-pane>

    </el-tabs>

  </div>
</template>


<script>

import QtnTabList from "@/views/biz/question/qtnTabList";

export default {
  name: 'QtnList',
  components: {QtnTabList},
  data() {
    return {
      tabChosen: '4',
      lastChose: '4',
    }
  },
  created() {

  },
  methods: {
    leaveTab(newName, oldName) {
      this.lastChose = oldName
    },
    tabChange(tab) {
      if (this.lastChose !== this.tabChosen) {
        this.lastChose = this.tabChosen

        switch (this.tabChosen) {
          case '2':
            this.$refs.tabList2.refresh()
            break
          case '4':
            this.$refs.tabList4.refresh()
            break
          case '6':
            this.$refs.tabList6.refresh()
            break
        }
      }
    },

  }
}


</script>
