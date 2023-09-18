package org.tinyReactorCore.example.Impl.publishers;

import org.tinyReactorCore.example.Impl.SimpleSubsciption;
import org.tinyReactorCore.example.specification.Publisher;
import org.tinyReactorCore.example.specification.Subscriber;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

// this abstraction will contain all the common function of the publisher and processor like .map .publishOn etc...
public abstract class MyFlux<T> implements Publisher<T> {
    protected Subscriber<T> subscriber;
    protected ExecutorService subscribeExecutor;

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        this.subscriber = subscriber;
        if (this.subscribeExecutor == null) {
            this.subscriber.onSubscribe(new SimpleSubsciption(this::OnRequest));
        } else {
            this.subscribeExecutor.execute(() -> {
                this.subscriber.onSubscribe(new SimpleSubsciption(this::OnRequest));
            });
        }
    }
    public abstract void OnRequest(Integer count);

    public MyFlux<T> subscribeOn(ExecutorService executorService) {
        this.subscribeExecutor = executorService;
        return this;
    }
    public static <T> MyFluxListImpl<T> just(List<T> list) {
        return new MyFluxListImpl<>(list);
    }

    public <Treturn> MyFluxProxy<Treturn,T> map (Function<T,Treturn> mapper) {
        return new MyFluxProxyMap<>(this, mapper);
    }

    public MyFluxProxyPublishOn<T> publishOn(ExecutorService executor) {
        return new MyFluxProxyPublishOn<>(this, executor);
    }
}
