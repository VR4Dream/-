package com.weijie.vr4dream.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.weijie.vr4dream.R;

/**
 * 顶部滑动导航
 * 作者：guoweijie on 16/12/20 16:13
 * 邮箱：529844698@qq.com
 */
public class GuideTabView extends RelativeLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mViewPager;
    private LinearLayout tabsLayout;
    private View indicateView;
    private RelativeLayout.LayoutParams indicateLayoutParams;
    private LinearLayout.LayoutParams tabLayoutParams;
    private ColorStateList tabTextColor;
    private OnTabSeletedListener mListener;
    private boolean updateIndicateViewXOnPreDraw; //是否在界面绘制完成时更新指示器的坐标

    private final int TABLINE = 2;

    // 当前选中的tab的序号
    private int currentSeletTabIndex = 0;
    private int index;

    public GuideTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        tabsLayout = new LinearLayout(context);
        indicateView = new View(context);
        indicateView.setBackgroundColor(getResources().getColor(R.color.guideIndicate));
        addView(tabsLayout, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        int indicateHeight = getResources().getDimensionPixelSize(R.dimen.guide_view_height);
        indicateLayoutParams = new RelativeLayout.LayoutParams(0, indicateHeight);
        indicateLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        addView(indicateView, indicateLayoutParams);

        tabLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        tabTextColor = getResources().getColorStateList(R.color.selector_guide);
    }

    public int getCurrentSeletTabIndex() {
        return currentSeletTabIndex;
    }

    public void setUp(ViewPager viewPager) {
        this.mViewPager = viewPager;
        this.mViewPager.addOnPageChangeListener(this);
    }

    public void setTabs(int namesResId) {
        String[] array = getResources().getStringArray(namesResId);
        setTabs(array);
    }

    /**
     * 设置tab的名称
     *
     * @param names
     */
    public void setTabs(final String[] names) {
        for (int i = 0; i < names.length; i++) {
            tabsLayout.addView(createTab(names[i], i));
        }

        // 指示条宽度
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (indicateLayoutParams.width == 0) {
                    indicateLayoutParams.width = getWidth() / (names.length * TABLINE);
                    indicateLayoutParams.leftMargin = getWidth() / (names.length * TABLINE * 2);
                    indicateView.setLayoutParams(indicateLayoutParams);

                    if(updateIndicateViewXOnPreDraw){
                        updateIndicateViewXOnPreDraw = false;
                        onClick(tabsLayout.getChildAt(index));
                    }

                } else {
                    getViewTreeObserver().removeOnPreDrawListener(this);
                }
                return false;
            }
        });
    }

    @SuppressLint("NewApi")
    private Button createTab(String name, int index) {
        Drawable tabBackground = getResources().getDrawable(R.drawable.selector_guide_tab);

        Button button = new Button(getContext());
        button.setText(name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackground(tabBackground);
        } else {
            button.setBackgroundDrawable(tabBackground);
        }
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        button.setTextColor(tabTextColor);
        button.setOnClickListener(this);
        button.setId(index);
        // 初始选中
        button.setSelected(index == currentSeletTabIndex);
        button.setLayoutParams(tabLayoutParams);
        return button;
    }

    /**
     * 设置当前选中tab
     * @param index
     */
    public void setCurrentSelectTabIndex(int index){
        if(index != currentSeletTabIndex && indicateLayoutParams.width == 0){ //View未初始化完成,在OnPreDraw方法中再更新指示器的坐标
            this.index = index;
            updateIndicateViewXOnPreDraw = true;
        }else{
            onClick(tabsLayout.getChildAt(index));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        updateIndicateViewX(position * indicateLayoutParams.width * TABLINE +
                positionOffset * indicateLayoutParams.width * TABLINE + indicateLayoutParams.width / TABLINE);
    }

    @SuppressLint("NewApi")
    private void updateIndicateViewX(float x) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            indicateView.setX(x);
        } else {
            indicateLayoutParams.leftMargin = (int) x;
            indicateView.setLayoutParams(indicateLayoutParams);
        }
    }

    @Override
    public void onPageSelected(int position) {
        onClick(tabsLayout.getChildAt(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() != currentSeletTabIndex) {
            currentSeletTabIndex = v.getId();
            for (int i = 0; i < tabsLayout.getChildCount(); i++) {
                tabsLayout.getChildAt(i).setSelected(i == currentSeletTabIndex);
            }
            if (mViewPager != null) {
                mViewPager.setCurrentItem(currentSeletTabIndex);
            } else {

                updateIndicateViewX(currentSeletTabIndex * indicateLayoutParams.width);
            }
            if (mListener != null) {
                mListener.onTabSeleted(currentSeletTabIndex);
            }
        }
    }

    public void setOnTabSeletedListener(OnTabSeletedListener listener) {
        this.mListener = listener;
    }

    public interface OnTabSeletedListener {
        public void onTabSeleted(int index);
    }
}
