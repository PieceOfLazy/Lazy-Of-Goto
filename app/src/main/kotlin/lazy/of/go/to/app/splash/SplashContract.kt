package lazy.of.go.to.app.splash

import lazy.of.framework.library.mvp.MvpPresenter
import lazy.of.framework.library.mvp.MvpView

/**
* @author lazy.of.zpdl
*/
interface SplashContract {

    interface View: MvpView<Presenter> {
        fun onLaunch()
        fun onFinish(isLogin: Boolean)
    }

    interface Presenter: MvpPresenter<View> {
        fun onAnimationEnd()
    }
}


