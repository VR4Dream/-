package com.weijie.vr4dream.ui.fragment.gallery;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.adapter.OnListItemClickListener;
import com.weijie.vr4dream.adapter.GalleryListAdapter;
import com.weijie.vr4dream.model.Gallery;
import com.weijie.vr4dream.presenter.gallery.GalleryPresenter;
import com.weijie.vr4dream.rxEvent.SlidingMenuStatusEvent;
import com.weijie.vr4dream.ui.fragment.BaseListFragment;
import com.weijie.vr4dream.ui.view.gallery.IGalleryView;
import com.weijie.vr4dream.ui.widget.MultiRadioGroup;
import com.weijie.vr4dream.ui.widget.RecyclerViewDivider;
import com.weijie.vr4dream.ui.widget.VRSlidingMenu;
import com.weijie.vr4dream.utils.ActivitySkipHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTouch;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.functions.Action1;

/**
 * 图库fragment
 * 作者：guoweijie on 16/12/16 09:31
 * 邮箱：529844698@qq.com
 */
public class GalleryFragment extends BaseListFragment<GalleryPresenter> implements IGalleryView {

    @Bind(R.id.vr_slidingmenu)
    VRSlidingMenu vrSlidingMenu;
    @Bind(R.id.rg_house)
    MultiRadioGroup rgHouse;
    @Bind(R.id.rg_build)
    MultiRadioGroup rgBuild;
    @Bind(R.id.rg_area)
    MultiRadioGroup rgArea;
    @Bind(R.id.rg_budget)
    MultiRadioGroup rgBudget;
    @Bind(R.id.rg_style)
    MultiRadioGroup rgStyle;
    @Bind(R.id.rg_hotness)
    MultiRadioGroup rgHotness;

