package com.learn.juc;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 可重入锁
 */
public class MatexLock {
    private AtomicReference<Thread> owner = new AtomicReference<>();
    private LinkedList<Thread> waiterQueue = new LinkedList<>();
    private volatile AtomicInteger state = new AtomicInteger(0);

    public void lock(){
        Thread current = Thread.currentThread();
        if(owner.get() == current){
            state.incrementAndGet();
            return;
        }
        //没有任何线程持有锁时，让当前线程持有锁，反之加入等待对列中
        if(!owner.compareAndSet(null,current)){
            waiterQueue.add(current);
            LockSupport.park();
        }
    }

    public void unlock(){
       if(Thread.currentThread()!=owner.get()){
           throw new RuntimeException();
       }
       if(state.get() > 0){
           state.decrementAndGet();
           return;
       }
       //等待对列里有值时，释放指定队列，更改持有锁的线程，反之则清空持有锁的线程
       if(waiterQueue.size() > 0){
           Thread t = waiterQueue.poll();
           owner.set(t);
           LockSupport.unpark(t);
       } else{
         owner.set(null);
       }
    }
}
