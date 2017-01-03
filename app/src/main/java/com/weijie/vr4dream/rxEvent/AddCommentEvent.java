package com.weijie.vr4dream.rxEvent;

import com.weijie.vr4dream.model.IdeaComment;

/**
 * 添加评论事件
 * 作者：guoweijie on 16/12/31 08:59
 * 邮箱：529844698@qq.com
 */
public class AddCommentEvent {

    IdeaComment comment;

    public AddCommentEvent(IdeaComment comment) {
        this.comment = comment;
    }

    public IdeaComment getComment() {
        return comment;
    }

    public void setComment(IdeaComment comment) {
        this.comment = comment;
    }
}
