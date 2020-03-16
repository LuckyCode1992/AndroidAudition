package com.example.androidaudition.content_provider_demo

import android.os.AsyncTask
import android.widget.TextView


class MyAsyncTask(var text: TextView, var str: String) :
    AsyncTask<Int, Int, String>() {


    override fun doInBackground(vararg params: Int?): String {
        return ""
    }

    override fun onPreExecute() {
        super.onPreExecute()
        text.append(str)
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
    }

}