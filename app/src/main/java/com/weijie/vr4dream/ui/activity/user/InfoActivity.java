package com.weijie.vr4dream.ui.activity.user;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.user.InfoPresenter;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.user.IInfoView;
import com.weijie.vr4dream.ui.widget.AlertDialogFragment;
import com.weijie.vr4dream.ui.widget.EditDialogFragment;
import com.weijie.vr4dream.ui.widget.IdentifyView;
import com.weijie.vr4dream.ui.widget.UserInfoView;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户信息
 * 作者：guoweijie on 16/12/26 10:45
 * 邮箱：529844698@qq.com
 */
public class InfoActivity extends BaseActivity<InfoPresenter> implements IInfoView {

    @Bind(R.id.ui_name)
    UserInfoView uiName;
    @Bind(R.id.ui_tel)
    UserInfoView uiTel;
    @Bind(R.id.ui_psw)
    UserInfoView uiPsw;
    @Bind(R.id.ui_email)
    UserInfoView uiEmail;
    @Bind(R.id.ui_sex)
    UserInfoView uiSex;
    @Bind(R.id.ui_style)
    UserInfoView uiStyle;
    @Bind(R.id.ui_register_date)
    UserInfoView uiRegisterDate;

    private IdentifyView identifyView;

    @Override
    protected void initPresenter() {
        mPresenter = new InfoPresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_info;
    }

    @Override
    protected void initialize() {
        mPresenter.initInfo();
    }

    @Override
    public void setIcon(BmobFile icon) {

    }

    @Override
    public void setName(String name) {
        uiName.setContent(name);
    }

    @Override
    public void setTel(String tel) {
        uiTel.setContent(tel);
    }

    @Override
    public void setEmail(String email) {
        uiEmail.setContent(email);
    }

    @Override
    public void setSex(String sex) {
        uiSex.setContent(sex);
    }

    @Override
    public void setNiceStyle(String style) {
        uiStyle.setContent(style);
    }

    @Override
    public void getVerifyCodeSuccess() {
        identifyView.getVerifyCodeSuccess();
    }

    @Override
    public void setRegisterDate(String date) {
        uiRegisterDate.setContent(date);
    }

    @OnClick({R.id.btn_back, R.id.ui_name, R.id.ui_tel, R.id.ui_psw, R.id.ui_email, R.id.ui_sex, R.id.ui_style, R.id.bt_logout})
    void onTextMenuClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ui_name:
                showDialogFragment("填写昵称", R.layout.include_update_name, new EditDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onNegativeButtonClickListener(View content) {
                        String name = ((EditText) content.findViewById(R.id.ev_name)).getText().toString();
                        mPresenter.updateName(name);
                    }
                }, "inputName");
                break;
            case R.id.ui_tel:
                showDialogFragment("修改手机号码", R.layout.include_update_tel, new EditDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onNegativeButtonClickListener(View content) {
                        IdentifyView identifyView = (IdentifyView)content;
                        mPresenter.updateTel(identifyView.getTel(), identifyView.getIdentify());
                    }
                }, "updateTel");
                break;
            case R.id.ui_psw:
                showDialogFragment("修改密码", R.layout.include_update_psw, new EditDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onNegativeButtonClickListener(View content) {
                        String oldPsw = ((EditText)content.findViewById(R.id.ev_old_psw)).getText().toString();
                        String newPsw = ((EditText)content.findViewById(R.id.ev_new_psw)).getText().toString();
                        mPresenter.updatePsw(oldPsw, newPsw);
                    }
                }, "updatePsw");
                break;
            case R.id.ui_email:
                showDialogFragment("绑定邮箱", R.layout.include_update_email, new EditDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onNegativeButtonClickListener(View content) {
                        String email = ((EditText) content.findViewById(R.id.ev_email)).getText().toString();
                        mPresenter.updateEmail(email);
                    }
                }, "bindEmial");
                break;
            case R.id.ui_sex:
                showDialogFragment("填写性别", R.layout.include_update_sex, new EditDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onNegativeButtonClickListener(View content) {
                        RadioGroup rg = (RadioGroup) content.findViewById(R.id.rg_sex);
                        int checked = rg.getCheckedRadioButtonId();
                        mPresenter.updateSex(checked == R.id.boy);
                    }
                }, "updateSex");
                break;
            case R.id.ui_style:
                showDialogFragment("装修风格喜好", R.layout.include_update_style, new EditDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onNegativeButtonClickListener(View content) {
                        mPresenter.updateStyle(content);
                    }
                }, "updateStyle");
                break;
            case R.id.bt_logout:
                showLogoutDialog("是否确定退出登陆？");
                break;
        }
    }

    private void showDialogFragment(String title, int resId, EditDialogFragment.OnDialogClickListener listener, String tag) {
        View contentView = getLayoutInflater().inflate(resId, null, false);
        if(tag.equals("updateTel")) {
            identifyView = (IdentifyView)contentView;
            identifyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.requestSMSCode(identifyView.getTel());
                }
            });
        }
        EditDialogFragment dialogFragment = EditDialogFragment.getInstance(title, contentView);

        dialogFragment.setOnDialogClickListener(listener);
        dialogFragment.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void showLogoutDialog(String content) {
        AlertDialogFragment
                .getInstance(content, getString(R.string.text_cancel), getString(R.string.text_ensure))
                .setOnDialogClickListener(new AlertDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onPositiveButtonClickListener() {
                    }

                    @Override
                    public void onNegativeButtonClickListener() {
                        mPresenter.logoutState();
                    }
                })
                .show(getSupportFragmentManager(), "logout");
    }

}
