package org.reactiveStreams.example.Impl.publishers;

import org.reactiveStreams.example.Impl.SimpleSubsciption;
import org.reactiveStreams.example.specification.Publisher;
import org.reactiveStreams.example.specification.Subscriber;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class StringsPublisher implements Publisher<String> {
    private final List<String> strings;
    private Subscriber<String> subscriber;
    private Integer index;

    private ExecutorService executorService;

    public StringsPublisher(List<String> strings){
        this.strings = strings;
        this.index = 0;
    }

    @Override
    public void subscribe(Subscriber<String> subscriber) {
        if (this.executorService == null) {
            this.subscriber = subscriber;
            var subscription = new SimpleSubsciption(this::produceStrings);
            subscriber.onSubscribe(subscription);
        } else {
            this.executorService.execute(() -> {
                this.subscriber = subscriber;
                var subscription = new SimpleSubsciption(this::produceStrings);
                subscriber.onSubscribe(subscription);
            });
        }
    }

    public StringsPublisher subscribeOn(ExecutorService executer){
        this.executorService = executer;
        return this;
    }

    public ProxyPublisher<String> publishOn(ExecutorService executor) {
        return new ProxyPublisher<>(this, executor);
    }

    public void produceStrings(Integer count) {
        for (var i = 0; i < count; i++) {
            if (index == this.strings.size()) {
                this.subscriber.onComplete();
            } else {
                this.subscriber.onNext(this.strings.get(index++));
            }
        }
    }
}
