package miao.hr.dbviewer.db

import android.content.Context

internal object SQLHandler {

    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    //返回当前app data中的所有数据库
    fun getDatabaseList() = context.databaseList().asList()

}