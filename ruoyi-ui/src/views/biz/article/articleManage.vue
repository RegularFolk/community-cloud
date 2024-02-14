<template>
  <!-- 随笔管理页面，样式参考菜单管理，ruoyi-ui/src/views/system/menu/index.vue -->

  <div class="app-container">

    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" size="small">
      <el-form-item label="随笔标题" prop="title">
        <el-input
          v-model="queryParams.title"
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
      <el-form-item label="文章分类" prop="articleClassification">
        <el-select v-model="queryParams.articleClassification" clearable placeholder="文章分类">
          <el-option
            v-for="dict in dict.type.blog_article_classification"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="个人分类" prop="personClassification">
        <el-select v-model="queryParams.personClassification" clearable placeholder="个人分类">
          <el-option
            v-for="dict in personClassDict"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="loading" icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索
        </el-button>
        <el-button :disabled="loading" icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          icon="el-icon-plus"
          plain
          size="mini"
          type="primary"
          @click="handleAddClass"
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="handleQuery"/>
    </el-row>

    <!-- 文章表格 -->
    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="articleList"
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
      <el-table-column align="center" label="分类" prop="articleClassify">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.blog_article_classification" :value="scope.row.articleClassify"/>
        </template>
      </el-table-column>
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
              @click="handleAddClass"
            >新增
            </el-button>
            <el-button
              icon="el-icon-delete"
              size="mini"
              type="text"
              @click="deleteClass(scope.row)"
              v-if="scope.row.className !== '默认分类'"
            >删除
            </el-button>
          </div>

          <div v-if="!scope.row.isClass">
            <el-button
              icon="el-icon-search"
              size="mini"
              type="text"
              @click="browseArticle(scope.row)"
            >查看
            </el-button>
            <el-button
              icon="el-icon-edit"
              size="mini"
              type="text"
              @click="editArticle(scope.row)"
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

    <!-- 分页栏 -->
    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="handleQuery"
    />

    <!-- 新增分类名称输入框 -->
    <el-dialog :visible.sync="showNewClassDialog" append-to-body title="新增分类">
      <div class="flexClass">
        <div style="font-size: medium;padding-top: 4px">请输入分类名称：</div>
        <el-input v-model="classNameInput" :maxlength="classNameLimit" :placeholder="classNameLimit + '字以内'"
                  style="width: 50%;"/>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitNewClass">确 定</el-button>
        <el-button @click="cancelSubmitNewClass">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 删除分类确认弹窗 -->
    <el-dialog :visible.sync="showDeleteClassDialog" append-to-body title="确认删除">
      <div class="flexClass">
        <div style="font-size: medium;padding-top: 4px">请确认随笔处理模式：</div>
        <el-radio-group v-model="classDeleteType">
          <el-radio v-for="type in dict.type.blog_person_class_delete_type" :key="type.value" :label="type.value" border>{{type.label}}</el-radio>
        </el-radio-group>

      </div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmDeleteClass">确 定</el-button>
        <el-button @click="cancelDeleteClass">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>

import {deleteArticle, deletePersonClass, getArticleList, getPersonClassDict, postPersonClass} from "@/api/biz/article";

export default {
  name: 'ArticleManage',
  dicts: [
    'blog_article_status',
    'blog_article_classification',
    'blog_person_class_delete_type',
  ],
  data() {
    return {
      loading: false,
      showNewClassDialog: false,
      showDeleteClassDialog: false,
      classDeleteType: '1',
      deleteClassId: '',
      total: 0,
      queryParams: {
        title: '',
        status: '',
        articleClassification: '',
        personClassification: '',
        pageNum: 1,
        pageSize: 10,
      },
      personClassDict: [],
      classNameInput: '',
      classNameLimit: 10,
      refreshTable: true,
      showSearch: true,
      isExpandAll: false,
      articleList: [],

    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      this.initPersonClassDict()
      this.handleQuery()
    },
    // 初始化个人分类
    initPersonClassDict() {
      getPersonClassDict().then(resp => {
        this.personClassDict = resp.data
      })
    },
    // 编辑随笔路由跳转
    editArticle(row) {
      this.$router.push({
        path: '/article/articleModify',
        query: {
          articleId: row.articleId
        }
      })
    },
    // 跳转到查看随笔页面
    browseArticle(row) {
      this.$router.push({
        path: '/article/browse',
        query: {
          articleId: row.articleId
        }
      })
    },
    // 确认删除个人分类
    confirmDeleteClass() {

      let dto = {
        deleteType: this.classDeleteType,
        classId: this.deleteClassId
      }

      deletePersonClass(dto).then(resp => {
        if (resp.code === 200) {
          this.$message({
            message: '删除成功！',
            type: 'success'
          })
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).catch(ex => {
        this.$message({
          message: ex.message,
          type: 'error'
        })
      }).finally(() => {
        this.showDeleteClassDialog = false
        this.handleQuery()
      })

    },
    // 打开删除随笔弹窗
    deleteArticle(row) {
      this.$confirm('确认删除 ' + row.title + ' 吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let dto = {
          id: row.articleId
        }
        deleteArticle(dto).then(resp =>{
          if (resp.code === 200) {
            this.$message({
              message: '删除成功！',
              type: 'success'
            })
          } else {
            this.$message({
              message: resp.msg,
              type: 'error'
            })
          }
        }).finally(() => {
          this.handleQuery()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 删除个人分类
    deleteClass(row) {
      this.showDeleteClassDialog = true
      this.deleteClassId = row.classId

    },
    // 关闭新建分类弹窗
    cancelSubmitNewClass() {
      this.showNewClassDialog = false
    },
    // 关闭删除个人分类弹窗
    cancelDeleteClass() {
      this.showDeleteClassDialog = false
      this.deleteClassId = ''
    },
    // 提交新增个人分类
    submitNewClass() {
      // 非空判断
      if (this.classNameInput === null || this.classNameInput.trim().length === 0) {
        this.$message({
          message: '无法提交空内容！',
          type: 'error'
        })
        return
      }

      let dto = {
        className: this.classNameInput
      }

      postPersonClass(dto).then(resp => {
        if (resp.code === 200) {
          this.$message({
            message: '添加成功！',
            type: 'success'
          })
          this.initPersonClassDict()
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      })

      this.classNameInput = ''
      this.cancelSubmitNewClass()
    },
    clickRow(row, column, event) {
      // console.log('row', row)
      // console.log('column', column)
      // console.log('event', event)
    },
    handleQuery() {
      if (!this.loading) {
        this.loading = true
        getArticleList(this.queryParams).then(resp => {
          this.articleList = resp.rows
          this.total = resp.total
        }).finally(() => {
          this.loading = false
        })
      }
    },
    resetQuery() {
      this.queryParams = {
        title: '',
        status: '',
        articleClassification: '',
        personClassification: '',
        pageNum: 1,
        pageSize: 10,
      }

      this.handleQuery()
    },
    // 打开新增个人分类弹窗
    handleAddClass() {
      this.showNewClassDialog = true
    },
    // 利用v-if实现全部展开，原理是先把table取消掉，设置默认全部展开后再重新渲染
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },

  }


}

</script>


<style>
.flexClass {
  display: flex;
  flex-wrap: wrap;
}

</style>
