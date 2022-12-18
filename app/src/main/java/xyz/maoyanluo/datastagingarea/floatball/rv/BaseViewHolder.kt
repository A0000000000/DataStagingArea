package xyz.maoyanluo.datastagingarea.floatball.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    companion object {

        const val TYPE_UNKNOWN = -1
        const val TYPE_TEXT = 0
        const val TYPE_IMAGE = 1

    }

    var type = TYPE_UNKNOWN

}