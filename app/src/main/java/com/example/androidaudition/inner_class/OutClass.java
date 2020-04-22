package com.example.androidaudition.inner_class;

import android.util.Log;

public class OutClass {
    private String aa = "1212";

    public class InnerClass {
        {
            Log.d("inner_", aa);
        }

    }

}
