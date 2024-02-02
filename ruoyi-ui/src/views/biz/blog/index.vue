<template>
  <!--  <el-container>
      &lt;!&ndash; 左侧目录   &ndash;&gt;
      <el-aside width="200px">

      </el-aside>
      &lt;!&ndash;中部推送帖子&ndash;&gt;
      <el-main>


      </el-main>

      &lt;!&ndash; 右侧热榜 &ndash;&gt;
      <el-container>


      </el-container>
    </el-container>-->
  <div style="background-color: #EEF1F6FF">
    <div class="fixed-box-left">
      <el-menu default-active="1">
        <el-menu-item index="1">
          <i class="el-icon-message"/>
          <span slot="title">猜你想看</span>
        </el-menu-item>
        <el-menu-item index="2">
          <i class="el-icon-message"/>
          <span slot="title">最新发布</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-message"/>
          <span slot="title">我的关注</span>
        </el-menu-item>
        <el-menu-item index="4">
          <i class="el-icon-message"/>
          <span slot="title">热门帖子</span>
        </el-menu-item>
        <el-menu-item index="5">
          <i class="el-icon-menu"/>
          <span slot="title">热门提问</span>
        </el-menu-item>
        <el-menu-item index="6">
          <i class="el-icon-setting"/>
          <span slot="title">热门回答</span>
        </el-menu-item>
        <el-menu-item index="7">
          <i class="el-icon-setting"/>
          <span slot="title">校园榜单</span>
        </el-menu-item>
      </el-menu>
    </div>

    <div class="recommended-post">
      <el-main>
        <el-card v-for="blog in this.testBlogs" :key="blog.username" shadow="hover" style="margin-bottom: 20px">
          <div slot="header" class="clearfix">
            <div style="width: 60px; float: left">
              <el-avatar :size="50" src="http://121.43.39.16:8082/ruoyi-test/2024/02/02/2b_20240202165949A006.jpg"/>
            </div>
            <div style="float: left;margin-left: 10px">
              <div>{{ blog.username }}</div>
              <div style="margin-top: 10px">{{ blog.postTime }}</div>
            </div>
            <el-button style="float: right; padding: 10px" type="primary">关注</el-button>
          </div>
          <div class="card-content">{{ blog.content }}</div>
          <div class="card-button">
            <el-button icon="el-icon-search" type="primary">查看</el-button>
          </div>
          <div class="card-button">
            <el-button icon="el-icon-edit" type="warning">评论</el-button>
          </div>
          <div class="card-button">
            <el-button icon="el-icon-thumb" type="danger">点赞</el-button>
          </div>
        </el-card>
      </el-main>
    </div>


    <!--    <div class="fixed-box-right">
          最新发布
          <el-menu>
            <el-menu-item v-for="trend in this.testTrends" :key="trend.id">
              {{ trend.title }}
            </el-menu-item>
          </el-menu>
        </div>-->


  </div>
</template>


<script>


export default {
  name: "blog",
  data() {
    return {
      tableData: Array(20).fill({
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }),
      testBlogs: [
        {
          username: 'name1',
          postTime: '2024年2月2日20:41:37',
          content: 'Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。Tip: 使用 CSS 最大的好处是，如果把 CSS 代码存放到外部样式表中，那么站点会更易于维护。通过编辑单一的文件，就可以改变所有页面的布局。如需学习更多有关 CSS 的知识，请访问我们的CSS 教程。\n' +
            '\n' +
            'Tip: 由于创建高级的布局非常耗时，使用模板是一个快速的选项。通过搜索引擎可以找到很多免费的网站模板（您可以使用这些预先构建好的网站布局，并优化它们）。'
        }, {
          username: 'name2',
          postTime: '2024年2月2日20:41:37',
          content: 'hhhhhhhhhhh'
        }, {
          username: 'name3',
          postTime: '2024年2月2日20:41:37',
          content: 'hhhhhhhhhhh'
        }, {
          username: 'name4',
          postTime: '2024年2月2日20:41:37',
          content: 'hhhhhhhhhhh'
        }, {
          username: 'name5',
          postTime: '2024年2月2日20:41:37',
          content: 'hhhhhhhhhhh'
        }, {
          username: 'name6',
          postTime: '2024年2月2日20:41:37',
          content: 'hhhhhhhhhhh'
        }, {
          username: 'name7',
          postTime: '2024年2月2日20:41:37',
          content: 'hhhhhhhhhhh'
        }
      ]
    }
  },
  created() {
  },
  methods: {}
};


</script>

<style>
.fixed-box-left {
  background-color: rgb(238, 241, 246);
  padding-top: 30px;
  padding-left: 30px;
  padding-right: 0;
  width: 215px;
  position: fixed
}

.fixed-box-right {
  background-color: rgb(238, 241, 246);
  position: fixed;
  margin-left: 1650px;
  margin-top: 30px
}

.recommended-post {
  background-color: #EEF1F6FF;
  padding-left: 230px;
  padding-right: 330px;
  float: left
}

.card-button {
  float: left;
  width: 33%;
  text-align: center;
  margin-top: 30px;
  margin-bottom: 20px;
}

.card-content {
  float: left;
  width: 100%;
  border-bottom: 1px solid rgb(238, 241, 246);
  padding-left: 40px;
  padding-right: 40px;
  padding-bottom: 20px;
}
</style>
