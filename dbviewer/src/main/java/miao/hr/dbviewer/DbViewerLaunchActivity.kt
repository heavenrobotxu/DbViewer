package miao.hr.dbviewer

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import miao.hr.dbviewer.db.SQLHandler
import miao.hr.dbviewer.ui.LaunchScreen
import miao.hr.dbviewer.ui.Screen
import java.util.*

internal class DbViewerLaunchActivity : Activity() {

    private val llContainer by lazy {
        findViewById<LinearLayout>(R.id.ll_db_launch_container)
    }
    private val screenStack = Stack<Screen>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db_viewer_launch)

        SQLHandler.init(applicationContext)

        addScreen(LaunchScreen.start(this))
    }

    fun addScreen(screen: Screen) {
        if (!screenStack.isEmpty()) {
            val topScreen = screenStack.peek()
            topScreen.save()
            exit(topScreen.contentView)
        }
        screenStack.add(screen)
        enter(screen.contentView)
    }

    override fun onBackPressed() {
        if (screenStack.size > 1) {
            val top = screenStack.pop()
            top.close()
            exit(top.contentView)
            val current = screenStack.peek()
            current.restore()
            enter(current.contentView)
            return
        }
        super.onBackPressed()
    }

    private fun enter(view: View) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_screen_enter))
        llContainer.addView(view)
    }

    private fun exit(view: View) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_screen_exit))
        llContainer.removeView(view)
    }
}
