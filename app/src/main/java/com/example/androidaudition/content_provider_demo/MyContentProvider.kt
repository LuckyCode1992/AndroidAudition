package com.example.androidaudition.content_provider_demo

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.content.UriMatcher
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.ContentUris
import android.database.sqlite.SQLiteDatabase
import android.icu.lang.UCharacter.GraphemeClusterBreak.T






class MyContentProvider : ContentProvider() {


    var dbOpenHelper: DBOpenHelper? = null


    companion object {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        init {
            matcher.addURI("luckyContentProvider","test",1)
            matcher.match(Uri.EMPTY)
        }


    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        when (matcher.match(uri)) {
            //证明uri是否匹配成功
            1 -> {
                val db = dbOpenHelper?.getReadableDatabase()
                val rowId = db!!.insert("test", null, values)
                if (rowId > 0) {
                    // 在前面已有的uri后面追加id
                    val nameUri = ContentUris.withAppendedId(uri, rowId)
                    //通知内容提供器，内容发生改变了
                    context!!.contentResolver.notifyChange(nameUri, null)
                    return nameUri
                }
            }
        }

        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null

    }

    override fun onCreate(): Boolean {
        //实例化对象
        dbOpenHelper = DBOpenHelper(this.context, "test.db", null, 1)
        return true
    }


    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

}