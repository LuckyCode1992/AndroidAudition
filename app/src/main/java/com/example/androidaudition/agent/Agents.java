package com.example.androidaudition.agent;

import android.util.Log;

/**
 * 代理类：用来代理和封装真实主题
 * 再次，给房主找个中介(Agents)，作为房主出售房屋的代理
 */
public class Agents implements Sales{
    private Owner owner;
    public Agents() {
    }

    @Override
    public void sell() {
        Log.d("agent_","中介检查各种资料");
        getOwner().sell();
        Log.d("agent_","中介收提成");
    }
    private Owner getOwner() {
        if (owner==null) {
            owner=new Owner();
        }
        return owner;
    }
}
