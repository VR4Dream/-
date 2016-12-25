package com.weijie.vr4dream.presenter;

import android.content.Context;
import android.os.Bundle;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.rxEvent.ActivityFinishEvent;
import com.weijie.vr4dream.ui.view.IBaseView;


/**
 * presenter基类
 * 作者：guoweijie on 16/12/15 17:16
 * 邮箱：529844698@qq.com
 */
public class BaseFragmentPresenter<V extends IBaseView> implements IBasePresenter {
    protected Context mContext;
    protected V mView;

    public BaseFragmentPresenter(Context context, V view) {
        this.mContext = context;
        this.mView = view;
    }

    public void onCreateView(Bundle savedInstanceState) {
    }

    public void onActivityCreated(Bundle savedInstanceState) {
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroyView() {
    }

    public void onDestroy() {
    }

    public void onDetach() {
    }

    /**
     * activity结束自身的事件
     */
    public void sendFinishActivityEvent() {
        App.getInstance()
                .getRxBus()
                .sendNormalEvent(new ActivityFinishEvent(mContext.getClass().getSimpleName()));
    }

    @Override
    public void receiveActivityFinishEvent(ActivityFinishEvent event) {
    }
}
