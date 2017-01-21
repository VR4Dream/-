package com.weijie.vr4dream.presenter.user;

import com.weijie.vr4dream.presenter.IBasePresenter;

/**
 * 第三方注册绑定
 * 作者：guoweijie on 17/1/20 20:10
 * 邮箱：529844698@qq.com
 */
public interface IRegisterPresenter extends IBasePresenter {

    /**
     * 获取验证码
     */
    void requestSMSCode(String tel);

    /**
     * 注册
     */
    void register(String tel, String psw, String psw2, String code);

}
