package com.example.androidaudition.reflect;

import android.util.Log;

public class A {
    public String name;
    protected float height;


    public void eat(int food){
        Log.d("reflect_","A eat "+food);
    }
    public String drink(float water){
        Log.d("reflect_","A drink "+water);
        return "water is beauty";
    }

}
