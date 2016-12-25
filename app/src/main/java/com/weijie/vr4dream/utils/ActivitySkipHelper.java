package com.weijie.vr4dream.utils;

import android.content.Context;
import android.content.Intent;

import com.weijie.vr4dream.ui.activity.MainActivity;
import com.weijie.vr4dream.ui.activity.idea.IdeaDetailActivity;
import com.weijie.vr4dream.ui.activity.user.LoginActivity;
import com.weijie.vr4dream.ui.activity.user.SettingActivity;

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
     * 跳转到文章详情页
     *
     * @param context 上下文
     */
    public static void toIdeaDetailActivity(Context context) {
        Intent intent = new Intent(context, IdeaDetailActivity.class);
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

}
