package com.example.androidaudition;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androidaudition.agent.Agents;
import com.example.androidaudition.agent.Owner;
import com.example.androidaudition.agent.Sales;
import com.example.androidaudition.proxy.SaveInvocationHandler;
import com.example.androidaudition.reflect.B;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

        /**
         *  Java反射
         *      - 定义：Java语言中 一种 动态（运行时）访问、检测 & 修改它本身的能力
         *      - 作用：动态（运行时）获取类的完整结构信息 & 调用对象的方法
         *          - 类的结构信息包括：变量，方法等
         *          - 正常情况下，Java类在编译前，就已经被加载到JVM中；而反射机制使得程序运行时还可以动态地去操作类的变量、方法等信息
         *      - 优点：
         *          - 灵活性高。因为反射属于动态编译，即只有到运行时才动态创建 &获取对象实例。
         *              - 静态编译：在编译时确定类型 & 绑定对象。如常见的使用new关键字创建对象
         *              - 动态编译：运行时确定类型 & 绑定对象。动态编译体现了Java的灵活性、多态特性 & 降低类之间的藕合性
         *      - 缺点:
         *          - 执行效率低
         *              - 因为反射的操作 主要通过JVM执行，所以时间成本会 高于 直接执行相同操作
         *                  - 因为接口的通用性，java的invoke方法时传object和object[]数组的，基本类型参数需要装箱和拆箱
         *                    产生大量额外对象和内存开销，频繁GC
         *                  - 编译器难以对动态调用的代码提前做优化，比如方法内联。
         *                  - 反射需要按名称检索类和方法，有一定的时间开销
         *          - 容易破坏类结构
         *              - 因为反射操作饶过了源码，容易干扰类原有的内部逻辑
         *      - 应用场景：
         *          - 动态获取 类文件结构信息（如变量、方法等） & 调用对象的方法
         *          - 常用的需求场景有：动态代理、工厂模式优化、Java JDBC数据库操作等
         *      - 具体使用
         *          - 反射机制提供的功能
         *              - 运行时（动态）
         *                  - 获取类所有成员变量和方法（包含构造方法，有参数和无参数）
         *                  - 创建一个类对象
         *                      - 获取对象的成员变量，并赋值
         *                      - 调用一个对象的方法
         *                      - 判断一个对象所属的类
         *          - 实现手段
         *              - java.lang.Class
         *                  - java.lang.Class是java反射机制的基础
         *                  - 存放着对应类型对象的 运行时信息
         *                      - java程序运行时，java虚拟机为所有类型维护一个java.lang.Class对象
         *                      - 该class对象存放着所有关于该对象的 运行时信息
         *                      - 泛型形式为Class<T>
         *                  - 每种类型的Class对象只有一个 = 地址只有一个
         *                  - java反射机制的实现除了依靠java.lang.Class类，还需要依靠：Constructor类，Field类，Method类，分别作用于类的各个组成部分：
         *                      - Class：类对象
         *                      - Constructor：类的构造器对象
         *                      - Field：类的属性对象
         *                      - Method：类的方法对象
         *          - 使用步骤
         *              - 获取目标类型的Class对象
         *              - 通过 Class 对象分别获取Constructor类对象，Method类对象，Field类对象
         *              - 通过Constructor类对象，Method类对象，Field类对象分别获取 类的构造函数，方法，属性的具体信息，并进行后续操作
         *
         */
        // 1. 目标类型的Class对象
        获取目标类型的Class对象();
        // 2.通过 Class 对象分别获取Constructor类对象，Method类对象，Field类对象
        获取Constructor类对象和Method类对象和Field类对象();
        // 3.通过Constructor类对象，Method类对象，Field类对象分别获取 类的构造函数，方法，属性的具体信息，并进行后续操作
        获取类的构造方法和方法和属性进行后续操作();

        // 对于2个String类型对象，它们的Class对象相同
        Class c1 = "Carson".getClass();
        Class c2 = null;
        try {
            c2 = Class.forName("java.lang.String");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 用==运算符实现两个类对象地址的比较
        System.out.println(c1 == c2);
        // 输出结果：true

        /**
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         */


    }

    private void 获取类的构造方法和方法和属性进行后续操作() {
        Class<B> bClass = B.class;
        try {
            //1. 通过Constructor 类对象获取类构造函数信息
            Constructor<B> declaredConstructor = bClass.getDeclaredConstructor(int.class);
            Constructor<B> constructor = bClass.getConstructor();
            // 获取构造器函数的名字
            String name = declaredConstructor.getName();
            Log.d("reflect_", "Constructor.name= " + name);
            //获取一个用于描述类中定义的构造器的Class对象
            Class<B> declaringClass = declaredConstructor.getDeclaringClass();
            Log.d("reflect_", "Constructor.declaringClass= " + declaringClass.getName());
            int modifiers = declaredConstructor.getModifiers();
            int modifiers1 = constructor.getModifiers();
            Log.d("reflect_", "Constructor.modifiers= " + modifiers);
            Log.d("reflect_", "Constructor.modifiers1= " + modifiers1);
            //2. 通过Field类对象获取类属性信息
            Field field = bClass.getField("gender");


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void 获取Constructor类对象和Method类对象和Field类对象() {
        // 即以下方法都属于`Class` 类的方法。
        // 1.获取类的构造函数(传入构造函数的参数类型)
        // 1.1 获取指定的构造函数（公共/继承）
        // Constructor<T> getConstructor(Class<?>... parameterTypes)
        Class<?> classType = String.class;
        try {
            classType.getConstructor(int.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 1.2 获取所有的构造函数（公共/继承）
        // Constructor<?>[] getConstructors()
        Constructor<?>[] constructors = classType.getConstructors();
        // 1.3 获取指定的构造函数（不包含继承）
        // Constructor<T> getDeclaredConstructor(Class<?>.. parameterTypes)
        try {
            classType.getDeclaredConstructor(int.class, float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        // 1.4 获取所以构造函数（不包含继承）
        // Constructor<?>[] getDeclaredConstructors()
        Constructor<?>[] declaredConstructors = classType.getDeclaredConstructors();

        // 特别注意：
        // 1. 不带 "Declared"的方法支持取出包括继承、公有（Public） & 不包括有（Private）的构造函数
        // 2. 带 "Declared"的方法是支持取出包括公共（Public）、保护（Protected）、默认（包）访问和私有（Private）的构造方法，但不包括继承的构造函数
        // 下面同理

        // 2.获取类的属性（传入属性名）
        // 2.1 获取指定的属性(公共/继承)
        // Field getField(String name)
        try {
            Field age = classType.getField("age");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        // 2.2 获取所有的属性（公共/继承）
        // Field[] getFields()
        Field[] fields = classType.getFields();
        // 2.3 获取指定的属性（不包括继承）
        // Field getDeclaredField(String name)
        try {
            Field age = classType.getDeclaredField("age");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        // 2.4 获取所有的属性（不包括继承）
        // Field[] getDeclaredFields()
        Field[] declaredFields = classType.getDeclaredFields();

        // 3.获取类的方法（传入方法名&参数类型）
        // 3.1 获取指定的方法(公共/继承)
        // Method getMethod(String name,Class<?>... parameterTypes)
        try {
            Method setAges = classType.getMethod("setAges", int.class, int.class, float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        // 3.2 获取所有的方法（公共/继承）
        Method[] methods = classType.getMethods();
        // 3.3 获取所有指定的方法（不包括继承）
        try {
            Method setName = classType.getDeclaredMethod("setName", String.class, char.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        // 3.4 获取所有的方法（不包括继承）
        Method[] declaredMethods = classType.getDeclaredMethods();

        // 4.Class 类的其他常用方法
        // 返回父类
        Class<?> superclass = classType.getSuperclass();
        // 返回完整的类名
        String name = classType.getName();
        // 快速创建一个类的实例
        // 具体过程：调用默认构造器（若该类无默认构造器，则抛出异常
        // 注：若需要为构造器提供参数需使用java.lang.reflect.Constructor中的newInstance（）
        try {
            Object o = classType.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    private void 获取目标类型的Class对象() {
        // 获取 目标类型的`Class`对象的方式主要有4种

        // 方式1：Object.getClass()
        // object 类中的 getClass()返回一个Class类型的实例
        // 适用于 已经有对象了
        Boolean isTrue = true;
        Class<?> classType1 = isTrue.getClass();
        Log.d("class_type", classType1.getName());

        // 方式2.T.class语法
        // T 任意java类型
        // Class对象表示的一个类型，而这个类型未必一定是类（如int不是类，但是 int.class是一个Class类型的对象）
        // 适用于 知道类型 A类型，B类型 String类型
        Class<?> classType2 = Boolean.class;
        Log.d("class_type", classType2.getName());

        // 方式3.static method Class.forName("");
        // 适用于 知道完整路径
        try {
            Class<?> classType3 = Class.forName("java.lang.Boolean");
            Log.d("class_type", classType3.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 方式4：T.TYPE  语法
        // 不常用
        Class<?> classType4 = Boolean.TYPE;
        Log.d("class_type", classType4.getName());

    }
}
