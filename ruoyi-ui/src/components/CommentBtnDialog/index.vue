<template>
  <div>
    <el-button type="warning" @click="showComment" :disabled="dialogVisible">查看评论</el-button>

    <el-dialog
      :visible.sync="dialogVisible"
      title="查看评论">
      <blog-comment :article-id="contentId"/>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关 闭</el-button>
      </span>
    </el-dialog>

  </div>
</template>


<script>

import BlogComment from "@/components/BlogComment";
export default {
  name: 'commentBtnDialog',
  components: {BlogComment},
  props: {
    contentId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      dialogVisible: false
    }
  },
  created() {
  },
  methods: {
    showComment() {
      this.dialogVisible = true
    }
  }
}


</script>
