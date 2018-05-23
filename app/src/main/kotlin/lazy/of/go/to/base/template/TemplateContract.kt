package lazy.of.go.to.base.template

import lazy.of.framework.library.mvp.MvpPresenter
import lazy.of.framework.library.mvp.MvpView

/**
* @author lazy.of.zpdl
*/
interface TemplateContract {

    interface View: MvpView<Presenter> {
    }

    interface Presenter: MvpPresenter<View> {
    }
}


