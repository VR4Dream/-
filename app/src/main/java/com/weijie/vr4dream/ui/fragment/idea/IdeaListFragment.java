package com.weijie.vr4dream.ui.fragment.idea;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.adapter.IdeaListAdapter;
import com.weijie.vr4dream.adapter.OnListItemClickListener;
import com.weijie.vr4dream.presenter.idea.IdeaListPresenter;
import com.weijie.vr4dream.ui.fragment.BaseListFragment;
import com.weijie.vr4dream.ui.view.idea.IIdeaListView;
import com.weijie.vr4dream.ui.widget.RecyclerViewDivider;
import com.weijie.vr4dream.utils.ActivitySkipHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 文章列表
 * 作者：guoweijie on 16/12/20 16:54
 * 邮箱：529844698@qq.com
 */
public class IdeaListFragment extends BaseListFragment<IdeaListPresenter> implements IIdeaListView {

    private List<String> mDataList;
    private IdeaListAdapter mAdapter;

    @Override
    protected void initialize() {
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL, 20, ContextCompat.getColor(mContext, android.R.color.transparent)));
        mAdapter = new IdeaListAdapter(mContext);
        mAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(mAdapter);
    }

    private OnListItemClickListener itemClickListener = new OnListItemClickListener() {
        @Override
        public void onItemClickListener(View itemView, int position) {
            ActivitySkipHelper.toIdeaDetailActivity(mContext);
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
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDataList.add("决战紫禁城");
                mAdapter.notifyDataSetChanged();
                mPtrFrameLayout.refreshComplete();
                mIsLoadingMore = false;
            }
        }, 2000);
    }

    @Override
    protected void onRefresh() {
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDataList = new ArrayList<>();
                Random random = new Random();
                int num = random.nextInt(10);
                for (int i = 0; i < num; i++) {
                    mDataList.add("我是第" + i + "个");
                }
                mAdapter.notifyDataSetChanged(mDataList);

                mPtrFrameLayout.refreshComplete();
            }
        }, 2000);
    }
}
