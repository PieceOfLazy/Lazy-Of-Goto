package lazy.of.go.to.app.splash

import lazy.of.go.to.auth.LazyAuth
import lazy.of.go.to.auth.LazyUser
import lazy.of.go.to.common.Log
import lazy.of.go.to.di.ActivityScoped
import javax.inject.Inject

/**
 * @author lazy.of.zpdl
 */
@ActivityScoped
class SplashPresenter @Inject constructor(private val log: Log, private val auth: LazyAuth): SplashContract.Presenter {
    init {
        log.d("Create MainPresenter")
    }

    private var view: SplashContract.View? = null
    private var launch = false

    private var user: LazyUser? = null

    override fun onViewAttach(view: SplashContract.View) {
        this.view = view
        this.view?.setPresenter(this)
    }

    override fun onViewDetach() {
        view = null
    }

    override fun onLaunch() {
        if(!launch) {
            launch = true
            user = auth.currentUser()
            view?.onLaunch()
        }
    }

    override fun onAnimationEnd() {
        view?.onFinish(user != null)
    }
}