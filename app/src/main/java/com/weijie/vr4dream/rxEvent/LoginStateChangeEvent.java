package com.weijie.vr4dream.rxEvent;

/**
 * 登陆状态改变
 * 作者：guoweijie on 16/12/24 16:09
 * 邮箱：529844698@qq.com
 */
public class LoginStateChangeEvent {
    private boolean isLogin;

    public LoginStateChangeEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

}
