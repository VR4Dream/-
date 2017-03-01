package com.weijie.vr4dream.presenter.user;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.model.AppInfo;
import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.ui.view.user.ISettingView;
import com.weijie.vr4dream.utils.AppUtil;
import com.weijie.vr4dream.utils.ErrorUtil;
import com.weijie.vr4dream.utils.FileUtil;

import java.text.DecimalFormat;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 作者：guoweijie on 16/12/25 20:18
 * 邮箱：529844698@qq.com
 */
public class SettingPresenter extends BaseActivityPresenter<ISettingView> implements ISettingPresenter {

    private AppInfo info;

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
        mView.showProgressDialog();
        BmobQuery<AppInfo> query = new BmobQuery<AppInfo>("AppInfo");
        query.findObjects(new FindListener<AppInfo>() {
            @Override
            public void done(List<AppInfo> apps, BmobException e) {
                mView.dismissProgressDialog();
                if(e==null && apps!=null && apps.size()>0) {
                    info = apps.get(0);
                    AppInfo app = getAppInfo(mContext);
                    if(info !=null && info.isValid() && app.getVersionCode()>0 && info.getVersionCode() > app.getVersionCode()){
                        mView.showAppUpdateDialog("检测到新版的app,版本号V"+info.getVersionCode(), true);
                    } else {
                        mView.showTips("当前已经是最新版本。");
                    }
                } else {
                    mView.showTips("当前已经是最新版本。");
                }
            }
        });
    }

    //获取应用信息
    private static final AppInfo getAppInfo(Context context){
        AppInfo info = new AppInfo();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            info.setVersionCode(packageInfo.versionCode);
            info.setVersionName(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    @Override
    public void appUpdate() {
        if(info!=null) {
            Intent downloadIntent = new Intent(Intent.ACTION_VIEW);
            downloadIntent.setData(Uri.parse(info.getUrl()));
            mContext.startActivity(downloadIntent);
        }
    }
}
