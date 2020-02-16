package com.example.androidaudition;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
         *  5. Java中wait和sleep方法的不同
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

        /**
         *  6. wait/notify关键字的理解
         *      - wait（）：
         *          - 将当前线程置入休眠状态，直到接到通知或被中断为止
         *          - 在调用wait（）之前，线程必须要获得该对象的对象级别锁，即只能在同步方法或同步块中调用wait（）方法。
         *          - 进入wait（）方法后，当前线程释放锁。在从wait（）返回前，线程与其他线程竞争重新获得锁。
         *            如果调用wait（）时，没有持有适当的锁，则抛出IllegalMonitorStateException，它是RuntimeException的一个子类，
         *            因此，不需要try-catch结构。
         *      - notify（）：
         *          - 在同步方法或同步块中调用，即在调用前，线程也必须要获得该对象的对象级别锁，如果调用notify（）时没有持有适当的锁，
         *            也会抛出IllegalMonitorStateException
         *          - 该方法用来通知那些可能等待该对象的对象锁的其他线程。如果有多个线程等待，
         *            则线程规划器任意挑选出其中一个wait（）状态的线程来发出通知，并使它等待获取该对象的对象锁
         *          - notify后，当前线程不会马上释放该对象锁，wait所在的线程并不能马上获取该对象锁，
         *            要等到程序退出synchronized代码块后，当前线程才会释放锁，wait所在的线程也才可以获取该对象锁
         *      - 总结：
         *          - 1.如果线程调用了对象的wait（）方法，那么线程便会处于该对象的等待池中，等待池中的线程不会去竞争该对象的锁。
         *          - 2.当有线程调用了对象的notifyAll（）方法（唤醒所有wait线程）或notify（）方法（只随机唤醒一个wait线程），
         *              被唤醒的的线程便会进入该对象的锁池中，锁池中的线程会去竞争该对象锁。
         *          - 3.优先级高的线程竞争到对象锁的概率大，假若某线程没有竞争到该对象锁，它还会留在锁池中，
         *              唯有线程再次调用wait（）方法，它才会重新回到等待池中。而竞争到对象锁的线程则继续往下执行，
         *              直到执行完了synchronized代码块，它会释放掉该对象锁，这时锁池中的线程会继续竞争该对象锁。
         */

        /**
         *  7.什么导致线程阻塞
         *      - 阻塞状态的线程的特点是：该线程放弃CPU的使用，暂停运行，只有等到导致阻塞的原因消除之后才恢复运行。
         *        或者是被其他的线程中断，该线程也会退出阻塞状态，同时抛出InterruptedException
         *      - 1.线程执行了Thread.sleep(intmillsecond);方法，当前线程放弃CPU，睡眠一段时间，然后再恢复执行
         *      - 2.线程执行一段同步代码，但是尚且无法获得相关的同步锁，只能进入阻塞状态，等到获取了同步锁，才能回复执行。
         *      - 3.线程执行了一个对象的wait()方法，直接进入阻塞状态，等待其他线程执行notify()或者notifyAll()方法。
         *      - 4.线程执行某些IO操作，因为等待相关的资源而进入了阻塞状态。比如说监听system.in，但是尚且没有收到键盘的输入，则进入阻塞状态。
         */

        /**
         *  8.线程的关闭
         *      - 1.设置退出标志，使线程正常退出，正常地退出run()方法后线程结束。（最佳）
         *          - 比如 public volatile boolean exit = false
         *      - 2.使用interrupt()方法终止线程。
         *          - interrupt()方法并不会立即执行中断操作，而是资源释放后停止。会抛出异常。
         *      - 3.使用stop方法强制终止线程（不推荐使用）
         *          - stop()方法太过于暴力，会把执行到一半的线程给终止了，这样线程线程资源的释放就得不到保证。
         *            通常是没有给与线程完成资源释放工作的机会，因此会导致程序工作在不确定的状态下。
         */

        /**
         *  9.java中的同步的方法
         *      - 为何要使用同步？
         *          - java允许多线程并发控制，当多个线程同时操作一个可共享的资源变量时（如数据的增删改查），
         *            将会导致数据不准确，相互之间产生冲突，因此加入同步锁以避免在该线程没有完成操作之前，被其他线程的调用，
         *            从而保证了该变量的唯一性和准确性。
         *      1. 同步方法
         *          - synchronized关键字修饰方法
         *          - public synchronized void save(){}
         *          - java的每个对象都有一个内置锁，当用此关键字修饰方法时，
         *            内置锁会保护整个方法。在调用该方法前，需要获得内置锁，否则就处于阻塞状态。
         *          - 注： synchronized关键字也可以修饰静态方法，此时如果调用该静态方法，将会锁住整个类
         *      2.同步代码块
         *          - synchronized关键字修饰语句块
         *          - 被该关键字修饰的语句块会自动被加上内置锁，从而实现同步
         *          - synchronized(object){}
         *          -  注：同步是一种高开销的操作，因此应该尽量减少同步的内容。
         *             通常没有必要同步整个方法，使用synchronized代码块同步关键代码即可。
         *      3.使用特殊域变量(volatile)实现线程同步
         *          - volatile关键字为域变量的访问提供了一种免锁机制
         *          - 使用volatile修饰域相当于告诉虚拟机该域可能会被其他线程更新
         *          - 因此每次使用该域就要重新计算，而不是使用寄存器中的值
         *          - volatile不会提供任何原子操作，它也不能用来修饰final类型的变量
         *          -  注：多线程中的非同步问题主要出现在对域的读写上，如果让域自身避免这个问题，则就不需要修改操作该域的方法。
         *             用final域，有锁保护的域和volatile域可以避免非同步的问题。
         *      4.使用重入锁实现线程同步
         *          - 在JavaSE5.0中新增了一个java.util.concurrent包来支持同步。
         *          - ReentrantLock类是可重入、互斥、实现了Lock接口的锁
         *          - 它与使用synchronized方法和快具有相同的基本行为和语义，并且扩展了其能力
         *              - ReentrantLock() : 创建一个ReentrantLock实例;
         *              - lock() : 获得锁; unlock() : 释放锁
         *              - 执行需要执行的内容
         *              - unlock() : 释放锁
         *          - 注：ReentrantLock()还有一个可以创建公平锁的构造方法，但由于能大幅度降低程序运行效率，不推荐使用
         *      5.使用局部变量实现线程同步
         *          - 如果使用ThreadLocal管理变量，则每一个使用该变量的线程都获得该变量的副本，
         *            副本之间相互独立，这样每一个线程都可以随意修改自己的变量副本，而不会对其他线程产生影响。
         *          - ThreadLocal 类的常用方法：threadLocalDemo
         *              - ThreadLocal() : 创建一个线程本地变量
         *              - initialValue() : 返回此线程局部变量的当前线程的"初始值"
         *              - set(T value) : 将此线程局部变量的当前线程副本中的值设置为value
         *              - get() : 返回此线程局部变量的当前线程副本中的值
         *          - 注：ThreadLocal与同步机制
         *              - ThreadLocal与同步机制都是为了解决多线程中相同变量的访问冲突问题。
         *              - 前者采用以"空间换时间"的方法，后者采用以"时间换空间"的方式
         *      6.使用阻塞队列实现线程同步 见demo：BlockingSynchronizedThread
         *          - 前面5种同步方式都是在底层实现的线程同步，但是我们在实际开发当中，应当尽量远离底层结构。
         *            使用javaSE5.0版本中新增的java.util.concurrent包将有助于简化开发。
         *          - 使用LinkedBlockingQueue<E>来实现线程的同步
         *              - LinkedBlockingQueue<E>是一个基于已连接节点的，范围任意的blocking queue。
         *                队列是先进先出的顺序（FIFO）
         *          - LinkedBlockingQueue 类常用方法
         *              - LinkedBlockingQueue() : 创建一个容量为Integer.MAX_VALUE的LinkedBlockingQueue
         *              - put(E e) : 在队尾添加一个元素，如果队列满则阻塞（还有add，offer方法）
         *              - size() : 返回队列中的元素个数
         *              - take() : 移除并返回队头元素，如果队列空则阻塞
         *          - 注：BlockingQueue<E>定义了阻塞队列的常用方法，尤其是三种添加元素的方法，我们要多加注意，当队列满时
         *              - add()方法会抛出异常
         *              - offer()方法返回false
         *              - put()方法会阻塞
         *      7.使用原子变量实现线程同步
         *          - 需要使用线程同步的根本原因在于对普通变量的操作不是原子的。
         *          - 原子操作就是指将读取变量值、修改变量值、保存变量值看成一个整体来操作
         *            即-这几种行为要么同时完成，要么都不完成。
         *          - 在java的util.concurrent.atomic包中提供了创建了原子类型变量的工具类，
         *            使用该类可以简化线程同步
         *              - AtomicInteger类常用方法：用此举例
         *                  - AtomicInteger(int initialValue) : 创建具有给定初始值的新的AtomicInteger
         *                  - addAddGet(int data) : 以原子方式将给定值与当前值相加
         *                  - get() : 获取当前值
         *
         *      - 注意：关于Lock对象和synchronized关键字的选择
         *          - 最好两个都不用，使用一种java.util.concurrent包提供的机制，能够帮助用户处理所有与锁相关的代码。
         *          - 如果synchronized关键字能满足用户的需求，就用synchronized，因为它能简化代码
         *          - 如果需要更高级的功能，就用ReentrantLock类，此时要注意及时释放锁，否则会出现死锁，通常在finally代码释放锁
         *      - 注意：ThreadLocal与同步机制
         *          - ThreadLocal与同步机制都是为了解决多线程中相同变量的访问冲突问题。
         *          - ThreadLocal采用以"空间换时间"的方法，同步机制采用以"时间换空间"的方式
         *              - ThreadLocal创建副本，修改副本，所以是空间换时间
         *              - 同步机制 始终操作一个变量，但是，其他线程需要等待，所以是时间换空间
         */

        /**
         *  10.死锁
         *      - 死锁是指两个或两个以上的线程在执行过程中，由于竞争资源或者由于彼此通信而造成的一种阻塞的现象，若无外力作用，它们都将无法推进下去。
         *        此时称系统处于死锁状态或系统产生了死锁，这些永远在互相等待的进程称为死锁进程。
         *      - 死锁出现的场景
         *          - 多个线程同时被阻塞，它们中的一个或者全部都在等待某个资源被释放。由于线程被无限期地阻塞，因此程序不可能正常终止。
         *      - 死锁的4个必要条件
         *          - 互斥条件：线程要求对所分配的资源进行排他性控制,即在一段时间内某 资源仅为一个线程所占有.
         *            此时若有其他线程请求该资源.则请求线程只能等待.
         *          - 不剥夺条件：线程所获得的资源在未使用完毕之前,不能被其他线程强行夺走,即只能由获得该资源的线程自己来释放（只能是主动释放).
         *          - 请求和保持条件：线程已经保持了至少一个资源,但又提出了新的资源请求,而该资源已被其他线程占有,此时请求线程被阻塞,
         *            但对自己已获得的资源保持不放.
         *          - 循环等待条件：存在一种线程资源的循环等待链,链中每一个线程已获得的资源同时被链中下一个线程所请求。
         *       - 原因：一句话解释，锁使用不当。
         *          - 锁顺序死锁
         *          - 动态锁顺序死锁
         *          - 协作对象之间发生死锁
         *       - 解决方法：
         *          - 使用tryLock()定时锁，超过时限则返回错误信息
         *          - 缩减同步代码块范围，最好仅操作共享变量时才加锁
         *          - 以固定的顺序加锁
         */

        /**
         *  11.对象锁和类锁，私有锁
         *      - 类锁：在代码中的方法上加了static和synchronized的锁，或者synchronized(xxx.class）的代码段
         *      - 对象锁：在代码中的方法上加了synchronized的锁，或者synchronized(this）的代码段
         *      - 私有锁：在类内部声明一个私有属性如private Object lock，在需要加锁的代码段synchronized(lock）
         *      - 注意：对象锁，只针对对象。不同对象之间，互不影响。类锁，是针对类，即，所有此类的对象都会被这个锁影响。
         */

        /**
         *  12.线程池
         *      - 为什么用线程池:
         *          - 创建/销毁线程伴随着系统开销，过于频繁的创建/销毁线程，会很大程度上影响处理效率
         *          - 线程并发数量过多，抢占系统资源从而导致阻塞
         *          - 对线程进行一些简单的管理。比如：延时执行、定时循环执行的策略等。运用线程池都能进行很好的实现
         *      - 线程池ThreadPoolExecutor
         *          ThreadPoolExecutor(int corePoolSize,
         *                           int maximumPoolSize,
         *                           long keepAliveTime,
         *                           TimeUnit unit,
         *                           BlockingQueue<Runnable> workQueue,
         *                           ThreadFactory threadFactory,
         *                           RejectedExecutionHandler handler)
         *          - int corePoolSize => 该线程池中核心线程数最大值
         *              - 核心线程：
         *                  - 线程池新建线程的时候，如果当前线程总数小于corePoolSize，则新建的是核心线程，如果超过corePoolSize，则新建的是非核心线程
         *                  - 核心线程默认情况下会一直存活在线程池中，即使这个核心线程啥也不干(闲置状态)。
         *                  - 如果指定ThreadPoolExecutor的allowCoreThreadTimeOut这个属性为true，那么核心线程如果不干活(闲置状态)的话，
         *                    超过一定时间(时长下面参数决定)，就会被销毁掉
         *          - int maximumPoolSize => 该线程池中线程总数最大值
         *              - 线程总数 = 核心线程数 + 非核心线程数。非核心线程：不是核心线程的线程
         *          - long keepAliveTime => 该线程池中非核心线程闲置超时时长
         *              - 一个非核心线程，如果不干活(闲置状态)的时长超过这个参数所设定的时长，就会被销毁掉
         *              - 如果设置allowCoreThreadTimeOut = true，则会作用于核心线程
         *          - BlockingQueue<Runnable> workQueue => 该线程池中的任务队列：维护着等待执行的Runnable对象
         *              - 当所有的核心线程都在干活时，新添加的任务会被添加到这个队列中等待处理，如果队列满了，则新建非核心线程执行任务
         *                  - 常用的workQueue类型：
         *                      - SynchronousQueue：（这个消息队列相当于不存在一样）这个队列接收到任务的时候，会直接提交给线程处理，而不保留它，
         *                        如果所有线程都在工作那就新建一个线程来处理这个任务！
         *                      - LinkedBlockingQueue：（这个线程队列只给核心线程处理）这个队列接收到任务的时候，如果当前线程数小于核心线程数，
         *                        则新建线程(核心线程)处理任务；如果当前线程数等于核心线程数，则进入队列等待。
         *                      - ArrayBlockingQueue：（标注消息队列）可以限定队列的长度，接收到任务的时候，如果没有达到corePoolSize的值，
         *                        则新建线程(核心线程)执行任务，如果达到了，则入队等候，如果队列已满，则新建线程(非核心线程)执行任务，
         *                        又如果总线程数到了maximumPoolSize，并且队列也满了，则发生错误
         *                      - DelayQueue：（会给线程延迟的消息队列）队列内元素必须实现Delayed接口，这就意味着你传进去的任务必须先实现Delayed接口。
         *                        这个队列接收到任务时，首先先入队，只有达到了指定的延时时间，才会执行任务
         *
         *      - 常见线程池 （基本就使用这些，而不是去创建线程池）
         *          - CachedThreadPool()（可缓存线程池）
         *              - 线程数无限制
         *              - 有空闲线程则复用空闲线程，若无空闲线程则新建线程
         *              - 一定程序减少频繁创建/销毁线程，减少系统开销
         *          - FixedThreadPool()（定长线程池）
         *              - 可控制线程最大并发数（同时执行的线程数）
         *              - 超出的线程会在队列中等待
         *          - ScheduledThreadPool()（定长线程池）
         *              - 支持定时及周期性任务执行。
         *          - SingleThreadExecutor()（单线程化的线程池）
         *              - 有且仅有一个工作线程执行任务
         *              - 所有任务按照指定顺序执行，即遵循队列的入队出队规则
         *      - 使用方法：
         *           ExecutorService service = Executors.newCachedThreadPool();//线程池
         *           Runnable  runnable = xxx;
         *           service.execute(runnable);
         *
         */

        /**
         *  13.断点续传和多线程下载
         *      - RandomAccessFile
         *      - WEB下载方式分为HTTP与FTP两种类型
         *          - Hyper Text Transportation Protocol(超文本传输协议)
         *          - File Transportation Protocol(文件传输协议)
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
        findViewById(R.id.btn_linkedblockingqueue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlockingSynchronizedThread bst = new BlockingSynchronizedThread();
                BlockingSynchronizedThread.LinkBlockThread lbt = bst.new LinkBlockThread();
                Thread thread1 = new Thread(lbt);
                Thread thread2 = new Thread(lbt);
                thread1.start();
                thread2.start();
            }
        });
        findViewById(R.id.btn_cachedThreadPoolDemo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cachedThreadPoolDemo();
            }
        });
        findViewById(R.id.btn_fixedThreadPoolDemo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fixedThreadPoolDemo();
            }
        });
        findViewById(R.id.btn_scheduledThreadPoolDemo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduledThreadPoolDemo();
            }
        });
        findViewById(R.id.btn_singleThreadExecutorDemo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleThreadExecutorDemo();
            }
        });
    }


    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Log.d("executorService_", "runnable1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                Log.d("executorService_", "runnable2");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                Log.d("executorService_", "runnable3");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };


    void cachedThreadPoolDemo() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(runnable1);
        executorService.execute(runnable2);


    }

    void fixedThreadPoolDemo() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(runnable1);
        executorService.execute(runnable2);
        executorService.execute(runnable3);

    }

    int m = 0;

    void scheduledThreadPoolDemo() {

        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                m++;
                Log.d("executorService_", "scheduledThreadPoolDemo");
                if (m > 8) {
                    executorService.shutdown();
                }
            }
        }, 0, 300, TimeUnit.MILLISECONDS);


    }

    void singleThreadExecutorDemo() {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(runnable1);
        executorService.execute(runnable2);

    }

    void threadLocalDemo() {
        ThreadLocal<Integer> account = new ThreadLocal<Integer>() {
            @Nullable
            @Override
            protected Integer initialValue() {
                return super.initialValue();
            }
        };
        account.set(1000);
        account.get();

    }

    void semaphoreTest() {
        ExecutorService service = Executors.newCachedThreadPool();//线程池
        final Semaphore semaphore = new Semaphore(3);//3个坑位
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //使用
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getName() + "进入,当前已有" + (3 - semaphore.availablePermits()) + "个并发");
                    try {
                        Thread.sleep((long) (Math.random() * 10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getName() + "即将离开");
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
        ExecutorService service = Executors.newFixedThreadPool(1);
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

class BlockingSynchronizedThread {
    /**
     * 定义一个阻塞队列用来存储生产出来的商品
     */
    private LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
    /**
     * 定义生产商品个数
     */
    private static final int size = 10;
    /**
     * 定义启动线程的标志，为0时，启动生产商品的线程；为1时，启动消费商品的线程
     */
    private int flag = 0;

    public class LinkBlockThread implements Runnable {
        @Override
        public void run() {
            int new_flag = flag++;
            System.out.println("启动线程 " + new_flag);
            if (new_flag == 0) {
                for (int i = 0; i < size; i++) {
                    int b = new Random().nextInt(255);
                    System.out.println("生产商品：" + b + "号");
                    try {
                        queue.put(b);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("仓库中还有商品：" + queue.size() + "个");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                for (int i = 0; i < size / 2; i++) {
                    try {
                        int n = queue.take();
                        System.out.println("消费者买去了" + n + "号商品");
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("仓库中还有商品：" + queue.size() + "个");
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        }
    }
}