package com.weijie.vr4dream.utils;

import java.util.concurrent.ConcurrentHashMap;

import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;
import rx.subscriptions.CompositeSubscription;

/**
 * 事件订阅
 * 作者：guoweijie on 16/12/15 17:27
 * 邮箱：529844698@qq.com
 */
public class RxBus {

    private ConcurrentHashMap<Object, CompositeSubscription> subscriptionMapper = new ConcurrentHashMap<>();
    private final Subject<Object, Object> rxNormalBus = new SerializedSubject<>(PublishSubject.create());
    private final Subject<Object, Object> rxStickyBus = new SerializedSubject<>(BehaviorSubject.create());

    /**
     * 发送粘性事件
     *
     * @param o 事件
     */
    public void sendStickyEvent(Object o) {
        rxStickyBus.onNext(o);
    }

    /**
     * 发送普通事件
     *
     * @param o 事件
     */
    public void sendNormalEvent(Object o) {
        rxNormalBus.onNext(o);
    }

    /**
     * 普通事件订阅
     *
     * @param tag        标记
     * @param subscriber 订阅者
     */
    public void subscribeNormalEvent(Object tag, Action1<Object> subscriber) {
        CompositeSubscription compositeSubscription = subscriptionMapper.get(tag);
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
            subscriptionMapper.put(tag, compositeSubscription);
        }

        compositeSubscription.add(rxNormalBus.asObservable()
                        .onBackpressureBuffer()
                        .subscribe(subscriber)
        );
    }

    /**
     * 粘性事件订阅
     *
     * @param tag        标记
     * @param subscriber 订阅者
     */
    public void subscribeStickyEvent(Object tag, Action1<Object> subscriber) {
        CompositeSubscription compositeSubscription = subscriptionMapper.get(tag);
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
            subscriptionMapper.put(tag, compositeSubscription);
        }

        compositeSubscription.add(rxStickyBus.asObservable()
                        .share()
                        .onBackpressureBuffer()
                        .subscribe(subscriber)
        );
    }

    /**
     * 取消事件订阅
     *
     * @param tag 标记
     */
    public void unSubscribe(Object tag) {
        CompositeSubscription compositeSubscription = subscriptionMapper.get(tag);
        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

}
