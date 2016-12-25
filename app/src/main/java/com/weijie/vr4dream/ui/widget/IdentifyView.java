package com.weijie.vr4dream.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.utils.Countdown;
import com.weijie.vr4dream.utils.StringUtil;

/**
 * 验证码控件
 * 作者：guoweijie on 16/12/17 08:38
 * 邮箱：529844698@qq.com
 */
public class IdentifyView extends LinearLayout {

    static final String VERCODE_TEXT = "获取验证码";
    private IconEditText evTel, evIdentify;
    private Button btIdentify;
    private Countdown viewTimer;

    public IdentifyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewTimer = new Countdown(handler, 180);

        inflate(context, R.layout.view_identify_input, this);
        evTel = (IconEditText)findViewById(R.id.re_username);
        evTel.setTextWatcher(new IdentifyTextWatcher());
        evIdentify = (IconEditText)findViewById(R.id.re_identify);
        btIdentify = (Button)findViewById(R.id.bt_identify);
    }

    class IdentifyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(StringUtil.validateMobile(s.toString()) && viewTimer.isTimer()) {
                btIdentify.setEnabled(true);
            } else {
                btIdentify.setEnabled(false);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {}

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                btIdentify.setText(VERCODE_TEXT);
                if(StringUtil.validateMobile(evTel.getText().toString())) {
                    btIdentify.setEnabled(true);
                }
            } else {
                btIdentify.setText(VERCODE_TEXT + msg.what);
                btIdentify.setEnabled(false);
            }

            return true;
        }
    });

    public String getTel() {
        return evTel.getText().toString();
    }

    public String getIdentify() {
        return evIdentify.getText().toString();
    }

    public void getVerifyCodeSuccess() {
        viewTimer.start();
    }

    public void getVerifyCodeFail() {
    }

    public void setOnClickListener(OnClickListener clickListener) {
        btIdentify.setOnClickListener(clickListener);
    }

}
