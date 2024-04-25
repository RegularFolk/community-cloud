<template>
  <div class="app-container home">
<!--    <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <blockquote class="text-warning" style="font-size: 14px">
          领取阿里云通用云产品1888优惠券
          <br />
          <el-link
            href="https://www.aliyun.com/minisite/goods?userCode=brki8iof"
            type="primary"
            target="_blank"
            >https://www.aliyun.com/minisite/goods?userCode=brki8iof</el-link
          >
          <br />
          领取腾讯云通用云产品2860优惠券
          <br />
          <el-link
            href="https://cloud.tencent.com/redirect.php?redirect=1025&cps_key=198c8df2ed259157187173bc7f4f32fd&from=console"
            type="primary"
            target="_blank"
            >https://cloud.tencent.com/redirect.php?redirect=1025&cps_key=198c8df2ed259157187173bc7f4f32fd&from=console</el-link
          >
          <br />
          阿里云服务器折扣区
          <el-link href="http://aly.ruoyi.vip" type="primary" target="_blank"
            >>☛☛点我进入☚☚</el-link
          >
          &nbsp;&nbsp;&nbsp; 腾讯云服务器秒杀区
          <el-link href="http://txy.ruoyi.vip" type="primary" target="_blank"
            >>☛☛点我进入☚☚</el-link
          ><br />
          <h4 class="text-danger">
            云产品通用红包，可叠加官网常规优惠使用。(仅限新用户)
          </h4>
        </blockquote>

        <hr />
      </el-col>
    </el-row>-->

    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>EduVerse在线学习社区</h2>
        <p>
          本项目旨在打造一个集课程学习、答疑解惑、分享交流为一体的在线教育社区平台。通过丰富的课程内容、实时的答疑互动、个性化的随笔分享以及深度的想法交流，为用户提供一个全面、便捷、高效的学习与交流空间。同时，平台还设计了用户关注功能，以个性化推送内容，提升用户体验。
        </p>
        <p>
          <b>当前项目包含以下功能模块：</b>
        </p>
        <p>
          <b>1、课程广场：</b>
          课程广场是平台的核心功能之一，提供多样化的课程内容。用户可以浏览不同领域的课程，根据自己的学习需求选择合适的课程进行学习。课程形式包括视频、音频、文档等，以满足不同用户的学习习惯。同时，课程广场还提供课程上传功能，每个用户都可以上传自己制作的视频课程，供他人学习参考。
        </p>
        <p>
          <b>2、答疑广场：</b>
          答疑广场为用户提供实时的答疑解惑服务。用户可以在此发布自己的问题，寻求其他用户的帮助。同时，其他用户也可以浏览问题并参与讨论，共同解决问题。
        </p>
        <p>
          <b>3、随笔广场：</b>
          随笔广场是一个个性化的分享空间，用户可以在此发布自己的学习心得、笔记、感想等随笔内容。通过分享自己的学习经历和收获，用户可以与其他用户产生共鸣，相互激励，共同进步。同时，随笔广场也支持点赞、评论等互动功能，增强用户之间的交流和互动。
        </p>
        <p>
          <b>4、想法广场：</b>
          想法广场是一个发布轻度内容的平台，用户可以在此发表自己的观点、见解和思考。通过与其他用户的讨论和交流，用户可以拓宽视野，深化理解，提升思维水平。
        </p>
        <p>
          <b>5、API开放：</b>
          系统对第三方开发者开放API，经过认证的用户都可以接入系统，进行定制化的前台页面开发，一起助力社区的发展。
        </p>
        <p>
          <b>来探索更多的功能吧~~</b>
        </p>


        <p>
          <el-tag type="danger">本项目开放源代码，欢迎感兴趣的小伙伴一起参与开发~</el-tag>
        </p>
        <p>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-cloudy"
            plain
            @click="goTarget('https://github.com/RegularFolk/community-cloud')"
            >访问GitHub项目主页</el-button
          >
        </p>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row style="font-size: 20px">
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>SpringCloud</li>
              <li>Nacos</li>
              <li>Sentinel</li>
              <li>Seata</li>
              <li>Minio</li>
              <li>RocketMQ</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Echarts</li>
              <li>Quill</li>
              <li>DPlayer</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>联系作者</span>
          </div>
          <div class="body">
            <img
              src="@/assets/images/wxPic.jpg"
              alt="donate"
              width="70%"
              height="70%"
            />
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="16">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>系统架构图</span>
          </div>
          <div class="body">
            <img
              src="@/assets/images/architecture.jpg"
              alt="donate"
              width="100%"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.6.3",
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

