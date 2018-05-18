package lazy.of.go.to.base

import android.content.res.Configuration
import android.view.ViewGroup
import lazy.of.framework.library.mvp.MvpContract
import lazy.of.framework.library.mvp.MvpPresenter
import lazy.of.framework.library.mvp.MvpView
import javax.inject.Inject

/**
 * @author lazy.of.zpdl
 */
abstract class MvpActivity<V: MvpView<P>, P: MvpPresenter<V>> : BaseActivity() {

    @Inject
    lateinit var view: V
    @Inject
    lateinit var presenter: P

    protected val contract by lazy {
        MvpContract(view, presenter)
    }

    override fun onCreatedContentFrame(frame: ViewGroup) {
        contract.attach(this, frame)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

//        contract.
    }

    override fun onResume() {
        super.onResume()

        contract.launch()
    }

    override fun onDestroy() {
        super.onDestroy()

        contract.detach()
    }
}