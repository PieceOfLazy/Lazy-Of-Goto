package lazy.of.go.to.module.main;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import lazy.of.go.to.app.main.MainActivity;
import lazy.of.go.to.app.main.MainContract;
import lazy.of.go.to.app.main.MainFragment;
import lazy.of.go.to.app.main.MainPresenter;
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
public abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainFragment injectFragment();

    @ActivityScoped
    @Binds
    abstract MainContract.Presenter injectPresenter(MainPresenter presenter);

    @ActivityScoped
    @Provides
    static Log provideLog(MainActivity activity) {
        return activity.getLog();
    }

    @ActivityScoped
    @Provides
    static LazyAuth provideAuth() {
        return new FbAuth();
    }
}
