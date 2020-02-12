package linkan.a740362.nearbyrestaurantapp.data.network

import androidx.lifecycle.LiveData
import io.reactivex.Single
import linkan.a740362.nearbyrestaurantapp.data.entity.api.coordinateResponseApi.RestaurantResponse
import linkan.a740362.nearbyrestaurantapp.data.entity.api.locationSearchResponse.LocationRestaurantResponse
import linkan.a740362.nearbyrestaurantapp.data.network.base.Result
import linkan.a740362.nearbyrestaurantapp.util.AppConstants

interface ApiHelper {

    fun fetchCoordinateBasedRestaurant(latitude: Double, longitude: Double)

    fun getCoordinateRestaurantLiveData(): LiveData<Result<RestaurantResponse>>

    fun fetchRestaurantWithLocation(
        query: String,
        count_per_page: Int = AppConstants.PER_PAGE_COUNT,
        startFrom: Int = 0,
        entity_type: String = "city"
    ): Single<LocationRestaurantResponse>

}
