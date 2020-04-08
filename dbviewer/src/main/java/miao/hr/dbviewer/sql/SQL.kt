package miao.hr.dbviewer.sql

import org.intellij.lang.annotations.Language

//查询当前数据库中所有表的表名称信息
@Language(value = "RoomSql")
internal const val QUERY_TABLE_STRUCT = """
    SELECT name 
    FROM sqlite_master 
    WHERE type='table'"""

internal const val SYSTEM_TABLE_METADATA = "android_metadata"
internal const val SYSTEM_TABLE_SQLITE_SEQUENCE = "sqlite_sequence"

//返回查询制定表名的SQL语句
internal fun createQueryTableSQL(tableName: String) : String{
    return """SELECT * FROM $tableName"""
}