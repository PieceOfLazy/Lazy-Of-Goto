package lazy.of.go.to.base

import android.os.Bundle
import android.os.Message
import android.view.ViewGroup
import android.widget.Toast
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*
import lazy.of.go.to.R
import lazy.of.go.to.base.feature.FeatureListener
import lazy.of.go.to.base.feature.LoadingFeature
import lazy.of.go.to.base.feature.ToastFeature
import lazy.of.go.to.common.LogImpl
import lazy.of.go.to.common.WeakRefHandler
import lazy.of.go.to.widget.panel.LoadingPanel
import kotlin.reflect.KClass

/**
 * @author lazy.of.zpdl
 */
abstract class BaseActivity :
        DaggerActivity(),
        WeakRefHandler.HandleMessage,
        FeatureListener,
        LoadingFeature,
        ToastFeature {

    companion object {
        private const val LOADING_END_DELAY = 100L

        private const val MSG_LOADING_START = 0xF101
        private const val MSG_LOADING_END = 0xF102

        private const val MSG_SHOW_TOAST = 0xF201
    }

    val log by lazy {
        LogImpl(this::class.simpleName)
    }

    protected val handler by lazy {
        WeakRefHandler(this)
    }

    private val loadingPanel: LoadingPanel by lazy {
        LoadingPanel().apply {
            makeView(this@BaseActivity, activity_base_overlay)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_base)

        onCreatedContentFrame(activity_base_frame)
    }

    override fun onHandleMessage(msg: Message?) {
        msg?.let {
            when(it.what) {
                MSG_LOADING_START -> loadingPanel.loadingStart()
                MSG_LOADING_END -> loadingPanel.loadingEnd()
                MSG_SHOW_TOAST -> {
                    if(it.obj is CharSequence) {
                        Toast.makeText(this@BaseActivity, it.obj as CharSequence, it.arg1).show()
                    }
                }
            }
        }
    }

    override fun loadingStart() {
        handler.sendEmptyMessage(MSG_LOADING_START)
    }

    override fun loadingEnd() {
        handler.sendEmptyMessageDelayed(MSG_LOADING_END, LOADING_END_DELAY)
    }

    override fun showToast(message: CharSequence, duration: Int) {
        val msg = handler.obtainMessage(MSG_SHOW_TOAST).apply {
            obj = message
            arg1 = duration
        }
        handler.sendMessage(msg)
    }

    override fun showToast(message: Int, duration: Int) {
        val msg = handler.obtainMessage(MSG_SHOW_TOAST).apply {
            obj = getString(message)
            arg1 = duration
        }
        handler.sendMessage(msg)
    }

    override fun <T : Any> onGetFeature(type: KClass<T>): T?  {
        if (type.isInstance(this)) {
            @Suppress("UNCHECKED_CAST")
            return this as T
        }
        return null
    }

    abstract fun onCreatedContentFrame(frame: ViewGroup)

}
