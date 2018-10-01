package lazy.of.go.to.app.main

import lazy.of.go.to.base.MvpPresenter
import lazy.of.go.to.base.MvpView
import lazy.of.go.to.domain.entity.SettingRefEntity


/**
* @author lazy.of.zpdl
*/
interface MainPanelContract {

    interface View: MvpView<Presenter> {
        fun onLaunch(list: List<SettingRefEntity>)
    }

    interface Presenter: MvpPresenter<View> {
    }
}


