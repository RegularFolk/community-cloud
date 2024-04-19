<template>

  <div class="app-container">
    <div style="display: flex">
      <h1>上传课程视频</h1>
      <div style="display: flex; align-items: center; margin-left: 20px; cursor:pointer;">
        <el-tooltip class="item" content="点击添加章节" effect="dark" placement="top">
          <i class="el-icon-circle-plus-outline" style="font-size: 40px; color: #D956D7" @click="createDialog"/>
        </el-tooltip>
      </div>
    </div>
    <!-- 左边一个el-menu表示章节的层次结构，每选择一个展示对应的课程上传信息 -->

    <div style="display: flex;">

      <!-- 左侧的el-menu章节结构 -->
      <div
        style="width: 35%; margin-right: 5%; border: 1px solid #D956D7;">

        <el-menu default-active="1">

          <el-submenu v-for="(item, index) in chapterList" :key="index" :index="'' + index">
            <template slot="title">
              <div style="display: flex; justify-content: space-between;">
                <div><i class="el-icon-reading"/>{{ item.title }}</div>
                <div style="margin-right: 30px">
                  <span style="margin-right: 15px"><el-button circle icon="el-icon-edit-outline" size="medium" @click="editDialog(item, index)"/></span>
                  <span style="margin-right: 15px"><el-button circle icon="el-icon-plus" size="medium" @click="pushVod(index)"/></span>
                  <span><el-button circle icon="el-icon-delete" size="medium" @click="delChapter(index)"/></span>
                </div>
              </div>
            </template>

            <el-menu-item v-for="(v, vIdx) in item.vodList" :key="vIdx" :index="index + '-' + vIdx" @click="editVod(index, vIdx)">
              <template slot="title">
                <div style="display: flex; justify-content: space-between;">
                  <div><i class="el-icon-video-play"/>{{ v.title }}</div>
                  <div style="margin-right: 30px">
                    <el-button circle icon="el-icon-delete" @click="delVideo(index, vIdx)"/>
                  </div>
                </div>
              </template>
            </el-menu-item>


          </el-submenu>


        </el-menu>

      </div>

      <!-- 右侧的视频上传组件 -->
      <div style="width: 60%">

        <el-form ref="vodForm" :model="video" label-width="80px">

          <el-form-item label="视频标题" prop="title">
            <el-input v-model="video.title" :maxlength="100"/>
          </el-form-item>

          <el-form-item label="视频简介" prop="preview">
            <el-input
              v-model="video.preview"
              :maxlength="200"
              :rows="3"
              :show-word-limit="true"
              type="textarea"
            />
          </el-form-item>

          <el-form-item label="视频上传" prop="videoId">
            <file-upload
              :file-size="1024"
              :limit="1"
              ref="fileUpload"
              :upload-type="'vod'"
              style="border: 1px solid #c0c0c0; padding-left: 10px"
              @vodUploadSuccess="vodUploadSuccess"
            />
          </el-form-item>


        </el-form>

        <div id="dplayer" style="height: 700px; width: 100%"/>


      </div>


    </div>

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
            <el-input
              v-model="dialogInput.desc"
              :maxlength="100"
              :rows="3"
              :show-word-limit="true"
              type="textarea"
            />
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

import DPlayer from 'dplayer';
import {getVodUrl} from "@/api/biz/vod";

export default {
  name: 'CourseEdit3',
  data() {
    return {
      dialogType: 1, // 1：新建，2：修改
      editVisible: false,
      dialogTitle: '',
      dp: undefined,
      dialogInput: {
        idx: 0,
        title: '',
        desc: ''
      },
      video: {
        title: '',
        preview: '',
        videoId: ''
      },
      playUrl: '',
      chapterList: []

    }
  },
  created() {
  },
  methods: {
    // 删除视频
    delVideo(index, vIdx) {
      this.$confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let chapter = this.chapterList[index]
        chapter.vodList.splice(vIdx, 1)
      })
    },
    // 删除章节
    delChapter(index) {
      this.$confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.chapterList.splice(index, 1)
      })
    },
    // 新建章节弹窗
    createDialog() {
      this.dialogType = 1
      this.dialogTitle = '新增'
      this.editVisible = true
    },
    // 修改章节弹窗
    editDialog(chapter, index) {
      this.dialogType = 2
      this.dialogInput.title = chapter.title
      this.dialogInput.desc = chapter.desc
      this.dialogInput.idx = index
      this.dialogTitle = '修改'
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

      if (this.dialogType === 2) {
        let tar = this.chapterList[this.dialogInput.idx];
        tar.title = this.dialogInput.title
        tar.desc = this.dialogInput.desc
      } else {
        this.chapterList.push({
          title: this.dialogInput.title,
          desc: this.dialogInput.desc,
          vodList: []
        })
      }

      this.editVisible = false

      this.dialogInput = {
        idx: 0,
        title: '',
        desc: ''
      }

    },
    // 往父组件传递信息
    infoTransfer() {
      this.$emit('infoOutput', this.chapterList)
    },
    // 编辑视频
    editVod(index, vIdx) {
      this.video = this.chapterList[index].vodList[vIdx]
    },
    // 指定章节增加视频
    pushVod(chapterIdx) {
      console.log('chapterIdx', chapterIdx)
      this.chapterList[chapterIdx].vodList.push({
        title: '',
        preview: '',
        videoId: ''
      })
    },
    vodUploadSuccess(videoId) {
      this.video.videoId = videoId
    },
    refreshPlayer() {
      if (this.dp) {
        this.dp.destroy()
      }
      if (this.playUrl && this.playUrl.trim().length > 0) {
        this.dp = new DPlayer({
          container: document.getElementById('dplayer'),
          video: {
            url: this.playUrl,
          },
        });
      } else if (this.dp) {
        this.dp.destroy()
      }
    }
  },
  watch: {
    video: {
      deep: true,
      immediate: true,
      handler(nVal) {
        /*
        * video 变化说明选项变化
        * 两部分需要变，一个是文件上传组件，一个是视频播放组件
        *   对于文件上传组件，如果新选项有文件，需要替换为新选项选择的文件；如果没有，需要去掉原先的文件
        *   对于视频播放组件，如果新选项有视频，需要替换为新选项的播放地址；如果没有，需要去掉播放组件
        * */
        if (this.video.videoId && this.video.videoId.trim().length > 0) {
          getVodUrl(this.video.videoId).then(resp => {
            if (resp.code === 200) {
              this.playUrl = resp.msg
              this.refreshPlayer()
              this.$refs.fileUpload.fileList = [{name: this.video.videoId, url: '#'}]
            } else {
              this.$message({
                message: resp.msg,
                type: 'error'
              })
            }
          })
        } else {
          if (this.dp) {
            this.dp.destroy()
          }
          if (this.$refs.fileUpload) {
            this.$refs.fileUpload.fileList = []
          }
        }
      }
    },
  }
}

</script>
