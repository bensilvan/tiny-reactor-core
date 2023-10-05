package io.github.bensilvan.tinyreactorcore.Impl.publishers;

import io.github.bensilvan.tinyreactorcore.Impl.DefaultSubscriber;
import io.github.bensilvan.tinyreactorcore.Impl.SingleValueSubscription;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class MyMono<T> implements Publisher<T> {
    protected Subscriber<? super T> subscriber;
    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
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

    public static <T> MyMonoDelay<T> delay(T value, Duration delay, ScheduledExecutorService scheduler) {
        return new MyMonoDelay<>(value, delay, scheduler);
    }

    public static <T> MyMonoDelay<T> delay(T value, Duration delay) {
        return new MyMonoDelay<T>(value, delay);
    }
    public void subscribe() {
        this.subscribe(new DefaultSubscriber<>());
    }

    public void subscribe(Consumer<T> callback) {
        this.subscribe(new DefaultSubscriber<>(callback));
    }
}
