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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weijie.vr4dream.R;

/**
 * 编辑弹出框
 * 作者：guoweijie on 16/12/26 11:32
 * 邮箱：529844698@qq.com
 */
public class EditDialogFragment extends DialogFragment {
    private static EditDialogFragment fragment;

    private static String title;
    private static String positiveText;
    private static String negativeText;
    private static boolean negativeButtonVisible = true;
    private static View content;

    private OnDialogClickListener onDialogClickListener;

    public static EditDialogFragment getInstance(String title, View content) {
        EditDialogFragment.title = title;
        EditDialogFragment.content = content;
        if (fragment == null)
            fragment = new EditDialogFragment();
        return fragment;
    }

    public static EditDialogFragment getInstance(String title, boolean negativeButtonVisible, View content) {
        EditDialogFragment.title = title;
        EditDialogFragment.content = content;
        EditDialogFragment.negativeButtonVisible = negativeButtonVisible;
        if (fragment == null)
            fragment = new EditDialogFragment();
        return fragment;
    }

    public static EditDialogFragment getInstance(String title, String positiveText, String negativeText, View content) {
        EditDialogFragment.title = title;
        EditDialogFragment.content = content;
        EditDialogFragment.positiveText = positiveText;
        EditDialogFragment.negativeText = negativeText;
        if (fragment == null)
            fragment = new EditDialogFragment();
        return fragment;
    }

    public static EditDialogFragment getInstance(String title, String positiveText, String negativeText, boolean negativeButtonVisible, View content) {
        EditDialogFragment.title = title;
        EditDialogFragment.content = content;
        EditDialogFragment.positiveText = positiveText;
        EditDialogFragment.negativeText = negativeText;
        EditDialogFragment.negativeButtonVisible = negativeButtonVisible;
        if (fragment == null)
            fragment = new EditDialogFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View contentView = inflater.inflate(R.layout.fragment_edit_dialog, container, false);
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
        LinearLayout lvContent = (LinearLayout)contentView.findViewById(R.id.lv_content);
        lvContent.addView(content);
        TextView tvTitle = (TextView) contentView.findViewById(R.id.tvTitle);
        if (!TextUtils.isEmpty(title))
            tvTitle.setText(title);

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
                }
            });

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissAllowingStateLoss();
                    onDialogClickListener.onNegativeButtonClickListener(content);
                }
            });
        }
    }

    public interface OnDialogClickListener {
        void onNegativeButtonClickListener(View content);
    }

    public EditDialogFragment setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
        return this;
    }

}

