package org.tinyReactorCore.example.Impl.constans;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Schedulers {
    private static final ScheduledExecutorService defaultScheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    public static ScheduledExecutorService defaultScheduler() {
        return defaultScheduler;
    }
}
