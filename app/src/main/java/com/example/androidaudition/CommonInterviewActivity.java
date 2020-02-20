package com.example.androidaudition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class CommonInterviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_interview);

        /**
         *  java中的同步的方法
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

        /**      - 常见线程池 （基本就使用这些，而不是去创建线程池）
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
         */

        /**
         *  handler
         *      - 只要有异步线程与主线程通信的地方就一定会有 Handler
         *      - Handler 发送并处理与一个线程关联的 Message 和 Runnable 。（注意：Runnable 会被封装进一个 Message，所以它本质上还是一个 Message ）
         *      - 我们可以使用 Handler 发送并处理与一个线程关联的 Message 和 Runnable 。（注意：Runnable 会被封装进一个 Message，所以它本质上还是一个 Message ）
         *      - 每个 Handler 都会跟一个线程绑定，并与该线程的 MessageQueue 关联在一起，从而实现消息的管理以及线程间通信。
         *      - handler 的基本用法
         */
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                //这里接受并处理消息
            }
        };
        /**
         * 实例化一个 Handler 重写 handleMessage 方法 ，然后在需要的时候调用它的 send 以及 post 系列方法就可以了，非常简单易用，并且支持延时消息
         */
        handler.sendMessage(new Message());
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);
        /**
         *   Handler 原理解析
         *      - Handler 与 Looper 的关联
         *          - 实际上我们在实例化 Handler 的时候 Handler 会去检查当前线程的 Looper 是否存在，如果不存在则会报异常，
         *            也就是说在创建 Handler 之前一定需要先创建 Looper 。
         *          - 源码：
         *            public Handler(Callback callback, boolean async) {
         *            //检查当前的线程是否有 Looper
         *            mLooper = Looper.myLooper();
         *            if (mLooper == null) {
         *             throw new RuntimeException(
         *                 "Can't create handler inside thread that has not called Looper.prepare()");
         *              }
         *            //Looper 持有一个 MessageQueue
         *            mQueue = mLooper.mQueue;
         *          - 平时直接使用感受不到这个异常是因为主线程已经为我们创建好了 Looper
         *          - 一个完整的 Handler 使用例子其实是这样的：
         */
        class LooperThread extends Thread {
            public Handler mHandler;

            @SuppressLint("HandlerLeak")
            public void run() {
                Looper.prepare();
                mHandler = new Handler() {
                    public void handleMessage(Message msg) {
                        // process incoming messages here
                    }
                };
                Looper.loop();
            }
        }
        /**
         *  Handler 原理解析
         *      - Looper.prepare() :
         *      //Looper
         *      private static void prepare(boolean quitAllowed) {
         *          if (sThreadLocal.get() != null) {
         *            throw new RuntimeException("Only one Looper may be created per thread");
         *          }
         *          sThreadLocal.set(new Looper(quitAllowed));
         *      }
         *      - Looper 提供了 Looper.prepare()  方法来创建 Looper ，并且会借助 ThreadLocal 来实现与当前线程的绑定功能。
         *        Looper.loop() 则会开始不断尝试从 MessageQueue 中获取 Message , 并分发给对应的 Handler
         *        也就是说 Handler 跟线程的关联是靠 Looper 来实现的
         *      - 总结：handler 和looper关联，threadLocal实现了线程标识，把looper和线程关联，继而实现了 handler和线程的关联
         *
         *      - Message 的存储与管理
         *          - Handler 提供了一些列的方法让我们来发送消息，如 send()系列 post()系列
         *          - 不过不管我们调用什么方法，最终都会走到 MessageQueue.enqueueMessage(Message,long) 方法。
         *          - 以 sendEmptyMessage(int)  方法为例：
         *              //Handler
         *              sendEmptyMessage(int)
         *                  -> sendEmptyMessageDelayed(int,int)
         *                       -> sendMessageAtTime(Message,long)
         *                          -> enqueueMessage(MessageQueue,Message,long)
         *   			                -> queue.enqueueMessage(Message, long);
         *   		到了这里，消息的管理者 MessageQueue 也就露出了水面
         *          MessageQueue 顾明思议，就是个队列，负责消息的入队出队
         *      - Message 的分发与处理
         *          - 前面说到了 Looper.loop()  负责对消息的分发
         *              //Looper
         *              public static void loop() {
         *                  final Looper me = myLooper();
         *                  if (me == null) {
         *                      throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
         *                   }
         *                  final MessageQueue queue = me.mQueue;
         *                  //...
         *                  for (;;) {
         *                  // 不断从 MessageQueue 获取 消息
         *                  Message msg = queue.next(); // might block
         *                  //退出 Looper
         *                  if (msg == null) {
         *                      // No message indicates that the message queue is quitting.
         *                      return;
         *                  }
         *                  //...
         *                  try {
         *                      msg.target.dispatchMessage(msg);
         *                      end = (slowDispatchThresholdMs == 0) ? 0 : SystemClock.uptimeMillis();
         *                  } finally {
         *                      //...
         *                  }
         *                  //...
         * 		        		//回收 message, 见【3.5】
         *                  msg.recycleUnchecked();
         *                  }
         *              }
         *              loop() 里调用了 MessageQueue.next():
         *              //MessageQueue
         *              Message next() {
         *                  //...
         *                  for (;;) {
         *                      //...
         *                      nativePollOnce(ptr, nextPollTimeoutMillis);
         *
         *                      synchronized (this) {
         *                          // Try to retrieve the next message.  Return if found.
         *                          final long now = SystemClock.uptimeMillis();
         *                          Message prevMsg = null;
         *                          Message msg = mMessages;
         *                          //...
         *                          if (msg != null) {
         *                              if (now < msg.when) {
         *                                  // Next message is not ready.  Set a timeout to wake up when it is ready.
         *                                  nextPollTimeoutMillis = (int) Math.min(msg.when - now, Integer.MAX_VALUE);
         *                              } else {
         *                                  // Got a message.
         *                                  mBlocked = false;
         *                                  if (prevMsg != null) {
         *                                      prevMsg.next = msg.next;
         *                                  } else {
         *                                      mMessages = msg.next;
         *                                  }
         *                                  msg.next = null;
         *                                  return msg;
         *                              }
         *                          } else {
         *                              // No more messages.
         *                              nextPollTimeoutMillis = -1;
         *                          }
         *
         *                          // Process the quit message now that all pending messages have been handled.
         *                          if (mQuitting) {
         *                              dispose();
         *                              return null;
         *                          }
         *                      }
         *
         *                      // Run the idle handlers. 关于 IdleHandler 自行了解
         *                      //...
         *                  }
         *              }
         *              调用了 msg.target.dispatchMessage(msg) ，msg.target 就是发送该消息的 Handler，这样就回调到了 Handler 那边去了
         *              //Handler
         *              public void dispatchMessage(Message msg) {
         *                //msg.callback 是 Runnable ，如果是 post方法则会走这个 if
         *                if (msg.callback != null) {
         *                  handleCallback(msg);
         *                } else {
         *                  //callback 见【3.4】
         *                  if (mCallback != null) {
         *                    if (mCallback.handleMessage(msg)) {
         *                      return;
         *                    }
         *                  }
         *                  //回调到 Handler 的 handleMessage 方法
         *                  handleMessage(msg);
         *                }
         *              }
         *              注意：dispatchMessage() 方法针对 Runnable 的方法做了特殊处理，如果是 ，则会直接执行 Runnable.run()
         *              分析：Looper.loop() 是个死循环，会不断调用 MessageQueue.next() 获取 Message ，
         *              并调用 msg.target.dispatchMessage(msg) 回到了 Handler 来分发消息，以此来完成消息的回调
         *              注意：loop()方法并不会卡死主线程
         *                  - 因为：
         *                    Android应用程序的主线程在进入消息循环过程前，会在内部创建一个Linux管道（Pipe），
         *                    这个管道的作用是使得Android应用程序主线程在消息队列为空时可以进入空闲等待状态，
         *                    并且使得当应用程序的消息队列有消息需要处理时唤醒应用程序的主线程
         *                    Android应用程序的主线程进入空闲等待状态的方式实际上就是在管道的读端等待管道中有新的内容可读，
         *                    具体来说就是是通过Linux系统的Epoll机制中的epoll_wait函数进行的
         *                    当往Android应用程序的消息队列中加入新的消息时，会同时往管道中的写端写入内容，
         *                    通过这种方式就可以唤醒正在等待消息到来的应用程序主线程
         *                    当应用程序主线程在进入空闲等待前，会认为当前线程处理空闲状态，
         *                    于是就会调用那些已经注册了的IdleHandler接口，使得应用程序有机会在空闲的时候处理一些事情。
         *      - 线程的切换又是怎么回事呢？
         *          Thread.foo(){
         * 	        Looper.loop()
         * 	        -> MessageQueue.next()
         *  	        -> Message.target.dispatchMessage()
         *  	            -> Handler.handleMessage()
         *          }
         *          Handler.handleMessage() 所在的线程最终由调用 Looper.loop() 的线程所决定
         *          平时我们用的时候从异步线程发送消息到 Handler，这个 Handler 的 handleMessage() 方法是在主线程调用的，
         *          所以消息就从异步线程切换到了主线程。
         *      - 小结：Handler 的背后有着 Looper 以及 MessageQueue 的协助，三者通力合作，分工明确。
         *          - Looper ：负责关联线程以及消息的分发在该线程下**从 MessageQueue 获取 Message，分发给 Handler ；
         *          - MessageQueue ：是个队列，负责消息的存储与管理，负责管理由 Handler 发送过来的 Message
         *          - Handler : 负责发送并处理消息，面向开发者，提供 API，并隐藏背后实现的细节
         *      - Handler 发送的消息由 MessageQueue 存储管理，并由 Looper 负责回调消息到 handleMessage()。
         *      - 线程的转换由 Looper 完成，handleMessage() 所在线程由 Looper.loop() 调用者所在线程决定
         *
         *      - handler工作流程关键总结：
         *          - 1.handler 将消息 sendMessage 到 MessageQueue (最终调用方法是 MessageQueue.enqueueMessage(Message,long) 方法)
         *              由MessageQueue 负责存储和管理这些 message。
         *          - 2.Looper 不断循环MessageQueue队列 并 将消息 取出 分发给 对应线程的 handler（使用的是回调的方式）
         *          - 3.handler 处理 消息 handleMessage （实际会通过 dispatchMessage）
         */

        /**
         *  handler 的延伸
         *      - Handler 引起的内存泄露原因以及最佳解决方案
         *          - Handler 允许我们发送延时消息，如果在延时期间用户关闭了 Activity，那么该 Activity 会泄露。
         *            这个泄露是因为 Message 会持有 Handler，而又因为 Java 的特性，内部类会持有外部类，
         *            使得 Activity 会被 Handler 持有，这样最终就导致 Activity 泄露。
         *          - 解决该问题的最有效的方法是：将 Handler 定义成静态的内部类，在内部持有 Activity 的弱引用，并及时移除所有消息。
         *            private static class SafeHandler extends Handler {
         *
         *                  private WeakReference<HandlerActivity> ref;
         *
         *                  public SafeHandler(HandlerActivity activity) {
         *                      this.ref = new WeakReference(activity);
         *                  }
         *
         *                  @Override
         *                  public void handleMessage(final Message msg) {
         *                      HandlerActivity activity = ref.get();
         *                      if (activity != null) {
         *                          activity.handleMessage(msg);
         *                      }
         *                  }
         *           }
         *           并且再在 Activity.onDestroy() 前移除消息，加一层保障
         *           @Override
         *           protected void onDestroy() {
         *             safeHandler.removeCallbacksAndMessages(null);
         *             super.onDestroy();
         *           }
         *           注意：单纯的在 onDestroy 移除消息并不保险，因为 onDestroy 并不一定执行
         *      - 为什么我们能在主线程直接使用 Handler，而不需要创建 Looper
         *          - 注意：通常我们认为 ActivityThread 就是主线程。事实上它并不是一个线程，而是主线程操作的管理者，
         *            所以吧，我觉得把 ActivityThread 认为就是主线程无可厚非，另外主线程也可以说成 UI 线程。
         *            //android.app.ActivityThread
         *              public static void main(String[] args) {
         *                //...
         *                Looper.prepareMainLooper();
         *
         *                ActivityThread thread = new ActivityThread();
         *                thread.attach(false);
         *
         *                if (sMainThreadHandler == null) {
         *                  sMainThreadHandler = thread.getHandler();
         *                }
         *                //...
         *                Looper.loop();
         *
         *                throw new RuntimeException("Main thread loop unexpectedly exited");
         *              }
         *              public static void prepareMainLooper () {
         *                  prepare(false);
         *                  synchronized (Looper.class) {
         *                      if (sMainLooper != null) {
         *                      throw new IllegalStateException("The main Looper has already been prepared.");
         *                      }
         *                      sMainLooper = myLooper();
         *                   }
         *              }
         *              在 ActivityThread 里 调用了 Looper.prepareMainLooper() 方法创建了 主线程的 Looper ,
         *              并且调用了 loop() 方法，所以我们就可以直接使用 Handler 了。
         *              注意：Looper.loop() 是个死循环，后面的代码正常情况不会执行。
         *       - 主线程的 Looper 不允许退出
         *          - 主线程不允许退出，退出就意味 APP 要挂
         *       - Handler 里藏着的 Callback 能干什么
         *          - Handler.dispatchMessage(msg)  方法：
         *             public void dispatchMessage(Message msg) {
         *                //这里的 callback 是 Runnable
         *                if (msg.callback != null) {
         *                  handleCallback(msg);
         *                } else {
         *                  //如果 callback 处理了该 msg 并且返回 true， 就不会再回调 handleMessage
         *                  if (mCallback != null) {
         *                    if (mCallback.handleMessage(msg)) {
         *                      return;
         *                    }
         *                  }
         *                  handleMessage(msg);
         *                }
         *             }
         *              Handler.Callback 有优先处理消息的权利 ，当一条消息被 Callback 处理并拦截（返回 true），
         *              那么 Handler 的 handleMessage(msg) 方法就不会被调用了；如果 Callback 处理了消息，
         *              但是并没有拦截，那么就意味着一个消息可以同时被 Callback 以及 Handler 处理
         *              我们可以利用 Callback 这个拦截机制来拦截 Handler 的消息！
         *        - 创建 Message 实例的最佳方式
         *          - 通过 Message 的静态方法 Message.obtain();
         *          - 通过 Handler 的公有方法 handler.obtainMessage()
         *        - 子线程里弹 Toast 的正确姿势
         *          - 本质上是因为 Toast 的实现依赖于 Handler
         *           Looper.prepare();
         *           Toast.makeText(HandlerActivity.this, "不会崩溃啦！", Toast.LENGTH_SHORT).show();
         *           Looper.loop();
         *        - 妙用 Looper 机制
         *          - 将 Runnable post 到主线程执行
         *          - 利用 Looper 判断当前线程是否是主线程
         *          public final class MainThread {
         *
         *              private MainThread() {
         *              }
         *
         *              private static final Handler HANDLER = new Handler(Looper.getMainLooper());
         *
         *              public static void run(@NonNull Runnable runnable) {
         *                  if (isMainThread()) {
         *                      runnable.run();
         *                  }else{
         *                      HANDLER.post(runnable);
         *                  }
         *              }
         *
         *              public static boolean isMainThread() {
         *                  return Looper.myLooper() == Looper.getMainLooper();
         *              }
         *
         *          }
         *
         */

        /**
         *  handler 知识点汇总
         *      - Handler 的背后有 Looper、MessageQueue 支撑，Looper 负责消息分发，MessageQueue 负责消息管理
         *      - 在创建 Handler 之前一定需要先创建 Looper(主线程不需要手动创建，因为 activityThread 已经创建好了)
         *      - Looper 有退出的功能，但是主线程的 Looper 不允许退出
         *      - 异步线程的 Looper 需要自己调用 Looper.myLooper().quit(); 退出
         *      - Runnable 被封装进了 Message，可以说是一个特殊的 Message
         *      - Handler.handleMessage() 所在的线程是 Looper.loop() 方法被调用的线程，也可以说成 Looper 所在的线程，并不是创建 Handler 的线程
         *      - 使用内部类的方式使用 Handler 可能会导致内存泄露，即便在 Activity.onDestroy 里移除延时消息，
         *        必须要写成静态内部类，并使用弱引用，持有外部activity
         */


    }

@SuppressLint("HandlerLeak")
static Handler handler = new Handler(){
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
    }
};
}
