package miao.hr.dbviewer.ui

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_table_detail.*
import kotlinx.android.synthetic.main.fragment_table_detail.view.*
import miao.hr.dbviewer.R
import miao.hr.dbviewer.db.SQLHandler
import miao.hr.dbviewer.sql.QUERY_TABLE_STRUCT
import miao.hr.dbviewer.ui.adapter.DatabaseListAdapter

internal class TableDetailFragment : Fragment() {

    private var sqlDb: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contentView = inflater.inflate(R.layout.fragment_table_detail, container, false)
        initView(contentView)
        return contentView
    }

    private fun initView(view: View) {
        arguments?.getString(ARGUMENT_DATABASE_NAME)?.let {
            sqlDb = SQLHandler.openDatabaseByName(it)
            if (sqlDb != null) {
                val tableList = SQLHandler.getTableStructure(sqlDb!!)
                activity?.title = "${it}未加密，共有${tableList.size}张表"
                view.rv_table_list.adapter = DatabaseListAdapter(tableList, false)
            }
        }
    }

    companion object {

        const val ARGUMENT_DATABASE_NAME = "database_name"

        fun start(view: View, databaseName: String) {
            Navigation.findNavController(view)
                .navigate(R.id.action_launchFragment_to_tableDetailFragment,
                    Bundle().apply { putString(ARGUMENT_DATABASE_NAME, databaseName) })
        }
    }
}
