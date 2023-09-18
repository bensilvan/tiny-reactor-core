package org.reactiveStreams.example.Impl.publishers;

import java.util.function.Function;

public class MyFluxProxyMap<Treturn, Tparam> extends MyFluxProxy<Treturn,Tparam>{
    private final Function<Tparam,Treturn> mapper;

    public MyFluxProxyMap(MyFlux<Tparam> publisher, Function<Tparam,Treturn> mapper) {
        super(publisher);
        this.mapper = mapper;
    }
    @Override
    public Treturn operate(Tparam tparam) {
        return mapper.apply(tparam);
    }
}
