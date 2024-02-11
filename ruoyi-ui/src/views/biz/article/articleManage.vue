<template>
  <!-- 随笔管理页面，样式参考菜单管理，ruoyi-ui/src/views/system/menu/index.vue -->

  <div class="app-container">

    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" size="small">
      <el-form-item label="随笔标题" prop="menuName">
        <el-input
          v-model="queryParams.menuName"
          clearable
          placeholder="请输入随笔标题"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" clearable placeholder="随笔状态">
          <el-option
            v-for="dict in dict.type.blog_article_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="文章分类" prop="articleClassId">
        <el-select v-model="queryParams.articleClassId" clearable placeholder="文章分类">
          <el-option
            v-for="dict in dict.type.blog_article_classification"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>

      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          icon="el-icon-plus"
          plain
          size="mini"
          type="primary"
          @click="handleAdd"
        >新增个人分类
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          icon="el-icon-sort"
          plain
          size="mini"
          type="info"
          @click="toggleExpandAll"
        >展开/折叠
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"/>
    </el-row>

    <!-- 文章表格 -->
    <el-table
      v-if="refreshTable"
      :data="testArticle"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      row-key="id"
      @row-click="clickRow"
    >
      <el-table-column label="个人分类" prop="className" width="160"/>
      <el-table-column :show-overflow-tooltip="true" label="标题" prop="title"/>
      <el-table-column :show-overflow-tooltip="true" label="预览" prop="preview"/>
      <el-table-column align="center" label="状态" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.blog_article_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="分类" prop="articleClassify"/>
      <el-table-column align="center" label="发布时间" prop="publishTime" width="200"/>
      <el-table-column align="center" label="创建时间" prop="createTime" width="200"/>
      <el-table-column align="center" label="点赞数" prop="likeCnt" width="80"/>
      <el-table-column align="center" label="评论数" prop="commentCnt" width="80"/>
      <el-table-column align="center" label="浏览量" prop="viewCnt" width="80"/>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <div v-if="scope.row.isClass">
            <el-button
              icon="el-icon-plus"
              size="mini"
              type="text"
              @click="handleAdd"
            >新增
            </el-button>
            <el-button
              icon="el-icon-delete"
              size="mini"
              type="text"
              @click="deleteClass(scope.row)"
            >删除
            </el-button>
          </div>

          <div v-if="!scope.row.isClass">
            <el-button
              icon="el-icon-search"
              size="mini"
              type="text"
            >查看
            </el-button>
            <el-button
              icon="el-icon-edit"
              size="mini"
              type="text"
            >修改
            </el-button>
            <el-button
              icon="el-icon-delete"
              size="mini"
              type="text"
              @click="deleteArticle(scope.row)"
            >删除
            </el-button>
          </div>
        </template>

      </el-table-column>

    </el-table>

    <!-- 新增分类名称输入框 -->
    <el-dialog :title="title" :visible.sync="showDialog" append-to-body>
      <div class="flexClass">
        <div style="font-size: medium;padding-top: 4px">请输入分类名称：</div>
        <el-input v-model="classNameInput" maxlength="10" placeholder="请输入分类名称" style="width: 50%;"/>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitNewClass">确 定</el-button>
        <el-button @click="cancelSubmitNewClass">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>

export default {
  name: 'articleManege',
  dicts: ['blog_article_status', 'blog_article_classification'],
  data() {
    return {
      title: '新增分类',
      showDialog: false,
      queryParams: {
        menuName: '',
        status: '',
        articleClassId: '',
      },
      classNameInput: '',
      refreshTable: true,
      showSearch: true,
      isExpandAll: false,
      testArticle: [
        {
          id: '1',
          classId: '1',
          className: '默认分类',
          createTime: '2024年2月11日17:31:31',
          isClass: true,
          children: [
            {
              id: '10',
              articleId: '10',
              title: '文章一的标题',
              preview: '少时诵诗书所所所所所所adasdasdasdasdasdasdasdasdasdasdasd',
              status: '1',
              articleClassify: '技术分享',
              publishTime: '2024年2月11日17:50:08',
              createTime: '2024年2月11日17:31:31',
              likeCnt: 1,
              commentCnt: 2,
              viewCnt: 3,
              isClass: false,
            }, {
              id: '12',
              articleId: '12',
              title: '文章二的标题',
              preview: '少时诵诗书所所所所所所',
              status: '2',
              articleClassify: '新闻资讯',
              publishTime: '2024年2月11日17:50:08',
              createTime: '2024年2月11日17:31:31',
              likeCnt: 1,
              commentCnt: 2,
              viewCnt: 3,
              isClass: false,
            }
          ]
        }, {
          id: '2',
          classId: '2',
          className: '个人分类一',
          createTime: '2024年2月11日17:31:31',
          isClass: true,
          children: [
            {
              id: '13',
              articleId: '13',
              title: '文章三的标题',
              preview: '少时诵诗书所所所所所所',
              status: '1',
              articleClassify: '心得总结',
              publishTime: '2024年2月11日17:50:08',
              createTime: '2024年2月11日17:31:31',
              likeCnt: 1,
              commentCnt: 2,
              viewCnt: 3,
              isClass: false,
            }, {
              id: '14',
              articleId: '14',
              title: '文章四的标题',
              preview: '少时诵诗书所所所所所所',
              status: '2',
              articleClassify: '碎碎念',
              publishTime: '2024年2月11日17:50:08',
              createTime: '2024年2月11日17:31:31',
              likeCnt: 1,
              commentCnt: 2,
              viewCnt: 3,
              isClass: false,
            }
          ]
        }


      ]
    }
  },
  created() {

  },
  methods: {
    deleteArticle(row) {
      this.$confirm('确认删除 ' + row.title + ' 吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    deleteClass(row) {
      console.log('row', row)
      this.$confirm('确认删除 ' + row.className + ' 吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });


    },
    cancelSubmitNewClass() {
      this.showDialog = false
    },
    submitNewClass() {
      // 非空判断
      if (this.classNameInput === null || this.classNameInput.trim().length === 0) {
        this.$message({
          message: '无法提交空内容！',
          type: 'error'
        })
        return
      }

      // 重名判断
      if (this.testArticle.find(u => u.className === this.classNameInput)) {
        this.$message({
          message: '类型名称已存在！',
          type: 'error'
        })
        return
      }


      this.cancelSubmitNewClass()
    },
    clickRow(row, column, event) {
      // console.log('row', row)
      // console.log('column', column)
      // console.log('event', event)
    },
    handleQuery() {
      // console.log('dict', this.dict)
    },
    resetQuery() {

    },
    handleAdd() {
      this.showDialog = true
    },
    // 利用v-if实现全部展开，原理是先把table取消掉，设置默认全部展开后再重新渲染
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    getList() {

    }
  }


}

</script>


<style>
.flexClass {
  display: flex;
  flex-wrap: wrap;
}

</style>
