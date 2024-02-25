import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 测试方法，获取博客
export function getTestBlogs() {
  return request({
    url: '/blog/blogController/randomList',
    method: 'get'
  })
}

// 获取评论
export function getComment(data) {
  return request({
    url: '/blog/comment/getComment',
    method: 'post',
    data: data
  })
}

// 添加评论
export function postComment(data) {
  return request({
    url: '/blog/comment/postComment',
    method: 'post',
    data: data
  })
}

// MQ测试接口
export function testMq() {
  return request({
    url: '/blog/test/test',
    method: 'get'
  })
}

// 点赞接口
export function like(data) {
  return request({
    url: '/blog/blogController/like',
    method: 'post',
    data: data
  })
}

// 提价想法接口
export function submitBlog(data) {
  return request({
    url: '/blog/blogController/postBlog',
    method: 'post',
    data: data
  })
}

// 查看想法详情接口
export function getBlogDetail(data) {
  return request({
    url: '/blog/blogController/getBlog/' + data,
    method: 'get'
  })
}

// 评论点赞接口
export function commentLike(data) {
  return request({
    url: '/blog/comment/like',
    method: 'post',
    data: data
  })
}




