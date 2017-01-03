package com.weijie.vr4dream.ui.activity.user;


import android.view.View;
import android.widget.TextView;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.user.SettingPresenter;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.user.ISettingView;
import com.weijie.vr4dream.ui.widget.AlertDialogFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 设置
 * 作者：guoweijie on 16/12/25 20:13
 * 邮箱：529844698@qq.com
 */
public class SettingActivity extends BaseActivity<SettingPresenter> implements ISettingView {

    @Bind(R.id.tv_cache)
    TextView tvCache;
    @Bind(R.id.tv_version)
    TextView tvVersion;

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

    @OnClick({R.id.btn_back, R.id.lv_clear, R.id.lv_version, R.id.lv_about})
    void onTextMenuClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.lv_clear:
                //清空缓存
                mPresenter.clickClearCache();
                break;
            case R.id.lv_version:
                //检查版本号
                mPresenter.clickVersionUpdate();
                break;
            case R.id.lv_about:
                //关于我们
                break;
        }
    }

    @Override
    public void setCacheSize(String cacheSize) {
        tvCache.setText(cacheSize);
    }

    @Override
    public void setAppVersion(String version) {
        tvVersion.setText(version);
    }

    @Override
    public void showClearCacheDialog(String content) {
        AlertDialogFragment
                .getInstance(content, getString(R.string.text_cancel), getString(R.string.text_ensure))
                .setOnDialogClickListener(new AlertDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onPositiveButtonClickListener() {
                    }

                    @Override
                    public void onNegativeButtonClickListener() {
                        mPresenter.clearCache();
                    }
                })
                .show(getSupportFragmentManager(), "clearCache");
    }

    @Override
    public void showAppUpdateDialog(String content, boolean negativeButtonVisible) {
        AlertDialogFragment
                .getInstance(content, negativeButtonVisible)
                .setOnDialogClickListener(new AlertDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onPositiveButtonClickListener() {
                    }

                    @Override
                    public void onNegativeButtonClickListener() {
                        mPresenter.appUpdate();
                    }
                })
                .show(getSupportFragmentManager(), "appUpdate");
    }
}
