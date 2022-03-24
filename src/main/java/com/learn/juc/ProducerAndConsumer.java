package com.learn.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 经典的生产者和消费者问题
 */
public class ProducerAndConsumer {
    public static void main(String[] args) {
        //synchronized隐式的锁
        ClerkSyn clerkSyn = new ClerkSyn();
        Producer producer = new Producer(clerkSyn);
        Consumer consumer = new Consumer(clerkSyn);
        new Thread(producer,"生产者-A").start();
        new Thread(producer,"生产者-B").start();
        new Thread(consumer,"消费者-C").start();
        new Thread(consumer,"消费者-D").start();
        //使用Lock unLock的方式显示的锁
        ClerkLock clerkLock = new ClerkLock();
        ProducerLock producerLock = new ProducerLock(clerkLock);
        ConsumerLock consumerLock = new ConsumerLock(clerkLock);
        new Thread(producerLock,"生产者Lock-A").start();
        new Thread(producerLock,"生产者Lock-B").start();
        new Thread(consumerLock,"消费者Lock-C").start();
        new Thread(consumerLock,"消费者Lock-D").start();
    }
}

//以下两个方法使用while而不是if进行判断的原因是因为在多个生产者消费者的情况下预期是product这个货物只会存在0或者1即有货缺货两种情况
//使用if条件的话可能存在当product都为1时两个生产者被同时阻塞 然后消费者C或者D其中一个消费掉后去通知解除阻塞
//但是使用if条件就不会在进行判断是否已经不缺货了导致两个生产者都去生产了数据
//使用while循环的话就可以避免 因为while循环会多判断一次
class ClerkSyn{
    private int product = 0;//共享数据
    public synchronized void get(){ //进货
        //if(product != 0){
        while(product != 0){
            System.out.println("产品已满");
            try
            {
                this.wait();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+":"+ (++product));
        this.notify();
    }
    public synchronized void sell(){//卖货
       // if (product == 0){
        while(product == 0){
            System.out.println("缺货");
            try
            {
                this.wait();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+":"+ (--product));
        this.notify();
    }
}

//生产者
class Producer implements Runnable{
    private ClerkSyn clerk;
    public Producer(ClerkSyn clerk){
        this.clerk = clerk;
    }
    @Override
    public void run() {
        for (int i = 0;i<20;i++){
            //System.out.println("Producer"+i);
            clerk.get();
        }
    }
}

//消费者
class Consumer implements Runnable{
    private ClerkSyn clerk;
    public Consumer(ClerkSyn clerk){
        this.clerk = clerk;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(200);//睡0.2秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0;i<20;i++){
            //System.out.println("Consumer"+i);
            clerk.sell();
        }
    }
}


class ClerkLock{
    private int product = 0;//共享数据
    //使用Lock显示锁
    final Lock lock=new ReentrantLock();
    final Condition condition  = lock.newCondition();
    public void get(){ //进货
        lock.lock();
        try
        {
            while(product != 0){
                System.out.println("产品已满");
                condition.await();
            }
            System.out.println(Thread.currentThread().getName()+":"+ (++product));
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }
    public void sell(){//卖货
        lock.lock();
        try
        {
            while(product == 0){
                System.out.println("缺货");
                condition.await();
            }
            System.out.println(Thread.currentThread().getName()+":"+ (--product));
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }
}

//生产者
class ProducerLock implements Runnable{
    private ClerkLock clerkLock;
    public ProducerLock(ClerkLock clerkLock){
        this.clerkLock = clerkLock;
    }
    @Override
    public void run() {
        for (int i = 0;i<20;i++){
            clerkLock.get();
        }
    }
}

//消费者
class ConsumerLock implements Runnable{
    private ClerkLock clerkLock;
    public ConsumerLock(ClerkLock clerkLock){
        this.clerkLock = clerkLock;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(200);//睡0.2秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0;i<20;i++){
            clerkLock.sell();
        }
    }
}
