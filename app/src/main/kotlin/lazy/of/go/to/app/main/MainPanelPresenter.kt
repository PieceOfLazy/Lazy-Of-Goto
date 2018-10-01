package lazy.of.go.to.app.main

import lazy.of.go.to.base.feature.LoadingFeature
import lazy.of.go.to.common.Log
import lazy.of.go.to.domain.data.SettingRep
import lazy.of.go.to.domain.entity.SettingEntity
import lazy.of.go.to.domain.entity.SettingRefEntity
import lazy.of.go.to.domain.usecase.GetSetting
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.usecase.UseCase
import lazy.of.go.to.usecase.UseCaseTransaction

/**
 * @author lazy.of.zpdl
 */
class MainPanelPresenter constructor(val log: Log, val dbSetting: SettingRep, private val settingReference: SettingRefEntity): MainPanelContract.Presenter {

    private var view: MainPanelContract.View? = null
    private var launch = false

    private var settingEntity: SettingEntity? = null

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
            load()
        }
    }

    private fun load() {
        val useCaseGetSetting = GetSetting(settingReference.settingIdx, dbSetting).apply {
            setUseCaseCallback(object  : UseCase.UseCaseCallback<SettingEntity> {
                override fun onSuccess(response: SettingEntity) {
                    settingEntity = response
                }

                override fun onError(exception: AppException) {}
            })
        }

        UseCaseTransaction().apply {
            setLoadingFeature { view?.getFeature(LoadingFeature::class) }
            setUseCaseCallback(object : UseCaseTransaction.Callback {
                override fun onSuccess() {

                }

                override fun onError(exception: AppException) {
                    view?.onException(exception)
                }
            })
            addUseCase(useCaseGetSetting)
            run()
        }
    }

//    private fun loadSettingReferences() {
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
//    }
}