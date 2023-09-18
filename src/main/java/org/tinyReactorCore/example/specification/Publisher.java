package org.tinyReactorCore.example.specification;

public interface Publisher<T> {
    void subscribe(Subscriber<T> subscriber);
}
