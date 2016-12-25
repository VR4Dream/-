package com.weijie.vr4dream.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.weijie.vr4dream.R;

/**
 * 进度条对话框
 * 作者：guoweijie on 16/12/15 17:55
 * 邮箱：529844698@qq.com
 */
public class ProgressDialog extends Dialog {

    public ProgressDialog(Context context) {
        this(context, R.style.ProgressDialog);
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        this.setContentView(R.layout.progress_wheel);
        // 设置居中
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        // 设置弹出对话框背景变暗0.1
        this.getWindow().getAttributes().dimAmount = 0.3f;
        // 设置点击外边布局不可取消
        this.setCanceledOnTouchOutside(false);
    }

}