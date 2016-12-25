package com.weijie.vr4dream.ui.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.BaseFragmentPresenter;
import com.weijie.vr4dream.ui.widget.ParallaxPtrFrameLayout;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 列表BaseFragment
 * 作者：guoweijie on 16/12/20 17:37
 * 邮箱：529844698@qq.com
 */
public  abstract class BaseListFragment<P extends BaseFragmentPresenter> extends RefreshableFragment<P> {

    @Bind(R.id.refreshLayout)
    protected ParallaxPtrFrameLayout mPtrFrameLayout;
    @Bind(R.id.recycleView)
    protected RecyclerView recyclerView;

    // LayoutManager
    private RecyclerView.LayoutManager mLayoutManager;
    // 是否正在加载更多数据
    protected boolean mIsLoadingMore;
    // 最后可见的索引
    private int mLastVisibleItemPosition;

    @Override
    public void refresh() {
        refreshed = true;
        mLayoutManager = getRecyclerViewLayoutManager();
        initRecyclerView();

        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh();
            }
        }, 250);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        if (recyclerView == null) {
            throw new IllegalStateException("please add RecyclerView in your layout with id is recycleView...");
        }
        if (mLayoutManager == null) {
            throw new NullPointerException("RecyclerView's LayoutManager can not be null...");
        }
        if (!(mLayoutManager instanceof StaggeredGridLayoutManager)
                && !(mLayoutManager instanceof LinearLayoutManager)) {
            throw new IllegalStateException("this LayoutManager is not StaggeredGridLayoutManager or LinearLayoutManager...");
        }
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int visibleItemCount;
            int totalItemCount;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                findLastItemPosition();
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                if (!mIsLoadingMore
                        && visibleItemCount > 0
                        && newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition >= totalItemCount - 1
                        && totalItemCount > visibleItemCount) {

                    mIsLoadingMore = true;
                    onLoadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mPtrFrameLayout.setPtrHandler(mPtrHandler);

    }

    /**
     * 刷新监听。
     */
    private PtrHandler mPtrHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            onRefresh();
        }
    };

    private void findLastItemPosition() {
        mLastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
    }

    /**
     * 加载更多
     */
    protected abstract void onLoadMore();

    /**
     * 刷新
     */
    protected abstract void onRefresh();

    /**
     * 获取RecyclerView的LayoutManager
     *
     * @return LayoutManager
     */
    protected abstract RecyclerView.LayoutManager getRecyclerViewLayoutManager();

}
