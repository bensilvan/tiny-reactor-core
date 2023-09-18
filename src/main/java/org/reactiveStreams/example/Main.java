package org.reactiveStreams.example;

import org.reactiveStreams.example.Impl.publishers.MyFlux;
import org.reactiveStreams.example.Impl.StringsSubscriber;

import java.util.List;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        var subscriber = new StringsSubscriber();

        MyFlux.just(List.of(1,2,3))
                .map(Object::toString)
                .subscribeOn(Executors.newSingleThreadExecutor())
                .subscribe(subscriber);

    }
}
