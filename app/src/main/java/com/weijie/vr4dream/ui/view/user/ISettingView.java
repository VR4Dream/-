package com.weijie.vr4dream.ui.view.user;

import com.weijie.vr4dream.ui.view.IBaseView;

/**
 * 设置
 * 作者：guoweijie on 16/12/25 20:19
 * 邮箱：529844698@qq.com
 */
public interface ISettingView extends IBaseView {

    /**
     * 设置缓存大小文字
     *
     * @param cacheSize 缓存大小文字
     */
    void setCacheSize(String cacheSize);

    /**
     * 显示清除缓存警告对话框
     *
     * @param content 提示框的内容文字
     */
    void showClearCacheDialog(String content);

    /**
     * 设置app版本号
     *
     * @param version app版本号
     */
    void setAppVersion(String version);

    /**
     * 显示版本更新对话框
     *
     * @param content 版本更新对话框的内容文字
     * @param negativeButtonVisible 取消按钮是否可见
     */
    void showAppUpdateDialog(String content, boolean negativeButtonVisible);


}
