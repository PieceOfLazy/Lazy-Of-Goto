package lazy.of.go.to.app.main

import lazy.of.go.to.base.MvpPresenter
import lazy.of.go.to.base.MvpView


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


