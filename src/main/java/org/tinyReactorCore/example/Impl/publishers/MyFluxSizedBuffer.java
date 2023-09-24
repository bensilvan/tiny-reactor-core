package org.tinyReactorCore.example.Impl.publishers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyFluxSizedBuffer<T> extends MyFluxProxy<List<T>, T> {

    private final Integer bufferMaxSize;
    private final ArrayBlockingQueue<T> currentBuffer;

    public MyFluxSizedBuffer(MyFlux<T> actualPublisher, Integer bufferMaxSize) {
        super(actualPublisher);
        this.bufferMaxSize = bufferMaxSize;
        this.currentBuffer = new ArrayBlockingQueue<>(bufferMaxSize);
    }

    @Override
    public void onRequest(Integer count) {
        this.upperSubscription.request(count * this.bufferMaxSize);
    }

    @Override
    public void onNext(T item) {
        this.currentBuffer.add(item);
        if (this.currentBuffer.size() == this.bufferMaxSize) {
            var list = new ArrayList<T>();
            this.currentBuffer.drainTo(list);
            this.subscriber.onNext(list);
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Exception e) {

    }
}
