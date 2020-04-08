package miao.hr.dbviewer.ui.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.getSpans
import androidx.core.text.toSpannable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_column_list_bg.view.*
import kotlinx.android.synthetic.main.layout_item_dababase_list_bg.view.*
import kotlinx.android.synthetic.main.layout_item_dababase_list_bg.view.ll_item_db_list_bg
import miao.hr.dbviewer.ColumnData
import miao.hr.dbviewer.R
import miao.hr.dbviewer.ui.TableDataFragment
import miao.hr.dbviewer.ui.TableDetailFragment
import kotlin.properties.Delegates

internal class ColumnListAdapter(
    private val columnValueList: List<List<Pair<String, ColumnData>>>
) :
    RecyclerView.Adapter<ColumnListAdapter.ColumnListViewHolder>() {

    private val chosenArray = BooleanArray(columnValueList.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColumnListViewHolder {
        return ColumnListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.layout_item_column_list_bg,
                    parent, false
                )
        )
    }

    override fun getItemCount(): Int = columnValueList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ColumnListViewHolder, position: Int) {
        holder.itemView.tv_item_column_content.text = if (chosenArray[position])
            getDetailInfo(columnValueList[position]) else "$position"

        holder.itemView.ll_item_column_list_bg.setOnClickListener {
            chosenArray[position] = chosenArray[position].not()
            if (chosenArray[position]) {
                holder.itemView.tv_item_column_content.text =
                    getDetailInfo(columnValueList[position])
            } else {
                holder.itemView.tv_item_column_content.text = "$position"
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
            ssb.append( "(${getColumnType(pair.second.columnType)})",
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

    class ColumnListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}