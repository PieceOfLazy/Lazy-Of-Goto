package lazy.of.go.to.app.splash

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import lazy.of.go.to.app.main.MainActivity
import lazy.of.go.to.base.feature.GetFeature
import lazy.of.go.to.base.feature.LoadingFeature
import lazy.of.go.to.common.Log
import lazy.of.go.to.db.DbInjection
import lazy.of.go.to.di.ActivityScoped
import lazy.of.go.to.di.FragmentScoped
import lazy.of.go.to.domain.data.DbUser

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

//    @ActivityScoped
//    @Binds
//    internal abstract fun injectFeatureListener(activity: MainActivity): FeatureListener

    @Module
    companion object {
        @JvmStatic
        @ActivityScoped
        @Provides
        internal fun provideLog(activity: SplashActivity): Log = activity.log

        @JvmStatic
        @ActivityScoped
        @Provides
        internal fun provideDbUser(dbInjection: DbInjection) = dbInjection.getDB(DbUser::class)

        @JvmStatic
        @ActivityScoped
        @Provides
        internal fun provideGetFeature(activity: SplashActivity): GetFeature = activity
    }
}