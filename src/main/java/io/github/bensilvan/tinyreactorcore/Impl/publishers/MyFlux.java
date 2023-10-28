package io.github.bensilvan.tinyreactorcore.Impl.publishers;

import io.github.bensilvan.tinyreactorcore.Impl.DefaultSubscriber;
import io.github.bensilvan.tinyreactorcore.Impl.SimpleSubsciption;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class MyFlux<T> implements Publisher<T> {
    protected Subscriber<? super T> subscriber;
    protected ExecutorService subscribeExecutor;

    @Override
    public void subscribe(Subscriber<? super T> s) {
        this.subscriber = s;
        if (this.subscribeExecutor == null) {
            this.subscriber.onSubscribe(new SimpleSubsciption(this::onRequest));
        } else {
            this.subscribeExecutor.execute(() -> {
                this.subscriber.onSubscribe(new SimpleSubsciption(this::onRequest));
            });
        }
    }
    public abstract void onRequest(Long count);

    public MyFlux<T> subscribeOn(ExecutorService executorService) {
        this.subscribeExecutor = executorService;
        return this;
    }
    public static <T> MyFluxArrayList<T> just(List<T> list) {
        return new MyFluxArrayList<>(list);
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

    public MyFluxSizedBuffer<T> buffer(Integer maxSize) {
        return new MyFluxSizedBuffer<>(this, maxSize);
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
