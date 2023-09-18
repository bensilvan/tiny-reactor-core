package org.tinyReactorCore.example.Impl.publishers;

import org.tinyReactorCore.example.specification.Subscriber;

import java.util.concurrent.ExecutorService;

public class MyFluxProxyPublishOn<T> extends MyFluxProxy<T,T> implements Subscriber<T> {

    private final ExecutorService executor;

    public MyFluxProxyPublishOn(MyFlux<T> actualPublisher, ExecutorService executor) {
        super(actualPublisher);
        this.executor = executor;
    }
    @Override
    public void onNext(T item) {
        this.executor.execute(() -> {
            this.subscriber.onNext(item);
        });
    }

    @Override
    public void onError(Exception e){
        this.executor.execute(() -> {
            this.subscriber.onError(e);
        });
    }

    @Override
    public void onComplete(){
        this.executor.execute(() -> {
            this.subscriber.onComplete();
        });
    }
}
