<template>
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

      <el-form-item>
        <el-button :disabled="loading" icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索
        </el-button>
        <el-button :disabled="loading" icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>

    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="handleQuery"/>
    </el-row>

    <!-- 文章表格 -->
    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="articleList"
      row-key="id"
    >
      <el-table-column :show-overflow-tooltip="true" label="标题" prop="title"/>
      <el-table-column :show-overflow-tooltip="true" label="预览" prop="preview"/>
      <el-table-column :show-overflow-tooltip="true" label="作者" prop="authorName" width="150"/>
      <el-table-column align="center" label="分类" prop="articleClassify">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.blog_article_classification" :value="scope.row.articleClassification + ''"/>
        </template>
      </el-table-column>

      <el-table-column align="center" label="状态" prop="status" width="100">
        <template slot-scope="scope">
          <el-tooltip :disabled="scope.row.status !== disabledStatus" class="item" content="该随笔已被作者设置隐藏或被删除"
                      effect="dark"
                      placement="top">
            <dict-tag :options="dict.type.blog_collect_status" :value="scope.row.status"/>
          </el-tooltip>
        </template>
      </el-table-column>

      <el-table-column align="center" label="收藏时间" prop="collectTime" width="200"/>
      <el-table-column align="center" label="发布时间" prop="publishTime" width="200"/>
      <el-table-column align="center" label="点赞数" prop="likeCnt" width="80"/>
      <el-table-column align="center" label="评论数" prop="commentCnt" width="80"/>
      <el-table-column align="center" label="浏览量" prop="viewCnt" width="80"/>
      <el-table-column align="center" label="收藏数" prop="collectCnt" width="80"/>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-tooltip :disabled="scope.row.status !== disabledStatus" class="item" content="该随笔已被作者设置隐藏或被删除"
                      effect="dark"
                      placement="top">
            <el-button
              :disabled="scope.row.status === disabledStatus"
              icon="el-icon-search"
              size="mini"
              type="text"
              @click="browseArticle(scope.row)"
            >查看
            </el-button>
          </el-tooltip>
          <el-button
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="confirmCancelCollect(scope.row)"
          >取消收藏
          </el-button>
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


  </div>

</template>


<script>

import {cancelCollect, collectList} from "@/api/biz/article";

export default {
  name: 'ArticleFavorite',
  dicts: ['blog_article_classification', 'blog_collect_status'],
  data() {
    return {
      total: 0,
      refreshTable: true,
      loading: false,
      showSearch: true,
      queryParams: {
        title: '',
        articleClassification: '',
        pageNum: 1,
        pageSize: 10,
      },
      articleList: [],
      disabledStatus: 2, // 初始化为字典值
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      this.handleQuery()
    },
    // 进行查询
    handleQuery() {
      if (!this.loading) {
        this.loading = true
        collectList(this.queryParams).then(resp => {
          if (resp.code === 200) {
            this.articleList = resp.data.list
            this.total = resp.data.total
          } else {
            this.$message({
              message: resp.msg,
              type: 'error'
            })
          }
        }).finally(() => {
          this.loading = false
        })
      }
    },
    // 重置查询条件并刷新
    resetQuery() {
      this.queryParams = {
        title: '',
        articleClassification: '',
        pageNum: 1,
        pageSize: 10,
      }

      this.handleQuery()
    },
    // 跳转到查看随笔页面
    browseArticle(row) {
      this.$router.push({
        path: '/article/browse',
        query: {
          articleId: row.id
        }
      })
    },
    // 确认取消删除
    confirmCancelCollect(row) {
      this.$confirm('确认对 《' + row.title + '》 取消收藏吗？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.cancelCollect(row)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '操作已取消'
        });
      });
    },
    // 取消收藏
    cancelCollect(row) {
      let dto = {
        id: row.id
      }
      cancelCollect(dto).then(resp => {
        if (resp.code === 200) {
          this.$message({
            message: '操作成功！',
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


    },


  },


}


</script>
