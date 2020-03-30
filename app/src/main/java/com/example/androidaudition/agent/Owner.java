package com.example.androidaudition.agent;

import android.util.Log;

/**
 * 真实主题 具体处理业务
 * 其次，房主做为整个事件的主角，自然而然的就成了真实主题，也是代理的委托者
 */
public class Owner implements Sales{

    @Override
    public void sell() {
        Log.d("agent_","房主卖房子");
    }
}
