package com.weijie.vr4dream.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.weijie.vr4dream.R;

/**
 * 分享dialog
 * 作者：guoweijie on 17/1/12 14:36
 * 邮箱：529844698@qq.com
 */
public class ShareAppDialog extends Dialog {

    public ShareAppDialog(Context context) {
        super(context);
        this.show();
    }

    public ShareAppDialog(Context context, int theme) {
        super(context, theme);
        this.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_share_app);
    }

}
