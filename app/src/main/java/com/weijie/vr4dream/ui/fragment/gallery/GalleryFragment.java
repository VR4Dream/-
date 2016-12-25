package com.weijie.vr4dream.ui.fragment.gallery;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.adapter.VRListAdapter;
import com.weijie.vr4dream.presenter.gallery.GalleryPresenter;
import com.weijie.vr4dream.rxEvent.SlidingMenuStatusEvent;
import com.weijie.vr4dream.ui.fragment.BaseFragment;
import com.weijie.vr4dream.ui.view.gallery.IGalleryView;
import com.weijie.vr4dream.ui.widget.MultiRadioGroup;
import com.weijie.vr4dream.ui.widget.ParallaxPtrFrameLayout;
import com.weijie.vr4dream.ui.widget.VRSlidingMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
public class GalleryFragment extends BaseFragment<GalleryPresenter> implements IGalleryView {

    @Bind(R.id.vr_slidingmenu)
    VRSlidingMenu vrSlidingMenu;
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

    private VRListAdapter mVRListAdapter;
    private List<String> mDataList;

    @Bind(R.id.refreshLayout)
    ParallaxPtrFrameLayout mPtrFrameLayout;
    @Bind(R.id.recycleView)
    RecyclerView recyclerView;

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

        mPtrFrameLayout.addFooterView();
        mPtrFrameLayout.setPtrHandler(mPtrHandler);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        mVRListAdapter = new VRListAdapter(mContext);
        recyclerView.setAdapter(mVRListAdapter);

        // 由于PtrFrameLayout的自动刷新需要在onWindowFocusChanged(boolean)之后调用，所以这里延时250ms.
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh();
            }
        }, 250);
    }

    /**
     * 刷新监听。
     */
    private PtrHandler mPtrHandler = new PtrDefaultHandler2() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            // 这里延时2000ms，模拟网络请求。
            mPtrFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestDataList();
                }
            }, 2000);
        }

        @Override
        public void onLoadMoreBegin(PtrFrameLayout frame) {
            mPtrFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDataList.add("决战紫禁城");
                    mVRListAdapter.notifyDataSetChanged(mDataList);
                    mPtrFrameLayout.refreshComplete();
                }
            }, 2000);
        }
    };

    private void requestDataList() {
        mDataList = new ArrayList<>();
        Random random = new Random();
        int num = random.nextInt(10);
        for (int i = 0; i < num; i++) {
            mDataList.add("我是第" + i + "个");
        }
        mVRListAdapter.notifyDataSetChanged(mDataList);

        mPtrFrameLayout.refreshComplete();
    }

    @OnClick({R.id.tv_build_type, R.id.tv_area, R.id.tv_budget, R.id.tv_style, R.id.tv_hotness})
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
                Toast.makeText(mContext, "-----", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_question:
                break;
        }
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
