package com.example.androidaudition;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androidaudition.agent.Agents;
import com.example.androidaudition.agent.Owner;
import com.example.androidaudition.agent.Sales;
import com.example.androidaudition.proxy.SaveInvocationHandler;

public class JavaAdvancedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_advanced);

        /**
         *  Java代理和动态代理机制分析和应用
         *      - 概述
         *          - 代理是一种常用的设计模式，其目的就是为其他对象提供一个代理以控制对某个对象的访问
         *          - 代理类负责为委托类预处理消息，过滤消息并转发消息，以及进行消息被委托类执行后的后续处理
         *          - 根据代理类的生成时间不同可以将代理分为静态代理和动态代理两种
         *      - 代理模式一般涉及到的角色有4种：
         *          - 主题接口：定义代理类和真实主题的公共对外方法，也是代理类代理真实主题的方法
         *          - 真实主题：真正 实现 业务逻辑的类
         *          - 代理类：用来代理和封装真实主题
         *          - 客户端：使用代理类和主题接口完成一些工作
         *      - 代理的优点：
         *          - 隐藏委托类的实现，调用者只需要和代理类进行交互即可
         *          - 解耦，在不改变委托类代码情况下做一些额外处理，比如添加初始判断及其他公共操作
         *      - 代理模式的应用场景：
         *          - 在原方法执行之前和之后做一些操作，可以用代理来实现（比如，记录log，做事务控制等）
         *          - 封装真实的主题类，将真实的业务逻辑隐藏，只暴露给调用者公共的主题接口
         *          - 在延迟加载上的应用
         *      - 静态代理：demo 见agent
         *          - 所谓静态代理也就是在程序运行前就已经存在代理类的字节码文件
         *          - 代理类和委托类的关系在运行前就确定了
         *          - 场景demo : 一个房主要出售自己的房子，但房主不知道谁要买房，也没有时间去接待每一个看房者
         *      - 静态代理类优缺点:
         *          - 优点：业务类只需要关注业务逻辑本身，保证了业务类的重用性。这是代理的共有优点。
         *          - 缺点：
         *              - 代理对象的一个接口只服务于一种类型的对象，如果要代理的方法很多，势必要为每一种方法都进行代理，静态代理在程序规模稍大时，就无法胜任了
         *              - 如果接口增加一个方法，除了所有实现类需要实现这个方法外，所有代理类也需要实现此方法，增加了代码的维护复杂度
         *      - 动态代理：
         *          - 在java的动态代理API中，有两个重要的类和接口，一个是 InvocationHandler(Interface),一个是Proxy（Class），这两个类和接口是实现我们动态代理所必须用到的。
         *          - InvocationHandler(Interface)：
         *              - InvocationHandler 是负责 连接代理类 和 委托类 的 中间类 必须实现的接口，它定义了一个invoke 方法，
         *                 用于集中处理动态代理类对象上的方法调用，通常在该方法中实现对委托的代理访问
         *              - InvocationHandler的核心方法
         *                  - Object invoke(Object proxy, Method method, Object[] args)
         *                  - proxy 该参数为代理类的实例
         *                  - method 被调用的方法对象
         *                  - args 调用method对象的方法参数
         *                  - 该方法也是InvocationHandler接口所定义的唯一的一个方法，该方法负责集中处理动态代理类上的所有方法的调用，
         *                      调用处理器根据这三个参数进行预处理或分派到委托类实例上执行
         *          - Proxy(Class)
         *              - Proxy是 java 动态代理机制的主类，它提供了一组静态方法来为一组接口动态地生成代理类及其对象
         *              - Proxy 的静态方法：
         *                  // 于获取指定代理对象所关联的调用处理器
         *                  static InvocationHandler getInvocationHandler(Object proxy)
         *                  // 该方法用于获取关联于指定类装载器和一组接口的动态代理类的类对象
         *                  static Class getProxyClass(ClassLoader loader, Class[] interfaces)
         *                  // 该方法用于判断指定类对象是否是一个动态代理类
         *                  static boolean isProxyClass(Class cl)
         *                  // 该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例
         *                  static Object newProxyInstance(ClassLoader loader, Class[] interfaces,InvocationHandler h)
         *                      loader 指定代理类的ClassLoader加载器
         *                      interfaces 指定代理类要实现的接口
         *                      h: 表示的是当我这个动态代理对象在调用方法的时候，会关联到哪一个InvocationHandler对象上
         *          - 使用Java 动态代理的两个重要步骤 demo 见proxy
         *              - 通过实现InvocationHandler 接口创建自己的调用处理器
         *              - 通过为Proxy类的newProxyInstance方法指定代理类的ClassLoader 对象和代理要实现的interface以及调用处理器InvocationHandler对象 来创建动态代理类的对象
         *          - 动态代理的优缺点：
         *              - 优点：
         *                  - 动态代理类的字节码在程序运行时由Java反射机制动态生成，无需程序员手工编写它的源代码
         *                  - 动态代理类不仅简化了编程工作，而且提高了软件系统的可扩展性，因为Java 反射机制可以生成任意类型的动态代理类
         *              - 缺点：
         *                  - JDK的动态代理机制只能代理实现了接口的类，而不能实现接口的类就不能实现JDK的动态代理
         *
         */

        findViewById(R.id.btn_agent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //顾客使用 client 使用
                Agents agents = new Agents();
                agents.sell();
            }
        });
        findViewById(R.id.btn_proxy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sales delegate = new Owner();
                Log.d("proxy_", "开始创建代理");
                Sales proxy = (Sales) new SaveInvocationHandler().bind(delegate);
                Log.d("proxy_", "代理创建完成");
                proxy.sell();
                Log.d("proxy_", "方法执行完成");
            }
        });

        /**
         *  java 异常体系
         *      - 见 throwable.png
         *      - 基本的体系结构：
         *          - Throwable
         *              - Error
         *                  - VirtualMachineError
         *                      - StackOverFlowError
         *                      - OutOfMemoryError
         *                  - AWTError
         *              - Exception
         *                  - IOException
         *                  - SQLException
         *                  - RuntimeException
         *      - 处理异常：try{} catch{}finally {}
         */

        /**
         *  Java中实现多态的机制是什么
         *      - java中实现多态的机制是 依靠 父类或者接口的引用 指向 子类
         *      - 从而实现一个对象的多种形态的特性。
         *      - 父类的引用是在程序运行时 动态 地指向具体实例
         *      - 调用该引用的方法时，不是根据引用变量的 类型中定义的方法 来运行，而是根据具体的实例方法
         *      - 概念：
         *          - 多态就是指 一个引用变量 到底 指向哪个类的实例对象
         *          - 该引用变量 发出的方法 调用哪个类中实现的方法，必须由程序运行期间才能决定
         *          - 不修改程序代码就可以改变程序运行时所绑定的具体代码，让程序可以选择多个运行状态，这就是多态性
         *      - 特点：
         *          - 指向子类的父类引用 由于 向上转型 他只能访问父类中拥有的方法和属性
         *          - 对于子类中存在 而父类中不存在的方法，该引用是不能使用的
         *          - 若子类重写了父类中的某些方法，在调用该方法的时候，必须是使用子类定义的这些方法（动态链接，动态调用）
         *
         */
    }
}
