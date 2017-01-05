package com.weijie.vr4dream.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weijie.vr4dream.R;
/**
 * 搜索记录控件
 * 作者：guoweijie on 17/1/4 16:37
 * 邮箱：529844698@qq.com
 */
public class SearchLayout extends LinearLayout {

    private TextView tvLeft, tvRight;
    private LineBreakLayout lvParams;
    private LayoutInflater inflater;
    private SearchListener mSearchListener;

    public SearchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflater = LayoutInflater.from(context);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.user_info, 0, 0);
        String txtLeft = ta.getString(R.styleable.user_info_txt_left);
        String txtRight = ta.getString(R.styleable.user_info_txt_right);
        ta.recycle();

        inflate(context, R.layout.view_search_layout, this);
        setVisibility(GONE);

        tvLeft = (TextView)findViewById(R.id.tv_left);
        tvRight = (TextView)findViewById(R.id.tv_right);
        lvParams = (LineBreakLayout)findViewById(R.id.lv_params);

        if(txtLeft!=null) {
            tvLeft.setText(txtLeft);
        }
        if(txtRight!=null) {
            tvRight.setText(txtRight);
            tvRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mSearchListener!=null) {
                        mSearchListener.onRightClick();
                    }
                }
            });
        }
    }

    public void setData(Object[] params, SearchListener searchListener) {
        if(params!=null && params.length>0) {
            setVisibility(VISIBLE);
            lvParams.removeAllViews();
            for (Object param:params) {
                TextView tv = (TextView)inflater.inflate(R.layout.item_search_record, null);
                tv.setText(param.toString());
                tv.setOnClickListener(listener);
                lvParams.addView(tv);
            }
            mSearchListener = searchListener;
        } else {
            setVisibility(GONE);
            lvParams.removeAllViews();
        }
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mSearchListener.onClick(((TextView)v).getText().toString());
        }
    };

    public interface SearchListener {
        void onClick(String param);

        void onLeftClick();

        void onRightClick();
    }

}
