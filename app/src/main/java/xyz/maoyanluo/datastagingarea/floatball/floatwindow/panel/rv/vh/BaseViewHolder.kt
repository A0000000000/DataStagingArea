package xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.vh

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(view: View): RecyclerView.ViewHolder(view) {

    companion object {

        const val TYPE_UNKNOWN = -1
        const val TYPE_TEXT = 0
        const val TYPE_IMAGE = 1

    }

    var type = TYPE_UNKNOWN

}