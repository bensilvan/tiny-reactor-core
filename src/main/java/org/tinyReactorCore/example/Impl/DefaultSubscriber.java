package org.tinyReactorCore.example.Impl;

import org.tinyReactorCore.example.specification.Subscriber;
import org.tinyReactorCore.example.specification.Subscription;

public class DefaultSubscriber<T> implements Subscriber<T> {
    @Override
    public void onNext(T item) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("Run onSubscribe on: " + Thread.currentThread());
        subscription.request(Integer.MAX_VALUE);
    }
}
