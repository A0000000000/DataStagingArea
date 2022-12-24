package xyz.maoyanluo.datastagingarea.floatball.floatwindow

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.maoyanluo.datastagingarea.R
import xyz.maoyanluo.datastagingarea.floatball.FloatBallController
import xyz.maoyanluo.datastagingarea.floatball.ds.BaseDataSource
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.floatview.PanelView
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.vh.BaseViewHolder
import xyz.maoyanluo.datastagingarea.floatball.floatwindow.panel.rv.adapter.FloatBallAdapter
import java.io.Closeable

class Panel(private val controller: FloatBallController, private val ds: BaseDataSource): Closeable{

    var panelListener: PanelListener? = null
    private var adapter: FloatBallAdapter? = null
    private var isWindowCreated = false

    private val windowManager = controller.context.getSystemService(WindowManager::class.java)
    private val layoutInflater = LayoutInflater.from(controller.context)
    private val view = layoutInflater.inflate(R.layout.panel, null) as PanelView
    private val panelViewListener = object: PanelView.PanelViewListener {
        override fun onTouchOutSideListener() {
            hideView()
        }
    }

    init {
        view.panelViewListener = panelViewListener
        initRecycleView()
    }

    fun showView() {
        if (!isWindowCreated) {
            windowManager.addView(view, createWindowLayoutParams())
            isWindowCreated = true
        }
        view.visibility = View.VISIBLE
        panelListener?.onPanelOpen()
    }

    fun hideView() {
        if (!isWindowCreated) {
            return
        }
        view.visibility = View.GONE
        panelListener?.onPanelClose()
    }

    fun dataAppendOne() {
        adapter?.let {
            it.notifyItemInserted(it.itemCount - 1)
        }
    }

    override fun close() {
        if (isWindowCreated) {
            windowManager.removeView(view)
            isWindowCreated = false
        }
    }

    private fun initRecycleView() {
        val listView = view.findViewById<RecyclerView>(R.id.data_list)
        val linearLayoutManager = createRecyclerViewLayoutManager()
        val adapter = createRecyclerViewAdapter()
        listView.layoutManager = linearLayoutManager
        listView.adapter = adapter
    }

    private fun createRecyclerViewLayoutManager(): RecyclerView.LayoutManager {
        val linearLayoutManager = LinearLayoutManager(controller.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        return linearLayoutManager
    }

    private fun createRecyclerViewAdapter(): RecyclerView.Adapter<BaseViewHolder> {
        val adapter = FloatBallAdapter(ds, controller.context)
        this.adapter = adapter
        return adapter
    }

    private fun createWindowLayoutParams(): WindowManager.LayoutParams {
        val params = WindowManager.LayoutParams()

        // Todo: 位置和大小抽离出来
        val bounds = windowManager.currentWindowMetrics.bounds
        params.width = 400
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        params.x = bounds.right / 2
        params.y = - bounds.bottom / 2

        params.gravity = Gravity.NO_GRAVITY
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        params.format = PixelFormat.RGBA_8888
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        return params
    }

    interface PanelListener {
        fun onPanelOpen()
        fun onPanelClose()
    }

}