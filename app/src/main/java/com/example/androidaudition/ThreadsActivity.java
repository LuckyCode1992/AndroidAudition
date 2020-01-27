package com.example.androidaudition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

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

        /**
         * 2 进程和线程
         *      - 进程是什么：
         *          - 程序并不能单独运行，只有将程序装载到内存中，系统为它分配资源才能运行，而这种执行的程序就称之为进程。
         *            程序和进程的区别就在于：程序是指令的集合，它是进程运行的静态描述文本；进程是程序的一次执行活动，属于动态概念。
         *          - 在多道编程中，我们允许多个程序同时加载到内存中，在操作系统的调度下，可以实现并发地执行。
         *            这是这样的设计，大大提高了CPU的利用率。进程的出现让每个用户感觉到自己独享CPU，因此，进程就是为了在CPU上实现多道编程而提出的
         *      - 有了进程为什么还要线程：
         *          - 进程的缺点/缺陷：
         *              - 进程只能在一个时间干一件事，如果想同时干两件事或多件事，进程就无能为力了
         *              - 进程在执行的过程中如果阻塞，例如等待输入，整个进程就会挂起，即使进程中有些工作不依赖于输入的数据，也将无法执行
         *          - 线程的优点：
         *              - 因为要并发，我们发明了进程，又进一步发明了线程。只不过进程和线程的并发层次不同：
         *                  - 进程属于在处理器这一层上提供的抽象；线程则属于在进程这个层次上再提供了一层并发的抽象
         *              - 除了提高进程的并发度，线程还有个好处，就是可以有效地利用多处理器和多核计算机
         *       - 进程与线程的区别：
         *          - 进程和线程的主要差别在于它们是不同的操作系统资源管理方式
         *          - 一个程序至少有一个进程,一个进程至少有一个线程.
         *          - 线程的划分尺度小于进程，使得多线程程序的并发性高
         *          - 进程在执行过程中拥有独立的内存单元，而多个线程共享内存，从而极大地提高了程序的运行效率。
         *          - 线程在执行过程中与进程还是有区别的。每个独立的线程有一个程序运行的入口、顺序执行序列和程序的出口。
         *            但是线程不能够独立执行，必须依存在应用程序中，由应用程序提供多个线程执行控制。
         *          - 从逻辑角度来看，多线程的意义在于一个应用程序中，有多个执行部分可以同时执行。
         *            但操作系统并没有将多个线程看做多个独立的应用，来实现进程的调度和管理以及资源分配。这就是进程和线程的重要区别。
         */

        /**
         *  3.java中thread的start()和run()的区别
         *      - start（）方法来启动线程，真正实现了多线程运行
         *      - run()方法只是类的一个普通方法而已
         */

        /**
         *  4. 如何控制某个方法允许并发访问线程的个数：
         *      - 通过 信号量(Semaphore)，有时被称为信号灯
         *      - Semaphore可以维护当前访问自身的线程个数,并提供了同步机制。
         *        使用semaphore可以控制同时访问资源的线程个数,例如,实现一个文件允许的开发访问数
         *      - 见方法 semaphoreTest
         */

        /**
         *  5. Java中wait和seelp方法的不同
         *      - sleep()方法是Thread的方法
         *      - wait()方法是Object的方法
         *      - sleep方法没有释放锁，而wait方法释放了锁
         *      - wait，notify和notifyAll只能在同步控制方法或者同步控制块里面使用，而sleep可以在任何地方使用(使用范围)
         *      - sleep必须捕获异常，而wait，notify和notifyAll不需要捕获异常
         *          - 在sleep的过程中过程中有可能被其他对象调用它的interrupt(),产生InterruptedException异常，
         *            如果你的程序不捕获这个异常，线程就会异常终止，进入TERMINATED状态
         *      - 注意sleep()方法是一个静态方法，也就是说他只对当前对象有效，
         *        通过t.sleep()让t对象进入sleep，这样的做法是错误的，它只会是使当前线程被sleep 而不是t线程
         *      -  wait属于Object的成员方法，一旦一个对象调用了wait方法，必须要采用notify()和notifyAll()方法唤醒该进程;
         *         如果线程拥有某个或某些对象的同步锁，那么在调用了wait()后，这个线程就会释放它持有的所有同步资源，
         *         而不限于这个被调用了wait()方法的对象。wait()方法也同样会在wait的过程中有可能被其他对象调用interrupt()方法而产生
         */



        findViewById(R.id.btn_fun_thread_way).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadWay();
            }
        });
        findViewById(R.id.btn_semaphore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                semaphoreTest();
            }
        });
    }

    void semaphoreTest(){
        ExecutorService service = Executors.newCachedThreadPool();//线程池
        final Semaphore semaphore = new Semaphore(3);//3个坑位
        for(int i=0;i<10;i++){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //使用
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程"+Thread.currentThread().getName()+"进入,当前已有"+(3-semaphore.availablePermits()) + "个并发");
                    try {
                        Thread.sleep((long)(Math.random()*10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程"+Thread.currentThread().getName()+"即将离开");
                    //释放
                    semaphore.release();
                }
            };
            service.execute(runnable);
        }
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