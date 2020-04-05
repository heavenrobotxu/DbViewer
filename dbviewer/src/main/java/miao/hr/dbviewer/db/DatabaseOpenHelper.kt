package miao.hr.dbviewer.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper(context: Context, dbName: String,
                         factory: SQLiteDatabase.CursorFactory,
                         version: Int) : SQLiteOpenHelper(context, dbName, factory, version) {

    override fun onCreate(p0: SQLiteDatabase?) {
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}