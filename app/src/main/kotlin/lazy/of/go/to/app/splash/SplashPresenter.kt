package lazy.of.go.to.app.splash

import lazy.of.go.to.auth.LazyAuth
import lazy.of.go.to.base.feature.GetFeature
import lazy.of.go.to.common.Log
import lazy.of.go.to.di.ActivityScoped
import lazy.of.go.to.domain.data.DbUser
import lazy.of.go.to.domain.entity.User
import lazy.of.go.to.domain.usecase.SetUser
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.usecase.UseCase
import javax.inject.Inject

/**
 * @author lazy.of.zpdl
 */
@ActivityScoped
class SplashPresenter @Inject constructor(): SplashContract.Presenter {
    @Inject
    lateinit var getFeature: GetFeature
    @Inject
    lateinit var log: Log
    @Inject
    lateinit var auth: LazyAuth
    @Inject
    lateinit var dbUser: DbUser

    private var view: SplashContract.View? = null
    private var launch = false

    private var user: User? = null

    private var taskOffDbUser = false
    private var taskOffAnimation = false

    override fun onViewAttach(view: SplashContract.View) {
        this.view = view
        this.view?.setPresenter(this)
    }

    override fun onViewDetach() {
        view = null
    }

    override fun onLaunch() {
        if(!launch) {
            launch = true
            auth.currentUser()?.let {
                SetUser(it, dbUser).apply {
                    setUseCaseCallback(object : UseCase.UseCaseCallback<User> {
                        override fun onSuccess(response: User) {
                            this@SplashPresenter.user = response
                            taskOffDbUser = true
                            taskOff()
                        }

                        override fun onError(exception: AppException) {
                            this@SplashPresenter.user = null
                            taskOffDbUser = true
                            taskOff()
                        }
                    })
                    run()
                }
            } ?: run {
                taskOffDbUser = true
            }
            view?.onLaunch()
        }
    }

    private fun taskOff() {
        if(taskOffDbUser && taskOffAnimation) {
            view?.onFinish(user != null)
        }
    }

    override fun onAnimationEnd() {
        taskOffAnimation = true
        taskOff()
    }
}
