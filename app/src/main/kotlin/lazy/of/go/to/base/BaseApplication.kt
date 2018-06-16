package lazy.of.go.to.base

import com.crashlytics.android.Crashlytics
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.fabric.sdk.android.Fabric
import lazy.of.framework.library.util.Log
import lazy.of.go.to.di.DaggerAppComponent


class BaseApplication: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        Log.level = Log.LEVEL.VERBOSE
        Log.prefix = "Lazy:"

        Fabric.with(this, Crashlytics())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }
}