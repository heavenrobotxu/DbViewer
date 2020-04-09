package miao.hr.dbviewer.ui

import android.os.Bundle
import android.view.View

interface Screen {

    var contentView: View

    fun returnContentView(extra: Bundle?): View

    fun initScreen(extra: Bundle?)

    fun restore()

    fun save()

    fun close()
}