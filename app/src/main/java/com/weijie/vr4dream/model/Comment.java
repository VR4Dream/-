package com.weijie.vr4dream.model;

/**
 * 作者：guoweijie on 17/1/12 20:07
 * 邮箱：529844698@qq.com
 */
public class Comment {

    private VRUser author;

    private String content;

    private String createdAt;

    public Comment(VRUser author, String content, String createdAt) {
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
