package com.weijie.vr4dream.ui.view;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * 所有view的基类
 * 作者：guoweijie on 16/12/15 17:20
 * 邮箱：529844698@qq.com
 */
public interface IBaseView {

    /**
     * 显示错误
     */
    void showErrView();

    /**
     * 显示空的view
     */
    void showEmptyView();

    /**
     * 显示内容view
     */
    void showContentView();

    /**
     * 设置toolbar标题
     *
     * @param title      标题
     * @param isShowHome 是否显示home键
     */
    void setToolbarTitle(@NonNull String title, boolean isShowHome);

    /**
     * 设置toolbar标题
     *
     * @param title      标题
     * @param isShowHome 是否显示home键
     */
    void setToolbarTitle(@StringRes int title, boolean isShowHome);

    /**
     * 显示信息提示条
     *
     * @param tipsResId 提示信息字符串的资源id
     */
    void showTips(@StringRes int tipsResId);

    /**
     * 显示信息提示条
     *
     * @param tips 提示信息字符串
     */
    void showTips(@NonNull String tips);

    /**
     * 显示进度条对话框
     */
    void showProgressDialog();

    /**
     * 取消进度条对话框
     */
    void dismissProgressDialog();

    /**
     * 结束当前界面
     */
    void finish();

    /**
     * 显示对话框
     *
     * @param title                       对话框标题
     * @param message                     对话框的内容
     * @param positiveText                确定按钮的文字
     * @param positiveButtonClickListener 确定按钮的点击事件
     * @param negativeText                取消按钮的文字
     * @param negativeButtonClickListener 取消按钮的点击事件
     */
    void showAlertDialog(String title,
                         String message,
                         String positiveText,
                         DialogInterface.OnClickListener positiveButtonClickListener,
                         String negativeText,
                         DialogInterface.OnClickListener negativeButtonClickListener);


    /**
     * 显示对话框
     *
     * @param title                       对话框标题
     * @param message                     对话框的内容
     * @param positiveText                确定按钮的文字
     * @param positiveButtonClickListener 确定按钮的点击事件
     * @param negativeText                取消按钮的文字
     * @param negativeButtonClickListener 取消按钮的点击事件
     */
    void showAlertDialog(int title,
                         int message,
                         int positiveText,
                         DialogInterface.OnClickListener positiveButtonClickListener,
                         int negativeText,
                         DialogInterface.OnClickListener negativeButtonClickListener);

    /**
     * 显示对话框
     *
     * @param title                       对话框标题
     * @param message                     对话框的内容
     * @param positiveText                确定按钮的文字
     * @param positiveButtonClickListener 确定按钮的点击事件
     * @param negativeText                取消按钮的文字
     * @param negativeButtonClickListener 取消按钮的点击事件
     */
    void showAlertDialog(int title,
                         String message,
                         int positiveText,
                         DialogInterface.OnClickListener positiveButtonClickListener,
                         int negativeText,
                         DialogInterface.OnClickListener negativeButtonClickListener);

}
