package lazy.of.go.to.app.setting

import lazy.of.go.to.auth.LazyAuth
import lazy.of.go.to.common.LocalPreferences
import lazy.of.go.to.common.Log
import lazy.of.go.to.di.ActivityScoped
import javax.inject.Inject

/**
 * @author lazy.of.zpdl
 */
@ActivityScoped
class SettingPresenter @Inject constructor(): SettingContract.Presenter {

    @Inject
    lateinit var log: Log
    @Inject
    lateinit var lazyAuth: LazyAuth

    private var view: SettingContract.View? = null
    private var launch = false

    override fun onViewAttach(view: SettingContract.View) {
        this.view = view
        this.view?.setPresenter(this)
    }

    override fun onViewDetach() {
        view = null
    }

    override fun onLaunch() {
        if(!launch) {
            launch = true
        }
    }

    override fun logout() {
        lazyAuth.signOut()
        view?.onLogout()
    }
}