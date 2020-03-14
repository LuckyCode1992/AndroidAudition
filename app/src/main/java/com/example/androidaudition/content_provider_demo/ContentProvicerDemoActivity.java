package com.example.androidaudition.content_provider_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidaudition.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class ContentProvicerDemoActivity extends AppCompatActivity {
    RxPermissions rxPermissions;
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provicer_demo);
        rxPermissions = new RxPermissions(this);
        init();
    }

    @SuppressLint("CheckResult")
    private void init() {
        rxPermissions.request(Manifest.permission.READ_CONTACTS).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    getContacts();
                }
            }
        });
        rxPermissions.request(Manifest.permission.READ_SMS).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    getMsgs();
                }
            }
        });
        tvContent = findViewById(R.id.tv_content);
        findViewById(R.id.btn_intent_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ContentProvicerDemoActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_change_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //添加联系人，因为原方法抛出了异常，所以调用的时候需要try-catch语句
                    rxPermissions.request(Manifest.permission.WRITE_CONTACTS).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean){
                                AddContact();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void AddContact() throws OperationApplicationException, RemoteException {

        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");
        ContentResolver resolver = getContentResolver();
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        ContentProviderOperation op1 = ContentProviderOperation.newInsert(uri)
                .withValue("account_name",null)
                .build();
        operations.add(op1);
        ContentProviderOperation op2 = ContentProviderOperation.newInsert(dataUri)
                .withValueBackReference("raw_contact_id",0)
                .withValue("mimetype","vnd.android.cursor.item/name")
                .withValue("data2","Coder_pig")
                .build();
        operations.add(op2);
        ContentProviderOperation op3 = ContentProviderOperation.newInsert(dataUri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                .withValue("data1", "13798988888")
                .withValue("data2", "2")
                .build();
        operations.add(op3);
        ContentProviderOperation op4 = ContentProviderOperation.newInsert(dataUri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/email_v2")
                .withValue("data1", "1825273284@qq.com")
                .withValue("data2", "2")
                .build();
        operations.add(op4);
        //将上述内容添加到手机联系人中~
        resolver.applyBatch("com.android.contacts", operations);
        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();

    }

    private void getMsgs() {
        //短信的uri
        Uri uri = Uri.parse("content://sms/");
        ContentResolver resolver = getContentResolver();
        //获取的是哪些列的信息
        Cursor cursor = resolver.query(uri, new String[]{"address","date","type","body"}, null, null, null);
        while(cursor.moveToNext()){
            String address = cursor.getString(0);
            String date = cursor.getString(1);
            String type = cursor.getString(2);
            String body = cursor.getString(3);
            Log.d("content_provider:","地址:" + address);
            Log.d("content_provider:","时间:" + date);
            Log.d("content_provider:","类型:" + type);
            Log.d("content_provider:","内容:" + body);
            Log.d("content_provider:","结束:===========");
        }
        cursor.close();
    }

    private void getContacts() {
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = resolver.query(uri,null,null,null,null);
        while (cursor.moveToNext()){
            String cName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String cNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.d("content_provider:","姓名："+cName);
            Log.d("content_provider:","号码："+cNum);
            Log.d("content_provider:","++++++++++++++++++++++");
        }
        cursor.close();
    }
}
