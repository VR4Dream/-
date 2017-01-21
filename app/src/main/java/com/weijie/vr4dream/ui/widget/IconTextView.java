package com.weijie.vr4dream.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weijie.vr4dream.R;

/**
 * 作者：guoweijie on 17/1/14 10:10
 * 邮箱：529844698@qq.com
 */
public class IconTextView extends LinearLayout {

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.icon_edit, 0, 0);
        String txt = ta.getString(R.styleable.icon_edit_input_txt);
        Drawable icon = ta.getDrawable(R.styleable.icon_edit_input_left);
        ta.recycle();

        inflate(context, R.layout.view_icon_text, this);

        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

        TextView tvName = (TextView)findViewById(R.id.tv_name);
        ImageView ivIcon = (ImageView)findViewById(R.id.iv_icon);

        if(txt!=null) {
            tvName.setText(txt);
        }

        if(icon!=null) {
            ivIcon.setImageDrawable(icon);
        }
    }
}
