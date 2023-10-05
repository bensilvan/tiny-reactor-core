package io.github.bensilvan.tinyreactorcore.Impl;

import org.reactivestreams.Subscription;

public class SingleValueSubscription implements Subscription {
    private final Runnable producer;
    private Boolean isCompleted;

    public SingleValueSubscription(Runnable producer) {
        this.isCompleted = false;
        this.producer = producer;
    }
    @Override
    public void request(long count) {
        if (!this.isCompleted) {
            this.producer.run();
            this.isCompleted = true;
        }
    }

    @Override
    public void cancel() {

    }
}
