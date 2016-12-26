package com.weijie.vr4dream.presenter.user;

import android.content.Context;

import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.ui.view.user.IInfoView;

/**
 * 用户信息
 * 作者：guoweijie on 16/12/26 10:51
 * 邮箱：529844698@qq.com
 */
public class InfoPresenter extends BaseActivityPresenter<IInfoView> implements IInfoPresenter {

    public InfoPresenter(Context context, IInfoView view) {
        super(context, view);
    }
}
