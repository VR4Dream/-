package com.weijie.vr4dream.presenter.gallery;

import android.content.Context;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.model.Gallery;
import com.weijie.vr4dream.model.VRUser;
import com.weijie.vr4dream.presenter.BaseActivityPresenter;
import com.weijie.vr4dream.rxEvent.FavouriteChangeEvent;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.ui.view.gallery.IGalleryDetailView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;
import com.weijie.vr4dream.utils.ErrorUtil;

import org.json.JSONObject;

import java.util.HashMap;

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

/**
 * 作者：guoweijie on 17/1/3 11:08
 * 邮箱：529844698@qq.com
 */
public class GalleryDetailPresenter extends BaseActivityPresenter<IGalleryDetailView> implements IGalleryDetailPresenter {

    private VRUser user;
    private Gallery gallery;
    private boolean isLikes;
    private boolean isFavourite;

    public GalleryDetailPresenter(Context context, IGalleryDetailView view) {
        super(context, view);
    }

    @Override
    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
        user = VRUser.getCurrentUser(VRUser.class);
        mView.loadWeb(gallery.getLink());
        checkedInitStatus();
    }

    @Override
    public void checkedInitStatus() {
        if(user == null)
            return;
        else {
            try {
                String cloudCodeName = "isGalleryLikes";
                JSONObject params = new JSONObject();
                params.put("owner",user.getObjectId());
                params.put("gallery",gallery.getObjectId());
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
                String cloudCodeName = "isGalleryFavourite";
                JSONObject params = new JSONObject();
                params.put("owner",user.getObjectId());
                params.put("gallery",gallery.getObjectId());
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
            Gallery data = new Gallery();
            data.setObjectId(gallery.getObjectId());
            final BmobRelation relation = new BmobRelation();
            final int likesNum = data.getLikesNum();
            if(!isLikes) {
                relation.add(user);
                data.setLikesNum(likesNum+1);
            } else {
                relation.remove(user);
                data.setLikesNum(likesNum-1);
            }
            data.setLikes(relation);
            data.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null) {
                        isLikes = !isLikes;
                        mView.setLikesStatus(isLikes);
                        gallery.setLikes(relation);
                        if(isLikes) {
                            gallery.setLikesNum(likesNum+1);
                        } else {
                            gallery.setLikesNum(likesNum-1);
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
                String cloudCodeName = "add_remove_GalleryFavourite";
                JSONObject params = new JSONObject();
                params.put("owner",user.getObjectId());
                params.put("gallery",gallery.getObjectId());
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
                                changeFavouriteNum(true, true);
                            } else if (msg.equals("取消收藏")){
                                isFavourite = false;
                                changeFavouriteNum(true, false);
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
    public void loginStateChange(LoginStateChangeEvent event) {
        if(event.isLogin()) {
            user = VRUser.getCurrentUser(VRUser.class);
            checkedInitStatus();
        }
    }

    @Override
    public void clickComment() {
        if(user==null) {
            mView.showLoginDialog("亲，您还没有登录！");
        } else {
            ActivitySkipHelper.toCommentListActivity(mContext, gallery.getObjectId(), 0);
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
        sp.setTitle(gallery.getTitle());
        sp.setTitleUrl(gallery.getLink()); // 标题的超链接
        sp.setImageUrl(gallery.getCover());
        sp.setSiteUrl("http://www.h7sc.com");
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        qq.share(sp);
    }

    @Override
    public void shareSpace() {
        QZone.ShareParams sp = new QZone.ShareParams();
        sp.setTitle(gallery.getTitle());
        sp.setTitleUrl(gallery.getLink()); // 标题的超链接
        sp.setImageUrl(gallery.getCover());
        sp.setSiteUrl("http://www.h7sc.com");
        Platform qzone = ShareSDK.getPlatform (QZone.NAME);
        qzone.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp);
    }

    @Override
    public void shareBlog() {
        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setTitle(gallery.getTitle());
        sp.setTitleUrl(gallery.getLink()); // 标题的超链接
        sp.setImageUrl(gallery.getCover());
        sp.setSiteUrl("http://www.h7sc.com");
        Platform sina = ShareSDK.getPlatform (SinaWeibo.NAME);
        sina.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        sina.share(sp);
    }

    @Override
    public void shareWeChat() {
        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setTitle(gallery.getTitle());
        sp.setUrl(gallery.getLink());
        sp.setImageUrl(gallery.getCover());
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Platform wechat = ShareSDK.getPlatform (Wechat.NAME);
        wechat.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        wechat.share(sp);
    }

    @Override
    public void shareWechatMoments() {
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setTitle(gallery.getTitle());
        sp.setUrl(gallery.getLink());
        sp.setImageUrl(gallery.getCover());
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
