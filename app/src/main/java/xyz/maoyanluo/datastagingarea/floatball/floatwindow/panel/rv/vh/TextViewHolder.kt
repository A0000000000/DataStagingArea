package xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.vh

import android.content.ClipData
import android.view.View
import android.widget.TextView
import xyz.maoyanluo.datastagingarea.R
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.model.TextModel

class TextViewHolder(private val view: View): BaseViewHolder(view) {

    init {
        type = TYPE_TEXT
    }

    fun onBindData(data: TextModel) {
        view.findViewById<TextView>(R.id.vh_text_tv).text = data.text
        view.setOnLongClickListener {
            val clipData = ClipData.newPlainText(data.text, data.text)
            val dragShadowBuilder = View.DragShadowBuilder(view)
            it.startDragAndDrop(clipData, dragShadowBuilder, null, View.DRAG_FLAG_GLOBAL)
            return@setOnLongClickListener true
        }
    }

}