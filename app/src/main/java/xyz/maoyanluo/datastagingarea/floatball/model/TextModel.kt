package xyz.maoyanluo.datastagingarea.floatball.model

import xyz.maoyanluo.datastagingarea.floatball.rv.BaseViewHolder

class TextModel(var text: String = ""): BaseModel() {

    init {
        type = BaseViewHolder.TYPE_TEXT
    }

}