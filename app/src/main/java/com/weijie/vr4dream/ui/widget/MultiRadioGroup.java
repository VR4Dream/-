package com.weijie.vr4dream.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

/**
 * 多行Radiogroup
 * 作者：guoweijie on 16/12/17 17:46
 * 邮箱：529844698@qq.com
 */
public class MultiRadioGroup extends LinearLayout {

    // holds the checked id; the selection is empty by default
    private int mCheckedId = -1;

    // tracks children radio buttons checked state
    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;

    // when true, mOnCheckedChangeListener discards events
    private boolean mProtectFromCheckedChange = false;

    private OnCheckedChangeListener mOnCheckedChangeListener;
    private PassThroughHierarchyChangeListener mPassThroughListener;


    public MultiRadioGroup(Context context) {
        super(context);
        setOrientation(VERTICAL);
        init();
    }


    public MultiRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCheckedId = View.NO_ID;

        final int index = VERTICAL;
        setOrientation(index);

        init();
    }

    private void init() {
        mChildOnCheckedChangeListener = new CheckedStateTracker();
        mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(mPassThroughListener);
    }


    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        // the user listener is delegated to our pass-through listener
        mPassThroughListener.mOnHierarchyChangeListener = listener;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // checks the appropriate radio button as requested in the XML file
        if (mCheckedId != -1) {
            mProtectFromCheckedChange = true;
            setCheckedStateForView(mCheckedId, true);
            mProtectFromCheckedChange = false;
            setCheckedId(mCheckedId);
        }
    }


    @Override
    public void addView(final View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof RadioButton) {

            ((RadioButton) child).setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    ((RadioButton) child).setChecked(true);
                    checkRadioButton((RadioButton) child);
                    if(mOnCheckedChangeListener != null){
                        mOnCheckedChangeListener.onCheckedChanged(MultiRadioGroup.this, child.getId());
                    }
                    return true;
                }
            });

        } else if(child instanceof LinearLayout){
            int childCount = ((LinearLayout) child).getChildCount();
            for(int i = 0; i < childCount; i++){
                View view = ((LinearLayout) child).getChildAt(i);
                if (view instanceof RadioButton) {
                    final RadioButton button = (RadioButton) view;


                    ((RadioButton) button).setOnTouchListener(new OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            ((RadioButton) button).setChecked(true);
                            checkRadioButton((RadioButton) button);
                            if(mOnCheckedChangeListener != null){
                                mOnCheckedChangeListener.onCheckedChanged(MultiRadioGroup.this, button.getId());
                            }
                            return true;
                        }
                    });

                }
            }
        }

        super.addView(child, index, params);
    }

    private void checkRadioButton(RadioButton radioButton){
        View child;
        int radioCount = getChildCount();
        for(int i = 0; i < radioCount; i++){
            child = getChildAt(i);
            if (child instanceof RadioButton) {
                if(child == radioButton){
                    // do nothing
                } else {
                    ((RadioButton) child).setChecked(false);
                }
            } else if(child instanceof LinearLayout){
                int childCount = ((LinearLayout) child).getChildCount();
                for(int j = 0; j < childCount; j++){
                    View view = ((LinearLayout) child).getChildAt(j);
                    if (view instanceof RadioButton) {
                        final RadioButton button = (RadioButton) view;
                        if(button == radioButton){
                            // do nothing
                        } else {
                            ((RadioButton) button).setChecked(false);
                        }
                    }
                }
            }
        }
    }


    public void check(int id) {
        // don't even bother
        if (id != -1 && (id == mCheckedId)) {
            return;
        }

        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false);
        }

        if (id != -1) {
            setCheckedStateForView(id, true);
        }

        setCheckedId(id);
    }

    private void setCheckedId(int id) {
        mCheckedId = id;
    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = findViewById(viewId);
        if (checkedView != null && checkedView instanceof RadioButton) {
            ((RadioButton) checkedView).setChecked(checked);
        }
    }


    public int getCheckedRadioButtonId() {
        return mCheckedId;
    }


    public void clearCheck() {
        check(-1);
    }


    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MultiRadioGroup.LayoutParams(getContext(), attrs);
    }


    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MultiRadioGroup.LayoutParams;
    }

    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(MultiRadioGroup.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(MultiRadioGroup.class.getName());
    }


    public static class LayoutParams extends LinearLayout.LayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }


        public LayoutParams(int w, int h) {
            super(w, h);
        }


        public LayoutParams(int w, int h, float initWeight) {
            super(w, h, initWeight);
        }


        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }


        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }


        @Override
        protected void setBaseAttributes(TypedArray a,
                                         int widthAttr, int heightAttr) {

            if (a.hasValue(widthAttr)) {
                width = a.getLayoutDimension(widthAttr, "layout_width");
            } else {
                width = WRAP_CONTENT;
            }

            if (a.hasValue(heightAttr)) {
                height = a.getLayoutDimension(heightAttr, "layout_height");
            } else {
                height = WRAP_CONTENT;
            }
        }
    }


    public interface OnCheckedChangeListener {

        public void onCheckedChanged(MultiRadioGroup group, int checkedId);
    }

    private class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // prevents from infinite recursion
            if (mProtectFromCheckedChange) {
                return;
            }

            mProtectFromCheckedChange = true;
            if (mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false);
            }
            mProtectFromCheckedChange = false;

            int id = buttonView.getId();
            setCheckedId(id);
        }
    }


    private class PassThroughHierarchyChangeListener implements
            ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;


        public void onChildViewAdded(View parent, View child) {
            if (parent == MultiRadioGroup.this && child instanceof RadioButton) {
                int id = child.getId();
                // generates an id if it's missing
                if (id == View.NO_ID) {
                    id = child.hashCode();
                    child.setId(id);
                }
                ((RadioButton) child).setOnCheckedChangeListener(
                        mChildOnCheckedChangeListener);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }


        public void onChildViewRemoved(View parent, View child) {
            if (parent == MultiRadioGroup.this && child instanceof RadioButton) {
                ((RadioButton) child).setOnCheckedChangeListener(null);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }
}
