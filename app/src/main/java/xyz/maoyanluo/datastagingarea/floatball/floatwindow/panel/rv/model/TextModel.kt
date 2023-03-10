package xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.model

import android.content.ClipData
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.vh.BaseViewHolder

class TextModel(data: ClipData.Item): BaseModel() {

    init {
        type = BaseViewHolder.TYPE_TEXT
    }

    val text = data.text.toString()

}