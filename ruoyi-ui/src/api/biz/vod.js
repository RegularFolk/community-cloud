import request from '@/utils/request'

// 根据视频id获取视频播放url
export function getVodUrl(data) {
  return request({
    url: '/file/getVodUrl/' + data,
    method: 'get'
  })
}

// 根据视频Id删除视频
export function delVodUrl(data) {
  return request({
    url: '/file/delVod/' + data,
    method: 'get'
  })
}

// 新建/编辑课程
export function postCourse(data) {
  return request({
    url: '/blog/course/postCourse',
    method: 'post',
    data: data
  })
}

// 新建/编辑章节
export function postChapter(data) {
  return request({
    url: '/blog/course/postChapter',
    method: 'post',
    data: data
  })
}

// 新建/编辑视频
export function postVideo(data) {
  return request({
    url: '/blog/course/postVideo',
    method: 'post',
    data: data
  })
}

// 删除课程
export function delCourse(data) {
  return request({
    url: '/blog/course/delCourse',
    method: 'post',
    data: data
  })
}

// 删除章节
export function delChapter(data) {
  return request({
    url: '/blog/course/delChapter',
    method: 'post',
    data: data
  })
}

// 删除视频
export function delVideo(data) {
  return request({
    url: '/blog/course/delVideo',
    method: 'post',
    data: data
  })
}

// 课程列表查询
export function list(data) {
  return request({
    url: '/blog/course/list',
    method: 'post',
    data: data
  })
}

// 查询课程详情页
export function courseDetail(data) {
  return request({
    url: '/blog/course/courseDetail',
    method: 'post',
    data: data
  })
}

// 查看视频
export function videoDetail(data) {
  return request({
    url: '/blog/course/videoDetail',
    method: 'post',
    data: data
  })
}



