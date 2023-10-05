package io.github.bensilvan.tinyreactorcore.Impl;

import io.github.bensilvan.tinyreactorcore.specification.Subscriber;
import io.github.bensilvan.tinyreactorcore.specification.Subscription;

import java.util.function.Consumer;

public class DefaultSubscriber<T> implements Subscriber<T> {

    private final Consumer<T> callback;
    public DefaultSubscriber() {
        this.callback = null;
    }
    public DefaultSubscriber(Consumer<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onNext(T item) {
        if (this.callback != null) {
            this.callback.accept(item);
        }
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
