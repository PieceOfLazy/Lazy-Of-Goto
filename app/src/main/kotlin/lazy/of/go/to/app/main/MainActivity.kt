package lazy.of.go.to.app.main

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import lazy.of.go.to.R
import lazy.of.go.to.app.setting.SettingActivity
import lazy.of.go.to.base.MvpActivity
import lazy.of.go.to.common.LocalPreferences
import javax.inject.Inject


class MainActivity: MvpActivity<MainFragment, MainContract.Presenter>(), MainFragment.OnFragmentListener {

    @Inject
    lateinit var localPreferences: LocalPreferences

    override fun onCreatedContentFrame(frame: ViewGroup) {
        super.onCreatedContentFrame(frame)

        actionBar?.apply {
            setTitle("")
        }
        val uuid = localPreferences.getValue(LocalPreferences.KEY_USER_UUID, "123")
        log.d("uuid : $uuid")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.menu_main_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
//                val intent = Intent(this, RxTestActivity::class.java)
//                startActivity(intent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

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