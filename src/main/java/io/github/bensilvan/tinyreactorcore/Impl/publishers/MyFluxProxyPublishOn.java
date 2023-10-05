package io.github.bensilvan.tinyreactorcore.Impl.publishers;

import io.github.bensilvan.tinyreactorcore.specification.Subscriber;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class MyFluxProxyPublishOn<T> extends MyFluxProxy<T,T> implements Subscriber<T> {
    private final ExecutorService executor;
    private CompletableFuture<Void> currentTask;

    public MyFluxProxyPublishOn(MyFlux<T> actualPublisher, ExecutorService executor) {
        super(actualPublisher);
        this.executor = executor;
        currentTask = null;
    }
    @Override
    public void onNext(T item) {
        if (this.currentTask == null) {
            this.currentTask =  CompletableFuture.runAsync(() -> this.subscriber.onNext(item), this.executor);
        } else {
            this.currentTask = this.currentTask.thenRun(() -> this.subscriber.onNext(item));
        }
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

    @Override
    public void onRequest(Integer count) {
        this.upperSubscription.request(count);
    }
}
