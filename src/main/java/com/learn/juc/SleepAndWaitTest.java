package com.learn.juc;

public class SleepAndWaitTest {
    public static void main(String[] args) {
        new Thread(new Thread1()).start();
        try{
            Thread.sleep(10);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        new Thread(new Thread2()).start();
    }


    //sleep和wait的区别
    //sleep是Thread类的方法 主动让出cpu在sleep时间后CPU再回到该线程继续往下执行，不会释放同步锁
    //wait是Object的方法 暂时退让出同步资源锁，以便其他正在等待该资源的线程得到该资源执行，只有调用notify()方法之前wait()的线程
    //才会解除wait状态去参与资源锁的竞争

    public static class Thread1 implements Runnable{

        @Override
        public void run() {
            //由于 Thread1和下面Thread2内部run方法要用同一对象作为监视器，如果用this则Thread1和Threa2的this不是同一对象
            //所以用MultiThread.class这个字节码对象，当前虚拟机里引用这个变量时指向的都是同一个对象
            synchronized(JucTest.class){
                System.out.println("enter thread1 ...");
                System.out.println("thread1 is waiting");

                try{
                    //释放锁有两种方式：(1)程序自然离开监视器的范围，即离开synchronized关键字管辖的代码范围
                    //(2)在synchronized关键字管辖的代码内部调用监视器对象的wait()方法。这里使用wait方法
                    JucTest.class.wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                System.out.println("thread1 is going on ...");
                System.out.println("thread1 is being over!");
            }
        }
    }

    public static class Thread2 implements Runnable{

        @Override
        public void run() {
            //notify方法并不释放锁，即使thread2调用了下面的sleep方法休息10ms，但thread1仍然不会执行
            //因为thread2没有释放锁，所以Thread1得不到锁而无法执行
            synchronized(JucTest.class){
                System.out.println("enter thread2 ...");
                System.out.println("thread2 notify other thread can release wait status ...");
                JucTest.class.notify();
                System.out.println("thread2 is sleeping ten millisecond ...");

                try{
                    Thread.sleep(10);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                System.out.println("thread2 is going on ...");
                System.out.println("thread2 is being over!");
            }
        }
    }
}
