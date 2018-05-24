package lazy.of.go.to.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import lazy.of.go.to.auth.LazyAuth;
import lazy.of.go.to.auth.firebase.FbAuth;
import lazy.of.go.to.common.LocalPreferences;
import lazy.of.go.to.common.impl.SharedPreferences;

/**
 * This is a Dagger module. We use this to bind our Application class as a Context in the AppComponent
 * By using Dagger Android we do not need to pass our Application instance to any module,
 * we simply need to expose our Application as Context.
 * One of the advantages of Dagger.Android is that your
 * Application & Activities are provided into your graph for you.
 * {@link
 * AppComponent}.
 */
@Module
abstract class AppModule {
    //expose Application as an injectable context
    @Binds
    abstract Context bindContext(Application application);

    @Singleton
    @Binds
    abstract LocalPreferences bindLocalPreferences(SharedPreferences localPreferences);

    @Singleton
    @Binds
    abstract LazyAuth bindLazyAuth(FbAuth auth);
}