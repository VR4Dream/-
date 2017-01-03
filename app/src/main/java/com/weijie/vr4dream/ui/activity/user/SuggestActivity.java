package com.weijie.vr4dream.ui.activity.user;

import android.view.View;
import android.widget.EditText;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.user.SuggestPresenter;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.user.ISuggestView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 意见反馈
 * 作者：guoweijie on 16/12/27 11:05
 * 邮箱：529844698@qq.com
 */
public class SuggestActivity extends BaseActivity<SuggestPresenter> implements ISuggestView {

    @Bind(R.id.ev_suggest)
    EditText evSug;

    @Override
    protected void initPresenter() {
        mPresenter = new SuggestPresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_suggest;
    }

    @Override
    protected void initialize() {

    }

    @OnClick({R.id.btn_back, R.id.btn_commit})
    void onTextMenuClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_commit:
                mPresenter.clickCommit(evSug.getText().toString());
                break;
        }
    }

}
