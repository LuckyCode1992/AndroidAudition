package com.example.androidaudition.content_provider_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.example.androidaudition.R
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {


    var dbOpenHelper: DBOpenHelper? = null
    var name: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        btn_query.setOnClickListener {
            querry()
        }
    }

    private fun querry() {
        dbOpenHelper = DBOpenHelper(this, "test.db", null, 1)
        val sqLiteDatabase = dbOpenHelper!!.readableDatabase

        val cursor = sqLiteDatabase.query(
            "test",
            arrayOf("name"), "name=?", arrayOf("测试"), null, null, null
        )
        val cursor2 = sqLiteDatabase.query(
            "test", arrayOf("name"), null, null, null, null, null
        )
        while (cursor2.moveToNext()) {
            name = cursor2.getString(cursor2.getColumnIndex("name"))
            //输出查询结果
            Log.d("content_provider", "查询到的数据是:\nname: $name")
            if (!TextUtils.isEmpty(name)){
                MyAsyncTask(tv_show,name!!).execute()
            }
        }
        //关闭数据库
        sqLiteDatabase.close()
    }
}
