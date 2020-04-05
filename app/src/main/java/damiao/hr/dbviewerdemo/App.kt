package damiao.hr.dbviewerdemo

import android.app.Application
import damiao.hr.dbviewerdemo.database.DaoMaster
import damiao.hr.dbviewerdemo.database.DaoSession
import net.sqlcipher.database.SQLiteDatabase

class App : Application() {

    lateinit var dbSession: DaoSession

    override fun onCreate() {
        super.onCreate()

        val daoMasterHelper = DaoMaster.DevOpenHelper(this, "miao_db")
        dbSession = DaoMaster(daoMasterHelper.writableDatabase).newSession()
    }
}