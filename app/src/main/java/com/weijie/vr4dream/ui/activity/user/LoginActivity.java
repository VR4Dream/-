package com.weijie.vr4dream.ui.activity.user;


import android.view.View;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.user.LoginPresenter;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.user.ILoginView;
import com.weijie.vr4dream.ui.widget.IconEditText;
import com.weijie.vr4dream.ui.widget.IdentifyView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * 用户登录
 * 作者：guoweijie on 16/12/16 13:58
 * 邮箱：529844698@qq.com
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {

    @Bind(R.id.lv_login)
    View lvLogin;
    @Bind(R.id.username)
    IconEditText editUsername;
    @Bind(R.id.password)
    IconEditText editPassword;

    @Bind(R.id.lv_register)
    View lvRegister;
    @Bind(R.id.rs_identify)
    IdentifyView identifyView;
    @Bind(R.id.re_password)
    IconEditText editPsw;
    @Bind(R.id.re_password_again)
    IconEditText editPswAgain;

    @Bind(R.id.lv_forget)
    View lvForget;

    @Override
    protected void initPresenter() {
        mPresenter = new LoginPresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initialize() {
        setOnClickListener();
    }

    @Override
    public void clickLogin() {

    }

    @Override
    public void setUserName(String userName) {

    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public void setAccountTipsIconAlpha(int alpha) {

    }

    @Override
    public void setPasswordTipsIconAlpha(int alpha) {

    }

    @OnClick({R.id.tv_login, R.id.tv_forget, R.id.tv_register, R.id.tv_forget_login, R.id.bt_register, R.id.bt_login, R.id.login_qq})
    void clickMsg(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
            case R.id.tv_forget_login:
                lvLogin.setVisibility(View.VISIBLE);
                lvRegister.setVisibility(View.GONE);
                lvForget.setVisibility(View.GONE);
                break;
            case R.id.tv_register:
                lvRegister.setVisibility(View.VISIBLE);
                lvLogin.setVisibility(View.GONE);
                lvForget.setVisibility(View.GONE);
                break;
            case R.id.tv_forget:
                lvForget.setVisibility(View.VISIBLE);
                lvRegister.setVisibility(View.GONE);
                lvLogin.setVisibility(View.GONE);
                break;
            case R.id.bt_register:
                mPresenter.register(identifyView.getTel(), editPsw.getText().toString(), editPswAgain.getText().toString(), identifyView.getIdentify());
                break;
            case R.id.bt_login:
                mPresenter.login(editUsername.getText().toString(), editPassword.getText().toString());
                break;
            case R.id.login_qq:
                break;
            default:
                break;
        }
    }

    /**
     * 设置验证码点击监听
     */
    public void setOnClickListener() {
        identifyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.requestSMSCode(identifyView.getTel());
            }
        });
    }

    @Override
    public void getVerifyCodeSuccess() {
        identifyView.getVerifyCodeSuccess();
    }

}
