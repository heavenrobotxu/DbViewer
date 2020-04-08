package miao.hr.dbviewer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_table_data.*
import kotlinx.android.synthetic.main.fragment_table_data.view.*
import kotlinx.android.synthetic.main.fragment_table_detail.*
import miao.hr.dbviewer.R
import miao.hr.dbviewer.db.SQLHandler
import miao.hr.dbviewer.ui.adapter.ColumnListAdapter


class TableDataFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contentView = inflater.inflate(R.layout.fragment_table_data, container, false)
        initView(contentView)
        return contentView
    }

    private fun initView(view: View) {
        arguments?.getString(ARGUMENT_TABLE_NAME)?.let {
            val columnDetail = SQLHandler.getDataFromTable(it)
            activity?.title = "${it}共有${columnDetail.size}条数据"
            view.rv_column_list.adapter = ColumnListAdapter(columnDetail)
        }
    }

    companion object {

        const val ARGUMENT_TABLE_NAME = "table_name"

        fun start(view: View, tableName: String) {
            Navigation.findNavController(view)
                .navigate(R.id.action_tableDetailFragment_to_tableDataFragment,
                    Bundle().apply
                    { putString(ARGUMENT_TABLE_NAME, tableName) })
        }
    }
}
