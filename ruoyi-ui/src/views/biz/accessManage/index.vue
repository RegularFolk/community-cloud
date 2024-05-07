<template>

  <div class="app-container">

    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" size="small">
      <el-form-item label="手机号码" prop="phoneNumber">
        <el-input
          v-model="queryParams.phoneNumber"
          clearable
          placeholder="请输入手机号码"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="凭证状态" prop="status">
        <el-select v-model="queryParams.status" clearable placeholder="凭证状态">
          <el-option
            v-for="dict in dict.type.sys_access_status"
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
          @click="openDialog"
        >新增访问凭证
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="handleQuery"/>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="accessList"
      row-key="id"
    >
      <el-table-column label="手机号码" prop="phoneNumber"/>
      <el-table-column label="AccessKey ID" prop="accessKeyId"/>
      <el-table-column align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_access_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="最后使用时间" prop="lastUseTime"/>
      <el-table-column align="center" label="创建时间" prop="createTime"/>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <div>
            <el-button
              icon="el-icon-plus"
              size="mini"
              type="text"
              @click="handleDisable(scope.row)"
              :disabled="scope.row.status == 2"
            >禁用
            </el-button>
            <el-button
              icon="el-icon-delete"
              size="mini"
              type="text"
              @click="handleEnable(scope.row)"
              :disabled="scope.row.status == 1"
            >启用
            </el-button>
            <el-button
              icon="el-icon-delete"
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
            >
              删除
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
    <el-dialog :visible.sync="showDialog" append-to-body title="新增凭证">
      <div style="display: flex">
        <div style="font-size: medium;padding-top: 4px">请输入手机号码：</div>
        <el-input v-model="phoneNumberInput" style="width: 50%;"/>
      </div>

      <el-dialog
        title="请妥善保存您的凭证，您将无法再次查看到生成的AccessKeySecret！"
        :visible.sync="innerVisible"
        append-to-body>
        <div>Access Key ID ： {{generateRes.accessKeyId}}</div>
        <div>Access Key SECRET ： {{generateRes.accessKeySecret}}</div>

        <div slot="footer" class="dialog-footer">
          <el-button :loading="loading" type="primary" @click="closeAllDialog">关 闭</el-button>
        </div>
      </el-dialog>

      <div slot="footer" class="dialog-footer">
        <el-button :loading="loading" type="primary" @click="handleAddAccess">确 定</el-button>
        <el-button :loading="loading" @click="closeDialog">取 消</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>

import {accessList, changeStatus, deleteAccess, generate} from "@/api/biz/access";

export default {
  name: 'AccessManage',
  dicts: [
    'sys_access_status',
  ],
  data() {
    return {
      showSearch: true,
      loading: false,
      innerVisible: false,
      total: 100,
      queryParams: {
        phoneNumber: '',
        status: '',
        pageSize: 10,
        pageNum: 1
      },
      refreshTable: true,
      accessList: [

      ],
      showDialog: false,
      phoneNumberInput: '',
      generateRes: {}
    }
  },
  created() {
    this.handleQuery()
  },
  methods: {
    handleQuery() {
      this.loading = true
      accessList(this.queryParams).then(resp => {
        if (resp.code === 200) {
          this.accessList = resp.data
          this.total = resp.total
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
        }
      }).finally(() => {
        this.loading = false
      })
    },
    handleAddAccess() {
      this.loading = true
      generate(this.phoneNumberInput).then(resp => {
        if (resp.code === 200) {
          this.$message({
            message: '操作成功！',
            type: 'success'
          })
          this.generateRes = resp.data
          this.innerVisible = true
        } else {
          this.$message({
            message: resp.msg,
            type: 'error'
          })
          this.showDialog = false
        }
      }).finally(() => {
        this.loading = false
        this.phoneNumberInput = ''
      })
    },
    openDialog() {
      this.showDialog = true
    },
    closeDialog() {
      this.showDialog = false
    },
    closeAllDialog() {
      this.$confirm('请确认已保存访问凭证！', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.innerVisible = false
        this.showDialog = false
        this.handleQuery()
      })
    },
    handleDelete(row) {
      this.$confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteAccess(row.phoneNumber).then(resp => {
          if (resp.code === 200) {
            this.$message({
              message: '操作成功！',
              type: 'success'
            })
            this.handleQuery()
          } else {
            this.$message({
              message: resp.msg,
              type: 'error'
            })
          }
        })
      })
    },
    handleEnable(row) {
      this.$confirm('确定要启用吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeStatus({phoneNumber: row.phoneNumber, status: 1}).then(resp => {
          if (resp.code === 200) {
            this.$message({
              message: '操作成功！',
              type: 'success'
            })
            this.handleQuery()
          } else {
            this.$message({
              message: resp.msg,
              type: 'error'
            })
          }
        })
      })
    },
    handleDisable(row) {
      this.$confirm('确定要停用吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeStatus({phoneNumber: row.phoneNumber, status: 2}).then(resp => {
          if (resp.code === 200) {
            this.$message({
              message: '操作成功！',
              type: 'success'
            })
            this.handleQuery()
          } else {
            this.$message({
              message: resp.msg,
              type: 'error'
            })
          }
        })
      })
    },
    resetQuery() {
      this.queryParams = {
        phoneNumber: '',
        status: '',
        pageSize: 10,
        pageNum: 1
      }
      this.handleQuery()
    }
  }
}

</script>
