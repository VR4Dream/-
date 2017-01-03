package com.weijie.vr4dream.model;

import cn.bmob.v3.BmobObject;

/**
 * VR场景收藏
 * 作者：guoweijie on 16/12/28 10:42
 * 邮箱：529844698@qq.com
 */
public class GalleryFavourite extends BmobObject {

    private VRUser owner;

    private Gallery gallery;

    public VRUser getOwner() {
        return owner;
    }

    public void setOwner(VRUser owner) {
        this.owner = owner;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }
}
