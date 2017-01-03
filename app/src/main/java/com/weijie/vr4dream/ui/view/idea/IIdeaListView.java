package com.weijie.vr4dream.ui.view.idea;

import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.model.Idea;
import com.weijie.vr4dream.ui.view.IBaseView;

import java.util.List;

/**
 * 灵感列表
 * 作者：guoweijie on 16/12/20 15:00
 * 邮箱：529844698@qq.com
 */
public interface IIdeaListView extends IBaseView {

    /**
     * 下拉刷新
     * @param ideas
     */
    void refreshView(List<Idea> ideas);

    /**
     * 加载更多
     * @param ideas
     */
    void loadMoreView(List<Idea> ideas);

    /**
     * 隐藏提示语
     */
    void hideTip();

    /**
     * 显示提示语
     */
    void showTip(LoadTipAdapter.ViewStatus status);

    /**
     * 设置加载状态 true正在加载
     * @param mIsLoadingMore
     */
    void setLoadStatus(boolean mIsLoadingMore);

    void refreshComplete();

}
