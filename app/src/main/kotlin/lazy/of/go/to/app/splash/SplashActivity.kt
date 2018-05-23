package lazy.of.go.to.app.splash

import android.content.Intent
import lazy.of.go.to.app.login.LoginActivity
import lazy.of.go.to.base.MvpActivity

class SplashActivity: MvpActivity<SplashFragment, SplashContract.Presenter>(), SplashFragment.OnFragmentListener {

    override fun onLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        finish()
    }

    override fun onMain() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}