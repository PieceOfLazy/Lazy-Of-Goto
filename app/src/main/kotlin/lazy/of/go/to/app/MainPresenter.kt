package lazy.of.go.to.app

import lazy.of.framework.library.mvp.MvpContractListener
import lazy.of.go.to.common.Log
import javax.inject.Inject

/**
 * Created by piece.of.lazy
 */
class MainPresenter @Inject constructor(log: Log): MainContract.Presenter {

    init {
        log.d("Create MainPresenter")
    }

    private var view: MainContract.View? = null
    private var listener: MainContract.Listener? = null

    private var launch = false

    override fun onViewAttach(view: MainContract.View, listener: MvpContractListener?) {
        if(listener is MainContract.Listener) {
            this.listener = listener
        }
        this.view = view
        this.view?.setPresenter(this)
    }

    override fun onViewDetach() {
        view = null
        listener = null
    }

    override fun onLaunch() {
        if(!launch) {
            launch = true
            view?.onLanuch()
        }
    }
}