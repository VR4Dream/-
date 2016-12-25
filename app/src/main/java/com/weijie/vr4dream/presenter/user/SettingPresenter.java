package com.weijie.vr4dream.presenter.user;

import android.content.Context;

import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.ui.view.user.ISettingView;

/**
 * 作者：guoweijie on 16/12/25 20:18
 * 邮箱：529844698@qq.com
 */
public class SettingPresenter extends BaseActivityPresenter<ISettingView> implements ISettingPresenter {

    public SettingPresenter(Context context, ISettingView view) {
        super(context, view);
    }
}
