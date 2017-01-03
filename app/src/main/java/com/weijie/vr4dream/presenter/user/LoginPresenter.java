package com.weijie.vr4dream.presenter.user;

import android.content.Context;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.model.VRUser;
import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.ui.view.user.ILoginView;
import com.weijie.vr4dream.utils.ErrorUtil;
import com.weijie.vr4dream.utils.StringUtil;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

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
                if(user!=null) {
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

    }
}
