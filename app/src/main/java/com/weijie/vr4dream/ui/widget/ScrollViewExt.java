package com.weijie.vr4dream.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ExpandableListView;
import android.widget.ScrollView;

/**
 * 防止嵌套ViewPager时，水平滚动的冲突
 * 
 * @author weijie
 * @datetime 2014-12-1 下午9:32:09
 */
public class ScrollViewExt extends ScrollView {

	public ScrollViewExt(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// 解决ViewPager和ScrollView滑动冲突
	private float xDistance, yDistance, xLast, yLast;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(ev.getX() - xLast);
			yDistance += Math.abs(ev.getY() - yLast);
			xLast = curX;
			yLast = curY;

			if (xDistance > yDistance) {
				return false; // 表示向下传递事件
			}
		}
		return super.onInterceptTouchEvent(ev);
	}
}
