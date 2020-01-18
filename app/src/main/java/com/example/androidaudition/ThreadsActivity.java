package com.example.androidaudition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        /**
         *  1 开启线程的三种方式
         *      - 继承Thread这个类，重写run方法
         *      - 实现Runnable接口，实现run方法。（Thread是Runnable的实现类）
         *      - 实现Callable接口，实现call方法。（这个方法可对外抛出异常和拥有返回值）
         *      - 区别，继承简单，但只能单继承，实现的方式，稍微复杂有点，但灵活性更好
         */



        findViewById(R.id.btn_fun_thread_way).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadWay();
            }
        });
    }

    void threadWay() {
        Thread thread1 = new Thread1();
        thread1.start();

        Runnable runnable2 = new Runnable2();
        Thread thread2 = new Thread(runnable2);
        thread2.start();


        Callable callable3 = new Callable3();
        ExecutorService service= Executors.newFixedThreadPool(1);
        service.submit(callable3);
        service.shutdownNow();//现在关闭


    }
}

class Thread1 extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("当前线程：Thread1-" + this.getName());
    }
}

class Runnable2 implements Runnable {

    @Override
    public void run() {
        System.out.println("当前线程：Thread2-" + Thread.currentThread().getName());
    }
}

class Callable3 implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("当前线程：Thread3-" + Thread.currentThread().getName());
        return "返回结果了";
    }
}