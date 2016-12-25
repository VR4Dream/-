package com.weijie.vr4dream.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.weijie.vr4dream.R;

/**
 * 家装百科多状态加载view
 * 作者：guoweijie on 16/12/15 20:23
 * 邮箱：529844698@qq.com
 */
public class VRMultiStatusView extends MultiStatusView {

    public VRMultiStatusView(Context context) {
        this(context, null);
    }

    public VRMultiStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setLoadingView(R.layout.progress_wheel, false);
        super.setErrView(R.layout.network_err, false);
    }

    public interface OnRetryListener {
        void retryListener();
    }

    /**
     * 设置重试按钮的监听事件
     *
     * @param onRetryListener 重试按钮点击监听事件
     */
    public void setOnRetryListener(final OnRetryListener onRetryListener) {
        if (onRetryListener != null) {
            getErrView().findViewById(R.id.tvRetry).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    setViewStatus(ViewStatus.STATUS_LOADING);
                    onRetryListener.retryListener();
                }
            });
        }
    }

}

