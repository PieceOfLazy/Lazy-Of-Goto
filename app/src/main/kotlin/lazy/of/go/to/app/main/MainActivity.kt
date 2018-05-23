package lazy.of.go.to.app.main

import lazy.of.go.to.base.MvpActivity

class MainActivity: MvpActivity<MainFragment, MainContract.Presenter>(), MainFragment.OnFragmentListener {

    override fun onLogin() {
//        val intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)
//
//        finish()
    }

    override fun onMain() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}