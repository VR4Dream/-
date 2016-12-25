package com.weijie.vr4dream.presenter.idea;

import android.content.Context;

import com.weijie.vr4dream.presenter.BaseFragmentPresenter;
import com.weijie.vr4dream.ui.view.idea.IIdeaListView;
import com.weijie.vr4dream.ui.view.idea.IIdeaView;

/**
 * 灵感列表
 * 作者：guoweijie on 16/12/20 15:01
 * 邮箱：529844698@qq.com
 */
public class IdeaListPresenter extends BaseFragmentPresenter<IIdeaListView> implements IIdeaListPresenter {

    public IdeaListPresenter(Context context, IIdeaListView view) {
        super(context, view);
    }
}
