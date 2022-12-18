package xyz.maoyanluo.datastagingarea.floatball.rv

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import xyz.maoyanluo.datastagingarea.floatball.ds.BaseDataSource
import xyz.maoyanluo.datastagingarea.floatball.model.TextModel

class FloatBallAdapter(private val ds: BaseDataSource?): RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            BaseViewHolder.TYPE_UNKNOWN -> {
                return BaseViewHolder(View(parent.context))
            }
            BaseViewHolder.TYPE_TEXT -> {
                return TextViewHolder(RelativeLayout(parent.context))
            }
            BaseViewHolder.TYPE_IMAGE -> {
                return BaseViewHolder(View(parent.context))
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
        if (holder is TextViewHolder) {
            holder.onBindData(ds?.getDataAt(position) as TextModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ds?.getDataAt(position)?.type ?: BaseViewHolder.TYPE_UNKNOWN
    }

}
