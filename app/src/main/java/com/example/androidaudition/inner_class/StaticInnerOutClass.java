package com.example.androidaudition.inner_class;

import android.util.Log;

public class StaticInnerOutClass {
    private String nor = "nor";
    private static String sta = "static";

    public class InnerNor {
        // static int a = 0;报错 非静态 内部类，不能有静态成员
        int a = 0;

        public void nor() {
            Log.d("inner_", nor);
            Log.d("inner_", sta);

        }

    }

    public static class InnerStatic {
        static String  b = "12";

        public void sta() {
//            Log.d("inner_", nor);
            Log.d("inner_", sta);
        }

        public static void sta2() {
            Log.d("inner_", sta);
            Log.d("inner_",  b);
//            Log.d("inner_",  nor); 静态不能 访问非静态
        }
    }
}
