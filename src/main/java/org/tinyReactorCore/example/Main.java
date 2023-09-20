package org.tinyReactorCore.example;

import org.tinyReactorCore.example.Impl.publishers.MyFlux;

import java.util.List;
import java.util.concurrent.Executors;

public class Main {
    // TODO: implement a parallel flux or a way to process by parallel
    // TODO: make sure that the publisher will not call onNext after it called onComplete
    public static void main(String[] args) {
        MyFlux.just(List.of(1,2,3))
                .publishOn(Executors.newFixedThreadPool(3))
                .map(x -> {
                    System.out.println("starting map for: " + x + " on " + Thread.currentThread());
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("finish for: " + x + " on " +  Thread.currentThread());
                    return x.toString();
                })
                .subscribe();
    }
}
