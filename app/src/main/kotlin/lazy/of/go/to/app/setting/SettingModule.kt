package lazy.of.go.to.app.setting

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
abstract class SettingModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun injectFragment(): SettingFragment

    @ActivityScoped
    @Binds
    internal abstract fun injectPresenter(presenter: SettingPresenter): SettingContract.Presenter

    @Module
    companion object {
        @JvmStatic
        @ActivityScoped
        @Provides
        internal fun provideLog(activity: SettingActivity): Log = activity.log
    }
}