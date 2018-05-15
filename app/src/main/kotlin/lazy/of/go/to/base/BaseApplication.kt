package lazy.of.go.to.base

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import lazy.of.framework.library.util.Log
import lazy.of.go.to.di.DaggerAppComponent

class BaseApplication: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        Log.level = Log.LEVEL.VERBOSE
        Log.prefix = "Lazy:"
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }
}