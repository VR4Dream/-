package com.weijie.vr4dream.ui.activity;


import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.CommentPresenter;
import com.weijie.vr4dream.presenter.gallery.GalleryCommentPresenter;
import com.weijie.vr4dream.presenter.idea.IdeaCommentPresenter;
import com.weijie.vr4dream.ui.view.ICommentView;
import com.weijie.vr4dream.ui.widget.AlertDialogFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 评论界面
 * 作者：guoweijie on 16/12/30 10:47
 * 邮箱：529844698@qq.com
 */
public class CommentActivity extends BaseActivity<CommentPresenter> implements ICommentView {

    @Bind(R.id.ev_comment)
    EditText evComment;

    private String id;
    private int tag;

    @Override
    protected void initPresenter() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        tag = intent.getIntExtra("tag", 1);
        if(tag == 0) {
            mPresenter = new GalleryCommentPresenter(mContext, this);
        } else {
            mPresenter = new IdeaCommentPresenter(mContext, this);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initialize() {
    }

    @Override
    public void showLoginDialog(String content) {
        AlertDialogFragment dialogFragment = AlertDialogFragment.getInstance(content);
        dialogFragment.setOnDialogClickListener(new AlertDialogFragment.OnDialogClickListener() {
            @Override
            public void onPositiveButtonClickListener() {
            }
            @Override
            public void onNegativeButtonClickListener() {
                mPresenter.clickLogin();
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "loginTip");
    }

    @OnClick({R.id.btn_left, R.id.btn_right})
    void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_right:
                mPresenter.submitComment(id, evComment.getText().toString());
                break;
        }
    }

}
