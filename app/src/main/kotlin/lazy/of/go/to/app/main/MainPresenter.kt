package lazy.of.go.to.app.main

import lazy.of.go.to.base.feature.GetFeature
import lazy.of.go.to.base.feature.LoadingFeature
import lazy.of.go.to.common.LocalPreferences
import lazy.of.go.to.common.Log
import lazy.of.go.to.di.ActivityScoped
import lazy.of.go.to.domain.data.DbSettingReference
import lazy.of.go.to.domain.data.DbUser
import lazy.of.go.to.domain.entity.SettingReference
import lazy.of.go.to.domain.usecase.GetSettingReferences
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
    lateinit var getFeature: GetFeature
    @Inject
    lateinit var log: Log
    @Inject
    lateinit var dbUser: DbUser
    @Inject
    lateinit var dbSettingReference: DbSettingReference

    private var view: MainContract.View? = null
    private var launch = false

    override fun onViewAttach(view: MainContract.View) {
        this.view = view
        this.view?.setPresenter(this)
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
        GetSettingReferences(localPreferences.getValue(LocalPreferences.KEY_USER_UUID, ""), dbSettingReference).apply {
            setLoadingFeature(getFeature.getFeature(LoadingFeature::class))
            setUseCaseCallback(object : UseCase.UseCaseCallback<List<SettingReference>> {
                override fun onSuccess(response: List<SettingReference>) {
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