package org.tinyReactorCore.example;

import org.tinyReactorCore.example.Impl.publishers.MyFlux;
import org.tinyReactorCore.example.Impl.publishers.MyMono;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args){
        MyFlux.just(List.of(1,2,3,4,5,6,7,8,9,10))
                .flatMap(msg -> {
                    System.out.println("call an async method for: " + msg + " on Thread: " + Thread.currentThread());
                    return MyMono.delay(msg, Duration.ofSeconds(5)); // represent async code
                }, 3) // concurrency set to 3
                .subscribeOn(Executors.newSingleThreadExecutor())
                .subscribe(msg -> {
                  //  System.out.println("Finish processing " + msg + " on thread: " + Thread.currentThread());
                });
    }
}
