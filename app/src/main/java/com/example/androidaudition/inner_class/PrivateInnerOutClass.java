package com.example.androidaudition.inner_class;

import android.util.Log;

public class PrivateInnerOutClass {

    private class Inner implements InterfaceA {

        @Override
        public void funA() {
            Log.d("inner_", "测试funA");
        }
    }

    public InterfaceA interfaceATest() {
        return new Inner();
    }
}

