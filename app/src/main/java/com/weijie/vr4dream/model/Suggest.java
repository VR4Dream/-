package com.weijie.vr4dream.model;

import cn.bmob.v3.BmobObject;

/**
 * 意见
 * 作者：guoweijie on 16/12/27 11:08
 * 邮箱：529844698@qq.com
 */
public class Suggest extends BmobObject {

    private String content;

    public Suggest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
