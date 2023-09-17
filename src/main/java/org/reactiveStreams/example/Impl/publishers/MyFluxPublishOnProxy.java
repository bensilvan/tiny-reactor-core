package org.reactiveStreams.example.Impl.publishers;

import org.reactiveStreams.example.Impl.SimpleSubsciption;
import org.reactiveStreams.example.specification.Publisher;
import org.reactiveStreams.example.specification.Subscriber;
import org.reactiveStreams.example.specification.Subscription;

import java.util.concurrent.ExecutorService;

public class MyFluxPublishOnProxy<T> implements Publisher<T>, Subscriber<T> {
    private final MyFlux<T> actualPublisher;
    private final ExecutorService publishExecutor;
    private Subscriber<T> subscriber;
    private Subscription parentSubscription;
    private ExecutorService subscribeExecutor;

    public MyFluxPublishOnProxy(Subscriber<T> subscriber, MyFlux<T> actualPublisher, ExecutorService publishExecutor){
        this.subscriber = subscriber;
        this.actualPublisher = actualPublisher;
        this.publishExecutor = publishExecutor;
    }

    public void ProxyToParent(Integer count) {
        this.parentSubscription.request(count);
    }

    /*
        TODO: make the publish function somehow private or protected to send it by the subscription
            and still not access by public
     */
    @Override
    public void subscribe(Subscriber<T> subscriber) {
        this.subscriber = subscriber;
        if (this.subscribeExecutor == null) {
            this.actualPublisher.subscribe(this);
        } else {
            this.actualPublisher.subscribeOn(this.subscribeExecutor).subscribe(this);
        }
    }

    @Override
    public void onNext(T item) {
        this.publishExecutor.execute(() -> {
            this.subscriber.onNext(item);
        });
    }

    @Override
    public void onComplete() {
        this.publishExecutor.execute(()->{
            this.subscriber.onComplete();
        });
    }

    @Override
    public void onError(Exception e) {
        this.publishExecutor.execute(()-> {
            this.subscriber.onError(e);
        });
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.parentSubscription = subscription;
        this.subscriber.onSubscribe(new SimpleSubsciption(this::ProxyToParent));
    }

    public MyFluxPublishOnProxy<T> subscribeOn(ExecutorService executor) {
        this.subscribeExecutor = executor;
        return this;
    }
}
