package lazy.of.go.to.app.splash

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import lazy.of.go.to.common.Log
import lazy.of.go.to.di.ActivityScoped
import lazy.of.go.to.di.FragmentScoped

/**
 * @author piece.of.lazy
 */
@Module
abstract class SplashModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun injectFragment(): SplashFragment

    @ActivityScoped
    @Binds
    internal abstract fun injectPresenter(presenter: SplashPresenter): SplashContract.Presenter

    @Module
    companion object {
        @JvmStatic
        @ActivityScoped
        @Provides
        internal fun provideLog(activity: SplashActivity): Log = activity.log
    }
}