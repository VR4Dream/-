package com.weijie.vr4dream.ui.view.launcher;

import com.weijie.vr4dream.ui.view.IBaseView;

/**
 * 启动页
 * 作者：guoweijie on 16/12/15 20:52
 * 邮箱：529844698@qq.com
 */
public interface ILauncherView extends IBaseView {

    /**
     * 设置启动页面的透明度
     * @param alpha 透明度
     * @param duration 动画时长
     */
    void setSplashViewAlpha(float alpha, long duration);
}

