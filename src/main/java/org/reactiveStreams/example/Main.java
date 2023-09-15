package org.reactiveStreams.example;

import org.reactiveStreams.example.Impl.publishers.StringsPublisher;
import org.reactiveStreams.example.Impl.StringsSubscriber;

import java.util.List;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        var subscriber = new StringsSubscriber();
        var publisher = new StringsPublisher(List.of("1", "2", "3"));

        System.out.println("should publish the strings to the specified executor");
        publisher
                .publishOn(Executors.newSingleThreadExecutor())
                .subscribe(subscriber);
    }
}
