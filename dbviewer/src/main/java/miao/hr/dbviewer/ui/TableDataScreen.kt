package miao.hr.dbviewer.ui

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import miao.hr.dbviewer.R
import miao.hr.dbviewer.db.SQLHandler
import miao.hr.dbviewer.ui.adapter.ColumnListAdapter
import kotlin.properties.Delegates


class TableDataScreen(private var activity: Activity?) : Screen {

    private val container: SparseArray<Parcelable> = SparseArray()
    private var title: String by Delegates.notNull()
    override var contentView: View by Delegates.notNull()

    override fun returnContentView(extra: Bundle?): View {
        contentView =
            LayoutInflater.from(activity).inflate(R.layout.screen_table_data, null, false)
        initScreen(extra)
        return contentView
    }

    override fun initScreen(extra: Bundle?) {
        extra?.getString(ARGUMENT_TABLE_NAME)?.let {
            val columnDetail = SQLHandler.getDataFromTable(it)
            title = "当前表共有${columnDetail.size}条数据"
            activity?.title = title
            contentView.findViewById<ListView>(R.id.rv_column_list).adapter =
                ColumnListAdapter(columnDetail)
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
        activity = null
    }

    companion object {

        const val ARGUMENT_TABLE_NAME = "table_name"

        fun start(activity: Activity, tableName: String): Screen {
            return TableDataScreen(activity).apply {
                returnContentView(Bundle().apply {
                    putString(ARGUMENT_TABLE_NAME, tableName)
                })
            }
        }
    }
}
