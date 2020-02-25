package com.example.androidaudition.servicedemo;

import android.os.RemoteException;


import com.example.androidaudition.IMyAidlInterface;

public class IMyAidlInterfaceImp extends IMyAidlInterface.Stub {


    @Override
    public int getProcessId() throws RemoteException {
        return android.os.Process.myPid();
    }
}
