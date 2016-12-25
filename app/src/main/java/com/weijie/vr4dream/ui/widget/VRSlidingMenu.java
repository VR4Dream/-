package com.weijie.vr4dream.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.weijie.vr4dream.R;

/**
 * 自定义侧滑
 * 作者：guoweijie on 16/12/17 11:11
 * 邮箱：529844698@qq.com
 */
public class VRSlidingMenu extends ViewGroup {

    private View menuView;
    private View mainView;
    private int menuWidth;
    private int downX;
    private int distance;
    private int currentstart;
    private Scroller scroller;
    private int downY;
    private int menuWidthDB;

    public VRSlidingMenu(Context context) {
        // super(context);
        this(context, null);
    }

    public VRSlidingMenu(Context context, AttributeSet attrs) {
        // super(context, attrs);
        this(context, attrs, 0);
    }

    public VRSlidingMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        menuWidthDB = context.getResources().getDimensionPixelSize(R.dimen.slidingmenu_width);
        // 将移动的距离拆分成一段一段的实现对象
        scroller = new Scroller(context);
    }

    // 测量
    // 1.自定义控件
    // 2.菜单页
    // 3.首页
    // widthMeasureSpec heightMeasureSpec : 当前控件的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 1.自定义控件
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 测量菜单页和首页
        // 根据布局文件中的角标获取子控件的对象
        menuView = getChildAt(0);
        mainView = getChildAt(1);
        // 2.菜单页
        menuWidth = menuView.getLayoutParams().width;
        menuView.measure(menuWidth, heightMeasureSpec);
        // 3.首页
        mainView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    // 排版
    // changed : 是否有最新的位置
    // l t r b : 控件的左上右下的位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 菜单页
        menuView.layout(l - menuWidthDB, t, l, b);
        // 首页
        mainView.layout(l, t, r, b);
    }

    // 绘制
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
    }

    /**
     * 缓慢滑动的
     *
     * @param startX
     *            : 开始的位置 toX : 结束的位置 2016-8-14 下午4:21:25
     */
    private void scrollToTime(int startX, int toX) {
        // 将距离拆分成一段一段
        int dx = toX - startX;

        // 根据距离设置相应的时间
        // 获取每段距离所需时间
        int scale = 1000 / menuWidth;
        // 因为移动的距离可以能会是负数,但是时间是不能为负数
        int time = scale * Math.abs(dx);
        // startX, startY : 开始移动的位置
        // dx, dy : 移动的距离
        // duration : 持续的时间
        scroller.startScroll(startX, 0, dx, 0, time);
        invalidate();
    }

    /**
     * 获取一段移动距离的操作
     */
    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {// 判断是否已经完成距离拆分操作
            int currX = scroller.getCurrX();// 获取一小段移动的距离
            // 移动一小段距离
            myScrollTo(currX);
            // 进行下一次移动距离操作
            invalidate();
        }
        super.computeScroll();
    }

    /**
     * 封装了系统的scrollTo方法 2016-8-14 下午4:06:46
     */
    private void myScrollTo(int x) {
        scrollTo(-x, 0);
    }

    /**
     * 手动打开关闭侧拉菜单 2016-8-14 下午5:02:54
     */
    public void toggle() {
        // 关闭 -> 打开
        // 打开 -> 关闭
        // 问题:如何知道侧拉菜单是打开还是关闭
        // 可以通过移动距离判断,如果是整个菜单页的宽度,表示打开,如果是0表示关闭
        // getScrollX();//获取x轴的移动距离,也是正负相反
        if (myGetScrollX() == 0) {
            // 关闭 -> 打开
            distance = 0;
            currentstart = menuWidth;
        } else {
            // 打开 -> 关闭
            distance = menuWidth;
            currentstart = 0;
        }
        // 移动操作
        scrollToTime(distance, currentstart);

    }

    public boolean isMenuShow() {
        return myGetScrollX()!=0;
    }

    public void showMenu() {
        distance = 0;
        currentstart = menuWidth;
        scrollToTime(distance, currentstart);
    }

    public void hideMenu() {
        distance = menuWidth;
        currentstart = 0;
        scrollToTime(distance, currentstart);
    }

    public int myGetScrollX() {
        return -getScrollX();
    }

    // 事件分发操作,主要用来将触摸事件传递给子控件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        return super.dispatchTouchEvent(ev);
    }

    // 事件拦截操作,主要用来判断是否拦截事件,是否传递给子控件事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //判断横向滑动还是竖向滑动,如果是横向滑动,拦截事件,如果是竖向滑动,放开事件不去拦截
        //可以根据x轴和y的轴距离来判断
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                //获取x轴和y轴的距离
                int distanceX = Math.abs(moveX - downX);
                int distanceY = Math.abs(moveY - downY);
                if (distanceX > distanceY + 100) {
                    return true;//拦截操作
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    // 侧拉菜单的滑动操作
    // 1.触摸事件
    // 2.移动相应的滑动距离
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                // 计算移动的距离
                distance = currentstart + moveX - downX;
                System.out.println(distance);
                // 2.控制滑动的范围
                if (distance < 0) {
                    distance = 0;
                } else if (distance > menuWidth) {
                    distance = menuWidth;
                }
                // 1.让侧拉菜单移动相应的距离
                // scrollTo(-distance, 0);//移动相应的x和y的距离
                myScrollTo(distance);
                break;
            case MotionEvent.ACTION_UP:
                // 3.保存当前滑动的位置,当下一次滑动的时候,从保存的位置开始滑动相应的距离
                // currentstart = distance;
                // 4.实现自动滑动操作,根据菜单页的宽度的一半来进行计算的
                if (distance < menuWidth / 2) {
                    // myScrollTo(0);
                    // 因为自动滑动到了0的位置,所以下一次滑动的时候,要从0的位置开始滑动
                    currentstart = 0;
                } else {
                    // myScrollTo(menuWidth);
                    currentstart = menuWidth;
                }
                // 6.自动滑动的时候,实现缓慢滑动
                scrollToTime(distance, currentstart);
                break;
        }
        return true;
    }

}
