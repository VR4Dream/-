package com.weijie.vr4dream.presenter.launcher;

import android.content.Context;
import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.ui.view.launcher.ILauncherView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;
import com.weijie.vr4dream.utils.WeakHandler;

/**
 * 启动页
 * 作者：guoweijie on 16/12/15 20:50
 * 邮箱：529844698@qq.com
 */
public class LauncherPresenter extends BaseActivityPresenter<ILauncherView> implements ILauncherPresenter {
    final long DURATION = 2 * 1000; // 动画启动时长
    private WeakHandler mWeakHandler;

    public LauncherPresenter(Context context, ILauncherView view) {
        super(context, view);
        mView.setSplashViewAlpha(1, DURATION);
        mWeakHandler = new WeakHandler();
        mWeakHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivitySkipHelper.toMainActivity(mContext);
                mView.finish();
            }
        }, DURATION*2);
    }

    @Override
    public void onDestroy() {
        if (mWeakHandler != null)
            mWeakHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
