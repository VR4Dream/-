package com.weijie.vr4dream.presenter.user;

import android.content.Context;
import android.util.Log;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.model.VRUser;
import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.ui.view.user.ILoginView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;
import com.weijie.vr4dream.utils.ErrorUtil;
import com.weijie.vr4dream.utils.StringUtil;


import org.json.JSONObject;

import java.util.HashMap;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 用户登陆
 * 作者：guoweijie on 16/12/16 14:08
 * 邮箱：529844698@qq.com
 */
public class LoginPresenter extends BaseActivityPresenter<ILoginView> implements ILoginPresenter {

    public LoginPresenter(Context context, ILoginView view) {
        super(context, view);
    }

    @Override
    public void login(String tel, String password) {
        mView.showProgressDialog();
        BmobUser.loginByAccount(tel, password, new LogInListener<VRUser>() {
            @Override
            public void done(VRUser user, BmobException e) {
                if (user != null) {
                    loginState();
                    mView.finish();
                } else {
                    mView.showTips(ErrorUtil.getErrorMsg(e));
                }
                mView.dismissProgressDialog();
            }
        });
    }

    @Override
    public void register(String tel, String psw, String psw2, String code) {
        if(!StringUtil.validateMobile(tel)) {
            mView.showTips(mContext.getString(R.string.input_the_true_number));
        } else if(!psw.trim().equals("") && !psw.equals(psw2)) {
            mView.showTips(mContext.getString(R.string.psw_must_same));
        } else if(code.length()!=6) {
            mView.showTips(mContext.getString(R.string.input_true_identifying));
        } else {
            VRUser user = new VRUser();
            user.setMobilePhoneNumber(tel);
            user.setPassword(psw);
            user.signOrLogin(code, new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if(bmobUser != null) {
                        loginState();
                        mView.showTips(mContext.getString(R.string.register_success));
                        mView.finish();
                    } else {
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                }
            });
        }
    }

    @Override
    public void requestSMSCode(String tel) {
        BmobSMS.requestSMSCode(tel, "VerifyCode", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {//验证码发送成功
                    mView.getVerifyCodeSuccess();
                    mView.showTips(mContext.getString(R.string.send_identify_success));
                } else {
                    mView.showTips(ErrorUtil.getErrorMsg(e));
                }
            }
        });
    }

    @Override
    public void loginState() {
        App.getInstance()
                .getRxBus()
                .sendNormalEvent(new LoginStateChangeEvent(true));
    }

    @Override
    public void logoutState() {
        BmobUser.logOut();
    }

    @Override
    public void loginByQQ() {
        Platform plat = ShareSDK.getPlatform(QQ.NAME);
        if (plat == null) {
            return;
        }

        if (plat.isAuthValid()) {
            plat.removeAccount(true);
            return;
        }

        //使用SSO授权，通过客户单授权
        plat.SSOSetting(false);
        plat.setPlatformActionListener(listener);
        plat.showUser(null);
    }

    @Override
    public void loginByWeiXin() {
        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        if (plat == null) {
            return;
        }

        if (plat.isAuthValid()) {
            plat.removeAccount(true);
            return;
        }

        plat.SSOSetting(false);
        plat.setPlatformActionListener(listener);
        plat.showUser(null);
    }

    @Override
    public void loginBySina() {
        Platform plat = ShareSDK.getPlatform(SinaWeibo.NAME);
        if (plat == null) {
            return;
        }

        if (plat.isAuthValid()) {
            plat.removeAccount(true);
            return;
        }

        plat.SSOSetting(false);
        plat.setPlatformActionListener(listener);
        plat.showUser(null);
    }

    private PlatformActionListener listener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            PlatformDb db = platform.getDb();
            BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(platform.getName().toLowerCase(),db.getToken(), db.getExpiresIn()+"", db.getUserId());
            loginWithAuth(authInfo);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            mView.showTips("授权失败");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            mView.showTips("取消授权");
        }
    };

    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo){
        //mView.showProgressDialog();
        BmobUser.loginWithAuthData(authInfo, new LogInListener<JSONObject>() {
            @Override
            public void done(JSONObject jsonObject, BmobException e) {
                //mView.dismissProgressDialog();
                if(e==null) {
                    BmobUser user = VRUser.getCurrentUser();
                    if(user.getMobilePhoneNumber() == null || user.getMobilePhoneNumber().equals("")) {
//                        Log.e("json", jsonObject.toString());
//                        JSONObject json;
//                        Bundle bundle = new Bundle();
//                        if((json = jsonObject.optJSONObject(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ))!=null) {
//                            bundle.putString("snsType", BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ);
//                        } else if((json = jsonObject.optJSONObject(BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIXIN))!=null) {
//                            bundle.putString("snsType", BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIXIN);
//                        } else if((json = jsonObject.optJSONObject(BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIBO))!=null) {
//                            bundle.putString("snsType", BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIBO);
//                        }
//                        bundle.putString("accessToken", json.optString("access_token"));
//                        bundle.putString("expiresIn", json.optLong("expires_in")+"");
//                        bundle.putString("userId", json.optString("openid"));
//                        ActivitySkipHelper.toRegisterActivity(mContext, bundle);
                        ActivitySkipHelper.toRegisterActivity(mContext);
                    } else {
                        loginState();
                        mView.finish();
                    }
                } else {
                    Log.e("error", e.getMessage());
                }
            }

        });
    }

}
