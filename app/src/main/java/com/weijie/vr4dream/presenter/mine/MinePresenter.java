package com.weijie.vr4dream.presenter.mine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.model.VRUser;
import com.weijie.vr4dream.presenter.BaseFragmentPresenter;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.ui.view.mine.IMineView;
import com.weijie.vr4dream.utils.ActivitySkipHelper;
import com.weijie.vr4dream.utils.ErrorUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 我的页面
 * 作者：guoweijie on 16/12/16 10:52
 * 邮箱：529844698@qq.com
 */
public class MinePresenter extends BaseFragmentPresenter<IMineView> implements IMinePresenter {

    private VRUser user;
    //private Bitmap bm;

    public MinePresenter(Context context, IMineView view) {
        super(context, view);
    }

    @Override
    public void initInfo() {
        user = BmobUser.getCurrentUser(VRUser.class);
        if(user!=null) {
            mView.setUserName(user.getUsername()==null ? user.getMobilePhoneNumber() : user.getUsername());
            initFavouriteNum();
        }
    }

    @Override
    public void clickHeadIcon() {
        if(BmobUser.getCurrentUser(VRUser.class)==null) {
            ActivitySkipHelper.toLoginActivity(mContext);
        }
    }

    @Override
    public void clickPersonalData() {
        if(BmobUser.getCurrentUser(VRUser.class)==null) {
            mView.showLoginDialog("亲，您还没有登录！");
        } else {
            ActivitySkipHelper.toInfoActivity(mContext);
        }
    }

    @Override
    public void clickGalleryFavourite() {
        if(BmobUser.getCurrentUser(VRUser.class)==null) {
            mView.showLoginDialog("亲，您还没有登录！");
        } else {
            ActivitySkipHelper.toGalleryListActivity(mContext);
        }
    }

    @Override
    public void clickIdeaFavourite() {
        if(BmobUser.getCurrentUser(VRUser.class)==null) {
            mView.showLoginDialog("亲，您还没有登录！");
        } else {
            ActivitySkipHelper.toIdeaListActivity(mContext);
        }
    }

    @Override
    public void clickSetting() {
        ActivitySkipHelper.toSettingActivity(mContext);
    }

    @Override
    public void clickSuggest() {
        ActivitySkipHelper.toSuggestActivity(mContext);
    }

    @Override
    public void clickShare() {
        mView.showShareDialog();
    }

    @Override
    public void loginStateChange(LoginStateChangeEvent event) {
        if(event.isLogin()) {
            user = BmobUser.getCurrentUser(VRUser.class);
            mView.setUserName(user.getUsername()==null ? user.getMobilePhoneNumber() : user.getUsername());
            initFavouriteNum();
        } else {
            mView.setUserName(mContext.getString(R.string.text_user_name));
            mView.setIdeaNum(0);
            mView.setGalleryNum(0);
        }
    }

    private void initFavouriteNum() {
        try {
            String cloudCodeName = "getFavouriteNum";
            JSONObject params = new JSONObject();
            params.put("owner",user.getObjectId());
            AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
            cloudCode.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
                @Override
                public void done(Object o, BmobException e) {
                    if(e==null) {
                        try {
                            JSONObject json = new JSONObject(o.toString());
                            mView.setIdeaNum(json.optInt("idea"));
                            mView.setGalleryNum(json.optInt("gallery"));
                        } catch (JSONException jse) {
                            jse.printStackTrace();
                        }
                    } else {
                        mView.showTips(ErrorUtil.getErrorMsg(e));
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public void shareBlog() {
        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
//        if(bm==null) {
//            bm = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.share_pic);
//        }
//        sp.setImageData(bm);
        sp.setImageUrl("http://bmob-cdn-8496.b0.upaiyun.com/2017/02/13/b18b705c40b7eefb80c382fbb3df4c8c.png");
        sp.setShareType(Platform.SHARE_IMAGE);
        Platform platform = ShareSDK.getPlatform (SinaWeibo.NAME);
        platform.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        platform.share(sp);
    }

    @Override
    public void shareSpace() {
        QZone.ShareParams sp = new QZone.ShareParams();
//        if(bm==null) {
//            bm = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.share_pic);
//        }
//        sp.setImageData(bm);
        sp.setImageUrl("http://bmob-cdn-8496.b0.upaiyun.com/2017/02/13/b18b705c40b7eefb80c382fbb3df4c8c.png");
        sp.setShareType(Platform.SHARE_IMAGE);
        Platform platform = ShareSDK.getPlatform (QZone.NAME);
        platform.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        platform.share(sp);
    }

    @Override
    public void shareWeChat() {
        Wechat.ShareParams sp = new Wechat.ShareParams();
//        if(bm==null) {
//            bm = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.share_pic);
//        }
//        sp.setImageData(bm);
        sp.setImageUrl("http://bmob-cdn-8496.b0.upaiyun.com/2017/02/13/b18b705c40b7eefb80c382fbb3df4c8c.png");
        sp.setShareType(Platform.SHARE_IMAGE);
        Platform platform = ShareSDK.getPlatform (Wechat.NAME);
        platform.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        platform.share(sp);
    }

    @Override
    public void shareWechatMoments() {
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
//        if(bm==null) {
//            bm = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.share_pic);
//        }
//        sp.setImageData(bm);
        sp.setImageUrl("http://bmob-cdn-8496.b0.upaiyun.com/2017/02/13/b18b705c40b7eefb80c382fbb3df4c8c.png");
        sp.setShareType(Platform.SHARE_IMAGE);
        Platform platform = ShareSDK.getPlatform (WechatMoments.NAME);
        platform.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        platform.share(sp);
    }

    @Override
    public void shareQQ() {
        QQ.ShareParams sp = new QQ.ShareParams();
//        if(bm==null) {
//            bm = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.share_pic);
//        }
//        sp.setImageData(bm);
        sp.setImageUrl("http://bmob-cdn-8496.b0.upaiyun.com/2017/02/13/b18b705c40b7eefb80c382fbb3df4c8c.png");
        sp.setShareType(Platform.SHARE_IMAGE);
        Platform platform = ShareSDK.getPlatform (QQ.NAME);
        platform.setPlatformActionListener(listener); // 设置分享事件回调
        // 执行图文分享
        platform.share(sp);
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
