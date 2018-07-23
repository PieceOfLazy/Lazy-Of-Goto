package lazy.of.go.to.app.main

import android.content.Context
import lazy.of.framework.library.panel.PanelLayoutContainer
import lazy.of.go.to.R
import lazy.of.go.to.base.MvpPanel
import lazy.of.go.to.base.MvpView
import lazy.of.go.to.common.Log
import lazy.of.go.to.db.DbInjection
import lazy.of.go.to.domain.entity.SettingReference

/**
 * @author lazy.of.zpdl
 */
class MainPanel constructor(mvpView: MvpView<*>, log: Log, dbInjection: DbInjection, val settingReference: SettingReference, private var listener: OnPanelListener? = null):
        MvpPanel<MainPanelContract.View, MainPanelContract.Presenter>(mvpView, log, dbInjection), MainPanelContract.View {
    override fun onLaunch(list: List<SettingReference>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface OnPanelListener {
    }

    override val presenter: MainPanelContract.Presenter = MainPanelPresenter(log)

    override fun onBindPresenterView(): MainPanelContract.View = this

    override fun onBindLayoutContainer(context: Context, layoutContainer: PanelLayoutContainer) {
//        Log.d("KKH", "onBindLayoutContainer ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listener = null
    }

    override fun onResume() {
//        Log.d("KKH", "onResume ")
    }

    override fun onPause() {
//        Log.d("KKH", "onPause ")
    }

    override fun onLayout(): Int = R.layout.main_panel
}