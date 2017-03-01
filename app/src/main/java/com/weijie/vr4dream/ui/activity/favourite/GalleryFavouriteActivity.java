package com.weijie.vr4dream.ui.activity.favourite;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.adapter.GalleryListAdapter;
import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.adapter.OnListItemClickListener;
import com.weijie.vr4dream.model.Gallery;
import com.weijie.vr4dream.presenter.favourite.GalleryFavouritePresenter;
import com.weijie.vr4dream.ui.activity.BaseListActivity;
import com.weijie.vr4dream.ui.view.gallery.IGalleryActivityView;
import com.weijie.vr4dream.ui.widget.TitleBarView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 文章收藏列表
 * 作者：guoweijie on 17/2/8 19:58
 * 邮箱：529844698@qq.com
 */
public class GalleryFavouriteActivity extends BaseListActivity<GalleryFavouritePresenter> implements IGalleryActivityView {

    @Bind(R.id.titlebar)
    TitleBarView titleBarView;

    private GalleryListAdapter mAdapter;
    private LoadTipAdapter tipAdapter;

    @Override
    protected void initialize() {
        super.initialize();
        titleBarView.setTitle("3D场景收藏");
        initRecyclerView();
        refresh();
    }

    @Override
    protected void onLoadMore() {
        mPresenter.loadMore(false);
    }

    @Override
    protected void onRefresh() {
        mPresenter.loadMore(true);
    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        return lm;
    }

    private OnListItemClickListener itemClickListener = new OnListItemClickListener() {
        @Override
        public void onItemClickListener(View itemView, Object obj) {
            ActivitySkipHelper.toGalleryDetailActivity(mContext, (Gallery) obj);
        }
    };

    @Override
    protected void initPresenter() {
        mPresenter = new GalleryFavouritePresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gallery_list;
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mAdapter = new GalleryListAdapter(mContext);
        tipAdapter = new LoadTipAdapter(mContext);
        mAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void refreshView(List<Gallery> galleries) {
        mAdapter.notifyDataSetChanged(galleries);
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void loadMoreView(List<Gallery> galleries) {
        mAdapter.loadMore(galleries);
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

    @OnClick({R.id.btn_left})
    void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
        }
    }
}
