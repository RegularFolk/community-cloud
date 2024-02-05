import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 测试方法，获取博客
export function getTestBlogs() {
  return request({
    url: '/blog/blogController/randomList',
    method: 'get'
  })
}
