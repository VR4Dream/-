package com.weijie.vr4dream.presenter;

import android.content.Context;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.model.Idea;
import com.weijie.vr4dream.model.IdeaComment;
import com.weijie.vr4dream.model.VRUser;
import com.weijie.vr4dream.rxEvent.AddCommentEvent;
import com.weijie.vr4dream.ui.view.ICommentView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;
import com.weijie.vr4dream.utils.ErrorUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 评论
 * 作者：guoweijie on 16/12/30 11:58
 * 邮箱：529844698@qq.com
 */
public class CommentPresenter extends BaseActivityPresenter<ICommentView> implements ICommentPresenter {

    public CommentPresenter(Context context, ICommentView view) {
        super(context, view);
    }

    @Override
    public void submitComment(String id, String content) {
        if(content.trim().equals("")) {
            mView.showTips("亲，您还没填写评论哦！");
            return;
        }
        VRUser user = BmobUser.getCurrentUser(VRUser.class);
        if(user == null) {
            mView.showLoginDialog("亲，您还没有登录！");
        } else {
            Idea idea = new Idea();
            idea.setObjectId(id);
            final IdeaComment comment = new IdeaComment();
            comment.setContent(content);
            comment.setIdea(idea);
            comment.setAuthor(user);
            comment.save(new SaveListener<String>() {
                @Override
                public void done(String objectId,BmobException e) {
                    if(e==null) {
                        mView.showTips("评论成功！");
                        App.getInstance().getRxBus().sendNormalEvent(new AddCommentEvent(comment));
                        mView.finish();
                    } else {
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                }
            });
        }
    }

    @Override
    public void clickLogin() {
        ActivitySkipHelper.toLoginActivity(mContext);
    }

}
