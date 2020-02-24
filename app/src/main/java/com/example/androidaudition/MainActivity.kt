package com.example.androidaudition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent()

        btn_1.setOnClickListener {
            intent.setClass(this,CommonInterviewActivity::class.java)
            startActivity(intent)
        }
        btn_java_basics.setOnClickListener {
            intent.setClass(this,JavaBasicsActivity::class.java)
            startActivity(intent)
        }
        btn_android_basics.setOnClickListener {
            intent.setClass(this,AndroidBasicActivity::class.java)
            startActivity(intent)
        }
        btn_data_structure.setOnClickListener {
            intent.setClass(this,DataStructureActivity::class.java)
            startActivity(intent)
        }
        btn_threads.setOnClickListener {
            intent.setClass(this,ThreadsActivity::class.java)
            startActivity(intent)
        }

    }
}
