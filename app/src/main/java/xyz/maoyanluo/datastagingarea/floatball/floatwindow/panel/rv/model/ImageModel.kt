package xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.model

import android.content.ClipData
import android.net.Uri
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.vh.BaseViewHolder

class ImageModel(val mimeType: String, data: ClipData.Item): BaseModel() {

    val uri: Uri = data.uri

    init {
        type = BaseViewHolder.TYPE_IMAGE
    }

}