package org.tinyReactorCore.example.Impl;

import org.tinyReactorCore.example.specification.Subscription;

import java.util.function.Consumer;

public class SingleValueSubscription implements Subscription {
    private Boolean isCompleted;
    private Consumer<Integer> producer;

    public SingleValueSubscription(Consumer<Integer> producer) {
        this.isCompleted = false;
        this.producer = producer;
    }
    @Override
    public void request(Integer count) {
        if (!this.isCompleted) {
            this.producer.accept(1);
            this.isCompleted = true;
        }
    }
}
