package com.weijie.vr4dream.presenter.mine;

import android.content.Context;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.BaseFragmentPresenter;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.ui.view.mine.IMineView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;

import cn.bmob.v3.BmobUser;

/**
 * 我的页面
 * 作者：guoweijie on 16/12/16 10:52
 * 邮箱：529844698@qq.com
 */
public class MinePresenter extends BaseFragmentPresenter<IMineView> implements IMinePresenter {

    public MinePresenter(Context context, IMineView view) {
        super(context, view);
    }

    @Override
    public void initViewInfo() {

    }

    @Override
    public void clickPersonalData() {
        ActivitySkipHelper.toLoginActivity(mContext);
    }

    @Override
    public void clickGalleryFavourite() {

    }

    @Override
    public void clickIdeaFavourite() {

    }

    @Override
    public void clickSetting() {
        ActivitySkipHelper.toSettingActivity(mContext);
    }

    @Override
    public void clickSuggest() {

    }

    @Override
    public void clickShare() {

    }

    @Override
    public void onAccountInputFocus() {

    }

    @Override
    public void onPasswordInputFocus() {

    }

    @Override
    public void loginStateChange(LoginStateChangeEvent event) {
        if(event.isLogin()) {
            BmobUser user = event.getBmobUser();
            mView.setUserName(user.getUsername()==null ? user.getMobilePhoneNumber() : user.getUsername());
        } else {
            mView.setUserName(mContext.getString(R.string.text_user_name));
        }
    }
}
