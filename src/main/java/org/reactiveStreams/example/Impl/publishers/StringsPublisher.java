package org.reactiveStreams.example.Impl.publishers;

import org.reactiveStreams.example.Impl.SimpleSubsciption;
import org.reactiveStreams.example.specification.Publisher;
import org.reactiveStreams.example.specification.Subscriber;

import java.util.List;

public class StringsPublisher implements Publisher<String> {
    private final List<String> strings;
    private Subscriber<String> subscriber;
    private Integer index;

    public StringsPublisher(List<String> strings){
        this.strings = strings;
        this.index = 0;
    }

    @Override
    public void subscribe(Subscriber<String> subscriber) {
        this.subscriber = subscriber;
        var subscription = new SimpleSubsciption(this::produceStrings);
        subscriber.onSubscribe(subscription);
    }

    public void produceStrings() {
        if (index == this.strings.size()) {
            this.subscriber.onComplete();
        } else {
            this.subscriber.onNext(this.strings.get(index++));
        }

    }


}
