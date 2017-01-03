package com.weijie.vr4dream.presenter.gallery;

import android.content.Context;

import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.ui.view.gallery.IGalleryDetailView;

/**
 * 作者：guoweijie on 17/1/3 11:08
 * 邮箱：529844698@qq.com
 */
public class GalleryDetailPresenter extends BaseActivityPresenter<IGalleryDetailView> implements IGalleryDetailPresenter {

    public GalleryDetailPresenter(Context context, IGalleryDetailView view) {
        super(context, view);
    }
}
