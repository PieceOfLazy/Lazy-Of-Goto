package lazy.of.go.to.module;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import lazy.of.go.to.app.MainActivity;
import lazy.of.go.to.app.MainContract;
import lazy.of.go.to.app.MainPresenter;
import lazy.of.go.to.app.MainView;
import lazy.of.go.to.common.Log;
import lazy.of.go.to.di.ActivityScoped;

/**
 * @author piece.of.lazy
 */
@Module
public abstract class MainModule {

    @ActivityScoped
    @Binds
    abstract MainContract.View injectView(MainView view);

    @ActivityScoped
    @Binds
    abstract MainContract.Presenter injectPresenter(MainPresenter presenter);

    @Provides
    @ActivityScoped
    static Log provideLog(MainActivity activity) {
        return activity.getLog();
    }
}
