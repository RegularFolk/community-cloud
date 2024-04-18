<template>
  <div class="app-container">
    <div style="display: flex; justify-content: center">
      <h1>编辑课程基本信息</h1>
    </div>

    <el-form ref="ruleForm" :rules="validations" :model="courseInfo" label-width="80px">

      <el-form-item label="课程名称" prop="title">
        <el-input v-model="courseInfo.title" :maxlength="100"/>
      </el-form-item>

      <el-form-item label="课程简介" prop="desc">
        <el-input
          v-model="courseInfo.desc"
          :maxlength="200"
          :rows="3"
          :show-word-limit="true"
          type="textarea"
        />
      </el-form-item>

      <el-form-item label="课程封面" prop="coverPic">
        <file-upload
          :file-type="fileType"
          :limit="1"
          style="border: 1px solid #c0c0c0; padding-left: 10px"
          @input="picUploadSuccess"
        />
      </el-form-item>

    </el-form>

  </div>
</template>

<script>

export default {
  name: 'CourseEdit1',
  data() {
    return {
      validations: {
        title: [
          {required: true, message: '请输入课程名称', trigger: 'blur'},
          {max: 100, message: '长度在100个字以内', trigger: 'blur'}
        ],
        desc: [
          {required: true, message: '请输入课程简介', trigger: 'blur'},
          {max: 200, message: '长度在200个字以内', trigger: 'blur'}
        ],
        coverPic: [
          {required: true, message: '请上传课程封面', trigger: 'blur'}
        ]
      },
      fileType: ["jpg", "png"],
      courseInfo: {
        title: '',
        coverPic: '',
        desc: ''
      }
    }
  },
  created() {

  },
  methods: {
    // 图片上传成功后的回调方法
    picUploadSuccess(fileList) {
      console.log('fileList', fileList)
      this.courseInfo.coverPic = fileList
    },
    infoTransfer() {
      this.$emit('infoOutput', this.courseInfo)
    }
  }
}


</script>
