package xyz.maoyanluo.datastagingarea

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import xyz.maoyanluo.datastagingarea.floatball.FloatBallController

/**
 * 用于管理创建FloatBallController的类
 * Todo: 后续将逻辑抽离到Service中, 不再监听Activity的生命周期 --> 问题: URI访问权限需要绑定到一个Activity, 后续调研如何解决
 */
class MainActivityLifecycleObserver(private val mainActivity: MainActivity): DefaultLifecycleObserver {

    private var flag = false

    override fun onResume(owner: LifecycleOwner) {
        if (!flag && mainActivity.checkPermission()) {
            flag = true
            justCreateIt()
        }
        super.onResume(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        if (!flag && mainActivity.checkPermission()) {
            flag = true
            justCreateIt()
        }
        super.onStop(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        if (flag) {
            flag = false
            justDestroyIt()
        }
        super.onDestroy(owner)
    }

    private var floatBallController: FloatBallController? = null

    private fun justCreateIt() {
        if (floatBallController == null) {
            floatBallController = FloatBallController(mainActivity)
        }
        floatBallController?.initialize()
    }

    private fun justDestroyIt() {
        floatBallController?.dispose()
        floatBallController = null
    }

}