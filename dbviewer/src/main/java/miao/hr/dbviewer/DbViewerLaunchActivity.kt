package miao.hr.dbviewer

import android.content.Context
import android.os.Bundle
import android.util.ArrayMap
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_db_viewer_launch.*
import miao.hr.dbviewer.sql.QUERY_TABLE_STRUCT

class DbViewerLaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db_viewer_launch)

        val dbName = databaseList()[0]

        var database = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null)
        val cursor = database.rawQuery(QUERY_TABLE_STRUCT, null)
        /*test.text = buildString {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val names = cursor.columnNames
                    appendln(cursor.getString(cursor.getColumnIndex("name")))
                }
                cursor.close()
            }
        }*/
    }
}
