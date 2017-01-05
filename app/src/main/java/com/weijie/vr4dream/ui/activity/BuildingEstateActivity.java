package com.weijie.vr4dream.ui.activity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.adapter.EstateListAdapter;
import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.adapter.OnListItemClickListener;
import com.weijie.vr4dream.config.AppConstant;
import com.weijie.vr4dream.config.AppSetting;
import com.weijie.vr4dream.model.BuildingEstate;
import com.weijie.vr4dream.presenter.BuildingEstatePresenter;
import com.weijie.vr4dream.ui.view.IBuildingEstateView;
import com.weijie.vr4dream.ui.widget.EditDialogFragment;
import com.weijie.vr4dream.ui.widget.IconEditText;
import com.weijie.vr4dream.ui.widget.RecyclerViewDivider;
import com.weijie.vr4dream.ui.widget.ScrollerNumberPicker;
import com.weijie.vr4dream.ui.widget.SearchLayout;
import com.weijie.vr4dream.utils.JSONUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 楼盘列表
 * 作者：guoweijie on 17/1/4 09:55
 * 邮箱：529844698@qq.com
 */
public class BuildingEstateActivity extends BaseListActivity<BuildingEstatePresenter> implements IBuildingEstateView, View.OnLayoutChangeListener {

    @Bind(R.id.lv_search)
    LinearLayout lvSearch;
    @Bind(R.id.slv_history)
    SearchLayout slvHistory;
    @Bind(R.id.slv_estate)
    SearchLayout slvEstate;
    @Bind(R.id.slv_city)
    SearchLayout slvCity;
    @Bind(R.id.lv_root)
    View activityRootView;
    @Bind(R.id.ev_estate)
    IconEditText evEstate;
    @Bind(R.id.tv_district)
    TextView tvDistrict;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    private EstateListAdapter mAdapter;
    private LoadTipAdapter tipAdapter;

    private TypeToken<ArrayList<String>> typeToken = new TypeToken<ArrayList<String>>(){};

    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        return lm;
    }

    @Override
    protected void initialize() {
        super.initialize();

        String sHistory = AppSetting.getInstance().getString(AppConstant.HISTORYS);
        if(sHistory!=null && sHistory.length()>0) {
            slvHistory.setData(JSONUtils.toList(sHistory, typeToken).toArray(), listener);
        }
        slvEstate.setData(new String[] {"誉江华府", "阳光海岸花园", "粤彩豪庭", "高逸华轩"},listener);
        slvCity.setData(new String[] {"北京市", "广州市", "佛山市"},listener);

        initRecyclerView();
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;
        activityRootView.addOnLayoutChangeListener(this);
        refresh();
    }


    @Override
    protected void initPresenter() {
        mPresenter = new BuildingEstatePresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_building_list;
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        recyclerView.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(mContext, R.color.dividerColor)));
        mAdapter = new EstateListAdapter(mContext);
        mAdapter.setOnItemClickListener(itemClickListener);
        tipAdapter = new LoadTipAdapter(mContext);
        recyclerView.setAdapter(mAdapter);
    }

    private OnListItemClickListener itemClickListener = new OnListItemClickListener() {
        @Override
        public void onItemClickListener(View itemView, Object obj) {
            mPresenter.onItemClick((BuildingEstate)obj);
        }
    };

    @Override
    public void refreshView(List<BuildingEstate> datas) {
        mAdapter.notifyDataSetChanged(datas);
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void loadMoreView(List<BuildingEstate> datas) {
        mAdapter.loadMore(datas);
        mPtrFrameLayout.refreshComplete();
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

    @Override
    public void setHistory(List<String> params) {
        slvHistory.setData(params.toArray(), listener);
    }

    @Override
    public void hideSearchLayout() {
        lvSearch.setVisibility(View.GONE);
        mPtrFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void autoRefresh() {
        mPtrFrameLayout.autoRefresh();
    }

    @Override
    public void hideSoftInputFromWindow() {
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick({R.id.lv_search, R.id.btn_left, R.id.btn_search, R.id.tv_district})
    void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.lv_search:
                hideSearchLayout();
                break;
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_search:
                String param = evEstate.getText().toString();
                mPresenter.setHistory(param);
                break;
            case R.id.tv_district:
                showDialogFragment("选择地区", R.layout.view_address_dialog, new EditDialogFragment.OnDialogClickListener() {
                    @Override
                    public void onNegativeButtonClickListener(View content) {
                        ScrollerNumberPicker provincePicker = (ScrollerNumberPicker)content.findViewById(R.id.province);
                        ScrollerNumberPicker cityPicker = (ScrollerNumberPicker)content.findViewById(R.id.city);
                        ScrollerNumberPicker counyPicker = (ScrollerNumberPicker)content.findViewById(R.id.couny);
                        if(!counyPicker.getSelectedText().trim().equals("")) {
                            tvDistrict.setText(counyPicker.getSelectedText());
                            mPresenter.setParam(BuildingEstatePresenter.DISTRICT, counyPicker.getSelectedText());
                        } else if(!cityPicker.getSelectedText().trim().equals("")) {
                            tvDistrict.setText(cityPicker.getSelectedText());
                            mPresenter.setParam(BuildingEstatePresenter.CITY, cityPicker.getSelectedText());
                        } else if(!provincePicker.getSelectedText().trim().equals("")) {
                            tvDistrict.setText(provincePicker.getSelectedText());
                            mPresenter.setParam(BuildingEstatePresenter.PROVINCE, provincePicker.getSelectedText());
                        }
                    }
                }, "getAddress");

                break;
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){
            mPtrFrameLayout.setVisibility(View.GONE);
            lvSearch.setVisibility(View.VISIBLE);
        }
    }

    private void showDialogFragment(String title, int resId, EditDialogFragment.OnDialogClickListener listener, String tag) {
        View contentView = getLayoutInflater().inflate(resId, null, false);
        EditDialogFragment dialogFragment = EditDialogFragment.getInstance(title, contentView);
        dialogFragment.setOnDialogClickListener(listener);
        dialogFragment.show(getSupportFragmentManager(), tag);
    }

    private SearchLayout.SearchListener listener = new SearchLayout.SearchListener() {
        @Override
        public void onClick(String param) {
            mPresenter.setParam(BuildingEstatePresenter.NAME, param);
        }

        @Override
        public void onLeftClick() {
        }

        @Override
        public void onRightClick() {
            mPresenter.removeHistory();
            slvHistory.setData(null, null);
        }
    };

}
