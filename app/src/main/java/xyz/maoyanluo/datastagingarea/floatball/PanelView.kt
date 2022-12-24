package xyz.maoyanluo.datastagingarea.floatball

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class PanelView(context: Context, attrs: AttributeSet? = null): FrameLayout(context, attrs) {

    var panelViewListener: PanelViewListener? = null

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_OUTSIDE) {
            onTouchOutSide()
        }
        return super.onTouchEvent(event)
    }

    private fun onTouchOutSide() {
        panelViewListener?.onTouchOutSideListener()
    }

    interface PanelViewListener {
        fun onTouchOutSideListener()
    }

}