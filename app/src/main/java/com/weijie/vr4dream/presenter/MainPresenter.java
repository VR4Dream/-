package com.weijie.vr4dream.presenter;

import android.content.Context;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.model.VRUser;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.ui.view.IMainView;

import cn.bmob.v3.BmobUser;

/**
 * 首页presenter
 * 作者：guoweijie on 16/12/16 10:34
 * 邮箱：529844698@qq.com
 */
public class MainPresenter extends BaseActivityPresenter<IMainView> implements IMainPresenter {

    public MainPresenter(Context context, IMainView view) {
        super(context, view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BmobUser.logOut();
    }
}