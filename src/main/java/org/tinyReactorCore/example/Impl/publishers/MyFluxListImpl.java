package org.tinyReactorCore.example.Impl.publishers;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MyFluxListImpl<T> extends MyFlux<T>{
    private final ArrayBlockingQueue<T> queue;

    public MyFluxListImpl(List<T> list) {
        var queue = new ArrayBlockingQueue<T>(list.size());
        queue.addAll(list);
        this.queue = queue;
    }
    @Override
    public void onRequest(Integer count) {
        if (this.queue.isEmpty()) {
            this.subscriber.onComplete();
        } else {
            for (var i = 0; i < count; i ++) {
                this.subscriber.onNext(this.queue.poll());
            }
        }
    }
}
