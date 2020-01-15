package com.example.androidaudition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class DataStructureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_structure);

        /**
         *  1 选择排序和冒泡排序
         *      1.1 选择排序
         *          - 方法：
         *              假设有5个数，要求按照从高到低排序
         *
         *              第0轮，始终拿下标为0的数和后面的数依次比较，找到最大的数字，和下标为0的交换
         *              第1轮，始终拿下标为1的数和后面的数依次比较，找到第二大的数字，和下标为1的交换
         *              第2轮，始终拿下标为2的数和后面的数依次比较，找到第三大的数字，和下标为2的交换
         *              第3轮，始终拿下标为3的数和后面的数依次比较，找到第四大的数字，和下标3的交换
         *              ---
         *              现在 有n个数
         *              就需要比较 n-1轮
         *              第i轮，始终拿下标为i的数和后面（i到n-1）的数比较。fun_1_1
         *      1.2 冒泡排序
         *          - 方法
         *              假设5个数，要求按照从高到低排序
         *
         *              第0轮，比较4次，比较 01，12，23，34 得到最小的数
         *              第1轮，比较3次，比较 01，23，23  得到第二小的数
         *              ----
         *              现在 n个数
         *              需要比较 n-1轮 fun_1_2
         *      1.3 排序的优缺点
         *          -  冒泡排序优缺点：优点:比较简单，空间复杂度较低，是稳定的；
         *                           缺点:时间复杂度太高，效率慢；
         *          -  选择排序优缺点：优点：一轮比较只需要换一次位置；
         *                          缺点：效率慢，不稳定（举个例子5，8，5，2，9
         *                          我们知道第一遍选择第一个元素5会和2交换，那么原序列中2个5的相对位置前后顺序就破坏了）。
         */

        findViewById(R.id.btn_fun_1_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun_1_1();
            }
        });
        findViewById(R.id.btn_fun_1_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun_1_2();
            }
        });
    }

    void fun_1_1() {
        int n[] = {12, 33, 44, 22, 55, 11, 77, 34, 98, 47, 1, 6};
        //外层循环控制轮数，每循环依次，完成1轮比较
        int len = n.length;
        for (int i = 0; i < len - 1; i++) {
            //每一轮 都拿下标为i的元素和后面元素比较
            // n[i] i+1 len-1
            //将后面的所有元素遍历出来
            for (int j = i + 1; j < len; j++) {
                if (n[i] < n[j]) {
                    int temp = n[i];
                    n[i] = n[j];
                    n[j] = temp;
                }
            }
        }
        for (int a : n) {
            System.out.println(a);
        }
    }

    void fun_1_2() {
        int n[] = {12, 33, 44, 22, 55, 11, 77, 34, 98, 47, 1, 6};
        //外层循环控制轮数，每循环依次，完成1轮比较
        int len = n.length;
        for (int i = 0; i < len - 1; i++) {
            //每一轮比较次数
            int m = len - 1 - i;
            for (int j = 0; j < m; j++) {
                //比较相邻两个数 把小的放到右边
                if (n[j] < n[j + 1]) {
                    int temp = n[j];
                    n[j] = n[j + 1];
                    n[j + 1] = temp;
                }
            }
        }
        for (int i : n) {
            System.out.println(i);
        }
    }
}
