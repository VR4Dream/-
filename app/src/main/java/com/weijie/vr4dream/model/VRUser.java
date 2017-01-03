package com.weijie.vr4dream.model;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户
 * 作者：guoweijie on 16/12/26 17:22
 * 邮箱：529844698@qq.com
 */
public class VRUser extends BmobUser {

    private Boolean sex;

    private BmobFile icon;

    private List<String> style;

    private Integer galleryFavourite;

    public Boolean getSex() {
        return sex;
    }

    public BmobFile getIcon() {
        return icon;
    }

    public List<String> getStyle() {
        return style;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }

    public void setStyle(List<String> style) {
        this.style = style;
    }

    public Integer getGalleryFavourite() {
        return galleryFavourite;
    }

    public void setGalleryFavourite(Integer galleryFavourite) {
        this.galleryFavourite = galleryFavourite;
    }

    @Override
    public boolean equals(Object o) {
        if(((BmobObject)o).getObjectId().equals(getObjectId())) {
            return true;
        }
        return false;
    }
}
