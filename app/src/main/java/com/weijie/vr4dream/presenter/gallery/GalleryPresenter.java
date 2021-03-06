package com.weijie.vr4dream.presenter.gallery;

import android.content.Context;

import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.model.Gallery;
import com.weijie.vr4dream.presenter.BaseFragmentPresenter;
import com.weijie.vr4dream.ui.view.gallery.IGalleryView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;
import com.weijie.vr4dream.utils.ErrorUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 图库
 * 作者：guoweijie on 16/12/16 10:52
 * 邮箱：529844698@qq.com
 */
public class GalleryPresenter extends BaseFragmentPresenter<IGalleryView> implements IGalleryPresenter {

    private HashMap<String, Object> params;

    public static final String MODE = "mode";
    public static final String BUILDTYPE = "buildType";
    public static final String AREA = "area";
    public static final String BUDGET = "budget";
    public static final String STYLE = "style";
    public static final String HOTNESS = "hotness";

    private static final int PAGE_SIZE = 3;
    private int page = 0;

    public GalleryPresenter(Context context, IGalleryView view) {
        super(context, view);
        params = new HashMap<>();
        params.put(MODE, true);
        params.put(HOTNESS, "-createdAt");
    }

    @Override
    public void setParam(String key, Object value) {
        params.put(key, value);
        mView.hideMenu();
        mView.autoRefresh();
    }

    @Override
    public void removeParam(String key) {
        params.remove(key);
        mView.hideMenu();
        mView.autoRefresh();
    }

    @Override
    public void loadMore(final boolean refresh) {
        if(refresh) {
            mView.setLoadStatus(false);
            page = 0;
        }
        BmobQuery<Gallery> query = new BmobQuery<>();
        Iterator entries = params.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String)entry.getKey();
            Object value = entry.getValue();
            if(!key.equals(HOTNESS)) {
                query.addWhereEqualTo(key, value);
            }
        }
        query.include("buildingEstate.name").setLimit(PAGE_SIZE).setSkip(page*PAGE_SIZE).order((String)params.get(HOTNESS))
                .findObjects(new FindListener<Gallery>() {
                    @Override
                    public void done(List<Gallery> object, BmobException e) {
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
    public void clickBuildingEstate() {
        ActivitySkipHelper.toBuildingEstateActivity(mContext);
    }
}
