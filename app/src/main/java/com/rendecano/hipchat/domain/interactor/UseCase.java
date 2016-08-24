package com.rendecano.hipchat.domain.interactor;

import com.rendecano.hipchat.domain.ConvertRepository;
import com.rendecano.hipchat.domain.schedulers.ObserveOn;
import com.rendecano.hipchat.domain.schedulers.SubscribeOn;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Ren Decano.
 */

public abstract class UseCase<T> {

    protected final ConvertRepository convertRepository;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;
    private Subscription subscription = Subscriptions.empty();

    protected UseCase(ConvertRepository convertRepository, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.convertRepository = convertRepository;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public void execute(Subscriber<T> subscriber) {
        subscription = buildUseCaseObservable()
                .subscribeOn(subscribeOn.getScheduler())
                .observeOn(observeOn.getScheduler())
                .subscribe(subscriber);
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    protected abstract Observable<T> buildUseCaseObservable();

}
