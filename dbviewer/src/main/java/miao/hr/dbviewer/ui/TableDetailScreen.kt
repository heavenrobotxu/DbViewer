package miao.hr.dbviewer.ui

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.Toast
import miao.hr.dbviewer.R
import miao.hr.dbviewer.db.SQLHandler
import miao.hr.dbviewer.ui.adapter.DatabaseListAdapter
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.properties.Delegates

internal class TableDetailScreen(private var activity: Activity?) : Screen {

    private val container: SparseArray<Parcelable> = SparseArray()
    private var title: String = ""
    override var contentView: View by Delegates.notNull()
    private var executor: ExecutorService? = Executors.newSingleThreadExecutor()

    override fun returnContentView(extra: Bundle?): View {
        contentView =
            LayoutInflater.from(activity).inflate(R.layout.screen_table_detail, null, false)
        initScreen(extra)
        return contentView
    }

    override fun initScreen(extra: Bundle?) {
        extra?.getString(ARGUMENT_DATABASE_NAME)?.let {
            executor?.submit {
                if (SQLHandler.openDatabaseByName(it)) {
                    contentView.post {
                        val tableList = SQLHandler.getTableStructure()
                        title = "数据库${if (SQLHandler.isEncrypted()) "已加密" else "未加密"}，" +
                                "共有${tableList.size}张表"
                        activity?.title = title
                        contentView.findViewById<ListView>(R.id.rv_table_list).adapter =
                            DatabaseListAdapter(tableList, false)
                    }
                } else {
                    contentView.post {
                        Toast.makeText(this.activity, "打开数据库失败，请检查数据库密码是否设置正确",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun restore() {
        activity?.title = title
        contentView.restoreHierarchyState(container)
    }

    override fun save() {
        contentView.saveHierarchyState(container)
    }

    override fun close() {
        SQLHandler.destroy()
        executor?.shutdownNow()
        executor = null
        activity = null
    }

    companion object {

        const val ARGUMENT_DATABASE_NAME = "database_name"

        fun start(activity: Activity, databaseName: String): Screen {
            return TableDetailScreen(activity).apply {
                returnContentView(Bundle().apply {
                    putString(
                        ARGUMENT_DATABASE_NAME,
                        databaseName
                    )
                })
            }
        }
    }
}
