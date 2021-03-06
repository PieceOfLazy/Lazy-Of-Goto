package lazy.of.go.to.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import lazy.of.go.to.app.login.LoginActivity;
import lazy.of.go.to.app.login.LoginModule;
import lazy.of.go.to.app.main.MainActivity;
import lazy.of.go.to.app.main.MainModule;
import lazy.of.go.to.app.setting.SettingActivity;
import lazy.of.go.to.app.setting.SettingModule;
import lazy.of.go.to.app.splash.SplashActivity;
import lazy.of.go.to.app.splash.SplashModule;


/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and be aware of a scope annotation @ActivityScoped
 * When Dagger.Android annotation processor runs it will create 4 subcomponents for us.
 */
@Module
abstract class ActivityBindingBuilder {

    @ActivityScoped
    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity bindSplashActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity bindLoginActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity bindMainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SettingModule.class)
    abstract SettingActivity bindSettingActivity();

}
