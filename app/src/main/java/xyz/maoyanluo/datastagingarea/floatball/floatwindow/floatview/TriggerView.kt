package xyz.maoyanluo.datastagingarea.floatball.floatwindow.floatview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.OnGestureListener
import android.view.MotionEvent
import android.widget.FrameLayout
import kotlin.math.abs

class TriggerView(context: Context, attrs: AttributeSet? = null): FrameLayout(context, attrs) {


    var triggerViewListener: TriggerViewListener? = null
    private val gestureDetector = GestureDetector(context, object: OnGestureListener {
        override fun onDown(e: MotionEvent?): Boolean {
            return false
        }

        override fun onShowPress(e: MotionEvent?) {

        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            triggerViewListener?.onTriggerByTouch()
            return true
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            return false
        }

        override fun onLongPress(e: MotionEvent?) {

        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            if (abs(velocityX) >= 500 && abs((e1?.x ?: 0f) - (e2?.x ?: 0f)) > 100) {
                triggerViewListener?.onTriggerBySide()
                return true
            }
            return false
        }
    }, Handler(Looper.getMainLooper()))

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    interface TriggerViewListener {
        fun onTriggerBySide()
        fun onTriggerByTouch()
    }

}