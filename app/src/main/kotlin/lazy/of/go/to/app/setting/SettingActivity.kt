package lazy.of.go.to.app.setting

import android.content.Intent
import android.view.ViewGroup
import lazy.of.go.to.app.login.LoginActivity
import lazy.of.go.to.base.MvpActivity

class SettingActivity: MvpActivity<SettingFragment, SettingContract.Presenter>(), SettingFragment.OnFragmentListener {

    override fun onCreatedContentFrame(frame: ViewGroup) {
        super.onCreatedContentFrame(frame)

        actionBar?.apply {
            setTitle("설정")
        }
    }

    override fun onLogout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

        startActivity(intent)
    }
}