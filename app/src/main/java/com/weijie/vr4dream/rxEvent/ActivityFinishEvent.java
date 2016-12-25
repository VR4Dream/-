package com.weijie.vr4dream.rxEvent;

/**
 * activity结束自身的事件
 * 作者：guoweijie on 16/12/15 17:18
 * 邮箱：529844698@qq.com
 */
public class ActivityFinishEvent {

    private String currentActivitySimpleName;

    public ActivityFinishEvent(String currentActivitySimpleName) {
        this.currentActivitySimpleName = currentActivitySimpleName;
    }

    public String getName() {
        return currentActivitySimpleName;
    }

}
