package lazy.of.go.to.app

import android.os.Bundle
import lazy.of.go.to.base.MvpActivity

class MainActivity: MvpActivity<MainContract.View, MainContract.Presenter>()
        , MainContract.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}