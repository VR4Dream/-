package com.weijie.vr4dream.presenter;

import com.weijie.vr4dream.model.BuildingEstate;

/**
 * 楼盘列表presenter
 * 作者：guoweijie on 17/1/4 09:57
 * 邮箱：529844698@qq.com
 */
public interface IBuildingEstatePresenter extends IBasePresenter {

    /**
     * 加载数据
     * @param refresh
     */
    void loadMore(boolean refresh);

    /**
     * 设置历史记录
     */
    void setHistory(String param);

    /**
     * 清空历史记录
     */
    void removeHistory();

    /**
     *
     * @param param
     */
    void setParam(String key, String param);

    /**
     *
     */
    void removeParam(String key);

    /**
     * 点击item
     * @param estate
     */
    void onItemClick(BuildingEstate estate);
}
