package com.learn.proxy;

import java.lang.reflect.Proxy;

public class SubjectTest {
    public static void main(String[] args) throws Exception {
        //通过设置参数，将生成的代理类的.class文件保存在本地
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //通过调用Proxy.newProxyInstance生成代理对象
        //方法参数为：1）classLoader  2）要代理的接口 3）代理对象的InvocationHandler
        //(通过方法参数也可以看出来，JDK代理只能通过代理接口来来实现动态代理)
        RealSubject realSubject = new RealSubject();
        Class<?> c = realSubject.getClass();
        Subject subject = (Subject) Proxy.newProxyInstance(c.getClassLoader(),
                c.getInterfaces(), new JdkProxySubject(new RealSubject()));
        //调用代理对象的request方法。
        //（根据InvocationHandler接口的定义，可以知道实际调用的是JdkProxySubject里的invoke方法）
        subject.request();
    }
}
