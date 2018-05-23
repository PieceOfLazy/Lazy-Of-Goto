package lazy.of.go.to.app.main

import lazy.of.go.to.auth.LazyAuth
import lazy.of.go.to.common.Log
import lazy.of.go.to.di.ActivityScoped
import javax.inject.Inject

/**
 * @author lazy.of.zpdl
 */
@ActivityScoped
class MainPresenter @Inject constructor(private val log: Log, private val auth: LazyAuth): MainContract.Presenter {
    init {
        log.d("Create MainPresenter")
    }

    private var view: MainContract.View? = null
    private var launch = false

    override fun onViewAttach(view: MainContract.View) {
        this.view = view
        this.view?.setPresenter(this)
    }

    override fun onViewDetach() {
        view = null
    }

    override fun onLaunch() {
        if(!launch) {
            launch = true
            view?.onLaunch()
        }
    }
}