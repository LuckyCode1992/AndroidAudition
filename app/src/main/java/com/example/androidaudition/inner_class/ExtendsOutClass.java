package com.example.androidaudition.inner_class;

public class ExtendsOutClass {

    private class InnerA extends A {

    }

    private class InnerB extends B {

    }

    public String getName() {
        return new InnerA().getName();
    }

    public int getAge() {
        return new InnerB().getAge();
    }
}
