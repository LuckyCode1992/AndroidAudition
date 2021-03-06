package com.example.androidaudition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidaudition.inner_class.InterfaceA;
import com.example.androidaudition.inner_class.OutClass;
import com.example.androidaudition.inner_class.PrivateInnerOutClass;
import com.example.androidaudition.inner_class.StaticInnerOutClass;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class JavaBasicsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_basics);

        /**
         * 1.java中==和equals和hashCode的区别：
         *      1. == ： 该操作符生成的是一个boolean结果，它计算的是操作数的值之间的关系，即：基本类型比较值，引用类型比较内存地址
         *          1.1 操作数的值：
         *              1.1.1 基本数据类型变量：
         *                  浮点型：float(4 byte), double(8 byte)
         *                  整型：byte(1 byte), short(2 byte), int(4 byte) , long(8 byte)
         *                  字符型: char(2 byte)
         *                  布尔型: boolean(JVM规范没有明确规定其所占的空间大小，仅规定其只能够取字面值”true”和”false”)
         *                  对于这八种基本数据类型的变量，变量直接存储的是“值”。因此，在使用关系操作符 == 来进行比较时，比较的就是“值”本身。
         *              1.1.2 引用类型：
         *                  引用类型的变量存储的并不是“值”本身，而是与其关联的对象在内存中的地址
         *          1.2：例子见 fun_1_1
         *
         *      2. equals ： Object 的 实例方法，比较两个对象的content是否相同
         *          2.1 在Object类中，equals方法是用来比较两个对象的引用是否相等，即是否指向同一个对象。
         *          2.2 String 重写了 equals 方法。见String 中的方法。比较字符的个数，和对应位数的字符是否相等。
         *          2.3 总结：equals方法
         *              先 比较引用是否相同(是否为同一对象)(object 中的equals方法)
         *              再 判断类型是否一致（是否为同一类型）,（子类重写 比如 Integer）
         *              最后 比较内容是否一致 （子类重写，比如 Integer）
         *          2.4 equals 重写原则：（对象内容的比较才是设计equals()的真正目的）
         *              - 对称性： 如果x.equals(y)返回是“true”，那么y.equals(x)也应该返回是“true”
         *              - 自反性： x.equals(x)必须返回是“true”
         *              - 类推性： 如果x.equals(y)返回是“true”，而且y.equals(z)返回是“true”，那么z.equals(x)也应该返回是“true”
         *              - 一致性： 如果x.equals(y)返回是“true”，只要x和y内容一直不变，不管你重复x.equals(y)多少次，返回都是“true”
         *              - 任何情况下，x.equals(null)【应使用关系比较符 ==】，永远返回是“false”；x.equals(和x不同类型的对象)永远返回是“false”
         *
         *
         *      3. hashCode ： Object 的 native方法 , 获取对象的哈希值，用于确定该对象在哈希表中的索引位置，它实际上是一个int型整数
         *          3.1 哈希相关概念
         *              3.1.1 概念 ：
         *                  Hash 就是把任意长度的输入(又叫做预映射， pre-image)，通过散列算法，变换成固定长度的输出(int)，
         *                  该输出就是散列值。这种转换是一种 压缩映射，也就是说，散列值的空间通常远小于输入的空间。
         *                  不同的输入可能会散列成相同的输出，从而不可能从散列值来唯一的确定输入值。
         *                  简单的说，就是一种将任意长度的消息压缩到某一固定长度的消息摘要的函数。
         *              3.1.2 应用–数据结构 ：
         *                  数组的特点是：寻址容易，插入和删除困难; 而链表的特点是：寻址困难，插入和删除容易。
         *                  那么我们能不能综合两者的特性，做出一种寻址容易，插入和删除也容易的数据结构？答案是肯定的，这就是我们要提起的哈希表，
         *                  哈希表有多种不同的实现方法，我接下来解释的是最常用的一种方法——拉链法，我们可以理解为 “链表的数组”
         *                  将根据元素特征计算元素数组下标的方法就是散列法。
         *                  拉链法的适用范围 ： 快速查找，删除的基本数据结构，通常需要总数据量可以放入内存。
         *          3.2 hashCode 简述 （在 Java 中，由 Object 类定义的 hashCode 方法会针对不同的对象返回不同的整数。
         *                             （这是通过将该对象的内部地址转换成一个整数来实现的，但是 JavaTM 编程语言不需要这种实现技巧）。）
         *              3.2.1 hashCode 的常规协定是
         *                  - 在 Java 应用程序执行期间，在对同一对象多次调用 hashCode 方法时，必须一致地返回相同的整数，
         *                    前提是将对象进行 equals 比较时所用的信息没有被修改
         *                  - 如果根据 equals(Object) 方法，两个对象是相等的，那么对这两个对象中的每个对象调用 hashCode 方法都必须生成相同的整数结果。
         *                  - 如果根据 equals(java.lang.Object) 方法，两个对象不相等，那么对这两个对象中的任一对象上调用 hashCode 方法
         *                    不要求 一定生成不同的整数结果。但是，程序员应该意识到，为不相等的对象生成不同整数结果可以提高哈希表的性能。
         *                  - hash表存入值得方法（步骤）
         *                      - 先调用这个元素的 hashCode 方法，然后根据所得到的值计算出元素应该在数组的位置。如果这个位置上没有元素，
         *                        那么直接将它存储在这个位置上；
         *                      - 如果这个位置上已经有元素了，那么调用它的equals方法与新元素进行比较：相同的话就不存了，
         *                        否则，将其存在这个位置对应的链表中（Java 中 HashSet, HashMap 和 Hashtable的实现总将元素放到链表的表头）。
         *      4.总结：
         *          - hashcode是系统用来快速检索对象而使用
         *          - equals方法本意是用来判断引用的对象是否一致
         *          - 重写equals方法和hashcode方法时，equals方法中用到的成员变量也必定会在hashcode方法中用到,只不过前者作为比较项，
         *          后者作为生成摘要的信息项，本质上所用到的数据是一样的，从而保证二者的一致性
         *
         */

        /**
         *  2.java 基本数据类型
         *      - Java基本类型共有八种，基本类型可以分为三类
         *      - java 基本类型共有8种，可以分为三类：
         *          - 字符类型
         *              - char:16位 2字节 存储Unicode码，用单引号赋值，如'a'
         *          - 布尔类型
         *              - boolean ：只有true和false两个取值
         *                  - 在 boolean 数组中时，占用1字节
         *                  - 在 单个存在时 比如 boolean b = false ，取决于jvm虚拟机，正规情况下，是4字节
         *          - 数值类型 （分为整数类型和浮点类型， 数值类型不存在无符号的，它们的取值范围是固定的，不会随着机器硬件环境或者操作系统的改变而改变）
         *              - 整数类型 byte、short、int、long
         *                  - byte: 8位 1字节 存放的数据范围是-128~127之间  Byte.MIN_VALUE~Byte.MAX_VALUE
         *                  - short: 16位 2字节 最大数据存储量是65535（2的16次方-1）  数据范围是 -32768~32767（负的2的15次方到2的15次方减1）
         *                  - int: 32位 4字节 最大存储量是 2的32次方减1  数据范围是负的2的31次方到正的2的31次方减1
         *                  - long: 64位 8字节 最大数据存储容量是2的64次方减1  数据范围为负的2的63次方到正的2的63次方减1
         *              - 浮点类型 float、double
         *                  - float: 32位 4字节 数据范围在3.4e-45~1.4e38，直接赋值时必须在数字后加上f或F
         *                  - double: 64位  8字节 数据范围在4.9e-324~1.8e308，赋值时可以加d或D也可以不加
         */

        /**
         *  2.Java中的四种引用方式(强引用、软引用、弱引用、虚引用)
         *      1 强引用（StrongReference）
         *          1.1 强引用就是指在程序代码之中普遍存在的 比如 Object obj = new Object();
         *              - 只要某个对象有强引用与之关联，JVM必定不会回收这个对象，即使在内存不足的情况下，JVM宁愿抛出OutOfMemory错误也不会回收这种对象
         *              - 如果想中断强引用和某个对象之间的关联，可以显示地将引用赋值为null，这样一来的话，JVM在合适的时间就会回收该对象
         *      2 软引用（SoftReference）
         *          2.1 软引用是用来描述一些有用但并不是必需的对象，在Java中用java.lang.ref.SoftReference类来表示
         *              - 对于软引用关联着的对象，只有在内存不足的时候JVM才会回收该对象
         *              - 因此，这一点可以很好地用来解决OOM的问题，并且这个特性很适合用来实现缓存：比如网页缓存、图片缓存等。
         *              - 软引用可以和一个引用队列（ReferenceQueue）联合使用，如果软引用所引用的对象被JVM回收，这个软引用就会被加入到与之关联的引用队列中
         *              - 例子见 fun_2_2
         *      3 弱引用（WeakReference）
         *          3.1 弱引用也是用来描述非必需对象的，当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象
         *              - 在java中，用java.lang.ref.WeakReference类来表示 使用方式和软引用一样
         *      4 虚引用（PhantomReference）
         *              - 虚引用和前面的软引用、弱引用不同，它并不影响对象的生命周期
         *              - 在java中用java.lang.ref.PhantomReference类表示
         *              - 如果一个对象与虚引用关联，则跟没有引用与之关联一样，在任何时候都可能被垃圾回收器回收。
         */

        /**
         * 3.int与integer的区别
         *      - Integer是int的包装类；int是基本数据类型；
         *      - Integer变量必须实例化后才能使用；int变量不需要；
         *      - Integer实际是对象的引用，指向此new的Integer对象；int是直接存储数据值 ；
         *      - Integer的默认值是null；int的默认值是0。
         *      - 泛型不支持int，但是支持Integer
         *      - int 存储在栈中，Integer 对象的引用存储在栈空间中，对象的数据存储在堆空间中。
         *      1.自动装箱和自动拆箱(8种基本类型才有)
         *          1.1 自动装箱：将基本数据类型重新转化为对象： Integer num = 1;
         *          1.2 自动拆箱：将对象重新转化为基本数据类型： Integer integer = 1; int a = integer+5;
         *          1.3 相同值下的 int 和 Integer 的比较结果 见例子 fun_3_1_1
         *              - 两个通过new生成的变量,是创建两个对象的比较
         *              - int 和 Integer 的值比较,Integer会自动拆箱为int类型，然后再做比较，实际上就变为两个int变量的比较
         *              - new 生成的Integer变量 和 非new 生成的Integer变量比较，new 生成的Integer变量的值在堆空间中，
         *                非new 生成的Integer变量的值在在常量池中
         *              - 对于两个非new生成的Integer对象，进行比较时，如果两个变量的值在区间-128到127之间，则比较结果为true，
         *                如果两个变量的值不在此区间，则比较结果为false
         *              - 非new生成的Integer变量，会先判断常量池中是否有该对象，若有则共享，若无则在常量池中放入该对象；这也叫享元模式
         */

        /**
         *  4 进制
         *      4.1 基本概念 0b10011001 (0b开头表示二进制)
         *          - 数码：值这个数据的每一位的数字
         *          - 数位：数码在这个数中的位置，从右到左，依次递增，从0开始
         *          - 基数：每一个数码可以有多少个数据表示（其实就是指这个数的进制）
         *          - 位权： 数码 * （基数的数位次方）
         *      4.2 进制转换：
         *          4.2.1 十进制与二进制转换：
         *              - 10进制转2进制：除以2取余，直到余数是1或者0，然后将余数倒序就是十进制对应的二进制
         *              - 2进制转10进制：加权法，将二进制数的没个数码的位权相加
         *          4.2.2 十进制于八进制转换
         *          - 10进制转8进制：除以8取余
         *          - 8进制转10进制：加权法，和2进制相似
         *          4.2.3 二进制和八进制的转换
         *              - 二进制转八进制：三合一法则，将这个二进制从低位到高位（左高，右低）每三位分成1组，高位不够补0，将每组转换转为8进制
         *                然后将每一组的八进制连起来。
         *              - 8进制转2进制：一拆三，将八进制数的每一个数码拆分为一个三位的二进制，然后连起来
         *          4.2.4 二进制和十六进制的转换
         *              - 二进制转十六进制：四合一法则，参考三合一
         *              - 十六进制转二进制：一拆四法则，参考一拆三
         */

        /**
         *
         *  5 原码，反码，补码 和运算
         *      - 无论任何数据，在内存中存储的时候都是以二进制的形式存储的，
         *        原码，反码，补码都是二进制，只不过是二进制的不同表现形式，数据是以补码的二进制存储的。
         *      5.1 以int为例 ：4字节 32位
         *          00000000 00000000 00000000 00000000
         *          为了表示正负，使用最高位表示正负 0表示正，1表示负
         *          int，表示数据只有31位
         *      5.2 原码：
         *          最高位表示符号位，剩下的位数，是这个数的绝对值的二进制
         *          10 的原码：
         *          00000000 0000000 00000000 00001010
         *          -8 的原码：
         *          10000000 0000000 00000000 00001000
         *      5.3 反码：
         *          - 正数的反码，是其原码
         *          - 负数的反码，是其原码基础上，符号位不变，其他位取反
         *          10的反码：
         *          00000000 00000000 00000000 00001010
         *          -8的反码：
         *          11111111 11111111 11111111 11110111
         *      5.4 补码：
         *          - 正数的补码，就是其原码
         *          - 负数的补码，就是在其反码基础上+1
         *      5.5 按位与运算 &
         *          - 指的是1个二进制数据的每一位参与运算，
         *          - 参与位运算的二进制必须是补码形式
         *          - 运算的结果也是二进制的补码形式
         *          - 同位，均为1，结果为1，否则为0.
         *          00000000 00000000 00000000 00000001
         *          00000000 00000000 00000000 00001001
         *          结果为
         *          00000000 00000000 00000000 00000001
         *          - 在判断一个数的奇偶时，可以使用 （x&1）== 0
         *      5.6 按位或 |
         *          - 参与二进制的数据，只要有一位是1结果就为1 （依然是补码形式）
         *            00000000 00000000 00000000 00000001
         *            00000000 00000000 00000000 00001001
         *            结果为
         *            00000000 00000000 00000000 00001001
         *      5.7 按位取反 ~
         *          - 单目运算，将二进制每一位取反（包含符号位，依然是补码形式）
         *      5.8 按位异或 ^
         *          - 参与的数据，如果相同为0，不同为1
         *          使用： 交换两个数据
         *          a = a^b;b = a^b;a=a^b;
         *      5.9 按位 左移，右移
         *          - 左移：向左移动指定位置，低位不够补0，高位溢出丢掉
         *              - 左移运算可能改变正负
         *              - 将数左移n位，相当于，这个数 * 2的n次方
         *          - 向右移动指定位置，低位溢出丢掉，高位补符号位（原来是0，就补0 ，原来是1，就补1）。
         *              - 右移不会改变正负
         *              - 将数右移，相当于，这个数 除以 2的n次方
         *
         */

        /**
         *  6.java多态
         *      - 多态是同一个行为具有多个不同的表现形式
         *      - 当使用多态方式调用方法时，首先检查父类中是否有该方法，如果没有，则编译错误；如果有，再去调用子类的同名方法。
         *        多态的好处：可以使程序有良好的扩展，并可以对所有类的对象进行通用处理。
         *      6.1 多态的优点
         *          - 消除类型之间的耦合关系
         *          - 可替换性
         *          - 可扩展性
         *          - 接口性
         *          - 灵活性
         *          - 简化性
         *      6.2 多态存在的必要条件
         *          - 继承
         *          - 重写
         *          - 父类引用指向子类引用 （ Father f = new Child(); ）
         *      6.3 虚函数
         *          - 虚函数的存在是为了多态。
         *          - Java 中其实没有虚函数的概念，它的普通函数就相当于 C++ 的虚函数，动态绑定是Java的默认行为。
         *            如果 Java 中不希望某个函数具有虚函数特性，可以加上 final 关键字变成非虚函数。
         *      6.4 多态的实现方式
         *          - 重写
         *          - 接口
         *          - 抽象类和抽象方法
         *
         */

        /**
         *  7.String、StringBuffer、StringBuilder区别
         *      7.1 String: 字符串常量,String类是不可变类，任何对String的改变都 会引发新的String对象的生成
         *      7.2 StringBuffer 字符串变量（线程安全）
         *      7.3 StringBuilder 字符串变量（非线程安全）
         */

        /**
         * java 内部类
         *      - 内部类 ( inner class )： 定义在另一个类中的类
         *      - 内部类为什么存在？（内部类的作用/内部类的特点（优点方面））
         *          - 1. 内部类方法可以访问该类定义所在作用域中的数据，包括被 private 修饰的私有数据(见OutClass)
         *              - 当外部类的对象创建了一个内部类的对象时，内部类对象必定会秘密捕获一个指向外部类对象的引用，
         *              然后访问外部类的成员时，就是用那个引用来选择外围类的成员的。当然这些编辑器已经帮我们处理了
         *              - 原因：为什么内部类可以访问外部类的数据？
         *                  - 当外部类的对象创建内部类的对象时，内部类对象会捕获到一个指向外部类对象的引用，有了这个引用（this）就可以访问外部类对象的数据
         *                      - 关键代码：
         *                          - final com.xxx.xxx.Out this$0
         *                              - 编译器会默认为成员内部类添加一个指向外部类对象的引用，就是这个this
         *                          - public com.xxx.xxx.Out$Inner(com.xxx.xxx.Out)
         *                              - 虽然我们在定义的内部类的构造器是无参构造器，编译器还是会默认添加一个参数，
         *                              - 编译器会默认给内部类构造一个带外部类 类型 的一个参数的构造器
         *                              - 所以，在创建内部类对象的时候，就将外部类的对象的指针引用，传入了内部类
         *                              - 因此，内部类就拥有了外部类的指针引用，就可以随意访问外部类
         *                              - 同时，间接说明成员内部类 依赖于外部类，（构造内部类，需要传入外部类的指针引用）
         *                      - 因为 内部类在外部类中，所以符合 “同类中”的条件，又拥有外部类的引用，所以，就能访问外部类的private数据了
         *          - 2. 内部类可以对同一包中的其他类隐藏起来
         *              - 在内部类前加上 private 这样，内部类就完全隐藏起来，其他类无法访问,但是，如果改用public修饰，能被其他类访问
         *              - 例子：btn_inner_1 。作用就是安全。
         *          - 3. 内部类可以实现 java 单继承的缺陷
         *              - 采用内部类继承对应的类，外部类再创建，使用内部类的方式。
         *              - 例子见 ExtendsOutClass
         *          - 4. 当我们想要定义一个回调函数，却不想写大量代码的时候 我们可以选择使用匿名内部类来实现
         *      - 内部类与外部类的关系
         *          - 非静态内部类，内部类的创建，依赖 外部类 的实例对象，换言之，先有外部类对象，才能有内部类对象
         *          - 内部类是一个相对独立的实体，外部类，是have a的关系，相当于，外部类有一个属性叫内部类
         *      - 内部类的分类
         *          - 静态内部类（嵌套类）
         *          - 非静态内部类
         *              - 成员内部类
         *              - 方法内部类
         *              - 匿名内部类
         *          - 静态内部类和非静态内部类的区别
         *              - 1. 静态内部类可以有静态成员，非静态内部类不能有静态成员
         *              - 2. 静态内部类可以访问外部类的 静态变量 ，不可以访问外部类的 非静态变量
         *              - 3. 非静态内部类的非静态成员 可以访问外部类的 非静态变量 和 静态变量
         *              - 4. 静态内部类的 创建 不依赖 外部类，而非静态内部类 依赖外部类的创建
         */
        findViewById(R.id.btn_inner_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivateInnerOutClass privateInnerOutClass = new PrivateInnerOutClass();
                InterfaceA interfaceA = privateInnerOutClass.interfaceATest();
                interfaceA.funA();
            }
        });
        findViewById(R.id.btn_inner_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 非静态内部类 创建
                StaticInnerOutClass.InnerNor innerNor = new StaticInnerOutClass().new InnerNor();
                innerNor.nor();
                //静态内部类创建
                 StaticInnerOutClass.InnerStatic innerStatic = new StaticInnerOutClass.InnerStatic();
                 innerStatic.sta();
                 //静态内部类，静态成员调用
                 StaticInnerOutClass.InnerStatic.sta2();

            }
        });


        /**
         *  8. JAVA 内部类
         *      8.1 内部类为什么存在
         *          - 内部类（inner class）：定义在另一个类中的类
         *          - 内部类的特点：
         *              - 内部类方法可以访问改定义所在作用域中的数据，包括被private修饰的私有数据（内部类可以访问外部类所以方法和属性见A类）
         *              - 内部类可以对同一包中的其他类隐藏（安全）
         *              - 内部类可以实现java单继承的缺陷
         *              - 当我们想定义一个回调函数，缺不想写大量代码的时候，可以使用匿名内部类实现
         *      8.2 内部类与外部类的关系
         *          - 对于非静态内部类，内部类的创建依赖外部类的实例对象，在没有外部类对象实例之前无法创建内部类
         *          - 内部类是一个相对独立的实体，与外部类不是 is -a 关系
         *          - 创建内部类的时刻并不依赖于外部类的创建
         *      8.3 内部类的分类
         *          - 静态内部类（嵌套类）
         *          - 非静态内部类
         *              - 成员内部类
         *              - 方法内部类（局部内部类）
         *                  - 如果一个内部类只有一个方法中使用到了，那么我们可以将这个定义在方法内部，
         *                      这种内部类就被称为局部内部类，作用域，仅限于该方法
         *                  - 注意：
         *                      - 局部内部类不允许使用权限修饰符(比如 void fun(){ public Class C{} })，不被允许
         *                      - 局部内部类对外完全隐藏，除了创建类的方法可以访问，其他不允许访问
         *                      - 局部内部类与成员内部类不同之处，是他可以引用成员变量，但该成员必须声明为final
         *              - 匿名内部类
         *                  - 匿名内部类是没有访问修饰符的。
         *                  - 匿名内部类必须继承一个抽象类或者实现一个接口
         *                  - 匿名内部类中不能存在任何静态成员或方法
         *                  - 匿名内部类是没有构造方法的，因为它没有类名。
         *                  - 与局部内部相同匿名内部类也可以引用局部变量。此变量也必须声明为 final
         *      8.4 静态内部类，可能会引起内存泄漏：
         *          - 如果当内部类的引用被外部类以外的其他类引用时，就会造成内部类和外部类无法被GC回收的情况，
         *            即使外部类没有被引用，因为内部类持有指向外部类的引用）
         *          - 可以采用弱引用的方式 ，来持有外部类对象
         *
         */

        /**
         *  9 抽象类和接口
         *      9.1 什么是抽象类和接口
         *          9.1.1 抽象类
         *              - abstract 修饰的类
         *              - 抽象类的特点：
         *                  - 抽象类，不允许直接实例化
         *                  - 抽象类，通常作为父类
         *                  - 抽象方法必须是public或者protected，缺省情况下，默认是public
         *                  - 抽象方法，必须由子类实现
         *                  - 如果一个类继承抽象类，则子类必须实现抽象方法，或者子类也为抽象类
         *                  - 抽象类还是很重要的重构工具，因为它们使得我们很容易地将公共方法沿着继承结构向上移动
         *          9.1.2 接口
         *              - 接口 是抽象类的特殊形式，使用interface修饰
         *              - 接口的特点：
         *                  - 接口所有方法的访问权限均为public
         *                  - 接口中定义的成员变量，会自动变为 public static final 修饰的静态常亮
         *                  - 实现接口的非抽象类必须实现接口所有方法，抽象类可以部分实现
         *                  - 接口不能直接创建对象，但可以申明一个接口变量，方便调用
         *                  - 完全解耦，可以编写可复用性更好的代码
         *       9.2 接口和抽象类的不同
         *          - 抽象层次不同
         *              - 抽象类是对类抽象，接口是对行为抽象
         *              - 抽象类是对整个类整体进行抽象，包括属性，行为，但接口却是对类局部行为进行抽象
         *          - 跨域不同
         *              - 抽象类所跨域的是具有相似特点的类，而接口却可以跨域不同的类
         *              - 抽象类所体现的一种继承关系，考虑的是子类和父类本质"是不是"同一类的关系
         *              - 接口不要求实现类和接口是同一本质，它们之间只存在"有没有这个能力"的关系
         *          - 设计层次不同
         *              - 抽象类是自下而上的设计，子类中重复出现的工作，抽象到抽象类中
         *              - 接口是自上而下，定义行为和规范
         *       9.3 如何选择抽象类还是接口
         *          - 只有必须使用方法定义或者成员变量的时候，才考虑使用抽象类
         *          - 其他情况，都应该优先使用接口。接口可以多实现，更灵活，抽象类，只能单继承。
         *
         *
         */

        /**
         *  10 java泛型
         *      10.1 泛型是什么
         *          - List<String> list=new ArrayList<>();
         *          - ArrayList 就是一个泛型。泛型，就是泛指的意思。设定一个数据类型，避免出现运行时数据转型错误。
         *      10.2 泛型介绍
         *          10.2.1 java泛型类
         *              - 类结构是面向对象中最基本的元素，如果我们需要类具有很好的扩展性，那么我们可以将其设置为泛型的
         *              - 见类 DataHolder
         *          10.2.2 java泛型方法
         *              - 如果使用泛型方法可以解决问题，那么应该尽量使用泛型方法
         *              - 见 DataHolder
         *              - 泛型方法的基本特征：
         *                  - public 和 返回值中间（<T>）非常重要,可以理解为声明此方法为泛型方法： public <T> void xx(T t){}
         *                  - 只要声明了的方法才是泛型方法，泛型类中的使用泛型的成员方法并不是泛型方法
         *                  - 表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T
         *                  - 与泛型类的定义一样，此时T可以随便写为任意标识，常见的如T,E,K等形式
         *          10.2.3 java泛型接口
         *              - 泛型接口例子 见 Generator
         *          10.2.4 java泛型擦除及相关内容
         *              - 类型擦除例子 见 fun_10_2_4
         *              - 编译器虽然会在编译过程中移除参数的类型信息，但是会保证类或方法内部参数类型的一致性。
         *              - 泛型参数将会被擦除到它的第一个边界（边界可以有多个，重用 extends 关键字，通过它能给参数类型添加一个边界）见 ManiPulator
         *              - 为了“还原”返回结果的类型，编译器在get之后添加了类型转换。它是编译器自动帮我们加进去的。
         *              10.2.4.1 java泛型擦除的缺陷和补救措施
         *                 -缺陷： 泛型类型不能显式地运用在运行时类型的操作当中，例如：转型、instanceof 和 new。因为在运行时，所有参数的类型信息都丢失了。
         *                 - 补救：
         *                      - 类型判断问题（instanceof 问题） 见GenericType 见方法 fun_10_2_4_1()
         *                      - 创建类型实例(new 问题)
         *                          - 泛型代码中不能new T()的原因有两个，一是因为擦除，不能确定类型；二是无法确定T是否包含无参构造函数。
         *                          - 解决方法，见 Factory 方法 fun_10_2_4_2()
         *                          - 不建议使用泛型数组，所以就用ArrayList 代替。
         *          10.2.5 java泛型通配符（? extends T 和 ? supper T ）
         *              - 例子见 fun_10_2_5()
         */

        /**
         *  11 父类的静态方法能否被子类重写
         *      - 不能
         *      - 静态方法的调用方式，是类.方法。
         */

        /**
         * 12 Java中final,finalize和finally的区别
         *      - final 用于修饰 类，方法，变量
         *          - final关键字用于基本数据类型前：这时表明该关键字修饰的变量是一个常量，在定义后该变量的值就不能被修改
         *          - final关键字用于方法声明前：这时意味着该方法时最终方法，只能被调用，不能被覆盖，但是可以被重载。
         *          - final关键字用于类名前：此时该类被称为最终类，该类不能被其他类继承。
         *      - finalize方法来自于java.lang.Object，用于回收资源。
         *      - finally 常用与 try catch场景。通常在会后一定要执行的地方，就使用，比如，关闭文件流。
         */

        /**
         *  13 序列化：
         *      - 序列化是将对象的状态信息转换为可以存储或传输的形式的过程
         *      - 序列化的方式：json，xml文件。还有Serializable，Parcelable
         *      13.1 Serializable：
         *          - 表示将一个对象转换成可存储或可传输的状态。序列化后的对象可以在网络上进行传输，也可以存储到本地。
         *          - Serializable的特点：
         *              - 可序列化类中，未实现 Serializable 的属性状态无法被序列化/反序列化
         *              - 反序列化一个类的过程中，它的非可序列化的属性将会调用无参构造函数重新创建
         *              - 这个属性的无参构造函数必须可以访问，否者运行时会报错
         *              - 一个实现序列化的类，它的子类也是可序列化的
         *      13.2 Parcelable：
         *          - 将一个完整的对象进行分解， 而分解后的每一部分都是Intent所支持的数据类型，这样也就实现传递对象的功能。
         *          - Parcelable的作用：
         *              - 永久性保存对象，保存对象的字节序列到本地文件中
         *              - 通过序列化对象在网络中传递对象
         *              - 通过序列化在进程间传递对象
         *      13.3 Parcelable和Serializable之间的区别：
         *          - Serializable使用IO读写存储在硬盘上，Parcelable是直接在内存中读写
         *          - Parcelable的效率比Serializable 高
         *          - Serializable序列化过程使用了反射技术，并且期间产生临时对象，容易触发垃圾回收
         */

        /**
         *  14 线程和进程的区别
         *      - 进程（process）和线程（thread）是操作系统的基本概念
         *      - 类比的方式：
         *          1.计算机的核心是CPU，它承担了所有的计算任务。它就像一座工厂，时刻在运行。
         *          2.假定工厂的电力有限，一次只能供给一个车间使用。也就是说，一个车间开工的时候，其他车间都必须停工。背后的含义就是，单个CPU一次只能运行一个任务。
         *          3.进程就好比工厂的车间，它代表CPU所能处理的单个任务。任一时刻，CPU总是运行一个进程，其他进程处于非运行状态。
         *          4.一个车间里，可以有很多工人。他们协同完成一个任务。
         *          5.线程就好比车间里的工人。一个进程可以包括多个线程。
         *          6.车间的空间是工人们共享的，比如许多房间是每个工人都可以进出的。这象征一个进程的内存空间是共享的，每个线程都可以使用这些共享内存。
         *          7.可是，每间房间的大小不同，有些房间最多只能容纳一个人，比如厕所。里面有人的时候，其他人就不能进去了。
         *            这代表一个线程使用某些共享内存时，其他线程必须等它结束，才能使用这一块内存。
         *          8.一个防止他人进入的简单方法，就是门口加一把锁。先到的人锁上门，后到的人看到上锁，就在门口排队，等锁打开再进去。
         *            这就叫"互斥锁"（Mutual exclusion，缩写 Mutex），防止多个线程同时读写某一块内存区域。
         *          9.还有些房间，可以同时容纳n个人，比如厨房。也就是说，如果人数大于n，多出来的人只能在外面等着。
         *            这好比某些内存区域，只能供给固定数目的线程使用。
         *          10.这时的解决方法，就是在门口挂n把钥匙。进去的人就取一把钥匙，出来时再把钥匙挂回原处。后到的人发现钥匙架空了，就知道必须在门口排队等着了。
         *             这种做法叫做"信号量"（Semaphore），用来保证多个线程不会互相冲突。
         *             不难看出，mutex是semaphore的一种特殊情况（n=1时）。也就是说，完全可以用后者替代前者。但是，因为mutex较为简单，且效率高，
         *             所以在必须保证资源独占的情况下，还是采用这种设计。
         *          11.操作系统的设计，因此可以归结为三点：
         *              - 以多进程形式，允许多个任务同时运行；
         *              - 以多线程形式，允许单个任务分成不同的部分运行；
         *              - 提供协调机制，一方面防止进程之间和线程之间产生冲突，另一方面允许进程之间和线程之间共享资源。
         */


        findViewById(R.id.btn_fun_1_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun_1_1();
            }
        });
        findViewById(R.id.btn_fun_2_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun_2_2();
            }
        });
        findViewById(R.id.btn_fun_3_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun_3_1();
            }
        });
        findViewById(R.id.btn_fun_10_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun_10_2_4();
            }
        });
        findViewById(R.id.fun_10_2_4_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun_10_2_4_1();
            }
        });
        findViewById(R.id.fun_10_2_4_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun_10_2_4_2();
            }
        });
        findViewById(R.id.fun_10_2_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fun_10_2_5();
            }
        });

    }


    void fun_10_2_5() {
        List<TextView> textViews = new ArrayList<>();
        // java 为了保证类型擦除中的类型安全，添加了这种限制，子类泛型类型对象不能 赋值 给父类的泛型类型声明
//        List<TextView> textViews1 = new ArrayList<Button>();
        List<? extends TextView> textViews1 = new ArrayList<Button>();
        // 不允许修改和赋值，但允许获取
        // 上界通配符的应用 扩大 变量或参数的接收范围
        print(new Button(this));
        prints(textViews);
        List<Button> buttons = new ArrayList<>();
        prints(buttons);

        //下界通配符
        // 只能修改不能使用 可以set不能get
        List<? super Button> buttonList = new ArrayList<TextView>();
        List<TextView> textViews2 = new ArrayList<>();
        addTextiew(textViews2);


    }

    void addTextiew(List<? super Button> buttons) {

    }

    void print(TextView tv) {
        System.out.println(tv.getText());
    }

    void prints(List<? extends TextView> textViews) {

    }

    void fun_10_2_4_2() {
        Create<Integer> create = new Create<>();
        create.newInstance(new IntegerFactory());
        System.out.println(create);
        System.out.println(create.instance);
    }

    void fun_10_2_4_1() {
        GenericType<A> genericType = new GenericType<>(A.class);
        System.out.println(genericType.isInstance(new A()));
        System.out.println(genericType.isInstance(new DD()));
    }

    void fun_10_2_4() {
        Class<?> class1 = new ArrayList<String>().getClass();
        Class<?> class2 = new ArrayList<Integer>().getClass();
        System.out.println(class1);
        System.out.println(class2);
        System.out.println(class1.equals(class2));
        A a1 = new A();
        A a2 = new A();
        System.out.println(a1 == a2);
        System.out.println(a1.equals(a2));

    }

    void fun_3_1() {
        Integer i1 = new Integer(3);
        Integer i2 = new Integer(3);
        Integer i3 = Integer.valueOf(3);
        Integer i4 = 3;
        int i5 = 3;
        Integer i6 = 3;
        Integer i7 = 128;
        Integer i8 = 128;
        Log.d("fun_3_1", "i1==i2:" + (i1 == i2));
        Log.d("fun_3_1", "i1==i3:" + (i1 == i3));
        Log.d("fun_3_1", "i1==i4:" + (i1 == i4));
        Log.d("fun_3_1", "i1==i5:" + (i1 == i5));
        Log.d("fun_3_1", "i4==i6:" + (i4 == i6));
        Log.d("fun_3_1", "i7==i8:" + (i7 == i8));

    }

    //软引用
    void fun_2_2() {
        SoftReference<String> softReference = new SoftReference<String>(new String("hello"));
        System.out.println(softReference.get());
        System.gc();
        System.out.println(softReference.get());
    }

    // 字符串常量池 demo
    void fun_1_1() {
        int a = 10;
        int b = 12;
        int c = 10;
        // JVM为了提升性能和减少内存开销，避免字符串的重复创建，维护了一块特殊的内存空间——字符串实例池。
        String d = "www";
        String e = "www";
        String f = new String("eee");
        String g = new String("eee");
        Log.d("fun_1_1", String.valueOf(a == b));
        Log.d("fun_1_1", String.valueOf(a == c));
        Log.d("fun_1_1", String.valueOf(d == e));
        Log.d("fun_1_1", String.valueOf(f == g));


    }
}

