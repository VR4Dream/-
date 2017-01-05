package com.weijie.vr4dream.presenter.gallery;

import com.weijie.vr4dream.presenter.IBasePresenter;

/**
 * 图库
 * 作者：guoweijie on 17/1/5 17:17
 * 邮箱：529844698@qq.com
 */
public interface IGalleryActivityPresenter extends IBasePresenter {

    /**
     * 分页加载
     * @param refresh 是否刷新
     */
    void loadMore(boolean refresh, String id);

}
