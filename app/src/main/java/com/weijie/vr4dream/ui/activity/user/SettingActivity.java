package com.weijie.vr4dream.ui.activity.user;


import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.user.SettingPresenter;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.user.ISettingView;

/**
 * 设置
 * 作者：guoweijie on 16/12/25 20:13
 * 邮箱：529844698@qq.com
 */
public class SettingActivity extends BaseActivity<SettingPresenter> implements ISettingView {

    @Override
    protected void initPresenter() {
        mPresenter = new SettingPresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initialize() {

    }
}
