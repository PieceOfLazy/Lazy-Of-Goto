package lazy.of.go.to.app.login

import android.content.Intent
import android.text.TextUtils
import android.view.ViewGroup
import lazy.of.go.to.R
import lazy.of.go.to.app.main.MainActivity
import lazy.of.go.to.auth.AuthUser
import lazy.of.go.to.auth.firebase.FbAuth
import lazy.of.go.to.base.BaseActivity
import lazy.of.go.to.base.feature.LoadingFeature
import lazy.of.go.to.common.LocalPreferences
import lazy.of.go.to.domain.data.DbUser
import lazy.of.go.to.domain.entity.User
import lazy.of.go.to.domain.usecase.SetUser
import lazy.of.go.to.exception.AppException
import lazy.of.go.to.usecase.UseCase
import javax.inject.Inject
import kotlin.reflect.KClass


class LoginActivity: BaseActivity() {

    @Inject
    lateinit var auth: FbAuth
    @Inject
    lateinit var localPreferences: LocalPreferences
    @Inject
    lateinit var dbUser: DbUser

    private var view: LoginView? = LoginView(log, object : LoginView.OnPanelListener {
        override fun onAnonymouslyLogin() {
            anonymouslyLogin()
        }

        override fun onGoogleLogin() {
            auth.signInWithGoogle(this@LoginActivity)
        }

        override fun <T : Any> getFeature(type: KClass<T>): T? = this@LoginActivity.getFeature(type)
    })

    override fun onCreatedContentFrame(frame: ViewGroup) {
        actionBar?.apply {
            setTitle(R.string.login)
        }

        view?.makeView(this, frame)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        auth.signInActivityResult(this, requestCode, resultCode, data, object : FbAuth.OnFbAuthCompleteListener {
            override fun onStart() {
                loadingStart()
            }

            override fun onComplete(Immediately: Boolean, user: AuthUser?) {
                loadingEnd()
                if(user == null) {
                    showToast("로그인에 실패하였습니다.")
                } else {
                    setUser(user)
                }
            }
        })
    }

    private fun anonymouslyLogin() {
        auth.signInAnonymously(this@LoginActivity, object : FbAuth.OnFbAuthCompleteListener {
            override fun onStart() {
                loadingStart()
            }

            override fun onComplete(Immediately: Boolean, user: AuthUser?) {
                loadingEnd()
                if(user == null) {
                    showToast("로그인에 실패하였습니다.")
                } else {
                    var anonymouslyUUID = localPreferences.getValue(LocalPreferences.KEY_AUTH_ANONYMOUSLY, "")
                    if(TextUtils.isEmpty(anonymouslyUUID)) {
                        anonymouslyUUID = user.uuid
                        localPreferences.setValue(LocalPreferences.KEY_AUTH_ANONYMOUSLY, anonymouslyUUID)
                    }
                    localPreferences.setValue(LocalPreferences.KEY_USER_UUID, anonymouslyUUID)

                    setUser(user)
                }
            }
        })
    }

    private fun setUser(user: AuthUser) {
        SetUser(user, dbUser).apply {
            setLoadingFeature {
                getFeature(LoadingFeature::class)
            }
            setUseCaseCallback(object : UseCase.UseCaseCallback<User> {
                override fun onSuccess(response: User) {
                    showToast("로그인에 성공하였습니다.")

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }

                override fun onError(exception: AppException) {
                    showToast("로그인에 실패하였습니다.")
                }
            })
            run()
        }
    }
}