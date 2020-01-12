package com.example.androidaudition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

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


    void fun_2_2() {
        SoftReference<String> softReference = new SoftReference<String>(new String("hello"));
        System.out.println(softReference.get());
        System.gc();
        System.out.println(softReference.get());
    }

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
