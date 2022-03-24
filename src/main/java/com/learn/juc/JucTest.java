package com.learn.juc;

import java.util.concurrent.locks.LockSupport;

public class JucTest {
    public static volatile int val = 0;
    public static void main(String[] args) {
        for (int i=0 ;i <10; i++){
            new Thread(()->{
                for(int j=0;j<1000;j++){
                    val++;
                }
            }).start();
        }
        //IDE本身会有一个监控线程 所以除了主线程之外这里应该大于2
        while(Thread.activeCount()>2)
            Thread.yield();
        System.out.println(val);
    }
}


