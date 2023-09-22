package org.tinyReactorCore.example.Impl.publishers;

import org.tinyReactorCore.example.Impl.DefaultSubscriber;
import org.tinyReactorCore.example.Impl.SingleValueSubscription;
import org.tinyReactorCore.example.specification.Publisher;
import org.tinyReactorCore.example.specification.Subscriber;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class MyMono<T> implements Publisher<T> {
    protected Subscriber<T> subscriber;
    @Override
    public void subscribe(Subscriber<T> subscriber) {
        this.subscriber = subscriber;
        this.subscriber.onSubscribe(new SingleValueSubscription(this::onRequest));
    }
    public abstract void onRequest();
    public static <T> MyMonoSimpleValue<T> just(T value) {
        return new MyMonoSimpleValue<>(value);
    }
    public static <T> MyMonoFutureValue<T> fromFuture(CompletableFuture<T> future) {
        return new MyMonoFutureValue<>(future);
    }
    public static <T> MyMonoFutureValueSupplier<T> fromFuture(Supplier<CompletableFuture<T>> futureSupplier){
        return new MyMonoFutureValueSupplier<>(futureSupplier);
    }
    public void subscribe() {
        this.subscribe(new DefaultSubscriber<>());
    }

    public void subscribe(Consumer<T> callback) {
        this.subscribe(new DefaultSubscriber<>(callback));
    }
}
