package miao.hr.dbviewer.db

import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.ArrayMap
import android.util.Log
import miao.hr.dbviewer.ColumnData
import miao.hr.dbviewer.buildList
import miao.hr.dbviewer.sql.QUERY_TABLE_STRUCT
import miao.hr.dbviewer.sql.SYSTEM_TABLE_METADATA
import miao.hr.dbviewer.sql.SYSTEM_TABLE_SQLITE_SEQUENCE
import miao.hr.dbviewer.sql.createQueryTableSQL
import java.util.concurrent.Executors

internal object SQLHandler {

    private const val TAG = "SQLHandler"

    private lateinit var context: Context
    private var isEncrypted = false
    private var currentDatabase: SQLiteDatabase? = null
    private var currentEncryptedDatabase: net.sqlcipher.database.SQLiteDatabase? = null

    fun init(context: Context) {
        this.context = context
    }

    //return all app databases in data dir
    fun getDatabaseList() = context.databaseList().asList().filter { !it.endsWith("journal") }

    //open database by given name
    fun openDatabaseByName(dbName: String) : Boolean{
        val pwd: String? = try {
            context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            ).metaData.getString("DB_PWD")
        } catch (e: Exception) {
            null
        }
        return if (pwd.isNullOrEmpty()) {
            //没有找到该数据库的密码，默认使用普通方式打开数据库
            openNoEncryptedDatabaseByName(dbName)
        } else {
            //找到该数据库的密码，使用加密方式打开数据库
            openEncryptedDatabaseByName(dbName, pwd.drop(5))
                    || openNoEncryptedDatabaseByName(dbName)
        }
    }

    private fun openNoEncryptedDatabaseByName(dbName: String): Boolean {
        return try {
            currentDatabase = SQLiteDatabase.openDatabase(
                context.getDatabasePath(dbName).path,
                null,
                SQLiteDatabase.OPEN_READONLY
            )
            isEncrypted = false
            true
        } catch (e: Exception) {
            Log.e(TAG, "can not open this database")
            false
        }
    }

    //open database by given name
    private fun openEncryptedDatabaseByName(dbName: String, pwd: String): Boolean {
        return try {
            net.sqlcipher.database.SQLiteDatabase.loadLibs(context)
            currentEncryptedDatabase = net.sqlcipher.database.SQLiteDatabase.openDatabase(
                context.getDatabasePath(dbName).path,
                pwd,
                null,
                SQLiteDatabase.OPEN_READONLY
            )
            isEncrypted = true
            true
        } catch (e: Exception) {
            Log.e(TAG, "can not open this database")
            false
        }
    }

    //return all tables by given database
    fun getTableStructure(): List<String> {
        return if (!isEncrypted) getNoEncryptedTableStructure() else getEncryptedTableStructure()
    }

    private fun getNoEncryptedTableStructure(): List<String> {
        val cursor = currentDatabase!!.rawQuery(QUERY_TABLE_STRUCT, null)
        return if (cursor != null) {
            buildList<String> {
                while (cursor.moveToNext()) {
                    add(cursor.getString(cursor.getColumnIndex("name")))
                }
                cursor.close()
            }.filter { it != SYSTEM_TABLE_METADATA && it != SYSTEM_TABLE_SQLITE_SEQUENCE }
        } else emptyList()
    }

    private fun getEncryptedTableStructure(): List<String> {
        val cursor = currentEncryptedDatabase!!.rawQuery(QUERY_TABLE_STRUCT, null)
        return if (cursor != null) {
            buildList<String> {
                while (cursor.moveToNext()) {
                    add(cursor.getString(cursor.getColumnIndex("name")))
                }
                cursor.close()
            }.filter { it != SYSTEM_TABLE_METADATA && it != SYSTEM_TABLE_SQLITE_SEQUENCE }
        } else emptyList()
    }

    //get all column data by given table name
    fun getDataFromTable(tableName: String): List<List<Pair<String, ColumnData>>> {
        return if (!isEncrypted) getNoEncryptedDataFromTable(tableName)
        else getEncryptedDataFromTable(tableName)
    }

    private fun getNoEncryptedDataFromTable(tableName: String): List<List<Pair<String, ColumnData>>> {
        val cursor = currentDatabase!!.rawQuery(createQueryTableSQL(tableName), null)
        if (cursor != null) {
            return buildList {
                while (cursor.moveToNext()) {
                    val columnCount = cursor.columnCount
                    add(buildList {
                        for (i in 0 until columnCount) {
                            val columnName = cursor.getColumnName(i)
                            val columnType = cursor.getType(i)
                            val columnValue: Any = when (columnType) {
                                Cursor.FIELD_TYPE_NULL -> {
                                    "null"
                                }
                                Cursor.FIELD_TYPE_INTEGER -> {
                                    cursor.getLong(i)
                                }
                                Cursor.FIELD_TYPE_FLOAT -> {
                                    cursor.getDouble(i)
                                }
                                Cursor.FIELD_TYPE_STRING -> {
                                    cursor.getString(i)
                                }
                                Cursor.FIELD_TYPE_BLOB -> {
                                    cursor.getBlob(i)
                                }
                                else -> "unknown"
                            }
                            add(columnName to ColumnData(columnValue, columnType))
                        }
                    })
                }
                cursor.close()
            }
        } else {
            return emptyList()
        }
    }

    private fun getEncryptedDataFromTable(tableName: String): List<List<Pair<String, ColumnData>>> {
        val cursor = currentEncryptedDatabase!!.rawQuery(createQueryTableSQL(tableName), null)
        if (cursor != null) {
            return buildList {
                while (cursor.moveToNext()) {
                    val columnCount = cursor.columnCount
                    add(buildList {
                        for (i in 0 until columnCount) {
                            val columnName = cursor.getColumnName(i)
                            val columnType = cursor.getType(i)
                            val columnValue: Any = when (columnType) {
                                Cursor.FIELD_TYPE_NULL -> {
                                    "null"
                                }
                                Cursor.FIELD_TYPE_INTEGER -> {
                                    cursor.getLong(i)
                                }
                                Cursor.FIELD_TYPE_FLOAT -> {
                                    cursor.getDouble(i)
                                }
                                Cursor.FIELD_TYPE_STRING -> {
                                    cursor.getString(i)
                                }
                                Cursor.FIELD_TYPE_BLOB -> {
                                    cursor.getBlob(i)
                                }
                                else -> "unknown"
                            }
                            add(columnName to ColumnData(columnValue, columnType))
                        }
                    })
                }
                cursor.close()
            }
        } else {
            return emptyList()
        }
    }

    fun isEncrypted() = isEncrypted

    fun destroy() {
        currentDatabase?.close()
        currentDatabase = null
        currentEncryptedDatabase?.close()
        currentEncryptedDatabase = null
        isEncrypted = false
    }
}