package io.github.bensilvan.tinyreactorcore.Impl.publishers;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

// for cold publisher
public class MyMonoFutureValueSupplier<T> extends MyMono<T>{
    private final Supplier<CompletableFuture<T>> futureSupplier;

    public MyMonoFutureValueSupplier(Supplier<CompletableFuture<T>> futureSupplier) {
        this.futureSupplier = futureSupplier;
    }
    @Override
    public void onRequest() {
        this.futureSupplier.get().thenAccept((res)-> this.subscriber.onNext(res));
    }
}
