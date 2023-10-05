package io.github.bensilvan.tinyreactorcore.specification;

public interface Subscriber<T> {
     void onNext(T item);
     void onComplete();
     void onError(Exception e);
     void onSubscribe(Subscription subscription);
}
