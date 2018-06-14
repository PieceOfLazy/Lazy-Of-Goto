package lazy.of.go.to.app.splash

import lazy.of.go.to.auth.LazyAuth
import lazy.of.go.to.auth.LazyUser
import lazy.of.go.to.common.Log
import lazy.of.go.to.db.DbMng
import lazy.of.go.to.db.DbUser
import lazy.of.go.to.db.OnDbListener
import lazy.of.go.to.db.data.User
import lazy.of.go.to.db.firebase.FbDbUser
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
    lateinit var dbMng: DbMng

    private val dbUser: DbUser by lazy {
        dbMng.getDB(DbUser::class)
    }

    private var view: SplashContract.View? = null
    private var launch = false

    private var user: LazyUser? = null

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
            user = auth.currentUser()
            view?.onLaunch()
        }

        user?.let {
            log.d("USER : uuid : ${it.uuid}")
            log.d("USER : email : ${it.email}")
            log.d("USER : emailVerified : ${it.emailVerified}")
            log.d("USER : phoneNumber : ${it.phoneNumber}")
            log.d("USER : displayName : ${it.displayName}")
            log.d("USER : photoURL : ${it.photoURL}")
            dbUser.getUser()
            dbUser.setUser(User(
                    it.displayName ?: "",
                    it.email ?: "",
                    it.photoURL?.toString() ?: ""),
                    object : OnDbListener<User> {
                        override fun onSuccess(data: User) {
                            log.d("fbDbUser.setUser onSuccess $data")
                        }
                        override fun onFail(code: Int) {
                            log.d("fbDbUser.setUser onFail $code")
                        }
            })
        }


    }

    override fun onAnimationEnd() {
        view?.onFinish(user != null)
    }
}