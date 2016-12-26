package com.weijie.vr4dream.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weijie.vr4dream.R;

/**
 * 用户信息控件
 * 作者：guoweijie on 16/12/26 10:18
 * 邮箱：529844698@qq.com
 */
public class UserInfoView extends LinearLayout {

    private TextView tvTitle, tvContent;
    private ImageView ivIcon;

    public UserInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.user_info, 0, 0);
        String title = ta.getString(R.styleable.user_info_txt_title);
        String content = ta.getString(R.styleable.user_info_txt_content);
        Drawable icon = ta.getDrawable(R.styleable.user_info_icon_right);
        float width = ta.getDimension(R.styleable.user_info_icon_width, 50);
        float height = ta.getDimension(R.styleable.user_info_icon_height, 50);
        ta.recycle();

        inflate(context, R.layout.view_info_menu, this);

        ivIcon = (ImageView)findViewById(R.id.iv_icon);
        if(icon!=null) {
            LayoutParams params = new LayoutParams((int)width, (int)height);
            ivIcon.setLayoutParams(params);
            ivIcon.setImageDrawable(icon);
        } else {
            LayoutParams params = new LayoutParams(1, (int)height);
            ivIcon.setLayoutParams(params);
        }

        tvTitle = (TextView)findViewById(R.id.tv_title);
        if(title!=null) {
            tvTitle.setText(title);
        }

        tvContent = (TextView)findViewById(R.id.tv_content);
        if(content!=null) {
            tvContent.setText(content);
        }

    }
}
