package org.tinyReactorCore.example.Impl.publishers;

import org.tinyReactorCore.example.Impl.constans.Schedulers;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyMonoDelay<T> extends MyMono<T>{
    private final T value;
    private final Duration delay;

    private final ScheduledExecutorService scheduler;

    public MyMonoDelay(T value, Duration delay, ScheduledExecutorService scheduler) {
        this.value = value;
        this.delay = delay;
        this.scheduler = scheduler;
    }
    public MyMonoDelay(T value, Duration delay) {
        this(value, delay, Schedulers.defaultScheduler());
    }
    @Override
    public void onRequest() {
        CompletableFuture<T> future = new CompletableFuture<>();
        this.scheduler.schedule(() -> future.complete(this.value), delay.toMillis(), TimeUnit.MILLISECONDS);
        future.thenRun(() -> {
            this.subscriber.onNext(this.value);
        });
    }
}
