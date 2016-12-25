package com.weijie.vr4dream.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.BaseFragmentPresenter;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.IBaseView;
import com.weijie.vr4dream.ui.widget.MultiStatusView;
import com.weijie.vr4dream.ui.widget.ProgressDialog;
import com.weijie.vr4dream.ui.widget.VRMultiStatusView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * fragment基类
 * 作者：guoweijie on 16/12/16 09:27
 * 邮箱：529844698@qq.com
 */
public abstract class BaseFragment<P extends BaseFragmentPresenter> extends Fragment implements IBaseView {
    protected View mContentView;
    protected P mPresenter;
    protected Context mContext;
    protected AlertDialog.Builder mAlertDialogBuilder;
    protected ProgressDialog mProgressDialog;

    @Nullable
    @Bind(R.id.statusView)
    protected VRMultiStatusView mStatusView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutResId(), container, false);
        mContext = getActivity();
        ButterKnife.bind(this, mContentView);
        if (mPresenter == null)
            initPresenter();
        checkPresenterIsNull();
        initialize();
        mPresenter.onCreateView(savedInstanceState);
        return mContentView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mPresenter != null)
            mPresenter.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null)
            mPresenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null)
            mPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null)
            mPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (mPresenter != null)
            mPresenter.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null)
            mPresenter.onDetach();
    }

    @Override
    public void finish() {
        if (getActivity() != null)
            getActivity().finish();
    }

    /**
     * 获取布局的resId
     */
    @CheckResult
    protected abstract int getLayoutResId();

    /**
     * 初始化presenter
     */
    protected abstract void initPresenter();

    /**
     * 初始化
     */
    protected abstract void initialize();

    /**
     * 检查mPresenter是否为空
     */
    protected void checkPresenterIsNull() {
        if (mPresenter == null) {
            throw new IllegalStateException("please init mPresenter at initPresenter() method...");
        }
    }

    /**
     * 显示内容view
     */
    @Override
    public void showContentView() {
        if (mStatusView != null) {
            mStatusView.setViewStatus(MultiStatusView.ViewStatus.STATUS_SUCCESS);
        }
    }

    /**
     * 显示空view
     */
    @Override
    public void showEmptyView() {
        if (mStatusView != null) {
            mStatusView.setViewStatus(MultiStatusView.ViewStatus.STATUS_EMPTY);
        }
    }

    /**
     * 显示错误页面
     */
    @Override
    public void showErrView() {
        if (mStatusView != null) {
            mStatusView.setViewStatus(MultiStatusView.ViewStatus.STATUS_ERR);
        }
    }

    /**
     * 设置toolbar标题
     *
     * @param title      标题
     * @param isShowHome 是否显示home键
     */
    @SuppressWarnings("unused")
    @Override
    public void setToolbarTitle(@NonNull String title, boolean isShowHome) {
        ((BaseActivity) getActivity()).setToolbarTitle(title, isShowHome);
    }

    /**
     * 设置toolbar标题
     *
     * @param title      标题
     * @param isShowHome 是否显示home键
     */
    @SuppressWarnings("unused")
    @Override
    public void setToolbarTitle(@StringRes int title, boolean isShowHome) {
        ((BaseActivity) getActivity()).setToolbarTitle(title, isShowHome);
    }

    /**
     * 显示信息提示条
     *
     * @param tipsResId 提示信息字符串的资源id
     */
    @SuppressWarnings("unused")
    @Override
    public void showTips(@StringRes int tipsResId) {
        Toast.makeText(mContext, tipsResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示信息提示条
     *
     * @param tips 提示信息字符串
     */
    @SuppressWarnings("unused")
    @Override
    public void showTips(@NonNull String tips) {
        Toast.makeText(mContext, tips, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示进度条对话框
     */
    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 取消进度条对话框
     */
    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    /**
     * 显示弹出框
     *
     * @param title                       对话框标题
     * @param message                     对话框的内容
     * @param positiveText                确定按钮的文字
     * @param positiveButtonClickListener 确定按钮的点击事件
     * @param negativeText                取消按钮的文字
     * @param negativeButtonClickListener 取消按钮的点击事件
     */
    @Override
    public void showAlertDialog(@NonNull String title, @NonNull String message, @NonNull String positiveText, DialogInterface.OnClickListener positiveButtonClickListener, @NonNull String negativeText, DialogInterface.OnClickListener negativeButtonClickListener) {
        mAlertDialogBuilder = new AlertDialog.Builder(mContext);
        mAlertDialogBuilder.setTitle(title);
        mAlertDialogBuilder.setMessage(message);
        mAlertDialogBuilder.setPositiveButton(positiveText, positiveButtonClickListener);
        mAlertDialogBuilder.setNegativeButton(negativeText, negativeButtonClickListener);
        mAlertDialogBuilder.show();
    }

    /**
     * 显示弹出框
     *
     * @param title                       对话框标题
     * @param message                     对话框的内容
     * @param positiveText                确定按钮的文字
     * @param positiveButtonClickListener 确定按钮的点击事件
     * @param negativeText                取消按钮的文字
     * @param negativeButtonClickListener 取消按钮的点击事件
     */
    @Override
    public void showAlertDialog(int title, int message, int positiveText, DialogInterface.OnClickListener positiveButtonClickListener, int negativeText, DialogInterface.OnClickListener negativeButtonClickListener) {
        mAlertDialogBuilder = new AlertDialog.Builder(mContext);
        mAlertDialogBuilder.setTitle(title);
        mAlertDialogBuilder.setMessage(message);
        mAlertDialogBuilder.setPositiveButton(positiveText, positiveButtonClickListener);
        mAlertDialogBuilder.setNegativeButton(negativeText, negativeButtonClickListener);
        mAlertDialogBuilder.show();
    }

    /**
     * 显示弹出框
     *
     * @param title                       对话框标题
     * @param message                     对话框的内容
     * @param positiveText                确定按钮的文字
     * @param positiveButtonClickListener 确定按钮的点击事件
     * @param negativeText                取消按钮的文字
     * @param negativeButtonClickListener 取消按钮的点击事件
     */
    @Override
    public void showAlertDialog(int title, String message, int positiveText, DialogInterface.OnClickListener positiveButtonClickListener, int negativeText, DialogInterface.OnClickListener negativeButtonClickListener) {
        mAlertDialogBuilder = new AlertDialog.Builder(mContext);
        mAlertDialogBuilder.setTitle(title);
        mAlertDialogBuilder.setMessage(message);
        mAlertDialogBuilder.setPositiveButton(positiveText, positiveButtonClickListener);
        mAlertDialogBuilder.setNegativeButton(negativeText, negativeButtonClickListener);
        mAlertDialogBuilder.show();
    }

}
