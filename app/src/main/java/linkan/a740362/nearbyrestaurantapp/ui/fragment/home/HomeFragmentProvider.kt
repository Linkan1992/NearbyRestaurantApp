package linkan.a740362.nearbyrestaurantapp.ui.fragment.home

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentProvider {

    @ContributesAndroidInjector(modules = [HomeFragModule::class])
    abstract fun provideHomeFragment(): HomeFragment

}