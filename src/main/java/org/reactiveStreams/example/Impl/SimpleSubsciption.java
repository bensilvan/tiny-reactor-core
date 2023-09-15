package org.reactiveStreams.example.Impl;

import org.reactiveStreams.example.specification.Subscription;

import java.util.function.Consumer;

public class SimpleSubsciption implements Subscription {
    private final Consumer<Integer> produce;
    public SimpleSubsciption(Consumer<Integer> produce){
        this.produce = produce;
    }
    @Override
    public void request(Integer count) {
        produce.accept(count);

    }
}
