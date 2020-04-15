package com.example.androidaudition.reflect;

import android.util.Log;

public class B extends A {
    private double weight;
    public String gender;
    int age;


    public B() {
        Log.d("reflect_", "B 构造成功");
    }

    protected B(int age) {
        this.age = age;
        Log.d("reflect_", "B 构造成功:age=" + age);
    }

    B(String gender) {
        this.gender = gender;
        Log.d("reflect_", "B 构造成功:gender=" + gender);
    }

    private B(double weight) {
        this.weight = weight;
        Log.d("reflect_", "B 构造成功:weight=" + weight);
    }

    private B(double weight, String gender, int age) {
        this.weight = weight;
        this.gender = gender;
        this.age = age;
        Log.d("reflect_", "B 构造成功:weight,gender,age  = " + weight + " " + gender + " " + age);
    }


    @Override
    public void eat(int food) {
        Log.d("reflect_", "B eat " + food);
    }

    @Override
    public String drink(float water) {
        Log.d("reflect_", "B drink " + water);
        return "watter no beauty";
    }
}
