package com.learn.thread;

import com.learn.juc.SleepAndWaitTest;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * @author cuideng
 * @className: SimpleThreadTest
 * @description //TODO
 * @date 2022/6/13 9:39
 */
public class SimpleThreadTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,15,0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

    }
}
