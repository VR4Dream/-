package com.weijie.vr4dream.ui.view.user;

import com.weijie.vr4dream.ui.view.IBaseView;

/**
 * 登录
 * 作者：guoweijie on 16/12/16 13:59
 * 邮箱：529844698@qq.com
 */
public interface ILoginView extends IBaseView {

    /**
     * 点击登陆
     */
    void clickLogin();

    /**
     * 设置用户名
     * @param userName 用户名
     */
    void setUserName(String userName);

    /**
     * 设置密码
     * @param password 密码
     */
    void setPassword(String password);

    /**
     * 设置顶部账号icon提示的alpha值
     *
     * @param alpha 透明值
     */
    void setAccountTipsIconAlpha(int alpha);

    /**
     * 设置顶部密码icon提示的alpha值
     *
     * @param alpha 透明值
     */
    void setPasswordTipsIconAlpha(int alpha);

    /**
     * 验证短信已发送成功
     */
    void getVerifyCodeSuccess();

}
