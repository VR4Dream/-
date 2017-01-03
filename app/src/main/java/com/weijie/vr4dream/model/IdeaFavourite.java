package com.weijie.vr4dream.model;

import cn.bmob.v3.BmobObject;

/**
 * 文章收藏
 * 作者：guoweijie on 16/12/29 11:40
 * 邮箱：529844698@qq.com
 */
public class IdeaFavourite extends BmobObject {

    private VRUser owner;

    private Idea idea;

    public VRUser getOwner() {
        return owner;
    }

    public void setOwner(VRUser owner) {
        this.owner = owner;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

}
