package xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.model

import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.vh.BaseViewHolder

class TextModel(var text: String = ""): BaseModel() {

    init {
        type = BaseViewHolder.TYPE_TEXT
    }

}