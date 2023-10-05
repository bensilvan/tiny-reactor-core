package io.github.bensilvan.tinyreactorcore.Impl.publishers;

import io.github.bensilvan.tinyreactorcore.Impl.SimpleSubsciption;
import io.github.bensilvan.tinyreactorcore.specification.Publisher;
import io.github.bensilvan.tinyreactorcore.specification.Subscriber;

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

    public void produceStrings(Integer count) {
        for (var i = 0; i < count; i++){
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
}
