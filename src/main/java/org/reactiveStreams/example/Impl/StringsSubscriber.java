package org.reactiveStreams.example.Impl;

import org.reactiveStreams.example.specification.Subscriber;
import org.reactiveStreams.example.specification.Subscription;

public class StringsSubscriber implements Subscriber<String> {
    private Subscription subscription;
    @Override
    public void onNext(String item) {
        System.out.println("got: " + item + " On thread: "+ Thread.currentThread());
        this.subscription.request(1);
    }

    @Override
    public void onComplete() {
        System.out.println("got complete on " + Thread.currentThread());
    }

    @Override
    public void onError(Exception e) {
        System.out.println("got Exception: " + e.getMessage() + " on thread: " + Thread.currentThread());

    }
    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }
}

