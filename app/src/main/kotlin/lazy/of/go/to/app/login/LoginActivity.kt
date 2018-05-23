package lazy.of.go.to.app.login

import android.content.Intent
import android.view.ViewGroup
import lazy.of.go.to.R
import lazy.of.go.to.auth.LazyUser
import lazy.of.go.to.auth.firebase.FbAuth
import lazy.of.go.to.base.BaseActivity
import javax.inject.Inject
import kotlin.reflect.KClass

class LoginActivity: BaseActivity() {

    @Inject
    lateinit var auth: FbAuth

    private var view: LoginView? = LoginView(log, object : LoginView.OnPanelListener {
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

//                    val intent = Intent(this@LoginActivity, AnimationActivity::class.java)
//                    startActivity(intent)
                }
            }
        })
    }
}