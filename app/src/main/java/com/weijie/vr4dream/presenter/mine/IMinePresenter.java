package com.weijie.vr4dream.presenter.mine;

import com.weijie.vr4dream.presenter.IBasePresenter;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;

/**
 * 我的页面
 * 作者：guoweijie on 16/12/16 10:48
 * 邮箱：529844698@qq.com
 */
public interface IMinePresenter extends IBasePresenter {

    /**
     * 初始化界面信息
     */
    void initViewInfo();

    /**
     * 点击头像
     */
    void clickHeadIcon();

    /**
     * 点击个人信息
     */
    void clickPersonalData();

    /**
     * 点击场景收藏
     */
    void clickGalleryFavourite();

    /**
     * 点击灵感收藏
     */
    void clickIdeaFavourite();

    /**
     * 点击设置
     */
    void clickSetting();

    /**
     * 点击意见反馈
     */
    void clickSuggest();

    /**
     * 点击推荐给好友
     */
    void clickShare();

    /**
     * 账号输入框聚焦
     */
    void onAccountInputFocus();

    /**
     * 密码输入框聚焦
     */
    void onPasswordInputFocus();

    /**
     * 登录状态改变
     */
    void loginStateChange(LoginStateChangeEvent event);
}
