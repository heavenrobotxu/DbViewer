package miao.hr.dbviewer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import miao.hr.dbviewer.DbViewerLaunchActivity
import miao.hr.dbviewer.R
import miao.hr.dbviewer.ui.TableDataScreen
import miao.hr.dbviewer.ui.TableDetailScreen

internal class DatabaseListAdapter(
    private val dataBaseList: List<String>,
    private val isDbUi: Boolean = true
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return if (convertView == null) {
            val view = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.layout_item_dababase_list_bg,
                    parent, false
                )
            initView(view, position)
            view
        } else {
            initView(convertView, position)
            convertView
        }
    }

    override fun getItem(position: Int): Any = dataBaseList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = dataBaseList.size

    private fun initView(contentView: View, position: Int) {
        val tv = contentView.findViewById<TextView>(R.id.tv_item_db_list_db_name)
        tv.text = dataBaseList[position]
        if (!isDbUi) tv.textSize = 18f
        contentView.findViewById<LinearLayout>(R.id.ll_item_db_list_bg).setOnClickListener {
            val act = (contentView.context as DbViewerLaunchActivity)
            if (isDbUi) {
                act.addScreen(TableDetailScreen.start(act, dataBaseList[position]))
            } else {
                act.addScreen(TableDataScreen.start(act, dataBaseList[position]))
            }
        }
    }
}