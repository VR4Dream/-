package com.weijie.vr4dream.ui.activity.launcher;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.launcher.LauncherPresenter;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.launcher.ILauncherView;

import butterknife.Bind;


/**
 * 启动页面
 * 作者：guoweijie on 16/12/15 17:11
 * 邮箱：529844698@qq.com
 */
public class LauncherActivity extends BaseActivity<LauncherPresenter> implements ILauncherView {

    @Bind(R.id.ivSplash)
    ImageView ivSplash; // 启动页图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置全屏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new LauncherPresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initialize() {

    }

    @Override
    public void setSplashViewAlpha(float alpha, long duration) {
        ivSplash.animate()
                .alpha(alpha)
                .setDuration(duration)
                .start();
    }

}
