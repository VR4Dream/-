package com.weijie.vr4dream.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.weijie.vr4dream.model.BuildingEstate;
import com.weijie.vr4dream.model.Gallery;
import com.weijie.vr4dream.model.Idea;
import com.weijie.vr4dream.ui.activity.BuildingEstateActivity;
import com.weijie.vr4dream.ui.activity.CommentActivity;
import com.weijie.vr4dream.ui.activity.CommentListActivity;
import com.weijie.vr4dream.ui.activity.MainActivity;
import com.weijie.vr4dream.ui.activity.gallery.GalleryDetailActivity;
import com.weijie.vr4dream.ui.activity.gallery.GalleryListActiity;
import com.weijie.vr4dream.ui.activity.idea.IdeaDetailActivity;
import com.weijie.vr4dream.ui.activity.user.InfoActivity;
import com.weijie.vr4dream.ui.activity.user.LoginActivity;
import com.weijie.vr4dream.ui.activity.user.RegisterActivity;
import com.weijie.vr4dream.ui.activity.user.SettingActivity;
import com.weijie.vr4dream.ui.activity.user.SuggestActivity;

/**
 * activity跳转帮助类
 * 作者：guoweijie on 16/12/15 20:56
 * 邮箱：529844698@qq.com
 */
public class ActivitySkipHelper {

    /**
     * 跳转到主页面
     *
     * @param context 上下文
     */
    public static void toMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到登录页面
     *
     * @param context 上下文
     */
    public static void toLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到第三方注册绑定页面
     *
     * @param context 上下文
     * @param bundle
     */
    public static void toRegisterActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void toRegisterActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到文章详情页
     *
     * @param context 上下文
     */
    public static void toIdeaDetailActivity(Context context, Idea idea) {
        Intent intent = new Intent(context, IdeaDetailActivity.class);
        intent.putExtra("data", idea);
        context.startActivity(intent);
    }

    /**
     * 跳转到VR详情页
     *
     * @param context 上下文
     */
    public static void toGalleryDetailActivity(Context context, Gallery gallery) {
        Intent intent = new Intent(context, GalleryDetailActivity.class);
        intent.putExtra("data", gallery);
        context.startActivity(intent);
    }

    /**
     * 跳转到设置
     *
     * @param context 上下文
     */
    public static void toSettingActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到个人信息
     *
     * @param context 上下文
     */
    public static void toInfoActivity(Context context) {
        Intent intent = new Intent(context, InfoActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到意见反馈
     *
     * @param context 上下文
     */
    public static void toSuggestActivity(Context context) {
        Intent intent = new Intent(context, SuggestActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到评论页面
     * @param context
     * @param id 文章id
     */
    public static void toCommentActivity(Context context, String id, int tag) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    /**
     * 跳转到评论列表页面
     * @param context
     * @param id 文章id
     */
    public static void toCommentListActivity(Context context, String id, int tag) {
        Intent intent = new Intent(context, CommentListActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    /**
     * 跳转到楼盘列表
     * @param context
     */
    public static void toBuildingEstateActivity(Context context) {
        Intent intent = new Intent(context, BuildingEstateActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到图库列表
     * @param context
     * @param estate
     */
    public static void toGalleryListActivity(Context context, BuildingEstate estate) {
        Intent intent = new Intent(context, GalleryListActiity.class);
        intent.putExtra("data", estate);
        context.startActivity(intent);
    }

}
