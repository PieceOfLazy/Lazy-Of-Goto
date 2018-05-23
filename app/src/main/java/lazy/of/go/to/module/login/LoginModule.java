package lazy.of.go.to.module.login;

import dagger.Module;
import dagger.Provides;
import lazy.of.go.to.app.login.LoginActivity;
import lazy.of.go.to.app.login.LoginView;
import lazy.of.go.to.auth.LazyAuth;
import lazy.of.go.to.auth.firebase.FbAuth;
import lazy.of.go.to.common.Log;
import lazy.of.go.to.di.ActivityScoped;

/**
 * @author piece.of.lazy
 */
@Module
public abstract class LoginModule {

    @ActivityScoped
    @Provides
    static LazyAuth provideAuth() {
        return new FbAuth();
    }
}
