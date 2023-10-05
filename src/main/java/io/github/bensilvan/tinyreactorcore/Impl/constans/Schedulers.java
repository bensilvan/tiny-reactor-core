package io.github.bensilvan.tinyreactorcore.Impl.constans;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Schedulers {
    private static final ScheduledExecutorService defaultScheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private static final ScheduledExecutorService workerPool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    public static ScheduledExecutorService getDefaultScheduler() {
        return defaultScheduler;
    }

    public static ScheduledExecutorService getWorkerPool() {
        return workerPool;
    }
}
