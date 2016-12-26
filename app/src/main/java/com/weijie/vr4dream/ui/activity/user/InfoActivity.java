package com.weijie.vr4dream.ui.activity.user;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.user.InfoPresenter;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.user.IInfoView;

/**
 * 用户信息
 * 作者：guoweijie on 16/12/26 10:45
 * 邮箱：529844698@qq.com
 */
public class InfoActivity extends BaseActivity<InfoPresenter> implements IInfoView {

    @Override
    protected void initPresenter() {
        mPresenter = new InfoPresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_info;
    }

    @Override
    protected void initialize() {

    }
}
