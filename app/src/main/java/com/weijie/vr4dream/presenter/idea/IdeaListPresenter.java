package com.weijie.vr4dream.presenter.idea;

import android.content.Context;

import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.model.Idea;
import com.weijie.vr4dream.presenter.BaseFragmentPresenter;
import com.weijie.vr4dream.ui.view.idea.IIdeaListView;
import com.weijie.vr4dream.utils.ErrorUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 灵感列表
 * 作者：guoweijie on 16/12/20 15:01
 * 邮箱：529844698@qq.com
 */
public class IdeaListPresenter extends BaseFragmentPresenter<IIdeaListView> implements IIdeaListPresenter {

    private static final int PAGE_SIZE = 3;
    private int page = 0;

    public IdeaListPresenter(Context context, IIdeaListView view) {
        super(context, view);
    }

    @Override
    public void loadMore(final boolean refresh, int type) {
        if(refresh) {
            mView.setLoadStatus(false);
            page = 0;
        }
        BmobQuery<Idea> query = new BmobQuery<>();
        query.setLimit(PAGE_SIZE).setSkip(page*PAGE_SIZE).addWhereEqualTo("type", type).order("-createdAt")
                .findObjects(new FindListener<Idea>() {
                    @Override
                    public void done(List<Idea> object, BmobException e) {
                        if (e == null) {
                            if(!refresh) {
                                //加载更多
                                if(!object.isEmpty()) {
                                    mView.loadMoreView(object);
                                    page ++ ;
                                    mView.setLoadStatus(false);
                                } else {
                                    mView.refreshComplete();
                                    mView.showTips("没有更多数据");
                                }
                            } else {
                                //刷新
                                if(!object.isEmpty()) {
                                    mView.hideTip();
                                    mView.refreshView(object);
                                    page ++ ;
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
