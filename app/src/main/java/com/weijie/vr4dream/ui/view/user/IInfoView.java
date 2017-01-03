package com.weijie.vr4dream.ui.view.user;

import com.weijie.vr4dream.ui.view.IBaseView;
import com.weijie.vr4dream.ui.widget.IdentifyView;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户信息
 * 作者：guoweijie on 16/12/26 10:50
 * 邮箱：529844698@qq.com
 */
public interface IInfoView extends IBaseView {

    /**
     * 设置用户头像
     * @param icon 头像图片
     */
    void setIcon(BmobFile icon);

    /**
     * 修改昵称
     * @param name 昵称
     */
    void setName(String name);

    /**
     * 设置手机号码
     * @param tel 手机号码
     */
    void setTel(String tel);

    /**
     * 设置邮箱
     * @param email 邮箱
     */
    void setEmail(String email);

    /**
     * 设置性别
     * @param sex 性别 true男 false女
     */
    void setSex(String sex);

    /**
     * 设置喜好风格
     * @param style 风格
     */
    void setNiceStyle(String style);

    /**
     * 设置注册时间
     * @param date 注册时间
     */
    void setRegisterDate(String date);

    /**
     * 验证短信已发送成功
     */
    void getVerifyCodeSuccess();

    /**
     * 显示退出登陆对话框
     *
     * @param content 退出登陆的内容文字
     */
    void showLogoutDialog(String content);


}
