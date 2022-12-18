package xyz.maoyanluo.datastagingarea.floatball

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.maoyanluo.datastagingarea.floatball.ds.BaseDataSource
import xyz.maoyanluo.datastagingarea.floatball.rv.FloatBallAdapter

class Panel(private val controller: FloatBallController, private val ds: BaseDataSource) {

    interface PanelListener {
        fun onPanelOpen()
        fun onPanelClose()
    }

    var panelListener: PanelListener? = null
    private var panelView: PanelView? = null
    private var adapter: FloatBallAdapter? = null

    private val windowManager = controller.context.getSystemService(WindowManager::class.java)

    private fun createViewInner(): View {
        val panelView = PanelView(controller.context)
        this.panelView = panelView
        panelView.setBackgroundColor(Color.GRAY)
        panelView.panelViewListener = object: PanelView.PanelViewListener {
            override fun onTouchOutSideListener() {
                hideView()
            }
        }
        val listView = RecyclerView(controller.context)
        val linearLayoutManager = LinearLayoutManager(controller.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val listViewParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        val adapter = FloatBallAdapter(ds)
        this.adapter = adapter
        listView.layoutManager = linearLayoutManager
        listView.adapter = adapter
        panelView.addView(listView, listViewParams)
        return panelView
    }

    private fun createView() {
        val view = createViewInner()
        val params = WindowManager.LayoutParams()
        val bounds = windowManager.currentWindowMetrics.bounds
        params.width = 400
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        params.gravity = Gravity.NO_GRAVITY
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        params.x = bounds.right / 2
        params.y = - bounds.bottom / 2
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        windowManager.addView(view, params)
    }

    fun showView() {
        if (panelView == null) {
            createView()
        }
        panelView?.visibility = View.VISIBLE
        panelListener?.onPanelOpen()
    }

    fun hideView() {
        panelView?.visibility = View.GONE
        panelListener?.onPanelClose()
    }

    fun dataChange() {
        adapter?.notifyDataSetChanged()
    }

}