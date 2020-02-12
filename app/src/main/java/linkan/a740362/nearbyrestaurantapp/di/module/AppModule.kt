package linkan.a740362.nearbyrestaurantapp.di.module

import linkan.a740362.nearbyrestaurantapp.data.network.ApiHelper
import linkan.a740362.nearbyrestaurantapp.data.network.AppApiHelper
import linkan.a740362.nearbyrestaurantapp.data.persistence.db.AppDbHelper
import linkan.a740362.nearbyrestaurantapp.data.persistence.db.DbHelper
import linkan.a740362.nearbyrestaurantapp.data.persistence.pref.AppPrefHelper
import linkan.a740362.nearbyrestaurantapp.data.persistence.pref.PrefHelper
import linkan.a740362.nearbyrestaurantapp.di.annotation.CoroutineScopeIO
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class AppModule {


    @Provides
    @CoroutineScopeIO
    internal fun provideCoroutineScopeIO() : CoroutineScope = CoroutineScope(Dispatchers.IO)


    @Provides
    @Singleton
    internal fun provideDBHelper(appDbHelper: AppDbHelper) : DbHelper = appDbHelper


    @Provides
    @Singleton
    internal fun provideApiHelper(appApiHelper: AppApiHelper) : ApiHelper = appApiHelper


    @Provides
    @Singleton
    internal fun providePrefHelper(appPrefHelper: AppPrefHelper) : PrefHelper = appPrefHelper


}
