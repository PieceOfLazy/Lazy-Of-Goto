package lazy.of.go.to.app.login

import android.content.Intent
import android.text.TextUtils
import android.view.ViewGroup
import lazy.of.go.to.R
import lazy.of.go.to.app.main.MainActivity
import lazy.of.go.to.auth.LazyUser
import lazy.of.go.to.auth.firebase.FbAuth
import lazy.of.go.to.base.BaseActivity
import lazy.of.go.to.common.LocalPreferences
import javax.inject.Inject
import kotlin.reflect.KClass
import android.content.ComponentName



class LoginActivity: BaseActivity() {

    @Inject
    lateinit var auth: FbAuth
    @Inject
    lateinit var localPreferences: LocalPreferences

    private var view: LoginView? = LoginView(log, object : LoginView.OnPanelListener {
        override fun onAnonymouslyLogin() {
            anonymouslyLogin()
        }

        override fun onGoogleLogin() {
            auth.signInWithGoogle(this@LoginActivity)
        }

        override fun <T : Any> onGetFeature(type: KClass<T>): T? = this@LoginActivity.onGetFeature(type)
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

            override fun onComplete(Immediately: Boolean, user: LazyUser?) {
                loadingEnd()
                if(user == null) {
                    showToast("로그인에 실패하였습니다.")
                } else {
                    showToast("로그인에 성공하였습니다.")

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }

    private fun anonymouslyLogin() {
        auth.signInAnonymously(this@LoginActivity, object : FbAuth.OnFbAuthCompleteListener {
            override fun onStart() {
                loadingStart()
            }

            override fun onComplete(Immediately: Boolean, user: LazyUser?) {
                loadingEnd()
                if(user == null) {
                    showToast("로그인에 실패하였습니다.")
                } else {
                    showToast("로그인에 성공하였습니다.")

                    user.let {
                        log.d("anonymouslyLogin : uuid : ${it.uuid}")
                        log.d("anonymouslyLogin : email : ${it.email}")
                        log.d("anonymouslyLogin : emailVerified : ${it.emailVerified}")
                        log.d("anonymouslyLogin : phoneNumber : ${it.phoneNumber}")
                        log.d("anonymouslyLogin : displayName : ${it.displayName}")
                        log.d("anonymouslyLogin : photoURL : ${it.photoURL}")
                    }

                    var anonymouslyUUID = localPreferences.getValue(LocalPreferences.KEY_AUTH_ANONYMOUSLY, "")
                    if(TextUtils.isEmpty(anonymouslyUUID)) {
                        anonymouslyUUID = user.uuid
                        localPreferences.setValue(LocalPreferences.KEY_AUTH_ANONYMOUSLY, anonymouslyUUID)
                    }
                    localPreferences.setValue(LocalPreferences.KEY_USER_UUID, anonymouslyUUID)

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}