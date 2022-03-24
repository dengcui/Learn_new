package com.learn.juc;

import java.util.concurrent.*;

public class ThreadPool {
    public static void main(String[] args) {
//        ExecutorService pool = Executors.newSingleThreadExecutor();
//        pool.execute(new MyThread());
//        pool.execute(new MyThread());
//        pool.execute(new MyThread());
//        pool.execute(new MyThread());
//        pool.shutdown();
//
//        ExecutorService fixedPool = Executors.newFixedThreadPool(2);
//        fixedPool.execute(new MyThread());
//        fixedPool.execute(new MyThread());
//        fixedPool.execute(new MyThread());
//        fixedPool.execute(new MyThread());
//        fixedPool.shutdown();
//
//        ExecutorService cachedPool = Executors.newCachedThreadPool();
//        cachedPool.execute(new MyThread());
//        cachedPool.execute(new MyThread());
//        cachedPool.execute(new MyThread());
//        cachedPool.execute(new MyThread());
//        cachedPool.shutdown();

        ScheduledThreadPoolExecutor scheduledPool = new ScheduledThreadPoolExecutor(2);
        scheduledPool.scheduleAtFixedRate(new MyThread(),0,3000,TimeUnit.MILLISECONDS);
        scheduledPool.scheduleAtFixedRate(new MyThread(),0,2000,TimeUnit.MILLISECONDS);
        //scheduledPool.shutdown();
    }
}

class MyThread extends Thread
{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId()+"run,timestamp:"+System.currentTimeMillis());
    }
}
