package com.example.androidaudition

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.androidaudition.content_provider_demo.ContentProvicerDemoActivity
import com.example.androidaudition.demoactivity.ResultActivity
import kotlinx.android.synthetic.main.activity_android_basic.*
import java.util.*

class AndroidBasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
         *              -
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
         *          - 1.startService
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
            intent.setClass(this,ContentProvicerDemoActivity::class.java)
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
    }

    override fun onPause() {
        super.onPause()
        Log.d("activity_", "A - onPause")
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
