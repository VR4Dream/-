package com.weijie.vr4dream.ui.activity.user;

import android.view.View;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.model.VRUser;
import com.weijie.vr4dream.presenter.user.RegisterPresenter;
import com.weijie.vr4dream.rxEvent.BindThirdEvent;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.user.IRegisterView;
import com.weijie.vr4dream.ui.widget.IconEditText;
import com.weijie.vr4dream.ui.widget.IdentifyView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * 第三方登录（注册）绑定
 * 作者：guoweijie on 17/1/20 20:31
 * 邮箱：529844698@qq.com
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements IRegisterView {

    @Bind(R.id.rs_identify)
    IdentifyView identifyView;
    @Bind(R.id.re_password)
    IconEditText editPsw;
    @Bind(R.id.re_password_again)
    IconEditText editPswAgain;

    @Override
    protected void initPresenter() {
        mPresenter = new RegisterPresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initialize() {
        setOnClickListener();
    }

    @OnClick({R.id.bt_bind, R.id.btn_left})
    void clickMsg(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                BmobUser.logOut();
                finish();
                break;
            case R.id.bt_bind:
                mPresenter.register(identifyView.getTel(), editPsw.getText().toString(), editPswAgain.getText().toString(), identifyView.getIdentify());
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

    @Override
    public void finish() {
        VRUser user = BmobUser.getCurrentUser(VRUser.class);
        if(user!=null && user.getMobilePhoneNumber()!=null && !user.getMobilePhoneNumber().equals("")) {
            App.getInstance().getRxBus().sendNormalEvent(new BindThirdEvent(true));
        } else {
            App.getInstance().getRxBus().sendNormalEvent(new BindThirdEvent(false));
        }
        super.finish();
    }
}
