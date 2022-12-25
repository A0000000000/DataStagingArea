package xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import xyz.maoyanluo.datastagingarea.R
import xyz.maoyanluo.datastagingarea.floatball.ds.BaseDataSource
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.model.ImageModel
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.model.TextModel
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.vh.BaseViewHolder
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.vh.ImageViewHolder
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.vh.TextViewHolder

class FloatBallAdapter(private val ds: BaseDataSource?, context: Context): RecyclerView.Adapter<BaseViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            BaseViewHolder.TYPE_UNKNOWN -> {
                return BaseViewHolder(View(parent.context))
            }
            BaseViewHolder.TYPE_TEXT -> {
                return TextViewHolder(layoutInflater.inflate(R.layout.vh_text, null))
            }
            BaseViewHolder.TYPE_IMAGE -> {
                return ImageViewHolder(layoutInflater.inflate(R.layout.vh_image, null))
            }
            else -> {
                return BaseViewHolder(View(parent.context))
            }
        }
    }

    override fun getItemCount(): Int {
        return ds?.getDataCount() ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val baseModel = ds?.getDataAt(position)
        when (holder) {
            is TextViewHolder -> {
                holder.onBindData(baseModel as TextModel)
            }
            is ImageViewHolder -> {
                holder.onBindData(baseModel as ImageModel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ds?.getDataAt(position)?.type ?: BaseViewHolder.TYPE_UNKNOWN
    }

}
