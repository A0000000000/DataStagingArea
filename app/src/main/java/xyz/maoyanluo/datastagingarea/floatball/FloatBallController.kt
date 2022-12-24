package xyz.maoyanluo.datastagingarea.floatball

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import xyz.maoyanluo.datastagingarea.floatball.ds.BaseDataSource
import xyz.maoyanluo.datastagingarea.floatball.ds.MemoryDataSource
import xyz.maoyanluo.datastagingarea.floatball.model.TextModel

class FloatBallController(val context: Context) {

    private var trigger: Trigger? = null
    private var panel: Panel? = null
    private var isInit = false

    private val ds: BaseDataSource = MemoryDataSource()
    private val triggerListener = object: Trigger.TriggerListener {
        override fun onTriggerViewShow() {

        }

        override fun onTriggerViewHide() {

        }

        override fun onTrigger() {
            panel?.showView()
        }

        override fun onDataDrop(data: ClipData) {
            if (data.description?.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) == true) {
                for (i in 0 until data.itemCount) {
                    ds.addItem(TextModel(data.getItemAt(i).text.toString()))
                    panel?.dataAppendOne()
                }
            }
        }

    }
    private val panelListener = object: Panel.PanelListener {
        override fun onPanelOpen() {
            trigger?.hideView()
        }

        override fun onPanelClose() {
            trigger?.showView()
        }

    }

    fun initialize() {
        if (!isInit) {
            isInit = true
            createTrigger()
            createPanel()
        }
    }

    fun dispose() {
        if (isInit) {
            isInit = false
            trigger?.close()
            panel?.close()
            trigger = null
            panel = null
        }
    }

    private fun createTrigger() {
        trigger = Trigger(this)
        trigger?.triggerListener = triggerListener
        trigger?.showView()
    }

    private fun createPanel() {
        panel = Panel(this, ds)
        panel?.panelListener = panelListener
    }


}