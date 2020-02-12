package linkan.a740362.nearbyrestaurantapp.ui.activity.main

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import linkan.a740362.nearbyrestaurantapp.ui.adapter.geocodeAdapter.GeocodeRestAdapter

@Module
class MainActivityModule {

    // provide main activity level dependency

    @Provides
    fun provideLayoutManager(activity: MainActivity): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @Provides
    fun provideGeocodeAdapter(): GeocodeRestAdapter {
        return GeocodeRestAdapter(ArrayList())
    }

}