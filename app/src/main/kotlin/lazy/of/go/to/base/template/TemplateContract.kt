package lazy.of.go.to.base.template


import lazy.of.go.to.base.MvpPresenter
import lazy.of.go.to.base.MvpView


/**
* @author lazy.of.zpdl
*/
interface TemplateContract {

    interface View: MvpView<Presenter> {
    }

    interface Presenter: MvpPresenter<View> {
    }
}


