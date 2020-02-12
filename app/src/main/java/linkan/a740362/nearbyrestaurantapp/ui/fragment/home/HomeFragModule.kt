package linkan.a740362.nearbyrestaurantapp.ui.fragment.home

import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import linkan.a740362.nearbyrestaurantapp.ui.adapter.searchAdapter.SearchRestAdapter

@Module
class HomeFragModule {

    // provide home fragment level dependency

  /*  @Provides
    fun provideFragLayoutManager(fragment : HomeFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }*/

    @Provides
    fun provideSearchAdapter(): SearchRestAdapter {
        return SearchRestAdapter(ArrayList())
    }

}