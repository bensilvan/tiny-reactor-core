package org.reactiveStreams.example.Impl.publishers;

import org.reactiveStreams.example.Impl.SimpleSubsciption;
import org.reactiveStreams.example.specification.Publisher;
import org.reactiveStreams.example.specification.Subscriber;
import org.reactiveStreams.example.specification.Subscription;

import java.util.concurrent.ExecutorService;

public class ProxyPublisher<T> implements Publisher<T>,Subscriber<T> {

    private final Publisher<T> parent;
    private final ExecutorService executor;
    private Subscriber<T> subscriber;
    private Subscription parentSubscription;

    public ProxyPublisher(Publisher<T> parent, ExecutorService executor){
        this.parent = parent;
        this.executor = executor;
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        this.subscriber = subscriber;
        this.parent.subscribe(this);
    }

    @Override
    public void onNext(T item) {
        this.executor.execute(() -> {
            this.subscriber.onNext(item);
        });
    }

    @Override
    public void onComplete() {
        this.executor.execute(()-> {
            this.subscriber.onComplete();
        });
    }

    @Override
    public void onError(Exception e) {
        this.executor.execute(() -> {
            this.subscriber.onError(e);
        });

    }

    public void propegateToParent(Integer count){
        this.parentSubscription.request(count);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.parentSubscription = subscription;
        this.subscriber.onSubscribe(new SimpleSubsciption(this::propegateToParent));
    }
}
