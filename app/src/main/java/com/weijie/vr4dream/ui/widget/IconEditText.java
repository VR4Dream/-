package com.weijie.vr4dream.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.weijie.vr4dream.R;

/**
 * 带logo的输入框
 * 作者：guoweijie on 16/12/16 15:14
 * 邮箱：529844698@qq.com
 */
public class IconEditText extends LinearLayout {

    private EditText tvContent;
    private ImageView lIcon, rIcon;

    public IconEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.icon_edit, 0, 0);
        String txt = ta.getString(R.styleable.icon_edit_input_txt);
        String hint = ta.getString(R.styleable.icon_edit_input_hint);
        Drawable left = ta.getDrawable(R.styleable.icon_edit_input_left);
        int inputType = ta.getInteger(R.styleable.icon_edit_input_type, 0);
        ta.recycle();

        inflate(context, R.layout.view_icon_edittext, this);

        setBackgroundResource(R.drawable.shape_round_edittext);

        tvContent = (EditText)findViewById(R.id.tv_content);
        lIcon = (ImageView) findViewById(R.id.left_icon);
        rIcon = (ImageView) findViewById(R.id.right_icon);
        if(hint != null) {
            tvContent.setHint(hint);
        }
        if(txt != null) {
            tvContent.setText(txt);
        }

        if(left != null) {
            lIcon.setImageDrawable(left);
        } else {
            lIcon.setVisibility(GONE);
        }

        tvContent.setSingleLine(true);

        switch (inputType) {
            case 1:
                tvContent.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                break;
            case 2:
                tvContent.setInputType(EditorInfo.TYPE_CLASS_NUMBER
                        | EditorInfo.TYPE_NUMBER_FLAG_SIGNED);
                break;
            case 3:
                tvContent.setInputType(EditorInfo.TYPE_CLASS_NUMBER
                        | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case 4:
                tvContent.setInputType(EditorInfo.TYPE_CLASS_TEXT
                        | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case 5:
                tvContent.setInputType(EditorInfo.TYPE_CLASS_NUMBER
                        | EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD);
                break;
            case 6:
                tvContent.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                break;
            default:
                tvContent.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                break;
        }

    }

    public String getText() {
        return tvContent.getText().toString();
    }

    public void setError(String error) {
        tvContent.setError(error);
    }

    public void setTextWatcher(TextWatcher watcher) {
        tvContent.addTextChangedListener(watcher);
    }
}
