package com.weijie.vr4dream.ui.view.idea;

import com.weijie.vr4dream.model.Comment;
import com.weijie.vr4dream.ui.view.IBaseView;

/**
 * 文章详情
 * 作者：guoweijie on 16/12/22 10:40
 * 邮箱：529844698@qq.com
 */
public interface IIdeaDetailView extends IBaseView {

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

    /**
     * 设置评论总数
     * @param num
     */
    void setCommentNum(int num);

    /**
     * 刷新评论列表
     * @param comment
     */
    void createComment(Comment comment);

}
