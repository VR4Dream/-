package com.weijie.vr4dream.presenter.gallery;

import com.weijie.vr4dream.model.Gallery;
import com.weijie.vr4dream.presenter.IBasePresenter;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;

/**
 * VR详情
 * 作者：guoweijie on 17/1/3 11:05
 * 邮箱：529844698@qq.com
 */
public interface IGalleryDetailPresenter extends IBasePresenter {

    /**
     * @param gallery
     */
    void setGallery(Gallery gallery);

    /**
     * 检查初始状态
     */
    void checkedInitStatus();

    /**
     * 点击喜欢
     */
    void clickLikes();

    /**
     * 点击收藏
     */
    void clickFavourite();

    /**
     * 登录
     */
    void clickLogin();

    /**
     * 登录状态改变
     */
    void loginStateChange(LoginStateChangeEvent event);

    /**
     * 点击评论
     */
    void clickComment();

    /**
     * 分享给QQ好友
     */
    void shareQQ();

    /**
     * 分享到QQ空间
     */
    void shareSpace();

    /**
     * 分享到新浪微博
     */
    void shareBlog();

    /**
     * 分享到微信好友
     */
    void shareWeChat();

    /**
     * 分享到朋友圈
     */
    void shareWechatMoments();

}
