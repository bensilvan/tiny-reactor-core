package org.tinyReactorCore.example.Impl.publishers;

import org.tinyReactorCore.example.Impl.InnerMonoSubscriber;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Function;

// TODO: support all kinds of publishers
// TODO: check when publisher is actually async and do the thread sync using locks/atomics
public class MyFluxProxyFlatMap<Treturn,Tparam> extends MyFluxProxy<Treturn,Tparam>{
    private final Function<Tparam,MyMono<Treturn>> publisherProducer;
    private final Integer concurrency;

    private  Integer currentlyRunning;
    private final Integer prefetch;
    private final Queue<Tparam> prefetchedQueue;
    private Integer downstreamRequested;

    public MyFluxProxyFlatMap(MyFlux<Tparam> actualPublisher, Function<Tparam, MyMono<Treturn>> producer, Integer concurrency, Integer prefetch) {
        super(actualPublisher);
        this.publisherProducer = producer;
        this.concurrency = concurrency;
        this.prefetch = prefetch;
        this.downstreamRequested = 0;
        this.currentlyRunning = 0;
        this.prefetchedQueue = new ArrayBlockingQueue<Tparam>(prefetch);
    }

    @Override
    public void onNext(Tparam item) {
        this.prefetchedQueue.add(item);
        if (this.currentlyRunning < this.concurrency) {
            var newItem = this.prefetchedQueue.poll();
            this.currentlyRunning += 1;
            runNewPublisher(newItem);
        }
    }

    private void runNewPublisher(Tparam item) {
        var publisher = this.publisherProducer.apply(item);
        publisher.subscribe(new InnerMonoSubscriber<>(this::onInnerPublisherResult));
    }

    private void onInnerPublisherResult(Treturn res) {
        this.currentlyRunning -= 1;
        this.subscriber.onNext(res);
        if (this.prefetchedQueue.size() > 0) {
            runNewPublisher(this.prefetchedQueue.poll());
        }
        if (this.downstreamRequested > 0) {
            this.downstreamRequested -= 1;
            this.upperSubscription.request(1);
        }

    }

    @Override
    public void onRequest(Integer count) {
        if (this.prefetchedQueue.size() == 0) {
            var toRquesteCount = count >= this.prefetch ? this.prefetch : count;
            this.downstreamRequested += count;
            this.upperSubscription.request(toRquesteCount);
        } else {
            this.downstreamRequested += count;
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Exception e) {

    }
}
