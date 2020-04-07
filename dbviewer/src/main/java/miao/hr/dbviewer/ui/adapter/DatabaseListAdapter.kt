package miao.hr.dbviewer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_dababase_list_bg.view.*
import miao.hr.dbviewer.R

internal class DatabaseListAdapter(private val dataBaseList: List<String>) :
    RecyclerView.Adapter<DatabaseListAdapter.DatabaseListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatabaseListViewHolder {
        return DatabaseListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.layout_item_dababase_list_bg,
                    parent, false
                )
        )
    }

    override fun getItemCount(): Int = dataBaseList.size

    override fun onBindViewHolder(holder: DatabaseListViewHolder, position: Int) {
        holder.itemView.tv_item_db_list_db_name.text = dataBaseList[position]
    }

    class DatabaseListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}