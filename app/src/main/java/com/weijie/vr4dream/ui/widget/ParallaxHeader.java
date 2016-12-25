package com.weijie.vr4dream.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.weijie.vr4dream.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * 自定义下下拉刷新头部
 * 作者：guoweijie on 16/12/19 16:30
 * 邮箱：529844698@qq.com
 */
public class ParallaxHeader extends FrameLayout implements PtrUIHandler {

    ImageView mIvIcon;
    TextView mTvTip;

    private String tip1,tip2,tip4;
    private AnimationDrawable mAnimationDrawable;

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.refresh_parallax, this);
        mIvIcon = (ImageView) findViewById(R.id.iv_refresh_icon);
        mAnimationDrawable = (AnimationDrawable) mIvIcon.getDrawable();
        mAnimationDrawable.start();
        mTvTip = (TextView)findViewById(R.id.tv_refresh_tip);
    }

    public ParallaxHeader(Context context) {
        this(context, null, 0);
    }

    public ParallaxHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        tip1 = context.getResources().getString(R.string.loading_tip1);
        tip2 = context.getResources().getString(R.string.loading_tip2);
        tip4 = context.getResources().getString(R.string.loading_tip4);
        initialize();
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        mTvTip.setText(tip1);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mTvTip.setText(tip1);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mTvTip.setText(tip2);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();

        if (currentPos < mOffsetToRefresh ) {
            //未到达刷新线
            if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                mTvTip.setText(tip1);
            }
        } else if (currentPos > mOffsetToRefresh ) {
            //到达或超过刷新线
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                mTvTip.setText(tip4);
            }
        }

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {
        mTvTip.setText(tip1);
    }
}
