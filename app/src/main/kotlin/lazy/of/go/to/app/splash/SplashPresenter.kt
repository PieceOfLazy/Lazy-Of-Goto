package lazy.of.go.to.app.splash

import io.reactivex.disposables.Disposable
import lazy.of.go.to.auth.LazyAuth
import lazy.of.go.to.common.Log
import lazy.of.go.to.db.DbInjection
import lazy.of.go.to.db.DbUser
import lazy.of.go.to.db.data.User
import lazy.of.go.to.di.ActivityScoped
import javax.inject.Inject

/**
 * @author lazy.of.zpdl
 */
@ActivityScoped
class SplashPresenter @Inject constructor(): SplashContract.Presenter {

    @Inject
    lateinit var log: Log
    @Inject
    lateinit var auth: LazyAuth
    @Inject
    lateinit var dbInjection: DbInjection

    private val dbUser: DbUser by lazy {
        dbInjection.getDB(DbUser::class)
    }

    private var view: SplashContract.View? = null
    private var launch = false

    private var user: User? = null

    private var taskOffDbUser = false
    private var taskOffAnimation = false

    private var observable : Disposable? = null

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
                observable = dbUser.observableSetUser(
                        User(
                                it.isAnonymous,
                                it.providerId,
                                it.displayName ?: "",
                                it.email ?: "",
                                it.photoURL?.toString() ?: "")
                ).map {
                    dbUser.getUser()
                }.subscribe({
                    this@SplashPresenter.user = it
                    taskOffDbUser = true
                    taskOff()

                    observable?.let {
                        log.d("USER : subscribe onNext : ${it.isDisposed}")
                    }
                }, {
                    this@SplashPresenter.user = null
                    taskOffDbUser = true
                    taskOff()

                    observable?.let {
                        log.d("USER : subscribe onError : ${it.isDisposed}")
                    }
                }, {
                    observable?.let {
                        log.d("USER : observable onComplete : ${it.isDisposed}")
                    }
                })

                observable?.let {
                    log.d("USER : observable isDisposed : ${it.isDisposed}")
                }


                log.d("USER : uuid : ${it.uuid}")
                log.d("USER : email : ${it.email}")
                log.d("USER : emailVerified : ${it.emailVerified}")
                log.d("USER : phoneNumber : ${it.phoneNumber}")
                log.d("USER : displayName : ${it.displayName}")
                log.d("USER : photoURL : ${it.photoURL}")
                log.d("USER : isAnonymous : ${it.isAnonymous}")
                log.d("USER : providerId : ${it.providerId}")
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
