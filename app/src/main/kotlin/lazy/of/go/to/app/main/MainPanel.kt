package lazy.of.go.to.app.main

import android.content.Context
import lazy.of.framework.library.panel.PanelLayoutContainer
import lazy.of.go.to.R
import lazy.of.go.to.base.MvpPanel
import lazy.of.go.to.base.MvpView
import lazy.of.go.to.common.Log
import lazy.of.go.to.db.DbInjection
import lazy.of.go.to.domain.data.SettingRep
import lazy.of.go.to.domain.entity.SettingRefEntity

/**
 * @author lazy.of.zpdl
 */
class MainPanel constructor(mvpView: MvpView<*>, log: Log, dbInjection: DbInjection, settingReference: SettingRefEntity, private var listener: OnPanelListener? = null):
        MvpPanel<MainPanelContract.View, MainPanelContract.Presenter>(mvpView, log, dbInjection), MainPanelContract.View {

    override fun onLaunch(list: List<SettingRefEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface OnPanelListener {
    }

    override val presenter: MainPanelContract.Presenter = MainPanelPresenter(log, dbInjection.getDB(SettingRep::class), settingReference)

    override fun onBindPresenterView(): MainPanelContract.View = this

    override fun onBindLayoutContainer(context: Context, layoutContainer: PanelLayoutContainer) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listener = null
    }

    override fun onResume() {
        presenter.onLaunch()
    }

    override fun onPause() {
    }

    override fun onLayout(): Int = R.layout.main_panel
}