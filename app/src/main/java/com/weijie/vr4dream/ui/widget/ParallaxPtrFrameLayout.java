package com.weijie.vr4dream.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * 自定义下拉刷新
 * 作者：guoweijie on 16/12/19 17:01
 * 邮箱：529844698@qq.com
 */
public class ParallaxPtrFrameLayout extends PtrClassicFrameLayout {

    private ParallaxHeader mParallaxHeader;

    public ParallaxPtrFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public ParallaxPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ParallaxPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mParallaxHeader = new ParallaxHeader(getContext());
        setHeaderView(mParallaxHeader);
        addPtrUIHandler(mParallaxHeader);


    }

    public void addFooterView() {
        ParallaxFooter mParallaxFooter;

        mParallaxFooter = new ParallaxFooter(getContext());
        setFooterView(mParallaxFooter);
        addPtrUIHandler(mParallaxFooter);
    }

}

