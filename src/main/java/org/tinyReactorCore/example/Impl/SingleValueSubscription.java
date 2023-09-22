package org.tinyReactorCore.example.Impl;

import org.tinyReactorCore.example.specification.Subscription;

import java.util.function.Consumer;

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
