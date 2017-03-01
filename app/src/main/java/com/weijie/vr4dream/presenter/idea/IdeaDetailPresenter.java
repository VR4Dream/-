package com.weijie.vr4dream.presenter.idea;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.weijie.vr4dream.App;
import com.weijie.vr4dream.model.Comment;
import com.weijie.vr4dream.model.Idea;
import com.weijie.vr4dream.model.IdeaComment;
import com.weijie.vr4dream.model.VRUser;
import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.rxEvent.AddCommentEvent;
import com.weijie.vr4dream.rxEvent.FavouriteChangeEvent;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.ui.view.idea.IIdeaDetailView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;
import com.weijie.vr4dream.utils.ErrorUtil;
import com.weijie.vr4dream.utils.JSONUtils;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WXTextObject;

/**
 * 文章详情
 * 作者：guoweijie on 16/12/22 10:42
 * 邮箱：529844698@qq.com
 */
public class IdeaDetailPresenter extends BaseActivityPresenter<IIdeaDetailView> implements IIdeaDeatilPresenter {

    private VRUser user;
    private Idea idea;
    private boolean isLikes;
    private boolean isFavourite;
    private List<Comment> comments;

    public IdeaDetailPresenter(Context context, IIdeaDetailView view) {
        super(context, view);
    }

    @Override
    public void setIdea(Idea idea) {
        this.idea = idea;
        user = VRUser.getCurrentUser(VRUser.class);
        mView.loadWeb(idea.getLink());
        checkedInitStatus();
        getCommentList();
    }

