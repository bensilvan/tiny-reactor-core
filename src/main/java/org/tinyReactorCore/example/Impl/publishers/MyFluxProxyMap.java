package org.tinyReactorCore.example.Impl.publishers;

import java.util.function.Function;

public class MyFluxProxyMap<Treturn, Tparam> extends MyFluxProxy<Treturn,Tparam>{
    private final Function<Tparam,Treturn> mapper;

    public MyFluxProxyMap(MyFlux<Tparam> publisher, Function<Tparam,Treturn> mapper) {
        super(publisher);
        this.mapper = mapper;
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
    public void onRequest(Integer count) {
        this.upperSubscription.request(count);
    }
}
