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

        btn_java_basics.setOnClickListener {
            intent.setClass(this,JavaBasicsActivity::class.java)
            startActivity(intent)
        }
    }
}
