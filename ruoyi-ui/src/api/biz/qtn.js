import request from '@/utils/request'

// 新增/编辑/回答问题
export function postQtn(data) {
  return request({
    url: '/blog/qtn/postQtn',
    method: 'post',
    data: data
  })
}

// 删除问题
export function delQtn(data) {
  return request({
    url: '/blog/qtn/delQtn',
    method: 'post',
    data: data
  })
}


// 问题列表查询
export function qtnList(data) {
  return request({
    url: '/blog/qtn/list',
    method: 'post',
    data: data
  })
}

// 问题详情
export function qtnDetail(data) {
  return request({
    url: '/blog/qtn/detail',
    method: 'post',
    data: data
  })
}

// 回答列表查询
export function ansList(data) {
  return request({
    url: '/blog/qtn/ansList',
    method: 'post',
    data: data
  })
}

// 关注问题
export function qtnCollect(data) {
  return request({
    url: '/blog/qtn/collect',
    method: 'post',
    data: data
  })
}

// 采纳回答
export function qtnAnsAcpt(data) {
  return request({
    url: '/blog/qtn/accept',
    method: 'post',
    data: data
  })
}

