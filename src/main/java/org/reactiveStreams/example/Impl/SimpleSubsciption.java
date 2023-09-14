package org.reactiveStreams.example.Impl;

import org.reactiveStreams.example.specification.Subscription;

public class SimpleSubsciption implements Subscription {
    private final Runnable produce;
    public SimpleSubsciption(Runnable produce){
        this.produce = produce;
    }
    @Override
    public void request(Integer count) {
        for (var i = 0; i < count; i++){
            produce.run();
        }

    }
}
