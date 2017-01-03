package com.weijie.vr4dream.presenter.user;

import android.content.Context;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.ui.view.user.ISettingView;
import com.weijie.vr4dream.utils.AppUtil;
import com.weijie.vr4dream.utils.FileUtil;

import java.text.DecimalFormat;

/**
 * 作者：guoweijie on 16/12/25 20:18
 * 邮箱：529844698@qq.com
 */
public class SettingPresenter extends BaseActivityPresenter<ISettingView> implements ISettingPresenter {

    public SettingPresenter(Context context, ISettingView view) {
        super(context, view);
        initViewInfo();
    }

    private void initViewInfo() {
        setCacheSize();
        // 版本
        String versionName = "V" + AppUtil.getPackageInfo(mContext).versionName;
        mView.setAppVersion(versionName);
    }

    /**
     * 设置缓存大小
     */
    private void setCacheSize() {
        DecimalFormat df = new DecimalFormat("#0.00");
        // 缓存
        String cacheSize =
                df.format(
                        FileUtil.getSize(
                                App.getInstance().getPicCacheDir()
                        )
                )
                        + "MB";
        mView.setCacheSize(cacheSize);
    }

    @Override
    public void clickClearCache() {
        mView.showClearCacheDialog("确认要删除所有缓存吗？");
    }

    @Override
    public void clearCache() {
        mView.showProgressDialog();
        FileUtil.delAllFile(App.getInstance().getPicCacheDir());
        setCacheSize();
        mView.dismissProgressDialog();
    }

    @Override
    public void clickVersionUpdate() {

    }

    @Override
    public void appUpdate() {

    }
}
