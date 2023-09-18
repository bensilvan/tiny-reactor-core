package org.reactiveStreams.example;

import org.reactiveStreams.example.Impl.publishers.MyFlux;
import org.reactiveStreams.example.Impl.StringsSubscriber;

import java.util.List;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        var subscriber = new StringsSubscriber();

        MyFlux.just(List.of(1,2,3))
                .publishOn(Executors.newSingleThreadExecutor())
                .map(x -> {
                    System.out.println("inside map: running on : " + Thread.currentThread());
                    return x.toString();
                })
                .subscribe(subscriber);
    }
}
