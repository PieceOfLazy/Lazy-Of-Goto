package lazy.of.go.to.module.splash;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import lazy.of.go.to.app.splash.SplashActivity;
import lazy.of.go.to.app.splash.SplashContract;
import lazy.of.go.to.app.splash.SplashFragment;
import lazy.of.go.to.app.splash.SplashPresenter;
import lazy.of.go.to.auth.LazyAuth;
import lazy.of.go.to.auth.firebase.FbAuth;
import lazy.of.go.to.common.Log;
import lazy.of.go.to.di.ActivityScoped;
import lazy.of.go.to.di.FragmentScoped;

/**
 * @author piece.of.lazy
 */
@Module
public abstract class SplashModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract SplashFragment injectFragment();

    @ActivityScoped
    @Binds
    abstract SplashContract.Presenter injectPresenter(SplashPresenter presenter);

    @ActivityScoped
    @Provides
    static Log provideLog(SplashActivity activity) {
        return activity.getLog();
    }

    @ActivityScoped
    @Provides
    static LazyAuth provideAuth() {
        return new FbAuth();
    }
}
