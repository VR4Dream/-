package com.weijie.vr4dream.ui.view.gallery;

import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.model.Gallery;
import com.weijie.vr4dream.ui.view.IBaseView;

import java.util.List;

/**
 * 图库
 * 作者：guoweijie on 16/12/19 10:23
 * 邮箱：529844698@qq.com
 */
public interface IGalleryActivityView extends IBaseView {

    /**
     * 下拉刷新
     * @param gallerys
     */
    void refreshView(List<Gallery> gallerys);

    /**
     * 加载更多
     * @param gallerys
     */
    void loadMoreView(List<Gallery> gallerys);

    /**
     * 隐藏提示语
     */
    void hideTip();

    /**
     * 显示提示语
     */
    void showTip(LoadTipAdapter.ViewStatus status);

    void refreshComplete();

    /**
     * 设置加载状态 true正在加载
     * @param mIsLoadingMore
     */
    void setLoadStatus(boolean mIsLoadingMore);

}
