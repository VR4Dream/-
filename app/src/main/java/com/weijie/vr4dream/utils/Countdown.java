package com.weijie.vr4dream.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;

/**
 * 计时器
 * 
 * @author weijie
 * @version 创建时间：2014年11月6日 下午5:51:22
 */
public class Countdown {

	private Timer timer;
	private Handler handler;
	private int timeOffset;
	private int current;
	private boolean isEnd = true; // 倒计时是否已结束，默认是未开始

	/**
	 * @param time
	 *            时间间隔
	 * @param handler
	 *            通过what获取倒计时
	 */
	public Countdown(Handler handler, int time) {
		this.timeOffset = time;
		this.handler = handler;
	}

	public void start() {
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new Task(), 0, 1000);
			isEnd = false;
		}
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
			current = 0;
			isEnd = true;
		}
	}

	class Task extends TimerTask {
		@Override
		public void run() {
			current++;
			int offset = timeOffset - current;
			if (offset <= 0) {
				offset = 0;
				stop();
			}
			if (handler != null) {
				handler.sendEmptyMessage(offset);
			}
		}
	}

	public boolean isTimer() {
		return timer==null;
	}

}
