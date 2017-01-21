package com.weijie.vr4dream.ui.activity.gallery;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.adapter.GalleryListAdapter;
import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.adapter.OnListItemClickListener;
import com.weijie.vr4dream.model.BuildingEstate;
import com.weijie.vr4dream.model.Gallery;
import com.weijie.vr4dream.presenter.gallery.GalleryActivityPresenter;
import com.weijie.vr4dream.ui.activity.BaseListActivity;
import com.weijie.vr4dream.ui.view.gallery.IGalleryActivityView;
import com.weijie.vr4dream.ui.widget.TitleBarView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 场景列表
 * 作者：guoweijie on 17/1/5 17:14
 * 邮箱：529844698@qq.com
 */
public class GalleryListActiity extends BaseListActivity<GalleryActivityPresenter> implements IGalleryActivityView {

    @Bind(R.id.titlebar)
    TitleBarView titleBarView;

    private GalleryListAdapter mAdapter;
    private LoadTipAdapter tipAdapter;
    private BuildingEstate estate;

    @Override
    protected void initialize() {
        super.initialize();
        estate = (BuildingEstate)getIntent().getSerializableExtra("data");
        titleBarView.setTitle(estate.getName());
        initRecyclerView();
        refresh();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GalleryActivityPresenter(mContext, this);
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

    private OnListItemClickListener itemClickListener = new OnListItemClickListener() {
        @Override
        public void onItemClickListener(View itemView, Object obj) {
            ActivitySkipHelper.toGalleryDetailActivity(mContext, (Gallery)obj);
        }
    };

    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        return lm;
    }

    @Override
    public void refreshView(List<Gallery> datas) {
        mAdapter.notifyDataSetChanged(datas);
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void loadMoreView(List<Gallery> datas) {
        mAdapter.loadMore(datas);
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    protected void onLoadMore() {
        mPresenter.loadMore(false, estate.getObjectId());
    }

    @Override
    protected void onRefresh() {
        mPresenter.loadMore(true, estate.getObjectId());
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

    @OnClick({R.id.btn_left})
    void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
        }
    }

}
