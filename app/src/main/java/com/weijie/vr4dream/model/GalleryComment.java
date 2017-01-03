package com.weijie.vr4dream.model;

import cn.bmob.v3.BmobObject;

/**
 * VR评论
 * 作者：guoweijie on 16/12/28 10:37
 * 邮箱：529844698@qq.com
 */
public class GalleryComment extends BmobObject {

    private Gallery gallery;

    private VRUser author;

    private String content;

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
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
