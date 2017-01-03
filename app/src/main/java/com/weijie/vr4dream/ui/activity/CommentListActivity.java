package com.weijie.vr4dream.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.adapter.CommentListAdapter;
import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.model.IdeaComment;
import com.weijie.vr4dream.presenter.CommentListPresenter;
import com.weijie.vr4dream.rxEvent.AddCommentEvent;
import com.weijie.vr4dream.ui.view.ICommentListView;

import java.util.List;

import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 评论列表
 * 作者：guoweijie on 16/12/31 09:48
 * 邮箱：529844698@qq.com
 */
public class CommentListActivity extends BaseListActivity<CommentListPresenter> implements ICommentListView {

    private CommentListAdapter mAdapter;
    private LoadTipAdapter tipAdapter;
    private String id;

    @Override
    protected void initPresenter() {
        mPresenter = new CommentListPresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment_list;
    }

    @Override
    protected void initialize() {
        super.initialize();
        id = getIntent().getStringExtra("id");
        initRecyclerView();
        subscribeEvent();
        refresh();
    }

    /**
     * 事件订阅
     */
    private void subscribeEvent() {
        App.getInstance()
                .getRxBus()
                .subscribeNormalEvent(this, new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof AddCommentEvent) {
                            AddCommentEvent event = (AddCommentEvent) o;
                            if(recyclerView.getAdapter()==tipAdapter) {
                                hideTip();
                            }
                            mAdapter.addTop(event.getComment());
                            mPresenter.addMore();
                        }
                    }
                });
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mAdapter = new CommentListAdapter(mContext);
        tipAdapter = new LoadTipAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        return lm;
    }

    @Override
    public void refreshView(List<IdeaComment> comments) {
        mAdapter.notifyDataSetChanged(comments);
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void loadMoreView(List<IdeaComment> comments) {
        mAdapter.loadMore(comments);
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    protected void onLoadMore() {
        mPresenter.loadMore(false, id);
    }

    @Override
    protected void onRefresh() {
        mPresenter.loadMore(true, id);
    }

    @Override
    public void setLoadStatus(boolean isLoadingMore) {
        mIsLoadingMore = isLoadingMore;
    }

    @Override
    public void refreshComplete() {
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void hideTip() {
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showTip(LoadTipAdapter.ViewStatus status) {
        recyclerView.setAdapter(tipAdapter);
        tipAdapter.notifyDataSetChanged(status);
    }

    @OnClick({R.id.btn_left, R.id.btn_right})
    void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_right:
                mPresenter.clickComment(id);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        App.getInstance()
                .getRxBus()
                .unSubscribe(this);
        super.onDestroy();
    }
}
