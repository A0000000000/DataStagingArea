package xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.vh

import android.content.ClipData
import android.content.ClipDescription
import android.view.View
import android.widget.ImageView
import xyz.maoyanluo.datastagingarea.R
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.model.ImageModel

class ImageViewHolder(private val view: View): BaseViewHolder(view) {

    init {
        type = TYPE_IMAGE
    }

    fun onBindData(imageModel: ImageModel) {
        view.findViewById<ImageView>(R.id.vh_image_iv)?.let { iv ->
            iv.setImageURI(imageModel.uri)
            iv.setOnLongClickListener {
                val clipDataItem = ClipData.Item(imageModel.uri)
                val clipData = ClipData(ClipDescription("image", arrayOf(imageModel.mimeType)), clipDataItem)
                val dragShadowBuilder = View.DragShadowBuilder(view)
                it.startDragAndDrop(clipData, dragShadowBuilder, null, View.DRAG_FLAG_GLOBAL or View.DRAG_FLAG_GLOBAL_URI_READ)
                return@setOnLongClickListener true
            }
        }
    }

}