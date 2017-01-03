package com.weijie.vr4dream.presenter.idea;

import com.weijie.vr4dream.model.Idea;
import com.weijie.vr4dream.presenter.IBasePresenter;
import com.weijie.vr4dream.rxEvent.AddCommentEvent;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;

/**
 * 作者：guoweijie on 16/12/22 10:41
 * 邮箱：529844698@qq.com
 */
public interface IIdeaDeatilPresenter  extends IBasePresenter {

    /**
     * 设置文字对象
     * @param idea
     */
    void setIdea(Idea idea);

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
     * 查看全部评论
     */
    void clickCheckAllComment();

    /**
     * 获取评论总数和前两条评论
     */
    void getCommentList();

    /**
     * 添加评论事件
     */
    void addComment(AddCommentEvent event);

}
