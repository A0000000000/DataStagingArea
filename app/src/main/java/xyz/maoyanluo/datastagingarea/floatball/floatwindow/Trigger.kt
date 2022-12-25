package xyz.maoyanluo.datastagingarea.floatball.floatwindow

import android.app.Activity
import android.content.ClipData
import android.graphics.PixelFormat
import android.view.DragEvent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import xyz.maoyanluo.datastagingarea.R
import xyz.maoyanluo.datastagingarea.floatball.FloatBallController
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.floatview.TriggerView
import java.io.Closeable

class Trigger(private val controller: FloatBallController): Closeable {

    var triggerListener: TriggerListener? = null
    private var isWindowCreated = false

    private val windowManager = controller.context.getSystemService(WindowManager::class.java)
    private val layoutInflater = LayoutInflater.from(controller.context)
    private val view = layoutInflater.inflate(R.layout.trigger, null) as TriggerView
    private val viewStyle = view.findViewById<View>(R.id.trigger_style)
    private val triggerViewListener =  object: TriggerView.TriggerViewListener {

        override fun onTriggerBySide() {
            triggerListener?.onTrigger()
        }

        override fun onTriggerByTouch() {
            triggerListener?.onTrigger()
        }

    }

    init {
        view.triggerViewListener = triggerViewListener
        view.setOnDragListener { _, event ->
            event?.let {
                when (it.action) {
                    DragEvent.ACTION_DROP -> {
                        it.clipData?.let { data ->
                            // Tips: 这里这样写很不合理，但是我暂时想不到别的方式解决了
                            (view.context as Activity).requestDragAndDropPermissions(event)
                            triggerListener?.onDataDrop(data)
                        }
                        viewStyle.setBackgroundColor(controller.context.getColor(R.color.trigger_bg))
                    }
                    DragEvent.ACTION_DRAG_ENTERED -> {
                        viewStyle.setBackgroundColor(controller.context.getColor(R.color.trigger_bg_active))
                    }
                    DragEvent.ACTION_DRAG_EXITED -> {
                        viewStyle.setBackgroundColor(controller.context.getColor(R.color.trigger_bg))
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
        if (!isWindowCreated) {
            windowManager.addView(view, createWindowLayoutParams())
            isWindowCreated = true
        }
        view.visibility = View.VISIBLE
        triggerListener?.onTriggerViewShow()
    }

    fun hideView() {
        view.visibility = View.GONE
        triggerListener?.onTriggerViewHide()
    }

    override fun close() {
        if (isWindowCreated) {
            isWindowCreated = false
            windowManager.removeView(view)
        }
    }

    private fun createWindowLayoutParams(): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams()

        // Todo: 位置和大小抽离出来
        params.width = 50
        params.height = 300
        val bounds = windowManager.currentWindowMetrics.bounds
        params.x = bounds.right / 2
        params.y = - bounds.bottom / 3

        params.format = PixelFormat.RGBA_8888
        params.gravity = Gravity.NO_GRAVITY
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        return params
    }

    interface TriggerListener {
        fun onTriggerViewShow()
        fun onTriggerViewHide()
        fun onTrigger()
        fun onDataDrop(data: ClipData)
    }

}