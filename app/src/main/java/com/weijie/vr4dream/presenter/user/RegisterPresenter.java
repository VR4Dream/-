package com.weijie.vr4dream.presenter.user;

import android.content.Context;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.model.VRUser;
import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.rxEvent.BindThirdEvent;
import com.weijie.vr4dream.ui.view.user.IRegisterView;
import com.weijie.vr4dream.utils.ErrorUtil;
import com.weijie.vr4dream.utils.StringUtil;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 第三方注册绑定
 * 作者：guoweijie on 17/1/20 20:11
 * 邮箱：529844698@qq.com
 */
public class RegisterPresenter extends BaseActivityPresenter<IRegisterView> implements IRegisterPresenter {

    public RegisterPresenter(Context context, IRegisterView view) {
        super(context, view);
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
    public void register(final String tel, String psw, String psw2, final String code) {
        if(!StringUtil.validateMobile(tel)) {
            mView.showTips(mContext.getString(R.string.input_the_true_number));
        } else if(!psw.trim().equals("") && !psw.equals(psw2)) {
            mView.showTips(mContext.getString(R.string.psw_must_same));
        } else if(code.length()!=6) {
            mView.showTips(mContext.getString(R.string.input_true_identifying));
        } else {
            mView.showProgressDialog();
            VRUser user = BmobUser.getCurrentUser(VRUser.class);
            user.setMobilePhoneNumber(tel);
            user.setPassword(psw);
            user.update(user.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        BmobSMS.verifySmsCode(tel, code, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                mView.dismissProgressDialog();
                                if (e == null) {
                                    mView.showTips("绑定成功！");
                                    mView.finish();
                                } else {
                                    mView.showTips(ErrorUtil.getErrorMsg(e));
                                }
                            }
                        });
                    } else {
                        mView.dismissProgressDialog();
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                }
            });

        }
    }

    /**和第三方账号进行关联
     * @method associateThirdParty
     * @param  snsType
     * @param  accessToken
     * @param  expiresIn
     * @param  userId
     * @return void
     * @exception
     */
    private void associateThirdParty(String snsType,String accessToken, String expiresIn,String userId){
        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(snsType,accessToken, expiresIn, userId);
        BmobUser.associateWithAuthData(authInfo, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null) {
                    mView.showTips("绑定成功！");
                    mView.finish();
                }
            }
        });
    }

}
