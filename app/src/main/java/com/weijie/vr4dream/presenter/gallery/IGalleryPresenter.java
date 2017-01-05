package com.weijie.vr4dream.presenter.gallery;

import android.content.Context;

import com.weijie.vr4dream.presenter.IBasePresenter;

/**
 * 图库
 * 作者：guoweijie on 16/12/19 10:20
 * 邮箱：529844698@qq.com
 */
public interface IGalleryPresenter extends IBasePresenter {

    /**
     * 添加条件
     * @param key
     * @param value
     */
    void setParam(String key, Object value);

    /**
     * 剔除某个条件
     * @param key
     */
    void removeParam(String key);

    /**
     * 分页加载
     * @param refresh 是否刷新
     */
    void loadMore(boolean refresh);

    /**
     * 跳转到楼盘列表
     */
    void clickBuildingEstate();

}
