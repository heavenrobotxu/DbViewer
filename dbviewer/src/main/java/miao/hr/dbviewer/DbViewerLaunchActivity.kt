package miao.hr.dbviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import miao.hr.dbviewer.db.SQLHandler

internal class DbViewerLaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db_viewer_launch)

        SQLHandler.init(applicationContext)
    }
}
