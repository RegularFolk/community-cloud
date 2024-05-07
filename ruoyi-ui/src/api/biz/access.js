import request from '@/utils/request'

// 生成凭证
export function generate(data) {
  return request({
    url: '/blog/access/generate/' + data,
    method: 'get'
  })
}

// 列表查询
export function accessList(data) {
  return request({
    url: '/blog/access/list',
    method: 'post',
    data: data
  })
}

// 改变状态
export function changeStatus(data) {
  return request({
    url: '/blog/access/changeStatus',
    method: 'post',
    data: data
  })
}

// 删除凭证
export function deleteAccess(data) {
  return request({
    url: '/blog/access/delete/' + data,
    method: 'post'
  })
}
