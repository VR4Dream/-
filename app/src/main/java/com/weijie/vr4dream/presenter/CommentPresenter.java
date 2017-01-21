package com.weijie.vr4dream.presenter;

import android.content.Context;

import com.weijie.vr4dream.ui.view.ICommentView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;

/**
 * 评论
 * 作者：guoweijie on 16/12/30 11:58
 * 邮箱：529844698@qq.com
 */
public abstract class CommentPresenter extends BaseActivityPresenter<ICommentView> implements ICommentPresenter {

    public CommentPresenter(Context context, ICommentView view) {
        super(context, view);
    }

    @Override
    public abstract void submitComment(String id, String content);

    @Override
    public void clickLogin() {
        ActivitySkipHelper.toLoginActivity(mContext);
    }

}
