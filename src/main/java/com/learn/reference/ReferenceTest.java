package com.learn.reference;

import java.lang.ref.*;

public class ReferenceTest {
    public static void main(String[] args) {
        ReferenceQueue<Ref> queue = new ReferenceQueue<>();
        Ref ref = new Ref("hard");
        WeakReference<Ref> weak = new WeakReference<>(ref,queue);
        PhantomReference<Ref> phantom = new PhantomReference<>(ref,queue);
        SoftReference<Ref> soft = new SoftReference<>(new Ref("soft"),queue);

        System.out.println("引用内容:");
        System.out.println(weak.get());
        System.out.println(phantom.get());
        System.out.println(soft.get());

        System.gc();
        System.out.println("被回收的引用:");
        for(Reference r =null;(r=queue.poll())!=null;){
            System.out.println(r);
        }
    }
}
