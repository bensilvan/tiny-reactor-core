package org.tinyReactorCore.example.Impl.publishers;

import org.tinyReactorCore.example.Impl.DefaultSubscriber;
import org.tinyReactorCore.example.Impl.SimpleSubsciption;
import org.tinyReactorCore.example.specification.Publisher;
import org.tinyReactorCore.example.specification.Subscriber;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class MyFlux<T> implements Publisher<T> {
    protected Subscriber<T> subscriber;
    protected ExecutorService subscribeExecutor;

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        this.subscriber = subscriber;
        if (this.subscribeExecutor == null) {
            this.subscriber.onSubscribe(new SimpleSubsciption(this::onRequest));
        } else {
            this.subscribeExecutor.execute(() -> {
                this.subscriber.onSubscribe(new SimpleSubsciption(this::onRequest));
            });
        }
    }
    public abstract void onRequest(Integer count);

    public MyFlux<T> subscribeOn(ExecutorService executorService) {
        this.subscribeExecutor = executorService;
        return this;
    }
    public static <T> MyFluxListImpl<T> just(List<T> list) {
        return new MyFluxListImpl<>(list);
    }

    public <Treturn> MyFluxProxyMap<Treturn,T> map (Function<T,Treturn> mapper) {
        return new MyFluxProxyMap<>(this, mapper);
    }

    public <Treturn> MyFluxProxyFlatMap<Treturn,T> flatMap(Function<T,MyMono<Treturn>> publisherProducer, Integer concurrecny) {
        return new MyFluxProxyFlatMap<>(this, publisherProducer, concurrecny);
    }

    public <Treturn> MyFluxProxyFlatMap<Treturn,T> flatMap(Function<T,MyMono<Treturn>> publisherProducer) {
        return new MyFluxProxyFlatMap<>(this, publisherProducer);
    }

    public <Treturn> MyFluxProxyFlatMap<Treturn,T> concatMap(Function<T, MyMono<Treturn>> publisherProducer) {
        return new MyFluxProxyFlatMap<>(this, publisherProducer, 1);
    }

    public MyFluxProxyPublishOn<T> publishOn(ExecutorService executor) {
        return new MyFluxProxyPublishOn<>(this, executor);
    }

    public void subscribe(){
        this.subscribe(new DefaultSubscriber<>());
    }

    public void subscribe(Consumer<T> callback) {
        this.subscribe(new DefaultSubscriber<>(callback));
    }
}
