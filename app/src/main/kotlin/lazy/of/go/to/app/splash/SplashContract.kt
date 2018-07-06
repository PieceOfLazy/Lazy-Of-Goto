package lazy.of.go.to.app.splash


import lazy.of.go.to.base.MvpPresenter
import lazy.of.go.to.base.MvpView

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


