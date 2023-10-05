package io.github.bensilvan.tinyreactorcore.Impl;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.function.Consumer;

public class InnerMonoSubscriber<T> implements Subscriber<T> {
    private Consumer<T> callback;

    public InnerMonoSubscriber(Consumer<T> callback) {
        this.callback = callback;
    }
    @Override
    public void onNext(T item) {
        this.callback.accept(item);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(1);
    }
}
