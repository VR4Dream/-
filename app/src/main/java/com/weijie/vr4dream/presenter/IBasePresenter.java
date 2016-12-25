package com.weijie.vr4dream.presenter;

import com.weijie.vr4dream.rxEvent.ActivityFinishEvent;

/**
 * presenter基类
 * 作者：guoweijie on 16/12/15 17:16
 * 邮箱：529844698@qq.com
 */
public interface IBasePresenter {

    /**
     * 接收到activity结束的事件
     */
    void receiveActivityFinishEvent(ActivityFinishEvent event);

}
