package org.reactiveStreams.example.Impl.publishers;

import org.reactiveStreams.example.specification.Subscriber;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class MyFluxListImpl<T> extends MyFlux<T>{

    private Integer index;
    private final List<T> list;

    public MyFluxListImpl(List<T> list) {
        this.list = list;
        this.index = 0;
    }
    @Override
    public void OnRequest(Integer count) {
        for (var i = 0; i < count; i++) {
            if (index < this.list.size()) {
                super.subscriber.onNext(this.list.get(this.index++));
            } else {
                super.subscriber.onComplete();
                this.index = 0;
            }
        }
    }
}