class A {
    int a;
    int c = 10;
    private String b;

    void fun1() {
    }

    private void fun2() {
        final int d = 10;
        class C {
            void c() {
                int ee = d + 10;
                int ff = a;
            }
        }
    }

    public class B {
        void xx() {
            a = 10;
            b = "sss";
            fun1();
            fun2();
        }
    }
}

abstract class AB {

}

class DD extends AB {

}

class DataHolder<T> {
    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
        printInfo(item);
    }

    T item;

    public <E> void printInfo(E e) {
        System.out.println(e);
    }

}

interface Generator<T> {
    T next();
}

class FruitGenerator<T> implements Generator<T> {

    @Override
    public T next() {
        return null;
    }
}

interface Hasf {
    void f();
}

class ManiPulator<T extends Hasf> {
    T obj;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}

/**
 * 解决泛型类型判断 问题 （instanceof 问题）
 *
 * @param <T>
 */
class GenericType<T> {
    Class<?> classType;

    public GenericType(Class<?> classType) {
        this.classType = classType;
    }

    public boolean isInstance(Object object) {
        return classType.isInstance(object);
    }
}

/**
 * 使用工厂方法创建实例，解决 泛型无法 new 的问题
 *
 * @param <T>
 */
interface Factory<T> {
    T create();
}

class Create<T> {
    T instance;

    public <F extends Factory<T>> T newInstance(F f) {
        instance = f.create();
        return instance;
    }
}

class IntegerFactory implements Factory<Integer> {

    @Override
    public Integer create() {
        Integer integer = new Integer(9);
        return integer;
    }
}

class Father {
    static void fun() {
        System.out.println("父类方法");
    }
}

class Son extends Father {
    static void fun() {
        System.out.println("子类方法");
    }
}