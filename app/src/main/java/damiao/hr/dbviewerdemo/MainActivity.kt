package damiao.hr.dbviewerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.widget.Toast
import androidx.core.util.DebugUtils
import damiao.hr.dbviewerdemo.database.DaoMaster
import damiao.hr.dbviewerdemo.database.DaoSession
import kotlinx.android.synthetic.main.activity_main.*
import leakcanary.AppWatcher
import leakcanary.LeakCanary
import org.intellij.lang.annotations.Language

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_hw.setOnClickListener {
            (application as App).dbSession.catDao.insertInTx(
                Cat().apply {
                    name = "damiao"
                    age = 1
                },
                Cat().apply {
                    name = "xiaomiao"
                    age = 2
                })
        }

        tv_show.setOnClickListener {
            val catList = (application as App).dbSession.catDao.queryBuilder().list()
            Toast.makeText(
                this, "共有${catList.size}只猫",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
