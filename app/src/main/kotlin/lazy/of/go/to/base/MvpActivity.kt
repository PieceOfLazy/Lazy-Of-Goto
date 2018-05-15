package lazy.of.go.to.base

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_base.*
import lazy.of.framework.library.mvp.MvpContract
import lazy.of.framework.library.mvp.MvpPresenter
import lazy.of.framework.library.mvp.MvpView
import lazy.of.go.to.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onSetContentView(savedInstanceState)

        contract.attach(this, activity_base_frame)
    }

    open fun onSetContentView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_base)
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