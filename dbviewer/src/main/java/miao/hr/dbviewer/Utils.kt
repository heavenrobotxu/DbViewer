package miao.hr.dbviewer

import android.content.Context
import android.widget.Toast
import java.util.*

internal fun Context.toast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

internal fun <T> buildList(receiver: LinkedList<T>.() -> Unit) : List<T>{
    val list = LinkedList<T>()
    receiver(list)
    return list
}