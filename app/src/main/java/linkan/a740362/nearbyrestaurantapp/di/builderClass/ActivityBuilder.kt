package linkan.a740362.nearbyrestaurantapp.di.builderClass

import linkan.a740362.nearbyrestaurantapp.ui.activity.main.MainActivity
import linkan.a740362.nearbyrestaurantapp.ui.activity.main.MainActivityModule
import linkan.a740362.nearbyrestaurantapp.ui.activity.splash.SplashActivity
import linkan.a740362.nearbyrestaurantapp.ui.activity.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import linkan.a740362.nearbyrestaurantapp.ui.fragment.home.HomeFragmentProvider

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun provideSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class, HomeFragmentProvider::class])
    abstract fun provideMainActivity(): MainActivity

}
