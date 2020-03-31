package com.example.androidaudition.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理类对应的调用处理程序类
 * 实现 InvocationHandler 接口
 */
public class SaveInvocationHandler implements InvocationHandler {

    // 代理类持有一个委托类的对象引用
    private Object delegate;

    public Object bind(Object delegate) {
        this.delegate = delegate;
        return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), delegate.getClass().getInterfaces(), this);

    }

    /**
     * SalesInvocationHandler 实现了 InvocationHandler的invoke方法，当代理对象的方法被调用时，invoke方法会被回调，其中proxy表示实现了公共代理方法的动态代理对象
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d("proxy_", "传入方法：" + method.getName());
        long start = System.currentTimeMillis();

        Object result = method.invoke(delegate, args);

        long end = System.currentTimeMillis();
        Log.d("proxy_", "退出方法：" + method.getName());
        System.out.println("执行时间：" + (end - start));
        Log.d("proxy_", "执行时间：" + (end - start));

        return result;

    }
}
