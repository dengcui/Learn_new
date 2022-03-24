package com.learn.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MultiThreadTest {
    public static void main(String[] args) throws InterruptedException {
        ExtendThread extendThread = new ExtendThread();
        extendThread.start();
        System.out.println("我是主线程");
        new Thread(new ImplThreads()).start();
        //创建实现callable接口的类对象
        ImplCallableThreads implCallableThreads = new ImplCallableThreads();
        //将此callable的对象作为参数传入到FutureTask构造器中,创建FutureTask的对象
        FutureTask futureTask = new FutureTask(implCallableThreads);
        //将FutureTask对象作为参数传递到Thread类的构造器中,创建Thread对象,并调用start()方法 FutureTask继承了Runnable接口
        new Thread(futureTask).start();

        try {
            //get()方法返回值即为FutureTask构造器参数callable实现类重写的call方法的返回值
            Object sum = futureTask.get();
            System.out.println("获取Callable返回的值:"+sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //主线程执行完毕之后守护线程自动终止自己的生命周期
        Thread daemonThread = new Thread(()->{
            while(true){
                try {
                    Thread.sleep(1000);
                    System.out.println("我是子线程(用户线程.I am running");
                } catch (Exception e) {
                }
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
        Thread.sleep(3000);
        System.out.println("主线程执行完毕");
    }

    /**
     * 通过集成的方式实现多线程
     */
    public static class ExtendThread extends Thread{
        @Override
        public void run(){
            System.out.println("通过继承方式实现");
        }
    }

    /**
     * 通过实现接口的方式实现多线程
     */
    public static class ImplThreads implements Runnable{
        @Override
        public void run(){
            System.out.println("通过实现接口方式");
        }
    }

    /**
     * 通过实现接口的方式实现多线程
     */
    public static class ImplCallableThreads implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("通过实现Callable接口方式");
            return 10;
        }
    }

}
