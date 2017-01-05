package com.weijie.vr4dream.presenter;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.config.AppConstant;
import com.weijie.vr4dream.config.AppSetting;
import com.weijie.vr4dream.model.BuildingEstate;
import com.weijie.vr4dream.ui.view.IBuildingEstateView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;
import com.weijie.vr4dream.utils.ErrorUtil;
import com.weijie.vr4dream.utils.JSONUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 楼盘列表
 * 作者：guoweijie on 17/1/4 09:58
 * 邮箱：529844698@qq.com
 */
public class BuildingEstatePresenter extends BaseActivityPresenter<IBuildingEstateView> implements IBuildingEstatePresenter{

    private HashMap<String, Object> params;

    public static final String NAME = "name";
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String DISTRICT = "district";

    private TypeToken<ArrayList<String>> typeToken = new TypeToken<ArrayList<String>>(){};
    private static final int PAGE_SIZE = 10;
    private int page = 0;

    public BuildingEstatePresenter(Context context, IBuildingEstateView view) {
        super(context, view);
        params = new HashMap<>();
    }

    @Override
    public void setParam(String key, String param) {
        params.put(key, param);
        mView.hideSearchLayout();
        mView.hideSoftInputFromWindow();
        mView.autoRefresh();
    }

    @Override
    public void removeParam(String key) {
        params.remove(key);
        mView.hideSearchLayout();
        mView.hideSoftInputFromWindow();
        mView.autoRefresh();
    }

    @Override
    public void loadMore(final boolean refresh) {
        if(refresh) {
            mView.setLoadStatus(false);
            page = 0;
        }
        BmobQuery<BuildingEstate> query = new BmobQuery<>();
        if(params.containsKey(NAME)) {
            query.addWhereEqualTo(NAME, params.get(NAME));
        }
        if(params.containsKey(DISTRICT)) {
            query.addWhereEqualTo(DISTRICT, params.get(DISTRICT));
        } else if(params.containsKey(CITY)) {
            query.addWhereEqualTo(CITY, params.get(CITY));
        } else if(params.containsKey(PROVINCE)) {
            query.addWhereEqualTo(PROVINCE, params.get(PROVINCE));
        }
        query.setLimit(PAGE_SIZE).setSkip(page * PAGE_SIZE).order("-createdAt")
                .findObjects(new FindListener<BuildingEstate>() {
                    @Override
                    public void done(List<BuildingEstate> object, BmobException e) {
                        if (e == null) {
                            if (!refresh) {
                                //加载更多
                                if (!object.isEmpty()) {
                                    mView.loadMoreView(object);
                                    page++;
                                    mView.setLoadStatus(false);
                                } else {
                                    mView.refreshComplete();
                                    mView.showTips("没有更多数据");
                                }
                            } else {
                                //刷新
                                if (!object.isEmpty()) {
                                    mView.hideTip();
                                    mView.refreshView(object);
                                    page++;
                                } else {
                                    mView.refreshComplete();
                                    mView.showTip(LoadTipAdapter.ViewStatus.STATUS_EMPTY);
                                }
                            }
                        } else {
                            mView.refreshComplete();
                            mView.showTips(ErrorUtil.getErrorMsg(e));
                        }
                    }
                });
    }

    @Override
    public void setHistory(String param) {
        if(!param.trim().equals("")) {
            ArrayList<String> list;
            String sHistory = AppSetting.getInstance().getString(AppConstant.HISTORYS);
            if(sHistory!=null && sHistory.length()>0) {
                list = JSONUtils.toList(sHistory, typeToken);
            } else {
                list = new ArrayList<>();
            }
            if(!list.contains(param)) {
                list.add(param);
                AppSetting.getInstance().save(AppConstant.HISTORYS, JSONUtils.toJson(list).toString());
                mView.setHistory(list);
            }
            setParam(NAME, param);
        } else {
            removeParam(NAME);
        }
    }

    @Override
    public void removeHistory() {
        AppSetting.getInstance().removeAppSettingByKey(AppConstant.HISTORYS);
    }

    @Override
    public void onItemClick(BuildingEstate estate) {
        ActivitySkipHelper.toGalleryListActivity(mContext, estate);
    }
}
