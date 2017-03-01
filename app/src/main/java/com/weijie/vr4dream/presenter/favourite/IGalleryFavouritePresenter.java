package com.weijie.vr4dream.presenter.favourite;

import com.weijie.vr4dream.presenter.IBasePresenter;

/**
 * 作者：guoweijie on 17/2/8 19:59
 * 邮箱：529844698@qq.com
 */
public interface IGalleryFavouritePresenter extends IBasePresenter {

    /**
     * 分页加载
     * @param refresh 是否刷新
     */
    void loadMore(boolean refresh);

}