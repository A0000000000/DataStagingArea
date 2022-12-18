package xyz.maoyanluo.datastagingarea.floatball

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.view.DragEvent
import android.view.Gravity
import android.view.View
import android.view.WindowManager


class Trigger(private val controller: FloatBallController) {

    interface TriggerListener {
        fun onTriggerShow()
        fun onTriggerHide()
        fun onTriggerSide()
        fun onDataDrop(data: ClipData)
    }

    var triggerListener: TriggerListener? = null
    private var triggerView: TriggerView? = null

    private val windowManager = controller.context.getSystemService(WindowManager::class.java)

    private fun createViewInner(): View {
        val triggerView = TriggerView(controller.context)
        this.triggerView = triggerView
        triggerView.setBackgroundColor(Color.RED)
        triggerView.triggerViewListener = object: TriggerView.TriggerViewListener {
            override fun onTriggerLeftSide() {
                triggerListener?.onTriggerSide()
            }

            override fun onSingleTouch() {
                triggerListener?.onTriggerSide()
            }

        }
        return triggerView
    }

    private fun createView() {
        val view = createViewInner()
        val params = WindowManager.LayoutParams()
        params.width = 50
        params.height = 300
        params.gravity = Gravity.NO_GRAVITY
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        val bounds = windowManager.currentWindowMetrics.bounds
        params.x = bounds.right / 2
        params.y = - bounds.bottom / 3
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        bindDragAndDrop(view)
        windowManager.addView(view, params)
    }


    private fun bindDragAndDrop(view: View) {
        view.setOnDragListener { v, event ->
            event?.let {
                when (it.action) {
                    DragEvent.ACTION_DROP -> {
                        it.clipData?.let { data ->
                            triggerListener?.onDataDrop(data)
                        }
                        view.layoutParams.width = 50
                        view.layoutParams = view.layoutParams

                    }

                    else -> {
                        // ignore
                    }
                }
            }
            true
        }
    }

    fun showView() {
        if (triggerView == null) {
            createView()
        }
        triggerView?.visibility = View.VISIBLE
        triggerListener?.onTriggerShow()
    }

    fun hideView() {
        triggerView?.visibility = View.GONE
        triggerListener?.onTriggerHide()
    }


}