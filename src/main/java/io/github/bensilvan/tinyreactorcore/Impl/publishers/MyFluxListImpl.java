package io.github.bensilvan.tinyreactorcore.Impl.publishers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyFluxListImpl<T> extends MyFlux<T> {
    private final List<T> items;

    private final Integer size;

    private final AtomicInteger nextIndex;

    private final AtomicInteger currentIndex;

    private final ConcurrentHashMap<Integer, T> itemsToSendById;

    public MyFluxListImpl(List<T> list) {
        this.items = new ArrayList<>(list);
        this.size = list.size();
        this.itemsToSendById = new ConcurrentHashMap<>();
        this.nextIndex = new AtomicInteger(0);
        this.currentIndex = new AtomicInteger();
    }

    @Override
    public void onRequest(Long count) {
        for (var i = 0; i < count; i++) {
            var currentIndex = this.currentIndex.getAndIncrement();
            if (currentIndex >= this.size) {
                this.subscriber.onComplete();
                break;
            }
            this.itemsToSendById.computeIfAbsent(currentIndex, this.items::get);

            while (true) {
                var nextItem = this.itemsToSendById.remove(this.nextIndex.get());
                if (nextItem != null) {
                    this.subscriber.onNext(nextItem);
                    this.itemsToSendById.remove(this.nextIndex.getAndIncrement());
                } else {
                    break;
                }
            }
        }
    }
}
