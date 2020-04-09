package miao.hr.dbviewer.ui.adapter

import android.database.Cursor
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_item_column_list_bg.view.*
import miao.hr.dbviewer.ColumnData
import miao.hr.dbviewer.R

internal class ColumnListAdapter(
    private val columnValueList: List<List<Pair<String, ColumnData>>>
) : BaseAdapter() {

    private val chosenArray = BooleanArray(columnValueList.size)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return if (convertView == null) {
            val view = LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.layout_item_column_list_bg,
                    parent, false
                )
            initView(view, position)
            view
        } else {
            initView(convertView, position)
            convertView
        }
    }

    override fun getItem(position: Int): Any = columnValueList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = columnValueList.size

    private fun initView(contentView: View, position: Int) {
        contentView.findViewById<TextView>(R.id.tv_item_column_content).text =
            if (chosenArray[position])
                getDetailInfo(columnValueList[position]) else "$position"

        contentView.findViewById<LinearLayout>(R.id.ll_item_column_list_bg).setOnClickListener {
            chosenArray[position] = chosenArray[position].not()
            if (chosenArray[position]) {
                contentView.tv_item_column_content.text =
                    getDetailInfo(columnValueList[position])
            } else {
                contentView.tv_item_column_content.text = "$position"
            }
        }
    }

    private fun getDetailInfo(columnInfo: List<Pair<String, ColumnData>>): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        for ((index, pair) in columnInfo.withIndex()) {
            ssb.append(
                pair.first, ForegroundColorSpan(Color.parseColor("#c28ffe")),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ssb.append(
                "(${getColumnType(pair.second.columnType)})",
                ForegroundColorSpan(Color.parseColor("#00ddc5")),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ssb.append(" : ")
            if (index != columnInfo.size - 1) {
                ssb.appendln(pair.second.value.toString())
            } else {
                ssb.append(pair.second.value.toString())
            }
        }
        return ssb
    }

    private fun getColumnType(columnType: Int): String {
        return when (columnType) {
            Cursor.FIELD_TYPE_NULL -> "NULL"
            Cursor.FIELD_TYPE_INTEGER -> "INTEGER"
            Cursor.FIELD_TYPE_FLOAT -> "REAL"
            Cursor.FIELD_TYPE_STRING -> "TEXT"
            Cursor.FIELD_TYPE_BLOB -> "BLOB"
            else -> "UNKNOWN"
        }
    }
}