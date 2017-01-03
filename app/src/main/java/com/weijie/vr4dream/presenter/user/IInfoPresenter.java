package com.weijie.vr4dream.presenter.user;

import android.view.View;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.IBasePresenter;
import com.weijie.vr4dream.utils.ErrorUtil;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


/**
 * 用户信息
 * 作者：guoweijie on 16/12/26 10:49
 * 邮箱：529844698@qq.com
 */
public interface IInfoPresenter extends IBasePresenter {

    /**
     * 初始化用户信息
     */
    void initInfo();

    /**
     * 修改昵称
     * @param name 昵称
     */
    void updateName(String name);

    /**
     * 修改密码
     * @param oldPsw 旧密码
     * @param newPsw 新密码
     */
    void updatePsw(String oldPsw, String newPsw);

    /**
     * 绑定邮箱
     * @param email 邮箱
     */
    void updateEmail(String email);

    /**
     * 填写性别
     * @param sex 性别 true男 false女
     */
    void updateSex(Boolean sex);

    /**
     * 风格喜好（推送用的）
     * @param cintent
     */
    void updateStyle(View cintent);

    /**
     * 获取验证码
     * @param tel 手机号码
     */
    void requestSMSCode(String tel);

    /**
     * 绑定手机
     * @param tel 手机号码
     * @param code 验证码
     */
    void updateTel(String tel, String code);

    /**
     * 发送用户退出状态
     */
    void logoutState();

}
