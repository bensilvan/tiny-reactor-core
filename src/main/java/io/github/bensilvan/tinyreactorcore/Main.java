package io.github.bensilvan.tinyreactorcore;

import io.github.bensilvan.tinyreactorcore.Impl.publishers.MyMono;
import io.github.bensilvan.tinyreactorcore.Impl.constans.Schedulers;
import io.github.bensilvan.tinyreactorcore.Impl.publishers.MyFlux;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        MyFlux.just(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
//                .publishOn(Schedulers.getWorkerPool())
//                .map(msg -> {
//                    System.out.println("running a heavy cpu operation on: " + msg + " on thread" + Thread.currentThread());
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        System.out.println("Exception on thread.sleep " + e.getMessage());
//                    }
//                    return msg;
//                })
                .flatMap(msg -> {
                    System.out.println("start async call after the heavy cpu operation: " + msg + " on thread " + Thread.currentThread());
                    return MyMono.delay(msg, Duration.ofSeconds(4)); // simulate async call for db \ api
                }, 3) // 3 async calls can run in the same time concurrently
                .subscribeOn(Executors.newSingleThreadExecutor())
                .subscribe();
    }
}
