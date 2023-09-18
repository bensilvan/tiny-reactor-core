package org.tinyReactorCore.example.Impl.publishers;

import java.util.List;

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
