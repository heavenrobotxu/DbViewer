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
import miao.hr.dbviewer.ui.adapter.DatabaseListAdapter
import kotlin.properties.Delegates

internal class LaunchScreen(private var activity : Activity?) : Screen {

    private val container: SparseArray<Parcelable> = SparseArray()
    private var title : String by Delegates.notNull()
    override var contentView: View by Delegates.notNull()

    override fun returnContentView(extra: Bundle?): View {
        contentView = LayoutInflater.from(activity)
            .inflate(R.layout.screen_launch, null, false)
        initScreen(extra)
        return contentView
    }

    override fun initScreen(extra: Bundle?){
        val databaseList = SQLHandler.getDatabaseList()
        title = "应用共有${databaseList.size}个数据库文件"
        activity?.title = title
        contentView.findViewById<ListView>(R.id.rv_launch_db_list).
            adapter = DatabaseListAdapter(databaseList)
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

        fun start(activity: Activity): Screen {
            return LaunchScreen(activity).apply {
                returnContentView(null)
            }
        }
    }
}
