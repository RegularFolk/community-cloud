<template>

  <div class="app-container">
    <h1>测试视频上传和播放</h1>

    <div>
      上传视频的按钮
      <file-upload upload-type="vod" :file-size="500" :limit="1" @vodUploadSuccess="handleSuccess"/>
    </div>

    <div>
      {{'上传后获取的视频Id为：' + vodId}}
    </div>

    <div>
      <el-button type="success" round @click="getVodUrl">点击获取替换视频播放地址</el-button>
    </div>

    <div>
      {{'当前的视频播放地址为：' + this.vodUrl}}
    </div>

    <div>
      <el-button type="danger" round @click="delVod">点击删除刚才上传的视频</el-button>
    </div>

    <div id="dplayer" style="height: 1000px; width: 1800px"/>
  </div>

</template>

<script>

import DPlayer from 'dplayer';
import {delVodUrl, getVodUrl} from "@/api/biz/vod";

export default {
  name: 'TestVideo',
  data() {
    return {
      dp: {},
      vodId: '',
      vodUrl: 'https://outin-876cab0e0e7d11edbae200163e1a65b6.oss-cn-shanghai.aliyuncs.com/sv/21dff8a6-182493b39d3/21dff8a6-182493b39d3.mp4?Expires=1712497377&OSSAccessKeyId=LTAI3DkxtsbUyNYV&Signature=%2B1tkP6Emc5ZqFxSFCZVE5XLm6js%3D',
    }
  },
  created() {
  },
  mounted() {
    this.dp = new DPlayer({
      container: document.getElementById('dplayer'),
      video: {
        url: this.vodUrl,
      },
    });
  },
  methods: {
    handleSuccess(vodId) {
      this.$message({
        message: '视频上传成功！视频Id为：' + vodId,
        type: 'success'
      })
      this.vodId = vodId
    },
    delVod() {
      if (!this.vodId || this.vodId.trim().length === 0) {
        this.$message({
          message: '请先上传视频！',
          type: 'error'
        })
        return
      }

      delVodUrl(this.vodId).then(resp => {
        if (resp.code === 200) {
          this.vodUrl = ''
          this.dp.destroy()

          this.$message({
            message: '删除视频成功！',
            type: 'success'
          })
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })

    },
    getVodUrl() {
      if (!this.vodId || this.vodId.trim().length === 0) {
        this.$message({
          message: '请先上传视频！',
          type: 'error'
        })
        return
      }
      getVodUrl(this.vodId).then(resp => {
        if (resp.code === 200) {
          this.$message({
            message: '获取视频播放地址成功',
            type: 'success'
          })
          this.vodUrl = resp.msg
          this.dp.destroy()
          this.dp = new DPlayer({
            container: document.getElementById('dplayer'),
            video: {
              url: this.vodUrl,
            },
          });
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





