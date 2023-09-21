package org.tinyReactorCore.example;

import org.tinyReactorCore.example.Impl.publishers.MyFlux;
import org.tinyReactorCore.example.Impl.publishers.MyMono;

import java.util.List;
import java.util.concurrent.Executors;

public class Main {
    // TODO: implement a parallel flux or a way to process by parallel
    // TODO: make sure that the publisher will not call onNext after it called onComplete
    public static void main(String[] args) {
        MyFlux.just(List.of(1,2,3))
                .flatMap(x -> {
                    System.out.println("got into flatMap with: " + x);
                    return MyMono.just(x);
                },1,1)
                .subscribe();
    }
}
