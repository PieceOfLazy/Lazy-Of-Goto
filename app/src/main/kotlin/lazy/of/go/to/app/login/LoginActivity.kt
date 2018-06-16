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
import lazy.of.go.to.db.DbUser
import lazy.of.go.to.db.data.User
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
                if(user == null) {
                    loadingEnd()
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

            override fun onComplete(Immediately: Boolean, user: LazyUser?) {
                if(user == null) {
                    loadingEnd()
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

    private fun setUser(user: LazyUser) {
        dbUser.observableSetUser(
                User(
                        user.isAnonymous,
                        user.providerId,
                        user.displayName ?: "",
                        user.email ?: "",
                        user.photoURL?.toString() ?: "")
        ).subscribe({
            loadingEnd()
            showToast("로그인에 성공하였습니다.")

            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }, {
            loadingEnd()
            showToast("로그인에 실패하였습니다.")
        })
    }
}