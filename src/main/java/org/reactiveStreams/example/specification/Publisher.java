package org.reactiveStreams.example.specification;

public interface Publisher<T> {
    void subscribe(Subscriber<T> subscriber);
}
