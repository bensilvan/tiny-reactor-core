package org.reactiveStreams.example.Impl.publishers;

import org.reactiveStreams.example.Impl.SimpleSubsciption;
import org.reactiveStreams.example.specification.Publisher;
import org.reactiveStreams.example.specification.Subscriber;
import org.reactiveStreams.example.specification.Subscription;

import java.util.concurrent.ExecutorService;
import java.util.function.Function;

// TODO: make some base class for middleware publisher because there is a lot common with the pusblishOn
public class MapOperator<Treturn,Tparam> implements Publisher<Treturn>, Subscriber<Tparam> {
    private Subscriber<Treturn> subscriber;
    private final MyFlux<Tparam> actualPublisher; // TODO: consider make a new interface
    private ExecutorService subscribeExecutor;
    private ExecutorService publishExecutor;
    private Subscription actualSubscription;
    private final Function<Tparam,Treturn> mapper;

    public MapOperator(MyFlux<Tparam> actualPublisher, Function<Tparam,Treturn> mapper) {
        this.actualPublisher = actualPublisher;
        this.mapper = mapper;
    }
    @Override
    public void subscribe(Subscriber<Treturn> subscriber) {
        this.subscriber = subscriber;
        if (this.subscribeExecutor == null) {
            this.actualPublisher.subscribe(this);
        } else {
            this.actualPublisher.subscribeOn(this.subscribeExecutor).subscribe(this);
        }
    }

    public void OnRequest(Integer count){
        this.actualSubscription.request(count);
    }

    @Override
    public void onNext(Tparam item) {
        this.subscriber.onNext(this.mapper.apply(item));
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
        this.actualSubscription = subscription;
        this.subscriber.onSubscribe(new SimpleSubsciption(this::OnRequest));
    }

    public MapOperator<Treturn, Tparam> subscribeOn(ExecutorService executor) {
        this.subscribeExecutor = executor;
        return this;
    }

    /*
         TODO: create a basic class that common to publishON and operators
    public MyFluxPublishOnProxy<Treturn> publishOn(ExecutorService executor) {
        return new MyFluxPublishOnProxy<Treturn>(this.subscriber, this, executor)
    }

     */
}
