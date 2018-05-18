package lazy.of.go.to.module;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import lazy.of.go.to.app.MainActivity;
import lazy.of.go.to.app.MainContract;
import lazy.of.go.to.app.MainFragment;
import lazy.of.go.to.app.MainPresenter;
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
}
