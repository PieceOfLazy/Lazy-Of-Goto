package lazy.of.go.to.app

import lazy.of.framework.library.mvp.MvpPresenter
import lazy.of.framework.library.mvp.MvpView
import lazy.of.go.to.base.BaseListener

/**
* @author lazy.of.zpdl
*/
interface MainContract {

    interface View: MvpView<Presenter> {
        fun onLanuch()
    }

    interface Presenter: MvpPresenter<View> {

    }

    interface Listener: BaseListener {

    }
}

