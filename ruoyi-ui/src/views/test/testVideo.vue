<template>

  <div class="app-container">
    <h1>测试视频上传和播放</h1>
    <div id="dplayer" style="height: 1000px; width: 1800px"/>
  </div>

</template>

<script>

import DPlayer from 'dplayer';

export default {
  name: 'TestVideo',
  data() {
    return {
      dp: {}

    }
  },
  created() {
  },
  mounted() {
    this.dp = new DPlayer({
      container: document.getElementById('dplayer'),
      video: {
        url: 'https://outin-876cab0e0e7d11edbae200163e1a65b6.oss-cn-shanghai.aliyuncs.com/sv/21dff8a6-182493b39d3/21dff8a6-182493b39d3.mp4?Expires=1712497377&OSSAccessKeyId=LTAI3DkxtsbUyNYV&Signature=%2B1tkP6Emc5ZqFxSFCZVE5XLm6js%3D',
      },
    });
  },
  methods: {

  }
}


</script>





