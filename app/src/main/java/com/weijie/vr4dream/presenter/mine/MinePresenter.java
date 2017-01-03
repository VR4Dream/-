package com.weijie.vr4dream.presenter.mine;

import android.content.Context;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.model.VRUser;
import com.weijie.vr4dream.presenter.BaseFragmentPresenter;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.ui.view.mine.IMineView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;
import com.weijie.vr4dream.utils.ErrorUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

/**
 * 我的页面
 * 作者：guoweijie on 16/12/16 10:52
 * 邮箱：529844698@qq.com
 */
public class MinePresenter extends BaseFragmentPresenter<IMineView> implements IMinePresenter {

    private VRUser user;

    public MinePresenter(Context context, IMineView view) {
        super(context, view);
    }

    @Override
    public void initInfo() {
        user = BmobUser.getCurrentUser(VRUser.class);
        if(user!=null) {
            mView.setUserName(user.getUsername()==null ? user.getMobilePhoneNumber() : user.getUsername());
            initFavouriteNum();
        }
    }

    @Override
    public void clickHeadIcon() {
        if(BmobUser.getCurrentUser(VRUser.class)==null) {
            ActivitySkipHelper.toLoginActivity(mContext);
        }
    }

    @Override
    public void clickPersonalData() {
        if(BmobUser.getCurrentUser(VRUser.class)==null) {
            mView.showLoginDialog("亲，您还没有登录！");
        } else {
            ActivitySkipHelper.toInfoActivity(mContext);
        }
    }

    @Override
    public void clickGalleryFavourite() {
        if(BmobUser.getCurrentUser(VRUser.class)==null) {
            mView.showLoginDialog("亲，您还没有登录！");
        } else {
        }
    }

    @Override
    public void clickIdeaFavourite() {
        if(BmobUser.getCurrentUser(VRUser.class)==null) {
            mView.showLoginDialog("亲，您还没有登录！");
        } else {
        }
    }

    @Override
    public void clickSetting() {
        ActivitySkipHelper.toSettingActivity(mContext);
    }

    @Override
    public void clickSuggest() {
        ActivitySkipHelper.toSuggestActivity(mContext);
    }

    @Override
    public void clickShare() {

    }

    @Override
    public void loginStateChange(LoginStateChangeEvent event) {
        if(event.isLogin()) {
            user = BmobUser.getCurrentUser(VRUser.class);
            mView.setUserName(user.getUsername()==null ? user.getMobilePhoneNumber() : user.getUsername());
            initFavouriteNum();
        } else {
            mView.setUserName(mContext.getString(R.string.text_user_name));
            mView.setIdeaNum(0);
            mView.setGalleryNum(0);
        }
    }

    private void initFavouriteNum() {
        try {
            String cloudCodeName = "getFavouriteNum";
            JSONObject params = new JSONObject();
            params.put("owner",user.getObjectId());
            AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
            cloudCode.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
                @Override
                public void done(Object o, BmobException e) {
                    if(e==null) {
                        try {
                            JSONObject json = new JSONObject(o.toString());
                            mView.setIdeaNum(json.optInt("idea"));
                            mView.setGalleryNum(json.optInt("gallery"));
                        } catch (JSONException jse) {
                            jse.printStackTrace();
                        }
                    } else {
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                }
            });
        } catch (Exception e) {
        }
    }
}