    private GalleryListAdapter mAdapter;
    private LoadTipAdapter tipAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_gallery;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GalleryPresenter(mContext, this);
    }

    @Override
    protected void initialize() {
        subscribeEvent();

        setCheckedListener();

        refreshOnActivityCreated = true;
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL, 20, ContextCompat.getColor(mContext, android.R.color.transparent)));
        mAdapter = new GalleryListAdapter(mContext);
        mAdapter.setOnItemClickListener(itemClickListener);
        tipAdapter = new LoadTipAdapter(mContext);
        recyclerView.setAdapter(mAdapter);

        mPtrFrameLayout.addFooterView();
        mPtrFrameLayout.setPtrHandler(mPtrHandler);

    }

    private OnListItemClickListener itemClickListener = new OnListItemClickListener() {
        @Override
        public void onItemClickListener(View itemView, Object obj) {
            ActivitySkipHelper.toGalleryDetailActivity(mContext, null);
        }
    };

    /**
     * 刷新监听。
     */
    private PtrHandler mPtrHandler = new PtrDefaultHandler2() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            onRefresh();
        }

        @Override
        public void onLoadMoreBegin(PtrFrameLayout frame) {
            onLoadMore();
        }
    };

    @Override
    protected void onLoadMore() {
        mPresenter.loadMore(false);
    }

    @Override
    protected void onRefresh() {
        mPresenter.loadMore(true);
    }

    @Override
    public void refreshView(List<Gallery> gallerys) {
        mAdapter.notifyDataSetChanged(gallerys);
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void loadMoreView(List<Gallery> gallerys) {
        mAdapter.loadMore(gallerys);
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
    public void refreshComplete() {
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void autoRefresh() {
        mPtrFrameLayout.autoRefresh();
    }

    @Override
    public void hideMenu() {
        vrSlidingMenu.hideMenu();
    }

    @Override
    public void setLoadStatus(boolean isLoadingMore) {
        mIsLoadingMore = isLoadingMore;
    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        return lm;
    }

    @OnClick({R.id.tv_build_type, R.id.tv_area, R.id.tv_budget, R.id.tv_style, R.id.tv_hotness, R.id.tv_build_rel})
    void onRadioGroupTvClick(View view) {
        switch (view.getId()) {
            case R.id.tv_build_type:
                rgBuild.setVisibility(rgBuild.getVisibility()==View.GONE ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_area:
                rgArea.setVisibility(rgArea.getVisibility()==View.GONE ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_budget:
                rgBudget.setVisibility(rgBudget.getVisibility()==View.GONE ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_style:
                rgStyle.setVisibility(rgStyle.getVisibility()==View.GONE ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_hotness:
                rgHotness.setVisibility(rgHotness.getVisibility()==View.GONE ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_build_rel:
                mPresenter.clickBuildingEstate();
                break;
        }
    }

    @OnTouch({R.id.lv_gallery})
    boolean onLayoutTouch(View view, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN && vrSlidingMenu.isMenuShow()) {
            vrSlidingMenu.hideMenu();
            return true;
        }
        return false;
    }

    @OnClick({R.id.iv_menu, R.id.iv_question})
    void onToolBtClick(View view) {
        switch (view.getId()) {
            case R.id.iv_menu:
                vrSlidingMenu.toggle();
                break;
            case R.id.iv_question:
                break;
        }
    }

    private void setCheckedListener() {
        rgHouse.setOnCheckedChangeListener(listener);
        rgBuild.setOnCheckedChangeListener(listener);
        rgHotness.setOnCheckedChangeListener(listener);
        rgBudget.setOnCheckedChangeListener(listener);
        rgArea.setOnCheckedChangeListener(listener);
        rgStyle.setOnCheckedChangeListener(listener);
    }

    private MultiRadioGroup.OnCheckedChangeListener listener = new MultiRadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(MultiRadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_all_house:
                    mPresenter.setParam(GalleryPresenter.MODE, true);
                    break;
                case R.id.rb_part_house:
                    mPresenter.setParam(GalleryPresenter.MODE, false);
                    break;
                case R.id.building_all:
                    mPresenter.removeParam(GalleryPresenter.BUILDTYPE);
                    break;
                case R.id.building_01:
                    mPresenter.setParam(GalleryPresenter.BUILDTYPE, 1);
                    break;
                case R.id.building_02:
                    mPresenter.setParam(GalleryPresenter.BUILDTYPE, 2);
                    break;
                case R.id.building_03:
                    mPresenter.setParam(GalleryPresenter.BUILDTYPE, 3);
                    break;
                case R.id.building_04:
                    mPresenter.setParam(GalleryPresenter.BUILDTYPE, 4);
                    break;
                case R.id.building_05:
                    mPresenter.setParam(GalleryPresenter.BUILDTYPE, 5);
                    break;
                case R.id.area_all:
                    mPresenter.removeParam(GalleryPresenter.AREA);
                    break;
                case R.id.area_01:
                    mPresenter.setParam(GalleryPresenter.AREA, 1);
                    break;
                case R.id.area_02:
                    mPresenter.setParam(GalleryPresenter.AREA, 2);
                    break;
                case R.id.area_03:
                    mPresenter.setParam(GalleryPresenter.AREA, 3);
                    break;
                case R.id.area_04:
                    mPresenter.setParam(GalleryPresenter.AREA, 4);
                    break;
                case R.id.area_05:
                    mPresenter.setParam(GalleryPresenter.AREA, 5);
                    break;
                case R.id.area_06:
                    mPresenter.setParam(GalleryPresenter.AREA, 6);
                    break;
                case R.id.budget_all:
                    mPresenter.removeParam(GalleryPresenter.BUDGET);
                    break;
                case R.id.budget_01:
                    mPresenter.setParam(GalleryPresenter.BUDGET, 1);
                    break;
                case R.id.budget_02:
                    mPresenter.setParam(GalleryPresenter.BUDGET, 2);
                    break;
                case R.id.budget_03:
                    mPresenter.setParam(GalleryPresenter.BUDGET, 3);
                    break;
                case R.id.budget_04:
                    mPresenter.setParam(GalleryPresenter.BUDGET, 4);
                    break;
                case R.id.budget_05:
                    mPresenter.setParam(GalleryPresenter.BUDGET, 5);
                    break;
                case R.id.style_all:
                    mPresenter.removeParam(GalleryPresenter.STYLE);
                    break;
                case R.id.style_01:
                    mPresenter.setParam(GalleryPresenter.STYLE, 1);
                    break;
                case R.id.style_02:
                    mPresenter.setParam(GalleryPresenter.STYLE, 2);
                    break;
                case R.id.style_03:
                    mPresenter.setParam(GalleryPresenter.STYLE, 3);
                    break;
                case R.id.style_04:
                    mPresenter.setParam(GalleryPresenter.STYLE, 4);
                    break;
                case R.id.style_05:
                    mPresenter.setParam(GalleryPresenter.STYLE, 5);
                    break;
                case R.id.style_06:
                    mPresenter.setParam(GalleryPresenter.STYLE, 6);
                    break;
                case R.id.style_07:
                    mPresenter.setParam(GalleryPresenter.STYLE, 7);
                    break;
                case R.id.style_08:
                    mPresenter.setParam(GalleryPresenter.STYLE, 8);
                    break;
                case R.id.style_09:
                    mPresenter.setParam(GalleryPresenter.STYLE, 9);
                    break;
                case R.id.hotness_01:
                    mPresenter.setParam(GalleryPresenter.HOTNESS, "-createdAt");
                    break;
                case R.id.hotness_02:
                    mPresenter.setParam(GalleryPresenter.HOTNESS, "-likeNum");
                    break;
            }
        }
    };

    /**
     * 事件订阅
     */
    private void subscribeEvent() {
        App.getInstance()
                .getRxBus()
                .subscribeNormalEvent(this, new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof SlidingMenuStatusEvent) {
                            // 更改侧滑菜单状态
                            if(vrSlidingMenu.isMenuShow()) {
                                vrSlidingMenu.hideMenu();
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        App.getInstance()
                .getRxBus()
                .unSubscribe(this);
        super.onDestroy();
    }

}
