package com.weijie.vr4dream.ui.activity.gallery;

import com.weijie.vr4dream.presenter.gallery.GalleryDetailPresenter;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.gallery.IGalleryDetailView;

/**
 * VR详情页
 * 作者：guoweijie on 17/1/3 10:58
 * 邮箱：529844698@qq.com
 */
public class GalleryDetailActivity extends BaseActivity<GalleryDetailPresenter> implements IGalleryDetailView {

    @Override
    protected void initPresenter() {
        mPresenter = new GalleryDetailPresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initialize() {

    }
}