    @Override
    public void checkedInitStatus() {
        if(user == null)
            return;
        else {
            try {
                String cloudCodeName = "isIdeaLikes";
                JSONObject params = new JSONObject();
                params.put("owner",user.getObjectId());
                params.put("idea",idea.getObjectId());
                AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
                cloudCode.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if(e==null) {
                            isLikes = Boolean.parseBoolean(o.toString());
                            mView.setLikesStatus(isLikes);
                        } else {
                            mView.showTips(ErrorUtil.getErrorMsg(e));
                        }
                    }
                });
            } catch (Exception e) {
            }

            try {
                String cloudCodeName = "isIdeaFavourite";
                JSONObject params = new JSONObject();
                params.put("owner",user.getObjectId());
                params.put("idea",idea.getObjectId());
                AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
                cloudCode.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if(e==null) {
                            isFavourite = Boolean.parseBoolean(o.toString());
                            mView.setFavourite(isFavourite);
                        } else {
                            mView.showTips(ErrorUtil.getErrorMsg(e));
                        }
                    }
                });
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void clickLikes() {
        if(user==null) {
            mView.showLoginDialog("亲，您还没有登录！");
        } else {
            Idea mIdea = new Idea();
            mIdea.setObjectId(idea.getObjectId());
            final BmobRelation relation = new BmobRelation();
            final int likesNum = idea.getLikesNum();
            if(!isLikes) {
                relation.add(user);
                mIdea.setLikesNum(likesNum+1);
            } else {
                relation.remove(user);
                mIdea.setLikesNum(likesNum-1);
            }
            mIdea.setLikes(relation);
            mIdea.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        isLikes = !isLikes;
                        mView.setLikesStatus(isLikes);
                        idea.setLikes(relation);
                        if (isLikes) {
                            idea.setLikesNum(likesNum + 1);
                        } else {
                            idea.setLikesNum(likesNum - 1);
                        }

                    } else {
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                }
            });
        }
    }

    @Override
    public void clickFavourite() {
        if(user==null) {
            mView.showLoginDialog("亲，您还没有登录！");
        } else {
            try {
                String cloudCodeName = "add_remove_IdeaFavourite";
                JSONObject params = new JSONObject();
                params.put("owner",user.getObjectId());
                params.put("idea",idea.getObjectId());
                if (isFavourite) params.put("tag", 1);
                else params.put("tag", 0);
                AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
                cloudCode.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if(e==null) {
                            String msg = o.toString();
                            if(msg.equals("收藏成功")) {
                                isFavourite = true;
                                changeFavouriteNum(false, true);
                            } else if (msg.equals("取消收藏")){
                                isFavourite = false;
                                changeFavouriteNum(false, false);
                            }
                            mView.showTips(msg);
                            mView.setFavourite(isFavourite);
                        } else {
                            mView.showTips(ErrorUtil.getErrorMsg(e));
                        }
                    }
                });
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void clickLogin() {
        ActivitySkipHelper.toLoginActivity(mContext);
    }

    @Override
    public void clickComment() {
        if(user==null) {
            mView.showLoginDialog("亲，您还没有登录！");
        } else {
            ActivitySkipHelper.toCommentActivity(mContext, idea.getObjectId(), 1);
        }
    }

    @Override
    public void clickCheckAllComment() {
        if(user==null) {
            mView.showLoginDialog("亲，您还没有登录！");
        } else {
            ActivitySkipHelper.toCommentListActivity(mContext, idea.getObjectId(), 1);
        }
    }

    @Override
    public void loginStateChange(LoginStateChangeEvent event) {
        if(event.isLogin()) {
            user = VRUser.getCurrentUser(VRUser.class);
            checkedInitStatus();
        }
    }

    @Override
    public void addComment(AddCommentEvent event) {
        if(comments.size()<2) {
            comments.add(event.getComment());
            mView.createComment(event.getComment());
        }
    }

    @Override
    public void getCommentList() {
        try {
            String cloudCodeName = "getIdeaCommentList";
            JSONObject params = new JSONObject();
            params.put("idea",idea.getObjectId());
            AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
            cloudCode.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
                @Override
                public void done(Object o, BmobException e) {
                    if(e==null) {
                        try {
                            String msg = o.toString();
                            JSONObject json = new JSONObject(msg);
                            mView.setCommentNum(json.optInt("count"));
                            TypeToken<ArrayList<IdeaComment>> typeToken = new TypeToken<ArrayList<IdeaComment>>(){};
                            List<IdeaComment> ideas = JSONUtils.toList(json, "results", typeToken);
                            comments = new ArrayList<Comment>();
                            for(IdeaComment comment:ideas) {
                                Comment c = new Comment(comment.getAuthor(), comment.getContent(), comment.getCreatedAt());
                                comments.add(c);
                                mView.createComment(c);
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    private void changeFavouriteNum(boolean tag, boolean active) {
        App.getInstance()
                .getRxBus()
                .sendNormalEvent(new FavouriteChangeEvent(tag, active));
    }

    @Override
    public void shareQQ() {
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(idea.getTitle());
        sp.setTitleUrl(idea.getLink()); // 标题的超链接
        sp.setImageUrl(idea.getCover());
        sp.setSiteUrl("http://www.h7sc.com");
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        qq.share(sp);
    }

    @Override
    public void shareSpace() {
        QZone.ShareParams sp = new QZone.ShareParams();
        sp.setTitle(idea.getTitle());
        sp.setTitleUrl(idea.getLink()); // 标题的超链接
        sp.setImageUrl(idea.getCover());
        sp.setSiteUrl("http://www.h7sc.com");
        Platform qzone = ShareSDK.getPlatform (QZone.NAME);
        qzone.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp);
    }

    @Override
    public void shareBlog() {
        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setTitle(idea.getTitle());
        sp.setTitleUrl(idea.getLink()); // 标题的超链接
        sp.setImageUrl(idea.getCover());
        sp.setSiteUrl("http://www.h7sc.com");
        Platform sina = ShareSDK.getPlatform (SinaWeibo.NAME);
        sina.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        sina.share(sp);
    }

    @Override
    public void shareWeChat() {
        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setTitle(idea.getTitle());
        sp.setUrl(idea.getLink());
        sp.setImageUrl(idea.getCover());
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Platform wechat = ShareSDK.getPlatform (Wechat.NAME);
        wechat.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        wechat.share(sp);
    }
    //023d83c38cc8c846d31f730d3202897e

    @Override
    public void shareWechatMoments() {
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setTitle(idea.getTitle());
        sp.setUrl(idea.getLink());
        sp.setImageUrl(idea.getCover());
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Platform wechatMoments = ShareSDK.getPlatform (WechatMoments.NAME);
        wechatMoments.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        wechatMoments.share(sp);
    }

    private PlatformActionListener listener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            mView.showTips("分享成功");
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            mView.showTips("分享失败");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            mView.showTips("取消分享");
        }
    };

}
