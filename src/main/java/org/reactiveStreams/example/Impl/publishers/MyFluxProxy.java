package org.reactiveStreams.example.Impl.publishers;

import org.reactiveStreams.example.Impl.SimpleSubsciption;
import org.reactiveStreams.example.specification.Subscriber;
import org.reactiveStreams.example.specification.Subscription;

public abstract class MyFluxProxy<Treturn,Tparam> extends MyFlux<Treturn> implements Subscriber<Tparam> {
    private final MyFlux<Tparam> upperPublisher;
    private Subscription upperSubscription;
    public MyFluxProxy(MyFlux<Tparam> actualPublisher) {
        this.upperPublisher = actualPublisher;
    }

    @Override
    public void subscribe(Subscriber<Treturn> subscriber) {
        this.subscriber = subscriber;
        if (this.subscribeExecutor == null) {
            this.upperPublisher.subscribe(this);
        } else {
            this.upperPublisher.subscribeOn(this.subscribeExecutor).subscribe(this);
        }
    }

    @Override
    public void OnRequest(Integer count) {
        this.upperSubscription.request(count);
    }
    public abstract Treturn operate(Tparam tparam);

    @Override
    public void onNext(Tparam item) {
        this.subscriber.onNext(this.operate(item));
    }

    @Override
    public void onComplete() {
        this.subscriber.onComplete();
    }

    @Override
    public void onError(Exception e) {
        this.subscriber.onError(e);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.upperSubscription = subscription;
        this.subscriber.onSubscribe(new SimpleSubsciption(this::OnRequest));
    }
}
