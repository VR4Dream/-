package com.weijie.vr4dream.presenter;

/**
 * 评论
 * 作者：guoweijie on 16/12/30 11:58
 * 邮箱：529844698@qq.com
 */
public interface ICommentPresenter extends IBasePresenter {

    /**
     *
     * @param id
     * @param content
     */
    void submitComment(String id, String content);

    /**
     *
     */
    void clickLogin();

}
