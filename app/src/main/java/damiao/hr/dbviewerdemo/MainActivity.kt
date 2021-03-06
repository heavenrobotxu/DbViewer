package damiao.hr.dbviewerdemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

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

            (application as App).dbSession.badCatDao.insertInTx(
                BadCat().apply {
                   bodyHeight = 3.8F
                    bodyWidth = 3.6
                    weight = 13
                    tone = byteArrayOf(1,2,3,4,5)
                    nickName = "Tom"
                },
                BadCat().apply {
                    bodyHeight = 10F
                    bodyWidth = 3.6999
                    weight = 10
                    tone = byteArrayOf(1,2)
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
