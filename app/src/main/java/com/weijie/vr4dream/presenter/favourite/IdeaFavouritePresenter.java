package com.weijie.vr4dream.presenter.favourite;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.model.Idea;
import com.weijie.vr4dream.model.IdeaFavourite;
import com.weijie.vr4dream.model.VRUser;
import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.ui.view.idea.IIdeaListView;
import com.weijie.vr4dream.utils.ErrorUtil;
import com.weijie.vr4dream.utils.JSONUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

/**
 * 作者：guoweijie on 17/2/8 20:00
 * 邮箱：529844698@qq.com
 */
public class IdeaFavouritePresenter extends BaseActivityPresenter<IIdeaListView> implements IIdeaFavouritePresenter {

    private static final int PAGE_SIZE = 3;
    private int page = 0;
    private VRUser user;

    public IdeaFavouritePresenter(Context context, IIdeaListView view) {
        super(context, view);
        user = BmobUser.getCurrentUser(VRUser.class);
    }

    @Override
    public void loadMore(final boolean refresh) {
        if(refresh) {
            mView.setLoadStatus(false);
            page = 0;
        }
        try {
            String cloudCodeName = "getIdeaFavourite";
            JSONObject params = new JSONObject();
            params.put("owner",user.getObjectId());
            params.put("skip",PAGE_SIZE*page);
            AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
            cloudCode.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
                @Override
                public void done(Object o, BmobException e) {
                    if(e == null) {
                        try {
                            String msg = o.toString();
                            JSONObject json = new JSONObject(msg);
                            TypeToken<ArrayList<IdeaFavourite>> typeToken = new TypeToken<ArrayList<IdeaFavourite>>(){};
                            List<IdeaFavourite> favs = JSONUtils.toList(json, "results", typeToken);
                            if (!refresh) {
                                //加载更多
                                if (!favs.isEmpty()) {
                                    List<Idea> ideas = new ArrayList<Idea>();
                                    for(IdeaFavourite fav : favs) {
                                        ideas.add(fav.getIdea());
                                    }
                                    mView.loadMoreView(ideas);
                                    page++;
                                    mView.setLoadStatus(false);
                                } else {
                                    mView.refreshComplete();
                                    mView.showTips("没有更多数据");
                                }
                            } else {
                                //刷新
                                if (!favs.isEmpty()) {
                                    mView.hideTip();
                                    List<Idea> ideas = new ArrayList<Idea>();
                                    for(IdeaFavourite fav : favs) {
                                        ideas.add(fav.getIdea());
                                    }
                                    mView.refreshView(ideas);
                                    page++;
                                } else {
                                    mView.refreshComplete();
                                    mView.showTip(LoadTipAdapter.ViewStatus.STATUS_EMPTY);
                                }
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        mView.refreshComplete();
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                }
            });
        } catch (Exception e) {
        }
    }
}
