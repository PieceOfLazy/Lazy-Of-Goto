package lazy.of.go.to.app.main

import lazy.of.go.to.base.MvpPresenter
import lazy.of.go.to.base.MvpView
import lazy.of.go.to.domain.entity.SettingReference


/**
* @author lazy.of.zpdl
*/
interface MainContract {

    interface View: MvpView<Presenter> {
        fun onLaunch(list: List<SettingReference>)
    }

    interface Presenter: MvpPresenter<View> {
    }
}


