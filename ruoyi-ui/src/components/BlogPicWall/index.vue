<template>
  <!-- 图片展示栏  -->
  <div v-if="picUrlList && picUrlList.length > 0" class="flexClass">
    <el-image
      v-for="pic in picUrlList"
      :key="pic"
      :preview-src-list='previewList'
      :src="pic"
      fit="scale-down"
      style="width: 15%; height: 15%;padding: 15px;border-right: 1px solid #EEF1F6FF"/>
  </div>


</template>

<script>

export default {
  name: 'BlogPicWall',
  props: {
    /* 图片的Url集合 */
    picUrlList: {
      type: Array,
      default: []
    },
    /* 是否开启点击预览 */
    enablePreview: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      previewList: []
    }
  },
  created() {
    if (this.enablePreview) {
      this.previewList = this.picUrlList
    }
  },
  methods: {}
}


</script>
