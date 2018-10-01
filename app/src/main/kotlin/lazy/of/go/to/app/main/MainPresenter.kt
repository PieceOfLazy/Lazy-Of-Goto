package lazy.of.go.to.app.main

import lazy.of.go.to.base.feature.LoadingFeature
import lazy.of.go.to.common.LocalPreferences
import lazy.of.go.to.common.Log
import lazy.of.go.to.di.ActivityScoped
import lazy.of.go.to.domain.data.SettingRefRep
import lazy.of.go.to.domain.data.UserRepository
import lazy.of.go.to.domain.entity.SettingRefEntity
import lazy.of.go.to.domain.usecase.GetSettingRef
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.usecase.UseCase
import javax.inject.Inject

/**
 * @author lazy.of.zpdl
 */
@ActivityScoped
class MainPresenter @Inject constructor(): MainContract.Presenter {
    @Inject
    lateinit var localPreferences: LocalPreferences
    @Inject
    lateinit var log: Log
    @Inject
    lateinit var dbUser: UserRepository
    @Inject
    lateinit var settingRefRep: SettingRefRep

    private var view: MainContract.View? = null
    private var launch = false

    override fun onViewAttach(view: MainContract.View) {
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
        GetSettingRef(localPreferences.getValue(LocalPreferences.KEY_USER_UUID, ""), settingRefRep).apply {
            setLoadingFeature{view?.getFeature(LoadingFeature::class)}
            setUseCaseCallback(object : UseCase.UseCaseCallback<List<SettingRefEntity>> {
                override fun onSuccess(response: List<SettingRefEntity>) {
                    view?.onLaunch(response)
                }

                override fun onError(exception: AppException) {
                    view?.onException(exception)
                }
            })
            run()
        }
    }
}