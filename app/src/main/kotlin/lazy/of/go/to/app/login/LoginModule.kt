package lazy.of.go.to.app.login

import dagger.Module
import dagger.Provides
import lazy.of.go.to.db.DbInjection
import lazy.of.go.to.di.ActivityScoped
import lazy.of.go.to.domain.data.DbUser

/**
 * @author piece.of.lazy
 */
@Module
abstract class LoginModule {
    @Module
    companion object {
        @JvmStatic
        @ActivityScoped
        @Provides
        internal fun provideDbUser(dbInjection: DbInjection) = dbInjection.getDB(DbUser::class)
    }
}