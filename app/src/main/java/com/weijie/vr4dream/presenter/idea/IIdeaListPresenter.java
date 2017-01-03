package com.weijie.vr4dream.presenter.idea;

import com.weijie.vr4dream.presenter.IBasePresenter;

/**
 * 灵感列表
 * 作者：guoweijie on 16/12/21 08:53
 * 邮箱：529844698@qq.com
 */
public interface IIdeaListPresenter extends IBasePresenter {

    /**
     * 分页加载
     * @param refresh 是否刷新
     * @param type 类型
     */
    void loadMore(boolean refresh, int type);

}
