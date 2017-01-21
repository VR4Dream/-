package com.weijie.vr4dream.presenter;

import android.content.Context;

import com.weijie.vr4dream.ui.view.ICommentListView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;

/**
 * 评论列表
 * 作者：guoweijie on 16/12/31 09:50
 * 邮箱：529844698@qq.com
 */
public abstract class CommentListPresenter extends BaseActivityPresenter<ICommentListView> implements ICommentListPresenter {

    protected static final int PAGE_SIZE = 10;
    protected int more = 0;
    protected int page = 0;

    public CommentListPresenter(Context context, ICommentListView view) {
        super(context, view);
    }

    @Override
    public void clickComment(String id, int tag) {
        ActivitySkipHelper.toCommentActivity(mContext, id, tag);
    }

    @Override
    public abstract void loadMore(final boolean refresh, String id);

    @Override
    public void addMore() {
        more = more + 1;
    }

}
