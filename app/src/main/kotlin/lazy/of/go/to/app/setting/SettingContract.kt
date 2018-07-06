package lazy.of.go.to.app.setting


import lazy.of.go.to.base.MvpPresenter
import lazy.of.go.to.base.MvpView


/**
* @author lazy.of.zpdl
*/
interface SettingContract {

    interface View: MvpView<Presenter> {
        fun onLogout()
    }

    interface Presenter: MvpPresenter<View> {
        fun logout()
    }
}


