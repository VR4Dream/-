package com.weijie.vr4dream.ui.view;

import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.model.BuildingEstate;

import java.util.List;

/**
 * 楼盘列表
 * 作者：guoweijie on 17/1/4 09:56
 * 邮箱：529844698@qq.com
 */
public interface IBuildingEstateView extends IBaseView {

    /**
     * 设置加载状态 true正在加载
     * @param mIsLoadingMore
     */
    void setLoadStatus(boolean mIsLoadingMore);

    /**
     * 刷新
     */
    void refreshView(List<BuildingEstate> estates);

    /**
     * 加载
     */
    void loadMoreView(List<BuildingEstate> estates);

    void refreshComplete();

    /**
     * 隐藏提示语
     */
    void hideTip();

    /**
     * 显示提示语
     */
    void showTip(LoadTipAdapter.ViewStatus status);

    /**
     * 设置历史记录
     * @param params
     */
    void setHistory(List<String> params);

    /**
     * 隐藏搜索
     */
    void hideSearchLayout();

    /**
     * 自动刷新
     */
    void autoRefresh();

    /**
     * 隐藏软键盘
     */
    void hideSoftInputFromWindow();

}
