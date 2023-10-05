package io.github.bensilvan.tinyreactorcore.Impl.publishers;

import io.github.bensilvan.tinyreactorcore.Impl.InnerMonoSubscriber;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/*
    currently flatMap only support myNono (publisher that publish only one item)
    that is the reason it does not have prefetch yet.
 */
public class MyFluxProxyFlatMap<Treturn,Tparam> extends MyFluxProxy<Treturn,Tparam>{
    private final Function<Tparam,MyMono<Treturn>> publisherProducer;
    private final Integer concurrency;
    private final AtomicInteger currentlyRunning;
    private final AtomicInteger downstreamRequested;

    public MyFluxProxyFlatMap(MyFlux<Tparam> actualPublisher, Function<Tparam, MyMono<Treturn>> producer, Integer concurrency) {
        super(actualPublisher);
        this.publisherProducer = producer;
        this.concurrency = concurrency;
        this.downstreamRequested = new AtomicInteger(0);
        this.currentlyRunning = new AtomicInteger(0);
    }

    public MyFluxProxyFlatMap(MyFlux<Tparam> actualPublisher, Function<Tparam, MyMono<Treturn>> producer) {
        this(actualPublisher, producer, 10);
    }


    @Override
    public void onNext(Tparam item) {
        if (this.currentlyRunning.getAndIncrement() < this.concurrency) {
            runNewPublisher(item);
        }
    }

    private void checkAndRequestFromUpper() {
        if (this.downstreamRequested.getAndDecrement() >= 1 ) {
            this.upperSubscription.request(1);
        }
    }

    private void runNewPublisher(Tparam item) {
        var publisher = this.publisherProducer.apply(item);
        publisher.subscribe(new InnerMonoSubscriber<>(this::onInnerPublisherResult));
    }

    private void onInnerPublisherResult(Treturn res) {
        this.currentlyRunning.decrementAndGet();
        this.subscriber.onNext(res);
        checkAndRequestFromUpper();
    }

    @Override
    public void onRequest(Integer count) {
        if (this.currentlyRunning.get() == 0) {
            this.downstreamRequested.set(count - this.concurrency);
            this.upperSubscription.request(this.concurrency);
        } else {
            this.downstreamRequested.getAndAdd(count);
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Exception e) {

    }
}
