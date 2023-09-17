package org.reactiveStreams.example.Impl.publishers;

import org.reactiveStreams.example.Impl.SimpleSubsciption;
import org.reactiveStreams.example.specification.Publisher;
import org.reactiveStreams.example.specification.Subscriber;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class MyFlux<T> implements Publisher<T> {
    private Subscriber<T> subscriber;
    private final List<T> items;
    private Integer index;
    private ExecutorService subscribeExecution;

    private MyFlux (List<T> items) {
        this.items = items;
        this.index = 0;
    }

    public static <T> MyFlux<T> create(List<T> items) {
        return new MyFlux<>(items);
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        this.subscriber = subscriber;
        if (this.subscribeExecution == null) {
            this.subscriber.onSubscribe(new SimpleSubsciption(this::OnRequest));
        } else {
            this.subscribeExecution.execute(() -> {
                this.subscriber.onSubscribe(new SimpleSubsciption(this::OnRequest));
            });
        }
    }
    public void OnRequest(Integer count) {
        for (var i = 0; i < count ; i++) {
            if(this.index < this.items.size()) {
                this.subscriber.onNext(this.items.get(this.index++));
            } else {
                this.subscriber.onComplete();
            }
        }
    }

    public MyFlux<T> subscribeOn(ExecutorService executer) {
        this.subscribeExecution = executer;
        return this;
    }

    public MyFluxPublishOnProxy<T> publishOn(ExecutorService executor){
        return new MyFluxPublishOnProxy<>(this.subscriber, this, executor);
    }
}
