package io.github.bensilvan.tinyreactorcore.Impl.publishers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MyFluxArrayList<T> extends MyFlux<T>{

    private final AtomicLong requested;
    private final AtomicInteger index;
    private final ArrayList<T> items;

    private final AtomicBoolean isCancelled;

    public MyFluxArrayList(List<T> items) {
        this.index = new AtomicInteger(0);
        this.requested = new AtomicLong(0);
        this.items = new ArrayList<>(items);
        this.isCancelled = new AtomicBoolean(false);
    }

    @Override
    public void onRequest(Long count) {
        var initialRequests = this.requested.getAndAdd(count);
        if (initialRequests > 0) {
            return;
        }
        var sent = 0;
        while (true) {
            for (;sent < count && this.index.get() < this.items.size(); sent++,this.index.incrementAndGet()) {
                this.subscriber.onNext(this.items.get(this.index.get()));
            }

            var currentRequested = this.requested.addAndGet(-count);
            if (currentRequested == 0) {
                return;
            }

            sent = 0;
            count = currentRequested 
        }
    }
}
