package com.rendecano.hipchat.domain.subscriber;

import rx.Subscriber;

/**
 * Created by Ren Decano.
 */
public abstract class UseCaseSubscriber<T> extends Subscriber<T> {
    @Override public void onCompleted() {}

    @Override public void onError(Throwable e) {}

    @Override public void onNext(T t) {}
}
