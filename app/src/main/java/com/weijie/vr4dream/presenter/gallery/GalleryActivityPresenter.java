package com.weijie.vr4dream.presenter.gallery;

import android.content.Context;

import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.model.BuildingEstate;
import com.weijie.vr4dream.model.Gallery;
import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.ui.view.gallery.IGalleryActivityView;
import com.weijie.vr4dream.utils.ErrorUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 图库
 * 作者：guoweijie on 16/12/16 10:52
 * 邮箱：529844698@qq.com
 */
public class GalleryActivityPresenter extends BaseActivityPresenter<IGalleryActivityView> implements IGalleryActivityPresenter {

    private static final int PAGE_SIZE = 3;
    private int page = 0;

    public GalleryActivityPresenter(Context context, IGalleryActivityView view) {
        super(context, view);
    }

    @Override
    public void loadMore(final boolean refresh, String id) {
        if(refresh) {
            mView.setLoadStatus(false);
            page = 0;
        }
        BuildingEstate estate = new BuildingEstate();
        estate.setObjectId(id);
        BmobQuery<Gallery> query = new BmobQuery<>();
        query.include("buildingEstate.name").addWhereEqualTo("buildingEstate", estate).setLimit(PAGE_SIZE).setSkip(page*PAGE_SIZE).order("-createdAt")
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

}
