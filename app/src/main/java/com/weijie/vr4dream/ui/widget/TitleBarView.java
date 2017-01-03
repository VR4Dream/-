package com.weijie.vr4dream.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weijie.vr4dream.R;

/**
 * 标题栏
 * 作者：guoweijie on 16/12/30 11:37
 * 邮箱：529844698@qq.com
 */
public class TitleBarView extends LinearLayout {

    private TextView tvLeft, tvRight, tvTitle;

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.user_info, 0, 0);
        String title = ta.getString(R.styleable.user_info_txt_title);
        Drawable left = ta.getDrawable(R.styleable.user_info_icon_left);
        Drawable right = ta.getDrawable(R.styleable.user_info_icon_right);
        String txtLeft = ta.getString(R.styleable.user_info_txt_left);
        String txtRight = ta.getString(R.styleable.user_info_txt_right);
        ta.recycle();

        inflate(context, R.layout.view_title_bar, this);
        setOrientation(VERTICAL);
        tvLeft = (TextView)findViewById(R.id.btn_left);
        tvRight = (TextView)findViewById(R.id.btn_right);
        tvTitle = (TextView)findViewById(R.id.tv_title);

        if(title!=null) {
            tvTitle.setText(title);
        }
        if(txtLeft!=null) {
            tvLeft.setText(txtLeft);
        }
        if(txtRight!=null) {
            tvRight.setText(txtRight);
        }
        if(left!=null) {
            left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());
            tvLeft.setCompoundDrawables(left,null,null,null);
        }
        if(right!=null) {
            right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
            tvRight.setCompoundDrawables(null,null,right,null);
        }


    }



}
