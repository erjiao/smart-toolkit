package com.bmsoft.toolkit.core.job;


import com.bmsoft.toolkit.core.factory.SmartToolkitThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author llk
 * @date 2020-01-17 02:41
 */
public abstract class AbstractJob implements Job {

    private ThreadFactory threadFactory;

    private int corePoolSize;

    private int period;

    private int initialDelay;

    private TimeUnit unit;

    private ScheduledExecutorService executorService;

    public AbstractJob(ThreadFactory threadFactory, int corePoolSize, int period, int initialDelay, TimeUnit unit) {
        this.threadFactory = threadFactory;
        this.corePoolSize = corePoolSize;
        this.period = period;
        this.initialDelay = initialDelay;
        this.unit = unit;
    }

    public AbstractJob(String name, int corePoolSize, int period, int initialDelay, TimeUnit unit) {
        this.threadFactory = SmartToolkitThreadFactory.createDaemon(name);
        this.corePoolSize = corePoolSize;
        this.period = period;
        this.initialDelay = initialDelay;
        this.unit = unit;
    }

    public AbstractJob(String name, int period, int initialDelay) {
        this.threadFactory = SmartToolkitThreadFactory.createDaemon(name);
        this.corePoolSize = 1;
        this.period = period;
        this.initialDelay = initialDelay;
        this.unit = TimeUnit.SECONDS;
    }

    @Override
    public void start() {
        executorService = Executors.newScheduledThreadPool(corePoolSize, threadFactory);
        Runnable task = this::task;
        executorService.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    @Override
    public void stop() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    protected abstract void task();
}
