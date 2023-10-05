package io.github.bensilvan.tinyreactorcore.Impl.publishers;

public class MyMonoSimpleValue<T> extends MyMono<T>{
    private final T value;
    public MyMonoSimpleValue(T value) {
        this.value = value;
    }
    @Override
    public void onRequest() {
        this.subscriber.onNext(this.value);
    }
}
