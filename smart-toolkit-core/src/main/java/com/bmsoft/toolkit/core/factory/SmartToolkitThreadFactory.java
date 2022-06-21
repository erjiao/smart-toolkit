package com.bmsoft.toolkit.core.factory;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程工厂
 *
 * @author llk
 * @date 2020-04-16 10:09
 */
@Slf4j
public final class SmartToolkitThreadFactory implements ThreadFactory {


    private static final AtomicLong THREAD_NUMBER = new AtomicLong(1);

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("smart-toolkit");

    private final boolean daemon;

    private final String namePrefix;

    private SmartToolkitThreadFactory(final String namePrefix, final boolean daemon) {
        this.namePrefix = namePrefix;
        this.daemon = daemon;
    }

    /**
     * create custom thread factory.
     *
     * @param namePrefix prefix
     * @param daemon     daemon
     * @return {@linkplain ThreadFactory}
     */
    public static ThreadFactory create(final String namePrefix, final boolean daemon) {
        return new SmartToolkitThreadFactory(namePrefix, daemon);
    }


    public static ThreadFactory createDaemon(final String namePrefix) {
        return create(namePrefix, true);
    }


    @Override
    public Thread newThread(final Runnable runnable) {
        Thread thread = new Thread(THREAD_GROUP, runnable,
                THREAD_GROUP.getName() + "-" + namePrefix + "-" + THREAD_NUMBER.getAndIncrement());
        thread.setDaemon(daemon);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }

}
