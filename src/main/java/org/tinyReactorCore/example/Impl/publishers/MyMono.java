package org.tinyReactorCore.example.Impl.publishers;

import org.tinyReactorCore.example.Impl.DefaultSubscriber;
import org.tinyReactorCore.example.Impl.SingleValueSubscription;
import org.tinyReactorCore.example.specification.Publisher;
import org.tinyReactorCore.example.specification.Subscriber;

public abstract class MyMono<T> implements Publisher<T> {
    protected Subscriber<T> subscriber;
    @Override
    public void subscribe(Subscriber<T> subscriber) {
        this.subscriber = subscriber;
        this.subscriber.onSubscribe(new SingleValueSubscription(this::onRequest));
    }
    public abstract void onRequest(Integer count);

    public static <T> MyMonoSimpleValue<T> just(T value) {
        return new MyMonoSimpleValue<>(value);
    }

    public void subscribe() {
        this.subscribe(new DefaultSubscriber<>());
    }
}
