package com.weijie.vr4dream.presenter.user;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.model.VRUser;
import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.ui.view.user.IInfoView;
import com.weijie.vr4dream.utils.ErrorUtil;
import com.weijie.vr4dream.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 用户信息
 * 作者：guoweijie on 16/12/26 10:51
 * 邮箱：529844698@qq.com
 */
public class InfoPresenter extends BaseActivityPresenter<IInfoView> implements IInfoPresenter {

    public InfoPresenter(Context context, IInfoView view) {
        super(context, view);
    }

    private VRUser user;

    @Override
    public void initInfo() {
        user = BmobUser.getCurrentUser(VRUser.class);
        if(user!=null) {
            mView.setName(user.getUsername());
            mView.setTel(user.getMobilePhoneNumber());
            mView.setEmail(user.getEmail() == null ? "" : user.getEmail());
            mView.setSex(user.getSex() == null ? "" : user.getSex() ? "男" : "女");

            List<String> styles = user.getStyle();
            if(styles!=null) {
                StringBuilder style = new StringBuilder();
                for(String s : styles) {
                    style.append(s);
                    style.append(" | ");
                }
                mView.setNiceStyle(style.substring(0, style.length() - 2));
            }

            mView.setRegisterDate(user.getCreatedAt().substring(0, 10));
        }
    }

    @Override
    public void updateName(final String name) {
        if(!name.trim().isEmpty()) {
            mView.showProgressDialog();
            VRUser newUser = new VRUser();
            newUser.setUsername(name);
            newUser.update(user.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        mView.setName(name);
                        App.getInstance()
                                .getRxBus()
                                .sendNormalEvent(new LoginStateChangeEvent(true));
                    } else {
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                    mView.dismissProgressDialog();
                }
            });
        }
    }

    @Override
    public void updatePsw(String oldPsw, String newPsw) {
        if(oldPsw.isEmpty() || newPsw.isEmpty()) {
            mView.showTips("密码不能为空，请填写完整！");
        } else {
            mView.showProgressDialog();
            BmobUser.updateCurrentUserPassword(oldPsw, newPsw, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        mView.showTips("修改密码成功！");
                    } else {
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                    mView.dismissProgressDialog();
                }
            });
        }
    }

    @Override
    public void updateEmail(final String email) {
        if(StringUtil.validateEmail(email)) {
            mView.showProgressDialog();
            VRUser newUser = new VRUser();
            newUser.setEmail(email);
            newUser.update(user.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        mView.setEmail(email);
                        //requestEmailVerify(email);
                        mView.showTips("我们已经把邮件发到您的邮箱，请收到之后进行验证激活！");
                    } else {
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                    mView.dismissProgressDialog();
                }
            });
        } else {
            mView.showTips("您的邮箱格式不正确，请重新填写！");
        }
    }

    @Override
    public void updateSex(final Boolean sex) {
        mView.showProgressDialog();
        VRUser newUser = new VRUser();
        newUser.setSex(sex);
        newUser.update(user.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    mView.setSex(sex ? "男" : "女");
                } else {
                    mView.showTips(ErrorUtil.getErrorMsg(e));
                }
                mView.dismissProgressDialog();
            }
        });
    }

    @Override
    public void updateStyle(View cintent) {
        final List<String> styles = new ArrayList<String>();
        String style;
        CheckBox c1 = (CheckBox)cintent.findViewById(R.id.style_01);
        if ((style = getCheckedText(c1))!=null) {
            styles.add(style);
        }
        CheckBox c2 = (CheckBox)cintent.findViewById(R.id.style_02);
        if ((style = getCheckedText(c2))!=null) {
            styles.add(style);
        }
        CheckBox c3 = (CheckBox)cintent.findViewById(R.id.style_03);
        if ((style = getCheckedText(c3))!=null) {
            styles.add(style);
        }
        CheckBox c4 = (CheckBox)cintent.findViewById(R.id.style_04);
        if ((style = getCheckedText(c4))!=null) {
            styles.add(style);
        }
        CheckBox c5 = (CheckBox)cintent.findViewById(R.id.style_05);
        if ((style = getCheckedText(c5))!=null) {
            styles.add(style);
        }
        CheckBox c6 = (CheckBox)cintent.findViewById(R.id.style_06);
        if ((style = getCheckedText(c6))!=null) {
            styles.add(style);
        }
        CheckBox c7 = (CheckBox)cintent.findViewById(R.id.style_07);
        if ((style = getCheckedText(c7))!=null) {
            styles.add(style);
        }
        CheckBox c8 = (CheckBox)cintent.findViewById(R.id.style_08);
        if ((style = getCheckedText(c8))!=null) {
            styles.add(style);
        }
        CheckBox c9 = (CheckBox)cintent.findViewById(R.id.style_09);
        if ((style = getCheckedText(c9))!=null) {
            styles.add(style);
        }

        if(styles.size()>0) {
            mView.showProgressDialog();
            VRUser newUser = new VRUser();
            newUser.setStyle(styles);
            newUser.update(user.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        StringBuilder style = new StringBuilder();
                        for(String s : styles) {
                            style.append(s);
                            style.append(" | ");
                        }
                        mView.setNiceStyle(style.substring(0, style.length() - 2));
                    } else {
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                    mView.dismissProgressDialog();
                }
            });
        }

    }

    private String getCheckedText(CheckBox checkBox) {
        if(checkBox.isChecked()) {
            return checkBox.getText().toString();
        } else return null;
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
    public void updateTel(final String tel, String code) {
        if(!StringUtil.validateMobile(tel)) {
            mView.showTips(mContext.getString(R.string.input_the_true_number));
        } else if(code.length()!=6) {
            mView.showTips(mContext.getString(R.string.input_true_identifying));
        } else {
            mView.showProgressDialog();
            BmobSMS.verifySmsCode(tel, code, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        mView.showTips("您已成功绑定手机号码！");
                        mView.setTel(tel);
                    } else {
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                    mView.dismissProgressDialog();
                }
            });

        }
    }

    @Override
    public void logoutState() {
        BmobUser.logOut();
        App.getInstance()
                .getRxBus()
                .sendNormalEvent(new LoginStateChangeEvent(false));
        mView.finish();
    }
}
