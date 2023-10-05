package io.github.bensilvan.tinyreactorcore.Impl.publishers;

import io.github.bensilvan.tinyreactorcore.specification.Subscriber;
import io.github.bensilvan.tinyreactorcore.Impl.SimpleSubsciption;
import io.github.bensilvan.tinyreactorcore.specification.Subscription;

public abstract class MyFluxProxy<Treturn,Tparam> extends MyFlux<Treturn> implements Subscriber<Tparam> {
    private final MyFlux<Tparam> upperPublisher;
    protected Subscription upperSubscription;
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
    public abstract void onRequest(Integer count);
    @Override
    public void onSubscribe(Subscription subscription) {
        this.upperSubscription = subscription;
        this.subscriber.onSubscribe(new SimpleSubsciption(this::onRequest));
    }
}
