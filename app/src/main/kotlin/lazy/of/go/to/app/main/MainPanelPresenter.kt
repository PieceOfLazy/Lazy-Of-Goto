package lazy.of.go.to.app.main

import lazy.of.go.to.common.Log
import lazy.of.go.to.domain.data.DbUser

/**
 * @author lazy.of.zpdl
 */
class MainPanelPresenter constructor(val log: Log): MainPanelContract.Presenter {

    lateinit var dbUser: DbUser

    private var view: MainPanelContract.View? = null
    private var launch = false

    override fun onViewAttach(view: MainPanelContract.View) {
        this.view = view
        this.view?.initPresenter(this)
    }

    override fun onViewDetach() {
        view = null
    }

    override fun onLaunch() {
        if(!launch) {
            launch = true
            loadSettingReferences()
        }
    }

    private fun loadSettingReferences() {
//        GetSettingReferences(localPreferences.getValue(LocalPreferences.KEY_USER_UUID, ""), dbSettingReference).apply {
//            setLoadingFeature(getFeature.getFeature(LoadingFeature::class))
//            setUseCaseCallback(object : UseCase.UseCaseCallback<List<SettingReference>> {
//                override fun onSuccess(response: List<SettingReference>) {
//                    view?.onLaunch(response)
//                }
//
//                override fun onError(exception: AppException) {
//                    view?.onException(exception)
//                }
//            })
//            run()
//        }
    }
}