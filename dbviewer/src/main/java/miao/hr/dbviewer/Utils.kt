package miao.hr.dbviewer

import android.content.Context
import android.widget.Toast

internal fun Context.toast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}