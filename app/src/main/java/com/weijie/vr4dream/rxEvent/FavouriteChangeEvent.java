package com.weijie.vr4dream.rxEvent;

/**
 * 收藏数改变
 * 作者：guoweijie on 16/12/24 16:09
 * 邮箱：529844698@qq.com
 */
public class FavouriteChangeEvent {

    // true gallery false idea
    private boolean tag;

    // true + false -
    private boolean active;

    public FavouriteChangeEvent(boolean tag, boolean active) {
        this.tag = tag;
        this.active = active;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
