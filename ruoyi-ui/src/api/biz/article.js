import request from '@/utils/request'

// 上传文章
export function postArticle(data) {
  return request({
    url: '/blog/article/postArticle',
    method: 'post',
    data: data
  })
}

// 获取个人分类
export function getPersonClassDict() {
  return request({
    url: '/blog/article/getPersonClassList',
    method: 'get'
  })
}

// 随笔列表查询
export function getArticleList(data) {
  return request({
    url: '/blog/article/list?pageNum=' + data.pageNum + 'pageSize=' + data.pageSize,
    method: 'post',
    data: data
  })
}

// 删除随笔
export function deleteArticle(data) {
  return request({
    url: '/blog/article/deleteArticle',
    method: 'post',
    data: data
  })
}

// 新增个人分类
export function postPersonClass(data) {
  return request({
    url: '/blog/article/postPersonClass',
    method: 'post',
    data: data
  })
}

// 删除个人分类
export function deletePersonClass(data) {
  return request({
    url: '/blog/article/deletePersonClass',
    method: 'post',
    data: data
  })
}
