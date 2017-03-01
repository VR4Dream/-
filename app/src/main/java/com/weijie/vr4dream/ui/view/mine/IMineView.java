package com.weijie.vr4dream.ui.view.mine;

import com.weijie.vr4dream.ui.view.IBaseView;

/**
 * 我的页面
 * 作者：guoweijie on 16/12/16 10:53
 * 邮箱：529844698@qq.com
 */
public interface IMineView extends IBaseView {

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    void setUserName(String userName);

    /**
     *
     * @param content dialog提示信息
     */
    void showLoginDialog(String content);

    /**
     * 设置灵感收藏数
     * @param num
     */
    void setIdeaNum(int num);

    /**
     * 设置VR收藏数
     * @param num
     */
    void setGalleryNum(int num);

    /**
     * 分享app
     */
    void showShareDialog();

}