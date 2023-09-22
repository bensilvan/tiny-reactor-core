package org.tinyReactorCore.example.Impl.publishers;

import java.util.concurrent.CompletableFuture;

public class MyMonoFutureValue<T> extends MyMono<T>{

    private final CompletableFuture<T> future;

    public MyMonoFutureValue(CompletableFuture<T> future) {
        this.future = future;
    }
    @Override
    public void onRequest() {
        future.thenAccept((x)-> this.subscriber.onNext(x));
    }
}
