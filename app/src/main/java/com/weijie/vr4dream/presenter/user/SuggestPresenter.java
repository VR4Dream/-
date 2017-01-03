package com.weijie.vr4dream.presenter.user;

import android.content.Context;

import com.weijie.vr4dream.model.Suggest;
import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.ui.view.user.ISuggestView;
import com.weijie.vr4dream.utils.ErrorUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 意见反馈
 * 作者：guoweijie on 16/12/27 11:04
 * 邮箱：529844698@qq.com
 */
public class SuggestPresenter extends BaseActivityPresenter<ISuggestView> implements ISuggestPresenter {

    public SuggestPresenter(Context context, ISuggestView view) {
        super(context, view);
    }

    @Override
    public void clickCommit(String content) {
        if(!content.isEmpty()) {
            mView.showProgressDialog();
            Suggest suggest = new Suggest(content);
            suggest.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null) {
                        mView.showTips("谢谢您宝贵的意见！");
                        mView.finish();
                    } else {
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                    mView.dismissProgressDialog();
                }
            });
        } else {
            mView.showTips("请填写意见再提交。");
        }

    }
}
