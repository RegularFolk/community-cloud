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
