package lazy.of.go.to.app.main

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
abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun injectFragment(): MainFragment

    @ActivityScoped
    @Binds
    internal abstract fun injectPresenter(presenter: MainPresenter): MainContract.Presenter

    @Module
    companion object {
        @JvmStatic
        @ActivityScoped
        @Provides
        internal fun provideLog(activity: MainActivity): Log = activity.log
    }
}