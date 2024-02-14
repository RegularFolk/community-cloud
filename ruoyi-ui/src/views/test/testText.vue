<template>
  <div id="main">
    <Editor v-model="value" ref="input" @on-text-change="valueChanged" :enable-limit="true" :limit-count="1000"/>

    <!-- 用于回显的部分外部必须套上这两层div -->
    <div class="ql-container ql-snow">
      <div class="ql-editor">
        <div v-html="value"/>
      </div>
    </div>


    <div style="background-color: #aeafb1">currentValue:</div>
    <div>{{this.currentValue}}</div>

    <div style="background-color: #aeafb1">Quill.getContents:</div>
    <div>{{this.getContents}}</div>

    <div style="background-color: #aeafb1">Quill.getText</div>
    <div>{{this.getTest}}</div>

    <div style="background-color: #aeafb1">Quill.getLength</div>
    <div>{{this.getLength}}</div>

    <Editor :read-only="true" ref="input2" :for-present="true"/>
  </div>

</template>



<script>

import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';

export default {
  name: 'xxx',
  data() {
    return {
      value: '',
      currentValue: '',
      getContents: '',
      getTest: '',
      getLength: '',
    }
  },
  created() {
  },
  mounted() {
    // console.log("ref:", this.$refs.input)
  },
  watch: {
  },
  methods: {
    valueChanged(delta, oldDelta, source) {
      console.log('delta:', delta)
      console.log('oldDelta', oldDelta)
      console.log('source', source)

      this.currentValue = this.$refs.input.currentValue
      this.getContents = this.$refs.input.Quill.getContents()
      this.getTest = this.$refs.input.Quill.getText()
      this.getLength = this.$refs.input.Quill.getLength()

      this.$refs.input2.Quill.setContents(this.getContents)
    }
  }
}


</script>
