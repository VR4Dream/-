package com.weijie.vr4dream.rxEvent;

/**
 * 第三方绑定成功事件
 * 作者：guoweijie on 17/1/21 10:23
 * 邮箱：529844698@qq.com
 */
public class BindThirdEvent {
    private boolean isBind;

    public BindThirdEvent(boolean isBind) {
        this.isBind = isBind;
    }

    public boolean isBind() {
        return isBind;
    }
}
