package com.weijie.vr4dream.presenter;

import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;

/**
 * 首页 presenter
 * 作者：guoweijie on 16/12/16 10:32
 * 邮箱：529844698@qq.com
 */
public interface IMainPresenter extends IBasePresenter {

    /**
     * 用户登陆状态更改
     *
     * @param event 事件
     */
    void loginStateChange(LoginStateChangeEvent event);

}
