package org.reactiveStreams.example.Impl.publishers;

import org.reactiveStreams.example.Impl.SimpleSubsciption;
import org.reactiveStreams.example.specification.Publisher;
import org.reactiveStreams.example.specification.Subscriber;

import java.util.List;

public class ErrorPublisher implements Publisher<String> {
    private final List<String> strings;
    private Subscriber<String> subscriber;
    private Integer index;

    private boolean isSubsciptionTerminated;

    public ErrorPublisher(List<String> strings){
        this.strings = strings;
        this.index = 0;
        this.isSubsciptionTerminated = false;
    }

    @Override
    public void subscribe(Subscriber<String> subscriber) {
        this.subscriber = subscriber;
        var subscription = new SimpleSubsciption(this::produceStrings);
        subscriber.onSubscribe(subscription);
    }

    public void produceStrings() {
        if (!this.isSubsciptionTerminated) {
            if (index == this.strings.size()) {
                this.subscriber.onComplete();
                this.isSubsciptionTerminated = true;
            } else if (index == 2) {
                this.subscriber.onError(new Exception("Some error producing the value"));
                this.isSubsciptionTerminated = true;
            } else {
                this.subscriber.onNext(this.strings.get(index++));
            }
        }
    }
}
