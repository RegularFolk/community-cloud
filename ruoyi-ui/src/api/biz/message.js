import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 获取联系人列表
export function getContactList(data) {
  return request({
    url: '/notify/message/contactList',
    method: 'post',
    data: data
  })
}

// 查询message
export function msgList(data) {
  return request({
    url: '/notify/message/msgList',
    method: 'post',
    data: data
  })
}

// 发送消息
export function sendMSg(data) {
  return request({
    url: '/notify/message/send',
    method: 'post',
    data: data
  })
}

// 拉取消息
export function pullMsg(data) {
  return request({
    url: '/notify/message/pullMsg',
    method: 'post',
    data: data
  })
}
