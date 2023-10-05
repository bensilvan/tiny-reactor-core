package io.github.bensilvan.tinyreactorcore.Impl;

import io.github.bensilvan.tinyreactorcore.specification.Subscription;

public class SingleValueSubscription implements Subscription {
    private final Runnable producer;
    private Boolean isCompleted;

    public SingleValueSubscription(Runnable producer) {
        this.isCompleted = false;
        this.producer = producer;
    }
    @Override
    public void request(Integer count) {
        if (!this.isCompleted) {
            this.producer.run();
            this.isCompleted = true;
        }
    }
}
