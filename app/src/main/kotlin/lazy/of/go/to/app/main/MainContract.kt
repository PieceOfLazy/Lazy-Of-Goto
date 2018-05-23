package lazy.of.go.to.app.main

import lazy.of.framework.library.mvp.MvpPresenter
import lazy.of.framework.library.mvp.MvpView

/**
* @author lazy.of.zpdl
*/
interface MainContract {

    interface View: MvpView<Presenter> {
        fun onLaunch()
    }

    interface Presenter: MvpPresenter<View> {
    }
}


