package org.reactiveStreams.example;

import org.reactiveStreams.example.Impl.publishers.ErrorPublisher;
import org.reactiveStreams.example.Impl.publishers.StringsPublisher;
import org.reactiveStreams.example.Impl.StringsSubscriber;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        var subscriber = new StringsSubscriber();
        var publisher = new StringsPublisher(List.of("1","2","3"));
        var errorPublisher = new ErrorPublisher(List.of("1","2","3"));

        System.out.println("Running the simple producer: (should be got 1, got 2, got 3, got complete)");
        publisher.subscribe(subscriber);
        System.out.println("Running the error producer: (should be got 1, got 2, got error)");
        errorPublisher.subscribe(subscriber);
    }
}
