package com.example.androidaudition

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
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
    }

    fun startForResult() {
        val intent = Intent()
        intent.setClass(this,ResultActivity::class.java)
        startActivityForResult(intent,999)
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
        if (resultCode== Activity.RESULT_OK){
            if (requestCode==999){
                val result =data?.getExtras()?.getString("result")
                Toast.makeText(this,result,Toast.LENGTH_LONG).show()
            }
        }
    }

}
