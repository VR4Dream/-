package com.weijie.vr4dream.ui.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.weijie.vr4dream.R;

/**
 * 弹出框
 * 作者：guoweijie on 16/12/26 11:32
 * 邮箱：529844698@qq.com
 */
public class AlertDialogFragment extends DialogFragment {
    private static AlertDialogFragment fragment;

    private static String title;
    private static String content;
    private static String positiveText;
    private static String negativeText;
    private static boolean negativeButtonVisible = true;

    private OnDialogClickListener onDialogClickListener;

    public static AlertDialogFragment getInstance(String content) {
        AlertDialogFragment.content = content;
        if (fragment == null)
            fragment = new AlertDialogFragment();
        return fragment;
    }

    public static AlertDialogFragment getInstance(String content, boolean negativeButtonVisible) {
        AlertDialogFragment.content = content;
        AlertDialogFragment.negativeButtonVisible = negativeButtonVisible;
        if (fragment == null)
            fragment = new AlertDialogFragment();
        return fragment;
    }

    public static AlertDialogFragment getInstance(String title, String content) {
        AlertDialogFragment.title = title;
        AlertDialogFragment.content = content;
        if (fragment == null)
            fragment = new AlertDialogFragment();
        return fragment;
    }

    public static AlertDialogFragment getInstance(String title, String content, boolean negativeButtonVisible) {
        AlertDialogFragment.title = title;
        AlertDialogFragment.content = content;
        AlertDialogFragment.negativeButtonVisible = negativeButtonVisible;
        if (fragment == null)
            fragment = new AlertDialogFragment();
        return fragment;
    }

    public static AlertDialogFragment getInstance(String title, String content, String positiveText, String negativeText) {
        AlertDialogFragment.title = title;
        AlertDialogFragment.content = content;
        AlertDialogFragment.positiveText = positiveText;
        AlertDialogFragment.negativeText = negativeText;
        if (fragment == null)
            fragment = new AlertDialogFragment();
        return fragment;
    }

    public static AlertDialogFragment getInstance(String title, String content, String positiveText, String negativeText, boolean negativeButtonVisible) {
        AlertDialogFragment.title = title;
        AlertDialogFragment.content = content;
        AlertDialogFragment.positiveText = positiveText;
        AlertDialogFragment.negativeText = negativeText;
        AlertDialogFragment.negativeButtonVisible = negativeButtonVisible;
        if (fragment == null)
            fragment = new AlertDialogFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View contentView = inflater.inflate(R.layout.fragment_dialog, container, false);
        initView(contentView);
        return contentView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View contentView) {
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tvTitle);
        if (!TextUtils.isEmpty(title))
            tvTitle.setText(title);

        TextView tvContent = (TextView) contentView.findViewById(R.id.tvContent);
        if (!TextUtils.isEmpty(content))
            tvContent.setText(content);

        TextView tvEnsure = (TextView) contentView.findViewById(R.id.btEnsure);
        if (!TextUtils.isEmpty(positiveText))
            tvEnsure.setText(positiveText);

        View cancelLayout = contentView.findViewById(R.id.rlCancel);
        TextView tvCancel = (TextView) contentView.findViewById(R.id.btCancel);
        if (!TextUtils.isEmpty(negativeText))
            tvCancel.setText(negativeText);
        cancelLayout.setVisibility(negativeButtonVisible ? View.VISIBLE : View.GONE);

        if (onDialogClickListener != null) {
            tvEnsure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissAllowingStateLoss();
                    onDialogClickListener.onPositiveButtonClickListener();
                }
            });

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissAllowingStateLoss();
                    onDialogClickListener.onNegativeButtonClickListener();
                }
            });
        }
    }

    public interface OnDialogClickListener {
        void onPositiveButtonClickListener();

        void onNegativeButtonClickListener();
    }

    public AlertDialogFragment setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
        return this;
    }

}

