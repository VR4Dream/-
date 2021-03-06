package com.weijie.vr4dream.presenter.user;

import com.weijie.vr4dream.presenter.IBasePresenter;

/**
 * 设置
 * 作者：guoweijie on 16/12/25 20:17
 * 邮箱：529844698@qq.com
 */
public interface ISettingPresenter extends IBasePresenter {

    /**
     * 点击清理缓存
     */
    void clickClearCache();

    /**
     * 清除缓存
     */
    void clearCache();

    /**
     * 点击版本更新
     */
    void clickVersionUpdate();

    /**
     * app更新
     */
    void appUpdate();

}
