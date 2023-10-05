package io.github.bensilvan.tinyreactorcore.specification;

public interface Publisher<T> {
    void subscribe(Subscriber<T> subscriber);
}
