package com.ruoyi.blog.domain.vo;

import com.ruoyi.blog.domain.BlogComment;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.SysUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 推文详情页评论对象
 */
public class BlogCommentVo {

    // 父子评论的总数
    private Long commentCnt;

    private Boolean hasMore = true;

    private List<CommentUnit> comments;

    public static class CommentUnit {
        private Long id;

        private Long parentId;

        private String senderName;

        private String receiverName;

        private String senderAvatar;

        private String sendTime;

        private Long likeCnt;

        private String content;

        private Long senderId;

        private Long receiverId;

        private List<CommentUnit> subComments = new ArrayList<>();

        public void packFromBlogComment(BlogComment blogComment, SysUser sender, SysUser receiver) {
            this.setId(blogComment.getId());
            this.setParentId(blogComment.getParentId());
            this.setLikeCnt(blogComment.getLikeCnt());
            this.setContent(blogComment.getContent());
            this.setSendTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, blogComment.getCreateTime()));
            if (sender != null) {
                this.setSenderAvatar(sender.getAvatar());
                this.setSenderName(sender.getNickName());
                this.setSenderId(sender.getUserId());
            }
            if (receiver != null) {
                this.setReceiverId(receiver.getUserId());
                this.setReceiverName(receiver.getNickName());
            }
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getParentId() {
            return parentId;
        }

        public void setParentId(Long parentId) {
            this.parentId = parentId;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public Long getLikeCnt() {
            return likeCnt;
        }

        public void setLikeCnt(Long likeCnt) {
            this.likeCnt = likeCnt;
        }

        public List<CommentUnit> getSubComments() {
            return subComments;
        }

        public void setSubComments(List<CommentUnit> subComments) {
            this.subComments = subComments;
        }

        public String getSenderAvatar() {
            return senderAvatar;
        }

        public void setSenderAvatar(String senderAvatar) {
            this.senderAvatar = senderAvatar;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Long getSenderId() {
            return senderId;
        }

        public void setSenderId(Long senderId) {
            this.senderId = senderId;
        }

        public Long getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(Long receiverId) {
            this.receiverId = receiverId;
        }

        @Override
        public String toString() {
            return "CommentUnit{" +
                    "id=" + id +
                    ", parentId=" + parentId +
                    ", senderName='" + senderName + '\'' +
                    ", receiverName='" + receiverName + '\'' +
                    ", senderAvatar='" + senderAvatar + '\'' +
                    ", sendTime='" + sendTime + '\'' +
                    ", likeCnt=" + likeCnt +
                    ", content='" + content + '\'' +
                    ", senderId=" + senderId +
                    ", receiverId=" + receiverId +
                    ", subComments=" + subComments +
                    '}';
        }
    }

    public Long getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Long commentCnt) {
        this.commentCnt = commentCnt;
    }

    public List<CommentUnit> getComments() {
        return comments;
    }

    public void setComments(List<CommentUnit> comments) {
        this.comments = comments;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Override
    public String toString() {
        return "BlogCommentVo{" +
                "commentCnt=" + commentCnt +
                ", comments=" + comments +
                ", hasMore=" + hasMore +
                '}';
    }
}

