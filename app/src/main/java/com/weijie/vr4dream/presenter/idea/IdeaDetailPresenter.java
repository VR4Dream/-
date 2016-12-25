package com.weijie.vr4dream.presenter.idea;

import android.content.Context;

import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.ui.view.idea.IIdeaDetailView;

/**
 * 文章详情
 * 作者：guoweijie on 16/12/22 10:42
 * 邮箱：529844698@qq.com
 */
public class IdeaDetailPresenter extends BaseActivityPresenter<IIdeaDetailView> implements IIdeaDeatilPresenter {
    public IdeaDetailPresenter(Context context, IIdeaDetailView view) {
        super(context, view);
    }
}
