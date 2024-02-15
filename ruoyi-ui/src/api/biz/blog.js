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



