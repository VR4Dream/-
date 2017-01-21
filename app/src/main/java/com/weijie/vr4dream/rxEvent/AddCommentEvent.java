package com.weijie.vr4dream.rxEvent;

import com.weijie.vr4dream.model.Comment;

/**
 * 添加评论事件
 * 作者：guoweijie on 16/12/31 08:59
 * 邮箱：529844698@qq.com
 */
public class AddCommentEvent {

    Comment comment;

    public AddCommentEvent(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
