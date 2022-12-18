package xyz.maoyanluo.datastagingarea.floatball.rv

import android.content.ClipData
import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import xyz.maoyanluo.datastagingarea.floatball.model.TextModel

class TextViewHolder(private val view: View): BaseViewHolder(view) {

    init {
        type = TYPE_TEXT
    }

    fun onBindData(data: TextModel) {
        if (view is RelativeLayout) {
            val textView = TextView(view.context)
            textView.isSingleLine = false
            textView.text = data.text
            textView.textSize = 20f
            textView.isAllCaps = false
            val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            params.addRule(RelativeLayout.CENTER_IN_PARENT)
            params.topMargin = 20
            params.bottomMargin = 20
            view.setBackgroundColor(Color.parseColor("#5066CCFF"))
            view.addView(textView, params)
            bindDragAndDrop(view, data.text)
        }
    }

    private fun bindDragAndDrop(view: View, data: String) {
        view.setOnLongClickListener {
//            val clipData = ClipData(data, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), ClipData.Item(data))
            val clipData = ClipData.newPlainText(data, data)
            val dragShadowBuilder = View.DragShadowBuilder(view)
            it.startDragAndDrop(clipData, dragShadowBuilder, null, View.DRAG_FLAG_GLOBAL)
            return@setOnLongClickListener true
        }
    }

}