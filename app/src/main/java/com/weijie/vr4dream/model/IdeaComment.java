package com.weijie.vr4dream.model;

import cn.bmob.v3.BmobObject;

/**
 * 文章评论
 * 作者：guoweijie on 16/12/28 10:22
 * 邮箱：529844698@qq.com
 */
public class IdeaComment extends BmobObject {

    private Idea idea;

    private VRUser author;

    private String content;

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public VRUser getAuthor() {
        return author;
    }

    public void setAuthor(VRUser author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
