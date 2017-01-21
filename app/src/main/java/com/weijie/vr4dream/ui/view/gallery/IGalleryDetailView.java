package com.weijie.vr4dream.ui.view.gallery;

import com.weijie.vr4dream.ui.view.IBaseView;

/**
 * VR详情
 * 作者：guoweijie on 17/1/3 11:04
 * 邮箱：529844698@qq.com
 */
public interface IGalleryDetailView extends IBaseView {

    /**
     * 显示菜单
     */
    void showMenuDialog();

    /**
     * 隐藏菜单
     */
    void hideMenuDialog();

    /**
     * 加载页面
     * @param link
     */
    void loadWeb(String link);

    /**
     * 改变likes图标状态
     * @param status
     */
    void setLikesStatus(boolean status);

    /**
     * 改变收藏状态
     * @param status
     */
    void setFavourite(boolean status);

    /**
     *
     * @param content dialog提示信息
     */
    void showLoginDialog(String content);

}
