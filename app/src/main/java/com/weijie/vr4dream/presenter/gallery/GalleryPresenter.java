package com.weijie.vr4dream.presenter.gallery;

import android.content.Context;

import com.weijie.vr4dream.presenter.BaseFragmentPresenter;
import com.weijie.vr4dream.presenter.mine.IMinePresenter;
import com.weijie.vr4dream.ui.view.gallery.IGalleryView;
import com.weijie.vr4dream.ui.view.mine.IMineView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;

/**
 * 图库
 * 作者：guoweijie on 16/12/16 10:52
 * 邮箱：529844698@qq.com
 */
public class GalleryPresenter extends BaseFragmentPresenter<IGalleryView> implements IGalleryPresenter {

    public GalleryPresenter(Context context, IGalleryView view) {
        super(context, view);
    }

}
