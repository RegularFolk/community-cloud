<template>

  <div class="app-container">
    <div style="padding-bottom: 20px; border-bottom: 1px solid #D956D7">
      <el-steps :active="stepActive" align-center>
        <el-step icon="el-icon-edit" title="编辑课程"/>
        <el-step icon="el-icon-notebook-2" title="编辑章节"/>
      </el-steps>
    </div>

    <div style="display: flex; justify-content: center" v-loading="editLoading">
      <course-edit1
        v-show="stepActive === 1"
        ref="step1"
        style="width: 70%"
        @infoOutput="step1Info"
      />

      <course-edit3
        v-show="stepActive === 2"
        ref="step3"
        style="width: 100%"
        @infoOutput="step3Info"
      />
    </div>

    <!-- 底部按钮 -->
    <div style="left: 50%; position: fixed; bottom: 50px; display: flex; justify-content: center">
      <el-button :disabled="stepActive === 1" type="primary" @click="lastStep">上一步</el-button>
      <el-button type="info" @click="cancelPage">取消</el-button>
      <el-button v-if="stepActive === 1" type="primary" @click="nextStep">下一步</el-button>
      <el-button v-if="stepActive === 2" type="primary" @click="saveCourse">保存</el-button>
    </div>
  </div>

</template>

<script>

import CourseEdit1 from "@/views/biz/course/CourseEditStep1";
import CourseEdit3 from "@/views/biz/course/CourseStep3";
import {courseDetail, postCourse} from "@/api/biz/vod";

export default {
  name: 'CourseEdit',
  components: {CourseEdit3, CourseEdit1},
  data() {
    return {
      stepActive: 1,
      editLoading: false,
      courseEdit: {
        courseId: 0,
        course: {},
        chapterList: []
      }
    }
  },
  created() {
    if (this.$route.query.courseId && this.$route.query.courseId > 0) {
      // 进入页面路由包含课程id，变成编辑页面
      this.getCourseDetail(this.$route.query.courseId)
    }
  },
  methods: {
    // 课程编辑回显
    getCourseDetail(courseId) {
      this.editLoading = true
      courseDetail(courseId).then(resp => {
        if (resp.code === 200) {
          this.courseEdit.courseId = courseId
          this.courseEdit.chapterList = resp.data.chapterList

          this.$refs.step1.injectValue(resp.data.course)
          this.$refs.step3.injectValue(resp.data.chapterList)
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.editLoading = false
      })
    },
    // 保存课程
    saveCourse() {
      this.$refs.step3.infoTransfer()
      console.log('最终提交数据打包：', this.courseEdit)
      postCourse(this.courseEdit).then(resp => {
        if (resp.code === 200) {
          this.$message({
            message: '保存课程成功！',
            type: 'success'
          })
          this.$tab.closePage()
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })
    },
    // 跳转到上一步
    lastStep() {
      if (this.stepActive > 1) {
        this.stepActive--;
      }
    },
    // 跳转到下一步
    nextStep() {
      if (this.stepActive === 1) {
        this.$refs.step1.$refs.ruleForm.validate((flag, obj) => {
          if (flag) {
            this.$refs.step1.infoTransfer()
            this.stepActive++;
          }
        })
      }

    },
    cancelPage() {
      this.$confirm('确定要取消吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$tab.closePage()
      })
    },
    // 步骤一数据取出
    step1Info(info) {
      this.courseEdit.course = info
    },
    // 步骤二数据取出
    step3Info(info) {
      this.courseEdit.chapterList = info
    }
  }
}


</script>
