package com.weijie.vr4dream.presenter.user;

import com.weijie.vr4dream.presenter.IBasePresenter;

/**
 * 登录界面
 * 作者：guoweijie on 16/12/16 14:05
 * 邮箱：529844698@qq.com
 */
public interface ILoginPresenter extends IBasePresenter {

    /**
     * 登陆
     * @param tel 用户名
     * @param password 密码
     */
    void login(String tel, String password);

    /**
     * 注册
     */
    void register(String tel, String psw, String psw2, String code);

    /**
     * 获取验证码
     */
    void requestSMSCode(String tel);

    /**
     * 发送用户登录状态
     */
    void loginState();

    /**
     * 发送用户退出状态
     */
    void logoutState();

}
