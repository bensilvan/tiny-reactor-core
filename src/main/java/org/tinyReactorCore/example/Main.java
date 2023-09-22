package org.tinyReactorCore.example;

import org.tinyReactorCore.example.Impl.publishers.MyFlux;
import org.tinyReactorCore.example.Impl.publishers.MyMono;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        MyMono.fromFuture(delayAndGetValue(Duration.ofSeconds(10), "hello", scheduler))
                .subscribe(x -> System.out.println("got " + x + " on thread: " + Thread.currentThread()));
    }

    private static CompletableFuture<String> delayAndGetValue(Duration delayInSecond, String value, ScheduledExecutorService schedul) {
        var future = new CompletableFuture<String>();
        schedul.schedule(() -> future.complete(value), delayInSecond.toSeconds(), TimeUnit.SECONDS);

        return future;
    }
}
