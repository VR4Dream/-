package com.weijie.vr4dream.presenter.gallery;

import android.content.Context;

import com.weijie.vr4dream.adapter.LoadTipAdapter;
import com.weijie.vr4dream.model.Comment;
import com.weijie.vr4dream.model.Gallery;
import com.weijie.vr4dream.model.GalleryComment;
import com.weijie.vr4dream.presenter.CommentListPresenter;
import com.weijie.vr4dream.ui.view.ICommentListView;
import com.weijie.vr4dream.utils.ErrorUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 评论列表
 * 作者：guoweijie on 16/12/31 09:50
 * 邮箱：529844698@qq.com
 */
public class GalleryCommentListPresenter extends CommentListPresenter {

    public GalleryCommentListPresenter(Context context, ICommentListView view) {
        super(context, view);
    }

    @Override
    public void loadMore(final boolean refresh, String id) {
        if(refresh) {
            mView.setLoadStatus(false);
            page = 0;
            more = 0;
        }
        BmobQuery<GalleryComment> query = new BmobQuery<>();
        Gallery gallery = new Gallery();
        gallery.setObjectId(id);
        query.addWhereEqualTo("gallery",gallery).setLimit(PAGE_SIZE).setSkip(page*PAGE_SIZE+more).include("author.username,author.icon").order("-createdAt")
                .findObjects(new FindListener<GalleryComment>() {
                    @Override
                    public void done(List<GalleryComment> object, BmobException e) {
                        if (e == null) {
                            if(!refresh) {
                                //加载更多
                                if(!object.isEmpty()) {
                                    List<Comment> comments = new ArrayList<Comment>();
                                    for(GalleryComment comment:object) {
                                        comments.add(new Comment(comment.getAuthor(), comment.getContent(), comment.getCreatedAt()));
                                    }
                                    mView.loadMoreView(comments);
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
                                    List<Comment> comments = new ArrayList<Comment>();
                                    for(GalleryComment comment:object) {
                                        comments.add(new Comment(comment.getAuthor(), comment.getContent(), comment.getCreatedAt()));
                                    }
                                    mView.refreshView(comments);
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
