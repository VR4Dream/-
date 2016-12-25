package com.weijie.vr4dream.rxEvent;

import cn.bmob.v3.BmobUser;

/**
 * 登陆状态改变
 * 作者：guoweijie on 16/12/24 16:09
 * 邮箱：529844698@qq.com
 */
public class LoginStateChangeEvent {
    private boolean isLogin;
    private BmobUser bmobUser;

    public LoginStateChangeEvent(boolean isLogin, BmobUser bmobUser) {
        this.isLogin = isLogin;
        this.bmobUser = bmobUser;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public BmobUser getBmobUser() {
        return bmobUser;
    }
}
