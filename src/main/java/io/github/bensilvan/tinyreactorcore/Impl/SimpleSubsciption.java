package io.github.bensilvan.tinyreactorcore.Impl;

import org.reactivestreams.Subscription;

import java.util.function.Consumer;

public class SimpleSubsciption implements Subscription {
    private final Consumer<Long> produce;
    public SimpleSubsciption(Consumer<Long> produce){
        this.produce = produce;
    }
    @Override
    public void request(long n) {
        produce.accept(n);
    }

    @Override
    public void cancel() {

    }
}
