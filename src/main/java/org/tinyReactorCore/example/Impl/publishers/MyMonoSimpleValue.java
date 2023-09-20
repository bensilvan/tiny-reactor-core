package org.tinyReactorCore.example.Impl.publishers;

public class MyMonoSimpleValue<T> extends MyMono<T>{
    private T value;
    public MyMonoSimpleValue(T value) {
        this.value = value;
    }
    @Override
    public void onRequest(Integer count) {
        this.subscriber.onNext(this.value);
    }
}
