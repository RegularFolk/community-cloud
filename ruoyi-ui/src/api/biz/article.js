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

// 获取随笔
export function getArticle(articleId) {
  return request({
    url: '/blog/article/getArticle/' + articleId,
    method: 'get'
  })
}

// 收藏随笔
export function collect(data) {
  return request({
    url: '/blog/article/collect',
    method: 'post',
    data: data
  })
}

// 取消收藏随笔
export function cancelCollect(data) {
  return request({
    url: '/blog/article/cancelCollect',
    method: 'post',
    data: data
  })
}

// 收藏随笔列表查询
export function collectList(data) {
  return request({
    url: '/blog/article/collect/list',
    method: 'post',
    data: data
  })
}

// 查询指定用户随笔
export function getPersonArticle(data) {
  return request({
    url: '/blog/article/getPersonalArticle',
    method: 'post',
    data: data
  })
}

// 随笔广场列表查询
export function squareList(data) {
  return request({
    url: '/blog/article/square/list',
    method: 'post',
    data: data
  })
}

// 查询阅读排行榜
export function getArticleViewRank() {
  return request({
    url: '/blog/article/viewRank',
    method: 'post'
  })
}

// 查询点赞排行榜
export function getArticleLikeRank() {
  return request({
    url: '/blog/article/likeRank',
    method: 'post'
  })
}

// 查询收藏排行榜
export function getArticleCollectRank() {
  return request({
    url: '/blog/article/collectRank',
    method: 'post'
  })
}











