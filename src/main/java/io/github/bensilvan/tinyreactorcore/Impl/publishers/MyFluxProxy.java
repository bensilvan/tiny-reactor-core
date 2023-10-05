package io.github.bensilvan.tinyreactorcore.Impl.publishers;

import io.github.bensilvan.tinyreactorcore.Impl.SimpleSubsciption;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class MyFluxProxy<Treturn,Tparam> extends MyFlux<Treturn> implements Subscriber<Tparam> {
    private final MyFlux<Tparam> upperPublisher;
    protected Subscription upperSubscription;
    public MyFluxProxy(MyFlux<Tparam> actualPublisher) {
        this.upperPublisher = actualPublisher;
    }

    @Override
    public void subscribe(Subscriber<? super Treturn> s) {
        this.subscriber = s;
        if (this.subscribeExecutor == null) {
            this.upperPublisher.subscribe(this);
        } else {
            this.upperPublisher.subscribeOn(this.subscribeExecutor).subscribe(this);
        }
    }

    @Override
    public abstract void onRequest(Long count);
    @Override
    public void onSubscribe(Subscription subscription) {
        this.upperSubscription = subscription;
        this.subscriber.onSubscribe(new SimpleSubsciption(this::onRequest));
    }
}
