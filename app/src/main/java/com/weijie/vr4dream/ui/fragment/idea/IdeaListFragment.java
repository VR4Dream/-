package com.weijie.vr4dream.ui.fragment.idea;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.adapter.IdeaListAdapter;
import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.adapter.OnListItemClickListener;
import com.weijie.vr4dream.model.Idea;
import com.weijie.vr4dream.presenter.idea.IdeaListPresenter;
import com.weijie.vr4dream.ui.fragment.BaseListFragment;
import com.weijie.vr4dream.ui.view.idea.IIdeaListView;
import com.weijie.vr4dream.ui.widget.RecyclerViewDivider;
import com.weijie.vr4dream.utils.ActivitySkipHelper;

import java.util.List;

/**
 * 文章列表
 * 作者：guoweijie on 16/12/20 16:54
 * 邮箱：529844698@qq.com
 */
public class IdeaListFragment extends BaseListFragment<IdeaListPresenter> implements IIdeaListView {

    private IdeaListAdapter mAdapter;
    private LoadTipAdapter tipAdapter;
    private int type;

    @Override
    protected void initialize() {
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL, 20, ContextCompat.getColor(mContext, android.R.color.transparent)));
        mAdapter = new IdeaListAdapter(mContext);
        mAdapter.setOnItemClickListener(itemClickListener);
        tipAdapter = new LoadTipAdapter(mContext);
        recyclerView.setAdapter(mAdapter);

        Bundle bundle = getArguments();
        if (bundle != null)
            type = bundle.getInt("type", 1);

    }

    private OnListItemClickListener itemClickListener = new OnListItemClickListener() {
        @Override
        public void onItemClickListener(View itemView, Object obj) {
            ActivitySkipHelper.toIdeaDetailActivity(mContext, (Idea)obj);
        }
    };

    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        return lm;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_idea_list;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new IdeaListPresenter(mContext, this);
    }

    @Override
    protected void onLoadMore() {
        mPresenter.loadMore(false,type);
    }

    @Override
    protected void onRefresh() {
        mPresenter.loadMore(true, type);
    }

    @Override
    public void refreshView(List<Idea> ideas) {
        mAdapter.notifyDataSetChanged(ideas);
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void loadMoreView(List<Idea> ideas) {
        mAdapter.loadMore(ideas);
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

    @Override
    public void setLoadStatus(boolean isLoadingMore) {
        mIsLoadingMore = isLoadingMore;
    }

    @Override
    public void refreshComplete() {
        mPtrFrameLayout.refreshComplete();
    }
}
