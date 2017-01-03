package com.weijie.vr4dream.presenter;

/**
 * 评论列表
 * 作者：guoweijie on 16/12/31 09:49
 * 邮箱：529844698@qq.com
 */
public interface ICommentListPresenter extends IBasePresenter {

    /**
     * 跳转到添加评论
     * @param id
     */
    void clickComment(String id);

    /**
     * 加载数据
     * @param refresh
     */
    void loadMore(boolean refresh, String id);

    /**
     * 偏移量+1
     */
    void addMore();

}
