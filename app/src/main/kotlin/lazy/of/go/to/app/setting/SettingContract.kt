package lazy.of.go.to.app.setting

import lazy.of.framework.library.mvp.MvpPresenter
import lazy.of.framework.library.mvp.MvpView

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


