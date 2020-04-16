package com.example.androidaudition

import android.app.Activity
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.androidaudition.broadcastReceive.BroadCastReceiver2
import com.example.androidaudition.broadcastReceive.BroadCastReceiver3
import com.example.androidaudition.content_provider_demo.ContentProvicerDemoActivity
import com.example.androidaudition.demoactivity.FragmentDemoActivity
import com.example.androidaudition.demoactivity.ResultActivity
import kotlinx.android.synthetic.main.activity_android_basic.*
import java.util.*

class AndroidBasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("activity_", "A - onCreate")
        setContentView(R.layout.activity_android_basic)
        /**
         *  1.activity(活动)
         *      - Activity 是一个应用组件，用户可与其提供的屏幕进行交互，以执行拨打电话、拍摄照片、发送电子邮件或查看地图等操作。
         *        每个 Activity 都会获得一个用于绘制其用户界面的窗口。窗口通常会充满屏幕，但也可小于屏幕并浮动在其他窗口之上。
         *      - 1.1 Activity的四种基本状态
         *          - Active/Running: Activity处于活动状态，此时Activity处于栈顶，是可见状态，可与用户进行交互。
         *          - Paused：当Activity失去焦点时，或被一个新的非全屏的Activity，或被一个透明的Activity放置在栈顶时，
         *            Activity就转化为Paused状态。但我们需要明白，此时Activity只是失去了与用户交互的能力，
         *            其所有的状态信息及其成员变量都还存在，只有在系统内存紧张的情况下，才有可能被系统回收掉。
         *          - Stopped： 当一个Activity被另一个Activity完全覆盖时，被覆盖的Activity就会进入Stopped状态，
         *            此时它不再可见，但是跟Paused状态一样保持着其所有状态信息及其成员变量。
         *          - Killed： 当Activity被系统回收掉时，Activity就处于Killed状态。 Activity会在以上四种形态中相互切换，
         *            至于如何切换，这因用户的操作不同而异。
         *      - 1.2 生命周期
         *          - onCreate： Activity被创建时的第一个方法，表示正在被创建，一些初始化工作在这里（加载布局资源，初始化所需要的数据等）
         *            如果有耗时任务需开异步线程去完成。
         *          - onStart： Activity正在被启动，在这个状态下Activity还在加载其他资源，正在向我们展示，用户还无法看到，不能交互
         *          - onResume：Activity创建完成，用户可看见界面，可交互
         *          - onPause：Activity正在暂停，正常情况下接着会执行onStop()。这时可以做数据的存储、动画停止的操作，但是不能太耗时，
         *            要不然影响新Activity的创建
         *          - onStop：Activity即将停止，这时可以做一些回收工作，一样不能太耗时
         *          - onDestroy：Activity即将被销毁，可以做一些工作和资源的回收（Service、BroadCastReceiver、Map、Bitmap回收等）
         *          - onRestart: Activity正在重新启动，一般时当前Activity从不可见到可见状态时会执行这个方法，
         *            例如：用户按下Home键（锁屏）或者打开新Activity再返回这个Activity
         *
         *          - Aactivity 跳转到Bactivity的过程中，生命周期
         *              - A onPause -> B onCreate -> B onStart -> B onResume -> onStop
         *          - Aactivity 跳转到Bactivity 后 点返回键
         *              - B onPause -> A onStart -> A onResume -> B onStop -> B onDestroy
         *          - home键：
         *              - 退到后台：onPause -> onStop
         *              - 重回前台: onStart -> onResume
         *          - 横竖屏切换
         *              - 默认情况下：
         *                  - onPause -> onStop -> onSaveInstanceState -> onDestroy -> onCreate -> onStart -> onResume
         *              - 配置 android:configChanges="orientation"  只要含有 orientation 就可
         *                  - 只执行 onConfigurationChanged
         *      - 1.3 启动模式
         *          - standard：
         *              - Standard模式是Android的默认启动模式，你不在配置文件中做任何设置，那么这个Activity就是standard模式，
         *                这种模式下，Activity可以有多个实例，每次启动Activity，无论任务栈中是否已经有这个Activity的实例，
         *                系统都会创建一个新的Activity实例（一直创建）
         *          - singleTop：
         *              - SingleTop模式和standard模式非常相似，主要区别就是当一个singleTop模式的Activity已经位于任务栈的栈顶，
         *                再去启动它时，不会再创建新的实例,如果不位于栈顶，就会创建新的实例。（栈顶复用）
         *          - SingleTask：
         *              - SingleTask模式的Activity在同一个Task内只有一个实例，如果Activity已经位于栈顶，
         *                系统不会创建新的Activity实例，和singleTop模式一样。但Activity已经存在但不位于栈顶时，
         *                系统就会把该Activity移到栈顶，并把它上面的activity出栈。
         *          - singleInstance：
         *              - singleInstance模式也是单例的，但和singleTask不同，singleTask只是任务栈内单例，
         *                系统里是可以有多个singleTask Activity实例的，而singleInstance Activity在整个系统里只有一个实例，
         *                启动一singleInstanceActivity时，系统会创建一个新的任务栈，并且这个任务栈只有他一个Activity。
         *       - 1.4 onNewIntent
         *          - 通常是在 ACTIVITY 的启动模式为 singleTop时，从其他activity 跳转过来的时候，
         *            onNewIntent() -> onRestart() -> onStart() -> onResume()
         *          - 需要特别注意的是， 如果在 onNewIntent(Intent) 中，不调用 setIntent(Intent)
         *            方法对 Intent 进行更新的话，那么之后在调用 getIntent() 方法时得到的依然是最初的值
         *       - 1.5 IntentFilter
         *          - 意图过滤器，Activity之间通过intent实现通信，intent-filter就是用来注册Activity,Service和Broadcast Receiver
         *            使Android知道那个应用程序（或组件）能用来响应intent请求使其可以在一片数据上执行那个动作。
         *            为了注册一个应用程序组件为intent处理者，在其组件的manifest节点中添加一个intent-filter标签。
         *          - intent-filter 包含 action category data
         *              - action : 见 actionDemo
         *              - category: 见 categoryDemo
         *       - 1.6 onConfigurationChanged
         *          - 在一些特殊的情况中，你可能希望当一种或者多种配置改变时避免重新启动你的activity。
         *            你可以通过在manifest中设置android:configChanges属性来实现这点。
         *            你可以在这里声明activity可以处理的任何配置改变，当这些配置改变时不会重新启动activity，
         *            而会调用activity的onConfigurationChanged(Resources.Configuration)方法
         *       - 1.7 startActivityForResult
         *          - 见 startForResult
         *
         *
         */

        btn_action_demo.setOnClickListener {
            actionDemo()
        }
        btn_category_demo.setOnClickListener {
            categoryDemo()
        }

        btn_start_for_result.setOnClickListener {
            startForResult()
        }

        /**
         *  service
         *      - Service既不是一个线程，Service通常运行在当成宿主进程的主线程中，
         *        所以在Service中进行一些耗时操作就需要在Service内部开启线程去操作，否则会引发ANR异常。
         *      - 不是一个单独的进程。除非在清单文件中声明时指定进程名，否则Service所在进程就是application所在进程
         *      - Service存在的目的有2个:
         *          - 告诉系统，当前程序需要在后台做一些处理。这意味着，Service可以不需要UI就在后台运行，
         *            不用管开启它的页面是否被销毁，只要进程还在就可以在后台运行。可以通过startService()方式调用，
         *            这里需要注意，除非Service手动调用stopService()或者Service内部主动调用了stopSelf()，否则Service一直运行
         *          - 程序通过Service对外开放某些操作。通过bindService()方式与Service调用，长期连接和交互，Service生命周期和其绑定的组件相关
         *      - service 的 启动方式 注意：要在清单配置文件中 注册
         *          - 1.startService (android 8.0 后 需要使用startForegroundService方法，并开启一个Notification 否则服务会在一定时间后自动关闭)
         *              - Intent intent = new Intent(MainActivity.this, MyService.class)
         *              - startService（intent）
         *              - 注意事项
         *                  - 当调用Service的startService()后
         *                      - Service首次启动，则先调用onCreate()，在调用onStartCommand()
         *                      - Service已经启动，则直接调用onStartCommand()
         *                  - 当调用stopSelf()或者stopService()后，会执行onDestroy(),代表Service生命周期结束
         *                  - startService方式启动Service不会调用到onBind()。
         *                    startService可以多次调用，每次调用都会执行onStartCommand()。
         *                    不管调用多少次startService，只需要调用一次stopService就结束。
         *                    如果startService后没有调用stopSelf或者stopService，则Service一直存活并运行在后台。
         *                  - onStartCommand的返回值是一个int值，一共有3种
         *                      - START_STICKY = 1:service所在进程被kill之后，系统会保留service状态为开始状态。
         *                        系统尝试重启service，当服务被再次启动，传递过来的intent可能为null，需要注意。
         *                      - START_NOT_STICKY = 2:service所在进程被kill之后，系统不再重启服务
         *                      - START_REDELIVER_INTENT = 3:系统自动重启service，并传递之前的intent
         *                      - 默认返回START_STICKY
         *          - 2.bindService
         *              - 通过bindService绑定Service相对startService方式要复杂一点。由于bindService是异步执行的，
         *                所以需要额外构建一个ServiceConnection对象用与接收bindService的状态，同时还要指定bindService的类型。
         *              - 使用步骤：
         *                  - 1.定义用于通信的对象，在Service的onBind()中返回的对象
         *                  - 2.定义用于接收状体的ServiceConnection
         *                  - 3.在需要的地方绑定到Service
         *              - bindService()也可以调用多次，与startService()不同，当发起对象与Service已经成功绑定后，
         *                不会多次返回ServiceConnection中的回调方法。
         *              - 通过bindService方式与Service进行绑定后，当没有对象与Service绑定后，Service生命周期结束，
         *                这个过程包括绑定对象被销毁，或者主动掉调用unbindService()
         *          - 3.一起调用
         *              - 当同时调用startService和bindService后，需要分别调用stopService和unbindService，Service才会走onDestroy()
         *              - 一个Service必须要在既没有和任何Activity关联又处理停止状态的时候才会被销毁。
         *      - intentService: 为异步而生。
         *          - 执行某些一次性、异步的操作时，IntentService能很好的满足这个场景。
         *          - IntentService相比在使用时将不再需要实现onStartCommand(),同时需要实现onHandleIntent()
         *          - 真正需要我们处理的逻辑就在onHandleIntent()实现，IntentService会内部自动调用stopSelf()关闭自己
         *
         */
        /**
         *  使用Service实现IPC通信
         *      - AIDL:Android Interface Definition Language,即Android接口定义语言
         *      - Service跨进程传递数据需要借助aidl，主要步骤是这样的：
         *          - 1.编写aidl文件，AS自动生成的java类实现IPC通信的代理
         *          - 2.继承自己的aidl类，实现里面的方法
         *          - 3.在onBind()中返回我们的实现类，暴露给外界
         *          - 需要跟Service通信的对象通过bindService与Service绑定，并在ServiceConnection接收数据
         *      - 代码实现:
         *          - 1.新建一个Service
         *          - 2.在manifest文件中声明我们的Service同时指定运行的进程名,这里并是不只能写remote进程名，你想要进程名都可以
         *          - 3.新建一个aidl文件用户进程间传递数据
         *              - AIDL支持的类型：八大基本数据类型、String类型、CharSequence、List、Map、自定义类型
         *          - 4.实现我们的aidl类
         *          - 5.在Service的onBind()中返回
         *          - 6.绑定Service
         *
         *
         */
        /**
         *  调用其他app的Service
         *      - 跟调同app下不同进程下的Service相比，调用其他的app定义的Service有一些细微的差别
         *      - 1.由于需要其他app访问，所以之前的bindService()使用的隐式调用不在合适，需要在Service定义时定义action
         *          Intent intent = new Intent();
         *         intent.setAction("com.jxx.server.service.bind");//Service的action
         *         intent.setPackage("com.jxx.server");//App A的包名
         *         bindService(intent, mServerServiceConnection, BIND_AUTO_CREATE);
         */

        /**
         *  ContentProvider
         *      - ContentProvider是Android四大组件之一，其本质上是一个标准化的数据管道，它屏蔽了底层的数据管理和服务等细节，
         *        以标准化的方式在Android 应用间共享数据
         *      - 用户可以灵活实现ContentProvider所封装的数据存储以及增删改查等，所有的ContentProvider 必须实现一个对外统一的接口（URI）。
         *      - contentProvider是android中提供专门进行不同程序数据共享的方式，其底层实现是Binder，contentProvider以表格的形式组织数据，
         *        而且包含多个表。
         *      - 1.ContentProvider的工作过程 (见图 content_provider_start.jpg)
         *          - 一个应用启动入口方法是ActivityThread的main方法，main方法中会创建ActivityThread的实例和主线程的消息队列，
         *            然后通过attach方法远程调用远程的Ams的attachApplication，并把ApplicationThread对象给Ams。
         *            ams的attachApplication方法会调用ApplicationThread的bindApplication，
         *            经过handler传回ActivityThread中处理，即交给handleBindApplication方法，
         *            此方法会创建Application对象，和加载ContentProvider。
         *          - 所以Application的onCreate的执行在加载ContentProvider之后
         *          - ApplicationThread是Binder对象，它的Binder接口是IApplicationThread，主要用于Ams和ActivityThread的之间通信。
         *          - contentProvider开始后，外界无法直接访问，必须通过AMS根据uri获取对应的ContentProvider的binder接口（IContentProvider），来访问数据。
         *          - 通过android：multiProcess设置ContentProvider是单实例还是多实例
         *          - 访问ContentProvider通过ContentResolver，实际上就是ApplicationContentProvider，第一次触发ContentProvider方法，伴随着启动其所在进程。
         *          - 选query方法，来仔细介绍
         *              - 1、首先获取IContentProvider对象，（通过acquireProvider方法获取ContentProvider）
         *              - 2、发送一个进程间请求给AMS让其启动目标ContentProvider，最后通过installProvider修改引用次数
         *              - 3、AMS首先启动进程，然后启动ContentProvider，具体启动进程的方法是startProcessLocked方法，内部通过Process的start方法完成进程的启动。
         *              - 4、ActivityThread的attach方法会将Application对象通过AMS的attachApplication方法跨进程传递给AMS，最终会完成ContentProvider创建过程。
         *              - 5、一个流程：
         *                  - attachApplication（ams）--> attachApplicationLocked（ams）--> bindApplication（ApplicationThread）
         *                  ->（发送消息）-> Handler -->handleBindApplication
         *              - 6、四个步骤：
         *                  - 创建ContextImpl和Instrumentation
         *                  - 创建Application对象
         *                  - 启动当前进程的ContentProvider并调用其OnCreate
         *                  - 调用Application的onCreate方法
         */

        btn_content_provider_demo.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, ContentProvicerDemoActivity::class.java)
            startActivity(intent)
        }

        /**
         *  BroadCastReceiver(广播接收器)
         *      - 广播，是一个全局的监听器，属于Android四大组件之一
         *      - Android 广播分为两个角色：广播发送者、广播接收者
         *      - 广播接收器 负责 监听 / 接收 应用 App 发出的广播消息，并 做出响应
         *      - 应用场景：
         *          - Android不同组件间的通信（含 ：应用内 / 不同应用之间）
         *          - 多线程通信
         *          - 与 Android 系统在特定情况下的通信（电话呼入时、网络可用时）
         *      - 实现原理
         *          - 采用的模型
         *              - Android中的广播使用了设计模式中的观察者模式：基于消息的发布 / 订阅事件模型
         *              - Android将广播的发送者 和 接收者 解耦，使得系统方便集成，更易扩展
         *              - 模型中有3个角色：
         *                  - 消息订阅者（广播接收者）
         *                  - 消息发布者（广播发布者）
         *                  - 消息中心（AMS，即Activity Manager Service）
         *              - 原理描述：见图 broadcastreceiver.png
         *                  - 1.广播接收者，通过binder机制在AMS中注册
         *                  - 2.广播发送者，通过binder机制向AMS发送广播
         *                  - 3.AMS 根据广播发送者 要求，在已注册列表中，寻找合适的广播接收者（寻找依据：InterFilter/Permission）
         *                  - 4.AMS 将广播 发送到 合适的 广播接收者相应的消息循环队列中
         *                  - 5.广播接收者 通过 消息循环 拿到此广播 并回调 onReceive()
         *                  - 注意：广播发送者 和 广播接收者 的执行 是异步。广播发送者 只管发 不管接收者收到与否。
         *      - 使用流程：
         *          - 1.自定义广播接收者BroadcastReceiver
         *              - 继承BroadcastReceive基类 必须实现抽象方法onReceive()方法
         *                  - 广播接收器接收到相应广播后，会自动回调 onReceive() 方法
         *                  - 通常情况下，onReceive方法会涉及 与 其他组件之间的交互，如发送Notification、启动Service等
         *                  - 默认情况下，广播接收器运行在 UI 线程，因此，onReceive()方法不能执行耗时操作，否则将导致ANR
         *          - 2.广播接收器注册
         *              - 静态注册
         *                  - 注册方式：在AndroidManifest.xml里通过<receive>标签声明
         *                      <receiver
         *                          此broadcastReceiver能否接收其他App的发出的广播
         *                          android:enabled=["true" | "false"]
         *                          默认值是由receiver中有无intent-filter决定的：如果有intent-filter，默认值为true，否则为false
         *                          android:exported=["true" | "false"]
         *                          android:icon="drawable resource"
         *                          android:label="string resource"
         *                          继承BroadcastReceiver子类的类名
         *                          android:name=".xx"
         *                          具有相应权限的广播发送者发送的广播才能被此BroadcastReceiver所接收
         *                          android:permission="string"
         *                          BroadcastReceiver运行所处的进程
         *                          默认为app的进程，可以指定独立的进程
         *                          注：Android四大基本组件都可以通过此属性指定自己的独立进程
         *                           android:process="string" >
         *                           用于指定此广播接收器将接收的广播类型
         *                            <intent-filter>
         *                                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
         *                             </intent-filter>
         *                         </receiver>
         *                  - 当此 App首次启动时，系统会自动实例化mBroadcastReceiver类，并注册到系统中
         *              - 动态注册
         *                  - 注册方式：在代码中调用Context.registerReceiver（）方法
         *                  - 动态广播最好在Activity 的 onResume()注册、onPause()注销
         *                      - 在onResume()注册、onPause()注销是因为onPause()在App死亡前一定会被执行，从而保证广播在App死亡前一定会被注销，从而防止内存泄露。
         *                  - 对于动态广播，有注册就必然得有注销，否则会导致内存泄露
         *                      - 重复注册、重复注销也不允许
         *              - 两种注册方式的区别：
         *                  - 使用方式：
         *                      - 静态注册 是 AndroidManifest.xml 中
         *                      - 动态注册 是 Context.registerReceiver（）
         *                  - 特点：
         *                      - 静态注册 常驻 不受任何组件生命周期 影响 ，耗电 占内存
         *                      - 动态注册 非常驻 受组件生命周期影响，灵活 省电
         *                  - 应用场景：
         *                      - 静态注册：需要时刻监听广播，如电量，网络状态
         *                      - 动态注册：特定时刻监听
         *          - 3. 广播发送者向AMS发送广播
         *              - 广播的发送
         *                  - 广播 是 用”意图（Intent）“标识
         *                  - 定义广播的本质 = 定义广播所具备的“意图（Intent）”
         *                  - 广播发送 = 广播发送者 将此广播的“意图（Intent）”通过sendBroadcast（）方法发送出去
         *              - 广播的类型
         *                  - 普通广播（Normal Broadcast）
         *                      - 开发者自身定义 intent的广播（最常用）
         *                          Intent intent = new Intent();
         *                          //对应BroadcastReceiver中intentFilter的action
         *                          intent.setAction(BROADCAST_ACTION);
         *                          //发送广播
         *                          sendBroadcast(intent);
         *                      - 若被注册了的广播接收者中注册时intentFilter的action与上述匹配，则会接收此广播（即进行回调onReceive()）。
         *                           <intent-filter>
         *                              <action android:name="BROADCAST_ACTION" />
         *                           </intent-filter>
         *                      - 若发送广播有相应权限，那么广播接收者也需要相应权限
         *                  - 系统广播（System Broadcast）
         *                      - Android中内置了多个系统广播：只要涉及到手机的基本操作（如开机、网络状态变化、拍照等等），都会发出相应的广播
         *                      - 每个广播都有特定的Intent - Filter（包括具体的action）
         *                      - 注：当使用系统广播时，只需要在注册广播接收者时定义相关的action即可，并不需要手动发送广播，当系统有相关操作时会自动进行系统广播
         *                  - 有序广播（Ordered Broadcast）
         *                      - 定义:
         *                          - 发送出去的广播被广播接收者按照先后顺序接收
         *                          - 有序是针对广播接收者而言的
         *                      - 广播接受者接收广播的顺序规则（同时面向静态和动态注册的广播接受者）
         *                          - 按照Priority属性值从大-小排序（-1000~1000）
         *                          - Priority属性相同者，动态注册的广播优先
         *                          - 官方文档：
         *                            The value must be an integer, such as "100". Higher numbers have a higher priority.
         *                            The default value is 0. The value must be greater than -1000 and less than 1000.
         *                      - 特点：
         *                          - 接收广播按顺序接收
         *                          - 先接收的广播接收者可以对广播进行截断，即后接收的广播接收者不再接收到此广播
         *                          - 先接收的广播接收者可以对广播进行修改，那么后接收的广播接收者将接收到被修改后的广播
         *                      - 有序广播的使用过程与普通广播非常类似，差异仅在于广播的发送方式：sendOrderedBroadcast(intent);
         *                  - 粘性广播（Sticky Broadcast）
         *                      - 由于在Android5.0 & API 21中已经失效，所以不建议使用，在这里也不作过多的总结
         *                  - App应用内广播（Local Broadcast）
         *                      - 背景：
         *                          - Android中的广播可以跨App直接通信（exported对于有intent-filter情况下默认值为true）
         *                      - 冲突：
         *                          - 其他App针对性发出与当前App intent-filter相匹配的广播，由此导致当前App不断接收广播并处理；
         *                          - 其他App注册与当前App一致的intent-filter用于接收广播，获取广播具体信息，即会出现安全性 & 效率性的问题
         *                          - 解决方案：
         *                              - 使用App应用内广播（Local Broadcast）
         *                                  - App应用内广播可理解为一种局部广播，广播的发送者和接收者都同属于一个App
         *                                  - 相比于全局广播（普通广播），App应用内广播优势体现在：安全性高 & 效率高
         *                              - 具体使用1 - 将全局广播设置成局部广播
         *                                  - 1.注册广播时将exported属性设置为false，使得非本App内部发出的此广播不被接收
         *                                  - 2.在广播发送和接收时，增设相应权限permission，用于权限验证
         *                                  - 3.发送广播时指定该广播接收器所在的包名，此广播将只会发送到此包中的App内与之相匹配的有效广播接收器中
         *                                      - 通过intent.setPackage(packageName)指定报名
         *                              - 具体使用2 - 使用封装好的LocalBroadcastManager类
         *                                  - 使用方式上与全局广播几乎相同，只是注册/取消注册广播接收器和发送广播时将参数的context变成了LocalBroadcastManager的单一实例
         *                                  - 注：对于LocalBroadcastManager方式发送的应用内广播，只能通过LocalBroadcastManager动态注册，不能静态注册
         *              - 特别注意
         *                  - 对于静态注册（全局+应用内广播），回调onReceive(context, intent)中的context返回值是：ReceiverRestrictedContext
         *                  - 对于全局广播的动态注册，回调onReceive(context, intent)中的context返回值是：Activity Context；
         *                  - 对于应用内广播的动态注册（LocalBroadcastManager方式），回调onReceive(context, intent)中的context返回值是：Application Context。
         *                  - 对于应用内广播的动态注册（非LocalBroadcastManager方式），回调onReceive(context, intent)中的context返回值是：Activity Context；
         */

        /**
         *  Fragment
         *      - Fragment 是什么
         *          - public class Fragment implements ComponentCallbacks, OnCreateContextMenuListener {...}
         *          - Fragment 没有继承任何类，只是实现了这两个接口，第二个不太重要，第一个是在内存不足时可以收到回调
         *          - 生命周期方法：fragment_life.png
         *              - onAttach(Context)
         *                  - onAttach() 是一个 Fragment 和它的 Context 关联时第一个调用的方法
         *                  - 可以获得对应的 Context 或者 Activity
         *              - onCreate(Bundle)
         *                  - onCreate() 在 onAttach() 后调用，用于做一些初始化操作
         *                  - 需要注意的是，Fragment 的 onCreate() 调用时关联的 Activity 可能还没创建好，所以这里不要有依赖外部 Activity 布局的操作
         *                  - 如果有依赖 Activity 的操作，可以放在 onActivityCreate()
         *              - onCreateView(LayoutInflater, ViewGroup, Bundle)
         *                  - 在 onCreate() 后就会执行 onCreateView()，这个方法返回一个 View，默认返回为 null。
         *                  - 当我们需要在 Fragment 中显示布局时，需要重写这个方法，返回要显示的布局
         *                  - 后面布局销毁时就会调用 onDestroyView()
         *              - onViewCreated
         *                  - onViewCreate() 不是生命周期中的方法，但是却很有用
         *                  - 它会在 onCreateView() 返回后立即执行
         *                  - 参数中的 view 就是之前创建的 View，因此我们可以在 onViewCreate() 中进行布局的初始化（比如 设置button）
         *              - onActivityCreated(Bundle)
         *                  - onActivityCreated() 会在 Fragment 关联的 Activity 创建好、Fragment 的布局结构初始化完成后调用
         *                  - 可以在这个方法里做些和布局、状态恢复有关的操作
         *              - onViewStateRestored(Bundle)
         *                  - onViewStateRestored() 方法会在 onActivityCreated() 结束后调用，
         *                    用于一个 Fragment 在从旧的状态恢复时，获取状态 saveInstanceState 恢复状态，比如恢复一个 check box 的状态
         *              - onStart()
         *                  - onStart() 当 Fragment 可见时调用，同步于 Activity 的 onStart()
         *              - onResume()
         *                  - onResume() 当 Fragment 可见并且可以与用户交互时调用 ，和 Activity 的 onResume() 同步
         *              - onPause()
         *                  - onPause() 当 Fragment 不再可见时调用 和 Activity 的 onPause() 同步
         *              - onStop()
         *                  - onStop() 当 Fragment 不再启动时调用，和 Activity.onStop() 一致
         *              - onDestroyView()
         *                  - 当 onCreateView() 返回的布局（不论是不是 null）从 Fragment 中解除绑定时调用 onDestroyView()
         *              - onDestroy()
         *                  - 当 Fragment 不再使用时会调用 onDestroy()，它是一个 Fragment 生命周期的倒数第二步
         *              - onDetach()
         *                  - Fragment 生命周期的最后一个方法，当 Fragment 不再和一个 Activity 绑定时调用
         *                  - Fragment 的 onDestroyView(), onDestroy(), onDetach() 三个对应 Activity 的 onDestroyed() 方法
         *      - Fragment 的使用
         *          - 1.创建 Fragment
         *          - 2.获取 FragmentManager
         *          - 3.调用事务，添加、替换
         *          - getSupportFragmentManager()
         *              .beginTransaction()
         *              .replace(R.id.fl_content, fragment)
         *              .commitAllowingStateLoss() (不使用commit)
         *      - FragmentManager
         *          - public abstract class FragmentManager {...}
         *          - FragmentManager 是一个抽象类，定义了一些和 Fragment 相关的操作和内部类/接口。
         *          - 定义的操作：
         *              // 开启一系列对 Fragments 的操作
         *              public abstract FragmentTransaction beginTransaction();
         *
         *              //FragmentTransaction.commit() 是异步执行的，如果你想立即执行，可以调用这个方法 (几乎没用到)
         *              public abstract boolean executePendingTransactions();
         *
         *              // 根据 ID 找到从 XML 解析出来的或者事务中添加的 Fragment
         *              // 首先会找添加到 FragmentManager 中的，找不到就去回退栈里找
         *              public abstract Fragment findFragmentById(@IdRes int id);
         *
         *              // 跟上面的类似，不同的是使用 tag 进行查找
         *              public abstract Fragment findFragmentByTag(String tag);
         *
         *              // 弹出回退栈中栈顶的 Fragment，异步执行的
         *              public abstract void popBackStack();
         *
         *              // 立即弹出回退栈中栈顶的，直接执行哦
         *              public abstract boolean popBackStackImmediate();
         *
         *              // 返回栈顶符合名称的，如果传入的 name 不为空，在栈中间找到了 Fragment，那将弹出这个 Fragment 上面的所有 Fragment
         *              // 有点类似启动模式的 singleTask 的感觉
         *              // 如果传入的 name 为 null，那就和 popBackStack() 一样了
         *              // 异步执行
         *              public abstract void popBackStack(String name, int flags)
         *
         *              // 同步版的上面
         *              public abstract boolean popBackStackImmediate(String name, int flags);
         *
         *              // 和使用 name 查找、弹出一样
         *              // 不同的是这里的 id 是 FragmentTransaction.commit() 返回的 id
         *              public abstract void popBackStack(int id, int flags);
         *              //同步版本
         *              public abstract boolean popBackStackImmediate(int id, int flags);
         *
         *              // 获取回退栈中的元素个数
         *              public abstract int getBackStackEntryCount();
         *
         *              // 根据索引获取回退栈中的某个元素
         *              public abstract BackStackEntry getBackStackEntryAt(int index);
         *
         *              // 获取 manager 中所有添加进来的 Fragment
         *              public abstract List<Fragment> getFragments();
         *          - FragmentManager 是一个抽象类，它定义了对一个 Activity/Fragment 中 添加进来的 Fragment 列表、Fragment 回退栈的操作、管理
         *
         *      - 事务
         *          - BackStackRecord 继承了 FragmentTransaction：
         *              - final class BackStackRecord extends FragmentTransaction implements
         *                  FragmentManager.BackStackEntry, FragmentManagerImpl.OpGenerator {...}
         *              - FragmentTransaction add,remove,show,hide replace 等方法都是此类的
         *              - 事务的四种提交方式
         *                  - commit()
         *                      - commit() 在主线程中异步执行，其实也是 Handler 抛出任务，等待主线程调度执行。
         *                      - 注意 :commit() 需要在宿主 Activity 保存状态之前调用，否则会报错。
         *                              这是因为如果 Activity 出现异常需要恢复状态，在保存状态之后的 commit() 将会丢失，这和调用的初衷不符，所以会报错
         *                  - commitAllowingStateLoss()  （常用）
         *                      - commitAllowingStateLoss() 也是异步执行，但它的不同之处在于，允许在 Activity 保存状态之后调用，也就是说它遇到状态丢失不会报错。
         *                  - commitNow()
         *                      - commitNow() 是同步执行的，立即提交任务
         *                      - 前面提到 FragmentManager.executePendingTransactions() 也可以实现立即提交事务。
         *                        但我们一般建议使用 commitNow(), 因为另外那位是一下子执行所有待执行的任务，可能会把当前所有的事务都一下子执行了，这有可能有副作用。
         *                      - 和 commit() 一样，commitNow() 也必须在 Activity 保存状态前调用，否则会抛异常
         *                  - commitNowAllowingStateLoss()
         *                      - 同步执行的 commitAllowingStateLoss()。
         *              - 事务真正实现/回退栈 BackStackRecord
         *                  - BackStackRecord 既是对 Fragment 进行操作的事务的真正实现，也是 FragmentManager 中的回退栈的实现
         *      - 总结
         *          - Fragment、FragmentManager、FragmentTransaction 关系
         *              - Fragment 其实是对 View 的封装，它持有 view, containerView, fragmentManager, childFragmentManager 等信息
         *              - FragmentManager 是一个抽象类，它定义了对一个 Activity/Fragment 中 添加进来的 Fragment 列表、Fragment 回退栈的操作、管理方法
         *                  还定义了获取事务对象的方法,具体实现在 FragmentImpl 中
         *              - FragmentTransaction 定义了对 Fragment 添加、替换、隐藏等操作，还有四种提交方法 具体实现是在 BackStackRecord 中
         */

        /**
         *  AlertDialog、PopupWindow 与 Activity 之间区别
         *      - AlertDialog 是非阻塞式对话框；而PopupWindow 是阻塞式对话框
         *      - AlertDialog 弹出时，后台还可以做事情；PopupWindow 弹出时，程序会等待，
         *        在PopupWindow 退出前，程序一直等待，只有当我们调用了 dismiss() 方法的后，PopupWindow 退出，程序才会向下执行
         *      - AlertDialog 和 PopupWindow 的本质区别在于有没有新建一个 window，PopupWindow 没有新建，而是通过 WMS 将 View 加到 DecorView；
         *        Dialog 是新建了一个 window (PhoneWindow)，相当于走了一遍 Activity 中创建 window 的流程。
         */


        btn_fragment.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, FragmentDemoActivity::class.java)
            startActivity(intent)
        }

    }

    fun startForResult() {
        val intent = Intent()
        intent.setClass(this, ResultActivity::class.java)
        startActivityForResult(intent, 999)
    }

    fun actionDemo() {
        val intent = Intent()
        intent.setAction("action_2")
        startActivity(intent)
    }

    fun categoryDemo() {
        //存在bug
//        val intent = Intent()
//        intent.addCategory("android.intent.category.MY_CATEGORY")
//        startActivity(intent)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 999) {
                val result = data?.getExtras()?.getString("result")
                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("activity_", "A - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("activity_", "A - onResume")
        registerReceiverNor()
        registerReceiverApp()
        sendBroadCastApp()
    }

    private fun sendBroadCastApp() {
        //发送应用内广播
        val intent = Intent()
        intent.action = "ACTION_APP_NEI"
        localBroadcastManager.sendBroadcast(intent)
    }

    lateinit var receiver2: BroadCastReceiver2
    private fun registerReceiverNor() {
        // 1. 实例化BroadcastReceiver子类 &  IntentFilter
        receiver2 = BroadCastReceiver2()
        val intentFilter = IntentFilter()
        // 2. 设置接收广播的类型
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        // 3. 动态注册：调用Context的registerReceiver（）方法
        registerReceiver(receiver2, intentFilter)
    }

    lateinit var receiver3: BroadCastReceiver3
    lateinit var localBroadcastManager: LocalBroadcastManager
    private fun registerReceiverApp() {
        //注册应用内广播接收器
        //步骤1：实例化BroadcastReceiver子类 & IntentFilter mBroadcastReceiver
        receiver3 = BroadCastReceiver3()
        val intentFilter = IntentFilter()
        //步骤2：实例化LocalBroadcastManager的实例
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        //步骤3：设置接收广播的类型
        intentFilter.addAction("ACTION_APP_NEI")
        //步骤4：调用LocalBroadcastManager单一实例的registerReceiver（）方法进行动态注册
        localBroadcastManager.registerReceiver(receiver3, intentFilter)
    }

    private fun unRegisterReceiverApp() {
        //取消注册应用内广播接收器
        localBroadcastManager.unregisterReceiver(receiver3)
    }

    private fun unRegisterReceiver(receiver: BroadcastReceiver) {
        unregisterReceiver(receiver)
    }

    override fun onPause() {
        super.onPause()
        Log.d("activity_", "A - onPause")
        unRegisterReceiver(receiver2)
        unRegisterReceiverApp()
    }

    override fun onStop() {
        super.onStop()
        Log.d("activity_", "A - onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("activity_", "A - onDestroy")
    }

}
