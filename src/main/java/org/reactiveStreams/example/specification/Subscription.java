package org.reactiveStreams.example.specification;

public interface Subscription {
    void request(Integer count);
}
