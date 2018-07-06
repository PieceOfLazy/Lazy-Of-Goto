package lazy.of.go.to.app.main

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import lazy.of.go.to.base.feature.GetFeature
import lazy.of.go.to.common.Log
import lazy.of.go.to.db.DbInjection
import lazy.of.go.to.di.ActivityScoped
import lazy.of.go.to.di.FragmentScoped
import lazy.of.go.to.domain.data.DbSettingReference
import lazy.of.go.to.domain.data.DbUser

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

        @JvmStatic
        @ActivityScoped
        @Provides
        internal fun provideGetFeature(activity: MainActivity): GetFeature = activity

        @JvmStatic
        @ActivityScoped
        @Provides
        internal fun provideDbUser(dbInjection: DbInjection) = dbInjection.getDB(DbUser::class)

        @JvmStatic
        @ActivityScoped
        @Provides
        internal fun provideSettingReference(dbInjection: DbInjection) = dbInjection.getDB(DbSettingReference::class)
    }
}