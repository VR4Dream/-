package com.weijie.vr4dream.ui.view;

import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.model.IdeaComment;

import java.util.List;

/**
 * 评论列表
 * 作者：guoweijie on 16/12/31 09:49
 * 邮箱：529844698@qq.com
 */
public interface ICommentListView extends IBaseView {

    /**
     * 刷新
     */
    void refreshView(List<IdeaComment> comments);

    /**
     * 加载
     */
    void loadMoreView(List<IdeaComment> comments);

    /**
     * 设置加载状态 true正在加载
     * @param mIsLoadingMore
     */
    void setLoadStatus(boolean mIsLoadingMore);

    void refreshComplete();

    /**
     * 隐藏提示语
     */
    void hideTip();

    /**
     * 显示提示语
     */
    void showTip(LoadTipAdapter.ViewStatus status);

}
