<template>

  <div class="app-container">
    <div style="display: flex">
      <h1>编辑章节信息</h1>
      <div style="display: flex; align-items: center; margin-left: 20px; cursor:pointer;">
        <el-tooltip class="item" content="点击添加章节" effect="dark" placement="top">
          <i class="el-icon-circle-plus-outline" style="font-size: 40px; color: #D956D7" @click="createDialog"/>
        </el-tooltip>
      </div>
    </div>

    <el-card v-for="(item, index) in chapterList" :key="index" shadow="never" style="margin-bottom: 10px">
      <div slot="header">
        <span>{{ '第' + (index + 1) + '章：' + item.title }}</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="editDialog(item)">修改</el-button>
        <el-button type="danger" icon="el-icon-delete" circle @click="delChapter(item.id)"/>
      </div>
      <div>
        {{ item.desc }}
      </div>
    </el-card>

    <!-- 编辑章节弹窗 -->
    <el-dialog
      :title="dialogTitle + '章节'"
      :visible.sync="editVisible"
      :close-on-click-modal="false"
      width="30%">
      <div>
        <el-form :model="dialogInput" label-width="80px">
          <el-form-item label="章节标题">
            <el-input v-model="dialogInput.title" :maxlength="100"/>
          </el-form-item>
          <el-form-item label="章节介绍">
            <el-input v-model="dialogInput.desc" :maxlength="100"/>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogConfirm">确 定</el-button>
      </span>
    </el-dialog>

  </div>

</template>

<script>

export default {
  name: 'CourseEdit2',
  computed: {

  },
  data() {
    return {
      editVisible: false,
      dialogTitle: '',
      dialogType: 1, // 1：新增，2：修改
      idStart: 1, // id起点，用于前端渲染的临时id
      chapterList: [
        {
          id: 1,
          title: '测试title',
          desc: '测试desc'
        }
      ],
      dialogInput: {
        id: 0,
        title: '',
        desc: ''
      }
    }
  },
  created() {
  },
  methods: {
    // 删除章节
    delChapter(chapterId) {
      this.$confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.chapterList = this.chapterList.filter(item => item.id !== chapterId)
      })

    },
    // 传出数据
    infoTransfer() {
      this.$emit('infoOutput', this.chapterList)
    },
    // 章节列表不为空才可以下一步
    validate() {
      return this.chapterList.length > 0
    },
    // 新建章节弹窗
    createDialog() {
      this.dialogType = 1
      this.dialogTitle = '新增'
      this.editVisible = true
    },
    nextId() {
      console.log(this.idStart)
      return ++this.idStart
    },
    // 修改章节弹窗
    editDialog(chapter) {
      this.dialogType = 2
      this.dialogTitle = '修改'
      this.dialogInput.id = chapter.id
      this.dialogInput.title = chapter.title
      this.dialogInput.desc = chapter.desc
      this.editVisible = true
    },
    dialogConfirm() {
      if (!this.dialogInput.title
        || this.dialogInput.title.trim().length === 0
        || !this.dialogInput.desc
        || this.dialogInput.desc.trim().length === 0) {
        this.$message({
          message: '章节标题和介绍不可为空！',
          type: 'warning'
        })
        return
      }

      if (this.dialogInput.id && this.dialogInput.id > 0) {
        let tar = this.chapterList.find(item => item.id === this.dialogInput.id);
        tar.title = this.dialogInput.title
        tar.desc = this.dialogInput.desc
      } else {
        this.chapterList.push({
          id: this.nextId(),
          title: this.dialogInput.title,
          desc: this.dialogInput.desc
        })
      }

      this.editVisible = false

      this.dialogInput = {
        id: 0,
        title: '',
        desc: ''
      }

    }
  }
}

</script>
