package miao.hr.dbviewer.sql

import org.intellij.lang.annotations.Language

//查询当前数据库中所有表的表名称信息
@Language(value = "RoomSql")
const val QUERY_TABLE_STRUCT = """
    SELECT name 
    FROM sqlite_master 
    WHERE type='table'"""

//返回查询制定表名的SQL语句
fun createQueryTableSQL(tableName: String) : String{
    return """SELECT * FROM $tableName"""
}